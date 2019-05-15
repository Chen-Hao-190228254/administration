package com.skm.exa.common.object;

import lombok.Data;

import java.io.Serializable;

@Data
public class UnifyAuthority implements Serializable {

    /**
     * ID
     */
    private Long id;

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

}
