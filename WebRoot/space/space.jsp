<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="zh-CN" >
<head>
<script type="text/javascript">
	var root="<%=basePath%>";
</script>
    <meta charset="utf-8">
    <title>空间</title>
    <!--[if lte IE 8]>
    <script type="text/javascript" src="../scripts/components/html5/html5.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/space.css"/>
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>plug/layer-v1.7.0/layer-v1.7.0/layer/layer.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
</head>
<body>
    <!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->
    
    <!--    content   -->
    <section class="banner">
        <div class="container">
            <div class="banner-content">
                <p class="banner-text">
                    秋天到了,快和你的小伙伴一起跟上秋天的节奏!<br/>
                    带上你的装备和 一颗年轻的心,一起去秋游吧!!!
                </p>
                <button class="banner-button">
                    点击报名
                </button>
            </div>
            <div class="banner-img">

            </div>
        </div>
    </section>

    <div class="container">
        <section class="apply clearfix">
            <div class="apply-item vip fl">
                <div class="apply-header">

                </div>
                <div class="apply-text">
                    成为"一起开工社区"的付费会员后,你即可享受线上及线下的专属会员福利.
                </div>
                <div class="apply-button-group clearfix">
                    <a class="apply-button detail">查看详情</a>
                    <a class="apply-button signUp">点击报名</a>
                </div>
            </div>
            <div class="apply-item enter fr">
                <div class="apply-header">
                    <div class="apply-title">入驻</div>
                </div>
                <div class="apply-text">
                    成为"一起开工社区"的付费会员后,你即可享受线上及线下的专属会员福利.
                </div>
                <div class="apply-button-group clearfix">
                    <a class="apply-button detail">查看详情</a>
                    <a class="apply-button signUp">点击报名</a>
                </div>
            </div>
        </section>
    </div>
    <!--    content   -->


    <!--    footer    -->
    <footer class="footer">
        <ul class="footer-nav">
            <li class="footer-nav-item"><a class="footer-nav-link" href="#">关于我们</a></li>
            <li class="footer-nav-item"><a class="footer-nav-link" href="#">版权说明</a></li>
            <li class="footer-nav-item"><a class="footer-nav-link" href="#">联系我们</a></li>
            <li class="footer-nav-item"><a class="footer-nav-link" href="#">意见反馈</a></li>
        </ul>
        <div class="footer-text">
            广州公司
        </div>
    </footer>
    <!--    footer    -->


</body>
</html>
