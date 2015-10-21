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
<div class="row row-flow">
        <div class="form-group">
            <button  class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" name="mainform" id="mainform" action="<%=basePath%>admin/activity/updateactivity"
				method="post" onsubmit="return validateform(this);">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">标题</label>

                        <div class="col-xs-5">
                        	<input type="hidden" value="${activity.id}" name="activityid" id="activityid">
                        	<input type="hidden" value="${activity.id}" name="id" id="id">
                            <input type="text" class="form-control" id="title" name="title" value="${activity.title}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">发起者</label>

                        <div class="col-xs-5">
                            <p class="form-control-static"><a class="form-control-static" href="javascript:;">${activity.usernickname}</a></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">领域</label>

                        <div class="col-xs-5 form-inline">
                        	<!-- 标签 -->
                         <c:forEach items="${labels}" var="label" varStatus="vs">
								<c:if test="${vs.index mod 10 eq 0 }"></c:if>
								<c:set var="la" value="${label.id}"></c:set>
								<div class="checkbox">
                            		<label>
                              			<input name="groups" value="${label.id}" type="checkbox" <c:if test="${fn:contains(labelList,la) }">checked="checked"</c:if> />${label.zname }
                            		</label>
                            	</div>
						</c:forEach>      
                            
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">费用</label>

                        <div class="col-xs-2">
                            <select class="form-control" name="cost">
                                <option value="1"  <c:if test="${activity.cost eq 1 }">selected="selected"</c:if>>收费</option>
                                <option value="0"  <c:if test="${activity.cost eq 0 }">selected="selected"</c:if>>免费</option>
                            </select>
                        </div>
                        
                        <div class="col-xs-1">
                       		<input type="text" class="form-control" id="price" name="price" value="${activity.price}">
                       </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">最高人数</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="maxnum" name="maxnum" value="${activity.maxnum}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">出席人数</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="joinnum" name="joinnum" value="${activity.joinnum}" readonly="readonly">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">报名人数</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="signnum" name="signnum" value="${activity.signnum}" readonly="readonly">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">地点</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="address" name="address" value="${activity.address}">
                        </div>
                    </div>
                    
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">个性化</label>
                        <div class="col-xs-5">
                            <select class="form-control" name="acttype">
                                <option value="0"  <c:if test="${activity.acttype eq 0 }">selected="selected"</c:if>>常规</option>
                                <option value="1"  <c:if test="${activity.acttype eq 1 }">selected="selected"</c:if>>个性化</option>
                            </select>
                        </div>
                    </div>
	
					<div class="form-group"  id="specialType">
                        <label class="col-xs-2 control-label">个性化参数</label>

                        <div class="col-xs-1">
                           按钮 <input type="text" class="form-control" id="acttypetitle" name="acttypetitle" value="${activity.acttypetitle}">
                        </div>
                        
                        <div class="col-xs-4">
                           连接 <input type="text" class="form-control" id="acttypeurl" name="acttypeurl" value="${activity.acttypeurl}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">联系方式</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" name="tel" value="${activity.tel}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">简介</label>

                        <div class="col-xs-5">
                            <textarea rows="4" class="form-control" id="summary" name="summary" >${activity.summary}</textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">开始时间</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="opendate" name="open" value="<fmt:formatDate value="${activity.opendate1 }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="开启时间">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">结束时间</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="enddate" name="end" value="<fmt:formatDate value="${activity.enddate1 }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="结束时间">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-5 col-xs-offset-2">
                            <a href="<%=basePath%>admin/activity/getactsignlist?pageSize=12&activityid=${activity.id}" class="btn btn-primary col-xs-12" >报名列表</a>
                        </div>
                    </div>

                   	  <button class="col-xs-1 col-xs-offset-1 btn btn-default btn-back">返回</button>
                   	  <c:if test="${tips ne null}">
		    			<tr align="center">
		    				<td colspan="4"><font color="red"">${tips}</font></td>
		    			</tr>
		    		</c:if>
		    		
                    <c:if test="${activity.checktype ne 1 }">
	                    <button type="submit" class="col-xs-1 col-xs-offset-1 btn btn-primary">保存</button>
						<button type="button" class="col-xs-1 col-xs-offset-1 btn btn-success" onclick="verify(1)">审核</button>
	                    <button type="button" class="col-xs-1 col-xs-offset-1 btn btn-danger" onclick="verify(2);">驳回</button>
                    </c:if>
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
