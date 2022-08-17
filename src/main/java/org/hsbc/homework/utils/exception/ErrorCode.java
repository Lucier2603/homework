package org.hsbc.homework.utils.exception;

public class ErrorCode {

    private String code;
    private String msg;

    public ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public static final ErrorCode SUCCESS = new ErrorCode("success", "成功");
    /**
     * 系统错误
     */
    // 系统错误
    public static final ErrorCode SYSTEM_ERROR = new ErrorCode("system_error", "系统错误");

    /**
     * 业务错误 User
     */
    // 用户名过短
    public static final ErrorCode USER_NAME_TOO_SHORT = new ErrorCode("user_name_too_short", "用户名过短");
    // 用户名过长
    public static final ErrorCode USER_NAME_TOO_LONG = new ErrorCode("user_name_too_long", "用户名过长");
    // 用户名不合法
    public static final ErrorCode USER_NAME_ILLEGAL = new ErrorCode("user_name_illegal", "用户名不合法");
    // 用户名过短
    public static final ErrorCode PASSWD_TOO_SHORT = new ErrorCode("passwd_too_short", "密码过短");
    // 用户名过长
    public static final ErrorCode PASSWD_TOO_LONG = new ErrorCode("passwd_too_long", "密码过长");
    // 用户名不合法
    public static final ErrorCode PASSWD_ILLEGAL = new ErrorCode("passwd_illegal", "密码不合法");
    // 用户不存在
    public static final ErrorCode USER_NOT_EXIST = new ErrorCode("user_not_exist", "用户不存在");
    // 用户已存在
    public static final ErrorCode USER_ALREADY_EXIST = new ErrorCode("user_already_exist", "用户已存在");
    // 密码不正确
    public static final ErrorCode PASSWD_INCORRECT = new ErrorCode("passwd_incorrect", "密码不正确");
    // 登录已过期
    public static final ErrorCode LOGIN_EXPIRED = new ErrorCode("login_expired", "登录已过期");
    /**
     * 业务错误 Role
     */
    // role不存在
    public static final ErrorCode ROLE_NOT_EXIST = new ErrorCode("role_not_exist", "ROLE不存在");
    // role已存在
    public static final ErrorCode ROLE_ALREADY_EXIST = new ErrorCode("role_already_exist", "ROLE已存在");
}
