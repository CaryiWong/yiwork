angular.module('app.start', [])
    .directive('startItem', function (editType, $location) {
        return {
            link: function (scope, ele, attrs) {
                ele.on('click', function () {
                    scope.$apply(function () {
                        var to = attrs['startItem'];
                        editType.set(to);
                        $location.path('/article');
                    })
                })
            }
        }
    })
    .controller('StartController', function ($scope) {

    });
