/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.sdk.api;

import java.io.IOException;

import java.util.Map;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.kit.ParaMap;

/**
 * 认证并获取 access_token API
 * http://mp.weixin.qq.com/wiki/index.php?title=%E8%8E%B7%E5%8F%96access_token
 */
public class AccessTokenApi {
	
	// "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	private static String codeurl="https://open.weixin.qq.com/connect/oauth2/authorize?response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	
	private static AccessToken accessToken;
	
	public static AccessToken getAccessToken() {
		if (accessToken != null && accessToken.isAvailable())
			return accessToken;
		
		refreshAccessToken();
		return accessToken;
	}
	
	public static void refreshAccessToken() {
		accessToken = requestAccessToken();
	}
	
	private static synchronized AccessToken requestAccessToken() {
		AccessToken result = null;
		ApiConfig ac = ApiConfigKit.getApiConfig();
		for (int i=0; i<3; i++) {
			String appId = ac.getAppId();
			String appSecret = ac.getAppSecret();
			Map<String, String> queryParas = ParaMap.create("appid", appId).put("secret", appSecret).getData();
			String json = HttpKit.get(url, queryParas);
			result = new AccessToken(json);
			
			if (result.isAvailable())
				break;
		}
		return result;
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ApiConfig ac = new ApiConfig();
		ac.setAppId("wxa7f35f92e66817e5");
		ac.setAppSecret("71b9a8cced773dc160b418529ee5c664");
		ApiConfigKit.setThreadLocalApiConfig(ac);
	/*	
		UserApi us= new UserApi();
		ApiResult result=us.getUserToken("0415f87b11c86868c1cdbd9349020e6Y","wxa7f35f92e66817e5","71b9a8cced773dc160b418529ee5c664");
		System.out.println(result);
		*/
		
		/*ApiResult result=us.getUserInfo("OezXcEiiBSKSxW0eoylIeBK0HgIRMZw9NiIkyIf_kaeAKRrLVxh0os7iBV37ecYtU6Cw-w3C3lLwT3sqXBXiORNI7m39QpV38w-UJ8fjOD15EKsR_LKbKD6OxM05SbXJ21I-1pj8P2759ZZxoO5-kQ","oBMJ0t1D0_PWcFHmCJkCcFXIPQEM");
		System.out.println(result);
		*/
		 
		AccessToken at = getAccessToken();
		if (at.isAvailable())
			System.out.println("access_token : " + at.getAccessToken());
		else
			System.out.println(at.getErrorCode() + " : " + at.getErrorMsg());
	}
}
