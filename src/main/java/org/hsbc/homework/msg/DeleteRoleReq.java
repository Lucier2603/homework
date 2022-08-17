package org.hsbc.homework.msg;

public class DeleteRoleReq extends BaseRequest {
    private String roleName;

    public DeleteRoleReq() {
    }

    public DeleteRoleReq(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
