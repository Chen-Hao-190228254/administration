package com.skm.exa.webapi.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("更新管理员密码模型")
@Data
public class PasswordUpdateVo {


    /**
     * 管理员账号名称
     */
    @ApiModelProperty("需要更新的管理员ID")
    private Long id;


    /**
     * 管理员账号密码
     */
    @ApiModelProperty("需要更新的管理员密码")
    private String password;
}
