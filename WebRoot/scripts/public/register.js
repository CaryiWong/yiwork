var userNametrue=false;  //邮箱
var nick=false;			//昵称

$(document).ready(function(e) {
	allowCreate();
 	loadLables(); //加载领域
 	loadHangYe(); //加载行业
// 	 var $customCheckbox = $('.custom-checkbox').click(function (){
//        var $custom = $(this).toggleClass('active');
//    })
    //validata();  //重新加载验证方法 	 
 	//根据行业加载jobs
 	 $('select[name=business]').change(function(){
        var id=$(this).children('option:selected').val();  //弹出select的值
        loadJobs(id);
    });
 	$("input[name=email]").blur(function(){verifiUserName();}); //邮箱
 	$("input[name=name]").blur(function(){verifiNickName();});  //用户名
 	//$("#regSubmit").click(function(){submitReg()});
});


/**
 * 非法访问
 */
function allowCreate()
{
	if(session_loginId!="")
	{
		window.location.href=root+"events/activities.jsp"
	}
	
}


/**
 * 加载注册需要的领域
 */
function loadLables()
{
	//var tmp=root+"labels/getlabellist";
	var tmp=root+"labels/getalllabel?type=1";
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
					 var hiddenText=$("#hidden").attr("data-valid-text"); //获取hidden值
				 	 str+='<div class="form-control-customCheckbox custom-checkbox">';
					 str+='<label id="'+json.data[i].id+'" class="form-control-customCheckbox-label">'+json.data[i].zname+'</label>'
					 str+='<input type="checkbox" class="form-control-checkbox valid-input"  data-valid-rule="required,max" data-valid-max="3" data-valid-text='+hiddenText+' name="domain" data-valid-group="domain"  value='+json.data[i].id+' />'
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
	var tmp=root+"classsort/getclasssortlist";
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
			$("select[name=business]").text("");	
			$("select[name=business]").append(str);
			loadJobs(json.data[0].id)
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
				var str='<option value="">请选择</option>';
				for(var i=0;i<json.data.length;i++){
				str+='<option   value="'+json.data[i].id+'">'+json.data[i].zname+'</option>';
			} 
			$("select[name=job]").append(str)
		}
	}
	});
}

//验证用户名
function verifiUserName(){
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
					 	$("input[name=email]").after(error);
						userNametrue=false;
						break;
					case 0: 
					 	userNametrue=true;
						break;
				}
			}
		});
}

/**
 * 昵称验证
 * @return {TypeName} 
 */
function verifiNickName(){
	var str = $("input[name=name]").val();
	
	if(str=="")
		return;
	
	var tmp=root+"user/checknickname";  //验证昵称是否唯一
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
					 	$("input[name=name]").after(error);
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

/**
 * 提交注册
 */
function submitReg(pass)
{
	 
	if(!pass)  //验证不通过
	{
		event.preventDefault();
		return ;
	}
	//获取标签值
	var lables=''; 
	var lablesLength=0
	$(".form-control-customCheckbox-label").each(function(){
		var str=$(this).css("color")
		if(RGBToHex(str)=='#FFFFFF')
		{
			lablesLength=lablesLength+1;
			if(lables.length>0)
			{
				lables=lables+","+$(this).attr("id");
			}else
			{
				lables=$(this).attr("id");
			}
			
		}
		
	})
	
	//保证不能多余3个标签
	if(lablesLength>3)
	{
		return false;	
	}
	
	
	var userName = $("input[name=email]").val();
	var nickName = $("input[name=name]").val();
	var prov= $("select[name=province]").children('option:selected').val();   
	var city= $("select[name=city]").children('option:selected').val();   
	var pwd=$("input[name=password]").val();
	var hangye=$("select[name=business]").children('option:selected').val();   
	var jobs=$("select[name=job]").children('option:selected').val();   
	;
	
	
	
	
	 
	
	var tmp=root+"user/register";  //验证昵称是否唯一
	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"email":userName,"password":($.md5(pwd)),"nickname":nickName,"province":prov,"city":city,"industry.id":hangye,"job.id":jobs,"labels":lables,"type":1},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-用户名重复
				var type = parseInt(json.cord);
				switch (type){
					case -1: 
						alert("系统错误,请稍后再试")
						break;
					case 0: 
						alert("注册成功");
						//存用户session
						loginUser(userName,pwd);
						window.location.href=root+"events/activities.jsp";
						break;
				}
			}
		});
}

/**
 * 注册之后存用户session
 * @param {Object} userName
 * @param {Object} pwd
 */
function loginUser(userName,pwd)
{
	var tmp=root+"user/login";  //验证昵称是否唯一
	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"username":userName,"password":($.md5(pwd)),"type":1},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-用户名重复
				var type = parseInt(json.cord);
				switch (type){
					//case 0:alert("登录成功")
				}
			}
		});
}


/**
 * 把RGB颜色转换为#
 * @param {Object} rgb
 * @return {TypeName} 
 */
function RGBToHex(rgb){ 
   var regexp = /[0-9]{0,3}/g;  
   var re = rgb.match(regexp);//利用正则表达式去掉多余的部分，将rgb中的数字提取
   var hexColor = "#"; var hex = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'];  
   for (var i = 0; i < re.length; i++) {
        var r = null, c = re[i], l = c; 
        var hexAr = [];
        while (c > 16){  
              r = c % 16;  
              c = (c / 16) >> 0; 
              hexAr.push(hex[r]);  
         } hexAr.push(hex[c]);
         if(l < 16&&l != ""){        
             hexAr.push(0)
         }
       hexColor += hexAr.reverse().join(''); 
    }  
   //alert(hexColor)  
   return hexColor;  
} 


