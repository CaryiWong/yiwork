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
    <title>商品实例追踪</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=basePath %>v20/admin/item/item_trace" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div class="search-otherGroup form-inline col-xs-12">
                            <div class="form-group ">
                                    <input type="text" name="item_instance_id" class="form-control input-sm" value="${item_instance_id}" placeholder="商品实例编号"/>
                            </div>
                            <button type="submit" class=" btn btn-default btn-primary">查询</button>
                        </div>
                    </div>
                </div>
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>商品实例编号</th>
                        <th>商品名称</th>
                        <th>状态</th>
                        <th>关联的订单号</th>
                        <th>属主用户</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${item_instance eq null}">
                         <tr align="center">
                             <td colspan="10"><span>无记录</span></td>
                         </tr>
                     </c:if>                     
                     <c:if test="${item_instance ne null}">
                         <tr>
                             <td>${item_instance.id}</td>
                             <td>${item_instance.sku.name}</td>
                             <td>${item_instance.statusName}</td>
                             <td>${item_instance.orderId}</td>
                             <td>
                                 <c:if test="${item_instance.userId ne null}">
                                     ${item_instance.userNickname}(${item_instance.userId})
                                 </c:if>
                             </td>
                             <td></td>
                         </tr>
                     </c:if>
                    
                    </tbody>
                </table>
                
                商品追踪记录
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>时间</th>
                        <th>动作</th>
                        <th>关联的订单号</th>
                        <th>属主用户</th>
                        <th>备注</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${page.totalPage eq 0}">
                        <tr align="center">
                            <td colspan="10"><span>无记录</span></td>
                        </tr>
                     </c:if>                     
                     <c:if test="${page.totalPage gt 0 }">
                         <c:forEach items="${page.result }" var="item_log" varStatus="vs">
                             <tr>
                                 <td>${item_log.dateTime}</td>
                                 <td>${item_log.opTypeName}</td>
                                 <td>${item_log.orderId}</td>
                                 <td>
                                     <c:if test="${item_log.userId ne null}">
                                         ${item_log.userNickname}(${item_log.userId})
                                     </c:if>
                                 </td>
                                 <td>${item_log.memo}</td>
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
