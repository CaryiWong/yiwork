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
                $loading.removeClass('hidden');
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
                        $loading.addClass('hidden');
                        if (data.cord === 0) {
                            $warn.find('p').html("你对服务\"" + serviveName + "\"的合作申请表已提交");
                            $warn.find('img').attr('src','/images/pages/server/icon_succeed@2x.png');
                            setTimeout(function() {
                                if (serviceType === 'individual') {
                                    $form.append("<a class='hidden' id='fa' href = '/pages/server/personalServer.html?serviceid=" + serviceId + "'></a>");
                                } else {
                                    $form.append("<a class='hidden' id='fa' href = '/pages/server/teamServer.html?serviceid=" + serviceId + "'></a>");
                                }
                                document.getElementById("fa").click();
                            },3200);
                            $form[0].reset();
                        } else {
                            $warn.find('p').html('发送失败 ' + data.msg);
                            $warn.find('img').attr('src','/images/pages/server/icon_attention@2x.png');
                        }
                    }).fail(function () {
                        $loading.addClass('hidden');
                        $warn.find('p').html('发送失败 ');
                        $warn.find('img').attr('src','/images/pages/server/icon_attention@2x.png');
                    });
                $warn.fadeIn(600);
                setTimeout(function(){ $warn.fadeOut(600); }, 2000);
            }else { alert($form.find('.valid-error').first().html());}
        })
    });

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
                alert('获取用户信息失败 ' + data.msg);
            }
        }).fail(function () {
            alert('获取服务信息失败');
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
            alert('获取用户信息失败');
        });

});