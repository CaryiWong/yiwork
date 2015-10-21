<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../res/common/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登陆首页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body background="<%=basePath%>res/images/index_background.jpg">
    <form name="mainForm" action="admin/user/login" method="post">
    	<table align="center" background="<%=basePath%>res/images/index_loginbox.png" style="height: 273px;width: 446px;margin-top: 185px;">
    		<tr>
    			<td colspan="2" align="center" style="color: green;font-size: 28">
    				<span>"一起开工"管理后台</span>
    			</td>
    		</tr>
    		<tr>
    			<td colspan="2" align="center">
    				<img alt="" src="<%=basePath%>res/images/index_line.png">
    			</td>
    		</tr>
    		<c:if test="${tips ne null }">
    			<tr align="center">
    				<td colspan="2">${tips }</td>
    			</tr>
    		</c:if>
    		<tr align="left">
    			<td colspan="2">
    				<input type="text" title="帐号" placeholder="帐号" name="username" value="" style="margin-left: 58px;padding-top: 10px;width: 188px; height: 38px;"/>
    			</td>
    		</tr>
    		<tr align="left">
    			<td colspan="2">
    				<input type="password" title="密码" placeholder="密码" name="password" value="" style="margin-left: 58px;padding-top: 10px;width: 188px; height: 38px;"/>
    			</td>
    		</tr>
    		<tr>
    			<td>
    				<input type="text" title="验证码" placeholder="验证码" name="code" value="" style="margin-left: 58px;padding-top: 10px;width: 94px; height: 38px;" maxlength="4"/>
    				<img align="top" src="<%=basePath%>kaptcha.jpg" id="codeID" title="点击重新获取" onclick="this.src=document.getElementById('codeID').src + '?t=' + new Date().getTime();"/>
    			</td>
    			<td >
    				<button type="submit" name="login" style="width: 100px;height: 50px;"><font style="color: blue;">登陆</font></button>
    			</td>
    		</tr>
    	</table>
    </form>
  </body>
</html>
