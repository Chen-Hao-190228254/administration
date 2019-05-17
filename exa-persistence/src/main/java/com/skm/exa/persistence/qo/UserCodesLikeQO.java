package com.skm.exa.persistence.qo;

import com.skm.exa.domain.bean.UserCodesBean;
import com.skm.exa.mybatis.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCodesLikeQO extends UserCodesBean implements DynamicSearchable , Sortable {
    @ApiModelProperty(value = "代码模糊查询")
    private String codesLike;  // 代码
    @ApiModelProperty(value = "代码名称迷糊查询")
    private String codeNameLike;        //代码名称
    @ApiModelProperty(value = "搜索环境")
    private List<SearchCondition> searchConditions;   //搜索环境
    @ApiModelProperty(value = "搜索条件组")
    private List<SearchConditionGroup> searchConditionGroups;  //搜索条件组
    @ApiModelProperty(value = "种类")
    private Sort sort;   //种类
}
