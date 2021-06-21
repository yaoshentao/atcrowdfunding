package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("admin/do/logout.html")
    public String doLoginOut(HttpSession session){
        //强制Session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("/admin/get/page.html")
    public String getPage(
            // defaultValue使用的默认值
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelMap){
        //调动service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        // 将pageInfo存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    // 删除成员
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyword") String keyword) {
        // 执行删除
        adminService.remove(adminId);

        // 页面跳转：回到分页页面
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    // 新增成员
    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/admin/save.html")
    public String save(Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
    }

    // 修改成员信息
    @RequestMapping("/admin/to/edit.html")
    public String toEdit(@RequestParam("adminId") Integer adminId,
                         ModelMap modelMap) {
        Admin admin = adminService.getAdminById(adminId);
        modelMap.addAttribute("admin", admin);
        return "admin-edit";
    }

    // 提交更新信息
    @RequestMapping(value = "/admin/update.html")
    public String update(Admin admin,
                         @RequestParam(value = "keyword",defaultValue = "") String keyword,
                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

}
