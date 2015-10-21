package cn.yi.gather.v20.service;

import java.sql.Connection;
import java.sql.Date;

import cn.yi.gather.v20.entity.AlipayNotificationLog;
import cn.yi.gather.v20.entity.PendingRefund;
import cn.yi.gather.v20.entity.UserAccount;
import cn.yi.gather.v20.entity.UserAccountLog;
import cn.yi.gather.v20.entity.YiGatherAccountLog;


public interface IYigatherAccountService {

	/**
	 * 获取一起账户的流水记录，分页
	 * 
	 * @param conn
	 * @param page
	 * @param page_size
	 * @return
	 * @throws Exception
	 */
	public com.common.Page<YiGatherAccountLog> getYiGatherAccountLogForPage(Connection conn, Date start_date, Date end_date, Integer page, Integer page_size) throws Exception;
	
	/**
	 * 获取一起账户的 支付宝／网银 流水记录，分页
	 * 
	 * @param conn
	 * @param start_date
	 * @param end_date
	 * @param page
	 * @param page_size
	 * @return
	 * @throws Exception
	 */
	public com.common.Page<AlipayNotificationLog> getAlipayNotificationLogForPage(Connection conn, Date start_date, Date end_date, Integer page, Integer page_size) throws Exception;
	
	/**
	 * 分页显示待退款表
	 * 
	 * @param conn
	 * @param page
	 * @param page_size
	 * @return
	 */
	public com.common.Page<PendingRefund> getPendingRefundForPage(Connection conn, Integer page, Integer page_size) throws Exception;

}
