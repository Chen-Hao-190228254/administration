package com.skm.exa.webapi.vo;

import com.skm.exa.domain.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "代码管理详情VO")
public class UserCodesDetailsVO extends BaseBean {
    @ApiModelProperty(value = "id")
    private Long id ; //id
    @ApiModelProperty(value = "代码")
    private String codes;  // 代码
    @ApiModelProperty(value = "代码名称")
    private String codeName;        //代码名称
    @ApiModelProperty(value = "状态")
    private Long status;        //状态
    @ApiModelProperty(value = "是否可编辑状态")
    private Long editStatus;     //是否可编辑状态
}
