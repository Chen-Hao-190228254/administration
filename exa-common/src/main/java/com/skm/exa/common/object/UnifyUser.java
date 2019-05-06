package com.skm.exa.common.object;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author dhc
 * 2019-03-05 16:14
 */
public class UnifyUser implements Serializable {  //同一用户
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String realname;  //真实名称
    private String avatar;  //体现

    public UnifyUser() {
    }

    public UnifyUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
