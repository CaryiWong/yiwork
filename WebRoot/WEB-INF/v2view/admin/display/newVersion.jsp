<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String webBasePath=basePath+"v20/";
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>新增版本</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/jquery.gridster.min.css">
</head>


<body>
     <div class="row row-flow version-detail">
        <div class="form-group">
            <button  class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" >
                    <div class="form-group">
                        <label class="col-xs-2 control-label">平台</label>

                        <div class="col-xs-5">
                            <select class="form-control" id="platform">
                                <option value="ios">iOS</option>
                                <option value="android">Android</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">版本号(例如2.1)</label>

                        <div class="col-xs-5">
                            <input id="version" type="text" class="form-control valid-input"
                                    data-valid-rule="required,version">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">版本值(整数)</label>

                        <div class="col-xs-5">
                            <input id="ver" type="text" class="form-control valid-input"
                                    data-valid-rule="required,number,min"
                                    data-valid-min="0"
                                    data-valid-text='{"min":"不能少于1"}'>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">超链接</label>

                        <div class="col-xs-5">
                            <input id="url" type="text" class="form-control valid-input"
                                    data-valid-rule="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">简介</label>

                        <div class="col-xs-5">
                            <textarea id="description" rows="4" class="form-control valid-input"
                                    data-valid-rule="required"></textarea>
                        </div>
                    </div>

                    <button class="col-xs-3 col-xs-offset-1 btn btn-default btn-back">取消</button>
                    <button onclick="saveVersion()" class="col-xs-3 col-xs-offset-1 btn btn-primary">新增</button>
                </form>
    </div>

   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
	
	<script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/version/version.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/scripts/sortable/jquery.sortable.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>    
    
  </body>
</html>
