<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="cn.yi.gather.v20.entity.*" %>
<%@ page language="java" import="com.common.R" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String webBasePath=basePath+"v20/";
User loginU=(User)session.getAttribute(R.User.SESSION_USER);
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
 

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          
               
            </div>
        </nav>
        <div class="row row-flow">
            <div class="col-xs-2 nav-list">
                <div class="nav-list">
                    <div class="panel-group" id="accordion">
                      <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
				                             角色  
                            </a>
                          </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                                  <!-- <li><a href="member/add.jsp" target="iframe">会员申请</a></li> -->
                                  <li class="active"><a href="<%=webBasePath%>admin/user/userlist?pageSize=12" target="iframe">用户列表</a></li>
                                  <li class=""><a href="<%=webBasePath%>admin/permission/addUser" target="iframe">新增用户</a></li>
                                  <li class=""><a href="<%=webBasePath%>admin/permission/addRole" target="iframe">新增部门</a></li>
                                  <li class=""><a href="<%=webBasePath%>admin/permissionr/addPermission" target="iframe">新增权限</a></li>
                                  
                              </ul>
                          </div>
                        </div>
                      </div>
			 
            </div>
            </div>
            </div>
           
            <iframe name="iframe"  class="col-xs-10 content-frame" src="<%=webBasePath%>admin/user/userlist?pageSize=5">
            </iframe>
        </div>

        <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
        <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
</body>
</html>
