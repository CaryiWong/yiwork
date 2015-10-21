$(document).ready(function(e){
	//$("#nick").change(function(){validateform()});
	$("#nickname").change(function(){checknickname()});  //昵称
	$("#email").change(function(){checkemail()});		 //邮箱
	$("#telephone").change(function(){checktel()});		 //电话
	$("#unum").change(function(){checkunum()});		 //会员号检查
	//loadJobs($("select[name='jobinfo.industry.id']").val());// 加载职业
	$("select[name='jobinfo.industry.id']").change(function(){loadJobs($(this).val())});		 //切换职业
	$("select[name='root']").change(function(){checkNumShow()})
})

root=root+"v20";
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
     url: root+"user/checkunum",
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
	$("select[name='jobinfo.job.id']").find("option").remove()
	var tmp=root+"user/getclasssortlist";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"pid":id},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				//var str='<option  value="">请选择</option>';
				var str="";
				for(var i=0;i<json.data.length;i++){
				str+='<option   value="'+json.data[i].id+'">'+json.data[i].zname+'</option>';
			} 
			$("select[name='jobinfo.job.id']").append(str)
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
	if(root==1){
		if(unum==""||begin==""||end==""){
			$("#unum").removeAttr("readonly");
			$("#unum").focus();
			alert("请输入会员号与会员的起、止日期");
			return false;
		}
	}
	
	
	
	if(!nickNameTag|| !emailTag ||!telTag)
		return false;
	
	
	return true;
}

var nickNameTag=true;
function checknickname(){
	var nick = $("#nickname").val()
	var submitdata = {
		nickname:nick
	};
	$.ajax({
     type: "post",
     url: root+"user/checknickname",
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
     url: root+"user/checkemail",
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
	var tel = $("#telephone").val();
	var submitdata = {
		tel:tel
	};
	$.ajax({
     type: "post",
     url: root+"user/checktel",
     data: submitdata,
     complete:function(data){
     	var result = JSON.parse(data.responseText);
     	if(result.cord!=0){
     		var error='<span class="valid-error" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机已占用，请重新输入</span>';
			$("#telephone").after(error);
			$("#telephone").focus(); //获取焦点
			telTag=false;
     	}else
     	{
     		$("#telephone").next('.valid-error').remove();
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