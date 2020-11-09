package com.wangwb.service.common.util;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;


/**
 * 	列表数据导出为excel
 * @author wangwb
 *
 */
public class ExportExcelUtil {
	
	/**
	 * 导出EXCEL（.xls）
	 * @param headers   表头title数组
	 * @param fields    对应dataList中字段名数组
	 * @param dataList  需要导出的数据list
	 * @param fileName  导出的文件名
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public static void exportExcelXLS(String[] headers,String[] fields,List<Map<String, Object>> dataList,String fileName,HttpServletRequest request,HttpServletResponse response) throws Exception {
		int rowNum = 1;
		//创建表格
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建sheet
	    HSSFSheet sheet = workbook.createSheet();
	    //在sheet中添加表头行
	    HSSFRow firstRow = sheet.createRow(0);
	    //表头设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        
	    //表头行添加内容
	    for(int i=0;i<headers.length;i++){
	    	//表头行添加单元格
            HSSFCell cell = firstRow.createCell(i);
            HSSFRichTextString headerText = new HSSFRichTextString(headers[i]);
            //单元格添加内容
            cell.setCellValue(headerText);
            cell.setCellStyle(style);
        }
	    //在数据放入对应的列
        for (Map<String, Object> map : dataList) {
        	//添加行
            HSSFRow dataRow = sheet.createRow(rowNum);
            for(int i=0;i<fields.length;i++) {
            	HSSFRichTextString dataText = new HSSFRichTextString(StringUtil.nullToEmpty(map.get(fields[i])));
            	dataRow.createCell(i).setCellValue(dataText);
            }
            rowNum++;
        }
        //设置表格默认宽度
        sheet.setDefaultColumnWidth(25);
        
        String agent = request.getHeader("User-Agent");
        String filenameEncoder = "";
        if (agent.contains("Firefox")) {
			// 火狐浏览器
			filenameEncoder = "=?utf-8?B?"
					+ Base64Util.encode(fileName.getBytes("utf-8")) + "?=";
		} else {
			// 其它浏览器
			filenameEncoder = URLEncoder.encode(fileName, "utf-8");				
		}
        
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename="+filenameEncoder);
        //刷新缓冲
        response.flushBuffer();
        //workbook写入到response的输出流中
        workbook.write(response.getOutputStream());
	}

	


}
