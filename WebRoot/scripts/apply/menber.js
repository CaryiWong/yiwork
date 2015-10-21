$(document).ready(function(e) {
	// $(".apply-dialog.hide").removeClass("hide");
	//checkUser(); //如果是登录用户自动加载信息
	$("#menberEmail").blur(function(){verifiUserName()});  //验证email重复
	$("#menberTel").blur(function(){verifiTel()});  //验证手机号重复
	$("#menberNum").blur(function(){verifiIdnum()});  //验证idcard重复
	$("#nickname").blur(function(){verNick()});  //验证nick重复
	//alert();
}); 

/*统一前缀*/

var rootjs= root+"v20/";
if(platform==""||platform==null ||platform=="null"){
	platform="web";
}else{
	platform="mobile";
}

//表单验证
var nickname=false;
var userNametrue=false;
var idnum=false;
var tel=false;

/**
 * 根据UserId验证登录用户
 */
function  checkUser()
{
	var userid =app_user_id;
	if(userid=="" ||userid==null)
		return;
	var tmp=root+"user/getuser";
	$.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"userid":userid,"type":1},
		dataType:"json",
		success: function(json){
			//json.jr.code  0-成功 1-失败
			var type = parseInt(json.cord);
			switch (type){
				case 0: 
					if(json.data.root!=3)
					{
						alert("你已经是会员，无需申请");
						window.location.href=root;
					}
					$("#menberName").val(json.data.realname);  //邮箱
					$("#menberEmail").val(json.data.email);  //用户名-昵称
					$("#menberTel").val(json.data.telnum);  //所在地
					$("#menberAiHao").text(json.data.favourite);  //性别
					//end 以上为基本信息
					return;
			}
		}
	});
}

/**
 * 点支付的时候弹出用户名，密码
 */
function showUserName()
{	
	verifiUserName();  //验证email重复
	verifiTel();  //验证手机号重复
	verifiIdnum();  //验证idcard重复
	verNick();  //验证nick重复
	if(!userNametrue ){
		alert("邮箱已经存在,请重新填写!");
		return;
	}
	if(!tel ){
		alert("手机号码已经存在,请重新填写!");
		return;
	}
	if(!idnum ){
		alert("证件号码已经存在,请重新填写!");
		return;
	}
	if(!nickname ){
		alert("昵称已经存在,请重新填写!");
		return;
	}
	/*if(!userNametrue || !tel || !idnum ||!nickname)
	{
		alert("手机号/邮箱/昵称/身份证有重复, 请检查~");
		return ;
	}*/
	
	$("#loginEmail").text($("#menberEmail").val());
	$("#loginTel").text($("#menberTel").val());	
	
	var $applyDialog = $('.apply-dialog');  
	util.dialog.show($applyDialog);	
	 $applyDialog.find('.apply-dialog-cancel').click(function (){
        util.dialog.close();
    });
	$applyDialog.find('.apply-dialog-sure').click(function (){
        util.dialog.close();
		saveMenber();
    });
}


//验证邮箱重复
function verifiUserName() {
	var str = $("#menberEmail").val();
	var tmp = rootjs + "user/checkemail"; ////验证邮箱是否唯一
	 
	$.ajax( {
		url : tmp,
		type : "POST",
		async : false,
		data : {
			"email" : str
		},
		dataType : "json",
		success : function(json) {
			//json.jr.code 0-成功 1-用户名重复
		var type = parseInt(json.cord);
		switch (type) {
		case -1:
			var error = '<span class="valid-error" >邮箱已占用,请重新输入</span>';
			$("#menberEmail").after(error);
			userNametrue = false;
			break;
		case 0:
			userNametrue = true;
			break;
		}
	}
	});
} 


/**
 * 验证手机号重复
 * @return {TypeName} 
 */

function verifiTel(){
	var str = $("#menberTel").val();
	var tmp=rootjs+"user/checktel";  //验证tel是否唯一
	//if(str!="")
		//$("#menberTel").next('.valid-error').remove();
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
					 	$("#menberTel").after(error);
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


/**
 * 验证身份证号重复
 * @return {TypeName} 
 */
function verifiIdnum(){
	var str = $("#menberNum").val();
	var tmp=rootjs+"user/checkidnum";  //验证idnum是否唯一
	$("#menberNum").children('.valid-error').remove();
	//if(str!="")
		//$("#menberTel").next('.valid-error').remove();
	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"idnum":str,"type":1},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-用户名重复
				var type = parseInt(json.cord);
				switch (type){
					case -1: 
						var error='<span class="valid-error" >身份证号重复,请重新输入</span>';
					 	$("#menberNum").after(error);
						idnum=false;
						return idnum;
						break;
					case 0: 
						idnum = true;
						return idnum;
						break;
				}
			}
		});
}


/**
 * 验证昵称重复
 * @return {TypeName} 
 */

function verNick(){
	var str = $("#nickname").val();
	var tmp=rootjs+"user/checknickname";  //验证昵称是否唯一
	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"nickname":str},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-用户名重复
				var type = parseInt(json.cord);
				switch (type){
					case -1: 
						var error='<span class="valid-error" >昵称重复,请重新输入</span>';
					 	$("#nickname").after(error);
					 	nickname=false;
						return nickname;
						break;
					case 0: 
						nickname = true;
						return nickname;
						break;
				}
			}
		});
}

 
function saveMenber(){
	var oldTime=$("#menberBirthDate").val();
	var d =oldTime.split("-");
	//var time=new Date(d[0],d[1],d[2])
	//time=time.getTime();
    time=oldTime+" 00:00:00";
	
	
	util.loading.show(); //打开loading层
	var tmp=rootjs+"website/addapplyvip";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"type":1,"icnum_type":$("#menberIdType").val(),"name":$("#menberName").val(),"sex":$("#menberSex").val(),"industry.id":$("#menberBus").val(),"job.id":$("#menberJob").val(),"birthday":time,"constellation":$("#menberXingZuo").text(),"idnum":$("#menberNum").val(),"tel":$("#menberTel").val(),"email":$("#menberEmail").val(),"jobyear":$("#menberJobYear").val(),"introduction":$("#menberAiHao").val(),"actnum":$("#menberJoinType").val(),"jobdemand":$("#menberJoinUsType").val(),"channel":$("#menberKnowUsType").val(),"understand":$("#menberLiJie").val(),"result":$("#menberHuoDe").val(),"nickname":$("#nickname").val(),"acttype":$("#menberXiHuan").val()},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
			 	util.loading.close();
			 	alert("申请成功,请付款.");
			 	util.loading.show();
			 	var applyid=json.data.id;
			 	jQuery.ajax({
			 		url:rootjs+"website/becomevip",
			 		type:"post",
			 		async:false,
			 		data:{"type":platform,"applyid":applyid},
			 		dataType:"json",
			 		success:function(json){
			 		   if(json.cord==0){
			 			  //等待付款提示层 
			 			  var $applyDialog1 = $('.apply-dialog1');  
			 			  //util.dialog.show($applyDialog1);	
			 		      $applyDialog1.find('.apply-dialog-cancel.green').click(function (){
			 		    	 checkifpaied(1)
			 			   });
			 			   $applyDialog1.find('.apply-dialog-sure.green').click(function (){
			 				  checkifpaied(2);
			 			   });
			 			  
			 			  $("#payorderinfo").val(json.data);
			 			  $("#payorderid").val();
			 			  //window.open(root+"apply/payforOrder.jsp",'_blank');
			 			   $('body').append($(json.data));
			 		   }else
			 		   {
			 			  alert(json.data2.msg); 
			 			 util.loading.close();
			 		   }
			 		}
			 	})
			 	//window.location.href="http://wd.koudai.com/item.html?itemID=22899715&wfr=dx"; 付款地址
			 	//window.location.reload();
			}else
			{
				alert(json.data2.msg);
				util.loading.close();
				//出现404.或者统一页面.服务器出错
			}
		}
	});
	
	/**
	 * 检测是否付款
	 */
	function checkifpaied(i){
		var orderid = $("#payorderid").val();
		
		switch(i){
		 case 1:
			 $.ajax({
					url: rootjs+"order/get_order",
					type: "POST",
					async:false,
					data:{"order_id":orderid},
					dataType:"json",
					success: function(json){
						//json.jr.code  0-成功 1-用户名重复
						var type = parseInt(json.data.status);
						switch (type){
							case 0: 
								 alert("等待付款,请完成支付");
								break;
							case 1: 
								alert("付款确认中,请稍后.")
								break;
							case 2: 
								 alert("订单已取消,请重新下单");
								 window.location.reload();
								break;
							case 3:
								 alert("交易已超时,请重新下单");
								 window.location.reload();
								break;
							case 4:
							 alert("付款成功,你可以登录网站和APP");
							 util.dialog.close();
							 showTopLogin();
							break;

						}
					}
				});
			 break;
		 case 2:
			 alert("重新跳到支付宝付款");
			 window.open(root+"apply/payforOrder.jsp",'_blank');
		}
	}
	
}

function checkmaco(c){
	 var a = /^[HMhm]{1}([0-9]{10}|[0-9]{8})$/; //m12345623
	 return a.test(c);
}

function checkTaiw(c, a, e)
{
    if ($(e).val() == "G")
    {
        var d = /^[0-9]{8}$/; //123456236
        var b = /^[0-9]{10}$/;
        return this.optional(a) || (d.test(c)) || (b.test(c))
    }
    else {
        return true;
    }
}


function checkPassport(d, b, e)
{
    if ($(e).val() == "B")
    {
        var c = /^[a-zA-Z]{5,17}$/;
        var a = /^[a-zA-Z0-9]{5,17}$/;
        return this.optional(b) || (a.test(d)) || c.test(d)
    }
    else {
        return true;
    }
}

