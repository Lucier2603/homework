package org.hsbc.homework.api;

import org.hsbc.homework.msg.BaseResponse;
import org.hsbc.homework.msg.CreateRoleReq;
import org.hsbc.homework.msg.DeleteRoleReq;

public interface RoleApi extends HttpApi {

    BaseResponse createRole(CreateRoleReq req);

    BaseResponse deleteRole(DeleteRoleReq req);
}
