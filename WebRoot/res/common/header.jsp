<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp" %>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String nowtime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()); 
%>
<div style="float: left;padding-left: 130px;">
	<img alt="logo" src="<%=basePath%>res/images/logo.jpg">
</div>
<div style="float: right;margin-right: 50px;">
	<span style="font-size: 20px;">welcome back!<strong>${session_user.basicinfo.nickname }</strong></span>
	<br/>
	<span>现在是:<%=nowtime %><a href="<%=basePath%>v2/admin/user/logout" style="font-size: 20;">[退出]</a></span>
</div>
<div>
	<img alt="" src="<%=basePath%>res/images/head_line.png">
</div>