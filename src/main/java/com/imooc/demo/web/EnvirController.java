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

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.imooc.demo.entity.Envir;
import com.imooc.demo.service.EnvirService;

@RestController
@RequestMapping("/Envir")
public class EnvirController {
	@Autowired
	private EnvirService envirService;
	protected static Logger logger = LoggerFactory.getLogger(EnvirController.class);

	/**
	 * 获取所有的环境信息
	 * 
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/listEnvir", method = RequestMethod.POST)
	private Map<String, Object> listEnvir(@RequestBody String isAdmin) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Envir> list = new ArrayList<Envir>();
		// 获取区域列表
		logger.info("IsAdmin:" + isAdmin);
		int admin = Integer.valueOf(isAdmin);
		list = envirService.getEnvirList(admin);
		logger.info("获取到list的size：" + String.valueOf(list.size()));
		modelMap.put("envirList", list);
		return modelMap;
	}

	/**
	 * 通过IP获取环境信息
	 * 
	 * @param IP
	 * @return
	 */
	@RequestMapping(value = "/getEnvirByIP", method = RequestMethod.POST)
	private Map<String, Object> getEnvirByIP(@RequestBody JSONObject json) {
		int isAdmin = Integer.parseInt(json.getString("isAdmin"));
		String ip = String.valueOf(json.getString("ip"));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		logger.info("IP：" + ip);
		logger.info("IsAdmin：" + isAdmin);
		List<Envir> envirList = envirService.getEnvirByIP(ip, isAdmin);
		logger.info("获取到list的size：" + String.valueOf(envirList.size()));
		modelMap.put("envirList", envirList);
		return modelMap;
	}

	/**
	 * 添加环境信息
	 * 
	 * @param envir
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/addEnvir", method = RequestMethod.POST)
	private Map<String, Object> addEnvir(@RequestBody Envir envir)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 添加环境信息
		logger.info(envir.getEnvirName());
		logger.info(envir.getSystemName());
		logger.info(envir.getLoginName());
		modelMap.put("success", envirService.addEnvir(envir));
		return modelMap;
	}

	/**
	 * 
	 * 修改环境信息
	 * 
	 * @param envir
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/modifyEnvir", method = RequestMethod.POST)
	private Map<String, Object> modifyEnvir(@RequestBody Envir envir)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		logger.info("ID ：" + envir.getId());
		// 修改区域信息
		modelMap.put("success", envirService.modifyEnvir(envir));
		return modelMap;
	}

	@RequestMapping(value = "/removeEnvir", method = RequestMethod.POST)
	private Map<String, Object> removeEnvir(@RequestBody Integer id) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 修改区域信息
		logger.info("id：" + id);
		modelMap.put("success", envirService.removeEnvir(id));
		return modelMap;
	}

}
