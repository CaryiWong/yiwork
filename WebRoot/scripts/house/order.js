define(['jquery', 'server', 'user', 'wx', 'column'], function ($, server, user, wx) {
    function orderHeightResponse() {
        $('.house-order .order-list .order-info').css({height: '23.6vw'});
    }

    $.fn.touchclick = function (clickHandle) {
        var self = this;
        var isMove = false;
        this.on('touchmove', function () {
            isMove = true;
        })
            .on('touchend', function (event) {
                if (!isMove) {
                    clickHandle(event);
                }
                isMove = false;
            });
        return this;
    }

    var $loading = $('.house-loading');
    var $body = $('body');
    var ygPhone = '020-81869804';
    //var userInfo;
    //user.sign().done(function (info) {
    //    userInfo = info;
    //});

    var $template = $('.house-route.house-order #order-template'),
        $list = $('.house-route.house-order .order-list');

    function orderHtml(obj) {
        var html = $template.html();
        html = html.replace(/{id}/, obj.orderid)
            .replace(/{img}/, obj.img.split(',')[0])
            .replace(/{name}/, obj.name)
            .replace(/{person}/, obj.buypeople)
            .replace(/{days}/, obj.buynum)
            .replace(/{price}/, obj.price)
            .replace(/{number}/, obj.couponnumber)
            .replace(/{timestamp}/, obj.timestamp)
            .replace(/{nonceStr}/, obj.nonceStr)
            .replace(/{package}/, obj.package)
            .replace(/{signType}/, 'MD5')
            .replace(/{date}/, obj.comeintime.split(' ')[0])
            .replace(/{sign}/, obj.sign);
        var $html = $(html);
        switch (obj.ordertatus) {
            case 'cancel':
                $html.find('.order-state').html('已取消').end()
                    .find('.book-paper').remove().end();
                break;
            case 'submit':
            case 'sure':
                $html.find('.order-state').addClass('order-red').html('未付款').end()
                    .find('.book-paper').remove().end()
                    .find('.order-content .house-date').before('<a href="#/pay/' + obj.orderid + '" ' +
                    'class="house-button pay-link order-phone">付款</a>')
                    .end();
                break;
            case 'success':
                $html.find('.order-state')
                    .html('未消费')
                    .addClass('order-red')
                    .end();
                break;
        }
        html = $html[0].outerHTML;
        return html;
    }

    var payLock = false;
    var $payCode = $('.house-pay .pay-code');
    var $payButton = $('.house-pay .pay-button').touchclick(function () {
        if (payLock) return false;
        payLock = true;
        var userInfo = user.getUserInfo();
        var openid = userInfo.openid,
            orderid = $payButton.attr('data-orderid');
        var code = $payCode.val().trim();
        if (code && code.length>0) {
            server.pay(openid, orderid, code)
                .done(function (data) {
                    wx.simplePay({
                        timestamp: data.timestamp,
                        nonceStr: data.nonceStr,
                        package: data.package,
                        signType: data.package.signType,
                        paySign: data.sign,
                        success: function () {
                            $loading.fadeIn();
                            setTimeout(function () {
                                window.location.href = '#/book/'+ orderid;
                            }, 3000);
                        },
                        fail: function () {
                            alert('支付失败');
                        },
                        cancel: function () {
                            alert('支付取消');
                        }
                    })
                })
                .always(function () {
                    payLock = false;
                })
        }
    });

    var $bookOrderid = $('.house-book 。book-order-number .book-gray'),
        $bookCoupon = $('.house-book .coupon-id');

    return {
        loadUserOrderList: function (userInfo) {
            var defer = $.Deferred();
            server.list(userInfo.openid, userInfo.tel).done(function (data) {
                var html = '';
                data.forEach(function (d) {
                    html += orderHtml(d);
                });
                $list.html(html);
                orderHeightResponse();
                defer.resolve();
            });
            return defer.promise();
        },
        setPayOrder: function (orderid) {
            $payButton.attr('data-orderid', orderid);
        },
        bookSuccess: function (orderid) {
            var defer = $.Deferred();
            server.getOrder(orderid)
                .done(function (data) {
                    if(data.ordertatus === 'success'){
                        $bookOrderid.html(data.orderid);
                        $bookCoupon.html(data.couponnumber);
                        defer.resolve();
                    } else {
                        alert('服务端正在处理，请稍后查看');
                        window.location.href = '#/order';
                    }
                })
            return defer.promise();
        }
    }
});
