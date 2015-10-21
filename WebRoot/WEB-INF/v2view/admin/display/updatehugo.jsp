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
    <title>hugo列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/jquery.gridster.min.css">
    
</head>


<body>
    <div class="row row-flow member-add">
        <div class="form-group">
            <button  class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" name="mainform" id="mainform" action="<%=webBasePath %>admin/display/updatehugo"
				method="post" onsubmit="return checkform(this);">
				<input type="hidden" name="id" value="${indexuser.id }" />
                    <div class="form-group">
                        <label class="col-xs-2 control-label">姓名</label>

                        <div class="col-xs-5">
                            <input type="text" id="iusernickname" name="iusernickname" class="form-control" value="${indexuser.iusernickname }">
                        </div>
                    </div>
                  <%--   <div class="form-group">
                        <label class="col-xs-2 control-label">领域</label>
                        <div class="col-xs-5 form-inline">
                           <label>标签</label>
							<c:forEach items="${labels }" var="label" varStatus="vs">
								<c:if test="${vs.index mod 10 eq 0 }"><br></c:if>
								<c:set var="la" value="${label.id}"></c:set>
								<input id="groups" name="groups" type="checkbox"  value="${label.id}" <c:if test="${fn:contains(labelList,la) }">checked="checked"</c:if> />${label.zname }
							</c:forEach>
                        </div>	
                    </div> --%>
               
                    <div class="form-group">
                        <label class="col-xs-2 control-label">职业</label>

                        <div class="col-xs-5">
                            <input type="text" id="iuserjobinfo" name="iuserjobinfo"  class="form-control" value="${indexuser.iuserjobinfo }">
                        </div>
                    </div>
                     <div class="form-group">
                        <label class="col-xs-2 control-label">原头像</label>
                        
                        <div class="col-xs-5">
                        	 <img alt="hugo头像" id="hugoicon" src="<%=webBasePath %>download/img?type=web&path=${indexuser.iuserimg }" style="width: 180px;height: 180px;">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">头像</label>
                        
                        <div class="col-xs-5">
                        	<input type="file" id="imgfile" name="img" onchange="uploadimg();" style="width: 150px;"/>
							<input type="hidden" name="iuserimg" id="iuserimg" value="${indexuser.iuserimg }">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">原封面</label>
                         
                        <div class="col-xs-5">
                         	<img alt="hugo封面" id="hugocover" src="<%=webBasePath %>download/img?type=web&path=${indexuser.iusermaximg }" style="width: 180px;height: 180px;">
                        </div>
                    </div>
                    
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">修改封面</label>
                         
                        <div class="col-xs-5">
                         	<input type="file" id="maximgfile" name="img" onchange="uploadmaximg();" style="width: 150px;"/>
							<input type="hidden" name="iusermaximg" id="iusermaximg" value="${indexuser.iusermaximg }">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">简介</label>

                        <div class="col-xs-5">
                            <textarea rows="4" class="form-control" id="iusersummery" name="iusersummery">${indexuser.iusersummery }</textarea>
                        </div>
                    </div>
					<c:if test="${tips ne null }">
						<tr align="center">
							<td colspan="4">${tips }</td>
						</tr>
					</c:if>
                    <button class="col-xs-3 col-xs-offset-1 btn btn-default btn-back">取消</button>
                    <button class="col-xs-3 col-xs-offset-1 btn btn-primary">保存</button>
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
