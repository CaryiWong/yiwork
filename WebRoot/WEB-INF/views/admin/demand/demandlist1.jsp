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
    
    <title>需求列表</title>
    
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
				<form id="mainForm" name="mainForm" action="admin/demand/demandlist" method="post">
					<div style="display: inline-block;float: left;width: 1000px;height: 100px;padding-left: 200px;">
						<span style="font-size: 18px;font-weight: bold;padding-left: 100px;">搜索条件：</span>
						<div style="padding-left: 50px;">
							<label>需求状态</label>
							<select name="status">
								<option value="-1" <c:if test="${status==null or status eq -1 }">selected="selected"</c:if>>全部</option>
								<option value="0" <c:if test="${status eq 0 }">selected="selected"</c:if>>待解决</option>
								<option value="1" <c:if test="${status eq 1 }">selected="selected"</c:if>>解决中</option>
								<option value="2" <c:if test="${status eq 2 }">selected="selected"</c:if>>已解决</option>
							</select>
							<label>关键字</label>
							<input name="keyword" value="${keyword }" maxlength="20" placeholder="关键字" style="width: 100px"/>
							<input type="submit" value="查询" style="font-size: 14px;width: 80px;height: 30px;"/>
						</div>
						<div style="padding-left: 50px;">
							<label>标签</label>
							<c:forEach items="${labels }" var="label" varStatus="vs">
								<c:if test="${vs.index mod 10 eq 0 }"><br></c:if>
								<c:set var="la" value="${label.id}"></c:set>
								<input name="groups" type="checkbox" value="${label.id}" <c:if test="${fn:contains(labelList,la) }">checked="checked"</c:if> />${label.zname }
							</c:forEach>
						</div>
					</div>
					<div id="contentDiv" style="padding-left: 50px;float: !important;padding-top: 120px;">
						<table align="center" border="1">
							<thead>
								<tr>
									<th width="50">序号</th>
									<th width="150">标题</th>
									<th width="120">发布者</th>
									<th width="200">发布时间</th>
									<th width="50">状态</th>
								</tr>
							</thead>
							<tbody align="center">
								<c:if test="${page.totalPage le 0 }">
									<tr>
										<td colspan="10"><span>无记录</span></td>
									</tr>
								</c:if>
								<c:if test="${page.totalPage gt 0 }">
									<c:forEach items="${page.result }" var="demands" varStatus="vs">
										<tr>
											<td>${(page.currentPage-1)*page.pageSize+vs.index+1}</td>
											<td>
												<a href="admin/demand/updatedemand?demandid=${demands.id }">${demands.title }</a>
											</td>
											<td>${demands.publishnickname }</td>
											<td>
												<fmt:formatDate value="${demands.publishdate1 }" pattern="yyyy-MM-dd"/>
											</td>
											<td>
												<c:if test="${demands.status eq 0 }">待解决</c:if>
												<c:if test="${demands.status eq 1 }">解决中</c:if>
												<c:if test="${demands.status eq 2 }">已解决</c:if>
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
