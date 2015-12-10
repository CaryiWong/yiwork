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
    var locationOriginalURL = 'http://' + window.location.host;
    //var locationOriginalURL = 'http://test.yi-gather.com:1717';
    var args = getArgs();
    var serverId = args['serviceid'];
    var userId = $.cookie('userid');
    var serverUserId = '';
    var $body = $('body');
    var $teamHeadPic = $('.team-header-pic');
    var talkParam = '';
    var $applyBtn = $('.btn-apply');
    var $talkBtn = $('.btn-talk');
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
                if(ua.match('yiqi')){
                    $('.toCards').attr('href','/pages/members/card.html?userid=' + serverUserId);
                }
                if(serverUserId === userId){
                    $('.server-btn-group').hide();
                }
                var $main = $('.info-main');
                $main.show();
                $main.find('.serverName').html(data['name']);
                var sType = '';
                var $type = data['servicetype'];
                if ($type === 'individual') {
                    sType = '个人服务';
                }
                else if ($type === 'team') {
                    sType = '团队服务';
                    $applyBtn.hide();
                }
                else {
                    sType = '企业服务';
                    $talkBtn.hide();
                }
                $main.find('.head-class').html(sType);
                $main.find('.supplier').html(data['servicesupplier']);
                var $location = $main.find('.location');
                $location.find('span').html(data['city']);
                $main.find('.content').html(data['context'].replace(/\n/g, '<br/>'));
                $main.find('.head-pic').attr('src', data['titleimg']);
                $('.team-header-pic,.psn-header-pic').css('background-image',"url(" + data['titleimg'] + ")");
                $main.find('.user').html(data['servicesupplier']);
                var introduction = user['introduction'].substring(0, 27);
                $main.find('.introduction').html(introduction);
                for (var i = 0; i < 3; i++) {
                    if(user.focus[i]){
                        $main.find('.marks-btn-box').append('<span>' + user.focus[i]['zname'] + '</span>');
                    }
                }
                talkParam = serverUserId + ',' + user.nickname + ',' + user.minimg;
                var shareBody = {
                    "title" : data['name'],
                    "desc" : data['context'],
                    "img" : data['titleimg'],
                    "link" : window.location.href
                };
                var shareString = JSON.stringify(shareBody);
                if (uaNow() === 'ios') {
                    shareAction.iosShare(shareString);
                    shareAction.iosIsShare('isShare',1)
                } else {
                    shareAction.androidShare(shareString);
                    shareAction.androidIsShare('isShare', 1);
                }
            } else {
                $body.append('<div class="get-info-erro"><p>网络出错了 T_T ' + respond.msg + '</p>' +
                    '</div>');
            }
        }).fail(function () {
            $body.append('<div class="get-info-erro"><p>网络出错了 T_T </p></div>');
        });

    //聊天室跳转
    var Ua = navigator.userAgent;
    var ua = Ua.toLocaleLowerCase();
    var talkAction = {
        ios :function(method,params){
            window.iosWebParams = function () {
                return params;
            };
            window.location.href = method ;
        },
        android : function(method,params){
            if(window.yiqi && window.yiqi[method]){
                window.yiqi[method](params);
            }
        }
    };

    var shareAction = {
        iosShare :function(params){
            window.iosGetShare = function () {
                return params;
            };
        },
        iosIsShare:function(method,params){
            window.iosWebParams = function () {
                return params;
            };
            window.location.href = method ;
        },
        androidIsShare : function(method,params){
            if(window.yiqi && window.yiqi[method]){
                window.yiqi[method](params);
            }
        },
        androidShare : function(params){
            window.yiqi.getShare = function () {
                return params;
            };
        }
    };

    //分享打开的页面按钮
    if(ua.match('micromessenger')){
        $talkBtn.hide();
        $applyBtn.hide();
        $('.server-btn-group').append("<a class='btn form-button' href='/pages/other/download.html'>下载一起APP</a>")
    }

    var uaNow = function(){
        if (ua.match('yiqi') && !ua.match('micromessenger')) {
            if (ua.match('iphone' || 'ipod' || 'ipad')) {
                return 'ios';
            } else if (ua.match('android')) {
                return 'android';
            }
        }
    };

    var appLocation = function() {
        if (uaNow()==='ios') {
            talkAction.ios('talk', talkParam)
        } else{
            talkAction.android('talk', talkParam)
        }

    };
    $talkBtn.on('touchstart',function(){
        appLocation();
    });
    //申请表跳转
    $applyBtn.attr('href','/pages/server/intentLetter.html?serviceid=' + serverId);

});