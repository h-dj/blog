package cn.hdj.hdjblog.constaint.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: hdj
 * @Date: 2019/6/2 16:13
 * @Description: 响应码枚举
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {

    //响应成功
    SUCCESS(200, "操作成功"),

    // 系统错误
    UNKNOWN(500, "系统内部错误，请联系管理员"),
    PATH_NOT_FOUND(404, "路径不存在，请检查路径"),
    NO_AUTH(403, "抱歉，您没有访问权限！"),
    DUPLICATE_KEY(501, "数据库中已存在该记录"),
    SQL_ILLEGAL(504, "sql非法"),
    TOKEN_GENERATOR_ERROR(502, "token生成失败"),
    NO_UUID(503, "uuid为空"),

    //登录模块错误
    LOGIN_FAIL(10001, "登录失败"),
    CAPTCHA_WRONG(10002, "验证码错误"),
    USERNAME_OR_PASSWORD_WRONG(10003, "用户名或密码错误！"),
    LOCK_ACCOUNT(10004, "账户被锁定或禁用,请稍后再试！"),
    UNKNOWN_ACCOUNT(10005, "账号不存在"),

    //用户权限错误
    TOKEN_GENERATOR_EXPIRE(500001, "token 过期"),
    TOKEN_GENERATOR_ILLEGAL(500002, "非法token");

    private Integer code;
    private String msg;

}
