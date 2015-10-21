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
    <title>入住列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/jquery.gridster.min.css">
    
</head>

<body>
   <div class="row row-flow version-list">
            <div class="form-group col-xs-3">
                <a href="<%=basePath %>version/newversion" class="btn btn-primary col-xs-12">新增版本 <i class="glyphicon glyphicon-plus"></i></a>
            </div>
            <div class="col-xs-12">
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>平台</th>
                        <th>版本号</th>
                        <th>版本值</th>
                        <th>创建时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.result }" var="ver" varStatus="ve">
                    <tr>
                        <td>${ver.platform}</td>
                        <td><a href="#">${ver.version}</a></td>
                        <td>${ver.ver}</td>
                        <td><fmt:formatDate value="${ver.createdate}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                   </c:forEach>
                   
                    </tbody>
                </table>
              
              
            </div>
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
