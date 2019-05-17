package com.skm.exa.webapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuestionBankDetailsVO {
    @ApiModelProperty(value = "id")
    private Long id ;
    @ApiModelProperty(value = "题目类型")
    private Long topicType;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "标签")
    private String label;
    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;
    @ApiModelProperty(value = "题目详情")
    private String topicDetails;
    @ApiModelProperty(value = "问题解答")
    private String answer;
}
