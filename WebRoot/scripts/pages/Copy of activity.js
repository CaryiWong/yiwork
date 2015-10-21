var commentId=""; //评论Id
var successNum=0;

$(function () {
	// 首页slide hover
	$('.slide').hover(function (){
		$(this).find('.slide-item-info').fadeIn(200);
	}, function (){
		$(this).find('.slide-item-info').fadeOut(200);
	}); 
	
    //活动创建
    $('.create-form').submit(function (event) {
        event.preventDefault();
        $(this).valid(function (pass) {
            if (!pass) {
                event.preventDefault();
            }else
            {7
            	saveEvent();	
            }
        })
    });

    // 活动报名二次确认之前
    var $applyDialog = $('.apply-dialog');
    var $applyButton = $('.info-apply').click(function (){
    	if(session_loginId!="")
    	{
    		util.dialog.show($applyDialog); //edie by kcm @0414	
    	}else
    	{
    		//没登录就弹出登录框
    		$('.header-status-link.status-link-sign').click();
    	}
    });

    $applyDialog.find('.apply-dialog-cancel').click(function (){
        util.dialog.close();
    });
    
    // 活动详情
    // 二级评论
    var activityCommentReply = {
        $doms: {
            $replyButton: $('.comment-reply'),
            $commentButton: $('.comment-reply-button'),
            listTemplate: '<ul class="comment-reply-list">'
        },
        param: {
            replyInfo: {
                name: session_nickName
            },
            url: root+'comment/replycomment',
            config: {
            	method:'post',
                type: 'json',
                data: {
                    type: 1
                }
            }
        },
        action: {
            findComment: function ($dom) {
                return $dom.parents('.comment-content');
            },
            getReplyItem: function (obj) {
                return $('<li class="reply-item">' +
                    '<a class="reply-name">' + obj.name + '</a>' +
                    '<span class="reply-text">：' + obj.text + '</span>' +
                    '</li>');
            },
            addReplyList: function ($comment) {
                var $list = $(activityCommentReply.$doms.listTemplate);
                $comment.append($list);
                return $list;
            },
            addReplyListItem: function (obj, $comment) {
                var $list = $comment.find('.comment-reply-list') ,
                    $item = this.getReplyItem(obj);
                if ($list.length === 0) {
                    $list = this.addReplyList($comment);
                }
                $list.prepend($item);
            },
            failSend: function (data) {
                alert(data || '回复失败，请稍后重试');
            },
            sendReply: function (text, $text) {
                var self = this,                    
                    $comment = this.findComment($text);
                activityCommentReply.param.config.data.comment = $comment.attr('id');
                activityCommentReply.param.config.data.user = session_loginId;
                activityCommentReply.param.config.data.retext = text;
	                $.ajax(activityCommentReply.param.url, activityCommentReply.param.config)
                    .done(function (data) {
                    	data = JSON.parse(data);
                        if (data.cord === 0) {
                            var info = activityCommentReply.param.replyInfo;
                            info.text = text;
                            $text.val('');
                            self.addReplyListItem(info, $comment);
                        } else {
                            self.failSend();
                        }
                    }).fail(function () {
                        self.failSend();
                    })
            }
        },
        event: {
            replyClick: function () {
                var $this = $(this).toggleClass('active');
                $this.parents('.comment').find('.comment-control').slideToggle(200);
            },
            commentClick: function () {
                var $text = $(this).parents('.comment-control').find('.comment-reply-text'),
                    text = $.trim($text.val());
                if (text.length) {
                    activityCommentReply.action.sendReply(text, $text);
                }
            }
        },
        init: function () {
            var self = this;

            // 事件绑定
            this.$doms.$replyButton.on('click', self.event.replyClick);
            this.$doms.$commentButton.on('click', self.event.commentClick);
        }
    };
    activityCommentReply.init();

    // 一级评论提交表单
    var activityCommentSubmit = {
        $doms: {
            $form: $('.activity-comment-form'),
            $button: $('.activity-comment-button'),
            $textarea: $('.activity-editor'),
            $editor: customUI.editor,
            $editorBody: $('.wysihtml5-sandbox').contents().find('body'),
            $file: $('.activity-comment-file')
        },
        action: {
            getText: function () {
                return activityCommentSubmit.$doms.$editorBody.html();
            },
            checkText: function () {
                //var value = this.getText();
                var value = $('.wysihtml5-sandbox').contents().find('body').html().trim(); //@kcm
                value = $.trim(value);
                return value.length > 0;
            },
            checkFile: function () {
                var files = activityCommentSubmit.$doms.$file[0].files ,
                    length = files.length;
                for (var i = 0; i < length; i++) {
                    if (!/image/.test(files[i].type)) {
                        alert('请只上传图片文件');
                        return false;
                    }
                }
                return true;
            }
        },
        event: {
            editorKeyUp: function () {
                var $button = activityCommentSubmit.$doms.$button;
                if (activityCommentSubmit.action.checkText()) {
                    $button.removeClass('gray').removeAttr('disabled');
                } else {
                    $button.addClass('gray').attr('disabled');
                }
            },
            formSubmit: function (event) {
                if (!activityCommentSubmit.action.checkText() || !activityCommentSubmit.action.checkFile()) {
                	return false;
                }else if(session_loginId=="") //提交
                {
                	alert("发布评论请先登录.")
                	return false;
                }else
                {
                	sendComment();return false; 
                }
                
            }
        },
        init: function () {
            this.$doms.$button.removeClass('gray').removeAttr('disabled'); //@kcm
        	this.$doms.$editorBody.keyup(this.event.editorKeyUp);
            //this.$doms.$form.submit(this.event.formSubmit);
            this.$doms.$button.click(this.event.formSubmit) ;  //@kcm
        }
    };
    activityCommentSubmit.init();

});



/**
 * 提交评论，不带图片,返回该评论ID
 */
function sendComment()
{
	var texts= $('.wysihtml5-sandbox').contents().find('body').html().trim();
	var tmp=root+"comment/publishcomment";
	jQuery.ajax({
		url: tmp,
		type: "POST",
		async:false,
		data:{"activity":event_id,"user":session_loginId,"texts":texts},
		dataType:"json",
		success: function(json){
			if(json.cord==0){
				//返回评论列表
				commentId=json.data.id;
				//异步上传文件
				//for(var i=1;i<=$(".activity-comment-file").length;i++)
				//{
					if($("#upload").val()=="")
					{
						alert("发表评论成功.");
						window.location.reload();
					}else
					{
							sendFile(1);
							//alert("发表评论成功，上传成功"+($(".activity-comment-file").length-1)+"张图片");
							//window.location.reload();
					}
					
					//$("#upload"+i).attr("name","img");
					
					//$("#upload"+(i-1)).attr("name","img1");
			//	}
				
			}else
			{
				//出现404.或者统一页面.服务器出错
			}
		}
	});
}

/**
 * 保存文件
 * @param {Object} i
 */	
function sendFile(i)
{
	
  	$.ajaxFileUpload( 
     { 
    	url:root+"comment/addcommentimg?id="+commentId,//用于文件上传的服务器端请求的Action地址 
     	type:"post",//请求方法 
     	secureuri:false,//一般设置为false 
     	fileElementId:'upload',//文件id属性  &amp;lt;input type="file" id="upload" name="upload" /&amp;gt; 
     	dataType:"JSON",//返回值类型 一般设置为json,一定要大写,否则可能会出现一些bug 
     	success:function(json)  { 
    	 	 	   //json=JSON.parse(json)  //手动转json
    	 	 	   	successNum=successNum+1;
    	 	 	   	alert("发表评论成功，上传成功"+($(".activity-comment-file").length-1)+"张图片");
					window.location.reload();
					//alert(json.cord);//从服务器返回的json中取出message中的数据,其中message为在struts2中定义的成员变量	
					//$.each(msg,function(k,y){ 
					//$("#id").append("&amp;lt;option  &amp;gt;"+y+" &amp;lt;/option&amp;gt;"); 
			//}); 
		 } 
     }); 
}







