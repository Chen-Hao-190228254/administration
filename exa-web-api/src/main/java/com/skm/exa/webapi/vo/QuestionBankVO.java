package com.skm.exa.webapi.vo;

import com.skm.exa.domain.bean.OptionCodesBean;
import com.skm.exa.domain.bean.QuestionBankBean;
import lombok.Data;

import java.util.List;

@Data
public class QuestionBankVO extends QuestionBankBean {
    List<OptionCodesBean> optionCodesBeans;
}
