package com.skm.exa.webapi.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@ApiModel("角色更新模型")
@Data
public class RoleUpdateVo {

    /**
     * ID
     */
    @ApiModelProperty("需要更新的角色ID")
    private Long id;


    /**
     * 编码
     */
    @ApiModelProperty("更新后角色的编码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("更新后角色的名称")
    private String name;

    /**
     * 状态
     */
    @ApiModelProperty("更新后角色的状态")
    private Byte status;

    /**
     * 权限ID列表
     */
    @ApiModelProperty("更新后角色的权限ID集合")
    private List<Long> authorityId;

}
