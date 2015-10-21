package com.weixin.util;

/**
 * 微信授权登录支付 公众号配置
 * @author Administrator
 *
 */
public class WxConfig {

	public static String APPID = "wxa7f35f92e66817e5";//微信开发平台应用id    
	
	public static String APPSECRET = "71b9a8cced773dc160b418529ee5c664";//应用对应的密钥凭证   
	
	public static String MCHID = "1253068501";//微信商户号  
	
	public static String MCHID_KEY = "guangzhouyiqiwangluoyouXIAN12345";//商户号对应的密钥
	
	private static Boolean MESSAGEENCRYPT= false;	// 消息加密与否
	
	public static String GRANT_TYPE = "client_credential";//常量固定值 
	
	public static String ACCESS_TOKEN = "access_token";//access_token常量值
	
	public static String TICKET = "ticket";//ticket常量值
	
	public static String SIGN_METHOD = "yiqi";//签名算法常量值
	
	public static String JS_NOTIFY_URL = "http://www.yi-gather.com/yg/wxpay/wxjsnotify_url";//微信JS支付成功后回调url
	
	public static String GATEURL = "https://api.weixin.qq.com/pay/genprepay?access_token=";//获取预支付id的接口url
	
	public static String WX_DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	
	public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";//获取access_token对应的url
	
	public static String TICKETURL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";//获取ticket对应的url
	
	public static String EXPIRE_ERRCODE = "42001";//access_token失效后请求返回的errcode
	
	public static String FAIL_ERRCODE = "40001";//重复获取导致上一次获取的access_token失效,返回错误码
	
	//微信统一支付接口
	public static String WX_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	//订单查询接口
	public static String WX_ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	//退款接口
	public static String WX_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	//退款查询接口
	public static String WX_REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	
	//package常量值
	public static String packageValue = "bank_type=WX&body=%B2%E2%CA%D4&fee_type=1&input_charset=GBK&notify_url=http%3A%2F%2F127.0.0.1%3A8180%2Ftenpay_api_b2c%2FpayNotifyUrl.jsp&out_trade_no=2051571832&partner=1900000109&sign=10DA99BCB3F63EF23E4981B331B0A3EF&spbill_create_ip=127.0.0.1&time_expire=20131222091010&total_fee=1";
		
}
