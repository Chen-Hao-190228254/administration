package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@ApiModel("添加管理员模型")
@Data
public class AdminSaveVo {


    /**
     * 管理员账号名称
     */
    @ApiModelProperty("管理员账号")
    private String username;

    /**
     * 管理员账号密码
     */
    @ApiModelProperty("管理员密码")
    @NotBlank
    @Pattern(regexp = "^[\\w_.@]{5,20}$")
    private String password;

    /**
     * 管理员名称
     */
    @ApiModelProperty("管理员名称")
    private String name;


    /**
     * 管理员电话
     */
    @ApiModelProperty("管理员联系电话")
    private Long phone;

    /**
     * 管理员邮箱
     */
    @ApiModelProperty("管理员邮箱")
    private String email;

    /**
     * 管理员状态
     */
    @ApiModelProperty("管理员状态")
    private byte status;

    /**
     * 管理员角色ID列表
     */
    @ApiModelProperty("管理员的角色ID集合")
    private List<Long> roleId;

}
