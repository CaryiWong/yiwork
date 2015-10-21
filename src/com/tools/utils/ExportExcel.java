package com.tools.utils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;

import cn.yi.gather.v20.entity.Question;
import cn.yi.gather.v20.entity.User;
 
/***
 * @author lsf
 */
public class ExportExcel {
	/***************************************************************************
	 * @param fileName
	 *            EXCEL文件名称
	 * @param listTitle
	 *            EXCEL文件第一行列标题集合
	 * @param listContent
	 *            EXCEL文件正文数据集合
	 * @return
	 */
	public void ExportExcel(OutputStream out,List list) throws IOException {
		 // 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("报名列表");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 4000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 4000);
        sheet.setColumnWidth(7, 9000);
        
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("姓名");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 1);  
        cell.setCellValue("昵称");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 2);  
        cell.setCellValue("电话");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 3);  
        cell.setCellValue("邮箱");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 4);  
        cell.setCellValue("性别");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 5);  
        cell.setCellValue("是否会员");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 6);  
        cell.setCellValue("会员号");  
        cell.setCellStyle(style);  
        
        cell = row.createCell((short) 7);  
        cell.setCellValue("报名时间");  
        cell.setCellStyle(style);  
        
        
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
       // List list = new ArrayList();
  
        for (int i = 0; i < list.size(); i++)  
        {  
            row = sheet.createRow((int) i + 1);  
            User user = (User) list.get(i);  
            // 第四步，创建单元格，并设置值  
            row.createCell((short) 0).setCellValue(user.getRealname());  
            row.createCell((short) 1).setCellValue(user.getNickname());
            row.createCell((short) 2).setCellValue(user.getTelnum());  
            String sex ="女";
            if(user.getSex()==0)
            	sex="男";
            String root="";
            switch (user.getRoot()) {
			case 0:
				root="系统";
				break;
			case 1:
				root="管理员";
				break;
			case 2:
				root="会员";
				break;
			case 3:
				root="过期会员";
				break;
			case 4:
				root="注册会员";
				break;
			}
            
            row.createCell((short) 3).setCellValue(user.getEmail());  
            row.createCell((short) 4).setCellValue(sex);  
            row.createCell((short) 5).setCellValue(root);  
            row.createCell((short) 6).setCellValue(user.getUnum());  
            //row.createCell((short) 7).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getCreatedate()));  
            row.createCell((short) 7).setCellValue(user.getCompanyurl());
           
             
           
        }  

		wb.write(out);// 向外写出OutputStream out;
	}
	
	
	/**
	 * 2003版 excel 表格生成
	 * @param sheetName
	 * @param headers   二维数组  支持 一二级表头 arr[i][0] 为一级菜单 arr[i][j](j>0)开始为二级菜单
	 * @param beannames 填充数据对象  对应 表头 应当取值的属性名
	 * @param headercolumn  2级菜单传2   一级菜单传1
	 * @param dataset   填充数据级 
	 * @param dateformattype 时间格式 例如：(yyyy-MM-dd HH:mm:ss)
	 * @param clazz 填充数据对应的 类名
	 * @author ys
	 * @return
	 */
	public HSSFWorkbook createExcelWorkbook(String sheetName, String[][] headers,String[][] beannames,Class[] list_clazz, Integer headercolumn,
			Collection<T> dataset, String dateformattype,Class clazz){  
		HSSFWorkbook workbook = new HSSFWorkbook();  
		HSSFSheet sheet = workbook.createSheet(sheetName);  
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //左右居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
		
		HSSFCellStyle header_style = workbook.createCellStyle();
		header_style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //左右居中
		header_style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
		header_style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
		header_style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		header_style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
		header_style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
		header_style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
		header_style.setBorderTop(HSSFCellStyle.BORDER_THIN); 
		
		if(headercolumn!=null){
			for (int i = 0; i < headercolumn; i++) {
				sheet.createRow(i);
			}
		}else{
			sheet.createRow(0);
			headercolumn=1;
		}
		HSSFRow row = null;  
		HSSFCell cell =null;
		HSSFRichTextString text=null;
		Integer ls_cell=0;
		for (short i = 0; i < headers.length; i++) {
			if(headers[i].length>1){
				//一级菜单
				row = sheet.getRow(0); 
				cell = row.createCell(i+ls_cell);  
				text = new HSSFRichTextString(headers[i][0]);  
				sheet.setColumnWidth(i+ls_cell, text.length()*256*4);//设置单元格宽度
				CellRangeAddress region = new CellRangeAddress(0,0,(i+ls_cell),(i+ls_cell+headers[i].length-2));
				sheet.addMergedRegion(region);
				cell.setCellStyle(header_style); 
				cell.setCellValue(text);
				//二级菜单
				row = sheet.getRow(1); 
				for (int j = 1; j < headers[i].length; j++) {
					cell = row.createCell(i+ls_cell+j-1);  
					text = new HSSFRichTextString(headers[i][j]);  
					sheet.setColumnWidth(i+ls_cell+j-1, text.length()*256*4);//设置单元格宽度
					cell.setCellStyle(header_style); 
					cell.setCellValue(text);
				}
				ls_cell=ls_cell+headers[i].length-2;
			}else{
				row = sheet.getRow(0); 
				cell = row.createCell(i+ls_cell);  
				text = new HSSFRichTextString(headers[i][0]); 
				sheet.setColumnWidth(i+ls_cell, text.length()*256*4);//设置单元格宽度
				if(text.length()>5){
					sheet.setColumnWidth(i+ls_cell, text.length()*256*2);//设置单元格宽度			
				}
				CellRangeAddress region = new CellRangeAddress(0, headercolumn-1, i+ls_cell, i+ls_cell);
				sheet.addMergedRegion(region);
				cell.setCellStyle(header_style); 
				cell.setCellValue(text);
			}
		}
		// 遍历集合数据，产生数据行  
		if(dataset!=null){
			try{
				Iterator<T> it = dataset.iterator();
				int rowNum=headercolumn;
				String fieldName="";
				Object value=null;
				Object o=null;
				PropertyDescriptor pd=null;
				Method readMethod=null;
				String str="";
				Map lsmap=new HashMap<String, String>();
				while (it.hasNext()){ 
					o=it.next();
					row=sheet.createRow(rowNum);
					int ls_cell_num=0;
					int clazz_num=0;
					//利用反射取值
					for (int i = 0; i < beannames.length; i++) {
						cell=row.createCell(i+ls_cell_num);
						fieldName=beannames[i][0];
						if(fieldName.indexOf(".")>0){//方便取 引用对象里面的值
							String fname1=fieldName.substring(0, fieldName.indexOf("."));
							String fname2=fieldName.substring((fieldName.indexOf(".")+1));
							pd = new PropertyDescriptor(fname1, clazz);
							readMethod = pd.getReadMethod();
							Object o1=readMethod.invoke(o);
							PropertyDescriptor pd2 = new PropertyDescriptor(fname2, pd.getPropertyType());
							readMethod = pd2.getReadMethod();
							value =readMethod.invoke(o1);
							if(fieldName.equals("user.sex")){
								if(value.toString().equals("0")){
									value="男";
								}else{
									value="女";
								}
							}
							if(fieldName.equals("user.root")){
								if(value.toString().equals("2")){
									value="会员";
								}else if(value.toString().equals("1")){
									value="管理员";
								}else{
									value="非会员";
								}
							}
						}else{
							pd = new PropertyDescriptor(fieldName, clazz);
							readMethod = pd.getReadMethod();
							value = readMethod.invoke(o);
						}
						if(beannames[i].length>1&& value instanceof List&& list_clazz!=null&&list_clazz.length>0){
							List list=(List) value;
							if(list!=null&&list.size()>0){
								for (int k = 0; k < list.size(); k++) {
									//临时处理方法：
									Question question=(Question) list.get(k);
									if(lsmap!=null&&lsmap.get(question.getNum())!=null&&lsmap.get(question.getNum()).equals("Y")){
										
									}else{
										lsmap.put(question.getNum(), "Y");
										HSSFCell h_cell=sheet.getRow(1).createCell(i+Integer.parseInt(question.getNum()));
										header_style.setWrapText(true);
										h_cell.setCellStyle(header_style);
										h_cell.setCellValue(question.getQuestiontext());
										if(i<i+Integer.parseInt(question.getNum())){
											CellRangeAddress region = new CellRangeAddress(0, 0, i, i+Integer.parseInt(question.getNum()));
											sheet.addMergedRegion(region);
										}
									}
									cell=row.createCell(i+Integer.parseInt(question.getNum()));
									value=question.getAnswertext();
									if(value==null){
										value="";
									}
									str=value.toString();
									if(str.length()>10){
										sheet.setColumnWidth(i+ls_cell_num, 256*36);//设置单元格宽度
										style.setWrapText(true);
									}
									cell.setCellStyle(style);
									cell.setCellValue(value.toString());
									/*for(int j=1;j<beannames[i].length;j++){
										cell=row.createCell(i+Integer.parseInt(question.getNum()));
										fieldName=beannames[i][j];
										pd = new PropertyDescriptor(fieldName, list_clazz[clazz_num]);
										readMethod = pd.getReadMethod();
										value = readMethod.invoke(list.get(k));
										if(value instanceof java.util.Date){
											SimpleDateFormat sdf=new SimpleDateFormat(dateformattype);
											value=sdf.format(value);
										}
										if(value==null){
											value="";
										}
										str=value.toString();
										if(str.length()>10){
											sheet.setColumnWidth(i+ls_cell_num, str.length()*256*2);//设置单元格宽度
											style.setWrapText(true);
										}
										cell.setCellStyle(style);
										cell.setCellValue(value.toString());
										
									}*/
									ls_cell_num++;
								}
							}
							clazz_num++;
						}else{
							if(value instanceof java.util.Date){
								SimpleDateFormat sdf=new SimpleDateFormat(dateformattype);
								value=sdf.format(value);
							}
							if(value==null){
								value="";
							}
							str=value.toString();
							if(str.length()>10){
								sheet.setColumnWidth(i+ls_cell_num,256*36);//设置单元格宽度			
								style.setWrapText(true);
							}
							cell.setCellStyle(style);
							cell.setCellValue(value.toString());
						}
					}
					rowNum++;
				}
			}catch(Exception e){
				e.printStackTrace();
			}

		}
		return workbook;
	} 
 
}
