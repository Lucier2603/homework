package org.hsbc.homework.api;

import org.hsbc.homework.msg.AddRoleToUserReq;
import org.hsbc.homework.msg.BaseRequest;
import org.hsbc.homework.msg.BaseResponse;
import org.hsbc.homework.msg.CheckRoleReq;

import java.util.Set;

public interface UserRoleRelApi extends HttpApi {
    BaseResponse addRoleToUser(AddRoleToUserReq req);

    BaseResponse<Boolean> checkRole(CheckRoleReq req);

    BaseResponse<Set<String>> getAllRules(BaseRequest req);
}
