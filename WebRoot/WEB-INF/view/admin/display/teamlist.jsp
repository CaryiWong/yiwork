<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>入驻团队列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script type="text/javascript" src="<%=basePath%>res/js/jquery-1.9.1.min.js"></script>
<style type="text/css">
/*custom_list_order*/
.custom_order {overflow-y:auto; float:left; margin-left:8px; padding:6px; width:1200px; height:368px; border:1px solid #80DDF9;position: absolute;}
.custom_order ul {position:relative; margin:0 0 0 0px;overflow:hidden;zoom:1; _margin-left:-2px;}
.custom_order li {float:left; margin:0 14px;padding:0; width:120px; height:90px; border:1px solid #c2c2c2; text-align:center; cursor:pointer;overflow:hidden;}
.custom_order li.drag_li {float:left; margin:12px 14px; width:120px; height:90px; border:5px solid #c2c2c2; text-align:center; cursor:pointer;overflow:hidden;}
.custom_order li img{width:60px;height:60px}
.custom_order .modbox {float:left; margin:12px 14px; width:120px; height:90px; border:1px solid #c2c2c2; text-align:center; cursor:pointer;}
.custom_order li table {margin:8px 9px; width:120px; height:90px; border-collapse:collapse; text-align:center; vertical-align:middle; table-layout:fixed; overflow:hidden;}
.custom_order li p {margin:0 auto; width:100px; overflow:hidden; white-space:nowrap;}
.custom_order .no_drag {width:120px; height:90px; overflow:hidden; padding:0; border:0;}
.custom_order .pre_sort {margin:10px auto -10px 25px; color:#666;}
.custom_order .pre_sort a{margin-left:2px; margin-right:2px; color:#666;}
.custom_order .pre_sort a.on {color:#f60;}
.custom_order .v_middle{width:120px;height:90px;font-size:12px;border:none;}
.myimgcs {display: none;}
.myspancs {}
</style>
<script type="text/javascript">
var root="<%=basePath%>";
var this_div    = 'custom_order';
var is_saved = false;
var old_sort = '';
var new_sort = '';
var pianYi=0;
/********p********/
var   Class   =   { 
      create:   function()   { 
          return   function()   { 
              this.initialize.apply(this,   arguments); 
          } 
      } 
} 
    
$$ = document.getElementsByClassName = function(classname){
     var elems = [];
     var alls = document.getElementsByTagName('*'); 
     for(var i=0; i<alls.length; i++){
          if(alls[i].className && alls[i].className == classname){
               elems.push(alls[i]);     
          }
     }
     return elems;
}
function $A(iterable) {
if (!iterable) return [];
if (iterable.toArray) return iterable.toArray();
var length = iterable.length, results = new Array(length);
while (length--) results[length] = iterable[length];
return results;
}

Function.prototype.bind=function(){
    if (arguments.length < 2 && arguments[0] === undefined) return this;
    var __method = this, args = $A(arguments), object = args.shift();
    return function() {
      return __method.apply(object, args.concat($A(arguments)));
    }
}

var Position = {
positionedOffset:   function(element)   { 
          var valueT =0, valueL = 0; 
          //do { 
              valueT += element.offsetTop||0; 
              valueL += element.offsetLeft||0; 
              element = element.offsetParent; 
              if (element){ 
                  p = element.style.position;
                  if (p == 'relative'||p == 'absolute') {
                	 // break; 
                  }
              } 
          //}while  (element); 
          return   [valueL,   valueT]; 
      }
};    
/**********p*********/

function save_sort(jurl,jdiv,jfunc){
    is_saved = true;
    new_sort="";
    var arr=new Array();
    var tmpElements = document.getElementsByClassName('myimgcs');
    for(var i=0;i<tmpElements.length;i++)
    {
    	arr[i]=tmpElements[i].value;
    }
 	var tmp=root+"display/saveorderlist"
 	$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"ids":arr,"idtype": 1,"type":1},
			dataType:"json",
			success: function(json){
				if(json.cord==0){
					alert("保存成功！");
				}else{
					alert("保存失败！");
				}
				getData();
			}
		});
 	
}

// drag.js
var Drag = {
    // 对这个element的引用，一次只能拖拽一个Element
    obj: null , 
    /**
    * @param: elementHeader    used to drag..
    * @param: element            used to follow..
    */
    init: function(elementHeader, element) {
        // 将 start 绑定到 onmousedown 事件，按下鼠标触发 start
        elementHeader.onmousedown = Drag.start;
        // 将 element 存到 header 的 obj 里面，方便 header 拖拽的时候引用
        elementHeader.obj = element;
        // 初始化绝对的坐标，因为不是 position = absolute 所以不会起什么作用，但是防止后面 onDrag 的时候 parse 出错了
        if(isNaN(parseInt(element.style.left))) {
            element.style.left = "0px";
        }
        if(isNaN(parseInt(element.style.top))) {
            element.style.top = "0px";
        }
        // 挂上空 Function，初始化这几个成员，在 Drag.init 被调用后才帮定到实际的函数
        element.onDragStart = new Function();
        element.onDragEnd = new Function();
        element.onDrag = new Function();
    },
    // 开始拖拽的绑定，绑定到鼠标的移动的 event 上
    start: function(event) {
        var element = Drag.obj = this.obj;
        // 解决不同浏览器的 event 模型不同的问题
        event = Drag.fixE(event);
        // 看看是不是左键点击
        if(event.which != 1){
            // 除了左键都不起作用
            return true ;
        }
        // 参照这个函数的解释，挂上开始拖拽的钩子
        element.onDragStart();
        var custom_order=document.getElementById("custom_order");// 最外层div
        var num=custom_order.scrollTop+event.clientY;
        pianYi=64-(num%128);
        // 记录鼠标坐标
        element.lastMouseX = event.clientX;
        element.lastMouseY = event.clientY;
        // 绑定事件
        document.onmouseup = Drag.end;
        document.onmousemove = Drag.drag;
        return false ;
    }, 
    // Element正在被拖动的函数
    drag: function(event) {
        event = Drag.fixE(event);
        if(event.which == 0 ) {
             return Drag.end();
        }
        // 正在被拖动的Element
        var element = Drag.obj;
        // 鼠标坐标
        var _clientX = event.clientY;
        var _clientY = event.clientX;
        var eventUpOrDown="up";
        // 如果鼠标没动就什么都不作
        if(element.lastMouseX == _clientY && element.lastMouseY == _clientX) {
            return    false ;
        }else if(element.lastMouseY<_clientX){
        	eventUpOrDown="down";
        }else if(element.lastMouseY>=_clientX){
        	eventUpOrDown="up";
        }
        var custom_order=document.getElementById("custom_order");// 最外层div
        //document.getElementById("xy").value=pianYi;
        if(eventUpOrDown=="up"){
        	if(pianYi>0){
        		_clientX-=pianYi;
        	}else{
        		_clientX+=pianYi;
        	}
	        var num=0;
	        if(0<=_clientX&&_clientX<375){
        		num=0;
	        }else if( _clientX<15&&custom_order.scrollTop>0){
	       		custom_order.scrollTop=custom_order.scrollTop+_clientX;
        		num+=_clientX;
	        }else {
	        	num=0;
	        }
	        // 刚才 Element 的坐标
	        var _lastX = parseInt(element.style.top);
	        var _lastY = parseInt(element.style.left);
	        // 新的坐标
	        var newX, newY;
	        // 计算新的坐标：原先的坐标+鼠标移动的值差
	        newX = _lastY + _clientY - element.lastMouseX;
	        if(pianYi>0){
	        	 newY = _lastX + _clientX - (element.lastMouseY-pianYi)+num;
	        }else{
	        	 newY = _lastX + _clientX - (element.lastMouseY+pianYi)+num;
	        }
	        // 修改 element 的显示坐标
	        element.style.left = newX + "px";
	        element.style.top = newY + "px";
	        // 记录 element 现在的坐标供下一次移动使用
	        element.lastMouseX = _clientY;
	        if(pianYi>0){
        		element.lastMouseY = _clientX+pianYi;
        	}else{
        		element.lastMouseY = _clientX-pianYi;
        	}
	        // 参照这个函数的解释，挂接上 Drag 时的钩子
	        element.onDrag(newX, newY);
	        return false;
        }else{
	        var num=0;
	        if(pianYi>0){
        		_clientX+=pianYi;
        	}else{
        		_clientX-=pianYi;
        	}
	        if(_clientX<=375){
	        	num=0;
	        }else if(_clientX>375&&custom_order.scrollTop<(custom_order.scrollHeight-412)){
	       		custom_order.scrollTop=custom_order.scrollTop+(_clientX-375);
	        	num+=(_clientX-375);
	        }else{
	        	num=0;
	        }
	        // 刚才 Element 的坐标
	        var _lastX = parseInt(element.style.top);
	        var _lastY = parseInt(element.style.left);
	        // 新的坐标
	        var newX, newY;
	        // 计算新的坐标：原先的坐标+鼠标移动的值差
	        newX = _lastY + _clientY - element.lastMouseX;
	        if(pianYi>0){
	        	 newY = _lastX + _clientX - (element.lastMouseY+pianYi)+num;
	        }else{
	        	 newY = _lastX + _clientX - (element.lastMouseY-pianYi)+num;
	        }
	        // 修改 element 的显示坐标
	        element.style.left = newX + "px";
	        element.style.top = newY + "px";
	        // 记录 element 现在的坐标供下一次移动使用
	        element.lastMouseX = _clientY;
	         if(pianYi>0){
        		element.lastMouseY = _clientX-pianYi;
        	}else{
        		element.lastMouseY = _clientX+pianYi;
        	}
	        // 参照这个函数的解释，挂接上 Drag 时的钩子
	        element.onDrag(newX, newY);
	        return false;
        }
    },
    // Element 正在被释放的函数，停止拖拽
    end: function(event) {
        event = Drag.fixE(event);
        // 解除事件绑定
        document.onmousemove = null;
        document.onmouseup = null;
        // 先记录下 onDragEnd 的钩子，好移除 obj
        var _onDragEndFuc = Drag.obj.onDragEnd();
        // 拖拽完毕，obj 清空
        Drag.obj = null ;
        return _onDragEndFuc;
    },
    // 解决不同浏览器的 event 模型不同的问题
    fixE: function(ig_) {
        if( typeof ig_ == "undefined" ) {
            ig_ = window.event;
        }
        if( typeof ig_.layerX == "undefined" ) {
            ig_.layerX = ig_.offsetX;
        }
        if( typeof ig_.layerY == "undefined" ) {
            ig_.layerY = ig_.offsetY;
        }
        if( typeof ig_.which == "undefined" ) {
            ig_.which = ig_.button;
        }
        return ig_;
    }
};

var DragDrop = Class.create();
DragDrop.prototype = {
    initialize: function(elementHeader_id , element_id){
        var element = document.getElementById(element_id);
        var elementHeader = document.getElementById(elementHeader_id);
        this._dragStart = ((typeof this.start_Drag == "function") ? this.start_Drag : start_Drag); 
        this._drag = ((typeof this.when_Drag == "function") ? this.when_Drag : when_Drag);
        this._dragEnd = ((typeof this.end_Drag == "function") ? this.end_Drag : end_Drag);
        this._afterDrag = ((typeof this.after_Drag == "function") ? this.after_Drag : after_Drag);
        this.isDragging = false;
        this.elm = element;
        this.hasIFrame = this.elm.getElementsByTagName("IFRAME").length > 0;
        if( elementHeader) {
            elementHeader.style.cursor = "move";
            Drag.init(elementHeader, this.elm);
            this.elm.onDragStart = this._dragStart.bind(this);
            this.elm.onDrag = this._drag.bind(this);
            this.elm.onDragEnd = this._dragEnd.bind(this);
        }
    }
};

// google_drag.js
var DragUtil = new Object();
// 获得浏览器信息
DragUtil.getUserAgent = navigator.userAgent;
DragUtil.isGecko = DragUtil.getUserAgent.indexOf("Gecko") != -1;
DragUtil.isOpera = DragUtil.getUserAgent.indexOf("Opera") != -1;
// 计算每个可拖拽的元素的坐标
DragUtil.reCalculate = function(el) {
    for( var i = 0 ; i < DragUtil.dragArray.length; i++ ) {
        var ele = DragUtil.dragArray[i];
        var position = Position.positionedOffset(ele.elm);
        ele.elm.pagePosLeft = position[0];
        ele.elm.pagePosTop = position[1];
    }
};
// 拖动元素时显示的占位框
DragUtil.ghostElement = null ;
DragUtil.getGhostElement = function(){
    if(!DragUtil.ghostElement){
        DragUtil.ghostElement = document.createElement("DIV");
        DragUtil.ghostElement.className = "modbox";
        DragUtil.ghostElement.style.border = "5px dashed #aaa";
        DragUtil.ghostElement.innerHTML = "&nbsp;";
    }
    return DragUtil.ghostElement;
};


/***************drag.js*****************/

// 初始化所有可拖拽的元素，依靠 className 来确定是否可拖拽，可拖拽的部分的 id 为该元素 id 加上 _h
var initDrag = function() {
    var tmpElements = document.getElementsByClassName('drag_li');
    DragUtil.dragArray = new Array();
    for(var i = 0 ; i < tmpElements.length ; i++){
        var tmpElement = tmpElements[i];
        var tmpElementId = tmpElement.id;
        var tmpHeaderElementId = tmpElement.id + '_h';
        DragUtil.dragArray[i] = new DragDrop(tmpHeaderElementId , tmpElementId);
    }
};
// 获取外部样式表中的属性
function getRealStyle(element,styleName) {
    var realStyle = null;
    if(element.currentStyle)
        realStyle = element.currentStyle[styleName];
    else if(window.getComputedStyle)
        realStyle = window.getComputedStyle(element, null)[styleName];
    return realStyle;
}
DragUtil.curTop = 0, DragUtil.curLeft = 0; // 当前拖拽元素的位置
DragUtil.lastTop = 0, DragUtil.lastLeft = 0; // 开始拖拽的元素的位置
DragUtil.rangeX = 0, DragUtil.rangeY = 0; // 拖动的响应范围的修正值
// 开始拖拽
function start_Drag(){
    DragUtil.reCalculate(this);    // 重新计算所有可拖拽元素的位置
    this.origNextSibling = this.elm.nextSibling;
    var _ghostElement = DragUtil.getGhostElement();
    var offH = this.elm.offsetHeight;
//    if(DragUtil.isGecko){    // 修正 Gecko 引擎的怪癖
        offH -= parseInt(_ghostElement.style.borderTopWidth) *   2 ;
//    }
    var offW = this.elm.offsetWidth;
    var position = Position.positionedOffset(this.elm);
    var offLeft = position[0];
    var offTop = position[1];
    //alert(offLeft+"||"+offTop);
    // 在元素的前面插入占位虚线框
    _ghostElement.style.height = offH + "px";
    this.elm.parentNode.insertBefore(_ghostElement, this.elm.nextSibling);
    // 设置元素样式属性  -10是因为装图片的层的边框
    this.elm.style.width = offW-10+ "px";
    this.elm.style.position = "absolute";
    this.elm.style.zIndex = 100;
    var tmpMarginL = parseInt(getRealStyle(this.elm, 'marginLeft'));
    var tmpMarginT = parseInt(getRealStyle(this.elm, 'marginTop'));
    this.elm.style.left = offLeft - tmpMarginL/2 + 'px';
    this.elm.style.top = offTop - tmpMarginT/2 + 'px';
    this.isDragging = false;
//#
    DragUtil.lastLeft = parseInt(this.elm.style.left);
    DragUtil.lastTop = parseInt(this.elm.style.top);
    // 修正值为：水平超过元素的宽度的一半，垂直超过元素的高度的一半
    DragUtil.rangeX = parseInt(offW / 2);
    DragUtil.rangeY = parseInt(offH / 2);
    return false;
}
// 拖动时触发这个函数（每次鼠标坐标变化时）
function when_Drag(clientX , clientY){
    if (!this.isDragging){    // 第一次移动鼠标，设置它的样式
        this.elm.style.filter = "alpha(opacity=70)";
        this.elm.style.opacity = 0.7;
        this.isDragging = true;
    }
    // 计算离当前鼠标位置最近的可拖拽的元素，把该元素放到 found 变量中
    var found = null;
    var max_distance = 100000000;
    for(var i = 0 ; i < DragUtil.dragArray.length; i++) {
        var ele = DragUtil.dragArray[i];
        DragUtil.curLeft = parseInt(this.elm.style.left);
        DragUtil.curTop = parseInt(this.elm.style.top);
        var distance = Math.sqrt(Math.pow(clientX - ele.elm.pagePosLeft, 2 ) + Math.pow(clientY - ele.elm.pagePosTop, 2 ));
//        if(ele == this ) {
//            continue;
//        }
        if(isNaN(distance)){
            continue;
        }
        if(distance < max_distance){
            max_distance = distance;
            found = ele;
        }
    }
    if(DragUtil.curTop > (DragUtil.lastTop+DragUtil.rangeY) || DragUtil.curTop >= (DragUtil.lastTop-DragUtil.rangeY) && 

DragUtil.curLeft > (DragUtil.lastLeft+DragUtil.rangeX)) {
        direction = 1;
    } else if(DragUtil.curTop < (DragUtil.lastTop-DragUtil.rangeY) || DragUtil.curTop >= (DragUtil.lastTop-DragUtil.rangeY) 

&& DragUtil.curLeft < (DragUtil.lastLeft-DragUtil.rangeX)) {
        direction = -1;
    } else return;
    // 把虚线框插到 found 元素的前面
    var _ghostElement = DragUtil.getGhostElement();
//    if(found != null && _ghostElement.nextSibling != found.elm) {
    if(found != null) {
        if(direction == -1) {
            found.elm.parentNode.insertBefore(_ghostElement, found.elm);
        } else if(direction == 1) {
            found.elm.parentNode.insertBefore(_ghostElement, found.elm.nextSibling);
        }
        direction = '';
        if(DragUtil.isOpera){    // Opera 的毛病
            document.body.style.display = "none";
            document.body.style.display = "";
        }
    }
}
// 结束拖拽
function end_Drag(){
    if(this._afterDrag()){
        // 在这可以做一些拖拽成功后的事，比如 Ajax 通知服务器端修改坐标，以便下次用户进来时位置不变
        showtab();
    }
    return true;
}
// 结束拖拽时调用的函数
function after_Drag(){
    var returnValue = false;
    // 把拖动的元素的样式回复到原来的状态
    DragUtil.curTop = 0;
    DragUtil.curLeft = 0;
    DragUtil.lastTop = 0;
    DragUtil.lastLeft = 0;

    this.elm.style.position = "";
    this.elm.style.top = "";
    this.elm.style.left = "";
    this.elm.style.width = "";
    this.elm.style.zIndex = "";
    this.elm.style.filter = "";
    this.elm.style.opacity = "";
    // 在虚线框的地方插入拖动的这个元素
    var ele = DragUtil.getGhostElement();
    if(ele.nextSibling != this.origNextSibling) {
        ele.parentNode.insertBefore(this.elm, ele.nextSibling);
        //#需要对dragArray相应调整
        returnValue = true;
    }
    // 删除虚线框
    ele.parentNode.removeChild(ele);
    if(DragUtil.isOpera) {
        document.body.style.display = "none";
        document.body.style.display = "" ;
    }
    return returnValue;
}
</script>	
	
<script type="text/javascript">
function doinit(){
	getData();
}
var dataNum=0;
function getData(){
	 var tmp=root+"display/getteamlist";
	 document.getElementById("sort_div").innerHTML='';
	document.getElementById("showtable").innerHTML='';
	 var str="";
	 var tr1=tr2=tr3=tr4=tr5=tr6=tr7=tr8="";
		$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"page":0,"size": 100,"type":1},
			dataType:"json",
			success: function(json){
				if(json.data.length>0){
					tr1='<tr align="center" bgcolor="red">';
	 				tr2="<tr>";
	 				dataNum=1;
				}else if(json.data.length>25){
					tr3='<tr align="center" bgcolor="red">';
	 				tr4="<tr>";
	 				dataNum=2;
				}else if(json.data.length>50){
					tr5='<tr align="center" bgcolor="red">';
	 				tr6="<tr>";
	 				dataNum=3;
				}else if(json.data.length>75){
					tr7='<tr align="center" bgcolor="red">';
	 				tr8="<tr>";
	 				dataNum=4;
				}
				for(var i=0;i<json.data.length;i++){
					str+='<li id="drag_'+i+'" class="drag_li" style="left: 0px; top: 0px;">';
			      	str+='<div class="v_middle" id="drag_'+i+'_h" style="cursor: move;">';
			      	str+='<input class="myimgcs" id="'+json.data[i].id+'" value="'+json.data[i].id+'" />';
			      	str+='<span class="myspancs">'+json.data[i].teamname+'</span>';
			      	str+='</br><img src="'+root+'/download/img?path='+json.data[i].teamminim+'" />';
			      	str+='</div></li>';
			      	if(i<25){
			      		tr1+='<td>'+(i+1)+'</td>';
			      		tr2+='<td>'+json.data[i].teamname;
			      		tr2+='<input type="button" value="删除" onclick="delobj(\''+json.data[i].id+'\')"><input type="button" value="修改" onclick="updateobj(\''+json.data[i].id+'\')"></td>';
			      	}else if(i>24&&i<50){
			      		tr3+='<td>'+(i+1)+'</td>';
			      		tr4+='<td>'+json.data[i].teamname;
			      		tr4+='<input type="button" value="删除" onclick="delobj(\''+json.data[i].id+'\')"><input type="button" value="修改" onclick="updateobj(\''+json.data[i].id+'\')"></td>';
			      	}else if(i>49&&i<75){
			      		tr5+='<td>'+(i+1)+'</td>';
			      		tr6+='<td>'+json.data[i].teamname;
			      		tr8+='<input type="button" value="删除" onclick="delobj(\''+json.data[i].id+'\')"><input type="button" value="修改" onclick="updateobj(\''+json.data[i].id+'\')"></td>';
			      	}else if(i>74&&i<100){
			      		tr7+='<td>'+(i+1)+'</td>';
			      		tr8+='<td>'+json.data[i].teamname;
			      		tr8+='<input type="button" value="删除" onclick="delobj(\''+json.data[i].id+'\')"><input type="button" value="修改" onclick="updateobj(\''+json.data[i].id+'\')"></td>';
			      	}
				}
			}
		});
		if(dataNum==0){
			
		}else if(dataNum>0){
			tr1+='</tr>';
			tr2+='</tr>';
		}else if(dataNum>1){
			tr3+='</tr>';
			tr4+='</tr>';
		}else if(dataNum>2){
			tr5+='</tr>';
			tr6+='</tr>';
		}else if(dataNum>3){
			tr7+='</tr>';
			tr8+='</tr>';
		}
	str+=' <div class="drag_li no_drag" id="col_1_hidden_div" style="left:0px;top:0px;">';
    str+=' <div id="col_1_hidden_div_h" style="cursor: move;"></div></div>';
	document.getElementById("sort_div").innerHTML=str;
	document.getElementById("showtable").innerHTML='<table border="1" width="100%">'+tr1+tr2+tr3+tr4+tr5+tr6+tr7+tr8+'</table>';
	initDrag();
}

function showtab(){
	var tmpElements = document.getElementsByClassName('myspancs');
	var tmpids = document.getElementsByClassName('myimgcs');
	if(tmpElements.length>0){
		tr1='<tr align="center" bgcolor="red">';
		tr2="<tr>";
	}else if(tmpElements.length>25){
		tr3='<tr align="center" bgcolor="red">';
		tr4="<tr>";
	}else if(tmpElements.length>50){
		tr5='<tr align="center" bgcolor="red">';
		tr6="<tr>";
	}else if(tmpElements.length>75){
		tr7='<tr align="center" bgcolor="red">';
		tr8="<tr>";
	}
    for(var i=0;i<tmpElements.length;i++)
    {
       if(i<25){
    	   	tr1+='<td>'+(i+1)+'</td>';
      		tr2+='<td>'+tmpElements[i].innerHTML;
      		tr2+='<input type="button" value="删除" onclick="delobj(\''+tmpids[i].value+'\')"><input type="button" value="修改" onclick="updateobj(\''+tmpids[i].value+'\')"></td>';
      	}else if(i>24&&i<50){
      		tr3+='<td>'+(i+1)+'</td>';
      		tr4+='<td>'+tmpElements[i].innerHTML;
      		tr4+='<input type="button" value="删除" onclick="delobj(\''+tmpids[i].value+'\')"><input type="button" value="修改" onclick="updateobj(\''+tmpids[i].value+'\')"></td>';
      	}else if(i>49&&i<75){
      		tr5+='<td>'+(i+1)+'</td>';
      		tr6+='<td>'+tmpElements[i].innerHTML;
      		tr6+='<input type="button" value="删除" onclick="delobj(\''+tmpids[i].value+'\')"><input type="button" value="修改" onclick="updateobj(\''+tmpids[i].value+'\')"></td>';
      	}else if(i>74&&i<100){
      		tr7+='<td>'+(i+1)+'</td>';
      		tr8+='<td>'+tmpElements[i].innerHTML;
      		tr8+='<input type="button" value="删除" onclick="delobj(\''+tmpids[i].value+'\')"><input type="button" value="修改" onclick="updateobj(\''+tmpids[i].value+'\')"></td>';
      	}
    }
    if(dataNum==0){
			
	}else if(dataNum>0){
		tr1+='</tr>';
		tr2+='</tr>';
	}else if(dataNum>1){
		tr3+='</tr>';
		tr4+='</tr>';
	}else if(dataNum>2){
		tr5+='</tr>';
		tr6+='</tr>';
	}else if(dataNum>3){
		tr7+='</tr>';
		tr8+='</tr>';
	}
	document.getElementById("showtable").innerHTML='<table border="1" width="100%">'+tr1+tr2+tr3+tr4+tr5+tr6+tr7+tr8+'</table>';
}
function updateobj(id){
	location.href=root+"admin/display/teaminfodetail?id="+id;
	/* var tmp=root+"display/gethugolist";
		$.ajax({
			url: tmp,
			type: "POST",
			async:false,
			data:{"id":id},
			dataType:"json",
			success: function(json){
				
			}
		}); */
}

function delobj(id){
	var obj=document.getElementById(id);
	obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
	showtab();
}
</script>
  </head>
  
  <body background="<%=basePath%>res/images/index_background.jpg" onload="doinit();">
  	<div id="head">
  		<jsp:include page="/res/common/header.jsp" />
  	</div>
  	<div id="main">
	  	<div id="left" style="display: inline-block;float: left;">
	  		<jsp:include page="/res/common/left.jsp" />
	  	</div>
	  	<div id="right" style="display: inline-block;float: left;position: absolute;width: 1210px;height: 625px;">
	  	<div class="custom_order" id="custom_order">
	<form id="custom_sort_form">
	  <ul class="sort_div" id="sort_div">
	  </ul>
	</form>
</div>
<div style="position: absolute; bottom: 20px; width: 1200px" align="center">
	显示顺序预览 <font color="red">( </font>如有执行删除操作必需点击保存排序才会真正删除,删除按钮只是页面临时删除未持久化<font color="red"> ) </font>
	<div id="showtable">
	<table>
</table>

	</div>
    <input name="button" type="button" style="width: auto;" onclick="save_sort()" value="保存排序"/>
    <a href="<%=basePath%>admin/display/createteam">新增</a>
</div>
	  	</div>
  	</div>
  	<div id="foot" style="padding-top: 5px;float: left;">
		<jsp:include page="/res/common/footer.jsp" />
	</div>
  </body>
</html>
