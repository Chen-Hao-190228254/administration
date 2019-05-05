package com.skm.exa.webapi.conf.security;

import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.service.UnifyAdminService;
import com.skm.exa.common.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * @author dhc
 * 2019-03-05 14:34
 */
@Configurable
@EnableWebSecurity
@EnableRedisHttpSession
//@EnableGlobalMethodSecurity(prePostEnabled=true)  // 支持类和方法的注解权限验证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_URL = "/web/v1/login";
    private static final String LOGOUT_URL = "/web/v1/logout";

    private final AuthenticationHandler authenticationHandler = new AuthenticationHandler();

    private final UserAuthenticationFilterAndProvider authenticationFilterAndProvider;

    @Autowired
    public WebSecurityConfig(UnifyAdminService unifyAdminService) {
        this.authenticationFilterAndProvider = new UserAuthenticationFilterAndProvider(unifyAdminService, LOGIN_URL);
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        authenticationFilterAndProvider.setAuthenticationManager(super.authenticationManager());
        authenticationFilterAndProvider.setAuthenticationSuccessHandler(authenticationHandler);
        authenticationFilterAndProvider.setAuthenticationFailureHandler(authenticationHandler);

        http.cors().and().csrf().disable()
                .sessionManagement().maximumSessions(1).expiredSessionStrategy(event -> ServletUtils.responseJson(event.getResponse(), Result.error(Msg.E40004)))
                .and().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().exceptionHandling().authenticationEntryPoint(authenticationHandler).accessDeniedHandler(authenticationHandler)
                .and().authorizeRequests().anyRequest().authenticated()
//                .antMatchers("/web/v1/admin").hasRole("ADMIN")
                .and().formLogin().loginProcessingUrl(LOGIN_URL).successHandler(authenticationHandler).failureHandler(authenticationHandler)
                .and().addFilterAt(authenticationFilterAndProvider, UsernamePasswordAuthenticationFilter.class)
                .logout().logoutUrl(LOGOUT_URL).logoutSuccessHandler(authenticationHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationFilterAndProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/",
                "/error",
                "favicon.ico",
                "/v2/api-docs",
                "/swagger-**/**",
                "/webjars/**");
    }
}
