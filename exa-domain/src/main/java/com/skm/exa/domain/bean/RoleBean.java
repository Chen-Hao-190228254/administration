package com.skm.exa.domain.bean;

import com.skm.exa.domain.BaseBean;
import lombok.Data;

import java.util.List;

@Data
public class RoleBean extends BaseBean {

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色状态
     */
    private byte status;






}
