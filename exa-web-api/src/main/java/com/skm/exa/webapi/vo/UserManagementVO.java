package com.skm.exa.webapi.vo;

import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.domain.bean.UserManagementBean;
import lombok.Data;

import java.util.List;


/**
 * 用户管理Vo
 */
@Data
public class UserManagementVO extends UserManagementBean{

    List<FileBean> fileBeans;
}

