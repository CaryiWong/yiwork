<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" import="cn.yi.gather.v20.entity.*"%>
<%@ page language="java" import="com.common.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<footer class="footer">
    <ul class="footer-nav">
    	<li class="footer-nav-item"><a  class="footer-nav-link" href="<%=basePath %>space/space_new.jsp">关于我们</a></li>
        <li class="footer-nav-item"><a  class="footer-nav-link" href="<%=basePath %>contact/copyright.jsp">版权说明</a></li>
        <li class="footer-nav-item"><a  class="footer-nav-link" href="<%=basePath %>contact/contact.jsp">联系我们</a></li>
        <li class="footer-nav-item"><a class="footer-nav-link" href="<%=basePath %>my/feedback.jsp">意见反馈</a></li>
    </ul>
    <div class="footer-text">
        Copyright © 2014 广州诣启网络科技有限公司  <a style="color:white;text-decoration: none;" href="http://www.miibeian.gov.cn/">粤ICP备11000699号-3</a> 
    </div>
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F670a42ee1837bf5d8a2ea85864f3645a' type='text/javascript'%3E%3C/script%3E"));
</script>
</footer>