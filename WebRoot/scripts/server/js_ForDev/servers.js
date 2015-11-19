function getArgs() {
    var args = {},
        query = location.search.substring(1),
        pairs = query.split("&");
    for (var i = 0; i < pairs.length; i++) {
        var pos = pairs[i].indexOf('=');
        if (pos == -1) continue;
        var argname = pairs[i].substring(0, pos),
            value = pairs[i].substring(pos + 1);
        value = decodeURIComponent(value);
        args[argname] = value;
    }
    return args;
}
$(function () {
    //var locationOriginalURL = window.location.origin;
    var locationOriginalURL = 'http://test.yi-gather.com:1717';
    var args = getArgs();
    var serverId = args['serviceid'];
    var userId = $.cookie('userid');
    var serverUserId = '';
    var $body = $('body');
    var $teamHeadPic = $('.team-header-pic');
    $.ajax(
        locationOriginalURL + '/v20/yqservice/findyqservice', {
            dataType: 'json',
            type: 'POST',
            data: {
                type: 'web',
                id: serverId
            }
        }).success(function (respond) {
            if (respond.cord === 0) {
                var data = respond.data;
                var user = data.user;
                serverUserId = user.id;
                if(serverUserId === userId){
                    $('.btn-group').hide();
                }
                var $main = $('.info-main');
                $main.show();
                $main.find('.serverName').html(data['name']);
                var sType = '';
                var $type = data['servicetype'];
                if ($type === 'individual') {
                    sType = '个人服务';
                    $applyBtn.hide().parent().removeClass('btn-group');
                }
                else if ($type === 'team') {
                    sType = '团队服务';
                    $applyBtn.hide().parent().removeClass('btn-group');
                }
                else {
                    sType = '企业服务';
                }
                $main.find('.head-class').html(sType);
                $main.find('.supplier').html(data['servicesupplier']);
                var $location = $main.find('.location');
                $location.find('span').html(data['city']);
                var contextStr = data['context'].trim();
                var arr = contextStr.split(/\n/g);
                for (var j = 0; j < arr.length; j++) {
                    $main.find('.content').append("<p>" + arr[j] + "</p>");
                }
                $main.find('.head-pic').attr('src', data['titleimg']);

                $('.team-header-pic,.psn-header-pic').css('background-image',"url(" + data['titleimg'] + ")");
                $main.find('.user').html(user['nickname']);
                var introduction = user['introduction'].substring(0, 27);
                $main.find('.introduction').html(introduction);
                for (var i = 0; i < 3; i++) {
                    $main.find('.marks-btn-box').append('<span>' + user.focus[i]['zname'] + '</span>');
                }
            } else {
                $body.append('<div class="get-info-erro"><p>网络出错了 T_T ' + respond.msg + '</p>' +
                    //'<button onclick="history.go(-1)" class="btn form-button"> 返回 </button>' +
                    '</div>');
            }
        }).fail(function () {
            $body.append('<div class="get-info-erro"><p>网络出错了 T_T </p></div>');
        });

    //聊天室跳转按钮
    var $talkBtn = $('.btn-talk');
    var Ua = navigator.userAgent;
    var ua = Ua.toLocaleLowerCase();
    var talkAction = {
        ios :function(method,params){
            window.iosWebParams = function () {
                return params;
            };
            window.location.href = method ;
        },
        iosShare :function(params){
            window.iosIsShare = function () {
                return params;
            };
        },
        android : function(method,params){
            if(window.yiqi && window.yiqi[method]){
                window.yiqi[method](params);
            }
        }
    };
    var uaNow = function(){
        if (ua.match('yiqi') && !ua.match('micromessenger')) {
            if (ua.match('iphone' || 'ipod' || 'ipad')) {
                return 'ios';
            } else if (ua.match('android')) {
                return 'android';
            }
        }
    };

    if (uaNow() === 'ios') {
        talkAction.iosShare(1)
    } else {
        talkAction.android('isShare', 1);
    }

    var appLocation = function() {
        if (uaNow()==='ios') {
            talkAction.ios('myTalk', serverUserId)
        } else{
            talkAction.android('myTalk', serverUserId)
        }

    };
    $talkBtn.on('touchstart',function(){
        appLocation();
    });
    //申请表跳转
    var $applyBtn = $('.btn-apply');
    $applyBtn.attr('href','/pages/server/intentLetter.html?serviceid=' + serverId);

});