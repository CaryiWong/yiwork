angular.module('app.interact', ['app.drag'])
    //.factory('InteractPageType', function () { // 模板类型
    //    var defaultSetting = {type: 'content', files: []};
    //    return function (obj) {
    //        var setting = {};
    //        $.extend(setting, defaultSetting, obj);
    //        this.name = setting.name;
    //        this.desc = setting.desc;
    //        this.files = setting.files;
    //        this.switchName = setting.switchName;
    //        this.type = setting.type;
    //    }
    //})
    //.factory('interactTemplates', function () {
    //    var data;
    //    return {
    //        get: function () {
    //            return data;
    //        },
    //        set: function (v) {
    //            data = v;
    //        }
    //    }
    //})
    //.factory('setInteractTemplates', function (InteractPageType, getAllTemplates, interactTemplates) {
    //    return function (ts, cb) {
    //        getAllTemplates('interact')
    //            .then(function (data) {
    //                angular.forEach(data, function (obj) {
    //                    ts.push(new InteractPageType(obj));
    //                });
    //                interactTemplates.set(ts);
    //                cb && cb();
    //            });
    //    };
    //})
    //.directive('typeTemplate', function (interactTemplates) {
    //    return {
    //        scope: {
    //            page: '=typeTemplate',
    //            type: '@typeName'
    //        },
    //        link: function (scope, ele, attr) {
    //            ele.on('click', function () {
    //                scope.$apply(function () {
    //                    scope.page.type = _.find(interactTemplates.get(), function (t) {
    //                        return t.name === scope.type;
    //                    });
    //                    scope.page.html = '';
    //                });
    //            })
    //        }
    //    }
    //})
    //.directive('toolTemplate', function ($http, getTemplateHTML) {
    //    return {
    //        scope: {
    //            page: '=toolTemplate'
    //        },
    //        link: function (scope, ele, attr) {
    //            var templateName = attr['templateName'],
    //                templateUrl = attr['templateUrl'];
    //            ele.on('click', function () {
    //                getTemplateHTML(templateUrl, 'interact').then(function (data) {
    //                    scope.page.html = data;
    //                    scope.page.templateName = templateName;
    //                });
    //            })
    //        }
    //    }
    //})
    //.directive('editorArrow', function () {
    //    return {
    //        link: function (scope, ele, attr) {
    //            var position = attr['editorArrow'],
    //                index;
    //            if (position === 'left') {
    //                index = 0;
    //            }
    //            scope.$watch('showPage', function (value) {
    //                position === 'right' && (index = scope.pages.length - 1);
    //                if (_.indexOf(scope.pages, value) === index) {
    //                    ele.hide();
    //                } else {
    //                    ele.show();
    //                }
    //            });
    //            ele.on('click', function () {
    //                var nowIndex = _.indexOf(scope.pages, scope.showPage),
    //                    newPage;
    //                if (position === 'left') {
    //                    nowIndex--;
    //                } else if (position === 'right') {
    //                    nowIndex++;
    //                }
    //                newPage = scope.pages[nowIndex];
    //                scope.$apply(function () {
    //                    scope.showPage = newPage;
    //                })
    //            })
    //        }
    //    }
    //})
    //.directive('editorAdd', function (InteractPage) {
    //    return {
    //        scope: {
    //            pages: '=',
    //            showPage: '='
    //        },
    //        link: function (scope, ele, attrs) {
    //            ele.on('click', function () {
    //                var newPage = new InteractPage(),
    //                    addIndex = attrs['addIndex'];
    //                scope.$apply(function () {
    //                    scope.pages.splice(addIndex, 0, newPage);
    //                    scope.showPage = newPage;
    //                });
    //            });
    //        }
    //    }
    //})
    //.directive('editorSave', function () {
    //    return {
    //        link: function (scope, ele) {
    //            ele.on('click', function () {
    //                alert('暂时没有这功能啦 ╮(╯▽╰)╭');
    //            })
    //        }
    //    }
    //})
    //.directive('editorRemove', function (InteractPage) {
    //    return {
    //        link: function (scope, ele, attrs) {
    //            ele.on('click', function () {
    //                    var index = attrs['removeIndex'] - 0,
    //                        showIndex = index - 1 < 0 ? 0 : index - 1;
    //
    //                    scope.$apply(function () {
    //                        scope.pages.splice(index, 1);
    //                        if (scope.pages.length === 0) {
    //                            scope.pages.push(new InteractPage);
    //                        }
    //                        scope.$parent.showPage = scope.pages[showIndex];
    //                    });
    //                }
    //            )
    //        }
    //    }
    //})
    //.directive('editorSwitch', function () {
    //    return {
    //        link: function (scope, ele, attrs) {
    //            ele.on('click', function () {
    //                scope.$apply(function () {
    //                    scope.$parent.showPage = scope.pages[attrs['index']];
    //                })
    //            })
    //        }
    //    }
    //})
    .factory('InteractPage', function () { // 页面
        return function () {
            this.type = undefined;
            this.html = undefined;
            this.images = []; // 页面元素下的图片
            this.texts = []; // 页面元素下的文字
            this.templateName = undefined;
            this.elements = []; // 表单页面中多少个元素
        };
    })
    .factory('InteractFormElement', function () { // 表单元素
        return function (templateName, type, index) {
            this.templateName = templateName;
            this.type = type;
            this.index = index;
            this.images = [];
            this.texts = [];
            this.options = [];
            this.html = undefined;
        }
    })
    .factory('InteractPageType', function () { // 模板类型
        var defaultSetting = {type: 'content', files: []};
        return function (obj) {
            var setting = {};
            $.extend(setting, defaultSetting, obj);
            this.name = setting.name;
            this.desc = setting.desc;
            this.files = setting.files;
            this.switchName = setting.switchName;
            this.type = setting.type;
        }
    })
//    .factory('initDefaultFeedback', function (InteractPage, InteractFormElement, interactTemplates, interactTemplatePath, $http) {
//        return function (pages) {
//            var defaultForm = new InteractPage();
//            var defaultElement = new InteractFormElement();
//            defaultForm.type = _.find(interactTemplates, function (t) {
//                return t.name === 'feedback'
//            });
//            defaultElement.templateName = 'defaultFeedback';
//            defaultElement.type = defaultForm.type;
//            defaultElement.index = 0;
//            $http.get(interactTemplatePath + defaultElement.templateName + '.html')
//                .success(function (data) {
//                    defaultElement.html = data;
//                    defaultForm.elements.push(defaultElement);
//                    pages.push(defaultForm);
//                })
//                .error(function (data) {
//                    alert(data);
//                })
//        }
//    })
//    .factory('initInteractPages', function ($http, interactTemplatePath, interactTemplates, InteractPage, getTemplateHTML) {
//        return function (pages) {
//            if (pages === undefined || pages === null || pages.length === 0) {
//                pages.push(new InteractPage());
////                initDefaultFeedback(pages);
//            } else {
//                angular.forEach(pages, function (page) {
//                    page.type = _.find(interactTemplates.get(), function (t) {
//                        return t.name === page.type
//                    });
//                    if (page.type) {
//                        var templateName = page.type['files'][0];
//                        getTemplateHTML(interactTemplatePath + "?id=" + templateName.id, 'interact')
//                            .then(function (data) {
//                                page.html = data;
//                                page.templateName = templateName.id;
//                            });
//                    }
//                });
//            }
//            return pages;
//        }
//    })
    .directive('rollStatic', function (){
        return {
            link: function (scope, ele){
                $(window).scroll(function (){
                    var $area = $('.editorCustom .editorForm .editorArea');
                    if($area.length){
                        var top = $area.offset().top;
                        if(this.document.body.scrollTop > top && !ele.is('.rollStatic')){
                            ele.addClass('rollStatic');
                        } else
                        if(this.document.body.scrollTop < top && ele.is('.rollStatic')) {
                            ele.removeClass('rollStatic');
                        }
                    } else {
                        $(window).off('scroll');
                    }
                })
            }
        }
    })
    .factory('formObj', function (InteractPage){
        return {
            apply: new InteractPage(),
            feedback: new InteractPage()
        }
    })
    .controller('InteractController', function ($scope, formObj, editType, getFormTemplates) {
        $scope.editType = $.trim(editType.get());
        $scope.formTemplates = getFormTemplates();
        $scope.applyForm = formObj.apply;
        $scope.feedbackForm = formObj.feedback;
    });
