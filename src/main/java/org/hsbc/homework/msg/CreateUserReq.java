package org.hsbc.homework.msg;

public class CreateUserReq extends BaseRequest {
    private String userName;
    private String passWd;

    public CreateUserReq() {
    }

    public CreateUserReq(String userName, String passWd) {
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
