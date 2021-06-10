package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAllAdmin();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    //根据id删除用户
    void remove(Integer adminId);

    //根据id更新用户信息
    void update(Admin adminId);

    Admin getAdminById(Integer adminId);

}
