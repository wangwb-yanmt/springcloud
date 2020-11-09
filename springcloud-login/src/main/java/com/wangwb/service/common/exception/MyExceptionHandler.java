package com.wangwb.service.common.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 	全局异常处理
 * @author wangwb@sparknet.com.cn
 *
 */
@ControllerAdvice
public class MyExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public Map<String, Object> handle1(Exception e) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", false);
		resultMap.put("msg", "运行时异常");
		return resultMap;
	}

}
