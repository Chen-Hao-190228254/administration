package com.skm.exa.persistence.qo;

import com.skm.exa.domain.bean.UserCodesBean;
import com.skm.exa.mybatis.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCodesLikeQO extends UserCodesBean implements DynamicSearchable , Sortable {
    private String codesLike;  // 代码
    private String codeNameLike;        //代码名称
    private List<SearchCondition> searchConditions;   //搜索环境
    private List<SearchConditionGroup> searchConditionGroups;  //搜索条件组
    private Sort sort;   //种类
}
