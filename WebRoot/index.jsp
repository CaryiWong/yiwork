	<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!doctype html>
<html lang="zh-CN" >
<head>
<script type="text/javascript">
	var root="<%=basePath%>";
</script>

    <meta charset="utf-8">
    <title>首页</title>
    <!--[if lte IE 8]>
    <script type="text/javascript" src="/scripts/components/html5/html5.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/index.css"/>
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>plug/layer-v1.7.0/layer-v1.7.0/layer/layer.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/index.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
    
</head>	
<body>
  <jsp:include page="/pages/header.jsp" flush="false"></jsp:include>
  
    <!--    content   -->
    <section class="banner">
        <div class="container">
            <div class="banner-content">
                <p class="banner-text">
                    秋天到了,快和你的小伙伴一起跟上秋天的节奏!<br/>
                    带上你的装备和 一颗年轻的心,一起去秋游吧!!!
                </p>
                <button class="banner-button"> 
                    点击报名
                </button>
            </div>
            <div class="banner-img">

            </div>
        </div>
    </section>

    <section class="activities">
        <div class="container" >
            <div class="panel" >
                <div class="panel-header">
                    <span class="panel-header-text ">活动</span>
                    <div class="panel-header-buttons" id="activityListpage">
                        
                    </div>
                </div>
                <div class="panel-content clearfix" id="activityList">
                    
                </div>
            </div>
        </div>
    </section>

    <div class="other-panels">
        <div class="container">
            <section class="panel vip">
                <div class="panel-header">
                    <span class="panel-header-text">会员</span>

                    <div class="panel-header-buttons" id="UserListpage">
                    	<!-- 
                        <button class="panel-header-button left">&lt;</button>
                        <button class="panel-header-button right">&gt;</button>
                         -->
                    </div>
                </div>
                <div class="panel-content" id="userList">
                    <div class="member">
                        <img class="member-img" src="<%=basePath %>images/demo_imgs/head.jpg" />
                        <div class="member-info">
                            <div class="member-name">kiki大叔控</div>
                            <div class="member-job">设计师</div>
                            <div class="member-introduction">
                                说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山不为人知的故事,谈谈大片里的大
                            </div>
                        </div>
                    </div>
                    <div class="member">
                        <img class="member-img" src="<%=basePath %>images/demo_imgs/head.jpg" />


                        <div class="member-info">
                            <div class="member-name">kiki大叔控</div>
                            <div class="member-job">设计师</div>
                            <div class="member-introduction">
                                说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                            </div>
                        </div>
                    </div>
                    <div class="member">
                        <img class="member-img" src="<%=basePath %>images/demo_imgs/head.jpg" />


                        <div class="member-info">
                            <div class="member-name">kiki大叔控</div>
                            <div class="member-job">设计师</div>
                            <div class="member-introduction">
                                说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                            </div>
                        </div>
                    </div>
                    <div class="member">
                        <img class="member-img" src="<%=basePath %>images/demo_imgs/head.jpg" />

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
                                    <button class="panel-header-button left">&lt;</button>
                                    <button class="panel-header-button right">&gt;</button>
                                </div>
                            </div>
                            <div class="panel-content">
                                <div class="member">
                                    <img class="member-img" src="<%=basePath %>images/demo_imgs/head.jpg" />

                                    <div class="member-info">
                                        <div class="member-name">kiki大叔控</div>
                                        <div class="member-introduction">
                                            说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                                        </div>
                                    </div>
                                </div>
                                <div class="member">
                                    <img class="member-img" src="<%=basePath %>images/demo_imgs/head.jpg" />

                                    <div class="member-info">
                                        <div class="member-name">kiki大叔控</div>
                                        <div class="member-introduction">
                                            说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                                        </div>
                                    </div>
                                </div>
                                <div class="member">
                                    <img class="member-img" src="<%=basePath %>images/demo_imgs/head.jpg" />

                                    <div class="member-info">
                                        <div class="member-name">kiki大叔控</div>
                                        <div class="member-introduction">
                                            说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                                        </div>
                                    </div>
                                </div>
                                <div class="member">
                                    <img class="member-img" src="<%=basePath %>images/demo_imgs/head.jpg" />
                                    <div class="member-info">
                                        <div class="member-name">kiki大叔控</div>
                                        <div class="member-introduction">
                                            说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山说说电影背后不为人知的故事,谈谈大片里的大孤山
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
        </div>
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


</body>
</html>
