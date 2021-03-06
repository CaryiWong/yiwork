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
    <title>一起账户的支付宝／网银支付通知记录</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap-datetimepicker.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=basePath %>v20/admin/yigather_account/view_alipay_log" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div class="row">
                            <div class="col-xs-4">
                                <div id="id_start_time_div" class="input-group date" >
                                    <input class="form-control" type="text" name="start_date" value="${start_date}" placeholder="起始日期">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                                </div>
                            </div>
                            <div class="col-xs-4">
                                <div id="id_end_time_div" class="input-group date">
                                    <input class="form-control" type="text" name="end_date" value="${end_date}" placeholder="结束日期">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                                </div>
                            </div>
                            <div class="col-xs-4">
                                <button type="submit" class=" btn btn-default btn-primary">查询</button>
                            </div>
                        </div>
                    </div>
                </div>
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>时间</th>
                        <th>通知号</th>
                        <th>支付方式</th>
                        <th>支付宝流水号</th>
                        <th>支付方的支付宝账号</th>
                        <th>银联流水号</th>
                        <th>金额</th>
                        <th>关联的订单号</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${page eq null || page.currentCount eq 0}">
                         <tr align="center">
                             <td colspan="10"><span>无记录</span></td>
                         </tr>
                     </c:if>                     
                      <c:if test="${page.currentCount gt 0 }">
                         <c:forEach items="${page.result }" var="alipay_log" varStatus="vs">
                         <tr>
                             <td>${alipay_log.notifyTime}</td>
                             <td>${alipay_log.notifyId}</td>
                             <td>${alipay_log.paymentType}</td>
                             <td>${alipay_log.alipayTradeNo}</td>
                             <td>${alipay_log.buyerEmail}</td>
                             <td>${alipay_log.bankSeqNo}</td>
                             <td>${alipay_log.totalFee}</td>
                             <td>${alipay_log.orderId}</td>
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
    <script type="text/javascript" src="<%=basePath%>res/scripts/moment.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
    <script>
        $().ready(function(){
            $('#id_start_time_div').datetimepicker({
                language: "zh-CN",
                format: "YYYY-MM-DD",
                autoclose: true,
                todayHighlight: true,
                pickTime: false
            });
            $('#id_end_time_div').datetimepicker({
                language: "zh-CN",
                format: "YYYY-MM-DD",
                autoclose: true,
                todayHighlight: true,
                pickTime: false
            });
        });
    </script>
</body>
</html>
