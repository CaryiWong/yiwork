package cn.yi.gather.v20.application;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.permission.entity.AdminUser;

import com.common.R;

/**
 * admin后台管理-权限过滤
 * @author Lee.J.Eric
 * @time 2015年3月2日 下午5:12:16
 */
public class AdminFilter extends OncePerRequestFilter{
	private static Logger log = Logger.getLogger(AdminFilter.class);
	

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//获取请求的URI
		String uri = request.getRequestURI();
		
		
		
		//请求后台才过滤
		if(uri.contains("v20/admin")&&!uri.contains("v20/admin/user/index") || uri.contains("editor")){
			System.out.println(uri);
			//User sessionuser = (User)request.getSession().getAttribute(R.User.SESSION_USER);
			AdminUser sessionuser = (AdminUser)request.getSession().getAttribute(R.User.SESSION_USER);
			if (sessionuser == null) { //||sessionuser.getRoot()>UserRoot.MANAGER.getCode()
				if(!uri.contains("v20/admin/user/login")){
					// 上下文地址
					String path = request.getContextPath();
					// 未登录
					String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
					request.getSession().setAttribute("tips", "请先登录");
					response.sendRedirect(basePath+"/v20/admin/user/index");//session超时，跳转到登陆页
				}else {
					filterChain.doFilter(request, response);
				}
			}else {
				filterChain.doFilter(request, response);
			}
		}else {
			filterChain.doFilter(request, response);
		}
	}

}
