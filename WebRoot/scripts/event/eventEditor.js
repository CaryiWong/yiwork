$(document).ready(function(e) {
	//初始化文本编辑器
	KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="commentId"]', {
				width : '600px',
				minWidth:'600px',
				themeType : 'simple',
				items : [
					'fontname','bold','italic','underline','fontsize','forecolor','link','multiimage'  //'emoticons' 表情
				],
				//cssPath : '../plugins/code/prettify.css',
				filePostName : 'imgs',
				uploadJson : root+'upload/uploadimgs',
				allowFileManager : true,
				extraFileUploadParams : {  //上传图片时，支持添加别的参数一并传到服务器。
					 
				},
				afterCreate : function () {
					var self = this;
					self.sync();
					K.ctrl(document, 13, function() {
						self.sync();
						alert(document.getElementById("commentId").value);
						//document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						//文本框内按ctrl+enter  提交
						self.sync();
						alert(document.getElementById("commentId").value);
						//document.forms['example'].submit();
					});
					 
				}
				
			});
			prettyPrint();
		});
	
	
});





