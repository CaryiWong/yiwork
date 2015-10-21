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
    <title>商品大类列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="itemClass-management" class="panel panel-primary search">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseManagement">
                        新增商品大类
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseManagement">
                      <form id="mainForm" name="mainForm" action="<%=basePath %>v20/admin/item/add_item_class" method="post">
                        <div class="row">
                            <div class="col-xs-4">
                                    <input class="form-control" type="text" name="name" placeholder="名称">
                                
                            </div>
                            
                            <div class="col-xs-4">
                                <button type="submit" class=" btn btn-default btn-primary">确定</button>
                            </div>
                        </div>
                      </form>
                    </div>
                </div>
                   
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                    	<th>编号</th>
                        <th>名称</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${item_class_list eq null || item_class_list.size() eq 0 }">
						<tr align="center">
							<td colspan="10"><span>无记录</span></td>
						</tr>
					</c:if>
					<c:if test="${item_class_list.size() gt 0 }">
                        <c:forEach items="${item_class_list }" var="item_class" varStatus="vs">
                            <tr>
                                <td>${item_class.id}</td>
                                <td>${item_class.name}</td>
							    <td>${item_class.statusName}</td>
							    <td>
							        <c:if test="${item_class.status eq 0}">
							            <button id = "id_modify_item_class_btn" class=" btn btn-info" onclick="modifyItemClass(${item_class.id})">编辑</button>
							            <button id = "id_modify_item_class_btn" class=" btn btn-danger" onclick="deleteItemClass(${item_class.id})">删除</button>
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
            </div>
        </div>

    <div class="modal fade" id="id_modify_item_class_modal" >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">修改</h4>
            <input type="hidden" id="id_modify_item_class_id" value="">
          </div>
          <div class="modal-body">
            <div class="form-horizontal" role="form">
              <fieldset>
                <div class="form-group">
                  <label class="col-sm-3 control-label" for="textinput">名称</label>
                  <div class="col-sm-7">
                    <input type="text" class="form-control" id="id_modify_item_class_name" value="">
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


    <div class="modal fade" id="id_delete_item_class_modal" >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">删除确认</h4>
            <input type="hidden" id="id_delete_item_class_id" value="">
          </div>
          <div class="modal-body">
                 确定要删除选中的商品大类吗？
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
    <script type="text/javascript" src="<%=basePath%>scripts/item/item_class.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
