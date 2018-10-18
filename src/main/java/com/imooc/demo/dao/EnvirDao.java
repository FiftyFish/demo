package com.imooc.demo.dao;

import java.util.List;

import com.imooc.demo.entity.Envir;

public interface EnvirDao {
	/**
	 * 列出环境表
	 * 
	 * @return EnvirList
	 */
	List<Envir> queryAdminEnvirList(int isAdmin);

	List<Envir> queryEnvirList(int isAdmin);

	/**
	 * 根据ip查询环境列表
	 * 
	 * @return EnvirList
	 */
	List<Envir> queryEnvirByIP(String ip, int isAdmin);

	List<Envir> queryAdminEnvirByIP(String ip);

	/**
	 * 增加环境信息
	 * 
	 * @param envir
	 * @return
	 */
	int insertEnvir(Envir envir);

	/**
	 * 更新环境信息
	 * 
	 * @param envir
	 * @return
	 */
	int updateEnvir(Envir envir);

	/**
	 * 删除环境
	 * 
	 * @param envirId
	 * @return
	 */
	int deleteEnvir(Integer id);

}
