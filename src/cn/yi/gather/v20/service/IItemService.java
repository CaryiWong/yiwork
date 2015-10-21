package cn.yi.gather.v20.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import cn.yi.gather.v20.entity.ItemClass;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.ItemInstanceLog;
import cn.yi.gather.v20.entity.Payment;
import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.YigatherItemInventoryLog;


public interface IItemService {

	/**
	 * 获取商品大类列表
	 * @return
	 */
	public List<ItemClass> getItemClassList();
	
	/**
	 * 获取商品大类的详情
	 * @param item_class_id
	 * @return
	 */
	public ItemClass getItemClass(Long item_class_id);
	
	/**
	 * 新增商品大类
	 * @param name
	 */
	public void addItemClass(String name);
	
	/**
	 * 修改商品大类
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public int modifyItemClass(Long id, String name);
	
	/**
	 * 删除商品大类
	 * 
	 * @param id
	 */
	public void deleteItemClass(Long id);
	
	/**
	 * 获取指定大类的商品品类列表
	 * @param item_class_id
	 * @return
	 */
	public List<SKU> getSKUListByItemClassId(Long item_class_id);
	
	/**
	 * 获取指定大类的商品品类列表
	 * @param item_class_name
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月26日 上午11:43:52
	 */
	public List<SKU> findSKUListByItemClassName(String item_class_name);
	
	/**
	 * 分页查看SKU列表
	 * @param page
	 * @param size
	 * @param item_class_id
	 * @return
	 */
	public com.common.Page<SKU> getSKUListForPage(Integer page, Integer size, Long item_class_id);
	
	/**
	 * 获取一个SKU的数据
	 * @param sku_id
	 * @return
	 */
	public SKU getSku(String sku_id);
	
	/**
	 * 新建SKU 
	 * @param item_class
	 * @param sku_id
	 * @param sku_name
	 * @param default_price
	 * @param member_price
	 */
	public void addSku(Connection conn, Long item_class_id, String sku_name,
			Double default_price, Double member_price, Integer is_unlimited) throws Exception;
	
	/**
	 * 修改SKU
	 * @param sku_id
	 * @param item_class_id
	 * @param sku_name
	 * @param default_price
	 * @param member_price
	 */
	public int modifySku(String sku_id, Long item_class_id,  String sku_name,
			Double default_price, Double member_price);
	
	/**
	 * 删除SKU
	 * @param sku_id
	 */
	public void deleteSku(String sku_id);
	
	/**
	 * 上架
	 * @param sku_id
	 * @return
	 */
	public int putOnShelves(String sku_id);
	
	/**
	 * 下架
	 * @param sku_id
	 * @return
	 */
	public int getOffShelves(String sku_id);
	
	/**
	 * 获取一个SKU的库存量
	 * @param sku_id
	 * @return
	 */
	public String getSkuInventory(Connection conn, String sku_id) throws Exception;
	
	/**
	 * 增加库存
	 * @param conn
	 * @param sku_id
	 * @param amount
	 * @throws Exception
	 */
	public void InventoryIncrease(Connection conn, String sku_id, Integer amount, User admin) throws Exception;
	
	/**
	 * 减少库存
	 * @param conn
	 * @param sku_id
	 * @param amount
	 * @throws Exception
	 */
	public void InventoryReduce(Connection conn, String sku_id, Integer amount, User admin) throws Exception;
	
	/**
	 * 分页查看商品入库／发货 log
	 * 
	 * @param conn
	 * @param sku_id
	 * @param page
	 * @param page_size
	 * @return
	 * @throws Exception
	 */
	public com.common.Page<YigatherItemInventoryLog> getYigatherItemInventoryLog(Connection conn, String sku_id, Integer page, Integer page_size) throws Exception;
	
	/**
	 * 获取一个用户的商品库
	 * @param user_id
	 * @return
	 */
	public List<ItemInstance> getUserInventory(Connection conn, String user_id) throws Exception;
	
	/**
	 * 获取一个商品实例的当前状态
	 * @param conn
	 * @param item_instance_id
	 * @return
	 * @throws Exception
	 */
	public ItemInstance getItemInstance(Connection conn, String item_instance_id) throws Exception;
	
	/**
	 * 分页查看商品实例 log
	 * 
	 * @param conn
	 * @param item_instance_id
	 * @param page
	 * @param page_size
	 * @return
	 * @throws Exception
	 */
	public com.common.Page<ItemInstanceLog> getItemInstanceLog(Connection conn, String item_instance_id, Integer page, Integer page_size) throws Exception;
	
	
	/**
	 * 分页查看用户的物品log
	 * 
	 * @param conn
	 * @param user_id
	 * @param page
	 * @param page_size
	 * @return
	 * @throws Exception
	 */
	public com.common.Page<ItemInstanceLog> getUserItemLog(Connection conn, String user_id, Date start_date, Date end_date, Integer page, Integer page_size) throws Exception;
	

	/**
	 * 正式发货给用户
	 * @param conn
	 * @param user_id
	 * @param order_id
	 * @throws Exception
	 */
	public void doItemDelivery(Connection conn, String user_id, String order_id) throws Exception;
	
	/**
	 * 正式发货给用户
	 * @param payment
	 * @author Lee.J.Eric
	 * @time 2015年1月7日 下午2:34:09
	 */
	public void doItemDelivery(Payment payment) throws Exception;
	
	/**
	 * 消耗／使用一个物品
	 * @param conn
	 * @param item_instance_id
	 * @throws Exception
	 */
	public void consumeItemInstance(Connection conn, String user_id, String item_instance_id) throws Exception;
	
	/**
	 * 销毁物品
	 * @param conn
	 * @param item_instance_id
	 * @throws Exception
	 */
	public void destroyItemInstance(Connection conn, String item_instance_id, String memo) throws Exception;

	/**
	 * 根据指定商品大类代码名查询
	 * @param code
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月2日 下午3:08:16
	 */
	public ItemClass findByCode(String code);
	
	/**
	 * 会员续年费
	 * @param payment
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月7日 下午3:12:39
	 */
	public void doRenewal(Payment payment) throws Exception;
	
	/**
	 * 获取一个用户的商品库
	 * @param user_id
	 * @return
	 */
	public List<ItemInstance> getUserInventory(String user_id) throws Exception;
}
