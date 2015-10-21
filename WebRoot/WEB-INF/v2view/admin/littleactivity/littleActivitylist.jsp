<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Date date = new Date();
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>小活动列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
<input type="hidden" id="kcm" value="<%=basePath%>">
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="search-condition" class="panel panel-primary search">
             	<form id="mainForm" name="mainForm" action="<%=basePath %>v20/admin/littleactivity/activitylist" method="post">
                   <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        	查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                    	 <div id="checkBoxLables" class="search-checkboxGroup  form-inline">
                   		 
                   		 </div>
                   		 
                        <div class="search-otherGroup form-inline col-xs-12">
                            <div class="form-group" name="status">
                                <select class="form-control input-sm" name="status">
                                    <option value="-1" <c:if test="${status==null or status eq -1 }">selected="selected"</c:if>>全部</option>
									<option value="1" <c:if test="${status eq 1 }">selected="selected"</c:if>>待开启</option>
									<%-- <option value="2" <c:if test="${status eq 2 }">selected="selected"</c:if>>进行中</option>
									<option value="3" <c:if test="${status eq 3 }">selected="selected"</c:if>>已结束</option> --%>
                                </select>
                            </div>
                            <div class="form-group">
                                    <input type="text" name="keyword" value="${keyword}" maxlength="30" class="form-control input-sm" placeholder="关键字">
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
                        <th>发起者</th>
                        <th>地点</th>
                        <th>创建时间</th>
                        <th>开启时间</th>
                        <th>简介</th>
                      <!--   <th>操作</th> -->
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${page.totalPage le 0 }">
						<tr align="center">
							<td colspan="10"><span>无记录</span></td>
						</tr>
					</c:if>
					<c:if test="${page.totalPage gt 0 }">
							<c:forEach items="${page.result }" var="activity" varStatus="vs">
								<tr>
											<td >${(page.currentPage-1)*page.pageSize+vs.index+1}</td>
											<td ><a href="<%=basePath %>v20/admin/littleactivity/getlittleupdateactivity?activityid=${activity.id}">${activity.title}</a></td>
											<td >${activity.user.nickname}</td>
											<td >${activity.address}</td>
											<td >${activity.createdate}</td>
											<td >
												<c:set var="nowDate" value="<%=date%>"></c:set>
												<c:choose>
													<c:when test="${nowDate<activity.opendate}"><font title="${activity.opendate}">待开启</font></c:when>
													<c:when test="${nowDate>activity.opendate && nowDate<action.enddate}"><font title="${activity.opendate}">进行中</font></c:when>
													<c:when test="${nowDate>activity.opendate}"><font title="${activity.opendate}">已结束</font></c:when>
												</c:choose>
											</td>
											<td title="${activity.summary}">${fn:substring(activity.summary,0,10)}</td>
											<!-- <td><a href="javacsript:void(0)">删除</a></td> -->
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
