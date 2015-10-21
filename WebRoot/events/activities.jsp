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
    <title>活动-一起开工社区</title>
	<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/activities.css"/>
    <link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">
 	<script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
       
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->

    <!--    content   -->
    <section class="slide" data-slide='{"orderSwitch":true}'>
        <div class="slide-area container">
            <ul id="bannerList" class="slide-list">
            <!-- 
                <li class="slide-item" data-slide-bgColor="#003300" >
                    <img src="../images/demo_imgs/1.jpg" class="slide-item-img"/>
                    <div class="slide-item-info">
                        <div class="slide-item-text">
                            Through The Looking Glass - 透镜之下－<br/>Kreuzzz帽子展
                        </div>
                        <a class="slide-item-button">点击报名</a>
                    </div>
                </li>
                <li class="slide-item" data-slide-bgColor="#E0E0E0">
                    <img src="../images/demo_imgs/2.jpg" class="slide-item-img"/>
                    <div class="slide-item-info">
                        <div class="slide-item-text">
                            Through The Looking Glass - 透镜之下－Kreuzzz帽子展
                        </div>
                        <a class="slide-item-button">点击报名</a>
                    </div>
                </li>
                <li class="slide-item" data-slide-bgColor="#E0E0E0">
                    <img src="../images/demo_imgs/3.jpg" class="slide-item-img"/>

                    <div class="slide-item-info">
                        <div class="slide-item-text">
                            Through The Looking Glass - 透镜之下－Kreuzzz帽子展
                        </div>
                        <a class="slide-item-button">点击报名</a>
                    </div>
                </li>
                <li class="slide-item" data-slide-bgColor="#E0E0E0">
                    <img src="../images/demo_imgs/4.jpg" class="slide-item-img"/>

                    <div class="slide-item-info">
                        <div class="slide-item-text">
                            Through The Looking Glass - 透镜之下－Kreuzzz帽子展
                        </div>
                        <a class="slide-item-button">点击报名</a>
                    </div>
                </li>
                <li class="slide-item" data-slide-bgColor="#E0E0E0" >
                    <img src="../images/demo_imgs/2.jpg" class="slide-item-img"/>

                    <div class="slide-item-info">
                        <div class="slide-item-text">
                            Through The Looking Glass - 透镜之下－Kreuzzz帽子展
                        </div>
                        <a class="slide-item-button">点击报名</a>
                    </div>
                </li>
                 -->
            </ul>

            <div id="bannerListSmall" class="slide-buttonGroup">
            	
               <!--  <div class="slide-button active">
                    <img src="../images/demo_imgs/1.jpg" class="slide-button-img"/>

                    <div class="slide-button-mask"></div>
                </div>
                <div class="slide-button">
                    <img src="../images/demo_imgs/2.jpg" class="slide-button-img"/>

                    <div class="slide-button-mask"></div>
                </div>
                <div class="slide-button">
                    <img src="../images/demo_imgs/3.jpg" class="slide-button-img"/>

                    <div class="slide-button-mask"></div>
                </div>
                <div class="slide-button">
                    <img src="../images/demo_imgs/4.jpg" class="slide-button-img"/>

                    <div class="slide-button-mask"></div>
                </div>
                <div class="slide-button">
                    <img src="../images/demo_imgs/2.jpg" class="slide-button-img"/>

                    <div class="slide-button-mask"></div>
                </div>
                 -->
            </div>
          
        </div>

        <div class="slide-switchButtons container">
                        <button class="slide-switch-button left"><i class="add-icon-left"></i></button>
                        <button class="slide-switch-button right"><i class="add-icon-right"></i></button>
        </div>

    </section>

    <div class="container">
        <section class="panel activities">
            <div class="panel-header">
                <span class="panel-header-text">活动</span>

                <div class="panel-header-buttons">
                    <button class="panel-header-button add">+ 发起活动</button>
                </div>
            </div>

            <div class="panel-content">
                <nav id="event_lables" class="activities-nav">
                	<!-- 
                    <a class="activities-nav-link" href="#">全部</a><a class="activities-nav-link" href="#">创新</a><a
                        class="activities-nav-link" href="#">公益</a><a class="activities-nav-link" href="#">旅行</a><a
                        class="activities-nav-link" href="#">影视</a><a class="activities-nav-link" href="#">音乐</a><a
                        class="activities-nav-link" href="#">运动</a><a class="activities-nav-link" href="#">文学</a><a
                        class="activities-nav-link" href="#">游戏</a><a class="activities-nav-link" href="#">动漫</a><a
                        class="activities-nav-link" href="#">动漫</a><a class="activities-nav-link" href="#">桌游</a><a
                        class="activities-nav-link" href="#">健康</a><a class="activities-nav-link" href="#">美食</a><a
                        class="activities-nav-link" href="#">求职</a><a class="activities-nav-link" href="#">励志</a><a
                        class="activities-nav-link" href="#">创业</a><a class="activities-nav-link" href="#">金融</a><a
                        class="activities-nav-link" href="#">传媒</a><a class="activities-nav-link" href="#">设计</a><a
                        class="activities-nav-link" href="#">手工</a><a class="activities-nav-link" href="#">更多分类</a>
                      -->
                </nav>
                <section class="activities-view clearfix" id="activityList">

                </section>
            
            <div id="pagesMain">
            	<div id="pages" class="pages" >
				<a class="page-item">&lt; 上一页</a>
				<a class="page-item">1</a>
				<a class="page-item">2</a>
				<a class="page-item active">3</a>
				<a class="page-item">4</a>
				<a class="page-item">5</a>
				<a class="page-item">下一页 &gt;</a>
				<a class="page-item">最后一页 &gt;&gt;</a>
			</div>
			
        </section>
        
    </div>
    

    <!--    content   -->


<!-- footer -->
  	<jsp:include page="/pages/footer.jsp" flush="true"></jsp:include>
<!--    footer    -->
    
   
    <script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/event/activities.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/pages/activity.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>    
    <script type="text/javascript" src="<%=basePath %>scripts/components/customUI/customUI.js"></script>    
    <script type="text/javascript" src="<%=basePath %>scripts/components/slide/slide.js"></script>
   
    <script type="text/javascript" src="<%=basePath %>scripts/public/plug_login.js"></script>
	<script type="text/javascript" src="<%=basePath %>scripts/components/md5/jQuery.md5.js"></script>
    
    

</body>
</html>
