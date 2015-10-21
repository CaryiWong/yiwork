<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<!--[if IE 8]>
<html lang="zh-CN" class="no-js lt-ie9"><![endif]-->
<!--[if gt IE 8]><!-->
<html lang="zh-CN" class="no-js"><!--<![endif]-->
<head>
<script type="text/javascript">
	var root="<%=basePath%>";
</script>
    <meta charset="utf-8">
    <title>空间-一起开工社区</title>
	<!--[if lte IE 8]>
    <script type="text/javascript" src="<%=basePath%>scripts/components/html5/html5.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
	<script type="text/javascript" src="<%=basePath%>scripts/components/ecmascript/es5-shim.js"></script>
	<![endif]-->
    <link rel="stylesheet" href="<%=basePath %>styles/public/reset.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/components/icon/yiqi_icon.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/public/public.css"/>
    <link rel="stylesheet" href="<%=basePath %>styles/pages/space.css"/>
    <link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
</head>
<body>
    <!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->
    <!--    header    -->

    <!--    content   -->
    <section class="intro">
        <div class="container clearfix intro-bg">
            <article class="intro-article">
                <h1 class="intro-title">一起在做什么？</h1>
                <p class="intro-text">
        “一起开工社区”是一个会员共建的创新创业社区。 <br/>
                    在这里聚集了一群跨界的创新创业者，他们在“一起”社区内办公、协作、 <br/>
                    会客、交流，并可以通过社区线上空间实现与其他会员之间的零距离沟通。 <br/>
                    在“一起”开放、共享、协作、创新的社区里，会员间分享着资源链接的 <br/>
                    无限可能。<br/>
                    社区实体空间位于广州老城区荔湾区，线上空间由微信订阅号 <br/>
                    （“一起开工”）、微博公众号（“一起开工”）、移动应用APP（“一<br/>
                    起”）及社区官网 www.yi-gather.com 四大部分组成。
                </p>
            </article>
        </div>
    </section>
    <section class="layout">
        <div class="container">
            <header class="space-header">空间布局</header>
            <div class="layout-text">“一起”为了能够给更多创业创新者提供更好的办公、交流与协作的环境与氛围，在空间的功能及细节设计上花费了大量的心思，空间的各个部分不但有着相对独立的功能，同时各个区域也可以根据具体的需要进行灵活的调整与优化。</div>
            <section class="layout-map">
                <div class="slide" data-slide='{"autoTime":0,"buttonChange":true}'  >
                    <div class="slide-area container">
                        <ul class="slide-list">
							<li class="slide-item">
                                <!--
                                  first: 一楼区域
                                -->
                                <div class="layoutDetail first"
                                     data-layoutDetail-rule='{
                                        "first":{"width":"366","height":"385","left":0,"top":0}
                                     }'>
                                    <div class="layoutDetail-map">
                                        <div class="layoutDetail-map-text">一楼布局</div>
                                        <div class="layoutDetail-map-plane"></div>
                                        <div class="layoutDetail-map-light"></div>
                                        <div class="layoutDetail-map-blue"></div>
                                    </div>
                                    <ul class="layoutDetail-img-list">
                                        <li id="first" class="layoutDetail-img-item picture"
                                            data-layoutDetail-group="first">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/demo_imgs/1.jpg"
                                                 alt=""/></li>
                                    </ul>
                                </div>
                            </li>
                            <li class="slide-item">
                                <!--
                                  second: 二楼区域
                                -->
                                <div class="layoutDetail second"
                                     data-layoutDetail-rule='{
                                      "second":{"width":"650","height":"372","left":0,"top":0}
                                      }'>
                                    <div class="layoutDetail-map">
                                        <div class="layoutDetail-map-text">二楼布局</div>
                                        <div class="layoutDetail-map-plane"></div>
                                        <div class="layoutDetail-map-light"></div>
                                        <div class="layoutDetail-map-blue"></div>
                                    </div>
                                    <ul class="layoutDetail-img-list">
                                        <li id="second" class="layoutDetail-img-item picture"
                                            data-layoutDetail-group="second">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/demo_imgs/1.jpg"
                                                 alt=""/></li>
                                    </ul>
                                </div>
                            </li>
                        
                        
                            <li class="slide-item">
                                <!--
                                  move：流动区，read：阅读区，product：会员产品展示区，meeting：大会议室
                                  talk：小会议室，client：会客区，office：固定办公区，toilet：卫生间
                                  draw：手绘场，vip：会员展示区,front:前台,door:大门
                                  为保留动画流畅 可考虑把位置定位全用left和top
                                -->
                                <div class="layoutDetail third"
                                     data-layoutDetail-rule='{
                                        "move":{"width":200,"height":240,"left":0,"top":0},
                                        "read":{"width":160,"height":130,"left":0,"top":240},
                                        "talk":{"width":64,"height":20,"left":145,"top":240},
                                        "meeting":{"width":125,"height":130,"left":170,"top":240},
                                        "talk":{"width":60,"height":63,"left":207,"top":30},
                                        "client":{"width":73,"height":67,"left":205,"top":94},
                                        "office":{"width":320,"height":160,"left":385,"top":85},
                                        "toilet":{"width":113,"height":50,"left":460,"top":250},
                                        "draw":{"width":105,"height":39,"left":358,"top":258},
                                        "vip":{"width":50,"height":59,"left":305,"top":240},
                                        "front":{"width":95,"height":85,"left":280,"top":135},
                                        "door":{"width":33,"height":70,"left":305,"top":300}
                                        }'>
                                    <div class="layoutDetail-map">
                                        <div class="layoutDetail-map-text">三楼布局</div>
                                        <div class="layoutDetail-map-plane"></div>
                                        <div class="layoutDetail-map-light"></div>
                                        <div class="layoutDetail-map-blue"></div>
                                    </div>
                                    <ul id="thridMain" class="layoutDetail-img-list">
                                        <li id="move" class="layoutDetail-img-item picture"
                                            data-layoutDetail-group="move" >
                                            <img class="layoutDetail-img picture" data-picture-src="<%=basePath %>images/public/loading.gif" src="<%=basePath %>images/demo_imgs/1.jpg" alt=""/></li>
                                        <!-- <li class="layoutDetail-img-item"
                                            data-layoutDetail-group="move">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/demo_imgs/2.jpg" alt=""/></li> -->
                                        <li id="read" class="layoutDetail-img-item"
                                                data-layoutDetail-group="read">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <!-- <li  class="layoutDetail-img-item"
                                            data-layoutDetail-group="read">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/demo_imgs/4.jpg" alt=""/></li> -->
                                        <li id="meeting" class="layoutDetail-img-item"
                                            data-layoutDetail-group="meeting">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="product" class="layoutDetail-img-item picture"
                                            data-layoutDetail-group="product">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="talk" class="layoutDetail-img-item"
                                            data-layoutDetail-group="talk">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="client" class="layoutDetail-img-item"
                                            data-layoutDetail-group="client">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <!-- <li id="read" class="layoutDetail-img-item"
                                            data-layoutDetail-group="read">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/demo_imgs/1.jpg" alt=""/></li> -->
                                        <li id="office" class="layoutDetail-img-item" data-layoutDetail-group="office">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="toilet" class="layoutDetail-img-item" data-layoutDetail-group="toilet">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="front" class="layoutDetail-img-item" data-layoutDetail-group="front">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        
                                        <li id="door" class="layoutDetail-img-item" data-layoutDetail-group="door">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="draw" class="layoutDetail-img-item" data-layoutDetail-group="draw">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="vip" class="layoutDetail-img-item" data-layoutDetail-group="vip">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                    </ul>
                                </div>
                            </li>
                            <li class="slide-item">
                                <!--
                                  balcony：楼台，client1:会客区1，client2:会客区2
                                  resource：资源墙，bar：吧台,stairs:楼梯,blackboard:黑板
                                -->
                                <div class="layoutDetail forth"
                                     data-layoutDetail-rule='{
                                        "balcony":{"width":105,"height":375,"left":0,"top":0},
                                        "client1":{"width":65,"height":115,"left":107,"top":0},
                                        "client2":{"width":205,"height":110,"left":108,"top":263},
                                        "resource":{"width":45,"height":140,"left":140,"top":114},
                                        "bar":{"width":130,"height":132,"left":183,"top":130},
                                        "stairs":{"width":93,"height":100,"left":198,"top":33},
                                        "blackboard":{"width":26,"height":123,"left":110,"top":122}
                                        }'>
                                    <div class="layoutDetail-map">
                                        <div class="layoutDetail-map-text">四楼布局</div>
                                        <div class="layoutDetail-map-plane"></div>
                                        <div class="layoutDetail-map-light"></div>
                                        <div class="layoutDetail-map-blue"></div>
                                    </div>
                                    <ul class="layoutDetail-img-list">
                                        <li id="balcony" class="layoutDetail-img-item" data-layoutDetail-group="balcony">
                                            <img  class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="client1" class="layoutDetail-img-item" data-layoutDetail-group="client1">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="client2" class="layoutDetail-img-item" data-layoutDetail-group="client2">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="resource" class="layoutDetail-img-item" data-layoutDetail-group="resource">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="bar" data-picture-src="" class="layoutDetail-img-item picture" data-layoutDetail-group="bar">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="stairs" class="layoutDetail-img-item" data-layoutDetail-group="stairs">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                        <li id="blackboard" class="layoutDetail-img-item" data-layoutDetail-group="blackboard">
                                            <img class="layoutDetail-img picture" src="<%=basePath %>images/public/loading.gif" alt=""/></li>
                                    </ul>
                                </div>
                            </li>

                        </ul>
                    </div>
                    <div class="slide-switchButtons container">
                        <button class="slide-switch-button left">
                            <i class="add-icon-left"></i>
                        </button>
                        <button class="slide-switch-button right">
                            <i class="add-icon-right"></i>
                        </button>
                    </div>
                </div>
            </section>
        </div>
    </section>
    <section id="contact" class="address">
        <div  class="container">
            <header class="space-header">地址</header>
            <div class="address-map walk">

            </div>
            <div class="clearfix address-content">
                <div class="address-info fl">
                    <address>
                        <i class="yiqi-icon-place"></i> 广东省，广州市，荔湾区，中山七路68号
                    </address>
                    <span class="phone">
                        <i class="yiqi-icon-phone"></i> 81869804
                    </span>
                </div>
                <div class="address-buttonGroup fr">
                    <div class="address-button walk">
                        <div class="address-button-icon">
                            <i class="yiqi-icon-walk"></i>
                        </div>
                        <div class="address-button-text">
                            步行路线
                        </div>
                    </div>
                    <div class="address-button car">
                        <div class="address-button-icon">
                            <i>P</i>
                        </div>
                        <div class="address-button-text">
                            停车路线
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
     <section class="platform">
        <div class="container clearfix">
            <header class="space-header">一起线上平台</header>
            <div class="platform-item platform-apple">
                <div class="platform-title">
                    <div class="platform-icon">
                        <i class="add-icon-apple"></i>
                    </div>
                    IOS版本下载：
                </div>
                <div class="platform-content"></div>
            </div>
            <div class="platform-item platform-android">
                <div class="platform-title">
                    <div class="platform-icon">
                        <i class="add-icon-android"></i>
                    </div>
                    安卓版本下载：
                </div>
                <div class="platform-content"></div>
            </div>
            <div class="platform-item platform-weixin">
                <div class="platform-title">
                    <div class="platform-icon">
                        <i class="add-icon-weixin"></i>
                    </div>
                   微信公众号：
                </div>
                <div class="platform-content"></div>
            </div>
        </div>
    </section>
    
    
    
    <section class="partner">
        <div class="container">
            <header class="space-header">伙伴</header>
            <ul class="partner-list">
                <li class="partner-list-item">
                    <a class="partner-link" href="#"><img class="partner-img" src="<%=basePath %>images/pages/space/space_22.jpg"
                                                                                    alt=""/></a></li>
                <li class="partner-list-item">
                    <a class="partner-link" href="#"><img class="partner-img" src="<%=basePath %>images/pages/space/space_24.jpg"
                                                                                    alt=""/></a></li>
                <li class="partner-list-item">
                    <a class="partner-link" href="#"><img class="partner-img" src="<%=basePath %>images/pages/space/space_26.jpg"
                                                                                    alt=""/></a></li>
                <li class="partner-list-item"><a class="partner-link" href=""><img class="partner-img" src="<%=basePath %>images/pages/space/space_28.jpg"
                                                                                   alt=""/></a></li>
                <li class="partner-list-item"><a class="partner-link" href=""><img class="partner-img" src="<%=basePath %>images/pages/space/space_30.jpg"
                                                                                   alt=""/></a></li>
                <li class="partner-list-item"><a class="partner-link" href=""><img class="partner-img" src="<%=basePath %>images/pages/space/space_32.jpg"
                                                                                   alt=""/></a></li>
                <li class="partner-list-item"><a class="partner-link" href=""><img class="partner-img" src="<%=basePath %>images/pages/space/space_34.jpg"
                                                                                   alt=""/></a></li>
                <li class="partner-list-item"><a class="partner-link" href=""><img class="partner-img" src="<%=basePath %>images/pages/space/space_36.jpg"
                                                                                   alt=""/></a></li>
                <li class="partner-list-item"><a class="partner-link" href=""><img class="partner-img" src="<%=basePath %>images/pages/space/space_38.jpg"
                                                                                   alt=""/></a></li>
            </ul>
        </div>
    </section>
    <!--    content   -->

	<!-- footer -->
  	<jsp:include page="/pages/footer.jsp" flush="true"></jsp:include>
	<!--    footer    -->
    <script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/picture/picture.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/slide/slide.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/layoutDetail/layoutDetail.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/pages/space.js"></script>
	<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>
	<script type="text/javascript" src="<%=basePath %>scripts/public/plug_login.js"></script>
	<script type="text/javascript" src="<%=basePath %>scripts/components/md5/jQuery.md5.js"></script>
	<script type="text/javascript" src="<%=basePath %>scripts/space/space.js"></script>
	
</body>
</html>
