package org.hsbc.homework.manager;

import org.hsbc.homework.api.RoleApi;
import org.hsbc.homework.msg.BaseResponse;
import org.hsbc.homework.msg.CheckRoleReq;
import org.hsbc.homework.msg.CreateRoleReq;
import org.hsbc.homework.msg.DeleteRoleReq;
import org.hsbc.homework.service.RoleService;
import org.hsbc.homework.service.ServiceManager;
import org.hsbc.homework.service.TokenService;
import org.hsbc.homework.service.UserService;
import org.hsbc.homework.storage.MemKVStorage;
import org.hsbc.homework.storage.StorageManager;
import org.hsbc.homework.utils.Constants;
import org.hsbc.homework.utils.RouterUrl;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;

public class RoleManager implements RoleApi {

    private RoleService roleService = (RoleService) ServiceManager.getGlobal().get(RoleService.class.getName());

    @RouterUrl("/api/createRole")
    public BaseResponse createRole(CreateRoleReq req) {
        String roleName = req.getRoleName();
        roleService.save(roleName);

        return new BaseResponse().success();
    }

    @RouterUrl("/api/deleteRole")
    public BaseResponse deleteRole(DeleteRoleReq req) {
        String roleName = req.getRoleName();
        roleService.delete(roleName);

        return new BaseResponse().success();
    }
}
