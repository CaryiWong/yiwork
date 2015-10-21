<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新增广播</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.9.1.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body background="<%=basePath%>res/images/index_background.jpg">
	<div id="head">
		<jsp:include page="/res/common/header.jsp" />
	</div>
	<div id="main">
		<div id="left" style="display: inline-block;float: left;">
			<jsp:include page="/res/common/left.jsp" />
		</div>
		<div id="right"
			style="display: inline-block;float: left;position: absolute;width: 1200px;height: 620px;background-image: url('res/images/mainbox.png');">
			<form name="mainform" id="mainform" action="admin/user/savebroadcast"
				method="post">
				<table align="center" style="margin-top: 100px;">
					<tr>
						<td><label>标题<font color="red">*</font></label></td>
						<td><input id="title" name="title" style="width: 100%"/></td>
						<td><label>类型<font color="red">*</font></label></td>
						<td>
							<select id="istype" name="istype" style="width: 100%">
								<option value=0>活动</option>
								<option value=1>会员</option>
								<option value=2>需求</option>
								<option value=3>系统</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>内容</label></td>
						<td colspan="3">
							<textarea rows="10" cols="100" id="texts" name="texts"></textarea>
						</td>
					</tr>
					<tr align="center">
						<td colspan="2"><input type="submit" value="推送" /></td>
						<td colspan="2"><input type="button" value="取消" onclick="history.back(-1);" /></td>
					</tr>
					<c:if test="${tips ne null }">
						<tr align="center">
							<td colspan="6">${tips }</td>
						</tr>
					</c:if>
				</table>
			</form>
		</div>
	</div>
	<div id="foot" style="padding-top: 5px;float: left;">
		<jsp:include page="/res/common/footer.jsp" />
	</div>
</body>
</html>
