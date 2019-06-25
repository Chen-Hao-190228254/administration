package com.skm.exa.webapi.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
public class QuestionBankBeanDetailsVo {
    private Long id ;
    @ApiModelProperty(value = "企业id")
    private Long enterpriseId;
    @ApiModelProperty(value = "企业名称")
    private String enterpriseName;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "技术类型")
    private Long technologicalType;
    @ApiModelProperty(value = "题目类型")
    private Long topicType;
    @ApiModelProperty(value = "题目详情")
    private String topicDetails;
    @ApiModelProperty(value = "选择")
    private Long optionCodes;
    @ApiModelProperty(value = "标签")
    private String label;
    @ApiModelProperty(value = "问题解答")
    private String answer;
    @ApiModelProperty(value = "状态")
    private Long status;

    List<OptionCodesVo> optionCodesVoList;
}
