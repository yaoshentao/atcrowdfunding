package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.service.impl.RoleServiceImpl;
import com.atguigu.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    @ResponseBody
    @PreAuthorize("hasRole('部长')")
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
            @RequestParam(value="keyword", defaultValue="") String keyword
    ){
        //获取分页数据
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(pageInfo);
    }

    @ResponseBody
    @RequestMapping("role/save.do")
    public ResultEntity<String> saveRole(@RequestParam("roleName") String roleName) {
        System.out.println(roleName);
        roleService.saveRole(new Role(null, roleName));

        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("role/update.do")
    public ResultEntity<String> updateRole(Role role) {

        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/role/remove/by/role/id/array.do")
    public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIdList) {

        roleService.removeRole(roleIdList);

        return ResultEntity.successWithoutData();
    }


}
