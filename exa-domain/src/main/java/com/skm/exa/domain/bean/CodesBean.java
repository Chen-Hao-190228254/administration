package com.skm.exa.domain.bean;


import com.skm.exa.domain.BaseBean;
import lombok.Data;

import java.util.Date;
@Data
public class CodesBean extends BaseBean {
    /*代码id*/
    private Long id;
    /*代码*/
    private String code;
    /*选取状态*/
    private boolean selectStatus;
    /*代码名称*/
    private String codeName;
    /*状态*/
    private boolean status;
    /*是否可编辑状态*/
    private boolean compileStatus;

}
