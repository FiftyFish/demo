package com.imooc.demo.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imooc.demo.entity.UploadRcrd;

public class ExtractFiles {

	protected static Logger logger = LoggerFactory.getLogger(ExtractFiles.class);

	public boolean extractFiles(List<UploadRcrd> list) {

		boolean flag = true;
		try {
			SSHService ss = new SSHService();
			// 解压文件
			logger.info("开始解压上传到64.2的tar包...");
			ss.sshExcute("192.168.43.183", "root", "111111",
					"cd /elk/fanjia/ && tar -xvf cobp.tar -C /elk/fanjia/uploadfile/BP");
//		ss.sshExcute("22.5.64.2", "rtadm", "dev@098%", "cd /elk/tra/cobp/ && tar -xvf cobp.tar -C /elk/fanjia/uploadfile/BP");
//		ss.sshExcute("22.5.64.2", "rtadm", "dev@098%", "cd /elk/tra/cotb/ && tar -xvf cotb.tar -C /elk/fanjia/uploadfile/WEB");
//		ss.sshExcute("22.5.64.2", "rtadm", "dev@098%", "cd /elk/tra/cotbpre/ && tar -xvf cotbpre.tar -C /elk/fanjia/uploadfile/PRE");

			// 根据RcrdList进行文件抽取
			logger.info("根据rcrdList进行文件抽取文件...");
			ExtractFiles d = new ExtractFiles();
			d.deleteOtherFiles(list);

			// 将文件打成Tar包
			ss.sshExcute("192.168.43.183", "root", "111111",
					"cd /elk/fanjia/uploadfile/ && tar -cvf cobp.tar file.list uploadRcrd.xls BP");
			Ftp tf = new Ftp("192.168.43.183", "111111");
			tf.uploadFile("/elk/haiyu", "cobp.tar", "G://Users//fanjia//cobp.tar");
//		ss.sshExcute("22.5.64.2", "rtadm", "dev@098%", "cd /elk/fanjia/uploadfile/ &&tar -cvf cotb.tar file.list WEB");
//		//将Tar包上传到对应服务器中
//		logger.info("将增量文件包和file.list上传到服务器/usr/release目录下...");
//		Ftp tf=new Ftp("22.5.98.196","dev@098%");
//		tf.uploadFile("/usr/release", "cotb.tar", "/elk/fanjia/uploadfile/cotb.tar");
//		
//		//将文件打成Tar包
//		ss.sshExcute("22.5.64.2", "rtadm", "dev@098%", "cd /elk/fanjia/uploadfile/ &&tar -cvf pre.tar file.list  PRE");
//		//将Tar包上传到对应服务器中
//		Ftp tf1=new Ftp("22.5.98.197","dev@098%");
//		tf1.uploadFile("/usr/release", "pre.tar", "/elk/fanjia/uploadfile/pre.tar");
//		
//		//将文件打成Tar包
//		ss.sshExcute("22.5.64.2", "rtadm", "dev@098%", "cd /elk/fanjia/uploadfile/ &&tar -cvf cobp.tar file.list BP");
//		//将Tar包上传到对应服务器中
//		Ftp tf2=new Ftp("22.5.98.198","dev@098%");
//		tf2.uploadFile("/usr/release", "cobp.tar", "/elk/fanjia/uploadfile/cobp.tar");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			flag = false;
		}

		return flag;
	}

	/**
	 * 上线时从服务器上取到对应包
	 */
	public static void getServerFile() {
		// 在服务器中将文件打成Tar包
		SSHService ss = new SSHService();
		ss.sshExcute("22.5.98.196", "rtadm", "dev@098%", "cd /opt/IBM/HTTPServer/htdocs/ &&tar -cvf cotb.tar cotb");
		// 将Tar包上传到64.2目标文件夹下
		Ftp tf = new Ftp("22.5.64.2", "dev@098%");
		tf.uploadFile("/elk/tra/cotb/", "cotb.tar", "/opt/IBM/HTTPServer/htdocs/cotb.tar");

		ss.sshExcute("22.5.98.197", "rtadm", "dev@098%",
				"cd /usr/ebank/buildit/app/cotb.ear/COTBPRE.war/ &&tar -cvf cotbpre.tar config WEB-INF META-INF");
		Ftp tf1 = new Ftp("22.5.64.2", "dev@098%");
		tf1.uploadFile("/elk/tra/cotbpre/", "cotbpre.tar", "/usr/ebank/buildit/app/cotb.ear/COTBPRE.war/cotbpre.tar");

		ss.sshExcute("22.5.98.198", "rtadm", "dev@098%", "cd /usr/ebank/buildit/app/ &&tar -cvf cobp.tar cobp");
		Ftp tf2 = new Ftp("22.5.98.198", "dev@098%");
		tf2.uploadFile("/elk/tra/cobp/", "cobp.tar", "/usr/ebank/buildit/app/cobp.tar");

	}

	/**
	 * 将上线文件进行规整，并下载到本地
	 */
	public static void downloadFile() {
		Ftp tf = new Ftp("192.168.43.183", "111111");
		tf.downloadFile("/elk/fanjia/uploadfile/", "upload.tar", "F:/package/upload.tar");
		// 将Tar包上传到对应服务器中
//		Ftp tf=new Ftp("22.5.98.196","dev@098%");
//		tf.downloadFile("/elk/fanjia/uploadfile/", "upload.tar", "D:/package/upload.tar");
//		logger.info("上线包存储路径为：D:/package/upload.tar");
	}

	/**
	 * 删除不在uploadRcrd中的文件
	 * 
	 * @param list
	 */
	public void deleteOtherFiles(List<UploadRcrd> list) {
		ArrayList<String> pathListBP = new ArrayList<>();
		ArrayList<String> pathListWEB = new ArrayList<>();
		ArrayList<String> pathListPRE = new ArrayList<>();
		String prefixFileBP = "";
		String prefixFileWEB = "";
		String prefixFilePRE = "";
		for (UploadRcrd upload : list) {
			if ("BP".equals(upload.getBelongSys())) {
				prefixFileBP = "/elk/fanjia/uploadfile/BP/";
				pathListBP.add(prefixFileBP + upload.getChangeContent());
			} else if ("WEB".equals(upload.getBelongSys())) {
				prefixFileWEB = "/elk/fanjia/uploadfile/WEB/";
				pathListWEB.add(prefixFileWEB + upload.getChangeContent());

			} else if ("PRE".equals(upload.getBelongSys())) {
				prefixFilePRE = "/elk/fanjia/uploadfile/PRE/";
				pathListPRE.add(prefixFilePRE + upload.getChangeContent());
			}
		}
		File fileBP = new File(prefixFileBP);
		File fileWEB = new File(prefixFileWEB);
		File filePRE = new File(prefixFilePRE);
		ExtractFiles d = new ExtractFiles();
		d.deleteFile(fileBP, pathListBP);
		d.deleteDir(fileBP);
		d.deleteFile(fileWEB, pathListWEB);
		d.deleteDir(fileBP);
		d.deleteFile(filePRE, pathListPRE);
		d.deleteDir(fileBP);
		logger.info("count BP" + String.valueOf(pathListBP.size()));
		logger.info("count WEB" + String.valueOf(pathListWEB.size()));
		logger.info("count PRE" + String.valueOf(pathListPRE.size()));

	}

	/**
	 * 根据list抽取file中的文件
	 * 
	 * @param file
	 * @param list
	 */
	public void deleteFile(File file, ArrayList<String> list) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i], list);
			}
		} else {
			if (!list.contains(file.getAbsolutePath())) {
				file.delete();
			} else {
				System.out.println(file.getAbsolutePath());
			}
		}

	}

	/**
	 * 删除file中的空文件夹
	 * 
	 * @param file
	 */
	public void deleteDir(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				file.delete();
				File file3 = file.getParentFile();
				if (file3.getAbsolutePath().contains("cobp") || file3.getAbsolutePath().contains("COTBPRE")
						|| file3.getAbsolutePath().contains("WEB")) {
					deleteDir(file.getParentFile());
				}
			}
			for (int i = 0; i < files.length; i++) {
				deleteDir(files[i]);
			}
		}
	}

}
