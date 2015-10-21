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
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.Reviewcontent;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.IActivityJoinService;
import cn.yi.gather.v20.service.IActivityService;
import cn.yi.gather.v20.service.IItemInstanceService;
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
 * 后台-优惠卷消卷管理
 * @author Lee.J.Eric
 * @time 2014年6月13日下午12:05:01
 */
@Controller("AdminCouponControllerV20")
@RequestMapping(value = "v20/admin/coupon")
public class AdminCouponController {
		
	@Resource(name = "itemInstanceServiceV20")
	private IItemInstanceService itemInstanceServiceV20;
	
	 
	
	/**
	 * 跳转
	 */
	@RequestMapping(value="getcouponpage")
	public ModelAndView getCouponPage(HttpServletResponse response,Integer type,String activityid,Page<ActivityJoin> page,ModelMap modelMap,String keyword){
		 
		return new ModelAndView("admin/coupon/activate");
	}
	
	/**
	 * 消费优惠卷
	 * @param response
	 * @param type
	 * @param activityid
	 * @param page
	 * @param modelMap
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value="usecoupon")
	public ModelAndView useCoupon(HttpServletRequest req,HttpServletResponse response,ModelMap modelMap,String coffeenum){
		int result=itemInstanceServiceV20.usecoffee(coffeenum);
		String contextURL = req.getContextPath(); //上下文地址
		String tips="使用优惠卷失败";
		if(result==0)
			tips="使用优惠卷成功";
		
		modelMap.put("tips", tips+"券号: "+coffeenum);
		return new ModelAndView("admin/coupon/activate");
	}
	
	/**
	 * 优惠卷列表
	 * @param response
	 * @param type
	 * @param activityid
	 * @param page
	 * @param modelMap
	 * @param keyword
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="couponlist")
	public ModelAndView couponList(ModelMap modelMap,Page<User> page,String keyword,String start_date,String end_date) throws ParseException{
		  Date start_date1 = null;
		  Date end_date1 = null;
		if(StringUtils.isNotBlank(start_date) && StringUtils.isNotBlank(end_date)){
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		      start_date1 = sdf.parse(start_date);  
		      end_date1 = sdf.parse(end_date);  
	   }
	    
		Page<ItemInstance> pages=itemInstanceServiceV20.couponlist(keyword,start_date1,end_date1, page.getCurrentPage(), page.getPageSize());
		modelMap.put("page", pages);
		modelMap.put("keyword", keyword);
		modelMap.put("start_date", start_date);
		modelMap.put("end_date", end_date);
		return new ModelAndView("admin/coupon/couponlist",modelMap);
	}
}
