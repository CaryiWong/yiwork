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
    <title>新增需求</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
      <div class="row row-flow">
        <div class="form-group">
            <button  class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" role="form" action="<%=basePath%>admin/demand/savedemand"
				method="post" onsubmit="return validateform(this);">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">标题</label>

                        <div class="col-xs-5">
                            <input type="text" id="title" name="title" value="${demand.title }" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">发布者</label>
                        <div class="col-xs-5">
                        	<input type="hidden" name="publishnickname" id="publishnickname" value="${session_user.basicinfo.nickname}">
                        	<input type="hidden" name="publishuser" id="publishuser" value="${session_user.uuid}">
                            <p class="form-control-static"><a class="form-control-static" href="javascript:;">${session_user.basicinfo.nickname }</a></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">需求类型</label>
                        <div class="col-xs-5">
                        	<input type="hidden" id="kcmType" value="${demand.demandstype}">
                            <select class="form-control" name="demandstype">
                                <option value="-1"<c:if test="${demand.demandstype eq -1}">selected="selected"</c:if>>需求类型</option>
                                <option value="0" <c:if test="${demand.demandstype eq 0}">selected="selected"</c:if>> 寻人</option>
                                <option value="1" <c:if test="${demand.demandstype eq 1}">selected="selected"</c:if>>视频制作</option>
                                <option value="2" <c:if test="${demand.demandstype eq 2}">selected="selected"</c:if>>发起活动</option>
                                <!-- <option value="3" <c:if test="${demand.demandstype eq 3}">selected="selected"</c:if>>其他</option> -->
                            </select>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">领域</label>
						<!-- 标签 -->
                        <div class="col-xs-5 form-inline">
                             <c:forEach items="${labels}" var="label" varStatus="vs">
								<c:if test="${vs.index mod 10 eq 0 }"></c:if>
								<c:set var="la" value="${label.id}"></c:set>
								<div class="checkbox">
                            		<label>
                              			<input name="areas" value="${label.id}" type="checkbox" <c:if test="${fn:contains(labelList,la) }">checked="checked"</c:if> />${label.zname }
                            		</label>
                            	</div>
						</c:forEach>
                        </div>
                    </div>

					<!--    寻人类型    -->
                   
                    <div id="findsDiv" class="form-group">
                        <label class="col-xs-2 control-label">寻人类型</label>
                        <div class="col-xs-5">
                            <select class="form-control" name="findid">
                             	<c:forEach items="${finds}" var="fi" varStatus="fs">
                                	<option value="${fi.id}" <c:if test="${fn:contains(findList,fi.id) }">selected="selected"</c:if>>${fi.zname}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
					
                    <!--    视频需求属性      -->
                   
                    <div id="videoDiv" class="form-group">
                        <label class="col-xs-2 control-label">视频制作类型</label>
						<!-- 制作类型 -->
                        <div class="col-xs-5">
                            <select class="form-control" id="videoType" name="makeid">
                             	<c:forEach items="${makes}" var="ma" varStatus="ms">
                                	<option value="${ma.id}" <c:if test="${fn:contains(makeList,ma.id) }">selected="selected"</c:if>>${ma.zname}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    
                    <!--  -->
                 	 <div id="videoImg" class="form-group">
                        <label class="col-xs-2 control-label">视频制作封面</label>
						<!-- 制作类型 -->
                        <div class="col-xs-5">
                           	<input type="file" name="img" id="coverImg" onchange="sendFile()">
                           	<input type="hidden" name="cover" id="cover" value="0">
                        </div>
                    </div>
                    
                    <div id="videoUrl" class="form-group">
                        <label class="col-xs-2 control-label">视频制作地址</label>
						<!-- 制作类型 -->
                        <div class="col-xs-5">
                           	<input type="text" name="mediaurl" class="form-control">
                        </div>
                    </div>
                    
                    <!--    活动形式      -->
                   
                    <div id="huodongxingshi" class="form-group">
                        <label class="col-xs-2 control-label">活动形式</label>

                        <div class="col-xs-5">
                            <select class="form-control" name="formid">
                             	<c:forEach items="${forms}" var="de" varStatus="ts">
                                	<option value="${de.id}" <c:if test="${fn:contains(formList,de.id) }">selected="selected"</c:if>>${de.zname}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <!-- 面向人群 -->
                    <div id="mianxiangrenqun" class="form-group">
                        <label class="col-xs-2 control-label">面向人群</label>

                        <div class="col-xs-5">
                            <select class="form-control" name="groupid">
                            	<c:forEach items="${groups}" var="fr" varStatus="fs">
                                	<option value="${fr.id}" <c:if test="${fn:contains(groupList,fr.id) }">selected="selected"</c:if>>${fr.zname}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div id="joinNum" class="form-group">
                        <label class="col-xs-2 control-label">希望参加人数</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" name="hopepeoples" value="${demand.hopepeoples}">
                        </div>
                    </div>
                    <div id="plantime" class="form-group">
                        <label class="col-xs-2 control-label">计划时间</label>

                        <div class="col-xs-5">
                        	<input type="text" class="form-control" id="plandatetime" name="plantime" value="<fmt:formatDate value="${demand.publishdate1 }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="计划时间">
                        </div>
                    </div>
                   

					<!-- 是否审核标志 -->
					<input type="hidden" id="ischeckType" name="ischeckType" value="0">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">简介</label>

                        <div class="col-xs-5">
                            <textarea rows="4" class="form-control" name="texts">${demand.texts }</textarea>
                        </div>
                    </div>

                   
                    <button class="col-xs-2 col-xs-offset-1 btn btn-default btn-back">返回</button>
	               <button type="submit" class="col-xs-2 col-xs-offset-1 btn btn-primary">保存</button>
						 
                     
                </form>
    </div>
   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/demand/addDemand.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/validation.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
</body>
</html>
