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
	<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">
    <title>找回密码-一起开工社区</title>
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
       
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->

<!--    content   -->
<div class="container editPassword editPassword-forget clearfix">
    <div class="panel">
        <div class="panel-header">
            <div class="panel-header-text">找回密码</div>
        </div>
        <div  class="panel-content">
            <div id="main"  class="forget-step-email clearfix">
                <p class="forget-text">如果您的账号绑定了邮箱，您可以使用已验证的邮箱地址重设您的密码</p>
                <div class="form-control forget">
                    <div class="form-control-label">请填写您绑定邮箱地址：</div>
                    <div class="form-control-input">
                        <input type="text" class="form-control-text" />
                    </div>
                </div>
                <div class="form-control form-control-buttons fr">
                    <input type="button" class="form-control-button button gray" value="取消" />
                    <input id="findBtn" type="button" class="form-control-button form-control-button-submit button" value="提交" />
                </div>
            </div>
            <div id="success" class="forget-step-success clearfix hide">

                <div class="forget-success-content clearfix">
                    <div class="forget-success-img fl">
                        <i class="add-icon-successForget"></i>
                    </div>
                    <div class="forget-success-text fl">
                        我们已经给您的邮箱发了邮件， <br/>
                        请访问邮件中给出的网页链接地址， <br/>
         <font color="red">温馨提示:如果没有收到邮件,尝试在垃圾箱里查看</font><br/>
                        根据页面提示完成密码重置。
                    </div>
                </div>

                <div class="form-control form-control-buttons fr">
                    <input id="shangyibu" type="button" class="form-control-button button gray" value="上一步"/>
                   <!--  <input  id="sucsBtn" type="button" class="form-control-button form-control-button-submit button" value="确定"/> -->
                </div>
            </div>
        </div>
    </div>
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
<script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/md5/jQuery.md5.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/plug_login.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/pages/findpwd.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/validation/validation.js"></script>

</body>
</html>


    