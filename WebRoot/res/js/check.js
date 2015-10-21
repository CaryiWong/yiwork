
//公共函数判断输入的email地址是否正确。�Ƿ���ȷ��
function checkEmail(str){
 var pattern=/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
  var flag = pattern.test(str);
  return flag;
}
//公共函数判断输入的电话号码是否正确。�����Ƿ���ȷ��
function checkTelNumber(s)
{
	var pt_fax1=/^\d{7,8}$/
	var pt_fax2=/^\d{11,12}$/
	return pt_fax1.test(s) || pt_fax2.test(s);
}

//公共函数判断输入的手机是否正确。�����Ƿ���ȷ��
function checkMobile(s)
{
	var pt_fax1=/^\d{11,12}$/
	return pt_fax1.test(s);
}

//公共函数判断6位邮编�Ƿ���ȷ��
function checkPostcode(str){
 var pattern = /^\d{6}$/;
  var flag = pattern.test(str);
  return flag;
}

function checkNumber(str) 
{     
    var patrn = /^[-\+]?\d+$/;
    return patrn.test(str);
}

function checkInteger(str){
var patrn=/^[-\+]?\d+$/
return patrn.test(str);
}

 
  