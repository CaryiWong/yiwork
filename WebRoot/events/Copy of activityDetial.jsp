<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <!doctype html>
<html lang="zh-CN">
<head>
<%
String event_id=request.getParameter("event_id");
%>
<script type="text/javascript">
	var root="<%=basePath%>";
	var event_id="<%=event_id%>";  //活动ID
</script>
    <meta charset="utf-8">
    <title>活动详情</title>
    <!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath %>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <!--    editor      -->
    <link rel="stylesheet" href="<%=basePath %>styles/components/editor/editor.css"/>
    <!--    editor      -->
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/activities.css"/>
   
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->
<!--    content   -->

<!--    content   -->
<div class="container activity-content clearfix">
    <section class="activity-detail fl">
       
        <article class="activity-articleInfo">
            <header class="info-header clearfix">
            	<img id="event_mainImg" class="info-poster" src="" />
            	<div class="info-header-operate">
               	 	<h1 id="event_title" class="info-title fl">光影下的老油条</h1>
               		<a id="event_join"  class="info-apply button fr green">报名</a>
                </div>
            </header>
            <p class="info-text" id="event_summary">拿起你的手，和我们一同寻找西门口的老油条。留住那些年我们共同的回忆，快快加入早餐猎手的行列！拿起你的手，和我们一同寻找西门口的老油条。留住那些年我们共同的回忆，快快加入早餐猎手的行列！拿起你的手，和我们一同寻找西门口的老油条。留住那些年我们共同的回忆，快快加入早餐猎手的行列！</p>
            <ul class="info-list">
                <li id="event_time" class="info-list-item">
                   <i class="add-icon-clock"></i> 时间：2013.12.12
                </li>
                <li id="event_address" class="info-list-item">
                    <i class="add-icon-place"></i>
                    地点：广州市荔湾区中山七路68号3楼
                </li>
                <li id="event_num" class="info-list-item">
                    <i class="add-icon-smile"></i>
                    人数：30人
                </li>
                <li id="event_cost" class="info-list-item">
                    <i class="add-icon-money"></i>
                    费用：免费
                </li>
                <li id="event_tel" class="info-list-item">
                    <i class="add-icon-phone"></i>
                    电话：15023432345
                </li>
            </ul>
            <div id="event_labels" class="info-tags">
                <span id="" class="info-tags-text">标签：</span>
                <div class="info-tag">创新</div>
                <div class="info-tag">美食</div>
                <div class="info-tag">旅行</div>
            </div>
        </article>
        <section id="event_commentList" class="activity-comments">
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
        </section>
        <form name="myform" action="<%=basePath%>comment/publishcomment"  ENCTYPE="multipart/form-data" method="post" class="activity-comment-form clearfix">
            <div class="activity-comment-title">发表评论</div>
            <div id="editor-toolbar">
                  <header>
                    <ul class="commands">
                      <li data-wysihtml5-command="bold" title="Make text bold (CTRL + B)" class="command"></li>
                      <li data-wysihtml5-command="italic" title="Make text italic (CTRL + I)" class="command"></li>
                      <li data-wysihtml5-command="insertUnorderedList" title="Insert an unordered list" class="command"></li>
                      <li data-wysihtml5-command="insertOrderedList" title="Insert an ordered list" class="command"></li>
                    </ul>
                  </header>
             </div>
            <textarea class="activity-editor editor">dddddd</textarea>
            <div class="activity-comment-img image-preview fl">
                <div class="activity-img-input">
                    上传图片 <br/>
                    <input type="file" id="upload" name="img" class="activity-comment-file" multiple />
                </div>
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
            已报名人数18人
        </header>
        <ul id="join_userList" class="member-list" style="display: block;">
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
        </ul>
        <div class="member-more clearfix">
            <a class="member-more-link fr" href="#">查看更多 <i class="yiqi-icon-right"></i></a>
        </div>
    </aside>
</div>
<!--    content   -->

<!--    footer    -->
<footer class="footer">
    <ul class="footer-nav">
        <li class="footer-nav-item"><a class="footer-nav-link" href="#">关于我们</a></li>
        <li class="footer-nav-item"><a class="footer-nav-link" href="#">版权说明</a></li>
        <li class="footer-nav-item"><a class="footer-nav-link" href="#">联系我们</a></li>
        <li class="footer-nav-item"><a class="footer-nav-link" href="#">意见反馈</a></li>
    </ul>
    <div class="footer-text">
        广州公司
    </div>
</footer>
<!--    footer    -->

<script type="text/javascript" src="<%=basePath %>scripts/components/jquery/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
<script type="text/javascript" src="<%=basePath %>plug/timeago/jquery.timeago.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/event/activities_detail.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>

<script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
<!--    editor      -->
<script type="text/javascript" src="<%=basePath %>scripts/components/editor/advanced.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/editor/wysihtml5-0.3.0.js"></script>
<!--    editor      -->
<!--    picture -->
<script type="text/javascript" src="<%=basePath %>scripts/components/picture/picture.js"></script>
<!--    picture -->
<script type="text/javascript" src="<%=basePath %>scripts/components/customUI/customUI.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/pages/activity.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/public/plug_login.js"></script>
<script type="text/javascript" src="<%=basePath %>scripts/components/md5/jQuery.md5.js"></script>

<!-- confirm -->
<div class="apply-dialog hide">
    <p class="apply-dialog-text">确认报名参加这个活动吗</p>
    <div class="apply-dialog-buttons container clearfix">
        <button class="apply-dialog-cancel apply-dialog-button button gray fl">取消</button>
        <a class="apply-dialog-sure apply-dialog-button button fr">确认</a>
    </div>
</div>


</body>
</html>


