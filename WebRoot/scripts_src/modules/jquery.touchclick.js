var $ = require('jquery');

(function () {
    // 为避免ios touchend问题把alert设为异步处理
    window.requestAnimationFrame = (function () {
        return window.requestAnimationFrame ||
            window.webkitRequestAnimationFrame ||
            window.mozRequestAnimationFrame ||
            function (callback) {
                window.setTimeout(callback, 1000 / 60);
            };
    })();
})();

var _alert = window.alert;
var _confirm = window.confirm;

function asyncAlert(text) {
    requestAnimationFrame(function () {
        _alert(text);
    })
}

function asyncConfirm(text) {
    requestAnimationFrame(function () {
        _confirm(text);
    })
}

function setAsync() {
    window.alert = asyncAlert;
    //window.confirm = asyncConfirm;
}

function cancelAsync() {
    window.alert = _alert;
    //window.confirm = _confirm;
}

$.fn.touchclick = function (handle) {
    var lock = false,
        self = this;
    this.on('touchstart', function () {
        lock = false;
    }).on('touchmove', function () {
        lock = true;
    }).on('touchend', function (event) { // ios所有版本的touchend事件中如果有如alert之类的打断情况
        if (lock) {                        // 会在下一次全局范围监听这个touchend事件
            return false;
        }
        setAsync();
        handle.call(this, event);
        cancelAsync();
    });
    return self;
}

$.fn.livetouchclick = function (query, handle) {
    var lock = false,
        self = this;
    this.on('touchstart', query, function () {
        lock = false;
    }).on('touchmove', query, function () {
        lock = true;
    }).on('touchend', query, function (event) {
        if (lock) {
            return false;
        }
        setAsync();
        handle.call(this, event);
        cancelAsync();
    });
    return self;
}


$.fn.quicktouchclick = function (element, handle) {
    var hold,
        lock = false,
        self = this;
    if (arguments.length === 1) {
        handle = element;
        self.on('touchstart', function () {
            hold = Date.now();
            lock = false;
        }).on('touchmove', function () {
            lock = true;
        }).on('touchend', function (event) {
            hold = Date.now() - hold;
            if (lock || hold >= 500) {
                return false;
            }
            setAsync();
            handle.call(this, event);
            cancelAsync();
        });
    } else if (arguments.length === 2) {
        self.on('touchstart', element, function () {
            hold = Date.now();
            lock = false;
        }).on('touchmove', element, function () {
            lock = true;
        }).on('touchend', element, function (event) {
            hold = Date.now() - hold;
            if (lock || hold >= 200) {
                return false;
            }
            setAsync();
            handle.call(this, event);
            cancelAsync();
        });
    }
    return self;
}

$.fn.holdtouchclick = function (element, handle) {
    var hold,
        lock = false,
        self = this;
    if (arguments.length === 1) {
        handle = element;
        self.on('touchstart', function () {
            hold = Date.now();
            lock = false;
        }).on('touchmove', function () {
            lock = true;
        }).on('touchend', function (event) {
            hold = Date.now() - hold;
            if (lock || hold < 500) {
                return false;
            }
            setAsync();
            handle.call(this, event);
            cancelAsync();
        });
    } else if (arguments.length === 2) {
        self.on('touchstart', element, function () {
            hold = Date.now();
            lock = false;
        }).on('touchmove', element, function () {
            lock = true;
        }).on('touchend', element, function (event) {
            hold = Date.now() - hold;
            if (lock || hold < 200) {
                return false;
            }
            setAsync();
            handle.call(this, event);
            cancelAsync();
        });
    }
    return self;
}
