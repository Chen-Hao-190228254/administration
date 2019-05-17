package com.skm.exa.service.biz.impl;

import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.enums.StatusEnum;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.object.UnifyAuthority;
import com.skm.exa.common.object.UnifyRole;
import com.skm.exa.common.service.UnifyAdminService;
import com.skm.exa.common.utils.BeanMapper;
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
        AdminBean adminBean = dao.getAdmin(id);
        if (adminBean == null)
            return null;
        AdminDto adminDto = getAdminRole(adminBean);
        return adminDto;
    }



    /**
     * 获得所有管理员
     * @return
     */
    public List<AdminDto> getAdminList(){
        List<AdminBean> adminBeans = dao.getAdminList();
        return getAdminRole(adminBeans);
    }



    /**
     * 分页获得管理员，可加条件
     * @param pageParam
     * @return
     */
    @Override
    public Page<AdminDto> getAdminPage(PageParam<AdminQO> pageParam) {
        Page<AdminBean> pageAdminBean = dao.getAdminPage(pageParam);
        List<AdminBean> adminBeans = pageAdminBean.getContent();
        List<AdminDto> adminDtos = getAdminRole(adminBeans);
        Page<AdminDto> page = BeanMapper.map(pageAdminBean,Page.class);
        page.setContent(adminDtos);
        return page;
    }


    /**
     * 添加管理员
     * @param adminSaveDto
     * @return
     */
    @Override
    @Transactional
    public Result<AdminDto> addAdmin(AdminSaveDto adminSaveDto, UnifyAdmin unifyAdmin) {
        String username = adminSaveDto.getUsername();
        AdminQO adminQO = new AdminQO();
        adminQO.setUsername(username);
        boolean is = super.has(adminQO);
        if (is) {
            Result<AdminDto> result = Result.error(-1,"账号已存在，请重新输入账号");
            result.setContent(BeanMapper.map(adminSaveDto,AdminDto.class));
            return result;
        }else {
            AdminBean adminBean = BeanMapper.map(adminSaveDto,AdminBean.class);
            adminBean.setEntryId(unifyAdmin.getId());
            adminBean.setEntryName(unifyAdmin.getName());
            adminBean.setEntryDt(new Date());
            adminBean.setUpdateId(unifyAdmin.getId());
            adminBean.setUpdateName(unifyAdmin.getName());
            adminBean.setUpdateDt(new Date());
            int i = dao.addAdmin(adminBean);
            if(i<=0){
                Result<AdminDto> result = Result.error(-1,"在添加管理员时失败");
                result.setContent(BeanMapper.map(adminSaveDto,AdminDto.class));
                return result;
            }else {
                List<Long> roleIdList=adminSaveDto.getRoleId();
                if(roleIdList.size()<=0){
                    return Result.success(getAdmin(adminBean.getId()));
                }else {
                    Result<List<RoleBean>> roleList = addAdminRole(roleIdList,adminBean.getId(),unifyAdmin);
                    AdminDto adminDto = BeanMapper.map(adminBean,AdminDto.class);
                    Result<AdminDto> result = BeanMapper.map(roleList,Result.class);
                    result.setContent(adminDto);
                    return result;
                }
            }
        }
    }


    /**
     * 更新管理员
     * @param adminUpdateDto
     * @param unifyAdmin
     * @return
     */
    @Override
    @Transactional
    public Result<AdminDto> updateAdmin(AdminUpdateDto adminUpdateDto, UnifyAdmin unifyAdmin) {
        String username = adminUpdateDto.getUsername();
        AdminQO adminQO = new AdminQO();
        adminQO.setUsername(username);
        boolean is = super.has(adminQO);
        if (is) {
            Result<AdminDto> result = Result.error(-1,"账号已存在，请重新输入账号");
            result.setContent(BeanMapper.map(adminUpdateDto,AdminDto.class));
            return result;
        }else{
            AdminBean adminBean = BeanMapper.map(adminUpdateDto, AdminBean.class);
            adminBean.setUpdateId(unifyAdmin.getId());
            adminBean.setUpdateName(unifyAdmin.getName());
            adminBean.setUpdateDt(new Date());
            int i = dao.updateAdmin(adminBean);
            if(i<=0){
                Result<AdminDto> result = Result.error(-1,"在更新管理员时失败");
                result.setContent(BeanMapper.map(adminUpdateDto,AdminDto.class));
                return result;
            }else {
                List<Long> roleIdList=adminUpdateDto.getRoleId();
                if(roleIdList.size()<=0){
                    return Result.success(getAdmin(adminUpdateDto.getId()));
                }else {
                    Result<List<RoleBean>> role = updateAdminRole(roleIdList,adminUpdateDto.getId(),unifyAdmin);
                    Result<AdminDto> result = BeanMapper.map(role,Result.class);
                    AdminDto adminDto = getAdmin(adminUpdateDto.getId());
                    result.setContent(adminDto);
                    return result;
                }
            }
        }
    }



    /**
     * 更新管理员密码
     * @param password
     * @return
     */
    @Override
    public Result updatePassword(String password , Long adminId) {
        if(dao.getAdmin(adminId) == null)
            return Result.error(-1,"管理员ID有误");
        int i = dao.updatePassword(password, adminId);
        if(i<=0){
            return Result.success(false);
        }else {
            return Result.success(true);
        }
    }


    /**
     * 更新管理员状态
     * @param id
     * @return
     */
    @Override
    public Result<AdminDto> updateStatus(Long id) {
        AdminDto adminDto = getAdmin(id);
        if(adminDto == null)
            return Result.error(-1,"管理员ID有误");
        byte status = adminDto.getStatus();
        if(status == StatusEnum.FORBIDDEN.getIndex()){
            int i = dao.updateStatus(id,StatusEnum.NORMAL.getIndex());
            if(i<=0){
                return Result.error(-1,"管理员状态更新失败");
            }
        }else if (status == StatusEnum.NORMAL.getIndex()){
            int i = dao.updateStatus(id,StatusEnum.FORBIDDEN.getIndex());
            if(i<=0){
                return Result.error(-1,"管理员状态更新失败");
            }
        }else {
            return Result.error(-1,"数据库的管理员状态有误");
        }
        return Result.success(getAdmin(id));
    }


    /**
     * 删除指定管理员
     * @param id
     * @return
     */
    @Override
    public Boolean deleteAdmin(Long id) {
        boolean is = deleteAdminRole(id);
        if(is){
            if(dao.getAdmin(id) != null){
                int i = dao.delete(id);
                if(i<=0){
                    return false;
                }else {
                    return true;
                }
            }else {
                return true;
            }
        }else {
            return false;
        }
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
    public Result<List<RoleBean>> addAdminRole(List<Long> roleId, Long adminId,UnifyAdmin unifyAdmin){
        List<RoleBean> list = new ArrayList<>();
        for(Long i:roleId){
            RoleBean roleBean = new RoleBean();
            roleBean.setId(i);
            roleBean.setEntryId(unifyAdmin.getId());
            roleBean.setEntryName(unifyAdmin.getName());
            roleBean.setEntryDt(new Date());
            roleBean.setUpdateId(unifyAdmin.getId());
            roleBean.setUpdateName(unifyAdmin.getName());
            roleBean.setUpdateDt(new Date());
            list.add(roleBean);
        }
        int is = dao.addAdminRole(list,adminId);
        if(is != roleId.size()){
            return  new Result(-1,"添加管理员角色时发生错误");
        }else {
            return Result.success(dao.getAdminRoleAdminId(adminId));
        }
    }


    /**
     * 更新管理员角色
     * @param roleId
     * @param adminId
     * @param unifyAdmin
     * @return
     */
    @Override
    public Result<List<RoleBean>> updateAdminRole(List<Long> roleId, Long adminId, UnifyAdmin unifyAdmin) {
        boolean i = deleteAdminRole(adminId);
        if (!i) {
            Result result = new Result(Msg.E40000);
            result.setMessage("删除管理员角色时发生错误");
            return result;
        }else {
            return addAdminRole(roleId,adminId,unifyAdmin);
        }
    }


    /**
     * 删除指定管理员的角色
     * @param adminId
     * @return
     */
     public Boolean deleteAdminRole(Long adminId){
        List<RoleBean> roleBeans = dao.getAdminRoleAdminId(adminId);
        int i = dao.deleteAdminRole(adminId);
        if(roleBeans.size() == i){
            return true;
        }else {
            return false;
        }

     }



}
