package cn.yi.gather.v20.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.SKU;

public interface IItemInstanceService {
	
	public void saveOrUpdate(List<ItemInstance> entities);
	
	public ItemInstance saveOrUpdate(ItemInstance entity);
	
	public ItemInstance findById(String id);
	
	public List<ItemInstance> findByOrderIdAndStatus(String orderId,Integer status);
	
	public List<ItemInstance> findByUserIdAndStatus(String userId,Integer status);
	
	/**
	 * 根据订单号update订单信息
	 * @param status
	 * @param modifyTime
	 * @param orderId
	 * @author Lee.J.Eric
	 * @time 2015年1月7日 下午2:40:49
	 */
	public void updateByOrderId(Integer status,Date modifyTime,String orderId);
	
	/**
	 * 根据订单号查询商品实例
	 * @param orderId
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月14日 下午2:15:41
	 */
	public List<ItemInstance> findByOrderId(String orderId);
	
	/**
	 * 咖啡卷
	 * @param userId  所属者
	 * @param status  使用状态
	 * @param skuId   咖啡卷种类的商品ID
	 * @return
	 */
	public List<ItemInstance> findCoffee(String userId,
			Integer status,String skuId,String type);
	
	/**
	 * 获取所有消费卷
	 * @param userId
	 * @param status  状态  null 表示所有  
	 * @param type    指定类型   Null 表示所有
	 * @param skus    指定SKU 的消费卷 null 表示所有
	 * @return
	 */
	public List<ItemInstance> findAllCoupon(String userId,
			Integer status,String type,List<SKU> skus);
	
	/**
	 * 使用咖啡卷/消劵
	 * @param coffeenum
	 * @return  0 使用OK   1 无效劵
	 */
	public Integer usecoffee(String coffeenum);
	
	/**
	 * 商品实例过期
	 * @param endDate
	 * @author Lee.J.Eric
	 * @time 2015年2月5日 上午11:30:33
	 */
	public void instanceOverdue(Date endDate);
	
	/**
	 * 获取已消券列表
	 * @param keyword
	 * @param start
	 * @param end
	 * @param page
	 * @param size
	 * @author kcm
	 * @time 2015年3月13日 上午11:30:33
	 */
	public com.common.Page<ItemInstance> couponlist(String keyword,Date start,Date end,Integer page,Integer size);
}
