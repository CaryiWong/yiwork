package com.weixin.util;

import com.weixin.client.WeixinHttpClient;
import com.weixin.util.PrepayIdRequestHandler;
import com.weixin.util.WXUtil;

public class WxAccessTokenRequestHandler {

	private static String access_token = "";
	
	private static String ticket = "";
	
	/**
	 * 获取凭证access_token
	 * @return
	 */
	public static String getAccessToken() {
		if ("".equals(access_token)) {// 如果为空直接获取
			return getTokenReal();
		}

		if (tokenIsExpire(access_token)) {// 如果过期重新获取
			return getTokenReal();
		}
		return access_token;
	}
	
	/**
	 * 获取凭证ticket
	 * @return
	 */
	public static String getTicket(String accesstoken) {
		return getTicketReal(accesstoken);
	}

	/**
	 * 实际获取access_token的方法
	 * @return
	 */
	protected static String getTokenReal() {
		String requestUrl = WxConfig.TOKENURL + "?grant_type=" + WxConfig.GRANT_TYPE + "&appid="
				+ WxConfig.APPID + "&secret=" + WxConfig.APPSECRET;
		String resContent = "";
		WeixinHttpClient httpClient = new WeixinHttpClient();
		httpClient.setMethod("GET");
		httpClient.setReqContent(requestUrl);
		if (httpClient.call()) {
			resContent = httpClient.getResContent();
			if (resContent.indexOf(WxConfig.ACCESS_TOKEN) > 0) {
				access_token = TenpayJsonUtil.getJsonValue(resContent, WxConfig.ACCESS_TOKEN);
			} else {
				System.out.println("获取access_token值返回错误！！！");
			}
		} else {
			System.out.println("后台调用通信失败");
			System.out.println(httpClient.getResponseCode());
			System.out.println(httpClient.getErrInfo());
			// 有可能因为网络原因，请求已经处理，但未收到应答。
		}

		return access_token;
	}
	
	/**
	 * 实际获取ticket的方法
	 * @return
	 */
	protected static String getTicketReal(String accesstoken) {
		String requestUrl = WxConfig.TICKETURL + "?access_token=" + accesstoken + "&type=jsapi";
		String resContent = "";
		WeixinHttpClient httpClient = new WeixinHttpClient();
		httpClient.setMethod("GET");
		httpClient.setReqContent(requestUrl);
		if (httpClient.call()) {
			resContent = httpClient.getResContent();
			if (resContent.indexOf(WxConfig.TICKET) > 0) {
				ticket = TenpayJsonUtil.getJsonValue(resContent, WxConfig.TICKET);
			} else {
				System.out.println("获取TICKET值返回错误！！！");
			}
		} else {
			System.out.println("后台调用通信失败");
			System.out.println(httpClient.getResponseCode());
			System.out.println(httpClient.getErrInfo());
			// 有可能因为网络原因，请求已经处理，但未收到应答。
		}
		return ticket;
	}

	/**
	 * 判断传递过来的参数access_token是否过期
	 * @param access_token
	 * @return
	 */
	private static boolean tokenIsExpire(String access_token) {
		boolean flag = false;
		PrepayIdRequestHandler wxReqHandler = new PrepayIdRequestHandler(null, null);
		wxReqHandler.setParameter("appid", WxConfig.APPID);
		wxReqHandler.setParameter("appkey",WxConfig.APPSECRET);
		wxReqHandler.setParameter("noncestr", WXUtil.getNonceStr());
		wxReqHandler.setParameter("package", WxConfig.packageValue);
		wxReqHandler.setParameter("timestamp", WXUtil.getTimeStamp());

		// 生成支付签名
		String sign = wxReqHandler.createSHA1Sign();
		wxReqHandler.setParameter("app_signature", sign);
		wxReqHandler.setParameter("sign_method", WxConfig.SIGN_METHOD);
		String gateUrl = WxConfig.GATEURL + access_token;
		wxReqHandler.setGateUrl(gateUrl);

		// 发送请求
		String accesstoken = wxReqHandler.sendAccessToken();
		if (WxConfig.EXPIRE_ERRCODE.equals(accesstoken) || WxConfig.FAIL_ERRCODE.equals(accesstoken))
			flag = true;
		return flag;
	}
}
