package com.skm.exa.persistence.dto;



import lombok.Data;

import java.util.List;

@Data
public class AdminSaveDto {

    /**
     * 管理员账号名称
     */
    private String username;

    /**
     * 管理员账号密码
     */
    private String password;

    /**
     * 管理员名称
     */
    private String name;


    /**
     * 管理员联系电话
     */
    private Long phone;

    /**
     * 管理员邮箱
     */
    private String email;

    /**
     * 管理员状态
     */
    private byte status;

    /**
     * 管理员角色ID列表
     */
    private List<Long> roleId;
}
