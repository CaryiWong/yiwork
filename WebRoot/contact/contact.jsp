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
    <title>联系我们-一起开工社区</title>
	<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">
    <style type="text/css">
 
        .article {
            width: 950px;
            margin: 0 auto;
            padding: 10px 0;
        }

        .article-title {
            text-align: center;
            font-size: 28px;
            font-weight: bold;
            color: #000;
        }

        .article-content {
            padding: 10px 0;
            line-height: 20px;
            min-height: 600px;
        }

        .article-close {
            margin: 5px 0;
            color: #333;
            background-color: #fff;
            border: 1px solid #ccc;
            padding: 8px 12px;
            font-size: 14px;
            text-align: center;
            vertical-align: middle;
            cursor: pointer;
            width: 100%;
            border-radius: 4px;
        }

        .article-close:hover {
            background-color: #ebebeb;
            border-color: #adadad;
        }

        .article-list-title {
            font-size: 32px;
            font-weight: bold;
            margin: 0 0 15px;
            padding: 15px 0 0;
        }

        .article-list {
            margin: 0 0 60px;
        }

        .article-list-item {
            padding: 3px 0;
            font-size: 14px;
        }
    </style>
    
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
   
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->

   <!--    content   -->
<div class="article">
    <div class="article-title">联系我们</div>
    <div class="article-content">
        <div class="article-list-title">一起开工社区</div>
        <ul class="article-list">
            <li class="article-list-item">地址：广州市荔湾区中山七路68号</li>
            <li class="article-list-item">电话：020-81869804</li>
            <li class="article-list-item">邮箱：yigather@gmail.com</li>
        </ul>

        <div class="article-list-title">关注我们：</div>
        <ul class="article-list">
            <li class="article-list-item">新浪微博：一起开工社区</li>
            <li class="article-list-item">微信订阅号：一起开工社区</li>
            <li class="article-list-item">APP客户端（Android/IOS平台）搜：一起"</li>
        </ul>

    </div>
</div>
<!-- footer -->
  	<jsp:include page="/pages/footer.jsp" flush="true"></jsp:include>
<!--    footer    -->

<!--    footer    -->
<script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/slide/slide.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>   
<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/plug_login.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/md5/jQuery.md5.js"></script>
</body>
</html>
   
    