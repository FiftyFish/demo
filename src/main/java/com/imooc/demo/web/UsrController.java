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
import com.imooc.demo.entity.Usr;
import com.imooc.demo.service.UsrService;

@RestController
@RequestMapping("/login")
public class UsrController {
	@Autowired
	private UsrService usrService;
	protected static Logger logger = LoggerFactory.getLogger(UsrController.class);

	/**
	 * 登陆
	 * 
	 * @return
	 */
	@RequestMapping(value = "/usrLogin", method = RequestMethod.POST)
	private Map<String, Object> login(@RequestBody Usr usr)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 用户登陆
		String name = usr.getUsrName();
		String pass = usr.getUsrPassword();
		logger.info("输入的用户名：" + name);
		logger.info("输入的密码：" + pass);
		
		
		boolean flag =usrService.verifyUser(usr);
		if (flag) {
			Usr usr1=usrService.getUsrByName(name);
			logger.info(usr1.getIsAdmin().toString());		
			modelMap.put("success",flag);
			modelMap.put("usr", usr1);
			return modelMap;
		} else {
			modelMap.put("false", flag);
			return modelMap;
		}
	}

	/**
	 * 获取所有的用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listusr", method = RequestMethod.POST)
	private Map<String, Object> listUsr() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<Usr> list = new ArrayList<Usr>();
		// 获取用户列表
		list = usrService.getUsrList();
		logger.info("获取到list的size：" + String.valueOf(list.size()));
		modelMap.put("usrList", list);
		return modelMap;
	}

	/**
	 * 通过用户名获取用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getusrbyname", method = RequestMethod.POST)
	private Map<String, Object> getUsrByName(@RequestBody String usrName) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取用户信息
		logger.info("输入的用户名：" + usrName);
		Usr usr = usrService.getUsrByName(usrName);
		logger.info("获取的用户名：" + usr.getUsrName());
		modelMap.put("usrList", usr);
		return modelMap;
	}

	/**
	 * 添加用户信息
	 * 
	 * @param usrStr
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@RequestMapping(value = "/addusr", method = RequestMethod.POST)
	private Map<String, Object> addUsr(@RequestBody Usr usr)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		logger.info("输入的用户名：" + usr.getUsrName());
		logger.info("输入的密码：" + usr.getUsrPassword());

		modelMap.put("success", usrService.addUsr(usr));
		return modelMap;
	}

	/**
	 * 修改用户信息，主要修改名字
	 * 
	 * @param usr
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/modifyUsr", method = RequestMethod.POST)
	private Map<String, Object> modifyUsr(@RequestBody Usr usr)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 修改用户信息
		logger.info("修改的用户名为：" + usr.getUsrName());
		modelMap.put("success", usrService.updateUsr(usr));
		return modelMap;
	}

	/**
	 * 删除用户
	 * 
	 * @param usrName
	 * @return
	 */
	@RequestMapping(value = "/removeusr", method = RequestMethod.POST)
	private Map<String, Object> removeUsr(@RequestBody String usrName) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 删除用户信息
		logger.info("删除的用户名为：" + usrName);
		modelMap.put("success", usrService.deleteUsr(usrName));
		return modelMap;
	}

}
