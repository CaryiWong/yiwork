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
    <title>hugo列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/jquery.gridster.min.css">
    
</head>


<body>
    <!--    入驻列表    -->
        <div class="row row-flow enter-list">
            <div class="enter-list-operate">
                <button id="saveBut" class="btn btn-primary btn-sm ">保存排序</button>
                <a href="<%=basePath%>admin/display/createhugo" class="btn btn-default btn-sm">新增hugo</a>
            </div>
            <div class="col-xs-12 enter-list-group">
              <!-- 
                <div id="1" class="col-xs-3 enter-list-item">
                    <div class="enter-item-index">1</div>
                    <img src="../../images/enter.jpg" class="col-xs-4 col-xs-offset-2">
                    <div class="col-xs-6">
                        <h5>hugoA</h5>
                        <button class="btn btn-sm btn-block btn-default">修改</button>
                        <button class="btn btn-sm btn-block btn-danger">删除</button>
                    </div>
                </div>
                <div id="2" class="col-xs-3 enter-list-item">
                    <div class="enter-item-index">2</div>
                    <img src="../../images/enter.jpg" class="col-xs-4 col-xs-offset-2">

                    <div class="col-xs-6">
                        <h5>hugoB</h5>
                        <button class="btn btn-sm btn-block btn-default">修改</button>
                        <button class="btn btn-sm btn-block btn-danger">删除</button>
                    </div>
                </div>
                <div id="3" class="col-xs-3 enter-list-item">
                    <div class="enter-item-index">3</div>
                    <img src="../../images/enter.jpg" class="col-xs-4 col-xs-offset-2">

                    <div class="col-xs-6">
                        <h5>hugoC</h5>
                        <button class="btn btn-sm btn-block btn-default">修改</button>
                        <button class="btn btn-sm btn-block btn-danger">删除</button>
                    </div>
                </div>
                <div id="4" class="col-xs-3 enter-list-item">
                    <div class="enter-item-index">4</div>
                    <img src="../../images/enter.jpg" class="col-xs-4 col-xs-offset-2">

                    <div class="col-xs-6">
                        <h5>hugoD</h5>
                        <button class="btn btn-sm btn-block btn-default">修改</button>
                        <button class="btn btn-sm btn-block btn-danger">删除</button>
                    </div>
                </div>
                <div id="5" class="col-xs-3 enter-list-item">
                    <div class="enter-item-index">5</div>
                    <img src="../../images/enter.jpg" class="col-xs-4 col-xs-offset-2">

                    <div class="col-xs-6">
                        <h5>hugoE</h5>
                        <button class="btn btn-sm btn-block btn-default">修改</button>
                        <button  class="btn btn-sm btn-block btn-danger">删除</button>
                    </div>
                </div>
            </div>
             -->
        </div>
        </div>
      
   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/hugo/hugo.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/scripts/sortable/jquery.sortable.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>    
    
</body>
</html>
