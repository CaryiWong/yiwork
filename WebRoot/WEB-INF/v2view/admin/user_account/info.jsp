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
    <title>用户资产信息</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=basePath %>v20/admin/user_account/info" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div class="search-otherGroup form-inline col-xs-12">
                            <div class="form-group ">
                                    <input type="text" name="user_id" class="form-control input-sm" value="${user_id}" placeholder="用户编号" />
                            </div>
                            <button type="submit" class=" btn btn-default btn-primary">查询</button>
                        </div>
                    </div>
                </div>
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>社区虚拟币种类</th>
                        <th>余额</th>
                        <th>账户状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${account_info_list eq null || account_info_list.size() eq 0}">
                         <tr align="center">
                             <td colspan="10"><span>无记录</span></td>
                         </tr>
                     </c:if>                     
                     <c:if test="${account_info_list.size() gt 0}">
                         <c:forEach items="${account_info_list }" var="account_info" varStatus="vs">
                         <tr>
                             <td>${account_info.moneyTypeName}</td>
                             <td>${account_info.money}</td>
                             <td>${account_info.statusName}</td>
                             <td>
                                 <c:if test="${account_info.status eq 0}">
                                     <a class=" btn btn-warning" onclick="freezeAccount('${account_info.userId}')">冻结账户</a>
                                 </c:if>
                                 <c:if test="${account_info.status eq 1}">
                                     <a class=" btn btn-success" onclick="unfreezeAccount('${account_info.userId}')">解冻账户</a>
                                 </c:if>
                                 <a class=" btn btn-danger" onclick="editBalance('${account_info.userId}', ${account_info.moneyType})">修改余额</a>
                             </td>
                         </tr>
                         </c:forEach>
                     </c:if>
                    
                    </tbody>
                </table>
                
                当前拥有的物品
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>物品实例编号</th>
                        <th>种类名称</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${item_list eq null || item_list.size() eq 0}">
                        <tr align="center">
                            <td colspan="10"><span>无记录</span></td>
                        </tr>
                     </c:if>                     
                     <c:if test="${item_list.size() gt 0 }">
                         <c:forEach items="${item_list }" var="item_instance" varStatus="vs">
                             <tr>
                                 <td>${item_instance.id}</td>
                                 <td>${item_instance.sku.name}</td>
                                 <td>
                                     <a class=" btn btn-danger" onclick="destroyItem('${item_instance.id}')">销毁</a>
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

    <div class="modal fade" id="id_freeze_user_account_modal" >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">冻结确认</h4>
            <input type="hidden" id="id_user_id" value="">
          </div>
          <div class="modal-body">
                 确定要冻结选中的用户账户吗？
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="id_freeze_btn">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <div class="modal fade" id="id_edit_balance_modal" >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">修改账户余额</h4>
            <input type="hidden" id="edit_user_id" value="">
            <input type="hidden" id="edit_money_type" value="">
          </div>
          <div class="modal-body">
            <div class="form-horizontal" role="form">
              <fieldset>
                <div class="form-group">
                  <label class="col-sm-3 control-label" for="textinput">动作</label>
                  <div class="col-sm-7">
                    <select class="form-control" id="edit_action">
                        <option value=1>增加</option>
                        <option value=2>减少</option>
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label" for="textinput">数额</label>
                  <div class="col-sm-7">
                    <input type="text" class="form-control" id="edit_money" value="">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label" for="textinput">事由</label>
                  <div class="col-sm-7">
                    <input type="text" class="form-control" id="edit_reason" value="">
                  </div>
                </div>
              </fieldset>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="id_edit_btn">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <div class="modal fade" id="id_delete_item_modal" >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">销毁确认</h4>
            <input type="hidden" id="delete_item_instance_id" value="">
          </div>
          <div class="modal-body">
                 确定要销毁选中的用户物品吗？
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="id_destroy_item_btn">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>scripts/account/user_account.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
