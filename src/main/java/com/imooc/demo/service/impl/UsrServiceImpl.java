package com.imooc.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.demo.dao.UsrDao;
import com.imooc.demo.entity.Usr;
import com.imooc.demo.service.UsrService;
import com.imooc.demo.util.EncryptUtil;

@Service
public class UsrServiceImpl implements UsrService{
	@Autowired
	private UsrDao usrDao;
	
	@Override
	public List<Usr> getUsrList() {
		// 列出所有的用户
		return usrDao.queryUsr();
	}

	@Override
	public Usr getUsrByName(String usrName) {
		//根据用户名获取用户信息
		return usrDao.queryUsrByName(usrName);
	}
	@Transactional
	@Override
	public boolean addUsr(Usr usr) {
		//注册用户
		// 空值判断，主要是判断areaName不为空
				if (usr.getUsrName() != null && !"".equals(usr.getUsrName())) {
					//密码加密操作
					try {
						String pwd;
						pwd = EncryptUtil.aesDecrypt(usr.getUsrPassword(), EncryptUtil.KEY);
						usr.setUsrPassword(pwd);
					} catch (Exception e1) {
						throw new RuntimeException("密码加密失败:" + e1.toString());
					} 
					try {
						int effectedNum = usrDao.insertUsr(usr);
						if (effectedNum > 0) {
							return true;
						} else {
							throw new RuntimeException("添加用户失败!");
						}
					} catch (Exception e) {
						throw new RuntimeException("添加用户信息失败:" + e.toString());
					}
				} else {
					throw new RuntimeException("用户信息不能为空！");
				}
			
	}
	@Transactional
	@Override
	public boolean updateUsr(Usr usr) {

		// 空值判断，主要是areaId不为空
		if (usr.getId() != null && usr.getUserId() > 0) {
			// 设置默认值
			
			try {
				// 更新区域信息
				int effectedNum = usrDao.updateUsr(usr);
				if (effectedNum > 0) {
					return true;
				} else {
					throw new RuntimeException("更新用户信息失败!");
				}
			} catch (Exception e) {
				throw new RuntimeException("更新用户信息失败:" + e.toString());
			}
		} else {
			throw new RuntimeException("用户信息不能为空！");
		}
	}
	@Transactional
	@Override
	public boolean deleteUsr(String usrName) {
		if (!usrName.isEmpty()) {
			try {
				// 删除用户信息
				int effectedNum = usrDao.deleteUsr(usrName);
				if (effectedNum > 0) {
					return true;
				} else {
					throw new RuntimeException("删除用户信息失败!");
				}
			} catch (Exception e) {
				throw new RuntimeException("删除用户信息失败:" + e.toString());
			}
		} else {
			throw new RuntimeException("用户名不能为空！");
		}
	}

	@Override
	public boolean verifyUser(Usr usr) {
		String usrName =usr.getUsrName();
		String password =usr.getUsrPassword();
		String pwd = null;
		try {
			pwd = EncryptUtil.aesDecrypt(password, EncryptUtil.KEY);
			usrDao.queryUsrByName(usrName);
			if (pwd.equals(usrDao.queryUsrByName(usrName).getUsrPassword())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException("密码解密失败:" + e.toString());
		}	
		
		
	}

}
