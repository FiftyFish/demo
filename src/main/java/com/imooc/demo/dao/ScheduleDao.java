package com.imooc.demo.dao;

import java.util.List;

import com.imooc.demo.entity.Schedule;

public interface ScheduleDao {

	/**
	 * 列出排期列表
	 * 
	 * @return scheduleList
	 */
	List<Schedule> queryscheduleList();

	/**
	 * 根据用排期号查询排期信息
	 * 
	 * @return schedule
	 */
	List<Schedule> queryScheduleById(String scheduleId);

	/**
	 * 插入排期
	 * 
	 * @param schedule
	 * @return
	 */
	int insertScheule(Schedule schedule);

	/**
	 * 更新排期信息
	 * 
	 * @param schedule
	 * @return
	 */
	int updateSchedule(Schedule schedule);

	/**
	 * 删除排期
	 * 
	 * @param scheduleId
	 * @return
	 */
	int deleteSchedule(Integer id);

	List<Schedule> querySchedule();

}
