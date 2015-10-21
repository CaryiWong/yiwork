(function () {
    var serverName = window.location.origin + '/v20',
        pageName = window.location.origin;
    //var serverName = 'http://test.yi-gather.com:1717' + '/v20',
    //    pageName = 'http://test.yi-gather.com:1717';
    var codeName = 'http://api2.yi-gather.com:8030/v20/download/qrcode?path=';
    var userId;
    angular.module('app', ['ngSanitize', 'ngAnimate', 'ngRoute', 'ngHtmlCompile',
        'app.preview', 'app.tool', 'app.form', 'app.start', 'app.static',
        'app.home', 'app.article', 'app.interact'])
        .config(function ($routeProvider) {
            // 路由定义
            $routeProvider
                .when('/', {
                    templateUrl: 'views/start.html',
                    controller: 'StartController'
                })
                .when('/article', {
                    templateUrl: 'views/articleEditor.html',
                    controller: 'ArticleController'
                })
                .when('/export', {
                    templateUrl: 'views/export.html',
                    controller: 'ExportController'
                })
                .when('/connect/:type', {
                    templateUrl: 'views/connect.html',
                    controller: 'ConnectController'
                })
                .when('/success', {
                    templateUrl: 'views/success.html',
                    controller: 'SuccessController'
                })
                .otherwise({redirectTo: '/'});
        })
        .value('serverName', serverName)
        .value('pageName', pageName)
        .value('codeName', codeName)
        .value('bindReviewPath', serverName + '/activity/bdhuiguurl')
        .value('labelsPath', serverName + '/labels/lablelist')
        .value('updatepagePath', serverName + '/editor/updatehtmlpage')
        .value('coursePath', serverName + '/activity/getcourselist')
        .value('activityPath', serverName + '/activity/getactivitylist')
        .value('uploadImgPath', serverName + '/upload/uploadimg')
        .value('saveOptionPath', serverName + '/editor/saveobjectoptions')
        .value('createActivityPath', serverName + '/activity/createactivity')
        .value('createCoursePath', serverName + '/activity/createcourse')
        .value('sendHTMLPath', serverName + '/editor/createhtmlpage')
        .value('saveAndBindPath', serverName + '/activity/saveandbdpage')
        .value('addressPath', serverName + '/workspace/getallworkspace')
        .run(function ($rootScope) {
            $rootScope.$on('$routeChangeSuccess', function (event, next, before) {
                $rootScope.showNav = /article|interact|export/.test(next.originalPath);
                switch (next.originalPath) {
                    case '/article':
                        $rootScope.navTitle = '编辑活动';
                        break;
                    case '/interact':
                        $rootScope.navTitle = '编辑HTML5';
                        break;
                    case '/export':
                        $rootScope.navTitle = '导出';
                        break;
                    default:
                        $rootScope.navTitle = '一起编辑器';
                        break;
                }
                if (before) {
                    if (/article/.test(before.loadedTemplateUrl)) {
                        $('.editorCopy').find('.editorArea-interact').remove().end()
                            .append($('.editorForm .editorArea-interact').clone());
                    }
                }
            })
        })
        .factory('initAllow', function () {
            var allow = true;
            return {
                get: function () {
                    return allow;
                },
                set: function (value) {
                    if (allow) {
                        allow = value;
                    }
                }
            }
        })
        .factory('userId', function ($routeParams) {
            userId = userId || getArgs().userid;
            return {
                get: function () {
                    return userId;
                }
            }
        })
        .factory('editType', function () {
            var t = 'activity';
            return {
                get: function () {
                    return t;
                },
                set: function (v) {
                    t = v;
                }
            }
        })
        .factory('dragObj', function () { // 拖曳传输对象
            var obj = undefined;
            return {
                set: function (v) {
                    obj = v;
                },
                get: function () {
                    return obj;
                }
            }
        })
        .factory('articleInfo', function (appBasic) {
            var pages = [],
                objectId,
                guests = [],
                basic = appBasic.get();
            return {
                getObjectId: function () {
                    return objectId;
                },
                setObjectId: function (v) {
                    if (objectId === undefined) {
                        objectId = v;
                    }
                },
                getPages: function () {
                    return pages;
                },
                setPages: function (value) {
                    pages = value;
                },
                getBasic: function () {
                    return basic;
                },
                getGuests: function () {
                    return guests;
                },
                setGuests: function (v) {
                    guests = v;
                }
            }
        })
        .factory('interactInfo', function (appBasic) {
            var pages = [],
                objectId,
                guests = [],
                basic = appBasic.get();
            return {
                getObjectId: function () {
                    return objectId;
                },
                setObjectId: function (v) {
                    if (objectId === undefined) {
                        objectId = v;
                    }
                },
                getPages: function () {
                    return pages;
                },
                setPages: function (value) {
                    pages = value;
                },
                getBasic: function () {
                    return basic;
                },
                getGuests: function () {
                    return guests;
                },
                setGuests: function (v) {
                    guests = v;
                }
            }
        })
        .factory('Basic', function () { // 活动/课程基本信息类（唯一属性）
            return function () {
                this.title = '双击编辑标题';
                this.startTime = undefined;
                this.endTime = undefined;
                this.domain = {value: '1417406722975'};
                this.people = undefined;
                this.phone = undefined;
                this.address = undefined;
                //this.activityType = undefined; // 活动类型 分常规和个性化
                //this.buttonName = undefined; // 个性化下可填写按钮名称
                //this.url = undefined; // 个性化下可填写跳转连接
                this.isVip = '0';
                this.pay = '1'; // 收费分免费和收费
                this.payType = '1'; // 单一收费还是多收费 肯定是多收费（会员、非会员）
                this.activityType = 'single'; // 单一收费还是分摊位收费
                this.moneyPair = [ // 非会员价100 和 会员价50
                    {
                        name: "非会员价",
                        value: 100
                    },
                    {
                        name: "会员价",
                        value: 50
                    }
                ];
                this.childActivity = [
                    {
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
                    }
                ];
                this.imgtexturl = '';
                this.h5url = '';
                this.huiguurl = '';
                //this.introduce = undefined;
                //this.connect = undefined; // 需要进行绑定的活动的opjID
            }
        })
        .factory('Guest', function () { // 嘉宾类
            return function (name, desc) {
                this.name = name || '双击编辑嘉宾名字';
                this.desc = desc || '双击编辑嘉宾简介';
                this.image = undefined;
            }
        })
        .factory('appBasic', function (Basic) {
            var basic = new Basic;
            return {
                get: function () {
                    return basic;
                },
                set: function (v) {
                    basic = v;
                }
            }
        })
        .factory('selectData', function (getAddress, getLabels) {
            var data = {};
            getAddress(function (d){
                data.address = d;
            });
            getLabels(function (d){
                data.domain = d;
            });
            return {
                get: function () {
                    return data;
                }
            }
        })
        .directive('preventLink', function () {
            return {
                link: function (scope, ele, attr) {
                    var $body = $('body');
                    $body.on('click', 'a', function (event) {
                        var href = this.getAttribute('href');
                        if (/tool/.test(href)) {
                            event.preventDefault();
                        }
                    })
                }

            }
        })
        .controller('AppController', function ($scope, $rootScope, articleInfo, serverName, selectData) {
            $rootScope.imgPath = function (path) {
                if (path) {
                    return serverName + '/download/img?path=' + path + '&type=web';
                } else {
                    return undefined;
                }
            };
        });
})();
