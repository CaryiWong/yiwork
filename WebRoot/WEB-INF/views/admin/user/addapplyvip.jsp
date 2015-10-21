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
    <title>添加会员</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
 <div class="row row-flow member-apply">
            <form class="form-horizontal col-xs-12" name="mainform" id="mainform" action="<%=basePath %>v2/admin/user/saveapplyvip"
				method="post" onsubmit="return validateform(this);">
                <div class="row">
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">会员号</label>

                        <div class="col-xs-9">
                            <input type="text" id="unum" name="unum" class="form-control valid-input"
                                   data-valid-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">姓名</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control valid-input"
                                id="name" name="name"  data-valid-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">性别</label>

                        <div class="col-xs-9">
                            <select  name="sex" class="form-control">
                                <option value="0">男</option>
                                <option value="1">女</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">邮箱</label>

                        <div class="col-xs-9">
                            <input id="email" name="email" type="text" class="form-control valid-input"
                                   data-valid-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group col-xs-4">
                        <label   class="col-xs-3 control-label">手机</label>

                        <div class="col-xs-9">
                            <input type="text"  id="tel" name="tel"  class="form-control valid-input"
                                   data-valid-rule="required"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">出生日期</label>

                        <div class="col-xs-9">
                            <input type="text" name="birth" value="${birth }" type="text" onClick="WdatePicker();" placeholder="出生日期" class="form-control valid-input"
                                   data-valid-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">身份证</label>

                        <div class="col-xs-9">
                            <input type="text" id="idnum" name="idnum" class="form-control valid-input"
                                   data-valid-rule="required"/>
                        </div>
                    </div>

                </div>
                <div class="row">
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">行业</label>

                        <div class="col-xs-9">
                            <select name="industry.id"  class="form-control valid-input"
                                    data-valid-rule="required">
                            <!-- 行业 -->
                            <c:forEach items="${industrys }" var="industry" varStatus="vs">
									<option value="${industry.id }">${industry.zname }</option>
							</c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">职业</label>

                        <div class="col-xs-9">
                            <select id="job" name="job.id" class="form-control valid-input"
                                    data-valid-rule="required">
                            </select>
                     
                           	输入职业<input type="text"  id="duties" name="duties"  class="form-control valid-input"
                                   data-valid-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">工作年限</label>

                        <div class="col-xs-9">
                            <select name="jobyear" class="form-control valid-input"
                                    data-valid-rule="required">
                            	<option value="-1">未知</option>
								<option value="0">1-2年</option>
								<option value="1">3-4年</option>
								<option value="2">5-6年</option>
								<option value="3">6年以上</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">办公需求</label>

                        <div class="col-xs-9">
                            <select  name="jobdemand" class="form-control valid-input"
                                    data-valid-rule="required">
                            	<option value="-1">未知</option>
								<option value="0">是</option>
								<option value="1">否</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">了解渠道</label>

                        <div class="col-xs-9">
                            <select name="channel" class="form-control valid-input"
                                    data-valid-rule="required">
                            	<option value="0" selected="selected">同事/朋友</option>
								<option value="1">媒体报道</option>
								<option value="2">参加活动</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">活动频率</label>

                        <div class="col-xs-9">
                            <select  name="actnum" class="form-control valid-input"
                                    data-valid-rule="required">
                            	<option value="-1">未知</option>
								<option value="0">1-2次/月</option>
								<option value="1">3-4次/月</option>
								<option value="2">5-6次/月</option>
								<option value="3">6次以上/月</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">喜欢活动类型</label>

                        <div class="col-xs-9">
                            <select name="acttype" class="form-control valid-input"
                                    data-valid-rule="required">
                          	    <option value="-1">未知</option>
								<option value="0">科技与互联网</option>
								<option value="1">艺术与设计</option>
								<option value="2">社会创新</option>
								<option value="3">文化生活</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="col-xs-2 control-label">兴趣爱好</label>

                        <div class="col-xs-10">
                            <textarea rows="4"  id="interests" name="interests" class="form-control valid-input" data-valid-rule="required"></textarea>
                        </div>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="col-xs-2 control-label">对一起的理解</label>

                        <div class="col-xs-10">
                            <textarea rows="4" id="understand" name="understand" class="form-control valid-input" data-valid-rule="required"></textarea>
                        </div>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="col-xs-2 control-label">希望获得</label>

                        <div class="col-xs-10">
                            <textarea rows="4" id="result" name="result"  class="form-control valid-input" data-valid-rule="required"></textarea>
                        </div>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="col-xs-2 control-label">建议</label>

                        <div class="col-xs-10">
                            <textarea rows="4" id="proposal" name="proposal" class="form-control valid-input" data-valid-rule="required"></textarea>
                        </div>
                    </div>
                    <div class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">缴费</label>

                        <div class="col-xs-9">
                            <select class="form-control valid-input"  id="charge" name="charge"
                                    data-valid-rule="required">
                            	<option value="1" selected="selected">是</option>
                            	<option value="0">否</option>
                            </select>
                        </div>
                    </div>
                    <div id="startDiv" class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">起始日期</label>

                        <div class="col-xs-9">
                            <input type="text" name="begin" type="text" onClick="WdatePicker()" placeholder="起始日期" class="form-control valid-input"
                                   data-valid-rule="required" onchange="addOneyear(this,1)">
                        </div>
                    </div>
                    <div id="endDiv" class="form-group col-xs-4">
                        <label class="col-xs-3 control-label">截止日期</label>
						<input type="hidden" id="chargetype" name="chargetype" value="1"/></td>
                        <div class="col-xs-9">
                            <input type="text" id="end" name="end" type="text" onClick="WdatePicker()" placeholder="截止日期" class="form-control valid-input"
                                   data-valid-rule="required">
                        </div>
                    </div>
                </div>
				<c:if test="${tips ne null }">
						<tr align="center">
							<td colspan="6">${tips }</td>
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
    <script type="text/javascript" src="<%=basePath%>res/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/user/addvip.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/js/page.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/validation.js"></script>
        
</body>
</html>
