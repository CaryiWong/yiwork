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

        <nav class="navbar navbar-default" role="navigation">
            <div class="navbar-header">
                <p class="navbar-text">Yi-gather V2 后台  ，欢迎你 <%=loginU.getNickname() %></p>
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
                    
                    
                    <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapsetwelve">
                              管理员设置
                            </a>
                          </h4>
                        </div>
                        <div id="collapsetwelve" class="panel-collapse collapse">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                                 <li><a href="<%=webBasePath%>admin/permission/adduser" target="iframe">添加管理员</a></li>
                                 <li><a href="<%=webBasePath%>admin/permission/addrole" target="iframe">添加角色</a></li>
                                 <li><a href="<%=webBasePath%>admin/permission/permanage" target="iframe">角色管理</a></li>
                              </ul>
                          </div>
                        </div>
                      </div>
                      
                      
                    
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
                                  <li class="active"><a href="<%=webBasePath%>admin/user/userlist?pageSize=12" target="iframe">用户列表</a></li>
                                  <%-- <li ><a href="<%=webBasePath%>admin/user/addapplyvip" target="iframe">会员申请</a></li> --%>
                                 <!--<li><a href="<%=webBasePath%>admin/user/viplist?pageSize=12" target="iframe">会员列表</a></li> -->
                                  <!-- <li><a href="<%=webBasePath%>admin/user/checkinlist?pageSize=12" target="iframe">在线列表</a></li> -->
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
                                  <li><a href="<%=webBasePath%>admin/activity/activitylist?pageSize=12" target="iframe">活动列表v2</a></li>
                              </ul>
                               <ul class="nav nav-pills nav-stacked">
                                  <li><a href="<%=webBasePath%>admin/littleactivity/activitylist?pageSize=12" target="iframe">小活动列表</a></li>
                              </ul>
                              <%--  <ul class="nav nav-pills nav-stacked">
                                  <li><a href="<%=webBasePath%>admin/activity/getcourselist?pageSize=1" target="iframe">课程列表</a></li>
                              </ul> --%>
                          </div>
                        </div>
                      </div>
                      
                        <!-- add -->
                        <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-tog	gle="collapse" data-parent="#accordion" href="#collapseFours">
                              文章+公告
                            </a>
                          </h4>
                        </div>
                        <div id="collapseFours" class="panel-collapse collapse">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                               	  <li><a href="<%=webBasePath%>admin/article/articlelist?pageSize=12" target="iframe">文章列表</a></li>
                              </ul>
                               <ul class="nav nav-pills nav-stacked">
                               	   <li><a href="<%=webBasePath%>admin/article/getcreatearticle" target="iframe">创建文章</a></li>
                              </ul>
                              <ul class="nav nav-pills nav-stacked">
                              	 <li><a href="<%=webBasePath%>admin/user/noticemsglist?pageSize=99" target="iframe">公告管理</a></li>
                              </ul>
                          </div>
                        </div>
                      </div>
                 
                      
                      
                      <!-- add -->
                        <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-tog	gle="collapse" data-parent="#accordion" href="#collapseFour">
                              需求
                            </a>
                          </h4>
                        </div>
                        <div id="collapseFour" class="panel-collapse collapse">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                               	  <li><a href="<%=webBasePath%>admin/demand/demandlist?pageSize=12&demandtype=-1" target="iframe">需求列表v2</a></li>
                              </ul>
                          </div>
                        </div>
                      </div>
                 
                
                <!-- add 
                        <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
                              互动
                            </a>
                          </h4>
                        </div>
                        <div id="collapseFive" class="panel-collapse collapse">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                               	  <%-- 1.5屏蔽 <li><a href="<%=webBasePath%>admin/user/broadcastlist?pageSize=12" target="iframe">广播列表v2</a></li> --%>
                               	  <li><a href="<%=webBasePath%>admin/user/feedbacklist?pageSize=12" target="iframe">反馈列表v2</a></li>
                              </ul>
                          </div>
                        </div>
                      </div>
                -->
                
              <%--    <!-- add -->
                        <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseSix">
                              网站
                            </a>
                          </h4>
                        </div>
                        <div id="collapseSix" class="panel-collapse collapse">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                               	  <li><a href="<%=webBasePath%>admin/display/hugolist" target="iframe">hugo列表</a></li>
                                  1.5屏蔽 <li><a href="<%=webBasePath%>admin/display/teamlist" target="iframe">团队列表</a></li>
                                  <li><a href="<%=webBasePath%>admin/display/showspace" target="iframe">空间区域</a></li>
                                 1.5屏蔽 <li><a href="<%=webBasePath%>admin/display/joinapplicationlist?pageSize=12" target="iframe">团队入住申请列表</a></li>
                              </ul>
                          </div>
                        </div>
                      </div>
                    --%>

				   <!-- add -->
                        <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseSeven">
                              商品管理
                            </a>
                          </h4>
                        </div>
                        <div id="collapseSeven" class="panel-collapse collapse">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                                 <%-- <li><a href="<%=webBasePath%>admin/item/item_class_list?pageSize=20" target="iframe">商品大类管理</a></li> --%>
                                 <li><a href="<%=webBasePath%>admin/item/skulist?pageSize=20" target="iframe">商品种类管理</a></li>
                                 <li><a href="<%=webBasePath%>admin/item/inventory?pageSize=20" target="iframe">库存管理</a></li>
                                 <li><a href="<%=webBasePath%>admin/item/item_trace?pageSize=20" target="iframe">商品追踪</a></li>
                                 <li><a href="<%=webBasePath%>admin/item/order?pageSize=20" target="iframe">订单查询</a></li>
                                 <li><a href="<%=webBasePath%>admin/item/giveuserlist?pageSize=100" target="iframe">赠送商品</a></li>
                              </ul>
                          </div>
                        </div>
                      </div>
                   
                   <!-- add -->
                        <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseEight">
                              “一起”账户管理
                            </a>
                          </h4>
                        </div>
                        <div id="collapseEight" class="panel-collapse collapse">
                          <div class="panel-body">
                             <ul class="nav nav-pills nav-stacked">
                                 <li><a href="<%=webBasePath%>admin/yigather_account/pending_refund?pageSize=99" target="iframe">退款审核</a></li>
                                 <li><a href="<%=webBasePath%>admin/yigather_account/view_log?pageSize=99" target="iframe">收支流水</a></li>
                                 <li><a href="<%=webBasePath%>admin/yigather_account/view_alipay_log?pageSize=99" target="iframe">支付宝/网银支付记录</a></li>
                              </ul>
                          </div>
                        </div>
                      </div>
                   
                   <!-- add -->
                        <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseNine">
                              会员资产管理
                            </a>
                          </h4>
                        </div>
                        <div id="collapseNine" class="panel-collapse collapse">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                                 <li><a href="<%=webBasePath%>admin/user_account/info?pageSize=20" target="iframe">账户情况</a></li>
                                 <li><a href="<%=webBasePath%>admin/user_account/view_log?pageSize=99" target="iframe">账户流水</a></li>
                                 <li><a href="<%=webBasePath%>admin/user_account/view_item_log?pageSize=99" target="iframe">商品购买/使用记录</a></li>
                              </ul>
                          </div>
                        </div>
                      </div>
                      
                   <!-- add -->
                        <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTen">
                              其它
                            </a>
                          </h4>
                        </div>
                        <div id="collapseTen" class="panel-collapse collapse">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                                 <li><a href="<%=webBasePath%>version/versionlist?pageSize=99" target="iframe">版本列表v2</a></li>
                                 <li><a href="<%=webBasePath%>admin/coupon/getcouponpage?pageSize=20" target="iframe">优惠卷使用</a></li>
                                 <li><a href="<%=webBasePath%>admin/coupon/couponlist?pageSize=20" target="iframe">优惠卷使用记录</a></li>
                                 <li><a href="<%=webBasePath%>admin/labels/labels?labeltype=job" target="iframe">标签管理</a></li>
                                 <li><a href="<%=webBasePath%>admin/labels/qylabels" target="iframe">标签迁移</a></li>
                                 <li><a href="javascript:void(0)" onclick="javscript:window.open('<%=basePath%>editor/app/index.html?userid=<%=userid%>')" target="_parent">编辑器</a></li>
                                 <li><a href="<%=webBasePath%>admin/crest/crestlist?pageSize=20" target="iframe">预约表单查看</a></li>
                                 <%-- <li><a href="<%=basePath%>admin/user/importuser" target="iframe">导出EXCEL</a></li> --%>
                              </ul>
                          </div>
                        </div>
                      </div>
                      
                      <div class="panel panel-default">
                        <div class="panel-heading">
                          <h4 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseEleven">
                              服务管理
                            </a>
                          </h4>
                        </div>
                        <div id="collapseEleven" class="panel-collapse collapse">
                          <div class="panel-body">
                              <ul class="nav nav-pills nav-stacked">
                                 <li><a href="<%=webBasePath%>admin/yqservice/addyqservice" target="iframe">新增服务</a></li>
                              </ul>
                          </div>
                        </div>
                      </div>
                      
                      
                      
			 
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
