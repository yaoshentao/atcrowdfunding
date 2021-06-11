package com.atguigu.crowd.mvc.handler;


import com.atguigu.crowd.entity.Auth;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.AuthService;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @ResponseBody
    @RequestMapping("assign/get/all/auth.do")
    public List<Auth> getAll(){
        return authService.getAll();
    }

    @ResponseBody
    @RequestMapping("assign/get/assigned/auth/id/by/role/id.do")
    public ResultEntity<List<Integer>> getAssignAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId) {
        List<Integer> adminIdList =  authService.getAssignAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(adminIdList);
    }


    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.do")
    public ResultEntity<String>  saveRoleAuthRelathinship(@RequestBody Map<String, List<Integer>> map) {

        authService.saveRoleAuthRelationship(map);
        return ResultEntity.successWithoutData();
    }


    @RequestMapping("assign/to/assign/role/page.do")
    public String toAssignRolePage(
            @RequestParam("adminId") Integer adminId,

            ModelMap modelMap ) {
        // 1.查询已分配的角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

        // 2. 查询未分配的角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);

        // 存入模型
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);

        return "assign-role";
    }

    @RequestMapping("assign/do/role/assign.do")
    public String saveAdminRoleRelationShip(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "roleList", required = false) List<Integer> roleIdList

    ){
        adminService.saveAdminRoleRelationship(adminId, roleIdList);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }
}
