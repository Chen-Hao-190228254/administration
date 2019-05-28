package com.skm.exa.webapi.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "角色管理修改状态")
public class UserManagementStatusVO {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "状态")
    private Long status ;
}
