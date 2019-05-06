package com.skm.exa.persistence.qo;

import com.skm.exa.domain.bean.UserBean;
import com.skm.exa.mybatis.*;
import lombok.Data;

import java.util.List;

/**
 * @author dhc
 * 2019-03-07 14:01             //DynamicSearchable ,动态查询 ，Sortable排序
 */
@Data
public class UserQO extends UserBean implements DynamicSearchable, Sortable {
    private String usernameLike;  //名字模糊查询
    private String realnameLike;   //真实姓名模糊查询

    private List<Long> ids;
    private String searchLike;  //模糊搜索

    private List<SearchCondition> searchConditions;   //搜索环境
    private List<SearchConditionGroup> searchConditionGroups;  //搜索条件组
    private Sort sort;   //种类

}
