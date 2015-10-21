package com.weixin.util;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.weixin.client.WeixinHttpClient;


public class GetWxOrderno
{
  /**
   *description:获取预支付id
   *@param url
   *@param xmlParam
   *@return
   * @author duanbin
   * @see   
  */
	// 提交预支付
	public String sendPrepay(String requestUrl,String xmlParam) throws JDOMException, IOException {
		String prepayid = "";
		WeixinHttpClient httpClient = new WeixinHttpClient();
		httpClient.setReqContent(requestUrl);
		String resContent = "";
		
		if (httpClient.callHttpPost(requestUrl, xmlParam)) {
			resContent = httpClient.getResContent();
			prepayid = TenpayJsonUtil.getJsonValue(resContent, "prepay_id");
//			System.out.println("--resContent = " + resContent);
			Map data = XMLUtil.doXMLParse(resContent);
			if(data.get("prepay_id")!=null&&!"".equals(data.get("prepay_id"))){
				prepayid = data.get("prepay_id").toString();
			}
		}
		return prepayid;
	}
  
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
  public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
  
}