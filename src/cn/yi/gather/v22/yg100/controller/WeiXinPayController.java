package cn.yi.gather.v22.yg100.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v22.yg100.entity.YgItemInstance;
import cn.yi.gather.v22.yg100.entity.OrderInfo;
import cn.yi.gather.v22.yg100.entity.WxUser;
import cn.yi.gather.v22.yg100.entity.WxpayLog;
import cn.yi.gather.v22.yg100.service.IWxTicketService;
import cn.yi.gather.v22.yg100.service.IWxUserService;
import cn.yi.gather.v22.yg100.service.IWxpayLogService;
import cn.yi.gather.v22.yg100.service.IYgItemInstanceService;
import cn.yi.gather.v22.yg100.service.IYgOrderService;

import com.common.Jr;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.UserApi;
import com.tools.utils.JSONUtil;
import com.tools.utils.RandomUtil;
import com.weixin.util.GetWxOrderno;
import com.weixin.util.JSRequestHandler;
import com.weixin.util.SHA1;
import com.weixin.util.WXUtil;
import com.weixin.util.WxAccessTokenRequestHandler;
import com.weixin.util.WxConfig;
import com.weixin.util.XMLUtil;

@Controller("weiXinPayController")
@RequestMapping(value="/yg/wxpay")
public class WeiXinPayController {

	@Resource(name="wxTicketService")
	private IWxTicketService ticketService;
	
	@Resource(name="wxUserService")
	private IWxUserService wxUserService;
	
	@Resource(name = "wxpayLogService")
	private IWxpayLogService wxpayLogService;
	
	@Resource(name="ygOrderService")
	private IYgOrderService ygOrderService;
	
	@Resource(name="ygItemInstanceService")
	private IYgItemInstanceService instanceService;
	
	private static Logger yqlog = Logger.getLogger(WeiXinPayController.class);
	
	/**
	 * 1步 微信授权登录 获取 openid 等信息
	 * @param request
	 * @param response
	 * @param code
	 * @param state
	 */
	@RequestMapping(value="wx_login")
	public void wxLogin(HttpServletRequest request,HttpServletResponse response,String code,String state) {
		Jr jr=new Jr();
		jr.setMethod("wx_login");
		if(code==null||code==""){
			jr.setCord(-1);
			jr.setMsg("获取code失败/非法传参");
		}else{
			/*String openid="";
			String unionid;*/
			//if(!code.equals("111111")){
			ApiResult tokenResult = UserApi.getUserToken(code,WxConfig.APPID,WxConfig.APPSECRET);
			Map tokenMap=this.toMap(tokenResult.getJson());
			String userToken = (String)tokenMap.get("access_token");
			String openid = (String)tokenMap.get("openid");
			String unionid=(String)tokenMap.get("unionid");
			/*}else{
				openid="oBMJ0tw3vpLr7WLCwpL_XpVgzdyQ";
			}*/
			if(openid!=null){
				WxUser user=wxUserService.findByOpenId(openid);
				if(user==null&&state.equals("login")){
					ApiResult userInfo = UserApi.getUserInfo(userToken, openid);
					Map userInfoMap = this.toMap(userInfo.getJson());
					user=new WxUser();
					user.setNickname((String)userInfoMap.get("nickname"));
					user.setOpenid(openid);
					user.setHeadimgurl((String)userInfoMap.get("headimgurl"));
					user.setUnionid(unionid);
					user=wxUserService.save(user);
					jr.setCord(0);
					jr.setData(user);
					jr.setMsg("已授权成功");
				}else if(user!=null){
					jr.setCord(0);
					jr.setData(user);
					jr.setMsg("已授权的用户");
				}
			}else{
				jr.setCord(-1);
				jr.setMsg("获取openid失败");
			}
		}
		
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2步 微信绑定手机
	 * @param request
	 * @param response
	 * @param openid
	 * @param tel
	 */
	@RequestMapping(value="wx_bd_tel")
	public void bdTel(HttpServletRequest request,HttpServletResponse response,String openid,String tel) {
		Jr jr=new Jr();
		jr.setMethod("wx_bd_tel");
		if(openid==null||openid==""||tel==null||tel==""){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}else{
			WxUser user=wxUserService.findByOpenId(openid);
			WxUser user2=wxUserService.findByTel(tel);
			if(user!=null&&user2==null){
				user.setTel(tel);
				user=wxUserService.save(user);
				jr.setCord(0);
				jr.setData(user);
				jr.setMsg("绑定手机成功");
			}else{
				jr.setCord(-1);
				jr.setMsg("不存在该授权用户/手机号已绑定其他微信");
			}
		}
		
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 微信登录 调用SDK 后对json 转化处理
	 * @param jsonString
	 * @return
	 * @throws JSONException
	 */
	public static Map toMap(String jsonString) throws JSONException {
		JSONObject json = null; 
		try {
			// 将json字符串转成json对象
			json = JSONObject.fromObject(jsonString);
		} catch (Exception e) {
			throw new JSONException("string转化json对象异常", e);
		}
		Iterator iter = json.keySet().iterator();
		Map<String, String> map = new HashMap<String, String>();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = json.getString(key);
			map.put(key, value);
		}
		return map;
	}
	
	
	/**
	 * 4步 支付方式选择 微信   微信 统一预下订单
	 * @param request
	 * @param response
	 * @param openid
	 * @param orderid
	 * @param vcode 验证码
	 */
	@RequestMapping(value = "wx_unifiedorder")
	public void wxUnifiedorder(HttpServletRequest request,HttpServletResponse response,String openid,String orderid,String vcode){
		Jr jr=new Jr();
		jr.setMethod("wx_unifiedorder");
		try {
			OrderInfo order=ygOrderService.findById(orderid);
			if(new Date().after(order.getCanceltime())){
				order.setOrderstatus("cancel");
				order.setMemo(order.getMemo()+ "验证码过期订单销毁");
				ygOrderService.saveOrUpdate(order);
			}
			if(order!=null&&order.getOrdervcode().equals(vcode)&&"submit".equals(order.getOrderstatus())){
				Double money=order.getOrderprice();
				/*order.setOpenid(openid);
				WxUser wxUser=wxUserService.findByOpenId(openid);
				if(wxUser!=null){
					order.setTel(wxUser.getTel());
				}*/
			// 随机数
			String nonce_str = WXUtil.getNonceStr();
			// 订单生成的机器 IP
			String spbill_create_ip = request.getRemoteAddr();
			//微信JS 支付成功后回调方法 用来处理 一起 的后续处理
			String notify_url = WxConfig.JS_NOTIFY_URL;
			//支付方式
			String trade_type = "JSAPI";
			//需要带回给 回调方法处理的参数
			String body="支付测试";
			// 金额转化为分为单位，不带小数点
			String finalmoney = String.format("%.2f", money);
			finalmoney = finalmoney.replace(".", "");
			int intMoney = Integer.parseInt(finalmoney);
			//测试阶段用1分钱
			intMoney=1;
			//打包参数
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", WxConfig.APPID);
			packageParams.put("mch_id", WxConfig.MCHID);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body", body);
			packageParams.put("out_trade_no", orderid);
			packageParams.put("total_fee", String.valueOf(intMoney));
			packageParams.put("spbill_create_ip", spbill_create_ip);
			packageParams.put("notify_url", notify_url);
			packageParams.put("trade_type", trade_type);
			packageParams.put("openid", openid);
			//初始化
			JSRequestHandler reqHandler = new JSRequestHandler(request, response);
			reqHandler.init(WxConfig.APPID, WxConfig.APPSECRET, WxConfig.MCHID_KEY);
			//打包数据签名一次
			String sign = reqHandler.createSign(packageParams);
			packageParams.put("sign", sign);
			String xml = "<xml>" + "<appid>"
					+ WxConfig.APPID
					+ "</appid>"
					+ "<mch_id>"
					+ WxConfig.MCHID
					+ "</mch_id>"
					+ "<nonce_str>"
					+ nonce_str
					+ "</nonce_str>"
					+ "<sign><![CDATA["
					+ sign
					+ "]]></sign>"
					+ "<body><![CDATA["
					+ body
					+ "]]></body>"
					+
					"<out_trade_no>"
					+ orderid
					+ "</out_trade_no>"
					+
					"<total_fee>" + intMoney + "</total_fee>"
					+ "<spbill_create_ip>" + spbill_create_ip
					+ "</spbill_create_ip>" + "<notify_url>" + notify_url
					+ "</notify_url>" +"<trade_type>" + trade_type
					+ "</trade_type>"
					+ "<openid>" + openid + "</openid>"
					+ "</xml>";
			String allParameters = "";
			allParameters = reqHandler.genPackage(packageParams);
			String prepay_id = "";
			prepay_id = new GetWxOrderno().sendPrepay(WxConfig.WX_UNIFIEDORDER_URL, xml);
			if (prepay_id==null||"".equals(prepay_id)) {
				jr.setCord(-1);
				jr.setMsg("统一支付接口获取预支付订单出错");
			}
			SortedMap<String, String> finalpackage = new TreeMap<String, String>();
			String appid2 = WxConfig.APPID;
			String timestamp = WXUtil.getTimeStamp();
			String prepay_id2 = "prepay_id=" + prepay_id;
			String packages = prepay_id2;
			finalpackage.put("appId", appid2);
			finalpackage.put("timeStamp", timestamp);
			finalpackage.put("nonceStr", nonce_str);
			finalpackage.put("package", packages);
			finalpackage.put("signType", "MD5");
			String finalsign = reqHandler.createSign(finalpackage);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("appid", WxConfig.APPID);
			map.put("timestamp", timestamp);
			map.put("nonceStr", nonce_str);
			map.put("package", finalpackage);
			map.put("sign", finalsign);
			order.setTimestamp(timestamp);
			order.setNonce_str(nonce_str);
			order.setFinalpackage(packages);
			order.setFinalsign(finalsign);
			ygOrderService.saveOrUpdate(order);
			jr.setData(map);
			jr.setCord(0);
			jr.setMsg("微信统一支付预下订单成功");
			}else{
				jr.setCord(-1);
				jr.setMsg("订单不存在/验证码错误/订单已过期请重新下单");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jr.setCord(-1);
			jr.setMsg("服务器异常");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 3步  JS 支付 准备
	 * @param request
	 * @param response
	 * @param url
	 */
	@RequestMapping(value = "wxjs_sha1")
	public void wxJsSha1(HttpServletRequest request,HttpServletResponse response,String url){
			Jr jr=new Jr();
			jr.setMethod("wxjs_sha1");
		String accesstoken= WxAccessTokenRequestHandler.getAccessToken();
		String jsapi_ticket=WxAccessTokenRequestHandler.getTicket(accesstoken);
		String noncestr=WXUtil.getNonceStr(); //WXUtil.getNonceStr();
		String timestamp=WXUtil.getTimeStamp(); //WXUtil.getTimeStamp();
		String str="jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		String signature=new SHA1().getDigestOfString(str.getBytes());
		signature=signature.toLowerCase();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("accesstoken",accesstoken );
		map.put("jsapi_ticket",jsapi_ticket );
		map.put("noncestr",noncestr );
		map.put("timestamp",timestamp );
		map.put("signature",signature );
		map.put("url",url );
		jr.setData(map);
		jr.setCord(0);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 5步  微信支付成功  微信回调方法 处理 业务逻辑
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "wxjsnotify_url")
	public void weiXinJsNotifyUrl(HttpServletRequest request,HttpServletResponse response){
		try{
			yqlog.warn(String.format("wxjsnotify_url 开始"));
			Map<Object, Object> paramMap = new HashMap<Object, Object>();
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
			String line = null;
	        StringBuilder sb = new StringBuilder();
	        while((line = br.readLine())!=null){
	            sb.append(line);
	        }        
	        TreeMap<String, String> resultMap = XMLUtil.doXMLParseForTreeMap(sb.toString());
        	//获取微信appid 判断是不是我们自己的微信回调
	        String appid = resultMap.get("appid");
	        yqlog.warn(String.format("wxjsnotify_url resultMap："+resultMap));
	        if(appid.equals(WxConfig.APPID)){
	        	 yqlog.warn(String.format("wxjsnotify_url <1>"));
	        	//支付成功 处理我们自己的业务
		        if("SUCCESS".equals(resultMap.get("result_code"))){
		        	 yqlog.warn(String.format("wxjsnotify_url <2>"));
		        	//我们的订单号
	        		String my_orderid = resultMap.get("out_trade_no");
	            	//微信的订单号
	    			String wx_orderid = resultMap.get("transaction_id");
	    			//交易金额,以分为单位
	    			String total_money = resultMap.get("total_fee");
	    			//支付完成时间
	    			String pay_time = resultMap.get("time_end");
	    			//那个微信用户支付的 
	    			String openid = resultMap.get("openid");
	    			
	    			//是否关注公众账号  is_subscribe  Y/N
	    			//付款银行 bank_type
	    			//货币类型 fee_type 一般 CNY 人民币
	    			 yqlog.warn(String.format("wxjsnotify_url <3>"));
	    			OrderInfo order=ygOrderService.findById(my_orderid);
	    			if(order.getOrderstatus().equals("sure")){
		    			WxpayLog log=new WxpayLog();
		    			log.setOpenid(openid);
		    			log.setOrderid(my_orderid);
		    			log.setWxorderid(wx_orderid);
		    			log.setMoney(total_money);
		    			log.setCreatetime(pay_time);
		    			yqlog.warn(String.format("wxjsnotify_url <4>"));
		    			log.setTel(order.getTel());
		    			log.setWxnickname(order.getUsername());
		    			log.setIfgzhu(resultMap.get("is_subscribe"));
		    			wxpayLogService.save(log);
		    			 yqlog.warn(String.format("wxjsnotify_url <5>"));
		    			order.setOpenid(openid);
		    			order.setOrderstatus("success");
		    			order.setMemo(order.getMemo()+"，已支付成功");
		    			order.setUpdatetime(new Date());
		    			order=ygOrderService.saveOrUpdate(order);
		    			 yqlog.warn(String.format("wxjsnotify_url <6>"));
		    			//暂时只处理阳光100 购房的
	    				List<YgItemInstance> instances = instanceService.findByOrder(order);
	    				if(instances!=null&&instances.size()>0){
	    					 yqlog.warn(String.format("wxjsnotify_url <7>"));
	    					YgItemInstance i=instances.get(0);
		    				i.setUpdatetime(new Date());
		    				i.setItemstatus("unused");
		    				i.setCouponnumber(RandomUtil.getRandomeStringFornum(10));
		    				i.setMemo(i.getMemo()+"于"+i.getUpdatetime()+"支付成功");
		    				instanceService.saveOrUpdate(i);
	    				}
	    			}
	        	}else{
	        		paramMap.put("result", "-1");
	        		paramMap.put("msg", resultMap.get("err_code_des"));
	        	}
	        }else{
	        	//不属于我们的微信支付
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		yqlog.warn(String.format("wxjsnotify_url 结束"));
	}
	
	
	
	
}
