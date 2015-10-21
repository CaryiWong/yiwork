(function(win, doc, $){
    if( !window.util ){

        window.util = {
            //判断是否为空对象
            isEmptyObject : function(obj){
                if( typeof obj == 'object'){
                    for(var p in obj){
                        return false;
                    }
                    return true;
                }
                return false;
            },


            //获取当前时间，精确到秒
            getTime : function(dec, differ, intime){
                differ = differ || 0;

                var date = intime || new Date();
                date.setDate(date.getDate()+differ);

                var year = date.getFullYear(),
                    month = date.getMonth() + 1,
                    day = date.getDate(),
                    hours = date.getHours(),
                    minutes = date.getMinutes(),
                    seconds = date.getSeconds();

                dec = dec ? dec : "-";
                month = month < 10 ? "0" + month : month;
                day = day < 10 ? "0" + day : day;
                hours = hours < 10 ? "0" + hours : hours;
                minutes = minutes < 10 ? "0" + minutes : minutes;
                seconds = seconds < 10 ? "0" + seconds : seconds;

                return year + dec + month + dec + day + ' ' + hours + ':' + minutes + ':' + seconds;
            },


            //打印log
            log : function(msg){
                if( window.console ) console.log(msg);
            },


            //格式化字符串(除a,img,br标签外，所有的标签将被转义)，防止js攻击。
            dataFormat : function(data){
                var r = /<((?!\/?(a|img|br))[^>]*)>/img,
                    r_on = /on\w+=.+\s/img,
                    r_js = /javascript:/;

                var formater = function(str){
                    return str.replace(r, '&lt;$1&gt;').replace(r_on, '').replace(r_js, '');
                };

                if( data && typeof data == 'object' && win.JSON ){
                    data = JSON.stringify(data);
                    return formater(data);
                }

                if( data && typeof data == 'string' ){
                    return formater(data);
                }
            },


            //合并两个数组，同时去除重复的元素
            merge : function(ary1, ary2){
                var map = {};
                ary1 = exports.unique(ary1);
                ary1.forEach(function(value, index){
                    if( !map[value] ) map[value] = true;
                });
                ary2.forEach(function(value, index){
                    if( !map[value] && value != 'undefined' ) ary1.push(value);
                });
                return ary1;
            },


            //去除字符串两端的空格
            trim : function(str){
                if( !str ) return str;
                return str.replace(/(^\s*)|(\s*$)/g, '');
            },


            //返回在第一个数组的基础上剔除二个数组差集后的结果
            defference : function(ary1, ary2){
                var inter = this.intersection(ary1, ary2),
                    mapper = {},
                    newAry = [];

                inter.forEach(function(num, index){
                    mapper[num] = true;
                });

                ary1.forEach(function(num, index){
                    if( !mapper[num] ) newAry.push(num);
                });

                return newAry;
            },


            //返回两数组的交集
            intersection : function(ary1, ary2){
                var mapper = {},
                    newAry = [];

                ary1.forEach(function(num, index){
                    mapper[ num ] = true;
                });

                ary2.forEach(function(num, index){
                    if( mapper[num] ) newAry.push(num);
                });

                return newAry;
            },


            //返回地址栏参数对象化后的参数对象
            query : function(){
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
            }

        };


        /* dialog ------------------------------------------------------- */
        var Dialog = function(option){
            this.ops = option;
        };
        Dialog.prototype.FrameWidth = function (frame) {
            return frame.contentWindow.document.documentElement.scrollWidth;
        };
        Dialog.prototype.FrameHeight = function (frame) {
            return frame.contentWindow.document.documentElement.scrollHeight;
        };
        Dialog.prototype.FrameResize = function ($frame) {
            window.onresize = function () {
                $frame.css({
                    left: (  document.body.scrollWidth - $frame.width() ) / 2,
                    top: (  document.documentElement.clientHeight - $frame.height() ) / 2   });
            };
        };
        Dialog.prototype.SetMask = function () {
            var $mask = $("<div class='mask'></div>").css({
                position: "absolute",
                top: 0,
                left: 0,
                backgroundColor: "#333",
                opacity: 0.7,
                height: document.body.scrollHeight,
                width: "100%"
            }).appendTo("body");
        };
        Dialog.prototype.SetStyle = function ($frame, obj) {
            var position = browserVersion != 6 ? "fixed" : "absolute";
            var top = obj.top ? obj.top : ( document.documentElement.clientHeight - $frame.height() ) / 2;
            top = browserVersion != 6 ? top : (document.documentElement.scrollTop + top);
            $frame.css({
                width: obj.width || $frame.width(),
                height: obj.height || $frame.height(),
                left: obj.left,
                border: obj.border || $frame.css("border"),
                position: position,
                top: top,
                zIndex: 999
            }).addClass("dialog");
        };
        Dialog.prototype.ShowIframe = function (src) {
            var self = this;
            var $frame = $("<iframe></iframe>").attr("src", src).appendTo("body").on("load", function () {
                var parameters = {
                    width: self.FrameWidth(this),
                    height: self.FrameHeight(this),
                    left: (  document.body.scrollWidth - self.FrameWidth(this) ) / 2,
                    border: "none"
                }
                self.SetMask();
                self.SetStyle($frame, parameters);
                self.FrameResize($frame);
            });
        };
        Dialog.prototype.ShowHTML = function (selector, callBack) {
            var $dialog = $(selector).appendTo("body").show(0);
            var parameters = {  left: ( document.body.scrollWidth - $dialog.width() ) / 2 }
            this.SetMask();
            this.SetStyle($dialog, parameters);
            this.FrameResize($dialog);
            callBack($dialog);
        };
        Dialog.Close = function () {
            $(".dialog").hide(0);
            $(".mask").remove();
        };

    }
})(window, document, jQuery);


// 窗口切换
function WindowSwitch($hide, $show) {
    $hide.hide(0);
    $show.show(0);
}

//去除不必要空格
function TextLength(text) {
    return $.trim(text).length;
}

//敏感字符过滤
function StringEscape(str) {
    str = str.replace(/</, '&lt;');
    return str.replace(/>/, '&gt;');
}

//$Ajax封装
function AjaxParameter(url, data) {
    this.url = url;
    this.data = data;
}

function GetData(obj, callBack) {
    $.ajax({
        url: obj.url,
        data: obj.data,
        success: function (data) {
            callBack(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(XMLHttpRequest.status + " " + XMLHttpRequest.readyState + " " + textStatus);
        }
    })
}

//自定义select
function CustomSelect($div) {
    var self = this;
    this.$appearance = $div;
    this.$select = $div.find(".control");
    this.$value = $div.find(".customSelect-value");
    this.Update = function () {
        this.$value.html(this.$select.find(":selected").text());
    }
    //绑定select的change事件到value
    this.$select.on("change", function () {
        self.Update();
    })
}

//初始化每一个自定义select
(function InitCustomSelect() {
    $(".customSelect").each(function () {
        (new CustomSelect($(this))).Update();
    })
})();

//定时轮换

    //轮换对象
    function Slide($slide, slideItemSelector, time, animateTime, $buttons, index, expand) {
        this.$slide = $slide.wrap("<div style='width:9999px; overflow:hidden'></div>");
        this.$slideItems = $slide.find(slideItemSelector);
        this.$buttons = $buttons;
        this.length = this.$slideItems.length;
        this.distance = this.$slideItems.outerWidth();
        this.index = index || 0;
        this.time = time || 2000;
        this.auto = null;
        this.OrderSwitch = function (level) {
            if (this.index >= this.length) {
                this.index = 0;
                this.$slide.stop(true, true).animate({ left: 0 }, animateTime);
            } else {
                this.$slide.stop(true, true).animate({ left: "-=" + this.distance * level }, animateTime);
            }
        };
        this.CyclicSwitch = function (level, oIndex) {
            if (this.index >= this.length) {
                this.index = 0;
            }
            if( this.index == oIndex ){
                console.log("fuck");
                var fixIndex = this.$buttons.index( this.$buttons.filter(".active") );
                this.index = fixIndex;
                return;
            }
            var $nextItem = this.$slideItems.eq(this.index),
                $nowItem = this.$slideItems.eq(oIndex),
                self = this;
            $nowItem.after($nextItem);
            this.$slide.stop(true, true).animate({ left: "-=" + this.distance }, animateTime , function(){
                self.$slide.append( $nowItem );
                self.$slide.css({ left: 0 });
            });

        };

        this.Switch = function (showIndex) {
            var oIndex = this.index,
                level;
            this.index = showIndex >= 0 ? showIndex : ++this.index;
            level = this.index - oIndex;
            this[expand.SwitchType](level, oIndex);
            if (this.$buttons) {
                this.$buttons.eq(this.index).addClass("active");
                this.$buttons.eq(oIndex).removeClass("active");
            }
            return this;
        };
        this.AutoSwitch = function () {
            var self = this;
            this.auto = setInterval(function () {
                self.Switch();
            }, this.time);
            return this;
        };
        this.StopAutoSwitch = function () {
            clearInterval(this.auto);
            return this;
        }

        //在轮换区域停止自动切换
        this.StopAutoAtSlide = function () {
            this.$slide.mouseenter({ self: this },function (event) {
                event.data["self"].StopAutoSwitch();
            }).mouseleave({ self: this }, function (event) {
                    event.data["self"].AutoSwitch();
                })
        };

        //按钮触发切换事件
        this.ButtonSwitch = function () {
            this.$buttons.mouseenter({self: this},function (event) {
                var self = event.data["self"],
                    index = self.$buttons.index(this);
                self.StopAutoSwitch().Switch(index);
            }).mouseleave({ self: this }, function (event) {
                    event.data["self"].AutoSwitch();
                });
        };

        //构造函数
        this.Init = function () {
            if (index != null) {
                this.$buttons.eq(index).addClass("active");
            }
            this.StopAutoAtSlide();
            this.ButtonSwitch();
        };

        this.Init();
    }


//弹出窗口对象
var $body = $("body");
var browserVersion = parseInt($.browser.version);
function Dialog() {

    this.FrameWidth = function (frame) {
        return frame.contentWindow.document.documentElement.scrollWidth;
    };

    this.FrameHeight = function (frame) {
        return frame.contentWindow.document.documentElement.scrollHeight;
    };

    this.FrameResize = function ($frame) {
        window.onresize = function () {
            $frame.css({
                left: (  document.body.scrollWidth - $frame.width() ) / 2,
                top: (  document.documentElement.clientHeight - $frame.height() ) / 2   });
        };
    };

    this.SetMask = function () {
        var $mask = $("<div class='mask'></div>").css({
            position: "absolute",
            top: 0,
            left: 0,
            backgroundColor: "#333",
            opacity: 0.7,
            height: document.body.scrollHeight,
            width: "100%"
        }).appendTo("body");
    };

    this.SetStyle = function ($frame, obj) {
        var position = browserVersion != 6 ? "fixed" : "absolute";
        var top = obj.top ? obj.top : ( document.documentElement.clientHeight - $frame.height() ) / 2;
        top = browserVersion != 6 ? top : (document.documentElement.scrollTop + top);
        $frame.css({
            width: obj.width || $frame.width(),
            height: obj.height || $frame.height(),
            left: obj.left,
            border: obj.border || $frame.css("border"),
            position: position,
            top: top,
            zIndex: 999
        }).addClass("dialog");
    };

    this.ShowIframe = function (src) {
        var self = this;
        var $frame = $("<iframe></iframe>").attr("src", src).appendTo("body").on("load", function () {
            var parameters = {
                width: self.FrameWidth(this),
                height: self.FrameHeight(this),
                left: (  document.body.scrollWidth - self.FrameWidth(this) ) / 2,
                border: "none"
            }
            self.SetMask();
            self.SetStyle($frame, parameters);
            self.FrameResize($frame);
        });
    };

    this.ShowHTML = function (selector, callBack) {
        var $dialog = $(selector).appendTo("body").show(0);
        var parameters = {  left: ( document.body.scrollWidth - $dialog.width() ) / 2 }
        this.SetMask();
        this.SetStyle($dialog, parameters);
        this.FrameResize($dialog);
        callBack($dialog);
    };

    this.Close = function () {
        $(".dialog").hide(0);
        $(".mask").remove();
    };
}

// divEditor 内容去除样式
var $divEditor = $("div[contenteditable]").blur(function () {
        var $this = $(this),
            content = $this.html();
        content = content.replace(/<([a-z]+)\ [^>]*>/ig, "<$1>").replace(/<b>/, "<span>").replace(/<i>/, "<span>");
        $this.html(content);
    })


// divEditor textholder
// IE8及以下会引出一个错误，需要添加新的功能请在这段代码之前
var Holder = function (input, holder, color) {
    this.$input = $(input);
    this.holder = holder;
    this.color = color;
    this.holderColor = "#999";
    this.Content = function () {
        if (this.$input[0].nodeName == "DIV") {
            return $.trim(this.$input.text());
        } else {
            return $.trim(this.$input.val());
        }
    };
    this.Use = function () {
        if (!TextLength(this.Content()))
            this.$input.html(holder).css("color", this.holderColor);
    };
    this.Cancel = function () {
        if (this.Content() == holder)
            this.$input.html("").css("color", this.color);
    };
}
var holderSelecter = "div[placeholder]";
if ($.browser.msie) {
    holderSelecter += " , input[placeholder] , textarea[placeholder]";
}
$(holderSelecter).each(function () {
    var $this = $(this);
    var holder = new Holder(this, $this.attr("placeholder"), $this.css("color"));
    holder.Use();
    $this.click(function () {
        holder.Cancel();
    }).blur(function () {
            holder.Use();
        })
})
