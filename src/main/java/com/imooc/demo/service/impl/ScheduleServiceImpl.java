package com.imooc.demo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.demo.dao.ScheduleDao;
import com.imooc.demo.entity.Schedule;
import com.imooc.demo.service.ScheduleService;
@Service
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	private ScheduleDao scheduleDao;
	@Override
	public List<Schedule> getScheduleList() {
		
		return scheduleDao.queryscheduleList();
	}

	@Override
	public List<Schedule> getScheduleById(String scheduleId) {
		// TODO Auto-generated method stub
		return scheduleDao.queryScheduleById(scheduleId);
	}
	@Transactional
	@Override
	public boolean addSchedule(Schedule schedule) {
		if (schedule.getScheduleId() != null && !"".equals(schedule.getScheduleId())) {
			// 设置默认值
			schedule.setUploadDate(new Date());
			try {
				int effectedNum = scheduleDao.insertScheule(schedule);
				if (effectedNum > 0) {
					return true;
				} else {
					throw new RuntimeException("添加排期失败!");
				}
			} catch (Exception e) {
				throw new RuntimeException("添加排期信息失败:" + e.toString());
			}
		} else {
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
							return true;
						} else {
							throw new RuntimeException("更新排期信息失败!");
						}
					} catch (Exception e) {
						throw new RuntimeException("更新排期信息失败:" + e.toString());
					}
				} else {
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
					return true;
				} else {
					throw new RuntimeException("删除信息失败!");
				}
			} catch (Exception e) {
				throw new RuntimeException("删除信息失败:" + e.toString());
			}
		} else {
			throw new RuntimeException("id不能为空！");
		}
	}

}
