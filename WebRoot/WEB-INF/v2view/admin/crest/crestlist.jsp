<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>
<%@ page import="cn.yi.gather.v20.*" %>
<%@ page import="java.lang.reflect.*" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String webBasePath=basePath+"v20/";
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>预约表单查看</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap-datetimepicker.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
       <div class="row row-flow">
             <div id="search-condition" class="panel panel-primary search">
                <form id="mainForm" name="mainForm" action="<%=webBasePath %>admin/crest/crestlist" method="post">
                    <a class="panel-heading" data-toggle="collapse" data-parent="#search-condition" href="#collapseSearch">
                        	查询条件
                        <i class="pull-right glyphicon glyphicon-list"></i>
                    </a>
            
                    <div class="panel-body collapse in" id="collapseSearch">
                        <div id="checkBoxLables" class="search-checkboxGroup  form-inline">
                   
                        <div class="search-otherGroup form-inline col-xs-12">
                          
                           <div class="form-group ">
                                      <select class="form-control input-sm need-type-select" name="ischeck">
		                                   	<option value="Tribe" <c:if test="${ischeck eq 'Tribe' }">selected="selected"</c:if>>社群共建</option>
		                                    <option value="Enterprise"  <c:if test="${ischeck eq 'Enterprise' }">selected="selected"</c:if>>品牌商／企业</option>
		                                    <option value="Co_working_space"  <c:if test="${ischeck eq 'Co_working_space' }">selected="selected"</c:if>>众创空间</option>
		                                    <option value="Tribe_partner"  <c:if test="${ischeck eq 'Tribe_partner' }">selected="selected"</c:if>>社区伙伴</option>
		                                    <option value="Visit"  <c:if test="${ischeck eq 'Visit' }">selected="selected"</c:if>>预约参观</option>
                               		 </select>
                            </div>
                            
                            
                            <button type="submit" class=" btn btn-default btn-primary">点击查询</button>
                        </div>
                    </div>
                </div>
                </div>
                
                
                <table class="table table-bordered table-striped member-table">
                    <thead>
                    <tr>
                    	<th>序号</th>
                    	 
                    	<%
                    		String entityName= request.getAttribute("ischeck").toString();
                    		Class<?> entity = Class.forName("cn.yi.gather.v20.entity."+entityName);
                    		Field[] field = entity.getDeclaredFields();
                    		 for (int i = 0; i < field.length; i++) {
                    			 Class<?> type = field[i].getType();
                    			 if(!type.getCanonicalName().equals("java.util.Date") && !field[i].getName().equals("serialVersionUID") &&!field[i].getName().equals("id") ){
						%>
						<th><%=field[i].getName() %></th>
						<% 
                    		 }}
                    	%>
                    	 <td>时间</td>
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
							<!-- Tribe -->
							<c:if test="${ischeck eq 'Tribe'}">
								<tr>
									<td><a href="javascript:void(0)">${(page.currentPage-1)*page.pageSize+vs.index+1}</a></td>
									<td>${coff.name }</td>
									<td>${coff.phone_number }</td>
									<td>${coff.email }</td>
									<td><c:if test="${coff.is_member eq '1'}">非会员</c:if> <c:if test="${coff.is_member eq '0'}">会员</c:if> </td>
									<td>${coff.tribe_name }</td>
									<td>${coff.tribe_introduction }</td>
									<td>${coff.plan }</td>
									<td>${coff.creator_introduction }</td>
									<td>${coff.reason }</td>
									<td>${coff.ip }</td>
									<td><fmt:formatDate value="${coff.createdate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									</tr>
							</c:if>		
						 
						   <!-- Enterprise -->
							<c:if test="${ischeck eq 'Enterprise'}">
								<tr>
									<td><a href="javascript:void(0)">${(page.currentPage-1)*page.pageSize+vs.index+1}</a></td>
									<td>${coff.name }</td>
									<td>${coff.phone_number }</td>
									<td>${coff.email }</td>
									<td><c:if test="${coff.is_member eq '1'}">非会员</c:if> <c:if test="${coff.is_member eq '0'}">会员</c:if> </td>
									<td>${coff.enterprise_name }</td>
									<td>${coff.project_introduction }</td>
									<td>${coff.need_service }</td>
									<td>${coff.ip }</td>
									<td><fmt:formatDate value="${coff.createdate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									</tr>
							</c:if>		
							
							 <!-- Co_working_space -->
							<c:if test="${ischeck eq 'Co_working_space'}">
								<tr>
									<td><a href="javascript:void(0)">${(page.currentPage-1)*page.pageSize+vs.index+1}</a></td>
									<td>${coff.name }</td>
									<td>${coff.phone_number }</td>
									<td>${coff.email }</td>
									<td><c:if test="${coff.is_member eq '1'}">非会员</c:if> <c:if test="${coff.is_member eq '0'}">会员</c:if> </td>
									<td>${coff.space_name }</td>
									<td>${coff.city }</td>
									<td>${coff.space_introduction }</td>
									<td>${coff.idea }</td>
									<td>${coff.ip }</td>
									<td><fmt:formatDate value="${coff.createdate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									</tr>
							</c:if>	
							
							 <!-- Visit -->
							<c:if test="${ischeck eq 'Visit'}">
								<tr>
									<td><a href="javascript:void(0)">${(page.currentPage-1)*page.pageSize+vs.index+1}</a></td>
									<td>${coff.name }</td>
									<td>${coff.phone_number }</td>
									<td>${coff.email }</td>
									<td><c:if test="${coff.is_member eq '1'}">非会员</c:if> <c:if test="${coff.is_member eq '0'}">会员</c:if> </td>
									<td>${coff.organization_name }</td>
									<td>${coff.organization_introduction }</td>
									<td>${coff.num_of_visitors }</td>
									<td>${coff.purpose }</td>
									<td>${coff.visit_date_time }</td>
									<td>${coff.ip }</td>
									<td><fmt:formatDate value="${coff.createdate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									</tr>
							</c:if>			
							
							<!-- Tribe_partner -->
							<c:if test="${ischeck eq 'Tribe_partner'}">
								<tr>
									<td><a href="javascript:void(0)">${(page.currentPage-1)*page.pageSize+vs.index+1}</a></td>
									<td>${coff.name }</td>
									<td>${coff.phone_number }</td>
									<td>${coff.email }</td>
									<td><c:if test="${coff.is_member eq '1'}">非会员</c:if> <c:if test="${coff.is_member eq '0'}">会员</c:if> </td>
									<td>${coff.organization_name }</td>
									<td>${coff.product_name }</td>
									<td>${coff.product_introduction }</td>
									<td>${coff.ip }</td>
									<td>${coff.idea }</td>
									<td><fmt:formatDate value="${coff.createdate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									</tr>
							</c:if>		
							
							
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
