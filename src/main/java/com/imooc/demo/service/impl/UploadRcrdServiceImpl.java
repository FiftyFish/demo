package com.imooc.demo.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.demo.dao.UploadRcrdDao;
import com.imooc.demo.entity.UploadRcrd;
import com.imooc.demo.service.UploadRcrdService;

@Service
public class UploadRcrdServiceImpl implements UploadRcrdService {
	@Autowired
	private UploadRcrdDao uploadRcrdDao;
	protected static Logger logger = LoggerFactory.getLogger(UploadRcrdServiceImpl.class);

	@Override
	public List<UploadRcrd> getUploadRcrdList() {
		return uploadRcrdDao.queryUploadRcrd();
	}

	@Override
	// 根据排期编号列出发版申请表
	public List<UploadRcrd> getUploadRcrdListByScheduleId(String scheduleId) {
		return uploadRcrdDao.queryUploadRcrdByScheduleId(scheduleId);
	}

	@Transactional
	@Override
	public boolean addUploadRcrd(UploadRcrd uploadRcrd, String usrName) {
		// 设置默认值
		Date input = new Date();
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String inputDate = sDateFormat.format(input);
		// 申请日期、编辑日期
		uploadRcrd.setInptDate(inputDate);
		uploadRcrd.setEditDate(inputDate);
		// rcrdId自动生成规则：插入时间XXX；XXX为三位数，是主键，不能重复
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		String timeStr = sdf.format(currentTime);
		String rcrdId = UUID.randomUUID().toString() + timeStr;
		uploadRcrd.setRcrdId(rcrdId);
		uploadRcrd.setInptOpr(usrName);
		logger.info("InptOpr" + usrName);
		logger.info("InptDate" + uploadRcrd.getInptDate());
		logger.info("EditDate" + uploadRcrd.getEditDate());
		logger.info("RcrdId" + uploadRcrd.getRcrdId());
		try {
			int effectedNum = uploadRcrdDao.insertUploadRcrd(uploadRcrd);
			if (effectedNum > 0) {
				return true;
			} else {
				logger.error("添加发版申请失败！");
				throw new RuntimeException("添加发版申请失败！");
			}
		} catch (Exception e) {
			logger.error("添加发版申请失败：", e.getMessage(), e);
			throw new RuntimeException("添加发版申请失败：" + e.toString());
		}

	}

	@Transactional
	@Override
	public boolean modifyUploadRcrd(UploadRcrd uploadRcrd, String usrName) {
		if (uploadRcrd.getRcrdId() != null) {
			Date eidt = new Date();
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String eidtDate = sDateFormat.format(eidt);
			uploadRcrd.setEditDate(eidtDate);
			logger.info("EditDate" + uploadRcrd.getEditDate());
			logger.info("RcrdId" + uploadRcrd.getRcrdId());
			// 设置默认值
			uploadRcrd.setEditDate(eidtDate);
			uploadRcrd.setInptOpr(usrName);
			try {
				int effectedNum = uploadRcrdDao.updateUploadRcrd(uploadRcrd);
				if (effectedNum > 0) {
					return true;
				} else {
					logger.error("修改发版申请失败！");
					throw new RuntimeException("修改发版申请失败！");
				}
			} catch (Exception e) {
				logger.error("修改发版申请失败：", e.getMessage(), e);
				throw new RuntimeException("修改发版申请失败：" + e.toString());
			}
		} else {
			logger.error("发版申请不能为空！");
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
					logger.error("删除信息失败！");
					throw new RuntimeException("删除信息失败！");
				}
			} catch (Exception e) {
				logger.error("删除信息失败：", e.getMessage(), e);
				throw new RuntimeException("删除信息失败：" + e.toString());
			}
		} else {
			logger.error("排期编号不能为空！");
			throw new RuntimeException("排期编号不能为空！");
		}
	}

	@Transactional
	@Override
	public boolean removeUploadRcrdByRcrdId(String rcrdId) {
		if (!rcrdId.isEmpty()) {
			try {
				// 删除排期信息
				int effectedNum = uploadRcrdDao.deleteUploadRcrdByRcrdId(rcrdId);
				if (effectedNum > 0) {
					return true;
				} else {
					logger.error("删除信息失败！");
					throw new RuntimeException("删除信息失败!");
				}
			} catch (Exception e) {
				logger.error("删除信息失败：", e.getMessage(), e);
				throw new RuntimeException("删除信息失败:" + e.toString());
			}
		} else {
			logger.error("发版编号不能为空！");
			throw new RuntimeException("发版编号不能为空！");
		}

	}

}
