package com.skm.exa.common.service;

import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.domain.bean.LabelBean;

import java.util.List;


public interface FileBeanListDto {

    void setFile(List<FileBean> fileBean);

    void setLable(List<LabelBean> lable);
}
