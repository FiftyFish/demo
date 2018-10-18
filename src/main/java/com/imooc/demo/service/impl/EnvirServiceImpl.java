package com.imooc.demo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.demo.dao.EnvirDao;
import com.imooc.demo.entity.Envir;
import com.imooc.demo.service.EnvirService;

@Service
public class EnvirServiceImpl implements EnvirService {
	@Autowired
	private EnvirDao envirDao;

	protected static Logger logger = LoggerFactory.getLogger(EnvirServiceImpl.class);

	@Override
	public List<Envir> getEnvirList(int isadmin) {
		if (isadmin == 0) {
			logger.info("当前用户非管理员！");
			return envirDao.queryEnvirList(isadmin);
		}
		logger.info("当前用户是管理员！");
		return envirDao.queryAdminEnvirList(isadmin);
	}

	@Override
	public List<Envir> getEnvirByIP(String ip, int isadmin) {
		if (isadmin == 0) {
			logger.info("当前用户非管理员！");
			return envirDao.queryEnvirByIP(ip, isadmin);
		}
		logger.info("当前用户是管理员！");
		logger.info(ip+"    "+isadmin);
		return envirDao.queryAdminEnvirByIP(ip);
	}

	@Transactional
	@Override
	public boolean addEnvir(Envir envir) {
		if (envir.getIp() != null && !"".equals(envir.getIp())) {
			try {
				int effectedNum = envirDao.insertEnvir(envir);
				if (effectedNum > 0) {
					logger.info("添加环境成功！");
					return true;
				} else {
					logger.error("添加环境失败！");
					throw new RuntimeException("添加环境失败!");
				}
			} catch (Exception e) {
				logger.error("添加环境失败：", e.getMessage(), e);
				throw new RuntimeException("添加环境失败:" + e.toString());
			}
		} else {
			logger.error("IP不能为空！");
			throw new RuntimeException("IP不能为空！");
		}
	}

	@Transactional
	@Override
	public boolean modifyEnvir(Envir envir) {
		// 空值判断，主要是IP不为空
		if (envir.getIp() != null && !"".equals(envir.getIp())) {

			try {
				// 更新环境信息
				int effectedNum = envirDao.updateEnvir(envir);
				if (effectedNum > 0) {
					logger.info("更新环境信息成功！");
					return true;
				} else {
					logger.error("更新环境信息失败！");
					throw new RuntimeException("更新环境信息失败！");
				}
			} catch (Exception e) {
				logger.error("更新环境信息失败：", e.getMessage(), e);
				throw new RuntimeException("更新环境信息失败:" + e.toString());
			}
		} else {
			logger.error("IP不能为空！");
			throw new RuntimeException("IP不能为空！");
		}
	}

	@Transactional
	@Override
	public boolean removeEnvir(Integer id) {
		if (id != 0) {
			try {
				// 删除环境信息
				int effectedNum = envirDao.deleteEnvir(id);
				if (effectedNum > 0) {
					logger.info("删除环境成功！");
					return true;
				} else {
					logger.error("删除信息失败！");
					throw new RuntimeException("删除信息失败!");
				}
			} catch (Exception e) {
				logger.error("删除信息失败：" + e.getMessage(), e);
				throw new RuntimeException("删除信息失败:" + e.toString());
			}
		} else {
			logger.error("ID不能为空！");
			throw new RuntimeException("ID不能为空！");
		}
	}

}
