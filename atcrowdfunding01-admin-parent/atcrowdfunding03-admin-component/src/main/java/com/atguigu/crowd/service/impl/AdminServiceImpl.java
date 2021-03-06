package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.AdminExample;
import com.atguigu.crowd.entity.AdminExample.Criteria;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseException;
import com.atguigu.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;
import com.github.pagehelper.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    // 打印日志
    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveAdmin(Admin admin) {
        // 密码加密
        String userPswd = admin.getUserPswd();
        admin.setUserPswd(passwordEncoder.encode(userPswd));

        // ...

        // 2 创建时间
        admin.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        // 3 用户名已存在错误
        try {
            adminMapper.insert(admin);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.info("异常全类名" + e.getClass().getName());
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Admin> getAllAdmin() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        // 1.根据登录账号查询
        AdminExample adminExample = new AdminExample();

        //创建Criteria对象
        Criteria criteria = adminExample.createCriteria();

        // 在criteria对象中封装查询条件
        criteria.andLoginEqualTo(loginAcct);


        //调用mapper方法查询
        List<Admin> list = adminMapper.selectByExample(adminExample);


        //判断admin是否为空
        if(list==null||list.size()==0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        //如果大于1抛出系统错误
        if(list.size()>1){
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = list.get(0);


        //如果admin为null则抛出异常
        if(admin==null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }



        System.out.println("查到用户了");

        // 4.Admin对象不为null，取出密码
        String userPswdDB = admin.getUserPswd();

        // 5.将表单提交的明文密码进行加密
        String userPswmForm = CrowdUtil.md5(userPswd);

        // 6.比较密码
        System.out.println("登录密码"+userPswmForm);
        System.out.println("用户密码"+userPswdDB);

        if (!Objects.equals(userPswdDB, userPswmForm)) {
            // 7.结果不一致，抛异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 8.如果一致则返回Admin对象
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {

        //  1.调用PageHelper方法开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        // 2.执行查询
        List<Admin> list = adminMapper.selectAdminByKeyWord(keyword);

        // 3.封装到PageInfo对象中
        return new PageInfo<>(list);
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public void update(Admin admin) {
        try{
            adminMapper.updateByPrimaryKeySelective(admin);
        }
        catch(Exception e){
            e.printStackTrace();
            // 用户名重复
            logger.info("异常全类名"+e.getClass().getName());
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }

        }
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 1. 根据adminId删除旧的关联关系数据
        adminMapper.deleteOldRelationship(adminId);

        // 2.根据roleIdList和adminId保存新的关联关系
        if (roleIdList != null && roleIdList.size()>0) {
            adminMapper.insertNewRelationship(adminId, roleIdList);
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String username) {

        AdminExample adminExample = new AdminExample();

        Criteria criteria = adminExample.createCriteria();

        criteria.andLoginEqualTo(username);

        List<Admin> admins = adminMapper.selectByExample(adminExample);

        return admins.get(0);
    }
}
