package com.skm.exa.domain.bean;

import com.skm.exa.domain.BaseBean;

/**
 * @author dhc
 * 2019-03-07 12:00
 */
public class UserBean extends BaseBean {
    private String username;
    private String password;
    private String realname;
    private String avatar;

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
