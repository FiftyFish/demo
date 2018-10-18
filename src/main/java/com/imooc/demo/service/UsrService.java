package com.imooc.demo.service;

import java.util.List;

import com.imooc.demo.entity.Usr;

public interface UsrService {

	/**
	 * 获取用户列表
	 * 
	 * @return
	 */
	List<Usr> getUsrList();
	
	
	/**
	 * 获取用户列表使用模糊查询的方式
	 * 
	 * @return
	 */
	List<Usr> getUsrsList(String usrName);

	/**
	 * 通过用户名获取用户信息
	 * 
	 * @param usrName
	 * @return
	 */
	Usr getUsrByName(String usrName);

	/**
	 * 增加用户信息
	 * 
	 * @param usr
	 * @return
	 */
	boolean addUsr(Usr usr);

	/**
	 * 修改用户信息
	 * 
	 * @param usr
	 * @return
	 */
	boolean updateUsr(Usr usr);

	/**
	 * 删除用户信息
	 * 
	 * @param usr
	 * @return
	 */
	boolean deleteUsr(String usrName);

	boolean verifyUser(Usr usr);

}
