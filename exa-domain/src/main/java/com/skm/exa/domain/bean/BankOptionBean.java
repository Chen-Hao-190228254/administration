package com.skm.exa.domain.bean;

import lombok.Data;

import java.util.List;

@Data
public class BankOptionBean {
    private Long id ;
    private Long type ;
    private String bankTechnologicalType;/*技术类型*/
}
