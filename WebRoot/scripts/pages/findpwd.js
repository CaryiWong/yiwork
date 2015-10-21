$(document).ready(function(e) {
 	$(".form-control-text").blur(function(){checkMail();}); //邮箱
 	$("#findBtn").click(function(){sendPost()}); 
 	$("#sucsBtn").click(function(){window.location.href=root;})  //确定之后跳到主页
 	$("#shangyibu").click(function(){
 		$("#main").show();
		$("#success").hide();
	})
});

/*统一前缀*/
var rootjs= root+"v20/";


var mail=false;
function checkMail()
{
	$(".form-control-text").next('.valid-error').remove();
	//var test=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
	var test=/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/
	var str=$(".form-control-text").val();
	if(!test.exec(str)){
		 var error='<span class="valid-error" >邮箱格式不正确</span>';
		 $(".form-control-text").after(error);
	}else
	{
		mail=true;	
	}
	
}

function sendPost()
{
	if(mail)
	{
		util.loading.show();
		var str = $(".form-control-text").val();
		var tmp=rootjs+"user/applyresetpwd";  //验证昵称是否唯一
		$.ajax({
			url: tmp,
			type: "POST",
			data:{"email":str},
			dataType:"json",
			success: function(json){
				//json.jr.code  0-成功 1-用户名重复
				var type = parseInt(json.cord);
				switch (type){
					case -1: 
						util.loading.close();
					 	alert(json.msg)
						break;
					case 0: 
						 util.loading.close();
						 $("#main").hide();
						 $("#success").show();
						break;
					case 1:
						util.loading.close();
						alert(json.msg)
						break;
				}
			}
		});
	}
}

