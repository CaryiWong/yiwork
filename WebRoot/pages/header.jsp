<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="cn.yi.gather.v20.entity.*"%>
<%@ page language="java" import="com.common.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
Object objUser = session.getAttribute(R.User.SESSION_USER);
%>
<%
if(objUser==null ||objUser==""){
%>
<script>
var session_loginId ="";	//已登录的账户ID
var session_nickName="";  //昵称
</script>
    <!--    header    -->
    <header class="header">
        <div class="container clearfix">
             <a class="fl logo" href="#"></a>
            <ul class="header-nav fl">
                <li class="header-nav-item"><a id="spaceMain" class="header-nav-link" href="javascript:void(0)">空间</a></li>
                <li class="header-nav-item"><a id="eventsMain" class="header-nav-link" href="javascript:void(0)">活动</a></li>
                <li class="header-nav-item"><a id="anoutMain" class="header-nav-link" href="javascript:void(0)">会员</a></li>
            </ul>
            <div class="header-status fr">
                <a href="javascript:void(0)" id="index_reg" class="header-status-link status-link-login" >注册</a>
                <a href="javascript:void(0)" id="" class="header-status-link status-link-sign" >登录</a>
                <div class="header-search ">
                    <input class="header-search-text" />
                    <button class="header-search-button" >
                    	 <i class="yiqi-icon-search"></i>
                    </button>
                </div>
            </div>
        </div>
    </header>
    
    
<header class="hide">
    <div class="container clearfix">
        <a class="logo fl " href="#">
            <i class="yiqi-icon">&#xf000c;</i>
        </a>
        <div class="header-nav-button fr hide">
            <i class="yiqi-icon-list"></i>
        </div>
        <!--<div class="header-linkGroup fr">-->
            <!--<a class="header-link">登录</a>-->
            <!--<a class="header-link">注册</a>-->
        <!--</div>-->
        <div class="header-status fr hide">
            <a href="#" class="header-status-link status-link-head">
                <img class="header-user" src="<%=basePath %>images/demo_imgs/head2.png"/>
            </a>
            <a class="header-status-link status-link-notice" href="#">
                <i class="yiqi-icon">&#xf0013;</i>
               <!--  <i class="yiqi-icon-circle"></i> -->
            </a>
        </div>
        <ul class="header-nav hide">
            <li class="header-nav-item"><a class="header-nav-link" href="#">空间</a></li>
            <li class="header-nav-item"><a class="header-nav-link" href="#">活动</a></li>
            <li class="header-nav-item"><a class="header-nav-link" href="#">关于我们</a></li>
            <li class="header-nav-item">
                <div class="header-search ">
                    <input class="header-search-text"/>
                    <button class="header-search-button">
                        <i class="yiqi-icon-search"></i>
                    </button>
                </div>
            </li>
        </ul>
    </div>
</header>
    
    
    <!--    header    -->
<%}else{
	 System.out.println(objUser);
	 User user=(User)objUser;
%>
<script>
var session_loginId ="<%=user.getId()%>";	//已登录的账户ID
var session_nickName="<%=user.getNickname()%>";  //昵称
var user_root="<%=user.getRoot()%>";  //root 
</script>
       <!--    header    -->
    <header class="header">
        <div class="container clearfix">
              <a class="fl logo" href="javascript:void(0)"></a>
            <ul class="header-nav fl">
                <li class="header-nav-item"><a id="spaceMain" class="header-nav-link" href="javascript:void(0)">空间</a></li>
                <li class="header-nav-item"><a id="eventsMain" class="header-nav-link" href="javascript:void(0)">活动</a></li>
                <li class="header-nav-item"><a id="anoutMain" class="header-nav-link" href="javascript:void(0)">会员</a></li>
            </ul>
            <div class="header-status fr">
            	<a href="javascript:void(0)" id="user_center" class="header-status-link status-link-head" >
                    <img class="header-user" src="<%=basePath %>images/demo_imgs/head2.png" />
                </a>
                <a id="user_msg" class="header-status-link status-link-notice" href="javascript:void(0)">
                    <i class="yiqi-icon-notice"></i>
                    <i id="headerUnRead" class="yiqi-icon-circle"></i>
                </a>
                <!-- <a href="#" id="user_center" class="header-status-link" ><%=user.getNickname() %>的账号</a>
                <a id="index_exit" class="header-status-link" href="#">退出</a> -->
                <div class="header-search ">
                    <input class="header-search-text" />
                    <button class="header-search-button" >
                    	 <i class="yiqi-icon-search"></i>
                    </button>
                </div>
            </div>
        </div>
    </header>
    
    <header class="hide">
    <div class="container clearfix">
        <a class="logo fl " href="#">
            <i class="yiqi-icon-logo"></i>
        </a>
        <div class="header-nav-button fr hide">
            <i class="yiqi-icon-list"></i>
        </div>
        <!--<div class="header-linkGroup fr">-->
            <!--<a class="header-link">登录</a>-->
            <!--<a class="header-link">注册</a>-->
        <!--</div>-->
        <div class="header-status fr hide">
            <a href="#" class="header-status-link status-link-head">
                <img class="header-user" src="<%=basePath %>images/demo_imgs/head2.png"/>
            </a>
            <a class="header-status-link status-link-notice" href="#">
                <i class="yiqi-icon-notice"></i>
                <i class="yiqi-icon-circle"></i>
            </a>
        </div>
        <ul class="header-nav hide">
            <li class="header-nav-item"><a class="header-nav-link" href="#">空间</a></li>
            <li class="header-nav-item"><a class="header-nav-link" href="#">活动</a></li>
            <li class="header-nav-item"><a class="header-nav-link" href="#">关于我们</a></li>
            <li class="header-nav-item">
                <div class="header-search ">
                    <input class="header-search-text"/>
                    <button class="header-search-button">
                        <i class="yiqi-icon-search"></i>
                    </button>
                </div>
            </li>
        </ul>
    </div>
</header>
    
    <!--    header    -->
<%} %>    

