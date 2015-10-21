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
    <title>文章列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
<input type="hidden" id="kcm" value="<%=basePath%>">
       <div class="row row-flow">
            <div class="col-xs-12 member-list">
                <div id="search-condition" class="panel panel-primary search">
             	<form id="mainForm" name="mainForm" action="<%=basePath %>v20/admin/article/articlelist" method="post">
                   <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        	查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
                    <div class="panel-body collapse in" id="collapseSearch">
                    	 <div id="checkBoxLables" class="search-checkboxGroup  form-inline">
                   		 
                   		 </div>
                   		 
                        <div class="search-otherGroup form-inline col-xs-12">
                             
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
                        <th>链接</th>
                        <th>创建时间</th>
                      <!--   <th>审核状态</th> -->
                        <th>上下架</th>
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
							<c:forEach items="${page.result }" var="article" varStatus="vs">
								<tr>
											<td >${(page.currentPage-1)*page.pageSize+vs.index+1}</td>
											<td ><a href="<%=basePath %>v20/admin/article/getarticle?activityid=${article.id}">${article.title}</a></td>
											<td >${article.user.username}</td>
											<td >${article.linkurl}</td>
											<td >${article.createdate}</td>
											<%-- <td>
												<c:if test="${article.ischeck eq 0}">待审核</c:if>
												<c:if test="${article.ischeck eq 1}">通过审核</c:if>
												<c:if test="${article.ischeck eq 2}">审核失败</c:if>
											</td> --%>
											<td>
												<c:if test="${article.isshelves eq 0 }"><a class="btn-link btn-sm" href="javascript:void(0);" onclick="setonsale('${article.id}',1,this);">下架</a></c:if>
												<c:if test="${article.isshelves eq 1 }"><a class="btn-link btn-sm" href="javascript:void(0);" onclick="setonsale('${article.id}',0,this);">上架</a></c:if>
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
    <script type="text/javascript" src="<%=basePath%>res/scripts/article/addArticle.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
