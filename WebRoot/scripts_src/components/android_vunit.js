var device = require('../modules/device');
var $ = require('jquery');

if (device.android && device.version<4.4) {
    (function () {
        var $win = $(window)
            , _css = $.fn.css;

        function viewportToPixel(val) {
            var percent = val.match(/[\d.]+/)[0] / 100
                , unit = val.match(/[vwh]+/)[0];
            return (unit == 'vh' ? $win.height() : $win.width()) * percent + 'px';
        }

        function parseProps(props) {
            var p, prop;
            for (p in props) {
                prop = props[p];
                if (/[vwh]$/.test(prop)) {
                    props[p] = viewportToPixel(prop);
                }
            }
            return props;
        }

        $.fn.css = function (props) {
            var self = this
                , update = function () {
                    return _css.call(self, parseProps(props));
                };
            $win.resize(update).resize();
            return update();
        };
    }());

    function setStyle(dom) {
        var $dom = $(dom);
        var styleText = $dom.css('content');
        if (styleText.length) {
            var styleRule = styleText.replace(/\"|\'/g, '').split(';');
            var style = {};
            styleRule.forEach(function (rule) {
                rule = rule.split(':');
                style[rule[0]] = rule[1];
            });
            style.content = 'normal';
            $dom.addClass('vunit-render').css(style);
        }
    }

    //var sheets = document.styleSheets;
    //for (var i=0; i<sheets.length; i++) {
    //    var sheet = sheets[i];
    //    var rules = sheet.rules || sheet.cssRules;
    //    var ruleLenght = rules.length;
    //    for(var i = 0; i<ruleLenght; i++){
    //        var rule = rules[i];
    //        if (rule.cssText.match(/counter-reset/)) {
    //            console.log(rule.selectorText);
    //        }
    //    }
    //}

    //var rules = document.styleSheets[1].rules || document.styleSheets[1].cssRules;
    //for (var i = 0; i<rules.length; i++) {
    //    var rule = rules[i];
    //    if (rule.selectorText && rule.selectorText.toLowerCase() == ".test") {
    //        console.log(rule.cssText);
    //    }
    //}


    //function render() {
    //    $('.vunit-item:not(".vunit-render")').each(function () {
    //        setStyle(this);
    //    })
    //}

    document.body.classList.add('android-vunit');
    $('body').on('DOMSubtreeModified', function (event) {
        //render();
    });
}
