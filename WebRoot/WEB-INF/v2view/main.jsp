<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="cn.yi.gather.v20.entity.*" %>
<%@ page language="java" import="com.common.R" %>
<%@ page import="org.springframework.context.*" %>
<%@ page import="org.springframework.web.context.support.*" %>
<%@page import="cn.yi.gather.v20.permission.entity.AdminPermission"%>
<%@page import="cn.yi.gather.v20.permission.service.IAdminPerService"%>
<%@page import="cn.yi.gather.v20.permission.entity.AdminRole"%>
<%@page import="cn.yi.gather.v20.permission.service.IAdminRoleService"%>
<%@page import="cn.yi.gather.v20.permission.entity.*"%>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String webBasePath=basePath+"v20/";
//User loginU=(User)session.getAttribute(R.User.SESSION_USER);
AdminUser loginU=(AdminUser)session.getAttribute(R.User.SESSION_USER);

String userid = loginU.getId();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript">
	var root="<%=basePath%>";
	var adminloginId="<%=userid%>";
	</script>
    
    <meta charset="utf-8">
    <title>后台主页</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>

        <nav class="navbar navbar-default" role="navigation">
            <div class="navbar-header">
                <p class="navbar-text">Yi-gather V2 后台  ，欢迎你来自 <font style="color:red"><%=loginU.getWorkspaceinfo().getSpacename() %></font> 的用户   <%=loginU.getUsername() %></p>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          
                <ul class="nav navbar-nav navbar-right">
                	
                    <li><a href="<%=webBasePath %>admin/user/logout">退出</a></li>
                </ul>
            </div>
        </nav>
        <div class="row row-flow">
            <div class="col-xs-2 nav-list">
                <div class="nav-list">
                    <div class="panel-group" id="accordion">
                    
                    <%
                    ServletContext sc=request.getServletContext();
                    ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
                    IAdminPerService perService =(IAdminPerService)ac1.getBean("adminPerService");
                    Integer roleid=loginU.getAdminrole().getRoleid();
                    System.out.println(roleid);
                    List<AdminPermission> permitList =perService.selectParentByRoleId(roleid+"");
                    
                    
                    %>
                    
                    <%for(AdminPermission permit:permitList){ %>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#<%=permit.getPermitid()%>">
                              <%=permit.getPermitname() %>
                            </a>
                          </h4>
                        </div>
                        <div id="<%=permit.getPermitid()%>" class="panel-collapse collapse">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                              <%
                              List<AdminPermission> psList = perService.selectPermitByRoleIdAndParent(roleid+"",permit.getParent()+permit.getPermitid().toString()+"#","0");                              
								for (AdminPermission ps : psList) {
                              %>
                              	 <%if(ps.getPermitname().equals("活动编辑器")){ %>
                              	  <li><a href="javascript:void(0)" onclick="javscript:window.open('<%=basePath%>editor/app/index.html?userid=<%=userid%>')" target="_parent"><%=ps.getPermitname() %></a></li>
                              	 <%}else if(ps.getPermitname().equals("二次编辑")){ %>
                                	<li><a href="javascript:void(0)" onclick="javscript:window.open('<%=ps.getUrl() %>')" target="_parent"><%=ps.getPermitname() %></a></li>
                               	 <%}else{%>
                               	 <li><a href="<%=basePath%><%=ps.getUrl() %>" target="iframe"><%=ps.getPermitname() %></a></li>
                               <%} }%>
                              </ul>
                          </div>
                        </div>
                      </div>
                     <%} %>
                      
                    
                       
            </div>
            </div>
            </div>
           
            <iframe name="iframe"  class="col-xs-10 content-frame" src="<%=webBasePath%>admin/user/userlist?pageSize=12">
            </iframe>
        </div>

        <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
        <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
</body>
</html>
