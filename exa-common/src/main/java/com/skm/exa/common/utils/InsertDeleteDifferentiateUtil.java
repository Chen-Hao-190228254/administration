package com.skm.exa.common.utils;

import com.skm.exa.common.object.FileUpdateObject;
import com.skm.exa.domain.bean.CorrelationLabelBean;
import com.skm.exa.domain.bean.FileBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertDeleteDifferentiateUtil {


    /**
     * 文件添加删除区分工具
     * @param fileBeans 数据库已有文件的信息
     * @param fileUpdateDtos  前端传的文件夹信息
     * @return
     */
    public Map<String,Object> FileInsertDeleteDifferentiateUtil(List<FileBean> fileBeans, List<FileUpdateObject> fileUpdateDtos){
        Map<Long,Long> longMap = new HashMap<>();
        for(FileBean fileBean:fileBeans){
            for(FileUpdateObject fileUpdateDto:fileUpdateDtos){
                if(fileBean.getId() == fileUpdateDto.getId()) {
                    longMap.put(fileBean.getId(),fileBean.getId());
                }
            }
        }
        List<FileBean> f = new ArrayList<>();
        for(FileBean fileBean:fileBeans){
            if(!longMap.containsKey(fileBean.getId()))
                f.add(fileBean);
        }
        fileBeans = f;
        List<FileUpdateObject> updateDtos = new ArrayList<>();
        for(FileUpdateObject fileUpdateDto:fileUpdateDtos){
            if(!longMap.containsKey(fileUpdateDto.getId()))
                updateDtos.add(fileUpdateDto);
        }
        fileUpdateDtos = updateDtos;
        Map<String,Object> map = new HashMap<>();
        map.put("fileBeans",fileBeans);
        map.put("fileUpdateDtos",fileUpdateDtos);
        return map;
    }


    /**
     * 标签添加删除区分工具
     * @param correlationLabelBeans 数据已有标签信息
     * @param labelIds  前端传的标签信息
     * @return
     */
    public Map<String,Object> LabelInsertDeleteDifferentiateUtil(List<CorrelationLabelBean> correlationLabelBeans,List<Long> labelIds){
        Map<Long,Long> and = new HashMap<>();
        for(CorrelationLabelBean correlationLabelBean:correlationLabelBeans){
            for(Long labelId:labelIds){
                if(labelId == correlationLabelBean.getId()){
                    and.put(labelId,labelId);
                }
            }
        }
        if(and != null && and.size() != 0){
            List<CorrelationLabelBean> c = new ArrayList<>();
            for(CorrelationLabelBean correlationLabelBean:correlationLabelBeans){
                if(!and.containsKey(correlationLabelBean.getId())){
                    c.add(correlationLabelBean);
                }
            }
            correlationLabelBeans=c;
            List<Long> longs=new ArrayList<>();
            for (Long id:labelIds){
                if(!and.containsKey(id)){
                    longs.add(id);
                }
            }
            labelIds = longs;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("correlationLabelBeans",correlationLabelBeans);
        map.put("labelIds",labelIds);
        return map;
    }




}
