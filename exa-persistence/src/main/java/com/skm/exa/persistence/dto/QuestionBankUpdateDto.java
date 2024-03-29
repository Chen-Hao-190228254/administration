package com.skm.exa.persistence.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class QuestionBankUpdateDto {
    @ApiModelProperty(value = "id")
    private Long id ;
    private Long enterpriseId;
    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "技术类型 1.编程 2.算法 3.数据库 4.优化")
    private Long technologicalType;
    @ApiModelProperty(value = "题目类型 1.问答 2.单选 3.多选 4.编程")
    private Long topicType;
    @ApiModelProperty(value = "题目详情")
    private String topicDetails;
    @ApiModelProperty(value = "标签")
    private String label;
    private Long optionCodes;
    @ApiModelProperty(value = "问题解答")
    private String answer;
    private List<OptionCodesUpdateDto> optionCodesUpdateDtoList ;
}
