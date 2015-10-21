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
    <title>查看会员信息</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
    <div class="row row-flow member-add">
        <div class="form-group">
            <button class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" role="form" action="<%=basePath%>admin/user/saveuser"
				method="post" onsubmit="return validateform(this);">
				<input type="hidden" value="${user.uuid }" name="uuid">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">会员号</label>
                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="unum" name="unum" value="${user.unum }" <c:if test="${user.root eq 2}">readonly="readonly"</c:if> />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">姓名</label>
                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="realname" name="basicinfo.realname" value="${user.basicinfo.realname }">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">昵称</label>
                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="nickname" name="basicinfo.nickname" value="${user.basicinfo.nickname }">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">邮箱</label>
                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="email" name="basicinfo.email" value="${user.basicinfo.email}" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">电话</label>
                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="telephone" name="basicinfo.tel" value="${user.basicinfo.tel }">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">微信</label>
                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="telephone" name="basicinfo.weixin" value="${user.basicinfo.weixin }">
                        </div>
                    </div>
                     <!-- 是否收费 -->
                     <div class="form-group">
                        <label class="col-xs-2 control-label">收费</label>
                        <div class="col-xs-5">
                            <select class="form-control"  id="root" name="root">
								<option value="1" <c:if test="${user.root ne 2 }">selected="selected"</c:if>>是</option>
								<option value="2" <c:if test="${user.root eq 2 }">selected="selected"</c:if>>否</option>
							</select>
                        </div>
                    </div>
                    
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">性别</label>
                        <div class="col-xs-5">
                            <select class="form-control" name="basicinfo.sex">
								<option value="0" <c:if test="${user.basicinfo.sex eq 0 }">selected="selected"</c:if>>男</option>
								<option value="1" <c:if test="${user.basicinfo.sex eq 1 }">selected="selected"</c:if>>女</option>
							</select>
                        </div>
                    </div>
                    <div class="form-group">
                     <!-- 标签 -->
                        <label class="col-xs-2 control-label">领域</label>
                        <div class="col-xs-5 form-inline">
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
                    </div>
                    <!-- 行业 职业 -->
                    <div class="form-group">
                        <label class="col-xs-2 control-label">行业</label>
                        <div class="col-xs-2">
                            <select class="form-control"  name="jobinfo.industry.id">
                            	<c:forEach items="${industrys}" var="hy" varStatus="vs">
                            		<option value="${hy.id }" <c:if test="${hy.id eq user.jobinfo.industry.id}">selected="selected" </c:if>>${hy.zname }</option>
                            	</c:forEach>
                            </select>
                        </div>
                        <div class="">
                            <label class="col-xs-1 control-label">职业</label>
                            <div class="col-xs-2">
                            	<input type="hidden" id="hiddenJob" value="${user.jobinfo.job.id}">
                                <select class="form-control" name="jobinfo.job.id">
                                <c:forEach items="${jobs}" var="job" varStatus="vs">
                                	 <c:if test="${job.id eq user.jobinfo.job.id}">
                            			<option value="${job.id }"selected="selected">${job.zname }</option>
                            		 </c:if>
                            	</c:forEach>
                                	 
                                </select>
                            </div>
                        </div>
                    </div>
                    <!-- 公司 网址 简介 -->
                    <div class="form-group">
                        <label class="col-xs-2 control-label">公司</label>
                        <div class="col-xs-5">
                           <input type="text" class="form-control" id="company" name="jobinfo.company" value="${user.jobinfo.company}" maxlength="20">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">网址</label>
                        <div class="col-xs-5">
                             <input type="text" class="form-control" id="weburl" name="jobinfo.weburl" value="${user.jobinfo.weburl}" maxlength="200">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">个人简介</label>

                        <div class="col-xs-5">
                            <textarea rows="4" name="introduction"  class="form-control">${user.introduction }</textarea>
                        </div>
                    </div>
                    <!-- 未付费不展示 1.收费会员2.注册用户3.访客-->
                    <c:if test="${user.root eq 1}"> 
                    <c:if test="${focusList.size() > 0}">
                     <div class="form-group">
                        <label class="col-xs-2 control-label">加星会员</label>
                        <div class="col-xs-5">
                            <div class="list-group">
                            	<c:forEach var="fc" items="${focusList}" varStatus="vs">
                            		<a href="#" class="list-group-item">${fc.realname}</a>
                            	</c:forEach>
                            </div>
                        </div>
                    </div>
                    </c:if>
                    
                  
                    </c:if>
                    
                      <div class="form-group">
                        <label class="col-xs-2 control-label">开始日期</label>
                        <div class="col-xs-5">
                            <input class="form-control" id="begin" name="begin" value="<fmt:formatDate value="${user.basicinfo.vipdate1 }" pattern="yyyy-MM-dd"/>" type="text" onClick="WdatePicker()"  style="width: 160" onchange="addOneyear(this,1)" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">结束日期</label>
                        <div class="col-xs-5">
                            <input class="form-control"  id="end" name="end" value="<fmt:formatDate value="${user.basicinfo.vipenddate1 }" pattern="yyyy-MM-dd"/>" type="text" onClick="WdatePicker()"  style="width: 160" />
                        </div>
                    </div>
                    
                    <c:if test="${tips ne null }">
		    			<tr align="center">
		    				<td colspan="4"><font color="red">${tips }</font></td>
		    			</tr>
		    		</c:if>
                    <button  class="col-xs-3 col-xs-offset-1 btn btn-default btn-back">取消</button>
                    <button type="submit" class="col-xs-3 col-xs-offset-1 btn btn-primary">保存</button>
                    
                </form>
    </div>
   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/user/updateUser.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/validation.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
</body>
</html>
