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
    <title>新增服务</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
      <div class="row row-flow">
        <div class="form-group">
            <button  class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" role="form" action="<%=basePath%>v20/admin/yqservice/saveyqservice"
				method="post" >
                    <div class="form-group">
                        <label class="col-xs-2 control-label">服务名：</label>
                        <div class="col-xs-5">
                            <input type="text" id="name" name="name" value="${serviceInfo.name }" class="form-control">
                        </div>
                    </div>
                   <div class="form-group">
                        <label class="col-xs-2 control-label">简介/标语/口号：</label>
                        <div class="col-xs-5">
                            <input type="text" id="introduction" name="introduction" value="${serviceInfo.introduction }" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">邮箱地址：</label>
                        <div class="col-xs-5">
                            <input type="text" id="email" name="email" value="${serviceInfo.email }" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">城市：</label>
                        <div class="col-xs-5">
                            <input type="text" id="city" name="city" value="${serviceInfo.city }" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">封面图：</label>
                        <div class="col-xs-5">
                        <input type="file" name="img" id="titleimg" onchange="sendFile()">
                         <input type="hidden" name="titleimg" id="titleimgv">
                         <img id="showimg" name="showimg" src="" width="400" height="300">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">详情URL：</label>
                        <div class="col-xs-5">
                            <input type="text" id="contexturl" name="contexturl" value="${serviceInfo.contexturl }" class="form-control">
                        </div>
                    </div>
                    
                    
                    <c:if test="${tips ne null }">
				<tr align="center">
					<td colspan="6"><font color="red">${tips}</font></td>
				</tr>
			</c:if>
	               <button type="submit" class="col-xs-2 col-xs-offset-1 btn btn-primary">保存</button>
                     
                </form>
    </div>
   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/ajaxfileupload.js"></script>
    <script type="text/javascript">
    function sendFile()
    {
      	$.ajaxFileUpload( 
         { 
        	url:root+"v20/upload/uploadimg",//用于文件上传的服务器端请求的Action地址 
         	type:"post",//请求方法 
         	secureuri:false,//一般设置为false 
         	fileElementId:'titleimg',//文件id属性 
         	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
         	success:function(json)  { 
      	 	 	   json=JSON.parse(json);  //手动转json
      	 	 	   if(json.cord==0){
      	 	 		   $("#titleimgv").val(json.data);
      	 	 		   $("#showimg").attr("src",root+"v20/download/img?path="+json.data);
      	 	 	   }
    		 } 
         }); 
    }
    
    </script>
</body>
</html>
