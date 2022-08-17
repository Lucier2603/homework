package org.hsbc.homework.model;

public class User {
    // 用户名
    private String userName;
    // 密码
    private String passwdMd5;

    public User(String userName, String passwdMd5) {
        this.userName = userName;
        this.passwdMd5 = passwdMd5;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswdMd5() {
        return passwdMd5;
    }

    public void setPasswdMd5(String passwdMd5) {
        this.passwdMd5 = passwdMd5;
    }
}
