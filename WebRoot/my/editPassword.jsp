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
    <title>修改密码-一起开工社区</title>
	<!--[if lte IE 8]>d
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
   
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->

<div class="container editPassword clearfix">
    <div class="panel">
        <div class="panel-header">
            <div class="panel-header-text">修改密码</div>
        </div>
        <form class="panel-content info-form" novalidate>
            <div class="form-control old">
                <div class="form-control-label">旧密码：</div>
                <div class="form-control-input">
                    <input id="oldPwd" type="password" name="name" class="form-control-text text valid-input"
                           data-valid-rule="required"
                           data-valid-text='{"required":"旧密码不能为空"}'/>
                </div>
            </div>
            <div class="form-control new">
                <div class="form-control-label">新密码：</div>
                <div class="form-control-input">
                    <input id="newPwd" type="password" name="name" class="form-control-text text valid-input"
                           data-valid-rule="required,min"
                           data-valid-min="6"
                           data-valid-text='{"required":"新密码不能为空","min":"密码字数不得少于6位"}'/>
                </div>
            </div>
            <div class="form-control old">
                <div class="form-control-label">重复密码：</div>
                <div class="form-control-input">
                    <input type="password" name="name" class="form-control-text text valid-input"
                           data-valid-rule="required,same"
                           data-valid-same=".form-control.new input"
                           data-valid-text='{"required":"重复密码不能为空","same":"两次输入的密码不一致"}'/>
                </div>
            </div>

            <div class="form-control form-control-buttons clearfix">
                <input type="button" class="button form-control-button gray" value="取消"/>
                <input type="submit" class="button form-control-button fr form-control-button-submit" value="确定"/>
            </div>

        </form>
    </div>
</div>
<!--    content   -->

<!-- footer -->
  	<jsp:include page="/pages/footer.jsp" flush="true"></jsp:include>
<!--    footer    -->
<script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/md5/jQuery.md5.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/validation/validation.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/customUI/customUI.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/my/editPwd.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/pages/my.js"></script>
 


</body>
</html>


