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
    var args = getArgs();
    var serviceId = args['serviceid'],
        userId = $.cookie('userid'),
        serviceUserId = '',
        $form = $('form'),
        serviveName='',
        serviceType='',
        $loading = $('.loading'),
        $warn = $('.warn'),
    //locationOriginalURL = window.location.origin;
        locationOriginalURL = 'http://test.yi-gather.com:1717';
    $('.form-button').on('touchstart', function (event) {
        event.preventDefault();
        $form.valid(function (pass) {
            if (pass) {
                $loading.show();
                $.ajax(
                    locationOriginalURL + '/v20/yqserviceapply/apply', {
                        dataType: 'json',
                        type: 'POST',
                        data: {
                            type: 'web',
                            serviceid: serviceId,
                            userid: userId,
                            linkname : $(".nickname").val(),
                            linktel: $(".phone").val(),
                            linkother: $("input[name='email']").val(),
                            locationname: $("input[name='company']").val(),
                            introduction: $("input[name='intro']").val()
                        }
                    }).success(function (data) {
                        $loading.hide();
                        if (data.cord === 0) {
                            $warn.find('p').html("你对服务\"" + serviveName + "\"的合作申请表已提交");
                            $warn.find('img').attr('src','/images/pages/server/icon_succeed@2x.png');
                            setTimeout(function() {
                                appLocation();
                            },3200);
                            $form[0].reset();
                        } else {
                            $warn.find('p').html('发送失败 ' + data.msg);
                            $warn.find('img').attr('src','/images/pages/server/icon_attention@2x.png');
                        }
                    }).fail(function () {
                        $loading.hide();
                        $warn.find('p').html('发送失败 ');
                        $warn.find('img').attr('src','/images/pages/server/icon_attention@2x.png');
                    });
                $warn.fadeIn(600);
                setTimeout(function(){ $warn.fadeOut(600); }, 2000);
            }else { alert($form.find('.valid-error').first().html());}
        })
    });

    var Ua = navigator.userAgent;
    var ua = Ua.toLocaleLowerCase();
    var action = {
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
                if(method ==='back'){
                    window.yiqi[method]();
                }else{
                    window.yiqi[method](params);
                }
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
        action.iosShare(0)
    } else {
        action[uaNow()]('isShare', 0);
    }

    function appLocation() {
            if (uaNow() === 'ios') {
                action[uaNow()]('back', serviceId)
            } else if (uaNow() === 'android'){
                action.android('back');
            }
    }
    //利用userid获取用户信息
    $.ajax(
        locationOriginalURL + '/v20/user/getuser', {
            dataType: 'json',
            type: 'POST',
            data: {
                type: 'web',
                userid: userId
            }
        }).success(function (data) {
            if (data.cord === 0) {
                $(".nickname").attr({value:data.data["nickname"]});
                $(".phone").attr({value:data.data["telnum"]});
            } else {
                alert('获取用户信息失败，请检查您是否已登录');
            }
        }).fail(function () {
            alert('获取用户信息失败，请检查您是否已登录');
        });

    //利用serviceid获取服务信息
    $.ajax(
        locationOriginalURL + '/v20/yqservice/findyqservice', {
            dataType: 'json',
            type: 'POST',
            data: {
                type: 'web',
                id: serviceId
            }
        }).success(function (sData) {
            if (sData.cord === 0) {
                serviveName = sData.data["name"];
                serviceType = sData.data['servicetype'];

            } else {
                alert('获取服务信息失败 ' + sData.msg);
            }
        }).fail(function () {
            alert('获取服务信息失败');
        });

});