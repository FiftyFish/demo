package com.imooc.demo.dao;

import java.util.List;

import com.imooc.demo.entity.UploadRcrd;

public interface UploadRcrdDao {

	/**
	 * 列出发版申请表
	 * 
	 * @return UploadRcrdList
	 */
	List<UploadRcrd> queryUploadRcrd();

	/**
	 * 根据排期编号列出发版申请
	 * 
	 * @return UploadRcrdList
	 */
	List<UploadRcrd> queryUploadRcrdByScheduleId(String scheduleId);

	/**
	 * 插入发版申请
	 * 
	 * @return
	 */
	int insertUploadRcrd(UploadRcrd uploadRcrd);

	/**
	 * 更新发版申请
	 * 
	 * @param uploadRcrd
	 * @return
	 */
	int updateUploadRcrd(UploadRcrd uploadRcrd);

	/**
	 * 根据排期编号删除上线申请
	 * 
	 * @return
	 */
	int deleteUploadRcrdByScheduleId(String scheduleId);

	/**
	 * 根据发版申请编号删除上线申请
	 * 
	 * @return
	 */
	int deleteUploadRcrdByRcrdId(String rcrdId);

}
