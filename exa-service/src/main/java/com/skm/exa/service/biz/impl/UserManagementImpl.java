package com.skm.exa.service.biz.impl;

import com.skm.exa.common.object.UnifyAdmin;
import com.skm.exa.common.utils.BeanMapper;
import com.skm.exa.common.utils.FileTidyingUtil;
import com.skm.exa.common.utils.SetCommonElement;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.domain.bean.UserManagementBean;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.enums.UserManagementStatusEnum;
import com.skm.exa.persistence.dao.UserManagementDao;
import com.skm.exa.persistence.dto.*;
import com.skm.exa.persistence.qo.RoleQO;
import com.skm.exa.persistence.qo.UserManagementLikeQO;
import com.skm.exa.service.BaseServiceImpl;
import com.skm.exa.service.biz.CommonService;
import com.skm.exa.service.biz.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class UserManagementImpl extends BaseServiceImpl<UserManagementBean , UserManagementDao> implements UserManagementService  {

    String correlationTableName = "administration_users_management"; //关联表名


    @Autowired
    FileTidyingUtil fileTidyingUtil;
    /**
     *  UserManagement 分页查询
     * @param userManagementLikeQoPage  //分页
     * @return
     */
    @Override
    @Transactional
    public Page<UserManagementDto> selectDtoPage(PageParam<UserManagementLikeQO> userManagementLikeQoPage) {
        Page<UserManagementBean> userManagementBeanPage =  dao.getManagementDtoPage(userManagementLikeQoPage);
        List<UserManagementDto> userManagementDtos = getImagesMessage(userManagementBeanPage.getContent());
        Page<UserManagementDto> userManagementDtoPage = BeanMapper.map(userManagementBeanPage,Page.class );
        userManagementDtoPage.setContent(userManagementDtos);
        return userManagementDtoPage;
    }

    /**
     * 添加用户
     * @param userManagementAddDto
     * @param
     * @return
     */
    @Override
    @Transactional
    public Boolean add(UserManagementAddDto userManagementAddDto, UnifyAdmin unifyAdmin) {
        UserManagementBean userManagementBean = BeanMapper.map(userManagementAddDto, UserManagementBean.class);
        userManagementBean = new SetCommonElement().setAdd(userManagementBean,unifyAdmin );
        if(dao.addManagement(userManagementBean) <= 0)
            return false;
        if(userManagementAddDto.getFileSaveDtos() == null || userManagementAddDto.getFileSaveDtos().size() == 0)
            return true;
        return addImages(userManagementAddDto.getFileSaveDtos(), userManagementBean.getId());
    }


    /**
     *  更新用户
     * @param userManagementUpdateDto
     * @param unifyAdmin
     * @return
     */
    @Override
    @Transactional
    public Boolean update(UserManagementUpdateDto userManagementUpdateDto, UnifyAdmin unifyAdmin) {
        UserManagementBean userManagementBean = BeanMapper.map(userManagementUpdateDto, UserManagementBean.class);
        UserManagementBean  beans = dao.detailsManagement(userManagementBean.getId());
        if (beans.getStatus() != UserManagementStatusEnum.NORMAL.getValue())
            return false;
        SetCommonElement setCommonElement = new SetCommonElement() ;
        setCommonElement.setupdate(userManagementBean,unifyAdmin );
        boolean is = dao.updateManagement(userManagementBean)>0;
        if(!is)
            return false;
        return updateImages(userManagementUpdateDto.getFileUpdateDtos(), userManagementUpdateDto.getId());
    }

    /**
     * 通过id删除
     * @param
     * @param userManagementBean
     * @return
     */
    @Override
    public boolean  delete(UserManagementBean userManagementBean ) {
        if(dao.detailsManagement(userManagementBean.getId()).getStatus() != UserManagementStatusEnum.NORMAL.getValue())
            return false;
        UserManagementDto userManagementDto = details(userManagementBean.getId());
        if(userManagementDto == null)
            return true;
        boolean is = dao.deleteManagement(userManagementBean) > 0;
        if(is){
            if(userManagementDto.getFileBeans() == null || userManagementDto.getFileBeans().size() == 0)
                return true;
            List<FileBean> fileBeans = userManagementDto.getFileBeans();
            //图片ID集合
            List<Long> fileIds = new ArrayList<>();
            //图片名称集合
            List<String> filenames = new ArrayList<>();
            for(FileBean fileBean:fileBeans){
                fileIds.add(fileBean.getId());
                filenames.add(fileBean.getName());
            }
            if(!deleteImageMessage(new FileDeleteDto(fileIds,filenames)))
                return false;
            return false;
        }else {
            return false;
        }
    }

    /**
     * 通过id获取数据
     * @param
     * @param id
     * @return
     */
    @Override
    public UserManagementDto details(Long id ) {
        UserManagementBean bean = dao.detailsManagement(id);
        if(bean == null)
            return null;
        return getImagesMessage(bean);
    }


    /***
     * 通过id 更该角色状态
     * @param userManagementBean
     * @param unifyAdmin
     * @return
     */
    @Override
    public UserManagementBean updateStatus(UserManagementBean userManagementBean, UnifyAdmin unifyAdmin) {
        if ( userManagementBean.getStatus() == UserManagementStatusEnum.NORMAL.getValue()){
            dao.updateStatus(userManagementBean);
            return userManagementBean;
        }
        if (userManagementBean.getStatus() == UserManagementStatusEnum.FORBIDDEN.getValue()){
            dao.updateStatus(userManagementBean);
            return userManagementBean;
        }
        if (userManagementBean.getStatus() == UserManagementStatusEnum.VOID.getValue()){
            dao.updateStatus(userManagementBean);
            return userManagementBean;
        }
            return null;
    }

    /**
     * 更改密码
     * @param updatePasswordDto
     * @param unifyAdmin
     * @return
     */
    @Override
    public int updatePassword(UserManagementUpdatePasswordDto updatePasswordDto, UnifyAdmin unifyAdmin) {
        UserManagementBean bean = dao.detailsManagement(updatePasswordDto.getId());
        if (bean .getStatus() != UserManagementStatusEnum.NORMAL.getValue())
            return 0 ;
        if (bean.getPassword() == updatePasswordDto.getOldPassword() || updatePasswordDto.getOldPassword() == null) {
            if (bean.getPassword() != updatePasswordDto.getNewPassword()) {
                return dao.updatePassword(updatePasswordDto);
            }
        }
            return 0 ;
    }

    /**
     * 获取所有数据
     * @param userManagementBean
     * @return
     */
    @Override
    public List<UserManagementBean> selectManagement(UserManagementBean userManagementBean) {
        return dao.selectManagement(userManagementBean);
    }

    /**
     * 判断唯一值
     * @param accountNumber
     * @return
     */
    @Override
    public Boolean judgeUnique(String accountNumber) {
        List<UserManagementBean> managementBean = dao.select(new UserManagementBean(accountNumber) );
        return managementBean != null && managementBean.size() > 0;
    }


    /*----------------------图片操作---------------------------------*/

    @Autowired
    CommonService commonService;

    /**
     * 查询图片
     * @param userManagementBean
     * @return
     */
    public UserManagementDto getImagesMessage(UserManagementBean userManagementBean){
        List<FileBean> fileBeans = commonService.getFileList(new FileSelectDto(correlationTableName,new ArrayList<>(Collections.singleton(userManagementBean.getId()))));
        UserManagementDto userManagementDto = BeanMapper.map(userManagementBean, UserManagementDto.class);
        userManagementDto.setFileBeans(fileBeans);
        return userManagementDto;
    }

    /**
     * 查询多个图片
     * @param userManagementBeans
     * @return
     */
    public List<UserManagementDto> getImagesMessage(List<UserManagementBean> userManagementBeans){
        List<Long> ids = new ArrayList<>();
        for(UserManagementBean userManagementBean:userManagementBeans){
            ids.add(userManagementBean.getId());
        }
        List<FileBean> fileBeans = commonService.getFileList(new FileSelectDto(correlationTableName,ids));
        List<UserManagementDto> userManagementDtos = fileTidyingUtil.getList(fileBeans, userManagementBeans, UserManagementBean.class, UserManagementDto.class);
        return userManagementDtos;
    }
    public boolean addImages(List<FileSaveDto> fileSaveDtos ,Long id){
        for(FileSaveDto fileCorrelationSaveDto:fileSaveDtos){
            fileCorrelationSaveDto.setCorrelationId(id);
            fileCorrelationSaveDto.setCorrelationTableName(correlationTableName);
        }
        return commonService.addFileMessage(fileSaveDtos);
    }


    /**
     * 删除图片
     * @param fileDeleteDto
     * @return
     */
    @Transactional
    public Boolean deleteImageMessage(FileDeleteDto fileDeleteDto){
        return commonService.deleteFileMessage(fileDeleteDto);
    }

    /**
     * 更新图片
     * @param fileUpdateDtos
     * @param id
     * @return
     */
    public Boolean updateImages(List<FileUpdateDto> fileUpdateDtos, Long id){
        if(fileUpdateDtos == null || fileUpdateDtos.size() == 0)
            return true;
        UserManagementBean userManagementBean = new UserManagementBean();
        userManagementBean.setId(id);
        List<FileBean> fileBeans = details(id).getFileBeans();
        for(int i = 0; i < fileBeans.size(); i++){
            for(int j = 0; j < fileUpdateDtos.size(); j++){
                if(fileUpdateDtos.get(j).getId() == fileBeans.get(i).getId()) {
                    fileBeans.remove(i);
                    fileUpdateDtos.remove(j);
                }
            }
        }
        //删除图片
        if(fileBeans != null && fileBeans.size() != 0){
            //图片ID集合
            List<Long> fileIds = new ArrayList<>();

            //图片名称集合
            List<String> filenames = new ArrayList<>();
            for(FileBean fileBean:fileBeans){
                fileIds.add(fileBean.getId());
                filenames.add(fileBean.getName());
            }
            boolean is = deleteImageMessage(new FileDeleteDto(fileIds,filenames));
            if(!is)
                return false;
        }

        //添加图片
        if(fileUpdateDtos != null && fileUpdateDtos.size() != 0){
            boolean is = addImages(BeanMapper.mapList(fileUpdateDtos,FileUpdateDto.class,FileSaveDto.class),fileUpdateDtos.get(0).getCorrelationId());
            if(!is)
                return false;
        }
        return true;
    }
}
