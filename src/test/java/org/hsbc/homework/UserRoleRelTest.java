package org.hsbc.homework;

import org.hsbc.homework.manager.UserManager;
import org.hsbc.homework.msg.BaseResponse;
import org.hsbc.homework.msg.CreateUserReq;
import org.hsbc.homework.msg.DeleteUserReq;
import org.hsbc.homework.service.ServiceManager;
import org.hsbc.homework.service.UserRoleRelService;
import org.hsbc.homework.service.UserService;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.Set;

public class UserRoleRelTest extends BaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before() {
        beforeTest();
    }

    @After
    public void after() {
        afterTest();
    }

    @Test
    // 正常把role加给user
    public void testAddRoleToUser() {
        createUser(userA, passWdA);
        createRole(roleA);
        addRoleAToUserA(userA, roleA);

        UserRoleRelService relService = (UserRoleRelService) ServiceManager.getGlobal().get(UserRoleRelService.class.getName());

        Assert.assertTrue(relService.ifUserHasRole(userA, roleA));
    }

    @Test
    // 重复把role加给user
    public void testAddRoleToUser_Duplicate() {
        createUser(userA, passWdA);
        createRole(roleA);
        addRoleAToUserA(userA, roleA);
        addRoleAToUserA(userA, roleA);

        UserRoleRelService relService = (UserRoleRelService) ServiceManager.getGlobal().get(UserRoleRelService.class.getName());

        Assert.assertTrue(relService.ifUserHasRole(userA, roleA));
    }

    @Test
    // 检查用户是否有role 结果为是
    public void testCheckRole() {
        createUser(userA, passWdA);
        createRole(roleA);
        addRoleAToUserA(userA, roleA);
        BaseResponse<String> loginResp = login(userA, passWdA);
        String token = loginResp.getToken();
        BaseResponse<Boolean> resp = checkRole(roleA, token);

        Assert.assertEquals(resp.getErrorCode(), ErrorCode.SUCCESS.getCode());
        Assert.assertEquals(resp.getData(), true);
    }

    @Test
    // 检查用户是否有role 结果为否
    public void testCheckRole_False() {
        createUser(userA, passWdA);
        createRole(roleA);

        BaseResponse<String> loginResp = login(userA, passWdA);
        String token = loginResp.getToken();
        BaseResponse<Boolean> resp = checkRole(roleA, token);

        Assert.assertEquals(resp.getErrorCode(), ErrorCode.SUCCESS.getCode());
        Assert.assertEquals(resp.getData(), false);
    }

    @Test
    // 检查用户是否有role 结果为未登录
    public void testCheckRole_NotLogin() {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorCode.LOGIN_EXPIRED.getCode());

        createUser(userA, passWdA);
        createRole(roleA);
        BaseResponse<String> loginResp = login(userA, passWdA);

        BaseResponse<Boolean> resp = checkRole(roleA, "1234567890");
    }

    @Test
    // 检查用户具有的所有的role
    public void testGetAllRole() {
        createUser(userA, passWdA);
        createRole(roleA);
        createRole(roleB);
        createRole(roleC);
        addRoleAToUserA(userA, roleA);
        addRoleAToUserA(userA, roleB);
        addRoleAToUserA(userA, roleC);
        BaseResponse<String> loginResp = login(userA, passWdA);
        String token = loginResp.getToken();
        BaseResponse<Set<String>> resp = getAllRules(token);

        Assert.assertEquals(resp.getErrorCode(), ErrorCode.SUCCESS.getCode());
        Assert.assertEquals(resp.getData().size(), 3);
        Assert.assertTrue(resp.getData().contains(roleA));
        Assert.assertTrue(resp.getData().contains(roleB));
        Assert.assertTrue(resp.getData().contains(roleC));
    }

    @Test
    // 检查用户具有的所有的role 结果为未登录
    public void tesGetAllRole_NotLogin() {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorCode.LOGIN_EXPIRED.getCode());

        createUser(userA, passWdA);
        createRole(roleA);
        createRole(roleB);
        createRole(roleC);
        addRoleAToUserA(userA, roleA);
        addRoleAToUserA(userA, roleB);
        addRoleAToUserA(userA, roleC);
        BaseResponse<String> loginResp = login(userA, passWdA);
        BaseResponse<Set<String>> resp = getAllRules("1234567890");
    }
}
