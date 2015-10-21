angular.module('edit', [])
    .directive('saveButton', function (articleOperate) {
        return {
            scope: {
                article: '='
            },
            link: function (scope, ele) {
                var article = scope.article;
                ele.on('click', function () {
                    articleOperate.create(article.title, article.htmlcode, article.id)
                        .then(function (data) {
                            article.url = data.url;
                            alert('创建/修改成功');
                        });
                })
            }
        }
    })
    .factory('upload', function (imageUpLoad) {
        return function (article, files) {
            imageUpLoad(files)
                .then(function (data) {
                    var $editor = article.editor;
                    $editor.summernote('insertImage', data.url);
                });
        }
    })
    .factory('getArticle', function ($routeParams, $q, articleOperate) {
        return function () {
            var defer = $q.defer();
            var obj = {};
            if ($routeParams.id && $routeParams.id !== 'new') {
                var id = $routeParams.id;
                return articleOperate.findOne(id)
            } else {
                defer.resolve(obj);
                return defer.promise;
            }
        }
    })
    .controller('EditController', function ($scope, upload, getArticle) {
        $scope.article = {};
        getArticle().then(function (obj) {
            _.extend($scope.article, obj);
        })
        $scope.imageUpLoad = upload.bind(this, $scope.article);
    });
