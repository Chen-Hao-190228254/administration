package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class UserManagementUpdatePasswordVO {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "密码")
    private String password ;
}
