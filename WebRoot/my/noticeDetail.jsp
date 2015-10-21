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
<%
String msgId=request.getParameter("msgId");
%>
<script type="text/javascript">
	var root="<%=basePath%>";
	var msgId="<%=msgId%>";
</script>
    <meta charset="utf-8">
    <title>查看系统消息-一起开工社区</title>
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
    <script type="text/javascript" src="<%=basePath %>scripts/my/noticeDetail.js"></script>   
    
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->

<!--    content   -->
<div class="container">
    <div class="myNotice myNotice-detail">
        <div class="myNotice-detail-header">
            <div id="msgTitle" class="myNotice-detail-title"></div>
            <time id="msgTime" class="myNotice-detail-time"></time>
        </div>
        <div id="msgSummary" class="myNotice-detail-content">
            
        </div>
    </div>
</div>
<!--    content   -->

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
    