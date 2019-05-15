package com.skm.exa.common.object;

import lombok.Data;

import java.io.Serializable;

@Data
public class UnifyRole implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色状态
     */
    private byte status;

}
