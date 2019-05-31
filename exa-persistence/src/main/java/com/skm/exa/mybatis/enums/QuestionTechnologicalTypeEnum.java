package com.skm.exa.mybatis.enums;

public enum  QuestionTechnologicalTypeEnum {
    PROGRAMME(1),  /*编程*/
    ARITHMETIC(2), /*算法*/
    DATABASE (3),  /*数据库*/
    OPTIMIZE(4);    /*优化*/
    private int value;
    QuestionTechnologicalTypeEnum(int value){
        this.value = value;
    }
    public int getValue(){
        return value ;
    }
}
