package cn.yi.gather.v20.editor.comm;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
public class CreateHtml {

	private String htmltext; // 页面BODY text
	private String url_path;//目录
	private String bpath; //域名部分
	private String html_type; //h5 or imgtext
	private String createorupdate; //create   or  update
	private String sourseurl;//修改时 使用替换文件 需要原文件地址

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

	public String getUrl_path() {
	    return this.url_path; 
	}

	public void setUrl_path(String urlPath) {
	    this.url_path = urlPath; 
	}
		
	public String getHtml_type() {
		return html_type;
	}

	public void setHtml_type(String html_type) {
		this.html_type = html_type;
	}
	

	public String getCreateorupdate() {
		return createorupdate;
	}

	public void setCreateorupdate(String createorupdate) {
		this.createorupdate = createorupdate;
	}

	public String getSourseurl() {
		return sourseurl;
	}

	public void setSourseurl(String sourseurl) {
		this.sourseurl = sourseurl;
	}

	/**
	 * 创建  HTML 页面
	 * @param htmltext (BODY TEXT)
	 * @param path     (request.getServletContext().getRealPath("/"))
	 * @param htmltype (h5 or imgtext)
	 * @return
	 */
	public CreateHtml(String htmltext,String path,String htmltype,String createorupdate,String sourseurl) {
	    this.htmltext = htmltext;
	    this.bpath=path;
	    this.html_type=htmltype;
	    this.createorupdate=createorupdate;
	    this.sourseurl=sourseurl;
	    try {
	      update();
	    } catch (Exception e) {
	    	
	    }
	}
	/**
	 * 创建  HTML 页面
	 * @param htmltext (BODY TEXT)
	 * @param path     (request.getServletContext().getRealPath("/"))
	 * @param htmltype (h5 or imgtext)
	 * @return
	 */
	public CreateHtml(String htmltext,String path,String htmltype) {
	    this.htmltext = htmltext;
	    this.bpath=path;
	    this.html_type=htmltype;
	    try {
	    	create();
	    } catch (Exception e) {
	    	
	    }
	}
	
	/**
	 * 修改时使用
	 * @param htmltext
	 * @param path
	 * @param htmltype
	 * @param createorupdate
	 * @param sourseurl
	 * @return
	 */
	  public static CreateHtml Instance(String htmltext,String path,String htmltype,String createorupdate,String sourseurl) {
	    return new CreateHtml(htmltext,path,htmltype,createorupdate,sourseurl);
	  }
	  
	  public static CreateHtml Instance(String htmltext,String path,String htmltype) {
		    return new CreateHtml(htmltext,path,htmltype);
		  }

	  private void create() {
	    StringBuilder path_url = new StringBuilder();
	    if(html_type.equals("h5")){
	    	 path_url.append("h5");
	    }else if(html_type.equals("imgtext")){
	    	 path_url.append("imgtext");
	    }else if(html_type.equals("commhtml")){
	    	html_type="commhtml";
	    	path_url.append("commhtml");
	    }else{
	    	html_type="imgtext";
	    	path_url.append("imgtext");
	    }
	   
	    Date todaytime = new Date();
	    SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
	    SimpleDateFormat ttime = new SimpleDateFormat("yyyyMMddhhMMSS");
	    String name = ttime.format(todaytime);
	    String folder = date.format(todaytime);
        File file1 = new File(new File(bpath+"/"+html_type),new StringBuilder("/").append(folder).append("/").toString());
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
	      if(html_type.equals("imgtext")){
	    	  sb.append("<link rel='stylesheet' href='/styles/product/article.css'/>");
		      sb.append("<script type='text/javascript' src='/scripts/product/imgtext.js'></script> ");
		      sb.append("</head><body>");
	      }else if(html_type.equals("h5")){
	    	  sb.append("<link rel='stylesheet' href='/styles/product/interact.css'/>");
		      sb.append("<script type='text/javascript' src='/scripts/product/h5.js'></script> ");
		      sb.append("</head><body class='animateArea'>");
	      }else if(html_type.equals("commhtml")){
	    	  sb.append("</head><body>");
	      }
	      
	      sb.append(content);
	      if(html_type.equals("commhtml")){
	    	 sb.append("<script type='text/javascript'>");
	    	 sb.append("	window.addEventListener('message', function (e) {");
	    	 sb.append("		var data = e.data;");
	    	 sb.append("     	var result;");
	    	 sb.append("     	if(data === 'height'){");
	    	 sb.append("         	result = document.body.scrollHeight;");
	    	 sb.append("		} else if(data === 'html'){");
	    	 sb.append("			result = document.body.innerHTML;");
	    	 sb.append("		}");
	    	 sb.append("		window.parent.postMessage(result, '*');");
	    	 sb.append("	})");
	    	 sb.append("</script>");
	      }
	      sb.append("</body>");
	      sb.append("</html>");
	      PrintStream printStream = new PrintStream(new FileOutputStream(file2),true,"utf-8");
	      printStream.println(sb.toString());
	      this.url_path = path_url.toString().replace("\\", "/");
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }

	  private void update() {
	    String content = this.htmltext;
	    File file2 = new File(bpath+"/"+sourseurl);
	    StringBuilder sb = new StringBuilder();
	    try {
	      file2.createNewFile();
	      sb.append("<!doctype html><html lang='zh-CN' ><head>");
	      sb.append("<meta name='viewport' content='width=device-width, initial-scale=1.0,user-scalable=no'>");
	      if(html_type.equals("imgtext")){
	    	  sb.append("<link rel='stylesheet' href='/styles/product/article.css'/>");
		      sb.append("<script type='text/javascript' src='/scripts/product/imgtext.js'></script> ");
		      sb.append("</head><body>");
	      }else{
	    	  sb.append("<link rel='stylesheet' href='/styles/product/interact.css'/>");
		      sb.append("<script type='text/javascript' src='/scripts/product/h5.js'></script> ");
		      sb.append("</head><body class='animateArea'>");
	      }
	      sb.append(content);
	      sb.append("</body>");
	      sb.append("</html>");
	      PrintStream printStream = new PrintStream(new FileOutputStream(file2),true,"utf-8");
	      printStream.println(sb.toString());
	     this.url_path=this.sourseurl;
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
}
