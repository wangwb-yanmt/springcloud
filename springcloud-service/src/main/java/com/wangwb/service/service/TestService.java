package com.wangwb.service.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangwb.service.common.bean.ModuleTree;
import com.wangwb.service.common.bean.Page;
import com.wangwb.service.common.util.ModuleTreeUtil;
import com.wangwb.service.common.util.ZtreeKeyTransUtil;
import com.wangwb.service.dao.TestDao;

/**
 * 	测试service
 * @author wangwb
 *
 */
@Service
@Transactional
public class TestService {
	
	@Resource
	private TestDao testDao;

	/**
	 * 	测试查询list
	 * @param paramsMap
	 * @param loginUserId
	 * @return
	 */
	public Map<String, Object> getList(Map<String, Object> paramsMap, String loginUserId) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> bodyMap = new HashMap<String, Object>();
		
		try {
			Page page = testDao.getList(paramsMap);
			bodyMap.put("data", page.getList());
			bodyMap.put("count", page.getCount());
			
			resultMap.put("success", true);
			resultMap.put("code", 0);
			resultMap.put("msg", page.getMsg());
			resultMap.put("body", bodyMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("success", false);
			resultMap.put("code", 1);
			resultMap.put("msg", "查询出现异常");
		}
		return resultMap;
	}

	/**
	 * 	测试更新
	 * @param paramsMap
	 * @param loginUserId
	 * @return
	 */
	public Map<String, Object> updateData(Map<String, Object> paramsMap, String loginUserId) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> bodyMap = new HashMap<String, Object>();
		
		testDao.updateData(paramsMap);
		testDao.updateData2(paramsMap);
		
		resultMap.put("code", 0);
		resultMap.put("msg", "更新成功");
		return resultMap;
	}

	/**
	 * 查询树测试
	 * @param paramsMap
	 * @param loginUserId
	 * @return
	 */
	public Map<String, Object> queryTree(Map<String, Object> paramsMap, String loginUserId) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> bodyMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> dataList = testDao.queryTree();
			dataList = ZtreeKeyTransUtil.ZtreeKeyTrans(dataList);
			
			bodyMap.put("data", dataList);
			
			resultMap.put("code", 0);
			resultMap.put("success", true);
			resultMap.put("msg", "查询成功");
			resultMap.put("body", bodyMap);
		} catch (Exception e) {
			resultMap.put("code", 1);
			resultMap.put("success", false);
			resultMap.put("msg", "查询出现异常");
			throw new RuntimeException();
		}
		return resultMap;
	}

	/**
	 * 查询菜单
	 * @param paramsMap
	 * @param loginUserId
	 * @return
	 */
	public Map<String, Object> queryModule(Map<String, Object> paramsMap, String loginUserId) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String,Object> bodyMap = new HashMap<String, Object>();
		
		try {
			List<Map<String, Object>> dataList = testDao.queryModule();
			List<ModuleTree> module = ModuleTreeUtil.getModuleTree(dataList);
			
			bodyMap.put("data", module);
			
			resultMap.put("code", 0);
			resultMap.put("success", true);
			resultMap.put("msg", "查询成功");
			resultMap.put("body", bodyMap);
		} catch (Exception e) {
			resultMap.put("code", 1);
			resultMap.put("success", false);
			resultMap.put("msg", "查询出现异常");
			throw new RuntimeException();
		}
		return resultMap;
	}
	
	
	public Map<String, Object> queryLoginInfo(Map<String, Object> paramsMap, String loginId) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String, Object>> data = testDao.queryLoginInfo(loginId);
			resultMap.put("code", 0);
			resultMap.put("success", true);
			resultMap.put("msg", "查询成功");
			resultMap.put("data", data);
		} catch (Exception e) {
			resultMap.put("code", 1);
			resultMap.put("success", false);
			resultMap.put("msg", "查询出现异常");
			e.printStackTrace();
		}
		return resultMap;
	}
	
//	public void exportExcel(Map<String, Object> paramsMap,HttpServletRequest request,HttpServletResponse response) throws IOException {
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		list = userDao.getList(paramsMap);
//		String[] headers = {"用户ID","用户名"};
//		String[] fields = {"USER_ID","USERNAME"};
//		String fileName = "用户表.xls";
//		ExportExcelUtil.exportExcelXLS(headers,fields,list,fileName,request,response);
//	}

}
