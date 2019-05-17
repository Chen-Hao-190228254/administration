package com.skm.exa.persistence.dao;

import com.skm.exa.domain.bean.EnterpriseBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.BaseDao;
import com.skm.exa.persistence.qo.EnterpriseQO;

import java.util.List;

public interface EnterpriseDao extends BaseDao<EnterpriseBean> {


    /**
     * 获取所有企业
     * @return
     */
    List<EnterpriseBean> getEnterpriseList();


    /**
     * 根据ID获取企业
     * @param id
     * @return
     */
    EnterpriseBean getEnterprise(Long id);


    /**
     * 分页获取企业
     * @param pageParam
     * @return
     */
    Page<EnterpriseBean> getEnterprisePage(PageParam<EnterpriseQO> pageParam);


    /**
     * 添加企业
     * @param enterpriseBean
     * @return
     */
    int addEnterprise(EnterpriseBean enterpriseBean);


    /**
     * 更新企业
     * @param enterpriseBean
     * @return
     */
    int updateEnterprise(EnterpriseBean enterpriseBean);


    /**
     * 删除企业
     * @param id
     * @return
     */
    int deleteEnterprise(Long id);




}
