package com.imooc.demo.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.imooc.demo.entity.Usr;
import com.imooc.demo.service.UsrService;


@RestController
@RequestMapping("/user")
public class UsrController {
	@Autowired
	private UsrService usrService;
	
	/**
	 * 登陆
	 * 
	 * @return
	 */
	@RequestMapping(value = "/usrLogin", method = RequestMethod.POST)
    String userLogin(@RequestBody Usr usr, Model model) {
        boolean verify = usrService.verifyUser(usr);
        if (verify) {
            model.addAttribute("usrName", usr.getUsrName());
            model.addAttribute("password", usr.getUsrPassword());
            
            System.out.println("success!!!!");
            return "result";
        } else {
            return "redirect:/notVerify";
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
		// 获取区域列表
		list = usrService.getUsrList();
		modelMap.put("usrList", list);
		return modelMap;
	}
	/**
	 * 通过用户名获取用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getusrbyname", method = RequestMethod.GET)
	private Map<String, Object> getUsrByName(String usrName) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取用户信息
		Usr usr = usrService.getUsrByName(usrName);
		modelMap.put("usr", usr);
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
	private Map<String, Object> addUsr(Usr usr)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 添加用户信息
		//密码加密操作
//		Usr newusr = null;
//		try {
//			String pwd;
//			pwd = EncryptUtil.aesDecrypt(usr.getUsrPassword(), EncryptUtil.KEY);
//			newusr.setUsrPassword(pwd);
//		} catch (Exception e1) {
//			throw new RuntimeException("密码加密失败:" + e1.toString());
//		}
//		newusr.setAffiliatedCompany(usr.getAffiliatedCompany());
//		newusr.setAttribute(usr.getAttribute());
//		newusr.setBankLeader(usr.getBankLeader());
//		newusr.setEditDate(new Date());
//		newusr.setId(usr.getId());
//		newusr.setInputDate(new Date());
//		newusr.setIsAdmin(usr.getIsAdmin());
//		newusr.setUserId(usr.getUserId());
//		newusr.setUsrName(usr.getUsrName());
//		modelMap.put("success", usrService.addUsr(newusr));
		modelMap.put("success", usrService.addUsr(usr));
		return modelMap;
	}
	/**
	 * 修改区域信息，主要修改名字
	 * 
	 * @param areaStr
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@RequestMapping(value = "/modifyusr", method = RequestMethod.POST)
	private Map<String, Object> updateUsr(Usr usr)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 修改区域信息
		modelMap.put("success",usrService.updateUsr(usr));
		return modelMap;
	}

	@RequestMapping(value = "/removeusr", method = RequestMethod.GET)
	private Map<String, Object> removeUsr(String usrName) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 修改区域信息
		modelMap.put("success", usrService.deleteUsr(usrName));
		return modelMap;
	}
	
}
