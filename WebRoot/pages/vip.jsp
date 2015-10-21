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
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/vip.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/activities.css"/>
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
    
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->
<!--    content   -->
   <!--    content   -->
        <div class="container">
            <section class="panel vip">
                <div class="panel-header">
                    <span class="panel-header-text">会员</span>

                    <div class="panel-header-buttons">
                        <button class="panel-header-button left disabled">
                            <i class="yiqi-icon-slLeft"></i>
                        </button>
                        <button class="panel-header-button right">
                            <i class="yiqi-icon-slRight"></i>
                        </button>
                    </div>
                </div>
                <div class="panel-content">
                    <div class="member">
                        <div class="member-imgArea">
                            <img class="member-img" src="../images/demo_imgs/head.jpg"/>

                            <div class="member-imgMask"></div>
                        </div>

                        <div class="member-info">
                            <div class="member-name">kiki大叔控</div>
                            <div class="member-job">设计师</div>
                            <div class="member-introduction">
                                说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                            </div>
                        </div>
                    </div>
                    <div class="member">
                        <div class="member-imgArea">
                            <img class="member-img" src="../images/demo_imgs/head.jpg"/>

                            <div class="member-imgMask"></div>
                        </div>

                        <div class="member-info">
                            <div class="member-name">kiki大叔控</div>
                            <div class="member-job">设计师</div>
                            <div class="member-introduction">
                                说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                            </div>
                        </div>
                    </div>
                    <div class="member">
                        <div class="member-imgArea">
                            <img class="member-img" src="../images/demo_imgs/head.jpg"/>

                            <div class="member-imgMask"></div>
                        </div>

                        <div class="member-info">
                            <div class="member-name">kiki大叔控</div>
                            <div class="member-job">设计师</div>
                            <div class="member-introduction">
                                说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                            </div>
                        </div>
                    </div>
                    <div class="member">
                        <div class="member-imgArea">
                            <img class="member-img" src="../images/demo_imgs/head.jpg"/>

                            <div class="member-imgMask"></div>
                        </div>

                        <div class="member-info">
                            <div class="member-name">kiki大叔控</div>
                            <div class="member-job">设计师</div>
                            <div class="member-introduction">
                                说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section class="panel group">
                <div class="panel-header">
                    <span class="panel-header-text">入驻团队</span>

                    <div class="panel-header-buttons">
                        <button class="panel-header-button left disabled">
                            <i class="yiqi-icon-slLeft"></i>
                        </button>
                        <button class="panel-header-button right">
                            <i class="yiqi-icon-slRight"></i>
                        </button>
                    </div>
                </div>
                <div class="panel-content">
                    <div class="member">
                        <div class="member-imgArea">
                            <img class="member-img" src="../images/demo_imgs/head.jpg"/>

                            <div class="member-imgMask"></div>
                        </div>

                        <div class="member-info">
                            <div class="member-name">kiki大叔控</div>
                            <div class="member-introduction">
                                说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                            </div>
                        </div>
                    </div>
                    <div class="member">
                        <div class="member-imgArea">
                            <img class="member-img" src="../images/demo_imgs/head.jpg"/>

                            <div class="member-imgMask"></div>
                        </div>

                        <div class="member-info">
                            <div class="member-name">kiki大叔控</div>
                            <div class="member-introduction">
                                说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                            </div>
                        </div>
                    </div>
                    <div class="member">
                        <div class="member-imgArea">
                            <img class="member-img" src="../images/demo_imgs/head.jpg"/>

                            <div class="member-imgMask"></div>
                        </div>

                        <div class="member-info">
                            <div class="member-name">kiki大叔控</div>
                            <div class="member-introduction">
                                说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                            </div>
                        </div>
                    </div>
                    <div class="member">
                        <div class="member-imgArea">
                            <img class="member-img" src="../images/demo_imgs/head.jpg"/>

                            <div class="member-imgMask"></div>
                        </div>

                        <div class="member-info">
                            <div class="member-name">kiki大叔控</div>
                            <div class="member-introduction">
                                说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section class="panel join">
                <div class="panel-header">
                    <span class="panel-header-text">成为会员</span>
                </div>
                <div class="panel-content">
                    <article class="join-type join-type-member clearfix">
                        <header class="join-type-header">
                            <div class="join-type-img"></div>
                            <a class="join-type-link" href="#">申请会员&gt;&gt;</a>
                        </header>
                        <div class="join-right">
                            成为了 “一起”的会员后，你就可以享受以下的福利，当然你可以享 <br/>
                            受的会有更多，加入我们你就知道了！ <br/>
                            <br/>
                            可以在空间的流动办公区免费办公 <br/>
                            可以免费链接资源与获得咨询顾问的服务 <br/>
                            经过审核后可以在“一起”线下空间、线上平台以及自媒体发布需求 <br/>
                            可以在“一起”线下空间、线上平台及自媒体展示产品和资料 <br/>
                            可以在空间免费参与每日固定交流活动的福利 <br/>
                            享有主动发起、参与会员活动的福利 <br/>
                            享有优先参与公开活动的福利 <br/>
                            享有八折优惠购买“一起”品牌活动门票的福利 <br/>
                            享有优惠购买“一起”产品及会员产品的福利 <br/>
                            享有以会员价购买“一起咖啡”的福利 <br/>
                            享有购买团队入驻空间办公套餐的资格 <br/>
                            享有与异地联合办公空间通用的co-working VISA资格 <br/>
                        </div>
                    </article>
                    <article class="join-type join-type-enter">
                        <header class="join-type-header">
                            <div class="join-type-img"></div>
                            <a class="join-type-link" href="#">申请入驻&gt;&gt;</a>
                        </header>
                        <div class="join-right">
                            成为了 “一起”的会员后，你就可以享受以下的福利，当然你可以享 <br/>
                            受的会有更多，加入我们你就知道了！ <br/>
                            <br/>
                            可以在空间的流动办公区免费办公 <br/>
                            可以免费链接资源与获得咨询顾问的服务 <br/>
                            经过审核后可以在“一起”线下空间、线上平台以及自媒体发布需求 <br/>
                            可以在“一起”线下空间、线上平台及自媒体展示产品和资料 <br/>
                            可以在空间免费参与每日固定交流活动的福利 <br/>
                            享有主动发起、参与会员活动的福利 <br/>
                            享有优先参与公开活动的福利 <br/>
                            享有八折优惠购买“一起”品牌活动门票的福利 <br/>
                            享有优惠购买“一起”产品及会员产品的福利 <br/>
                            享有以会员价购买“一起咖啡”的福利 <br/>
                            享有购买团队入驻空间办公套餐的资格 <br/>
                            享有与异地联合办公空间通用的co-working VISA资格 <br/>
                        </div>
                    </article>
                </div>
            </section>
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

    <script type="text/javascript" src="../scripts/components/jquery/jquery.js"></script>
    <script type="text/javascript" src="../scripts/components/util/util.js"></script>
    <script type="text/javascript" src="../scripts/public/public.js"></script>

</body>
</html>
