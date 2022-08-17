package org.hsbc.homework.msg;

public class CreateRoleReq extends BaseRequest {
    private String roleName;

    public CreateRoleReq() {
    }

    public CreateRoleReq(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
