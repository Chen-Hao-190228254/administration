package com.skm.exa.common.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dhc
 * 2019-03-05 14:56
 */
public enum Msg {
    S00000("成功"),
    E40000("未知的错误信息"),
    E40001("需要登录"),
    E40002("登录失败：错误的用户名或密码"),
    E40003("没有权限：您没有权限请求当前信息"),
    E40004("登录过期：您的身份验证已过期，请重新登录"),
    E40011("参数错误"),
    E40012("参数类型错误"),
    E40013("上传文件超出限制大小"),
    ;

    private static final Pattern NUMPTN = Pattern.compile("\\d{5}");

    public final String message;

    Msg(String message) {
        this.message = message;
    }

    public int code() {
        Matcher matcher = NUMPTN.matcher(this.name());
        return matcher.find() ? Integer.parseInt(matcher.group(0)) : 0;
    }

    public boolean isError() {
        return this.name().startsWith("E");
    }
}
