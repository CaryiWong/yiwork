package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.List;
import java.sql.Connection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tools.utils.JSONUtil;

import cn.yi.gather.v20.entity.ItemClass;
import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.service.IItemService;
import cn.yi.gather.v20.service.ISkuService;

@Controller("itemCatalogControllerV20")
@RequestMapping(value = "v20/item_catalog")
public class ItemCatalogController {

	@Resource(name = "itemServiceV20")
	private IItemService itemService;
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource; 
	
	@Resource(name = "skuServiceV20") 
	private ISkuService skuService;
	
	/**
	 * 获取所有的商品大类列表
	 */
	@RequestMapping(value="get_item_class_list")
	public void getItemClassList(HttpServletResponse response){
		Jr jr = new Jr();
		jr.setMethod("get_item_class_list");
		try {
			List<ItemClass> item_class_list = itemService.getItemClassList();
			jr.setCord(0);
			jr.setData(item_class_list);
		} catch (Exception e) {
			// TODO: handle exception
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
	 * 按ID获取商品大类的详情信息
	 */
	@RequestMapping(value="get_item_class")
	public void getItemClassById(HttpServletResponse response, Long id){
		Jr jr = new Jr();
		jr.setMethod("get_item_class");
		try {
			ItemClass item_class = itemService.getItemClass(id);
			jr.setCord(0);
			jr.setData(item_class);
		} catch (Exception e) {
			// TODO: handle exception
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
	 * 看一个商品大类下面有多少种品类
	 */
	@RequestMapping(value="get_sku_list_by_item_class")
	public void getSKUListByItemClassId(HttpServletResponse response, Long item_class_id){
		Jr jr = new Jr();
		jr.setMethod("get_sku_list_by_item_class");
		try {
			List<SKU> sku_list = itemService.getSKUListByItemClassId(item_class_id);
			jr.setCord(0);
			jr.setData(sku_list);
		} catch (Exception e) {
			// TODO: handle exception
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
	 * 查询所有的咖啡券
	 * @param response
	 * @author Lee.J.Eric
	 * @time 2014年12月26日 上午11:49:55
	 */
	@RequestMapping(value = "find_sku_list_coffee")
	public void findSKUListCoffee(HttpServletResponse response){
		Jr jr = new Jr();
		jr.setMethod("find_sku_list_coffee");
		try {
			ItemClass itemClass = itemService.findByCode("coffee");
			List<SKU> sku_list = skuService.findListByItemClass(itemClass.getId());
//			List<SKU> sku_list = itemService.findSKUListByItemClassName("咖啡");
			jr.setCord(0);
			jr.setData(sku_list);
		} catch (Exception e) {
			// TODO: handle exception
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
	 * 看一个商品大类下面有多少种品类
	 */
	@RequestMapping(value="get_sku")
	public void getSKUData(HttpServletResponse response, String sku_id){
		Jr jr = new Jr();
		jr.setMethod("get_sku");
		try {
			SKU sku = itemService.getSku(sku_id);
			jr.setCord(0);
			jr.setData(sku);
		} catch (Exception e) {
			// TODO: handle exception
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
	 * 看一个SKU的库存量
	 */
	@RequestMapping(value="get_inventory_of_sku")
	public void getSKUInventory(HttpServletResponse response, String sku_id){
		Jr jr = new Jr();
		jr.setMethod("get_inventory_of_sku");
		try {
			Connection conn = dataSource.getConnection();
			String amount = itemService.getSkuInventory(conn, sku_id);
			conn.close();
			jr.setCord(0);
			jr.setData(amount);
		} catch (Exception e) {
			// TODO: handle exception
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
