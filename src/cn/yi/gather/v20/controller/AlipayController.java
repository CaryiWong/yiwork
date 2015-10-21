package cn.yi.gather.v20.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v20.entity.AlipayNotificationLog;
import cn.yi.gather.v20.entity.AlipayRefundNotificationLog;
import cn.yi.gather.v20.service.IAlipayNotificationLogService;
import cn.yi.gather.v20.service.IAlipayRefundNotificationLogService;
import cn.yi.gather.v20.service.IAlipayService;
import cn.yi.gather.v20.service.IItemInstanceService;
import cn.yi.gather.v20.service.IOrderService;
import cn.yi.gather.v20.service.IPaymentService;

import com.alipay.util.AlipayNotify;

@Controller("alipayControllerV20")
@RequestMapping(value = "v20/alipay")
public class AlipayController {

	private static Logger log = Logger.getLogger(AlipayController.class);
	
//	@Resource(name = "dataSourceV20")
//	private ComboPooledDataSource dataSource; 
	
	@Resource(name = "paymentServiceV20")
	private IPaymentService paymentService;
	
	@Resource(name = "orderServiceV20")
	private IOrderService orderService;
	
	@Resource(name = "itemInstanceServiceV20")
	private IItemInstanceService itemInstanceService;
	
	@Resource(name = "alipayServiceV20")
	private IAlipayService alipayService;
	
	@Resource(name = "alipayNotificationLogServiceV20")
	private IAlipayNotificationLogService alipayNotificationLogService;
	
	@Resource(name = "alipayRefundNotificationLogServiceV20")
	private IAlipayRefundNotificationLogService alipayRefundNotificationLogService;
	
	
	/**
	 * 接收来自支付宝的支付通知-wap
	 * @param request
	 * @param response
	 * @param service
	 * @param sign
	 * @param sec_id
	 * @param v
	 * @param notify_data
	 * @author Lee.J.Eric
	 * @time 2015年1月6日 下午4:47:53
	 */
	@RequestMapping(value = "notify_payment_wap")
	public void notifyPayment_wap(HttpServletRequest request,HttpServletResponse response,
			String service,String sign,String sec_id,String v,String notify_data){
		log.warn(new Date()+"notify_payment_wap");
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Document document = DocumentHelper.parseText(notify_data);
			Element rElem = document.getRootElement();//notify
			
			//解释document格式的应答报文
			String notify_id = rElem.selectSingleNode("notify_id")==null?null:rElem.selectSingleNode("notify_id").getText();
			Timestamp notify_time = new Timestamp(format.parse(rElem.selectSingleNode("notify_time").getText()).getTime());
			String notify_type = rElem.selectSingleNode("notify_type")==null?null:rElem.selectSingleNode("notify_type").getText();
			String trade_no = rElem.selectSingleNode("trade_no")==null?null:rElem.selectSingleNode("trade_no").getText();
			String out_trade_no = rElem.selectSingleNode("out_trade_no")==null?null:rElem.selectSingleNode("out_trade_no").getText();
			String subject = rElem.selectSingleNode("subject")==null?null:rElem.selectSingleNode("subject").getText();
			String payment_type = rElem.selectSingleNode("payment_type")==null?null:rElem.selectSingleNode("payment_type").getText();
			String trade_status = rElem.selectSingleNode("trade_status")==null?null:rElem.selectSingleNode("trade_status").getText();
			String refund_status = rElem.selectSingleNode("refund_status")==null?null:rElem.selectSingleNode("refund_status").getText();
			String buyer_id = rElem.selectSingleNode("buyer_id")==null?null:rElem.selectSingleNode("buyer_id").getText();
			String buyer_email = rElem.selectSingleNode("buyer_email")==null?null:rElem.selectSingleNode("buyer_email").getText();
			String seller_id = rElem.selectSingleNode("seller_id")==null?null:rElem.selectSingleNode("seller_id").getText();
			String seller_email = rElem.selectSingleNode("seller_email")==null?null:rElem.selectSingleNode("seller_email").getText();
			Double total_fee = Double.valueOf(rElem.selectSingleNode("total_fee").getText());
			Integer quantity = Integer.valueOf(rElem.selectSingleNode("quantity").getText());
			Double price = Double.valueOf(rElem.selectSingleNode("price").getText());
			String body = rElem.selectSingleNode("body")==null?null:rElem.selectSingleNode("body").getText();
			Timestamp gmt_create = new Timestamp(format.parse(rElem.selectSingleNode("gmt_create").getText()).getTime());
			Timestamp gmt_payment = rElem.selectSingleNode("gmt_payment")==null?null:new Timestamp(format.parse(rElem.selectSingleNode("gmt_payment").getText()).getTime());
			Timestamp gmt_close = new Timestamp(format.parse(rElem.selectSingleNode("gmt_close").getText()).getTime());
			Timestamp gmt_refund = rElem.selectSingleNode("gmt_refund")==null?null:new Timestamp(format.parse(rElem.selectSingleNode("gmt_refund").getText()).getTime());
			String bank_seq_no = rElem.selectSingleNode("bank_seq_no")==null?null:rElem.selectSingleNode("bank_seq_no").getText();
			String error_code = rElem.selectSingleNode("error_code")==null?null:rElem.selectSingleNode("error_code").getText();
			String out_channel_type = rElem.selectSingleNode("out_channel_type")==null?null:rElem.selectSingleNode("out_channel_type").getText();
			String out_channel_amount = rElem.selectSingleNode("out_channel_amount")==null?null:rElem.selectSingleNode("out_channel_amount").getText();
			String out_channel_inst = rElem.selectSingleNode("out_channel_inst")==null?null:rElem.selectSingleNode("out_channel_inst").getText();
			
			//ali通知记录
			AlipayNotificationLog notification = new AlipayNotificationLog();
			notification.setNotifyId(notify_id);
			notification.setNotifyTime(notify_time);
			notification.setNotifyType(notify_type);
			notification.setAlipayTradeNo(trade_no);
			notification.setOrderId(out_trade_no);
			notification.setSubject(subject);
			notification.setPaymentType(payment_type);
			notification.setTradeStatus(trade_status);
			notification.setRefundStatus(refund_status);;
			notification.setBuyerId(buyer_id);
			notification.setBuyerEmail(buyer_email);
			notification.setSellerId(seller_id);
			notification.setSellerEmail(seller_email);
			notification.setTotalFee(total_fee);
			notification.setQuantity(quantity);
			notification.setPrice(price);
			notification.setBody(body);
			notification.setGmtCreate(gmt_create);
			notification.setGmtPayment(gmt_payment);
			notification.setGmtClose(gmt_close);
			notification.setGmtRefund(gmt_refund);
			notification.setBankSeqNo(bank_seq_no);
			notification.setErrorCode(error_code);
			notification.setOutChannelType(out_channel_type);
			notification.setOutChannelAmount(out_channel_amount);
			notification.setOutChannelInst(out_channel_inst);
			
			boolean is_valid_request = true;
			if (refund_status != null || gmt_refund != null) {
				is_valid_request = false;
				log.warn("received alipay refund notification, " + notification);
			} else {
				log.warn(
					String.format(
					"received alipay notification, out_trade_no=%s, trade_no=%s, trade_status=%s",
					out_trade_no, trade_no, trade_status));
			}
			
			if (is_valid_request) {
				//获取支付宝POST过来反馈信息
				Map<String,String> params = new HashMap<String,String>();
				Map requestParams = request.getParameterMap();
				for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					String[] values = (String[]) requestParams.get(name);
					String valueStr = "";
					for (int i = 0; i < values.length; i++) {
						valueStr = (i == values.length - 1) ? valueStr + values[i]
								: valueStr + values[i] + ",";
					}
					//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
					//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
					params.put(name, valueStr);
				}
			
				if (!AlipayNotify.verifyNotify(params)) {
					is_valid_request = false;
				}
			}
			
			if (is_valid_request) {
//				Connection conn = dataSource.getConnection();
				try {
//					conn.setAutoCommit(false);
					alipayService.dealAlipayNotification(notification);	
					// 记录
//					alipayNotificationLogService.saveOrUpdate(notification);
//					paymentService.logAlipayNotification(conn, notification);
//					conn.commit();

//					if (alipayNotificationLogService.isDuplicatedAlipayNotification(notification)) {
//						log.warn(String.format("received a duplicated alipay notification: %s", notification));
//					} else {
//						log.warn(String.format("received alipay notification: %s", notification));
//						alipayService.dealAlipayNotification(notification);
						
//						Order order = orderService.findById(out_trade_no);
//						if(order==null){//无此订单
//							log.error("can not find the order!notify_payment_wap");
//						}else {
//							if(trade_status.equals("TRADE_FINISHED") && error_code == null) {
//								Payment payment = new Payment();
//								payment.setAlipayTradeNo(trade_no);
//								payment.setBuyerId(buyer_id);
//								payment.setBuyerEmail(buyer_email);
//								payment.setMoneyType(MoneyType.RMB.getCode());
//								payment.setMoney(total_fee);
//								payment.setOrderId(out_trade_no);
//								payment.setStatus(PaymentStatus.RECORDED.getCode());
//								payment.setUserId(order.getUserId());
//								if (bank_seq_no != null) {
//									// 网银支付
//									payment.setPaymentType(PaymentType.UNIONPAY.getCode());
//									payment.setBankSeqNo(bank_seq_no);
//								} else {
//									// 支付宝支付
//									payment.setPaymentType(PaymentType.ALIPAY.getCode());
//								}
//								//相关业务处理
//								paymentService.savePayment(conn, payment);
//								orderService.updatePayment(conn, out_trade_no, payment);
//							}
//						}
//					}
//					conn.commit();
				}
				catch (Exception e) {
//					conn.rollback();
					log.error(String.format("failed to handle alipay notification: exception:%s", e));
				}
//				finally {
//					conn.close();
//				}
	
				response.getWriter().println("success");	//请不要修改或删除
	
				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				response.getWriter().println("fail");
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(String.format("notify_payment_wap call back api failed:exception:%s",e));
		} 
	}
	
	/**
	 * 接收来自支付宝的支付通知-web
	 */
	@RequestMapping(value="notify_payment_web")
	public void notifyPayment_web(HttpServletRequest request, HttpServletResponse response,
							String notify_id, Timestamp notify_time, String notify_type, String trade_no,
							String out_trade_no, String subject, String payment_type, String trade_status,
							String refund_status, String buyer_id, String buyer_email, String seller_id,
							String seller_email, Double total_fee, Integer quantity, Double price,
							String body, Timestamp gmt_create, Timestamp gmt_payment, Timestamp gmt_close,
							Timestamp gmt_refund, String bank_seq_no, String error_code,
							String out_channel_type, String out_channel_amount, String out_channel_inst) {
		log.warn(new Date()+"notify_payment_web");
		try {
			AlipayNotificationLog notification = new AlipayNotificationLog();
			notification.setNotifyId(notify_id);
			notification.setNotifyTime(notify_time);
			notification.setNotifyType(notify_type);
			notification.setAlipayTradeNo(trade_no);
			notification.setOrderId(out_trade_no);
			notification.setSubject(subject);
			notification.setPaymentType(payment_type);
			notification.setTradeStatus(trade_status);
			notification.setRefundStatus(refund_status);;
			notification.setBuyerId(buyer_id);
			notification.setBuyerEmail(buyer_email);
			notification.setSellerId(seller_id);
			notification.setSellerEmail(seller_email);
			notification.setTotalFee(total_fee);
			notification.setQuantity(quantity);
			notification.setPrice(price);
			notification.setBody(body);
			notification.setGmtCreate(gmt_create);
			notification.setGmtPayment(gmt_payment);
			notification.setGmtClose(gmt_close);
			notification.setGmtRefund(gmt_refund);
			notification.setBankSeqNo(bank_seq_no);
			notification.setErrorCode(error_code);
			notification.setOutChannelType(out_channel_type);
			notification.setOutChannelAmount(out_channel_amount);
			notification.setOutChannelInst(out_channel_inst);
			
			boolean is_valid_request = true;
			if (refund_status != null || gmt_refund != null) {
				is_valid_request = false;
				log.warn("received alipay refund notification, " + notification);
			} else {
				log.warn(
					String.format(
					"received alipay notification, out_trade_no=%s, trade_no=%s, trade_status=%s",
					out_trade_no, trade_no, trade_status));
			}
			if (is_valid_request) {
				//获取支付宝POST过来反馈信息
				Map<String,String> params = new HashMap<String,String>();
				Map requestParams = request.getParameterMap();
				for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
					String name = (String) iter.next();
					String[] values = (String[]) requestParams.get(name);
					String valueStr = "";
					for (int i = 0; i < values.length; i++) {
						valueStr = (i == values.length - 1) ? valueStr + values[i]
								: valueStr + values[i] + ",";
					}
					//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
					//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
					params.put(name, valueStr);
				}
				if (!AlipayNotify.verifyReturn(params)) {
					is_valid_request = false;
				}
			}
			if (is_valid_request) {
//				Connection conn = dataSource.getConnection();
				try {
//					conn.setAutoCommit(false);
					alipayService.dealAlipayNotification(notification);
					// 记录
//					alipayNotificationLogService.saveOrUpdate(notification);
//					paymentService.logAlipayNotification(conn, notification);
//					conn.commit();

//					if (alipayNotificationLogService.isDuplicatedAlipayNotification(notification)) {
//						log.warn(String.format("received a duplicated alipay notification: %s", notification));
//					} else {
//						log.warn(String.format("received alipay notification: %s", notification));
//						alipayService.dealAlipayNotification(notification);
//						if(trade_status.equals("TRADE_FINISHED") && error_code == null) {
//							Payment payment = new Payment();
//							payment.setAlipayTradeNo(trade_no);
//							payment.setBuyerId(buyer_id);
//							payment.setBuyerEmail(buyer_email);
//							payment.setMoneyType(MoneyType.RMB.getCode());
//							payment.setMoney(total_fee);
//							payment.setOrderId(out_trade_no);
//							
//							if (bank_seq_no != null) {
//								// 网银支付
//								payment.setPaymentType(PaymentType.UNIONPAY.getCode());
//								payment.setBankSeqNo(bank_seq_no);
////								paymentService.savePayment(conn, payment);
////								orderService.updatePayment(conn, out_trade_no, payment);
//							} else {
//								// 支付宝支付
//								payment.setPaymentType(PaymentType.ALIPAY.getCode());
////								paymentService.savePayment(conn, payment);
////								orderService.updatePayment(conn, out_trade_no, payment);
//							}
//							//相关业务处理
////							//获取此订单商品实例列表
//							
//							alipayService.dealAlipayNotification(notification);
//							paymentService.savePayment(conn, payment);
//							orderService.updatePayment(conn, out_trade_no, payment);
//						}
//					}
//					conn.commit();
				}
				catch (Exception e) {
//					conn.rollback();
					log.warn(String.format("failed to handle alipay notification: exception:%s", e));
				}
//				finally {
//					conn.close();
//				}
	
				response.getWriter().println("success");	//请不要修改或删除
	
				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				response.getWriter().println("fail");
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 接收来自支付宝的退款通知
	 */
	@RequestMapping(value="notify_refund")
	public void notifyRefund(HttpServletRequest request, HttpServletResponse response,
							String notify_id, Timestamp notify_time, String notify_type, 
							String batch_no, String success_num, String result_details) {
		try {
			AlipayRefundNotificationLog notification = new AlipayRefundNotificationLog();
			notification.setNotifyId(notify_id);
			notification.setNotifyTime(notify_time);
			notification.setNotifyType(notify_type);
			notification.setBatchNo(batch_no);
			notification.setSuccessNum(success_num);
			notification.setResultDetails(result_details);
			
			log.info(
				String.format(
					"received alipay refund notification, notify_id=%s, batch_no=%s, success_num=%s",
					notify_id, batch_no, success_num));
		
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
			
			if (AlipayNotify.verifyNotify(params)) {//验证成功
//				Connection conn = dataSource.getConnection();
				try {
//					conn.setAutoCommit(false);
						
					// 记录
					alipayRefundNotificationLogService.saveOrUpdate(notification);
//					paymentService.logAlipayRefundNotification(conn, notification);
//					conn.commit();
				}
				catch (Exception e) {
//					conn.rollback();
					log.warn(String.format("failed to handle alipay refund notification: exception:%s", e));
				}
//				finally {
//					conn.close();
//				}
	
				response.getWriter().println("success");	//请不要修改或删除
	
				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				response.getWriter().println("fail");
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
