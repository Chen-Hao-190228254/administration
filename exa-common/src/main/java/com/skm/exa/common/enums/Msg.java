package com.skm.exa.common.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dhc
 * 2019-03-05 14:56
 */
public enum Msg {    //枚举信息
    S00000("成功"),
    E40000("未知的错误信息"),
    E40001("需要登录"),
    E40002("登录失败：错误的用户名或密码"),
    E40003("没有权限：您没有权限请求当前信息"),
    E40004("登录过期：您的身份验证已过期，请重新登录"),
    E40011("参数错误"),
    E40012("参数类型错误"),
    E40013("上传文件超出限制大小"),
    E40014("两次输入的密码不一致"),
    E40015("密码格式有误"),
    E40016("您输入的ID有误，数据库没有该ID的数据"),
    E40017("空文件"),
    E40018("账号/编码已经存在"),
    E40019("添加数据失败"),
    E40020("文件上传失败"),
    E40021("数据为空"),
    E40022("删除数据失败"),
    E40023("更新数据失败"),
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
