$(document).ready(function(e){
	loadJobs($("select[name='industry.id']").val());// 加载职业
	$("select[name='industry.id']").change(function(){loadJobs($(this).val())});		 //切换职业
 	$("#tel").blur(function(){verifiTel()});  //验证手机号重复
 	$("#unum").blur(function(){checkunum()});  //验证会员号重复
 	$("#email").blur(function(){verifiEmail()});  //验证邮箱重复
 	$("#nickname").blur(function(){verifiNickName()});  //验昵称重复
 	
 	
 	$("#charge").change(function(){
 		$("#startDiv").show();$("#endDiv").show();
 		if($("#charge").val()==0){
 			$("#startDiv").hide();$("#endDiv").hide();
 		}
 	})
})


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

function validateform(obj) {
		var name = obj.name.value;
		var email = obj.email.value;
		var tel = obj.tel.value;
		var charge = $("#chargetype").val();
		var nickname=obj.nickname.value;
		var birth=obj.birth.value; 
		var begin=obj.begin.value;
		var unum=obj.idnum.value;
		
		if (nickname == "") {
			alert("请输入昵称");
			return false;
		}
		
		if (birth == "") {
			alert("请选择生日");
			return false;
		}
		
		if (unum == "") {
			alert("请输入身份证号");
			return false;
		}
		
		if (name == "") {
			alert("请输入用户姓名");
			return false;
		}
		if(email == ""){
			alert("请输入邮箱");
			return false;
		}
		if (tel == "") {
			alert("请输入手机号");
			return false;
		}
		if(charge==1){
			if($("#unum").val()==""){
				alert("请输入会员号");
				return false;
			}
		}
		
		if(charge==1 && begin=="")
		{
			alert("请选择缴费日期");
			return false;
		}
	
		if(verifiTel()&&checkunum() &&verifiEmail() && verifiNickName())
		{
			return true;	
		}
		 
	}


/**
 * 查看会员号是否占用
 * @param {Object} o
 */
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
     		alert("此会员号已存在，请重新输入");
			$("#unum").val("");
			return true;
     	}else{
     		return false;
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
	$("select[name='job.id']").find("option").remove()
	var tmp=root+"classsort/getclasssortlist";
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
			$("select[name='job.id']").append(str)
		}
	}
	});
}




//验证昵称
var nickname=false;
function verifiNickName(){
	var str = $("input[name=nickname]").val();
	var tmp=root+"user/checknickname";  ////验证昵称是否唯一
	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"nickname":str,"type":1},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-用户名重复
				var type = parseInt(json.cord);
				switch (type){
					case -1: 
						var error='<span class="valid-error" >昵称已占用,请重新输入</span>';
						alert("昵称已被占用,请重新输入")
						$("#nickname").val("");
						nickname=false;
						break;
					case 0: 
						nickname=true;
						break;
				}
			}
		});
}

//验证邮箱
var email=false;
function verifiEmail(){
	var str = $("input[name=email]").val();
  	var tmp=root+"user/checkemail";  ////验证邮箱是否唯一
	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"email":str,"type":1},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-用户名重复
				var type = parseInt(json.cord);
				switch (type){
					case -1: 
						var error='<span class="valid-error" >邮箱已占用,请重新输入</span>';
						alert("邮箱已被占用,请重新输入");
						$("#email").val("");
					 	//$("input[name=email]").after(error);
						email=false;
						break;
					case 0: 
						email=true;
						break;
				}
			}
		});
}

/**
 * 手机号
 * @return {TypeName} 
 */
var tel=false;
function verifiTel(){
	var str = $("input[name=tel]").val();
	var tmp=root+"user/checktel";  //验证昵称是否唯一
	$("input[name=tel]").next('.valid-error').remove();
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
						alert("手机号已被占用,请重新输入")
					 	$("#tel").val("");
						tel=false;
						return tel;
						break;
					case 0: 
						tel = true;
						return tel;
						break;
				}
			}
		});
}




