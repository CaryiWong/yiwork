$(document).ready(function(e) {
	checkIfAllow();  // 非法访问
	loadUserInfo();  //设置用户信息
	window.setTimeout(util.loading.show(), 1000)
	//loadLables(); //加载领域
 	//loadHangYe(); //加载行业
	$("#saveUserInfo").click(function(){saveUserInfo()})
	$("#top_save").click(function(){saveUserInfo()})
	window.setTimeout("util.loading.close()", 1000)
	$("#user_telValue").blur(function(){verifiTel()});
})

var userObj;
var jobInfo;
var hangye;
/*统一前缀*/
var rootjs= root+"v20/";

/**
 * 非法访问
 */
function checkIfAllow()
{
	if(session_loginId=="")
	{
		alert("请先登录");
		window.location.href=root+"events/activities.jsp"
	}
}
 


/**
 * 设置用户信息
 */
function loadUserInfo()
{
	 //var tmp=root+"user/getuserinfo";
	var tmp=rootjs+"user/getuser";
		$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"userid":session_loginId,"type":1},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-失败
				var type = parseInt(json.cord);
				switch (type){
					case 0: 
						userObj=json.data;
						jobInfo=json.data.job;
						hangye=json.data.industry;
						$("#user_mail").text(json.data.email);  //邮箱
						$("#user_name").text(json.data.nickname);  //用户名-昵称
						$("#user_address").text(json.data.province+json.data.city);  //所在地
						
						$("#user_img").attr("src",rootjs+'download/img?type=web&path='+json.data.minimg);   //用户头像
						var sex="男";
						if(json.data.sex==1) sex="女";
						$("#user_sex").text(sex);  //性别
						$("#user_sex").attr("data-switch-value",json.data.sex);
						if(json.data.job!=null)
							$("#user_job").text(json.data.job.zname);  //job
						
						$("#user_tel").text(json.data.telnum);  //联系电话
						//end 以上为基本信息
						var lingyu="";  //领域标签
						/**for(var i=0;i<json.data.label.length;i++)
						{
							lingyu+='<div class="form-control-customCheckbox custom-checkbox disabled">'
                       		lingyu+='<label  class="form-control-customCheckbox-label">'+json.data.label[i].zname+'</label>'
                        	lingyu+='<input id='+json.data.label[i].id+' type="checkbox" class="form-control-checkbox valid-input disabled" name="domain" checked disabled  data-valid-group="domain"/></div>'
						}
						$("#user_lingyu").text(""); $("#user_lingyu").append(lingyu); 
						loadLablesUnSelect(json.data.label)  //加载未选中的标签列表**/
						return;
				}
			}
		});
}


/**
 * 手机号
 * @return {TypeName} 
 */
var nick=true;
function verifiTel(){
	var str = $("input[name=phone]").val();
	var tmp=rootjs+"user/checktel";  //验证昵称是否唯一
	$("input[name=phone]").next('.valid-error').remove();
	if(str=="" || str==null){
		var error='<span class="valid-error" >手机号不能为空,请输入</span>';
	 	$("input[name=phone]").after(error);
		nick=false;
		return nick;
	}
		
	if(str==userObj.telnum){
		return true;
	}
	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"tel":str,"type":1},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-用户名重复
				var type = parseInt(json.cord);
				switch (type){
					case -1: 
						var error='<span class="valid-error" >手机号已被占用,请重新输入</span>';
					 	$("input[name=phone]").after(error);
						nick=false;
						return nick;
						break;
					case 0: 
						nick = true;
						return nick;
						break;
				}
			}
		});
}


//设置未选的便签值
function loadLablesUnSelect(arr)
{ 
	//var tmp=root+"labels/getlabellist";
	var tmp=rootjs+"labels/getalllabel";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				var str="";
				for(var i=0;i<json.data.length;i++){
					//alert(arr[i].id)
					if(i<arr.length){
						if(json.data[i].id==arr[i].id )
						{
							continue;
						}	
					}
					str+='<div class="form-control-customCheckbox custom-checkbox ">';
                    str+='<label  class="form-control-customCheckbox-label">'+json.data[i].zname+'</label>';
                    str+='<input  id='+json.data[i].id+' type="checkbox" class="form-control-checkbox valid-input" name="domain"data-valid-group="domain"/></div>'
				}
				jQuery("#userNoSelect").text("");
				jQuery("#userNoSelect").append(str);
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}

/**
 * 保存用户的基本上信息
 */
function saveUserInfo()
{
	if(!nick)
	{
		return ;
	}
	
	
	//saveUserImg(); //保存图片
	var lables="";  //列表值
	
	$(".form-control-checkbox.valid-input.disabled").each(function(){
		if(lables=="")
		{
			lables=this.id;
		}else
		{
			lables=lables+","+this.id;
		}
	})
	var tel=$("#user_telValue").val();  //电话
	var sex=jQuery("#user_sexValue  option:selected").val();;  //性别
	
	//attr
	var prov= $("select[name=province]").children('option:selected').val();   
	var city= $("select[name=city]").children('option:selected').val();  
	var hangye=$("select[name=business]").children('option:selected').val();   
	var jobs=$("select[name=job]").children('option:selected').val();  
	
	//空值
	if(prov=="")prov=userObj.province
	if(city=="")city=userObj.city;
	if(hangye=="")hangye=jobInfo.id;
	if(jobs=="")jobs=jobInfo.id;
	
	
	var tmp=rootjs+"user/updateuser";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"id":session_loginId,"province":prov,"city":city,"sex":sex,"telnum":tel,"industry":hangye,"job":jobs,"label":lables},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				alert("修改成功!")
				window.location.reload();
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}



/**
 * 保存用户头像
 * @param {Object} i
 */	
function saveUserImg()
{
  	$.ajaxFileUpload( 
     { 
    	//url:root+"upload/setusericon/a?userid="+session_loginId,//用于文件上传的服务器端请求的Action地址
    	url:rootjs+"user/updateusericon",
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'img',//文件id	属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	data:{"userid":"7f7641397e3f45eebf7e1399107572747","type":"web"},
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
    	 	 	   json=JSON.parse(json)  //手动转json
				   if(json.cord==0)
				   {
					 $("#user_img").attr("src",rootjs+'download/img?type=web&path='+json.data);   //刷新用户头像
					 var tmp=root+"user/updateuser";
						jQuery.ajax({
							url: tmp,
							type: "POST",
							async:false,
							data:{"type":1,"userid":session_loginId,"minimg":json.data},
							dataType:"json",
							success: function(json){
								if(json.cord==0){
									//加载列表数据
									//alert("修改成功!")
									alert("头像修改成功");
									//window.location.reload();
								} 
							}
						});
					    
				   }
    	 	 	   //alert(json.cord)
					//alert(json.cord);//从服务器返回的json中取出message中的数据,其中message为在struts2中定义的成员变量	
					//$.each(msg,function(k,y){ 
					//$("#id").append("&amp;lt;option  &amp;gt;"+y+" &amp;lt;/option&amp;gt;"); 
			//}); 
		 } 
     }); 
}



/**
 * 加载注册需要的领域
 */
function loadLables()
{
	var tmp=rootjs+"labels/getlabellist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				var str="";
				for(var i=0;i<json.data.length;i++){
					 if(i!=json.data.length-1){
					 str+='<div class="form-control-customCheckbox custom-checkbox">';
					 str+='<label id="'+json.data[i].id+'" class="form-control-customCheckbox-label">'+json.data[i].zname+'</label>'
					 str+='<input type="checkbox" class="form-control-checkbox valid-input" name="domain" data-valid-group="domain"  value='+json.data[i].id+' />'
                     str+='</div>'
				}else{
				 	 str+='<div class="form-control-customCheckbox custom-checkbox">';
					 str+='<label id="'+json.data[i].id+'" class="form-control-customCheckbox-label">'+json.data[i].zname+'</label>'
					 str+='<input type="checkbox" class="form-control-checkbox valid-input" name="domain" data-valid-group="domain"  value='+json.data[i].id+' />'
                     str+='</div>'
                 }   	 
				jQuery("#lablesList").html(str);
			} 
		}}
	});
}

/**
 * 加载行业信息 ,下拉框
 */
function loadHangYe()
{
	var tmp=rootjs+"classsort/getclasssortlist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"pid":0},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				var str="";
				for(var i=0;i<json.data.length;i++){
				 str+='<option value="'+json.data[i].id+'">'+json.data[i].zname+'</option>';
			} 
			$("select[name=business]").append(str);
			//loadJobs(json.data[0].id)
		}
	}
	});
}

/**
 * 根据行业选取职业
 */
function loadJobs(id)
{ 
	//首先清空残留的值
	$("select[name=job]").find("option").remove()
	var tmp=rootjs+"classsort/getclasssortlist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"pid":id},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				var str='<option  value="">请选择</option>';
				for(var i=0;i<json.data.length;i++){
				str+='<option   value="'+json.data[i].id+'">'+json.data[i].zname+'</option>';
			} 
			$("select[name=job]").append(str)
		}
	}
	});
}




