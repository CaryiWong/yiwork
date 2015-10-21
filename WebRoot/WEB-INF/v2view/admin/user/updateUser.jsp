<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="cn.yi.gather.v20.entity.*"%>
<%@include file="/res/common/taglib.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String userjsId=request.getParameter("userid");
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>查看会员</title>
<link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css" />
<link rel="stylesheet" href="<%=basePath%>res/styles/public.css" />
<link rel="stylesheet"
	href="<%=basePath%>res/scripts/lou-multi-select-8712b02/css/multi-select.css" />
</head>
<body>
	<div class="row row-flow member-apply">
		<form class="form-horizontal col-xs-12" name="mainform" id="mainform"
			action="<%=basePath %>v20/admin/user/saveuser" method="post"
			onsubmit="return validateform(this);">
			<input type="hidden" value="${user.id }" name="id" id="id"> <input
				type="hidden" value="" id="favouriteid" name="favouriteid">

			<div class="row">
				<div class="form-group">
					<label class="col-xs-2 control-label">真实姓名</label>

					<div class="col-xs-5">
						<input type="hidden" id="userid" value="${user.id}" /> <input
							type="text" class="form-control valid-input"
							value="${user.realname}" name="realname"
							data-valid-rule="required">
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-2 control-label">昵称</label>
					<div class="col-xs-5">
						<input data-valid-rule="required" name="nickname" id="nickname"
							value="${user.nickname}" type="text"
							class="form-control valid-input" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-2 control-label">性别</label>

					<div class="col-xs-5">
						<select class="form-control" name="sex">
							<option value="0"
								<c:if test="${user.sex eq 0 }">selected="selected"</c:if>>男</option>
							<option value="1"
								<c:if test="${user.sex eq 1 }">selected="selected"</c:if>>女</option>
						</select>
					</div>
				</div>

				<!-- 是否缴费 -->
				<c:if test="${user.root ==2 || user.root ==3 || user.root ==4}">
					<div class="form-group">
						<label class="col-xs-2 control-label"><font color="red">是否缴费</font></label>

						<div class="col-xs-5">
							<select class="form-control" name="root">
								<option value="2"
									<c:if test="${user.root eq 2 }">selected="selected"</c:if>>是</option>
								<option value="3"
									<c:if test="${user.root eq 3 || user.root eq 4}">selected="selected"</c:if>>否</option>
							</select>
						</div>
					</div>
				</c:if>
				<c:if test="${user.root ==1 || user.root ==0}">
					<input type="hidden" name="root" value="${user.root}">
				</c:if>
				<div class="form-group">
					<label class="col-xs-2 control-label"><font color="red">会员号</font></label>

					<div class="col-xs-5">
						<input data-valid-rule="required" type="text" id="unum"
							name="unum" value="${user.unum}" class="form-control valid-input" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-2 control-label"><font color="red">会员开始日期</font></label>
					<div class="col-xs-5">
						<input class="form-control" id="begin" name="begin"
							value="<fmt:formatDate value="${user.vipdate }" pattern="yyyy-MM-dd"/>"
							type="text" onClick="WdatePicker()" style="width: 160"
							onchange="addOneyear(this,1)" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label"><font color="red">会员结束日期</font></label>
					<div class="col-xs-5">
						<input class="form-control" id="end" name="end"
							value="<fmt:formatDate value="${user.vipenddate }" pattern="yyyy-MM-dd"/>"
							type="text" onClick="WdatePicker()" style="width: 160" />
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-2 control-label">创建时间</label>

					<div class="col-xs-5">
						<input class="form-control" readonly="readonly" id="createdate2"
							name="createdate2"
							value="<fmt:formatDate value="${user.createdate }" pattern="yyyy-MM-dd hh:mm:ss"/>"
							type="text" onClick="WdatePicker()" />

					</div>
				</div>
				<hr />

				<!-- 年龄 -->
				<div class="form-group">
					<label class="col-xs-2 control-label">年龄</label>
					<div class="col-xs-5">
						<input type="text" class="form-control valid-input" name="age"
							value="${user.age}" readonly="readonly"
							data-valid-rule="required">
					</div>
				</div>

				<!-- 地区 -->
				<div class="form-group">
					<label class="col-xs-2 control-label">地区</label>

					<div class="col-xs-2">
						<select name="province" class="province form-control"
							data-valid-group="location">
							<option value="${user.province}">${user.province}</option>
						</select>
					</div>
					<div class="col-xs-2">
						<select name="city" class="city form-control"
							data-valid-rule="required" data-valid-group="location"
							data-valid-text='{"required":"请选择地区信息"}'>
							<option value="${user.city}">${user.city}</option>
						</select>
					</div>
				</div>

				<!-- 关注 -->
				<div class="form-group">
					<label class="col-xs-2 control-label">关注</label>
					<div class="col-xs-5 form-inline">
						<c:forEach items="${focus}" var="label" varStatus="vs">
							<c:if test="${vs.index mod 10 eq 0 }"></c:if>
							<c:set var="la" value="${label.id}"></c:set>
							<div class="checkbox">
								<input name="groups" value="${label.id}" type="checkbox"
									<c:if test="${fn:contains(focuslist,la) }">checked="checked"</c:if> />${label.zname}</div>
						</c:forEach>
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-2 control-label">行业</label>
					<div class="col-xs-5">
						<select class="form-control valid-input"
							data-valid-rule="required" name="industry.id">
							<c:forEach items="${industrys}" var="hy" varStatus="vs">
								<option value="${hy.id }"
									<c:if test="${fn:contains(jobpid,hy.id) }"> selected="selected" </c:if>>${hy.zname}</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-2 control-label">职业</label>
					<div class="col-xs-5">
						<select class="form-control valid-input" name="jobid"
							data-valid-rule="required">
							<c:forEach items="${jobs}" var="job" varStatus="vs">
								<option value="${job.id }"
									<c:if test="${fn:contains(jobid,job.id) }"> selected="selected" </c:if>>${job.zname}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<!-- 兴趣 
				<div class="form-group">
					<label class="col-xs-2 control-label">兴趣领域</label>
					<div class="col-xs-5">
						<select id='optgroup' multiple='multiple'>
							<%
							 User u = (User)request.getAttribute("user");
							 Set set =u.getFavourite();
							 Iterator it= set.iterator();
							 while(it.hasNext()){
								 Labels label =(Labels)it.next();
							%>
							<option value="<%=label.getId()%>" selected><%=label.getZname() %></option>
							<%} %>
						</select>
					</div>
				</div>
			</div>
			-->
				<div class="form-group">
					<label class="col-xs-2 control-label">证件类型${user.icnum_type}1</label>

					<div class="col-xs-2">
						<select name="icnum_type" class="province form-control">
							<option value="ID"
								<c:if test="${user.icnum_type eq 'ID' ||user.icnum_type==null}">selected="selected"</c:if>>二代身份证</option>
							<option value="HKM"
								<c:if test="${user.icnum_type eq 'HKM' }">selected="selected"</c:if>>港澳通行证</option>
							<option value="TW"
								<c:if test="${user.icnum_type eq 'TW' }">selected="selected"</c:if>>台湾通行证</option>
							<option value="PP"
								<c:if test="${user.icnum_type eq 'PP' }">selected="selected"</c:if>>护照</option>
						</select>
					</div>

				</div>

				<div class="form-group">
					<label class="col-xs-2 control-label">证件号码</label>

					<div class="col-xs-5">
						<input type="text" class="form-control valid-input" name="icnum"
							value="${user.icnum}" data-valid-rule="required">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">电话号码</label>

					<div class="col-xs-5">
						<input type="text" class="form-control valid-input" name="telnum"
							id="telnum" value="${user.telnum}" data-valid-rule="required">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">邮箱</label>

					<div class="col-xs-5">
						<input type="text" class="form-control valid-input" name="email"
							id="email" value="${user.email}" data-valid-rule="required">
					</div>
				</div>
				<hr />

				<div class="form-group">
					<label class="col-xs-2 control-label">自我介绍</label>

					<div class="col-xs-5">
						<textarea rows="4" class="form-control valid-input"
							name="introduction" data-valid-rule="required">${user.introduction}</textarea>
					</div>
				</div>

				<%--  暂时不做 0304   <!-- 附件信息 -->
                   <%
                   User us = (User)request.getAttribute("user");
                   List<Userother> list =(List<Userother>)request.getAttribute("userotherlist");
                   Set otherset =us.getUserothers();
                   
                   for(int i=0;i<list.size();i++){
                	   Userother userother=list.get(i);
                	   Iterator its = otherset.iterator();
                	   //UserotherInfo
                	
                   %>
                     <div class="form-group">
                        <label class="col-xs-2 control-label"><%=userother.getZtitle() %></label>
                        <div class="col-xs-5">
                            <textarea onchange="updateOther(this)" rows="4" class="form-control valid-input"  name="<%=userother.getEtitle() %>"><% while(its.hasNext()){UserotherInfo info = (UserotherInfo)its.next();if(((Userother)(info.getUserother())).getId().equals(userother.getId())){%><%=info.getTexts()%><%}}%></textarea>
                        </div>
                    </div>
                   
                   <%} %> --%>

				<!-- 各种图片 -->
			<%-- 	<div class="form-group">
					<label class="col-xs-2 control-label">原二维码</label>

					<div class="col-xs-5">
						<img alt="原二维码图片" id="Yuantwonum"
							src="<%=basePath %>v20/download/img?type=web&path=${user.twonum}"
							style="width: 180px;height: 180px;">
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-2 control-label">修改二维码</label>

					<div class="col-xs-5">
						<input type="file" id="maximgfile" name="img"
							onchange="uploaderweima();" style="width: 150px;" /> <input
							type="hidden" name="twonum" id="twonum" value="${user.twonum }">
					</div>
				</div>

				<hr /> --%>
				<div class="form-group">
					<label class="col-xs-2 control-label">原用户图片</label>

					<div class="col-xs-5">
						<img alt="原二维码图片" id="Yuanuserimg"
							src="<%=basePath %>v20/download/img?type=web&path=${user.minimg}"
							style="width: 180px;height: 180px;">
					</div>
				</div>


				<div class="form-group">
					<label class="col-xs-2 control-label">修改用户图像</label>
					<div class="col-xs-5">
						<input type="file" id="userimgfile" name="img"
							onchange="uploadtx();" style="width: 150px;" /> <input
							type="hidden" name="minimg" id="minimg" value="${user.minimg}">
					</div>
				</div>


				<%-- <div class="form-group">
					<label class="col-xs-2 control-label">原用户大图1</label>

					<div class="col-xs-5">
						<img alt="原用户大图" id="Yuanhugoimg"
							src="<%=basePath %>v20/download/img?type=web&path=${user.maximg}"
							style="width: 180px;height: 180px;">
					</div>
				</div>


				<div class="form-group">
					<label class="col-xs-2 control-label">用户大图</label>

					<div class="col-xs-5">
						<input type="file" id="hugoimgfile" name="img"
							onchange="uploadmaximg();" style="width: 150px;" /> <input
							type="hidden" name="maximg" id="maximg" value="${user.maximg}">
					</div>
				</div>
 --%>


				<div class="form-group hidden">
					<label class="col-xs-2 control-label">是否空间展示</label>

					<div class="col-xs-5">
						<select class="form-control" name="ifspace">
							<option
								<c:if test="${user.ifspace eq 0 }">selected="selected"</c:if>
								value="0">不展示</option>
							<option
								<c:if test="${user.ifspace eq 1 }">selected="selected"</c:if>
								value="1">展示</option>
						</select>
					</div>
				</div>

				<div class="form-group hidden">
					<label class="col-xs-2 control-label">是否会员展示</label>

					<div class="col-xs-5">
						<select class="form-control" name="ifvipshow">
							<option
								<c:if test="${user.ifvipshow eq 0 }">selected="selected"</c:if>
								value="0">不展示</option>
							<option
								<c:if test="${user.ifvipshow eq 1 }">selected="selected"</c:if>
								value="1">展示</option>
						</select>
					</div>
				</div>


				<c:if test="${tips ne null }">
					<tr align="center">
						<td colspan="6"><font color="red">${tips}</font></td>
					</tr>
				</c:if>
				<div class="form-group">
					<div class="container">
						<button type="submit"
							class="col-xs-3 col-xs-offset-3 btn btn-primary">提交</button>
					</div>
				</div>
		</form>
	</div>

	<script type="text/javascript">
	var root="<%=basePath%>";
	var userjsid = document.getElementById("id").value;
	</script>
	<script type="text/javascript"
		src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>res/scripts/lou-multi-select-8712b02/js/jquery.multi-select.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>res/js/ajaxfileupload.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>scripts/components/location/location.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>res/scripts/user/updatevip.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>res/scripts/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>res/scripts/validation.js"></script>

</body>
</html>
