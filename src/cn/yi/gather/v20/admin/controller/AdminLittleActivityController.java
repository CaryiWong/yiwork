package cn.yi.gather.v20.admin.controller;

import java.io.IOException;
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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.ActivityJoin;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.Reviewcontent;
import cn.yi.gather.v20.service.IActivityJoinService;
import cn.yi.gather.v20.service.IActivityService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.ILittleActivityService;
import cn.yi.gather.v20.service.IReviewcontentService;

import com.common.CreateHtml;
import com.common.Jr;
import com.common.Page;
import com.common.R;
import com.tools.utils.JSONUtil;

/**
 * 后台-小活动控制器
 * @author Lee.J.Eric
 * @time 2014年6月13日下午12:05:01
 */
@Controller("AdminLittleActivityControllerV20")
@RequestMapping(value = "v20/admin/littleactivity")
public class AdminLittleActivityController {
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name = "littleActivityServiceV20")
	private ILittleActivityService activityService;
	
	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;
	
	@Resource(name = "reviewcontentServiceV20")
	private IReviewcontentService reviewcontentService;
	
	@Resource(name = "activityJoinServiceV20")
	private IActivityJoinService activityJoinService;
	
	@RequestMapping(value = "activitylist")
	public ModelAndView activitylist(ModelMap modelMap, Page<Gathering> page, Integer cost,
			Integer checktype, Integer status, String keyword, Long[] groups){
		List<Long> labelList = new ArrayList<Long>();
		if (null != groups) {
			labelList = Arrays.asList(groups);
		} else {
			labelList = null;
		}
		//List<Labels> labels = labelsService.getLabelsByType(0);
		page = activityService.getActivityForPage(page.getCurrentPage(), page.getPageSize(), cost, checktype, status, keyword, labelList);
		modelMap.put("labelList", labelList);
		//modelMap.put("labels", labels);
		modelMap.put("page", page);
		modelMap.put("cost", cost);
		modelMap.put("checktype", checktype);
		modelMap.put("status", status);
		modelMap.put("keyword", keyword);
		return new ModelAndView("admin/littleactivity/littleActivitylist");
	}
	
	/**
	 * 跳转到更新活动页面
	 * @param modelMap
	 * @param activityid
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月13日 下午2:44:25
	 */
	@RequestMapping(value = "getlittleupdateactivity")
	public ModelAndView getUpdateActivity(ModelMap modelMap, String activityid){
		Gathering activity = activityService.findById(activityid);
		//List<Labels> labels = labelsService.getLabelsByType(0);
		//List<Long> labelList = new ArrayList<Long>();
		//List<Labels> myLabel = activity.getLabel();
//		for (Labels labels2 : myLabel) {
//			labelList.add(labels2.getId());
//		}
		//modelMap.put("labelList", labelList);
		modelMap.put("activity", activity);
		modelMap.put("labels", null);
		return new ModelAndView("admin/littleactivity/updateactivity");
	}
	
	/**
	 * 更新活动情况
	 * @param modelMap
	 * @param activity
	 * @param groups
	 * @param open
	 * @param end
	 * @return
	 * Lee.J.Eric
	 * 2014年6月13日 下午2:55:03
	 */
	@RequestMapping(value = "updatelittleactivity")
	public ModelAndView updateActivity(ModelMap modelMap, Activity activity, Long[] groups,String open,String end){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
		Gathering original = activityService.findById(activity.getId());
		original.setTitle(activity.getTitle());
		original.setSummary(activity.getSummary());
		original.setAddress(activity.getAddress());
		 
		 
		//original.setCost(activity.getCost());
		//original.setTel(activity.getTel());
		//original.setMaxnum(activity.getMaxnum());
		//original.setPrice(activity.getPrice());
		 
		/* *********************************0630 S*************************************  */	
		//original.setPricetype(activity.getPricetype());
		//original.setPricekey(activity.getPricekey());
		//original.setPricevalue(activity.getPricevalue());
 
		try {//兼容之前的日期格式
			if(open!=null&&!open.isEmpty()){
				original.setOpendate(format.parse(open));
//				original.setOpendate(original.getOpendate1().getTime());
			}
			if(end!=null&&!end.isEmpty()){
				//original.setEnddate(format.parse(end));
//				original.setEnddate(original.getEnddate1().getTime());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		original = activityService.activitySaveOrUpdate(original);
		modelMap.put("tips", "修改成功"); 
		modelMap.put("activity", original);
		return new ModelAndView("admin/littleactivity/updateactivity");
	}
	


	/**
	 * 获取活动的报名列表
	 * @param response
	 * @param type
	 * @param activityid
	 * @param page
	 * @param modelMap
	 * @param keyword
	 * @return
	 * Lee.J.Eric
	 * 2014年6月13日 下午4:53:13
	 */
	@RequestMapping(value="getactsignlist")
	public ModelAndView getActSignList(HttpServletResponse response,Integer type,String activityid,Page<ActivityJoin> page,ModelMap modelMap,String keyword){
		try {
			//Activity activity = activityService.findById(activityid);
			//page = activityJoinService.signList(page.getCurrentPage(), page.getPageSize(), activity, keyword);
			modelMap.put("keyword", keyword);
			modelMap.put("activityid", activityid);
			modelMap.put("page", page);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("admin/activity/signlist");
	}
}
