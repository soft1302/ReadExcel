package com;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GETID {
	public static void main(String[] args) throws UnsupportedEncodingException {
		GETID getid = new GETID();
		long start = System.currentTimeMillis();
		for (int i = 900461836; i < 900461876; i++) {
			String result = getid.getID(i);
			if (result != null) {
				getid.saveId("D:\\id.txt", result);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start) / 1000 + "秒");
	}

	public void saveId(String file, String conent) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			out.write(conent + "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getID(int id) {
		try {
			URL url = new URL("http://www.somsds.com/detail.asp?id=" + id);
			String urlsource = getURLSource(url);
			String[] sectionsItem = urlsource.split("<TABLE");
			sectionsItem[1] = sectionsItem[1].substring(0, sectionsItem[1].indexOf("<table"));
			String[] sections = urlsource.split("<TR bgcolor=\"#FFFFFF\">");
			String[] sectionsItems = sections[1].split("<TD");
			String name = sectionsItems[2]
					.substring(sectionsItems[2].indexOf(">") + 1, sectionsItems[2].indexOf("&nbsp")).trim();
			if (name == null || name.equals("")) {
				return null;
			}
			return id + ":" + name;
		} catch (Exception e) {
			return null;
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
