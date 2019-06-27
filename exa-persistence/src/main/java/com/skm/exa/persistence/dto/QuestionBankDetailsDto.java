package com.skm.exa.persistence.dto;



import com.skm.exa.domain.bean.QuestionBankBean;
import lombok.Data;

import java.util.List;
@Data
public class QuestionBankDetailsDto extends QuestionBankBean {
    private Long id ;
    private Long topicType;
    private String title;
    private String label;
    private String enterpriseName;
    private String topicDetails;
    private String answer;
    private Long optionCodes;
    private List<OptionCodesDto> optionCodesDtoList ;

    public QuestionBankDetailsDto() {
    }

    public QuestionBankDetailsDto(Long id) {
        this.id = id;
    }
}
