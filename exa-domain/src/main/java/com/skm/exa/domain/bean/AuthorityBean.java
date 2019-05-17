package com.skm.exa.domain.bean;

import com.skm.exa.domain.BaseBean;
import lombok.Data;


@Data
public class AuthorityBean extends BaseBean {


    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Byte status;

}
