package com.skm.exa.webapi.controller;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.qo.AuthorityQO;
import com.skm.exa.service.biz.AuthorityService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.AuthoritySaveVo;
import com.skm.exa.webapi.vo.AuthorityUpdateVo;
import com.skm.exa.webapi.vo.AuthorityVo;
import com.skm.exa.webapi.vo.QueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/web/v1/authority")
public class AuthorityController extends BaseController {


    @Autowired
    AuthorityService authorityService;


    /**
     * 获得所有权限
     * @return
     */
    @PostMapping("/getAuthorityList")
    public Result<List<AuthorityVo>> getAuthorityList(){
        List<AuthorityBean> authorityBeanList = authorityService.getAuthorityList();
        return new Result<List<AuthorityVo>>().setContent(BeanMapper.mapList(authorityBeanList,AuthorityBean.class,AuthorityVo.class));
    }


    /**
     * 获得指定ID的权限
     * @param id
     * @return
     */
    @PostMapping("/getAuthority/id")
    public Result<AuthorityVo> getAuthority(@RequestParam("id") Long id){
        AuthorityBean authorityBean = authorityService.getAuthority(id);
        return new Result<AuthorityVo>().setContent(BeanMapper.map(authorityBean,AuthorityVo.class));
    }


    /**
     * 分页查询
     * @param pageParam
     * @return
     */
    @PostMapping("/getAuthorityPage")
    public Result<Page<AuthorityVo>> getAuthorityPage(@RequestBody PageParam<QueryVo> pageParam){
        QueryVo queryVo = pageParam.getCondition();
        AuthorityQO qo = new AuthorityQO();
        qo.setNameLike(queryVo.getKey());
        qo.setCodeLike(queryVo.getKey());
        PageParam<AuthorityQO> authorityQO = new PageParam<>(pageParam.getPage(),pageParam.getSize());
        authorityQO.setCondition(qo);
        Page<AuthorityBean> authorityBeanPage = authorityService.getAuthorityPage(authorityQO);
        Page<AuthorityVo> page = authorityBeanPage.map(AuthorityBean.class,AuthorityVo.class);
        return Result.success(page);
    }

    /**
     * 添加权限
     * @param authoritySaveVo
     * @return
     */
    @PostMapping("/addAuthority")
    public Result<AuthorityVo> addAuthority(@RequestBody AuthoritySaveVo authoritySaveVo){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        AuthorityBean authorityBean = BeanMapper.map(authoritySaveVo,AuthorityBean.class);
        Result<AuthorityBean> resultAuthorityBean = authorityService.addAuthority(authorityBean,unifyAdmin);
        Result<AuthorityVo> result = BeanMapper.map(resultAuthorityBean,Result.class);
        AuthorityVo authorityVo = BeanMapper.map(resultAuthorityBean.getContent(),AuthorityVo.class);
        result.setContent(authorityVo);
        return result;
    }

    /**
     * 更新权限
     * @param authorityUpdateVo
     * @return
     */
    @PostMapping("/updateAuthority")
    public Result<AuthorityVo> updateAuthority(@RequestBody AuthorityUpdateVo authorityUpdateVo){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        AuthorityBean authorityBean = BeanMapper.map(authorityUpdateVo,AuthorityBean.class);
        Result<AuthorityBean> resultAuthorityBean = authorityService.updateAuthority(authorityBean,unifyAdmin);
        AuthorityBean authority  = BeanMapper.map(resultAuthorityBean.getContent(),AuthorityBean.class);
        Result<AuthorityVo> result = BeanMapper.map(resultAuthorityBean,Result.class).setContent(authority);
        return result;
    }

    /**
     * 删除指定ID权限
     * @param id
     * @return
     */
    @PostMapping("/deleteAuthority/id")
    public Result<Boolean> deleteAuthority(@RequestParam("id") Long id){
        boolean is = authorityService.deleteAuthority(id);
        if(is){
            Result<Boolean> result = new Result<>(1,"删除成功");
            result.setContent(true);
            return result;
        }else {
            Result<Boolean> result = new Result<>(-1,"删除失败");
            result.setContent(false);
            return result;
        }

    }

}
