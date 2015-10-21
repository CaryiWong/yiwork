//只能输入数字
function regNumReplace(obj){
	if(obj.value==0){
		obj.value = "";
	}
	obj.value=obj.value.replace(/[^\d]/g,'');
}


function jumpPage(pageNo, totalPages) {
	
    if( isNaN(pageNo)  ) {//判断是否数字
       return ;
    }
 
	if(new Number(pageNo) > new Number(totalPages))
		pageNo = totalPages;
	$("#currentPage").val(pageNo-1);
	$("#mainForm").submit();
}

function sort(orderBy, defaultOrder) {
	if ($("#orderBy").val() == orderBy) {
		if ($("#order").val() == "") {
			$("#order").val(defaultOrder);
		}
		else if ($("#order").val() == "desc") {
			$("#order").val("asc");
		}
		else if ($("#order").val() == "asc") {
			$("#order").val("desc");
		}
	}
	else {
		$("#orderBy").val(orderBy);
		$("#order").val(defaultOrder);
	}

	$("#mainForm").submit();
}

function search() {
	$("#order").val("");
	$("#orderBy").val("");
	$("#pageNo").val("1");

	$("#mainForm").submit();
}

function remove(url, msg) {
	if(confirm(msg) == true)
		window.location.assign(url);
}