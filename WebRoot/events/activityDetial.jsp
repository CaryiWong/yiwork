<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String useragent = request.getHeader("user-agent");
System.out.println(useragent);
//i1f(useragent.indexOf("iPhone")>0 || useragent.indexOf("iPod")>0 || useragent.indexOf("iPad")>0 ||useragent.indexOf("Android")>0)
//{
//	response.sendRedirect("www.baidu.com");
//}
%>
 <!doctype html>
<!--[if IE 8]>
<html lang="zh-CN" class="no-js lt-ie9"><![endif]-->
<!--[if gt IE 8]><!-->
<html lang="zh-CN" class="no-js"><!--<![endif]-->
<head>
<%
String event_id=request.getParameter("event_id");
%>
<script type="text/javascript">
	var root="<%=basePath%>";
	var event_id="<%=event_id%>";  //活动ID
</script>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0,user-scalable=no">
    <title>活动详情-一起开工社区</title>
	<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <!--    editor      -->
    <link rel="stylesheet" href="<%=basePath %>styles/components/editor/editor.css"/>
    <!--    editor      -->
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/activities.css"/>
    <link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">
   
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->
<!--    content   -->

<!--    content   -->
<div class="container activity-content clearfix">
 <iframe id="huiguiframe" class="iframe-response-height" style="width: 100%;border: 0;"  src="http://api2.yi-gather.com/imgtext/20141226/201412260312830.html">
 
 </iframe>
</div>

<div class="container activity-content clearfix hide">
    <section class="activity-detail fl">
        <article class="activity-articleInfo">
            <header class="info-header clearfix">
            	<img id="event_mainImg" class="info-poster" src="" />
            	<div class="info-header-operate">
               	 	<h1 id="event_title" class="info-title fl"></h1>
               		<a id="event_join"  class="info-apply button fr green">报名</a>
                </div>
            </header>
            <div class="tab">
                <div id="eventHuiGu" class="tab-buttons" style="display: none;">
                    <button data-tab-name="detail" class="tab-button button active">活动详情</button>
                    <button data-tab-name="review" class="tab-button button">活动回顾</button>
                </div>
                <div class="tab-contents">
                    <div data-tab-name="detail" class="tab-content active">
                        <p class="info-text" id="event_summary"></p>
                        <ul class="info-list">
                            <li id="event_time" class="info-list-item">
                                <i class="add-icon-clock"></i>  
                            </li>
                            <li id="event_address"  class="info-list-item">
                                <i class="add-icon-place"></i>
                                 
                            </li>
                            <li id="event_num" class="info-list-item">
                                <i class="add-icon-smile"></i>
                              
                            </li>
                            <li id="event_cost" class="info-list-item">
                                <i class="add-icon-money"></i>
                           
                            </li>
                            <li id="event_tel" class="info-list-item">
                                <i class="add-icon-phone"></i>
                                 
                            </li>
                        </ul>
                        <div id="event_labels" class="info-tags">
                            <span class="info-tags-text">标签：</span>
                            <div class="info-tag">创新</div>
                        </div>
                        <input type="hidden" name="acttype" value="0"/>
                        <input type="hidden" name="acttypeurl" value="0"/>
                    </div>
                    <div data-tab-name="review" class="tab-content">
                        <iframe name="eventIframe" id="eventIframe" class="activity-review iframe-response-height">
                        </iframe>
                         
                    </div>
                </div>
            </div>

        </article>
        <section id="event_commentList" class="activity-comments">
        <!-- 
            <article class="comment">
                <img class="comment-head" src="<%=basePath %>images/demo_imgs/head.png" />
                <div class="comment-content">
                    <a class="comment-name">KIMI</a>
                    <div class="comment-text">我最喜欢吃老油条了。我最喜欢吃老油条了。我最喜欢吃老油条了。我最喜欢吃老油条了。我最喜欢吃老油条了。我最喜欢吃老油条了。我最喜欢吃老油条了。我最喜欢吃老油条了。我最喜欢吃老油条了。</div>
                    <div class="comment-operate">
                        <button class="comment-reply"><i class="yiqi-icon-comment"></i> 回复</button>
                        <time class="comment-time">15分钟前</time>
                    </div>
                    <div class="comment-control clearfix">
                        <input class="comment-reply-text text"/>
                        <button class="comment-reply-button button ">评论</button>
                    </div>
                </div>
            </article>
            <article class="comment">
                <img class="comment-head" src="<%=basePath %>images/demo_imgs/head.png"/>

                <div class="comment-content">
                    <a class="comment-name">KIMI</a>

                    <div class="comment-text">我最喜欢吃老油条了。</div>
                    <div class="comment-img">
                        <img class="picture" src="<%=basePath %>images/demo_imgs/4.jpg"/>
                        <img class="picture" src="<%=basePath %>images/demo_imgs/5.jpg"/>
                        <img class="picture" src="<%=basePath %>images/demo_imgs/4.jpg"/>
                        <img class="picture" src="<%=basePath %>images/demo_imgs/5.jpg"/>
                        <img class="picture" src="<%=basePath %>images/demo_imgs/4.jpg"/>
                        <img class="picture" src="<%=basePath %>images/demo_imgs/5.jpg"/>
                        <img class="picture" src="<%=basePath %>images/demo_imgs/4.jpg"/>
                        <img class="picture" src="<%=basePath %>images/demo_imgs/5.jpg"/>
                        <img class="picture" src="<%=basePath %>images/demo_imgs/4.jpg"/>
                        <img class="picture" src="<%=basePath %>images/demo_imgs/5.jpg"/>
                    </div>
                    <div class="comment-operate">
                        <button class="comment-reply"><i class="yiqi-icon-comment"></i> 回复</button>
                        <time class="comment-time">15分钟前</time>
                    </div>
                    <div class="comment-control clearfix">
                                            <input class="comment-reply-text text" /><button class="comment-reply-button button ">评论</button>
                                        </div>
                </div>
            </article>
            <article class="comment">
                <img class="comment-head" src="" />

                <div class="comment-content">
                    <a class="comment-name">习大大</a>

                    <div class="comment-text">我最喜欢吃庆丰包子了！！</div>
                    <div class="comment-operate">
                        <button class="comment-reply"><i class="yiqi-icon-comment"></i> 回复</button>
                        <time class="comment-time">15分钟前</time>
                    </div>
                    <div class="comment-control clearfix">
                        <input class="comment-reply-text text" /><button class="comment-reply-button button ">评论</button>
                    </div>
                </div>
            </article>
            <article class="comment">
                <img class="comment-head" src="<%=basePath %>images/demo_imgs/head.png"/>

                <div class="comment-content">
                    <a class="comment-name">KIMI</a>

                    <div class="comment-text">我最喜欢吃老油条了。</div>
                    <div class="comment-operate">
                        <button class="comment-reply"><i class="yiqi-icon-comment"></i> 回复</button>
                        <time class="comment-time">15分钟前</time>
                    </div>
                    <div class="comment-control clearfix">
                                            <input class="comment-reply-text text" /><button class="comment-reply-button button ">评论</button>
                                        </div>
                    <ul class="comment-reply-list">
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item" ><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>
                        <li class="reply-item"><a class="reply-name" href="#">我就是那个姑娘</a><span
                                class="reply-text">：好吃的油条</span>
                        </li>

                        <li class="reply-item more">
                            还有15条评论，<a href="#">点击查看更多&gt;&gt;</a>
                        </li>
                    </ul>
                </div>
            </article>
             -->
        </section>
        
			<div id="pages" class="pages" >
			<!-- 
				<a class="page-item">&lt; 上一页</a>
				<a class="page-item">1</a>
				<a class="page-item">2</a>
				<a class="page-item active">3</a>
				<a class="page-item">4</a>
				<a class="page-item">5</a>
				<a class="page-item">下一页 &gt;</a>
				<a class="page-item">最后一页 &gt;&gt;</a>
			 -->
			</div>
			
			
        <form name="myform"  ENCTYPE="multipart/form-data" method="post" class="activity-comment-form clearfix hide">
            <div class="activity-comment-title">发表评论</div>
            <div style="display: none;" id="editor-toolbar">
                  <header>
                    <ul class="commands">
                      <li data-wysihtml5-command="bold" title="Make text bold (CTRL + B)" class="command"></li>
                      <li data-wysihtml5-command="italic" title="Make text italic (CTRL + I)" class="command"></li>
                      <li data-wysihtml5-command="insertUnorderedList" title="Insert an unordered list" class="command"></li>
                      <li data-wysihtml5-command="insertOrderedList" title="Insert an ordered list" class="command"></li>
                    </ul>
                  </header>
             </div>
           	<!-- <textarea class="activity-editor editor">dddddd</textarea>-->
           	<textarea id="commentId" name="commentId" class="activity-editor editor">
           	</textarea>
            <div class="activity-comment-img image-preview fl">
              
            </div>
            <!-- 参数 -->
            <input type="hidden" id="activity" name="activity" value="" />
            <input type="hidden" id="user" name="user" value="" />
            <input type="hidden" id="type" name="type" value="1" />
            <input type="hidden" id="texts" name="texts" value="" />
            
            <button disabled class="activity-comment-button fr button gray">评论</button>
        </form>
    </section>
    <aside class="activity-members fl">
        <header id="jionNum" class="members-header">
            已报名人数人
        </header>
        <ul id="join_userList" class="member-list clearfix" style="display: block;">
        	<!--  
            <li class="member">
                <img class="member-header" src="<%=basePath %>images/demo_imgs/head.png" alt=""/>
                <div class="member-info">
                    <div class="member-name">kimi</div>
                    <div class="member-job">艺术工作者、屌丝</div>
                </div>
            </li>
            <li class="member">
                <img class="member-header" src="<%=basePath %>images/demo_imgs/head.jpg" alt=""/>

                <div class="member-info">
                    <div class="member-name">kimi</div>
                    <div class="member-job">艺术工作者、屌丝</div>
                </div>
            </li>
            
            -->
            
        </ul>
        
       <!--  <div id="ifshowMore" class="member-more clearfix">
              <a class="member-more-link fr" href="#">查看更多 <i class="yiqi-icon-right"></i></a>  
        </div>
        -->
         
    </aside>
</div>
<!-- 
 	<iframe name="eventIframe" id="eventIframe1"  class="activity-review activity-review-mobile iframe-response-height">
    </iframe>
 -->
<!--    content   -->



<!-- footer -->
  	<jsp:include page="/pages/footer.jsp" flush="true"></jsp:include>
<!--    footer    -->

<script type="text/javascript" src="<%=basePath %>scripts/components/jquery/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
<script type="text/javascript" src="<%=basePath %>plug/timeago/jquery.timeago.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/event/activities_detail.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>

<script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
<!--    editor     
<script type="text/javascript" src="<%=basePath %>scripts/components/editor/advanced.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/editor/wysihtml5-0.3.0.js"></script>
<!--    editor      -->
<script type="text/javascript" src="<%=basePath %>scripts/components/customUI/customUI.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/pages/activity.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/plug_login.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/md5/jQuery.md5.js"></script>
<!-- kindEditor插件 -->
	<link rel="stylesheet" href="<%=basePath %>/plug/textarea/kindeditor-4.1.10/themes/default/default.css" />
	<link rel="stylesheet" href="<%=basePath %>/plug/textarea/kindeditor-4.1.10/plugins/code/prettify.css" />
	<script charset="utf-8" src="<%=basePath %>/plug/textarea/kindeditor-4.1.10/kindeditor.js"></script>
	<script charset="utf-8" src="<%=basePath %>/plug/textarea/kindeditor-4.1.10/lang/zh_CN.js"></script>
	<script charset="utf-8" src="<%=basePath %>/plug/textarea/kindeditor-4.1.10/plugins/code/prettify.js"></script>
	<!-- end -->
<!-- confirm -->
<script type="text/javascript" src="<%=basePath %>scripts/event/eventEditor.js"></script>
<!--    picture -->
<script type="text/javascript" src="<%=basePath %>scripts/components/picture/picture.js"></script>
<!--    picture -->
<div class="apply-dialog hide">
    <p class="apply-dialog-text">确认报名参加这个活动吗</p>
    <div class="apply-dialog-buttons container clearfix">
        <button class="apply-dialog-cancel apply-dialog-button button gray fl">取消</button>
        <a id="confirmButton" class="apply-dialog-sure apply-dialog-button button fr">确认</a>
    </div>
</div>


</body>
</html>


