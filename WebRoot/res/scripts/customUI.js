var customUI = {};
$(function () {
    var _$body = $('body'),
        _$formControl = $('.form-control');

    // checkbox
    _$body.on('click', '.custom-checkbox:not(.disabled)', function () {
        var $custom = $(this).toggleClass('active');
    });

    // 因为ie表单的自动完成性，需判断原页面的input是否已有值
    $('.custom-checkbox').each(function () {
        var $this = $(this),
            input = $this.find('input')[0];
        if (input.checked)
            $this.addClass('active');
    });

    // radio
    _$body.on('click', '.custom-radio:not(.disabled)', function () {
        var $this = $(this),
            name = $this.find('input').attr('name');
        $('[name="' + name + '"]').parents('.custom-radio').removeClass('active');
        $this.addClass('active');
    });

    // select
    $('.form-control-customSelect').find('select').change(function (){
        var $select = $(this);
        var choosedText = $select.find('option:selected').html();
        $select.nextAll('.form-control-customSelect-value').html(choosedText);
    })

    // 因为ie表单的自动完成性，需判断原页面的input是否已有值
    $('.custom-radio').each(function () {
        var $this = $(this),
            input = $this.find('input')[0];
        if (input.checked)
            $this.addClass('active');
    });

    // inputSwitchEdit jQuery extend
    var switchForm = {
        template: {
            addCustomCheckbox: '<div class="form-control-customCheckbox custom-checkbox active edit">' +
                '<label class="form-control-customCheckbox-label">' +
                '<i class="yiqi-icon-lPlus"></i></label>' +
                '</div>',
            chooseCheckbox: '<div class="form-control-customCheckbox custom-checkbox active">' +
                '<label class="form-control-customCheckbox-label"></label>' +
                '<input type="checkbox" checked class="form-control-checkbox valid-input disabled" ' +
                '</div>',
            otherCheckbox: '<div class="form-control-customCheckbox custom-checkbox">' +
                '<label class="form-control-customCheckbox-label"></label>' +
                '<input type="checkbox" class="form-control-checkbox valid-input" name="domain"' +
                '</div>'
        },
        $doms: {
            $chooseCheckboxView: $('.form-control-chooseCheckboxView'),
            $otherCheckboxView: $('.form-control-otherCheckboxView')
        },
        cache: {

        },
        action: {
            getChooseCheckboxView: function (group) {
                return $('.form-control-chooseCheckboxView[data-switchForm-group="' + group + '"]');
            },
            getOtherCheckboxView: function (group) {
                return $('.form-control-otherCheckboxView[data-switchForm-group="' + group + '"]');
            },
            switchCustomCheckboxEdit: function () {
                var $addCheckbox = $(switchForm.template.addCustomCheckbox);
                switchForm.$doms.$chooseCheckboxView.append($addCheckbox);
            },
            switchCustomCheckboxNow: function () {
                $('.custom-checkbox.edit').remove();
                switchForm.$doms.$otherCheckboxView.hide();
                switchForm.$doms.$chooseCheckboxView.html(switchForm.cache.choose);
                switchForm.$doms.$otherCheckboxView.html(switchForm.cache.other);
            },
            addCheckboxToView: function ($customCheckbox, group, text, $chooseView, $originCheckbox) {
                $customCheckbox.find('label').text(text);
                $customCheckbox.find('input').attr('name', group)
                    .attr('data-valid-group', group)
                    .attr('id', $originCheckbox.find('input').attr('id'));
                $chooseView.append($customCheckbox);
                $originCheckbox.remove();
            },
            switchEdit: function ($this) {
                function setInputValue(input, value, lazy) {
                    if (lazy) {
                        setTimeout(function () {
                            input.val(value).change();
                        }, lazy);
                    } else {
                        input.val(value).change();
                    }
                }

                var $value = $this.find('.form-control-value');
                $value.each(function () {
                    var $this = $(this),
                        $input = $this.nextAll('.form-control-input');
                    if ($input.length) {
                        var attrValue = $this.attr('data-switch-value');
                        var value = (attrValue && attrValue.split(',')) || $this.html();
                        $this.hide();
                        if ($.isArray(value)) {
                            var $inputOrigins = $input.show().find('input,select,textarea');
                            for (var i = 0; i < value.length; i++) {
                                var $inputOrigin = $inputOrigins.eq(i),
                                    valueLazy = $inputOrigin.attr('data-switch-valueLazy');
                                setInputValue($inputOrigin, value[i], valueLazy);
                            }
                        } else {
                            value = $.trim(value);
                            $input.show().find('input,select,textarea').val(value);
                        }
                    }
                });

                this.switchCustomCheckboxEdit();

            },
            switchNow: function ($this) {
                var $input = $this.find('.form-control-input');
                $input.each(function () {
                    var $this = $(this),
                        $label = $this.prevAll('.form-control-value');
                    if ($label.length) {
                        $this.hide();
                        $label.show();
                    }
                });
                this.switchCustomCheckboxNow();
            }
        }, events: {
            editCustomCheckboxClick: function () {
                var $this = $(this) ,
                    group = $this.parents('.form-control-chooseCheckboxView').attr('data-switchForm-group'),
                    $chooseView = switchForm.action.getChooseCheckboxView(group),
                    $otherView = switchForm.action.getOtherCheckboxView(group);
                $otherView.slideDown(200);
                $chooseView.find('.custom-checkbox').removeClass('disable').addClass('active');
                $chooseView.find('input').removeAttr('disabled');
                $this.remove();
            },
            addCustomCheckboxClick: function () {
                var $this = $(this),
                    group = $this.parents('.form-control-otherCheckboxView').attr('data-switchForm-group'),
                    text = $this.find('label').text(),
                    $customCheckbox = $(switchForm.template.chooseCheckbox),
                    $chooseView = switchForm.action.getChooseCheckboxView(group);
                switchForm.action.addCheckboxToView($customCheckbox, group, text, $chooseView, $this);
            },
            removeCustomCheckboxClick: function () {
                var $this = $(this),
                    group = $this.parents('.form-control-chooseCheckboxView').attr('data-switchForm-group'),
                    text = $this.find('label').text(),
                    $customCheckbox = $(switchForm.template.otherCheckbox),
                    $chooseView = switchForm.action.getOtherCheckboxView(group);
                switchForm.action.addCheckboxToView($customCheckbox, group, text, $chooseView, $this);
            }
        },
        init: function () {
            var selector = {
                customCheckboxEdit: '.custom-checkbox.edit',
                customCheckboxAdd: '.form-control-otherCheckboxView[data-switchForm-group] .custom-checkbox',
                customCheckboxRemove: '.form-control-chooseCheckboxView[data-switchForm-group] .custom-checkbox.active:not(.edit)'
            };

            // events bind
            _$body.on('click', selector.customCheckboxEdit, this.events.editCustomCheckboxClick);
            _$body.on('click', selector.customCheckboxAdd, this.events.addCustomCheckboxClick);
            _$body.on('click', selector.customCheckboxRemove, this.events.removeCustomCheckboxClick);
            this.cache.choose = this.$doms.$chooseCheckboxView.html();
            this.cache.other = this.$doms.$otherCheckboxView.html();

            // jQuery extend
            var actions = this.action;
            $.fn.switchForm = function (type, callback) {
                var $this = $(this);
                switch (type) {
                    case 'edit':
                        actions.switchEdit($this);
                        break;
                    case 'now':
                        actions.switchNow($this);
                        break;
                }
                $.isFunction(callback) && callback();
            };
        }
    };
    switchForm.init();

    // tab
    var tab = {
        $doms: {
            $buttons: $('.tab-button'),
            $contents: $('.tab-content')
        },
        events: {
            buttonClick: function () {
                var $button = $(this),
                    name = $button.attr('data-tab-name');
                tab.$doms.$buttons.removeClass('active')
                    .filter('[data-tab-name="' + name + '"]')
                    .addClass('active');
                tab.$doms.$contents.removeClass('active').hide();
                tab.$doms.$contents.filter('[data-tab-name="' + name + '"]').show().addClass('active');
            }
        },
        init: function () {
            this.$doms.$buttons.click(this.events.buttonClick);
            this.$doms.$contents.filter(':not(.active)').hide();
        }
    };
    tab.init();

    // image-preview
    var imagePreview = {
        $doms: {
            $preview: $('.image-preview'),
            $file: $('.image-preview :file')
        },
        action: {
            clearImgs: function (input) {
                $(input).parents('.image-preview').find('img').remove();
            },
            checkFileType: function (type) {
                return type.search(/jpg|jpeg|png|gif/) > -1;
            },
            errorFileType: function () {
                alert('请上传图片');
            },
            setReaderEvent: function (reader, input) {
                reader.onload = function (e) {
                    var $img = $('<div class="image-preview-area">' +
                        '<img src="' + e.target.result + '" >' +
                        '<div class="image-preview-remove"><i class="add-icon-lClose"></i></div>' +
                        '</div>');
                    var $preview = $(input).parents('.image-preview').append($img);
                }
            },
            addRemoveButton: function () {

            }
        },
        event: {
            fileChange: function () {
                if (this.files) {
                    var length = this.files.length;
                    for (var i = 0; i < length; i++) {
                        var file = this.files[i];
                        if (imagePreview.action.checkFileType(file.type)) {
                            var reader = new FileReader();
                            imagePreview.action.setReaderEvent(reader, this, $(this).attr('multiple'));
                            reader.readAsDataURL(file);
                        }
                    }
                } else {
                    this.select();
                    this.blur();
                    var path = document.selection.createRange().text;
                    if (imagePreview.action.checkFileType(path.substring(path.lastIndexOf(".") + 1
                        , path.length).toLowerCase())) {
                        var $img = $('<div class="image-preview-area">' +
                            '<div style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale' +
                            ',src=' + path + ');' +
                            'width:100%;height:100%"  ></div>' +
                            '<div class="image-preview-remove"><i class="add-icon-lClose"></i></div>' +
                            '</div>');
                        var $preview = $(this).parents('.image-preview').append($img);
                    }
                }
            },
            removeClick: function () {
                var $this = $(this),
                    $preview = $this.parents('.image-preview'),
                    $area = $this.parents('.image-preview-area'),
                    $input = $preview.find(':file');
                $area.remove();
                $input.val('');
            }
        },
        init: function () {
            this.$doms.$file.on('change', this.event.fileChange);
            _$body.on('click', '.image-preview-remove', this.event.removeClick);
        }
    };
    imagePreview.init();

    // datepicker
    // not good
    var dateConfig = {
        monthsFull: [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月' ],
        monthsShort: [ '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二' ],
        weekdaysFull: [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ],
        weekdaysShort: [ '日', '一', '二', '三', '四', '五', '六' ],
        today: '',
        clear: '',
        firstDay: 1,
        format: 'yyyy-mm-dd',
        formatSubmit: 'yyyy/mm/dd',
        onClose: function () {
            var $input = this.$node,
                datePicker = this,
                limitGroup = $input.attr('data-datepicker-limit');
            if (limitGroup) {
                setLimit(this, limitGroup);
            }
            if ($input.nextAll('.timePicker').length) {
                var group = $input.attr('data-datepicker-group'),
                    timePicker = $time.filter('[data-datepicker-group="' + group + '"]').pickatime('picker');
                timePicker.open();
                timePicker.set('select', timePicker.get('select') || [8, 0]);
                timePicker.on('close', function () {
                    $input.val(datePicker.get('select', 'yyyy-mm-dd') + ' ' + timePicker.get());
                    timePicker.off('set');
                })
            }
        }
    };

    function setLimit(fromPicker, group) {
        var $fromInput = fromPicker.$node,
            fromType = $fromInput.hasClass('dateStart') ? 'Start' : 'End' ,
            toType = fromType === 'Start' ? 'End' : 'Start',
            toPicker = $date.filter('.date' + toType + '[data-datepicker-limit="' + group + '"]').pickadate('picker'),
            value = fromPicker.get('select');

        if (toType === 'Start') {
            toPicker.set('max', value);
        } else if (toType === 'End') {
            toPicker.set('min', value);
        }
    }

    var $date = $('.datePicker'),
        $datePicker = $(),
        $timePicker;

    if ($date.length) {
        $date.each(function () {
            var $this = $(this);
            var customConfig = $.extend({},dateConfig, JSON.parse($this.attr('data-datepicker-config') || '{}'  )  );
            $datePicker.add($this.pickadate(customConfig));
        })
    }
    customUI.datePicker = $datePicker;

    // timepicker
    var $time = $('.timePicker');
    if ($time.length) {
        $time.pickatime();
    }

    // 行业与职业
    var $job = $('.form-control-select.job') ,
        $business = $('.form-control-select.business').on('change', function () {
            var type = $business.val();
            loadBusiness(type);
        });

    function loadBusiness(type) {
        type = type || 0;
        $.ajax(root + 'user/getclasssortlist', {
            dataType: 'json',
            data: {
                type: 1,
                pid: type
            }
        })
            .done(function (data) {
                var options = '';
                $.each(data.data, function () {
                    options += '<option value="' + this.id + '" >' + this.zname + '</option>'
                });
                type === 0 ? $business.html(options) : $job.html(options);

            })
            .fail(function () {

            })
    }

    loadBusiness();


    // iframe response
    // firefox iframe 不显示的时候无法获取实际高度
    var $iframes = $('iframe').load(function () {
        tab.$doms.$contents.show();
        $iframes.each(function () {
            var $this = $(this);
            if ($this.is('.iframe-response-height')) {
                var height = this.contentDocument.body.clientHeight;
                $this.height(height);
            }
        });
        tab.$doms.$contents.filter(':not(.active)').hide();
    });


});