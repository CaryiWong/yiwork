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
    <title>需求列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
<input type="hidden" id="kcm" value="<%=basePath%>">
       <div class="row row-flow">
        <%-- <div class="form-group col-xs-3">
                <a href="<%=basePath%>admin/demand/adddemand" class="btn btn-primary col-xs-12">新增需求 <i class="glyphicon glyphicon-plus"></i></a>
            </div> --%>
            <div class="col-xs-12 member-list">
                <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=webBasePath %>admin/demand/demandlist?demandtype=-1" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        	查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
            
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div id="checkBoxLables" class="search-checkboxGroup  form-inline">
                       
                 
                        <div class="search-otherGroup form-inline col-xs-12">
                            <!-- 需求状态 -->
                            <div class="form-group ">
                                    <select class="form-control input-sm" name="status">
                                  		<option value="-1" <c:if test="${status==null or status eq -1 }">selected="selected"</c:if>>全部</option>
										<option value="0" <c:if test="${status eq 0 }">selected="selected"</c:if>>待解决</option>
										<option value="1" <c:if test="${status eq 1 }">selected="selected"</c:if>>正在解决</option>
										<option value="2" <c:if test="${status eq 2 }">selected="selected"</c:if>>已解决</option>
                                    </select>
                            </div>
                            
                             <!-- 待后台完善  8.15
                            <div class="form-group ">
                                    <select class="form-control input-sm" name="onsale">
                                  		<option value="-1" <c:if test="${onsale==null or onsale eq -1 }">selected="selected"</c:if>>全部</option>
										<option value="0" <c:if test="${onsale eq 0 }">selected="selected"</c:if>>上架</option>
										<option value="1" <c:if test="${onsale eq 1 }">selected="selected"</c:if>>下架</option>
                                    </select>
                            </div>
                            上下架状态 -->
                            
                        <%--     <!-- 需求类型 -->
                             <div class="form-group ">
                                      <select class="form-control input-sm need-type-select" name="demandtype">
		                                   	<option value="-1" <c:if test="${demandtype eq -1 }">selected="selected"</c:if>>需求类型</option>
		                                    <option value="0"<c:if test="${demandtype eq 0 }">selected="selected"</c:if>>寻人</option>
		                                    <option value="1"<c:if test="${demandtype eq 1 }">selected="selected"</c:if>>视频制作</option>
		                                    <option value="2"<c:if test="${demandtype eq 2 }">selected="selected"</c:if>>发起活动</option>
		                                    <option value="3"<c:if test="${demandtype eq 3 }">selected="selected"</c:if>>其他</option>
                               		 </select>
                            </div> --%>
                            
                             <!-- 审核类型 -->
                             <div class="form-group ">
                                      <select class="form-control input-sm need-type-select" name="ischeck">
		                                   	<option value="-1" <c:if test="${ischeck eq -1 }">selected="selected"</c:if>>全部</option>
		                                    <option value="0"  <c:if test="${ischeck eq 0 }">selected="selected"</c:if>>待审核</option>
		                                    <option value="1"  <c:if test="${ischeck eq 1 }">selected="selected"</c:if>>已审核</option>
                               		 </select>
                            </div>
                            
                            
                            <div class="form-group">
                                    <input  name="keyword" type="text" value="${keyword}" class="form-control input-sm" placeholder="关键字">
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
                    	<th>标题</th>
                        <th>发布者</th>
                        <td>上下架</td>
                        <th>类型属性</th>
                        <th>发布时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${page.totalPage le 0 }">
									<tr>
										<td colspan="10"><span>无记录</span></td>
									</tr>
								</c:if>
								<c:if test="${page.totalPage gt 0 }">
									<c:forEach items="${page.result }" var="demands" varStatus="vs">
										<tr>
											<td>${(page.currentPage-1)*page.pageSize+vs.index+1}</td>
											<td>
												<a href="<%=webBasePath %>admin/demand/updatedemand?demandid=${demands.id }">${demands.title }</a>
											</td>
											<td> ${demands.publishuser.nickname }</td>
											<td>
												<c:if test="${demands.onsale eq 0 }"><a class="btn-link btn-sm" href="javascript:void(0);" onclick="setonsale('${demands.id}',1,this);">下架</a></c:if>
												<c:if test="${demands.onsale eq 1 }"><a class="btn-link btn-sm" href="javascript:void(0);" onclick="setonsale('${demands.id}',0,this);">上架</a></c:if>
											</td>
											
											<td>
												<c:if test="${demands.status eq 0 }">待解决</c:if>
												<c:if test="${demands.status eq 1 }">正在解决</c:if>
												<c:if test="${demands.status eq 2 }">已解决</c:if>
											</td>
											<td>
												<fmt:formatDate value="${demands.publishdate0 }" pattern="yyyy-MM-dd"/>
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
    <script type="text/javascript" src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/demand/demand.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
