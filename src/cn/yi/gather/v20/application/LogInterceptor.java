package cn.yi.gather.v20.application;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class LogInterceptor implements HandlerInterceptor{
	private static Logger log = Logger.getLogger(LogInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse res, Object obj, Exception exc)
			throws Exception {
		// TODO Auto-generated method stub
//		String currentURL = req.getRequestURI();
//		log.info("URL|"+currentURL+"|afterCompletion");
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res,
			Object object, ModelAndView view) throws Exception {
		// TODO Auto-generated method stub
//		String currentURL = req.getRequestURI();
//		log.info("URL|"+currentURL+"|postHandle");
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object object) throws Exception {
		// TODO Auto-generated method stub
		String currentURL = req.getRequestURI();
		StringBuffer sb = new StringBuffer();
		sb.append(currentURL).append(" ");
		
		Enumeration<String> keys = req.getParameterNames();
		String user_ID = "-";
		while(keys.hasMoreElements()) {
		    String k = keys.nextElement(); 
		    sb.append(k).append("=").append(req.getParameter(k)).append("&");
			if(k.equals("user_id")){//以后接口记录用户行为的用户id的key值,改动后改为相应的值
				user_ID = req.getParameter(k);
			}
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(user_ID).append(" ").append(sb);
		log.info(buffer.toString());
		return true;
	}

}
