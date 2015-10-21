/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.jfinal.weixin.sdk.api;

import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.kit.ParaMap;

/**
 * 用户管理 API
 * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
 */
public class UserApi {
	
	private static String getUserInfo = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN";
	//private static String getFollowers = "https://api.weixin.qq.com/cgi-bin/user/get";
	private static String getUserCode = "https://open.weixin.qq.com/connect/oauth2/authorize";  //微信登录获取CODE地址
	private static String redirect_uri ="test-jumpingsale.yi-gather.com/web/test";   //微信登录回调地址
	 
	private static String getUserToken="https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
	private static String getAccessToken="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";  //获取access_token
	private static String getjsapiTiket="https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi";//获取 jsapit
	
	/**
	 * 
	 * @param openId
	 * @return
	 */
	public static ApiResult getUserInfo(String openId) {
		ParaMap pm = ParaMap.create("access_token", AccessTokenApi.getAccessToken().getAccessToken()).put("openid", openId).put("lang", "zh_CN");
		return new ApiResult(HttpKit.get(getUserInfo, pm.getData()));
	}
	
	 
 
/*	*//**
	 * 获取用户信息
	 *//*
	public static ApiResult getUser() {
		String jsonResult = HttpKit.get(getUser + AccessTokenApi.getAccessToken().getAccessToken());
		return new ApiResult(jsonResult);
	}*/
	
	/**
	 * 获取用户token
	 */
	public static ApiResult getUserToken(String code,String appid,String secret) {
		String url=getUserToken+"&code="+code+"&appid="+appid+"&secret="+secret;
		//System.out.println("getUserToken="+getUserToken);
		String jsonResult = HttpKit.get(url);
		System.out.println(url);
		System.out.println(jsonResult);
		return new ApiResult(jsonResult);
	}
	
	
	/**
	 * 获取用户token
	 */
	public static ApiResult getUserInfo(String access_token,String openid) {
		String url=getUserInfo+"&access_token="+access_token+"&openid="+openid;
	 
		System.out.println(url);
		String jsonResult = HttpKit.get(url);
		System.out.println(jsonResult);
		return new ApiResult(jsonResult);
	}
	
	/**
	 * 获取APP AccessToken
	 */
	public static ApiResult getAccessToken(String appid,String secret) {
		String url=getAccessToken+"&appid="+appid+"&secret="+secret;
	 
		//System.out.println(url);
		String jsonResult = HttpKit.get(url);
		//System.out.println(jsonResult);
		return new ApiResult(jsonResult);
	}
	
	/**
	 * 获取jsapi_tiket
	 */
	public static ApiResult getjsapiTiket(String access_token) {
		String url=getjsapiTiket+"&access_token="+access_token;
		//System.out.println(url);
		String jsonResult = HttpKit.get(url);
		//System.out.println(jsonResult);
		return new ApiResult(jsonResult);
	}
	
}
