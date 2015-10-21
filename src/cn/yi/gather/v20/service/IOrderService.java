package cn.yi.gather.v20.service;

import java.sql.Connection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.Order;
import cn.yi.gather.v20.entity.Payment;
import cn.yi.gather.v20.entity.SkuInventory;
import cn.yi.gather.v20.entity.User;

public interface IOrderService {

	/**
	 * 创建订单
	 * 
	 * @param conn
	 * @param user_id
	 * @param cart
	 * @return
	 * @throws Exception
	 */
	public Order createOrder(Connection conn, String user_id, Hashtable<String, Integer> cart) throws Exception;
	
	
	/**
	 * 创建充值订单
	 * 
	 * @param conn
	 * @param user_id
	 * @param money
	 * @return
	 * @throws Exception
	 */
	public Order createDepositOrder(Connection conn, String user_id, Double money) throws Exception;
	
	/**
	 * 创建充值订单
	 * @param user_id
	 * @param money
	 * @return
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月14日 下午6:24:26
	 */
	public Order createDepositOrder(String user_id, Double money) throws Exception;
	
	/**
	 * 创建会员续费订单
	 * @param response
	 * @param user_id
	 * @param n
	 * @return
	 * @throws Exception
	 */
	public Order createMembershipRenewalOrder(Connection conn, String user_id,Integer n) throws Exception;
	
	/**
	 * 创建会员续费订单
	 * @param user_id
	 * @param n
	 * @return
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月15日 上午11:03:02
	 */
	public Order createMembershipRenewalOrder(String user_id,Integer n) throws Exception;
	
	/**
	 * 取消订单
	 * 
	 * @param conn
	 * @param user_id
	 * @param order_id
	 * @throws Exception
	 */
	public void cancelOrder(Connection conn, String order_id, String memo) throws Exception;
	
	/**
	 * 查看订单列表
	 * 
	 * @param conn
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public Order getOrder(Connection conn, String order_id) throws Exception;
	
	/**
	 * 查看订单列表
	 * 
	 * @param conn
	 * @param user_id
	 * @return
	 * @throws Exception
	 */
	public List<Order> getOrderList(Connection conn, String user_id) throws Exception;
	
	/**
	 * 支付订单
	 * 
	 * @param conn
	 * @param user_id
	 * @param order_id
	 * @param payment
	 * @throws Exception
	 */
	public void payForOrder(Connection conn, String user_id, String order_id, Hashtable<Integer, Double> payment) throws Exception;
	
	/**
	 * 支付订单
	 * @param user_id
	 * @param order_id
	 * @param payment
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年2月2日 上午11:01:33
	 */
	public void payForOrder(String user_id, String order_id, Hashtable<Integer, Double> payment) throws Exception;
	/**
	 * 更新支付状态
	 * 
	 * @param conn
	 * @param user_id
	 * @param order_id
	 * @param payment
	 * @throws Exception
	 */
	public void updatePayment(Connection conn, String order_id, Payment payment) throws Exception;
		
	/**
	 * 找出所有已超时、未处理的订单，关闭之
	 * 
	 * @param conn
	 * @throws Exception
	 */
	public void handleExpiredOrders(Connection conn) throws Exception;
	
	/**
	 * 找出所有已超时、未处理的订单，关闭之
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月14日 下午3:18:17
	 */
	public void handleExpiredOrders() throws Exception;
	
	/**
	 * 查询所有超时、未处理的订单
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月14日 下午3:20:18
	 */
	public List<Order> findExpiredOrders();
	
	/**
	 * 获取订单所属的UserID
	 * 
	 * @param conn
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public String getUserIdOfOrder(Connection conn, String order_id) throws Exception;
	
	/**
	 * 获取订单的商品列表
	 * 
	 * @param conn
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public List<ItemInstance> getItemInstanceList(Connection conn, String order_id) throws Exception;
 
	/**
	 * 获取订单的支付列表
	 * @param conn
	 * @param order_id
	 * @return
	 * @throws Exception
	 */
	public List<Payment> getPaymentList(Connection conn, String order_id) throws Exception;
	
	/**
	 * 
	 * @param entity
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月2日 下午4:31:54
	 */
	public Order saveOrUpdate(Order entity);
	
	/**
	 * 根据id查询订单
	 * @param id
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月5日 下午3:35:28
	 */
	public Order findById(String id);
	
	/**
	 * 更新支付状态
	 * @param payment
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月7日 上午11:19:46
	 */
	public void updatePayment(Payment payment) throws Exception;
	
	/**
	 * 创建订单
	 * @param jsontext
	 * @param memo
	 * @param p
	 * @param user
	 * @param skuid
	 * @param quantity
	 * @param inventory
	 * @return
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月9日 下午12:11:00
	 */
	public Order createOrder(String jsontext,String memo,String busi_type,String busi_id, Integer p, User user,
			String skuid,Integer quantity,Date endTime, SkuInventory inventory) throws Exception;
	
	/**
	 * 成为会员
	 * @param user
	 * @return
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月9日 下午4:48:52
	 */
	public Order becomeVip(User user) throws Exception;
	
	
	/**
	 * 根据用户与订单状态查询订单列表
	 * @param userid
	 * @param status
	 * @param page
	 * @param size
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月12日 下午4:54:33
	 */
	public Page<Order> orderList(String userid,Integer status,Integer page,Integer size,String openid,String tel);
	
	/**
	 * 查询用户的成功消费总额
	 * @param userid
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月15日 上午11:55:23
	 */
	public Double countConsumeTotal(String userid);
	
	/**
	 * 买咖啡订单处理
	 * @param orderid  订单ID
	 */
	public void buycoffeemanage(String orderid,Integer cof_num);

	public Order createOrder(String memo,User user,String skuid,Integer quantity,SkuInventory inventory,Order order) throws Exception;
}
