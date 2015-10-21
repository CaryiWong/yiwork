require(['jquery'], function ($) {
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

    var locationOriginalURL = 'http://test.yi-gather.com:1717/v20/';
    var args = getArgs();
    var serverId = args['serviceid'];
    var userId = args['userid'];
    var serverUser = '';
    var $body = $('body');
    $.ajax(
        locationOriginalURL + 'yqservice/findyqservice', {
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
                serverUser = user.id;
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
                }
                else {
                    sType = '企业服务';
                }
                $('title').html(sType);
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
                $main.find('.user').html(user['nickname']);
                var introduction = user['introduction'].substring(0, 27);
                $main.find('.introduction').html(introduction);
                for (var i = 0; i < 3; i++) {
                    $main.find('.marks-btn-box').append('<span>' + user.focus[i]['zname'] + '</span>');
                }
                //$('.banner').attr("src",data['banner']);
            } else {
                $body.append('<div class="get-info-erro"><p>网络出错了 T_T ' + respond.msg + '</p><button onclick="history.go(-1)" class="btn form-button"> 返回 </button></div>');
            }
        }).fail(function () {
            $body.append('<div class="get-info-erro"><p>网络出错了 T_T </p><button onclick="history.go(-1)" class="btn form-button"> 返回 </button></div>');
        });
    var $talkBtn = $('.btn-talk');
    window.yiqi = {};
    var talkAction = {
        android: function (myId, talkToId) {
            yiqi.talk(myId, talkToId);
        },
        ios: function (myId, talkToId) {
            window.getTalk = function () {
                return (myId+talkToId);
            };
            window.location.href = 'talk';
        }
    };
    var ua = navigator.userAgent;
    if (ua.match(/iPhone|iPod/i) != null || ua.match(/iPad/i) != null) {
        $talkBtn.on('touchstart', function(){
            talkAction.ios(serverUser, userId);
        });
    }
    else if (ua.match(/Android/i) != null) {
        $talkBtn.on('touchstart', function() {
            talkAction.android(serverUser, userId);
        });
    }
});