package com.skm.exa.persistence.dto;

import com.skm.exa.domain.bean.EnterpriseBean;
import com.skm.exa.domain.bean.ImageBean;
import lombok.Data;

import java.util.List;

@Data
public class EnterpriseDto extends EnterpriseBean {

    List<ImageBean> imageBeans;





}
