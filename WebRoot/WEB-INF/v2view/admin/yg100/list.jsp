<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>阿尔勒工作营</title>
    <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
    <link rel="stylesheet" href="<%=basePath%>/styles/admin/house_list.css"/>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-12">
                <table class="list-table table table-bordered table-hover table-striped">
                    <thead>
                    <tr>
                        <th>手机号码</th>
                        <th>客户称呼</th>
                        <th>标题</th>
                        <th>购买人数</th>
                        <th>购买天数</th>
                        <th>总价</th>
                        <th>入住时间</th>
                        <th>优惠券号</th>
                        <th>订单状态</th>
                        <th>订单ID</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <!--<tr class="order-item">-->
                        <!--<td>1340000000</td>-->
                        <!--<td>阳光100买房</td>-->
                        <!--<td>5</td>-->
                        <!--<td>3</td>-->
                        <!--<td>900</td>-->
                        <!--<td>2015-09-18</td>-->
                        <!--<td>未支付</td>-->
                        <!--<td>-->
                            <!--<button class="btn btn-primary btn-sm">发送验证码</button>-->
                        <!--</td>-->
                    <!--</tr>-->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="<%=basePath%>/scripts/components/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/scripts/admin/house_list.js"></script>
</body>
</html>
