package com.skm.exa.persistence.dto;


import com.skm.exa.domain.bean.OptionCodesBean;
import lombok.Data;

@Data
public class OptionCodesUpdateDto extends OptionCodesBean {
    private Long id ;
    private Long code ;
    private String bankOptionCodes;  // 选项
    private String content ;  //内容
}
