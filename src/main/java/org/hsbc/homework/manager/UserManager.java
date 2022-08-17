package org.hsbc.homework.manager;

import org.hsbc.homework.api.UserApi;
import org.hsbc.homework.msg.BaseResponse;
import org.hsbc.homework.msg.CreateUserReq;
import org.hsbc.homework.msg.DeleteUserReq;
import org.hsbc.homework.service.ServiceManager;
import org.hsbc.homework.service.UserService;
import org.hsbc.homework.storage.MemKVStorage;
import org.hsbc.homework.storage.StorageManager;
import org.hsbc.homework.utils.Constants;
import org.hsbc.homework.utils.RouterUrl;
import org.hsbc.homework.utils.Utils;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;

public class UserManager implements UserApi {

    private UserService userService = (UserService) ServiceManager.getGlobal().get(UserService.class.getName());

    @RouterUrl("/api/createUser")
    public BaseResponse createUser(CreateUserReq req) {
        String userName = req.getUserName();
        String passWd = req.getPassWd();

        checkUserNameLegal(userName);
        checkPassWdLegal(passWd);

        userService.save(userName, passWd);

        return new BaseResponse().success();
    }

    @RouterUrl("/api/deleteUser")
    public BaseResponse deleteUser(DeleteUserReq req) {
        String userName = req.getUserName();

        checkUserNameLegal(userName);

        userService.delete(userName);

        return new BaseResponse().success();
    }


    public void checkUserNameLegal(String userName) {
        // 长度校验
        if (userName.length() < Constants.USER_NAME_MIN_LEN) {
            throw new BusinessException(ErrorCode.USER_NAME_TOO_SHORT);
        }
        if (userName.length() > Constants.USER_NAME_MAX_LEN) {
            throw new BusinessException(ErrorCode.USER_NAME_TOO_LONG);
        }
        // 只允许字母和数字
        if (!userName.matches(Constants.USER_NAME_REGEX)) {
            throw new BusinessException(ErrorCode.USER_NAME_ILLEGAL);
        }
    }

    public void checkPassWdLegal(String passWd) {
        // 长度校验
        if (passWd.length() < Constants.PASSWD_MIN_LEN) {
            throw new BusinessException(ErrorCode.PASSWD_TOO_SHORT);
        }
        if (passWd.length() > Constants.PASSWD_MAX_LEN) {
            throw new BusinessException(ErrorCode.PASSWD_TOO_LONG);
        }
        // 只允许字母和数字
        if (!passWd.matches(Constants.PASSWD_NAME_REGEX)) {
            throw new BusinessException(ErrorCode.PASSWD_ILLEGAL);
        }
    }
}
