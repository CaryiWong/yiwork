<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="com.tools.utils.SpringUtils,com.common.Page"%>
<%
	HttpServletRequest req = SpringUtils.getRequest();
  	Page<?> pageObj = null;
  	String p = (String)pageContext.getAttribute("p");
  	if(p==null || p.trim().isEmpty()) p= "page";
  	pageObj = (Page<?>)req.getAttribute(p);
  	if(pageObj!=null){
  		if(pageObj.getTotalPage()>1){
	  		out.print("<div class=\"pages\">");
	  		out.print("<div class=\"page_l\">");
	  		if(pageObj.isHasPre()) 
	  			out.print("<span><a class=\"links_a\" href=\"javascript:jumpPage("+pageObj.getPrePage()+")\">上一页</a></span>");
			else 
				out.print("<span>上一页</span>");
			for(int i=1;i<pageObj.getTotalPage()+1&&i<6;i++){
				if(i!=pageObj.getCurrentPage())
					out.print("<span><a href=\"javascript:jumpPage("+i+")\">"+i+"</a></span>");
				else
					out.print("<span>"+i+"</span>");
			}
			if(pageObj.getTotalPage()>6){
				out.print("...");
				out.print("<a href=\"javascript:jumpPage("+pageObj.getTotalPage()+")\">"+pageObj.getTotalPage()+"</a>");
			}else if(pageObj.getTotalPage()==6){
				out.print("<a class=\"links_a\" href=\"javascript:jumpPage("+pageObj.getTotalPage()+")\">"+pageObj.getTotalPage()+"</a>");
			}
			if(pageObj.isHasNext()) 
				out.print("<span><a class=\"links_a\" href=\"javascript:jumpPage("+pageObj.getNextPage()+")\">下一页</a></span>");
			else 
				out.print("<span>下一页</span>");
	  		out.print("</div>");
	  		out.print("<div class=\"page_r\">");
	  		out.print("共"+pageObj.getTotalCount()+"条;跳转至<input id=\"jumpPageNo\" type=\"text\" style=\"width: 40px;\" onkeyup=\"regNumReplace(this) \" onafterpaste=\"regNumReplace(this)\" /> 页&nbsp;");
			out.print("<input class=\"newinput\" type=\"button\" value=\"GO\" onclick=\"jumpPage($('#jumpPageNo').val(), "+pageObj.getTotalPage()+");\" />");
			out.print("</div>");
	  		out.print("</div>");
	  		
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
