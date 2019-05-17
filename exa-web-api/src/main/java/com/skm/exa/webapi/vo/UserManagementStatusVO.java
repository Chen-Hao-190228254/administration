package com.skm.exa.webapi.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserManagementStatusVO {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "状态")
    private Long status ;
}
