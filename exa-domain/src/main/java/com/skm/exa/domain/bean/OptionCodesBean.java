package com.skm.exa.domain.bean;

import com.skm.exa.domain.BaseBean;
import lombok.Data;

@Data
public class OptionCodesBean extends BaseBean {
    private Long id ;
    private Long code ;
    private String bankOptionCodes; // 选项
    private String content ;  //内容
}
