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
import com.imooc.demo.entity.UploadRcrd;
import com.imooc.demo.service.UploadRcrdService;

@RestController
@RequestMapping("/uploadRcrd")
public class UploadRcrdController {
	@Autowired
	private UploadRcrdService uploadrcrdService;
	protected static Logger logger = LoggerFactory.getLogger(UploadRcrdController.class);

	/**
	 * 获取所有发版信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listUploadRcrd", method = RequestMethod.POST)
	private Map<String, Object> listUploadRcrd() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<UploadRcrd> list = new ArrayList<UploadRcrd>();
		// 获取发版列表
		list = uploadrcrdService.getUploadRcrdList();
		logger.info("获取到list的size：" + String.valueOf(list.size()));
		modelMap.put("uploadRcrdList", list);
		return modelMap;
	}

	/**
	 * 通过排期编号获取发版信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getUploadRcrdByScheduleId", method = RequestMethod.POST)
	private Map<String, Object> getUploadRcrdByScheduleId(@RequestBody String scheduleId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<UploadRcrd> list = new ArrayList<UploadRcrd>();
		list = uploadrcrdService.getUploadRcrdListByScheduleId(scheduleId);
		logger.info("排期编号：" + scheduleId);
		logger.info("获取到list的size：" + String.valueOf(list.size()));
		modelMap.put("uploadRcrdList", list);
		return modelMap;
	}

	/**
	 * 插入发版申请
	 * 
	 * @param uploadRcrd
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/addUploadRcrd", method = RequestMethod.POST)
	public Map<String, Object> addUploadRcrd(@RequestBody UploadRcrd uploadRcrd, String usrName)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 添加发版申请
		logger.info("ChangeContent" + uploadRcrd.getChangeContent());
		logger.info("ChangeType" + uploadRcrd.getChangeType());
		modelMap.put("success", uploadrcrdService.addUploadRcrd(uploadRcrd, usrName));
		return modelMap;
	}

	/**
	 * 修改发版申请信息
	 * 
	 * @param uploadRcrd
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/modifyUploadRcrd", method = RequestMethod.POST)
	private Map<String, Object> modifyUploadRcrd(@RequestBody UploadRcrd uploadRcrd, String usrName)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 修改发版申请信息
		logger.info("ChangeContent" + uploadRcrd.getChangeContent());
		logger.info("ChangeType" + uploadRcrd.getChangeType());
		modelMap.put("success", uploadrcrdService.modifyUploadRcrd(uploadRcrd, usrName));
		return modelMap;
	}

	/**
	 * 根据排期编号删除发版申请
	 * 
	 * @param scheduleId
	 * @return
	 */
	@RequestMapping(value = "/removeUploadRcrdByScheduleId", method = RequestMethod.POST)
	private Map<String, Object> removeUploadRcrdByScheduleId(@RequestBody String scheduleId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 已经发版的申请按照排期编号删除
		logger.info("scheduleId" + scheduleId);
		modelMap.put("success", uploadrcrdService.removeUploadRcrdByScheduleId(scheduleId));
		return modelMap;
	}

	/**
	 * 根据发版申请编号删除发版申请
	 * 
	 * @param scheduleId
	 * @return
	 */
	@RequestMapping(value = "/removeUploadRcrdByRcrdId", method = RequestMethod.POST)
	private Map<String, Object> removeUploadRcrdByRcrdId(@RequestBody String rcrdId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 已经发版的申请按照申请编号删除
		logger.info("rcrdId" + rcrdId);
		modelMap.put("success", uploadrcrdService.removeUploadRcrdByRcrdId(rcrdId));
		return modelMap;
	}

}
