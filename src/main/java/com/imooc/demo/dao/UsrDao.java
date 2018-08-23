package com.imooc.demo.dao;

import java.util.List;

import com.imooc.demo.entity.Usr;

public interface UsrDao {

	/**
	 * 列出用户列表
	 * 
	 * @return usrList
	 */
	List<Usr> queryUsr();

	/**
	 * 根据用户名列出具体用户
	 * 
	 * @return usr
	 */
	Usr queryUsrByName(String usrName);

	/**
	 * 插入用户
	 * 
	 * @param usr
	 * @return
	 */
	int insertUsr(Usr usr);

	/**
	 * 更新用户信息
	 * 
	 * @param usr
	 * @return
	 */
	int updateUsr(Usr usr);

	/**
	 * 删除用户
	 * 
	 * @param usrName
	 * @return
	 */
	int deleteUsr(String usrName);
}
