$(document).ready(function(e) {
	$("#yz-fmsubmit").click(function(){
		if(check)
			document.myform.submit();
		 
	});
	
	$("#couponNum").blur(function(){
		checkCoupon();
	})
});


function check(){
	alert(1)
	return check;
}



var rootjs= root+"v20/";
var check=false;
 
function checkCoupon(){
	var couponNum = $("#couponNum").val();
	var regx=/\d{10}/;
	$(".valid-error").remove();
	if(couponNum.length>10){
		$("#couponNum").after("<span class='valid-error'>长度不能大于10位</span>");
		return ;
	} 
	if(!regx.test(couponNum)){
		$("#couponNum").after("<span class='valid-error'>请输入10位整数</span>");
		return ;
	}
	$("#couponNum").after("<span class='valid-error'>ok</span>");
	check=true;
}
