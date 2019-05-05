package com.skm.exa.webapi.vo;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminSaveVo {

    /**
     * 用户账号名称
     */
    @ApiModelProperty(value = "用户账号")
    private String username;

    /**
     * 用户账号密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 角色code
     */
    @ApiModelProperty(value = "角色编码")
    private String adminRoleCode;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private Long phone;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "联系邮箱")
    private String email;

    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态")
    private byte status;

}
