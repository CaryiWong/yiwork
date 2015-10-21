package cn.yi.gather.v20.admin.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.sql.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.ItemInstanceLog;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.UserAccount;
import cn.yi.gather.v20.entity.UserAccountLog;
import cn.yi.gather.v20.service.IItemService;
import cn.yi.gather.v20.service.IUserAccountService;
import cn.yi.gather.v20.service.IYigatherAccountService;

import com.common.Jr;
import com.common.Page;
import com.common.R;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tools.utils.JSONUtil;

/**
 * 用户账户控制器
 * 
 */
@Controller("adminUserAccountControllerV20")
@RequestMapping(value = "v20/admin/user_account")
public class AdminUserAccountController {
	private static Logger log = Logger.getLogger(AdminUserAccountController.class);
	
	@Resource(name = "itemServiceV20")
	private IItemService itemService;
	
	@Resource(name = "yigatherAccountServiceV20")
	private IYigatherAccountService yigatherAccountService;
	
	@Resource(name = "userAccountServiceV20")
	private IUserAccountService userAccountService;
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource;
	
	/**
	 * 账户余额和物品列表
	 * 
	 * @param modelMap
	 * @param page
	 * @return
	 */
	@RequestMapping(value="info")
	public ModelAndView info(ModelMap modelMap, String user_id){
		modelMap.put("user_id", user_id);
		if (user_id != null) {
			try {
				
				Connection conn = dataSource.getConnection();
				List<UserAccount> account_info_list = userAccountService.getUserAccount(conn, user_id);
				modelMap.put("account_info_list", account_info_list);
				
				List<ItemInstance> item_list = itemService.getUserInventory(conn, user_id);
				modelMap.put("item_list", item_list);
				
				conn.close();
			}
			catch (Exception e) {
				log.warn(
					String.format(
						"failed to get user account info, exception: %s, user_id: %s",
						e.toString(), user_id));
			}
		}
		return new ModelAndView("admin/user_account/info", modelMap);
	}	
	
	/**
	 * 分页查看流水账列表
	 * 
	 * @param modelMap
	 * @param page
	 * @return
	 */
	@RequestMapping(value="view_log")
	public ModelAndView viewLog(ModelMap modelMap, String user_id, Date start_date, Date end_date, Page<UserAccountLog> page){
		modelMap.put("user_id", user_id);
		modelMap.put("start_date", start_date);
		modelMap.put("end_date", end_date);
		if (user_id != null) {
			try {
				Connection conn = dataSource.getConnection();
				Page<UserAccountLog> paged_result = userAccountService.getUserAccountLogForPage(conn, user_id, start_date, end_date, page.getCurrentPage(), page.getPageSize());
				modelMap.put("page", paged_result);
				conn.close();
			}
			catch (Exception e) {
				log.warn(
					String.format(
						"failed to view user account log, exception: %s, user_id=%s",
						e.toString(), user_id));
			}
		}
		return new ModelAndView("admin/user_account/view_log", modelMap);
	}	
	
	/**
	 * 分页查看用户的物品Log
	 * 
	 * @param modelMap
	 * @param page
	 * @return
	 */
	@RequestMapping(value="view_item_log")
	public ModelAndView viewItemLog(ModelMap modelMap, String user_id, Date start_date, Date end_date, Page<ItemInstanceLog> page){
		modelMap.put("user_id", user_id);
		modelMap.put("start_date", start_date);
		modelMap.put("end_date", end_date);
		if (user_id != null) {
			try {
				Connection conn = dataSource.getConnection();
				Page<ItemInstanceLog> paged_result = itemService.getUserItemLog(conn, user_id, start_date, end_date, page.getCurrentPage(), page.getPageSize());
				modelMap.put("page", paged_result);
				conn.close();
			}
			catch (Exception e) {
				log.warn(
					String.format(
						"failed to view user item log, exception: %s, user_id=%s",
						e.toString(), user_id));
			}
		}
		return new ModelAndView("admin/user_account/view_item_log", modelMap);
	}
	
	/**
	 * 冻结账号
	 */
	@RequestMapping(value="freeze_account")
	public void freezeAccount(HttpServletResponse response, String user_id){
		Jr jr = new Jr();
		jr.setMethod("frozen_account");
		try {
			Connection conn = dataSource.getConnection();
			try {
				conn.setAutoCommit(false);
				userAccountService.freezeAccount(conn, user_id);
				conn.commit();
				jr.setCord(0);
			}
			catch (Exception e) {
				conn.rollback();
				throw e;
			}
		} catch (Exception e) {
			jr.setCord(1);
			log.warn(String.format("failed to frozen account for user %s, exception:%s", user_id, e));
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
	 * 解冻账号
	 */
	@RequestMapping(value="unfreeze_account")
	public void unfreezeAccount(HttpServletResponse response, String user_id){
		Jr jr = new Jr();
		jr.setMethod("unfrozen_account");
		try {
			Connection conn = dataSource.getConnection();
			try {
				conn.setAutoCommit(false);
				userAccountService.unfreezeAccount(conn, user_id);
				conn.commit();
				jr.setCord(0);
			}
			catch (Exception e) {
				conn.rollback();
				throw e;
			}
			finally {
				conn.close();
			}
		} catch (Exception e) {
			log.warn(String.format("failed to unfroze account for user %s, exception:%s", user_id, e));
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
	 * 修改账号余额
	 */
	@RequestMapping(value="edit_user_balance")
	public void editUserBalance(HttpServletResponse response, HttpServletRequest request, String user_id, Integer money_type,
			Integer action_type, Double money, String reason){
		User admin = (User)request.getSession().getAttribute(R.User.SESSION_USER);
		Jr jr = new Jr();
		jr.setMethod("edit_user_balance");
		try {
			Connection conn = dataSource.getConnection();
			try {
				conn.setAutoCommit(false);
				userAccountService.editBalance(conn, user_id, money_type, action_type, money, reason, admin);
				conn.commit();
				jr.setCord(0);
			}
			catch (Exception e) {
				conn.rollback();
				throw e;
			}
			finally {
				conn.close();
			}
		} catch (Exception e) {
			log.warn(String.format("failed to edit_user_balance, user %s, exception:%s", user_id, e));
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
}
