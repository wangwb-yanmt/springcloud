package com.wangwb.service.controller;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangwb.service.common.util.ParamUtil;
import com.wangwb.service.common.util.RedisUtil;
import com.wangwb.service.service.LoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/**
 * 	系统登陆
 * @author wangwb
 *
 */
@RestController
@RequestMapping("/LoginController")
@Api(tags = "登录模块")
public class LoginController {
	
	
	
	@Resource
	private LoginService loginService;
	
	
	/**
	 * 系统登录
	 */
	@ApiOperation(value = "登录系统")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "userName", value = "登录用户名", required = true),
        @ApiImplicitParam(name = "password", value = "登录密码", required = true)
	})
	@RequestMapping("/login")
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//接收前台的参数集
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
		return loginService.login(paramsMap);
	}
	
	
}
