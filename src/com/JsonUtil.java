package com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

public class JsonUtil {
	private String[] str_sites = new String[] { "全部", "于桥水库", "引滦（暗渠）明渠", "尔王庄", "海河直取" };
	private static String[] sites_id = new String[] { "", "于桥水库", "引滦（暗渠）明渠", "尔王庄", "海河直取" };
	private static String[] areas_id = new String[] { "", "yls000000001", "yls000000002", "yls000000003",
			"yls000000004", "yls000000005", "yls000000006", "yls000000007", "yls000000008", "yls000000009", "yls000001",
			"yls000002", "yls000003", "700560092", "712823929", "103062677", "738479265", "103066846", "732818457" };
	public static String[] str_areas = { "全部", "大唐电厂", "国华电厂", "宝坻水厂", "东山水厂", "宜达水厂", "入塘管线", "入开管线", "入汉管线", "入杨管线",
			"入津滨管线", "入港管线", "入聚酯管线", "暗渠管线", "天津市毛织厂", "天津高力预一预应力钢纹线有限公司", "天津军粮城发电有限公司", "杨柳青电厂", "天津陈塘热电有限公司" };

	public static void main(String[] args) throws Exception {
		readExcel();
	}

	private static void readExcel() throws Exception {
		List<SelectModel> models = new ArrayList<>();
		InputStream is = new FileInputStream("D:\\soft.xlsx");
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0); // logical)
		XSSFRow xssfRow = null;
		Cell cell1 = null, cell2;
		for (int i = 0; i < xssfSheet.getLastRowNum(); i++) {
			xssfRow = xssfSheet.getRow(i); // (0-based)
			cell1 = xssfRow.getCell(0);
			cell2 = xssfRow.getCell(1);
			String data1 = cell1.getStringCellValue();
			String data2 = cell2.getStringCellValue();
			if (!"".equals(data1)) {
				SelectModel model = new SelectModel();
				model.setName(data1);
				model.getChild().add(data2);
				models.add(model);
			} else {
				models.get(models.size() - 1).getChild().add(data2);
			}
		}
		System.out.println(new Gson().toJson(models));
	}
}
