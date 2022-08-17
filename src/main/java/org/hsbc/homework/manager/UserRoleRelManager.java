package org.hsbc.homework.manager;

import org.hsbc.homework.api.UserRoleRelApi;
import org.hsbc.homework.model.UserRoleRel;
import org.hsbc.homework.msg.AddRoleToUserReq;
import org.hsbc.homework.msg.BaseRequest;
import org.hsbc.homework.msg.BaseResponse;
import org.hsbc.homework.msg.CheckRoleReq;
import org.hsbc.homework.service.*;
import org.hsbc.homework.storage.MemKVStorage;
import org.hsbc.homework.storage.StorageManager;
import org.hsbc.homework.utils.Constants;
import org.hsbc.homework.utils.RouterUrl;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;

import java.util.HashSet;
import java.util.Set;

public class UserRoleRelManager implements UserRoleRelApi {

    private UserService userService = (UserService) ServiceManager.getGlobal().get(UserService.class.getName());
    private RoleService roleService = (RoleService) ServiceManager.getGlobal().get(RoleService.class.getName());
    private UserRoleRelService relService = (UserRoleRelService) ServiceManager.getGlobal().get(UserRoleRelService.class.getName());
    private TokenService tokenService = (TokenService) ServiceManager.getGlobal().get(TokenService.class.getName());

    @RouterUrl("/api/addRoleToUser")
    public BaseResponse addRoleToUser(AddRoleToUserReq req) {
        String userName = req.getUserName();
        String roleName = req.getRoleName();

        if (!userService.userExist(userName)) {
            throw new BusinessException(ErrorCode.USER_NOT_EXIST);
        }
        if (!roleService.roleExist(roleName)) {
            throw new BusinessException(ErrorCode.ROLE_NOT_EXIST);
        }

        relService.save(userName, roleName);

        return new BaseResponse().success();
    }

    @RouterUrl("/api/checkRole")
    public BaseResponse<Boolean> checkRole(CheckRoleReq req) {
        String userName = tokenService.validToken(req.getToken());

        return new BaseResponse<Boolean>().success(relService.ifUserHasRole(userName, req.getRoleName()));
    }

    @RouterUrl("/api/getAllRules")
    public BaseResponse<Set<String>> getAllRules(BaseRequest req) {
        String userName = tokenService.validToken(req.getToken());

        return new BaseResponse<Set<String>>().success(relService.getAllRoles(userName));
    }


}
