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
    var $body = $('body').addClass('page');
    var action = {
        appDetail: function () {
            function finishCollect() {
                $button.attr('disabled', true)
                    .addClass('active').html('亲~你已报名成功，坐等开始吧！');
            }

            var $button = $('<button class="btn btn-block signButton">我要报名</button>'),
                $cover = $('.basic-cover'),
                $container = $('<div class="container"></div>'),
                $collect = $('<div class="collectButton"></div>'),
                $coverContainer = $('<div class="coverContainer"></div>'),
                $detail = $('.basic-detail');

            window.getUserInfoFinish = function (d) {
                var data = JSON.parse(d);
                if (data.isCollect) {
                    $collect.addClass('active');
                }
                if (data.isSign) {
                    finishCollect();
                }
            };

            yiqi.getUserInfo();

            window.signFinish = function () {
                finishCollect();
            };

            $button.on('click', function () {
                yiqi.sign()
            });

            window.collectFinish = function () {
                $collect.addClass('active');
            };

            window.cancelCollectFinish = function () {
                $collect.removeClass('active');
            };

            $collect.on('click', function () {
                if ($collect.hasClass('active')) {
                    yiqi.cancelCollect();
                } else {
                    yiqi.collect();
                }
            });
        },
        web: function () {
            //alert('web')
        },
        weixin: function () {
            //alert('weixin')
        }
    };
    var args = getArgs();
    action[args['type'] || 'weixin']();
    $('img').css('cssText', 'width: 100% !important');
});
