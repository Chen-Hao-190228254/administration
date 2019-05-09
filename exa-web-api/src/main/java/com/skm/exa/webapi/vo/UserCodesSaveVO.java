package com.skm.exa.webapi.vo;

import lombok.Data;

@Data
public class UserCodesSaveVO {
    private String codes;  // 代码
    private Long selectStatus;      //选取状态
    private String codeName;        //代码名称
    private Long status;        //状态
    private Long compileStatus;     //是否可编辑状态
}
