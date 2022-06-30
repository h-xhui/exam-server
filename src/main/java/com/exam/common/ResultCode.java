package com.exam.common;

/**
 * 返回状态码
 *
 * @author hongjinhui
 * 2022/5/20
 */

public class ResultCode {
    public static final String SUCCESS = "00000";
    // A开头为客户端错误
    public static final String CLIENT_ERROR = "A0001";
    // 第三位为1：表示用户注册相关错误
    public static final String USER_REGISTRATION_ERROR = "A0100";

    // 第三位为2：表示用户登录相关错误
    public static final String USERNAME_INVALID = "A0201";
    public static final String PASSWORD_WRONG = "A0202";
    public static final String CODE_ERROR = "A0203";

    // 第三位为3: 表示权限错误
    public static final String NO_PERMISSION = "A0300";

    // 第三位为4：表示参数错误
    public static final String MISSING_PARAM = "A0401";

    // B开头为服务端错误
    public static final String INTERNAL_SERVER_ERROR = "B0001";
}
