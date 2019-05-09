package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.Result;
import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.service.UnifyAdminService;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.domain.bean.AdminBean;
import com.skm.exa.domain.bean.RoleBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.persistence.dao.AdminDao;
import com.skm.exa.persistence.dto.AdminUpdateDto;
import com.skm.exa.persistence.qo.AdminQO;
import com.skm.exa.persistence.dto.AdminSaveDto;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.AdminService;
import com.skm.exa.service.biz.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.NewThreadAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        UnifyAdmin unifyAdmin = new UnifyAdmin();
        unifyAdmin.setId(adminBean.getId());
        unifyAdmin.setUsername(adminBean.getUsername());
        unifyAdmin.setPassword(adminBean.getPassword());
        unifyAdmin.setName(adminBean.getName());
        unifyAdmin.setPhone(adminBean.getPhone());
        unifyAdmin.setEmail(adminBean.getEmail());
        unifyAdmin.setStatus(adminBean.getStatus());
        System.out.println("登录人的数据："+unifyAdmin.toString());
        return unifyAdmin;
    }





    /**
     * 获得所有管理员
     * @return
     */
    public List<AdminBean> getAdminList(){
        List<AdminBean> adminBeans = dao.getAdminList();
        adminBeans = getAdminRoleList(adminBeans);
        return adminBeans;
    }


    /**
     * 获得指定ID的管理员
     * @param id
     * @return
     */
    public AdminBean getAdmin(Long id){
//        AdminBean adminBean =dao.getAdmin(id);
//        List<RoleBean> roleBean = dao.getRoleIn(id);
//        roleBean = roleService.getRoleAuthority(roleBean);
//        adminBean.setRoleBeans(roleBean);
//        return adminBean;
        return null;
    }


    /**
     * 分页获得管理员，可加条件
     * @param pageParam
     * @return
     */
    @Override
    public Page<AdminBean> getAdminPage(PageParam<AdminQO> pageParam) {
        Page<AdminBean> page = dao.getAdminPage(pageParam);
        List<AdminBean> adminBeans = page.getContent();
        adminBeans = getAdminRoleList(adminBeans);
        page.setContent(adminBeans);
        return page;
    }


    /**
     * 添加管理员
     * @param adminSaveDto
     * @return
     */
    @Override
    @Transactional
    public Result<AdminBean> addAdmin(AdminSaveDto adminSaveDto, UnifyAdmin unifyAdmin) {
        String username = adminSaveDto.getUsername();
        AdminQO adminQO = new AdminQO();
        adminQO.setUsername(username);
        boolean is = super.has(adminQO);
        if (is) {
            AdminBean adminBean = BeanMapper.map(adminSaveDto,AdminBean.class);
            Result<AdminBean> result = new Result<>();
            result.setMessage("账号已存在，请重新输入账号");
            result.setContent(adminBean);
            System.out.println("账号已存在："+adminBean.toString());
            return result;
        }else {
            AdminBean adminBean = BeanMapper.map(adminSaveDto,AdminBean.class);
            adminBean.setEntryId(unifyAdmin.getId());
            adminBean.setEntryName(unifyAdmin.getName());
            adminBean.setEntryDt(new Date());
            adminBean.setUpdateId(unifyAdmin.getId());
            adminBean.setUpdateName(unifyAdmin.getName());
            adminBean.setUpdateDt(new Date());
            int i = dao.insert(adminBean);
            if(i<=0){
                return Result.error(-1,"再添加管理员时失败");
            }else {
                List<Long> roleIdList=adminSaveDto.getRoleId();
                if(roleIdList.size()<=0){
                    return Result.success(adminBean);
                }else {
                    Result<List<RoleBean>> roleList = addAdminRole(roleIdList,adminBean.getId(),unifyAdmin);
                    adminBean.setRoleBeans(roleList.getContent());
                    Result<AdminBean> result = BeanMapper.map(roleList,Result.class);
                    result.setContent(adminBean);
                    return result;
                }
            }
        }
    }


    /**
     * 更新管理员
     * @param adminUpDto
     * @param unifyAdmin
     * @return
     */
    @Override
    public AdminBean upAdmin(AdminUpdateDto adminUpDto, UnifyAdmin unifyAdmin) {
        return null;
    }





//<-----------------------管理员角色的获取及操作---------------------------->

    /**
     * 添加管理员角色
     * @param roleId
     * @param adminId
     * @param unifyAdmin
     * @return
     */
    @Override
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
        if(is<=0){
            return  new Result(-1,"添加管理员角色时发生错误");
        }else {
            return Result.success(dao.getRoleIn(adminId));
        }
    }


    /**
     * 根据管理员列表，获得管理员角色
     * @param adminBeans
     * @return
     */
    @Override
    public List<AdminBean> getAdminRoleList(List<AdminBean> adminBeans){
        List<AdminBean> adminBeanList = new ArrayList<>();
        for(AdminBean adminBean:adminBeans){
            adminBeanList.add(getAdmin(adminBean.getId()));
        }
        return adminBeanList;
    }


}
