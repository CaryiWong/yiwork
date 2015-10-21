angular.module('app.drag', [])
    .factory('sortPages', function () {
        return function (pages) {
            angular.forEach(pages, function (page, index) {
                page.index = index;
            })
        }
    })
    .factory('dragAction', function ($http, ArticlePage, InteractFormElement, sortPages, articleTemplates) {
        return {
            createArticleDetail: function (scope, ele, obj) {
                var action = this;
                dialog.show(document.querySelector('.detailForm'), function () {
                    $('.detailForm .custom-button-red').one('click', function (event) {
                        event.preventDefault();
                        action.createArticle(scope, ele, obj);
                    })
                });
            },
            createArticle: function (scope, ele, obj) {
                var index = scope.index;
                getTemplateHTML(obj.url, 'article')
                    .then(function (data) {
                        var tType = _.find(articleTemplates.get(), function (t) {
                                return t.name === obj.templateType
                            }),
                            page = new ArticlePage(obj.templateName, tType, scope.index);
                        page.html = data;
                        scope.pages.splice(index, 0, page);
                        sortPages(scope.pages);
                    });
            },
            createFormElement: function (scope, ele, obj) {
                var index = scope.index;
                var page = new InteractFormElement(obj.templateName, undefined, scope.index);
                page.html = '<div static-'+ obj.templateName +'></div>';
                scope.$apply(function (){
                    scope.pages.splice(index, 0, page);
                    sortPages(scope.pages);
                });

                //getTemplateHTML(obj.url, 'interact')
                //    .then(function (data) {
                //        var tType = _.find(interactTemplates.get(), function (t) {
                //                return t.name === obj.templateType
                //            }),
                //            page = new InteractFormElement(obj.templateName, tType, scope.index);
                //        page.html = data;
                //        scope.pages.splice(index, 0, page);
                //        sortPages(scope.pages);
                //    });

                //$http.get(obj.url)
                //    .success(function (data) {
                //        var tType = _.find(interactTemplates, function (t) {
                //                return t.name === obj.templateType
                //            }),
                //            page = new InteractFormElement(obj.templateName, tType, scope.index);
                //        page.html = data;
                //        scope.pages.splice(index, 0, page);
                //        sortPages(scope.pages);
                //    })
                //    .error(function (data) {
                //        alert(data)
                //    });
            },
            changeArticle: function (scope, ele, obj) {
                var newIndex = scope.index,
                    oldIndex = obj.page.index;
                if (newIndex === oldIndex) return;
                scope.$apply(function () {
                    scope.pages.splice(oldIndex, 1);
                    scope.pages.splice(newIndex, 0, obj.page);
                    sortPages(scope.pages);
                });
            }
        }
    })
    .directive('dragTemplate', function (dragObj) {
        return {
            scope: {
                url: '@templateUrl',
                type: '@templateType',
                name: '@templateName',
                dragType: '@dragAction'
            },
            link: function (scope, ele, attr) {
                ele.attr('draggable', true)
                    .on('dragstart', function (event) {
                        dragObj.set({
                            type: scope.dragType,
                            templateName: scope.name,
                            templateType: scope.type,
                            url: scope.url
                        });
                    })
            }
        }
    })
    .directive('dragPage', function (sortPages) {
        return {
            scope: {
                page: '=dragPage',
                pages: '=pages'
            },
            link: function (scope, ele) {
                ele.on('mouseenter', function () {
                    ele.find('.dragPage-buttons').show().end()
                        .find('.dragSwitch').addClass('active');
                }).on('mouseleave', function () {
                    ele.find('.dragPage-buttons').hide().end()
                        .find('.dragSwitch').removeClass('active');
                })
                    .find('.dragPage-remove').on('click', function () {
                        var index = scope.page.index;
                        scope.$apply(function () {
                            scope.pages.splice(index, 1);
                            sortPages(scope.pages);
                        })
                    })
            }
        }
    })
    .directive('dragArea', function ($http, dragObj, $compile, dragAction, sortPages) {
        return {
            scope: {
                index: '@',
                pages: '='
            },
            link: function (scope, ele, attr) {
                ele.on('dragover', function (event) {
                    event.preventDefault();
                })
                    .on('dragenter', function () {
                        ele.addClass('enter');
                    })
                    .on('dragleave', function () {
                        ele.removeClass('enter');
                    })
                    .on('drop', function (event) {
                        var dragVar = dragObj.get();
                        ele.removeClass('enter');
                        event.preventDefault();
                        dragAction[dragVar.type](scope, ele, dragVar);
                    })
            }
        }
    })
    .directive('dragSwitch', function (dragObj) {
        return {
            scope: {
                page: '=dragSwitch'
            },
            link: function (scope, ele, attrs) {
                ele.on('dragstart', function (event) {
                    dragObj.set({
                        type: attrs['dragAction'],
                        page: scope.page
                    });
                })
            }
        }
    });
