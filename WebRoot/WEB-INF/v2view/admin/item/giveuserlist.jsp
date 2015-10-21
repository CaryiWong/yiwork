<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/res/common/taglib.jsp"%>
<%@ page language="java" import="cn.yi.gather.v20.entity.*"  %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String webBasePath=basePath+"v20/";
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>赠送商品</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
<div class="row row-flow">
        <div class="form-group">
            <button  class="btn btn-default btn-back">返回列表</button>
        </div>
                <form class="form-horizontal col-xs-12" name="mainform" id="mainform" action="<%=webBasePath%>admin/item/saveusergoods" method="post">
                    
                    <div class="form-group">
                        <label class="col-xs-2 control-label">赠送商品</label>

						<div class="col-xs-5 form-inline">
							<c:forEach items="${page.result}" var="sku" varStatus="vs">
								<c:if test="${vs.index mod 10 eq 0 }"></c:if>
								<c:set var="la" value="${sku.id}"></c:set>
								<div class="checkbox">
									<input name="item_class_id" value="${sku.id}" type="radio">${sku.name}</div>
							</c:forEach>
						</div>
					</div>
                    
                     <div class="form-group">
                        <label class="col-xs-2 control-label">接收会员号</label>
						<input type="hidden" name="pageSize" value="100">
                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="userid" name="userid" value="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">赠送数量</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control" id="cupnum" name="cupnum" value="1">
                        </div>
                    </div>
                    
                    <hr/>
                  
                   	  <button class="col-xs-1 col-xs-offset-1 btn btn-default btn-back">返回</button>
                   	  <c:if test="${tips ne null}">
		    			<tr align="center">
		    				<td colspan="4"><font color="red"">${tips}</font></td>
		    			</tr>
		    		</c:if>
		    		
	                 <button type="submit" onclick="javascript:document.mainform.submit()" class="col-xs-1 col-xs-offset-1 btn btn-primary">保存</button>
					
                </form>
    </div>


   <script type="text/javascript">
	var root="<%=basePath%>";
	</script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
</body>
</html>
