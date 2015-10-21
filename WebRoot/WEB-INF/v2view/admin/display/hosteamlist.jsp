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
    <title>申请入住团队列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/jquery.gridster.min.css">
    
</head>

<body>
   <div class="row row-flow version-list">
            <div class="form-group col-xs-3">
                <a href="<%=basePath %>version/joinapplicationlist" class="btn btn-primary col-xs-12">新增申请入住团队列表 <i class="glyphicon glyphicon-plus"></i></a>
            </div>
            <div class="col-xs-12">
             <form id="mainForm" name="mainForm" action="<%=basePath %>joinapplicationlist" method="post">
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>团队名</th>
                        <th>团队简介</th>
                        <th>团队人数</th>
                        <th>创建日期</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.result }" var="ver" varStatus="ve">
                    <tr>
                        <td>${ver.teamname}</td>
                        <td><a href="<%=basePath%>admin/display/joinapplicationdetail?id=${ver.id}">${ver.teamintroduce}</a></td>
                        <td>${ver.teampeople}</td>
                        <td><fmt:formatDate value="${ver.settleddate1}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    </tr>
                   </c:forEach>
                   
                    </tbody>
                </table>
              </form>
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
