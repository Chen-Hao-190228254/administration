package com.skm.exa.persistence.dto;


import com.skm.exa.common.service.FileBeanListDto;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.domain.bean.LabelBean;
import com.skm.exa.domain.bean.UserManagementBean;
import lombok.Data;

import java.util.List;

@Data
public class UserManagementDto extends UserManagementBean implements FileBeanListDto {

    private List<FileBean> fileBeans ;

    private List<LabelBean> labelBeans;

    @Override
    public void setFile(List<FileBean> fileBean) {
        fileBeans = fileBean;
    }

    @Override
    public void setLable(List<LabelBean> lable) {
        labelBeans = lable;
    }
}
