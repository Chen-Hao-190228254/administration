package com.skm.exa.persistence.dto;

import com.skm.exa.domain.bean.AuthorityBean;
import lombok.Data;

@Data
public class RoleAuthorityDto extends AuthorityBean {

    Long roleId;
}
