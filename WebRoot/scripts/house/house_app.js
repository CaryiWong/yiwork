require(['jquery', 'Path', 'user', 'detail', 'order'], function ($, Path, user, detail, order) {
    var $loading = $('.house-loading');
    var $houseRoutes = $('.house-route');
    var userInfo;
    user.sign()
        .done(function (info) {
            userInfo = info;
            Path.map('#/detail').to(function () {
                $loading.fadeIn();
                $houseRoutes.hide();
                // $loading.fadeOut(); //todo remove it
                // $houseRoutes.filter('.house-detail').show(); // todo same up
                detail.loadHouse(userInfo)
                    .done(function () {
                        $houseRoutes.filter('.house-detail').show();
                        $loading.fadeOut();
                    })
            });
            Path.map('#/order').to(function () {
                $loading.fadeIn();
                $houseRoutes.hide();
                //$houseRoutes.filter('.house-order').show();
                //$loading.fadeOut();
                order.loadUserOrderList(userInfo)
                    .done(function () {
                        $houseRoutes.filter('.house-order').show();
                        $loading.fadeOut();
                    });
            });
            Path.map('#/pay/:orderid').to(function () {
                $loading.fadeIn();
                $houseRoutes.hide();
                $houseRoutes.filter('.house-pay').show();
                $loading.fadeOut();
                order.setPayOrder(this.params['orderid']);
            });
            Path.map('#/book/:orderid').to(function () {
                $loading.fadeIn();
                $houseRoutes.hide();
                order.bookSuccess(this.params['orderid'])
                    .done(function () {
                        $houseRoutes.filter('.house-book').show();
                        $loading.fadeOut();
                    })
            })
            Path.root('#/detail');
            Path.listen();
        });
});
