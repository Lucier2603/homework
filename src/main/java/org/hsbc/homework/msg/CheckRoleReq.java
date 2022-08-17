package org.hsbc.homework.msg;

public class CheckRoleReq extends BaseRequest {
    private String roleName;

    public CheckRoleReq() {
    }

    public CheckRoleReq(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
