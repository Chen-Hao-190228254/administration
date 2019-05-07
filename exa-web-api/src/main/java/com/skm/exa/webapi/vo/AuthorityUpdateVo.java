package com.skm.exa.webapi.vo;

import lombok.Data;

@Data
public class AuthorityUpdateVo {

    /**
     * 更新的ID
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
