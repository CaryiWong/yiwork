<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>
<%@ page language="java" import="cn.yi.gather.v20.entity.*"  %>

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
                <form class="form-horizontal col-xs-12" name="mainform" id="mainform" action="<%=basePath%>v20/admin/activity/updateactivity"
				method="post" onsubmit="return validateformActivity(this);">
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
                            <p class="form-control-static"><a class="form-control-static" href="javascript:;">${activity.user.username}</a></p>
                        </div>
                    </div>
                    <%-- <div class="form-group">
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
                    </div> --%>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">费用</label>

                        <div class="col-xs-2">
                            <select class="form-control" name="cost">
                                <option value="1"  <c:if test="${activity.cost eq 1 }">selected="selected"</c:if>>收费</option>
                                <option value="0"  <c:if test="${activity.cost eq 0 }">selected="selected"</c:if>>免费</option>
                            </select>
                        </div>
                        
                        <div class="col-xs-2" id="shoufei">
                            <select class="form-control" name="pricetype">
                                <option value="0"  <c:if test="${activity.pricetype eq 0 }">selected="selected"</c:if>>统一价</option>
                                <option value="1"  <c:if test="${activity.pricetype eq 1 }">selected="selected"</c:if>>自定义价格</option>
                            </select>
                        </div>
                    </div>
                    
                    <input type="hidden" value="" name="pricekey">
                    <input type="hidden" value="" name="pricevalue">
                    <!-- 价格栏 -->
                    <%
                      Activity act  =(Activity)request.getAttribute("activity");
                      String pricekey=act.getPricekey();
                      String pricevalue=act.getPricevalue();
                      List<String> pricekeyList =  new ArrayList<String>();
                      List<String> pricevalueList =  new ArrayList<String>();
                      if(act.getPricetype()==1){
	                      String[] priceArr=pricekey.split(",");
	                      String[] priceValArr=pricevalue.split(",");
	                      for(String a:priceArr)
	                      {
	                    	  pricekeyList.add(a);
	                      }
	                      //value
	                      for(String b:priceValArr)
	                      {
	                    	  pricevalueList.add(b);
	                      }
                      }
                    %>
                  
                     <div>
                     	<!-- 统一价格 -->
                     	<div id="tongyi">
                     	 <div class="form-group">
	                        <label class="col-xs-2 control-label">统一价格</label>
	                        <div class="col-xs-1">
	                        	<input type="text" class="form-control"  name="pricevalueSingle" value="${activity.pricevalue}">
	                        </div>
                    	 </div>
                        </div>
                    </div>
                    
                    
                    <div class="form-group pay" id="zidingyi">
                        <label class="col-xs-2 control-label">自定义价格</label>
                        <div class="col-xs-10 pay-rows">
                              <%for(int i=0;i<pricekeyList.size();i++){ %>
                            <div class="row  pay-item">
                                <div class="col-xs-2">
                                    <input class="form-control valid-input" type="text"  name="pkey" value="<%=pricekeyList.get(i)%>" data-valid-rule="required"/>
                                </div>
                                <span class="pull-left">:</span>

                                <div class="col-xs-2">
                                    <input class="form-control valid-input" type="text" name="pval" value="<%=pricevalueList.get(i)%>" data-valid-rule="required,number"/>
                                </div>
                                <%if(i==0){ %>
                                <button class="col-xs-1 btn btn-primary btn-sm pay-add">
                                    <i class="glyphicon glyphicon-plus"></i>
                                </button>
                                <%}else{ %>
                                 <button class="btn btn-link text-danger btn-sm pay-remove">
                                    <i class="glyphicon glyphicon-remove text-danger"></i>
                                </button>
                                <%} %>
                            </div>
                            <%}if(pricekeyList==null || pricekeyList.size()==0){ %>
                             <div class="row">
                                <div class="col-xs-2">
                                    <input class="form-control valid-input" name="pkey"  type="text" data-valid-rule="required"/>
                                </div>
                                <span class="pull-left">:</span>

                                <div class="col-xs-2">
                                    <input class="form-control valid-input"  name="pval" type="text" data-valid-rule="required,number"/>
                                </div>
                                <button class="col-xs-1 btn btn-primary btn-sm pay-add">
                                    <i class="glyphicon glyphicon-plus"></i>
                                </button>
                            </div>
                            <%} %>
                        </div>
                        
                    </div>

                    <hr/>
                    
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
                    
                    
                    <%-- <div class="form-group">
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
                    </div> --%>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">联系方式</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" name="tel" value="${activity.tel}">
                        </div>
                    </div>
                   <%--  <div class="form-group">
                        <label class="col-xs-2 control-label">简介</label>

                        <div class="col-xs-5">
                            <textarea rows="4" class="form-control" id="summary" name="summary" >${activity.summary}</textarea>
                        </div>
                    </div> --%>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">开始时间</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="opendate" name="open" value="<fmt:formatDate value="${activity.opendate }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="开启时间">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">结束时间</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="enddate" name="end" value="<fmt:formatDate value="${activity.enddate }" pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'});" placeholder="结束时间">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-5 col-xs-offset-2">
                            <%-- <a href="<%=basePath%>v20/admin/activity/getactsignlist?pageSize=12&activityid=${activity.id}" class="btn btn-primary col-xs-12" >报名列表</a>
                             --%>
                             <a href="<%=basePath%>v20/admin/activity/activitysignlist?pageSize=12&activityid=${activity.id}&type=web" class="btn btn-primary col-xs-12" >报名列表</a>
                        </div>
                    </div>

                   	  <button class="col-xs-1 col-xs-offset-1 btn btn-default btn-back">返回</button>
                   	  <c:if test="${tips ne null}">
		    			<tr align="center">
		    				<td colspan="4"><font color="red"">${tips}</font></td>
		    			</tr>
		    		</c:if>
		    		
		    	 	<!-- checktype=0;//审核状态 0 待审核 ，1 通过审核 ，2 审核失败
		    	 		 onsale = 0;//活动上架(0)/下架(1) 
		    	    -->
		    		
                    <c:if test="${activity.checktype ne 1 && activity.onsale eq 0}">
	                   <button type="submit" class="col-xs-1 col-xs-offset-1 btn btn-primary">保存</button>
						<button type="button" class="col-xs-1 col-xs-offset-1 btn btn-success" onclick="verify(1)">审核</button>
	                    <button type="button" class="col-xs-1 col-xs-offset-1 btn btn-danger" onclick="verify(2);">驳回</button>
                    </c:if>
        
                    <c:if test="${ activity.onsale eq 1}">  <!-- onsale=1 是下架的 -->
                    	    <button type="submit" class="col-xs-1 col-xs-offset-1 btn btn-primary">保存</button>
							<button type="button" class="col-xs-1 col-xs-offset-1 btn btn-success" onclick="verify(1)">再次审核</button>
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
    <script type="text/javascript" src="<%=basePath%>res/scripts/activity/updateActivity.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
