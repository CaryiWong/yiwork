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
    <title>商品种类列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="itemClass-management" class="panel panel-primary search">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseManagement">
                        新增商品种类
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseManagement">
                        <div class="row">
                            <div class="col-xs-2">
                                <select id="id_item_class_id" class="form-control input-sm">
                                    <c:forEach items="${item_class_list}" var="item_class" varStatus="vs">
                                        <option value="${item_class.id}">${item_class.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-xs-2">
                                <input class="form-control" type="text" id="new_sku_name" placeholder="名称">
                            </div>
                            <div class="col-xs-2">
                                <input class="form-control" type="text" id="new_default_price" placeholder="价格">
                            </div>
                            <div class="col-xs-2">
                                <input class="form-control" type="text" id="new_member_price" placeholder="会员价">
                            </div>
                            <div class="col-xs-2">
                                <select class="form-control" type="text" id="new_is_unlimited">
                                    <option value=0>库存有限</option>
                                    <option value=1>库存无限</option>
                                </select>
                            </div> 
                            <div class="col-xs-2">
                                <button type="submit" class=" btn btn-default btn-primary" id="id_add_sku_btn">确定</button>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=basePath %>v20/admin/item/skulist" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div class="search-otherGroup form-inline col-xs-12">
                            <div class="form-group ">
                                    <select name="item_class_id" class="form-control input-sm">
                                      <option value=>全部</option>
                                      <c:forEach items="${item_class_list}" var="item_class" varStatus="vs">
                                        <c:if test="${item_class.id eq selected_item_class_id}">
                                          <option value="${item_class.id}" selected>${item_class.name}</option>
                                        </c:if>
                                        <c:if test="${item_class.id ne selected_item_class_id}">
                                          <option value="${item_class.id}">${item_class.name}</option>
                                        </c:if>
                                      </c:forEach>
                                    </select>
                            </div>
                            <button type="submit" class=" btn btn-default btn-primary">查询</button>
                        </div>
                    </div>
                </div>
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                    	<th>编号</th>
                        <th>名称</th>
                        <th>商品大类</th>
                        <th>价格</th>
                        <th>会员价</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${page.totalPage le 0 }">
						<tr align="center">
							<td colspan="10"><span>无记录</span></td>
						</tr>
					</c:if>
					<c:if test="${page.totalPage gt 0 }">
                        <c:forEach items="${page.result }" var="sku" varStatus="vs">
                            <tr>
                                <td>${sku.id}</td>
                                <td>${sku.name}</td>
							    <td>${sku.itemClass.name}</td>
							    <td>${sku.defaultPrice}</td>
								<td>${sku.memberPrice}</td>
								<td>${sku.statusName}</td>
								<td>
								  <c:if test="${sku.status ne 2}">
								    <a class=" btn btn-primary" onclick="gotoInventory('${sku.id}')">管理库存</a>
                                    <a class=" btn btn-info" onclick="modifySku('${sku.id}', ${sku.itemClass.id}, '${sku.name}', ${sku.defaultPrice}, ${sku.memberPrice})">编辑</a>
                                    <c:if test="${sku.status eq 0}">
                                        <a class=" btn btn-warning" onclick="getOffShelves('${sku.id}')">下架</a>
                                    </c:if>
                                    <c:if test="${sku.status eq 1}">
                                        <a class=" btn btn-success" onclick="putOnShelves('${sku.id}')">上架</a>
                                    </c:if>
                                    <a class=" btn btn-danger" onclick="deleteSku('${sku.id}')">删除</a>
                                  </c:if>
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

    <div class="modal fade" id="id_modify_sku_modal" >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">编辑</h4>
          </div>
          <div class="modal-body">
            <div class="form-horizontal" role="form">
              <fieldset>
                <div class="form-group">
                  <label class="col-sm-3 control-label" for="textinput">编号</label>
                  <div class="col-sm-7">
                    <input type="text" class="form-control" id="id_modify_sku_id" value="" readonly="true">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label" for="textinput">名称</label>
                  <div class="col-sm-7">
                    <input type="text" class="form-control" id="id_modify_sku_name" value="">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label" for="textinput">大类</label>
                  <div class="col-sm-7">
                      <select id="id_modify_sku_item_class_id" class="form-control input-sm">
                          <c:forEach items="${item_class_list}" var="item_class" varStatus="vs">
                              <option value="${item_class.id}">${item_class.name}</option>
                          </c:forEach>
                      </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label" for="textinput">价格</label>
                  <div class="col-sm-7">
                      <input type="text" class="form-control" id="id_modify_sku_price" value="">
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-sm-3 control-label" for="textinput">会员价</label>
                  <div class="col-sm-7">
                      <input type="text" class="form-control" id="id_modify_sku_member_price" value="">
                  </div>
                </div>
              </fieldset>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="id_btn_modify">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->


    <div class="modal fade" id="id_delete_sku_modal" >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">删除确认</h4>
            <input type="hidden" id="id_delete_sku_id" value="">
          </div>
          <div class="modal-body">
                 确定要删除选中的商品种类吗？
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="id_btn_delete">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>scripts/item/sku.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
