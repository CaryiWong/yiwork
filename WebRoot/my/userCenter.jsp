<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<!--[if IE 8]>
<html lang="zh-CN" class="no-js lt-ie9"><![endif]-->
<!--[if gt IE 8]><!-->
<html lang="zh-CN" class="no-js"><!--<![endif]-->
<head>
<script type="text/javascript">
	var root="<%=basePath%>";
</script>
    <meta charset="utf-8">
    <title>个人中心-一起开工社区</title>
	<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/my.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/activities.css"/>
    <link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">
    
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
   
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->

   
<!--    content   -->
<div class="container personal clearfix">
    <section class="myInfo fl">
        <div class="myInfo-basic myInfo-frame">
            <div class="myInfo-basic-head">
                <img id="user_img" src="" />
                <div id="user_nickName" class="myInfo-basic-name"></div>
                <button id="editBasicInfo" class="myInfo-basic-edit"><i class="yiqi-icon-tool"></i></button>
            </div>
            <div id="user_job" class="myInfo-basic-job myInfo-basic-item">
                <i class="yiqi-icon-card"></i>
            </div>
            <address id="user_address" class="myInfo-basic-address myInfo-basic-item">
                <i class="yiqi-icon-place"></i>
            </address>
        </div>
        
        <!-- add -->
         <div class="myInfo-center myInfo-frame">
            <div class="myInfo-frame-head">
                个人设置
            </div>
            <div class="myInfo-frame-content">
                <ul class="myInfo-domain-list">
                    <li class="domain-list-item"><a class="domain-link" href="<%=basePath %>/my/editPassword.jsp">修改密码</a></li>
                    <li class="domain-list-item"><a id="editeUser" class="domain-link" href="javascript:void(0)">修改个人资料</a></li>
                </ul>
            </div>
        </div>
        
        <div class="myInfo-domain myInfo-frame">
            <div class="myInfo-frame-head">
                我关注的领域
            </div>
            <div class="myInfo-frame-content">
                <ul class="myInfo-domain-list" id="user_lingyu">
                    <li class="domain-list-item"><a class="domain-link" href="#">社会创新</a></li>
                </ul>
            </div>
        </div>
        <div class="myInfo-notice myInfo-frame">
            <div class="myInfo-frame-head">系统消息</div>
            <div class="myInfo-frame-content">
                暂无消息
            </div>
        </div>
        
        <div class="myInfo-logOut myInfo-frame">
            <div id="index_exit" class="myInfo-frame-head red">退出登录</div>
        </div>
        
    </section>
    <section class="myActivities fl">
        <div class="myActivities-buttons">
            <button value="2" class="button black myActivities-button apply">我报名的活动</button>
            <button value="3" class="button gray myActivities-button create">我创建的活动</button>
        </div>
        <div id="userAttendEvent" class="myActivities-view apply">
        <!-- 
            <div class="activity">
                <img src="" class="activity-img"/>

                <div class="activity-info">
                    <time class="activity-time">
                        <span class="activity-time-year">2014.3</span>
                        <span class="activity-time-date">20</span>
                    </time>
                    <div class="activity-title">
                        广州历史建筑保护的反思
                    </div>
                </div>
                <div class="activity-apply">
                    已报名30人
                </div>
            </div>
            <div class="activity">
                <img src="" class="activity-img"/>

                <div class="activity-info">
                    <time class="activity-time">
                        <span class="activity-time-year">2014.3</span>
                        <span class="activity-time-date">20</span>
                    </time>
                    <div class="activity-title">
                        广州历史建筑保护的反思
                    </div>
                </div>
                <div class="activity-apply going">
                    进行中
                </div>
            </div>
            <div class="activity">
                <img src="" class="activity-img"/>

                <div class="activity-info">
                    <time class="activity-time">
                        <span class="activity-time-year">2014.12</span>
                        <span class="activity-time-date">20</span>
                    </time>
                    <div class="activity-title">
                        广州历史建筑保护的反思
                    </div>
                </div>
                <div class="activity-apply end">
                    已结束
                </div>
            </div>
            <div class="activity">
                <img src="" class="activity-img"/>

                <div class="activity-info">
                    <time class="activity-time">
                        <span class="activity-time-year">2014.3</span>
                        <span class="activity-time-date">20</span>
                    </time>
                    <div class="activity-title">
                        广州历史建筑保护的反思
                    </div>
                </div>
                <div class="activity-apply">
                    已报名30人
                </div>
            </div>
            <div class="activity">
                <img src="" class="activity-img"/>

                <div class="activity-info">
                    <time class="activity-time">
                        <span class="activity-time-year">2014.3</span>
                        <span class="activity-time-date">20</span>
                    </time>
                    <div class="activity-title">
                        广州历史建筑保护的反思
                    </div>
                </div>
                <div class="activity-apply">
                    已报名30人
                </div>
            </div>
             -->
        </div>
        
        
        
        
        <div id="userCreateEvent" class="myActivities-view mine hide">
        <!-- 
            <div class="activity">
                <img src="" class="activity-img"/>

                <div class="activity-info">
                    <time class="activity-time">
                        <span class="activity-time-year">2014.3</span>
                        <span class="activity-time-date">20</span>
                    </time>
                    <div class="activity-title">
                        广州历史建筑保护的反思2
                    </div>
                </div>
                <div class="activity-apply edit">
                    <i class="yiqi-icon-tool"></i>
                </div>
            </div>
            <div class="activity">
                <img src="" class="activity-img"/>

                <div class="activity-info">
                    <time class="activity-time">
                        <span class="activity-time-year">2014.3</span>
                        <span class="activity-time-date">20</span>
                    </time>
                    <div class="activity-title">
                        广州历史建筑保护的反思
                    </div>
                </div>
                <div class="activity-apply edit">
                    <i class="yiqi-icon-tool"></i>
                </div>
            </div>
             -->
        </div>
        
           
    </section>
    	  <div id="createPages" class="pages" >
    	  <!-- 
				<a class="page-item">&lt; 上一页</a>
				<a class="page-item">1</a>
				<a class="page-item">2</a>
				<a class="page-item active">3</a>
				<a class="page-item">4</a>
				<a class="page-item">5</a>
				<a class="page-item">下一页 &gt;</a>
				<a class="page-item">最后一页 &gt;&gt;</a>
		 -->
		 </div>
</div>
<!--    content   -->

<!-- footer -->
  	<jsp:include page="/pages/footer.jsp" flush="true"></jsp:include>
<!--    footer    -->

<!--    footer    -->
<script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/customUI/customUI.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/slide/slide.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>   
<script type="text/javascript" src="<%=basePath %>scripts/my/userCenter.js"></script>   
<script type="text/javascript" src="<%=basePath %>scripts/pages/my.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/pages/activity.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>
</body>
</html>
   
    