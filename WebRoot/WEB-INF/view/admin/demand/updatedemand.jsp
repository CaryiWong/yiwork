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
    
    <title>修改需求</title>
    
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
  	<div id="head">
  		<jsp:include page="/res/common/header.jsp" />
  	</div>
  	<div id="main">
	  	<div id="left" style="display: inline-block;float: left;">
	  		<jsp:include page="/res/common/left.jsp" />
	  	</div>
		<div id="right" style="display: inline-block;float: left;position: absolute;width: 1200px;height: 620px;background-image: url('../../res/images/mainbox.png');">
			<form name="mainform" id="mainform" action="<%=basePath%>admin/demand/savedemand"
				method="post" onsubmit="return validateform(this);">
				<input type="hidden" value="${demand.id }" name="id">
				<table align="center" style="margin-top: 50px;">
					<tr>
						<td colspan="4">
							<label>标签</label>
							<c:forEach items="${labels }" var="label" varStatus="vs">
								<c:if test="${vs.index mod 10 eq 0 }"><br></c:if>
								<c:set var="la" value="${label.id}"></c:set>
								<input name="groups" type="checkbox" value="${label.id}" <c:if test="${fn:contains(labelList,la) }">checked="checked"</c:if> />${label.zname }
							</c:forEach>						
						</td>
					</tr>
					<tr>
						<td><label>标题</label></td>
						<td><input style="width: 250;" id="title" name="title" value="${demand.title }"/></td>
						<td><label>关注数</label></td>
						<td>${demand.focusnum }</td>
					</tr>
					<tr>
						<td><label>发布者</label></td>
						<td>${demand.publishnickname }</td>
						<td><label>需求类型</label></td>
						<td>
							<c:if test="${demand.isneed eq 0 }">提供资源</c:if>
							<c:if test="${demand.isneed eq 1 }">需求资源</c:if>
						</td>
					</tr>
					<tr>
						<td><label>内容</label></td>
						<td colspan="3">
							<textarea rows="10" cols="20" name="texts" style="width: 100%;" >${demand.texts }</textarea>
						</td>
					</tr>
					<tr align="center">
						<td colspan="2"><input type="submit" value="保存" /></td>
						<td colspan="2"><input type="button" value="取消" onclick="history.back(-1);" /></td>
					</tr>
					<c:if test="${tips ne null }">
		    			<tr align="center">
		    				<td colspan="4">${tips }</td>
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
