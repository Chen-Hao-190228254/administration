package com.skm.exa.webapi.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel("添加角色模型")
@Data
public class RoleSaveVo {

    /**
     * 编码
     */
    @ApiModelProperty("角色编码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("角色名称")
    private String name;

    /**
     * 状态
     */
    @ApiModelProperty("角色状态")
    private Byte status;


    /**
     * 权限ID列表
     */
    @ApiModelProperty("权限ID集合")
    private List<Long> authorityId;


}
