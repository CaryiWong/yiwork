package com.tools.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test {
	public static void main(String[] args) {
		File dir = new File("D:/report");
		File target = new File(dir, "workbooktest.xlsx");
		if(!dir.exists())
			dir.mkdir();
		try {
			Workbook work = new XSSFWorkbook();
			// 新建excel的第0张表
			Sheet sheet0 = work.createSheet("花名册");
			// 这里面的行和列的数法与计算机里的一样，从0开始是第一
			// 填充title数据
			Row row = sheet0.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("2014年花名册");
			// 合并单元格,first row,last row,first column,last column
			sheet0.addMergedRegion(new CellRangeAddress(0, 1, 0, 2));
			CellStyle style = work.createCellStyle();
			Font titleFont = work.createFont();
			titleFont.setFontName("宋体");
			titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//粗体显示
			titleFont.setFontHeightInPoints((short) 16);
			style.setAlignment(XSSFCellStyle.VERTICAL_CENTER);
			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style.setFont(titleFont);
			cell.setCellStyle(style);
			int i = 2;
			int number = 0;
			// 得到行，并填充数据和表格样式
			for (; i < 10; i++) {
				row = sheet0.createRow(i);// 得到行
				cell = row.createCell(0);// 得到第0个单元格
				cell.setCellValue("琳" + i);// 填充值
				cell = row.createCell(1);
				cell.setCellValue("女");
				cell = row.createCell(2);
				cell.setCellValue(i + 20);
				// .....给每个单元格填充数据和样式
				number++;
			}
			// 创建每个单元格，添加样式，最后合并
			row = sheet0.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue("总计：" + number + "个学生");// 填充值
			cell = row.createCell(1);
			cell = row.createCell(2);
			sheet0.addMergedRegion(new CellRangeAddress(i, i, 0, 2));
			FileOutputStream os = new FileOutputStream(target);
			work.write(os);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			System.out.println("文件路径错误");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("文件输入流错误");
			e.printStackTrace();
		}
	}
}
