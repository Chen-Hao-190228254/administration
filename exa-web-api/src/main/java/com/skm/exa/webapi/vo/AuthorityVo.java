package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "权限返回类型")
@Data
public class AuthorityVo implements Serializable {


    /**
     * ID
     */
    @ApiModelProperty(value = "权限ID")
    private Long id;

    /**
     * 编码
     */
    @ApiModelProperty(value = "权限编码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value = "权限名称")
    private String name;

    /**
     * 状态
     */
    @ApiModelProperty(value = "权限状态")
    private Byte status;

    /**
     * 添加人ID
     */
    @ApiModelProperty(value = "添加人ID")
    private Long entryId;

    /**
     * 添加人名称
     */
    @ApiModelProperty(value = "添加人名称")
    private String entryName;

    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间")
    private Date entryDt;

    /**
     * 更新人ID
     */
    @ApiModelProperty(value = "更新人ID")
    private Long updateId;

    /**
     * 更新人名称
     */
    @ApiModelProperty(value = "更新人名称")
    private String updateName;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateDt;

}
