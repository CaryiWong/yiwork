package cn.yi.gather.v20.service;

import java.util.List;

import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.entity.SkuInventory;

public interface ISkuService {

	/**
	 * 获取指定大类的商品品类列表
	 * @param item_class_id
	 * @return
	 */
	public List<SKU> getSKUListByItemClassId(Long item_class_id);
	
	/**
	 * 获取一个SKU的数据
	 * @param sku_id
	 * @return
	 */
	public SKU getSku(String sku_id);
	
	/**
	 * 查看这个sku_id是否存在
	 * @param sku_id
	 * @return
	 */
	public Boolean skuExist(String sku_id) throws Exception;
	
	/**
	 * 获取默认价格
	 * @param sku_id
	 * @return
	 */
	public Double getDefaultPriceById(String sku_id) throws Exception;
	
	/**
	 * 获取会员价格
	 * @param sku_id
	 * @return
	 */
	public Double getMemberPriceById(String sku_id) throws Exception;
	
	/**
	 * 获取一个SKU的库存量数据
	 * @param sku_id
	 * @return
	 */
	public SkuInventory getSkuInventory(String sku_id);
	
	/**
	 * 新增&保存
	 * @param entity
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月2日 下午2:45:26
	 */
	public SKU saveOrUpdate(SKU entity);
	
	/**
	 * 主键查询
	 * @param id
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月2日 下午2:48:49
	 */
	public SKU findById(String id);
	
	/**
	 * 根据item_id获取sku列表
	 * @param item_id
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月2日 下午3:00:46
	 */
	public List<SKU> findListByItemClass(Long item_id);
 	
}
