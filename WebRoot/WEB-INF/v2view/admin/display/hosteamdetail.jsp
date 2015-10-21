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
    <title>申请入住列表详情</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/jquery.gridster.min.css">
    
</head>


<body>
    <div class="row row-flow member-add">
        <div class="form-group">
            <button  class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" name="mainform" id="mainform" action="<%=basePath %>admin/display/updatehugo"
				method="post" onsubmit="return checkform(this);">
				<input type="hidden" name="id" value="${indexuser.id }" />
                    <div class="form-group">
                        <label class="col-xs-2 control-label">团队名</label>

                        <div class="col-xs-5">
                            <input type="text" id="iusernickname" name="iusernickname" class="form-control" value="${joinapplication.teamname }">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">所属行业</label>
                        <div class="col-xs-5 form-inline">
							<c:forEach items="${labels }" var="label" varStatus="vs">
								<c:if test="${vs.index mod 10 eq 0 }"><br></c:if>
								<c:set var="la" value="${label.id}"></c:set>
								<input id="groups" name="groups" type="checkbox"  value="${label.id}" <c:if test="${fn:contains(labelList,la) }">checked="checked"</c:if> />${label.zname }
							</c:forEach>
                        </div>
                    </div>
               
                    <div class="form-group">
                        <label class="col-xs-2 control-label">团队简介</label>
                        <div class="col-xs-5">
                            <textarea rows="4" class="form-control" id="iusersummery" name="iusersummery">${joinapplication.teamintroduce }</textarea>
                        </div>
                    </div>
                    
                     <div class="form-group">
                        <label class="col-xs-2 control-label">团队人数</label>
                        <div class="col-xs-5">
                        	 <input type="text" id="iusernickname" name="iusernickname" class="form-control" value="${joinapplication.teampeople }">
                        </div>
                    </div>
					
					<div class="form-group">
                        <label class="col-xs-2 control-label">预计入住时间</label>
                        <div class="col-xs-5">
                        	<input type="text" readonly="readonly" class="form-control" id="opendate" name="open" value="<fmt:formatDate value="${joinapplication.settleddate1 }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="开启时间">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">申请时间</label>
                        <div class="col-xs-5">
                        	<input type="text" readonly="readonly" class="form-control" id="opendate" name="open" value="<fmt:formatDate value="${joinapplication.createdate1 }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="开启时间">
                        </div>
                    </div>
					
                    <button class="col-xs-3 col-xs-offset-1 btn btn-default btn-back">取消</button>
                 	
                </form>
    </div>
   
   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
    <script type="text/javascript" src="<%=basePath %>res/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/hugo/createhugo.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/scripts/sortable/jquery.sortable.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>    
    
  </body>
</html>
