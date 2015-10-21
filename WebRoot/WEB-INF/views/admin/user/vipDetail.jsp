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
    <title>查看会员</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
 <div class="row row-flow member-apply">
            <form class="form-horizontal col-xs-12" name="mainform" id="mainform" action="<%=basePath %>admin/user/savevip"
				method="post" onsubmit="return validateform(this);">
				<input type="hidden" value="${vip.id }" name="id">
                <div class="row">
                     <div class="form-group">
                        <label class="col-xs-2 control-label">真实姓名</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control valid-input"
                                 value="${vip.realname}" name="realname"  data-valid-rule="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">社区虚拟身份</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control valid-input"
                                                     name="virtualname"  value="${vip.virtualname}"         data-valid-rule="required">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">性别</label>

                        <div class="col-xs-5">
                            <select class="form-control" name="sex">
                                <option value="0" <c:if test="${vip.sex eq 0 }">selected="selected"</c:if>>男</option>
								<option value="1" <c:if test="${vip.sex eq 1 }">selected="selected"</c:if>>女</option>
                            </select>
                        </div>
                    </div>
                    <!-- 地区 -->
                    <div class="form-group">
                        <label class="col-xs-2 control-label">地区</label>

                        <div class="col-xs-2">
                             <select name="province" class="province form-control"
                                data-valid-group="location">
                            	<option value="${vip.province}">${vip.province}</option>
                        	</select>
                        </div>
                        <div class="col-xs-2">
                            <select name="city" class="city form-control"
                                data-valid-rule="required" data-valid-group="location"
                                data-valid-text='{"required":"请选择地区信息"}'>
                            <option value="${vip.city}">${vip.city}</option>
                       		 </select>
                        </div>
                    </div>
                    
                    
                    <!-- 年龄 -->
                     <div class="form-group">
                        <label class="col-xs-2 control-label">年龄</label>
                        <div class="col-xs-5">
                             <input type="text" class="form-control valid-input" name="age"
                                          value="${vip.age}"            data-valid-rule="required">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">身份证</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control valid-input" name="icnum"
                                          value="${vip.icnum}"            data-valid-rule="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">电话号码</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control valid-input"  name="telnum"
                                              value="${vip.telnum}"                    data-valid-rule="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">邮箱</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control valid-input"  name="email"
                                                    value="${vip.email}"              data-valid-rule="required">
                        </div>
                    </div>
                    <hr/>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">微信</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control valid-input"  name="wechat"
                                                value="${vip.wechat}"                  data-valid-rule="required">
                        </div>
                    </div>
                     <div class="form-group">
                     <!-- 标签 -->
                        <label class="col-xs-2 control-label">兴趣领域</label>
                        <div class="col-xs-5 form-inline">
                            <c:forEach items="${areas}" var="label" varStatus="vs">
								<c:if test="${vs.index mod 10 eq 0 }"></c:if>
								<c:set var="la1" value="${label.id}"></c:set>
								<div class="checkbox">
                            		<label>
                              			<input name="interests" value="${label.id}" type="checkbox" <c:if test="${fn:contains(interest,la1) }">checked="checked"</c:if> />${label.zname }
                            		</label>
                            	</div>
						</c:forEach>
                        </div>
                    </div>
                     <!-- 兴趣领域 -->
                     <div class="form-group">
                    
                        <label class="col-xs-2 control-label">擅长领域</label>
                        <div class="col-xs-5 form-inline">
                            <c:forEach items="${areas}" var="label" varStatus="vse">
								<c:if test="${vse.index mod 10 eq 0 }"></c:if>
								<c:set var="la" value="${label.id}"></c:set>
								<div class="checkbox">
                            		<label>
                              			<input name="skilleds" value="${label.id}" type="checkbox" <c:if test="${fn:contains(skilled,la) }">checked="checked"</c:if> />${label.zname }
                            		</label>
                            	</div>
						</c:forEach>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">自我介绍</label>

                        <div class="col-xs-5">
                            <textarea rows="4" class="form-control valid-input"  name="myintroduction"
                                   data-valid-rule="required">${vip.myintroduction}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">团队介绍</label>

                        <div class="col-xs-5">
                            <textarea rows="4" class="form-control valid-input"  name="teamintroduction"
                                                               data-valid-rule="required">${vip.teamintroduction}</textarea>
                        </div>
                    </div>
                   
                   <!-- 各种图片 -->
                   <div class="form-group">
                        <label class="col-xs-2 control-label">原二维码</label>

                        <div class="col-xs-5">
                        	<img alt="原二维码图片" id="Yuantwonum" src="<%=basePath %>download/img?type=1&path=${vip.twonum }" style="width: 180px;height: 180px;">
                        </div>
                    </div>
                   
                    <div class="form-group">
                        <label class="col-xs-2 control-label">修改二维码</label>

                        <div class="col-xs-5">
                        	<input type="file" id="maximgfile" name="img" onchange="uploaderweima();" style="width: 150px;"/>
							<input type="hidden" name="twonum" id="twonum" value="${vip.twonum }">
                        </div>
                    </div>
                    
                    
                    <hr/>
                     <div class="form-group">
                        <label class="col-xs-2 control-label">原用户图片</label>

                        <div class="col-xs-5">
                        	<img alt="原二维码图片" id="Yuanuserimg" src="<%=basePath %>download/img?type=1&path=${vip.userimg }" style="width: 180px;height: 180px;">
                        </div>
                    </div>
                    
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">修改用户图像</label>
                        <div class="col-xs-5">
                            <input type="file" id="userimgfile" name="img" onchange="uploadtx();" style="width: 150px;"/>
							<input type="hidden" name="userimg" id="userimg" value="${vip.userimg }">
                        </div>
                    </div>
                    
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">原用户大图</label>

                        <div class="col-xs-5">
                        	<img alt="原用户大图" id="Yuanhugoimg" src="<%=basePath %>download/img?type=1&path=${vip.hugoimg }" style="width: 180px;height: 180px;">
                        </div>
                    </div>
                    
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">用户大图</label>

                        <div class="col-xs-5">
                             <input type="file" id="hugoimgfile" name="img" onchange="uploadmaximg();" style="width: 150px;"/>
							<input type="hidden" name="hugoimg" id="hugoimg" value="${vip.hugoimg }">
                        </div>
                    </div>
					
					<!-- 所在地区 -->
					
					
                    <div class="form-group">
                        <label class="col-xs-2 control-label">会员号</label>

                        <div class="col-xs-5">
                            <input data-valid-rule="required" type="text"  name="vipnum" value="${vip.vipnum}"    class="form-control valid-input"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">昵称</label>

                        <div class="col-xs-5">
                            <input data-valid-rule="required" name="nickname"  value="${vip.nickname}" type="text" class="form-control valid-input"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">行业</label>

                        <div class="col-xs-5">
                            <select  class="form-control valid-input"  data-valid-rule="required" name="industry">
                            	<c:forEach items="${industrys}" var="hy" varStatus="vs">
                            		<option value="${hy.id }" <c:if test="${hy.id eq vip.industrybclass.id}">selected="selected" </c:if>>${hy.zname }</option>
                            	</c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">职业</label>

                        <div class="col-xs-5">
                            <select class="form-control valid-input"  name="job"
                                    data-valid-rule="required">
                               <c:forEach items="${jobs}" var="job" varStatus="vs">
                                	 <c:if test="${job.id eq vip.jobclass.id}">
                            			<option value="${job.id }" selected="selected">${job.zname}</option>
                            		 </c:if>
                            	</c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">所在团队名</label>

                        <div class="col-xs-5">
                            <input class="form-control valid-input"  name="teamname" value="${vip.teamname}" type="text" data-valid-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">团队url</label>

                        <div class="col-xs-5">
                            <input class="form-control valid-input" type="text"  name="teamurl" value="${vip.teamurl}" data-valid-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">会员级别</label>

                        <div class="col-xs-5">
                            <select class="form-control" name="viplevel">
                               			  <option <c:if test="${vip.viplevel eq 0 }">selected="selected"</c:if> value="0">会员</option>
                                		  <option <c:if test="${vip.viplevel eq 1 }">selected="selected"</c:if> value="1">共建者</option>
                                          <option <c:if test="${vip.viplevel eq 2 }">selected="selected"</c:if> value="2">雁行家</option>
                                          <option <c:if test="${vip.viplevel eq 3 }">selected="selected"</c:if> value="3">智库</option>
                                          <option <c:if test="${vip.viplevel eq 4 }">selected="selected"</c:if> value="4">投资人</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">视频链接</label>

                        <div class="col-xs-5">
                            <input class="form-control valid-input" type="text" name="mediaurl" value="${vip.mediaurl}" data-valid-rule="required"/>
                        </div>
                    </div>
				
				   <div class="form-group">
                        <label class="col-xs-2 control-label">个人主页</label>

                        <div class="col-xs-5">
                            <input class="form-control valid-input" type="text" name="mypageurl" value="${vip.mypageurl}" data-valid-rule="required"/>
                        </div>
                    </div>
				
					<div class="form-group">
                        <label class="col-xs-2 control-label">爱好</label>

                        <div class="col-xs-5">
                            <input class="form-control valid-input" type="text" name="favourite" value="${vip.favourite}" data-valid-rule="required"/>
                        </div>
                    </div>
					
				
                    <div class="form-group">
                        <label class="col-xs-2 control-label">创建时间</label>

                        <div class="col-xs-5">
                            <input class="form-control" readonly="readonly" id="createdate" name="createdate1" value="<fmt:formatDate value="${vip.createdate }" pattern="yyyy-MM-dd"/>" type="text" onClick="WdatePicker()"    />
                        </div>
                    </div>
                    <hr/>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">是否空间展示</label>

                        <div class="col-xs-5">
                            <select class="form-control" name="ifspace">
                                <option <c:if test="${vip.ifspace eq 0 }">selected="selected"</c:if> value="0">不展示</option>
                                <option <c:if test="${vip.ifspace eq 1 }">selected="selected"</c:if> value="1">展示</option>
                            </select>
                        </div>
                    </div>
					<!-- 
                    <div class="form-group">
                        <label class="col-xs-2 control-label">是否会员展示</label>

                        <div class="col-xs-5">
                            <select class="form-control" name="ifvipshow">
                                <option <c:if test="${vip.ifvipshow eq 0 }">selected="selected"</c:if> value="0">不展示</option>
                                <option <c:if test="${vip.ifvipshow eq 1 }">selected="selected"</c:if> value="1">展示</option>
                            </select>
                        </div>
                    </div>
					 -->
            
				<c:if test="${tips ne null }">
						<tr align="center">
							<td colspan="6"><font color="red">${tips }</font></td>
						</tr>
					</c:if>
                <div class="form-group">
                    <div class="container">
                        <button type="submit" class="col-xs-3 col-xs-offset-3 btn btn-primary">提交</button>
                    </div>
                </div>

            </form>
        </div>

   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/utilkcm/util.js"></script>
    <script type="text/javascript" src="<%=basePath %>res/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/location/location.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/user/updatevip.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/validation.js"></script>
</body>
</html>
