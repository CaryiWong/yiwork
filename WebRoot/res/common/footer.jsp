<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div>
	<img alt="" src="<%=basePath%>res/images/foot_line.png">
</div>
<div style="text-align: center;font-weight: bold;">
	<p>
	Yi-Gather Co.,Ltd.<br/>
	联系电话:020-88888888<br/>
	email:Yi-Gether@yi.com.cn
	</p>
</div>