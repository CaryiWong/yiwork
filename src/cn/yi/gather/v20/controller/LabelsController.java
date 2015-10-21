package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.service.ILabelsService;

@Controller("labelsControllerV20")
@RequestMapping(value = "v20/labels")
public class LabelsController {

	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
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
			List<Labels> labels = labelsService.getLabelsByType(0);
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
	
	/** 2.0
	 * 获取所有的标签列表
	 * @param response
	 * @param type  ios   android   web
	 * @param page
	 * @param size
	 * 2014-09-15
	 */
	@RequestMapping(value="getalllabel")
	public void getAllLabel(HttpServletResponse response,String type,Integer page, Integer size){
		Jr jr = new Jr();
		jr.setMethod("getalllabel");
		try {
//			Map<String, List<Labels>> map = new HashMap<String, List<Labels>>();
			Page<Labels> list = labelsService.getLabels(page, size, -1);
//			List<Labels> form = labelsService.getLabelsByType(1);
//			List<Labels> group = labelsService.getLabelsByType(2);
//			List<Labels> make = labelsService.getLabelsByType(3);
//			List<Labels> find = labelsService.getLabelsByType(4);
//			map.put("area", area);
//			map.put("form", form);
//			map.put("group", group);
//			map.put("make", make);
//			map.put("find", find);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
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
	
	/**
	 * 获取标签列表
	 * @param response
	 * @param type
	 * @param labletype job:职业，favourite:爱好,focus:关注,null:全部
	 * @param pid 父级id，0：只查父级，null:全部，给定值：查询指定值的子级
	 * @param page
	 * @param size
	 * @author Lee.J.Eric
	 * @time 2014年12月2日 下午2:22:57
	 */
	@RequestMapping(value = "lablelist")
	public void lableList(HttpServletResponse response,String type,String labletype,Long pid,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("lablelist");
		try {
			Page<Labels> list = labelsService.labelList(labletype, pid, page, size);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
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
