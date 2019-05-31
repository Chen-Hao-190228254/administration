package com.skm.exa.webapi;

import com.skm.exa.common.context.AppContext;
import com.skm.exa.mybatis.MybatisConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

/**
 * @author dhc
 * 2019-03-05 12:20
 */
@SpringBootApplication
@Import({MybatisConfiguration.class})
@MapperScan("com.skm.exa.persistence.dao")
@ComponentScan(basePackages = {"com.skm.exa.webapi.conf", "com.skm.exa.webapi.controller", "com.skm.exa.service.biz"})
public class Application {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        AppContext.context = SpringApplication.run(Application.class, args);
        String[] activeProfiles = AppContext.context.getEnvironment().getActiveProfiles();
        LOG.info("active profile: {}", Arrays.toString(activeProfiles));
    }
}
