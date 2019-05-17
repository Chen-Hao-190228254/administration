package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserCodesUpdateVO {
    @ApiModelProperty(value = "id")
    private Long id ; //id
    @ApiModelProperty(value = "代码")
    private String codes;  // 代码
    @ApiModelProperty(value = "代码名称")
    private String codeName;        //代码名称
    @ApiModelProperty(value = "状态")
    private Long status;        //状态
}
