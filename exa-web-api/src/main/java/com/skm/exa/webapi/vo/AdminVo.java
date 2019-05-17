package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@ApiModel("管理员返回模型")
@Data
public class AdminVo implements Serializable {

    /**
     * ID
     */
    @ApiModelProperty(value = "管理员ID")
    private Long id;

    /**
     * 用户账号名称
     */
    @ApiModelProperty(value = "管理员账号")
    private String username;


    /**
     * 用户名称
     */
    @ApiModelProperty(value = "管理员名称")
    private String name;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "管理员电话号码")
    private Long phone;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "管理员邮箱")
    private String email;

    /**
     * 用户状态
     */
    @ApiModelProperty(value = "管理员状态")
    private byte status;

    /**
     * 用户角色
     */
    @ApiModelProperty(value = "管理员角色")
    private List<RoleVo> roleVos;

    /**
     * 添加人ID
     */
    @ApiModelProperty(value = "添加人ID")
    private Long entryId;

    /**
     * 添加人名称
     */
    @ApiModelProperty(value = "添加人名称")
    private String entryName;

    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间")
    private Date entryDt;

    /**
     * 更新人ID
     */
    @ApiModelProperty(value = "更新人ID")
    private Long updateId;

    /**
     * 更新人名称
     */
    @ApiModelProperty(value = "更新人名称")
    private String updateName;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateDt;

}
