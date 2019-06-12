package com.skm.exa.persistence.dto;

import com.skm.exa.common.service.FileBeanListDto;
import com.skm.exa.domain.bean.EnterpriseBean;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.domain.bean.LabelBean;
import lombok.Data;

import java.util.List;

@Data
public class EnterpriseDto extends EnterpriseBean implements FileBeanListDto {

    List<FileBean> imageBeans;

    List<LabelBean> labelBeans;

    @Override
    public void setFile(List<FileBean> fileBean) {
        imageBeans = fileBean;
    }

    @Override
    public void setLable(List<LabelBean> lable) {
        labelBeans = lable;
    }
}
