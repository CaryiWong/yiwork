<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    
    <meta charset="utf-8">
    <title>后台主页</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
        <nav class="navbar navbar-default" role="navigation">
            <div class="navbar-header">
                <p class="navbar-text">Yi-gather 后台</p>
            </div>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="<%=basePath %>admin/user/logout">退出</a></li>
                </ul>
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
                              会员
                            </a>
                          </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                                  <!-- <li><a href="member/add.jsp" target="iframe">会员申请</a></li> -->
                                  <li class="active"><a href="<%=basePath%>v2/admin/user/userlist?pageSize=12" target="iframe">用户列表</a></li>
                                  <li ><a href="<%=basePath%>v2/admin/user/addapplyvip" target="iframe">会员申请</a></li>
                                  <li><a href="<%=basePath%>admin/user/viplist?pageSize=12" target="iframe">会员列表</a></li>
                                  <!-- <li><a href="<%=basePath%>admin/user/checkinlist?pageSize=12" target="iframe">在线列表</a></li> -->
                              </ul>
                          </div>
                        </div>
                      </div>
                      <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                              活动
                            </a>
                          </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                                  <li><a href="<%=basePath%>admin/activity/activitylist?pageSize=12" target="iframe">活动列表</a></li>
                              </ul>
                          </div>
                        </div>
                      </div>
                      <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                              其他
                            </a>
                          </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                               	  <li><a href="<%=basePath%>admin/demand/demandlist?pageSize=12" target="iframe">需求列表</a></li>
                                  <li><a href="<%=basePath%>admin/user/broadcastlist?pageSize=12" target="iframe">广播列表</a></li>
                                  <li><a href="<%=basePath%>admin/display/hugolist" target="iframe">hugo列表</a></li>
                                  <li><a href="<%=basePath%>admin/display/teamlist" target="iframe">入驻列表</a></li>
                                  <li><a href="<%=basePath%>admin/display/showspace" target="iframe">空间区域</a></li>
                                  <li><a href="<%=basePath%>version/versionlist?pageSize=99" target="iframe">版本列表</a></li>
                                  <li><a href="<%=basePath%>admin/user/feedbacklist?pageSize=12" target="iframe">反馈列表</a></li>
                                  <!-- <li><a href="<%=basePath%>/apply/versionList.jsp" target="iframe">反馈列表</a></li> -->
                              </ul>
                          </div>
                        </div>
                      </div>
                    </div>
                </div>

            </div>
            <iframe name="iframe"  class="col-xs-10 content-frame" src="<%=basePath%>admin/user/userlist?pageSize=12">
            </iframe>
        </div>

        <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
        <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
</body>
</html>
