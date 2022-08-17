package org.hsbc.homework.manager;

import org.hsbc.homework.api.LoginApi;
import org.hsbc.homework.msg.BaseRequest;
import org.hsbc.homework.msg.BaseResponse;
import org.hsbc.homework.msg.LoginReq;
import org.hsbc.homework.service.ServiceManager;
import org.hsbc.homework.service.TokenService;
import org.hsbc.homework.service.UserService;
import org.hsbc.homework.utils.RouterUrl;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;

public class LoginManager implements LoginApi {

    private UserService userService = (UserService) ServiceManager.getGlobal().get(UserService.class.getName());
    private TokenService tokenService = (TokenService) ServiceManager.getGlobal().get(TokenService.class.getName());

    @RouterUrl("/api/login")
    public BaseResponse login(LoginReq req) {
        String userName = req.getUserName();
        String passWd = req.getPassWd();

        if (!userService.userExist(userName)) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST);
        }

        if (!userService.checkPasswd(userName, passWd)) {
            throw new BusinessException(ErrorCode.PASSWD_INCORRECT);
        }

        String token = TokenService.genToken(userName);
        tokenService.setToken(userName, token);

        return new BaseResponse().success(token, null);
    }

    @RouterUrl("/api/logout")
    public BaseResponse logout(BaseRequest req) {
        String token = req.getToken();

        tokenService.validToken(token);
        tokenService.removeToken(token);

        return new BaseResponse().success();
    }
}
