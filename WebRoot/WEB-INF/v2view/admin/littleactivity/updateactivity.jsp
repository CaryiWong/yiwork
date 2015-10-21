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
    <title>更新小活动</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
<div class="row row-flow">
        <div class="form-group">
            <button  class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" name="mainform" id="mainform" action="<%=basePath%>v20/admin/littleactivity/updatelittleactivity"
				method="post" onsubmit="return validateformActivity(this);">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">标题</label>

                        <div class="col-xs-5">
                        	<input type="hidden" value="${activity.id}" name="activityid" id="activityid">
                        	<input type="hidden" value="${activity.id}" name="id" id="id">
                            <input type="text" class="form-control" id="title" name="title" value="${activity.title}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">发起者</label>

                        <div class="col-xs-5">
                            <p class="form-control-static"><a class="form-control-static" href="javascript:;">${activity.user.nickname}</a></p>
                        </div>
                    </div>
                    
                    <hr/>
                
                    <div class="form-group">
                        <label class="col-xs-2 control-label">报名人数</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="signnum" name="signnum" value="0" readonly="readonly">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">地点</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="address" name="address" value="${activity.address}">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">简介</label>

                        <div class="col-xs-5">
                            <textarea rows="4" class="form-control" id="summary" name="summary" >${activity.summary}</textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">开始时间</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="opendate" name="open" value="<fmt:formatDate value="${activity.opendate }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="开启时间">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-5 col-xs-offset-2">
                            <a href="<%=basePath%>admin/activity/getactsignlist?pageSize=12&activityid=${activity.id}" class="btn btn-primary col-xs-12" >报名列表</a>
                        </div>
                    </div>

                   	  <button class="col-xs-1 col-xs-offset-1 btn btn-default btn-back">返回</button>
                   	  <c:if test="${tips ne null}">
		    			<tr align="center">
		    				<td colspan="4"><font color="red"">${tips}</font></td>
		    			</tr>
		    		</c:if>
		    		
	                 <button type="submit" class="col-xs-1 col-xs-offset-1 btn btn-primary">保存</button>
					
                </form>
    </div>


   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/activity/littleActivity.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
