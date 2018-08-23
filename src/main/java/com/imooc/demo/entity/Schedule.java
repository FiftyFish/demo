package com.imooc.demo.entity;

import java.util.Date;

public class Schedule {
	
	
	private Integer id;

	private String scheduleId;

	private Date uploadDate;

	private Integer isUpload;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Integer getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}
	
	
}
