package com.skm.exa.persistence.qo;

import lombok.Data;

@Data
public class EnterpriseQO {

    private String codeLike;

    private String nameLike;

    public EnterpriseQO() {
    }

    public EnterpriseQO(String code, String name) {
        this.codeLike = code;
        this.nameLike = name;
    }
}
