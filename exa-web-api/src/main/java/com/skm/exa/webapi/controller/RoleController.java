package com.skm.exa.webapi.controller;


import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.RoleSaveDto;
import com.skm.exa.persistence.dto.RoleUpdateDto;
import com.skm.exa.persistence.qo.RoleQO;
import com.skm.exa.service.biz.RoleService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.QueryVo;
import com.skm.exa.webapi.vo.RoleSaveVo;
import com.skm.exa.webapi.vo.RoleUpdateVo;
import com.skm.exa.webapi.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.web.bind.annotation.*;
import sun.util.resources.cldr.az.CurrencyNames_az_Cyrl;

import java.util.List;


@RestController
@RequestMapping("/web/v1/role")
public class RoleController extends BaseController {


    @Autowired
    RoleService roleService;


    /**
     * 获得指定ID的角色
     * @param id
     * @return
     */
    @PostMapping("/getRole/id")
    public Result<RoleVo> getRole(@RequestParam("id") Long id){
        return new Result<RoleVo>().success(BeanMapper.map(roleService.getRole(id),RoleVo.class));
    }


    /**
     * 获得所有的角色
     * @return
     */
    @PostMapping("/getRoleList")
    public Result<List<RoleVo>> getRoleList(){
        List<RoleBean> roleBeans = roleService.getRoleList();
        List<RoleVo> roleVos = BeanMapper.mapList(roleBeans,RoleBean.class,RoleVo.class);
        return Result.success(roleVos);

    }


    /**
     *分页查询，可加条件
     * @param pageParam
     * @return
     */
    @PostMapping("/getRolePage")
    public Result<Page<RoleVo>> getRolePage(@RequestBody PageParam<QueryVo> pageParam){
        QueryVo queryVo = pageParam.getCondition();
        RoleQO qo = new RoleQO();
        qo.setCodeLike(queryVo.getKey());
        qo.setNameLike(queryVo.getKey());
        PageParam<RoleQO> param = new PageParam<>(pageParam.getPage(),pageParam.getSize());
        param.setCondition(qo);
        Page<RoleBean> roleBeanPage = roleService.getRolePage(param);
        Page<RoleVo> roleVoPage = roleBeanPage.map(RoleBean.class,RoleVo.class);
        return Result.success(roleVoPage);
    }

    /**
     * 添加角色
     * @param roleSaveVo
     * @return
     */
    @PostMapping("/addRole")
    public Result<RoleVo> addRole(@RequestBody RoleSaveVo roleSaveVo){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        RoleSaveDto roleSaveDto = BeanMapper.map(roleSaveVo,RoleSaveDto.class);
        Result<RoleBean> roleBeanResult = roleService.addRole(roleSaveDto, unifyAdmin);
        Result<RoleVo> roleVoResult = BeanMapper.map(roleBeanResult,Result.class);
        RoleVo roleVo = BeanMapper.map(roleBeanResult.getContent(),RoleVo.class);
        roleVoResult.setContent(roleVo);
        return roleVoResult;
    }


    @PostMapping("/updateRole")
    public Result<RoleVo> updateRole(@RequestBody RoleUpdateVo roleUpdateVo){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        RoleUpdateDto roleUpdateDto = BeanMapper.map(roleUpdateVo,RoleUpdateDto.class);
        Result<RoleBean> roleBeanResult = roleService.updateRole(roleUpdateDto,unifyAdmin);
        RoleVo roleVo = BeanMapper.map(roleBeanResult.getContent(),RoleVo.class);
        Result<RoleVo> result = BeanMapper.map(roleBeanResult,Result.class);
        result.setContent(roleVo);
        return result;

    }



    @PostMapping("/deleteRole/id")
    public Result<Boolean> deleteRole(@RequestParam("id") Long id){
        boolean is = roleService.deleteRole(id);
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
