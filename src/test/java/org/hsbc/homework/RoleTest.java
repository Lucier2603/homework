package org.hsbc.homework;

import org.hsbc.homework.manager.RoleManager;
import org.hsbc.homework.manager.UserManager;
import org.hsbc.homework.msg.CreateRoleReq;
import org.hsbc.homework.msg.CreateUserReq;
import org.hsbc.homework.msg.DeleteRoleReq;
import org.hsbc.homework.service.RoleService;
import org.hsbc.homework.service.ServiceManager;
import org.hsbc.homework.service.UserService;
import org.hsbc.homework.utils.exception.BusinessException;
import org.hsbc.homework.utils.exception.ErrorCode;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class RoleTest extends BaseTest {

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
    // 正常创建role
    public void testCreateRole() {
        createRole(roleA);

        RoleService roleService = (RoleService) ServiceManager.getGlobal().get(RoleService.class.getName());

        Assert.assertTrue(roleService.roleExist(roleA));
    }

    @Test
    // 重复创建role
    public void testCreate_DuplicateRole() {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorCode.ROLE_ALREADY_EXIST.getCode());

        createRole(roleA);
        createRole(roleA);
    }

    @Test
    // 正常删除role
    public void testDeleteRole() {
        createRole(roleA);
        deleteRole(roleA);

        RoleService roleService = (RoleService) ServiceManager.getGlobal().get(RoleService.class.getName());

        Assert.assertFalse(roleService.roleExist(roleA));
    }

    @Test
    // 删除role 不存在
    public void testDeleteRole_NotExist() {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(ErrorCode.ROLE_NOT_EXIST.getCode());

        deleteRole(roleA);
    }
}
