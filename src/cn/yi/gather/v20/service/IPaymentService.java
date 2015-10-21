package cn.yi.gather.v20.service;

import java.sql.Connection;
import java.util.List;

import cn.yi.gather.v20.entity.AlipayNotificationLog;
import cn.yi.gather.v20.entity.AlipayRefundNotificationLog;
import cn.yi.gather.v20.entity.Payment;

public interface IPaymentService {

	/**
	 * 是否收到了重复的支付宝通知
	 * @param conn
	 * @param alipay_notification
	 * @return
	 * @throws Exception
	 */
	public boolean isDuplicatedAlipayNotification(Connection conn, AlipayNotificationLog alipay_notification) throws Exception;
	
	
	/**
	 * 记录 收到来自支付宝的支付通知
	 * @param conn
	 * @param alipay_notification
	 * @throws Exception
	 */
	public void logAlipayNotification(Connection conn, AlipayNotificationLog alipay_notification) throws Exception;
	
	/**
	 * 记录 收到来自支付宝的退款通知
	 * @param conn
	 * @param alipay_notification
	 * @throws Exception
	 */
	public void logAlipayRefundNotification(Connection conn, AlipayRefundNotificationLog alipay_refund_notification) throws Exception;
	
	/**
	 * 保存支付记录
	 * 
	 * @param conn
	 * @param notification
	 * @throws Exception
	 */
	public void savePayment(Connection conn, Payment payment) throws Exception;
	
	/**
	 * 预备退款，待人工审核
	 * 
	 * @param conn
	 * @param user_id
	 * @param money
	 * @param order_id
	 * @param alipay_trade_no
	 * @param buyer_id
	 * @param buyer_email
	 * @throws Exception
	 */
	public void prepareToRefund(Connection conn, String user_id, Double money, String order_id,
							String alipay_trade_no, String buyer_id, String buyer_email, String bank_seq_no, String memo) throws Exception;
	
	/**
	 * 预备退款，待人工审核
	 * @param payment
	 * @param memo
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月14日 下午3:06:11
	 */
	public void prepareToRefund(Payment payment,String memo) throws Exception;
	
	/**
	 * 设置支付状态
	 * @param conn
	 * @param payment_id
	 * @param status
	 * @throws Exception
	 */
	public void setPaymentStatus(Connection conn, String payment_id, int status) throws Exception;
	
	/**
	 * 设置支付状态
	 * @param payment_id
	 * @param status
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月14日 下午3:08:33
	 */
	public void setPaymentStatus(String payment_id, int status) throws Exception;
	
	public void setPaymentStatusByAlipayTradeNo(Connection conn, String trade_no, int status) throws Exception;
	
	/**
	 * 查看支付状态
	 * @param conn
	 * @param payment_id
	 * @return
	 * @throws Exception
	 */
	public Integer getPaymentStatus(Connection conn, String payment_id) throws Exception;
	public Integer getPaymentStatusByAlipayTradeNo(Connection conn, String trade_no) throws Exception;
	
	public Payment saveOrUpdate(Payment entity);
	
	/**
	 * 根据订单号和电子单的状态查询
	 * @param orderId
	 * @param stauts
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月14日 下午2:49:45
	 */
	public List<Payment> findByOrderIdAndStatus(String orderId,Integer stauts);
	
	public Payment findById(String id);
}
