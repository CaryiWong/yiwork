package cn.yi.gather.v20.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
import cn.yi.gather.v20.entity.Co_working_space;
import cn.yi.gather.v20.entity.Enterprise;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.Reviewcontent;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.entity.Tribe_partner;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.Visit;
import cn.yi.gather.v20.service.IActivityJoinService;
import cn.yi.gather.v20.service.IActivityService;
import cn.yi.gather.v20.service.ICo_working_spaceService;
import cn.yi.gather.v20.service.IEnterpriseService;
import cn.yi.gather.v20.service.IItemInstanceService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.ILittleActivityService;
import cn.yi.gather.v20.service.IReviewcontentService;
import cn.yi.gather.v20.service.ITribeService;
import cn.yi.gather.v20.service.ITribe_partnerService;
import cn.yi.gather.v20.service.IVisitService;

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
@Controller("AdminCrestControllerV20")
@RequestMapping(value = "v20/admin/crest")
public class AdminCrestController {
		
	@Resource (name="tribeServiceV20")
	private ITribeService tribeService;
	
	@Resource (name="enterpriseServiceV20")
	private IEnterpriseService enterpriseService;
	
	@Resource (name="co_working_spaceServiceV20")
	private ICo_working_spaceService co_working_spaceService;
	
	@Resource (name="tribe_partnerServiceServiceV20")
	private ITribe_partnerService tribe_partnerService;
	
	@Resource (name="visitServiceV20")
	private IVisitService visitService;
	 
	
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
	@RequestMapping(value="crestlist")
	public ModelAndView crestList(ModelMap modelMap,Page page,String ischeck) throws ParseException{
		//type tribe、enterprise、co_working_space、tribe_partner、visit
	    if(ischeck==null){
	    	ischeck="Tribe";
	    }
	   
	    if(ischeck.equals("Tribe")){
	    	Page<Tribe> pages=tribeService.tribelist(page.getCurrentPage(), page.getPageSize());
			modelMap.put("page", pages);
			modelMap.put("ischeck", ischeck);
	    }else if(ischeck.equals("Enterprise")){
	    	Page<Enterprise> pages=enterpriseService.enterpriselist(page.getCurrentPage(), page.getPageSize());
			modelMap.put("page", pages);
			modelMap.put("ischeck", ischeck);
	    }else if(ischeck.equals("Co_working_space")){
	    	Page<Co_working_space> pages=co_working_spaceService.co_working_spacelist(page.getCurrentPage(), page.getPageSize());
			modelMap.put("page", pages);
			modelMap.put("ischeck", ischeck);
	    }else if(ischeck.equals("Tribe_partner")){
	    	Page<Tribe_partner> pages=tribe_partnerService.tribe_partnerlist(page.getCurrentPage(), page.getPageSize());
			modelMap.put("page", pages);
			modelMap.put("ischeck", ischeck);
	    }else if(ischeck.equals("Visit")){
	    	Page<Visit> pages=visitService.visitlist(page.getCurrentPage(), page.getPageSize());
	    	 
			modelMap.put("page", pages);
			modelMap.put("ischeck", ischeck);
	    }
	    
		return new ModelAndView("admin/crest/crestlist",modelMap);
	}
	public static void main(String[] args) {
		Class<?> demo=null;
		
		Enterprise en = new Enterprise();
		  try {
	            demo =Class.forName("cn.yi.gather.v20.entity."+"Enterprise");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	      
		  
		  System.out.println("===============本类属性========================");
	        // 取得本类的全部属性
	        Field[] field = demo.getDeclaredFields();
	        System.out.println(field.length);
	        for (int i = 0; i < field.length; i++) {
	            // 权限修饰符
	            int mo = field[i].getModifiers();
	            String priv = Modifier.toString(mo);
	            // 属性类型
	            Class<?> type = field[i].getType();
	          
	            System.out.println(type.getCanonicalName().equals("java.util.Date"));
	            System.out.println(priv + " " + type.getName() + " "
	                    + field[i].getName() + ";");
	        }
	}
}
