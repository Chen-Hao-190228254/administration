package com.skm.exa.service.biz.impl;


import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.enums.StatusEnum;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.common.utils.SetCommonElement;
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
        RoleBean role = dao.get(id);
        return getRoleAuthority(role);
    }


    /**
     * 获得所有的角色
     * @return
     */
    @Override
    public List<RoleDto> getRoleList(){
        List<RoleBean> roleBeans = dao.select(null);
        return getRoleAuthority(roleBeans);
    }


    /**
     * 分页查询，可加条件
     * @param qo
     * @return
     */
    @Override
    public Page<RoleDto> getRolePage(PageParam<RoleQO> qo){
        Page<RoleBean> page = dao.selectPage(qo);
        Page<RoleDto> roleDtoPage = BeanMapper.map(page,Page.class);
        roleDtoPage.setContent(getRoleAuthority(page.getContent()));
        return roleDtoPage;
    }


    /**
     * 判断该角色是否存在
     * @param code
     * @return
     */
    @Override
    public Boolean getRoleCode(String code) {
        return super.has(new RoleQO(code));
    }


    /**
     * 添加角色
     * @param roleSaveDto
     * @return
     */
    @Override
    @Transactional
    public Boolean addRole(RoleSaveDto roleSaveDto, UnifyAdmin unifyAdmin) {
        RoleBean roleBean = new SetCommonElement().setAdd(BeanMapper.map(roleSaveDto,RoleBean.class),unifyAdmin);
        int is = dao.insert(roleBean);
        if(is>0 && roleSaveDto.getAuthorityId().size() > 0)
            addRoleAuthority(roleSaveDto.getAuthorityId(),roleBean.getId(),unifyAdmin);
        return is>0;
    }


    /**
     * 更新角色
     * @param roleUpdateDto
     * @param unifyAdmin
     * @return
     */
    @Override
    @Transactional
    public Boolean updateRole(RoleUpdateDto roleUpdateDto,UnifyAdmin unifyAdmin){
        RoleBean roleBean = new SetCommonElement().setAdd(BeanMapper.map(roleUpdateDto,RoleBean.class),unifyAdmin);
        int is = dao.update(roleBean);
        if(is>0)
            updateRoleAuthority(roleUpdateDto.getAuthorityId(),roleUpdateDto.getId(),unifyAdmin);
        return is>0;
    }


    /**
     * 删除角色
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteRole(Long id) {
        //删除角色的权限
        boolean is = deleteRoleAuthority(id);
        if(!is)
            return false;
        is = dao.delete(id) > 0;
        return is;
    }

    /**
     * 更改权限状态
     * @param roleBean
     * @return
     */
    @Override
    @Transactional
    public Boolean setStatus(RoleBean roleBean,UnifyAdmin unifyAdmin) {
        roleBean = new SetCommonElement().setupdate(roleBean,unifyAdmin);
        int is = dao.update(roleBean);
        return is>0;
    }




//<-----------------------角色权限的获取及操作---------------------------->



    /**
     * 通过角色，获得角色的权限
     * @param roleBean
     * @return
     */
    @Override
     public RoleDto getRoleAuthority(RoleBean roleBean){
        if(roleBean == null)
            return null;
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
        if(roleBeans == null || roleBeans.size() == 0)
            return null;
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
    public Boolean addRoleAuthority(List<Long> authorityId,Long roleId,UnifyAdmin unifyAdmin){
        List<AuthorityBean> authorityBeans = new ArrayList<>();
        for (Long i:authorityId){
            AuthorityBean authorityBean = new AuthorityBean();
            authorityBean.setId(i);
            authorityBean = new SetCommonElement().setAdd(authorityBean,unifyAdmin);
            authorityBeans.add(authorityBean);
        }
        int i = dao.addRoleAuthority(authorityBeans,roleId);
        return i>0;
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
    public Boolean updateRoleAuthority(List<Long> authorityId,Long roleId,UnifyAdmin unifyAdmin){
        if(deleteRoleAuthority(roleId))
            if(addRoleAuthority(authorityId,roleId,unifyAdmin))
                return true;
        return false;
    }


    /**
     * 删除角色的权限所有
     * @param roleId 角色ID
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteRoleAuthority(Long roleId) {
        if(dao.getRoleAuthorityRoleId(roleId).size() == dao.deleteRoleAuthority(roleId))
            return true;
        return false;
    }


}
