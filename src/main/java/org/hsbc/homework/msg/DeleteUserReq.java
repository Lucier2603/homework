package org.hsbc.homework.msg;

public class DeleteUserReq extends BaseRequest {
    private String userName;

    public DeleteUserReq() {
    }

    public DeleteUserReq(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
