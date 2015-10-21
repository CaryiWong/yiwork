require.config({
    baseUrl: '/scripts',
    paths: {
        jquery: 'components/jquery',
        weixin: 'components/weixin',
        wx: 'house/wx_house',
        picker: 'components/picker',
        bootstrap: 'components/bootstrap',
        carousel: 'components/carousel',
        pickerdate: 'components/picker.date',
        Path: 'components/path',
        cookie: 'components/jquery.cookie',
        detail: 'house/detail',
        server: 'house/server',
        order: 'house/order',
        bind: 'house/bind',
        user: 'house/user',
        house_app: 'house/house_app',
        column: 'house/columns_extend'
    },
    shim: {
        Path: {
            exports: 'Path'
        },
        picker: {
            deps: ['jquery']
        },
        pickerdate: {
            deps: ['jquery']
        },
        cookie: {
            deps: ['jquery']
        },
        bootstrap: {
            deps: ['jquery']
        },
        carousel: {
            deps: ['jquery']
        },
        column: {
            deps: ['jquery']
        }
    }
})
