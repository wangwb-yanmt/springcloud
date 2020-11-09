package com.wangwb.service.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangwb.service.common.util.RedisUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 	系统登出
 * @author wangwb
 *
 */
@RestController
@RequestMapping("/LoginOutController")
public class LoginOutController {
	
	@Autowired
	private RedisUtil redisUtil;

	/**
	 *	注销/退出系统
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("退出登录")
	@RequestMapping("/loginOut")
	public Map<String,Object> loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> resultMap = new HashMap<String, Object>();
    	try{
    		String token = request.getHeader("token");
    		//删除该token
    		redisUtil.del("token:"+token);
    		resultMap.put("success", true);
    		resultMap.put("msg", "退出登录成功");
		}catch(Exception e){
			resultMap.put("success", false);
    		resultMap.put("msg", "退出登录失败");
			throw new RuntimeException(e.getMessage(),e);
		}
    	return resultMap;
	}
	
	
	
	
}
