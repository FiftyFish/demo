package com.imooc.demo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.demo.dao.ScheduleDao;
import com.imooc.demo.dao.UploadRcrdDao;
import com.imooc.demo.entity.Schedule;
import com.imooc.demo.entity.UploadRcrd;
import com.imooc.demo.service.ScheduleService;
import com.imooc.demo.util.ExtractExcel;
import com.imooc.demo.util.ExtractFileList;
import com.imooc.demo.util.ExtractFiles;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	private ScheduleDao scheduleDao;
	@Autowired
	private UploadRcrdDao uploadRcrdDao;
	protected static Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

	@Override
	public List<Schedule> getScheduleList() {
		return scheduleDao.queryscheduleList();
	}

	@Override
	public List<Schedule> getScheduleById(String scheduleId) {
		return scheduleDao.queryScheduleById(scheduleId);
	}

	/**
	 * 增加排期
	 */
	@Transactional
	@Override
	public boolean addSchedule(Schedule schedule) {
		if (schedule.getScheduleId() != null && !"".equals(schedule.getScheduleId())) {
			try {
				int effectedNum = scheduleDao.insertScheule(schedule);
				if (effectedNum > 0) {
					logger.info("添加排期成功！");
					return true;
				} else {
					logger.error("添加排期失败！");
					throw new RuntimeException("添加排期失败!");
				}
			} catch (Exception e) {
				logger.error("添加排期失败：", e.getMessage());
				throw new RuntimeException("添加排期失败：" + e.toString());
			}
		} else {
			logger.error("排期信息不能为空！");
			throw new RuntimeException("排期信息不能为空！");
		}
	}

	@Transactional
	@Override
	public boolean modifySchedule(Schedule schedule) {
		// 空值判断，主要是scheduleId不为空
		if (schedule.getScheduleId() != null && !"".equals(schedule.getScheduleId())) {
			// 设置默认值

			try {
				// 更新区域信息
				int effectedNum = scheduleDao.updateSchedule(schedule);
				if (effectedNum > 0) {
					logger.info("修改排期信息成功！");
					return true;
				} else {
					logger.error("修改排期信息失败！");
					throw new RuntimeException("修改排期信息失败!");
				}
			} catch (Exception e) {
				logger.error("修改排期信息失败！", e.getMessage(), e);
				throw new RuntimeException("修改排期信息失败:" + e.toString());
			}
		} else {
			logger.error("排期信息不能为空！");
			throw new RuntimeException("排期信息不能为空！");
		}
	}

	@Transactional
	@Override
	public boolean removeSchedule(Integer id) {
		if (id != 0) {
			try {
				// 删除排期信息
				int effectedNum = scheduleDao.deleteSchedule(id);
				if (effectedNum > 0) {
					logger.info("删除排期成功！");
					return true;
				} else {
					logger.error("删除排期失败！");
					throw new RuntimeException("删除信息失败!");
				}
			} catch (Exception e) {
				logger.error("删除排期失败！", e.getMessage());
				throw new RuntimeException("删除信息失败！");
			}
		} else {
			logger.error("id不能为空！");
			throw new RuntimeException("id不能为空！");
		}
	}
	/**
	 * 生成上线包
	 */
	@Transactional
	@Override
	public boolean getUploadFile(String scheduleId) {
		if (!scheduleId.equals(null)) {
			try {
				List<UploadRcrd> rcrdList = uploadRcrdDao.queryUploadRcrdByScheduleId(scheduleId);
				if (rcrdList.size() > 0) {
					logger.info("此排期内上线文件不为空！");
					try {
						ExtractFileList<UploadRcrd> fileList = new ExtractFileList<UploadRcrd>();
						fileList.rcrdListParse(rcrdList);
					} catch (Exception e) {
						logger.info("fileList生成失败！", e.getMessage());
						throw new RuntimeException("fileList生成失败！");
					}
					try {
						ExtractExcel<UploadRcrd> excel = new ExtractExcel<UploadRcrd>();
						excel.exportExcel(rcrdList);
					} catch (Exception e1) {
						logger.info("excel生成失败！", e1.getMessage());
						throw new RuntimeException("excel生成失败！");
					}
					try {
						ExtractFiles files = new ExtractFiles();
						files.extractFiles(rcrdList);
					} catch (Exception e) {
						logger.info("增量包解析出错！", e.getMessage());
						throw new RuntimeException("增量包解析出错！");
					}
					try {
						ExtractFiles.downloadFile();
					} catch (Exception e) {
						logger.info("上线包下载失败！", e.getMessage());
						throw new RuntimeException("上线包下载失败！");
					}
					return true;
				} else {
					logger.error("此排期内没有上线文件！请核对排期编号！");
					throw new RuntimeException("此排期内没有上线文件！请核对排期编号！");
				}
			} catch (Exception e) {
				logger.error("上线文件生成失败！", e.getMessage(), e);
				throw new RuntimeException("上线文件生成失败！" + e.toString());
			}
		} else {
			logger.error("排期编号不能为空！");
			throw new RuntimeException("排期编号不能为空！");
		}
	}
	
	/**
	 * 发版
	 */
	@Transactional
	@Override
	public boolean uploadList(String scheduleId) {
		if (!scheduleId.equals(null)) {
			try {
				List<UploadRcrd> uploadRcrdList = uploadRcrdDao.queryUploadRcrdByScheduleId(scheduleId);
				if (uploadRcrdList.size() > 0) {
					logger.info("发版列表不为空！");
					try {
						ExtractFileList<UploadRcrd> fileList = new ExtractFileList<UploadRcrd>();
						fileList.rcrdListParse(uploadRcrdList);
					} catch (Exception e) {
						logger.info("fileList生成失败！", e.getMessage());
						throw new RuntimeException("fileList生成失败！");
					}
					try {
						ExtractFiles files = new ExtractFiles();
						files.extractFiles(uploadRcrdList);
					} catch (Exception e) {
						logger.info("增量包解析出错！", e.getMessage());
						throw new RuntimeException("增量包解析出错！");
					}
					return true;
				} else {
					logger.error("发版列表为空！");
					throw new RuntimeException("发版列表为空！");
				}
			} catch (Exception e) {
				logger.error("发版失败！", e.getMessage(), e);
				throw new RuntimeException("发版失败！" + e.toString());
			}
		} else {
			logger.error("排期编号不能为空！");
			throw new RuntimeException("排期编号不能为空！");
		}
	}

}
