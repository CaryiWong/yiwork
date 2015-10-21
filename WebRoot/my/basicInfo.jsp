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
<script type="text/javascript">
	var root="<%=basePath%>";
</script>
    <meta charset="utf-8">
    <title>个人中心-一起开工社区</title>
	<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/my.css"/>
    <link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/ajaxfileupload.js"></script>
    
    <script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/my/basicInfo.js"></script>   
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->

 

<!--    content   -->
<div class="container info clearfix">
    <div class="panel info-panel">
        <div class="panel-header">
            <div class="panel-header-text">基本信息</div>
            <div class="panel-header-buttons now">
                <button class="button-link edit panel-header-buttonLink">编辑资料</button>
            </div>
            <div class="panel-header-buttons edit hide">
                <button class="button-link cancel panel-header-buttonLink">取消</button>
                <button id="top_save" class="button-link save panel-header-buttonLink">保存</button>
            </div>
        </div>
        <form class="panel-content info-form" novalidate>
            <div class="form-control head">
                <div class="form-control-label">个人头像</div>
                <div class="form-control-value">
                    <img id="user_img" src="" class="form-control-head"/>

                    <div class="form-control-fileArea image-preview hide">
                        <div class="form-control-file-content hide">
                            <div class="form-control-fileCustom button black">
                                更改头像
                                <input onchange="saveUserImg()" id="img" class="form-control-file" type="file" name="img"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-control email">
                <div class="form-control-label">登录邮箱</div>
                <div id="user_mail" class="form-control-value">
                   
                </div>
            </div>
            <div class="form-control name">
                <div class="form-control-label">昵称</div>
                <div id="user_name" class="form-control-value">
                    
                </div>
                <div class="form-control-input">
                    <input type="text" name="name" readonly="readonly" class="form-control-text text valid-input"
                           data-valid-rule="required"
                           data-valid-text='{"required":"用户名不能为空"}'/>
                </div>
            </div>
            
            <div class="form-control age">
                <div class="form-control-label">年龄</div>
                <div id="user_age" class="form-control-value">
                    18
                </div>
                <div class="form-control-input">
                    <input type="text" name="age"  class="form-control-text text valid-input"
                           data-valid-rule="required"
                           data-valid-text='{"required":"年龄不能为空"}'/>
                </div>
            </div>
            
            <div class="form-control password">
                <div class="form-control-label">密码</div>
                <div class="form-control-value">
                    <a class="passwordLink" href="<%=basePath %>/my/editPassword.jsp">修改密码</a>
                </div>
            </div>
            <div class="form-control address">
                <div class="form-control-label">地区</div>
                <div id="user_address" class="form-control-value">
                    广东省，广州市，荔湾区，中山七路68号，301室
                </div>
                <div class="form-control-input">
                        <select name="province" class="form-control-select province valid-input"
                                data-valid-group="location">
                            <option value="">省份</option>
                        </select>
                        <select name="city" class="form-control-select city valid-input"
                                data-valid-rule="required" data-valid-group="location"
                                data-valid-text='{"required":"请选择地区信息"}'>
                            <option value="">城市</option>
                        </select>
                    </div>
            </div>
            <div class="form-control sex">
                <div class="form-control-label">性别</div>
                <div id="user_sex" class="form-control-value" data-switch-value="0">
                    男
                </div>
                <div class="form-control-input">
                    <select id="user_sexValue" class="form-control-select" name="sex">
                        <option value="0">男</option>
                        <option value="1">女</option>
                    </select>
                </div>
            </div>
            <div class="form-control job">
                <div class="form-control-label">职业</div>
                <div id="user_job" class="form-control-value">
                </div>
                <div class="form-control-input">
                    <select name="business" class="form-control-select valid-input business"
                                data-valid-group="business" >
                            <option value="">行业</option>
                            <option value="1">行业A</option>
                        </select>
                        <select name="job" class="form-control-select valid-input job"
                                data-valid-rule="required" data-valid-group="business"
                                data-valid-text='{"required":"请选择职业"}'>
                            <option value="">业</option>
                            <option value="2">职业B</option>
                        </select>
                </div>
            </div>
            <div class="form-control phone">
                <div class="form-control-label">电话</div>
                <div id="user_tel" class="form-control-value">
                </div>
                <div class="form-control-input">
                    <input id="user_telValue" type="text" name="phone" class="form-control-text text valid-input"
                           data-valid-rule="phone"
                           data-valid-text='{"phone":"请填写正确的手机号码"}'/>
                </div>
            </div>
            <div class="form-control  domain">
                <div class="form-control-label">关注领域</div>
                <div id="user_lingyu" class="form-control-value form-control-chooseCheckboxView" data-switchForm-group="domain">
                    <div class="form-control-customCheckbox custom-checkbox disabled">
                        <label class="form-control-customCheckbox-label">互联网</label>
                        <input type="checkbox" class="form-control-checkbox valid-input disabled" name="domain"
                               checked disabled
                               data-valid-group="domain"/>
                    </div>
                    <div class="form-control-customCheckbox custom-checkbox disabled">
                        <label class="form-control-customCheckbox-label">创新</label>
                        <input type="checkbox" class="form-control-checkbox valid-input disabled" name="domain"
                               checked disabled
                               data-valid-group="domain"/>
                    </div>
                    <div class="form-control-customCheckbox custom-checkbox disabled">
                        <label class="form-control-customCheckbox-label">文章</label>
                        <input type="checkbox" class="form-control-checkbox valid-input disabled" name="domain"
                               checked disabled
                               data-valid-group="domain"/>
                    </div>
                    <input type="checkbox" class="valid-input hide" name="domain"
                           data-valid-group="domain"
                           data-valid-rule="required,max"
                           data-valid-max="3"
                           data-valid-text='{"required":"请选择关注领域","max":"关注领域不能大于3个"}'/>
                </div>
            </div>

            <!--        未选择领域  不包含input    -->
            <div id="userNoSelect" class="form-control form-control-otherCheckboxView hide" data-switchForm-group="domain">
                <div class="form-control-customCheckbox custom-checkbox ">
                    <label class="form-control-customCheckbox-label">出行</label>
                    <input type="checkbox" class="form-control-checkbox valid-input" name="domain"
                           data-valid-group="domain"/>
                </div>
                <div class="form-control-customCheckbox custom-checkbox ">
                    <label class="form-control-customCheckbox-label">嫁娶</label>
                    <input type="checkbox" class="form-control-checkbox valid-input" name="domain"
                           data-valid-group="domain"/>
                </div>
                <div class="form-control-customCheckbox custom-checkbox ">
                    <label class="form-control-customCheckbox-label">破屋</label>
                    <input type="checkbox" class="form-control-checkbox valid-input" name="domain"
                           data-valid-group="domain"/>
                </div>
                <div class="form-control-customCheckbox custom-checkbox ">
                    <label class="form-control-customCheckbox-label">扫地</label>
                    <input type="checkbox" class="form-control-checkbox valid-input" name="domain"
                           data-valid-group="domain"/>
                </div>
                <div class="form-control-customCheckbox custom-checkbox ">
                    <label class="form-control-customCheckbox-label">人文</label>
                    <input type="checkbox" class="form-control-checkbox valid-input" name="domain"
                           data-valid-group="domain"/>
                </div>

            </div>
            <!--        未选择领域       -->

            <div class="form-control form-control-submit hide">
                <input type="" id="saveUserInfo" class="button black" value="保存设置"/>
            </div>

        </form>
    </div>
</div>
<!--    content   -->

<!-- footer -->
  	<jsp:include page="/pages/footer.jsp" flush="true"></jsp:include>
<!--    footer    -->
<script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/location/location.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/customUI/customUI.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/pages/my.js"></script>
 


</body>
</html>


