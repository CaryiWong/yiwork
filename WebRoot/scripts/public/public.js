$(function () {
    // 登录
    var $loginDialog;
    $('.header-status-link.status-link-sign').click(function () {
        /*showLogin($loginDialog);*/
    });

    function showLogin() {
        if ($loginDialog) {
            util.dialog.show($loginDialog);
        } else {
            var $loginParent = $('<div></div>').load(root + 'pages/login.jsp style,.login', function () {
                $loginDialog = util.dialog.show($loginParent.find('.login'));
                $loginDialog.find('.login-close').click(util.dialog.close);
            }).appendTo(window.document.body);
        }
    }

//
//    // 活动hover
//    $('.activity').hover(function (){
//       $(this).find('.activity-info').height(80);
//    }, function (){
//        $(this).find('.activity-info').height(0);
//    });

    // 后退按钮
    $('.back-button').on('click', function (event) {
        event.preventDefault();
        history.go(-1);
    })

});


/**
 * 通用的分页方法
 */
function setPage(curPage, totalPage, pageTargetId) {
    var strHead = '<div class="pages"><a value=' + (curPage - 2) + ' href="javascript:void(0)" class="page-item">&lt; 上一页</a>';
    var strEnd = "";
    if (curPage + 1 <= totalPage) {
        strEnd = '<a value=' + (curPage) + ' href="javascript:void(0)" class="page-item">下一页 &gt;</a><a value=' + (totalPage - 1) + ' href="javascript:void(0)" class="page-item">最后一页 &gt;&gt;</a>';
    } else {
        strEnd = '<a value=' + (totalPage - 1) + ' href="javascript:void(0)" class="page-item">下一页 &gt;</a><a value=' + (totalPage - 1) + ' href="javascript:void(0)" class="page-item">最后一页 &gt;&gt;</a>';
    }

    var strCenter = "";
    //alert(curPage)
    //alert(curpage+"=="+totalPage)

    if (totalPage <= 0) {
        $("#" + pageTargetId).text("");
        return false;
    }

    for (var i = curPage - 2; i < curPage + 2; i++) {
        if (i <= 0) {
            continue;
        }
        if (i >= totalPage + 1) {
            continue;
        }


        if (i == (curPage)) {
            strCenter += '<a value=' + (i - 1) + ' href="javascript:void(0)" class="page-item active">' + i + '</a>';
        } else {
            strCenter += '<a value=' + (i - 1) + ' href="javascript:void(0)" class="page-item">' + i + '</a>'
        }

    }

    $("#" + pageTargetId).text("");
    $("#" + pageTargetId).append(strHead + strCenter + strEnd);

    //return strHead+strCenter+strEnd;
    //$("#"+pageTargetId).after(strHead+strCenter+strEnd);
}

/**
 * 返回本地时间2014-05-05
 * @param {Object} longTime
 * @return {TypeName}
 */
function getLocalTime(longTime) {
    var newTime = new Date(longTime); 		//得到普通的时间
    time = newTime.getFullYear() + "." + (newTime.getMonth() + 1) + "." + newTime.getDate();
    return time;
}

/**
 * 返回完整时间 yyyy-MM-dd HH:mm:ss
 * @param {Object} gmtTime
 */
function getFullTime(gmtTime) {
    var newTime = new Date(gmtTime);
    return newTime.getFullYear() + "-" + (newTime.getMonth() + 1) + "-" + newTime.getDate() + " " + newTime.getHours() + ":" + newTime.getMinutes() + ":" + newTime.getSeconds();
}

/**
 * 返回完整时间 yyyy-MM-dd HH:mm:ss 根据控件的值
 * @param {Object} gmtTime
 */
function getFullTimeByAmPm(amTime) {
    var newTime = new Date(desDate(amTime))
    return newTime.getFullYear() + "-" + (newTime.getMonth() + 1) + "-" + newTime.getDate() + " " + newTime.getHours() + ":" + newTime.getMinutes() + ":" + newTime.getSeconds();
}

/**
 * 日期转换 2014-05-09 11:00 PM
 * @param {Object} str
 */
function desDate(str) {
    if (str.length == 18) {
        str = str.substring(0, 10) + " 0" + str.substring(11, 18)
    }
    var year = str.substring(0, 4)
    var month = str.substring(5, 7)
    var day = str.substring(8, 10)
    var hour = str.substring(11, 13)
    var min = str.substring(14, 17)
    var pmam = str.substring(17, 30)
    if (pmam == "PM") {
        hour = parseInt(hour) + 12;
    }
    var date = new Date(year, month - 1, day, hour, min, "00");
    return date;
}


//导航展开按钮
var $navList = $('.header-nav');
$('.header-nav-button').on('click', function () {
    $navList.toggleClass('active');
});



