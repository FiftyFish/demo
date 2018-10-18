package com.imooc.demo.service;

import java.util.List;

import com.imooc.demo.entity.UploadRcrd;

public interface UploadRcrdService {

	/**
	 * 列出发版申请表
	 * 
	 * @return UploadRcrdList
	 */
	List<UploadRcrd> getUploadRcrdList(String usrName);

	/**
	 * 根据排期编号列出发版申请
	 * 
	 * @return UploadRcrdList
	 */
	List<UploadRcrd> getUploadRcrdListByScheduleId(String inptOpr,String scheduleId);

	List<UploadRcrd> getUploadRcrdListByScheduleIdAdmin(String scheduleId);
	/**
	 * 插入发版申请
	 * 
	 * @return UploadRcrdList
	 */
	boolean addUploadRcrd(UploadRcrd uploadRcrd);

	/**
	 * 更新发版申请
	 * 
	 * @param uploadRcrd
	 * @return
	 */
	boolean modifyUploadRcrd(UploadRcrd uploadRcrd, String usrName);

	/**
	 * 根据排期编号删除上线申请
	 * 
	 * @return UploadRcrdList
	 */
	boolean removeUploadRcrdByScheduleId(String scheduleId);

	/**
	 * 根据发版申请编号删除上线申请
	 * 
	 * @return
	 */
	boolean removeUploadRcrdByRcrdId(String rcrdId);

}
