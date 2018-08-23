package com.imooc.demo.entity;

public class Envir {
	
	//主键
	private Integer id;
	//IP
	private String ip;
	//登录名
	private String loginName;
	//登录密码
	private String loginPassword;
	
	private String envirName;
	
	private String systemName;
	//是否为管理员
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
	public void setLoginName(String login_name) {
		this.loginName = login_name;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String login_password) {
		this.loginPassword = login_password;
	}
	public String getEnvirName() {
		return envirName;
	}
	public void setEnvir_name(String envir_name) {
		this.envirName = envir_name;
	}
	public String getSystem_name() {
		return systemName;
	}
	public void setSystem_name(String system_name) {
		this.systemName = system_name;
	}
	public Integer getIs_admin() {
		return isAdmin;
	}
	public void setIs_admin(Integer is_admin) {
		this.isAdmin = is_admin;
	}

}
