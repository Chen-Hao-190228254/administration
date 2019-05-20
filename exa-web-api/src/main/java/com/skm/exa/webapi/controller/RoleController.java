package com.skm.exa.webapi.controller;


import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dto.RoleDto;
import com.skm.exa.persistence.dto.RoleSaveDto;
import com.skm.exa.persistence.dto.RoleUpdateDto;
import com.skm.exa.persistence.qo.RoleQO;
import com.skm.exa.service.biz.RoleService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "角色操作",description = "角色操作")
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
    @ApiOperation(value = "根据ID获得角色信息", notes = "根据ID获得角色信息")
    @GetMapping("/getRole/id")
    public Result<RoleVo> getRole(@ApiParam("需要获取角色的ID") @RequestParam("id") Long id){
        RoleDto roleDto = roleService.getRole(id);
        return Result.success(getRoleVo(roleDto));
    }


    /**
     * 获得所有的角色
     * @return
     */
    @ApiOperation(value = "获得所有角色信息", notes = "获得所有角色信息")
    @GetMapping("/getRoleList")
    public Result<List<RoleVo>> getRoleList(){
        List<RoleDto> roleDtos = roleService.getRoleList();
        return Result.success(getListRoleVo(roleDtos));

    }


    /**
     *分页查询，可加条件
     * @param pageParam
     * @return
     */
    @ApiOperation(value = "分页查询角色", notes = "分页查询角色")
    @PostMapping("/getRolePage")
    public Result<Page<RoleVo>> getRolePage(@ApiParam("分页及条件信息")@RequestBody PageParam<QueryVo> pageParam){
        QueryVo queryVo = pageParam.getCondition();
        RoleQO qo = new RoleQO();
        qo.setCodeLike(queryVo.getKey());
        qo.setNameLike(queryVo.getKey());
        PageParam<RoleQO> param = new PageParam<>(pageParam.getPage(),pageParam.getSize());
        param.setCondition(qo);
        Page<RoleDto> roleBeanPage = roleService.getRolePage(param);



        List<RoleDto> roleDtos = roleBeanPage.getContent();
        List<RoleVo> roleVos = getListRoleVo(roleDtos);



        Page<RoleVo> roleVoPage = roleBeanPage.map(RoleDto.class,RoleVo.class);
        return Result.success(roleVoPage);
    }

    /**
     * 添加角色
     * @param roleSaveVo
     * @return
     */
    @ApiOperation(value = "添加角色", notes = "添加角色")
    @PostMapping("/addRole")
    public Result<RoleVo> addRole(@ApiParam("需要添加角色的信息") @RequestBody RoleSaveVo roleSaveVo){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        RoleSaveDto roleSaveDto = BeanMapper.map(roleSaveVo,RoleSaveDto.class);
        Result<RoleDto> roleDtoResult = roleService.addRole(roleSaveDto, unifyAdmin);
        Result<RoleVo> roleVoResult = BeanMapper.map(roleDtoResult,Result.class);
        RoleVo roleVo = getRoleVo(roleDtoResult.getContent());
        roleVoResult.setContent(roleVo);
        return roleVoResult;
    }


    /**
     *更新角色
     * @param roleUpdateVo
     * @return
     */
    @ApiOperation(value = "更新角色", notes = "更新角色")
    @PutMapping("/updateRole")
    public Result<RoleVo> updateRole(@ApiParam("需要更新角色的信息") @RequestBody RoleUpdateVo roleUpdateVo){
        UnifyAdmin unifyAdmin = getCurrentAdmin();
        RoleUpdateDto roleUpdateDto = BeanMapper.map(roleUpdateVo,RoleUpdateDto.class);
        Result<RoleDto> roleDtoResult = roleService.updateRole(roleUpdateDto,unifyAdmin);
        RoleVo roleVo = getRoleVo(roleDtoResult.getContent());
        Result<RoleVo> result = BeanMapper.map(roleDtoResult,Result.class);
        result.setContent(roleVo);
        return result;

    }


    /**
     * 删除角色
     * @param id
     * @return
     */
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @DeleteMapping("/deleteRole/id")
    public Result<Boolean> deleteRole(@ApiParam("需要删除角色的ID") @RequestParam("id") Long id){
        boolean is = roleService.deleteRole(id);
        if(is){
            Result<Boolean> result = new Result<>(1,"删除成功");
            result.setContent(true);
            return result;
        }else {
            Result<Boolean> result = new Result<>(1,"删除成功");
            result.setContent(true);
            return result;
        }
    }


    /**
     * 更改角色状态
     * @param id
     * @return
     */
    @ApiOperation(value = "更改角色状态", notes = "更改角色状态")
    @PutMapping("/setStatus/id")
    public Result<RoleVo> setStatus(@ApiParam("需要更改角色状态的ID") @RequestParam("id") Long id){
        Result<RoleDto> roleDtoResult = roleService.setStatus(id);
        Result<RoleVo> result = BeanMapper.map(roleDtoResult,Result.class);
        if(result.getContent() ==null)
            return result;
        RoleVo roleVo = getRoleVo(roleDtoResult.getContent());
        result.setContent(roleVo);
        return result;
    }




    /**
     * 类型转换      根据List<RoleDto>返回List<RoleVo>
     * @param roleDtos
     * @return
     */
    public List<RoleVo> getListRoleVo(List<RoleDto> roleDtos){
        List<RoleVo> roleVos = BeanMapper.mapList(roleDtos,RoleDto.class,RoleVo.class);
        Map<Long,RoleVo> mapRole = new HashMap<>();
        for(RoleVo roleVo:roleVos){
            mapRole.put(roleVo.getId(),roleVo);
        }
        for(RoleDto roleDto:roleDtos){
            List<AuthorityVo> authorityVoList = BeanMapper.mapList(roleDto.getAuthorityBeans(),AuthorityBean.class,AuthorityVo.class);
            mapRole.get(roleDto.getId()).setAuthorityVos(authorityVoList);
        }
        roleVos = new ArrayList<>();
        for(Long roleId:mapRole.keySet()){
            roleVos.add(mapRole.get(roleId));
        }
        return roleVos;
    }

    /**
     * 类型转换      根据RoleDto返回RoleVo
     * @param roleDto
     * @return
     */
     public RoleVo getRoleVo(RoleDto roleDto){
         RoleVo roleVo = BeanMapper.map(roleDto,RoleVo.class);
         roleVo.setAuthorityVos(BeanMapper.mapList(roleDto.getAuthorityBeans(), AuthorityBean.class, AuthorityVo.class));
         return roleVo;
     }




}




