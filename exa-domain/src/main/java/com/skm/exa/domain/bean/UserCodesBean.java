package com.skm.exa.domain.bean;

import com.skm.exa.domain.BaseBean;
import lombok.Data;

@Data
public class UserCodesBean extends BaseBean {
    private Long id;  //代码id
    private String codes;  // 代码
    private Long selectStatus;      //选取状态
    private String codeName;        //代码名称
    private Long status;        //状态
    private Long compileStatus;     //是否可编辑状态
}
