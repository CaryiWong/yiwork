package cn.yi.gather.v20.application;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tools.utils.PropertyUtils;

public class ApplicationListener implements ServletContextListener{
	private static Logger log = LoggerFactory.getLogger(ApplicationListener.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		try {
			log.info("start to loading application...");
			// 加载配置信息
			String confile = sce.getServletContext().getRealPath("") + "/WEB-INF/classes/application.properties";
			PropertyUtils.loadFile(confile);
			log.info("...application loading is finished");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}
}
