<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="cn.yi.gather.v20.permission.entity.AdminPermission"%>
<%@page import="cn.yi.gather.v20.permission.service.IAdminPerService"%>
<%@page import="cn.yi.gather.v20.permission.entity.AdminRole"%>
<%@page import="cn.yi.gather.v20.permission.service.IAdminRoleService"%>
<%@ page import="org.springframework.context.*"%>
<%@ page import="org.springframework.web.context.support.*"%>
<%@include file="/res/common/taglib.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看系统管理员</title>
</head>
<script type="text/javascript"src="<%=basePath%>res/js/jquery-1.2.6.min.js"></script>
<script>var base="<%=basePath%>"</script>
<SCRIPT language="javascript">
function check(){
var username=document.getElementById("username").value;
var password=document.getElementById("password").value;
var des=document.getElementById("description").value;
var roleId=document.getElementById("roleId").value;
if (username== "")
			{
				alert("请输入用户名!");
				document.getElementById("username").focus();
			return false;
			}
			if(password==""){
			alert("密码不能为空！");
			document.getElementById("password").focus();
			return false;
			}
			if(des==""){
			alert("描述不能为空！");
			document.getElementById("description").focus();
			return false;
			}		
$.ajax({
		 type: "POST",
				  url: "/action/insertUser.action",
				  data: "username="+username+"&password="+password+"&description="+des+"&roleId="+roleId,
				  dataType:"xml",
				  success: function(xml){
					$(xml).find("action").each(function(i){
						var result_value=$(this).attr("result");
						if(result_value=="false"){
							alert('该用户已存在！');
						}else if(result_value=="true"){
						alert('插入成功');}
						else if(result_value==""){
							alert('插入失败');
						}
					});
				  },
				error: function(xml){

				  }
		});	
			}
			
			function deleteElement(userid){
			var b=window.confirm("您确定要删除该用户吗？");
			if(b){
				$.ajax({
							type : "POST",
							url : base+"/v20/admin/permission/deleteuser.action",
							data : "userId=" + userid,
							dataType : "json",
							error : function(String) {
								alert('删除失败');
							},
							success : function(json) {
								if (json.cord == 0) {
									alert(json.msg);
									window.location.reload();
									return false;
								} else {
									 alert(json.msg);
								}
							}
						});
					}
			}
			
			function searchElement(userid){
				window.location.href=base+"/v20/admin/permission/selectuser.action?userId="+userid;
			}
</SCRIPT>
<body>
	<TABLE class=DBGrid id=dgRole
		style="BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; BORDER-LEFT: #cccccc 1px solid; WIDTH: 100%; BORDER-BOTTOM: #cccccc 1px solid; BORDER-COLLAPSE: collapse"
		borderColor=#cccccc cellSpacing=0 cellPadding=4 rules=all align=center
		border=1>
		<h2>查看系统管理员</h2>
		<style>
<!--
* {
	font-size: 12px;
	line-height: 18px;
	padding: 0;
	margin: 0;
}

table {
	border: 0px solid #333;
	border-width: 1px 0 0 1px;
}

td {
	padding: 0px 4px;
	border: 1px solid #333;
	border-width: 0 1px 1px 0;
	font-size: 12px;
	line-height: 18px;
}
-->
</style>


		<TR class=HeaderStyle style="FONT-WEIGHT: bold; COLOR: black; BACKGROUND-COLOR: white" align=middle>
			<TD>用户名</TD>
			<TD>角色</TD>
			<TD>空间</TD>
			<TD>动作</TD>
		</TR>
		
		<c:forEach items="${userList}" var="user" varStatus="vs">
			<TR style="FONT-SIZE: x-small; COLOR: black; FONT-FAMILY: Arial; BACKGROUND-COLOR: #eff0f6" align=middle>
				<TD style="WIDTH: 20%" align=middle>&nbsp;
					${user.username}
				</TD>
				<TD style="WIDTH: 20%" align=middle>&nbsp; 
					${user.adminrole.rolename}
				</TD>
				<TD>
					${user.workspaceinfo.spacename}
				</TD>
				<TD style="WIDTH:20%" align=middle>
					<a href="javascript:searchElement('${user.id}')">查看</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:deleteElement('${user.id}')">删除</a>
				</TD>
			</TR>
		</c:forEach>
	 

	</TABLE>
</body>
</html>
