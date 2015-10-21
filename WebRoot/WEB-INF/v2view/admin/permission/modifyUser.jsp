<%@page import="cn.yi.gather.v20.permission.entity.AdminUser"%>
<%@page import="cn.yi.gather.v20.permission.service.IAdminUserService"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="cn.yi.gather.v20.permission.entity.AdminPermission"%>
<%@page import="cn.yi.gather.v20.permission.service.IAdminPerService"%>
<%@page import="cn.yi.gather.v20.permission.entity.AdminRole"%>
<%@page import="cn.yi.gather.v20.permission.service.IAdminRoleService"%>
<%@ page import="org.springframework.context.*" %>
<%@ page import="org.springframework.web.context.support.*" %>
<%@ page import="com.common.DES" %>
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
var userId=document.getElementById("userId").value;
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
					  url: base+"v20/admin/permission/updateuser",
					  data: "username="+username+"&userpassword="+password+"&description="+des+"&adminrole.roleid="+roleId+"&id="+userId,
					  dataType:"json",
					  success: function(json){
						  if(json.cord==0){
							  alert(json.msg);
							  window.location.reload();
							  return false;
						  }else{
							  alert("服务器异常 请稍后再试")
						  }
						}
					 
			});	
				}
			
			function deleteElement(userid){
			var b=window.confirm("您确定要删除该用户吗？");
			if(b){
			window.location.href="/action/deleteUser.action?userId="+userid;}
			}
</SCRIPT>
	<body>
		<% 
			String userId=request.getParameter("userId");
			String username=request.getParameter("username");
			
			ServletContext sc=request.getServletContext();
			ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
			IAdminUserService userService= (IAdminUserService)ac1.getBean("adminUserService");
			
			IAdminRoleService roleService=(IAdminRoleService)ac1.getBean("adminRoleService");
		/* 	DES des = new DES();
			des.setKey("F59BD65F7EDAFB087A81D4DCA06C4910"); */
			
		 	AdminUser user=userService.findById(userId);
		 	//String pwd=des.getDesString(user.getUserpassword());
			if(user!=null){
			 %>
		<form action="" method="post"  name="sUser">
		<TABLE class=DBGrid id=dgRole
			style="BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; BORDER-LEFT: #cccccc 1px solid; WIDTH: 100%; BORDER-BOTTOM: #cccccc 1px solid; BORDER-COLLAPSE: collapse"
			borderColor=#cccccc cellSpacing=0 cellPadding=4 rules=all
			align=center border=1>
			<TBODY>
			<h2>查看资料</h2>
		
								<TR>
									<TD vAlign=top align=middle>
												<TR bgColor=#ffffff>
													<TD style="WIDTH: 60px">
														&nbsp;用户名：
													</TD>
													<TD>
														<INPUT id=username style="WIDTH: 420px" name=username value=<%=user.getUsername() %>>
													</TD>
												</TR>
														<TR bgColor=#ffffff>
													<TD style="WIDTH: 60px">
														&nbsp;密码：
													</TD>
													<TD>
														<INPUT id=password style="WIDTH: 400px"
															name=password value=<%=user.getUserpassword() %>></INPUT>
													</TD>
													</TR>
													<TR bgColor=#ffffff>
													<TD style="WIDTH: 60px">
														&nbsp;描述：
													</TD>
													<TD>
														<TEXTAREA id=description style="WIDTH: 423px; HEIGHT: 58px"
															name=description><%=user.getDescription()%></TEXTAREA>
													</TD>
												</TR>
													<TR bgColor=#ffffff>
													<TD style="WIDTH: 60px">
														&nbsp;角色：
													</TD>
													<TD>
								<% 
														 
														List<AdminRole> roleList=roleService.selectRoleAll();
								%>
											<select name="roleId" id="roleId">
										<%for(AdminRole r:roleList){%>				
  											<option value="<%=r.getRoleid()%>" <%if(user.getAdminrole().getRoleid().equals(r.getRoleid())){ %> selected<%} %>><%=r.getRolename() %></option>
  										<% 	} %>
</select>
													</TD>
												</TR>	
											<INPUT id=userId type=hidden name=userId value=<%=user.getId()%>>
				<TR><TD>	<FONT face="Times New Roman"> <input id=submitUser  type='button' onclick="check();" value='提交' /> </FONT></TD></TR>							
			</TBODY>
		</TABLE>
			</form>	
			<%}%>
	</body>
</html>
