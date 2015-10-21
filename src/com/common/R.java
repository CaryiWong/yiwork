package com.common;

import java.sql.Timestamp;
import java.util.HashMap;

import com.alipay.config.AlipayConfig;
import com.alipay.util.UtilDate;




/**
 * 静态常量
 * 
 * @author Lee.J.Eric 2013-12-31 下午5:53:56
 * 
 */
public class R {
	
	/**
	 * 帐号
	 * @author Lee.J.Eric
	 *
	 */
	public static class User {
		public static String SESSION_USER = "session_user";
//		public static String ADMIN_USERNAME = "leejeric@qq.com";
//		public static String ADMIN_PASSWORD = "123456";
		public static String BEFORE = "before";
		public static String AFTER = "after";
		/**
		 * 会员年费
		 */
		public static final Double MEMBERSHIP_ANNUAL_FEE = 150d;
		
		/**
		 * 静态访问地址
		 */
		public static HashMap<String, String> STATICURL = new HashMap<String, String>(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 6943386553531110090L;

			{
		    put("vipapplyurl", "apply/menber.jsp");//会员申请
		    put("passwordurl", "pages/findPassword.jsp");//忘记密c码
		    put("signdealurl", "pages/signDeal.html");//注册协议
		    put("reviewispass", "no");//app审核状态
		    }            
		};
		
	}
	
	/**
	 * 公共常量
	 * @author Lee.J.Eric
	 *
	 */
	public static class Common {
		/**
		 * 邮箱帐号
		 */
		public static String EMAIL_ACCOUNT = "yigather_support@163.com";
		/**
		 * 邮箱密码
		 */
		public static String EMAIL_PASSWORD = "yiqijuhuiadmin";
		/**
		 * 邮箱服务器
		 */
		public static String EMAIL_SMTP = "smtp.163.com";
		
		/**
		 * 邮箱帐号-企业帐号-account
		 */
		public static String EMAIL_YIGATHER_ACCOUNT = "member@yi-gather.com";
		
		/**
		 * 邮箱帐号-企业帐号-pwd
		 */
		public static String EMAIL_YIGATHER_PASSWORD = "yigather2013";
		
		/**
		 * 邮箱帐号-企业帐号-smtp
		 */
		public static String EMAIL_YIGATHER_SMTP = "smtp.qq.com";
		
		/**
		 * 公网访问地址
		 */

		//public static String BASEPATH = "http://www.yi-gather.com/";
		public static String BASEPATH = "http://www.yi-gather.com/";
//		public static String BASEPATH = "http://192.168.1.190:8080/yiwork/";
		/**
		 * 用户图像目录
		 */
		public static String USER_ICON = "c";
		/**
		 * 图片目录
		 */
		public static String[] IMG_TMP = {"d","e","f","g"};
		
		/**
		 * 活动详情目录
		 */
		public static String ACT_IMG = "h";
		/**
		 * 图片目录基址
		 */
		//public static String IMG_DIR = "/home/www/yigather-test-img/";
		public static String IMG_DIR = "/home/www/appv2-images/";
		//public static String IMG_DIR = "D:/flydy/yiwork_img/";
		//public static String IMG_DEALT_DIR = "/home/www/yigather-test-img/z/";
		public static String IMG_DEALT_DIR = "/home/www/appv2-images/z/";
//		public static String IMG_DEALT_DIR = "D:/flydy/yiwork_img/z/";
		
	}
	
	/**
	 * 图片常量
	 * @author Lee.J.Eric
	 * @time 2014年3月28日下午5:30:07
	 */
	public static class Img{
		public static String _1280X600 = "_1280X600";
		public static String _640X640 = "_640X640";
		public static String _300X420 = "_300X420";
		public static String _320X320 = "_320X320";
		public static String _160X160 = "_160X160";
	}
	
	/**
	 * push常量(消息推送)
	 * @author Lee.J.Eric
	 * @time 2014年4月4日上午11:17:38
	 */
	public static class JPush{
		/**
		 * jpush接口
		 */
		public static String PUSH_URL_8800 = "http://api.jpush.cn:8800/v2/push";
		/**
		 * SSL安全接口
		 */
		public static String PUSH_URL_443 = "https://api.jpush.cn:443/v2/push";
		public static String SEND_NO = "sendno";
		public static String APP_KEY = "app_key";
		public static String APP_KEY_V = "b204fa37984de7ab7bf4075f";
		public static String APP_KEY_IOS_V = "48282c809b54f88b8b164c8d";
		public static String RECEIVER_TYPE = "receiver_type";
		/**
		 * 可选
		 */
		public static String RECEIVER_VALUE = "receiver_value";
		public static String MASTER_SECRET = "master_secret";
		public static String MASTER_SECRET_V = "1f79fd6942f8ad3bcc667187";
		public static String MASTER_SECRET_IOS_V = "6c603b06e64dd4122dc559b5";
		/**
		 * 由 sendno, receiver_type, receiver_value, master_secret  4个值拼接起来（直接拼接字符串）后，进行一次MD5 (32位大写) 生成
		 */
		public static String VERIFICATION_CODE = "verification_code";
		public static String MSG_TYPE = "msg_type";
		public static String MSG_CONTENT = "msg_content";
		/**
		 * 可选
		 */
		public static String SEND_DESCRIPTION = "send_description";
		/**
		 * 终端类型,如： android, ios 多个请使用逗号分隔
		 */
		public static String PLATFORM = "platform";
		/**
		 * 可选
		 */
		public static String APNS_PRODUCTION = "apns_production";
		/**
		 * 可选
		 */
		public static String TIME_TO_LIVE = "time_to_live";
		/**
		 * 可选
		 */
		public static String OVERRIDE_MSG_ID = "override_msg_id";
	}
	
	/**
	 * 活动常量
	 * @author Lee.J.Eric
	 * @time 2014年4月8日下午4:05:03
	 */
	public static class Act{
		/**
		 * 幻灯片上限
		 */
		public static Integer SLIDE_COUNT = 5;
	}

	/**
	 * 阿里支付
	 * @author Lee.J.Eric
	 * @time 2014年12月19日 下午4:44:10
	 */
	public static class AliPay{
		
		/**
		 * alipay网关-wap端
		 */
		public static String ALIPAY_GATEWAY_NEW_WAP = "http://wappaygw.alipay.com/service/rest.htm?";
	
		/**
		 * alipay网关-web端
		 */
		public static String ALIPAY_GATEWAY_NEW_WEB = "https://mapi.alipay.com/gateway.do?";
		
		/**
		 * 支付类型
		 */
		public static String PAYMENT_TYPE = "1";
		
		/**
		 * 防钓鱼时间戳
		 */
		public static String ANTI_PHISHING_KEY = new Timestamp(System.currentTimeMillis()).toString();
		
		/**
		 * 服务-web
		 */
		public static String SERVICE_WEB = "create_direct_pay_by_user";
		
		/**
		 * 服务-wap
		 */
		public static String SERVICE_WAP = "alipay.wap.trade.create.direct";
		
		/**
		 * 合作者
		 */
		public static String PATNER = AlipayConfig.partner;
		
		/**
		 * 字符集
		 */
		public static String INPUT_CHARSET = AlipayConfig.input_charset;
		
		/**
		 * 异步通知接口地址-web
		 */
		public static String NOTIFY_WEB_URL = Common.BASEPATH + "v20/alipay/notify_payment_web";
		
		/**
		 * 异步通知接口地址-wap
		 */
		public static String NOTIFY_WAP_URL = Common.BASEPATH + "v20/alipay/notify_payment_wap";
		
		/**
		 * 同步通知接口地址-web
		 */
		public static String CALLBACK_WEB_URL = "/pages/signSuccess.html";
		
		/**
		 * 同步通知接口地址-wap
		 */
		public static String CALLBACK_WAP_URL = "/pages/signSuccess.html";
		
		/**
		 * 操作中断返回地址
		 */
		public static String MERCHANT_URL = "";
		
		/**
		 * 退款通知接口地址
		 */
		public static String NOTIFY_REFUND = Common.BASEPATH + "v20/alipay/notify_refund";
		
		/**
		 * 客户端的IP地址
		 */
		public static String EXTER_INVOKE_IP = "121.8.145.54:8888";
		
		/**
		 * 卖家支付宝帐户
		 */
		public static String SELLER_EMAIL = "we17_yiqi@163.com";
		
		/**
		 * 返回格式
		 */
		public static String FORMAT = "xml";
		
		/**
		 * 返回版本
		 */
		public static String V = "2.0";
		
		/**
		 * 请求号
		 */
		public static String REQ_ID = UtilDate.getOrderNum();
		
		/**
		 * 签名方式
		 */
		public static String SIGN_TYPE = "MD5";
		
	}
	
	/**
	 * 短信渠道配置
	 * @author kcm
	 *
	 */
	public static class Sms{
		/**
		 * 提交账户
		 */
		public static String SNAME="dlgzyqwl";
		
		/**
		 * 提交账户的密码
		 */
		public static String SPWD="Gd04YQzg";
		
		/**
		 *产品编号  1012888 验证码通道   1012808 群发通道
		 */
		public static String SCOIRPID ="1012888";
		
		/**
		 * 服务器地址
		 */
		public static String SERVICEURL="http://60.28.200.162/submitdata/service.asmx";
		//网页版地址 http://sso.51welink.com/Login?backurl=http%3a%2f%2fweb.51welink.com%2f
		
		
		/**
		 * 每个号每天最多获取的短信条数 
		 */
		public static Integer DAYLIMITSINGLECOUNT=5;
		
		/**
		 * 服务器每天最多发送短信总条数
		 */
		public static Integer DAYLIMITALLCOUNT=500;
		
		/**
		 * 验证码的有效期 单位小时
		 * 
		 */
		public static Integer VALIDATIONHOURS=1; 
		
		/**
		 * 验证码的发送间隔时间 单位分钟
		 */
		public static Integer VALIDATIONINTERVAL=5; 
		
		/**
		 * 单个IP每天最多获取的短信量
		 */
		public static Integer IPLIMITDAYCOUNT=10; 
		
		/**
		 * 短信头部内容
		 */
		public static String SMSHEADCONTENT="验证码为";
		
		/**
		 * 短信尾部内容
		 */
		public static String SMSFOOTCONTENT="(一起开工社区的工作人员绝对不会索取，切勿告知他人)请在页面中输入以完成。【一起开工社区】";
		
		
	}
	
	
}
