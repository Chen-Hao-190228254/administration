package com.skm.exa.persistence.qo;

import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.mybatis.*;
import lombok.Data;

import java.util.List;

@Data
public class AuthorityQO extends AuthorityBean implements DynamicSearchable, Sortable {

    /**
     *编码条件搜索
     */
    private String codeLike;

    /**
     * 名称条件搜索
     */
    private String nameLike;



    /**
     * 搜索条件
     */
    private List<SearchCondition> searchConditions;

    /**
     * 搜索条件组
     */
    private List<SearchConditionGroup> searchConditionGroups;

    /**
     *
     */
    private Sort sort;


}
