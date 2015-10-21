package com.weixin.util;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;


public class TenpayJsonUtil {

	public static String getJsonValue(String rescontent, String key) {
		/*JSONObject jsonObject;
		String v = null;
		try {
			jsonObject = new JSONObject(rescontent);
			v = jsonObject.getString(key);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return v;*/
		
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, Boolean.TRUE);
			@SuppressWarnings("unchecked")
			Map<String, Object> map = mapper.readValue(rescontent, Map.class);
			Object value = map.get(key);
			if(value != null) {
				return value.toString();
			}
		} catch (IOException e) {
			return null;
		}
		return null;
	}
}
