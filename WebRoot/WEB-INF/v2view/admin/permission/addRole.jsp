<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.List"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加角色</title>
</head>

<body>
	<form action="" method="post" name="sUser">
		<TABLE cellSpacing=1 cellPadding=1 width="300" bgColor=#cccccc
			border=0>
			<TBODY>
				<h2>添加角色</h2>
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
				<TR>
					<TD vAlign=top align=middle>
				<TR bgColor=#ffffff>
					<TD style="WIDTH: 60px">&nbsp;角色：</TD>
					<TD><INPUT id=username style="WIDTH: 200px" name=username>
					</TD>
				</TR>

				<TR bgColor=#ffffff>
					<TD style="WIDTH: 60px">&nbsp;备注：</TD>
					<TD><TEXTAREA id=description
							style="WIDTH: 200px; HEIGHT: 60px" name=description></TEXTAREA></TD>
				</TR>

				<TR>
					<TD><FONT face="Times New Roman"> <input id=submitUser
							type='button' onclick="check();" value='提交' />
					</FONT></TD>
				</TR>
			</TBODY>
		</TABLE>
	</form>
	<script type="text/javascript"src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
	<script>var base="<%=basePath%>"</script>
	<SCRIPT language="javascript">
	function check(){
	var username=document.getElementById("username").value;
	var des=document.getElementById("description").value;
			if (username== "")
				{
					alert("请输入用户名!");
					document.getElementById("username").focus();
					return false;
				}
				 
				if(des==""){
					alert("描述不能为空！");
					document.getElementById("description").focus();
					return false;
				}		
		
				$.ajax({
				 type: "POST",
					  url: base+"v20/admin/permission/saverole",
					  data: "rolename="+username+"&description="+des,
					  dataType:"json",
					  success: function(json){
						 if(json.cord==0){
							 alert(json.msg);
						 }
					  },
				});	
				}
</SCRIPT>
</body>
</html>
