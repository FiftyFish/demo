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
import com.imooc.demo.entity.Envir;
import com.imooc.demo.service.EnvirService;

@RestController
@RequestMapping("/Envir")
public class EnvirController {
	@Autowired
	private EnvirService envirService;
	@Autowired
	
	/**
	 * 获取所有的环境信息
	 * @param isAdmin
	 * @return
	 */
	@RequestMapping(value = "/listEnvir", method = RequestMethod.GET)
	private Map<String, Object> listEnvir() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Envir> list = new ArrayList<Envir>();
		// 获取区域列表
		list = envirService.getEnvirList(1);
		modelMap.put("envirList", list);
		return modelMap;
	}
	
	/**
	 * 通过IP获取环境信息
	 * @param IP
	 * @return
	 */
	@RequestMapping(value = "/getEnvirByIP", method = RequestMethod.GET)
	private Map<String, Object> getEnvirByIP(String ip, int isAdmin) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Envir envir = envirService.getEnvirByIP(ip,isAdmin);
		modelMap.put("envir", envir);
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
	private Map<String, Object> addEnvir(Envir envir)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 添加用户信息
		modelMap.put("success", envirService.addEnvir(envir));
		return modelMap;
	}
	/**
	 * 
	 * 修改环境信息
	 * @param envir
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/modifyEnvir", method = RequestMethod.POST)
	private Map<String, Object> modifyEnvir(Envir envir)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 修改区域信息
		modelMap.put("success",envirService.modifyEnvir(envir));
		return modelMap;
	}

	@RequestMapping(value = "/removeEnvir", method = RequestMethod.GET)
	private Map<String, Object> removeEnvir(Integer id) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 修改区域信息
		modelMap.put("success", envirService.removeEnvir(id));
		return modelMap;
	}
	
}
