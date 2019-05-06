package com.skm.exa.persistence.dto;

import com.skm.exa.domain.bean.UserBean;

/**
 * @author dhc
 * 2019-03-08 23:02
 */
public class UserDto extends UserBean {
    private int loginCount;//登陆次数

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }
}
