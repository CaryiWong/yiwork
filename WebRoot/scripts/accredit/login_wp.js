/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	/**
	 * Created by Caryi on 2015/12/31.
	 */
	__webpack_require__(1);

/***/ },
/* 1 */
/***/ function(module, exports) {

	var ua = window.navigator.userAgent.toLowerCase(),
	    device = {
	        android: false,
	        ios: false,
	        weixin: false,
	        yiqi: false,
	    };
	if (ua.search(/android/) >= 0) {
	    device.android = true;
	    device.name = 'android';
	    device.version = parseFloat((ua.match('android ([0-9.]+)') || [])[1]);
	    document.documentElement.classList.add('android-body');
	}
	if (ua.search(/iphone|ipod|ipad/) >= 0) {
	    device.ios = true;
	    device.name = 'ios';
	    device.version = parseFloat(ua.match(/os (\d)/));
	    document.documentElement.classList.add('ios-body');
	}
	if (ua.search(/micromessenger/) >= 0) {
	    device.weixin = true;
	    document.documentElement.classList.add('weixin-body');
	}
	if (ua.search(/yiqi/) >= 0) {
	    device.yiqi = true;
	    document.documentElement.classList.add('yiqiApp-body');
	}
	if (device.android && device.version <= 6) {
	    device.androidVunit = true;
	    document.documentElement.classList.add('android-vunit');
	}
	if (device.android && device.version < 4.4) {
	    device.androidFlex = true;
	    document.documentElement.classList.add('android-flex');
	}

	function handleFunctionParams(params) {
	    if (params instanceof Object) {
	        if (params instanceof Array) {
	            params = params.join(',');
	        } else {
	            params = JSON.stringify(params);
	        }
	    }
	    return params;
	}

	device.iosCall = function (method, params) {
	    if (device.weixin || !device.yiqi || !device.ios) {
	        return false;
	    }
	    window.iosWebParams = function () {
	        params = handleFunctionParams(params);
	        return params;
	    }
	    window.location.href = method;
	}

	device.androidCall = function (method, params) {
	    if (device.weixin || !device.yiqi || !device.android) {
	        return false;
	    }
	    if (window.yiqi && window.yiqi[method]) {
	        if (params === undefined) {
	            yiqi[method]();
	        } else {
	            params = handleFunctionParams(params);
	            yiqi[method](params);
	        }
	    }
	}

	device.call = function (method, params) {
	    device.iosCall(method, params);
	    device.androidCall(method, params);
	}

	device.params = function (method, params) {
	    if (device.weixin || !device.yiqi) {
	        return false;
	    }
	    var action = {
	        ios: function () {
	            method = method.replace(/\w/, method[0].toUpperCase());
	            window['ios' + method] = function () {
	                params = handleFunctionParams(params);
	                return params;
	            }
	        },
	        android: function () {
	            if (window.yiqi) {
	                window.yiqi[method] = function () {
	                    params = handleFunctionParams(params);
	                    return params;
	                }
	            }
	        }
	    };
	    action[device.name]();
	}

	device.title = function (title) {
	    document.title = title;
	    if (device.ios) {
	        var iframe = document.createElement('iframe');
	        iframe.classList.add('normal-iframe');
	        iframe.src = '/images/public/logo.png';
	        iframe.style.opacity = 0;
	        iframe.onload = function () {
	            setTimeout(function () {
	                iframe.onload = undefined;
	                document.body.removeChild(iframe);
	            }, 0);
	        };
	        document.body.appendChild(iframe);
	    }
	}

	device.appShare = function (obj) {
	    obj.img = obj.img || window.location.origin + '/images/public/logo_icon_share.png';
	    obj.link = obj.link || window.location.href;
	    device.params('getShare', obj);
	}

	device.back = function (obj) {
	    if (device.weixin || !device.yiqi) {
	        if (obj && obj.web) {
	            obj.web();
	        } else {
	            window.history.go(-1);
	        }
	    } else {
	        device.call('back');
	    }
	}


	module.exports = device;


/***/ }
/******/ ]);