package org.hsbc.homework;

import org.hsbc.homework.manager.LoginManager;
import org.hsbc.homework.manager.RoleManager;
import org.hsbc.homework.manager.UserManager;
import org.hsbc.homework.manager.UserRoleRelManager;
import org.hsbc.homework.model.Token;
import org.hsbc.homework.model.UserRoleRel;
import org.hsbc.homework.msg.*;
import org.hsbc.homework.service.*;
import org.hsbc.homework.storage.MemKVStorage;
import org.hsbc.homework.storage.StorageManager;

import java.util.Set;

import static org.hsbc.homework.utils.Constants.*;
import static org.hsbc.homework.utils.Constants.MAX_TABLE_SIZE;

public class BaseTest {

    protected UserManager userManager;
    protected RoleManager roleManager;
    protected UserRoleRelManager userRoleRelManager;
    protected LoginManager loginManager;

    public void beforeTest() {
        // 注册存储
        StorageManager storageManager = StorageManager.init();
        StorageManager.getGlobal().registerMem(TABLE_USER, new MemKVStorage<String, String>(MAX_TABLE_SIZE));
        StorageManager.getGlobal().registerMem(TABLE_TOKEN, new MemKVStorage<String, Token>(MAX_TABLE_SIZE));
        StorageManager.getGlobal().registerMem(TABLE_ROLE, new MemKVStorage<String, Boolean>(MAX_TABLE_SIZE));
        StorageManager.getGlobal().registerMem(TABLE_USER_ROLE_REL, new MemKVStorage<String, UserRoleRel>(MAX_TABLE_SIZE));

        // 注册service
        ServiceManager serviceManager = ServiceManager.init();
        ServiceManager.getGlobal().register(new UserService());
        ServiceManager.getGlobal().register(new RoleService());
        ServiceManager.getGlobal().register(new TokenService());
        ServiceManager.getGlobal().register(new UserRoleRelService());

        userManager = new UserManager();
        roleManager = new RoleManager();
        userRoleRelManager = new UserRoleRelManager();
        loginManager = new LoginManager();
    }

    public void afterTest() {
        StorageManager.reset();
        ServiceManager.reset();

        userManager = null;
        roleManager = null;
        userRoleRelManager = null;
        loginManager = null;
    }

    /**
     * 测试使用的数据
     */
    protected String userA = "userA";
    protected String passWdA = "123456";
    // 创建userA
    protected void createUser(String user, String passwd) {
        CreateUserReq req = new CreateUserReq(user, passwd);
        userManager.createUser(req);
    }
    // 删除userA
    protected void deleteUser(String user) {
        DeleteUserReq req = new DeleteUserReq(user);
        userManager.deleteUser(req);
    }
    // userA 登录
    protected BaseResponse<String> login(String user, String passwd) {
        LoginReq loginReq = new LoginReq(user, passwd);
        return loginManager.login(loginReq);
    }
    // userA 登出
    protected BaseResponse logout(String token) {
        BaseRequest req = new BaseRequest();
        req.setToken(token);
        return loginManager.logout(req);
    }

    protected String roleA = "roleA";
    protected String roleB = "roleB";
    protected String roleC = "roleC";
    // 创建roleA
    protected void createRole(String role) {
        CreateRoleReq req = new CreateRoleReq(role);
        roleManager.createRole(req);
    }
    // 删除roleA
    protected void deleteRole(String role) {
        DeleteRoleReq req = new DeleteRoleReq(role);
        roleManager.deleteRole(req);
    }

    // role 设置给 user
    protected void addRoleAToUserA(String user, String role) {
        AddRoleToUserReq req = new AddRoleToUserReq(user, role);
        userRoleRelManager.addRoleToUser(req);
    }

    // 查询role和user是否匹配
    protected BaseResponse<Boolean> checkRole(String role, String token) {
        CheckRoleReq req = new CheckRoleReq(role);
        req.setToken(token);
        return userRoleRelManager.checkRole(req);
    }

    // 查询用户的所有role
    protected BaseResponse<Set<String>> getAllRules(String token) {
        BaseRequest req = new BaseRequest();
        req.setToken(token);
        return userRoleRelManager.getAllRules(req);
    }

    // 睡眠
    protected void sleep(long timeMills) {
        try {
            Thread.sleep(timeMills);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
