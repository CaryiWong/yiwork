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
    <title>活动列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
<div class="row row-flow">
            <div class="form-group col-xs-2">
                <button class="btn col-xs-12 btn-default btn-back">返回</button>
            </div>
            <div class="col-xs-12">
                <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=basePath %>admin/activity/getactsignlist" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div class="search-otherGroup form-inline col-xs-12">
                            <div class="form-group">
                            	<input type="hidden" value="${activityid}" name="activityid" id="activityid">
                                <input type="text" name="userNO" value="${userNO}" class="form-control input-sm" placeholder="会员号" />
                            </div>
                            <div class="form-group " name="charge">
                                    <select class="form-control input-sm">
                                        <option value="-1" <c:if test="${charge==null or charge eq -1 }">selected="selected"</c:if>>全部</option>
										<option value="0" <c:if test="${charge eq 0 }">selected="selected"</c:if>>管理员</option>
										<option value="1" <c:if test="${charge eq 1 }">selected="selected"</c:if>>已缴</option>
										<option value="2" <c:if test="${charge eq 2 }">selected="selected"</c:if>>未缴</option>
                                    </select>
                            </div>
                            <div class="form-group"  name="sex">
                                <select class="form-control input-sm">
                                    <option value="-1" <c:if test="${sex==null or sex eq -1 }">selected="selected"</c:if>>全部</option>
									<option value="0" <c:if test="${sex eq 0 }">selected="selected"</c:if>>男</option>
									<option value="1" <c:if test="${sex eq 1 }">selected="selected"</c:if>>女</option>
									<option value="2" <c:if test="${sex eq 2 }">selected="selected"</c:if>>保密</option>
                                </select>
                            </div>
                            <div class="form-group">
                                    <input type="text" name="keyword" value="${keyword }" class="form-control input-sm" placeholder="关键字">
                            </div>
                            <button type="submit" class="btn btn-default btn-primary">查询</button>
                        </div>
                    </div>
                </div>
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                        <th>姓名</th>
                        <th>电话</th>
                        <th>邮箱</th>
                        <th>性别</th>
                        <th>是否会员</th>
                        <th>会员号</th>
                        <th>报名时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    
                    <c:if test="${page.totalPage le 0 }">
									<tr align="center">
										<td colspan="10"><span>无记录</span></td>
									</tr>
								</c:if>
								<c:if test="${page.totalPage gt 0 }">
									<c:forEach items="${page.result }" var="userinfo" varStatus="vs">
										<tr>
											<td>${userinfo.name }</td>
											<td>${userinfo.tel }</td>
											<td>${userinfo.email }</td>
											<td>
												<c:if test="${userinfo.sex eq 0 }">男</c:if>
												<c:if test="${userinfo.sex eq 1 }">女</c:if>
												<c:if test="${userinfo.sex eq 2 }">保密</c:if>
											</td>
											<td>
												<c:if test="${userinfo.vipflag eq 1 }">是</c:if>
												<c:if test="${userinfo.vipflag eq 0 }">否</c:if>
											</td>
											<td>${userinfo.id }</td>
											<td><fmt:formatDate value="${userinfo.createtime }" pattern="yyyy-MM-dd"/></td>
										</tr>
									</c:forEach>
						</c:if>
                    </tbody>
                </table>
                 <div class="text-center">
                	 <ul class="pagination">
                     <%@ include file="/res/common/pages.jsp"%>
                    </ul>
                
      	</form>     
        </div>
     


   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/activity/activity.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
