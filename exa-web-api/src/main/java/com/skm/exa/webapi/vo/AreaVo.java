package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("地址返回模型")
public class AreaVo {

    /**
     * 地址ID
     */
    @ApiModelProperty("地址ID")
    private String id;

    /**
     * 地址编码
     */
    @ApiModelProperty("地址编码")
    private Long code;

    /**
     * 地址名称
     */
    @ApiModelProperty("地址名称")
    private String name;

    /**
     * 地址等级
     */
    @ApiModelProperty("地址等级")
    private byte level;

    /**
     * 地址上一级的ID
     */
    @ApiModelProperty("地址ID")
    private Long parentCode;

}
