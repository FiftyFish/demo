package com.imooc.demo.util;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


import com.imooc.demo.entity.UploadRcrd;

public class ExcelparseFileList<T>{
	
	public void excelParse(List<UploadRcrd> list) throws Exception {
		List<String> webAddlist = new ArrayList<String>();
		List<String> webUpdateList = new ArrayList<String>();
		List<String> webDellist = new ArrayList<String>();
		List<String> bpAddlist = new ArrayList<String>();
		List<String> bpUpdateList = new ArrayList<String>();
		List<String> bpDellist = new ArrayList<String>();
		List<String> preAddlist = new ArrayList<String>();
		List<String> preUpdateList = new ArrayList<String>();
		List<String> preDellist = new ArrayList<String>();
		FileOutputStream fs = new FileOutputStream("F://file.list");
		OutputStreamWriter osw = new OutputStreamWriter(fs, "GBK");

		try {
			List<String> dirList = new ArrayList<>();
			Set<String> com = new TreeSet<>();
			for (int i = 0; i < list.size(); i++) {
				UploadRcrd rcrd = list.get(i);
				if (rcrd.getChangeType().equals("新增")) {
					if (rcrd.getBelongSys().equals("WEB")) {
						dirList.add("WEB  COMMAND BACKUP mkdir -p      /opt/IBM/HTTPServer/htdocs" + " " + rcrd.getChangeContent());
						dirList.add("WEB  COMMAND UPDATE chmod -R 755  /opt/IBM/HTTPServer/htdocs" + " " + rcrd.getChangeContent());
					}else if (rcrd.getBelongSys().equals("BP")){
						dirList.add("BP   COMMAND BACKUP mkdir -p      /usr/ebank/buildit/app" + " " + rcrd.getChangeContent());
						dirList.add("BP   COMMAND BACKUP chmod -R 755  /usr/ebank/buildit/app" + " " + rcrd.getChangeContent());
					}else if (rcrd.getBelongSys().equals("PRE")) {
						dirList.add("PRE  COMMAND BACKUP mkdir -p      /usr/ebank/buildit/app/cotb.ear/COTBPRE.war" + " " + rcrd.getChangeContent());
						dirList.add("PRE  COMMAND UPDATE chmod -R 755  /usr/ebank/buildit/app/cotb.ear/COTBPRE.war" + " " + rcrd.getChangeContent());
					}

				}
			}
			for (String addDir : dirList) {
				int j;
				for (j = addDir.length(); j > 0; j--) {
					char curr = addDir.charAt(j - 1);
					if (curr == '/')
						break;
				}
				String dir = addDir.substring(0, j);
				com.add(dir);
			}
			String mkdir ="\n" + "#File Add : mkdir" + "\n\n";
			osw.write(mkdir);
			osw.flush();
			for (String dir : com) {
				dir += "\n";
				osw.write(dir);
				osw.flush();
			}
			for (int i = 0; i < list.size(); i++) {
				UploadRcrd rcrd = list.get(i);
				if (rcrd.getBelongSys().equals("WEB")) {
					if (rcrd.getChangeType().equals("修改")) {
						webUpdateList.add(rcrd.getChangeContent());
					} else if (rcrd.getChangeType().equals("新增")) {
						webAddlist.add(rcrd.getChangeContent());
					} else if (rcrd.getChangeType().equals("删除")) {
						webDellist.add(rcrd.getChangeContent());
					}
					
				}else if (rcrd.getBelongSys().equals("BP")) {
					if (rcrd.getChangeType().equals("修改")) {
						bpUpdateList.add(rcrd.getChangeContent());
					} else if (rcrd.getChangeType().equals("新增")) {
						bpAddlist.add(rcrd.getChangeContent());
					} else if (rcrd.getChangeType().equals("删除")) {
						bpDellist.add(rcrd.getChangeContent());
					}
					
				}else if (rcrd.getBelongSys().equals("PRE")){
					if (rcrd.getChangeType().equals("修改")) {
						preUpdateList.add(rcrd.getChangeContent());
					} else if (rcrd.getChangeType().equals("新增")) {
						preAddlist.add(rcrd.getChangeContent());
					} else if (rcrd.getChangeType().equals("删除")) {
						preDellist.add(rcrd.getChangeContent());
					}
					
				}
			}
			String s10086 = "                   \n" + "#WEB  file   update";
			s10086 += "\n";
			osw.write(s10086);
			osw.flush();
			for (String upstr : webUpdateList) {
				String s = "WEB  FILE   UPDATE   /opt/IBM/HTTPServer/htdocs  "
						+ upstr;
				s += "\n";
				osw.write(s);
				osw.flush();
			}
			
			String s1008611 = "                   \n" + "#WEB    file add";
			s1008611 += "\n";
			osw.write(s1008611);
			osw.flush();
			for (String addstr : webAddlist) {
				String s = "WEB  FILE   ADD      /opt/IBM/HTTPServer/htdocs  "
						+ addstr;
				s += "\n";
				osw.write(s);
				osw.flush();
			}
			String s1008610 = "                   \n" + "#WEB   file delete";
			s1008610 += "\n";
			osw.write(s1008610);
			osw.flush();
			for (String delstr : webDellist) {
				String s = "WEB  FILE   DELETE   /opt/IBM/HTTPServer/htdocs  "
						+ delstr;
				s += "\n";
				osw.write(s);
				osw.flush();
			}

			System.out.println("WEB写出完成");
			// 遍历bplist

			String s10010 = "                   \n" + "#BP  file update";
			s10010 += "\n";
			osw.write(s10010);
			osw.flush();
			for (String upstr : bpUpdateList) {
				String s = "BP  FILE   UPDATE   /usr/ebank/buildit/app  "
						+ upstr;
				s += "\n";
				osw.write(s);
				osw.flush();
			}
			String s10011 = "        \n" + "#BP  file add";
			s10011 += "\n";
			osw.write(s10011);
			osw.flush();
			for (String addstr : bpAddlist) {
				String s = "BP  FILE   ADD      /usr/ebank/buildit/app  " + addstr;
				s += "\n";
				osw.write(s);
				osw.flush();
			}
			String s10012 = "              \n" + "#BP  file delete";
			s10012 += "\n";
			osw.write(s10012);
			osw.flush();
			for (String delstr : bpDellist) {
				String s = "BP  FILE   DELETE   /usr/ebank/buildit/app  "
						+ delstr;
				s += "\n";
				osw.write(s);
				osw.flush();
			}
			System.out.println("BP写出完成");
			// 遍历preList
			String s10000 = "                \n" + "#PRE  file update";
			s10000 += "\n";
			osw.write(s10000);
			osw.flush();

			for (String upstr : preUpdateList) {
				String s = "PRE  FILE  UPDATE   /usr/ebank/buildit/app/cotb.ear/COTBPRE.war  "
						+ upstr;
				s += "\n";
				osw.write(s);
				osw.flush();
			}
			String s10001 = "                \n" + "#PRE  file add";
			s10001 += "\n";
			osw.write(s10001);
			osw.flush();
			for (String addstr : preAddlist) {
				String s = "PRE  FILE    ADD    /usr/ebank/buildit/app/cotb.ear/COTBPRE.war  "
						+ addstr;
				s += "\n\n";
				osw.write(s);
				osw.flush();
			}
			String s10002 = "                \n" + "#PRE  file delete";
			s10002 += "\n";
			osw.write(s10002);
			osw.flush();
			for (String delstr : preDellist) {
				String s = "PRE  FILE  DELETE   /usr/ebank/buildit/app/cotb.ear/COTBPRE.war  "
						+ delstr;
				s += "\n";
				osw.write(s);
				osw.flush();
			}
			System.out.println("PRE写出完成");
		} catch (Exception e) {
			System.out.println("导出失败!");
			e.printStackTrace();
		}finally {

			if (osw == null) {
				osw.close();
				}
		}
	}
	
//	public void main (List<UploadRcrd> list) throws Exception {
//		List<String> webAddlist = new ArrayList<String>();
//		List<String> webUpdateList = new ArrayList<String>();
//		List<String> webDellist = new ArrayList<String>();
//		List<String> bpAddlist = new ArrayList<String>();
//		List<String> bpUpdateList = new ArrayList<String>();
//		List<String> bpDellist = new ArrayList<String>();
//		List<String> preAddlist = new ArrayList<String>();
//		List<String> preUpdateList = new ArrayList<String>();
//		List<String> preDellist = new ArrayList<String>();
//		FileOutputStream fs = new FileOutputStream("F://file.list");
//		OutputStreamWriter osw = new OutputStreamWriter(fs, "GBK");
//
//		try {
//			List<String> dirList = new ArrayList<>();
//			Set<String> com = new TreeSet<>();
//			for (int i = 0; i < list.size(); i++) {
//				UploadRcrd rcrd = list.get(i);
//				if (rcrd.getChangeType().equals("新增")) {
//					if (rcrd.getBelongSys().equals("WEB")) {
//						dirList.add("WEB  COMMAND BACKUP mkdir -p      /opt/IBM/HTTPServer/htdocs" + " " + rcrd.getChangeContent());
//						dirList.add("WEB  COMMAND UPDATE chmod -R 755  /opt/IBM/HTTPServer/htdocs" + " " + rcrd.getChangeContent());
//					}else if (rcrd.getBelongSys().equals("PRE")) {
//						dirList.add("PRE  COMMAND BACKUP mkdir -p      /opt/IBM/HTTPServer/htdocs" + " " + rcrd.getChangeContent());
//						dirList.add("PRE  COMMAND UPDATE chmod -R 755  /opt/IBM/HTTPServer/htdocs" + " " + rcrd.getChangeContent());
//					}
//					else {
//						dirList.add("BP   COMMAND BACKUP mkdir -p      /opt/IBM/HTTPServer/htdocs" + " " + rcrd.getChangeContent());
//						dirList.add("BP   COMMAND BACKUP chmod -R 755  /opt/IBM/HTTPServer/htdocs" + " " + rcrd.getChangeContent());
//					}
//				}
//			}
//			for (String addDir : dirList) {
//				int j;
//				for (j = addDir.length(); j > 0; j--) {
//					char curr = addDir.charAt(j - 1);
//					if (curr == '/')
//						break;
//				}
//				String dir = addDir.substring(0, j);
//				com.add(dir);
//			}
//			String mkdir ="\n" + "#File Add : mkdir" + "\n\n";
//			osw.write(mkdir);
//			osw.flush();
//			for (String dir : com) {
//				dir += "\n";
//				osw.write(dir);
//				osw.flush();
//			}
//			for (int i = 0; i < list.size(); i++) {
//				UploadRcrd rcrd = list.get(i);
//				if (rcrd.getBelongSys().equals("WEB")) {
//					if (rcrd.getChangeType().equals("修改")) {
//						webUpdateList.add(rcrd.getChangeContent());
//					} else if (rcrd.getChangeType().equals("新增")) {
//						webAddlist.add(rcrd.getChangeContent());
//					} else if (rcrd.getChangeType().equals("删除")) {
//						webDellist.add(rcrd.getChangeContent());
//					}
//					
//				}else if (rcrd.getBelongSys().equals("BP")) {
//					if (rcrd.getChangeType().equals("修改")) {
//						bpUpdateList.add(rcrd.getChangeContent());
//					} else if (rcrd.getChangeType().equals("新增")) {
//						bpAddlist.add(rcrd.getChangeContent());
//					} else if (rcrd.getChangeType().equals("删除")) {
//						bpDellist.add(rcrd.getChangeContent());
//					}
//					
//				}else if (rcrd.getBelongSys().equals("PRE")){
//					if (rcrd.getChangeType().equals("修改")) {
//						preUpdateList.add(rcrd.getChangeContent());
//					} else if (rcrd.getChangeType().equals("新增")) {
//						preAddlist.add(rcrd.getChangeContent());
//					} else if (rcrd.getChangeType().equals("删除")) {
//						preDellist.add(rcrd.getChangeContent());
//					}
//					
//				}
//			}
//			String s10086 = "                   \n" + "#WEB  file   update";
//			s10086 += "\n";
//			osw.write(s10086);
//			osw.flush();
//			for (String upstr : webUpdateList) {
//				String s = "WEB  FILE   UPDATE   /opt/IBM/HTTPServer/htdocs  "
//						+ upstr;
//				s += "\n";
//				osw.write(s);
//				osw.flush();
//			}
//			
//			String s1008611 = "                   \n" + "#WEB    file add";
//			s1008611 += "\n";
//			osw.write(s1008611);
//			osw.flush();
//			for (String addstr : webAddlist) {
//				String s = "WEB  FILE   ADD      /opt/IBM/HTTPServer/htdocs  "
//						+ addstr;
//				s += "\n";
//				osw.write(s);
//				osw.flush();
//			}
//			String s1008610 = "                   \n" + "#WEB   file delete";
//			s1008610 += "\n";
//			osw.write(s1008610);
//			osw.flush();
//			for (String delstr : webDellist) {
//				String s = "WEB  FILE   DELETE   /opt/IBM/HTTPServer/htdocs  "
//						+ delstr;
//				s += "\n";
//				osw.write(s);
//				osw.flush();
//			}
//
//			System.out.println("WEB写出完成");
//			// 遍历bplist
//
//			String s10010 = "                   \n" + "#BP  file update";
//			s10010 += "\n";
//			osw.write(s10010);
//			osw.flush();
//			for (String upstr : bpUpdateList) {
//				String s = "BP  FILE   UPDATE   /usr/ebank/buildit/app  "
//						+ upstr;
//				s += "\n";
//				osw.write(s);
//				osw.flush();
//			}
//			String s10011 = "        \n" + "#BP  file add";
//			s10011 += "\n";
//			osw.write(s10011);
//			osw.flush();
//			for (String addstr : bpAddlist) {
//				String s = "BP  FILE   ADD      /usr/ebank/buildit/app  " + addstr;
//				s += "\n";
//				osw.write(s);
//				osw.flush();
//			}
//			String s10012 = "              \n" + "#BP  file delete";
//			s10012 += "\n";
//			osw.write(s10012);
//			osw.flush();
//			for (String delstr : bpDellist) {
//				String s = "BP  FILE   DELETE   /usr/ebank/buildit/app  "
//						+ delstr;
//				s += "\n";
//				osw.write(s);
//				osw.flush();
//			}
//			System.out.println("BP写出完成");
//			// 遍历preList
//			String s10000 = "                \n" + "#PRE  file update";
//			s10000 += "\n";
//			osw.write(s10000);
//			osw.flush();
//
//			for (String upstr : preUpdateList) {
//				String s = "PRE  FILE  UPDATE   /usr/ebank/buildit/app/cotb.ear/COTBPRE.war  "
//						+ upstr;
//				s += "\n";
//				osw.write(s);
//				osw.flush();
//			}
//			String s10001 = "                \n" + "#PRE  file add";
//			s10001 += "\n";
//			osw.write(s10001);
//			osw.flush();
//			for (String addstr : preAddlist) {
//				String s = "PRE  FILE    ADD    /usr/ebank/buildit/app/cotb.ear/COTBPRE.war  "
//						+ addstr;
//				s += "\n\n";
//				osw.write(s);
//				osw.flush();
//			}
//			String s10002 = "                \n" + "#PRE  file delete";
//			s10002 += "\n";
//			osw.write(s10002);
//			osw.flush();
//			for (String delstr : preDellist) {
//				String s = "PRE  FILE  DELETE   /usr/ebank/buildit/app/cotb.ear/COTBPRE.war  "
//						+ delstr;
//				s += "\n";
//				osw.write(s);
//				osw.flush();
//			}
//			System.out.println("PRE写出完成");
//		} catch (Exception e) {
//			System.out.println("导出失败!");
//			e.printStackTrace();
//		}finally {
//
//			if (osw == null) {
//				osw.close();
//				}
//		}
//	}

}
