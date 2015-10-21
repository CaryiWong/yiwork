$(function () {
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

    function getDevice() {
        var ua = window.navigator.userAgent.toLowerCase(),
            result = '';
        if (/android/.test(ua)) {
            result = 'android';
        } else if (/iphone|ipad/.test(ua)) {
            result = 'ios';
        }
        return result;
    }

    function isWeixin(){
    	var ua = navigator.userAgent.toLowerCase();
    	return (new RegExp(/micromessenger/i).test(ua))
    }

    function animate($active, $next) {
        function getDuration($page) {
            return parseFloat($page.css('transition-duration')) + parseFloat($page.css('transition-delay'));
        }

        var hidingDuration,
            showDuration;
        $active.addClass('hiding');
        hidingDuration = getDuration($active);
        setTimeout(function () {
            $active.removeClass('hiding active');
        }, hidingDuration * 1000);

        $next.css('transition-duration', '0').addClass('showing active');
        setTimeout(function () {
            $next.css('transition-duration', '');
            showDuration = getDuration($next);
            $next.addClass('active').removeClass('showing');
            setTimeout(function () {

            }, showDuration * 1000);
        }, 0);
    }

    function getControlType($input) {
        var nodeName = $input[0].nodeName.toLowerCase();
        var result;
        switch (nodeName) {
            case 'select':
                result = nodeName;
                break;
            case 'textarea':
                result = 'text';
                break;
            case 'input':
                var type = $input.attr('type');
                result = type;
        }
        return result;
    }

    function handleForm($form) {
        var userText = [];
        $form.find('[drag-page="element"]:visible').each(function (i) {
            var $this = $(this),
                $input = $this.find('input, select, textarea'),
                item = {num: i};
            if ($input.length === 0) return true;
            item.questiontext = $input.attr('name');
            item.questiontype = getControlType($input);
            if (item.questiontype === 'text') {
                item.answertext = $input.val();
                item.questionoptions = '';
            } else {
                var values = [];
                if (item.questiontype === 'checkbox' ||
                    item.questiontype === 'radio') {
                    $input.each(function () {
                        values.push(this.value);
                    });
                    item.answertext = [];
                    $input.filter(':checked').each(function () {
                        item.answertext.push(this.value);
                    });
                    item.answertext = item.answertext.join(',');
                } else if (item.questiontype === 'select') {
                    $input.find('option').each(function () {
                        values.push(this.innerText);
                    });
                    item.answertext = $input.find('option:selected').html();
                }
                item.questionoptions = values.join(',');
            }
            userText.push(item);
        });
        return userText;
    }

    var $body = $('body');
    var args = getArgs();
    var nextEvent = 'tap',
        prevEvent = 'swipedown',
        device = getDevice();
    var serverName = window.location.origin;
    //var serverName = 'http://192.168.1.30';
    //var serverName = 'http://192.168.1.190:8080/yiwork';

    function setTouchEvent($pages) {
        $pages.each(function () {
            var $page = $(this);
            var hammertime = new Hammer(this, {});
            hammertime.get('swipe').set({direction: Hammer.DIRECTION_VERTICAL});
            hammertime
                .on(nextEvent, function () {
                    var $nextPage = $page.nextAll('.page').not('.page-main');
                    if ($nextPage.length) {
                        animate($page, $nextPage.first());
                    }
                })
                .on(prevEvent, function () {
                    var $prevPage = $page.prevAll('.page');
                    if ($prevPage.length) {
                        animate($page, $prevPage.first());
                    }
                })
        });
    }

    var action = {
        normal: function () {

        },
        apply: function () {
            function getTicket(id) {
                $.ajax(serverName + '/v20/activity/searchrestticket', {
                    dataType: 'json',
                    data: {
                        type: 'web',
                        id: id
                    }
                })
                    .success(function (d) {
                        if (d.cord === 0) {
                            var data = d.data;
                            if (_.keys(data).length > 1) {
                                // 多活动票数
                                _.each(data, function (t, id) {
                                    $buy.filter('[value="' + id + '"]').next()
                                        .html(t.title + '：余票' + t.rest);
                                })
                            } else {
                                var t = _.values(data)[0];
                                $button.html('提交：余票' + t.rest);
                            }
                        }
                    });
            }

            function sign(info) {
                if(info.tel.length <=0
                    || info.email.length <= 0
                    || info.name.length <= 0) {
                    alert('请填写你的基本信息');
                    return;
                }
                var data = {
                    type: 'mobile',
                    id: info.buy.join(','),
                    idtype: info.buy.length > 1 ? 'multi' : 'single',
                    acttype: 'activity',
                    tel: info.tel,
                    name: info.name,
                    email: info.email,
                    questions: info.questions || '{}'
                };
                $.ajax(serverName + '/v20/activity/signup', {
                    type: 'post',
                    dataType: 'json',
                    data: data
                }).success(function (data) {
                    if (data.cord === 0) {
                        if (data.data2) {
                            var data2 = data.data2,
                                text = '',
                                $pay = $(data.data);
                            _.each(data2.success, function (t){
                                text += t.message + '\n';
                            });
                            _.each(data2.fail, function (t){
                                text += t.message + '\n';
                            });
                            if(data2.flag){
                                // 需要收费
                                text += '是否立即支付？';
                                if(confirm(text)){
                                    $body.append($pay);
                                }
                            } else {
                                var sLength = _.keys(data2.success).length,
                                    fLength = _.keys(data2.fail).length;
                                if(fLength){
                                    alert(text);
                                } else {
                                    window.location.href = '/pages/signSuccess.html';
                                }
                            }
                        }
                    } else {
                        alert(data.msg);
                    }
                }).error(function () {
                    console.error('报名失败');
                })
            }

            if(isWeixin()){
                $body.append(
                    $('<div class="mask">请点击右上角，选择<br/>“在浏览器中打开”，进行报名</div>')
                );
            }

            $('.page').not('.h5apply').addClass('ng-hide');
            var $form = $('.page.h5apply'),
                isVip = args['vip'] - 0,
                activityId = $('.activityInfo').attr('data-activity-id'),
                deviceAction = {
                    android: function (data) {
                        yiqi.signMessage(data);
                    },
                    ios: function (data) {
                        window.getMessage = function () {
                            return data;
                        };
                        window.location.href = 'signMessage';
                    }
                };
            var $name = $form.find('input[name="name"]'),
                $phone = $form.find('input[name="phone"]'),
                $email = $form.find('input[name="email"]'),
                $button = $form.find('.submit-button'),
                $buy = $form.find('input[name="buy"]');
            if (isVip) {
                $form.find('.interact-form-name, .interact-form-phone, .interact-form-email').hide();
            }
            getTicket(activityId);
            $button.click(function (event) {
                event.preventDefault();
                var buys = [],
                    userText;
                if ($buy.length) {
                    // 活动集
                    _.each($buy.filter(':checked'), function (b) {
                        buys.push(b.value);
                    });
                    if(buys.length === 0){
                        alert('请选择你要报名的内容');
                        return;
                    }
                } else {
                    // 单一活动
                    buys.push(activityId);
                }
                userText = handleForm($form);
                if (isVip) {
                    deviceAction[device](JSON.stringify({
                        questions: JSON.stringify(userText),
                        buy: buys.join(',')
                    }));
                } else {
                    sign({
                        tel: $phone.val(),
                        name: $name.val(),
                        email: $email.val(),
                        questions: JSON.stringify(userText),
                        buy: buys
                    });
                }
            });
        },
        survey: function () {
            $('.page').not('.h5feedback').addClass('ng-hide');
            var $form = $('.page.h5feedback'),
                objid = $('.activityInfo').attr('data-activity-id'),
                objtype = args['objtype'] || 'activity',
                userid = args['userid'];
            $('.submit-button').click(function (event) {
                event.preventDefault();

                var data = {
                    objid: objid,
                    objtype: objtype,
                    'user.id': userid,
                    questions: JSON.stringify(handleForm($form)) || '{}'
                };
                $.ajax(serverName + '/v20/answer/createanswer', {
                    type: 'post',
                    dataType: 'json',
                    data: data
                }).success(function (data) {
                    if (data.cord === 0) {
                        alert('问卷提交成功');
                    } else {
                        alert('问卷提交失败');
                    }
                }).fail(function () {
                    alert('问卷提交失败');
                })
            });
        },
        share: function () {
            var $pages = $('.page').removeClass('active');
            var $forms = $('form.page');
            var $form = $forms.filter('.h5sign');
            var $name = $form.find('input[name="name"]'),
                $phone = $form.find('input[name="phone"]'),
                $vip = $form.find('input[name="vip"]'),
                $price = $form.find('select[name="price"]');
            $pages.first().addClass('active');
            $forms.not($form).remove();
            $body.append($('.page-main'));
            setTouchEvent($pages);

            $('.submit-button').click(function (event) {
                event.preventDefault();
                var userText = [],
                    text = [];
                handleForm($form, userText);
                text = JSON.stringify(userText);
                sign($price, $phone, $name, text);
            });
        }
    };

    action[args['type'] || 'normal']();
});
