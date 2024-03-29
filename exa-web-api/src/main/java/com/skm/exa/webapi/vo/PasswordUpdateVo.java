package com.skm.exa.webapi.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel("更新管理员密码模型")
@Data
public class PasswordUpdateVo {

    /**
     * 管理员账号名称
     */
    @ApiModelProperty("需要更新的管理员ID")
    @NotNull
    private Long id;

    /**
     * 管理员账号密码
     */
    @ApiModelProperty("需要更新的管理员密码")
    @NotBlank
    @Pattern(regexp = "^[\\w_.@]{5,20}$")
    private String newPassword;


}
