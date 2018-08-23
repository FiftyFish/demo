package com.imooc.demo.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.demo.dao.UploadRcrdDao;
import com.imooc.demo.entity.UploadRcrd;
import com.imooc.demo.service.UploadRcrdService;
import com.imooc.demo.util.ExcelExport;

@Service
public class UploadRcrdServiceImpl implements UploadRcrdService{
	@Autowired
	private UploadRcrdDao uploadRcrdDao;
	@Override
	public List<UploadRcrd> getUploadRcrdList() {
		
		return uploadRcrdDao.queryUploadRcrd();
	}

	@Override
	//根据排期编号列出发版申请表
	public List<UploadRcrd> getUploadRcrdByScheduleId(String scheduleId) {
		return uploadRcrdDao.queryUploadRcrdByScheduleId(scheduleId);
	}
	
	@Transactional
	@Override
	public boolean addUploadRcrd(UploadRcrd uploadRcrd, String usrName) {
			// 设置默认值
			Date input = new Date();
			SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
			String inputDate = sDateFormat.format(input);
			//申请日期、编辑日期
			uploadRcrd.setInptDate(inputDate);
			uploadRcrd.setEditDate(inputDate);
			//rcrdId自动生成规则：插入时间XXX；XXX为三位数，是主键，不能重复
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			String timeStr = sdf.format(currentTime);
			String rcrdId=UUID.randomUUID().toString()+timeStr;
			uploadRcrd.setRcrdId(rcrdId);
			uploadRcrd.setInptOpr(usrName);
			try {
				int effectedNum = uploadRcrdDao.insertUploadRcrd(uploadRcrd);
				if (effectedNum > 0) {
					return true;
				} else {
					throw new RuntimeException("添加发版申请失败!");
				}
			} catch (Exception e) {
				throw new RuntimeException("添加发版申请失败:" + e.toString());
			}
			

	}
	
	
	@Transactional
	@Override
	public boolean modifyUploadRcrd(UploadRcrd uploadRcrd) {
				if (uploadRcrd.getRcrdId() != null ) {
					Date eidt = new Date();
					SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd");
					String eidtDate = sDateFormat.format(eidt);

					// 设置默认值
					uploadRcrd.setEditDate(eidtDate);
					try {
						int effectedNum = uploadRcrdDao.updateUploadRcrd(uploadRcrd);
						if (effectedNum > 0) {
							return true;
						} else {
							throw new RuntimeException("添加发版申请失败!");
						}
					} catch (Exception e) {
						throw new RuntimeException("添加发版申请失败:" + e.toString());
					}
				} else {
					throw new RuntimeException("发版申请不能为空！");
				}
	}
	
	
	@Transactional
	@Override
	public boolean removeUploadRcrdByScheduleId(String scheduleId) {
		if (!scheduleId.isEmpty()) {
			try {
				// 删除排期信息
				int effectedNum = uploadRcrdDao.deleteUploadRcrdByScheduleId(scheduleId);
				if (effectedNum > 0) {
					return true;
				} else {
					throw new RuntimeException("删除信息失败!");
				}
			} catch (Exception e) {
				throw new RuntimeException("删除信息失败:" + e.toString());
			}
		} else {
			throw new RuntimeException("排期编号不能为空！");
		}
	}

	@Transactional
	@Override
	public boolean removeUploadRcrdByRcrdId(String rcrdId) {
			try {
				// 删除排期信息
				int effectedNum = uploadRcrdDao.deleteUploadRcrdByRcrdId(rcrdId);
				if (effectedNum > 0) {
					return true;
				} else {
					throw new RuntimeException("删除信息失败!");
				}
			} catch (Exception e) {
				throw new RuntimeException("删除信息失败:" + e.toString());
			}
	}

	/**
	 * 变更影响分析生成
	 */
	@Override
	public boolean getUploadRcrdForExcel(List<UploadRcrd> uploadRcrdList) {
		try {
			if (uploadRcrdList.size() <= 0 )throw new RuntimeException("变更影响分析生成失败：上传列表为空");
			ExcelExport<UploadRcrd> excel = new ExcelExport<UploadRcrd>();
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
			excel.exportExcel(headerName, headerId, uploadRcrdList);
		} catch (Exception e) {
			throw new RuntimeException("变更影响分析生成失败:" + e.toString());
		}
		
		return true;
		
		
		
	}

	/**
	 * file.list生成
	 */
	@Override
	public boolean getUploadRcrdForFileList(List<UploadRcrd> uploadRcrdList) {
		try {
			if (uploadRcrdList.size() <= 0 )throw new RuntimeException("file.list生成失败：上传列表为空");
			ExcelExport<UploadRcrd> excel = new ExcelExport<UploadRcrd>();
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
			excel.exportExcel(headerName, headerId, uploadRcrdList);
		} catch (Exception e) {
			throw new RuntimeException("变更影响分析生成失败:" + e.toString());
		}
		
		return true;
	}

	
	
}
