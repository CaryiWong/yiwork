package cn.yi.gather.v11.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v11.service.IJPushServiceV2;

import com.common.Jr;
import com.tools.utils.JSONUtil;

@Controller("jPushControllerV2")
@RequestMapping(value = "v2/jpush")
public class JPushControllerV2 {
	
	@Resource(name = "jPushServiceV2")
	private IJPushServiceV2 pushServiceV2;
	
	/**
	 * 
	 * @param response
	 * @param time
	 * @param tag
	 * @param type
	 * Lee.J.Eric
	 * 2014年6月19日 下午5:50:19
	 */
	@RequestMapping(value="countnewpush")
	public void countNewPush(HttpServletResponse response,String time,String tag,Integer type){
		Jr jr = new Jr();
		jr.setMethod("countnewpush");
		if(time==null||tag==null){
			jr.setCord(-1);//非法传参
		}else {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date t = new Date();
			try {
				t = format.parse(time);
			} catch (Exception e) {
				// TODO: handle exception
			}
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("activity", pushServiceV2.countPushlogByTagTimeType(t, tag, 0).intValue());
			map.put("resource", pushServiceV2.countPushlogByTagTimeType(t, tag, 1).intValue());
			map.put("system", pushServiceV2.countPushlogByTagTimeType(t, tag, 2).intValue());
			jr.setCord(0);
			jr.setData(map);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
