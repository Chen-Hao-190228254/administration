package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("状态返回模型")
public class StatusVo {

    /**
     * 状态编码
     */
    @ApiModelProperty("状态编码")
    private Byte code;


    /**
     * 状态名称
     */
    @ApiModelProperty("状态名称")
    private String name;

}
