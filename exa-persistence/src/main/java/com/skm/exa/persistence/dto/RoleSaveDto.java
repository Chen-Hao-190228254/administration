package com.skm.exa.persistence.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleSaveDto  {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Byte status;


    /**
     * 权限ID列表
     */
    private List<Long> authorityId;
}
