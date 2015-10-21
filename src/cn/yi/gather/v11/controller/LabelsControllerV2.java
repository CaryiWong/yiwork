package cn.yi.gather.v11.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v11.entity.Labels;
import cn.yi.gather.v11.service.ILabelsServiceV2;

import com.common.Jr;
import com.tools.utils.JSONUtil;

@Controller("labelsControllerV2")
@RequestMapping(value = "v2/labels")
public class LabelsControllerV2 {

	@Resource(name = "labelsServiceV2")
	private ILabelsServiceV2 labelsServicev2;
	
	/**
	 * 获取所有的标签列表
	 * @param response
	 * Lee.J.Eric
	 * 2014-2-26 上午10:59:12
	 */
	@RequestMapping(value="getlabellist")
	public void getLabelList(HttpServletResponse response){
		Jr jr = new Jr();
		jr.setMethod("getlabellist");
		try {
			List<Labels> labels = labelsServicev2.getLabelsByType(0);
			if(labels!=null&&!labels.isEmpty()){
				jr.setData(labels);
				jr.setCord(0);
				jr.setTotal(labels.size());
			}else{
				jr.setCord(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			jr.setCord(1);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新版-获取所有的标签列表
	 * @param response
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年4月22日 下午6:52:00
	 */
	@RequestMapping(value="getalllabelgroup")
	public void getAllLabelGroup(HttpServletResponse response,Integer type){
		Jr jr = new Jr();
		jr.setMethod("getalllabelgroup");
		try {
			Map<String, List<Labels>> map = new HashMap<String, List<Labels>>();
			List<Labels> area = labelsServicev2.getLabelsByType(0);
			List<Labels> form = labelsServicev2.getLabelsByType(1);
			List<Labels> group = labelsServicev2.getLabelsByType(2);
			List<Labels> make = labelsServicev2.getLabelsByType(3);
			List<Labels> find = labelsServicev2.getLabelsByType(4);
			map.put("area", area);
			map.put("form", form);
			map.put("group", group);
			map.put("make", make);
			map.put("find", find);
			jr.setCord(0);
			jr.setData(map);
			jr.setMsg("获取成功");
		} catch (Exception e) {
			// TODO: handle exception
			jr.setCord(1);
			jr.setMsg("获取失败");
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
