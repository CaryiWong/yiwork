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
   <div class="row row-flow space">
        <div class="panel panel-primary">
            <div class="panel-heading">一楼</div>
            <ul id="first" class="list-group">
                
            </ul>
        </div>
    </div>
    
    <div class="row row-flow space">
        <div class="panel panel-primary">
            <div class="panel-heading">二楼</div>
            <ul id="second" class="list-group">
                
            </ul>
        </div>
    </div>
   
   <div class="row row-flow space">
        <div class="panel panel-primary">
            <div class="panel-heading">三楼</div>
            <ul id="third" class="list-group">
                
            </ul>
        </div>
    </div>
    
    <div class="row row-flow space">
        <div class="panel panel-primary">
            <div class="panel-heading">四楼</div>
            <ul id="fouth" class="list-group">
                <li class="list-group-item space-item">
                    <div class="form-group col-xs-12">
                        move:流动区
                        <input type="file" name="commentImg" class="activity-comment-file" multiple/>
                    </div>
                    <div class="clearfix">
                    
                    </div>
                </li>
                
            </ul>
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
