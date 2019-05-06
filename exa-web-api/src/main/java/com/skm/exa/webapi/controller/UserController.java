package com.skm.exa.webapi.controller;

import com.skm.exa.common.object.Result;
import com.skm.exa.mybatis.Page;
import com.skm.exa.mybatis.PageParam;
import com.skm.exa.mybatis.SearchConditionGroup;
import com.skm.exa.mybatis.Sort;
import com.skm.exa.persistence.dto.UserDto;
import com.skm.exa.persistence.qo.UserQO;
import com.skm.exa.service.biz.UserService;
import com.skm.exa.webapi.BaseController;
import com.skm.exa.webapi.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dhc
 * 2019-03-07 15:51
 */
@Api(tags = "用户操作", description = "用户的增删改查操作")
@RestController
@RequestMapping("/web/v1/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    // 测试分页    Operation 操作 ，Implicit 隐式 ，  Param 参数
    @PostMapping("/page")
    @ApiOperation(value = "分页获取用户信息", notes = "分页获取用户信息列表")
    @ApiImplicitParam(name = "param", value = "分页查询参数对象", required = true, dataType = "PageParam")
    public Result<Page<UserVo>> getUserPage(@RequestBody PageParam<UserQO> param) {
        UserQO qo = param.getCondition();
        qo.addSearchConditionGroup(SearchConditionGroup    //columns 列
                .buildMultiColumnsSearch("dh", "u.username", "u.realname"));
               /** buildMultiColumnsSearch  构建多列搜索*/
        if (!qo.hasSort()) {  //hasSort 已有排序
            qo.addOrder("u.entry_dt", Sort.DESC); //addOrder 添加排序
        }
        Page<UserDto> page = userService.selectDtoPage(param);
        Page<UserVo> voPage = page.map(UserDto.class, UserVo.class);
        return Result.success(voPage);
    }
}
