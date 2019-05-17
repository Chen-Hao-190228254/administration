package com.skm.exa.common.object;

import lombok.Data;

import java.io.Serializable;

@Data
public class UnifyAdmin implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 用户账号名称
     */
    private String username;

    /**
     * 用户账号密码
     */
    private String password;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 角色code
     */
    private String adminRoleCode;

    /**
     * 联系电话
     */
    private Long phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户状态
     */
    private byte status;

}
