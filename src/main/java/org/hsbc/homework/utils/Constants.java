package org.hsbc.homework.utils;

public class Constants {
    /**
     * 系统常量
     */
    // KV表
    public static final String TABLE_USER = "user";
    public static final String TABLE_TOKEN = "token";
    public static final String TABLE_ROLE = "role";
    public static final String TABLE_USER_ROLE_REL = "user_role_rel";
    // 表最大容量
    public static final int MAX_TABLE_SIZE = 1000 * 1000;
    // token失效时间
    public static final int TOKEN_EXPIRE_TIME = 5 * 1000;

    /**
     * 业务常量 - User
     */
    // 用户名最小 最大长度
    public static final int USER_NAME_MIN_LEN = 4;
    public static final int USER_NAME_MAX_LEN = 8;
    public static final String USER_NAME_REGEX = "^[a-z0-9A-Z]+$";
    // 密码最小 最大长度
    public static final int PASSWD_MIN_LEN = 6;
    public static final int PASSWD_MAX_LEN = 16;
    public static final String PASSWD_NAME_REGEX = "^[a-z0-9A-Z]+$";
}
