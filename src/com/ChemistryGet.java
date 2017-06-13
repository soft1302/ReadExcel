package com;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 通过网站域名URL获取该网站的源码
 * 
 * @author Administrator
 * 
 */
public class ChemistryGet {
	private static String allTitles[] = { "化学品中文名称：", "化学品俗名：", "化学品英文名称：", "英文名称：", "技术说明书编码：", "CAS No.：", "生产企业名称：",
			"地址：", "生效日期：", "有害物成分", "含量", "CAS No.：", "危险性类别：", "侵入途径：", "健康危害：", "环境危害：", "燃爆危险：", "皮肤接触：", "眼睛接触：",
			"吸入：", "食入：", "危险特性：", "有害燃烧产物：", "灭火方法：", "应急处理：", "操作注意事项：", "储存注意事项：", "中国MAC(mg/m3)：", "前苏联MAC(mg/m3)：",
			"TLVTN：", "TLVWN：", "监测方法：", "工程控制：", "呼吸系统防护：", "眼睛防护：", "身体防护：", "手防护：", "其他防护：", "外观与性状：", "pH：",
			"熔点(℃)：", "相对密度(水=1)：", "沸点(℃)：", "相对蒸气密度(空气=1)：", "分子式：", "分子量：", "主要成分：", "饱和蒸气压(kPa)：", "燃烧热(kJ/mol)：",
			"临界温度(℃)：", "临界压力(MPa)：", "辛醇/水分配系数的对数值：", "闪点(℃)：", "爆炸上限%(V/V)：", "引燃温度(℃)：", "爆炸下限%(V/V)：", "溶解性：",
			"主要用途：", "其它理化性质：", "稳定性：", "禁配物：", "避免接触的条件：", "聚合危害：", "分解产物：", "急性毒性：", "亚急性和慢性毒性：", "刺激性：", "致敏性：",
			"致突变性：", "致畸性：", "致癌性：", "生态毒理毒性：", "生物降解性：", "非生物降解性：", "生物富集或生物积累性：", "其它有害作用：", "废弃物性质：", "废弃处置方法：",
			"废弃注意事项：", "危险货物编号：", "UN编号：", "包装标志：", "包装类别：", "包装方法：", "运输注意事项：", "法规信息", "参考文献：", "填表部门：", "数据审核单位：",
			"修改说明：", "其他信息：" };

	public static void main(String[] args) throws Exception {
		String url = "http://www.somsds.com/detail.asp?id=52917442";
		String[] values = getResults(url);
		for (int i = 0; i < values.length; i++) {
			System.out.println(allTitles[i] + "---->" + values[i]);
		}
	}

	public static String[] getResults(String str) throws Exception {
		String[] values = new String[allTitles.length];
		URL url = new URL(str);
		String urlsource = getURLSource(url);
		urlsource = urlsource.substring(urlsource.indexOf("<TABLE"));
		urlsource = urlsource.replace("<br>", "");
		urlsource = urlsource.replace("<sub>", "");
		urlsource = urlsource.replace("</sub>", "");
		urlsource = urlsource.replace("<sup>", "");
		urlsource = urlsource.replace("</sup>", "");
		String[] sectionsItem = urlsource.split("<TABLE");
		String[] sectionsItems = null;
		// 第一部分
		sectionsItem[1] = sectionsItem[1].substring(0, sectionsItem[1].indexOf("<table"));
		getDatas(values, sectionsItem[1], -1, 7);
		// 第二部分
		sectionsItem[2] = sectionsItem[2].substring(sectionsItem[2].indexOf("<table"),
				sectionsItem[2].indexOf("</table>"));
		sectionsItems = sectionsItem[2].split("<tr");
		sectionsItems = sectionsItems[2].split("<td");
		// 第一个
		values[9] = sectionsItems[1].substring(sectionsItems[1].indexOf(">") + 1, sectionsItems[1].indexOf("<")).trim();
		// 第二个
		values[10] = sectionsItems[2].substring(sectionsItems[2].indexOf(">") + 1, sectionsItems[2].indexOf("<"))
				.trim();
		// 第三个
		values[11] = sectionsItems[3].substring(sectionsItems[3].indexOf(">") + 1, sectionsItems[3].indexOf("<"))
				.trim();
		// 第三部分
		sectionsItem[3] = sectionsItem[3].substring(0, sectionsItem[3].indexOf("</table>"));
		getDatas(values, sectionsItem[3], 11, 6);
		// 第四部分
		sectionsItem[4] = sectionsItem[4].substring(0, sectionsItem[4].indexOf("</table>"));
		getDatas(values, sectionsItem[4], 16, 5);
		// 第五部分
		sectionsItem[5] = sectionsItem[5].substring(0, sectionsItem[5].indexOf("</table>"));
		getDatas(values, sectionsItem[5], 20, 4);
		// 第六部分
		sectionsItem[6] = sectionsItem[6].substring(0, sectionsItem[6].indexOf("</table>"));
		getDatas(values, sectionsItem[6], 23, 2);
		// 第七部分
		sectionsItem[7] = sectionsItem[7].substring(0, sectionsItem[7].indexOf("</table>"));
		getDatas(values, sectionsItem[7], 24, 3);
		// 第八部分
		// 第9部分重合
		String temp9 = sectionsItem[8];
		sectionsItem[8] = sectionsItem[8].substring(0, sectionsItem[8].indexOf("</table>"));
		getDatas(values, sectionsItem[8], 26, 12);
		// 第九部分
		temp9 = temp9.substring(temp9.indexOf("第九部分：理化特性"));
		temp9 = temp9.substring(temp9.indexOf("<table"));
		temp9 = temp9.substring(0, temp9.indexOf("</table"));
		getDatas(values, temp9, 37, 15);
		// 第十部分
		sectionsItem[9] = sectionsItem[9].substring(0, sectionsItem[9].indexOf("</table>"));
		getDatas(values, sectionsItem[9], 58, 6);
		// 第十一部分
		sectionsItem[10] = sectionsItem[10].substring(0, sectionsItem[10].indexOf("</table>"));
		getDatas(values, sectionsItem[10], 63, 8);
		// 第十二
		sectionsItem[11] = sectionsItem[11].substring(0, sectionsItem[11].indexOf("</table>"));
		getDatas(values, sectionsItem[11], 70, 6);
		// 第十三
		sectionsItem[12] = sectionsItem[12].substring(0, sectionsItem[12].indexOf("</table>"));
		getDatas(values, sectionsItem[12], 75, 4);
		// 第十四
		sectionsItem[13] = sectionsItem[13].substring(0, sectionsItem[13].indexOf("</table>"));
		getDatas(values, sectionsItem[13], 78, 7);
		// 第十五
		sectionsItem[14] = sectionsItem[14].substring(0, sectionsItem[14].indexOf("</table>"));
		getDatas(values, sectionsItem[14], 84, 2);
		// 第十六
		sectionsItem[15] = sectionsItem[15].substring(0, sectionsItem[15].indexOf("</table>"));
		getDatas(values, sectionsItem[15], 85, 6);
		return values;
	}

	/**
	 * 获取数据
	 * 
	 * @param values
	 *            获取数据后存放的数组
	 * @param datas
	 *            源表格数据
	 * @param n
	 *            存放数组位置
	 * @param num
	 *            表格行数+1
	 */
	public static void getDatas(String values[], String datas, int n, int num) {
		String[] sections = null;
		String[] sectionsItems = null;
		String reg = "</td";
		String split = "";
		if (datas.contains("<TR")) {
			split = "<TD";
			sections = datas.split("<TR");
		} else {
			split = "<td";
			sections = datas.split("<tr");
		}
		for (int i = 1; i < num; i++) {
			sectionsItems = sections[i].split(split);
			if (sectionsItems[2].contains("</a>")) { // 若有链接
				sectionsItems[2] = sectionsItems[2].substring(0, sectionsItems[2].indexOf("</a>"));
				values[n + i] = sectionsItems[2].substring(sectionsItems[2].lastIndexOf(">") + 1).trim();
				// 判断是否有代码空格
				if (values[i + n].contains("&nbsp;")) {
					values[i + n] = values[i + n].replace("&nbsp;", "").trim();
				}
			} else {
				values[i + n] = sectionsItems[2]
						.substring(sectionsItems[2].indexOf(">") + 1, sectionsItems[2].indexOf(reg)).trim();
				if (values[i + n].contains("&nbsp;")) {
					values[i + n] = values[i + n].replace("&nbsp;", "").trim();
				}
				if (sectionsItems.length >= 4) {
					n++;
					values[i + n] = sectionsItems[4]
							.substring(sectionsItems[4].indexOf(">") + 1, sectionsItems[4].indexOf(reg)).trim();
					if (values[i + n].contains("&nbsp;")) {
						values[i + n] = values[i + n].replace("&nbsp;", "").trim();
					}
				}
			}
		}

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