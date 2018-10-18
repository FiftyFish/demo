package com.imooc.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.demo.dao.AreaDao;
import com.imooc.demo.entity.Area;
import com.imooc.demo.service.AreaService;

//使用@Servce注解声明当前AreaService类是Spring管理的一个Bean，其中使用@Component、@Service、@Repository和@Controller是等效的，可根据需要选用。
@Service
public class AreaServiceImpl implements AreaService {
	// 使用@Autowired将AreaDao的实体Bean注入到AreaService中，，让AreaService具备AreaDao的功能，
	// 此处使用JSR-330的@Inject注解或者JSR-250的@Resource注解是等效的。
	@Autowired
	private AreaDao areaDao;

	@Override
	public List<Area> getAreaList() {
		// 返回所有的区域信息
		return areaDao.queryArea();
	}

	@Override
	public Area getAreaById(int areaId) {
		return areaDao.queryAreaById(areaId);
	}

	@Transactional
	@Override
	public boolean addArea(Area area) {
		// 空值判断，主要是判断areaName不为空
		if (area.getAreaName() != null && !"".equals(area.getAreaName())) {
			// 设置默认值
			Date input = new Date();
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			area.setCreatTime(sDateFormat.format(input));
			area.setLastEditTime(sDateFormat.format(input));
			try {
				int effectedNum = areaDao.insertArea(area);
				if (effectedNum > 0) {
					return true;
				} else {
					throw new RuntimeException("添加区域信息失败!");
				}
			} catch (Exception e) {
				throw new RuntimeException("添加区域信息失败:" + e.toString());
			}
		} else {
			throw new RuntimeException("区域信息不能为空！");
		}
	}

	@Transactional
	@Override
	public boolean modifyArea(Area area) {
		// 空值判断，主要是areaId不为空
		if (area.getAreaId() != null && area.getAreaId() > 0) {
			// 设置默认值
			Date input = new Date();
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			area.setLastEditTime(sDateFormat.format(input));
			try {
				// 更新区域信息
				int effectedNum = areaDao.updateArea(area);
				if (effectedNum > 0) {
					return true;
				} else {
					throw new RuntimeException("更新区域信息失败!");
				}
			} catch (Exception e) {
				throw new RuntimeException("更新区域信息失败:" + e.toString());
			}
		} else {
			throw new RuntimeException("区域信息不能为空！");
		}
	}

	@Transactional
	@Override
	public boolean deleteArea(int areaId) {
		if (areaId > 0) {
			try {
				// 删除区域信息
				int effectedNum = areaDao.deleteArea(areaId);
				if (effectedNum > 0) {
					return true;
				} else {
					throw new RuntimeException("删除区域信息失败!");
				}
			} catch (Exception e) {
				throw new RuntimeException("删除区域信息失败:" + e.toString());
			}
		} else {
			throw new RuntimeException("区域Id不能为空！");
		}
	}
}
