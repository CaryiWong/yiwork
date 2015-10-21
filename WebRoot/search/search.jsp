<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="java.net.*" %>

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
//request.setCharacterEncoding("UTF-8");
String keyword=request.getParameter("keyword");
byte b[] = keyword.getBytes("ISO-8859-1");
keyword = new String(b,"UTF-8");
//keyword=java.net.URLDecoder.decode(keyword,"UTF-8");
%>
<script type="text/javascript">
	var root="<%=basePath%>";
	var keyword="<%=keyword%>";  //keyword
</script>
    <meta charset="utf-8">
    <title>搜索页面-一起开工社区</title>
	<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/search.css"/>
      <link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/search/search.js"></script> 
</head>
<body id="mybody">
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->

<!--    content   -->
<div class="container">
    <div class="searchPanel">

        <div class="search-control">
            <input id="eventSearchText" type="text" class="search-text text" value="<%=keyword %>"/>
            <i id="eventSearchIcon" class="yiqi-icon-search"></i>
        </div>
        <section class="search-area clearfix">
            <div class="search-type fl">
                <a class="search-type-button button " href="#" >活动</a>
                <a id="hrefUserSearch" class="search-type-button button gray" href="#" >会员</a>
            </div>

            <!--    search-result-xx  xx代表了搜索结果的类型  -->
            <!--    值 space=空间 activity=活动 member=会员  -->
            <section class="search-result search-result-member fl">
                <div class="search-result-header">
                    <div class="search-result-header-title">活动</div>
                    <div id="eventTotalNum" class="search-result-header-numbers">共有条结果</div>
                </div>
                <ul id="eventSearchList" class="search-result-list  resultList">

                   
                </ul>
                <button id="loadMoreEvent"  class="button gray search-more-button">继续加载更多</button>
            </section>
        </section>

    </div>
</div>
<!--    content   -->

<!-- footer -->
  	<jsp:include page="/pages/footer.jsp" flush="true"></jsp:include>
<!--    footer    -->

<script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>

</body>
</html>
    