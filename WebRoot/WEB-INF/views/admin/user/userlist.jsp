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
    <title>会员列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=basePath %>v2/admin/user/userlist" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div class="search-checkboxGroup  form-inline">
                        <!-- 标签 -->
                         <c:forEach items="${labels}" var="label" varStatus="vs">
								<c:if test="${vs.index mod 10 eq 0 }"><br></c:if>
								<c:set var="la" value="${label.id}"></c:set>
								<div class="checkbox">
                            		<label>
                              			<input name="groups" value="${label.id}" type="checkbox" <c:if test="${fn:contains(labelList,la) }">checked="checked"</c:if> />${label.zname }
                            		</label>
                            	</div>
						</c:forEach>
                        </div>
                        <div class="search-otherGroup form-inline col-xs-12">
                            <div class="form-group">
                                    <input type="text"  name="userNO" value="${userNO}" class="form-control input-sm" placeholder="会员号">
                            </div>
                            <div class="form-group ">
                                    <select name="sex" class="form-control input-sm">
                                       <option value="-1" <c:if test="${sex==null or sex eq -1 }">selected="selected"</c:if>>全部</option>
									   <option value="0" <c:if test="${sex eq 0 }">selected="selected"</c:if>>男</option>
									   <option value="1" <c:if test="${sex eq 1 }">selected="selected"</c:if>>女</option>
									   <option value="2" <c:if test="${sex eq 2 }">selected="selected"</c:if>>保密</option>
                                    </select>
                            </div>
                            
                            <div class="form-group">
                            	<select class="form-control input-sm"  name="charge">
	                            	<option value="-1" <c:if test="${charge==null or charge eq -1 }">selected="selected"</c:if>>全部</option>
									<option value="0" <c:if test="${charge eq 0 }">selected="selected"</c:if>>管理员</option>
									<option value="1" <c:if test="${charge eq 1 }">selected="selected"</c:if>>已缴</option>
									<option value="2" <c:if test="${charge eq 2 }">selected="selected"</c:if>>未缴</option>
								</select>
                            </div>
                            
                            <div class="form-group">
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
                        <th>会员号</th>
                        <th>昵称</th>
                        <th>性别</th>
                        <th>权限</th>
                        <th>邮箱</th>
                        <th>电话</th>
                       <!--  <th>展示</th> -->
                       <!--  <th>签到</th> -->
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
									<td><a href="<%=basePath %>admin/user/updateuser?userid=${userinfo.uuid}">${(page.currentPage-1)*page.pageSize+vs.index+1}</a></td>
									<td>${userinfo.unum }</td>
									<td><a href="<%=basePath %>admin/user/updateuser?userid=${userinfo.uuid}">${userinfo.basicinfo.nickname }</a></td>
									<td>
										<c:if test="${userinfo.basicinfo.sex eq 0 }">男</c:if>
										<c:if test="${userinfo.basicinfo.sex eq 1 }">女</c:if>
										<c:if test="${userinfo.basicinfo.sex eq 2 }">保密</c:if>
									</td>
									<td>
										<c:if test="${userinfo.root eq 0 }">管理员</c:if>
										<c:if test="${userinfo.root eq 1 }">会员</c:if>
										<c:if test="${userinfo.root eq 2 }">普通</c:if>
									</td>
									<td>${userinfo.basicinfo.email }</td>
									<td>${userinfo.basicinfo.tel }</td>
									<!-- 
									<td class="form-inline">
                            			<div class="checkbox">
                                			<label>
                                    			<input id="${userinfo.uuid}" class="spaceChecked" type="checkbox"> 空间展示
                                			</label>
                            			</div>
                            			<div class="checkbox">
                                			<label>
                                    			<input type="checkbox"> 会员展示
                                			</label>
                            			</div>
                        			</td>
                        			 -->
                        			<!-- 
									<td>
									 <input class="" type="text"/> <a href="#" class="btn-link">签到</a>
									</td>
									  -->
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
        </div>

    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/user/userList.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
