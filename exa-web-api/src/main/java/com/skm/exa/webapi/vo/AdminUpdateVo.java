package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("更新管理员模型")
@Data
public class AdminUpdateVo {

    /**
     * 管理员账号名称
     */
    @ApiModelProperty("需要更新的管理员ID")
    private Long id;


    /**
     * 管理员账号名称
     */
    @ApiModelProperty("更新后的管理员账号")
    private String username;


    /**
     * 管理员名称
     */
    @ApiModelProperty("更新后的管理员名称")
    private String name;


    /**
     * 管理员联系电话
     */
    @ApiModelProperty("更新后的管理员联系电话")
    private Long phone;

    /**
     * 管理员邮箱
     */
    @ApiModelProperty("更新后的管理员邮箱")
    private String email;

    /**
     * 管理员状态
     */
    @ApiModelProperty("更新后的管理员状态")
    private byte status;

    /**
     * 管理员角色ID列表
     */
    @ApiModelProperty("更新后的管理员的角色ID集合")
    private List<Long> roleId;

}
