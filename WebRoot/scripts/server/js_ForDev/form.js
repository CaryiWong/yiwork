$(function () {
    var serviceId = $.cookie('serviceid'),
        userId = $.cookie('userid'),
        $form = $('form'),
        serviveName='',
      //locationOriginalURL = window.location.origin;
        locationOriginalURL = 'http://test.yi-gather.com:1717';
    $('.form-button').on('touchstart', function (event) {
        event.preventDefault();
        $form.valid(function (pass) {
            if (pass) {
                $(".server-form").append("<div class='loading'></div>");
                var $loading = $('.loading');
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
                        $loading.remove();
                        if (data.cord === 0) {
                            alert("你对服务" + serviveName + "的合作申请表已提交");
                            //location.href=;
                            $form[0].reset();
                        } else {
                            alert('发送失败 ' + data.msg);
                        }
                    }).fail(function () {
                        $loading.remove();
                        alert('发送失败');
                    });
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
                userid: serviceId
            }
        }).success(function (sData) {
            if (sData.cord === 0) {
                serviveName = sData.data["name"];
            } else {
                alert('获取服务信息失败 ' + sData.msg);
            }
        }).fail(function () {
            alert('获取用户信息失败');
        });

});