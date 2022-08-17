package org.hsbc.homework;

import org.hsbc.homework.manager.LoginManager;
import org.hsbc.homework.manager.UserManager;
import org.hsbc.homework.msg.BaseResponse;
import org.hsbc.homework.msg.CreateUserReq;
import org.hsbc.homework.msg.DeleteUserReq;
import org.hsbc.homework.service.ServiceManager;
import org.hsbc.homework.service.UserService;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;
import org.junit.*;
import org.junit.rules.ExpectedException;


import static org.hsbc.homework.utils.Utils.md5;

public class UserTest extends BaseTest {

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
    // 正常创建user
    public void testCreateUser() {
        createUser(userA, passWdA);

        UserService userService = (UserService) ServiceManager.getGlobal().get(UserService.class.getName());

        Assert.assertTrue(userService.checkPasswd(userA, passWdA));
    }

    @Test
    // 重复创建user
    public void testCreateUser_Duplicate() {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorCode.USER_ALREADY_EXIST.getCode());

        createUser(userA, passWdA);
        createUser(userA, passWdA);
    }

    @Test
    // 正常删除user
    public void testDeleteUser() {
        createUser(userA, passWdA);
        deleteUser(userA);

        UserService userService = (UserService) ServiceManager.getGlobal().get(UserService.class.getName());

        Assert.assertFalse(userService.userExist(userA));
    }

    @Test
    // 删除user (不存在)
    public void testDeleteUser_NotExist() {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorCode.USER_NOT_EXIST.getCode());

        deleteUser(userA);
    }
}
