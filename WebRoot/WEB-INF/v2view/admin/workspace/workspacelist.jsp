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
    <title>空间列表</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap-datetimepicker.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
       <div class="row row-flow">
             <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=webBasePath %>admin/workspace/spacelist" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        	查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
            
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div id="checkBoxLables" class="search-checkboxGroup  form-inline">
                   
                        <div class="search-otherGroup form-inline col-xs-12">
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
                    	<th>空间logo</th> 
                        <th>空间名称</th>
                        <th>空间状态</th> 
                        <th>联系电话</th>
                        <th>创建时间</th> 
                    </tr>
                    </thead>
                    <tbody>
                     <c:if test="${page.totalPage le 0 }">
						<tr align="center">
							<td colspan="10"><span>无记录</span></td>
						</tr>
					</c:if>
					<c:if test="${page.totalPage gt 0 }">
							<c:forEach items="${page.result }" var="coff" varStatus="vs">
								<tr>
									<td><a href="<%=webBasePath %>admin/user/updateuser?userid=${coff.id}">${(page.currentPage-1)*page.pageSize+vs.index+1}</a></td>
									<td><img style="width:40px;height: 30px" src="<%=webBasePath %>download/img?type=web&path=${coff.spacelogo}"/></td>
									<td>${coff.spacename }</td>
									<%-- <td><c:if test="${coff.effective ==0}">正常咖啡</c:if><c:if test="${coff.effective ==1}">限时咖啡</c:if><fmt:formatDate value="${coff.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
									<td>${coff.spacestatus}</td>
									<td>${coff.spacetel }</td>
									<td><fmt:formatDate value="${coff.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/moment.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
    <script>
        $().ready(function(){
            $('#id_start_time_div').datetimepicker({
                language: "zh-CN",
                format: "YYYY-MM-DD",
                autoclose: true,
                todayHighlight: true,
                pickTime: false
            });
            $('#id_end_time_div').datetimepicker({
                language: "zh-CN",
                format: "YYYY-MM-DD",
                autoclose: true,
                todayHighlight: true,
                pickTime: false
            });
        });
    </script>
</body>
</html>
