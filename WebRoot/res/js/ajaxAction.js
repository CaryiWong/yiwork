var completeMethod;
var req;

function getResult(requestUrl,method){
	
	completeMethod = method;
	
	//alert(requestUrl);
	var url = requestUrl; 
    if (window.XMLHttpRequest) { 
            req = new XMLHttpRequest(); 
    }else if (window.ActiveXObject) { 
            req = new ActiveXObject("Microsoft.XMLHTTP"); 
    } 
    if(req){ 
            req.open("GET",url, true); 
            req.onreadystatechange = complete; 
            req.send(null); 
    } 
}

function complete(){
    if (req.readyState == 4) { 
    	if (req.status == 200) { 
            	var text = req.responseText;
            	//eval("tip()");
            	completeMethod.call(this,text);
        }
    }
}

function dateDiff(interval,startTime,endTime){
	switch (interval){
	     //計算秒差
	     case "s": return parseInt((endTime-startTime)/1000);
	     //計算分差
	     case "n": return parseInt((endTime-startTime)/60000);
	     //計算時差
	     case "h": return parseInt((endTime-startTime)/3600000);
	     //計算日差
	     case "d": return parseInt((endTime-startTime)/86400000);
	     //計算週差
	     case "w": return parseInt((endTime-startTime)/(86400000*7));
	     //計算月差
	     case "m": return (endTime.getMonth()+1)+((endTime.getFullYear()-startTime.getFullYear())*12)-(startTime.getMonth()+1);
	    //計算年差
	    case "y": return endTime.getFullYear()-startTime.getFullYear();
	    //輸入有誤
	    default: return undefined;
     }
}