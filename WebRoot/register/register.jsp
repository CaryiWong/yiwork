<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="zh-CN" >
<head>
<script>
var root="<%=basePath%>";
</script>
    <meta charset="utf-8">
    <title>注册</title>
    <!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/sign_login.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">
    
  	<script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
    
</head>
<body class="kcm">
     <jsp:include page="/pages/header.jsp" flush="false"></jsp:include>

   
    <!--    content   -->
    <section class="sign container ">
        <div class="sign-step">
            <div class="sign-step-item base active">
                <span class="sign-step-index">01</span>
                <span class="sign-step-text">基本信息</span>
            </div>
            <div class="sign-step-item interest">
                <span class="sign-step-index">02</span>
                <span class="sign-step-text">选择兴趣</span>
            </div>
        </div>
        <form class="sign-form clearfix" novalidate >
            <div class="sign-form-step  base">
                <div class="title">欢迎加入一起</div>
                <div class="form-control email">
                    <div class="form-control-label">
                        邮箱
                    </div>
                    <div class="form-control-input">
                        <input placeholder="邮箱" class="form-control-text valid-input" type="email" name="email"
                               data-valid-rule="required,email"
                               data-valid-text='{"required":"请填写邮箱","email":"请填写正确的邮箱格式"}'/>
                    </div>
                </div>
                <div class="form-control name">
                    <div class="form-control-label">
                        昵称
                    </div>
                    <div class="form-control-input">
                        <input placeholder="昵称" class="form-control-text valid-input" type="text" name="name"
                               data-valid-rule="required"
                               data-valid-text='{"required":"请填写昵称"}'/>
                    </div>
                </div>
                <div class="form-control address">
                    <div class="form-control-label">
                        所在地
                    </div>
                    <div class="form-control-input">
                <!-- select -->
                	 <div class="form-control-customSelect">
                        <select name="province" class="form-control-select province valid-input"
                                data-valid-group="location">
                            <option value="">省份</option>
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
                        <select name="city" class="form-control-select city valid-input"
                                data-valid-rule="required" data-valid-group="location"
                                data-valid-text='{"required":"请选择地区信息"}'>
                            <option value="">城市</option>
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
                <div class="form-control password">
                    <div class="form-control-label">
                        密码
                    </div>
                    <div class="form-control-input">
                        <input class="form-control-text password valid-input" placeholder="密码" type="password"
                               name="password"
                               data-valid-rule="required,min" data-valid-min="6"
                               data-valid-text='{"required":"请填写密码","min":"密码个数不能少于六位"}'/>
                    </div>
                </div>
                <div class="form-control passwordAgain">
                    <div class="form-control-label">
                        确认密码
                    </div>
                    <div class="form-control-input">
                        <input class="form-control-text valid-input" placeholder="再输入一次密码" type="password"
                               name="password_again"
                               data-valid-rule="required,same" data-valid-same=".form-control-text.password"
                               data-valid-text='{"required":"请再一次填写密码","same":"两次填写的密码不一致"}'/>

                    </div>
                </div>
                <div class="form-buttonArea clearfix">
                    <button class="form-button button gray">取消</button>
                    <input type="button" class="form-button black button" value="下一步" />
                </div>
            </div>

            <div class="sign-form-step  interest hide">
                <div class="form-control job">
                    <label class="form-control-label">请选择你的职业</label>
                    <div class="form-control-input">
                <!-- select -->
                	 <div class="form-control-customSelect">
                        <select name="business" class="form-control-select valid-input business"
                                data-valid-group="business" >
                            <option value="">行业</option>
                            <option value="1">行业A</option>
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
                        <select name="job" class="form-control-select valid-input job"
                                data-valid-rule="required" data-valid-group="business"
                                data-valid-text='{"required":"请选择职业"}'>
                            <option value="">业</option>
                            <option value="2">职业B</option>
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
                    <label class="form-control-label" >请选择你关注的领域（可多选）</label>
                    <div class="form-control-input" >
                        <div id="lablesList" class="form-control-checkboxGroup">
                            <div class="form-control-customCheckbox custom-checkbox ">
                                <label class="form-control-customCheckbox-label">互联网</label>
                                <input type="checkbox" class="form-control-checkbox valid-input " name="domain"
                                        data-valid-group="domain"  />
                            </div>
                            <div class="form-control-customCheckbox custom-checkbox "><label
                                    class="form-control-customCheckbox-label"
                                    >设计</label><input
                                    class="form-control-checkbox valid-input" type="checkbox" name="domain"
                                    data-valid-group="domain"/></div>
                            <div class="form-control-customCheckbox custom-checkbox "><label
                                    class="form-control-customCheckbox-label"
                                    >旅行</label><input
                                    class="form-control-checkbox valid-input" type="checkbox" name="domain"
                                    data-valid-group="domain"/></div>
                            <div class="form-control-customCheckbox custom-checkbox "><label
                                    class="form-control-customCheckbox-label"
                                    >智能硬件</label><input
                                    class="form-control-checkbox valid-input" type="checkbox" name="domain"
                                    data-valid-group="domain"/></div>
                            <div id="lablesMax" class="form-control-customCheckbox custom-checkbox "><label
                                    class="form-control-customCheckbox-label"
                                    >投融资</label><input
                                    class="form-control-checkbox valid-input" type="checkbox" name="domain"
                                    data-valid-group="domain" data-valid-rule="required,max" data-valid-max="3"
                                    data-valid-text='{"required":"请选择兴趣","max":"选择的兴趣不能多于三项"}'/></div>

                        </div>
                    </div>
                </div>
                <input id="hidden" type="hidden"   data-valid-text='{"required":"请选择兴趣","max":"选择的兴趣不能多于三项"}'/>
                <div class="form-buttonArea clearfix">
                 <label class="form-control-deal-checkbox clearfix">
                        <input type="checkbox" class="valid-input" name="deal"
                               checked
                               data-valid-rule="required"
                               data-valid-group="deal"
                               data-valid-text='{"required":"请同意注册协议"}'/>
                        阅读并同意
                        <a href="../pages/deal.html" target="_blank">注册协议</a>
                        </label>
                    <button class="form-button button gray">上一步</button>
                    <input id="regSubmit" type="button" class="form-button black button" value="完成" />
                </div>
            </div>
        </form>

    </section>
    <!--    content   -->


    <!--    footer    -->
    <footer class="footer">
        <ul class="footer-nav">
            <li class="footer-nav-item"><a class="footer-nav-link" href="#">关于我们</a></li>
            <li class="footer-nav-item"><a class="footer-nav-link" href="#">版权说明</a></li>
            <li class="footer-nav-item"><a class="footer-nav-link" href="#">联系我们</a></li>
            <li class="footer-nav-item"><a class="footer-nav-link" href="#">意见反馈</a></li>
        </ul>
        <div class="footer-text">
            广州公司
        </div>
    </footer>
    <!--    footer    -->
	
 

  
    <script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/location/location.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/customUI/customUI.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/validation/validation.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/register.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>  
    <script type="text/javascript" src="<%=basePath %>scripts/pages/sign_login.js"></script>    
    <script type="text/javascript" src="<%=basePath %>scripts/components/md5/jQuery.md5.js"></script>
    
</body>
</html>
