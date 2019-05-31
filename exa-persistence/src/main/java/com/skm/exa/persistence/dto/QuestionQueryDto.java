package com.skm.exa.persistence.dto;


import com.skm.exa.domain.bean.QuestionBankBean;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class QuestionQueryDto  extends QuestionBankBean implements Serializable {
    private Long id;
    private Long technologicalType;//技术类型
    private Long topicType; // 题目类型
    private String enterpriseName;// 企业名称
    private Long enterpriseId;//企业id
    private String topicDetails; //  题目详情
    private Long optionCodes;  //   选择
    private String label;//   标签
    private String answer;//   问题解答
    private Long status; //   状态
    private String title;  // 标题
    private Date startDt;
    private Date endDt;
}
