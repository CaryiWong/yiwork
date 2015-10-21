<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../res/common/taglib.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>密码重置</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/my.css"/>
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.9.1.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
function validateform(){
	var newpwd = $("#newpwd").val();
	var newpwd1 = $("#newpwd1").val();
	if(newpwd==""||newpwd1==""){
		alert("密码不能为空");
		return false;
	}else if(newpwd!=newpwd1){
		alert("两次密码输入不一致");
		return false;
	}else{
		alert("密码修改成功,请重新登录")
		return true;
	}
}
</script>
  </head>
  <body>
  <!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
  <!--    header    -->
    
  
  <div class="container editPassword editPassword-reset clearfix">
    <div class="panel">
        <div class="panel-header">
            <div class="panel-header-text">重置密码</div>
        </div>
  	<form action="<%=basePath %>v2/user/setmypwd" method="post" class="panel-content info-form" novalidate onsubmit="return validateform();">
  		<input id="userid" name="userid" value="${userid}" type="hidden"/> 
	     <div class="form-control">
                <div class="form-control-label">请输入新密码</div>
                <div class="form-control-input">
                    <input type="password" id="newpwd" name="newpwd" class="form-control-text text valid-input"
                           data-valid-rule="required"
                           data-valid-text='{"required":"新密码不能为空"}'/>
                </div>
            </div>
            <div class="form-control">
                <div class="form-control-label">再次输入密码</div>
                <div class="form-control-input">
                    <input type="password" id="newpwd1" name="newpwd1" class="form-control-text text valid-input"
                           data-valid-rule="required,min"
                           data-valid-min="6"
                           data-valid-text='{"required":"新密码不能为空","min":"密码字数不得少于6位"}'/>
                </div>
            </div>

            <div class="form-control form-control-buttons clearfix">
                <input type="submit" class="button form-control-button fr form-control-button-submit" value="提交"/>
                <input type="button" class="button form-control-button fr gray" value="取消"/>
            </div>
  	</form>
  	
  	</div>
  	</div>
  	
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

    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>
  	
  	
  </body>
</html>
