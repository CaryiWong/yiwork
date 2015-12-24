var $ = require('jquery');
require('../../sass/components/yiqi_input.scss');
require('../modules/jquery.touchclick');
var quickTip = require('../components/yiqi_quicktip');
var server = require('./yiqi_server');
var $body = $('body');
var $renderArea = $('.yiqi-render-area');
$renderArea.length === 0 ? $renderArea = $body : undefined;

$body.livetouchclick('.yiqi-input-area.text-area', function (event) {
    event.preventDefault();
    $(this).find('input').focus();
});

$body.livetouchclick('.yiqi-input-area.radio-area', function () {
    $(this).find('input')[0].checked = true;
});

$body.livetouchclick('.yiqi-input-area.checkbox-area', function (event) {
    event.preventDefault();
    var checked = $(this).find('input')[0].checked;
    $(this).find('input')[0].checked = !checked;
});

$body.on('change', '.yiqi-input-select', function () {
    var $this = $(this);
    var value,
    $selected = $this.find('option:selected'),
    limit = $this.attr('data-value-limit') || 8;

    if ($selected.length > 1) {
        limit = limit - 0;
        value = [];
        $selected.each(function () {
            value.push(this.innerHTML);
        })
        value = value.join(',');
    } else if ($selected.length === 1) {
        value = $selected.text();
    } else {
        $selected = $this.find('option').first();
        $this.val($selected.val());
        value = $selected.text();
    }
    value = value.length > limit ? value.slice(0, limit) + '...' : value;
    $this.next('.yiqi-input-value').removeClass('default-text').find('.value-text')
    .text(value);
})

$.fn.renderText = function () {
    var $input = this.not('.yiqi-input-render');
    $input.each(function (i) {
        var $this = $input.eq(i);
        var label = $this.attr('data-input-label');
        $this.addClass('yiqi-input-render')
        .wrap('<div class="yiqi-input-area text-area"></div>')
        .before('<div class="yiqi-input-label">' + label + '</div>');
    })
}

$.fn.renderSelect = function () {
    var $input = this.not('.yiqi-input-render');
    $input.each(function (i) {
        var $this = $input.eq(i);
        var label = $this.attr('data-input-label');
        var value = $this.attr('data-input-default') || '';
        var $selected = $this.find('option:selected');
        $this.addClass('yiqi-input-render')
        .wrap('<div class="yiqi-input-area select-area"></div>')
        .before('<div class="yiqi-input-label">' + label + '</div>');
        if ($selected.length) {
            if ($selected.length === 1) {
                $this.after('<div class="yiqi-input-value"><span class="value-text">' + $selected.text() + '</span></div>');
            }
        } else {
            $this.after('<div class="yiqi-input-value default-text"><span class="value-text">' + value + '</span></div>');
        }
        $this.change();
    });
}

$.fn.renderRadio = function () {
    var $input = this.not('.yiqi-input-render');
    var label = this.attr('data-input-label');
    $input.addClass('yiqi-input-render')
    .wrap('<div class="yiqi-input-area radio-area"></div>')
    .after('<div class="yiqi-input-label">' + label + '</div>')
    .after('<div class="yiqi-input-control"></div>');
}

$.fn.renderCheckbox = function () {
    var $input = this.not('.yiqi-input-render');
    var label = this.attr('data-input-label');
    $input.addClass('yiqi-input-render')
    .wrap('<div class="yiqi-input-area checkbox-area"></div>')
    .after('<div class="yiqi-input-label">' + label + '</div>')
    .after('<div class="yiqi-input-control"></div>');
}

$.fn.renderCodeText = function () {
    var $input = this.not('.yiqi-input-render');
    if ($input.length) {
        $input.renderText();
        $input.after('<div class="yiqi-code-button">发送验证码</div>')
        .parents('.yiqi-input-area').addClass('text-code-area');

        $input[0].sendCode = function (phone) {
            var $button = $input.nextAll('.yiqi-code-button');
            if (!$button.hasClass('disabled')) {
                server.post('v20/sms/sendvalidatesms', {
                    telnum: phone
                }).done(function () {
                    var limitTime = 60;
                    quickTip.success('成功发送验证码', 2000);
                    $button.addClass('disabled')
                    .html('重新发送('+ limitTime +'s)');
                    var lockClock = setInterval(function () {
                        limitTime--;
                        if(limitTime){
                            $button.html('重新发送('+ limitTime +'s)');
                        } else {
                            $button.html('发送验证码').removeClass('disabled');
                            clearInterval(lockClock);
                        }
                    }, 1000);
                }).fail(function (err) {
                    quickTip.fail(err);
                });
            }
        }

        $input[0].validCode = function (phone, code) {
            return server.post('v20/sms/validatecode', {
                telnum: phone,
                code: code
            })
        }
    }
}

$.fn.renderFileImage = function () {
    this.on('change', function () {
        var codes = [];
        var $input = $(this);
        var files = this.files;
        var index = 0;
        var length = files.length;

        function loadFile() {
            var file = files[index];
            var reader = new FileReader();
            reader.onload = function (event) {
                var code = reader.result.toString();
                code = code.replace(/^data:base64/, 'data:;base64') // 普通安卓的base64格式出错
                codes.push({url: code, file: file});
                index++;
                if (index >= length) {
                    $input.trigger('imagepreview', [codes]);
                } else {
                    loadFile();
                }
            }
            reader.readAsDataURL(file);
        }

        loadFile();
    })
    return this;
}

var renderInput = {
    text: function () {
        $('.yiqi-input-text').renderText();
    },
    select: function () {
        $('.yiqi-input-select').renderSelect();
    },
    radio: function () {
        $('.yiqi-input-radio').renderRadio();
    },
    checkbox: function () {
        $('.yiqi-input-checkbox').renderCheckbox();
    },
    codeText: function () {
        $('.yiqi-input-text-code').renderCodeText();
    }
};

var render = (function render() {
    renderInput.text();
    renderInput.select();
    renderInput.radio();
    renderInput.checkbox();
    renderInput.codeText();
    $renderArea.data('loading', false);
    return render;
})();

$renderArea.on('DOMSubtreeModified', function (event) {
    if ($renderArea.data('loading')) {
        return false;
    }
    $renderArea.data('loading', true);
    render();
});
