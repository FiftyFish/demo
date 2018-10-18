package com.imooc.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.demo.dao.UsrDao;
import com.imooc.demo.entity.Usr;
import com.imooc.demo.service.UsrService;
import com.imooc.demo.util.EncryptUtil;

@Service
public class UsrServiceImpl implements UsrService {
	@Autowired
	private UsrDao usrDao;
	protected static Logger logger = LoggerFactory.getLogger(UsrServiceImpl.class);

	@Override
	public List<Usr> getUsrList() {
		List<Usr> usrList = usrDao.queryUsr();
		List<Usr> aesDecryptusrList =new ArrayList<Usr>();
		try {
			for (Usr usr : usrList) {
				String pwd;
				pwd = EncryptUtil.aesDecrypt(usr.getUsrPassword(), EncryptUtil.KEY);
				usr.setUsrPassword(pwd);
				aesDecryptusrList.add(usr);
			}
		} catch (Exception e) {
			logger.error("获取用户列表时密码解密失败！", e.getMessage(), e);
			e.printStackTrace();
		}
		// 列出所有的用户
		return aesDecryptusrList;
	}

	@Override
	public Usr getUsrByName(String usrName) {
		return usrDao.queryUsrByName(usrName);
	}

	@Transactional
	@Override
	public boolean addUsr(Usr usr) {
		// 注册用户
		if (usr.getUsrName() != null && !"".equals(usr.getUsrName())) {
			// 设置默认值
			Date input = new Date();
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			usr.setInputDate(sDateFormat.format(input));
			usr.setEditDate(sDateFormat.format(input));
			// 密码加密操作
			try {
				String pwd;
				pwd = EncryptUtil.aesEncrypt(usr.getUsrPassword(), EncryptUtil.KEY);
				usr.setUsrPassword(pwd);
			} catch (Exception e1) {
				logger.error("密码加密失败：", e1.getMessage(), e1);
				throw new RuntimeException("密码加密失败：" + e1.toString());
			}
			try {
				int effectedNum = usrDao.insertUsr(usr);
				if (effectedNum > 0) {
					logger.info("添加用户成功！");
					return true;
				} else {
					logger.error("添加用户失败！");
					throw new RuntimeException("添加用户失败！");
				}
			} catch (Exception e) {
				logger.error("添加用户信息失败：", e.getMessage(), e);
				throw new RuntimeException("添加用户信息失败：" + e.toString(), e);
			}
		} else {
			logger.error("用户信息不能为空！");
			throw new RuntimeException("用户信息不能为空！");
		}

	}

	@Transactional
	@Override
	public boolean updateUsr(Usr usr) {

		// 空值判断，主要是Id不为空
		if (usr.getId() != null && usr.getUserId() > 0) {
			// 设置默认值
			Date input = new Date();
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				String pwd;
				pwd = EncryptUtil.aesEncrypt(usr.getUsrPassword(), EncryptUtil.KEY);
				usr.setUsrPassword(pwd);
			} catch (Exception e1) {
				logger.error("密码加密失败：", e1.getMessage(), e1);
				throw new RuntimeException("密码加密失败：" + e1.toString());
			}
			usr.setEditDate(sDateFormat.format(input));
			try {
				// 更新用户信息
				int effectedNum = usrDao.updateUsr(usr);
				if (effectedNum > 0) {
					logger.info("更新用户信息成功！");
					return true;
				} else {
					logger.error("更新用户信息失败！");
					throw new RuntimeException("更新用户信息失败!");
				}
			} catch (Exception e) {
				logger.error("更新用户信息失败：", e.getMessage(), e);
				throw new RuntimeException("更新用户信息失败:" + e.toString(), e);
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
					logger.info("删除用户成功！");
					return true;
				} else {
					logger.error("删除用户信息失败！");
					throw new RuntimeException("删除用户信息失败！");
				}
			} catch (Exception e) {
				logger.error("删除用户信息失败：", e.getMessage(), e);
				throw new RuntimeException("删除用户信息失败:" + e.toString(), e);
			}
		} else {
			logger.error("用户名不能为空！");
			throw new RuntimeException("用户名不能为空！");
		}
	}

	@Override
	public boolean verifyUser(Usr usr) {
		String usrName = usr.getUsrName();
		String password = usr.getUsrPassword();
		String pwd = null;
		try {
			pwd = EncryptUtil.aesEncrypt(password, EncryptUtil.KEY);
			logger.info(pwd);
		} catch (Exception e) {
			logger.error("密码解密失败：", e.getMessage(), e);
			throw new RuntimeException("密码解密失败:" + e.toString());
		}
		usrDao.queryUsrByName(usrName);
		if (pwd.equals(usrDao.queryUsrByName(usrName).getUsrPassword())) {
			logger.info("用户名密码验证成功！");
			return true;
		} else {
			logger.error("密码错误！");
			throw new RuntimeException("密码错误！");
//				return false;
		}

	}

	@Override
	public List<Usr> getUsrsList(String usrName) {
		List<Usr> usrList = usrDao.queryUsrs(usrName);
		List<Usr> aesDecryptusrList =new ArrayList<Usr>();
		try {
			for (Usr usr : usrList) {
				String pwd;
				pwd = EncryptUtil.aesDecrypt(usr.getUsrPassword(), EncryptUtil.KEY);
				usr.setUsrPassword(pwd);
				aesDecryptusrList.add(usr);
			}
		} catch (Exception e) {
			logger.error("获取用户列表时密码解密失败！", e.getMessage(), e);
			e.printStackTrace();
		}
		// 列出条件中的用户
		return aesDecryptusrList;
	}

}
