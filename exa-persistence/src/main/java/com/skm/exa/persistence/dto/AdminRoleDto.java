package com.skm.exa.persistence.dto;


import com.skm.exa.domain.bean.RoleBean;
import lombok.Data;


@Data
public class AdminRoleDto extends RoleBean {

    private Long adminId;
}
