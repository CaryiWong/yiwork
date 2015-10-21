package cn.yi.gather.v11.application;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.yi.gather.v11.entity.User;

import com.common.R;

public class SessionOverTimeInterceptor implements HandlerInterceptor{
	private User sessionuser = null;// session用户
	private List<String> allowURL; // 配置非拦截URL

	public void setAllowURL(List<String> allowURL) {
		this.allowURL = allowURL;
	}

	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse res, Object obj, Exception exc)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res,
			Object object, ModelAndView view) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object object) throws Exception {
		// TODO Auto-generated method stub
		sessionuser = (User)req.getSession().getAttribute(R.User.SESSION_USER);
		// 带命名空间的请求路径
		String currentURL = req.getRequestURI();
		if(currentURL.contains("v2/admin")){
			// 上下文地址
			String contextURL = req.getContextPath();
			String targetURL = currentURL.substring(currentURL.lastIndexOf("/") + 1);// 请求地址
			if (sessionuser == null) {
				if (!allowURL.contains(targetURL)) {
					// 未登录
					PrintWriter out = res.getWriter();
					StringBuilder builder = new StringBuilder();
					builder.append("<script type=\"text/javascript\" charset=\"UTF-8\">");
					builder.append("alert(\"login\");");
					builder.append("window.top.location.href=\"");
					builder.append(contextURL);
					builder.append("/v2/admin/user/index\";");
					builder.append("</script>");
					out.print(builder.toString());
					out.close();
					// res.sendRedirect(contextURL+"/index.jsp");//session超时，跳转到登陆页
					return false;
				}
			}
			return true;
		}else {
			return true;
		}
		
	}

}
