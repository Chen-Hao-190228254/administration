package com.skm.exa.persistence.dto;


import com.skm.exa.common.service.FileBeanListDto;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.domain.bean.UserManagementBean;
import lombok.Data;

import java.util.List;

@Data
public class UserManagementDto extends UserManagementBean implements FileBeanListDto {

    private List<FileBean> fileBeans ;

    @Override
    public void setFile(List<FileBean> fileBean) {
        fileBeans = fileBean;
    }
}
