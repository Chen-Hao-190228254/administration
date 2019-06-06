package com.skm.exa.common.utils;

import com.skm.exa.common.service.FileBeanListDto;
import com.skm.exa.domain.BaseBean;
import com.skm.exa.domain.bean.FileBean;

import java.util.*;

public class FileTidyingUtil {


    /**
     * 文件整合
     * @param fileBeans 文件集合
     * @param bean       父类bean
     * @param returnClass 需要返回的类型.class
     * @param <S> 传入类型
     * @param <D> 返回类型
     * @return
     */
    public <S extends BaseBean,D extends FileBeanListDto> D get(List<FileBean> fileBeans,S bean, Class<D> returnClass){
        if(bean == null)
            return null;
        D r = BeanMapper.map(bean,returnClass);
        if(fileBeans != null || fileBeans.size() != 0)
            r.setFile(fileBeans);
        return r;
    }


    /**
     * 文件集合整合
     * @param fileBeans 文件集合
     * @param beans     父类bean集合
     * @param beanClass 父类类型
     * @param returnClass 返回类型.class
     * @param <S> 传入类型
     * @param <D> 返回类型
     * @return
     */
    public <S extends BaseBean,D extends FileBeanListDto> List<D> getList(List<FileBean> fileBeans,List<S> beans, Class<S> beanClass, Class<D> returnClass){
        if(beans == null || beans.size() == 0)
            return null;
        List<Long> correlationIds = new ArrayList<>();
        Map<Long, D> mapReturnClass = new HashMap<>();
        for(S base:beans){
            correlationIds.add(base.getId());
            mapReturnClass.put(base.getId(), BeanMapper.map(base,returnClass));
        }
        if(fileBeans == null || fileBeans.size() == 0)
            return BeanMapper.mapList(beans, beanClass,returnClass);
        Map<Long,List<FileBean>> mapFile = new HashMap<>();
        for(FileBean fileBean:fileBeans){
            if(mapFile.containsKey(fileBean.getCorrelationId())){
                mapFile.get(fileBean.getCorrelationId()).add(fileBean);
            }else {
                List<FileBean> imageBeanList = new ArrayList<>();
                imageBeanList.add(fileBean);
                mapFile.put(fileBean.getCorrelationId(),imageBeanList);
            }
        }
        for(Long key:mapReturnClass.keySet()){
            if(mapFile.containsKey(key)){
                mapReturnClass.get(key).setFile(mapFile.get(key));
            }
        }
        List<D> r = new ArrayList<>();
        for(Long key:mapReturnClass.keySet()){
            r.add(mapReturnClass.get(key));
        }
        return r;
    }


}
