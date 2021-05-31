package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.AdminExample;
import com.atguigu.crowd.entity.AdminExample.Criteria;
import com.atguigu.crowd.exception.LoginFailedException;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.util.CrowdUtil;
import com.github.pagehelper.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
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
}
