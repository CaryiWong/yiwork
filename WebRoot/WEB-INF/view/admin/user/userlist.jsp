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
    
    <title>用户列表</title>
    
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
<script type="text/javascript">
function checkin(userid,index){
	var checkin = $("#checkin"+index).val();
	var submitdata = {
		userid:userid,
		time:new Date(checkin)
	};
	$.ajax({
     type: "post",
     url: "<%=basePath%>admin/user/vipsignin",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord==0){
     		//alert("签到成功");
     		$("#checkbtn"+index).toggle();
     	}else{
     		alert("签到失败");
     	}
     }
    });
}
</script>
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
				<form id="mainForm" name="mainForm" action="admin/user/userlist" method="post">
					<div style="display: inline-block;float: left;width: 1000px;height: 100px;padding-left: 200px;">
						<div style="padding-left: 50px;">
							<label>标签</label>
							<c:forEach items="${labels }" var="label" varStatus="vs">
								<c:if test="${vs.index mod 10 eq 0 }"><br></c:if>
								<c:set var="la" value="${label.id}"></c:set>
								<input name="groups" type="checkbox" value="${label.id}" <c:if test="${fn:contains(labelList,la) }">checked="checked"</c:if> />${label.zname }
							</c:forEach>
						</div>
						<div style="padding-left: 50px;">
							<label>会员号</label>
							<input name="userNO" value="${userNO}" style="width: 100px" />
							<label>资费</label>
							<select name="charge">
								<option value="-1" <c:if test="${charge==null or charge eq -1 }">selected="selected"</c:if>>全部</option>
								<option value="0" <c:if test="${charge eq 0 }">selected="selected"</c:if>>管理员</option>
								<option value="1" <c:if test="${charge eq 1 }">selected="selected"</c:if>>已缴</option>
								<option value="2" <c:if test="${charge eq 2 }">selected="selected"</c:if>>未缴</option>
							</select>
							<label>性别</label>
							<select name="sex">
								<option value="-1" <c:if test="${sex==null or sex eq -1 }">selected="selected"</c:if>>全部</option>
								<option value="0" <c:if test="${sex eq 0 }">selected="selected"</c:if>>男</option>
								<option value="1" <c:if test="${sex eq 1 }">selected="selected"</c:if>>女</option>
								<option value="2" <c:if test="${sex eq 2 }">selected="selected"</c:if>>保密</option>
							</select>
							<label>关键字</label>
							<input name="keyword" value="${keyword }" maxlength="20" placeholder="关键字" style="width: 100px"/>
							<input type="submit" value="查询" style="font-size: 14px;width: 80px;height: 30px;"/>
						</div>
					</div>
					<div id="contentDiv" style="padding-left: 50px;float: !important;padding-top: 100px;">
						<table align="center" border="1">
							<thead>
								<tr style="height: 20px;">
									<th width="50">序号</th>
									<th>会员号</th>
									<th width="120">昵称</th>
									<th width="50">性别</th>
									<th width="50">权限</th>
									<th width="150">邮箱</th>
									<th width="150">电话</th>
									<th width="100">签入</th>
								</tr>
							</thead>
							<tbody align="left">
								<c:if test="${page.totalPage le 0 }">
									<tr align="center">
										<td colspan="10"><span>无记录</span></td>
									</tr>
								</c:if>
								<c:if test="${page.totalPage gt 0 }">
									<c:forEach items="${page.result }" var="userinfo" varStatus="vs">
										<tr>
											<td><a href="admin/user/updateuser?userid=${userinfo.uuid}">${(page.currentPage-1)*page.pageSize+vs.index+1}</a></td>
											<td>${userinfo.unum }</td>
											<td>${userinfo.basicinfo.nickname }</td>
											<td>
												<c:if test="${userinfo.basicinfo.sex eq 0 }">男</c:if>
												<c:if test="${userinfo.basicinfo.sex eq 1 }">女</c:if>
												<c:if test="${userinfo.basicinfo.sex eq 2 }">保密</c:if>
											</td>
											<td>
												<c:if test="${userinfo.root eq 0 }">管理员</c:if>
												<c:if test="${userinfo.root eq 1 }">会员</c:if>
												<c:if test="${userinfo.root eq 2 }">普通</c:if>
											</td>
											<td>${userinfo.basicinfo.email }</td>
											<td>${userinfo.basicinfo.tel }</td>
											<td>
												<input id="checkin${vs.index }" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="签入时间" style="width: 125"/>
												<c:if test="${userinfo.root eq 1}">
													<input id="checkbtn${vs.index }" type="button" value="签到" onclick="checkin('${userinfo.uuid}','${vs.index }');"/>
												</c:if>
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
