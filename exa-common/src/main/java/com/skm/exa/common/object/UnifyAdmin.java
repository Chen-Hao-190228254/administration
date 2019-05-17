package com.skm.exa.common.object;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
    @JsonIgnore
    private String password;

    /**
     * 用户名称
     */
    private String name;

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

    /**
     * 角色
     */
    List<UnifyRole> role;

    /**
     * 权限
     */
    List<UnifyAuthority> authority;

}


