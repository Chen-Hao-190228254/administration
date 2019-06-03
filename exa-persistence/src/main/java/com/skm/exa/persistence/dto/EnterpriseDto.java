package com.skm.exa.persistence.dto;

import com.skm.exa.common.service.FileBeanListDto;
import com.skm.exa.domain.bean.EnterpriseBean;
import com.skm.exa.domain.bean.FileBean;
import lombok.Data;

import java.util.List;

@Data
public class EnterpriseDto extends EnterpriseBean implements FileBeanListDto {

    List<FileBean> imageBeans;

    @Override
    public void setFile(List<FileBean> fileBean) {
        imageBeans = fileBean;
    }
}
