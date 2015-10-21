package cn.yi.gather.v20.service.serviceImpl;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.dao.OrderRepository;
import cn.yi.gather.v20.entity.*;
import cn.yi.gather.v20.entity.ItemInstance.ItemInstanceStatus;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeActionTypeValue;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeMsgTypeValue;
import cn.yi.gather.v20.entity.Order.OrderStatus;
import cn.yi.gather.v20.entity.Order.OrderType;
import cn.yi.gather.v20.entity.Payment.PaymentStatus;
import cn.yi.gather.v20.entity.Payment.PaymentType;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.entity.UserAccount.AccountStatus;
import cn.yi.gather.v20.entity.UserAccount.MoneyType;
import cn.yi.gather.v20.entity.UserAccountLog.UserAccountOpType;
import cn.yi.gather.v20.entity.YiGatherAccountLog.AccountOpType;
import cn.yi.gather.v20.service.*;

import com.common.R;
import com.tools.utils.RandomUtil;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

/**
 * 订单操作
 * 
 */
@Service("orderServiceV20")
public class OrderService implements IOrderService{
	private Logger log = Logger.getLogger(OrderService.class);
	
	@Resource(name = "skuServiceV20")
	private ISkuService skuService;
	
	@Resource(name = "itemServiceV20")
	private IItemService itemService;
	
	@Resource(name = "userAccountServiceV20")
	private IUserAccountService userAccountService;
	
	@Resource(name = "paymentServiceV20")
	private IPaymentService paymentService;
	
	@Resource(name = "orderRepositoryV20")
	private OrderRepository repository;
	
	@Resource(name = "pendingRefundServiceV20")
	private IPendingRefundService pendingRefundService;
	
	@Resource(name = "userAccountLogServiceV20")
	private IUserAccountLogService userAccountLogService;
	
	@Resource(name = "yiGatherAccountLogServiceV20")
	private IYiGatherAccountLogService yiGatherAccountLogService;
	
	@Resource(name = "questionServiceV20")
	private IQuestionService questionService;
	
	@Resource(name = "skuInventoryServiceV20")
	private ISkuInventoryService skuInventoryService;
	
	@Resource(name = "orderServiceV20")
	private IOrderService orderService;
	
	@Resource(name = "itemInstanceServiceV20")
	private IItemInstanceService itemInstanceService;
	
	@Resource(name = "itemInstanceLogServiceV20")
	private IItemInstanceLogService itemInstanceLogService;
	
	@Resource(name ="yigatherItemInventoryLogServiceV20")
	private IYigatherItemInventoryLogService yigatherItemInventoryLogService;
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "activityServiceV20")
	private IActivityService activityService;
	
	@Resource(name = "courseServiceV20")
	private ICourseService courseService;
	
	@Resource(name = "entityManagerFactoryV20")
	private EntityManagerFactory emf;
	
	@Resource(name="invitationsServiceV20")
	private IinvitationsService invitationsService;
	
	@Resource(name = "jPushServiceV20")
	private JPushService pushService;
	
	@Resource(name = "noticeMsgServiceV20")
	private INoticeMsgService noticeMsgService;
	
//	private final Double MEMBERSHIP_ANNUAL_FEE = 150d;
	
	// 未支付订单的生存期 时间单位：分钟
	public static final int UNPAID_ORDER_LIFETIME_IN_MINUTES = 30;

	// 非会员未支付订单的生存期 时间单位：分钟
	public static final int UNPAID_ORDER_LIFETIME_IN_MINUTES_UNROOT = 10;
	
	
	/**
	 * 创建订单
	 */
	@Override
	public Order createOrder(Connection conn, String user_id, Hashtable<String, Integer> cart) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			if (!userExists(conn, user_id)) {
				throw new Exception("此用户不存在");
			}
			Double total_price = 0.0;
			for(Iterator itr = cart.keySet().iterator(); itr.hasNext();){
				String sku_id = (String)itr.next();
				if (!skuService.skuExist(sku_id)) {
					throw new Exception("此商品种类不存在");
				}
				Integer amount = (Integer)cart.get(sku_id);
				if (amount <= 0) {
					throw new Exception("商品数量不合法");
				}
				total_price = total_price + skuService.getMemberPriceById(sku_id) * amount;
			}
			
			// 1. 创建Order对象
			Order order = new Order();
			order.setMemo("您的购物");
			order.setUserId(user_id);
			order.setOrderType(OrderType.BUYING.getCode());
			order.setStatus(OrderStatus.UNPAID.getCode());
			order.setTotalPrice(total_price);
			order.setCreateTime(new Timestamp(System.currentTimeMillis()));
			order.setModifyTime(order.getCreateTime());
			
			String order_id = order.getId();
			pstat = conn.prepareStatement("INSERT INTO order_table(id, user_id, order_type, status, total_price, paid_money, create_time, modify_time,memo) VALUES(?,?,?,?,?,0,NOW(),NOW(),?)");
			pstat.setString(1, order_id);
			pstat.setString(2, order.getUserId());
			pstat.setInt(3, OrderType.BUYING.getCode());
			pstat.setInt(4, order.getStatus());
			pstat.setDouble(5, order.getTotalPrice());
			pstat.setString(6, order.getMemo());
			pstat.executeUpdate();
			
			// 2. 扣库存， 生成商品实例
			for(Iterator itr = cart.keySet().iterator(); itr.hasNext();){
				String sku_id = (String)itr.next();
				Integer amount = (Integer)cart.get(sku_id);
			
				// 扣库存
				pstat = conn.prepareStatement("UPDATE sku_inventory SET amount=amount-?, modify_time=NOW() WHERE sku_id=? AND is_unlimited=0");
				pstat.setInt(1, amount);
				pstat.setString(2, sku_id);
				pstat.executeUpdate();
				
				pstat = conn.prepareStatement("SELECT amount, is_unlimited FROM sku_inventory WHERE sku_id=?");
				pstat.setString(1, sku_id);
				result = pstat.executeQuery();
				if (!result.next()) {
					throw new Exception("商品不存在");
				}
				int sku_amount = result.getInt("amount");
				int is_unlimited = result.getInt("is_unlimited");
				if (is_unlimited == 0 && sku_amount < 0) {
					throw new Exception("库存不足");
				}
				
				Double sale_price = skuService.getMemberPriceById(sku_id);
				
				// 生成商品实例
				for (int i=0; i<amount; i++) {
					ItemInstance instance = new ItemInstance();
					pstat = conn.prepareStatement("INSERT INTO item_instance(id, status, order_id, sku_id, sale_price, create_time, modify_time) VALUES(?,?,?,?,?,NOW(),NOW())");
					pstat.setString(1, instance.getId());
					pstat.setInt(2, ItemInstanceStatus.UNDELIVERED.getCode());
					pstat.setString(3, order_id);
					pstat.setString(4, sku_id);
					pstat.setDouble(5, sale_price);
					pstat.executeUpdate();
					
					ItemInstanceLog item_log = new ItemInstanceLog();
					pstat = conn.prepareStatement("INSERT INTO item_instance_log(id, item_instance_id, op_type, order_id, sku_id, memo, date_time) VALUES(?,?,?,?,?,?,NOW())");
					pstat.setString(1, item_log.getId());
					pstat.setString(2, instance.getId());
					pstat.setInt(3, ItemInstanceLog.OpType.CREATE.getCode());
					pstat.setString(4, order_id);
					pstat.setString(5, sku_id);
					pstat.setString(6, "生成订单"+order_id+"，系统自动创建商品实例");
					pstat.executeUpdate();
				}
				
				// 记录出货
				YigatherItemInventoryLog record = new YigatherItemInventoryLog();
				pstat = conn.prepareStatement("INSERT INTO yigather_item_log(id, sku_id, amount, order_id, memo, op_type, date_time) VALUES(?,?,?,?,?,?,NOW())");
				pstat.setString(1, record.getId());
				pstat.setString(2, sku_id);
				pstat.setInt(3, amount);
				pstat.setString(4, order_id);
				pstat.setString(5, "生成订单"+order_id+", 系统自动出货");
				pstat.setInt(6, YigatherItemInventoryLog.OpType.DELIVERY.getCode());
				pstat.executeUpdate();
			}
			return order;
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	/**
	 * 创建充值订单
	 */
	@Override
	public Order createDepositOrder(Connection conn, String user_id, Double money) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			if (money <= 0.0) {
				throw new Exception("充值金额不正确");
			}
			if (!userExists(conn, user_id)) {
				throw new Exception("此用户不存在");
			}
			
			Order order = new Order();
			order.setMemo("您的帐户充值");
			order.setUserId(user_id);
			order.setOrderType(OrderType.DEPOSIT.getCode());
			order.setStatus(OrderStatus.UNPAID.getCode());
			order.setTotalPrice(money);
			order.setCreateTime(new Timestamp(System.currentTimeMillis()));
			order.setModifyTime(order.getCreateTime());
			
			String order_id = order.getId();
			pstat = conn.prepareStatement(
					"INSERT INTO order_table(id, user_id, order_type, status, total_price, deposit_money_type,"
					+ " deposit_money, paid_money, create_time, modify_time,memo) "
					+ " VALUES(?,?,?,?,?,?,?,0,NOW(),NOW()),?");
			pstat.setString(1, order_id);
			pstat.setString(2, order.getUserId());
			pstat.setInt(3, OrderType.DEPOSIT.getCode());
			pstat.setInt(4, order.getStatus());
			pstat.setDouble(5, order.getTotalPrice());
			pstat.setInt(6, MoneyType.YIGATHER.getCode());
			pstat.setDouble(7, money);		
			pstat.setString(8, order.getMemo());
			pstat.executeUpdate();
			
			return order;
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	
	
	@Override
	public Order createDepositOrder(String user_id, Double money)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			if (money <= 0.0) {
				throw new Exception("充值金额不正确");
			}
			if (!userExists(user_id)) {
				throw new Exception("此用户不存在");
			}
			Order order = new Order();
			order.setMemo("您的帐户充值");
			order.setUserId(user_id);
			order.setOrderType(OrderType.DEPOSIT.getCode());
			order.setStatus(OrderStatus.UNPAID.getCode());
			order.setTotalPrice(money);
			order.setCreateTime(new Timestamp(System.currentTimeMillis()));
			order.setModifyTime(order.getCreateTime());
			return repository.save(order);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	/**
	 * 创建会员续费订单
	 */
	@Override
	public Order createMembershipRenewalOrder(Connection conn, String user_id,Integer n) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			if (!userExists(conn, user_id)) {
				throw new Exception("此用户不存在");
			}
			if(n==null||n<1){
				throw new Exception("续费年限不正确");
			}
			
			Order order = new Order();
			order.setMemo("您的会员续费");
			order.setUserId(user_id);
			order.setOrderType(OrderType.MEMBERSHIP_RENEWAL.getCode());
			order.setStatus(OrderStatus.UNPAID.getCode());
			order.setTotalPrice(R.User.MEMBERSHIP_ANNUAL_FEE*n);
//			order.setTotalPrice(0.01*n);//test
			order.setCreateTime(new Timestamp(System.currentTimeMillis()));
			order.setModifyTime(order.getCreateTime());
			
			String order_id = order.getId();
			pstat = conn.prepareStatement(
					"INSERT INTO order_table(id, user_id, order_type, status, total_price, "
					+ " paid_money, create_time, modify_time,memo) "
					+ " VALUES(?,?,?,?,?,0,NOW(),NOW()),?");
			pstat.setString(1, order_id);
			pstat.setString(2, order.getUserId());
			pstat.setInt(3, OrderType.MEMBERSHIP_RENEWAL.getCode());
			pstat.setInt(4, order.getStatus());
			pstat.setDouble(5, order.getTotalPrice());	
			pstat.setString(6, order.getMemo());
			pstat.executeUpdate();
			
			ItemInstance instance = new ItemInstance();
			pstat = conn.prepareStatement("INSERT INTO item_instance(id, status, order_id, sku_id, sale_price, create_time, modify_time) VALUES(?,?,?,null,?,NOW(),NOW())");
			pstat.setString(1, instance.getId());
			pstat.setInt(2, ItemInstanceStatus.UNDELIVERED.getCode());
			pstat.setString(3, order_id);
//			pstat.setString(4, sku_id);
			pstat.setDouble(4, order.getTotalPrice());
			pstat.executeUpdate();
			
			ItemInstanceLog item_log = new ItemInstanceLog();
			pstat = conn.prepareStatement("INSERT INTO item_instance_log(id, item_instance_id, op_type, order_id, sku_id, memo, date_time) VALUES(?,?,?,?,null,?,NOW())");
			pstat.setString(1, item_log.getId());
			pstat.setString(2, instance.getId());
			pstat.setInt(3, ItemInstanceLog.OpType.CREATE.getCode());
			pstat.setString(4, order_id);
//			pstat.setString(5, sku_id);
			pstat.setString(5, "生成订单"+order_id+"，系统自动创建续费实例");
			pstat.executeUpdate();
			
			return order;
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	
	
	@Override
	public Order createMembershipRenewalOrder(String user_id, Integer n)
			throws Exception {
		// TODO Auto-generated method stub
		try {
			if (!userExists(user_id)) {
				throw new Exception("此用户不存在");
			}
			if(n==null||n<1){
				throw new Exception("续费年限不正确");
			}
			
			Order order = new Order();
			order.setUserId(user_id);
			order.setOrderType(OrderType.MEMBERSHIP_RENEWAL.getCode());
			order.setStatus(OrderStatus.UNPAID.getCode());
			order.setTotalPrice(R.User.MEMBERSHIP_ANNUAL_FEE*n);
			order.setPaidMoney(0d);
//			order.setTotalPrice(0.01*n);//test
			order.setCreateTime(new Timestamp(System.currentTimeMillis()));
			order.setModifyTime(order.getCreateTime());
			order.setMemo("您的会员续费");
			orderService.saveOrUpdate(order);
			
			ItemInstance instance = new ItemInstance();
			instance.setStatus(ItemInstanceStatus.UNDELIVERED.getCode());
			instance.setOrderId(order.getId());
			instance.setSalePrice(order.getTotalPrice());
			instance.setCreateTime(new Timestamp(System.currentTimeMillis()));
			instance.setModifyTime(new Timestamp(System.currentTimeMillis()));
			itemInstanceService.saveOrUpdate(instance);
			
			ItemInstanceLog item_log = new ItemInstanceLog();
			item_log.setItemInstanceId(instance.getId());
			item_log.setOpType(ItemInstanceLog.OpType.CREATE.getCode());
			item_log.setOrderId(order.getId());
			item_log.setMemo("生成订单"+order.getId()+"，系统自动创建续费实例");
			item_log.setDateTime(new Timestamp(System.currentTimeMillis()));
			itemInstanceLogService.saveOrUpdate(item_log);
			
			return order;
		}
		catch(Exception e) {
			throw e;
		}
	}

	/**
	 * 取消订单
	 */
	@Override
	public void cancelOrder(Connection conn, String order_id, String memo) throws Exception {
		try {
			Order order = getOrderById(conn, order_id);
			if (order == null) {
				throw new Exception("该订单不存在");
			}
			if (order.getStatus() != OrderStatus.UNPAID.getCode() &&
				order.getStatus() != OrderStatus.CONFIRMING.getCode()) {
				throw new Exception("不能取消此订单, 状态值为" + order.getStatus());
			}
			
			revokeOrder(conn, order_id, OrderStatus.CANCELED.getCode(), memo);
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 查看订单详情
	 */
	@Override
	public Order getOrder(Connection conn, String order_id) throws Exception {
		try {
			Order order = getOrderById(conn, order_id);
			return order;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * 查看订单列表
	 */
	@Override
	public List<Order> getOrderList(Connection conn, String user_id) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT order_table.id,order_table.memo user_id, user.nickname,"
					+ " order_type, status, total_price, create_time, modify_time"
					+ " FROM order_table LEFT JOIN user ON order_table.user_id=user.id"
					+ " WHERE user_id=? ORDER BY create_time DESC");
			pstat.setString(1, user_id);
			result = pstat.executeQuery();
			List<Order> array_list = new ArrayList<Order>();
			while (result.next()) {
				Order order = new Order();
				order.setId(result.getString("id"));
				order.setMemo(result.getString("memo"));
				order.setUserId(result.getString("user_id"));
				order.setUserNickname(result.getString("nickname"));
				order.setOrderType(result.getInt("order_type"));
				order.setStatus(result.getInt("status"));
				order.setTotalPrice(result.getDouble("total_price"));
				order.setCreateTime(result.getTimestamp("create_time"));
				order.setModifyTime(result.getTimestamp("modify_time"));
				array_list.add(order);
			}
			return array_list;
		}
		catch(Exception e) {
			return new ArrayList<Order>();
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	
	/**
	 * 支付订单
	 */
	@Override
	public void payForOrder(Connection conn, String user_id, String order_id, Hashtable<Integer, Double> payment) throws Exception{
		try {
			Double total_money = 0.0;
			for(Iterator itr = payment.keySet().iterator(); itr.hasNext();){
				Integer money_type = (Integer)itr.next();
				if (MoneyType.getName(money_type) == null) {
					throw new Exception("未知的虚拟币种类");
				}
				Double money = (Double)payment.get(money_type);
				if (money <= 0.0) {
					throw new Exception("金额不合法");
				}
				
				PreparedStatement pstat = null;
				ResultSet result = null;
				try {
					pstat = conn.prepareStatement("SELECT status, money FROM user_account WHERE userId=? AND moneyType=?");
					pstat.setString(1, user_id);
					pstat.setInt(2, money_type);
					result = pstat.executeQuery();
					if (result.next()) {
						int account_status = result.getInt("status");
						if (account_status != AccountStatus.NORMAL.getCode()) {
							if (account_status == AccountStatus.FROZEN.getCode()) {
								throw new Exception("用户账户已被冻结，无法支付");
							} else  {
								throw new Exception("用户账户状态异常，无法支付");
							}
						}
						if (result.getDouble("money") < money) {
							throw new Exception("用户账户余额不足");
						}
					} else {
						throw new Exception("用户账户里没有这种虚拟币");
					}
				}
				catch (Exception e) {
					throw e;
				}
				finally {
					if (result != null) {
						result.close();
					}
					if (pstat != null) {
						pstat.close();
					}
				}
				
				total_money = total_money + money;
			}

//			Order order = getOrderById(conn, order_id);
			Order order = findById(order_id);
			if (order == null) {
				throw new Exception("此订单不存在");
			}
			
			if (order.getStatus() != OrderStatus.UNPAID.getCode() &&
				order.getStatus() != OrderStatus.CONFIRMING.getCode()) {
				throw new Exception("此订单不能支付了");
			}
			
			// 如果支付的钱太多，抛异常
			if (total_money + order.getPaidMoney() > order.getTotalPrice()) {
				throw new Exception("支付金额不正确");
			}

			for(Iterator itr = payment.keySet().iterator(); itr.hasNext();){
				Integer money_type = (Integer)itr.next();
				Double money = (Double)payment.get(money_type);
				Payment payment_obj = new Payment();
				payment_obj.setPaymentType(PaymentType.YIGATHER.getCode());
				payment_obj.setMoneyType(money_type);
				payment_obj.setMoney(money);
				payment_obj.setOrderId(order_id);
//				updatePayment(conn, order_id, payment_obj);
				updatePayment(payment_obj);
			}
			
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	
	
	@Override
	public void payForOrder(String user_id, String order_id,
			Hashtable<Integer, Double> payment) throws Exception {
		// TODO Auto-generated method stub
		try {
			Double total_money = 0.0;
			for(Iterator itr = payment.keySet().iterator(); itr.hasNext();){
				Integer money_type = (Integer)itr.next();
				if (MoneyType.getName(money_type) == null) {
					throw new Exception("未知的虚拟币种类");
				}
				Double money = (Double)payment.get(money_type);
				if (money <= 0.0) {
					throw new Exception("金额不合法");
				}
				try {
					UserAccount userAccount = userAccountService.findByMoneyTypeAndUserId(money_type, user_id);
					if(userAccount != null){
						if(userAccount.getStatus() != AccountStatus.NORMAL.getCode()){
							if(userAccount.getStatus() == AccountStatus.FROZEN.getCode()){
								throw new Exception("用户账户已被冻结，无法支付");
							}else {
								throw new Exception("用户账户状态异常，无法支付");
							}
						}
						if(userAccount.getMoney() < money){
							throw new Exception("用户账户余额不足");
						}
					}else {
						throw new Exception("用户账户里没有这种虚拟币");
					}
				}
				catch (Exception e) {
					throw e;
				}
				
				total_money = total_money + money;
			}

			Order order = findById(order_id);
			if (order == null) {
				throw new Exception("此订单不存在");
			}
			
			if (order.getStatus() != OrderStatus.UNPAID.getCode() &&
				order.getStatus() != OrderStatus.CONFIRMING.getCode()) {
				throw new Exception("此订单不能支付了");
			}
			
			// 如果支付的钱太多，抛异常
			if (total_money + order.getPaidMoney() > order.getTotalPrice()) {
				throw new Exception("支付金额不正确");
			}

			for(Iterator itr = payment.keySet().iterator(); itr.hasNext();){
				Integer money_type = (Integer)itr.next();
				Double money = (Double)payment.get(money_type);
				Payment payment_obj = new Payment();
				payment_obj.setPaymentType(PaymentType.YIGATHER.getCode());
				payment_obj.setMoneyType(money_type);
				payment_obj.setMoney(money);
				payment_obj.setOrderId(order_id);
//				updatePayment(conn, order_id, payment_obj);
				updatePayment(payment_obj);
			}
			
		}
		catch(Exception e) {
			throw e;
		}
	}

	/**
	 * 找出所有超时为支付的订单，关闭之
	 */
	@Override
	public void handleExpiredOrders(Connection conn) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT id FROM order_table WHERE (status=? or status=?) and TIMESTAMPDIFF(MINUTE, create_time, NOW()) >= ?");
			pstat.setInt(1, OrderStatus.UNPAID.getCode());
			pstat.setInt(2, OrderStatus.CONFIRMING.getCode());
			pstat.setInt(3, UNPAID_ORDER_LIFETIME_IN_MINUTES);
			result = pstat.executeQuery();
			
			while (result.next()) {
				String order_id = result.getString("id");
				try {
					Order order = getOrderById(conn, order_id);
					revokeOrder(conn, order_id, OrderStatus.EXPIRED.getCode(), "订单超时失效，商品自动回到库存");
					conn.commit();
					log.info(String.format("order %s has expired", order_id));
				}
				catch(Exception e) {
					conn.rollback();
					log.warn(
						String.format(
							"failed to revoke order %s, exception:%s",
							order_id, e.toString()));
				}
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	
	
	@Override
	public void handleExpiredOrders() throws Exception {
		// TODO Auto-generated method stub
		try {
			List<Order> orders = findExpiredOrders();
			for (Order order : orders) {
				try {
					revokeOrder(order, OrderStatus.EXPIRED.getCode(), "订单超时失效，商品自动回到库存");
					log.warn(String.format("order %s has expired", order.getId()));
				} catch (Exception e) {
					// TODO: handle exception
					log.warn( String.format( "failed to revoke order %s, exception:%s", order.getId(), e.toString()));
				}
			}
		}
		catch(Exception e) {
			throw e;
		}
	}

	@Override
	public List<Order> findExpiredOrders() {
		// TODO Auto-generated method stub
		Specification<Order> spec = new Specification<Order>() {
			
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MINUTE, -UNPAID_ORDER_LIFETIME_IN_MINUTES);
				Date root_date = calendar.getTime();
				calendar.add(Calendar.MINUTE,UNPAID_ORDER_LIFETIME_IN_MINUTES);
				calendar.add(Calendar.MINUTE,-UNPAID_ORDER_LIFETIME_IN_MINUTES_UNROOT);
				Date u_root_date = calendar.getTime();

				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.or(cb.equal(root.<Integer>get("status"), OrderStatus.UNPAID.getCode()), cb.equal(root.<Integer>get("status"), OrderStatus.CONFIRMING.getCode())));
//				ps.add(cb.lessThan(root.<Date>get("createTime"), calendar.getTime()));

				Root<User> userRoot = query.from(User.class);
				ps.add(cb.equal(root.<String>get("userId"), userRoot.<String>get("id")));
				ps.add(cb.or(
						cb.and(cb.lessThan(userRoot.<Integer>get("root"), UserRoot.OVREDUE.getCode()), cb.lessThan(root.<Date>get("createTime"), root_date)),
						cb.and(cb.ge(userRoot.<Integer>get("root"), UserRoot.OVREDUE.getCode()), cb.lessThan(root.<Date>get("createTime"), u_root_date))
				));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec);
	}

	/**
	 * 更新支付数据
	 * @param conn
	 * @param order_id
	 * @param payment
	 */
	@Override
	public void updatePayment(Connection conn, String order_id, Payment payment) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			Order order = getOrderById(conn, order_id);
			
			/** 
			 * 如果订单不存在 或者 订单状态异常（不是"待支付"/"确认中"的状态）：
			 * 此时，如果支付方式是人民币（支付宝）， 那就待人工审核退款
			 *      如果支付方式是其他，那就直接抛异常，不接受此次支付
			 */
			if (order == null ||
				(order.getStatus() != OrderStatus.UNPAID.getCode() &&
				 order.getStatus() != OrderStatus.CONFIRMING.getCode())) {
				// 如果订单不存在
				if (payment.getMoneyType() == MoneyType.RMB.getCode()) {
					// 待人工审核退款
					paymentService.prepareToRefund(conn, null, payment.getMoney(), order_id, payment.getAlipayTradeNo(),
												payment.getBuyerId(), payment.getBuyerEmail(), payment.getBankSeqNo(),
												"订单不存在");
				} else {
					throw new Exception("此订单不存在或者状态异常，此次支付取消");
				}
			}
			
			String user_id = order.getUserId();
			
			/**
			 * 如果支付方式是支付宝，不涉及用户账户余额变动
			 * 如果支付方式不是支付宝，用户账号要修改和记录变动
			 */
			if (payment.getMoneyType() != MoneyType.RMB.getCode()) {
				// 1. 修改账户数据
				pstat = conn.prepareStatement("UPDATE user_account SET money=money-?, modify_time=NOW() WHERE userId=? AND moneyType=?");
				pstat.setDouble(1, payment.getMoney());
				pstat.setString(2, user_id);
				pstat.setInt(3, payment.getMoneyType());
				pstat.executeUpdate();
				
				pstat = conn.prepareStatement("SELECT money FROM user_account WHERE userId=? AND moneyType=?");
				pstat.setString(1, user_id);
				pstat.setInt(2, payment.getMoneyType());
				result = pstat.executeQuery();
				if (result.next()) {
					Double cur_money = result.getDouble("money");
					if (cur_money == null || cur_money < 0.0) {
						throw new Exception("账户余额不足");
					}
				} else {
					throw new Exception("此用户账号不存在");
				}
				
				// 2. 生成Payment
				payment.setUserId(user_id);
				payment.setOrderId(order_id);
				payment.setStatus(PaymentStatus.RECORDED.getCode());
				paymentService.savePayment(conn, payment);
				
				// 3. 记录用户虚拟币账户支出
				UserAccountLog record = new UserAccountLog();
				pstat = conn.prepareStatement("INSERT INTO user_account_log(id, order_id, user_id,"
						+ " op_type, money_type, money, memo, date_time) VALUES(?,?,?,?,?,?,?,NOW())");
				pstat.setString(1, record.getId());
				pstat.setString(2, order_id);
				pstat.setString(3, user_id);
				pstat.setInt(4, UserAccountOpType.EXPENDITURE.getCode());
				pstat.setInt(5, payment.getMoneyType());
				pstat.setDouble(6, payment.getMoney());
				pstat.setString(7, "支付虚拟币，订单号"+order_id);
				pstat.executeUpdate();
				log.warn("user_account_logs");
				
				// 4. 记录 一起社区账号收入虚拟币
				YiGatherAccountLog yi_log = new YiGatherAccountLog();
				pstat = conn.prepareStatement("INSERT INTO yigather_account_log(id, order_id, associated_user_id,"
						+ " op_type, money_type, money, date_time, memo, payment_type, operator_id, subject)"
						+ " VALUES(?,?,?,?,?,?,NOW(),?,?,?,?)");
				pstat.setString(1, yi_log.getId());
				pstat.setString(2, order_id);
				pstat.setString(3, user_id);
				pstat.setInt(4, AccountOpType.INCOME.getCode());
				pstat.setInt(5, payment.getMoneyType());
				pstat.setDouble(6, payment.getMoney());
				pstat.setString(7, "");
				pstat.setInt(8, payment.getPaymentType());
				pstat.setString(9, "system");
				String subject = "";
				if (order.getOrderType() == OrderType.BUYING.getCode()) {
					subject = "用户购物支付";
				} else if (order.getOrderType() == OrderType.DEPOSIT.getCode()) {
					subject = "用户充值";
				} else if (order.getOrderType() == OrderType.MEMBERSHIP_RENEWAL.getCode()) {
					subject = "会员续费";
				}
				pstat.setString(10, subject);
				pstat.executeUpdate();
				log.warn("yigather_account_log");
			}
			
			// 累加到订单中的paid_money, 并修改订单状态
			pstat = conn.prepareStatement("UPDATE order_table SET paid_money=paid_money+?, status=IF(paid_money<total_price, ?, ?), modify_time=NOW() WHERE id=?");
			pstat.setDouble(1, payment.getMoney());
			pstat.setInt(2, OrderStatus.CONFIRMING.getCode());
			pstat.setInt(3, OrderStatus.END.getCode());
			pstat.setString(4, order_id);
			pstat.executeUpdate();
			
			Integer new_status = getOrderStatus(conn, order_id);
			// 如果是已完成的充值订单（已付够钱），那就执行充值/发货 
			if (new_status == OrderStatus.END.getCode()) {
				if (order.getOrderType() == OrderType.DEPOSIT.getCode()) {
					// 充值
					userAccountService.doDeposit(conn, user_id, payment.getMoney(), order_id, payment.getAlipayTradeNo(), "");
				} else if (order.getOrderType() == OrderType.BUYING.getCode()){
					// 正式发货给用户
					itemService.doItemDelivery(conn, user_id, order_id);
				} else if (order.getOrderType() == OrderType.MEMBERSHIP_RENEWAL.getCode()){
					// TODO：会员续费
					throw new Exception("会员续费功能未完成");
					
				} else {
					throw new Exception("未知的订单类型: " + order.getOrderType());
				}
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}

	@Override
	public void updatePayment(Payment payment) throws Exception {
		// TODO Auto-generated method stub
		log.warn("updatePayment");
		try {
			Order order = findById(payment.getOrderId());
			/** 
			 * 如果订单不存在 或者 订单状态异常（不是"待支付"/"确认中"的状态）：
			 * 此时，如果支付方式是人民币（支付宝）， 那就待人工审核退款
			 *      如果支付方式是其他，那就直接抛异常，不接受此次支付
			 */
			if(order == null ||
					(order.getStatus() != OrderStatus.UNPAID.getCode() &&
					 order.getStatus() != OrderStatus.CONFIRMING.getCode())){
				// 如果订单不存在
				if (payment.getMoneyType() == MoneyType.RMB.getCode()) {
					pendingRefundService.prepareToRefund(payment);
				}else {
					throw new Exception("此订单不存在或者状态异常，此次支付取消");
				}
			}
			/**
			 * 如果支付方式是支付宝，不涉及用户账户余额变动
			 * 如果支付方式不是支付宝，用户账号要修改和记录变动
			 */
			if (payment.getMoneyType() != MoneyType.RMB.getCode()) {
				//用户消费
				userAccountService.userAccountConsume(payment.getMoney(), order.getUserId(), payment.getMoneyType());
				
				// 2. 生成Payment
				payment.setId(System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(5));
				payment.setUserId(order.getUserId());
				payment.setOrderId(order.getId());
				payment.setStatus(PaymentStatus.RECORDED.getCode());
				paymentService.saveOrUpdate(payment);
				
				// 3. 记录用户虚拟币账户支出
				UserAccountLog record = new UserAccountLog();
				record.setOrderId(order.getId());
				record.setUserId(order.getUserId());
				record.setOpType(UserAccountOpType.EXPENDITURE.getCode());
				record.setMoneyType(payment.getMoneyType());
				record.setMoney(payment.getMoney());
				record.setMemo("支付虚拟币，订单号"+order.getId());
				record.setDateTime(new Timestamp(System.currentTimeMillis()));
				userAccountLogService.saveOrUpdate(record);
				
				// 4. 记录 一起社区账号收入虚拟币
				YiGatherAccountLog yi_log = new YiGatherAccountLog();
				yi_log.setOrderId(order.getId());
				yi_log.setAssociatedUserId(order.getUserId());
				yi_log.setOpType(AccountOpType.INCOME.getCode());
				yi_log.setMoneyType(payment.getMoneyType());
				yi_log.setMoney(payment.getMoney());
				yi_log.setDateTime(new Timestamp(System.currentTimeMillis()));
				yi_log.setMemo("虚拟币收入，订单号"+payment.getOrderId());
				yi_log.setPaymentType(payment.getPaymentType());
				yi_log.setOperatorId("system");
				String subject = "";
				if (order.getOrderType() == OrderType.BUYING.getCode()) {
					subject = "用户购物支付";
				} else if (order.getOrderType() == OrderType.DEPOSIT.getCode()) {
					subject = "用户充值";
				} else if (order.getOrderType() == OrderType.MEMBERSHIP_RENEWAL.getCode()) {
					subject = "会员续费";
				}
				yi_log.setSubject(subject);
				yiGatherAccountLogService.saveOrUpdate(yi_log);
			}
			// 累加到订单中的paid_money, 并修改订单状态
			if(order.getPaidMoney()==null){
				order.setPaidMoney(payment.getMoney());
			}else {
				order.setPaidMoney(order.getPaidMoney()+payment.getMoney());
			}
			order.setStatus(order.getPaidMoney()<order.getTotalPrice()?OrderStatus.CONFIRMING.getCode():OrderStatus.END.getCode());
			order.setModifyTime(new Timestamp(System.currentTimeMillis()));
			order.setMemo(order.getMemo()+",已支付成功");
			order = saveOrUpdate(order);
			
			// 如果是已完成的充值订单（已付够钱），那就执行充值/发货 
			if (order.getStatus() == OrderStatus.END.getCode()) {
				if (order.getOrderType() == OrderType.DEPOSIT.getCode()) {
					// 充值
					userAccountService.doDeposit(payment);
				} else if (order.getOrderType() == OrderType.BUYING.getCode()){
					// 正式发货给用户
					itemService.doItemDelivery(payment);
					//
				} else if (order.getOrderType() == OrderType.MEMBERSHIP_RENEWAL.getCode()){
					// TODO：会员续费
					itemService.doRenewal(payment);
//					throw new Exception("会员续费功能未完成");
				} else {
					throw new Exception("未知的订单类型: " + order.getOrderType());
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
	}

	@Override
	public String getUserIdOfOrder(Connection conn, String order_id) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT user_id FROM order_table WHERE id=?");
			pstat.setString(1, order_id);
			result = pstat.executeQuery();
			if (result.next()) {
				return result.getString("user_id");
			}
			return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}

	@Override
	public List<ItemInstance> getItemInstanceList(Connection conn, String order_id) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		List<ItemInstance> item_list = new ArrayList<ItemInstance>();
		try {
			pstat = conn.prepareStatement("SELECT item_instance.id, sku_id, sku.name as sku_name, "
					+ "item_instance.status, user_id, order_id, sale_price "
					+ "FROM item_instance, sku WHERE order_id=? AND sku.id=item_instance.sku_id");
			pstat.setString(1, order_id);
			result = pstat.executeQuery();
			while (result.next()) {
				ItemInstance instance = new ItemInstance();
				instance.setId(result.getString("id"));
				instance.setOrderId(result.getString("order_id"));
				instance.setUserId(result.getString("user_id"));
				instance.setStatus(result.getInt("status"));
				instance.setSalePrice(result.getDouble("sale_price"));
				SKU sku = new SKU();
				sku.setId(result.getString("sku_id"));
				sku.setName(result.getString("sku_name"));
				instance.setSku(sku);
				item_list.add(instance);
			}
			return item_list;
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}		
	}

	@Override
	public List<Payment> getPaymentList(Connection conn, String order_id) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		List<Payment> payment_list = new ArrayList<Payment>();
		try {
			pstat = conn.prepareStatement("SELECT id, order_id, user_id, payment_type, money_type, money, status, create_time, modify_time "
					+ "FROM payment WHERE order_id=?");
			pstat.setString(1, order_id);
			result = pstat.executeQuery();
			while (result.next()) {
				Payment payment = new Payment();
				payment.setId(result.getString("id"));
				payment.setOrderId(result.getString("order_id"));
				payment.setUserId(result.getString("user_id"));
				payment.setPaymentType(result.getInt("payment_type"));
				payment.setMoneyType(result.getInt("money_type"));
				payment.setMoney(result.getDouble("money"));
				payment.setStatus(result.getInt("status"));
				payment.setCreateTime(result.getTimestamp("create_time"));
				payment.setModifyTime(result.getTimestamp("modify_time"));
				payment_list.add(payment);
			}
			return payment_list;
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	protected boolean userExists(String user_id) throws Exception {
		try {
			User user = userService.findById(user_id);
			if(user==null)
				return false;
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	protected boolean userExists(Connection conn, String user_id) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT id FROM user WHERE id=?");
			pstat.setString(1, user_id);
			result = pstat.executeQuery();
			if (result.next() && result.getString("id") != null) {
				return true;
			}
			return false;
		}
		catch(Exception e) {
			return false;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	
	protected Order getOrderById(Connection conn, String order_id) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT order_table.id,order_table.memo,order_type, status,"
					+ " user_id, user.nickname, total_price, paid_money, create_time, modify_time"
					+ " FROM order_table LEFT JOIN user ON order_table.user_id=user.id"
					+ " WHERE order_table.id=?");
			pstat.setString(1, order_id);
			result = pstat.executeQuery();
			if (result.next()) {
				Order order = new Order();
				order.setId(result.getString("id"));
				order.setMemo(result.getString("memo"));
				order.setOrderType(result.getInt("order_type"));
				order.setStatus(result.getInt("status"));
				order.setUserId(result.getString("user_id"));
				order.setUserNickname(result.getString("nickname"));
				order.setTotalPrice(result.getDouble("total_price"));
				order.setPaidMoney(result.getDouble("paid_money"));
				order.setCreateTime(result.getTimestamp("create_time"));
				order.setModifyTime(result.getTimestamp("modify_time"));
				return order;
			}
			return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}

	/**
	 * 直接修改订单状态
	 * @param conn
	 * @param order_id
	 * @param new_status
	 * @throws Exception
	 */
	protected void setOrderStatus(Connection conn, String order_id, int new_status) throws Exception {
		PreparedStatement pstat = null;
		try {
			pstat = conn.prepareStatement("UPDATE order_table SET status=?, modify_time=NOW() WHERE id=?");
			pstat.setInt(1, new_status);
			pstat.setString(2, order_id);
			pstat.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	/**
	 * 查询订单状态
	 * @param conn
	 * @param order_id
	 * @throws Exception
	 */
	protected Integer getOrderStatus(Connection conn, String order_id) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT status FROM order_table WHERE id=?");
			pstat.setString(1, order_id);
			result = pstat.executeQuery();
			if (result.next()) {
				return result.getInt("status");
			}
			return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	/**
	 * 使订单失效，订单中的商品放回库存, 已付的款退回原账号
	 * @param order
	 * @param new_status
	 * @param memo
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月14日 下午2:11:26
	 */
	protected void revokeOrder(Order order, int new_status, String memo) throws Exception{
		try {
//			Order order = findById(order_id);
			// 0. 先检查订单状态
			if(order.getStatus()==OrderStatus.CANCELED.getCode()||order.getStatus()==OrderStatus.EXPIRED.getCode()||order.getStatus() == OrderStatus.END.getCode())
				return;
			
			// 1. 销毁订单里的商品实例
			List<ItemInstance> instances = itemInstanceService.findByOrderId(order.getId());
			Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
			List<ItemInstance> instancesList = new ArrayList<ItemInstance>();
			List<ItemInstanceLog> instanceLogList = new ArrayList<ItemInstanceLog>();
			for (ItemInstance instance : instances) {
				String sku_id = instance.getSku()==null?null:instance.getSku().getId();
				if(sku_id!=null){
					Integer count = ht.get(sku_id);
					if (count==null) {
						ht.put(sku_id, new Integer(1));
					} else {
						ht.put(sku_id, count+1);
					}
				}
				// 记录销毁商品实例
				ItemInstanceLog item_log = new ItemInstanceLog();
				item_log.setItemInstanceId(instance.getId());
				item_log.setOpType(ItemInstanceLog.OpType.CANCEL.getCode());
				item_log.setOrderId(order.getId());
				item_log.setSkuId(sku_id);
				item_log.setMemo("订单取消, 销毁商品实例");
				item_log.setDateTime(new Timestamp(System.currentTimeMillis()));
//					itemInstanceLogService.saveOrUpdate(item_log);
				instanceLogList.add(item_log);
				
				instance.setStatus(ItemInstanceStatus.DESTROYED.getCode());
				instance.setModifyTime(new Timestamp(System.currentTimeMillis()));
//					itemInstanceService.saveOrUpdate(instance);
				instancesList.add(instance);
			}
			itemInstanceLogService.saveOrUpdate(instanceLogList);
			itemInstanceService.saveOrUpdate(instancesList);
			
			// 2. 修改库存，把订单里的商品数量加回去
			for (Iterator it=ht.keySet().iterator(); it.hasNext();) {
				String sku_id = (String)it.next();
				Integer amount = (Integer)ht.get(sku_id);
				
				// 加库存
				SkuInventory inventory = skuInventoryService.findById(sku_id);
				if(!inventory.getIsUnlimited()){
					inventory.setAmount(inventory.getAmount()+amount);
					inventory.setModifyTime(new Timestamp(System.currentTimeMillis()));
					skuInventoryService.saveOrUpdate(inventory);
				}
				// 记录入库
				// 记录入库
				YigatherItemInventoryLog record = new YigatherItemInventoryLog();
				record.setSkuId(sku_id);
				record.setAmount(amount);
				record.setDateTime(new Timestamp(System.currentTimeMillis()));
				record.setMemo(String.format("%s, 订单号%s", memo, order.getId()));
				record.setOpType(YigatherItemInventoryLog.OpType.INCOME.getCode());
				yigatherItemInventoryLogService.saveOrUpdate(record);
			}
			
			/**
			 * 3. 已付的钱，如果是人民币，预备退款, 等待人工审核；
			 *            如果不是人民币，直接退回原账号
			 */
			List<Payment> payments = paymentService.findByOrderIdAndStatus(order.getId(), PaymentStatus.RECORDED.getCode());
			for (Payment payment : payments) {
				String id = payment.getId();
				Integer money_type = payment.getMoneyType();
				Double money = payment.getMoney();
				if (money_type == MoneyType.RMB.getCode()) {
					// 预退款
					String alipay_trade_no = payment.getAlipayTradeNo();
					String buyer_id = payment.getBuyerId();
					String buyer_email = payment.getBuyerEmail();
					String bank_seq_no = payment.getBankSeqNo();
					paymentService.prepareToRefund(payment, "订单取消，退款");
					paymentService.setPaymentStatus(payment.getId(), PaymentStatus.PREPARE_TO_REFUND.getCode());
				} else {
					// 直接退回原账号
					userAccountService.doDeposit(payment);
					paymentService.setPaymentStatus(payment.getId(), PaymentStatus.REFUNDED.getCode());
				}		
			}
			// 4. 修改订单状态
			order.setStatus(new_status);
			order.setModifyTime(new Timestamp(System.currentTimeMillis()));
			order.setMemo(order.getMemo()+",支付失败");
			saveOrUpdate(order);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
		
		
	}
		
	/**
	 * 使订单失效，订单中的商品放回库存, 已付的款退回原账号
	 * @param conn
	 * @param order_id
	 * @param new_status
	 * @param memo
	 * @throws Exception
	 */
	protected void revokeOrder(Connection conn, String order_id, int new_status, String memo) throws Exception {
		PreparedStatement pstat = null;
		PreparedStatement pstat2 = null;
		ResultSet result = null;
		try {
			String user_id = "";
			
			// 0. 先检查订单状态
			pstat = conn.prepareStatement("SELECT user_id, status FROM order_table WHERE id=? ");
			pstat.setString(1, order_id);
			result = pstat.executeQuery();
			if (result.next()) {
				user_id = result.getString("user_id");
				Integer status = result.getInt("status");
				if (status == OrderStatus.CANCELED.getCode() ||
					status == OrderStatus.EXPIRED.getCode() ||
					status == OrderStatus.END.getCode()) {
					return;
				}
			}
			
			// 1. 销毁订单里的商品实例
			pstat = conn.prepareStatement("SELECT id, sku_id FROM item_instance WHERE order_id=? ");
			pstat.setString(1, order_id);
			result = pstat.executeQuery();
			Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
			while(result.next()) {
				String sku_id = result.getString("sku_id");
				if(sku_id!=null){
					Integer count = ht.get(sku_id);
					if (count==null) {
						ht.put(sku_id, new Integer(1));
					} else {
						ht.put(sku_id, count+1);
					}
					
					// 记录销毁商品实例
					ItemInstanceLog item_log = new ItemInstanceLog();
					pstat2 = conn.prepareStatement("INSERT INTO item_instance_log(id, item_instance_id, op_type, order_id, sku_id, memo, date_time) VALUES(?,?,?,?,?,?,NOW())");
					pstat2.setString(1, item_log.getId());
					pstat2.setString(2, result.getString("id"));
					pstat2.setInt(3, ItemInstanceLog.OpType.CANCEL.getCode());
					pstat2.setString(4, order_id);
					pstat2.setString(5, result.getString("sku_id"));
					pstat2.setString(6, "订单取消, 销毁商品实例");
					pstat2.executeUpdate();
				}
			}
			pstat = conn.prepareStatement("UPDATE item_instance SET status=?, modify_time=NOW() WHERE order_id=?");
			pstat.setInt(1, ItemInstanceStatus.DESTROYED.getCode());
			pstat.setString(2, order_id);
			pstat.executeUpdate();
		
			// 2. 修改库存，把订单里的商品数量加回去
			for (Iterator it=ht.keySet().iterator(); it.hasNext();) {
				String sku_id = (String)it.next();
				Integer amount = (Integer)ht.get(sku_id);
				
				// 加库存
				pstat = conn.prepareStatement("UPDATE sku_inventory SET amount=amount+?, modify_time=NOW() WHERE sku_id=?");
				pstat.setInt(1, amount);
				pstat.setString(2, sku_id);
				pstat.executeUpdate();
			
				// 记录入库
				pstat = conn.prepareStatement("INSERT INTO yigather_item_log(id, sku_id, amount, date_time, memo, op_type) VALUES(?,?,?,NOW(),?,?)");
				YigatherItemInventoryLog record = new YigatherItemInventoryLog();
				pstat.setString(1, record.getId());
				pstat.setString(2, sku_id);
				pstat.setInt(3, amount);
				pstat.setString(4, String.format("%s, 订单号%s", memo, order_id));
				pstat.setInt(5, YigatherItemInventoryLog.OpType.INCOME.getCode());
				pstat.executeUpdate();
			}

			/**
			 * 3. 已付的钱，如果是人民币，预备退款, 等待人工审核；
			 *            如果不是人民币，直接退回原账号
			 */
			pstat = conn.prepareStatement(
					"SELECT * FROM payment WHERE order_id=? AND status=?");
			pstat.setString(1, order_id);
			pstat.setInt(2, PaymentStatus.RECORDED.getCode());
			result = pstat.executeQuery();
			while (result.next()) {
				String id = result.getString("id");
				Integer money_type = result.getInt("money_type");
				Double money = result.getDouble("money");
				if (money_type == MoneyType.RMB.getCode()) {
					// 预退款
					String alipay_trade_no = result.getString("alipay_trade_no");
					String buyer_id = result.getString("buyer_id");
					String buyer_email = result.getString("buyer_email");
					String bank_seq_no = result.getString("bank_seq_no");
					paymentService.prepareToRefund(conn, user_id, money, order_id, alipay_trade_no, buyer_id, buyer_email, bank_seq_no, "订单取消，退款");
					paymentService.setPaymentStatus(conn, id, PaymentStatus.PREPARE_TO_REFUND.getCode());
				} else {
					// 直接退回原账号
					userAccountService.doDeposit(conn, user_id, money, order_id, "", "退款, 订单号"+order_id);
					paymentService.setPaymentStatus(conn, id, PaymentStatus.REFUNDED.getCode());
				}				
			}
			
			// 4. 修改订单状态
			pstat = conn.prepareStatement("UPDATE order_table SET status=?, modify_time=NOW() WHERE id=?");
			pstat.setInt(1, new_status);
			pstat.setString(2, order_id);
			pstat.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat2 != null) {
				pstat2.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}

	@Override
	public Order saveOrUpdate(Order entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public Order findById(String id) {
		// TODO Auto-generated method stub
		Order order = repository.findOne(id);
		if(order.getBusinessType().equals(Activity.entityName)){
			order.setBusinessTitle(activityService.findById(order.getBusinessId()).getTitle());
		}else if (order.getBusinessType().equals(Course.entityName)) {
			order.setBusinessTitle(courseService.findById(order.getBusinessId()).getTitle());
		}
		return order;
	}

	@Override
	public Order createOrder(String jsontext,String memo,String busi_type,String busi_id, Integer p, User user,
			String skuid,Integer quantity,Date endTime, SkuInventory inventory) throws Exception{
		// TODO Auto-generated method stub
		try {
			if(user==null){
				throw new Exception("this user do not exist---createOrder");
			}
			if(quantity<0){
				throw new Exception("quantity is a illegal number");
			}
			SKU sku = skuService.findById(skuid);
			if(sku==null){
				throw new Exception("do not exist this sku:"+skuid);
			}
			if(!inventory.getIsUnlimited()){//有限--减库存
				if(inventory.getAmount()<quantity){
					throw new Exception("inventory not enough");
				}else {
					inventory.setAmount(inventory.getAmount()-quantity);
					skuInventoryService.saveOrUpdate(inventory);
				}
			}
			// 1. 创建Order对象
			Order order = new Order();
			order.setUserId(user.getId());
			order.setOrderType(OrderType.BUYING.getCode());
			order.setStatus(OrderStatus.UNPAID.getCode());
			order.setCreateTime(new Timestamp(System.currentTimeMillis()));
			order.setModifyTime(order.getCreateTime());
			order.setMemo(memo);
			order.setBusinessId(busi_id);
			order.setBusinessType(busi_type);
			if(sku.getMultPrice().equals("")){//单价格
				if(user.getRoot()==UserRoot.REGISTER.getCode()||user.getRoot()==UserRoot.OVREDUE.getCode())//非会员
					order.setTotalPrice(sku.getDefaultPrice());
				else//会员
					order.setTotalPrice(sku.getMemberPrice());
			}else {//多价格
				String[] prices = sku.getMultPrice().split(",");
				order.setTotalPrice(Double.valueOf(prices[p]));
			}
			order.setJsontext(jsontext);//报名表单
//			order.setTotalPrice(0.01);//test
			saveOrUpdate(order);
			for (int i = 0; i < quantity; i++) {
				//商品实例入库
				ItemInstance instance = new ItemInstance();
				instance.setStatus(ItemInstanceStatus.UNDELIVERED.getCode());
				instance.setOrderId(order.getId());
				instance.setSku(sku);
				instance.setSalePrice(order.getTotalPrice());
				instance.setCreateTime(new Timestamp(System.currentTimeMillis()));
				instance.setModifyTime(new Timestamp(System.currentTimeMillis()));
				itemInstanceService.saveOrUpdate(instance);
				
				//商品实例记录
				ItemInstanceLog item_log = new ItemInstanceLog();
				item_log.setItemInstanceId(instance.getId());
				item_log.setOpType(ItemInstanceLog.OpType.CREATE.getCode());
				item_log.setOrderId(order.getId());
				item_log.setSkuId(sku.getId());
				item_log.setMemo("生成订单"+order.getId()+"，系统自动创建商品实例");
				item_log.setDateTime(new Timestamp(System.currentTimeMillis()));
				itemInstanceLogService.saveOrUpdate(item_log);
				
				// 记录出货
				YigatherItemInventoryLog record = new YigatherItemInventoryLog();
				record.setSkuId(sku.getId());
				record.setAmount(1);
				record.setOrderId(order.getId());
				record.setMemo("生成订单"+order.getId()+", 系统自动出货");
				record.setOpType(YigatherItemInventoryLog.OpType.DELIVERY.getCode());
				record.setDateTime(new Timestamp(System.currentTimeMillis()));
				yigatherItemInventoryLogService.saveOrUpdate(record);
			}
			return order;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public Order becomeVip(User user) throws Exception {
		// TODO Auto-generated method stub
		try {
			if(user==null)
				throw new Exception("this user do not exist---becomeVip");
			
			// 1. 创建Order对象
			Order order = new Order();
			order.setUserId(user.getId());
			order.setMemo("您的会员续费");
			order.setOrderType(OrderType.MEMBERSHIP_RENEWAL.getCode());
			order.setStatus(OrderStatus.UNPAID.getCode());
			order.setCreateTime(new Timestamp(System.currentTimeMillis()));
			order.setModifyTime(order.getCreateTime());
			order.setTotalPrice(R.User.MEMBERSHIP_ANNUAL_FEE);
//			order.setTotalPrice(0.01);//test
			saveOrUpdate(order);
			ItemInstance instance = new ItemInstance();
			instance.setStatus(ItemInstanceStatus.UNDELIVERED.getCode());
			instance.setOrderId(order.getId());
//			instance.setSku(sku);
			instance.setSalePrice(order.getTotalPrice());
			instance.setCreateTime(new Timestamp(System.currentTimeMillis()));
			instance.setModifyTime(new Timestamp(System.currentTimeMillis()));
			instance.setEffective(0);
			itemInstanceService.saveOrUpdate(instance);
			
			//商品实例记录
			ItemInstanceLog item_log = new ItemInstanceLog();
			item_log.setItemInstanceId(instance.getId());
			item_log.setOpType(ItemInstanceLog.OpType.CREATE.getCode());
			item_log.setOrderId(order.getId());
//			item_log.setSkuId(sku.getId());
			item_log.setMemo("生成订单"+order.getId()+"，系统自动创建商品实例");
			item_log.setDateTime(new Timestamp(System.currentTimeMillis()));
			itemInstanceLogService.saveOrUpdate(item_log);
			
			// 记录出货
			YigatherItemInventoryLog record = new YigatherItemInventoryLog();
//			record.setSkuId(sku.getId());
			record.setAmount(1);
			record.setOrderId(order.getId());
			record.setMemo("生成订单"+order.getId()+", 系统自动出货");
			record.setOpType(YigatherItemInventoryLog.OpType.DELIVERY.getCode());
			record.setDateTime(new Timestamp(System.currentTimeMillis()));
			yigatherItemInventoryLogService.saveOrUpdate(record);
			
			return order;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public Page<Order> orderList(final String userid, final Integer status,
			Integer page, Integer size,final String openid,final String tel) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createTime"));
		Specification<Order> spec = new Specification<Order>() {
			
			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				if(userid!=null){
					ps.add(cb.equal(root.<String>get("userId"), userid));
				}
				if(openid!=null&&tel!=null){
					ps.add(cb.or(cb.equal(root.<String>get("openid"),openid ),cb.equal(root.<String>get("tel"), tel)));
				}
				if(status!=null){
					ps.add(cb.equal(root.<Integer>get("status"), status));
				}
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public Double countConsumeTotal(final String userid) {
		Double sum=0.0;
		EntityManager em = emf.createEntityManager();
		StringBuffer sb=new StringBuffer();
		sb.append("select sum(o.total_price) from order_table o where ");
		sb.append("o.user_id='").append(userid).append("' and (o.order_type=2 or o.order_type=3) and o.status=4");
		Query query = em.createNativeQuery(sb.toString());
		try{
			sum = Double.valueOf(query.getSingleResult().toString());
		}catch(Exception e){
			sum=0.0;
		}
		return sum;//
	}
	
	@Override
	public void buycoffeemanage(String orderid,Integer cof_num) {
		//如果是邀请函模块产生的订单 则需要通知咖啡的接受者 以及修改当前邀请函里面的是否购买咖啡的标志
		Order order=repository.findOne(orderid);
		String invitationsid= order.getJsontext();
		if(invitationsid!=null&&invitationsid.length()>0){
			Invitations invitations=invitationsService.findInvitations(invitationsid);
			if(invitations!=null){
				invitations.setCoffee("Y");
				invitationsService.saveOrUpdateInvitations(invitations);
				String receiveuserId="";
				String usernickname="";
				if(order.getUserId().equals(invitations.getUser().getId())){
					receiveuserId=invitations.getInviteuser().getId();
					usernickname=invitations.getUser().getNickname();
				}else{
					receiveuserId=invitations.getUser().getId();
					usernickname=invitations.getInviteuser().getNickname();
				}
				
				//需要通知接受者 
				NoticeMsg msg=new NoticeMsg();
				msg.setActiontype(NoticeActionTypeValue.ANSWER.value);
				msg.setContents(usernickname+"送了你"+cof_num+"杯咖啡");
				msg.setIcon("coffee");
				msg.setMsgtype(NoticeMsgTypeValue.COFFEE.value);
				msg.setParam(receiveuserId);
				Set set=new HashSet<User>();
				set.add(userService.findById(receiveuserId));
				msg.setTags(set);
				noticeMsgService.saveOrUpdate(msg);
				
				JSONObject msg_extras = new JSONObject();
				msg_extras.put("type", NoticeMsgTypeValue.COFFEE.value);//协商 跳个人钱包的限时劵列表
				msg_extras.put("parameter", receiveuserId);
				msg_extras.put("icon", "coffee");
				msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
				pushService.push(usernickname+"送了你"+cof_num+"杯咖啡", "收到咖啡",msg_extras, Platform.android_ios(), new String[]{receiveuserId}, new String[]{"version4","userstart0"});
				
			}
		}
		
	}

	@Override
	public Order createOrder(String memo,User user, String skuid, Integer quantity, SkuInventory inventory,Order order) throws Exception {
		try {
			if(user==null){
				throw new Exception("this user do not exist---createOrder");
			}
			if(quantity<0){
				throw new Exception("quantity is a illegal number");
			}
			SKU sku = skuService.findById(skuid);
			if(sku==null){
				throw new Exception("do not exist this sku:"+skuid);
			}
			if(!inventory.getIsUnlimited()){//有限--减库存
				if(inventory.getAmount()<quantity){
					throw new Exception("inventory not enough");
				}else {
					inventory.setAmount(inventory.getAmount()-quantity);
					skuInventoryService.saveOrUpdate(inventory);
				}
			}
			if(user.getRoot() >= UserRoot.OVREDUE.getCode()){//过期会员权限以下的，非会员价
				order.setTotalPrice(order.getTotalPrice()+sku.getDefaultPrice());
			}else {//会员价
				order.setTotalPrice(order.getTotalPrice()+sku.getMemberPrice());
			}
			for (int i = 0; i < quantity; i++) {
				//商品实例入库
				ItemInstance instance = new ItemInstance();
				instance.setStatus(ItemInstanceStatus.UNDELIVERED.getCode());
				instance.setOrderId(order.getId());
				instance.setSku(sku);
				if(user.getRoot() >= UserRoot.OVREDUE.getCode()){//过期会员权限以下的，非会员价
					instance.setSalePrice(sku.getDefaultPrice());
				}else {//会员价
					instance.setSalePrice(sku.getMemberPrice());
				}
//				instance.setSalePrice(order.getTotalPrice());
				instance.setCreateTime(new Timestamp(System.currentTimeMillis()));
				instance.setModifyTime(new Timestamp(System.currentTimeMillis()));
				itemInstanceService.saveOrUpdate(instance);

				//商品实例记录
				ItemInstanceLog item_log = new ItemInstanceLog();
				item_log.setItemInstanceId(instance.getId());
				item_log.setOpType(ItemInstanceLog.OpType.CREATE.getCode());
				item_log.setOrderId(order.getId());
				item_log.setSkuId(sku.getId());
				item_log.setMemo("生成订单"+order.getId()+"，系统自动创建商品实例");
				item_log.setDateTime(new Timestamp(System.currentTimeMillis()));
				itemInstanceLogService.saveOrUpdate(item_log);

				// 记录出货
				YigatherItemInventoryLog record = new YigatherItemInventoryLog();
				record.setSkuId(sku.getId());
				record.setAmount(1);
				record.setOrderId(order.getId());
				record.setMemo("生成订单"+order.getId()+", 系统自动出货");
				record.setOpType(YigatherItemInventoryLog.OpType.DELIVERY.getCode());
				record.setDateTime(new Timestamp(System.currentTimeMillis()));
				yigatherItemInventoryLogService.saveOrUpdate(record);
			}
			return order;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}
}
