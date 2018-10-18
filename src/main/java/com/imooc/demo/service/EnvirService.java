package com.imooc.demo.service;

import java.util.List;

import com.imooc.demo.entity.Envir;

public interface EnvirService {

	/**
	 * 列出环境表
	 * 
	 * @return EnvirList
	 */
	List<Envir> getEnvirList(int isadmin);

	/**
	 * 根据IP查询环境列表
	 * 
	 * @return EnvirList
	 */
	List<Envir> getEnvirByIP(String ip, int isadmin);

	/**
	 * 增加环境信息
	 * 
	 * @param envir
	 * @return
	 */
	boolean addEnvir(Envir envir);

	/**
	 * 更新环境信息
	 * 
	 * @param envir
	 * @return
	 */
	boolean modifyEnvir(Envir envir);

	/**
	 * 删除环境
	 * 
	 * @param envirId
	 * @return
	 */
	boolean removeEnvir(Integer id);

}
