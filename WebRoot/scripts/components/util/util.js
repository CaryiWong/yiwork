var util = {
    _$body: $('body'),
    isKeyExit: function (key, array) {
        for (var item in array) {
            if (item === key) {
                return true;
            }
        }
        return false;
    },
    isValueExit: function (obj, array) {
        for (var item in array) {
            if (obj === array[item] || obj === item) {
                return true;
            }
        }
        return false;
    },
    getArgs: function () {
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
    },
    device: function (actions) {
        var device = this.getArgs().device;
        if(actions[device]){
            actions[device]();
        }
    },
    dialog: {
        _cache: {},
        _convertTojQuery: function (dom) {
            var $dom;
            if (dom instanceof jQuery) {
                $dom = $(dom);
            } else {
                $dom = dom;
            }
            return $dom;
        },
        _clearStyle: function ($dom) {
            $dom.css({
                margin: 0,
                top: 0,
                left: 0
            })
        },
        setStyle: function ($dom) {
            this._clearStyle($dom);
            var width = $dom.outerWidth(),
                height = $dom.outerHeight(),
                marginTop = -(height / 2),
                marginLeft = -(width / 2);
            $dom.css({
                position: 'fixed',
                top: '50%',
                left: '50%',
                marginTop: marginTop,
                marginLeft: marginLeft,
                zIndex: 9999
            });
        },
        _setMask: function () {
            if ($('#dialog-mask').length > 0) return;
            var $mask = $('<div id="dialog-mask"></div>');
            $mask.css({
                position: 'fixed',
                zIndex: 9998,
                width: '100%',
                height: '100%',
                top: 0,
                left: 0,
                backgroundColor: '#000',
                opacity: '.6'
            }).appendTo(util._$body);
        },
        _setResize: function ($dom) {
            var self = this;
            window.onresize = function () {
                self.setStyle($dom);
            }
        },
        show: function (dom, callback) {
            var $dom = this._convertTojQuery(dom).addClass('dialog-content').show();
            util._$body.append($dom);
            this.setStyle($dom);
            this._setMask();
            callback && callback();
            return $dom;
        },
        close: function (callback) {
            $('#dialog-mask').remove();
            $('.dialog-content').hide();
            $.isFunction(callback) && callback();
        }
    },
    loading: {
        _$loading: $('<div class="loading">' +
            '<i class="add-icon-loading"></i>' +
            '<div class="loading-text">Loading</div>' +
            '</div>'),
        show: function () {
            util._$body.append(this._$loading);
            util.dialog.show(this._$loading);
        },
        close: function () {
            util.dialog.close();
        }
    },
    confirm: {
        _$confirm: $('<div class="confirm-dialog">' +
            '<p class="confirm-dialog-text"></p>' +
            '<div class="confirm-dialog-buttonGroup">' +
            '<button class="button gray confirm-dialog-button confirm-button-cancel">取消</button>' +
            '<button class="button confirm-dialog-button confirm-button-sure">确定</button>' +
            '</div>' +
            '</div>'),
        show: function (text, sureClick, callback) {
            var self = this;
            this._$confirm.find('p').html(text);
            this._$confirmSure.on('click', sureClick);
            util._$body.append(this._$confirm);
            util.dialog.show(this._$confirm);
            callback && callback(this);
        },
        close: function () {
            util.dialog.close();
            this._$confirmSure.off('click');
        },
        init: function () {
            var self = this;
            this._$confirmCancel = this._$confirm.find('.confirm-button-cancel')
                .on('click', function () {
                    self.close();
                });
            this._$confirmSure = this._$confirm.find('.confirm-button-sure');
        }
    },
    start: function (month, date) {
        var value;
        if (month == 1 && date >= 20 || month == 2 && date <= 18) {
            value = "水瓶座";
        }
        if (month == 1 && date > 31) {
            value = "Huh?";
        }
        if (month == 2 && date >= 19 || month == 3 && date <= 20) {
            value = "双鱼座";
        }
        if (month == 2 && date > 29) {
            value = "Say what?";
        }
        if (month == 3 && date >= 21 || month == 4 && date <= 19) {
            value = "白羊座";
        }
        if (month == 3 && date > 31) {
            value = "OK. Whatever.";
        }
        if (month == 4 && date >= 20 || month == 5 && date <= 20) {
            value = "金牛座";
        }
        if (month == 4 && date > 30) {
            value = "I'm soooo sorry!";
        }
        if (month == 5 && date >= 21 || month == 6 && date <= 21) {
            value = "双子座";
        }
        if (month == 5 && date > 31) {
            value = "Umm ... no.";
        }
        if (month == 6 && date >= 22 || month == 7 && date <= 22) {
            value = "巨蟹座";
        }
        if (month == 6 && date > 30) {
            value = "Sorry.";
        }
        if (month == 7 && date >= 23 || month == 8 && date <= 22) {
            value = "狮子座";
        }
        if (month == 7 && date > 31) {
            value = "Excuse me?";
        }
        if (month == 8 && date >= 23 || month == 9 && date <= 22) {
            value = "处女座";
        }
        if (month == 8 && date > 31) {
            value = "Yeah. Right.";
        }
        if (month == 9 && date >= 23 || month == 10 && date <= 22) {
            value = "天秤座";
        }
        if (month == 9 && date > 30) {
            value = "Try Again.";
        }
        if (month == 10 && date >= 23 || month == 11 && date <= 21) {
            value = "天蝎座";
        }
        if (month == 10 && date > 31) {
            value = "Forget it!";
        }
        if (month == 11 && date >= 22 || month == 12 && date <= 21) {
            value = "人马座";
        }
        if (month == 11 && date > 30) {
            value = "Invalid Date";
        }
        if (month == 12 && date >= 22 || month == 1 && date <= 19) {
            value = "摩羯座";
        }
        if (month == 12 && date > 31) {
            value = "No way!";
        }
        return value;
    }
};
util.confirm.init();