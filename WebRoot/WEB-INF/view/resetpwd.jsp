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
    
    <title>密码重置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.9.1.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
function validateform(){
	var newpwd = $("#newpwd").val();
	var newpwd1 = $("#newpwd1").val();
	if(newpwd==""||newpwd1==""){
		alert("密码不能为空");
		return false;
	}else if(newpwd!=newpwd1){
		alert("两次密码输入不一致");
		return false;
	}else{
		return true;
	}
}
</script>
  </head>
  
  <body>
  	<form action="<%=basePath %>user/setmypwd" method="post" onsubmit="return validateform();">
  		<input id="userid" name="userid" value="${userid}" type="hidden"/> 
	    <table>
	    	<tr>
	    		<td>请输入新密码：</td>
	    		<td><input id="newpwd" name="newpwd" value=""/></td>
	    	</tr>
	    	<tr>
	    		<td>请确认新密码：</td>
	    		<td><input id="newpwd1" name="newpwd1" value=""/></td>
	    	</tr>
	    	<tr>
	    		<td>
	    			<input type="submit" value="OK " /> 
	    		</td>
	    	</tr>
	    </table>
  	</form>
  </body>
</html>
