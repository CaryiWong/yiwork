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
    <title>订单查询</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=basePath %>v20/admin/item/order" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div class="search-otherGroup form-inline col-xs-12">
                            <div class="form-group ">
                                    <input type="text" name="order_id" class="form-control input-sm" value="${order_id}" placeholder="订单号"/>
                            </div>
                            <button type="submit" class=" btn btn-default btn-primary">查询</button>
                        </div>
                    </div>
                </div>
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>订单号</th>
                        <th>订单类型</th>
                        <th>所属用户</th>
                        <th>总价</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${order eq null}">
                         <tr align="center">
                             <td colspan="10"><span>无记录</span></td>
                         </tr>
                     </c:if>                     
                     <c:if test="${order ne null}">
                         <tr>
                             <td>${order.id}</td>
                             <td>${order.orderTypeName}</td>
                             <td>
                                 <c:if test="${order.userId ne null}">
                                     ${order.userNickname}(${order.userId})
                                 </c:if>
                             </td>
                             <td>${order.totalPrice}</td>
                             <td>${order.statusName}</td>
                             <td>
                                 <c:if test="${order.status eq 0 || order.status eq 1}">
                                     <a class=" btn btn-danger" onclick="cancelOrder('${order.id}')">取消订单</a>
                                 </c:if>
                             </td>
                         </tr>
                     </c:if>
                    
                    </tbody>
                </table>
                
                订单详情
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>商品编号</th>
                        <th>商品类型</th>
                        <th>价格</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${item_instance_list.size() eq 0}">
                        <tr align="center">
                            <td colspan="10"><span>无记录</span></td>
                        </tr>
                     </c:if>                     
                     <c:if test="${item_instance_list.size() gt 0 }">
                         <c:forEach items="${item_instance_list }" var="item_instance" varStatus="vs">
                             <tr>
                                 <td>${item_instance.id}</td>
                                 <td>${item_instance.sku.name}</td>
                                 <td>${item_instance.salePrice}</td>
                             </tr>
                         </c:forEach>
                     </c:if>
                    
                    </tbody>
                </table>
              
                用户支付详情
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>支付方式</th>
                        <th>币种</th>
                        <th>金额</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${payment_list eq null || payment_list.size() eq 0}">
                        <tr align="center">
                            <td colspan="10"><span>无记录</span></td>
                        </tr>
                     </c:if>                     
                     <c:if test="${payment_list.size() gt 0 }">
                         <c:forEach items="${payment_list }" var="payment" varStatus="vs">
                             <tr>
                                 <td>${payment.paymentTypeName}</td>
                                 <td>${payment.moneyTypeName}</td>
                                 <td>${payment.money}</td>
                             </tr>
                         </c:forEach>
                     </c:if>
                    
                    </tbody>
                </table>
                
         </form>
            </div>
        </div>

    <div class="modal fade" id="id_cancel_order_modal" >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">取消订单确认</h4>
            <input type="hidden" id="id_order_id" value="">
          </div>
          <div class="modal-body">
                 确定要取消选中的订单吗？
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="id_btn_cancal_order">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>scripts/item/order.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
