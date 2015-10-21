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
	<meta http-equiv="cache-control0" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
  </head>
  
<body>
   
     <div class="container">
        <div class="row">
            <div class="col-xs-4 col-xs-offset-4 admin">
                <h1 class="text-center admin-form-title">Yi-gather</h1>
				<form name="mainForm" action="<%=basePath%>v2/admin/user/login" method="post" class="admin-form">
				<c:if test="${tips ne null }">
    			<tr align="center">
    				<td colspan="2"><font color="red">${tips }</font></td>
    			</tr>
    			</c:if>
                    <div class="form-group">
                        <input type="text" class="form-control" name="username" value="" placeholder="账号">
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="password" value=""  placeholder="密码">
                    </div>
                    <div class="form-group admin-code-group">
                        <input type="text" class="form-control" name="code" value="" placeholder="验证码">
                        <img src="<%=basePath%>kaptcha.jpg" id="codeID" title="点击重新获取" onclick="this.src=document.getElementById('codeID').src + '?t=' + new Date().getTime();"/>
                    </div>
                    <div class="form-group">
                        <button type="submit" name="login" class="btn btn-primary btn-lg col-xs-8 col-xs-offset-2">登录</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    
    
    
  </body>
</html>
