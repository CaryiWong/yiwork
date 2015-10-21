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
    
    <title>在线会员列表</title>
    
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
function checkout(userid,index){
	var time = $("#checkout"+index).val();
	var submitdata = {
		userid:userid,
		time:new Date(time)
	};
	$.ajax({
     type: "post",
     url: "<%=basePath%>admin/user/vipsignout",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord==0){
     		//alert("签出成功");
     		$("#checkbtn"+index).toggle();
     	}else{
     		alert("签出失败");
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
				<form id="mainForm" name="mainForm" action="admin/user/checkinlist" method="post">
					<div style="display: inline-block;float: left;width: 1000px;height: 100px;padding-left: 200px;">
						<span style="font-size: 18px;font-weight: bold;padding-left: 100px;">搜索条件：</span>
						<div style="padding-left: 50px;">
							<label>会员号</label>
							<input name="userNO" value="${userNO}" style="width: 100px" />
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
					<div id="contentDiv" style="padding-left: 50px;float: !important;padding-top: 50px;">
						<table align="center" border="1">
							<thead>
								<tr style="height: 25px;">
									<th width="50">序号</th>
									<th>会员号</th>
									<th width="120">昵称</th>
									<th width="50">性别</th>
									<th width="150">职业</th>
									<th width="150">签到时间</th>
									<th width="100">签出</th>
								</tr>
							</thead>
							<tbody align="left">
								<c:if test="${page.totalPage le 0 }">
									<tr align="center">
										<td colspan="10"><span>无记录</span></td>
									</tr>
								</c:if>
								<c:if test="${page.totalPage gt 0 }">
									<c:forEach items="${page.result }" var="viplog" varStatus="vs">
										<tr>
											<td>${(page.currentPage-1)*page.pageSize+vs.index+1}</td>
											<td>${viplog.unum }</td>
											<td>${viplog.nickname }</td>
											<td>
												<c:if test="${viplog.sex eq 0 }">男</c:if>
												<c:if test="${viplog.sex eq 1 }">女</c:if>
												<c:if test="${viplog.sex eq 2 }">保密</c:if>
											</td>
											<td>${viplog.jobinfo.zname }</td>
											<td>
												<fmt:formatDate value="${viplog.sdate1 }" pattern="yyyy-MM-dd HH:mm:ss"/>
											</td>
											<td>
												<input id="checkout${vs.index }" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="签出时间" style="width: 125"/>
												<input id="checkbtn${vs.index }" type="button" value="签出" onclick="checkout('${viplog.user}','${vs.index }');"/>
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
