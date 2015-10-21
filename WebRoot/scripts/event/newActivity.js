$(document).ready(function(e) {
	allowCreate();
	//$("#createEvent").click(function(){saveEvent()});
	loadLables();
});



//活动创建
$('.create-form').submit(function (event) {
    event.preventDefault();
    $(this).valid(function (pass) {
        if (!pass) {
            event.preventDefault();
        }else
        {
        	saveEvent();	
        }
    })
});

/**
 * 非法访问
 */
function allowCreate()
{
	if(session_loginId=="")
	{
		window.location.href=root+"events/activities.jsp"
	}
	if(user_root>2)
	{
		alert("非法访问")
		window.location.href=root+"events/activities.jsp"
	}
	
}




/**
 * 获取标签数据
 */
function loadLables()
{
	//var tmp=root+"labels/getlabellist";
	var tmp=root+"labels/getalllabelgroup";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//加载列表数据
				var str='';
				for(var i=0;i<json.data.area.length;i++){
				 	//str+='<a id='+json.data[i].id+' class="activities-nav-link" href="javascript:void(0)">'+json.data[i].zname+'</a>';
					if(i!=json.data.area.length-1)
                    {
	                    str+='<div class="form-control-customCheckbox custom-checkbox">'
	                    str+='<label class="form-control-customCheckbox-label" id='+json.data.area[i].id+'>'+json.data.area[i].zname+'</label>'
	               		str+="<input class='form-control-checkbox valid-input' id="+json.data.area[i].id+" name='domain' type='checkbox' data-valid-group='domain'  />"
	                    str+='</div>'
                    }else
                    {
                    	var hiddenText=$("#hidden").attr("data-valid-text"); //获取hidden值
                    	str+='<div class="form-control-customCheckbox custom-checkbox">'
                   		str+='<label class="form-control-customCheckbox-label">'+json.data.area[i].zname+'</label>'
               			str+="<input class='form-control-checkbox valid-input' id="+json.data.area[i].id+"  name='domain' type='checkbox' data-valid-group='domain' data-valid-rule='required,max' data-valid-max='1' data-valid-text="+hiddenText+" '/>"
                    	str+='</div>'
                    }
				 	
				}
				jQuery("#event_lables").text("");
				jQuery("#event_lables").append(str);
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}



/**
 * 日期转换 2014-05-09 11:00 PM
 * @param {Object} str
 */
function desDate(str)
{
	if(str.length==18)
	{
		str=str.substring(0,10)+" 0"+str.substring(11,18)
	}
	var year=str.substring(0,4)
	var month=str.substring(5,7)
	var day=str.substring(8,10)
	var hour=str.substring(11,13)
	var min=str.substring(14,17)
	var pmam=str.substring(17,30)
	
	if(pmam=="PM"){
		hour=parseInt(hour)+12;
	}
	var date=new Date(year,month-1,day,hour,min,"00");
	return date;
}


function saveEventImg(obj)
{
	 $.ajaxFileUpload( 
    	 { 
	    	url:root+"activity/createactivity",//用于文件上传的服务器端请求的Action地址 
	     	type:"post",//请求方法 
	     	secureuri:false,//一般设置为false 
	     	data:{"acttype":$("input[name=acttype]").val(),"acttypetitle":$("input[name=acttypetitle]").val(),"acttypeurl":$("input[name=acttypeurl]").val(),type:1,title:$("input[name=title]").val(),price:$("input[name='money']").val(),summary:$("textarea[name=summary]").val(),address:$("input[name=address]").val(),cost:$("input[name=cost]").val(),labels:$("input[name=labels]").val(),tel:$("input[name=tel]").val(),maxnum:$("input[name=maxnum]").val(),opendate1:$("input[name=opendate1]").val(),enddate1:$("input[name=enddate1]").val(),user:session_loginId},
	     	fileElementId:['cover','timg'],//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
	     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
	     	success:function(json)  { 
	     		  json=JSON.parse(json)  //手动转json
	     		  if(json.cord==0)
	     		  {
	     			  util.loading.close();
	    	 	 	  alert("活动已成功提交到后台，请等待审核");
	    	 	 	  window.location.href=root+"events/activities.jsp";
	     			 }else{
	     			   alert("活动创建失败")
	     			   //window.location.href=root+"events/activities.jsp";
	     			  }
	     				
						//window.location.reload();
						//alert(json.cord);//从服务器返回的json中取出message中的数据,其中message为在struts2中定义的成员变量	
						//$.each(msg,function(k,y){ 
						//$("#id").append("&amp;lt;option  &amp;gt;"+y+" &amp;lt;/option&amp;gt;"); 
				//}); 
				 } 
    	 }); 
		 
}



/**
 * 保存活动
 * @return {TypeName} 
 */
function saveEvent()
{
	var titleimg=""; //详情图
	var img="" ; // 小图
	if(session_loginId=="" || session_loginId==null)
	{
		alert("创建活动请先登录!") ;
		return;
	}
	var str1 =  $("input[name=start]").val();  
	$("input[name=opendate1]").val(desDate(str1)); 
		 	 
	var str2 =  $("input[name=end]").val();  
	$("input[name=enddate1]").val(desDate(str2));
		 
	$("input[name=user]").val(session_loginId);  //创建者
	 //$("input[name=address]").val($(".form-control-value").text());  //活动地址
	$("input[name=cost]").val($('input[name="pay"]:checked').val());  //是否收费
	$("input[name=acttype]").val($('input[name="acttypeflag"]:checked').val());  //是否个性化
	
	//是否自定义价格
	var pricekey="";
	var pricevalue=$("input[name='money']").val();
	var pricetype="0" ;  //0单一 1多种
	
	if($(".money-multiple").is(":visible")==true)
	{
		pricetype=1; //
		 
		$("input[name='moneyName']").each(function(){
			if($(this).val()!=""){
				if(pricekey!="" && pricekey.length>0)
				{
					pricekey=pricekey+","+$(this).val();
					pricevalue=pricevalue+","+$(this).next().next().val();
				}else
				{
					pricekey=$(this).val();
					pricevalue=$(this).next().next().val();
				}
			}
				
		})
	}
	
		 var labels="";
		 $('input[name="domain"]:checked').each(function(){
			 if(labels=="")
			 {
				 labels=this.id;
			 }else
			 {
				labels=labels+","+this.id; 
			 }
			
		 });
		 
		 if(labels=="")
		 {
			alert("请选择领域~");
			return;
		 }
		 
		 $("input[name=labels]").val(labels);  //
		 util.loading.show();//显示弹窗层
		
 		//先上传图片，再保存活动  封面图
  		$.ajaxFileUpload( 
     	{ 
	    	url:root+"upload/uploadimgs",//用于文件上传的服务器端请求的Action地址 
	     	type:"post",//请求方法 
	     	secureuri:false,//一般设置为false 
	     	fileElementId:['imgs'],//文件id	属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
	     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
	     	success:function(json)  { 
	    	 	 	   json=JSON.parse(json)  //手动转json
					   if(json.cord==0)
					   {
						  // alert(json.data)
						   titleimg=json.data;
						  // alert(json.data);
					   }else
					   {
						    alert("封面图上传失败,请稍后再试.") 
						    return;
					   }
			 } 
    	 }); 
  		
  			
 		//先上传图片，再保存活动  小图
  		$.ajaxFileUpload( 
     	{ 
	    	url:root+"upload/uploadimg",//用于文件上传的服务器端请求的Action地址 
	     	type:"post",//请求方法 
	     	secureuri:false,//一般设置为false 
	     	fileElementId:'img',//文件id	属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
	     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
	     	success:function(json)  { 
	    	 	 	   json=JSON.parse(json)  //手动转json
					   if(json.cord==0)
					   {
						  img=json.data; 
					   }else
					   {
						    alert("缩略图上传失败,请稍后再试.") 
						    return;
					   }
			 } 
    	 }); 
		 
		 //活动创建
  		 $.ajaxFileUpload( 
    	 { 
	    	url:root+"activity/createactivity",//用于文件上传的服务器端请求的Action地址 
	     	type:"post",//请求方法 
	     	secureuri:false,//一般设置为false 
	     	data:{"acttype":$("input[name=acttype]").val(),"pricetype":pricetype,"pricekey":pricekey,"pricevalue":pricevalue,"img":img,"titleimg":titleimg,"acttypetitle":$("input[name=acttypetitle]").val(),"acttypeurl":$("input[name=acttypeurl]").val(),type:1,title:$("input[name=title]").val(),price:$("input[name='money']").val(),summary:$("textarea[name=summary]").val(),address:$("input[name=address]").val(),cost:$("input[name=cost]").val(),labels:$("input[name=labels]").val(),tel:$("input[name=tel]").val(),maxnum:$("input[name=maxnum]").val(),open:getFullTime($("input[name=opendate1]").val()),end:getFullTime($("input[name=enddate1]").val()),"user.id":session_loginId},
	     	fileElementId:['cover','timg'],//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
	     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
	     	success:function(json)  { 
	     		  json=JSON.parse(json)  //手动转json
	     		  if(json.cord==0)
	     		  {
	     			  util.loading.close();
	    	 	 	  alert("活动已成功提交到后台，请等待审核");
	    	 	 	  window.location.href=root+"events/activities.jsp";
	     			 }else{
	     			   alert(json.msg);
	     			   //window.location.href=root+"events/activities.jsp";
	     			  }
				 } 
    	 }); 
}





//创建活动成功的回调函数
function createActityOk()
{
//layer.close(loadi) //关闭loading层
//layer.msg('活动创建成功!', 2, 1); //显示发送评论层
setInterval("doCountDown();",1000);
//window.location.href="../event/event.jsp";
} 


//定义倒计时时间
var times = 2;
//定义跳转地址
var redirecturl = "../event/events.jsp";
//执行倒计时函数
var doCountDown = function(){
if(times == 0){
//倒计时结束
	location.href = root+"events/activities.jsp";
}
//显示当前倒计数
//document.getElementById("countDown").innerHTML = times;
	times --;
} 