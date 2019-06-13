package com.skm.exa.domain.bean;


import lombok.Data;

@Data
public class LabelBean {

    private Long id;

    private String name;

    public LabelBean() {
    }

    public LabelBean(String name) {
        this.name = name;
    }
}
