package com.skm.exa.persistence.qo;

import com.skm.exa.domain.BaseBean;
import com.skm.exa.domain.bean.QuestionBankBean;
import com.skm.exa.mybatis.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionQueryLikeQO extends QuestionBankBean implements DynamicSearchable , Sortable {
    @ApiModelProperty(value = "技术类型")
    private Long technologicalTypeLike;
    @ApiModelProperty(value = "题目类型")
    private Long topicTypeLike;
    @ApiModelProperty(value = "企业名称")
    private String enterpriseNameLike;
    @ApiModelProperty(value = "标题")
    private String titleLike;
    private Date startDtLike;
    private Date endDtLike;
    private List<SearchCondition> searchConditions;   //搜索环境
    @ApiModelProperty(value = "搜索条件组")
    private List<SearchConditionGroup> searchConditionGroups;  //搜索条件组
    @ApiModelProperty(value = "种类")
    private Sort sort;   //种类
}
