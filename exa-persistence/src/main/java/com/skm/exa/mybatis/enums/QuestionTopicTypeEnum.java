package com.skm.exa.mybatis.enums;

public enum QuestionTopicTypeEnum {
    ANSWERS(0),     /*问答*/
    SINGLE_CHOICE(1),  /*单选*/
    MULTI_CHOICE(2),    /*多选*/
    PROGRAMME(3);   /*编程*/
    private int value ;
    QuestionTopicTypeEnum (int value){
        this.value = value ;
    }
    public int getValue(){
        return value ;
    }
}
