package com.skm.exa.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dhc
 * 2019-03-07 11:59
 */
@Data
public abstract class BaseBean implements Serializable {

    /**
     * ID
     */
    private Long id;

    /**
     * 添加人ID
     */
    private Long entryId;

    /**
     * 添加人名称
     */
    private String entryName;

    /**
     * 添加时间
     */
    private Date entryDt;

    /**
     * 更新人ID
     */
    private Long updateId;

    /**
     * 更新人名称
     */
    private String updateName;

    /**
     * 更新时间
     */
    private Date updateDt;


}
