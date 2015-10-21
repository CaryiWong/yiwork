<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>退款管理</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=basePath %>v20/admin/yigather_account/refund" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div class="search-otherGroup form-inline col-xs-12">
                            <div class="form-group ">
                                    <input type="text" name="item_instance_id" class="form-control input-sm" />
                            </div>
                            <button type="submit" class=" btn btn-default btn-primary">查询</button>
                        </div>
                    </div>
                </div>
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>待退款金额</th>
                        <th>关联的用户名称</th>
                        <th>关联的订单号</th>
                        <th>关联的用户支付宝账号</th>
                        <th>关联的支付宝交易号</th>
                        <th>关联的网银流水号</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${page eq null || page.currentCount eq 0}">
                         <tr align="center">
                             <td colspan="10"><span>无记录</span></td>
                         </tr>
                     </c:if>                     
                     <c:if test="${page.currentCount gt 0}">
                         <c:forEach items="${page.result }" var="pending_refund" varStatus="vs">
                             <tr>
                                 <td>${pending_refund.id}</td>
                                 <td>${pending_refund.money}</td>
                                 <td>
                                     <c:if test="${pending_refund.userId ne null}">
                                         ${pending_refund.userNickname}(${pending_refund.userId})
                                     </c:if>
                                 </td>
                                 <td>${pending_refund.orderId}</td>
                                 <td>${pending_refund.buyerEmail}</td>
                                 <td>${pending_refund.alipayTradeNo}</td>
                                 <td>${pending_refund.bankSeqNo}</td>
                                 <td>
                                     <a class=" btn btn-warning" onclick="doRefund('${pending_refund.id}')">同意退款</a>
                                     <a class=" btn btn-warning" onclick="doNotRefund('${pending_refund.id}')">不同意退款</a>
                                 </td>
                             </tr>
                         </c:forEach>
                     </c:if>
                    
                    </tbody>
                </table>
                
                <div class="text-center">
                	 <ul class="pagination">
                     <%@ include file="/res/common/pages.jsp"%>
                    </ul>
                </div>
         </form>
            </div>
        </div>

    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
