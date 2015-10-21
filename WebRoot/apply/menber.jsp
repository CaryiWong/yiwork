<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="cn.yi.gather.v20.entity.*"%>
<%@ page language="java" import="com.common.*"%>
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
<script type="text/javascript">
	var root="<%=basePath%>";
</script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">
    <title>会员申请-一起开工社区</title>
	<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
    <%--<link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>--%>
    <%--<link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>--%>
    <%--<link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>--%>
    <%--<link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">--%>
    <!--    datepicker      -->
    <%--<link rel="stylesheet" href="<%=basePath %>styles/components/datepicker/classic.css"/>--%>
    <!--    datepicker      -->
    <%--<link rel="stylesheet" href="<%=basePath %>styles/pages/apply.css"/>--%>
     <%--<link rel="stylesheet" href="<%=basePath %>styles/pages/activities.css"/>--%>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/applyMember.css"/>

     <style>
     	@media screen and (max-width:720px){
     			.apply-dialog, .apply-dialog1{
     		width: 95%;
     	}
     	
     	.apply-dialog .apply-dialog-buttons, 
     	.apply-dialog1 .apply-dialog-buttons{
     		width: 80%;
     		margin: 0 auto;  
     	}
     	}
     	
     </style>	
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/ajaxfileupload.js"></script>
    
    <script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>   
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->
    
    <%
    	String platform=request.getParameter("type");
		String app_user_id=request.getParameter("userid");
    	Object objUser = session.getAttribute(R.User.SESSION_USER);
    	if(objUser!=null && objUser!=""){
    		User obj=(User)objUser;
    		app_user_id=obj.getId();
    	}
	%>
<script type="text/javascript">
	var app_user_id="<%=app_user_id%>"; 
	var platform="<%=platform%>";
</script>
    
    
<!--    content   -->
    <div class="container">
        <form class="apply-form apply-form-member" novalidate>
            <div class="form-title">
                会员申请表
            </div>
            <div class="form-control-area">

            	<div class="form-control nickname">
                    <label class="form-control-label">
                        昵称<span class="form-control-label-required">＊</span>
                    </label>

                    <div class="form-control-input">
                        <input id="nickname" class="form-control-text text valid-input" type="text"
                               data-valid-rule="required"
                                data-valid-text='{"required":"请填写昵称"}'/>
                    </div>
                </div>

                <div class="form-control-group">
                    <div class="form-control name fl">
                        <label class="form-control-label">
                            姓名<span class="form-control-label-required">＊</span>
                        </label>

                        <div class="form-control-input">
                            <input id="menberName" class="form-control-text text valid-input" type="text"
                                   data-valid-rule="required"
                                   data-valid-text='{"required":"请填写姓名"}'/>
                        </div>
                    </div>

                    <div class="form-control sex fl">
                        <label class="form-control-label form-control-select-label">
                            性别<span class="form-control-label-required">＊</span>
                        </label>

                        <div class="form-control-input">
                          <div class="form-control-customSelect">

                            <select id="menberSex" class="form-control-select">
                                <option value="0">男</option>
                                <option value="1">女</option>
                            </select>
                             <div class="form-control-customSelect-value">

                             </div>
                             <div class="form-control-customSelect-button">
                                    <div class="form-control-customSelect-arrows"></div>
                             </div>
                          </div>

                        </div>
                    </div>
                </div>



                <div class="form-control job">
                    <label class="form-control-label form-control-select-label">
                        职业<span class="form-control-label-required">＊</span>
                    </label>

                    <div class="form-control-input">
                    	<div class="form-control-customSelect">
                        <select id="menberBus"  class="form-control-select business valid-input"
                                data-valid-group="business">
                            <option value="">请选择行业</option>
                        </select>

                         <div class="form-control-customSelect-value">

                         </div>
                         <div class="form-control-customSelect-button">
                             <div class="form-control-customSelect-arrows"></div>
                         </div>

                         </div>

                        <div class="form-control-customSelect">

                        <select id="menberJob" class="form-control-select job valid-input"
                                data-valid-group="business"
                                data-valid-rule="required"
                                data-valid-text='{"required":"请选择职业"}'>
                            <option  value="">请选择职业</option>
                        </select>

                         <div class="form-control-customSelect-value">

                         </div>
                         <div class="form-control-customSelect-button">
                             <div class="form-control-customSelect-arrows"></div>
                         </div>

                         </div>

                    </div>
                </div>

                <div class="form-control constellation hide">
                    <label class="form-control-label">
                        星座
                    </label>

                    <div id="menberXingZuo" class="form-control-value">
    					请选择
                    </div>
                </div>

                <div class="form-control birth">
                    <label class="form-control-label">
                        出生日期<span class="form-control-label-required">＊</span>
                    </label>

                    <div class="form-control-input">
                        <input id="menberBirthDate" class="form-control-text text datePicker valid-input" type="text"
                        		data-datepicker-config='{"selectYears":60,"selectMonths":true,"max":true}'
                                data-valid-rule="required" data-value="1980-01-01"
                                data-datepicker-type="age"
                                data-valid-text='{"required":"请填写出生日期"}'/>
                        <i class="add-icon-calendar"></i>
                    </div>
                </div>
    		<!--
                <div class="form-control applyTime">
                    <label class="form-control-label">
                        申请日期
                    </label>

                    <div class="form-control-input">
                        <input id="menberApplyDate" class="form-control-text text datePicker valid-input" type="text"
                                data-valid-rule="required"
                                data-valid-text='{"required":"请填写申请日期"}'/>
                        <i class="add-icon-calendar"></i>
                    </div>
                </div>
    		 -->
    		   <div class="form-control idtype">
                    <label class="form-control-label">
                        证件类型<span class="form-control-label-required">＊</span>
                    </label>
                   <div class="form-control-customSelect">
                            <select id="menberIdType" class="form-control-select">
                                <option value="ID">二代身份证</option>
                                <option value="HKM">港澳通行证</option>
                                <option value="TW">台湾通行证</option>
                                <option value="PP">护照</option>
                            </select>
                             <div class="form-control-customSelect-value">
                             </div>
                             <div class="form-control-customSelect-button">
                                    <div class="form-control-customSelect-arrows"></div>
                             </div>
                          </div>
                </div>

                <div class="form-control id">
                    <label class="form-control-label">
                        证件号码<span class="form-control-label-required">＊</span>
                    </label>

                    <div class="form-control-input">
                        <input id="menberNum" class="form-control-text text valid-input" type="text"
                               data-valid-rule="required,idCard"
                               data-valid-text='{"required":"请填写证件号码","idCard":"请填写正确的所选证件号码"}'/>
                    </div>
                </div>
                <div class="form-control-group">
                    <div class="form-control email fl">
                        <label class="form-control-label">
                            邮箱<span class="form-control-label-required">＊</span>
                        </label>

                        <div class="form-control-input">
                            <input id="menberEmail" class="form-control-text text valid-input" type="text"
                                    data-valid-rule="required,email"
                                    data-valid-text='{"required":"请填写邮箱","email":"请填写正确的邮箱"}'/>
                        </div>
                    </div>

                    <div class="form-control phone fl">
                        <label class="form-control-label">
                            手机<span class="form-control-label-required">＊</span>
                        </label>

    					<div class="form-control-tip">(*手机号是你付款的唯一凭证)</div>
                        <div class="form-control-input">

                            <input id="menberTel" class="form-control-text text valid-input" type="text"
                                    data-valid-rule="required,phone"
                                    data-valid-text='{"required":"请填写手机号码","phone":"请填写正确的手机号码"}'/>
                        </div>
                    </div>
                </div>

                <div class="form-control work hide">
                    <label class="form-control-label form-control-select-label">
                        工龄<span class="form-control-label-required">＊</span>
                    </label>
    				<!-- select -->
                    <div class="form-control-input">
                    	<div class="form-control-customSelect">

                        <select id="menberJobYear" class="form-control-select">
                            <option value="0">1~2年</option>
                            <option value="1">3~4年</option>
                            <option value="2">5~6年</option>
                            <option value="3">6年以上</option>
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

                <div class="form-control interest">
                    <label class="form-control-label form-control-textarea-label">
                        个人介绍<span class="form-control-label-required">＊</span>
                    </label>

                    <div class="form-control-input">
                        <textarea id="menberAiHao" class="form-control-textarea text valid-input"
                               data-valid-rule="required"
                                data-valid-text='{"required":"请填写个人介绍"}'></textarea>
                    </div>
                </div>

                <!--    表单 高级内容     -->
                <div class="form-control form-control-advance activityType">
                    <label class="form-control-label form-control-select-label">
                        喜好的活动类型<span class="form-control-label-required">＊</span>
                    </label>

                    <div class="form-control-input">
                          <div class="form-control-customSelect">
                        	<select id="menberXiHuan" class="form-control-select">
                            	 <option value="其他">其他</option>
    							 <option value="科技与互联网">科技与互联网</option>
    							 <option value="艺术与设计">艺术与设计</option>
    							 <option value="社会创新">社会创新</option>
    							 <option value="文化生活">文化生活</option>
                        	</select>
                        	<div class="form-control-customSelect-value">
                        	</div>
                        	<div class="form-control-customSelect-button">
                            	<div class="form-control-customSelect-arrows"></div>
                         	</div>
                         </div>
                    </div>
                </div>

                <div class="form-control form-control-advance activityFrequency hide">
                    <label class="form-control-label form-control-select-label">
                        参与活动的频率<span class="form-control-label-required">＊</span>
                    </label>

                    <div class="form-control-input">
                    <!-- select -->
                    	  <div class="form-control-customSelect">
                        <select id="menberJoinType" class="form-control-select">
                            <option value="0">1~2次／月</option>
                            <option value="1">3~4次／月</option>
                            <option value="2">5~6次／月</option>
                            <option value="3">6次以上／月</option>
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

                <div class="form-control form-control-advance together">
                    <label class="form-control-label form-control-select-label">
                        是否有在“一起”办公的需求<span class="form-control-label-required">＊</span>
                    </label>

                    <div class="form-control-input">
                    	<!-- select -->
                    	 <div class="form-control-customSelect">

                        <select id="menberJoinUsType" class="form-control-select">
                            <option value="0">是</option>
                            <option value="1">否</option>
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

                <div class="form-control form-control-advance channel">
                    <label class="form-control-label form-control-select-label">
                        你通过什么渠道知道“一起”<span class="form-control-label-required">＊</span>
                    </label>

                    <div class="form-control-input">
                    <!-- select -->
                    	 <div class="form-control-customSelect">

                        <select id="menberKnowUsType" class="form-control-select">
                            <option value="0">朋友／同事</option>
                            <option value="1">媒体报道</option>
                            <option value="2">参加活动(活动名字)</option>
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
    			<input type="hidden" id="payorderid">
    			<input type="hidden" id="payorderinfo">
                <div class="form-control form-control-advance understand hide">
                    <label class="form-control-label form-control-textarea-label">
                        你对“一起”的理解是：
                    </label>

                    <div class="form-control-input">
                        <textarea id="menberLiJie" class="form-control-textarea text"
                             ></textarea>
                    </div>
                </div>

                <div class="form-control form-control-advance get hide">
                    <label class="form-control-label form-control-textarea-label">
                        你希望在“一起”获得：
                    </label>

                    <div class="form-control-input">
                        <textarea id="menberHuoDe" class="form-control-textarea text"
                            ></textarea>
                    </div>
                </div>
                <div class="form-control form-control-advance comment hide">
                    <label class="form-control-label form-control-textarea-label">
                        你对“一起”的建议是：
                    </label>

                    <div class="form-control-input">
                        <textarea id="menberJianYi" class="form-control-textarea text "
                             ></textarea>
                    </div>
                </div>

                 <div class="form-control deal">
                    <label class="form-control-deal-checkbox ">
                        <input type="checkbox" class="valid-input" name="deal"
                               checked
                               data-valid-rule="required"
                               data-valid-group="deal"
                               data-valid-text='{"required":"请同意会员协议"}'/>
                            阅读并同意
                        <a href="../pages/deal.html" target="_blank">会员协议</a>
                    </label>
                </div>


                <div class="form-control form-control-buttonGroup">

                    <button type="submit" class="form-button button form-button-submit">提交</button>
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
<script type="text/javascript" src="<%=basePath %>scripts/apply/menber.js"></script>

<div class="apply-dialog hide">
	<p class="showBox_tit">
		亲爱的用户，请确认您输入的以下注册信息：填写完资料后，请继续支付环节，如不慎退出，可通过填写的用户名和手机号登陆（其中手机号作为初始密码），并继续支付流程
	</p>

    <p class="apply-dialog-text">你的登录账号：<font  style="color: red;padding: 25px 0 0 60px" id="loginEmail">234@qq.com</font>
    	
    </p>
    
    <p class="apply-dialog-text">绑定的手机号：<font  style="color: red;padding: 25px 0 0 60px" id="loginTel">18665584885</font>
    	
    </p>
    
    <p class="apply-dialog-text">你的登录密码：<font style="color: red;padding: 25px 0 0 60px" id="loginTel">即你的手机号</font>
    
    </p>
    
    <p class="apply-dialog-text">需要注意事项：<font style="color: red;padding: 25px 0 0 60px" id="loginTel">30分钟内完成支付，否则要重新下单哦</font>
    
    </p>
    
    <div class="apply-dialog-buttons container clearfix">
        <button class="apply-dialog-cancel apply-dialog-button button gray fl">取消</button>
        <a id="confirmButton" class="apply-dialog-sure apply-dialog-button button fr">确认</a>
    </div>
</div>


<!-- 等待付款页面 -->
<div class="apply-dialog1 hide">
	<p class="showBox_tit">
		现在支付
	</p>

    <p class="apply-dialog-text1"><strong>支付完成前请不要关闭此窗口。</strong>完成支付后请根据您的操作结果点击下面的按钮进行操作。
    	
    </p>
    
    <div class="apply-dialog-buttons container clearfix">
        <button id="paysuccess"  class="apply-dialog-cancel apply-dialog-button button green fl">已经支付</button>
        <a id="paywrong" class="apply-dialog-sure apply-dialog-button button green fr">支付出问题</a>
    </div>
</div>

</body>
</html>
    
    