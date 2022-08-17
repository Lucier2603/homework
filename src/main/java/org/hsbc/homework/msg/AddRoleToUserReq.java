package org.hsbc.homework.msg;

public class AddRoleToUserReq extends BaseRequest {
    private String userName;
    private String roleName;

    public AddRoleToUserReq() {
    }

    public AddRoleToUserReq(String userName, String roleName) {
        this.userName = userName;
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public String getRoleName() {
        return roleName;
    }
}
