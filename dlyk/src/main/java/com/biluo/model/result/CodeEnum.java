package com.biluo.model.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 枚举类
 *
 * 数得过来的东西，可以用枚举来定义
 *
 * 一年有12个月，一周有7天，那我们的状态码信息是数得过来的，几十个，最多一两个百个
 */

@AllArgsConstructor
public enum CodeEnum {

    OK(200, "成功"),

    FAIL(500, "失败"),


    TOKEN_IS_EMPTY(901, "请求token为空"),

    TOKEN_IS_ILLEGAL(902, "请求token不合法"),

    TOKEN_IS_EXPIRE(903, "请求token已过期"),

    LOGIN_JWT_NO_MATCH(904, "请求jwt不正确"),

    ACCESS_DENIED(905, "权限不足");

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;
}
