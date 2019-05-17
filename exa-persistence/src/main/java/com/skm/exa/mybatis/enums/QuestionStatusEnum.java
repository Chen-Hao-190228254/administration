package com.skm.exa.mybatis.enums;

import io.swagger.annotations.ApiModelProperty;

public enum QuestionStatusEnum {
    @ApiModelProperty(value = "正常")
    NORMAL(0),
    @ApiModelProperty(value = "禁用")
    FORBIDDEN(1);

    private int value;

    QuestionStatusEnum (int value){
        this.value = value ;
    }
    public int getValue(){
        return value;
    }
}
