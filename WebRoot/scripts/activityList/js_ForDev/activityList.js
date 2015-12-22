/**
 * Created by Caryi on 2015/12/21.
 */
var Handlebars = require('handlebars/dist/handlebars.runtime.js');
require('imports?Handlebars=handlebars/dist/handlebars.runtime.js!./Hdb.templates.js');
require('../../../scripts_src/components/yiqi_app_download.js');
//var isDev= require('isDev');
//var server = isDev ? 'http://www.yi-gather.com/' : "http://" + window.location.host;
//
var localOriginal= "http://www.yi-gather.com/";
var $activityList = $('.activityList');
var $info = $('.info');
var page = 0;
var $window = $(window),$body = $(document.body);
var winH = $window.height(); //页面可视区域高度
var startY = '',endY = '',slideY = 0,moveY = 0;
var isMove = false;
var lock = false;
var goLoad = true;
$info.html("<img class='loading' src='/images/pages/server/icon_loading_loads@2x.png'>");

document.addEventListener('touchstart',function(event){
    startY = event.touches[0].clientY;
    isMove = false;
});
document.addEventListener('touchmove',function(event){
    isMove = true;
    moveY = event.touches[0].clientY;
    slideY = moveY - startY;
    var pageH = $body.height();
    var scrollT = $window.scrollTop(); //滚动条top
    var rate = (pageH - winH - scrollT) / winH;
    if (goLoad && rate < 0.1 && slideY < -10 ) {
        $info.html("<img class='loading' src='/images/pages/server/icon_loading_loads@2x.png'>");
        if(lock) return false;
        lock = true;
        page++;
        getData(page).always(function(){
            lock = false;
        });
    }
});
getData(page);
function getData(page){
    return $.ajax( localOriginal + '/v20/eventlog/trends',{
        dataType: 'json',
        type: 'POST',
        data: {
            type: 'web',
            page: page,
            size: 10,
            //eventtype: 'activity'
        }
    }).success(function(data){
        if(data.cord === 0){
            var result = data.data;
            if( result && result.length != 0 ){
                for(var i = 0;i < result.length; i++){
                    var activity = result[i].activity;
                    var openDate = activity.opendate;
                    var year = openDate.substring(0,4);
                    var date = openDate.substring(8,10);
                    var time = openDate.substring(11,16);
                    var hour = openDate.substring(11,12);
                    var Noon = 'AM';
                    if( hour >=12){
                        Noon = 'PM';
                    }
                    var month = translateMon(openDate.substring(5,7)) + '.' + openDate.substring(5,7);
                    var status = activity.status;
                    var add_now = false;
                    if(status === 0){
                        add_now = true;
                    }
                    var status_class = translateStatus(status);
                    var context = {
                        Noon : Noon,
                        year : year,
                        month : month,
                        date : date,
                        time : time,
                        h5url : activity.h5url,
                        cover : activity.cover,
                        spacelogo : activity.spaceinfo.spacelogo,
                        title : activity.title,
                        s_class : status_class.s_class,
                        status : status_class.s_text,
                        add_now: add_now
                    };
                    var html = Handlebars.templates.list(context);
                    $activityList.append(html);
                }
                $info.html('上拉加载更多');
            }else{
                $info.remove();
            }
        }else{
            alert(data.msg)
        }
    }).fail(function(){alert('加载失败，请刷新')});
}


function translateMon(month){
    switch (month){
        case '01' : {return 'JAN';break;}
        case '02' : {return 'FEB';break;}
        case '03' : {return 'MAR';break;}
        case '04' : {return 'APR';break;}
        case '05' : {return 'MAY';break;}
        case '06' : {return 'JUN';break;}
        case '07' : {return 'JUL';break;}
        case '08' : {return 'AUG';break;}
        case '09' : {return 'SEP';break;}
        case '10' : {return 'OCT';break;}
        case '11' : {return 'NOV';break;}
        case '12' : {return 'DEC';break;}
    }
}

function translateStatus(status){
    switch (status){
        case -1 : return {s_text:"已结束", s_class : 's_end'};break;
        case 0 : return {s_text:"进行中", s_class : 's_active'};break;
        case 1 : return  {s_text:"未开始", s_class : 's_preBegin'};break;
        case 2 : return  {s_text:"NEW", s_class : 's_new'};break;

    }
}