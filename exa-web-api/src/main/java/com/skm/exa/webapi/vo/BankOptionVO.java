package com.skm.exa.webapi.vo;

import lombok.Data;

@Data
public class BankOptionVO {
    private Long id ;
    private Long type ;
    private String bankTechnologicalType;/*问题类型*/
}
