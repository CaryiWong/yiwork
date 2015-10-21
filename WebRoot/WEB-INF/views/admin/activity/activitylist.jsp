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
<input type="hidden" id="kcm" value="<%=basePath%>">
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="search-condition" class="panel panel-primary search">
             	<form id="mainForm" name="mainForm" action="<%=basePath %>admin/activity/activitylist" method="post">
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
                  
                   		</div>
                   		
                        <div class="search-otherGroup form-inline col-xs-12">
                            <div class="form-group">
                                    <select class="form-control input-sm" name="cost">
                                       <option value="-1" <c:if test="${cost== null or cost eq -1 }">selected="selected"</c:if>>全部</option>
									   <option value="0" <c:if test="${cost eq 0 }">selected="selected"</c:if>>免费</option>
									   <option value="1" <c:if test="${cost eq 1 }">selected="selected"</c:if>>收费</option>
                                    </select>
                            </div>
                            <div class="form-group" name="checktype">
                                <select class="form-control input-sm" name="checktype">
                                   	<option value="-1" <c:if test="${checktype==null or checktype eq -1 }">selected="selected"</c:if>>全部</option>
								    <option value="0" <c:if test="${checktype eq 0 }">selected="selected"</c:if>>待审核</option>
								    <option value="1" <c:if test="${checktype eq 1 }">selected="selected"</c:if>>通过审核</option>
								    <option value="2" <c:if test="${checktype eq 2 }">selected="selected"</c:if>>审核失败</option>
                                </select>
                            </div>
                            <div class="form-group" name="status">
                                <select class="form-control input-sm" name="status">
                                    <option value="-1" <c:if test="${status==null or status eq -1 }">selected="selected"</c:if>>全部</option>
									<option value="1" <c:if test="${status eq 1 }">selected="selected"</c:if>>待开启</option>
									<option value="2" <c:if test="${status eq 2 }">selected="selected"</c:if>>进行中</option>
									<option value="3" <c:if test="${status eq 3 }">selected="selected"</c:if>>已结束</option>
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
                        <th>费用</th>
                        <th>联系方法</th>
                        <th>活动状态</th>
                        <th>最高人数</th>
                        <th>报名人数</th>
                        <th>操作</th>
                        <th>上/下架</th>
                        <th>回顾</th>
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
											<td ><a href="<%=basePath %>admin/activity/getupdateactivity?activityid=${activity.id}">${activity.title}</a></td>
											<td >${activity.usernickname}</td>
											<td >${activity.address}</td>
											<td >
												<c:if test="${activity.cost eq 0}">免费</c:if>
												<c:if test="${activity.cost eq 1}">收费</c:if>
											</td>
											<td >${activity.tel}</td>
											<td >
												<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
												<c:choose>
													<c:when test="${nowDate-activity.opendate <= 0 }">待开启</c:when>
													<c:when test="${nowDate-activity.opendate > 0 and nowDate-activity.enddate < 0}">进行中</c:when>
													<c:when test="${nowDate-activity.enddate >= 0 }">已结束</c:when>
												</c:choose>
											</td>
											<td >${activity.maxnum}</td>
											<td >${activity.signnum}</td>
											<td >
												<c:if test="${activity.isgood eq 0 }"><a name="setTuiJian" id="${activity.id}" class="btn-link btn-sm" href="javascript:void(0);" >推荐</a></c:if>
												<c:if test="${activity.isgood eq 1 }">取消推荐</c:if> 
												<c:if test="${activity.isbanner eq 0 }"><a class="btn-link btn-sm" href="javascript:void(0);" onclick="setbanner('${activity.id}',1,this);">设置banner</a></c:if>
												<c:if test="${activity.isbanner eq 1 }"><a class="btn-link btn-sm" href="javascript:void(0);" onclick="setbanner('${activity.id}',0,this);">取消banner</a></c:if>
											</td>
											<td>
												<c:if test="${activity.checktype eq 0 }">未审核</c:if>
												<c:if test="${activity.onsale eq 0 && activity.checktype eq 1}"><a class="btn-link btn-sm" href="javascript:void(0);" onclick="setonsale('${activity.id}',1,this);">下架</a></c:if>
												<c:if test="${activity.onsale eq 1 && activity.checktype eq 1}"><a class="btn-link btn-sm" href="javascript:void(0);" onclick="setonsale('${activity.id}',0,this);">上架</a></c:if>
											</td>
											<td >
												<c:if test='${activity.huiguurl =="" }'><a class="btn-link btn-sm" href="<%=basePath %>admin/activity/createhtml?activityid=${activity.id}" target="_blank">新增回顾</a></c:if>
												<c:if test='${activity.huiguurl !="" }'><a class="btn-link btn-sm" href="<%=basePath %>${activity.huiguurl}" target="_blank">查看回顾</a><a class="btn-link btn-sm" href="<%=basePath %>admin/activity/updatehtml?activityid=${activity.id}" target="_blank">修改回顾</a></c:if>
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
