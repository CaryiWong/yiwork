$(document).ready(function(e){
	//$("#nick").change(function(){validateform()});
	$("input[name='nickname']").change(function(){checknickname()});  //昵称
	$("input[name='email']").change(function(){checkemail()});		 //邮箱
	$("input[name='telnum']").change(function(){checktel()});		 //电话
	$("input[name='unum']").change(function(){checkunum()});		 //会员号检查
	//getFavoriteList();  //获取兴趣爱好领域   2.1版本去除兴趣@kcm 5.15
	//初始化多选插件 
	/*$('#optgroup').multiSelect({
		 afterSelect: function(values){
			 var s =$($('#optgroup').multiSelect("select",Array)).val();
			 if(s.length>3){
				 alert("兴趣最多选择三项,请精简");
			 }
			},
		afterDeselect: function(values){
			var s =$($('#optgroup').multiSelect("select",Array)).val();
			if(s==""||s==null){
				 alert("兴趣不能为空,请选择");	 
			   }
			},
	}); 
	$('#optgroup').multiSelect('select_all'); 2.1版本去除兴趣@kcm 5.15*/
	$("select[name='industry.id']").change(function(){loadJobs($(this).val())});		 //切换职业
	//$("select[name='root']").change(function(){checkNumShow()})
	//$('#optgroup').multiSelect({ selectableOptgroup: true,keepOrder:true });
	//$($('#optgroup').multiSelect('select',String)).val()
	 
})


/*统一前缀*/
var rootjs= root+"v20/";

/**
 * 获取兴趣爱好领域
 */

function getFavoriteList(){
	var userfavourite = new Array();
	$.ajax({
	     type: "post",
	     url: rootjs+"user/getuser",
	     data:{'type':'web','userid':userjsid},
	     dataType:"json",
	     success: function(json){
	    	 if(json.cord==0){
	    		 userfavourite=json.data.favourite;
	    	 }else
	    	 {
	    	  alert(json.msg);
	    	}
	     }
	 });
	
	$.ajax({
	     type: "post",
	     url: rootjs+"labels/lablelist",
	     async :true,
	     data:{'type':'web','size':99,'page':0,'labletype':'favourite'},
	     dataType:"json",
	     success: function(json){
			if(json.cord==0){
				var parentArray=new Array();
				var sonArray=new Array();
				for(var i=0;i<json.data.length;i++){
					if(json.data[i].pid==0)
						parentArray[i]=json.data[i]; //父级
				}
				var v=0;
				for(var i=0;i<json.data.length;i++){
					if(json.data[i].pid!=0 && json.data[i].pid!="0"){
						sonArray[v]=json.data[i]; //子级
						v=v+1;
					}
				}
				//添加子项 
				 
				for(var j=0;j<sonArray.length;j++){					
					$('#optgroup').multiSelect('addOption', {value:sonArray[j].id, text:sonArray[j].zname, index: j });
					//加载出用户已选的值				
					/*for(var i=0;i<userfavourite.length;i++){
					if(userfavourite[i].id==sonArray[j].id)
						$("option[value='"+userfavourite[i].id+"']").attr("selected","")
					}*/
					 
				}
				//获取值$($('#optgroup').multiSelect("select",String)).val()
			}
	     }
	    });
}

/**
 * 如果是注册会员，选择收费之后，会员号可输入
 */
function checkNumShow()
{
	if($("select[name='root']").val()==1)
	{
		$("#unum").removeAttr("readonly")	
	}else
	{
		$("#unum").val("");
		$("#unum").attr("readonly","readonly")
	}
}

unumTag=false;
function checkunum(){
	var unum = $("#unum").val();
	var submitdata = {
		unum:unum
	};
	$.ajax({
     type: "post",
     url: rootjs+"user/checkunum",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		var error='<span class="valid-error">会员号已占用，请重新输入</span>';
			$("#unum").after(error);
			//$("#unum").val("");
     	}else
     	{
     		$("#unum").next('.valid-error').remove();
     		unumTag=true;
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
	$("select[name='jobid']").find("option").remove();
	//var tmp=rootjs+"classsort/getclasssortlist";
	var tmp=rootjs+"labels/lablelist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{ 
			type: 'web',
            labletype:'job',
            pid: id,
            page:'0',
            size:'99'
            },
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				//var str='<option  value="">请选择</option>';
				var str="";
				for(var i=0;i<json.data.length;i++){
				str+='<option   value="'+json.data[i].id+'">'+json.data[i].zname+'</option>';
			} 
			$("select[name='jobid']").append(str)
		}
	}
	});
}




function validateform(obj){
	var nick = $("#nick").val();
	var root = $("#root").val();  //是否收费
	var unum = $("#unum").val();
	var begin = $("#begin").val();
	var end = $("#end").val();
	var favouriteid=$("#optgroup").val();
	var focuslist=$("#groups")
	if(root==1){
		if(unum==""||begin==""||end==""){
			$("#unum").removeAttr("readonly");
			$("#unum").focus();
			alert("请输入会员号与会员的起、止日期");
			return false;
		}
	}
	
	 if($("input[name='groups']:checked").length<=0)
	 {
		 alert("请选择关注");
		 return false;
	 } 
	 
	 /*if($("input[name='groups']:checked").length>3)
	 {
		 alert("关注不能超过3个");
		 return false;
	 } */
	
	if(!nickNameTag|| !emailTag ||!telTag){
		alert("昵称/邮箱/手机号/已存在,请检查")
		return false;
	}

	 
	/*if(favouriteid=="" ||favouriteid==null ||$("#optgroup").val()==null){
		 alert("请选择兴趣领域")
		 return false;
	 }else{
		 $("#favouriteid").val(favouriteid);
	 }
	
	if(favouriteid.length>3){
		 alert("兴趣领域不能超过3个")
		 return false;
	}*/
	
	return true;
}

var nickNameTag=true;
function checknickname(){
	var nick = $("input[name='nickname']").val()
	var submitdata = {
		nickname:nick
	};
	$.ajax({
     type: "post",
     url: rootjs+"user/checknickname",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		//$("#nick").val(1);
     		//alert("此昵称已被使用，请重新输入一个昵称");
			$("#nickname").focus(); //获取焦点
			var error='<span class="valid-error" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;昵称已占用，请重新输入</span>';
			$("#nickname").after(error);
			nickNameTag=false;
     	}else{
     		$("#nickname").next('.valid-error').remove();
     		nickNameTag=true;
     	}
     }
    });
}

emailTag=true;
function checkemail(){
	var email = $("#email").val()
	var submitdata = {
		email:email
	};
	$.ajax({
     type: "post",
     url: rootjs+"user/checkemail",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		//$("#mail").val(1);
     		//alert("此邮箱已被使用，请重新输入一个邮箱");
     		var error='<span class="valid-error">邮箱已占用，请重新输入</span>';
			$("#email").after(error);
			$("#email").focus(); //获取焦点
			emailTag=false;
     	}else
     	{
     		emailTag=true;
     		$("#email").next('.valid-error').remove();
     	}
     }
    });
}

telTag=true;
function checktel(){
	var tel = $("#telnum").val();
	var submitdata = {
		tel:tel
	};
	$.ajax({
     type: "post",
     url: rootjs+"user/checktel",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		var error='<span class="valid-error" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机已占用，请重新输入</span>';
			$("#telnum").after(error);
			$("#telnum").focus(); //获取焦点
			telTag=false;
     	}else
     	{
     		$("#telnum").next('.valid-error').remove();
     		telTag=true;
     	}
     }
    });
}

function charge(obj){
	var charge = obj.value;
	if(charge==1){
		$("#unum").removeAttr("readonly");
		$("#chargetr").show(1000);
	}else{
		$("#unum").attr("readonly","readonly");
		$("#chargetr").hide(1000);
	}
}

function addOneyear(obj,n){
	var d=new Date(obj.value);
	var year=d.getYear()+n+1900;
	var m=d.getMonth()+1;
	var day=d.getDate();
	var str=year+"-";
	if(m<10){
		str+="0"+m+"-";
	}else{
		str+=m+"-";
	}
	if(day<10){
		str+="0"+day;
	}else{
		str+=day;
	}
	document.getElementById("end").value=str;
}




/**
 * 修改用户图像
 */
function uploadtx(){
	$.ajaxFileUpload( 
     { 
     	//用于文件上传的服务器端请求的Action地址 
     	url:rootjs+"upload/uploadimg",
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'userimgfile',//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
     		var result = JSON.parse(json);
     		if(result.cord==0){
     			$("#userimg").val(result.data);
     			//$("#yixuantouxian").show();
     			$("#Yuanuserimg").attr("src",rootjs+"download/img?type=web&path="+result.data);
     			var tmp=rootjs+"user/updateuser";
				jQuery.ajax({
					url: tmp,
					type: "POST",
					async:false,
					data:{"type":'web',"id":$("#userid").val(),"minimg":result.data},
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
		}
     });
}

/**
 * 修改二维码
 */
function uploaderweima(){
	$.ajaxFileUpload( 
     { 
     	//用于文件上传的服务器端请求的Action地址 
     	url:rootjs+"upload/uploadimg",
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'maximgfile',//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
     		var result = JSON.parse(json);
     		if(result.cord==0){
     			$("#twonum").val(result.data);
     			//$("#yixuanfengmian").show();
     			$("#Yuantwonum").attr("src",rootjs+"download/img?type=web&path="+result.data);
     			var tmp=rootjs+"user/updateuser";
				jQuery.ajax({
					url: tmp,
					type: "POST",
					async:false,
					data:{"type":'web',"id":$("#userid").val(),"twonum":result.data},
					dataType:"json",
					success: function(json){
						if(json.cord==0){
							//加载列表数据
							//alert("修改成功!")
							alert("用户二维码修改成功");
							//window.location.reload();
						} 
					}
				});
     		}
		}
     });
}



/**
 * 修改用户大图
 */
function uploadmaximg(){
	$.ajaxFileUpload( 
     { 
     	//用于文件上传的服务器端请求的Action地址 
     	url:rootjs+"upload/uploadimg",
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'hugoimgfile',//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
     		var result = JSON.parse(json);
     		if(result.cord==0){
     			$("#hugoimg").val(result.data);
     			//$("#yixuanfengmian").show();
     			$("#Yuanhugoimg").attr("src",rootjs+"download/img?type=web&path="+result.data);
     			var tmp=rootjs+"user/updateuser";
				jQuery.ajax({
					url: tmp,
					type: "POST",
					async:false,
					data:{"type":1,"id":$("#userid").val(),"maximg":result.data},
					dataType:"json",
					success: function(json){
						if(json.cord==0){
							//加载列表数据
							//alert("修改成功!")
							alert("用户大图修改成功");
							//window.location.reload();
						} 
					}
				});
     		}
		}
     });
}





