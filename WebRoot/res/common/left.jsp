<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<div>
	<div style="width: 150px;float: left;padding-left: 5px;padding-top: 10px;">
		<div><strong>管理模块</strong></div>
		<div><a href="<%=basePath%>admin/user/addapplyvip">会员申请</a></div>
		<div><a href="<%=basePath%>admin/user/userlist?pageSize=12">用户列表</a></div>
		<%-- <div><a href="<%=basePath%>admin/user/viplist?pageSize=12">会员列表</a></div> --%>
		<div><a href="<%=basePath%>admin/user/checkinlist?pageSize=12">在线列表</a></div>
		<div><a href="<%=basePath%>admin/activity/activitylist?pageSize=15">活动列表</a></div>
		<div><a href="<%=basePath%>admin/demand/demandlist?pageSize=15">需求列表</a></div>
		<div><a href="<%=basePath%>admin/user/broadcastlist?pageSize=15">广播列表</a></div>
		<div><a href="<%=basePath%>admin/display/hugolist">hugo列表</a></div>
		<div><a href="<%=basePath%>admin/display/teamlist">入驻列表</a></div>
		<div><a href="<%=basePath%>admin/display/showspace">空间区域</a></div>
		<!-- <div><a href="gather/grouplist?pageSize=15">组列表</a></div>
		<div><a href="activity/activitylist?pageSize=15">活动列表</a></div>
		<div><a href="user/userlist?pageSize=15">用户列表</a></div>
		<div><a href="indextitle/advList">广告列表</a></div>
		<div><a href="indextitle/addAdv">新增广告</a></div>
		<div><strong>数据统计</strong></div>
		<div><a href="analyse/gather">组统计</a></div>
		<div><a href="analyse/user">用户统计</a></div>
		<div><a href="analyse/activity">活动统计</a></div>
		<div><strong><a href="adminuser/updatepassword">密码修改</a></strong></div>
		<div><strong><a href="adminuser/updateinfo">个人信息修改</a></strong></div> -->
	</div>
	<div style="float: left;">
		<img style="padding-left: 5px;" alt="" src="<%=basePath%>res/images/left_line.png">
	</div>
</div>