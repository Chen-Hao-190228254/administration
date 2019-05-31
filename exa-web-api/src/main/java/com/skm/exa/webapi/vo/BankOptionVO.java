package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "问题类型")
public class BankOptionVO {
    @ApiModelProperty(value =  "id")
    private Long id ;
    @ApiModelProperty(value = "类型")
    private Long type ;
    @ApiModelProperty(value = "问题类型")
    private String bankTechnologicalType;/*问题类型*/
}
