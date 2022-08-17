package org.hsbc.homework;

import org.hsbc.homework.msg.BaseResponse;
import org.hsbc.homework.service.ServiceManager;
import org.hsbc.homework.service.TokenService;
import org.hsbc.homework.utils.JsonUtil;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class LoginTest extends BaseTest {

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
    // 正常登录
    public void testLogin() {
        createUser(userA, passWdA);
        BaseResponse<String> resp = login(userA, passWdA);

        Assert.assertEquals(resp.getErrorCode(), ErrorCode.SUCCESS.getCode());
        Assert.assertNotNull(resp.getToken());

        TokenService tokenService = (TokenService) ServiceManager.getGlobal().get(TokenService.class.getName());
        String user = tokenService.validToken(resp.getToken());

        Assert.assertEquals(user, userA);
    }

    @Test
    // 密码错误
    public void testLogin_PasswdWrong() {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorCode.PASSWD_INCORRECT.getCode());

        createUser(userA, passWdA);
        BaseResponse<String> resp = login(userA, "654321");
    }

    @Test
    // 用户不存在
    public void testLogin_UserNotExist() {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorCode.USER_NOT_EXIST.getCode());

        createUser(userA, passWdA);
        BaseResponse<String> resp = login("userB", "654321");
    }

    @Test
    // 注销
    public void testLogout() {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorCode.LOGIN_EXPIRED.getCode());

        createUser(userA, passWdA);
        BaseResponse<String> inResp = login(userA, passWdA);
        String token = inResp.getToken();
        BaseResponse outResp = logout(token);

        Assert.assertEquals(outResp.getErrorCode(), ErrorCode.SUCCESS.getCode());

        TokenService tokenService = (TokenService) ServiceManager.getGlobal().get(TokenService.class.getName());
        tokenService.validToken(token);
    }

    @Test
    // 3秒后未过期
    public void testLogin_NotExpire() {
        createUser(userA, passWdA);
        BaseResponse<String> resp = login(userA, passWdA);

        Assert.assertEquals(resp.getErrorCode(), ErrorCode.SUCCESS.getCode());
        Assert.assertNotNull(resp.getToken());
        // 刚登录校验
        TokenService tokenService = (TokenService) ServiceManager.getGlobal().get(TokenService.class.getName());
        String user = tokenService.validToken(resp.getToken());
        Assert.assertEquals(user, userA);

        // 3秒后校验
        sleep(3 * 1000);
        user = tokenService.validToken(resp.getToken());
        Assert.assertEquals(user, userA);
    }

    @Test
    // 6秒后过期
    public void testLogin_Expire() {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorCode.LOGIN_EXPIRED.getCode());

        createUser(userA, passWdA);
        BaseResponse<String> resp = login(userA, passWdA);

        Assert.assertEquals(resp.getErrorCode(), ErrorCode.SUCCESS.getCode());
        Assert.assertNotNull(resp.getToken());
        // 刚登录校验
        TokenService tokenService = (TokenService) ServiceManager.getGlobal().get(TokenService.class.getName());
        String user = tokenService.validToken(resp.getToken());
        Assert.assertEquals(user, userA);

        // 6秒后校验
        sleep(6 * 1000);
        user = tokenService.validToken(resp.getToken());
    }
}
