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
<input type="hidden" id="kcm" value="<%=basePath%>">
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=basePath %>admin/user/viplist" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        	查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div id="checkBoxLables" class="search-checkboxGroup  form-inline">
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
                 
                        <div class="search-otherGroup form-inline col-xs-12">
                        	<!-- 会员号 -->
                            <div class="form-group">
                                    <input name="vipnum" type="text" value="${vipnum}" class="form-control input-sm" placeholder="会员号">
                            </div>
                            <!-- 会员级别 -->
                            <div class="form-group ">
                                    <select class="form-control input-sm" name="viplevel">
                                          <option <c:if test="${viplevel==null or charge eq -1 }">selected="selected"</c:if>  value="-1">全部</option>
                                          <option <c:if test="${viplevel eq 0 }">selected="selected"</c:if> value="0">会员</option>
                                		  <option <c:if test="${viplevel eq 1 }">selected="selected"</c:if> value="1">共建者</option>
                                          <option <c:if test="${viplevel eq 2 }">selected="selected"</c:if> value="2">雁行家</option>
                                          <option <c:if test="${viplevel eq 3 }">selected="selected"</c:if> value="3">智库</option>
                                          <option <c:if test="${viplevel eq 4 }">selected="selected"</c:if> value="4">投资人</option>
                                    </select>
                            </div>
                            <div class="form-group ">
                            <!-- 性别 -->
                                    <select name="sex" class="form-control input-sm">
                                    	<option value="-1" <c:if test="${sex==null or sex eq -1 }">selected="selected"</c:if>>全部</option>
                                        <option value="0" <c:if test="${sex eq 0 }">selected="selected"</c:if>>男</option>
										<option value="1" <c:if test="${sex eq 1 }">selected="selected"</c:if>>女</option>
										<option value="2" <c:if test="${sex eq 2 }">selected="selected"</c:if>>保密</option>
                                    </select>
                            </div>
                            <!-- 空间展示 -->
                            <div class="checkbox">
                                <label>
                                    <select name="spaceshow" class="form-control input-sm">
                                    	<option value="-1" <c:if test="${spaceshow==null or sex eq -1 }">selected="selected"</c:if>>全部</option>
                                        <option value="0" <c:if test="${spaceshow eq 0 }">selected="selected"</c:if>>不展示</option>
										<option value="1" <c:if test="${spaceshow eq 1 }">selected="selected"</c:if>>空间展示</option>
                                    </select>
                                </label>
                            </div>
                            <!-- 会员展示 -->
                            <div class="checkbox">
                                 <select name="vipshow" class="form-control input-sm">
                                    	<option value="-1" <c:if test="${vipshow==null or sex eq -1 }">selected="selected"</c:if>>全部</option>
                                        <option value="0" <c:if test="${vipshow eq 0 }">selected="selected"</c:if>>不展示</option>
										<option value="1" <c:if test="${vipshow eq 1 }">selected="selected"</c:if>>会员展示</option>
                                    </select>
                            </div>
                            <div class="form-group">
                                    <input a name="keyword" type="text" value="${keyword}" class="form-control input-sm" placeholder="关键字">
                            </div>
                            <button type="submit" class=" btn btn-default btn-primary">查询</button>
                        </div>
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
                        <th>级别</th>
                        <th>邮箱</th>
                        <th>电话</th>
                        <th>展示</th>
                       <!-- <th>签到</th> --> 
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${page.totalPage le 0 }">
						<tr align="center">
							<td colspan="10"><span>无记录</span></td>
						</tr>
					</c:if>
					<c:if test="${page.totalPage gt 0 }">
							<c:forEach items="${page.result }" var="vip" varStatus="vs">
								<tr>
									<td>${(page.currentPage-1)*page.pageSize+vs.index+1}</td>
									<td><a href="<%=basePath %>admin/user/vipdetail?id=${vip.id}">${vip.vipnum}</a></td>
									<td>${vip.nickname}</td>
									<td>
										<c:if test="${vip.sex eq 0 }">男</c:if>
										<c:if test="${vip.sex eq 1 }">女</c:if>
										<c:if test="${vip.sex eq 2 }">保密</c:if>
									</td>
									<td>
										<c:if test="${vip.viplevel eq 0 }">会员</c:if>
										<c:if test="${vip.viplevel eq 1 }">共建者</c:if>
										<c:if test="${vip.viplevel eq 2 }">雁行家</c:if>
										<c:if test="${vip.viplevel eq 3 }">智库</c:if>
										<c:if test="${vip.viplevel eq 4 }">投资人</c:if>
									</td>
									<td>${vip.email }</td>
									<td>${vip.telnum }</td>
									<td class="form-inline">
                            			<div class="checkbox">
                                			<label>
                                				<input id="${vip.id}" class="spaceChecked"  type="checkbox" value="${vip.ifspace}" <c:if test="${vip.ifspace eq 1 }">checked="checked"</c:if> />
                                    			 空间展示
                                			</label>
                            			</div>
                            			<div class="checkbox">
                                			<label>
                                				<input id="${vip.id}" class="vipChecked"  type="checkbox" value="${vip.ifvipshow}" <c:if test="${vip.ifvipshow eq 1 }">checked="checked"</c:if> />
                                    			 会员展示
                                			</label>
                            			</div>
                        			</td>
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
