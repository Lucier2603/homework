package org.hsbc.homework.api;

import org.hsbc.homework.msg.BaseRequest;
import org.hsbc.homework.msg.BaseResponse;
import org.hsbc.homework.msg.LoginReq;

public interface LoginApi extends HttpApi {
    BaseResponse login(LoginReq req);

    BaseResponse logout(BaseRequest req);
}
