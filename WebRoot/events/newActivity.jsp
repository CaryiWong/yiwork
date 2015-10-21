<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%><!doctype html>
<!--[if IE 8]>
<html lang="zh-CN" class="no-js lt-ie9"><![endif]-->
<!--[if gt IE 8]><!-->
<html lang="zh-CN" class="no-js"><!--<![endif]-->
<head>
<script type="text/javascript">
	var root="<%=basePath%>";
</script>
    <meta charset="utf-8">
    <title>创建活动-一起开工社区</title>
	<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <!--    datepicker      -->
    <link rel="stylesheet" href="<%=basePath %>styles/components/datepicker/classic.css"/>
    <link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">
    <!--    datepicker      -->
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/activities.css"/>
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>	
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->
<!--    content   -->
<div class="container activity-create">
    <form name="myform" class="create-form" novalidate  ENCTYPE="multipart/form-data" action="<%=basePath %>activity/createactivity" method="post" target="hidden_frame">
          <div class="clearfix">
            <div class="form-control cover fl">
                <div class="form-control-fileArea image-preview">
                    <div class="form-control-file-content">
                        <div class="form-control-file-tip">
                            请上传最小300*420分辨率的图
                        </div>
                        <div class="form-control-fileCustom button black">
                            点击上传
                            <input  class="form-control-file valid-input" type="file" name="img"
                               data-valid-rule="required,image"
                               data-valid-text='{"required":"请选择上传的封面","image":"请上传图片"}' />
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-control activityDetail fl">
                <div class="form-control-fileArea image-preview">
                    <div class="form-control-file-content">
                        <div class="form-control-file-tip">
                            请上传最大1280*600，最小为950*450分辨率的图片（小于1M）
                        </div>
                        <div class="form-control-fileCustom button black">
                            点击上传
                            <input  class="form-control-file valid-input" type="file" name="imgs"
                                   data-valid-rule="required,image"
                                   data-valid-text='{"required":"请选择上传的介绍图片","image":"请上传图片"}'/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        

        <div class="form-base clearfix">
            <div class="form-base-title">
                活动基本信息
            </div>
            <div class="form-control name">
                <label class="form-control-label">标题</label>
                <div class="form-control-input">
                    <input type="text" name="title" class="form-control-text text valid-input"
                           data-valid-rule="required"
                           data-valid-text='{"required":"请填写标题"}'/>
                </div>
            </div>
            <div class="form-control date">
                <label class="form-control-label">日期/时间</label>

              <div class="form-control-input">
                    <div class="form-control-calendar">
                        <input type="text" class="form-control-text text dateStart valid-input datePicker"
                               placeholder="开始时间"
                               name="start"
                               data-datepicker-limit="date"
                               data-datepicker-group="start"
                               data-valid-rule="required"
                               data-valid-text='{"required":"请填写开始时间"}'/>
                        <i class="add-icon-calendar"></i>
                        <input type="text" class="timePicker hide dataStart"
                               data-datepicker-limit="date"
                               data-datepicker-group="start" />
                    </div>
                    <div class="form-control-calendar">
                        <input type="text" class="form-control-text text dateEnd valid-input datePicker"
                               placeholder="结束时间"
                               name="end"
                               data-datepicker-limit="date"
                               data-datepicker-group="end"
                               data-valid-rule="required"
                               data-valid-text='{"required":"请填写截止时间"}'/>
                        <i class="add-icon-calendar"></i>
                        <input type="text" class="timePicker hide dataEnd"
                               data-datepicker-limit="date"
                               data-datepicker-group="end" />
                    </div>
                </div>

            </div>
            <input type="hidden" name="opendate1" id="opendate1" value=""/>
            <input type="hidden" name="enddate1"  id="opendate1" value=""/>
            <div class="form-control address">
                <label class="form-control-label">活动地址</label>
					<input type="hidden" value="广州市荔湾区中山七路68号3楼" name="address1"/>
					<input type="hidden" value="0" name="cost"/>
					<input type="hidden" value="0" name="acttype"/>
					<input type="hidden" value="" name="labels"/>
					<input type="hidden" value="" name="user"/>
                <div class="form-control-input">
                     <input type="text" class="form-control-text text valid-input" name="address"
                     	   value="广州市荔湾区中山七路68号3楼"
                           data-valid-rule="required,max"
                           data-valid-max="30"
                           data-valid-text='{"required":"请填写活动地址","max":"请限定在30个字以内"}'/>
                </div>
            </div>
            <div class="form-control domain">
                <label class="form-control-label">所属领域</label>

                <div id="event_lables" class="form-control-input">
                    <div class="form-control-customCheckbox custom-checkbox">
                        <label class="form-control-customCheckbox-label">全部</label>
                        <input class="form-control-checkbox valid-input" name="domain"
                               type="checkbox"  data-valid-group="domain"/>
                    </div>
                    <div class="form-control-customCheckbox custom-checkbox">
                        <label class="form-control-customCheckbox-label">创新</label>
                        <input class="form-control-checkbox valid-input" name="domain"
                               type="checkbox"  data-valid-group="domain"/>
                    </div>
                    <div class="form-control-customCheckbox custom-checkbox">
                        <label class="form-control-customCheckbox-label">公益</label>
                        <input class="form-control-checkbox valid-input" name="domain"
                               type="checkbox" data-valid-group="domain"/>
                    </div>
                    <div class="form-control-customCheckbox custom-checkbox">
                        <label class="form-control-customCheckbox-label">旅行</label>
                        <input class="form-control-checkbox valid-input" name="domain"
                               type="checkbox" data-valid-group="domain"/>
                    </div>
                    <div class="form-control-customCheckbox custom-checkbox">
                        <label class="form-control-customCheckbox-label">影视</label>
                        <input class="form-control-checkbox valid-input" name="domain"
                               type="checkbox" data-valid-group="domain"/>
                    </div>
                    <div class="form-control-customCheckbox custom-checkbox">
                        <label class="form-control-customCheckbox-label">心理学</label>
                        <input class="form-control-checkbox valid-input" name="domain" type="checkbox"
                                data-valid-group="domain" data-valid-rule="required,max"
                                data-valid-max="1"
                                data-valid-text='{"required":"请选择所属领域","max":"只能选择一个领域"}'/>
                    </div>
                </div>
            </div>
            <div class="form-control people">
                <label class="form-control-label">参加人数</label>

                <div class="form-control-input">
                    <input type="text" min="1" class="form-control-text text valid-input" name="maxnum"
                            data-valid-rule="required,number,min"
                            data-valid-min="1"
                            data-valid-text='{"required":"请填写参加人数","number":"请填写有效数字","min":"参加人数不得少于1人"}'/> 人
                </div>
            </div>
            <div class="form-control phone">
                <label class="form-control-label">联系电话</label>

                <div class="form-control-input">
                    <input type="text" class="form-control-text text valid-input" name="tel"
                            data-valid-rule="required,phone"
                            data-valid-text='{"required":"请填写电话号码","phone":"请填写有效的电话号码"}'/>
                </div>
            </div>
            
            <!--    活动类型    -->
            <div class="form-control type">
                <label class="form-control-label">活动类型</label>

                <div class="form-control-input">
                    <div class="form-control-customCheckbox custom-radio">
                        <label class="form-control-customCheckbox-label">常规</label>
                        <input class="form-control-checkbox valid-input switch" checked name="acttypeflag" type="radio"
                               value="0"
                               data-switch-group="type"
                               data-valid-group="type"/>
                    </div>
                    <div class="form-control-customCheckbox custom-radio">
                        <label class="form-control-customCheckbox-label">个性化</label>
                        <input class="form-control-checkbox valid-input switch" name="acttypeflag" type="radio"
                               value="1"
                               data-switch-group="type"
                               data-valid-group="type" data-valid-rule="required"
                               data-valid-text='{"required":"请选择收费模式"}'/>
                    </div>

                </div>
            </div>
            
             <div class="form-control buttonName hide"
                    data-switch-group="type" data-switch-value="1">
                <label class="form-control-label">按钮名称</label>

                <div class="form-control-input">
                    <input type="text" class="form-control-text text valid-input" name="acttypetitle"
                           disabled
                           data-valid-rule="required,max"
                           data-valid-max="4"
                           data-valid-text='{"required":"请填写按钮名称","max":"请限定在4个字以内"}'/>
                </div>
            </div>
            <div class="form-control url hide"
                    data-switch-group="type" data-switch-value="1">
                <label class="form-control-label">链接</label>

                <div class="form-control-input">
                    <input type="text" class="form-control-text text valid-input" name="acttypeurl"
                           disabled
                           data-valid-rule="required,url"
                           data-valid-text='{"required":"请填写URL","url":"请填写正确的url"}'/>
                </div>
            </div>
            <!--    活动类型    -->
            
            
            
            
            <div class="form-control pay">
                <label class="form-control-label">活动收费</label>

                <div class="form-control-input">
                    <div class="form-control-customCheckbox custom-radio">
                        <label class="form-control-customCheckbox-label">免费</label>
                        <input class="form-control-checkbox valid-input" name="pay" type="radio"
                             value="0"  data-valid-group="pay"/>
                    </div>
                    <div class="form-control-customCheckbox custom-radio">
                        <label class="form-control-customCheckbox-label">收费</label>
                        <input class="form-control-checkbox valid-input" name="pay" type="radio"
                          value="1"     data-valid-group="pay" data-valid-rule="required"
                               data-valid-text='{"required":"请选择收费模式"}'/>
                    </div>
                    
                    <div class="form-control-input money money-single">
                        <div class="form-control-button">分类型收费</div>
                        <input type="text" disabled class="form-control-text text valid-input" name="money"
                               data-valid-rule="required,money,min"
                               data-valid-min="0"
                               data-valid-text='{"required":"请填写","money":"请填写有效的数字","min":"最少1元"}'/> 元
                    </div>

                    <div class="form-control-input money money-multiple">
                        <div class="form-control-button">统一收费</div>
                        <div class="money-item">
                            <input type="text" disabled class="form-control-text text valid-input" name="moneyName"
                                   value="类型1"
                                   data-valid-rule="required"
                                   data-valid-text='{"required":"请填写"}'/><span class="form-control-space">:</span><input type="text" disabled class="form-control-text text valid-input" name="moneyPay"
                                   value="30"
                                   data-valid-rule="required,money,min"
                                   data-valid-min="0"
                                   data-valid-text='{"required":"请填写","money":"请填写有效的数字","min":"最少1元"}'/> 元
                        </div>
                        <div class="money-item">
                            <input type="text" disabled class="form-control-text text valid-input" name="moneyName"
                                   value="类型2"
                                   data-valid-rule="required"
                                   data-valid-text='{"required":"请填写"}'/><span class="form-control-space">:</span><input disabled type="text" class="form-control-text text valid-input" name="moneyPay"
                                   value="50"
                                   data-valid-rule="required,money,min"
                                   data-valid-min="0"
                                   data-valid-text='{"required":"请填写","money":"请填写有效的数字","min":"最少1元"}'/> 元
                        </div>
                        <button class="button money-multiple-button">
                            <i class="yiqi-icon-lPlus"></i>
                        </button>
                        </div>
                </div>
            </div>
            <div class="form-control intro">
                <label class="form-control-label">活动介绍</label>
                <div class="form-control-input">
                    <textarea name="summary" class="form-control-textarea text valid-input"
                           data-valid-rule="required" data-valid-text='{"required":"请填写活动介绍"}' ></textarea>
                </div>
            </div>
            <div class="form-control buttonGroup fr">
                <button class="form-button gray button back-button">取消</button>
                <input type="submit" class="form-button button" value="申请"  />
            </div>
            <input id="hidden" type="hidden"   data-valid-text='{"required":"请选择活动领域","max":"选择的领域不能多于一项"}'/>
        </div>
    </form>
</div>
<!--    content   -->

<!-- footer -->
  	<jsp:include page="/pages/footer.jsp" flush="true"></jsp:include>
<!--    footer    -->
<iframe name='hidden_frame' id="hidden_frame" style="display:none" mce_style="display:none"></iframe>
<!--    footer    -->
<script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
<!--    datepicker      -->
<script type="text/javascript" src="<%=basePath %>scripts/components/datepicker/picker.js"></script>
<!--    datepicker      -->
<script type="text/javascript" src="<%=basePath %>scripts/components/customUI/customUI.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/validation/validation.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/jquery/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/pages/activity.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/event/newActivity.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/plug_login.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/md5/jQuery.md5.js"></script>

</body>
</html>
