$(function () {
    var $doms = $('.valid-input'),
        $body = $('body'),
        verify = {
            phone: function (value) {
                return value.length === 0 || /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/.test(value);
            },
            email: function (value) {
                return value.length === 0 || /\w@\w*\.\w/.test(value);
            },
            number: function (value) {
                return /\d/g.test(value);
            },
            version: function (value) {
                return /\d\.\d/g.test(value);
            },
            required: function (value, $dom, type) {
                if (type === 'checkbox' || type === 'radio') {
                    var name = $dom.attr('name');
                    if (action.getGroupInputChecked($dom).length === 0) {
                        return false;
                    }
                } else {
                    value = $.trim(value);
                    if (value === '' || value === undefined) {
                        return false;
                    }
                }
                return true;
            },
            max: function (value, $dom, type) {
                var max = $dom.attr('data-valid-max');
                if (type === 'text') {
                    if (action.isInputValidNumber($dom)) {
                        value = parseInt(value);
                        return value < max;
                    }
                    if ($.trim(value).length > max) {
                        return false;
                    }
                } else if (type === 'checkbox') {
                    if (action.getGroupInputChecked($dom).length > max) {
                        return false;
                    }
                }
                return true;
            },
            min: function (value, $dom, type) {
                var min = $dom.attr('data-valid-min');
                if (type === 'text') {
                    if (action.isInputValidNumber($dom)) {
                        value = parseInt(value);
                        return value > min;
                    }
                    if ($.trim(value).length < min) {
                        return false;
                    }
                } else if (type === 'checkbox') {
                    if (action.getGroupInputChecked($dom) < min) {
                        return false;
                    }
                }
                return true;
            },
            image: function (vale) {
                return /jpeg|jpg|png|gif/.test(vale);
            },
            same: function (value, $dom) {
                var othValue = $($dom.attr('data-valid-same')).val();
                return value === othValue
            },
            url: function (value) {
                return /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/
                    .test(value);
            },
            money: function (value) {
                return /^\d*$/.test(value.replace(/-/g, ''))
                    && /\d/.test(value[value.length - 1])
                    && /\d/.test(value[0]);
            },
            idCard: function (value) {
                return /^\d{6}((2000|(19|21)(0[48]|[2468][048]|[13579][26]))0229|(((20|19)\d\d)|2100)(0[13578]|10|12)(3[01]|[12]\d|0[1-9])|(0[469]|11)(30|[12]\d|0[1-9])|(02)(2[0-8]|1\d|0[1-9]))\d{3}[\dX]$/.test(value);
            }
        },
        action = {
            findGroupLast: function ($dom) {
                var groupName = $dom.attr('data-valid-group') ,
                    $group = $('[data-valid-group=' + groupName + ']');
                return $group.filter('[data-valid-rule]')
            },
            getGroupInput: function ($dom) {
                return $('[name="' + $dom.attr('name') + '"]');
            },
            getGroupInputChecked: function ($dom) {
                return action.getGroupInput($dom).filter(':checked');
            },
            getRule: function ($dom) {
                return $dom.attr('data-valid-rule').split(',');
            },
            getInputType: function ($dom) {
                var type = $dom[0].nodeName.toLowerCase();
                if (type === 'input') {
                    type = $dom.attr('type');
                }
                if (type === 'url' || type === 'email' || type === 'phone' || type === 'password' || type === 'number') {
                    type = 'text';
                }
                return type;
            },
            setError: function ($dom, rule) {
                var validText = JSON.parse($dom.attr('data-valid-text') || '{}'),
                    text = validText[rule],
                    $error = $('<span class="valid-error" ></span>');

                if (text === undefined) {
                    switch (rule) {
                        case 'required':
                            text = '这是一个必填项';
                            break;
                        case 'email':
                            text = '请填写正确的邮箱地址';
                            break;
                        case 'number':
                            text = '请填写数字';
                            break;
                        case 'version':
                            text = '请输入合适的浮点型版本号';
                            break;
                        case 'phone':
                            text = '请填写正确的电话号码';
                            break;
                        case 'max':
                            text = '选择的内容不得多于' + $dom.attr('data-valid-max') + '项';
                            break;
                        case 'min':
                            text = '选择的内容不得少于' + $dom.attr('data-valid-min') + '项';
                            break;
                        case 'same':
                            text = '该项内容并不相符';
                            break;
                        default :
                            break;
                    }
                }
                $error.html(text);
                $dom.after($error);
            },
            clearError: function ($dom) {
                if (action.isInputGroup($dom)) {
                    $dom = action.findGroupLast($dom)
                }
                $dom.next('.valid-error').remove();
            },
            isInputGroup: function ($dom) {
                return $dom.is('[data-valid-group]') || $dom.is(':checkbox') || $dom.is(':radio');
            },
            isInputGroupLast: function ($dom) {
                return action.isInputGroup($dom) && !$dom.is('[data-valid-rule]');
            },
            isInputValidNumber: function ($dom) {
                return $dom.attr('data-valid-rule').search(/number/) > -1;
            },
            validInput: function ($dom) {
                if (action.isInputGroup($dom)) {
                    $dom = action.findGroupLast($dom)
                }
                return action.validRule(action.getRule($dom), action.getInputType($dom), $dom);
            },
            validRule: function (ruleArray, type, $dom) {
                var value = $dom.val() ,
                    inputPass = true;
                for (var i = 0; i < ruleArray.length; i++) {
                    var result = verify[ruleArray[i]](value, $dom, type);
                    if (!result) {
                        action.setError($dom, ruleArray[i]);
                        inputPass = false;
                        break;
                    }
                }
                return inputPass;
            }
        };

    // input events
    $doms.filter('[type="text"],[type="password"],[type="email"],[type="phone"],[type="number"],textarea')
        .on('blur', function () {
            var $this = $(this);
            action.clearError($this);
            action.validInput($this);
        });

    $doms.filter('select,:file').on('change', function () {
        var $this = $(this);
        setTimeout(function () {
            action.clearError($this);
            action.validInput($this);
        }, 200);
    });

    $body.on('click', '.valid-input:checkbox,.valid-input:radio', function () {
        var $this = $(this);
        action.clearError($this);
        action.validInput($this);
    });

    $.fn.valid = function (callback) {
        var $validDOM = $(this).find($doms).not('[disabled]'),
            validPass = true;
        $validDOM.each(function () {
            var $this = $(this);

            // 增加组合input处理
            if (action.isInputGroupLast($this)) {
                return true;
            }
            action.clearError($this);
            if (!action.validInput($this)) {
                validPass = false;
            }
        });
        callback && callback(validPass);
    };

    $.fn.clearValid = function (callback) {
        var $this = $(this);
        $this.children('.valid-error').remove();
    };

    $.addValid = function ($validDoms) {
        $validDoms.each(function () {
            var $this = $(this).on('blur', function () {
                action.clearError($this);
                action.validInput($this);
            });
            $doms = $doms.add($this);
        })
    }
});
