angular.module('app', ['ngRoute', 'summernote', 'list', 'edit', 'article'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/static/list.html',
                controller: 'ListController'
            })
            .when('/edit', {
                redirectTo: '/edit/new'
            })
            .when('/edit/:id', {
                templateUrl: 'views/static/edit.html',
                controller: 'EditController'
            })
            .otherwise({
                redirectTo: '/'
            });
    })
    .factory('userid', function (){
        var args = (function getArgs() {
            var args = {},
                query = location.search.substring(1),
                pairs = query.split("&");
            for (var i = 0; i < pairs.length; i++) {
                var pos = pairs[i].indexOf('=');
                if (pos == -1) continue;
                var argname = pairs[i].substring(0, pos),
                    value = pairs[i].substring(pos + 1);
                value = decodeURIComponent(value);
                args[argname] = value;
            }
            return args;
        })();
        var id = args.uid;
        return function (){
            return id;
        }
    })
    .controller('AppController', function ($scope) {

    });
