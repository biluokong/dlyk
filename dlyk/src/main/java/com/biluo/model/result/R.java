package com.biluo.model.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 后端接口返回的数据，统一用 R对象 封装
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class R {

    //返回的状态码，比如200表示成功，500表示失败
    private int code;

    //返回的状态信息，也就是返回的那个状态码是代表后端什么错误，比如：用户不存在，登录密码错误....
    private String msg;

    //返回的具体数据，数据可以是任何类型，比如登录成功，那么返回user对象
    private Object data;

    public static R OK() {
        return R.builder().code(CodeEnum.OK.getCode()).msg(CodeEnum.OK.getMsg()).build();
    }

    public static R OK(Object data) {
        return R.builder().code(CodeEnum.OK.getCode()).msg(CodeEnum.OK.getMsg()).data(data).build();
    }

    public static R OK(Object data, String msg) {
        return R.builder().code(CodeEnum.OK.getCode()).msg(msg).data(data).build();
    }

    public static R OK(CodeEnum codeEnum) {
        return R.builder().code(codeEnum.getCode()).msg(codeEnum.getMsg()).build();
    }

    public static R FAIL() {
        return R.builder().code(CodeEnum.FAIL.getCode()).msg(CodeEnum.FAIL.getMsg()).build();
    }

    public static R FAIL(CodeEnum codeEnum) {
        return R.builder().code(codeEnum.getCode()).msg(codeEnum.getMsg()).build();
    }

    public static R FAIL(String msg) {
        return R.builder().code(CodeEnum.FAIL.getCode()).msg(msg).build();
    }
}
