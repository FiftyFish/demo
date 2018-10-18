package com.imooc.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSchException;

public class SSHService {
	protected static Logger logger = LoggerFactory.getLogger(SSHService.class);

	/**
	 * @param args
	 */
	public boolean sshExcute(String ip, String user, String password, String command) {
		try {
			// 使用目标服务器机上的用户名和密码登陆
			SSHHelper helper = new SSHHelper(ip, 22, user, password);
			try {
				SSHResInfo resInfo = helper.sendCmd(command);
				logger.info(resInfo.toString());
				helper.close();

			} catch (Exception e) {
				logger.error("command执行出错：", e.getMessage(), e);
				return false;
			}
		} catch (JSchException e) {
			return false;
		}
		return true;

	}

}
