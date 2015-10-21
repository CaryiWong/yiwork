package cn.yi.gather.v20.job;

import java.sql.Connection;

import javax.annotation.Resource;

import org.apache.log4j.*;
import org.springframework.stereotype.Service;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import cn.yi.gather.v20.service.IOrderService;

@Service
public class Crontab {
	public static Logger log = Logger.getLogger(Crontab.class);

	@Resource
	private IOrderService orderService;
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource; 
	
	public void doJob() {
//		try {
//			log.info("处理任务开始>........");
//            
//			// 1. 检查和处理所有超时未支付的订单
//			Connection conn = dataSource.getConnection();
//			conn.setAutoCommit(false);
//			orderService.handleExpiredOrders(conn);
//			conn.close();
//			
//			log.info("处理任务结束!");
//		} catch (Exception e) {
//            log.error("处理任务出现异常", e);
//		}
	}
  
}
