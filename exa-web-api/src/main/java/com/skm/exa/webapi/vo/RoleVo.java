package com.skm.exa.webapi.vo;

import com.skm.exa.domain.bean.AuthorityBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel("角色返回模型")
@Data
public class RoleVo implements Serializable {


    /**
     * ID
     */
    @ApiModelProperty("角色ID")
    private Long id;

    /**
     * 角色编码
     */
    @ApiModelProperty("角色编码")
    private String code;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 角色状态
     */
    @ApiModelProperty("角色状态")
    private byte status;


    /**
     * 权限
     */
    @ApiModelProperty("角色权限列表")
    List<AuthorityVo> authorityVos;

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
