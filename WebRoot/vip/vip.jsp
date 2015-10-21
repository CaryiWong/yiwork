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
    <title>会员-一起开工社区</title>
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
    <link rel="Shortcut Icon" href="<%=basePath %>/images/favicon.ico">
    
    <script type="text/javascript" src="<%=basePath %>scripts/components/jquery/jquery-1.9.1.min.js"></script>
   
</head>
<body>
	<!-- header -->
  	<jsp:include page="/pages/header.jsp" flush="true"></jsp:include>
    <!--    header    -->
<!--    content   -->
   <!--    content   -->
        <div class="container">
            <section   class="panel vip">
                <div class="panel-header">
                    <span class="panel-header-text">会员</span>

                    <div id="UserListpage" class="panel-header-buttons">
                        <!-- <button class="panel-header-button left disabled"> -->
                        <button class="panel-header-button left">
                            <i class="yiqi-icon-slLeft"></i>
                        </button>
                        <button class="panel-header-button right">
                            <i class="yiqi-icon-slRight"></i>
                        </button>
                    </div>
                </div>
                <div id="userList" class="panel-content">
                <!-- 
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
                    <div class="member ">
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
                     -->
                </div>
            </section>
            <section class="panel group hide">
                <div class="panel-header">
                    <span class="panel-header-text">入驻团队</span>

                    <div id="teamListPage" class="panel-header-buttons">
                        <button class="panel-header-button left disabled">
                            <i class="yiqi-icon-slLeft"></i>
                        </button>
                        <button class="panel-header-button right">
                            <i class="yiqi-icon-slRight"></i>
                        </button>
                    </div>
                </div>
                <div id="teamList" class="panel-content">
                
                    <!-- 
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
                    -->
                </div>
            </section>
            <section class="panel join">
                <div class="panel-header">
                    <span class="panel-header-text">成为会员</span>
                </div>
                <div class="panel-content">
                    <article class="join-type join-type-member clearfix">                            
                        <a id="joinMenber" class="join-type-header"  href="javascript:void(0)" >
                            <div class="join-type-img"></div>
                            <span class="join-type-link">申请会员 &gt;&gt;</span>
                        </a>
                         <div class="join-right">
                            成为了 “一起”的会员后，你就可以享受以下的福利，当然你可以享 <br/>
                            受的会有更多，加入我们你就知道了！ <br/>
                            <br/>
                            1．“一起”开工社区会员卡一张； <br/>
                            2．在“一起”开工社区开放区域内流动办公； <br/>
                            3．以会员价优先优惠购买“一起”品牌活动门票（公众门票价格的8折）； <br/>
                            4．在“一起”线下空间及发布资源链接等需求信息； <br/>
                            5. 通过线上平台获得社区内其他会员联系，自主勾搭资源；在社区经理引荐下链接社区智库及战略投资人。 <br/>
                            6．享受“一起”团队的咨询、顾问服务； <br/>
                            7．享有在异地与“一起”建立合作关系的联合办公空间通用的Co-working VISA资格。 <br/>  <br/><br/> 
                        </div>
                    </article>
                    <article class="join-type join-type-enter">
                        <a id="joinTeam" class="join-type-header"  href="javascript:void(0)" >
                            <div class="join-type-img"></div>
                            <span class="join-type-link">申请入驻 &gt;&gt;</span>
                        </a>
                          <div class="join-right">
                            申请并成功入驻“一起”，你就可以享受以下的入驻福利！<br/>
                            <br/>
                            <br/>
                            1. 在空间享有固定办公座位、区域； <br/>
                            2. 免费使用空间内的会议室场地（需提前预定） <br/>
                            3. 免费链接资源与咨询顾问； <br/>
                            4. 享有在“一起”的线上、线下平台发布内容及品牌、产品展示的机会； <br/>
                            5. 享有在空间免费参与每日固定交流活动的福利； <br/>
                            6. 享有主动发起、参与会员活动的福利； <br/>
                            7. 享有优先参与免费公开活动的福利； <br/>
                            8. 八折购买 “一起”品牌活动门票； <br/>
                            9. 以会员价在空间内购买“一起”咖啡； <br/>
                            10. 享有与异地联合办公空间通用的Co-working VISA资格。 <br/>
                            <br/>
                        </div>
                    </article>
                </div>
            </section>
        </div>
    <!--    content   -->


<!-- footer -->
  	<jsp:include page="/pages/footer.jsp" flush="true"></jsp:include>
<!--    footer    -->

    <script type="text/javascript" src="<%=basePath %>scripts/components/util/util.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/components/picture/picture.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/public/top.js"></script>
    <script type="text/javascript" src="<%=basePath %>scripts/vip/vip.js"></script>
	<script type="text/javascript" src="<%=basePath %>scripts/public/public.js"></script>
	
	<script type="text/javascript" src="<%=basePath %>scripts/public/plug_login.js"></script>
	<script type="text/javascript" src="<%=basePath %>scripts/components/md5/jQuery.md5.js"></script>
	
</body>
</html>
