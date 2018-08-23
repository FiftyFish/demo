package com.imooc.demo.service.impl;

import java.util.List;

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
	@Override
	public List<Envir> getEnvirList(int isadmin) {
		return envirDao.queryEnvirList(isadmin);
	}

	@Override
	public Envir getEnvirByIP(String ip,int isadmin) {
		return envirDao.queryEnvirByIP(ip,isadmin);
	}
	@Transactional
	@Override
	public boolean addEnvir(Envir envir) {
		if (envir.getIp() != null && !"".equals(envir.getIp())) {
			try {
				int effectedNum = envirDao.insertEnvir(envir);
				if (effectedNum > 0) {
					return true;
				} else {
					throw new RuntimeException("添加环境失败!");
				}
			} catch (Exception e) {
				throw new RuntimeException("添加环境信息失败:" + e.toString());
			}
		} else {
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
							return true;
						} else {
							throw new RuntimeException("更新环境信息失败!");
						}
					} catch (Exception e) {
						throw new RuntimeException("更新环境信息失败:" + e.toString());
					}
				} else {
					throw new RuntimeException("IP不能为空！");
				}
	}
	@Transactional
	@Override
	public boolean removeEnvir(Integer id) {
		if (!id.equals(null)) {
			try {
				// 删除环境信息
				int effectedNum = envirDao.deleteEnvir(id);
				if (effectedNum > 0) {
					return true;
				} else {
					throw new RuntimeException("删除信息失败!");
				}
			} catch (Exception e) {
				throw new RuntimeException("删除信息失败:" + e.toString());
			}
		} else {
			throw new RuntimeException("IP不能为空！");
		}
	}

}
