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
    <title>新增文章活动</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
      <div class="row row-flow">
        <div class="form-group">
            <button  class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" role="form" action="<%=basePath%>v20/admin/article/createarticle"
				method="post" >
                    <div class="form-group">
                        <label class="col-xs-2 control-label">标题</label>

                        <div class="col-xs-5">
                            <input type="text" id="title" name="title" value="${demand.title }" class="form-control">
                        </div>
                    </div>
                   
                    <!--  -->
                 	 <div id="videoImg" class="form-group">
                        <label class="col-xs-2 control-label">文章封面</label>
						<!-- 制作类型 -->
                        <div class="col-xs-5">
                           	<input type="file" name="img" id="coverImg" onchange="sendFile()">
                           <img alt="原二维码图片" id="coversrc"
						src="<%=basePath %>v20/download/img?type=web&path="
						style="width: 180px;height: 180px;">
                           	<input type="hidden" name="coverimg" id="coverimg" value="0">
                        </div>
                    </div>
                    
                    <div id="videoUrl" class="form-group">
                        <label class="col-xs-2 control-label">文章链接地址</label>
                        <div class="col-xs-5">
                           	<input type="text" name="linkurl" class="form-control">
                        </div>
                    </div>
                    
                    
			<c:if test="${tips ne null }">
				<tr align="center">
					<td colspan="6"><font color="red">${tips}</font></td>
				</tr>
			</c:if>
			<button class="col-xs-2 col-xs-offset-1 btn btn-default btn-back">返回</button>
	               <button type="submit" class="col-xs-2 col-xs-offset-1 btn btn-primary">保存</button>
						 
                     
                </form>
    </div>
   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/article/addArticle.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/validation.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
</body>
</html>
