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
    <meta charset="utf-8">
    <title>登录</title>
	<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
   <script type="text/javascript" src="<%=basePath %>scripts/components/md5/jQuery.md5.js"></script>
   <script type="text/javascript" src="<%=basePath %>scripts/public/plug_login.js"></script>
   
   
        <style type="text/css">
       .login {
            width: 400px;
            position: relative;
        }

        .login-formArea {
            background-color: #ededed;
            padding: 25px 20px 1px 20px;
        }

        .login-close {
            cursor: pointer;
            position: absolute;
            width: 40px;
            height: 40px;
            border-radius: 50%;
            background-color: #333;
            top: -20px;
            right: -20px;
            color: #fff;
            text-align: center;
            line-height: 40px;
            font-size: 24px;
        }

        .login-close:hover {
            background-color: #f96535;
        }

        .login-close i {
            position: relative;
            top: 1px;
        }

        .login-title {
            font-size: 24px;
            font-weight: bold;
        }

        .login-form {
            padding: 5px 0 0;
        }

        .login-form-control {
            padding: 10px 0;
        }

        .login-form-control.buttonGroup {
            padding: 15px 0 20px;
        }

        .login-form-control-text {
            width: 100%;
            height: 48px;
            font-size: 14px;
        }

        @media screen\0{
            .login-form-control-text{
                line-height: 48px;
            }
        }

        .login-form-control-checkbox {
            font-size: 14px;
        }

        .login-form-control-checkbox input {
            width: 14px;
            height: 14px;
            margin: 0 3px 0 0;
        }

        .login-forget {
            font-size: 14px;
            color: #0090ed;
        }

        .login-form-button {
            width: 48%;
            text-align: center;
            height: 50px;
            line-height: 50px;
            font-size: 18px;
            padding: 0;
        }

        .login-other {
            background-color: #dfddde;
            border-top: 1px solid #b4b4b4;
            padding: 10px 20px;
        }

        .login-other-text {
            font-size: 14px;
            color: #666;
            padding: 12px 0 0;
        }

        .login-other-list {
            vertical-align: middle;
        }

        .login-other-list-item {
            float: left;
            padding: 0 8px;
        }

        .login-other-item-link {
            display: block;
            color: #626262;
            font-size: 32px;
        }

        .login-other-item-link i {
            color: #777;
        }

        .login-other-item-link:hover {
            text-decoration: none;
            color: #f96535;
        }

        .login-other-item-link i:hover {
            color: #f96535;
        }

    </style>
    
</head>
<body id="login_body" >
<div class="login">
    <div class="login-close"><i class="add-icon-close"></i></div>
    <div class="login-formArea">
        <div class="login-title">登录</div>
        <form class="login-form">
            <div class="login-login-form-control">
                <input id="login_userName" class="login-form-control-text text" type="text" placeholder="昵称/邮箱/手机号"/>
            </div>
            <div class="login-form-control">
                <input id="login_pwd" class="login-form-control-text text" type="password" placeholder="密码"/>
            </div>
            <div class="login-form-control clearfix">
                <label class="login-form-control-checkbox fl">
                    <input type="checkbox"/> 记住我
                </label>
                <a id="forgetPwd" class="login-forget fr" href="#">忘记密码</a>
            </div>
            <div class="login-form-control buttonGroup clearfix">
                <a href="#" id="index_register" class="button gray login-form-button fl">注册</a>
                <a href="#" id="index_login" class="button login-form-button black fr">登录</a>
            </div>
        </form>
    </div>
    <div class="login-other clearfix hide">
        <div class="login-other-text fl">用其他账号登录</div>
        <ul class="login-other-list fr">
            <li class="login-other-list-item">
                <a class="login-other-item-link" href="#">
                    <i class="yiqi-icon-sina"></i>
                </a></li>
            <li class="login-other-list-item">
                <a class="login-other-item-link" href="#">
                    <i class="yiqi-icon-douban"></i>
                </a></li>
            <li class="login-other-list-item">
                <a class="login-other-item-link" href="#">
                    <i class="yiqi-icon-renren"></i>
                </a></li>
            <li class="login-other-list-item">
                <a class="login-other-item-link" href="#">
                    <i class="yiqi-icon-qq"></i>
                </a></li>
        </ul>
    </div>
</div>

</body>
</html>
