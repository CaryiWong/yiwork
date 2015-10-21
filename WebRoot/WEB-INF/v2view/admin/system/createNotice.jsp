<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>
<%@ page language="java" import="cn.yi.gather.v20.entity.*" %>
<%@ page language="java" import="com.common.R" %>
<%@page import="cn.yi.gather.v20.permission.entity.*"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String webBasePath=basePath+"v20/";
//User loginU=(User)session.getAttribute(R.User.SESSION_USER);
AdminUser loginU=(AdminUser)session.getAttribute(R.User.SESSION_USER);

String userid = loginU.getId();
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>新增公告</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
  <div class="row row-flow">
        <div class="form-group">
            <button  class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" name="mainform" id="mainform" action="<%=webBasePath %>admin/user/savenotice" method="post">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">标题</label>

                        <div class="col-xs-5">
                            <input id="title" name="title" type="text" class="form-control">
                        </div>
                    </div>
                 
                    <div class="form-group">
                        <label class="col-xs-2 control-label">简介</label>

                        <div class="col-xs-5">
                            <textarea id="content" name="content" rows="4" class="form-control"></textarea>
                        </div>
                      
                    </div>

                    <button class="col-xs-3 col-xs-offset-1 btn btn-default btn-back">取消</button>
                    <button class="col-xs-3 col-xs-offset-1 btn btn-primary">保存</button>
                    <c:if test="${tips ne null }">
						<tr align="center">
							<td colspan="6">${tips }</td>
						</tr>
					</c:if>
                </form>
    </div>
 
   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/user/userList.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
