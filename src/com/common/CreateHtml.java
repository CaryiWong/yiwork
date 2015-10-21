package com.common;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
public class CreateHtml {

	private String htmltext;
	private String url_path;
	private String bpath;

	  public String getBpath() {
		return bpath;
	}

	public void setBpath(String bpath) {
		this.bpath = bpath;
	}

	public String getHtmltext() {
		return htmltext;
	}

	public void setHtmltext(String htmltext) {
		this.htmltext = htmltext;
	}

	public String getUrl_path()
	  {
	    return this.url_path; }

	  public void setUrl_path(String urlPath) {
	    this.url_path = urlPath; }
	  
	  public CreateHtml(String htmltext,String path) {
		    this.htmltext = htmltext;
		    this.bpath=path;
		    try {
		      init();
		    } catch (Exception e) {
		    }
		  }

		  public static CreateHtml Instance(String htmltext,String path)
		  {
		    return new CreateHtml(htmltext,path);
		  }

		  private void init() {
		    StringBuilder path_url = new StringBuilder();
		    path_url.append("static");
		    Date todaytime = new Date();
		    SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		    SimpleDateFormat ttime = new SimpleDateFormat("yyyyMMddhhMMSS");
		    String name = ttime.format(todaytime);
		    String folder = date.format(todaytime);
	        File file1 = new File(new File(bpath+"/static"),new StringBuilder("/").append(folder).append("/").toString());
	        path_url.append("\\" + folder + "\\");
	        file1.mkdir();
		    String content = this.htmltext;
		    File file2 = new File(file1,new StringBuilder(String.valueOf(name)).append(".html").toString());
		    path_url.append(name + ".html");
		    StringBuilder sb = new StringBuilder();
		    try {
		      file2.createNewFile();
		      sb.append("<!doctype html><html lang='zh-CN' ><head>");
		      sb.append("<meta name='viewport' content='width=device-width, initial-scale=1.0,user-scalable=no'>");
		      sb.append("<link rel='stylesheet' type='text/css' href='../review.css'></style>");
		      sb.append("<body>");
		      sb.append(content);
		      sb.append("</body>");
		      sb.append("<!--[if IE]>");
		      sb.append("<script type='text/javascript' src='../jquery-1.9.1.min.js'></script>");
		      sb.append(" <![endif]-->");
		      sb.append(" <!--[if !IE]><!-->");
		      sb.append("<script type='text/javascript' src='../zepto.min.js'></script>");
		      sb.append(" <!--<![endif]-->");
		      sb.append("<script type='text/javascript' src='../review.js'></script>");
		      sb.append("</html>");
		      PrintStream printStream = new PrintStream(new FileOutputStream(file2),true,"utf-8");
		      printStream.println(sb.toString());
		      this.url_path = path_url.toString().replace("\\", "/");
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }

}
