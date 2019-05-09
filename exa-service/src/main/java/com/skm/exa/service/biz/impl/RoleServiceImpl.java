package com.skm.exa.service.biz.impl;


import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.enums.StatusEnum;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dao.RoleDao;
import com.skm.exa.persistence.dto.RoleAuthorityDto;
import com.skm.exa.persistence.dto.RoleDto;
import com.skm.exa.persistence.dto.RoleSaveDto;
import com.skm.exa.persistence.dto.RoleUpdateDto;
import com.skm.exa.persistence.qo.RoleQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleBean, RoleDao> implements RoleService {




    /**
     * 通过指定ID，获得角色
     * @param id
     * @return
     */
    @Override
    public RoleDto getRole(Long id) {
        RoleBean role = dao.getRole(id);
        return getRoleAuthority(role);
    }


    /**
     * 获得所有的角色
     * @return
     */
    @Override
    public List<RoleDto> getRoleList(){
        List<RoleBean> roleBeans = dao.getRoleList();
        List<RoleDto> roleDtos = getRoleAuthority(roleBeans);
        return roleDtos;
    }


    /**
     * 分页查询，可加条件
     * @param qo
     * @return
     */
    @Override
    public Page<RoleDto> getRolePage(PageParam<RoleQO> qo){
        Page<RoleBean> page = dao.getRolePage(qo);
        List<RoleBean> list = page.getContent();
        List<RoleDto> roleDtos = getRoleAuthority(list);
        Page<RoleDto> roleDtoPage = BeanMapper.map(page,Page.class);
        roleDtoPage.setContent(roleDtos);
        return roleDtoPage;
    }

    /**
     * 添加角色
     * @param roleSaveDto
     * @return
     */
    @Override
    @Transactional
    public Result<RoleDto> addRole(RoleSaveDto roleSaveDto, UnifyAdmin unifyAdmin) {
        RoleQO roleQO = new RoleQO();
        roleQO.setCode(roleSaveDto.getCode());
        boolean has = super.has(roleQO);
        if(has){
            RoleDto roleDto = BeanMapper.map(roleSaveDto,RoleDto.class);
            Result<RoleDto> result = Result.error(-1,"角色编码已经存在");
            result.setContent(roleDto);
            return result;
        }
        RoleBean roleBean = BeanMapper.map(roleSaveDto,RoleBean.class);
        roleBean.setEntryId(unifyAdmin.getId());
        roleBean.setEntryName(unifyAdmin.getName());
        roleBean.setEntryDt(new Date());
        roleBean.setUpdateId(unifyAdmin.getId());
        roleBean.setUpdateName(unifyAdmin.getName());
        roleBean.setUpdateDt(new Date());
        int is = dao.addRole(roleBean);
        if(is<=0){
            Result result = new Result(Msg.E40000);
            result.setMessage("添加角色时错误");
            return result;
        }else {
            List<Long> authorityId = roleSaveDto.getAuthorityId();
            Result<List<AuthorityBean>> addRoleAuthority = addRoleAuthority(authorityId,roleBean.getId(),unifyAdmin);
            List<AuthorityBean> authorityBeanList = addRoleAuthority.getContent();
            RoleDto roleDto = BeanMapper.map(roleBean,RoleDto.class);
            roleDto.setAuthorityBeans(authorityBeanList);
            Result<RoleDto> result = BeanMapper.map(addRoleAuthority,Result.class);
            result.setContent(roleDto);
            return result;

        }
    }


    /**
     * 更新角色
     * @param roleUpdateDto
     * @param unifyAdmin
     * @return
     */
    @Override
    @Transactional
    public Result<RoleDto> updateRole(RoleUpdateDto roleUpdateDto,UnifyAdmin unifyAdmin){
        RoleQO roleQO = new RoleQO();
        roleQO.setCode(roleUpdateDto.getCode());
        boolean has = super.has(roleQO);
        if(has){
            RoleDto roleDto = BeanMapper.map(roleUpdateDto,RoleDto.class);
            Result<RoleDto> result = Result.error(-1,"角色编码已经存在");
            result.setContent(roleDto);
            return result;
        }
        RoleBean roleBean = BeanMapper.map(roleUpdateDto,RoleBean.class);
        roleBean.setUpdateId(unifyAdmin.getId());
        roleBean.setUpdateName(unifyAdmin.getName());
        roleBean.setUpdateDt(new Date());
        int is = dao.updateRole(roleBean);
        if(is<=0){
            Result result = new Result(Msg.E40000);
            result.setMessage("更新角色时发生错误");
            return result;
        }else {
            List<Long> authorityId = roleUpdateDto.getAuthorityId();
            Result<List<AuthorityBean>> updateRoleAuthority = updateRoleAuthority(authorityId,roleBean.getId(),unifyAdmin);
            List<AuthorityBean> authorityBeanList = updateRoleAuthority.getContent();
            RoleDto roleDto = BeanMapper.map(roleBean,RoleDto.class);
            roleDto.setAuthorityBeans(authorityBeanList);
            Result<RoleDto> result = BeanMapper.map(updateRoleAuthority,Result.class);
            result.setContent(roleDto);
            return result;
        }

    }


    /**
     * 删除角色
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteRole(Long id) {
        int i = dao.deleteRoleAuthority(id);
        if(i<=0){
            return false;
        }else {
            int is = dao.delete(id);
            if (is<=0){
                return false;
            }else {
                return true;
            }
        }
    }

    /**
     * 更改权限状态
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Result<RoleBean> setStatus(Long id) {
        RoleBean roleBean = getRole(id);
        if(roleBean == null){
            return Result.error(-1,"指定ID的数据不存在");
        }else {
            if(roleBean.getStatus() == StatusEnum.NORMAL.getIndex()){
                int is = dao.setStatus(id,StatusEnum.FORBIDDEN.getIndex());
                if(is<=0){
                    return Result.error(-1,"状态更改失败");
                }
            }else if (roleBean.getStatus() == StatusEnum.FORBIDDEN.getIndex()){
                int is = dao.setStatus(id,StatusEnum.NORMAL.getIndex());
                if(is<=0){
                    return Result.error(-1,"状态更改失败");
                }
            }else {
                return Result.error(-1,"数据库状态数据有误");
            }
        }
        return Result.success(getRole(id));
    }




//<-----------------------角色权限的获取及操作---------------------------->



    /**
     * 通过角色ID，获得角色的权限
     * @param roleBean
     * @return
     */
    @Override
     public RoleDto getRoleAuthority(RoleBean roleBean){
        List<AuthorityBean> roleAuthority = dao.getRoleAuthorityRoleId(roleBean.getId());
        RoleDto roleDto = BeanMapper.map(roleBean,RoleDto.class);
        roleDto.setAuthorityBeans(roleAuthority);
        return roleDto;
     }


    /**
     * 通过角色列表，获得列表中角色的权限
     * @param roleBeans
     * @return
     */
    @Override
    public List<RoleDto> getRoleAuthority(List<RoleBean> roleBeans){
        Map<Long,List<AuthorityBean>> mapAuthority = new HashMap<>();
        Map<Long,RoleDto> mapRole = new HashMap<>();
        List<Long> roleIdList = new ArrayList<>();
        List<RoleDto> roleDtos = BeanMapper.mapList(roleBeans,RoleBean.class,RoleDto.class);
        for(RoleDto role:roleDtos){
            roleIdList.add(role.getId());
            mapRole.put(role.getId(),role);
        }
        List<RoleAuthorityDto> roleAuthorityDtos = dao.getRoleAuthority(roleIdList);
        for(RoleAuthorityDto roleAuthorityDto:roleAuthorityDtos){
           Long roleId = roleAuthorityDto.getRoleId();
           if(mapAuthority.containsKey(roleId)){
               mapAuthority.get(roleId).add(BeanMapper.map(roleAuthorityDto,AuthorityBean.class));
           }else {
               List<AuthorityBean> authorityBeanList = new ArrayList<>();
               authorityBeanList.add(BeanMapper.map(roleAuthorityDto,AuthorityBean.class));
               mapAuthority.put(roleId,authorityBeanList);
           }
        }
        for(Long roleId:mapAuthority.keySet()){
            mapRole.get(roleId).setAuthorityBeans(mapAuthority.get(roleId));
        }
        roleDtos = new ArrayList<>();
        for(Long roleId:mapRole.keySet()){
            roleDtos.add(mapRole.get(roleId));
        }
        return roleDtos;
    }


    /**
     * 添加角色权限
     * @param authorityId
     * @param roleId
     * @param unifyAdmin
     * @return
     */
    @Override
    @Transactional
    public Result<List<AuthorityBean>> addRoleAuthority(List<Long> authorityId,Long roleId,UnifyAdmin unifyAdmin){
        List<AuthorityBean> authorityBeans = new ArrayList<>();
        for (Long i:authorityId){
            AuthorityBean authorityBean = new AuthorityBean();
            authorityBean.setId(i);
            authorityBean.setEntryId(unifyAdmin.getId());
            authorityBean.setEntryName(unifyAdmin.getName());
            authorityBean.setEntryDt(new Date());
            authorityBean.setUpdateId(unifyAdmin.getId());
            authorityBean.setUpdateName(unifyAdmin.getName());
            authorityBean.setUpdateDt(new Date());
            authorityBeans.add(authorityBean);
        }
        int i = dao.addRoleAuthority(authorityBeans,roleId);
        if(i<=0){
            return new Result(-1,"添加角色权限时发生错误");
        }else {
            List<AuthorityBean> authorityBeanList = dao.getRoleAuthorityRoleId(roleId);
            return Result.success(authorityBeanList);
        }

    }

    /**
     * 更新角色权限     需要配合（添加角色权限)addRoleAuthority使用
     * @param authorityId
     * @param roleId
     * @param unifyAdmin
     * @return
     */
    @Override
    @Transactional
    public Result<List<AuthorityBean>> updateRoleAuthority(List<Long> authorityId,Long roleId,UnifyAdmin unifyAdmin){
        boolean i = deleteRoleAuthority(roleId);
        if(!i){
            Result result = new Result(Msg.E40000);
            result.setMessage("删除角色权限时发生错误");
            return result;
        }else {
            return addRoleAuthority(authorityId,roleId,unifyAdmin);
        }

    }


    /**
     * 删除角色的权限所有
     * @param roleId
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteRoleAuthority(Long roleId) {
        List<AuthorityBean> roleAuthority = dao.getRoleAuthorityRoleId(roleId);
        int i = dao.deleteRoleAuthority(roleId);
        if(i != roleAuthority.size()){
            return false;
        }else {
            return true;
        }
    }

}
