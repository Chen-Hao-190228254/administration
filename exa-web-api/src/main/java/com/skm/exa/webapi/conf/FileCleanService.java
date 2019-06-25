package com.skm.exa.webapi.conf;

import com.skm.exa.common.utils.AliyunOSSClientUtil;
import com.skm.exa.domain.bean.FileBean;
import com.skm.exa.service.biz.CommonService;
import com.skm.exa.webapi.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FileCleanService {

    @Autowired
    CommonService commonService ;

    private static final Logger LOG = LoggerFactory.getLogger(FileCleanService.class);

    public Boolean fileClean(){
        //数据库关联的文件        如果阿里云上有的文件而数据库没有相关信息，则为垃圾文件，进行删除操作
        List<FileBean> fileBeans = commonService.getAllFileMessage();
        //阿里云上的文件
        List<String> filenames = AliyunOSSClientUtil.GetFileAllContent();
        Map<String,String> map = new HashMap<>();
        for(FileBean fileBean:fileBeans){
            for(String filename:filenames){
                String name = filename.split("/")[1];
                if(fileBean.getName().equals(name) || fileBean.getName() == name)
                    map.put(filename,filename);
            }
        }

        List<String> deleteFilenames = new ArrayList<>();
        for (String filename:filenames){
            if(!map.containsKey(filename))
                deleteFilenames.add(filename);
        }
        boolean is = AliyunOSSClientUtil.deleteFile(deleteFilenames);
        LOG.info("数据库文件："+("（数量："+fileBeans.size()+")")+fileBeans.toString());
        LOG.info("阿里云上的文件："+("（数量："+filenames.size()+")")+ filenames.toString());
        LOG.info("数据库文件与阿里云上相同的文件："+("（数量："+map.size()+")")+ map.toString());
        LOG.info("阿里云上需要删除的文件：" +("（数量："+deleteFilenames.size()+")")+ deleteFilenames.toString());
        return is;
    }

}
