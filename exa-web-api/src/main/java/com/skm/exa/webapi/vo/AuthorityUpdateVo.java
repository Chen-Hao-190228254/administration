package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "权限更新模型")
@Data
public class AuthorityUpdateVo {

    /**
     * 更新的ID
     */
    @ApiModelProperty(value = "需要更新权限的ID/为更新识别的唯一标准")
    private Long id;


    /**
     * 编码
     */
    @ApiModelProperty(value = "更新后权限的编码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value = "更新后权限的名称")
    private String name;

    /**
     * 状态
     */
    @ApiModelProperty(value = "更新后权限的状态")
    private Byte status;

}
