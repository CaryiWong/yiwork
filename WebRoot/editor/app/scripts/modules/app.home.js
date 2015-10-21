angular.module('app.home', [])
    .factory('activityInfo', function () {
        var datas = [];
        return {
            get: function () {
                return datas;
            },
            set: function (v) {
                datas = v;
            }
        }
    })
    .factory('courseInfo', function () {
        var datas = [];
        return {
            get: function () {
                return datas;
            },
            set: function (v) {
                datas = v;
            }
        }
    })
    .directive('urlBack', function ($location) {
        return {
            link: function (scope, ele) {
                ele.on('click', function () {
                    window.history.back();
                })
            }
        }
    })
    .directive('exportCreate', function ($rootScope, $location, sendBasic, sendHTML, articleInfo, interactInfo, pageName) {
        return {
            link: function (scope, ele, attrs) {
                function create(type, cb) {
                    var basic = articleInfo.getBasic();
                    async.series([
                        function (pcb) {
                            sendBasic(type, basic, pcb);
                        }
                    ], function (err, results) {
                        if (err) {
                            alert(err);
                        } else {
                            sendHTML('interact', cb);
                        }
                    });
                }

                var action = {
                    activity: function (cb) {
                        create('activity', cb);
                    }
                };
                ele.attr('disabled', false)
                    .on('click', function () {
                    ele.attr('disabled', true);
                    action['activity'](function () {
                        ele.attr('disabled', false);
                        window.location.href = '#success';
                    });
                })
            }
        }
    })
    .directive('exportConnect', function ($location, interactInfo) {
        return {
            scope: {
                basic: '='
            },
            link: function (scope, ele) {
                ele.on('click', function () {
                    scope.$apply(function () {
                        if (ele[0].checked) {
                            $location.path('/connect');
                        } else {
                            scope.basic.connect = undefined;
                        }
                    })
                })
            }
        }
    })
    .directive('exportImageCheckbox', function (exportImage) {
        return {
            link: function (scope, ele) {
                ele.on('click', function () {
                    if (ele[0].checked) {
                        alert('右键另存为图片下载吧');
                        exportImage();
                    }
                })
            }
        }
    })
    .controller('ExportController', function ($scope, editType, interactInfo, articleInfo) {

    })
    .directive('connectContent', function (interactInfo) {
        return {
            link: function (scope, ele, attrs) {
                ele.on('click', function () {
                    $('.connect-content').removeClass('active');
                    ele.addClass('active');
                    interactInfo.getBasic().connect = attrs['id'];
                })
            }
        }
    })
    .directive('chooseButton', function () {
        return {
            link: function (scope, ele, attr) {
                ele.on('click', function () {
                    $('.connect-choose button')
                        .toggleClass('custom-button-red')
                        .toggleClass('custom-button-transparent');
                    scope.$apply(function () {
                        scope.choose = attr['chooseButton'];
                    });
                })
            }
        }
    })
    .directive('connectCreate', function ($location, $rootScope, interactInfo, articleInfo, updateHTML, bindReview) {
        return {
            link: function (scope, ele, attrs) {
                var action = {
                    blog: function (cb) {
                        var basic = articleInfo.getBasic();
                        bindReview(basic.connect, scope.choose, function (data) {
                            cb && cb();
                        });
                    },
                    form: function (cb) {
                        var basic = interactInfo.getBasic();
                        updateHTML(basic.connect, scope.choose, 'interact', function (data) {
                            basic.h5url = data.data;
                            cb && cb();
                        });
                    }
                };
                ele.on('click', function () {
                    ele.attr('disabled', true);
                    scope.$apply(function () {
                        action[attrs['action']](function () {
                            ele.attr('disabled', false);
                            window.location.href = '#success';
                        });
                    });
                })
            }
        }
    })
    .controller('ConnectController', function ($scope, $routeParams, editType, getConnectResource, activityInfo, courseInfo) {
        $scope.editType = editType.get();
        getConnectResource('activity', $routeParams.type, function (data) {
            $scope.$apply(function () {
                $scope.activity = data;
                activityInfo.set(data);
            })
        });
        getConnectResource('course', ' ' || $routeParams.type, function (data) {
            $scope.$apply(function () {
                $scope.course = data;
                courseInfo.set(data);
            })
        });
    })
    .controller('SuccessController', function ($scope, $location, interactInfo, articleInfo, pageName, codeName) {
        var h5url = interactInfo.getBasic().h5url,
            imgtexturl = articleInfo.getBasic().imgtexturl,
            basic = interactInfo.getBasic(),
            share = '&type=share' + '&objtype=' + basic.objtype + '&objid=' + basic.id;
        $scope.isOnlyArticle = true;
        $scope.isOnlyInteract = true;
        $scope.isBoth = true;
        $scope.basic = basic;
        $scope.h5url = pageName + '/' + h5url + '?' + share;
        $scope.codeurl = codeName + encodeURIComponent(h5url + '?' + share);
        console.log($scope.imgtexturl);
        $scope.success = function () {
            $location.path('/');
            window.location.reload();
        }
    });
