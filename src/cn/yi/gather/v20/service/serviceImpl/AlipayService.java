package cn.yi.gather.v20.service.serviceImpl;

import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v20.entity.AlipayNotificationLog;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.Payment;
import cn.yi.gather.v20.entity.ItemInstance.ItemInstanceStatus;
import cn.yi.gather.v20.entity.ItemInstanceLog;
import cn.yi.gather.v20.entity.ItemInstanceLog.OpType;
import cn.yi.gather.v20.entity.Order;
import cn.yi.gather.v20.entity.Order.OrderType;
import cn.yi.gather.v20.entity.Payment.PaymentStatus;
import cn.yi.gather.v20.entity.Payment.PaymentType;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.UserAccount;
import cn.yi.gather.v20.entity.UserAccount.AccountStatus;
import cn.yi.gather.v20.entity.UserAccount.MoneyType;
import cn.yi.gather.v20.service.IAlipayNotificationLogService;
import cn.yi.gather.v20.service.IAlipayService;
import cn.yi.gather.v20.service.IItemInstanceLogService;
import cn.yi.gather.v20.service.IItemInstanceService;
import cn.yi.gather.v20.service.IOrderService;
import cn.yi.gather.v20.service.IPaymentService;
import cn.yi.gather.v20.service.IUserAccountService;
import cn.yi.gather.v20.service.IUserService;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.common.Jr;
import com.common.R;

@Service("alipayServiceV20")
public class AlipayService implements IAlipayService{
	private static Logger log = Logger.getLogger(AlipayService.class);
	
	@Resource(name = "orderServiceV20")
	private IOrderService orderService;
	@Resource(name = "userAccountServiceV20")
	private IUserAccountService userAccountService;
	
	@Resource(name = "userServiceV20")
	private IUserService userService;

	@Resource(name = "itemInstanceServiceV20")
	private IItemInstanceService itemInstanceService;
	
	@Resource(name = "itemInstanceLogServiceV20") 
	private IItemInstanceLogService itemInstanceLogService;
	
	@Resource(name = "paymentServiceV20")
	private IPaymentService paymentService;
	
	@Resource(name = "alipayNotificationLogServiceV20")
	private IAlipayNotificationLogService alipayNotificationLogService;
	
	@Transactional
	@Override
	public void dealAlipayNotification(AlipayNotificationLog notification) throws Exception{
		// TODO Auto-generated method stub
		// 记录
		alipayNotificationLogService.saveOrUpdate(notification);
		if (alipayNotificationLogService.isDuplicatedAlipayNotification(notification)) {
			log.warn(String.format("received a duplicated alipay notification: %s", notification));
		} else {
			log.warn(String.format("received alipay notification: %s", notification));
			Order order = orderService.findById(notification.getOrderId());
			if(order==null){
				log.error("order does not exist.AlipayNotificationLog:"+notification.getNotifyId());
				throw new Exception("order does not exist!order_id:notification.getOrderId()");
			}else {
				log.warn("dealAlipayNotification----"+notification.getTradeStatus());
				if((notification.getTradeStatus().equals("TRADE_FINISHED")||notification.getTradeStatus().equals("TRADE_SUCCESS"))&&notification.getErorCode() == null){
					log.warn("TRADE_FINISHED||TRADE_SUCCESS");
					Payment payment = new Payment();
					payment.setAlipayTradeNo(notification.getAlipayTradeNo());
					payment.setBuyerId(notification.getBuyerId());
					payment.setBuyerEmail(notification.getBuyerEmail());
					payment.setMoneyType(MoneyType.RMB.getCode());
					payment.setMoney(notification.getTotalFee());
					payment.setOrderId(notification.getOrderId());
					payment.setStatus(PaymentStatus.RECORDED.getCode());
					payment.setUserId(order.getUserId());
					payment.setCreateTime(new Timestamp(System.currentTimeMillis()));
					payment.setModifyTime(new Timestamp(System.currentTimeMillis()));
					if (notification.getBankSeqNo() != null) {
						// 网银支付
						payment.setPaymentType(PaymentType.UNIONPAY.getCode());
						payment.setBankSeqNo(notification.getBankSeqNo());
					} else {
						// 支付宝支付
						payment.setPaymentType(PaymentType.ALIPAY.getCode());
					}
//				log.warn("paymentService.saveOrUpdate(payment)");
					paymentService.saveOrUpdate(payment);
//				log.warn("orderService.updatePayment(payment)");
					orderService.updatePayment(payment);
				}
			}
		}
		
	}

	@Override
	public Jr applyAlipay(String type, Jr jr, Order order) throws Exception {
		// TODO Auto-generated method stub
		if("mobile".equals(type)){
		//			//请求业务参数详细
		String req_dataToken = "<direct_trade_create_req><notify_url>"
		+ R.AliPay.NOTIFY_WAP_URL
		+ "</notify_url><seller_account_name>"
		+ R.AliPay.SELLER_EMAIL
		+ "</seller_account_name><out_trade_no>"
		+ order.getId()
		+ "</out_trade_no><subject>"
		+ order.getOrderTypeName()
		+ "</subject><total_fee>"
		+ order.getTotalPrice().toString()
		+ "</total_fee></direct_trade_create_req>";
		//必填
		//////////////////////////////////////////////////////////////////////////////////
		
		//把请求参数打包成数组
		Map<String, String> sParaTempToken = new HashMap<String, String>();
		sParaTempToken.put("service", R.AliPay.SERVICE_WAP);
		sParaTempToken.put("partner", R.AliPay.PATNER);
		sParaTempToken.put("_input_charset", R.AliPay.INPUT_CHARSET);
		sParaTempToken.put("sec_id", R.AliPay.SIGN_TYPE);
		sParaTempToken.put("format", R.AliPay.FORMAT);
		sParaTempToken.put("v", R.AliPay.V);
		sParaTempToken.put("req_id", R.AliPay.REQ_ID);
		sParaTempToken.put("req_data", req_dataToken);
		
		//建立请求
		String sHtmlTextToken = AlipaySubmit.buildRequest(R.AliPay.ALIPAY_GATEWAY_NEW_WAP,"", "",sParaTempToken);
		//URLDECODE返回的信息
		sHtmlTextToken = URLDecoder.decode(sHtmlTextToken,AlipayConfig.input_charset);
		//获取token
		String request_token = AlipaySubmit.getRequestToken(sHtmlTextToken);
		
		//业务详细
		String req_data = "<auth_and_execute_req><request_token>" + request_token + "</request_token></auth_and_execute_req>";
		//必填
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("sec_id", AlipayConfig.sign_type);
		sParaTemp.put("format", R.AliPay.FORMAT);
		sParaTemp.put("v", R.AliPay.V);
		sParaTemp.put("req_data", req_data);
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(R.AliPay.ALIPAY_GATEWAY_NEW_WAP, sParaTemp, "get", "确认");
		jr.setCord(0);
		jr.setData(sHtmlText);
		jr.setMsg("操作成功");
		
		}else if ("web".equals(type)) {
			// 把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "create_direct_pay_by_user");
			sParaTemp.put("partner", R.AliPay.PATNER);
			sParaTemp.put("_input_charset", R.AliPay.INPUT_CHARSET);
			sParaTemp.put("payment_type", R.AliPay.PAYMENT_TYPE);
			sParaTemp.put("notify_url", R.AliPay.NOTIFY_WEB_URL);
//			sParaTemp.put("return_url", return_url);
			sParaTemp.put("seller_email", R.AliPay.SELLER_EMAIL);
			sParaTemp.put("out_trade_no", order.getId());
			sParaTemp.put("subject", order.getOrderTypeName());
			sParaTemp.put("total_fee", order.getTotalPrice().toString());
//			sParaTemp.put("body", body);
//			sParaTemp.put("show_url", show_url);
			sParaTemp.put("anti_phishing_key", R.AliPay.ANTI_PHISHING_KEY);
			sParaTemp.put("exter_invoke_ip", R.AliPay.EXTER_INVOKE_IP);
			
			// 建立请求
			String sHtmlText = AlipaySubmit.buildRequest(R.AliPay.ALIPAY_GATEWAY_NEW_WEB,sParaTemp, "get", "确认");
//			response.setContentType("text/html;charset=UTF-8");  
//			response.setHeader("Cache-Control","no-cache");
//			PrintWriter out = response.getWriter();
			System.out.println(sHtmlText);
//			out.print(sHtmlText);
			
			jr.setCord(0);
			jr.setData(sHtmlText);
			jr.setMsg("操作成功");
		}else {
			
		jr.setCord(0);
		jr.setData(order);
		jr.setMsg("请支付订单");
		}
		return jr;
	}

}
