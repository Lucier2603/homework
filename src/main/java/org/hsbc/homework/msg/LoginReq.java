package org.hsbc.homework.msg;

public class LoginReq extends BaseRequest {
    private String userName;
    private String passWd;

    public LoginReq() {
    }

    public LoginReq(String userName, String passWd) {
        this.userName = userName;
        this.passWd = passWd;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWd() {
        return passWd;
    }
}
