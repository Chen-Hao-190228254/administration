package com.skm.exa.persistence.dto;



import lombok.Data;

import java.util.List;

@Data
public class AdminSaveDto {

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
     * 角色ID列表
     */
    private List<Long> roleId;
}
