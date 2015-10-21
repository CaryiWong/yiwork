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
    <title>商品库存</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=basePath %>v20/admin/item/inventory" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div class="search-otherGroup form-inline col-xs-12">
                            <div class="form-group ">
                                <input type="text" name="sku_id" class="form-control input-sm" value="${sku_id}" placeholder="商品种类编号" />
                            </div>
                            <button type="submit" class=" btn btn-default btn-primary">查询</button>
                        </div>
                    </div>
                </div>
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                    	<th>编号</th>
                    	<th>商品名称</th>
                    	<th>商品大类</th>
                    	<th>状态</th>
                        <th>库存量</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${inventory eq null }">
						<tr align="center">
							<td colspan="10"><span>无记录</span></td>
						</tr>
					 </c:if>
					 <c:if test="${inventory ne null }">
                        <tr>
                            <td>${sku_id}</td>
                            <td>${sku_name}</td>
                            <td>${item_class_id}</td>
                            <td>${sku_status}</td>
                            <c:if test="${inventory eq 'unlimited' }">
                                <td>无限</td>
                            </c:if>
                            <c:if test="${inventory ne 'unlimited' }">
                                <td>${inventory}</td>
                            </c:if>
                            <td>
                                <c:if test="${inventory ne 'unlimited' }">
                                    <a class=" btn btn-success" onclick="increase('${sku_id}')">增加库存</a>
                                    <c:if test="${inventory gt 0}">
                                        <a class=" btn btn-warning" onclick="reduce('${sku_id}')">减少库存</a>
                                    </c:if>
                                </c:if>
                            </td>
						</tr>
					  </c:if>
                    
                    </tbody>
                </table>
                
                出入库记录
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>时间</th>
                        <th>出／入</th>
                        <th>数量</th>
                        <th>操作员</th>
                        <th>备注</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${page.currentCount eq 0}">
                        <tr align="center">
                            <td colspan="10"><span>无记录</span></td>
                        </tr>
                     </c:if>                     
                     <c:if test="${page.totalPage gt 0 }">
                         <c:forEach items="${page.result }" var="yigather_log" varStatus="vs">
                             <tr>
                                 <td>${yigather_log.dateTime}</td>
                                 <td>${yigather_log.opTypeName}</td>
                                 <td>${yigather_log.amount}</td>
                                 <td>
                                     <c:if test="${yigather_log.operatorId ne null}">
                                         ${yigather_log.operatorName}(${yigather_log.operatorId})
                                     </c:if>
                                 </td>
                                 <td>${yigather_log.memo}</td>
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

    <div class="modal fade" id="id_increase_modal" >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">增加库存</h4>
            <input type="hidden" id="id_increase_sku_id" value=""/>
          </div>
          <div class="modal-body">
            <div class="form-horizontal" role="form">
              <fieldset>
                <div class="form-group">
                  <label class="col-sm-3 control-label" for="textinput">数量</label>
                  <div class="col-sm-7">
                    <input type="text" class="form-control" id="id_increase_amount" value="">
                  </div>
                </div>
              </fieldset>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="id_increase_btn">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <div class="modal fade" id="id_reduce_modal" >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">减少库存</h4>
            <input type="hidden" id="id_reduce_sku_id" value=""/>
          </div>
          <div class="modal-body">
            <div class="form-horizontal" role="form">
              <fieldset>
                <div class="form-group">
                  <label class="col-sm-3 control-label" for="textinput">数量</label>
                  <div class="col-sm-7">
                    <input type="text" class="form-control" id="id_reduce_amount" value="">
                  </div>
                </div>
              </fieldset>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="id_reduce_btn">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>scripts/item/sku_inventory.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
