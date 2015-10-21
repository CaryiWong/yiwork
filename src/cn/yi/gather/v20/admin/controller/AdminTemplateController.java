package cn.yi.gather.v20.admin.controller;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.yi.gather.v20.entity.AlipayNotificationLog;
import cn.yi.gather.v20.entity.Demands;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.PendingRefund;
import cn.yi.gather.v20.entity.YiGatherAccountLog;
import cn.yi.gather.v20.service.IClasssortService;
import cn.yi.gather.v20.service.IDemandsService;
import cn.yi.gather.v20.service.IItemService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.IYigatherAccountService;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 一起模板控制器
 * 
 */
@Controller("AdminTemplateControllerV20")
@RequestMapping(value = "v20/admin/template")
public class AdminTemplateController {
	private static Logger log = Logger.getLogger(AdminTemplateController.class);
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name = "classsortServiceV20")
	private IClasssortService classsortService;
	
	@Resource(name = "demandsServiceV20")
	private IDemandsService demandsService;
	
	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;
	
	
	/**
	 * 需求列表
	 * @param modelMap
	 * @param page
	 * @param status
	 * @param keyword  搜索关键字
	 * @param groups
	 * @param demandtype  -1全部，0  寻人  1 视频制作  2 发起活动  3 其他  4视频以外的
	 * @param ischeck
	 * @return
	 */
	@RequestMapping(value="demandlist")
	public ModelAndView demandList(ModelMap modelMap,com.common.Page<Demands> page,Integer status,String keyword,String groups,Integer demandtype,Integer ischeck){
		Page<Demands> list = demandsService.getDemandsByAdmin(page.getCurrentPage(),page.getPageSize(),status, demandtype, keyword,groups,ischeck);
		page.setCurrentCount(list.getNumber());
		page.setPageSize(list.getSize());
		page.setResult(list.getContent());
		page.setTotalCount((int)list.getTotalElements());
		List<Labels> labels = labelsService.getLabelsByType(0);
		modelMap.put("page", page);
		modelMap.put("status", status);
		modelMap.put("keyword", keyword);
		modelMap.put("labelList", groups);
		modelMap.put("labels", labels);
		modelMap.put("demandtype", demandtype);
		modelMap.put("ischeck", ischeck);
		return new ModelAndView("admin/demand/demandlist");
	}
	
}
