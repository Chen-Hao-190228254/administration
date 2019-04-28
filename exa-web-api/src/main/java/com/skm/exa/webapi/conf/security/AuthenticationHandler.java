package com.skm.exa.webapi.conf.security;

import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.object.Result;
import com.skm.exa.common.utils.ServletUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author dhc
 * 2019-03-05 16:09
 */
public class AuthenticationHandler implements AuthenticationEntryPoint, AccessDeniedHandler, AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {
    private static final int ONE_MONTH = 0x278d00;

    // 需要登录
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ServletUtils.responseJson(response, Result.error(Msg.E40001));
    }

    // 没有权限
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ServletUtils.responseJson(response, Result.error(Msg.E40003));
    }

    // 登录失败
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ServletUtils.responseJson(response, Result.error(Msg.E40002));
    }

    // 登录成功
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        // 设置Session时效为30天
        session.setMaxInactiveInterval(ONE_MONTH);
        response.setHeader("X-Auth-Token", session.getId());

        Object object = authentication.getPrincipal();
        ServletUtils.responseJson(response, Result.success(object));
    }

    // 登出成功
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ServletUtils.responseJson(response, Result.success());
    }
}
