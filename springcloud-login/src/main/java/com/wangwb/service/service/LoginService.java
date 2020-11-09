package com.wangwb.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangwb.service.common.bean.UserBean;
import com.wangwb.service.common.util.JWTUtil;
import com.wangwb.service.common.util.RedisUtil;
import com.wangwb.service.common.util.StringUtil;
import com.wangwb.service.common.util.UUIDUtil;
import com.wangwb.service.dao.LoginDao;

@Service
public class LoginService {
	
	@Resource
	private LoginDao loginDao;
	
	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 	系统登录
	 * @param paramsMap
	 * @param session
	 * @return
	 */
	public Map<String, Object> login(Map<String, Object> paramsMap) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
    	
    	String userName = StringUtil.nullToEmpty( paramsMap.get( "userName" ) );//用户名
    	String password = StringUtil.nullToEmpty( paramsMap.get( "password" ) );//密码
    	try{
    		List<Map<String, Object>> user = loginDao.queryUser(userName);
        	if(user.size() == 0){
        		resultMap.put("msg", "登陆失败!用户不存在，请检查是否正确");
        		resultMap.put("success",false);
        	}else{
        		if(password.equals(StringUtil.nullToEmpty(user.get(0).get("REG_NO")))){
        			String userId = StringUtil.nullToEmpty(user.get(0).get("ID"));//用户ID
//        			String token = JWTUtil.createToken(userId);
        			String token = UUIDUtil.getNextValue();
        			//用户ID存入redis,key值为token
        			redisUtil.set("token:"+token, userId,7200);
        			resultMap.put("success", true);
        			resultMap.put("msg", "登陆成功");
        			resultMap.put("token", token);
        		}else{
        			resultMap.put("success",false);
        			resultMap.put("msg", "登陆失败!密码错误，请检查密码是否正确");
        		}
        	}
    	}catch(Exception e){
        	resultMap.put("success",false);
        	resultMap.put("msg", "请求数据发生异常");
        	throw new RuntimeException(e.getMessage(),e);
        }
    	return resultMap;
	}

	
	
}
