var $body = window.$body || $('body');
window.dialog = {
    _convertTojQuery: function (dom) {
        var $dom;
        if (dom instanceof $) {
            $dom = dom;
        } else {
            $dom = $(dom);
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
    setStyle: function ($dom, $parent) {
        this._clearStyle($dom);
        var width = $dom.outerWidth(),
            height = $dom.outerHeight(),
            marginTop = -(height / 2),
            marginLeft = -(width / 2),
            position = ($parent === $body) ? 'fixed' : 'absolute';
        $dom.css({
            position: position,
            top: '50%',
            left: '50%',
            marginTop: marginTop,
            marginLeft: marginLeft,
            zIndex: 999
        });
    },
    _setResize: function ($dom) {
        var self = this;
        window.onresize = function () {
            self.setStyle($dom);
        }
    },
    show: function (dom, $parent, callback) {
        var argLength = arguments.length,
            lastArg = arguments[arguments.length - 1];
        if ($parent === undefined) {
            $parent = $body;
        }
        if (argLength < 3) {
            if ($.isFunction(lastArg)) {
                if (argLength === 2) {
                    callback = lastArg;
                    $parent = $body;
                }
            }
        }
        var $dom = this._convertTojQuery(dom).addClass('dialog-content').show(0);
        $parent.css('position', 'relative').append($dom);
        this.setStyle($dom, $parent);
        mask.set($parent);
        callback && callback();
        return $dom;
    },
    close: function ($parent, callback) {
        if ($parent === undefined) {
            $parent = $body;
        }
        if ($.isFunction($parent)) {
            callback = $parent;
            $parent = $body;
        }
        $parent.children('.dialog-mask').remove();
        $parent.children('.dialog-content').hide();
        $.isFunction(callback) && callback();
    }
};