/**
 * Created by Caryi on 2015/12/14.
 */
$(function(){
    var Handlebars = require('handlebars/dist/handlebars.runtime.js');
    require('imports?Handlebars=handlebars/dist/handlebars.runtime.js!./Handlebars.templates.js');
    var lock = false;
    var localOriginal= require('localOriginal');
    var page = 0;
    var $window = $(window),$body = $(document.body);
    var winH = $window.height(); //页面可视区域高度
    var startY = '',endY = '',slideY = 0;
    var isMove = false;
    var goLoad = true;
    var $serverList = $('.serverList');
    document.addEventListener('touchstart',function(event){
        startY = event.touches[0].clientY;
        isMove = false;
    });
    document.addEventListener('touchmove',function(){
        isMove = true;
    });
    document.addEventListener('touchend',function(event){
        endY = event.changedTouches[0].clientY;
        slideY = endY - startY;
        var pageH = $body.height();
        var scrollT = $window.scrollTop(); //滚动条top
        var rate = (pageH - winH - scrollT) / winH;
        if (goLoad && rate < 0.01 && slideY < -100 ) {
            $('.info').remove();
            if(lock) return false;
            lock = true;
            page++;
            getData(page).always(function(){
                lock = false;
            });
        }
    });

    getData(0);
    function getData(page){
        return $.ajax( localOriginal + '/v20/yqservice/findallyqservice',{
            dataType: 'json',
            type: 'POST',
            data: {
                type: 'web',
                page: page,
                size: 10
            }
        }).success(function(data){
            if(data.cord === 0){
                var result = data.data;
                if(result && result.length != 0){
                    for(var i = 0; i < result.length; i++){
                        var stype = result[i].servicetype;
                        var content = Handlebars.Utils.escapeExpression(result[i].context.substring(0,70));
                            content = content.replace(/\n/g,'<br/>');
                            content = new Handlebars.SafeString(content);
                        var context = {
                            headPic: result[i].titleimg,
                            title: result[i].name,
                            user: result[i].servicesupplier,
                            context: content,
                            city: result[i].city,
                            contextUrl: result[i].contexturl + '?serviceid=' + result[i].id
                        };
                        if(stype ==='individual'){
                            context.individual = true;
                        }else if(stype ==='team'){
                            context.team = true;
                        }else{
                            context.company = true;
                        }
                        var html = Handlebars.templates.list(context);
                        $serverList.append(html);
                            $('.list-box').on('touchend',function(){
                                if(!isMove){
                                  location.href = $(this).attr('data-action');
                                }
                            })
                    }
                    $body.append("<div class='info'>上拉加载更多</div>");
                }else{
                    $('body').append("<div class='info'>已经到底了</div>");
                    goLoad = false;
                }
            }else{
                alert(data.msg)
            }
        }).fail(function(){});
    }
    });
