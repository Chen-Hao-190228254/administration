package com.skm.exa.persistence.dto;

import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.domain.bean.RoleBean;
import lombok.Data;

import java.util.List;


@Data
public class AdminDto extends AdminBean {

    /**
     * 用户角色
     */
    private List<RoleBean> roleBeans;

}
