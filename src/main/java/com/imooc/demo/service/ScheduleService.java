package com.imooc.demo.service;

import java.util.List;

import com.imooc.demo.entity.Schedule;

public interface ScheduleService {
	/**
	 * 获取排期列表
	 * 
	 * @return
	 */
	List<Schedule> getScheduleList();

	/**
	 * 通过排期号获取排期信息
	 * 
	 * @param scheduleId
	 * @return
	 */
	List<Schedule> getScheduleById(String scheduleId);

	/**
	 * 增加排期信息
	 * 
	 * @param schedule
	 * @return
	 */
	boolean addSchedule(Schedule schedule);

	/**
	 * 修改排期信息
	 * 
	 * @param schedule
	 * @return
	 */
	boolean modifySchedule(Schedule schedule);

	/**
	 * 删除排期信息
	 * 
	 * @param schedule
	 * @return
	 */
	boolean removeSchedule(Integer id);

	/**
	 * 生成上线文件
	 * 
	 * @param id
	 * @return
	 */
	boolean getUploadFile(String scheduleId);

	/**
	 * 发版
	 * 
	 * @param scheduleId
	 * @return
	 */
	boolean uploadList(String scheduleId);

	List<Schedule> getSchedule();

}
