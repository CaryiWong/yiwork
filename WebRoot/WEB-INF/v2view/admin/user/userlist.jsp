<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>
<%@ page import="cn.yi.gather.v20.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String webBasePath=basePath+"v20/";
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
                <form id="mainForm" name="mainForm" action="<%=webBasePath %>admin/user/userlist" method="post">
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
									<!-- 用户状态 0 系统 1 管理员 2 会员 3普通用户 -->
									<option value="0" <c:if test="${charge eq 0 }">selected="selected"</c:if>>系统</option>
									<option value="1" <c:if test="${charge eq 1 }">selected="selected"</c:if>>管理员</option>
									<option value="2" <c:if test="${charge eq 2 }">selected="selected"</c:if>>会员</option>
									<option value="3" <c:if test="${charge eq 3 }">selected="selected"</c:if>>过期会员</option>
									<option value="4" <c:if test="${charge eq 4 }">selected="selected"</c:if>>注册用户</option>
								</select>
                            </div>
                            
                            <div class="form-group">
                                    <input type="text" name="keyword" value="${keyword }" class="form-control input-sm" placeholder="关键字/姓名/邮箱/手机/昵称">
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
                        <th>头像</th>
                        <th>姓名</th>
                        <th>昵称</th>
                        <th>性别</th>
                        <th>权限</th>
                        <th>邮箱</th>
                        <th>空间</th>
                        <th>电话</th>
                        <th>会员开始时间</th>
                        <th>会员结束时间</th>
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
									<td><a href="<%=webBasePath %>admin/user/updateuser?userid=${userinfo.id}">${(page.currentPage-1)*page.pageSize+vs.index+1}</a></td>
									<td>${userinfo.unum }</td>
									<td><img style="width:40px;height: 30px" src="<%=webBasePath %>download/img?type=web&path=${userinfo.minimg}"/></td>
									
									<td>${userinfo.realname}</td>
									<td><a href="<%=webBasePath %>admin/user/updateuser?userid=${userinfo.id}">${userinfo.nickname}</a></td>
									<td>
										<c:if test="${userinfo.sex eq 0 }">男</c:if>
										<c:if test="${userinfo.sex eq 1 }">女</c:if>
										<c:if test="${userinfo.sex eq 2 }">保密</c:if>
									</td>
									<td>
									<!-- 用户状态 0 系统 1 管理员 2 会员 3普通用户 -->
										<c:if test="${userinfo.root eq 0 }">系统</c:if>
										<c:if test="${userinfo.root eq 1 }">管理员</c:if>
										<c:if test="${userinfo.root eq 2 }">会员</c:if>
										<c:if test="${userinfo.root eq 3 }">过期会员</c:if>
										<c:if test="${userinfo.root eq 4 }">注册用户</c:if>
									</td>
									<td>${userinfo.email }</td>
									<td><font style="color: red">${userinfo.spaceInfo.spacename}</font></td>
									<td>${userinfo.telnum }</td>
									<td><fmt:formatDate value="${userinfo.vipdate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td><fmt:formatDate value="${userinfo.vipenddate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									
									<!-- 
									<td class="form-inline">
                            			<div class="checkbox">
                                			<label>
                                    			<input id="${userinfo.id}" class="spaceChecked" type="checkbox"> 空间展示
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
