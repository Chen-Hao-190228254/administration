package com.skm.exa.webapi.vo;

import com.skm.exa.domain.bean.UserCodesBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "返回类型")
public class UserCodesVO   extends UserCodesBean{
    @ApiModelProperty(value = "id")
    private Long id ; //id
    @ApiModelProperty(value = "代码")
    private String codes;  // 代码
    @ApiModelProperty(value = "代码名称")
    private String codeName;        //代码名称
    @ApiModelProperty(value = "状态")
    private Long status;        //状态
    @ApiModelProperty(value = "是否可编辑状态 0 禁用 ： 1 正常")
    private Long editStatus;     //是否可编辑状态
}
