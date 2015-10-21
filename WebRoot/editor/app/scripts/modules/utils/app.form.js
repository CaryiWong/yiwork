angular.module('app.form', [])
    .directive('datePicker', function () {
        return {
            require: '?ngModel',
            link: function (scope, ele, attrs, ngModel) {
                function setLimit(fromPicker) {
                    var $fromInput = ele,
                        fromType = $fromInput.hasClass('dateStart') ? 'Start' : 'End',
                        toType = fromType === 'Start' ? 'End' : 'Start',
                        toPicker = $('.date' + toType).pickadate('picker'),
                        value = fromPicker.get('select');

                    if (toPicker) {
                        if (toType === 'Start') {
                            toPicker.set('max', value);
                        } else if (toType === 'End') {
                            toPicker.set('min', value);
                        }
                    }
                }

                var dateConfig = {
                    monthsFull: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                    monthsShort: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'],
                    weekdaysFull: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
                    weekdaysShort: ['日', '一', '二', '三', '四', '五', '六'],
                    today: '',
                    clear: '',
                    firstDay: 1,
                    format: 'yyyy-mm-dd',
                    formatSubmit: 'yyyy/mm/dd',
                    onClose: function () {
                        var datePicker = this,
                            timePicker = ele.nextAll('.timePicker').pickatime('picker');
                        setLimit(this);
                        timePicker.open();
                        timePicker.set('select', timePicker.get('select') || [8, 0]);
                        timePicker.on('close', function () {
                            var value = datePicker.get('select', 'yyyy-mm-dd') + ' '
                                + timePicker.get()
                                + ':00';
                            ngModel.$setViewValue(value);
                            ele.val(value);
                        });

                    }
                };
                ele.pickadate(dateConfig);
            }
        }
    })
    .directive('timePicker', function () {
        return {
            link: function (scope, ele) {
                ele.pickatime({
                    format: 'HH:i',
                    formatSubmit: 'HH:i'
                });
            }
        }
    })
    .directive('addChildActivity', function () {
        return {
            scope: {
                activity: '='
            },
            link: function (scope, ele) {
                ele.on('click', function () {
                    scope.$apply(function () {
                        scope.activity.push({
                            name: "摊位名称",
                            people: '10',
                            activityType: 'sub',
                            moneyPair: [
                                {
                                    name: "非会员价",
                                    value: 100
                                },
                                {
                                    name: "会员价",
                                    value: 50
                                }
                            ]
                        });
                    });
                })
            }
        }
    })
    .directive('removeChildActivity', function () {
        return {
            scope: {
                activity: '=',
                index: '@'
            },
            link: function (scope, ele) {
                ele.on('click', function () {
                    scope.$apply(function () {
                        scope.activity.splice(scope.index, 1);
                    })
                })
            }
        }
    })
    .directive('closeDialog', function () {
        return function (scope, ele, attrs) {
            ele.on('click', function () {
                dialog.close();
            })
        }
    });
