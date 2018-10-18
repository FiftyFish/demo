package com.imooc.demo.entity;

/**
 * 区域信息
 * 
 * @author xiangze
 *
 */
public class Area {
	// 主键ID
	private Integer areaId;
	// 名称
	private String areaName;
	// 权重，越大越排前显示
	private Integer priority;
	// 创建时间
	private String creatTime;
	// 更新时间
	private String lastEditTime;

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getLastEditTime() {
		return lastEditTime;
	}

	public void setLastEditTime(String lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

}
