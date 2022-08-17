package org.hsbc.homework.api;

import org.hsbc.homework.msg.BaseResponse;
import org.hsbc.homework.msg.CreateUserReq;
import org.hsbc.homework.msg.DeleteUserReq;

public interface UserApi extends HttpApi {

    BaseResponse createUser(CreateUserReq req);

    BaseResponse deleteUser(DeleteUserReq req);
}
