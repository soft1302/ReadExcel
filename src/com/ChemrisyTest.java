package com;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ChemrisyTest {

	public static void main(String[] args) throws Exception {
		String allTitles[] = { "化学品中文名称：", "化学品俗名：", "化学品英文名称：", "英文名称：", "技术说明书编码：", "CAS No.：", "生产企业名称：", "地址：",
				"生效日期：", "危险性类别：", "侵入途径：", "健康危害：", "环境危害：", "燃爆危险：", "皮肤接触：", "眼睛接触：", "吸入：", "食入：", "危险特性：",
				"有害燃烧产物：", "灭火方法：", "应急处理：", "操作注意事项：", "储存注意事项：", "中国MAC(mg/m3)：", "前苏联MAC(mg/m3)：", "TLVTN：",
				"TLVWN：", "监测方法：", "工程控制：", "呼吸系统防护：", "眼睛防护：", "身体防护：", "手防护：", "其他防护：", "外观与性状：", "pH：", "熔点(℃)：",
				"相对密度(水=1)：", "沸点(℃)：", "相对蒸气密度(空气=1)：", "分子式：", "分子量：", "主要成分：", "饱和蒸气压(kPa)：", "燃烧热(kJ/mol)：",
				"临界温度(℃)：", "临界压力(MPa)：", "辛醇/水分配系数的对数值：", "闪点(℃)：", "爆炸上限%(V/V)：", "引燃温度(℃)：", "爆炸下限%(V/V)：", "溶解性：",
				"主要用途：", "其它理化性质：", "稳定性：", "禁配物：", "避免接触的条件：", "聚合危害：", "分解产物：", "急性毒性：", "亚急性和慢性毒性：", "刺激性：", "致敏性：",
				"致突变性：", "致畸性：", "致癌性：", "生态毒理毒性：", "生物降解性：", "非生物降解性：", "生物富集或生物积累性：", "其它有害作用：", "废弃物性质：", "废弃处置方法：",
				"废弃注意事项：", "危险货物编号：", "UN编号：", "包装标志：", "包装类别：", "包装方法：", "运输注意事项：", "法规信息", "参考文献：", "填表部门：",
				"数据审核单位：", "修改说明：", "其他信息：" };
		String[] values = new String[allTitles.length + 2];
		URL url = new URL("http://www.somsds.com/detail.asp?id=52917442");
		String urlsource = getURLSource(url);
		// 截取内容
		urlsource = urlsource.substring(urlsource.indexOf("第一部分：化学品名称"), urlsource.lastIndexOf("<!--endprint-->"));
		System.out.println(urlsource);
		// 截取第二部分
		String secondSource = "";
		boolean flag = false;
		boolean flag2 = false;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(urlsource);
		urlsource = "";
		while (scanner.hasNextLine()) {
			String str = scanner.nextLine();
			if (str.indexOf("第二部分：成分/组成信息") > 0) {
				flag = true;
			}
			if (str.indexOf("第三部分：危险性概述") > 0) {
				flag2 = true;
			}
			if (flag) {
				if (flag2) {
					flag = false;
				} else {
					secondSource += str + "\n";
				}
			} else {
				urlsource += str + "\n";
			}
		}
		// 开始查找Id
		scanner = new Scanner(urlsource);
		int j = 0, index = 0;
		String value = "";
		flag = false;
		flag2 = false;
		while (scanner.hasNextLine() && j < allTitles.length) {
			if (!flag) {// 没找到标题
				String temp = scanner.nextLine();
				if (temp.indexOf("第十五部分：法规信息") > 0)
					continue;
				if (temp.indexOf(allTitles[j]) > 0) {
					flag = true;
				}
			}
			if (flag) {
				/* 单纯获取TD */
				String str = scanner.nextLine();
				if (str.indexOf("<TD") > 0 || str.indexOf("<td") > 0) {
					flag2 = true;
				}

				if (flag2 && str.indexOf("</td") > 0) {
					str = replaceTD(str);
					value += str.replace("&nbsp;", "").trim();
					// resultStr+=str.replace("&nbsp;", "").trim()+"\n";

					values[index] = value;
					// System.out.println(value);
					j++;
					if (index == 8) {
						index = 11;
					} else {
						index++;
					}
					value = "";
					flag = false;
					flag2 = false;
				} else {
					if (flag2) {
						str = replaceTD(str);
						value += str.replace("&nbsp;", "").trim();
						// resultStr+=str.replace("&nbsp;", "").trim();
					}
				}
			}

		}

		// System.out.println(resultStr);

		/*-----------------------*/

		// System.out.println(secondSource);
		// System.out.println("/*-----------第二部分------------*/");
		scanner = new Scanner(secondSource);

		flag = false;
		flag2 = false;
		int num = 0;
		while (scanner.hasNextLine()) {
			/* 单纯获取tr */
			String str = scanner.nextLine();
			if (str.indexOf("<tr>") > 0) {
				num++;
			}
			if (num == 2) {
				String str1 = replaceTD(scanner.nextLine());
				String str2 = replaceTD(scanner.nextLine());
				replaceTD(scanner.nextLine());
				values[9] = str1.trim();
				values[10] = str2.trim();
				/*
				 * System.out.println(str1.trim());
				 * System.out.println(str2.trim());
				 * System.out.println(str3.trim());
				 */
				break;
			}
		}

		for (int i = 0; i < values.length; i++) {
			System.out.println(i + "    " + values[i]);
		}

		/*
		 * if(!flag){//没找到标题 if(scanner.nextLine().indexOf(allTitles[j])>0){
		 * flag=true; } } if(flag){ //开始找td String str=scanner.nextLine();
		 * if(str.indexOf("<TD")>0 || str.indexOf("<td")>0){ flag2=true; }
		 * 
		 * if(flag2&&str.indexOf("</td")>0){ resultStr+=str+"\n";
		 * System.out.println("第"+j+"部分"+resultStr+"\n\n"); resultStr=""; j++;
		 * flag=false; flag2=false; //break;
		 * 
		 * }else{ resultStr+=str+"\n"; } }
		 */

		/*
		 * for (int i = 0; i < allTitles.length - 1; i++) { values[i] =
		 * urlsource.substring(0, urlsource.indexOf(allTitles[1])); urlsource =
		 * urlsource.substring(urlsource.indexOf(allTitles[1])); }
		 */

		// str.replace("<TD * >", arg1)
		// 要验证的字符串
		// String str2 = "service@xsoftlab.net";
		// 邮箱验证规则
		/*
		 * String regEx =
		 * "[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]+[\\w]+";
		 * // 编译正则表达式 Pattern pattern = Pattern.compile(regEx); // 忽略大小写的写法 //
		 * Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		 * Matcher matcher = pattern.matcher(str); // 字符串是否与正则表达式相匹配 //
		 * matcher.replaceAll(""); // boolean rs = matcher.matches();
		 * System.out.println(matcher.replaceAll(""));
		 * str=matcher.replaceAll("");
		 */

	}

	public static String replaceTD(String str) {
		int beginIndex, endIndex;
		beginIndex = str.indexOf("<");
		while (beginIndex > 0) {
			endIndex = str.indexOf(">");
			if (endIndex > 0) {
				str = str.replace(str.substring(beginIndex, endIndex + 1), "");
			} else {
				break;
			}
			beginIndex = str.indexOf("<");
		}
		return str;
	}

	/** */
	/**
	 * 通过网站域名URL获取该网站的源码
	 * 
	 * @param url
	 * @return String
	 * @throws Exception
	 */
	public static String getURLSource(URL url) throws Exception {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		InputStream inStream = conn.getInputStream(); // 通过输入流获取html二进制数据
		byte[] data = readInputStream(inStream); // 把二进制数据转化为byte字节数据
		String htmlSource = new String(data, "GBK");
		return htmlSource;
	}

	/** */
	/**
	 * 把二进制流转化为byte字节数组
	 * 
	 * @param instream
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream instream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1204];
		int len = 0;
		while ((len = instream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		instream.close();
		return outStream.toByteArray();
	}

}
