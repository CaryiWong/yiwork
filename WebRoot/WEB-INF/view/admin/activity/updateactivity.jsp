<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>更新活动信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
function verify(v){
	var checktype = v;
	var id = $("#id").val();
	var submitdata = {
		id:id,
		checktype:checktype
	};
	$.ajax({
     type: "post",
     url: "<%=basePath%>admin/activity/verifyactivity",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		alert("审核失败");
     	}else{
     		alert("审核成功");
     		window.location.href="<%=basePath%>admin/activity/activitylist?pageSize=15";
     	}
     }
    });
	
}
function validateform(obj){
	var nick = $("#nick").val();
	if(nick==1){
		alert("此昵称已被使用，请重新输入一个昵称");
		$("#nick").focus();  //获取焦点
		return false;
	}
	return true;
}

function checknickname(o){
	var nick = o.value;
	var submitdata = {
		nickname:nick
	};
	$.ajax({
     type: "post",
     url: "<%=basePath%>user/checknickname",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		$("#nick").val(1);
     		alert("此昵称已被使用，请重新输入一个昵称");
			$("#nick").focus(); //获取焦点
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
		<div id="right" style="display: inline-block;float: left;position: absolute;width: 1200px;height: 620px;background-image: url('../../res/images/mainbox.png');">
			<form name="mainform" id="mainform" action="<%=basePath%>admin/activity/updateactivity"
				method="post" onsubmit="return validateform(this);">
				<input type="hidden" value="${activity.id}" name="id" id="id">
				<table align="center" style="margin-top: 50px;">
					<tr>
						<td colspan="4">
							<label>标签</label>
							<c:forEach items="${labels}" var="label" varStatus="vs">
								<c:if test="${vs.index mod 10 eq 0 }"><br></c:if>
								<c:set var="la" value="${label.id}"></c:set>
								<input name="groups" type="checkbox" value="${label.id}" <c:if test="${fn:contains(labelList,la) }">checked="checked"</c:if> />${label.zname }
							</c:forEach>						
						</td>
					</tr>
					<tr>
						<td><label>标题</label></td>
						<td><input style="width: 200;" id="title" name="title" value="${activity.title}" /></td>
						<td><label>报名人数</label></td>
						<td><input style="width: 200;" id="signnum" name="signnum" value="${activity.signnum}" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label>开启时间</label></td>
						<td>
							<input id="opendate" name="open" value="<fmt:formatDate value="${activity.opendate1 }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="开启时间" style="width: 200"/>
						</td>
						<td><label>结束时间</label></td>
						<td>
							<input id="enddate" name="end" value="<fmt:formatDate value="${activity.enddate1 }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="结束时间" style="width: 200"/>
						</td>
					</tr>
					<tr>
						<td><label>地点</label></td>
						<td><input style="width: 200px;" id="address" name="address" value="${activity.address}" /></td>
						<td><label>费用</label></td>
						<td><input style="width: 200;" id="cost" name="cost" value="${activity.cost}" /></td>
					</tr>
					<tr>
						<td><label>出席人数</label></td>
						<td><input style="width: 200;" id="joinnum" name="joinnum" value="${activity.joinnum}" readonly="readonly"/></td>
						<td><label>最高人数</label></td>
						<td><input style="width: 200;" id="maxnum" name="maxnum" value="${activity.maxnum}" /></td>
					</tr>
					<tr>
						
						<td><label>发起人</label></td>
						<td><input style="width: 200;" id="usernickname" name="usernickname" value="${activity.usernickname}" readonly="readonly" /></td>
						<td><label>联系方式</label></td>
						<td>
							<input style="width:200" name="tel" value="${activity.tel}">
						</td>
					</tr>
					<tr>
						<td><label>简介</label></td>
						<td colspan="3">
							<textarea style="width: 550px;height: 150px;" id="summary" name="summary" >${activity.summary}</textarea>
						</td>
					</tr>
					<%-- <tr>
						<td><label>评论数</label></td>
						<td><input style="width: 200;" id="commentnum" name="commentnum" value="${activity.commentnum}" /></td>
					</tr>
					<tr>
						<td><label>点赞数	</label></td>
						<td><input style="width: 200;" id="goodnum" name="goodnum" value="${activity.goodnum}" /></td>
					</tr>
					<tr>
						<td><label>分享数</label></td>
						<td><input style="width: 200;" id="sharenum" name="sharenum" value="${activity.sharenum}" /></td>
					</tr> --%>
					<tr align="center">
						<c:if test="${activity.checktype ne 1 }">
							<td><input type="submit" value="保存" /></td>
							<td><input type="button" value="通过" onclick="verify(1);"/></td>
							<td><input type="button" value="驳回" onclick="verify(2);"/></td>
						</c:if>
						<td><input type="button" value="返回" onclick="history.back(-1);" /></td>
					</tr>
					<c:if test="${tips ne null}">
		    			<tr align="center">
		    				<td colspan="4">${tips}</td>
		    			</tr>
		    		</c:if>
				</table>
			</form>
			<div style="padding-left: 200px;"><a href="<%=basePath%>admin/activity/getactsignlist?pageSize=12&id=${activity.id}">报名列表</a></div>
		</div>
	</div>
	<div id="foot" style="padding-top: 5px;float: left;">
		<jsp:include page="/res/common/footer.jsp" />
	</div>
  </body>
</html>
