angular.module('list', [])
    .controller('ListController', function ($scope, articleOperate) {
        articleOperate.find()
            .then(function (data) {
                $scope.articles = data;
            });
    });
