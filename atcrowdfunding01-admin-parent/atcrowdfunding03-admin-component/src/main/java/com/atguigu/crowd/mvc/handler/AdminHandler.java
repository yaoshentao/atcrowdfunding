package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * ClassName:    AdminHandler
 * Package:    com.atguigu.crowd.mvc.handler
 * Description:
 * Datetime:    2021/5/31   21:38
 * Author:   yaoshentao
 */

@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd,
                          HttpSession httpSession){
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
        httpSession.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "admin-main";
    }
}
