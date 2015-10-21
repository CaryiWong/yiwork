package cn.yi.gather.v20.admin.controller;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Calendar;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.yi.gather.v20.entity.AlipayNotificationLog;
import cn.yi.gather.v20.entity.PendingRefund;
import cn.yi.gather.v20.entity.YiGatherAccountLog;
import cn.yi.gather.v20.service.IItemService;
import cn.yi.gather.v20.service.IYigatherAccountService;

import com.common.Page;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 一起账户控制器
 * 
 */
@Controller("adminYiGatherAccountControllerV20")
@RequestMapping(value = "v20/admin/yigather_account")
public class AdminYiGatherAccountController {
	private static Logger log = Logger.getLogger(AdminYiGatherAccountController.class);
	
	@Resource(name = "itemServiceV20")
	private IItemService itemService;
	
	@Resource(name="yigatherAccountServiceV20")
	private IYigatherAccountService accountService;
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource;
	
	/**
	 * 分页查看流水账列表
	 * 
	 * @param modelMap
	 * @param page
	 * @return
	 */
	@RequestMapping(value="view_log")
	public ModelAndView viewLog(ModelMap modelMap, Page<YiGatherAccountLog> page, Date start_date, Date end_date){
		modelMap.put("start_date", start_date);
		modelMap.put("end_date", end_date);
		try {
			Connection conn = dataSource.getConnection();
			
			if (end_date == null) {
				end_date = new Date(System.currentTimeMillis());
			}
			if (start_date == null) {
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(end_date.getTime());
				c.add(Calendar.MONTH, -1);
				start_date = new Date(c.getTimeInMillis());
			}
			Page<YiGatherAccountLog> paged_result = accountService.getYiGatherAccountLogForPage(conn, start_date, end_date, page.getCurrentPage(), page.getPageSize());
			modelMap.put("page", paged_result);
			conn.close();
		}
		catch (Exception e) {
			log.warn(
				String.format(
					"failed to view yigather account log, exception: %s",
					e.toString()));
		}
		return new ModelAndView("admin/yigather_account/view_log", modelMap);
	}	
	
	/**
	 * 分页查看支付宝／网银支付记录
	 * 
	 * @param modelMap
	 * @param page
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	@RequestMapping(value="view_alipay_log")
	public ModelAndView viewRmbPaymentLog(ModelMap modelMap, Page<YiGatherAccountLog> page, Date start_date, Date end_date){
		modelMap.put("start_date", start_date);
		modelMap.put("end_date", end_date);
		try {
			Connection conn = dataSource.getConnection();
			
			if (end_date == null) {
				end_date = new Date(System.currentTimeMillis());
			}
			if (start_date == null) {
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(end_date.getTime());
				c.add(Calendar.MONTH, -1);
				start_date = new Date(c.getTimeInMillis());
			}
			Page<AlipayNotificationLog> paged_result = accountService.getAlipayNotificationLogForPage(conn, start_date, end_date, page.getCurrentPage(), page.getPageSize());
			modelMap.put("page", paged_result);
			conn.close();
		}
		catch (Exception e) {
			log.warn(
				String.format(
					"failed to view yigather account log, exception: %s",
					e.toString()));
		}
		return new ModelAndView("admin/yigather_account/view_alipay_log", modelMap);
	}	
	
	/**
	 * 分页查看待退款列表
	 * 
	 * @param modelMap
	 * @param page
	 * @return
	 */
	@RequestMapping(value="pending_refund")
	public ModelAndView pendingRefund(ModelMap modelMap, Page<PendingRefund> page){
		try {
			Connection conn = dataSource.getConnection();
			Page<PendingRefund> paged_result = accountService.getPendingRefundForPage(conn, page.getCurrentPage(), page.getPageSize());
			modelMap.put("page", paged_result);
			conn.close();
		}
		catch (Exception e) {
			log.warn(
				String.format(
					"failed to get pending refund, exception: %s",
					e.toString()));
		}
		return new ModelAndView("admin/yigather_account/pending_refund", modelMap);
	}	
	
}
