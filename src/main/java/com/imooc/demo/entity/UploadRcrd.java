package com.imooc.demo.entity;


public class UploadRcrd {
	

	//主键
	private String rcrdId;
	//申请人
	private String inptOpr ;
	//申请时间
	private String inptDate ;
	//变更所属系统
	private String belongSys ;
	
	private String belongEnvir;
	//变更内容
	private String changeContent ;
	//变更类型
	private String changeType ;
	//行方领导
	private String  bankLeader;
	//测试案例
	private String testCase ;
	//测试人员
	private String testerName ;
	//修改日期
	private String editDate ;
	//排期编号
	private String scheduleId;
	
	private Integer isSendLeader;

	private Integer isLocalTest;

	private Integer isTest;
	
	
	private Integer isAccept;


	
	
	public String getRcrdId() {
		return rcrdId;
	}
	
	public void setRcrdId(String rcrdId) {
		this.rcrdId = rcrdId;
	}
	

	public String getInptOpr() {
		return inptOpr;
	}

	public void setInptOpr(String inptOpr) {
		this.inptOpr = inptOpr;
	}


	public String getBelongSys() {
		return belongSys;
	}

	public void setBelongSys(String belongSys) {
		this.belongSys = belongSys;
	}



	public String getBelongEnvir() {
		return belongEnvir;
	}

	public void setBelongEnvir(String belongEnvir) {
		this.belongEnvir = belongEnvir;
	}

	public String getChangeContent() {
		return changeContent;
	}

	public void setChangeContent(String changeContent) {
		this.changeContent = changeContent;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getBankLeader() {
		return bankLeader;
	}

	public void setBankLeader(String bankLeader) {
		this.bankLeader = bankLeader;
	}

	public String getTestCase() {
		return testCase;
	}

	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}

	public String getTesterName() {
		return testerName;
	}

	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}


	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		
		this.scheduleId = scheduleId;
	}

	public Integer getIsLocalTest() {
		return isLocalTest;
	}

	public void setIsLocalTest(Integer isLocalTest) {
		this.isLocalTest = isLocalTest;
	}

	public Integer getIsTest() {
		return isTest;
	}

	public void setIsTest(Integer isTest) {
		this.isTest = isTest;
	}

	public Integer getIsSendLeader() {
		return isSendLeader;
	}

	public void setIsSendLeader(Integer isSendLeader) {
		this.isSendLeader = isSendLeader;
	}

	public Integer getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(Integer isAccept) {
		this.isAccept = isAccept;
	}

	public String getInptDate() {
		return inptDate;
	}

	public void setInptDate(String inptDate) {
		this.inptDate = inptDate;
	}

	public String getEditDate() {
		return editDate;
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate;
	}
	
		
}
