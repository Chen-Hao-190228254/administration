package com.skm.exa.persistence.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserManagementUpdatePasswordDto {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "密码")
    private Long oldPassword ;
    @ApiModelProperty(value = "新密码")
    private Long newPassword ;
}
