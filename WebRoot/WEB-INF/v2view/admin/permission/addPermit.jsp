<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="cn.yi.gather.v20.permission.entity.AdminPermission"%>
<%@page import="cn.yi.gather.v20.permission.service.IAdminPerService"%>
<%@page import="cn.yi.gather.v20.permission.entity.AdminRole"%>
<%@page import="cn.yi.gather.v20.permission.service.IAdminRoleService"%>
 
<%@ page import="org.springframework.context.*" %>
<%@ page import="org.springframework.web.context.support.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<%@ page import="java.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
ServletContext sc=request.getServletContext();
ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
IAdminRoleService roleService= (IAdminRoleService)ac1.getBean("adminRoleService");
List<AdminRole> roleList =roleService.selectRoleAll();

IAdminPerService perService =(IAdminPerService)ac1.getBean("adminPerService");
List<AdminPermission> permitList =perService.selectAllPermit();

	/* RoleDAO roleDAO = new RoleDAO();
	List<Role> roleList = roleDAO.selectRoleAll();
	PermitDAO permitDAO = new PermitDAO();
	List<Permit> permitList = permitDAO.selectPermitAll(); */
%>

<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=utf-8">
		<title>角色管理</title>
		<style>
body {
	font-size:12px;
}
</style>
		<link rel="stylesheet" href="<%=basePath%>res/images2/jquery.treeview.css" />
		<link rel="stylesheet" href="<%=basePath%>res/images2/red-treeview.css" />
		<script type="text/javascript"src="<%=basePath%>res/js/jquery-1.2.6.min.js"></script>
		<script type="text/javascript"src="<%=basePath%>res/js/jquery.treeview.js"></script>
		<script>var base="<%=basePath%>"</script>
		<script>
       
       jQuery.fn.multiSelect = function(to, options) {
	// support v0.1 syntax 

	if (typeof options == "string")
		options = {trigger: "#"+options};
	
	options = $.extend({
		trigger: null,     // selector of elements whose 'click' event should invoke a move
		autoSubmit: true,  // true => select all child <option>s on parent form submit (if any)
		beforeMove: null,  // before move callback function
		afterMove: null    // after move callback
	}, options);

	// for closures
	var $select = this;
	
	// make form submission select child <option>s
	if (options.autoSubmit)
		this.parents("form").submit(function() { selectChildOptions($select); });
	

	// create the closure
	var moveFunction = function() { moveOptions($select, to, options.beforeMove, options.afterMove); };
	
	// attach double-click behaviour
	this.dblclick(moveFunction);
	
	// trigger element behaviour
	if (options.trigger)
		jQuery(options.trigger).click(moveFunction);
	
	return this;

	// moves the options
	function moveOptions(from, toSel, beforeMove, afterMove) {
	document.getElementById("select_hide").options.length   =   0;   
	var selestr;
	selestr="";
		   
      if (beforeMove && !beforeMove())
			return;
		
		jQuery("option:selected", from).each(function() {
         jQuery(this)
            .attr("selected", false)
            .appendTo(toSel);
      });
		
		afterMove && afterMove();
		for (i = 0; i < document.getElementById("select_list").length; i++)
		   {selestr=selestr+document.getElementById("selec t_list").options[i].text +",";
		   }
	$("input[@name='input_checkbox']").setValue(selestr);
  
   }
	
	function selectChildOptions($select) {
		$select.children("option").each(function() {
			this.selected = true;
		});
	}
};
</script>
		<SCRIPT language=javascript>
		function CheckForm()
		{
			if (document.all.txtRole.value == "")
			{
				alert("请输入角色名!");
				document.all.txtRole.select();
				return false;
			}
			return true;
		}
		</SCRIPT>
		<SCRIPT src="<%=basePath%>res/js/jquery.field.js" type=text/javascript></SCRIPT>
		<script type="text/javascript">
		var bIsFirebugReady = (!!window.console && !!window.console.log);

	$(document).ready(function(){
 
		$("#black").treeview({
			control: "#treecontrol",
			collapsed:true
		});
			
	});
	
	</script>
		<script type="text/javascript">
	  
			function delsele() {
				var el = document.getElementsByTagName('input_checkbox');
				var len = el.length;
				for (var i = 0; i < len; i++) {
					if ((el[i].type == "checkbox") && (el[i].name == name)) {
						el[i].checked = false;
					}

				}
			}

			function deleteElement(roleId) {
				$
						.ajax({
							type : "POST",
							url : base+"/v20/admin/permission/searchuserbyroleid",
							data : "roleid=" + roleId,
							dataType : "json",
							error : function(String) {
								alert('删除失败');
							},
							success : function(json) {
								if (json.cord == -2) {
									alert(json.msg);
									return false;
								} else {
									var d = window.confirm("确定删除该角色？");
									if (d) {
										window.location.href = base+"/v20/admin/permission/deleterole?roleid="+roleId;
									} else {
									}
								}
							}
						});
			}

			function check() {
				if (document.getElementById("rolename").value == "") {
					alert("角色名不能为空！");
					document.getElementById("rolename").focus();
					return false;
				}
				if (document.getElementById("description").value == "") {
					alert("角色的描述不能为空！");
					document.getElementById("description").focus();
					return false;
				}
				return true;
			}
		</script>
		<style type="text/css">
#black.treeview li {
	background: url(../../../res/images2/black/tv-item.gif) 0 0 no-repeat;
}
#black li input {
	margin-top:-3px;
	margin-left:-3px;
	padding:0;
}
#black.treeview .collapsable {
	background-image: url(../../../res/images2/black/tv-collapsable.gif);
}
#black.treeview .expandable {
	background-image: url(../../../res/images2/black/tv-expandable.gif);
}
#black.treeview .last {
	background-image: url(../../../res/images2/black/tv-item-last.gif);
}
#black.treeview .lastCollapsable {
	background-image: url(../../../res/images2/black/tv-collapsable-last.gif);
}
#black.treeview .lastExpandable {
	background-image: url(../../../res/images2/black/tv-expandable-last.gif);
}
</style>
	</head>
	<body>
		<style><!--
*{font-size:12px;line-height:18px;padding:0;margin:0;}
table{ border:0px solid #333; border-width:1px 0 0 1px;}
td{ padding:0px 4px; border:1px solid #333; border-width:0 1px 1px 0;font-size:12px;line-height:18px;}
--></style>
		<TABLE height=450 cellSpacing=1 cellPadding=1 width="98%"
			bgColor=#ffffff border=0>
				<TR bgColor=#ffffff>
					<TD style="WIDTH: 200px" vAlign=top align=left>
						<span id="treecontrol"><a href="#">全部折叠</a>&nbsp;&nbsp;<a
							href="#">全部展开</a> </span>&nbsp;&nbsp;
						<a href="#" onclick="delsele()">取消选择</a>
						<ul id="black">

							<%
							for (AdminPermission p : permitList) {
							%>
							<li>
							<input id=<%=p.getPermitid()%> type='checkbox' name="input_checkbox" checked='checked'>
								<span><%=p.getPermitname()%> </span>
								<ul>
									<%
											List<AdminPermission> pSonList = perService.selectPermitByParent(Integer.toString(p.getPermitid()));
											for (AdminPermission pSon : pSonList) {
									%>
									<li>
										<input type="checkbox" name="input_checkbox" checked="checked" id=<%=pSon.getPermitid()%> />
										<span><%=pSon.getPermitname()%> </span>
										<ul>
									<%
											List<AdminPermission> psList = perService.selectPermitByParent(Integer.toString(pSon.getPermitid()));
											for (AdminPermission ps : psList) {
									%>
									
									<li>
										<input type="checkbox" name="input_checkbox" checked="checked" id=<%=ps.getPermitid()%> />
										<span><%=ps.getPermitname()%> </span>	
									</li>
									<%
									}
									%>
								</ul>
									</li>
									<%
									}
									%>
								</ul>
							</li>
							<%
							}
							%>
						</ul>
					</TD>

					<TD vAlign=top width=*>
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>

								<TR>
									<TD>
										&nbsp;
										<TABLE class=DBGrid id=dgRole
											style="BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; BORDER-LEFT: #cccccc 1px solid; WIDTH: 100%; BORDER-BOTTOM: #cccccc 1px solid; BORDER-COLLAPSE: collapse"
											borderColor=#cccccc cellSpacing=0 cellPadding=4 rules=all
											align=center border=1>
											<TBODY>

												<TR class=HeaderStyle
													style="FONT-WEIGHT: bold; COLOR: black; BACKGROUND-COLOR: white"
													align=middle>
													<TD>
														角色名
													</TD>
													<TD>
														&nbsp;
													</TD>
												</TR>
												<%
														if (roleList != null) {
														//超级管理员是不可见和不能被修改
														//roleList.remove(0);
														for (AdminRole r : roleList) {
															String isleaf = "0";
															String isleaf1="1";
															List<String> PCheck = new ArrayList<String>();
															List<AdminPermission> pl = perService.selectPermitByRoleId(r.getRoleid().toString(), isleaf);
															for (AdminPermission permit : pl) {
															//第一级菜单
															PCheck.add("#"+permit.getPermitid().toString());
														List<AdminPermission> pls1 = perService.selectPermitByRoleIdAndParent(r.getRoleid().toString(),permit.getParent()+permit.getPermitid().toString()+"#",isleaf);
														for (AdminPermission ps1 : pls1) 
														{
														//第二级菜单， 且有第三级菜单
															PCheck.add("#"+ ps1.getPermitid().toString());
														List<AdminPermission> pls2=perService.selectPermitByRoleIdAndParent(r.getRoleid().toString(),ps1.getParent()+ps1.getPermitid().toString()+"#",isleaf);	
														for (AdminPermission ps2 : pls2) 
														{
														//第三级菜单
															PCheck.add("#"+ ps2.getPermitid().toString());
															
															}
														}
											//只有两级菜单的情况
																List<AdminPermission> permitList2=perService.selectPermitByRoleIdAndParent(r.getRoleid().toString(),permit.getParent()+permit.getPermitid()+"#".toString(),isleaf1);
																for(AdminPermission permit2:permitList2){
																PCheck.add("#"+permit2.getPermitid().toString());
																}
															}
												%>

												<TR
													style="FONT-SIZE: x-small; COLOR: black; FONT-FAMILY: Arial; BACKGROUND-COLOR: #eff0f6"
													align=middle>
													<TD>
														<A style="COLOR: black"
															href="javascript:checkRole('<%=PCheck%>');getRole('<%=r.getRoleid()%>','<%=r.getRolename()%>','<%=r.getDescription()%>')"><%=r.getRolename()%>
														</A>
													</TD>
													<TD class=ItemStyle style="WIDTH: 20%" align=middle>
														<A id=<%=r.getRoleid()%> href="javascript:deleteElement(<%=r.getRoleid()%>)">删除</A>
													</TD>
													
												</TR>
												<%	}}  %>
											</TBODY>
										</TABLE>
									</TD>
								</TR>

								<TR>
									<TD vAlign=top align=middle>
										<TABLE cellSpacing=1 cellPadding=1 width="100%"
											bgColor=#cccccc border=0>
											<TBODY>
												<TR bgColor=#ffffff>
													<TD style="WIDTH: 60px">
														&nbsp;角色名：
													</TD>
													<TD>
														<INPUT id=rolename style="WIDTH: 420px" name=rolename>
													</TD>
												</TR>
												<TR bgColor=#ffffff>
													<TD style="WIDTH: 60px">
														&nbsp;备注：
													</TD>
													<TD>
														<TEXTAREA id=description style="WIDTH: 423px; HEIGHT: 58px"
															name=description></TEXTAREA>
													</TD>
												</TR>
											</TBODY>
										</TABLE>
									
									<FONT face="Times New Roman" id="Rfont"> <input id=newRole onclick="javascrip:if(check()){warnRole();}else{return false;}" type='button' value='新建'>
												 <INPUT id='permitList' type='hidden' name='permitList'> </FONT>
											
									</TD>
								</TR>
							
							</TBODY>
						</TABLE>
					</TD>
				</TR>
		</TABLE>
	</body>
</html>
<script>

	function getRole(roleId, rolename, roleDes) {
		if (document.getElementById("newRole")) {
			document.getElementById("newRole").style.display = "none";
		}
		document.getElementById("Rfont").innerHTML = "<INPUT id=btnSave  type=button value='保存'  onclick='javascript:if(check()){warnRole()}else{return false;}'/> <input id=roleId type=hidden value="+roleId+">  <INPUT id='permitList' type='hidden' name='permitList'>"
		$("input[@id='rolename']").attr('value', rolename);
		$("TEXTAREA[@id='description']").attr('value', roleDes);
	}

	function warnRole() {
		var w = window.confirm("确定要提交？");
		if (w) {
			saveRole()
		} else {
		}
	}

	$("input[@type='checkbox']").click(
			function() {
				if ($(this).attr('checked') == true && $(this).parent().parent().attr('id') != 'black') {  //不是一级节点的情况

					if($(this).parent().parent().parent().parent().attr("id")=='black'){  //二级节点选中，父节点选中
						$(this).parent().parent().parent().parent().siblings('input:checkbox').attr('checked', 'checked');
						$(this).parent().parent().siblings('input:checkbox').attr('checked', 'checked');
					}else{ //三级节点
						$(this).parent().parent().siblings('input:checkbox').attr('checked', 'checked');
						$(this).parent().parent().parent().parent().siblings('input:checkbox').attr('checked', 'checked');
					}
					
				}
				//如果是父类勾选，子类应该也被选中
				if ($(this).attr('checked') == true && $(this).parent().parent().attr('id') == 'black') {  //
					var le=$(this).parent().children();  //得到父类的容器
					le.find("[type='checkbox']").each(function(i){
						$(this).attr("checked","checked");
					})
				}
				
				//如果是父类取消勾选，子类应该也被取消选中
				if ($(this).attr('checked') == false && $(this).parent().parent().attr('id') == 'black') {  //
					var le=$(this).parent().children();  //得到父类的容器
					le.find("[type='checkbox']").each(function(i){
						   $(this).attr("checked",false);
					})
				}
				
				//alert($(this).parent().parent().attr("id"));  //这样就是父节点
			});

	function saveRole() {
		var sr = new Array();
		$("input[@type='checkbox']")
				.each(
						function(i) {
							if ($(this).attr('checked') == true
									&& $(this).parent().parent('ul').attr('id') != 'black') {
								sr.push($(this).attr('id'));
							} else {
								if ($(this).attr('checked')) {
									sr.push($(this).attr('id'));
								}
							}

						});
		$('#permitList').val(sr);
		if (document.getElementById("roleId")) {
			var rolename = document.getElementById("rolename").value;
			var des = document.getElementById("description").value;
			var permitList = document.getElementById("permitList").value;
			var roleId = document.getElementById("roleId").value;
			var url= base+"/v20/admin/permission/updaterole?rolename="
			+ rolename + "&&description=" + des + "&&permitList="
			+ permitList + "&&roleid=" + roleId+ "&&isdel=0";
			url = encodeURI(encodeURI(url));
			window.location.href = url;
		} else {
			alert("请从其它目录进行添加")
			/* var rolename = document.getElementById("rolename").value;
			var des = document.getElementById("description").value;
			var permitList = document.getElementById("permitList").value;
			window.location.href = "/action/insertRole.action?rolename="
					+ rolename + "&&description=" + des + "&&permitList="
					+ permitList; */
		}
	}

	function checkRole(role) {
		var r = role.replace('[', '').replace(']', '');
		$("input[@type='checkbox']").attr('checked', '');
		$(r).attr('checked', 'checked');
	}
</script>
