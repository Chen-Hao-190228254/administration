package com.skm.exa.webapi.controller;

import com.skm.exa.common.enums.Msg;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "权限操作",description = "权限操作")
@RestController
@RequestMapping("/web/v1/authority")
public class AuthorityController extends BaseController {


    @Autowired
    AuthorityService authorityService;


    /**
     * 获得所有权限
     * @return
     */
    @ApiOperation(value = "获得所有权限", notes = "获得所有权限")
    @GetMapping("/getAuthorityList")
    public Result<List<AuthorityVo>> getAuthorityList(){
        List<AuthorityBean> authorityBeanList = authorityService.getAuthorityList();
        return new Result<List<AuthorityVo>>().setContent(BeanMapper.mapList(authorityBeanList,AuthorityBean.class,AuthorityVo.class));
    }


    /**
     * 获得指定ID的权限
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID获得权限信息", notes = "根据ID获得权限信息")
    @GetMapping("/getAuthority/id")
    public Result<AuthorityVo> getAuthority(@ApiParam("要获取权限的ID") @RequestParam("id") Long id){
        AuthorityBean authorityBean = authorityService.getAuthority(id);
        return new Result<AuthorityVo>().setContent(BeanMapper.map(authorityBean,AuthorityVo.class));
    }


    /**
     * 分页查询
     * @param pageParam
     * @return
     */
    @ApiOperation(value = "分页查询权限", notes = "分页查询权限")
    @PostMapping("/getAuthorityPage")
    public Result<Page<AuthorityVo>> getAuthorityPage(@ApiParam("分页及条件信息") @RequestBody PageParam<QueryVo> pageParam){
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
     * 根据账号判读该权限是否已经存在
     * @param code
     * @return
     */
    @ApiOperation(value = "根据账号判读该权限是否已经存在", notes = "根据账号判读该权限是否已经存在")
    @GetMapping("/getAuthorityCode/code")
    public Result getAuthorityCode(@ApiParam("需要判定的权限CODE") @RequestParam("code") String code){
        boolean is = authorityService.getAuthorityCode(code);
        return Result.success(is);
    }




    /**
     * 添加权限
     * @param authoritySaveVo
     * @return
     */
    @ApiOperation(value = "添加权限", notes = "添加权限")
    @PostMapping("/addAuthority")
    public Result<AuthorityVo> addAuthority(@ApiParam("添加权限的信息") @RequestBody AuthoritySaveVo authoritySaveVo){
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
    @ApiOperation(value = "更新权限", notes = "更新权限")
    @PutMapping("/updateAuthority")
    public Result<AuthorityVo> updateAuthority(@ApiParam("更新权限的信息") @RequestBody AuthorityUpdateVo authorityUpdateVo){
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
    @ApiOperation(value = "删除权限", notes = "删除权限")
    @DeleteMapping("/deleteAuthority/id")
    public Result deleteAuthority(@ApiParam("需要删除权限的ID") @RequestParam("id") Long id){
        boolean is = authorityService.deleteAuthority(id);
        return is ? Result.success() : Result.error(Msg.E40000);
    }


    /**
     * 更改权限状态
     * @param id
     * @return
     */
    @ApiOperation(value = "更新权限状态", notes = "更新权限状态")
    @PutMapping("/setStatus/id")
    public Result<AuthorityVo> setStatus(@ApiParam("需要更新权限的ID") @RequestParam("id") Long id){
        Result<AuthorityBean> authorityBeanResult = authorityService.setStatus(id);
        Result<AuthorityVo> result = BeanMapper.map(authorityBeanResult,Result.class);
        AuthorityVo authorityVo = BeanMapper.map(authorityBeanResult.getContent(),AuthorityVo.class);
        result.setContent(authorityVo);
        return result;
    }

}
