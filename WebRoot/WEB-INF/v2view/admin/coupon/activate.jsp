<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Date date = new Date();
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>使用优惠卷</title>
     <link rel="stylesheet" href="<%=basePath %>styles/pages/apply.css"/>
<style type="text/css">
input#yz-fmsubmit {
width: 78px;
height: 27px;
display: inline-block;
overflow: hidden;
background-position: 0px -72px;
text-align: center;
line-height: 27px;
color: #FFF;
text-decoration: none;
margin-right: 16px;
text-shadow: 1px 1px 1px #F60;
}

.btn, .btn span {
border: 0;
background: url(<%=basePath%>/res/images/buttons.png) no-repeat;
cursor: pointer;
}

.valid-error {
    color: #f76d30;
}



</style>

<script type="text/javascript">
	var root="<%=basePath%>";
</script>


</head>

<body>
 <!--    content   -->
	<div class="container">
		<form class="apply-form apply-form-enter" name="myform" onSubmit="return check()" action="<%=basePath%>v20/admin/coupon/usecoupon" method="post">
			<div class="form-title">
				<strong> 优惠卷使用</strong>
			</div>
			<div class="form-control-area">
				<div class="form-control name">
					<label class="form-control-label"> 代金券号：<span
						class="form-control-label-required"></span>
					</label>
					<div class="form-control-input">
						<input id="couponNum" name="coffeenum" class="form-control-text text valid-input"
							type="text" data-valid-rule="required"  
							data-valid-text='{"required":"请填写代金券"}' />
							 
					</div>
				</div>
			</div>
			
			<div class="form-control form-control-buttonGroup">
				<input type="button" id="yz-fmsubmit" class="btn btn-fmsubmit"
					name="yz-fmsubmit" value="验证代金券">
			</div>
			<font color="red">${tips}</font>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/coupon/coupon.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
