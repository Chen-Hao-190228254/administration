package com.skm.exa.webapi.controller;

import com.skm.exa.common.enums.Msg;
import com.skm.exa.common.exception.BizException;
import com.skm.exa.common.object.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author dhc
 * 2019-03-05 15:42
 */
@Api(tags = "基础接口", description = "基本测试接口")
@RestController
@RequestMapping("/")
public class IndexController {

    // 首页
    @GetMapping
    @ApiOperation(value = "成功访问标志", notes = "用户测试是否成功访问应用程序")
    public Result index() {
        return Result.success();
    }

    @GetMapping("/web/v1/error")
    @ApiOperation(value = "错误输出", notes = "测试异常的输出格式")
    public void testError() {
        throw new BizException(Msg.E40000);
    }

    // 个人信息
    @GetMapping("/web/v1/me")
    @ApiOperation(value = "获取登录信息", notes = "用于获取当前登录用户的基本信息")
    public Result me(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (principal instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
            return Result.success(token.getPrincipal());
        }
        return Result.success(principal);
    }
}
