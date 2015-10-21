$(document).ready(function(e) {
	
});

/**
 * 发送私信
 * @param subId			发送人ID
 * @param readyId			接收人ID
 * @param contentID		内容ID
 */
function subSixin(subId, readyId, contentID){
	if($("#"+contentID).val()=='' || $("#"+contentID).val()==null){
		alert("您还没有写私信内容");
		return;
	}
	var tmp=root+"/api/createSx";
	$.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"sx.u0": subId, "sx.sx1":readyId, "sx.sx2":$("#"+contentID).val(), "sx.t0":1},
		dataType:"json",
		success: function(json){
			if(json.code==0){
				$("#"+contentID).val("")
				alert("私信发送成功");
			}else{
				alert("服务器忙，请稍后再试");
			}
		}
	})
}

/**
 * 跳转到圈子页面
 * @param cid	圈子ID
 */
function goCircle(cid){
	window.location.href = root+"/circle/yqCircle.jsp?cid="+cid;
}

/**
 * 前往用户个人页面
 * @param lookId		查看者ID
 * @param lookedId		被查看者ID
 */
function goUserPage(lookId, lookedId){
	if(lookId==lookedId){
		window.location.href = root+"/user/myUserInfo.jsp";
	}else{
		window.location.href = root+"/user/otherUserInfo.jsp?u0="+lookedId;
	}
}

/**
 * 改变按钮背景
 * @param btnId
 * @param type
 */
function changeBtnBg(btnId, type){
	if(type==1){
		$("#"+btnId).css("background-image","url("+root+"/circle/images/buttonbg2.png)");
	}else{
		$("#"+btnId).css("background-image","url("+root+"/circle/images/buttonbg.png)");
	}
}

/**
 * 打开用户页面
 * @param uid
 * @param goUid
 */
function userInfoJsp(uid, goUid){
	if(uid==goUid){
		window.open(root+'/user/myUserInfo.jsp?u0='+goUid);
	}else{
		window.open(root+'/user/otherUserInfo.jsp?u0='+goUid);
	}
}

/**
 * 根据毫秒数获取普通日期
 * @param {Object} tignoreCase     true or false 
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
String.prototype.getPtDate = function(ignoreCase) {
	var newTime = new Date(); 
	newTime.setTime(this);
	var year = newTime.getFullYear();
	var month = newTime.getMonth();
	if(parseInt(month)+1<10){
		month = '0'+month;
	}
	var day = newTime.getDate();
	if(parseInt(day)<10){
		day = '0'+day;
	}
	
	if(ignoreCase!=null && ignoreCase){	//返回中文格式
		return year+"年"+month+"月"+day+'日';
	}else{	//返回普通格式
		return year+"-"+month+"-"+day;
	}
}

/**
 * 字符串替换
 * @param {Object} reallyDo		检索的字符串
 * @param {Object} replaceWith	要替换成的字符串
 * @param {Object} ignoreCase
 * @memberOf {TypeName} 
 * @return {TypeName} 
 */
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) { 
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}

/**
 * 截取字符串
 * @param {Object} str	原字符串
 * @param {Object} sum	截取长度
 * @return {TypeName} 
 */
function getStr(str,sum){
try{
	if(str==null||str=="null"){
		return "";
	}else{
		var sstr = "";
	   	if(getBytesCount(str)>sum){	
		   var n = 0;
		   for(i = 0; i < sum; i++){
			   	n ++;
				var c = str.charAt(i);
				if (/^[\u0000-\u00ff]$/.test(c)){
					//sum -= 1;
				}else{
					i += 1;
				}
			}
			str = str.substring(0,n)+"...";
		}
	}
	return str;
	}catch(e){
	return "";
	}
}

/**
 * 获取字符串长度
 * @param {Object} str
 * @return {TypeName} 
 */
function getBytesCount(str){
	var bytesCount = 0;
	if (str != null){
		for (var i = 0; i < str.length; i++){
			var c = str.charAt(i);
			if (/^[\u0000-\u00ff]$/.test(c)){
				bytesCount += 1;
			}else{
				bytesCount += 2;
			}
		}
	}
	return bytesCount;
}



/**
 * by kcm Add two methods 10.25
 * 
 */


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
	
//改变框框颜色和字体颜色
function changeColor(oTarget)
{
		var buttonColor=RGBToHex(oTarget.style.backgroundColor);
		if(buttonColor=='#FFFFFF'){
			oTarget.style.backgroundColor = '#B1E147';  //绿色
			oTarget.style.color = '#FFFFFF';  
			
		}else{
			oTarget.style.backgroundColor = '#FFFFFF';
			oTarget.style.color = '#333333'; 
		};
}





// 获取页面的高度、宽度
function getPageSize() {
    var xScroll, yScroll;
    if (window.innerHeight && window.scrollMaxY) {
        xScroll = window.innerWidth + window.scrollMaxX;
        yScroll = window.innerHeight + window.scrollMaxY;
    } else {
        if (document.body.scrollHeight > document.body.offsetHeight) { // all but Explorer Mac    
            xScroll = document.body.scrollWidth;
            yScroll = document.body.scrollHeight;
        } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari    
            xScroll = document.body.offsetWidth;
            yScroll = document.body.offsetHeight;
        }
    }
    var windowWidth, windowHeight;
    if (self.innerHeight) { // all except Explorer    
        if (document.documentElement.clientWidth) {
            windowWidth = document.documentElement.clientWidth;
        } else {
            windowWidth = self.innerWidth;
        }
        windowHeight = self.innerHeight;
    } else {
        if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode    
            windowWidth = document.documentElement.clientWidth;
            windowHeight = document.documentElement.clientHeight;
        } else {
            if (document.body) { // other Explorers    
                windowWidth = document.body.clientWidth;
                windowHeight = document.body.clientHeight;
            }
        }
    }       
    // for small pages with total height less then height of the viewport    
    if (yScroll < windowHeight) {
        pageHeight = windowHeight;
    } else {
        pageHeight = yScroll;
    }    
    // for small pages with total width less then width of the viewport    
    if (xScroll < windowWidth) {
        pageWidth = xScroll;
    } else {
        pageWidth = windowWidth;
    }
    arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight);
    return arrayPageSize;
}


/*截取字符串*/
function getStr(str,sum){
try{
	if(str==null||str=="null"){
		return "";
	}else{
		var sstr = "";
	   	if(getBytesCount(str)>sum){	
		   var n = 0;
		   for(i = 0; i < sum; i++){
			   	n ++;
				var c = str.charAt(i);
				if (/^[\u0000-\u00ff]$/.test(c)){
					//sum -= 1;
				}else{
					i += 1;
				}
			}
			str = str.substring(0,n)+"...";
		}
	}
	return str;
	}catch(e){
	return "";
	}
}

/*获取字符串字节长度*/
function getBytesCount(str){
	var bytesCount = 0;
	if (str != null){
		for (var i = 0; i < str.length; i++){
			var c = str.charAt(i);
			if (/^[\u0000-\u00ff]$/.test(c)){
				bytesCount += 1;
			}else{
				bytesCount += 2;
			}
		}
	}
	return bytesCount;
}
