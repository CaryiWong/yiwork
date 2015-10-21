<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="com.tools.utils.SpringUtils,com.common.Page"%>
<%
	HttpServletRequest req = SpringUtils.getRequest();
  	Page<?> pageObj = null;
  	String p = (String)pageContext.getAttribute("p");
  	out.print("<div class=\"page clearfix\">");
  	out.print("<ul class=\"pagination pull-left\">");
  	
  	if(p==null || p.trim().isEmpty()) p= "page";
  	pageObj = (Page<?>)req.getAttribute(p);
  	if(pageObj!=null){
  		//out.print(pageObj.getTotalPage());
  		if(pageObj.getTotalPage()>1){
	  		if(pageObj.isHasPre()) 
	  			out.print("<li><a href=\"javascript:jumpPage("+pageObj.getPrePage()+")\">&lt;</a></li>");
			else 
				out.print("<li><a href=\"javascript:void("+pageObj.getPrePage()+")\">&lt;</a></li>");
			for(int i=pageObj.getCurrentPage()-2;i<pageObj.getCurrentPage()+3&&i<pageObj.getTotalPage()+1;i++){
				if(i<=0)
					continue;
				
				if(i!=pageObj.getCurrentPage())
					out.print("<li><a href=\"javascript:jumpPage("+i+")\">"+i+"</a></li>");
				else
					out.print("<li class=\"active\"><a href=\"javascript:void("+i+")\">"+i+"</a></li>");
			}
			if(pageObj.getTotalPage()>6){
				//out.print("...");  1.27
				//out.print("<a href=\"javascript:jumpPage("+pageObj.getTotalPage()+")\">"+pageObj.getTotalPage()+"</a>");
			}else if(pageObj.getTotalPage()==6){
				out.print("<li ><a  href=\"javascript:jumpPage("+pageObj.getTotalPage()+")\">"+pageObj.getTotalPage()+"</a></li>");
			}
			if(pageObj.isHasNext()) 
				out.print("<li><a  href=\"javascript:jumpPage("+pageObj.getNextPage()+")\">&gt;</a></li>");
			else 
				out.print("<li><a  href=\"javascript:void("+pageObj.getNextPage()+")\">&gt;</a></li>");
				//out.print("<li>&gt;</li>");
			out.print("</ul>");
	  		out.print("<div class=\"page-jump pull-right\">");
	  		out.print("共"+pageObj.getTotalCount()+"条,"+pageObj.getTotalPage()+"页,跳转至 <input id=\"jumpPageNo\" type=\"text\" /> 页");
			out.print("<input class=\"btn btn-sm btn-default\" type=\"button\" value=\"GO\" onclick=\"jumpPage($('#jumpPageNo').val(), "+pageObj.getTotalPage()+");\" />");
			out.print("</div>");
			out.print("</div>");
	  		
	  		//out.print("<div class=\"page_r\">");
	  		//out.print("共"+pageObj.getTotalCount()+"条;跳转至<input id=\"jumpPageNo\" type=\"text\" style=\"width: 40px;\" onkeyup=\"regNumReplace(this) \" onafterpaste=\"regNumReplace(this)\" /> 页&nbsp;");
			//out.print("<input class=\"newinput\" type=\"button\" value=\"GO\" onclick=\"jumpPage($('#jumpPageNo').val(), "+pageObj.getTotalPage()+");\" />");
			//out.print("</div>");
	  		//out.print("</div>");
			out.print("<input type=\"hidden\" name=\"currentPage\" id=\"currentPage\" value="+(pageObj.getCurrentPage()-1)+" />");
			out.print("<input type=\"hidden\" name=\"pageSize\" id=\"pageSize\" value="+pageObj.getPageSize()+" />");
			if(pageObj.getOrderBy()!=null) 
				out.print("<input type=\"hidden\" name=\""+p+".orderBy\" id=\"orderBy\" value="+pageObj.getOrderBy()+" />");
			if(pageObj.getOrder()!=null) 
				out.print("<input type=\"hidden\" name=\""+p+".order\" id=\"order\" value="+pageObj.getOrder()+" />");
		}else {
			out.print("<input type=\"hidden\" name=\"currentPage\" id=\"currentPage\" value=\""+(pageObj.getCurrentPage()-1)+"\" />");
			out.print("<input type=\"hidden\" name=\"pageSize\" id=\"pageSize\" value=\""+pageObj.getPageSize()+"\" />");
		}
	}
%>
