package com.imooc.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ftp {
	protected static Logger logger = LoggerFactory.getLogger(Ftp.class);

	private String hostname = null;
	private String pwd = null;

	public Ftp(String hostname, String pwd) {

		this.hostname = hostname;
		this.pwd = pwd;

	}

	// ftp服务器端口号默认为21
	public Integer port = 21;
	// ftp登录账号
	public String username = "root";

	public FTPClient ftpClient = null;

	/**
	 * 初始化ftp服务器
	 */
	public void initFtpClient() {
		ftpClient = new FTPClient();
		ftpClient.setControlEncoding("utf-8");
		try {
			logger.info("connecting...ftp服务器:" + this.hostname + ":" + this.port);
			ftpClient.connect(hostname, port); // 连接ftp服务器
			ftpClient.login(username, pwd); // 登录ftp服务器
			int replyCode = ftpClient.getReplyCode();
			logger.info("检查登录服务器状态" + replyCode);
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				logger.error("connect failed:ftp服务器:" + this.hostname + ":" + this.port);
			}
			logger.info("connect successful:ftp服务器:" + this.hostname + ":" + this.port);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param pathname       ftp服务保存地址
	 * @param fileName       上传到ftp的文件名
	 * @param originfilename 待上传文件的名称（绝对地址） *
	 * @return
	 */
	public boolean uploadFile(String pathname, String fileName, String originfilename) {
		boolean flag = false;
		InputStream inputStream = null;
		try {
			logger.info("开始上传文件");
			inputStream = new FileInputStream(new File(originfilename));
			initFtpClient();
			ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
			CreateDirecroty(pathname);
			ftpClient.makeDirectory(pathname);
			ftpClient.changeWorkingDirectory(pathname);
			ftpClient.storeFile(fileName, inputStream);
			inputStream.close();
			ftpClient.logout();
			flag = true;
			logger.info("上传文件成功");
		} catch (Exception e) {
			logger.error("上传文件失败", e.getMessage(), e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return flag;
	}

	/**
	 * * 下载文件 *
	 * 
	 * @param pathname  FTP服务器文件目录 *
	 * @param filename  文件名称 *
	 * @param localpath 下载后的文件路径 *
	 * @return
	 */
	public boolean downloadFile(String pathname, String filename, String localpath) {
		boolean flag = false;
		OutputStream os = null;
		try {
			logger.info("开始下载文件");
			initFtpClient();
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(pathname);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			for (FTPFile file : ftpFiles) {
				if (filename.equalsIgnoreCase(file.getName())) {
					File localFile = new File(localpath + "/" + file.getName());
					os = new FileOutputStream(localFile);
					ftpClient.retrieveFile(file.getName(), os);
					os.close();
				}
			}
			ftpClient.logout();
			flag = true;
			logger.info("下载文件成功");
		} catch (Exception e) {
			logger.error("下载文件失败", e.getMessage(), e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return flag;
	}

	// 改变目录路径
	public boolean changeWorkingDirectory(String directory) {
		boolean flag = true;
		try {
			flag = ftpClient.changeWorkingDirectory(directory);
			if (flag) {
				logger.info("进入文件夹" + directory + " 成功！");
				System.out.println("进入文件夹" + directory + " 成功！");

			} else {
				logger.info("进入文件夹" + directory + " 失败！开始创建文件夹");
				System.out.println("进入文件夹" + directory + " 失败！开始创建文件夹");
			}
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
			ioe.printStackTrace();
		}
		return flag;
	}

	// 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
	public boolean CreateDirecroty(String remote) throws IOException {
		boolean success = true;
		String directory = remote + "/";
		// 如果远程目录不存在，则递归创建远程服务器目录
		if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
			logger.info("远程目录不存在，递归创建远程服务器目录");
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			String path = "";
			String paths = "";
			while (true) {
				String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
				path = path + "/" + subDirectory;
				if (!existFile(path)) {
					if (makeDirectory(subDirectory)) {
						changeWorkingDirectory(subDirectory);
					} else {
						logger.error("\"创建目录[\" + subDirectory + \"]失败\"");
						System.out.println("创建目录[" + subDirectory + "]失败");
						changeWorkingDirectory(subDirectory);
					}
				} else {
					changeWorkingDirectory(subDirectory);
				}

				paths = paths + "/" + subDirectory;
				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return success;
	}

	// 判断ftp服务器文件是否存在
	public boolean existFile(String path) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr.length > 0) {
			flag = true;
		}
		return flag;
	}

	// 创建目录
	public boolean makeDirectory(String dir) {
		boolean flag = true;
		try {
			flag = ftpClient.makeDirectory(dir);
			if (flag) {
				logger.info("创建文件夹" + dir + " 成功！");
				System.out.println("创建文件夹" + dir + " 成功！");

			} else {
				logger.info("创建文件夹" + dir + " 失败！");
				System.out.println("创建文件夹" + dir + " 失败！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * * 删除文件 *
	 * 
	 * @param pathname FTP服务器保存目录 *
	 * @param filename 要删除的文件名称 *
	 * @return
	 */
	public boolean deleteFile(String pathname, String filename) {
		boolean flag = false;
		try {
			System.out.println("开始删除文件");
			initFtpClient();
			// 切换FTP目录
			ftpClient.changeWorkingDirectory(pathname);
			ftpClient.dele(filename);
			ftpClient.logout();
			flag = true;
			System.out.println("删除文件成功");
		} catch (Exception e) {
			System.out.println("删除文件失败");
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	public static void main(String[] args) {
		Ftp ftp = new Ftp("192.168.43.183", "111111");
//        ftp.uploadFile("/elk/fanjia", "fanjia.rar", "G://Users//fanjia.rar");
		ftp.downloadFile("/elk/fanjia", "fanjia.rar", "G://Users//");
//        ftp.deleteFile("/usr/ebank", "back.tar");
		System.out.println("upload is ok!");
//		String addstr = "config/SYSDATA/csmplog4j.properties";
//		int i;
//		for (i = addstr.length(); i > 0; i--) {
//			char curr = addstr.charAt(i - 1);
//			if (curr == '/')
//				break;
//		}
//		// 截取字符串
//		String dir = addstr.substring(0, i);
//		String chmod = "PRE COMMAND UPDATE chmod -R 755 /usr/ebank/buildit/app/cotb.ear/COTBPRE.war/" + dir;

	}
}
