package com.skm.exa.service.biz.impl;

import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.enums.StatusEnum;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.object.UnifyAuthority;
import com.skm.exa.common.object.UnifyRole;
import com.skm.exa.common.service.UnifyAdminService;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.common.utils.SetCommonElement;
import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.domain.bean.AuthorityBean;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dao.AdminDao;
import com.skm.exa.persistence.dto.*;
import com.skm.exa.persistence.qo.AdminQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.AdminService;
import com.skm.exa.service.biz.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.NewThreadAction;

import java.util.*;

@Service
public class AdminServiceImpl extends BaseServiceImpl<AdminBean , AdminDao> implements UnifyAdminService , AdminService {


    @Autowired
    RoleService roleService;


    /**
     * 管理员登录服务接口
     * @param username 用户名
     * @return
     */
    @Override
    public UnifyAdmin loadAdminByUsername(String username) {
        AdminQO adminQO = new AdminQO();
        adminQO.setUsername(username);
        AdminBean adminBean = super.find(adminQO);
        if(adminBean == null) return null;

        //获得登录管理员的角色和权限
        AdminDto roleBeans = getAdminRole(adminBean);
        List<RoleDto> roleDtos = roleService.getRoleAuthority(roleBeans.getRoleBeans());
        List<UnifyRole> roles = BeanMapper.mapList(roleDtos,RoleDto.class,UnifyRole.class);
        List<UnifyAuthority> authoritys = new ArrayList<>();
        for(RoleDto roleDto:roleDtos){
            for(AuthorityBean authorityBean:roleDto.getAuthorityBeans()){
                authoritys.add(BeanMapper.map(authorityBean,UnifyAuthority.class));
            }
        }
        UnifyAdmin unifyAdmin = new UnifyAdmin();
        unifyAdmin.setId(adminBean.getId());
        unifyAdmin.setUsername(adminBean.getUsername());
        unifyAdmin.setPassword(adminBean.getPassword());
        unifyAdmin.setName(adminBean.getName());
        unifyAdmin.setPhone(adminBean.getPhone());
        unifyAdmin.setEmail(adminBean.getEmail());
        unifyAdmin.setStatus(adminBean.getStatus());
        unifyAdmin.setRole(roles);
        unifyAdmin.setAuthority(authoritys);
        System.out.println("登录人的数据："+unifyAdmin.toString());
        return unifyAdmin;
    }

    /**
     * 获得指定ID的管理员
     * @param id
     * @return
     */
    @Override
    public AdminDto getAdmin(Long id){
        AdminBean adminBean = dao.get(id);
        return getAdminRole(adminBean);
    }



    /**
     * 获得所有管理员
     * @return
     */
    public List<AdminDto> getAdminList(){
        List<AdminBean> adminBeans = super.getList(null);
        return getAdminRole(adminBeans);
    }



    /**
     * 分页获得管理员，可加条件
     * @param pageParam
     * @return
     */
    @Override
    public Page<AdminDto> getAdminPage(PageParam<AdminQO> pageParam) {
        Page<AdminBean> pageAdminBean = dao.selectPage(pageParam);
        List<AdminDto> adminDtos = getAdminRole(pageAdminBean.getContent());
        Page<AdminDto> page = BeanMapper.map(pageAdminBean,Page.class);
        page.setContent(adminDtos);
        return page;
    }


    /**
     * 根据账号判读该账户是否存在
     * @param username
     * @return
     */
    @Override
    public boolean getAdminUsername(String username) {
        return super.has(new AdminQO(username));
    }


    /**
     * 添加管理员
     * @param adminSaveDto
     * @return
     */
    @Override
    @Transactional
    public Boolean addAdmin(AdminSaveDto adminSaveDto, UnifyAdmin unifyAdmin) {
        AdminBean adminBean = BeanMapper.map(adminSaveDto,AdminBean.class);
        adminBean = new SetCommonElement().setAdd(adminBean,unifyAdmin);
        int i = dao.insert(adminBean);
        if (i<=0)
            return false;
        if(adminSaveDto.getRoleId().size()<1)
            return true;
        return addAdminRole(adminSaveDto.getRoleId(),adminBean.getId(),unifyAdmin);
    }


    /**
     * 更新管理员
     * @param adminUpdateDto
     * @param unifyAdmin
     * @return
     */
    @Override
    @Transactional
    public Boolean updateAdmin(AdminUpdateDto adminUpdateDto, UnifyAdmin unifyAdmin) {
        AdminBean adminBean = BeanMapper.map(adminUpdateDto, AdminBean.class);
        adminBean = new SetCommonElement().setupdate(adminBean,unifyAdmin);
        int i = dao.update(adminBean);
        if(i<=0)
            return false;
        if(adminUpdateDto.getRoleId() == null || adminUpdateDto.getRoleId().size()<1)
            return true;
        return updateAdminRole(adminUpdateDto.getRoleId(),adminUpdateDto.getId(),unifyAdmin);
    }



    /**
     * 删除指定管理员
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteAdmin(Long id) {
        deleteAdminRole(id);
        return dao.delete(id) > 0;
    }


//<-----------------------管理员角色的获取及操作---------------------------->


    /**
     * 通过管理员，获得管理员角色
     * @param adminBean
     * @return
     */
    @Override
    public AdminDto getAdminRole(AdminBean adminBean) {
        if (adminBean == null)
            return null;
        List<RoleBean> roleBeans = dao.getAdminRoleAdminId(adminBean.getId());
        AdminDto adminDto = BeanMapper.map(adminBean,AdminDto.class);
        adminDto.setRoleBeans(roleBeans);
        return adminDto;
    }


    /**
     * 根据管理员列表，获得管理员角色
     * @param adminBeans
     * @return
     */
    @Override
    public List<AdminDto> getAdminRole(List<AdminBean> adminBeans){
        Map<Long,AdminDto> mapAdminDto = new HashMap<>();
        Map<Long,List<RoleBean>> mapRole = new HashMap<>();
        List<Long> adminId = new ArrayList<>();
        for(AdminBean adminBean:adminBeans){
            adminId.add(adminBean.getId());
            mapAdminDto.put(adminBean.getId(),BeanMapper.map(adminBean,AdminDto.class));
        }
        List<AdminRoleDto> adminRoleDtoList = dao.getAdminRole(adminId);
        for(AdminRoleDto adminRoleDto:adminRoleDtoList){
            if(mapRole.containsKey(adminRoleDto.getAdminId())){
                mapRole.get(adminRoleDto.getAdminId()).add(BeanMapper.map(adminRoleDto,RoleBean.class));
            }else {
                List<RoleBean> roleBeanList = new ArrayList<>();
                roleBeanList.add(BeanMapper.map(adminRoleDto,RoleBean.class));
                mapRole.put(adminRoleDto.getAdminId(),roleBeanList);
            }
        }
        for(Long id:mapRole.keySet()){
            mapAdminDto.get(id).setRoleBeans(mapRole.get(id));
        }
        List<AdminDto> adminDtos = new ArrayList<>();
        for(Long id:mapAdminDto.keySet()){
            adminDtos.add(mapAdminDto.get(id));
        }
        return adminDtos;

    }


    /**
     * 添加管理员角色
     * @param roleId
     * @param adminId
     * @param unifyAdmin
     * @return
     */
    @Override
    @Transactional
    public Boolean addAdminRole(List<Long> roleId, Long adminId,UnifyAdmin unifyAdmin){
        List<RoleBean> list = new ArrayList<>();
        for(Long i:roleId){
            RoleBean roleBean = new RoleBean();
            roleBean.setId(i);
            roleBean = new SetCommonElement().setAdd(roleBean,unifyAdmin);
            list.add(roleBean);
        }
        int is = dao.addAdminRole(list,adminId);
        if(is == roleId.size())
            return true;
        return false;
    }


    /**
     * 更新管理员角色
     * @param roleId
     * @param adminId
     * @param unifyAdmin
     * @return
     */
    @Override
    public Boolean updateAdminRole(List<Long> roleId, Long adminId, UnifyAdmin unifyAdmin) {
        if (!deleteAdminRole(adminId))
           return false;
        if(roleId.size() == 0)
            return true;
        return addAdminRole(roleId,adminId,unifyAdmin);
    }


    /**
     * 删除指定管理员的角色
     * @param adminId
     * @return
     */
     public Boolean deleteAdminRole(Long adminId){
         if (dao.getAdminRoleAdminId(adminId).size() == dao.deleteAdminRole(adminId))
             return true;
         return false;
     }
}
