package com.skm.exa.persistence.dto;


import lombok.Data;

import java.util.List;

@Data
public class AdminUpdateDto {


    /**
     * 需要更新的管理员ID
     */
    private Long id;


    /**
     * 更新后管理员名称
     */
    private String name;


    /**
     * 更新后的密码
     */
    private String password;


    /**
     * 更新后管理员联系电话
     */
    private Long phone;

    /**
     * 更新后管理员邮箱
     */
    private String email;

    /**
     * 更新后管理员状态
     */
    private byte status;

    /**
     * 更新后管理员角色ID列表
     */
    private List<Long> roleId;


    public AdminUpdateDto() {
    }

    public AdminUpdateDto(Long id,String password) {
        this.id = id;
        this.password = password;
    }

    public AdminUpdateDto(Long id,byte status) {
        this.id = id;
        this.status = status;
    }
}
