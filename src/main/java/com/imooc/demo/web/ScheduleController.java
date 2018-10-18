package com.imooc.demo.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
	protected static Logger logger = LoggerFactory.getLogger(ScheduleController.class);

	/**
	 * 获取所有排期信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listSchedule", method = RequestMethod.POST)
	private Map<String, Object> listSchedule() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Schedule> list = new ArrayList<Schedule>();
		// 获取排期列表
		list = scheduleService.getScheduleList();
		logger.info("获取到list的size：" + String.valueOf(list.size()));
		modelMap.put("scheduleList", list);
		return modelMap;
	}

	/**
	 * 通过排期编号获取排期信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getScheduleById", method = RequestMethod.POST)
	private Map<String, Object> getScheduleById(@RequestBody String scheduleId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Schedule> list = new ArrayList<Schedule>();
		list = scheduleService.getScheduleById(scheduleId);
		logger.info("scheduleId：" + scheduleId);
		logger.info("获取到list的size：" + String.valueOf(list.size()));
		modelMap.put("scheduleList", list);
		return modelMap;
	}

	/**
	 * 增加排期信息
	 * 
	 * @param schedule
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/addSchedule", method = RequestMethod.POST)
	private Map<String, Object> addSchedule(@RequestBody Schedule schedule)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 添加排期信息
		schedule.getScheduleId();
		logger.info(schedule.getScheduleId());
		logger.info(String.valueOf(schedule.getUploadDate()));
		logger.info(String.valueOf(schedule.getIsUpload()));
		modelMap.put("success", scheduleService.addSchedule(schedule));
		return modelMap;
	}

	/**
	 * 修改排期信息
	 * 
	 * @param schedule
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/modifySchedule", method = RequestMethod.POST)
	private Map<String, Object> modifySchedule(@RequestBody Schedule schedule)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 修改发版申请信息
		logger.info("IsUpload：" + schedule.getIsUpload());
		logger.info("UploadDate" + String.valueOf(schedule.getUploadDate()));
		modelMap.put("success", scheduleService.modifySchedule(schedule));
		return modelMap;
	}

	/**
	 * 按排期ID删除发版申请
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/removeSchedule", method = RequestMethod.POST)
	private Map<String, Object> removeSchedule(@RequestBody Integer id) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 已经发版的申请按照排期编号删除
		logger.info("Id：" + id);
		modelMap.put("success", scheduleService.removeSchedule(id));
		return modelMap;
	}

	/**
	 * 按排期编号生成上线文件
	 * 
	 * @param scheduleId
	 * @return
	 */
	@RequestMapping(value = "/getUploadFile", method = RequestMethod.POST)
	private Map<String, Object> getUploadFile(@RequestBody String scheduleId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		logger.info("scheduleId：" + scheduleId);
		modelMap.put("success", scheduleService.getUploadFile(scheduleId));
		return modelMap;
	}

	/**
	 * 发版
	 * 
	 * @param scheduleId
	 * @return
	 */
	@RequestMapping(value = "/uploadList", method = RequestMethod.POST)
	private Map<String, Object> uploadList(@RequestBody String scheduleId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		logger.info("scheduleId：" + scheduleId);
		modelMap.put("success", scheduleService.uploadList(scheduleId));
		return modelMap;
	}

}
