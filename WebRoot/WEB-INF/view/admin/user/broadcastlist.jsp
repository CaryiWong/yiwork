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
    
    <title>广播列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
	<link  rel="stylesheet"  href="<%=basePath%>res/css/page.css" />
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
		<div id="right" style="display: inline-block;position: absolute;float: left;width: 1200px;height: 620px;background-image: url('res/images/mainbox.png');">
			<div id="conditionDiv" style="padding-top: 20px;">
				<form id="mainForm" name="mainForm" action="admin/user/broadcastlist" method="post">
					<div style="display: inline-block;float: left;width: 1000px;height: 100px;padding-left: 200px;">
						<span style="font-size: 18px;font-weight: bold;padding-left: 100px;">搜索条件：</span>
						<div style="padding-left: 50px;">
							<label>关键字</label>
							<input name="keyword" value="${keyword }" maxlength="20" placeholder="关键字" style="width: 100px"/>
							<input type="submit" value="查询" style="font-size: 14px;width: 80px;height: 30px;"/>
						</div>
						<div>
							<label><a href="<%=basePath%>admin/user/createbroadcast">新增广播</a></label>
						</div>
					</div>
					<div id="contentDiv" style="padding-left: 50px;float: !important;padding-top: 50px;">
						<table align="center" border="1">
							<thead>
								<tr style="height: 25px;">
									<th width="50">序号</th>
									<th width="120">标题</th>
									<th width="150">广播时间</th>
								</tr>
							</thead>
							<tbody align="left">
								<c:if test="${page.totalPage le 0 }">
									<tr align="center">
										<td colspan="10"><span>无记录</span></td>
									</tr>
								</c:if>
								<c:if test="${page.totalPage gt 0 }">
									<c:forEach items="${page.result }" var="sysnetwork" varStatus="vs">
										<tr>
											<td>${(page.currentPage-1)*page.pageSize+vs.index+1}</td>
											<td>${sysnetwork.title }</td>
											<td>
												<fmt:formatDate value="${sysnetwork.createdate1 }" pattern="yyyy-MM-dd HH:mm:ss"/>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
						<%@ include file="/res/common/page.jsp"%>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div id="foot" style="padding-top: 5px;float: left;">
		<jsp:include page="/res/common/footer.jsp" />
	</div>
</body>
</html>
