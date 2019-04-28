package com.skm.exa.mybatis;

import com.skm.exa.mybatis.intercepts.BatchSaveInterceptor;
import com.skm.exa.mybatis.intercepts.PageInterceptor;
import com.skm.exa.mybatis.intercepts.SetBatchParameterInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dhc
 * 2019-03-07 16:21
 */
@Configuration
public class MybatisConfiguration {
    @Bean
    Interceptor[] interceptors() {
        return new Interceptor[]{
                new PageInterceptor(),
                new BatchSaveInterceptor(),
                new SetBatchParameterInterceptor()
        };
    }
}
