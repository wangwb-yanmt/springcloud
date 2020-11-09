package com.wangwb.service.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangwb.service.common.bean.UserBean;
import com.wangwb.service.common.util.ParamUtil;
import com.wangwb.service.common.util.RedisUtil;
import com.wangwb.service.service.TestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.bull.javamelody.MonitoredWithSpring;



/**
 * 	测试controller
 * @author wangwb
 *
 */
@Api(tags = "测试模块")
@RestController
@RequestMapping("/TestController")
@MonitoredWithSpring
public class TestController {
	
	@Resource
	private RedisUtil redisUtil;
	
	@Resource
	private TestService testService;
	
	@ApiOperation(value = "返回列表")
	@PostMapping("/getList")
	public Map<String, Object> getList(HttpServletRequest request) throws Exception {
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
		String userId = request.getHeader("loginId");
		return testService.getList(paramsMap,userId);
		
	} 
	
	@ApiOperation(value = "查询树测试")
	@PostMapping("/queryTree")
	public Map<String, Object> queryTree(HttpServletRequest request) throws Exception {
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
		String userId = request.getHeader("loginId");
		return testService.queryTree(paramsMap,userId);
		
	} 
	
	@ApiOperation(value = "测试更新数据")
	@PostMapping("/updateData")
	public Map<String, Object> updateData(HttpServletRequest request) throws Exception {
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
		String userId = request.getHeader("loginId");
		return testService.updateData(paramsMap,userId);
		
	} 
	
	@ApiOperation(value = "查询左侧菜单")
	@PostMapping("/queryModule")
	public Map<String, Object> queryModule(HttpServletRequest request) throws Exception {
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
		String userId = request.getHeader("loginId");
		return testService.queryModule(paramsMap,userId);
		
	} 
	
	@ApiOperation(value = "查询登录用户信息")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户的唯一标识", required = true),
        @ApiImplicitParam(name = "name", value = "用户名称", required = true)
	})
	@PostMapping("/queryLoginInfo")
	public Map<String, Object> queryLoginInfo(HttpServletRequest request) throws Exception {
		Map<String,Object> paramsMap = ParamUtil.requestParamMap(request);
		String token = request.getHeader("token");
		String userId = redisUtil.get("token:"+token).toString();
		return testService.queryLoginInfo(paramsMap,userId);
	} 
	

}
