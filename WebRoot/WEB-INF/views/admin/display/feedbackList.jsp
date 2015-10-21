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
    <title>反馈列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/jquery.gridster.min.css">
    
</head>

<body>
   <div class="row row-flow version-list">
           <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=basePath %>admin/user/feedbacklist" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        	查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div id="checkBoxLables" class="search-checkboxGroup  form-inline">
         
                            <div class="form-group">
                                    <input  name="keyword" type="text" value="${keyword}" class="form-control input-sm" placeholder="关键字">
                            </div>
                            <button type="submit" class=" btn btn-default btn-primary">查询</button>
                        </div>
                    </div>
                </div>
              
                
            <div class="col-xs-12">
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>反馈内容</th>
                        <th>联系电话</th>
                        <th>联系邮箱</th>
                        <th>创建时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.result }" var="ver" varStatus="ve">
                    <tr>
                     	<td>${(page.currentPage-1)*page.pageSize+ve.index+1}</td>
                        <td title="${ver.content}"><a href="javascript:;">${ver.content}</a></td>
                        <td>${ver.tel}</td>
                        <td>${ver.email}</td>
                        <td><fmt:formatDate value="${ver.createdate}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                   </c:forEach>
                   
                    </tbody>
                </table>
            </div>
              <div class="text-center">
                	 <ul class="pagination">
                     <%@ include file="/res/common/pages.jsp"%>
                    </ul>
                </div>
            </form>
        </div>
        
        
        
   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/space/spacearea.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/scripts/sortable/jquery.sortable.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/customUI.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>    
    
</body>
</html>
