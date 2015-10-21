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
    <title>入住列表-一起开工社区</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/jquery.gridster.min.css">
    
</head>

<body>
   <div class="row row-flow version-list">
            <div class="form-group col-xs-3">
                <a href="versionDetail.html" class="btn btn-primary col-xs-12">新增版本 <i class="glyphicon glyphicon-plus"></i></a>
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
                    <tr>
                        <td>iOS</td>
                        <td><a href="#">1.2</a></td>
                        <td>3</td>
                        <td>2013-09-12 21:12:12</td>
                    </tr>
                    <tr>
                        <td>Android</td>
                        <td><a href="#">1.2</a></td>
                        <td>3</td>
                        <td>2013-09-12 21:12:12</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        
        
   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/version/version.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/ajaxfileupload.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/scripts/sortable/jquery.sortable.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/customUI.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>    
    
</body>
</html>
