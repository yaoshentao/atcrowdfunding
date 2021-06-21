package com.atguigu.security.entity;

public class Admin {
    private Integer id;
    private String loginacct;
    private String userpswd;
    private String userName;
    private String email;

    public Admin(Integer id, String loginacct, String userpswd, String userName, String email) {
        this.id = id;
        this.loginacct = loginacct;
        this.userpswd = userpswd;
        this.userName = userName;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginacct() {
        return loginacct;
    }

    public void setLoginacct(String loginacct) {
        this.loginacct = loginacct;
    }

    public String getUserpswd() {
        return userpswd;
    }

    public void setUserpswd(String userpswd) {
        this.userpswd = userpswd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Admin() {
    }
}
