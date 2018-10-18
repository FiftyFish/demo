package com.imooc.demo.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractExcel<T> {
	protected static Logger logger = LoggerFactory.getLogger(ExtractExcel.class);

	public void exportExcel(List<T> dtoList) {
		List<String> headerName = new ArrayList<String>();
		headerName.add("申请人");
		headerName.add("申请时间");
		headerName.add("所属系统");
		headerName.add("变更文件");
		headerName.add("变更形式");
		headerName.add("测试案例");
		headerName.add("测试人");
		headerName.add("排期编号");
		List<String> headerId = new ArrayList<String>();
		headerId.add("inptOpr");
		headerId.add("inptDate");
		headerId.add("belongSys");
		headerId.add("changeContent");
		headerId.add("changeType");
		headerId.add("testCase");
		headerId.add("testerName");
		headerId.add("scheduleId");

		/* （一）表头--标题栏 */
		Map<Integer, String> headersNameMap = new HashMap<>();
		int key = 0;
		for (int i = 0; i < headerName.size(); i++) {
			if (!headerName.get(i).equals(null)) {
				headersNameMap.put(key, headerName.get(i));
				key++;
			}
		}
		/* （二）字段 */
		Map<Integer, String> titleFieldMap = new HashMap<>();
		int value = 0;
		for (int i = 0; i < headerId.size(); i++) {
			if (!headerId.get(i).equals(null)) {
				titleFieldMap.put(value, headerId.get(i));
				value++;
			}
		}
		/* （三）声明一个工作薄：包括构建工作簿、表格、样式 */
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = wb.createCellStyle();
		HSSFRow row = sheet.createRow(0);

		style.setAlignment(HorizontalAlignment.CENTER);
		HSSFCell cell;
		Collection c = headersNameMap.values();// 拿到表格所有标题的value的集合
		Iterator<String> it = c.iterator();// 表格标题的迭代器
		/* （四）导出数据：包括导出标题栏以及内容栏 */
		// 根据选择的字段生成表头
		short size = 0;
		while (it.hasNext()) {
			cell = row.createCell(size);
			cell.setCellValue(it.next().toString());
			cell.setCellStyle(style);
			size++;
		}
		// 表格标题一行的字段的集合
		Collection zdC = titleFieldMap.values();
		Iterator<T> labIt = dtoList.iterator();// 总记录的迭代器
		int zdRow = 0;// 列序号
		while (labIt.hasNext()) {// 记录的迭代器，遍历总记录
			int zdCell = 0;
			zdRow++;
			row = sheet.createRow(zdRow);
			T l = (T) labIt.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = l.getClass().getDeclaredFields();// 获得JavaBean全部属性
			for (short i = 0; i < fields.length; i++) {// 遍历属性，比对
				Field field = fields[i];
				String fieldName = field.getName();// 属性名
				Iterator<String> zdIt = zdC.iterator();// 一条字段的集合的迭代器
				while (zdIt.hasNext()) {// 遍历要导出的字段集合
					if (zdIt.next().equals(fieldName)) {// 比对JavaBean的属性名，一致就写入，不一致就丢弃
						String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);// 拿到属性的get方法
						Class tCls = l.getClass();// 拿到JavaBean对象
						try {
							Method getMethod = tCls.getMethod(getMethodName, new Class[] {});// 通过JavaBean对象拿到该属性的get方法，从而进行操控
							Object val = getMethod.invoke(l, new Object[] {});// 操控该对象属性的get方法，从而拿到属性值
							String textVal = null;
							if (val != null) {
								textVal = String.valueOf(val);// 转化成String
							} else {
								textVal = null;
							}
							row.createCell((short) zdCell).setCellValue(textVal);// 写进excel对象
							zdCell++;
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			FileOutputStream exportXls = new FileOutputStream("/elk/fanjia/uploadfile/uploadRcrd.xls");
			FileOutputStream exportXls = new FileOutputStream("F:/uploadRcrd.xls");
			logger.info("Excel导出位置：" + "/elk/fanjia/uploadfile/uploadRcrd.xls");
			wb.write(exportXls);
			exportXls.close();
			logger.info("变更影响分析导出成功！");
			System.out.println("变更影响分析导出成功！");
		} catch (FileNotFoundException e) {
			logger.error("变更影响分析导出失败！", e.getMessage(), e);
			System.out.println("变更影响分析导出失败！");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("变更影响分析导出失败！", e.getMessage(), e);
			System.out.println("变更影响分析导出失败！");
			e.printStackTrace();
		}
	}

}
