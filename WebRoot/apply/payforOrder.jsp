<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="cn.yi.gather.v20.entity.*"%>
<%@ page language="java" import="com.common.*"%>
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
<script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">
    <title>正在跳转到支付宝-一起开工社区</title>
	<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
   
</head>
<body onload="takeTarget()">
<script>
	function takeTarget()
	{
		var payinfo=this.opener.document.getElementById('payorderinfo').value; //控制父窗口\
		util.loading.show();
		$('body').append(payinfo);
		this.opener.document.getElementById('payorderid').value=$("input[name='out_trade_no']").val();
		//document.getElementById('1').value = this.opener.name;//获取父窗口变量(我是父窗口) 
	}
</script>
</body>
正在跳转到支付宝......
</html>
    
    