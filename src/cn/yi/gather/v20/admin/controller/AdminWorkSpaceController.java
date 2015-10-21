package cn.yi.gather.v20.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.ActivityJoin;
import cn.yi.gather.v20.entity.Article;
import cn.yi.gather.v20.entity.City;
import cn.yi.gather.v20.entity.Eventlog;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.Reviewcontent;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.WorkSpaceInfo;
import cn.yi.gather.v20.service.IActivityJoinService;
import cn.yi.gather.v20.service.IActivityService;
import cn.yi.gather.v20.service.ICityService;
import cn.yi.gather.v20.service.IItemInstanceService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.ILittleActivityService;
import cn.yi.gather.v20.service.IReviewcontentService;
import cn.yi.gather.v20.service.IWorkSpaceInfoService;

import com.common.CreateHtml;
import com.common.Jr;
import com.common.Page;
import com.common.R;
import com.tools.utils.JSONUtil;

/**
 * 后台-多地点--空间管理
 * @author  
 * @time  
 */
@Controller("AdminWorkSpaceControllerV20")
@RequestMapping(value = "v20/admin/workspace")
public class AdminWorkSpaceController {
		
	@Resource(name = "workSpaceInfoService")
	private IWorkSpaceInfoService workSpaceInfoServiceV20;
	
	@Resource(name = "cityService")
	private ICityService cityServiceV20;
	
	/**
	 * 跳转
	 */
	@RequestMapping(value="getcouponpage")
	public ModelAndView getCouponPage(HttpServletResponse response,Integer type,String activityid,Page<ActivityJoin> page,ModelMap modelMap,String keyword){
		return new ModelAndView("admin/coupon/activate");
	}
	
	 
	
	/**
	 * 查看多地点列表
	 */
	@RequestMapping(value="spacelist")
	public ModelAndView spaceList(ModelMap modelMap,Page<User> page,String status,String keyword) throws ParseException{
		
		Page<WorkSpaceInfo> pages=workSpaceInfoServiceV20.workspaceList(page.getCurrentPage(), page.getPageSize(), 0, keyword);
		modelMap.put("page", pages);
		modelMap.put("keyword", keyword);
		modelMap.put("status", status);
		return new ModelAndView("admin/workspace/workspacelist",modelMap);
	}
	

	/**
	 * 跳到新增空间页面
	 */
	@RequestMapping(value="getaddspace")
	public ModelAndView addSpace(ModelMap modelMap){
		List<City> cityList= cityServiceV20.findAll();
		modelMap.put("cityList", cityList);
		return new ModelAndView("admin/workspace/addworkspace");
	}
	
	/**
	 * 保存新增空间
	 * @param request
	 * @param response
	 * @param article
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="createworkspace")
	public ModelAndView createWorkSpace(HttpServletRequest request,HttpServletResponse response,WorkSpaceInfo workspace,ModelMap modelMap){
		Jr jr = new Jr();
		jr.setMethod("createworkspace");
		if(workspace==null ||response==null){
			modelMap.put("tips", "非法传参");
			return new ModelAndView("admin/workspace/addworkspace");
		}
		
		WorkSpaceInfo space=workSpaceInfoServiceV20.saveOrUpdate(workspace);
		modelMap.put("tips", "新建空间成功");
		
		return new ModelAndView("admin/workspace/addworkspace");
	}
}
