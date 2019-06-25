package com.skm.exa.persistence.dto;

import com.skm.exa.domain.bean.OptionCodesBean;
import com.skm.exa.domain.bean.QuestionBankBean;
import lombok.Data;

import java.util.List;

@Data
public class QuestionBankDto extends QuestionBankBean {
    List<OptionCodesBean> optionCodesBeans;
}
