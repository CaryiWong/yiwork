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
    <title>广播列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
<div class="row row-flow need-list">
            <div class="form-group col-xs-3">
                <a href="<%=basePath%>admin/user/createbroadcast" class="btn btn-primary col-xs-12">新增广播 <i class="glyphicon glyphicon-plus"></i></a>
            </div>
            <div class="col-xs-12">
            <form id="mainForm" name="mainForm" action="<%=basePath %>admin/user/broadcastlist" method="post">
                <div id="search-condition" class="panel panel-primary search">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        	查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse" id="collapseSearch">
                        <div class="search-otherGroup form-inline col-xs-12">
                            <div class="form-group col-xs-5">
                                    <input type="text" name="keyword" value="${keyword }" class="form-control input-sm" placeholder="关键字">
                                    
                            </div>
                            <button type="submit" class=" btn btn-default btn-primary">查询</button>
                        </div>
                    </div>
                </div>	
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                    	<th>序号</th>
                        <th>标题</th>
                        <th>广播时间</th>
                    </tr>
                    </thead>
                    <tbody>
                		 <c:if test="${page.totalPage le 0 }">
								<tr align="center">
									<td colspan="10"><span>无记录</span></td>
								</tr>
						</c:if>
						
						<c:if test="${page.totalPage gt 0 }">
									<c:forEach items="${page.result }" var="sysnetwork" varStatus="vs">
										<tr>
											<td>${(page.currentPage-1)*page.pageSize+vs.index+1}</td>
											<td>${sysnetwork.title }</td>
											<td>
												<fmt:formatDate value="${sysnetwork.createdate1 }" pattern="yyyy-MM-dd HH:mm:ss"/>
											</td>
										</tr>
									</c:forEach>
								</c:if>
						
                    </tbody>
                </table>
     
                <div class="text-center">
                	 <ul class="pagination">
                     <%@ include file="/res/common/pages.jsp"%>
                    </ul>
                </div>
        </div>
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
