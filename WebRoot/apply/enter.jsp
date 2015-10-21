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
    <meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">
    <title>入驻申请-一起开工社区</title>
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
    <form class="apply-form apply-form-enter">
        <div class="form-title">
            入驻申请表
        </div>
        <div class="form-control-area">

            <div class="form-control name">
                <label class="form-control-label">
                    团队名称<span class="form-control-label-required">＊</span>
                </label>

                <div class="form-control-input">
                    <input id="teamName" class="form-control-text text valid-input" type="text"
                           data-valid-rule="required"
                           data-valid-text='{"required":"请填写团队名称"}'/>
                </div>
            </div>

            <div class="form-control number">
                <label class="form-control-label">
                    团队人数<span class="form-control-label-required">＊</span>
                </label>

                <div class="form-control-input">
                    <input  id="teamPelNum" class="form-control-text text valid-input" type="text"
                            data-valid-rule="required,number,min"
                            data-valid-min="1"
                            data-valid-text='{"required":"请填写团队人数","number":"请填写正确的数字","min":"最少值不得为0"}'/>
                </div>
            </div>

            <div class="form-control type">
                <label class="form-control-label form-control-select-label">
                    业务类型<span class="form-control-label-required">＊</span>
                </label>

                <div class="form-control-input">
                
                <!-- select -->
                	 <div class="form-control-customSelect">
                    <select id="teamBus" class="form-control-select business valid-input"
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
                    <select id="teamJob" class="form-control-select job valid-input"
                            data-valid-group="business"
                            data-valid-rule="required"
                            data-valid-text='{"required":"请选择业务类型"}'>
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

            <div class="form-control time">
                <label class="form-control-label">
                    预计入驻时间<span class="form-control-label-required">＊</span>
                </label>

                <div class="form-control-input">
                    <input id="teamArrTime" class="form-control-text text datePicker valid-input" type="text"
                            data-valid-rule="required"
                            data-valid-text='{"required":"请填写预计入驻时间"}'/>
                    <i class="add-icon-calendar"></i>
                </div>
            </div>

            <div class="form-control intro">
                <label class="form-control-label form-control-textarea-label">
                    团队简介<span class="form-control-label-required">＊</span>
                </label>

                <div class="form-control-input">
                    <textarea id="teamSummary" class="form-control-textarea text valid-input"
                           data-valid-rule="required"
                           data-valid-text='{"required":"请填写团队简介"}'></textarea>
                </div>
            </div>

            <div class="form-control form-control-buttonGroup">
                <input type="submit" class="form-button button form-button-submit" value="提交" />
                <input type="button" class="form-button button gray form-button-cancel" value="取消" />
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
<script type="text/javascript" src="<%=basePath %>scripts/apply/enter.js"></script>

</body>
</html>
    
    