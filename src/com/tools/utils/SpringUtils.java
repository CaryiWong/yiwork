package com.tools.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringUtils {
	public static WebApplicationContext getCtx(ServletContext context) {
		return WebApplicationContextUtils.getWebApplicationContext(context);
	}

	public static Object getBean(String beanName) {
		WebApplicationContext ctx = ContextLoader
				.getCurrentWebApplicationContext();
		return ctx.getBean(beanName);
	}
	
	public static HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/*
	 * public static HttpServletRequest getRequest(){ return
	 * ServletActionContext.getRequest(); }
	 * 
	 * public static HttpServletResponse getResponse(){ return
	 * ServletActionContext.getResponse(); }
	 * 
	 * public static ServletContext getContext(){ return
	 * ServletActionContext.getServletContext(); }
	 */
}
