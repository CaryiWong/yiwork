package cn.yi.gather.v20.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.service.ICityService;

@Controller("cityController")
@RequestMapping(value="v20/city")
public class CityController {

	@Resource(name="cityService")
	private ICityService cityService;
	
	/**
	 * 获取 城市列表（动态 新增活动 等使用）
	 * @param response
	 * @param type
	 */
	@RequestMapping(value="getallcity")
	public void findAllCity(HttpServletResponse response,String type){
		Jr jr = new Jr();
		jr.setMethod("getallcity");
		try {
			jr.setData(cityService.findAll());
			jr.setCord(0);
			jr.setMsg("获取成功");
		} catch (Exception e) {
			jr.setCord(1);
			jr.setMsg("获取失败");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
