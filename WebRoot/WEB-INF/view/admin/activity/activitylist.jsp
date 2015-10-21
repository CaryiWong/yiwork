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
    
    <title>活动列表</title>
    
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
function recommend(id,o){
	var actid = id;
	var submitdata = {
		actid:actid
	};
	$.ajax({
     type: "post",
     url: "<%=basePath%>/admin/activity/recommend",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.result=="s"){
     		alert("推荐成功");
     		//window.location.reload();
     		$(o).html("已推荐");
     		$(o).removeAttr("href");
     	}else{
     		alert("推荐失败");
     	}
     }
    });
}

function setbanner(id,banner,o){
	var actid = id;
	var submitdata = {
		type:1,
		actid:actid,
		banner:banner
	};
	$.ajax({
     type: "post",
     url: "<%=basePath%>/admin/activity/setbanner",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	alert(result.msg);
     	if(result.result=="s"){
     		if(banner==1){
     			$(o).html("是");
     			$(o).attr("onclick","setbanner('"+id+"',0,this);");
     		}else if(banner==0){
     			$(o).html("否");
     			$(o).attr("onclick","setbanner('"+id+"',1,this);");
     		}
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
				<form id="mainForm" name="mainForm" action="admin/activity/activitylist" method="post">
					<div style="display: inline-block;float: left;width: 1000px;height: 100px;padding-left: 200px;">
						<span style="font-size: 18px;font-weight: bold;padding-left: 100px;">搜索条件：</span>
						<div style="padding-left: 50px;">
							<label>资费</label>
							<select name="cost">
								<option value="-1" <c:if test="${cost== null or cost eq -1 }">selected="selected"</c:if>>全部</option>
								<option value="0" <c:if test="${cost eq 0 }">selected="selected"</c:if>>免费</option>
								<option value="1" <c:if test="${cost eq 1 }">selected="selected"</c:if>>收费</option>
							</select>
							<label>审核状态</label>
							<select name="checktype">
								<option value="-1" <c:if test="${checktype==null or checktype eq -1 }">selected="selected"</c:if>>全部</option>
								<option value="0" <c:if test="${checktype eq 0 }">selected="selected"</c:if>>待审核</option>
								<option value="1" <c:if test="${checktype eq 1 }">selected="selected"</c:if>>通过审核</option>
								<option value="2" <c:if test="${checktype eq 2 }">selected="selected"</c:if>>审核失败</option>
							</select>
							<label>活动状态</label>
							<select name="status">
								<option value="-1" <c:if test="${status==null or status eq -1 }">selected="selected"</c:if>>全部</option>
								<option value="1" <c:if test="${status eq 1 }">selected="selected"</c:if>>待开启</option>
								<option value="2" <c:if test="${status eq 2 }">selected="selected"</c:if>>进行中</option>
								<option value="3" <c:if test="${status eq 3 }">selected="selected"</c:if>>已结束</option>
							</select>
							<label>关键字</label>
							<input name="keyword" value="${keyword}" maxlength="30" placeholder="关键字" style="width: 100px"/>
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
					<div id="contentDiv" style="float: !important;padding-top: 120px;">
						<table align="center" border="1">
							<thead>
								<tr>
									<th width="50">序号</th>
									<th width="300">标题</th>
									<th width="70">发起人</th>
									<th width="220">地点</th>
									<th width="50">费用</th>
									<th width="120">电话</th>
									<th width="50">活动状态</th>
									<th width="50">最高人数</th>
									<th width="50">报名人数</th>
									<th width="50">操作</th>
									<th width="50">banner</th>
									<th width="65">回顾</th>
								</tr>
							</thead>
							<tbody align="center">
								<c:if test="${page.totalPage le 0 }">
									<tr>
										<td colspan="10"><span>无记录</span></td>
									</tr>
								</c:if>
								<c:if test="${page.totalPage gt 0 }">
									<c:forEach items="${page.result}" var="activity" varStatus="vs">
										<tr>
											<td width="50">${(page.currentPage-1)*page.pageSize+vs.index+1}</td>
											<td width="300"><a href="admin/activity/getupdateactivity?activityid=${activity.id}">${activity.title}</a></td>
											<td width="70">${activity.usernickname}</td>
											<td width="220">${activity.address}</td>
											<td width="50">
												<c:if test="${activity.cost eq 0}">免费</c:if>
												<c:if test="${activity.cost eq 1}">收费</c:if>
											</td>
											<td width="120">${activity.tel}</td>
											<td  width="50">
												<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
												<c:choose>
													<c:when test="${nowDate-activity.opendate <= 0 }">待开启</c:when>
													<c:when test="${nowDate-activity.opendate > 0 and nowDate-activity.enddate < 0}">进行中</c:when>
													<c:when test="${nowDate-activity.enddate >= 0 }">已结束</c:when>
												</c:choose>
											</td>
											<td width="50">${activity.maxnum}</td>
											<td width="50">${activity.signnum}</td>
											<td width="50">
												<c:if test="${activity.isgood eq 0 }"><a href="javascript:void(0);" onclick="recommend('${activity.id}',this);">推荐</a></c:if>
												<c:if test="${activity.isgood eq 1 }">已推荐</c:if>
											</td>
											<td width="50">
												<c:if test="${activity.isbanner eq 0 }"><a href="javascript:void(0);" onclick="setbanner('${activity.id}',1,this);">否</a></c:if>
												<c:if test="${activity.isbanner eq 1 }"><a href="javascript:void(0);" onclick="setbanner('${activity.id}',0,this);">是</a></c:if>
											</td>
											<td width="65">
												<c:if test='${activity.huiguurl =="" }'><a href="admin/activity/createhtml?activityid=${activity.id}" target="_blank">新增回顾</a></c:if>
												<c:if test='${activity.huiguurl !="" }'><a href="${activity.huiguurl}" target="_blank">查看回顾</a></c:if>
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
