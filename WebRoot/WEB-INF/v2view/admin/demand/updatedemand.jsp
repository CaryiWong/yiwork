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
    <title>查看会员信息</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
      <div class="row row-flow">
        <div class="form-group">
            <button  class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" role="form" action="<%=webBasePath%>admin/demand/savedemand"
				method="post" onsubmit="return validateform(this);">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">标题</label>

                        <div class="col-xs-5">
                            <input type="text" id="title" name="title" value="${demand.title }" class="form-control">
                             <input type="hidden" id="id" name="id" value="${demand.id }" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">发布者</label>
                        <div class="col-xs-5">
                            <p class="form-control-static"><a class="form-control-static" href="#"></a>${demand.publishuser.nickname }</p>
                        </div>
                    </div>

                    <div id="plantime" class="form-group">
                        <label class="col-xs-2 control-label">创建时间</label>
                        <div class="col-xs-5">
                        	<input type="text" class="form-control" id="plandatetime" name="plantime" value="<fmt:formatDate value="${demand.publishdate0 }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="计划时间">
                        </div>
                    </div>
                   
                    <div class="form-group">
                        <label class="col-xs-2 control-label">简介</label>

                        <div class="col-xs-5">
                            <textarea rows="4" class="form-control" name="texts">${demand.texts }</textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">发布日期</label>

                        <div class="col-xs-5">
                        	 <input type="text" readonly="readonly" class="form-control" id="publishdate0" name="senddate" value="<fmt:formatDate value="${demand.publishdate0 }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text"  placeholder="发布日期">
                        </div>
                    </div>
                    
                    <!-- 手动修改是否解决 -->
                    <c:if test="${demand.ischeck eq 1}">
                    <div  class="form-group">
                        <label class="col-xs-2 control-label">是否已解决</label>
                        <div class="col-xs-5">
                            <select class="form-control" name="status">
										<option value="0" <c:if test="${demand.status eq 0 }">selected="selected"</c:if>>待解决</option>
										<option value="1" <c:if test="${demand.status eq 1 }">selected="selected"</c:if>>正在解决</option>
										<option value="2" <c:if test="${demand.status eq 2 }">selected="selected"</c:if>>已解决</option>
                             </select>
                        </div>
                    </div>
                    
                    </c:if>
                    
					<c:if test="${tips ne null }">
		    			<tr align="center">
		    				<td colspan="4"><font color="red">${tips }</font></td>
		    			</tr>
		    		</c:if>
                    <button class="col-xs-1 col-xs-offset-1 btn btn-default btn-back">返回</button>
                    <input type="hidden" id="ischeckType" name="ischeckType" value="${demand.ischeck}">
                     <c:if test="${demand.ischeck eq 1 }">
	                    <button type="submit" class="col-xs-1 col-xs-offset-1 btn btn-primary">再次保存</button>
	                    <!-- <button type="button" class="col-xs-1 col-xs-offset-1 btn btn-danger" onclick="verify(2);">驳回</button> -->
                    </c:if>
                    
                    <c:if test="${demand.ischeck eq 0 }">
	                    <button type="submit" class="col-xs-1 col-xs-offset-1 btn btn-primary">保存</button>
						<!-- <button id="checkOut" type="button" class="col-xs-1 col-xs-offset-1 btn btn-success" >审核</button> -->
	                    <!-- <button type="button" class="col-xs-1 col-xs-offset-1 btn btn-danger" onclick="verify(2);">驳回</button> -->
                    </c:if>
                </form>
    </div>
   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/demand/updateDemand.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/validation.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
</body>
</html>
