package cn.yi.gather.v20.admin.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.yi.gather.v20.entity.YqServiceInfo;
import cn.yi.gather.v20.service.IYqServiceService;


@Controller("adminYqServiceController")
@RequestMapping(value = "v20/admin/yqservice")
public class AdminYqServiceController {

	@Resource(name="yqServiceService")
	private IYqServiceService serviceService;
	
	@RequestMapping(value="addyqservice")
	public ModelAndView addYqService(HttpServletRequest request,ModelMap modelMap){
		return new ModelAndView("admin/yqservice/addservice");
	}
	
	@RequestMapping(value="saveyqservice")
	public ModelAndView saveYqService(HttpServletResponse response,ModelMap modelMap,YqServiceInfo serviceInfo){
		try {
			serviceService.save(serviceInfo);
			modelMap.put("serviceInfo", serviceInfo);
			modelMap.put("tips", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("admin/yqservice/addservice",modelMap);
	}
}
