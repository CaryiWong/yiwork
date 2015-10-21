        ;(function($)
        {			
           var minute = 1000 * 60;
           var hour = minute * 60;
           var day = hour * 24;
           var halfamonth = day * 15;
           var month = day * 30;

            $.extend({                
                PIF_dateDiffFormat:
                {
                    Info:{
                        Name: "格式化时间差",
                        Ver: "1.0.0.0",
                        Corp: "fly_binbin",
                        Author: "fly_binbin",
                        Date: "2011-12-17",
                        Email:"legend.binbin.fly@gmail.com",
                        Copyright: "Copyright @ 2000-2010 fly_binbin Technology Software All Rights Reserved",
                        License: "GPL"
                    },         
 
                    getDateDiff: function (varparam) {                        
                        var dateTimeStamp = varparam ? Date.parse(varparam.replace(/-/gi,"/")) : new Date().getTime();

                        var now = new Date().getTime();
                        var diffValue = now - dateTimeStamp;
 
                       if(diffValue < 0){
                         alert("结束日期不能小于开始日期！");
                       }
                       var monthC =diffValue/month;
                       var weekC =diffValue/(7*day);
                       var dayC =diffValue/day;
                       var hourC =diffValue/hour;
                       var minC =diffValue/minute;
                        if(monthC >=1){
						  return varparam;
                       }
                       else if(weekC>=1){
                         result="发表于" + parseInt(weekC) + "个星期前";
                      }
                      else if(dayC>=1){
                        result="发表于"+ parseInt(dayC) +"天前";
                      }
                      else if(hourC>=1){
                         result="发表于"+ parseInt(hourC) +"个小时前";
                      }
                      else if(minC>=1){
                        result="发表于"+ parseInt(minC) +"分钟前";
                     }else
                     result="刚刚发表";

                     return result;
                    }
                }
            });
        })(jQuery);
