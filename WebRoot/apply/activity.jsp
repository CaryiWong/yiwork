<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<!--[if IE 8]>
<html lang="zh-CN" class="no-js lt-ie9"><![endif]-->
<!--[if gt IE 8]><!-->
<html lang="zh-CN" class="no-js"><!--<![endif]-->
<head>
<%
String event_id=request.getParameter("event_id");
%>
<script type="text/javascript">
	var root="<%=basePath%>";
	var event_id="<%=event_id%>";  //活动ID
</script>
 
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">
    <title>活动报名-一起开工社区</title>
	<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">
    <!--    datepicker      -->
    <link rel="stylesheet" href="<%=basePath %>styles/components/datepicker/classic.css"/>
    <!--    datepicker      -->
    <link rel="stylesheet" href="<%=basePath %>styles/pages/apply.css"/>
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/ajaxfileupload.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
    
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->
    
<!--    content   -->
<div class="container">
    <form class="apply-form apply-form-activity" novalidate>
        <div id="eventTitle" class="apply-activity-name">
            邓飞全国演唱会
        </div>
        <div class="form-title">
            活动报名表
        </div>
        <div class="form-control-area">
                <div class="form-control name">
                    <label class="form-control-label">
                        姓名<span class="form-control-label-required">＊</span>
                    </label>

                    <div class="form-control-input">
                        <input id="userName" class="form-control-text text valid-input" type="text"
                               data-valid-rule="required"
                               data-valid-text='{"required":"请填写姓名"}'/>
                    </div>
                </div>

                <div class="form-control sex">
                    <label class="form-control-label form-control-select-label">
                        性别<span class="form-control-label-required">＊</span>
                    </label>
                
                	 
                    <div class="form-control-input">
					
					<!-- select -->
                	<div class="form-control-customSelect">
                        <select id="userSex" class="form-control-select">
                            <option value="0">男</option>
                            <option value="1">女</option>
                        </select>
                   			 <div class="form-control-customSelect-value">

                            </div>
                            <div class="form-control-customSelect-button">
                                <div class="form-control-customSelect-arrows"></div>
                            </div>
                        </div>
                 <!-- end select -->  
                    </div>
                </div>

            <div class="form-control job">
                <label class="form-control-label form-control-select-label">
                    职业<span class="form-control-label-required">＊</span>
                </label>

                <div class="form-control-input">
					<!-- select -->
                	<div class="form-control-customSelect">
                	
                    <select id="userBus" class="form-control-select business valid-input"
                            data-valid-group="business">
                        <option value="">请选择行业</option>
                    </select>
                    
                     <div class="form-control-customSelect-value">

                            </div>
                            <div class="form-control-customSelect-button">
                                <div class="form-control-customSelect-arrows"></div>
                            </div>
                        </div>
                 <!-- end select -->  
                 
                 <!-- select -->
                	<div class="form-control-customSelect">
                	
                    <select id="userJob" class="form-control-select job valid-input"
                            data-valid-group="business"
                            data-valid-rule="required"
                            data-valid-text='{"required":"请选择职业"}'>
                        <option value="">请选择职业</option>
                    </select>
                    
                     <div class="form-control-customSelect-value">

                            </div>
                            <div class="form-control-customSelect-button">
                                <div class="form-control-customSelect-arrows"></div>
                            </div>
                        </div>
                 <!-- end select -->  
                </div>
            </div>


            <div class="form-control phone fl">
                <label class="form-control-label">
                    手机<span class="form-control-label-required">＊</span>
                </label>

                <div class="form-control-input">
                    <input id="userTel" class="form-control-text text valid-input" type="text"
                           data-valid-rule="required,phone"
                           data-valid-text='{"required":"请填写手机号码","phone":"请填写正确的手机号码"}'/>
                </div>
            </div>


            <div class="form-control email fl">
                <label class="form-control-label">
                    邮箱<span class="form-control-label-required">＊</span>
                </label>

                <div class="form-control-input">
                    <input id="userEmail" class="form-control-text text valid-input" type="email"
                           data-valid-rule="required,email"
                           data-valid-text='{"required":"请填写邮箱","email":"请填写正确的邮箱"}'/>
                </div>
            </div>

            <div class="form-control vip">
                <label class="form-control-label form-control-select-label">
                    付费会员
                </label>


                <div class="form-control-input">
                    
                    <!-- select -->
                	<div id="ifVipDiv" class="form-control-customSelect">
                	
                   	<select id="ifVip" class="form-control-select">
                        <option value="1">是</option>
                    </select>
                    
                     <div class="form-control-customSelect-value">
                            </div>
                            <div class="form-control-customSelect-button">
                                <div class="form-control-customSelect-arrows"></div>
                            </div>
                        </div>
                 <!-- end select -->  
                 
                    <a class="form-control-link" href="<%=basePath %>apply/menber.jsp">您还不是会员！会员可享受八折优惠！</a>
                </div>
            </div>


            <div class="form-control reason">
                <label class="form-control-label form-control-textarea-label">
                    能告诉小Yi你为什么参加这次活动吗？<span class="form-control-label-required">＊</span>
                </label>

                <div class="form-control-input">
                    <textarea id="joinReason" class="form-control-textarea text valid-input"
                           data-valid-rule="required"
                           data-valid-text='{"required":"你就告诉人家嘛"}'></textarea>
                </div>
            </div>

            <div class="form-control form-control-buttonGroup">
                <input  type="submit" class="form-button button form-button-submit" value="提交" />
                <input type="button" class="form-button button form-button-cancel gray" value="取消" />
            </div>

        </div>
    </form>
</div>
<!--    content   -->

	<!-- footer -->
  	<jsp:include page="/pages/footer.jsp" flush="true"></jsp:include>
	<!--    footer    -->

<script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/datepicker/picker.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/customUI/customUI.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/validation/validation.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/pages/apply.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/apply/joinEvent.js"></script>

</body>
</html>
    
