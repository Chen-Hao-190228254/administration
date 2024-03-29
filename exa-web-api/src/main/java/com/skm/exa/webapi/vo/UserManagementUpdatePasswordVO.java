package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;


@Data
@ApiModel(value = "角色管理修改密码")
public class UserManagementUpdatePasswordVO {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "密码")
    private Long oldPassword ;
    @ApiModelProperty(value = "新密码")
    private Long newPassword ;
}
