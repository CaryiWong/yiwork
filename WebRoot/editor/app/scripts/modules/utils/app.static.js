angular.module('app.static', [])
    .directive('staticText', function () {
        return {
            templateUrl: 'data/text.html',
            replace: true
        }
    })
    .directive('staticLabel', function () {
        return {
            templateUrl: 'data/label.html',
            replace: true
        }
    })
    .directive('staticCheckbox', function () {
        return {
            templateUrl: 'data/checkbox.html',
            replace: true
        }
    })
    .directive('staticRadio', function () {
        return {
            templateUrl: 'data/radio.html',
            replace: true
        }
    })
    .directive('staticName', function () {
        return {
            templateUrl: 'data/name.html',
            replace: true
        }
    })
    .directive('staticPhone', function () {
        return {
            templateUrl: 'data/phone.html',
            replace: true
        }
    })
    .directive('staticVip', function () {
        return {
            templateUrl: 'data/vip.html',
            replace: true
        }
    })
    .directive('staticEmail', function () {
        return {
            templateUrl: 'data/email.html',
            replace: true
        }
    })
    .directive('staticButton', function () {
        return {
            templateUrl: 'data/button.html',
            replace: true
        }
    });
