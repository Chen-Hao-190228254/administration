package com.skm.exa.webapi;

import com.skm.exa.common.context.AppContext;
import com.skm.exa.common.utils.FileAndLabelTidyingUtil;
import com.skm.exa.mybatis.MybatisConfiguration;
import com.skm.exa.webapi.conf.FileCleanService;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author dhc
 * 2019-03-05 12:20
 */
@SpringBootApplication
@EnableScheduling
@Import({MybatisConfiguration.class})
@MapperScan("com.skm.exa.persistence.dao")
@ComponentScan(basePackages = {"com.skm.exa.webapi.conf", "com.skm.exa.webapi.controller", "com.skm.exa.service.biz"})
public class Application {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FileAndLabelTidyingUtil fileTidyingUtil(){
        return new FileAndLabelTidyingUtil();
    }

    @Bean
    public FileCleanService fileCleanService(){
        return new FileCleanService();
    }

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        AppContext.context = SpringApplication.run(Application.class, args);
        String[] activeProfiles = AppContext.context.getEnvironment().getActiveProfiles();
        LOG.info("active profile: {}", Arrays.toString(activeProfiles));
    }



    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        String location = System.getProperty("user.dir") + "/data/tmp";
        File file = new File(location);
        if(!file.exists()){
            file.mkdirs();
        }
        multipartConfigFactory.setLocation(location);
        return multipartConfigFactory.createMultipartConfig();
    }


    @Scheduled(cron="0 30 23 * * 7") //每周星期天23:30执行
    public void executeFileDownLoadTask() {
        LOG.info("文件清理 定时任务：启动");
        boolean fileClean = fileCleanService().fileClean();
        LOG.info("清理结果："+(fileClean? "清理成功":"清理失败"));
    }


}
