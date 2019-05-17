package com.skm.exa.persistence.dto;

import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.domain.bean.RoleBean;
import lombok.Data;

import java.util.List;

@Data
public class RoleDto extends RoleBean {


    /**
     * 权限
     */
    List<AuthorityBean> authorityBeans;

}
