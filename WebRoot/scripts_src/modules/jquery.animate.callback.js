var $ = require('jquery');
$.fn.cssAnimate = function (className, cb) {
    var duration = 0;
    this.addClass(className);
    duration = parseFloat(this.css('animation-duration')) * 1000 + parseFloat(this.css('animation-delay')) * 1000;
    setTimeout(function () {
        cb && cb(className);
    }, duration - 10);
    return this;
};

$.fn.cssTransitionClass = function (classname, cb) {
    var duration;
    this.addClass(classname);
    duration = parseFloat(this.css('transition-duration')) * 1000 + parseFloat(this.css('transition-delay')) * 1000;
    return setTimeout(function () {
        cb && cb();
    }, duration - 10);
};


$.fn.cssTransition = function (style, cb) {
    var duration;
    this.css(style);
    duration = parseFloat(this.css('transition-duration')) * 1000 + parseFloat(this.css('transition-delay')) * 1000;
    return setTimeout(function () {
        cb && cb();
    }, duration - 10);
};
