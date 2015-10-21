angular.module('app.article', ['app.drag', 'app.server'])
    .value('articleTemplatePath', '/article_template/')
    .factory('ArticlePage', function () {
        return function (templateName, type, index) {
            this.templateName = templateName;
            this.type = type;
            this.index = index;
            this.images = [];
            this.texts = [];
            this.html = undefined;
        }
    })
    .factory('ArticlePageType', function () {
        return function (obj) {
            this.name = obj.name;
            this.desc = obj.desc;
            this.files = obj.files;
            this.switchName = obj.switchName; // 对应另一种模板的类型名字
        }
    })
    .factory('articleTemplates', function () {
        var data = [];
        return {
            get: function () {
                return data;
            },
            set: function (v) {
                data = v;
            }
        }
    })
    .factory('setArticleTemplates', function (ArticlePageType, getAllTemplates, articleTemplates) {
        return function (ts, cb) {
            getAllTemplates('article')
                .then(function (data) {
                    angular.forEach(data, function (obj) {
                        ts.push(new ArticlePageType(obj));
                    });
                    articleTemplates.set(ts);
                    cb && cb();
                });
        };
    })
    .factory('initArticlePages', function (getTemplateHTML, articleTemplatePath, articleTemplates) {
        return function (pages) {
            angular.forEach(pages, function (page) {
                page.type = _.find(articleTemplates.get(), function (t) {
                    return t.name === page.type
                });
                if (page.type) {
                    var templateName = page.type['files'][0];
                    getTemplateHTML(articleTemplatePath + "?id=" + templateName.id, 'article')
                        .then(function (data) {
                            page.html = data;
                            page.templateName = templateName.id;
                        });
                }
            });
            return pages;
        }
    })

    .controller('ArticleController', function ($scope, $rootScope, articleInfo, articleTemplates, editType, selectData, userId) {
        $rootScope.label = 'article';
        $scope.editType = $.trim(editType.get());
        $scope.basic = articleInfo.getBasic();
        $scope.selectData = selectData.get();
    });
