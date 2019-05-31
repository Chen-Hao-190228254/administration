package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("更新权限模板")
@Data
public class SetStatusVo {

    /**
     * 要更新的权限ID
     */
    @ApiModelProperty("要更新的权限ID")
    private Long id;

    /**
     * 要更新的状态
     */
    @ApiModelProperty("要更新的状态")
    private Byte status;


}
