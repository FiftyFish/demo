package com.imooc.demo.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.imooc.demo.entity.Schedule;
import com.imooc.demo.service.ScheduleService;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
	@Autowired
	private ScheduleService scheduleService;
	
	
	/**
	 * 获取所有排期信息
	 * @return
	 */
	@RequestMapping(value = "/listSchedule", method = RequestMethod.GET)
	private Map<String, Object> listSchedule() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Schedule> list = new ArrayList<Schedule>();
		// 获取排期列表
		list = scheduleService.getScheduleList();
		modelMap.put("scheduleList", list);
		return modelMap;
	}

	/**
	 * 通过排期编号获取排期信息
	 * @return
	 */
	@RequestMapping(value = "/getScheduleById", method = RequestMethod.GET)
	private Map<String, Object> getScheduleById(String scheduleId){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Schedule> list = new ArrayList<Schedule>();
		list = scheduleService.getScheduleById(scheduleId);
		modelMap.put("uploadRcrd", list);
		return modelMap;
	}

	/**
	 *  增加排期信息
	 * @param schedule
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/addSchedule", method = RequestMethod.POST)
	private Map<String, Object> addSchedule(Schedule schedule)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 添加区域信息
		modelMap.put("success", scheduleService.addSchedule(schedule));
		return modelMap;
	}

	/**
	 * 修改排期信息
	 * @param schedule
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/modifySchedule", method = RequestMethod.POST)
	private Map<String, Object> modifySchedule(Schedule schedule)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 修改发版申请信息
		modelMap.put("success", scheduleService.modifySchedule(schedule));
		return modelMap;
	}

	/**
	 * 按排期编号删除发版申请
	 * @param scheduleId
	 * @return
	 */
	@RequestMapping(value = "/removeSchedule", method = RequestMethod.GET)
	private Map<String, Object> removeSchedule(Integer id) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 已经发版的申请按照排期编号删除
		modelMap.put("success", scheduleService.removeSchedule(id));
		return modelMap;
	}

}
