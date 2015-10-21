package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.List;
import java.sql.Connection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.entity.ItemClass;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.service.IItemService;
import cn.yi.gather.v20.service.ISkuService;

@Controller("itemControllerV20")
@RequestMapping(value = "v20/item")
public class ItemController {
	private static Logger log = Logger.getLogger(ItemController.class);
	
	@Resource(name = "itemServiceV20")
	private IItemService itemService;
	
	@Resource(name= "dataSourceV20")
	private ComboPooledDataSource dataSource; 
	
	@Resource(name="skuServiceV20")
	private ISkuService skuService;
	
	/**
	 * 看一个用户有哪些商品
	 */
	@RequestMapping(value="get_user_inventory")
	public void getUserInventory(HttpServletResponse response, String user_id){
		Jr jr = new Jr();
		jr.setMethod("get_sku_inventory");
		try {
			Connection conn = dataSource.getConnection();
//			List<ItemInstance> item_list = itemService.getUserInventory(conn, user_id);
			List<ItemInstance> item_list = itemService.getUserInventory(user_id);
			conn.close();
			jr.setCord(0);
			jr.setData(item_list);
		} catch (Exception e) {
			// TODO: handle exception
			jr.setCord(1);
			e.printStackTrace();
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
	 * 用户消耗一个物品
	 */
	@RequestMapping(value="consume_item_instance")
	public void consume(HttpServletResponse response, String user_id, String item_instance_id){
		Jr jr = new Jr();
		jr.setMethod("consume_item");
		try {
			Connection conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			itemService.consumeItemInstance(conn, user_id, item_instance_id);
			conn.commit();
			conn.close();
			jr.setCord(0);
		} catch (Exception e) {
			// TODO: handle exception
			jr.setCord(1);
			jr.setMsg(e.getMessage());
			log.warn("failed to consume item instance, user_id=" + user_id + ", item_instance_id=" + item_instance_id +
						", exception:" + e.toString());
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
	 * 获取阳光100 的商品详情
	 * @param response
	 */
	@RequestMapping(value = "getyginn")
	public void getYgInn(HttpServletResponse response){
		Jr jr = new Jr();
		jr.setMethod("getyginn");
		try {
			ItemClass item=itemService.findByCode("inn");
			List<SKU> skus=skuService.findListByItemClass(item.getId());
			jr.setData(skus);
			jr.setCord(0);
			jr.setMsg("OK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
