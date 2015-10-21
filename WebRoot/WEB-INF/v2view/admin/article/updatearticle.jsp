<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>
<%@ page language="java" import="cn.yi.gather.v20.entity.*"  %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>更新文章</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
<div class="row row-flow">
        <div class="form-group">
            <button  class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" name="mainform" id="mainform" action="<%=basePath%>v20/admin/article/updatearticle"
				method="post" onsubmit="return validateformActivity(this);">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">标题</label>

                        <div class="col-xs-5">
                        	<input type="hidden" value="${article.id}" name="activityid" id="activityid">
                        	<input type="hidden" value="${article.id}" name="id" id="id">
                            <input type="text" class="form-control" id="title" name="title" value="${article.title}">
                        </div>
                    </div>
                    
                     <div id="videoImg" class="form-group">
                        <label class="col-xs-2 control-label">文章封面</label>
						<!-- 制作类型 -->
                        <div class="col-xs-5">
                           	<input type="file" name="img" id="coverImg" onchange="sendFile()">
                           <img alt="原二维码图片" id="coversrc"
						src="<%=basePath %>v20/download/img?type=web&path=${article.coverimg}"
						style="width: 180px;height: 180px;">
                           	<input type="hidden" name="coverimg" id="coverimg" value="${article.coverimg}">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">发起者</label>

                        <div class="col-xs-5">
                            <p class="form-control-static"><a class="form-control-static" href="javascript:;">${article.user.username}</a></p>
                        </div>
                    </div>
                    
                    <hr/>
                
                	 <div class="form-group">
                        <label class="col-xs-2 control-label">链接地址</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="linkurl" name="linkurl" value="${article.linkurl}">
                        </div>
                    </div>
                
                    <div class="form-group">
                        <label class="col-xs-2 control-label">创建时间</label>

                        <div class="col-xs-5">
                            <input type="text" readonly="readonly" class="form-control" id="opendate" name="open" value="<fmt:formatDate value="${article.createdate }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text"  placeholder="开启时间">
                        </div>
                    </div>

                   
                   	  <button class="col-xs-1 col-xs-offset-1 btn btn-default btn-back">返回</button>
                   	  <c:if test="${tips ne null}">
		    			<tr align="center">
		    				<td colspan="4"><font color="red"">${tips}</font></td>
		    			</tr>
		    		</c:if>
		    		
		    		 <c:if test="${article.ischeck ne 1 && article.ischeck eq 0}">
						<!-- <button type="button" class="col-xs-1 col-xs-offset-1 btn btn-success" onclick="verify(1)">审核</button> -->
						<button type="submit" class="col-xs-1 col-xs-offset-1 btn btn-primary">保存</button>
	                   <!--  <button type="button" class="col-xs-1 col-xs-offset-1 btn btn-danger" onclick="verify(2);">驳回</button> -->
                    </c:if>
        
                    <c:if test="${article.ischeck eq 1}">  <!-- isshelves=1 是下架的 -->
                    	     <button type="submit" class="col-xs-1 col-xs-offset-1 btn btn-primary">保存</button> 
							<!-- <button type="button" class="col-xs-1 col-xs-offset-1 btn btn-success" onclick="verify(1)">再次审核</button> -->
					</c:if>
					
                </form>
    </div>


   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>res/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/article/addArticle.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
