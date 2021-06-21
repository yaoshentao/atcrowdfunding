package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {
    List<Auth> getAll();
    List<Integer> getAssignAuthIdByRoleId(Integer roleId);
    void saveRoleAuthRelationship(Map<String, List<Integer>> map);
    // AuthService
    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
