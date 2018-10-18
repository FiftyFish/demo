package com.imooc.demo.entity;

public class Envir {

	// 主键
	private Integer id;
	// IP
	private String ip;
	// 登录名
	private String loginName;
	// 登录密码
	private String loginPassword;

	private String envirName;

	private String systemName;
	// 是否为管理员
	private Integer isAdmin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getEnvirName() {
		return envirName;
	}

	public void setEnvirName(String envirName) {
		this.envirName = envirName;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

}
