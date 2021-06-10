package com.atguigu.crowd;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.RoleService;
import com.atguigu.crowd.service.impl.AdminServiceImpl;
import com.atguigu.crowd.util.CrowdUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

// 这里用junit5进行测试
@SpringJUnitConfig(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testTx(){
        Admin admin;
        admin = new Admin(null,"Rachel","123123","rui","rui@qq.com",null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void testInsertAdmin(){
        Admin admin;
        admin = new Admin(null,"Rachel","123123","rui","rui@qq.com",null);
        int count = adminMapper.insert(admin);
        System.out.println(count);
    }

    @Test
    public void  testConnection() throws SQLException{
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testLog(){
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        logger.debug("Debug");
        logger.debug("Debug");
        logger.debug("Debug");

        logger.info("你好");
        logger.warn("Warn");
        logger.error("Error");
    }

    @Test
    public void testCreateData(){
        Admin admin;
        for (int i = 0; i < 10; i++) {
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String password = String.valueOf((int)(Math.random()*900000 + 100000));;
            String userPswd = CrowdUtil.md5(password);
            String login = getStringRandom(5);
            String userName = login.substring(0,3);
            admin = new Admin(null,captureName(login),userPswd,userName,userName+"@qq.com",date);
            adminMapper.insert(admin);
        }
    }
    //生成随机用户名，数字和字母组成,
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            val += (char)(random.nextInt(26)+97);
        }
        return val;
    }
    public static String captureName(String name) {
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

    @Test
    public void getRole(){
        List<Role> roles = roleMapper.selectRoleByKeyword("yao");
        for(Role role: roles){
            System.out.println(role);
        }
    }
}