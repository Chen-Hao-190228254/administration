package com.skm.exa.webapi.controller;


import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.common.utils.SetCommonElement;
import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.domain.bean.RoleBean;
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
        if(roleDto == null)
            return Result.error(Msg.E40016);
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
        //搜索条件
        RoleQO qo = new RoleQO(pageParam.getCondition().getKey(),pageParam.getCondition().getKey());
        //分页条件
        PageParam<RoleQO> param = new PageParam<>(pageParam.getPage(),pageParam.getSize(),qo);
        Page<RoleDto> roleBeanPage = roleService.getRolePage(param);
        Page<RoleVo> roleVoPage = roleBeanPage.map(RoleDto.class,RoleVo.class);
        roleVoPage.setContent(getListRoleVo(roleBeanPage.getContent()));
        return Result.success(roleVoPage);
    }




    /**
     * 根据账号判读该角色是否已经存在
     * @param code
     * @return
     */
    @ApiOperation(value = "根据账号判读该角色是否已经存在", notes = "根据账号判读该角色是否已经存在")
    @GetMapping("/getRoleCode/code")
    public Result getRoleCode(@ApiParam("需要判定的角色CODE") @RequestParam("code") String code){
        boolean is = roleService.getRoleCode(code);
        return Result.success(is);
    }




    /**
     * 添加角色
     * @param roleSaveVo
     * @return
     */
    @ApiOperation(value = "添加角色", notes = "添加角色")
    @PostMapping("/addRole")
    public Result addRole(@ApiParam("需要添加角色的信息") @RequestBody RoleSaveVo roleSaveVo){
        if(roleService.getRoleCode(roleSaveVo.getCode()))
            return Result.error(Msg.E40018);
        boolean is = roleService.addRole(BeanMapper.map(roleSaveVo,RoleSaveDto.class), getCurrentAdmin());
        return is? Result.success():Result.error(Msg.E40019);

    }


    /**
     *更新角色
     * @param roleUpdateVo
     * @return
     */
    @ApiOperation(value = "更新角色", notes = "更新角色")
    @PutMapping("/updateRole")
    public Result updateRole(@ApiParam("需要更新角色的信息") @RequestBody RoleUpdateVo roleUpdateVo){
        if(!roleService.getRole(roleUpdateVo.getId()).getCode().equals(roleUpdateVo.getCode()))
            if(roleService.getRoleCode(roleUpdateVo.getCode()))
                return Result.error(Msg.E40018);
        RoleUpdateDto roleUpdateDto = BeanMapper.map(roleUpdateVo,RoleUpdateDto.class);
        boolean is = roleService.updateRole(roleUpdateDto,getCurrentAdmin());
        return is? Result.success() : Result.error(Msg.E40023);
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
        return is ? Result.success() : Result.error(Msg.E40022);
    }


    /**
     * 更改角色状态
     * @param setStatusVo
     * @return
     */
    @ApiOperation(value = "更改角色状态", notes = "更改角色状态")
    @PutMapping("/setStatus/id")
    public Result setStatus(@ApiParam("需要更新的信息") @RequestBody SetStatusVo setStatusVo){
        boolean is = roleService.setStatus(BeanMapper.map(setStatusVo, RoleBean.class),getCurrentAdmin());
        return is? Result.success() : Result.error(Msg.E40023);
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
         if(roleDto == null)
             return null;
         RoleVo roleVo = BeanMapper.map(roleDto,RoleVo.class);
         roleVo.setAuthorityVos(BeanMapper.mapList(roleDto.getAuthorityBeans(), AuthorityBean.class, AuthorityVo.class));
         return roleVo;
     }




}




