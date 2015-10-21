define(['jquery', 'server', 'user', 'wx', 'pickerdate', 'bootstrap', 'column'], function ($, server, user, wx) {
    var userInfo;
    var $body = $('body');
    var $nameInput = $('.input-name'),
        $phoneInput = $('.input-phone'),
        $dateInput = $('.input-date').pickadate({
            monthsFull: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            monthsShort: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'],
            weekdaysFull: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
            weekdaysShort: ['日', '一', '二', '三', '四', '五', '六'],
            today: '',
            clear: '',
            close: '',
            firstDay: 1,
            format: 'yyyy-mm-dd',
            formatSubmit: 'yyyy/mm/dd',
            min: 1,
        });
    $('.carousel').css({ height: '56.25vw'});


    $.fn.touchclick = function (clickHandle) {
        var self = this;
        var isMove = false;
        this.on('touchmove', function () {
            isMove = true;
        })
            .on('touchend', function (event) {
                if (!isMove) {
                    clickHandle.call(this, event);
                }
                isMove = false;
            });
        return this;
    }

    function initPage(obj, totalCtr) {
        var $body = $('body'),
            imgs = obj.imgs;
        $('.carousel').carousel('cycle');
        $body.find('.house-img-xx').attr('src', imgs.xx).end()
            .find('.house-img-gl').attr('src', imgs.gl).end()
            .find('.house-img-bg').attr('src', imgs.bg).end()
            .find('.house-img-js').attr('src', imgs.js).end()
            .find('.house-desc').html(obj.title).end()
            .find('.house-simple-price').html(obj.price).end()
            .find('.house-button').attr('data-good-id', obj.id);
        obj.price = obj.price || 0;
        totalCtr.setTotal();
    }

    var house;
    var Ctr = {
        max: 30,
        min: 1,
        set: function (v) {
            var max = this.max,
                min = this.min;
            this.value = v;
            if (v>min) {
                this.$mintue.removeClass('disabled');
            }
            else if (v<=min) {
                this.$mintue.addClass('disabled');
            }
            if (v>=max) {
                this.$add.addClass('disabled');
            } else if (v<max) {
                this.$add.removeClass('disabled');
            }
        },
        event: {
            add: function () {
                var value = this.value;
                if (value<this.max) {
                    this.set(++value);
                    this.$num.html(value);
                }
            },
            minute: function () {
                var value = this.value;
                if (value>this.min) {
                    this.set(--value);
                    this.$num.html(value);
                }
            }
        },
        init: function ($dom) {
            this.$dom = $dom;
            this.$mintue = $dom.find('.controller-minute')
                .touchclick(this.event.minute.bind(this));
            this.$add = $dom.find('.controller-add')
                .touchclick(this.event.add.bind(this));
            this.$num = $dom.find('.controller-num');
            this.value = 1;
            return this;
        }
    }
    var Total = {
        setTotal: function () {
            var total = this.people.value * this.day.value * house.price;
            this.total = total;
            this.$dom.html(total);
            return total;
        },
        ctrSet: function (ctr) {
            var self = this;
            ctr.originSet = ctr.set;
            ctr.set = function (v) {
                ctr.originSet(v);
                self.setTotal();
            }
            return ctr;
        },
        init: function ($dom, people, day) {
            this.$dom = $dom;
            this.people = this.ctrSet(people);
            this.day = this.ctrSet(day);
            return this;
        }
    }

    var peopleCtr = Object.create(Ctr).init($('.order-people'));
    var dayCtr = Object.create(Ctr).init($('.order-days'));
    var totalCtr = Object.create(Total).init($('.house-total-price')
        , peopleCtr, dayCtr);

    $('.house-sample .carousel img').touchclick(function () {
        var target = $(this).attr('data-target'),
            top = $body.find(target).offset().top;
        $body.animate({scrollTop: top}, 500);
    });

    var buyLock = false;
    $('.info-button').touchclick(function () {
        function always() {
            buyLock = false;
        }

        if(buyLock){
            return false;
        }
        buyLock = true;
        var total = totalCtr.setTotal();
        var userInfo = user.getUserInfo();
        var name = $nameInput.val().trim(),
            phone = $phoneInput.val().trim(),
            date = $dateInput.pickadate('picker').get();

        if (!name || name === '') {
            alert('请输入您的名称');
            always();
            return;
        }
        if (!date || date === '') {
            alert('请输入您的入住日期');
            always();
            return;
        }

        server.buy({
            buynum: dayCtr.value,
            buypeople: peopleCtr.value,
            goodid: house.id,
            userid: userInfo.id,
            usertype: 'wx',
            openid: userInfo.openid,
            tel: userInfo.tel,
            comeintime: date,
            peoplename: name
        }).done(function (data) {
            always();
            window.location = '#/order';
            //var orderid = data.id;
            //wx.quickPay(userInfo.openid, orderid, {
            //    success: function () {
            //        alert('支付成功');
            //        window.location.href = 'order.html'; // 跳转到订单页
            //    },
            //    fail: function () {
            //        alert('支付失败');
            //    },
            //    cancel: function () {
            //        alert('支付取消');
            //    }
            //})
        })
    })

    //var $frame = $('.detail');
    //$frame.ready(function () {
    //    $frame[0].contentWindow.postMessage('height', '*');
    //})
    //window.addEventListener('message', function (e) {
    //    $frame.height(e.data);
    //});

    return {
        loadHouse: function (us) {
            var defer = $.Deferred();
            server.getHouse()
                .done(function (data) {
                    house = data;
                    initPage(data, totalCtr);
                    defer.resolve();
                });
            $phoneInput.val(us.tel);
            userInfo = us;
            return defer.promise();
        }
    }
});
