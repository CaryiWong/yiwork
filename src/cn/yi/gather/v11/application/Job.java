package cn.yi.gather.v11.application;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.service.IUserServiceV2;

/**
 * 定时任务
 * 
 * @author Lee.J.Eric
 * 
 */
@Component("jobV2")
public class Job {
	private static Logger log = Logger.getLogger(Job.class);

	@Resource(name = "userServiceV2")
	private IUserServiceV2 userServiceV2;
	
	
//	// 每天23时59分
//	@Scheduled(cron = "0 59 23 * * ?")
//	public void checkout_user() {
//		log.info("space users check out !");
//		userServiceV2.checkoutalluser();
//	}
}
