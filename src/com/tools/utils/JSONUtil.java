package com.tools.utils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 
 * @author LeeJEric
 * @date 2013-11-14 下午5:05:54
 */
public class JSONUtil {

	/**
	 * 以json格式把对象数据打印到客户端
	 * @param jsonData
	 * @param response
	 * @throws IOException
	 */
	public void printJSONtoClient(String jsonData,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8");  
		response.setHeader("Cache-Control","no-cache");
		//response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonData); 
	}
	
	/**
	 * 以json格式把对象数据打印到客户端
	 * @param result
	 * @param data
	 * @param response
	 * @throws IOException
	 */
	public void printJSONtoClient(Map<String, String> result,Map<String, Object> data,Map<String, String> msgMap,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if(result!=null&&!result.isEmpty())
			map.put(result.keySet().iterator().next(), result.get(result.keySet().iterator().next()));
		if(data!=null&&!data.isEmpty())
			map.put(data.keySet().iterator().next(), data.get(data.keySet().iterator().next()));
		if(msgMap!=null&&!msgMap.isEmpty())
			map.put(msgMap.keySet().iterator().next(), msgMap.get(msgMap.keySet().iterator().next()));
		//JsonConfig config = new JsonConfig();
		//config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		//String jsonData = JSONObject.fromObject(map,config).toString();
		String jsonData = JSONObject.fromObject(map).toString();
		printJSONtoClient(jsonData,response);
	}
	
	/**
	 * 以json格式把对象数据打印到客户端(日期时间处理)
	 * @param result
	 * @param data
	 * @param msgMap
	 * @param response
	 * @param datetime 定义日期时间处理
	 * @throws IOException
	 */
	public void printJSONtoClient(Map<String, String> result,Map<String, Object> data,Map<String, String> msgMap,HttpServletResponse response,String datetime) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if(result!=null&&!result.isEmpty())
			map.put(result.keySet().iterator().next(), result.get(result.keySet().iterator().next()));
		if(data!=null&&!data.isEmpty())
			map.put(data.keySet().iterator().next(), data.get(data.keySet().iterator().next()));
		if(msgMap!=null&&!msgMap.isEmpty())
			map.put(msgMap.keySet().iterator().next(), msgMap.get(msgMap.keySet().iterator().next()));
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor(datetime));
		String jsonData = JSONObject.fromObject(map,config).toString();
//		String jsonData = JSONObject.fromObject(map).toString();
		printJSONtoClient(jsonData,response);
	}
	
	/**
	 * 以json格式把对象数据打印到客户端
	 * @param result
	 * @param data
	 * @msg msg
	 * @param response
	 * @throws IOException
	 */
	public void printJSONtoClient(String result,Object data,String msg,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if(result!=null&&!result.isEmpty())
			map.put("result", result);
		if(data!=null)
			map.put("data", data);
		if(msg!=null&&!msg.isEmpty())
			map.put("msg", msg);
		//JsonConfig config = new JsonConfig();
		//config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		//String jsonData = JSONObject.fromObject(map,config).toString();
		String jsonData = JSONObject.fromObject(map).toString();
		printJSONtoClient(jsonData,response);
	}
	
	/**
	 * 以json格式把对象数据打印到客户端（日期时间处理）
	 * @param result
	 * @param data
	 * @param msg
	 * @param response
	 * @param datetime 定义日期时间格式
	 * @throws IOException
	 */
	public void printJSONtoClient(String result,Object data,String msg,HttpServletResponse response,String datetime) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if(result!=null&&!result.isEmpty())
			map.put("result", result);
		if(data!=null)
			map.put("data", data);
		if(msg!=null&&!msg.isEmpty())
			map.put("msg", msg);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor(datetime));
		String jsonData = JSONObject.fromObject(map,config).toString();
//		String jsonData = JSONObject.fromObject(map).toString();
		printJSONtoClient(jsonData,response);
	}
	
	/**
	 * 以json格式把对象数据打印到客户端
	 * @param data
	 * @param response
	 * @throws IOException
	 * Lee.J.Eric
	 * 2014-2-25 上午10:26:29
	 */
	public void printJSONtoClient(Object data,HttpServletResponse response)throws IOException {
//		JsonConfig config = new JsonConfig();
//		config.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
//		String jsonData = JSONObject.fromObject(data, config).toString();
		String jsonData = JSONObject.fromObject(data).toString();
		printJSONtoClient(jsonData, response);
	}
	
	/**
	 * 以json格式把对象数据打印到客户端(时间格式处理)
	 * @param data
	 * @param response
	 * @param datetime 定义日期时间格式
	 * @throws IOException
	 */
	public void printJSONtoClient(Object data,HttpServletResponse response,String datetime)throws IOException {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor(datetime));
		String jsonData = JSONObject.fromObject(data, config).toString();
//		String jsonData = JSONObject.fromObject(data).toString();
		printJSONtoClient(jsonData, response);
	}

}
