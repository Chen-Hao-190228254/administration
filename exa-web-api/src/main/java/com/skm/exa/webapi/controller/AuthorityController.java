package com.skm.exa.webapi.controller;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.service.biz.AuthorityService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.AuthoritySaveVo;
import com.skm.exa.webapi.vo.AuthorityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/web/v1/authority")
public class AuthorityController extends BaseController {


    @Autowired
    AuthorityService authorityService;



    @PostMapping("/getAuthorityList")
    public List<AuthorityVo> getAuthorityList(){
        List<AuthorityBean> authorityBeanList = authorityService.getAuthorityList();
        List<AuthorityVo> authorityVos = BeanMapper.mapList(authorityBeanList,AuthorityBean.class,AuthorityVo.class);
        return authorityVos;
    }


    @PostMapping("/addAuthority")
    public Result<AuthorityVo> addAuthority(@RequestBody AuthoritySaveVo authoritySaveVo){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        AuthorityBean authorityBean = BeanMapper.map(authoritySaveVo,AuthorityBean.class);
        authorityBean = authorityService.addAuthority(authorityBean,unifyAdmin);
        AuthorityVo authorityVo = BeanMapper.map(authorityBean,AuthorityVo.class);
        Result<AuthorityVo> result = new Result<>();
        result.setContent(authorityVo);
        return result;

    }

}
