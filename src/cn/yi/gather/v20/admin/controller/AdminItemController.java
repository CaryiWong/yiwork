package cn.yi.gather.v20.admin.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.yi.gather.v20.entity.ItemClass;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.ItemInstanceLog;
import cn.yi.gather.v20.entity.Order;
import cn.yi.gather.v20.entity.Payment;
import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.entity.SkuInventory;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.YigatherItemInventoryLog;
import cn.yi.gather.v20.entity.ItemInstance.ItemInstanceStatus;
import cn.yi.gather.v20.entity.ItemInstanceLog.OpType;
import cn.yi.gather.v20.entity.Order.OrderStatus;
import cn.yi.gather.v20.entity.Order.OrderType;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.service.IItemInstanceLogService;
import cn.yi.gather.v20.service.IItemInstanceService;
import cn.yi.gather.v20.service.IItemService;
import cn.yi.gather.v20.service.IOrderService;
import cn.yi.gather.v20.service.ISkuInventoryService;
import cn.yi.gather.v20.service.ISkuService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IYigatherItemInventoryLogService;

import com.common.Jr;
import com.common.Page;
import com.common.R;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tools.utils.JSONUtil;

/**
 * 后台商品控制器
 * 
 */
@Controller("adminItemControllerV20")
@RequestMapping(value = "v20/admin/item")
public class AdminItemController {
	private static Logger log = Logger.getLogger(AdminItemController.class);
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name="skuServiceV20")
	private ISkuService skuService;
	
	@Resource(name="skuInventoryServiceV20")
	private ISkuInventoryService skuInventoryService;
	
	@Resource(name = "itemInstanceServiceV20")
	private IItemInstanceService itemInstanceService;
	
	@Resource(name ="yigatherItemInventoryLogServiceV20")
	private IYigatherItemInventoryLogService yigatherItemInventoryLogService;
	
	@Resource(name = "itemInstanceLogServiceV20")
	private IItemInstanceLogService itemInstanceLogService;
	
	//--
	
	@Resource(name = "itemServiceV20")
	private IItemService itemService;
	
	@Resource(name = "orderServiceV20")
	private IOrderService orderService;
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource;
	
	/**
	 * 查看商品大类列表
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="item_class_list")
	public ModelAndView itemClassList(ModelMap modelMap){
		List<ItemClass> item_class_list = itemService.getItemClassList();
		modelMap.put("item_class_list", item_class_list);
		return new ModelAndView("admin/item/item_class_list",modelMap);
	}
	
	
	@RequestMapping(value="add_item_class")
	public ModelAndView addItemClass(ModelMap modelMap, String name){
		itemService.addItemClass(name);
		
		List<ItemClass> item_class_list = itemService.getItemClassList();
		modelMap.put("item_class_list", item_class_list);
		return new ModelAndView("admin/item/item_class_list",modelMap);
	}
	
	@RequestMapping(value="modify_item_class")
	public void modifyItemClass(HttpServletResponse response, Long id, String name){
		Jr jr = new Jr();
		jr.setMethod("modify_item_class");
		try {
			int result = itemService.modifyItemClass(id, name);
			jr.setCord(result);
		} catch (Exception e) {
			jr.setCord(1);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="delete_item_class")
	public void deleteItemClass(HttpServletResponse response, Long id){
		Jr jr = new Jr();
		jr.setMethod("delete_item_class");
		try {
			itemService.deleteItemClass(id);
			jr.setCord(0);
		} catch (Exception e) {
			jr.setCord(1);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 分页查看SKU列表
	 * 
	 * @param modelMap
	 * @param page
	 * @param item_class_id
	 * @return
	 */
	@RequestMapping(value="skulist")
	public ModelAndView skuList(ModelMap modelMap,Page<SKU> page, Long item_class_id){
		modelMap.put("selected_item_class_id", item_class_id);
		
		List<ItemClass> item_class_list = itemService.getItemClassList();
		modelMap.put("item_class_list", item_class_list);
		
		page = itemService.getSKUListForPage(page.getCurrentPage(), page.getPageSize(), item_class_id);
		modelMap.put("page", page);
		return new ModelAndView("admin/item/skulist",modelMap);
	}
	
	/**
	 * 分页查看SKU列表  列出所有的sku 赠送给用户
	 * 
	 * @param modelMap
	 * @param page
	 * @param item_class_id
	 * @return
	 */
	@RequestMapping(value="giveuserlist")
	public ModelAndView giveuserlist(ModelMap modelMap,Page<SKU> page, Long item_class_id){
		modelMap.put("selected_item_class_id", item_class_id);
		
		List<ItemClass> item_class_list = itemService.getItemClassList();
		modelMap.put("item_class_list", item_class_list);
		
		page = itemService.getSKUListForPage(page.getCurrentPage(), page.getPageSize(), item_class_id);
		modelMap.put("page", page);
		return new ModelAndView("admin/item/giveuserlist",modelMap);
	}
	
	/**
	 * 保存赠送给用户的商品
	 * 
	 * @param modelMap
	 * @param page
	 * @param item_class_id
	 * @return
	 */
	@RequestMapping(value="saveusergoods")
	public ModelAndView saveusergoods(HttpServletRequest request,ModelMap modelMap,Page<SKU> page,String item_class_id,String userid,Integer cupnum){
		User user= userService.findByUnum(userid);
		User loginu=(User)request.getSession().getAttribute(R.User.SESSION_USER); //登录用户
		
		if(!loginu.getRoot().equals(User.UserRoot.SYS)){
			modelMap.put("tips", "当前无权限操作");
			return new ModelAndView("admin/item/giveuserlist",modelMap);
		}
		
		
		if(user!=null){
			int coffee_num=cupnum;
			List<ItemInstance> instances = new ArrayList<ItemInstance>();
			List<ItemInstanceLog> instanceLogs = new ArrayList<ItemInstanceLog>();
			SKU sku0 = skuService.findById(item_class_id);  //查询SKU
			SkuInventory inventory0 =null;
			if(sku0!=null){
				 inventory0 = skuInventoryService.findById(sku0.getId());
			}
			
			if(inventory0==null){
				page = itemService.getSKUListForPage(page.getCurrentPage(),
						page.getPageSize(), null);
				modelMap.put("page", page);
				modelMap.put("tips", "赠送失败,库存异常");
				return new ModelAndView("admin/item/giveuserlist", modelMap);
			}
			
			if (coffee_num > 0  &&inventory0!=null  && inventory0.getAmount() > coffee_num) {
					for (int j = 0; j < coffee_num; j++) {
						// 商品实例入库 正常咖啡
						ItemInstance instance = new ItemInstance(); // 商品实例
						instance.setStatus(ItemInstanceStatus.UNDELIVERED
								.getCode());
						SKU sku = skuService.findById(item_class_id); // 
						instance.setSku(sku);

						if (sku.getCoffeetype() != "") { // 咖啡的情况 --正常的或者赠送的
							if (sku.getCoffeetype().equals("normal"))
								instance.setEffective(0);// 无时效性
							else
								instance.setEffective(1);// 有时效性
						}
						instance.setUserId(user.getId());
						instance.setReceiveuserId(user.getId());
						instance.setCreateTime(new Timestamp(System
								.currentTimeMillis()));
						instance.setModifyTime(new Timestamp(System
								.currentTimeMillis()));
						instance.setStatus(ItemInstanceStatus.UNUSED.getCode()); // 已付款
																					// 未使用
						instances.add(instance);
						// 商品实例记录
						ItemInstanceLog item_log = new ItemInstanceLog();
						item_log.setItemInstanceId(instance.getId());
						item_log.setOpType(ItemInstanceLog.OpType.CREATE
								.getCode());
						item_log.setSkuId(sku.getId());
						item_log.setUserId(user.getId());
						item_log.setUserNickname(user.getNickname());
						item_log.setOpType(OpType.DELIVER.getCode());

						item_log.setMemo("后台管理员" + loginu.getNickname() + "赠送"
								+ sku.getName() + ",总共" + coffee_num + "份,当前第"
								+ (j + 1) + "份"); // 备注说明 商品来历
						item_log.setDateTime(new Timestamp(System
								.currentTimeMillis()));
						instanceLogs.add(item_log);
					}
				
				page = itemService.getSKUListForPage(page.getCurrentPage(),
						page.getPageSize(), null);
				modelMap.put("page", page);
				modelMap.put("tips", "  赠送成功 [" + sku0.getName() + "] ,共"
						+ coffee_num + "份,接收会员号为" + userid);
				itemInstanceService.saveOrUpdate(instances);
				itemInstanceLogService.saveOrUpdate(instanceLogs);
				}
		
		} else {
			page = itemService.getSKUListForPage(page.getCurrentPage(),
					page.getPageSize(), null);
			modelMap.put("page", page);
			modelMap.put("tips", "赠送失败,会员号= '" + userid + "' 不存在!");
		}

		return new ModelAndView("admin/item/giveuserlist", modelMap);
	}
 
	
	
	@RequestMapping(value="add_sku")
	public void addSku(HttpServletResponse response, Long item_class_id, String sku_name,
			Double default_price, Double member_price, Integer is_unlimited){
		Jr jr = new Jr();
		jr.setMethod("add_sku");
		try {
			Connection conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			itemService.addSku(conn, item_class_id, sku_name, default_price, member_price, is_unlimited);
			conn.commit();
			conn.close();
			jr.setCord(0);
		} catch (Exception e) {
			log.warn("failed to add sku, exception:" + e.toString());
			jr.setCord(1);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="modify_sku")
	public void modifySku(HttpServletResponse response, Long item_class_id, String sku_id, String sku_name,
			Double default_price, Double member_price){
		Jr jr = new Jr();
		jr.setMethod("modify_sku");
		try {
			int result = itemService.modifySku(sku_id, item_class_id, sku_name, default_price, member_price);
			jr.setCord(result);
		} catch (Exception e) {
			log.warn("failed to modify sku, exception:" + e.toString());
			jr.setCord(1);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="delete_sku")
	public void deleteSku(HttpServletResponse response, String sku_id){
		Jr jr = new Jr();
		jr.setMethod("delete_sku");
		try {
			itemService.deleteSku(sku_id);
			jr.setCord(0);
		} catch (Exception e) {
			log.warn("failed to delete_sku, exception:" + e.toString());
			jr.setCord(1);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="put_on_shelves")
	public void putOnShelves(HttpServletResponse response, String sku_id){
		Jr jr = new Jr();
		jr.setMethod("put_on_shelves");
		try {
			int result = itemService.putOnShelves(sku_id);
			jr.setCord(result);
		} catch (Exception e) {
			log.warn("failed to put_on_shelves, exception:" + e.toString());
			jr.setCord(1);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="get_off_shelves")
	public void getOffShelves(HttpServletResponse response, String sku_id){
		Jr jr = new Jr();
		jr.setMethod("get_off_shelves");
		try {
			int result = itemService.getOffShelves(sku_id);
			jr.setCord(result);
		} catch (Exception e) {
			log.warn("failed to get_off_shelves, exception:" + e.toString());
			jr.setCord(1);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查看SKU库存量
	 * 
	 * @param modelMap
	 * @param page
	 * @param sku_id
	 * @return
	 */
	@RequestMapping(value="inventory")
	public ModelAndView skuInventoryList(ModelMap modelMap, Page<YigatherItemInventoryLog> page, String sku_id){
		modelMap.put("sku_id", sku_id);
		
		String result = null;
		try {
			Connection conn = dataSource.getConnection();
			result = itemService.getSkuInventory(conn, sku_id);
			
			// SKU详细数据
			SKU sku = itemService.getSku(sku_id);
			if (sku != null) {
				modelMap.put("sku_name", sku.getName());
				modelMap.put("item_class_id", sku.getItemClass().getName());
				modelMap.put("sku_status", sku.getStatusName());
			}
			
			// 出入库记录
			page = itemService.getYigatherItemInventoryLog(conn, sku_id, page.getCurrentPage(), page.getPageSize());
			modelMap.put("page", page);
			
			conn.close();
			
		} catch (Exception e) {
			log.warn(
				String.format(
					"failed to getSkuInventory, exception: %s, sku_id=%s",
					e.toString(), sku_id));
		} finally {
		    modelMap.put("inventory", result);
		}
		
		return new ModelAndView("admin/item/inventory",modelMap);
	}
	
	@RequestMapping(value="inventory_increase")
	public void inventoryIncrease(HttpServletResponse response, HttpServletRequest request, String sku_id, Integer amount){
		Jr jr = new Jr();
		jr.setMethod("put_on_shelves");
		try {
			User user = (User)request.getSession().getAttribute(R.User.SESSION_USER);
			Connection conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			itemService.InventoryIncrease(conn, sku_id, amount, user);
			conn.commit();
			conn.close();
			jr.setCord(0);
		} catch (Exception e) {
			log.warn("failed to increase inventory, sku_id=" + sku_id + " exception:" + e.toString());
			jr.setCord(1);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="inventory_reduce")
	public void inventoryReduce(HttpServletResponse response, HttpServletRequest request, String sku_id, Integer amount){
		Jr jr = new Jr();
		jr.setMethod("put_on_shelves");
		try {
			User user = (User)request.getSession().getAttribute(R.User.SESSION_USER);
			Connection conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			itemService.InventoryReduce(conn, sku_id, amount, user);
			conn.commit();
			conn.close();
			jr.setCord(0);
		} catch (Exception e) {
			log.warn("failed to reduce inventory, sku_id=" + sku_id + " exception:" + e.toString());
			jr.setCord(1);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 追踪商品
	 * 
	 * @param modelMap
	 * @param sku_id
	 * @return
	 */
	@RequestMapping(value="item_trace")
	public ModelAndView getItemTraceLog(ModelMap modelMap, String item_instance_id){
		modelMap.put("item_instance_id", item_instance_id);
		String result = null;
		try {
			Connection conn = dataSource.getConnection();
			ItemInstance item_instance = itemService.getItemInstance(conn, item_instance_id);
			
			// 商品实例的当前状态数据
			if (item_instance != null) {
				modelMap.put("item_instance", item_instance);
			}
			
			// 出入库记录
			int page_size = 100;
			int page = 1;
			Page<ItemInstanceLog> paged_result = itemService.getItemInstanceLog(conn, item_instance_id, page, page_size);
			modelMap.put("page", paged_result);
			
			conn.close();
			
		} catch (Exception e) {
			log.warn(
				String.format(
					"failed to getItemTraceLog, exception: %s, item_instance_id=%s",
					e.toString(), item_instance_id));
		} finally {
		    modelMap.put("inventory", result);
		}
		
		return new ModelAndView("admin/item/item_trace",modelMap);
	}
	
	/**
	 * 销毁商品／物品
	 * 
	 * @param modelMap
	 * @param sku_id
	 * @return
	 */
	@RequestMapping(value="destroy_item")
	public void destroyItemInstance(HttpServletResponse response, HttpServletRequest request, String item_instance_id){
		Jr jr = new Jr();
		jr.setMethod("destroy_item");
		try {
			String memo = "";
			User admin = (User)request.getSession().getAttribute(R.User.SESSION_USER);
			if (admin != null) {
				memo = "后台销毁，操作者:" + admin.getNickname() + "(" + admin.getId() + ")";
			}
			Connection conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			itemService.destroyItemInstance(conn, item_instance_id, memo);
			conn.commit();
			conn.close();
			jr.setCord(0);
		} catch (Exception e) {
			log.warn(
				String.format(
					"failed to destroyItemInstance, exception: %s, item_instance_id=%s",
					e.toString(), item_instance_id));
			jr.setCord(1);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询订单
	 * 
	 * @param modelMap
	 * @param sku_id
	 * @return
	 */
	@RequestMapping(value="order")
	public ModelAndView getOrder(ModelMap modelMap, String order_id){
		modelMap.put("order_id", order_id);
		try {
			Connection conn = dataSource.getConnection();
			Order order = orderService.getOrder(conn, order_id);
			
			// 商品实例的当前状态数据
			if (order != null) {
				modelMap.put("order", order);
			}
			
			// 订单商品列表
			List<ItemInstance> item_instance_list = orderService.getItemInstanceList(conn, order_id);
			modelMap.put("item_instance_list", item_instance_list);
			
			List<Payment> payment_list = orderService.getPaymentList(conn, order_id);
			modelMap.put("payment_list", payment_list);
			conn.close();
			
		} catch (Exception e) {
			log.warn(
				String.format(
					"failed to getOrder, exception: %s, order_id=%s",
					e.toString(), order_id));
		}
		
		return new ModelAndView("admin/item/order",modelMap);
	}
	
	@RequestMapping(value="cancel_order")
	public ModelAndView cancelOrder(HttpServletRequest request, ModelMap modelMap, String order_id){
		try {
			Connection conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			String memo = "后台取消订单";
			User admin = (User)request.getSession().getAttribute(R.User.SESSION_USER);
			if (admin !=  null) {
				memo = memo + ", 操作者:" + admin.getNickname() + "(" + admin.getId() + ")";
			}
			orderService.cancelOrder(conn, order_id, memo);
			conn.commit();
			conn.close();
			
		} catch (Exception e) {
			log.warn(
				String.format(
					"failed to cancelOrder, exception: %s, order_id=%s",
					e.toString(), order_id));
		}
		
		return new ModelAndView("admin/item/order",modelMap);
	}
}
