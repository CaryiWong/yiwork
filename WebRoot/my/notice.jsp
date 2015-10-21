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
    <title>收件箱-一起开工社区</title>
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
    <link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/my/notice.js"></script>
       
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->

<!--    content   -->
<div class="container">
    <div class="myNotice">
        <div class="myNotice-header">
               <div class="myNotice-header-title">你的信息</div>
               <div id="" class="myNotice-header-numbers">共有<span id="totalMsg"></span>条消息/<a href="javascript:void(0)"><span id="unReadMsg" class="notice-blue"></span></a>条未读</div>
           </div>
           <ul id="msgList" class="myNotice-list resultList">
           <!-- 
               <li class="myNotice-list-item resultList-item">
                   <div class="myNotice-item-header">
                       <div class="myNotice-item-title">系统推送</div>
                       <time class="myNotice-item-time">2014-4-4</time>
                   </div>
                   <div class="myNotice-item-content">
                       你的活动【孤独美食家】在2014-4-4 3：30PM 于广州举行！
                   </div>
               </li>
               <li class="myNotice-list-item resultList-item">
                   <div class="myNotice-item-header">
                       <div class="myNotice-item-title">系统推送</div>
                       <time class="myNotice-item-time">2014-4-4</time>
                   </div>
                   <div class="myNotice-item-content">
                       每逢周六上午09：00——13：00，带上你的悠闲！带上你的创意！慵懒的来享受咖啡吧！
                   </div>
               </li>
               <li class="myNotice-list-item resultList-item">
                   <div class="myNotice-item-header">
                       <div class="myNotice-item-title">系统推送</div>
                       <time class="myNotice-item-time">2014-4-4</time>
                   </div>
                   <div class="myNotice-item-content">
                       拿起你的相机，和我们一同探寻老广州的光与影，留住那些年我们共同的回忆，快快加入光影捕手
                       的行列！拿起你的相机，和我们一同探寻老广州的光与影，留住那些年我们共同的回忆，快快加入
                       光影捕手的行列！拿起你的相机，留住那些年我们共同的回忆！
                   </div>
               </li>
                -->
           </ul>
           
            <div id="pagesNotice" class="pages" >
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

        
    
</div>


<!-- footer -->
  	<jsp:include page="/pages/footer.jsp" flush="true"></jsp:include>
<!--    footer    -->

<script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/customUI/customUI.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/validation/validation.js"></script>


</body>
</html>
 

 