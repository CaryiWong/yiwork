package cn.yi.gather.v20.service.serviceImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v20.dao.ItemClassRepository;
import cn.yi.gather.v20.dao.ItemInstanceRepository;
import cn.yi.gather.v20.dao.SKURepository;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Course;
import cn.yi.gather.v20.entity.ItemClass;
import cn.yi.gather.v20.entity.ItemClass.ItemClassStatus;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.ItemInstance.ItemInstanceStatus;
import cn.yi.gather.v20.entity.ItemInstanceLog;
import cn.yi.gather.v20.entity.ItemInstanceLog.OpType;
import cn.yi.gather.v20.entity.Order;
import cn.yi.gather.v20.entity.Payment;
import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.entity.SKU.SKUStatus;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.entity.VipNumLog;
import cn.yi.gather.v20.entity.YigatherItemInventoryLog;
import cn.yi.gather.v20.service.IActivityService;
import cn.yi.gather.v20.service.ICourseService;
import cn.yi.gather.v20.service.IItemInstanceLogService;
import cn.yi.gather.v20.service.IItemInstanceService;
import cn.yi.gather.v20.service.IItemService;
import cn.yi.gather.v20.service.IOrderService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IVipNumLogService;

import com.common.R;

/**
 * 商品操作
 */
@Service("itemServiceV20")
public class ItemService implements IItemService{
	private static Logger log = Logger.getLogger(ItemService.class);
	
	@Resource(name = "itemClassRepositoryV20")
	private ItemClassRepository repository;
	
	@Resource(name = "itemInstanceRepositoryV20")
	private ItemInstanceRepository itemInstanceRepository;
	
	@Resource(name = "sKURepositoryV20")
	private SKURepository sku_repository;
	
	@Resource(name = "itemInstanceServiceV20")
	private IItemInstanceService itemInstanceService;
	
	@Resource(name = "itemInstanceLogServiceV20")
	private IItemInstanceLogService itemInstanceLogService;
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "orderServiceV20")
	private IOrderService orderService;
	
	@Resource(name = "activityServiceV20")
	private IActivityService activityService;
	
	@Resource(name = "courseServiceV20")
	private ICourseService courseService;
	
	@Resource(name = "vipNumLogServiceV20")
	private IVipNumLogService vipNumLogService;
	
	@Override
	public List<ItemClass> getItemClassList() {
		return repository.findByStatus(ItemClassStatus.NORMAL.getCode());
	}
	
	@Override
	public ItemClass getItemClass(Long item_class_id) {
		return repository.findById(item_class_id);
	}

	@Override
	public void addItemClass(String name) {
		ItemClass item_class = new ItemClass();
		item_class.setName(name);
		item_class.setStatus(ItemClassStatus.NORMAL.getCode());
		repository.save(item_class);
	}
	
	@Override
	public int modifyItemClass(Long id, String name) {
		ItemClass item_class = repository.findById(id);
		if (item_class == null || item_class.getStatus() != ItemClassStatus.NORMAL.getCode()) {
			return 1;
		}
		item_class.setName(name);
		repository.save(item_class);
		return 0;
	}
	
	@Override
	public void deleteItemClass(Long id) {
		ItemClass item_class = repository.findById(id);
		if (item_class != null) {
			item_class.setStatus(ItemClassStatus.DELETED.getCode());
			repository.save(item_class);
		}
	}
	
	@Override
	public List<SKU> getSKUListByItemClassId(Long item_class_id) {
		// TODO Auto-generated method stub
		return sku_repository.findByItemClassId(item_class_id);
	}
	
	@Override
	public List<SKU> findSKUListByItemClassName(String item_class_name) {
		// TODO Auto-generated method stub
		return sku_repository.findByItemClassName(item_class_name);
	}
	
	@Override
	public com.common.Page<SKU> getSKUListForPage(Integer page, Integer size, final Long item_class_id) {
		com.common.Page<SKU> result = new com.common.Page<SKU>();
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.ASC, "id"));
		Specification<SKU> spec = new Specification<SKU>() {
			@Override
			public Predicate toPredicate(Root<SKU> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p0 = null;
				if(item_class_id != null){
					p0 = cb.and(cb.equal(root.<ItemClass>get("item_class"), item_class_id),
							cb.notEqual(root.<ItemClass>get("status"), SKUStatus.DELETED.getCode()));
				}else {
					p0 = cb.and(cb.isNotNull(root.<String>get("id")),
							cb.notEqual(root.<ItemClass>get("status"), SKUStatus.DELETED.getCode()));
				}
				query.where(p0);
				return query.getRestriction();
			}
		};
		Page<SKU> skus = sku_repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(skus.getNumber());
		result.setPageSize(skus.getSize());
		result.setResult(skus.getContent());
		result.setTotalCount((int)skus.getTotalElements());
		return result;
	}
	
	@Override
	public SKU getSku(String sku_id) {
		// TODO Auto-generated method stub
		return sku_repository.findById(sku_id);
	}
	
	@Override
	public void addSku(Connection conn, Long item_class_id, String sku_name,
			Double default_price, Double member_price, Integer is_unlimited) throws Exception{
		PreparedStatement pstat = null;
		try {
			String sku_id = String.format("%d-%d", item_class_id, System.currentTimeMillis());
			pstat = conn.prepareStatement(
					"INSERT INTO sku(id, name, status, item_class_id, default_price, member_price) "
					+ " VALUES(?,?,?,?,?,?)");
			pstat.setString(1, sku_id);
			pstat.setString(2, sku_name);
			pstat.setInt(3, SKUStatus.NOTSELLING.getCode());
			pstat.setLong(4, item_class_id);
			pstat.setDouble(5, default_price);
			pstat.setDouble(6, member_price);
			pstat.executeUpdate();
			
			pstat = conn.prepareStatement(
					"INSERT INTO sku_inventory(sku_id, amount, is_unlimited, create_time, modify_time) "
					+ " VALUES(?,?,?,NOW(),NOW())");
			pstat.setString(1, sku_id);
			pstat.setInt(2, 0);
			pstat.setInt(3, is_unlimited);
			pstat.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	@Override
	public int modifySku(String sku_id, Long item_class_id,  String sku_name,
			Double default_price, Double member_price) {
		SKU sku = sku_repository.findById(sku_id);
		if (sku == null || sku.getStatus() == SKUStatus.DELETED.getCode()) {
			return 1;
		}
		ItemClass item_class = repository.findById(item_class_id);
		if (item_class == null) {
			return 1;
		}
		sku.setItemClass(item_class);
		sku.setName(sku_name);
		sku.setDefaultPrice(default_price);
		sku.setMemberPrice(member_price);
		sku_repository.save(sku);
		return 0;
	}
	
	@Override
	public void deleteSku(String sku_id) {
		SKU sku = sku_repository.findById(sku_id);
		if (sku == null || sku.getStatus() == SKUStatus.DELETED.getCode()) {
			return;
		}
		sku.setStatus(SKUStatus.DELETED.getCode());
		sku_repository.save(sku);
	}
	
	@Override
	public int putOnShelves(String sku_id) {
		SKU sku = sku_repository.findById(sku_id);
		if (sku == null || sku.getStatus() == SKUStatus.DELETED.getCode()) {
			return 1;
		}
		sku.setStatus(SKUStatus.SELLING.getCode());
		sku_repository.save(sku);
		return 0;
	}
	
	@Override
	public int getOffShelves(String sku_id) {
		SKU sku = sku_repository.findById(sku_id);
		if (sku == null || sku.getStatus() == SKUStatus.DELETED.getCode()) {
			return 1;
		}
		sku.setStatus(SKUStatus.NOTSELLING.getCode());
		sku_repository.save(sku);
		return 0;
	}
	
	@Override
	public com.common.Page<YigatherItemInventoryLog> getYigatherItemInventoryLog(
			Connection conn, String sku_id, Integer page, Integer page_size) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		List<YigatherItemInventoryLog> array_list = new ArrayList<YigatherItemInventoryLog>();
		int total_count = 0;
		try {
			pstat = conn.prepareStatement("SELECT COUNT(*) as total_count FROM yigather_item_log WHERE sku_id=?");
			pstat.setString(1, sku_id);
			result = pstat.executeQuery();
			if (result.next()) {
				total_count = result.getInt("total_count");
			}
			
			pstat = conn.prepareStatement(
					"SELECT yigather_item_log.id, op_type, amount, order_id, operator_id, user.nickname, date_time, memo "
					+ " FROM  yigather_item_log LEFT JOIN user"
					+ " ON yigather_item_log.operator_id=user.id"
					+ " WHERE sku_id=?"
					+ " ORDER BY date_time DESC"
					+ " LIMIT ?, ?");
			pstat.setString(1, sku_id);
			
			if (page_size < 1) {
				page_size = 1;
			}
			if (page > (total_count-1)/page_size + 1) {
				page = (total_count-1)/page_size + 1;
			}
			if (page < 1) {
				page = 1;
			}
			int offset = 0;
			if (page > 1) {
				offset = (page-1) * page_size;
			}
			pstat.setInt(2, offset);
			pstat.setInt(3, page_size);			
			result = pstat.executeQuery();
			while (result.next()) {
				YigatherItemInventoryLog record = new YigatherItemInventoryLog();
				record.setId(result.getString("id"));
				record.setOpType(result.getInt("op_type"));
				record.setAmount(result.getInt("amount"));
				record.setOrderId(result.getString("order_id"));
				record.setOperatorId(result.getString("operator_id"));
				record.setOperatorName(result.getString("nickname"));
				record.setDateTime(result.getTimestamp("date_time"));
				record.setMemo(result.getString("Memo"));
				array_list.add(record);
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
		
		com.common.Page<YigatherItemInventoryLog> paged_result = new com.common.Page<YigatherItemInventoryLog>();
		paged_result.setCurrentPage(page);
		paged_result.setPageSize(page_size);
		paged_result.setCurrentCount(array_list.size());
		paged_result.setTotalCount(total_count);
		paged_result.setResult(array_list);
		return paged_result;
	}
	
	
	@Override
	public void doItemDelivery(Connection conn, String user_id, String order_id) throws Exception {
		PreparedStatement pstat = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		try {
			// 设置状态和属主
			pstat = conn.prepareStatement("UPDATE item_instance SET status=?, user_id=?, modify_time=NOW() WHERE order_id=?");
			pstat.setInt(1, ItemInstanceStatus.UNUSED.getCode());
			pstat.setString(2, user_id);
			pstat.setString(3, order_id);
			pstat.executeUpdate();
			
			pstat = conn.prepareStatement(
					"SELECT id, sku_id FROM item_instance" +
					" WHERE order_id=?");
			pstat.setString(1, order_id);
			result = pstat.executeQuery();
			while (result.next()) {
				// 记录发货
				ItemInstanceLog item_log = new ItemInstanceLog();
				pstat = conn.prepareStatement("INSERT INTO item_instance_log(id, item_instance_id, op_type, order_id, sku_id, user_id, memo, date_time) VALUES(?,?,?,?,?,?,?,NOW())");
				pstat.setString(1, item_log.getId());
				pstat.setString(2, result.getString("id"));
				pstat.setInt(3, ItemInstanceLog.OpType.DELIVER.getCode());
				pstat.setString(4, order_id);
				pstat.setString(5, result.getString("sku_id"));
				pstat.setString(6, user_id);
				pstat.setString(7, "支付完成，发货");
				pstat.executeUpdate();
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (stat != null) {
				stat.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	@Override
	public void doItemDelivery(Payment payment) throws Exception {
		// TODO Auto-generated method stub
		log.warn("doItemDelivery:"+new java.util.Date());
		try {
//			itemInstanceService.updateByOrderId(ItemInstanceStatus.UNUSED.getCode(), new Timestamp(System.currentTimeMillis()), payment.getOrderId());
			
			List<ItemInstance> instances = itemInstanceService.findByOrderIdAndStatus(payment.getOrderId(), null);
			List<ItemInstanceLog> logs = new ArrayList<ItemInstanceLog>();
			List<ItemInstance> itemInstances = new ArrayList<ItemInstance>();
			int coffee_num = 0;
			for (int j = 0; j < instances.size(); j++) {
				ItemInstance i = instances.get(j);
				ItemInstanceLog l = new ItemInstanceLog();
				l.setItemInstanceId(i.getId());
				l.setOpType(ItemInstanceLog.OpType.DELIVER.getCode());
				l.setOrderId(i.getOrderId());
				l.setSkuId(i.getSku().getId());
				l.setUserId(i.getUserId());
				l.setMemo("支付完成，发货");
				l.setDateTime(new Timestamp(System.currentTimeMillis()));
				logs.add(l);
				
				i.setModifyTime(new Timestamp(System.currentTimeMillis()));
				i.setStatus(ItemInstanceStatus.UNUSED.getCode());
				itemInstances.add(i);
				
				//商品实例关联相关的业务
				if(i.getSku().getItemClass().getCode().equals(Activity.entityName)){
					log.warn(Activity.entityName+"");
					Order order = orderService.findById(i.getOrderId());
					Activity activity = activityService.findBySkuId(i.getSku().getId());
					activityService.signActivity(order.getUserId(), order.getJsontext(), activity.getId());
					if(activity.getActivityType().equals(Activity.ActivityTypeValue.SUB)&&j==0){//若是子活动，往eventlog记录一条父活动记录
						Activity activity_parent = activityService.findById(activity.getPid());
						activityService.signActivity(order.getUserId(), order.getJsontext(), activity_parent.getId());
					}
				}else if(i.getSku().getItemClass().getCode().equals(Course.entityName)){
					Order order = orderService.findById(i.getOrderId());
					Course course = courseService.findBySkuid(i.getSku().getId());
					courseService.singCourse(order.getUserId(), order.getJsontext(), course.getId());
				}else if(i.getSku().getItemClass().getCode().equals("coffee")&&i.getSku().getCoffeetype().equals("giveaway")){
					/*  买咖啡回调  */
					coffee_num ++;
					
				}
			}
			if(coffee_num>0)
				orderService.buycoffeemanage(payment.getOrderId(),coffee_num);
			itemInstanceService.saveOrUpdate(itemInstances);
			itemInstanceLogService.saveOrUpdate(logs);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("doItemDelivery failed!exception:"+e);
		}
	}

	@Override
	public String getSkuInventory(Connection conn, String sku_id) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT is_unlimited, amount FROM sku_inventory WHERE sku_id=?");
			pstat.setString(1, sku_id);
			result = pstat.executeQuery();
			if (result.next()) {
				if (result.getInt("is_unlimited") == 1) {
					return "unlimited";
				} else {
					return result.getString("amount");
				}
			}
			return null;
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
	public void InventoryIncrease(Connection conn, String sku_id, Integer amount, User admin) throws Exception {
		if (amount == null || amount < 0) {
			throw new Exception("数量不合法");
		}
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT is_unlimited, status FROM sku, sku_inventory "
					+ " WHERE sku.id=? AND sku.id=sku_inventory.sku_id");
			pstat.setString(1, sku_id);
			result = pstat.executeQuery();
			if (result.next()) {
				if (result.getInt("is_unlimited") == 1) {
					throw new Exception("库存无限，不允许修改库存量");
				}
				if (result.getInt("status") == SKUStatus.DELETED.getCode()) {
					throw new Exception("SKU已删除");
				}
			} else {
				throw new Exception("SKU " + sku_id + " 不存在");
			}
			
			pstat = conn.prepareStatement("UPDATE sku_inventory SET amount=amount+?, modify_time=NOW() WHERE sku_id=? AND is_unlimited=0");
			pstat.setInt(1, amount);
			pstat.setString(2, sku_id);
			pstat.executeUpdate();
			
			// 记log
			pstat = conn.prepareStatement("INSERT INTO yigather_item_log(id, op_type, sku_id, amount, operator_id, memo, date_time)"
					+ " VALUES(?,?,?,?,?,?,NOW())");
			YigatherItemInventoryLog record = new YigatherItemInventoryLog();
			pstat.setString(1, record.getId());
			pstat.setInt(2, YigatherItemInventoryLog.OpType.INCOME.getCode());
			pstat.setString(3, sku_id);
			pstat.setInt(4, amount);
			pstat.setString(5, admin.getId());
			pstat.setString(6, "后台操作，增加库存");
			pstat.executeUpdate();
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
	public void InventoryReduce(Connection conn, String sku_id, Integer amount, User admin) throws Exception {
		if (amount == null || amount < 0) {
			throw new Exception("数量不合法");
		}
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT is_unlimited, status FROM sku, sku_inventory "
					+ " WHERE sku.id=? AND sku.id=sku_inventory.sku_id");
			pstat.setString(1, sku_id);
			result = pstat.executeQuery();
			if (result.next()) {
				if (result.getInt("is_unlimited") == 1) {
					throw new Exception("库存无限，不允许修改库存量");
				}
				if (result.getInt("status") == SKUStatus.DELETED.getCode()) {
					throw new Exception("SKU已删除");
				}
			} else {
				throw new Exception("SKU " + sku_id + " 不存在");
			}
			
			pstat = conn.prepareStatement("UPDATE sku_inventory SET amount=amount-?, modify_time=NOW() WHERE sku_id=? AND is_unlimited=0");
			pstat.setInt(1, amount);
			pstat.setString(2, sku_id);
			pstat.executeUpdate();
			
			// 记log
			pstat = conn.prepareStatement("INSERT INTO yigather_item_log(id, op_type, sku_id, amount, operator_id, memo, date_time)"
					+ " VALUES(?,?,?,?,?,?,NOW())");
			YigatherItemInventoryLog record = new YigatherItemInventoryLog();
			pstat.setString(1, record.getId());
			pstat.setInt(2, YigatherItemInventoryLog.OpType.DELIVERY.getCode());
			pstat.setString(3, sku_id);
			pstat.setInt(4, amount);
			pstat.setString(5, admin.getId());
			pstat.setString(6, "后台操作，减少库存");
			pstat.executeUpdate();
			
			pstat = conn.prepareStatement("SELECT amount FROM sku_inventory WHERE sku_id=?");
			pstat.setString(1, sku_id);
			result = pstat.executeQuery();
			if (result.next()) {
				if (result.getInt("amount") < 0) {
					throw new Exception("库存不足");
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
	public List<ItemInstance> getUserInventory(Connection conn, String user_id) throws Exception{
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement(
					"SELECT item_instance.id,item_instance.sku_id as sku_id, item_instance.status as item_status, sku.name as sku_name, sku.status as sku_status "
					+ " FROM item_instance, sku "
					+ " WHERE user_id=? AND item_instance.status=? AND item_instance.sku_id=sku.id");
			pstat.setString(1, user_id);
			pstat.setInt(2, ItemInstanceStatus.UNUSED.getCode());
			result = pstat.executeQuery();
			List<ItemInstance> array_list = new ArrayList<ItemInstance>();
			while (result.next()) {
				ItemInstance instance = new ItemInstance();
				instance.setId(result.getString("id"));
				String sku_id = result.getString("sku_id");
				SKU sku = new SKU();
				sku.setId(sku_id);
				sku.setName(result.getString("sku_name"));
				sku.setStatus(result.getInt("sku_status"));
				instance.setSku(sku);
				instance.setStatus(result.getInt("item_status"));
				array_list.add(instance);
			}
			return array_list;
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
	public ItemInstance getItemInstance(Connection conn, String item_instance_id) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement(
					"SELECT item_instance.id, item_instance.sku_id as sku_id, order_id, user_id, user.nickname, item_instance.status, sku.name as sku_name "
					+ " FROM item_instance LEFT JOIN user ON item_instance.user_id=user.id "
					+ "  LEFT JOIN sku ON item_instance.sku_id=sku.id"
					+ " WHERE item_instance.id=?");
			pstat.setString(1, item_instance_id);
			result = pstat.executeQuery();
			if (result.next()) {
				ItemInstance instance = new ItemInstance();
				instance.setId(result.getString("id"));
				String sku_id = result.getString("sku_id");
				SKU sku = new SKU();
				sku.setId(sku_id);
				sku.setName(result.getString("sku_name"));
				instance.setSku(sku);
				instance.setOrderId(result.getString("order_id"));
				instance.setUserId(result.getString("user_id"));
				instance.setStatus(result.getInt("status"));
				return instance;
			}
			return null;
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
	public com.common.Page<ItemInstanceLog> getItemInstanceLog(Connection conn, String item_instance_id, Integer page, Integer page_size) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		List<ItemInstanceLog> array_list = new ArrayList<ItemInstanceLog>();
		int total_count = 0;
		try {
			pstat = conn.prepareStatement("SELECT COUNT(*) as total_count FROM item_instance_log WHERE item_instance_id=?");
			pstat.setString(1, item_instance_id);
			result = pstat.executeQuery();
			if (result.next()) {
				total_count = result.getInt("total_count");
			}
			
			pstat = conn.prepareStatement(
					"SELECT item_instance_log.id, item_instance_id, op_type, order_id, user_id, user.nickname, date_time, memo "
					+ " FROM  item_instance_log LEFT JOIN user"
					+ " ON item_instance_log.user_id=user.id"
					+ " WHERE item_instance_id=?"
					+ " ORDER BY date_time ASC"
					+ " LIMIT ?, ?");
			pstat.setString(1, item_instance_id);
			int offset = 0;
			if (page > 1) {
				offset = (page-1) * page_size;
			}
			pstat.setInt(2, offset);
			pstat.setInt(3, page_size);			
			result = pstat.executeQuery();
			while (result.next()) {
				ItemInstanceLog record = new ItemInstanceLog();
				record.setId(result.getString("id"));
				record.setItemInstanceId(result.getString("item_instance_id"));
				record.setOpType(result.getInt("op_type"));
				record.setUserId(result.getString("user_id"));
				record.setUserNickname(result.getString("nickname"));
				record.setOrderId(result.getString("order_id"));
				record.setDateTime(result.getTimestamp("date_time"));
				record.setMemo(result.getString("Memo"));
				array_list.add(record);
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
		
		com.common.Page<ItemInstanceLog> paged_result = new com.common.Page<ItemInstanceLog>();
		paged_result.setCurrentPage(page);
		paged_result.setPageSize(page_size);
		paged_result.setCurrentCount(array_list.size());
		paged_result.setTotalCount(total_count);
		paged_result.setResult(array_list);
		return paged_result;
	}
	
	@Override
	public com.common.Page<ItemInstanceLog> getUserItemLog(Connection conn, String user_id, Date start_date, Date end_date, Integer page, Integer page_size) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		List<ItemInstanceLog> array_list = new ArrayList<ItemInstanceLog>();
		int total_count = 0;
		try {
			pstat = conn.prepareStatement("SELECT COUNT(*) as total_count FROM item_instance_log WHERE user_id=? AND date_time>=? AND date_time<=date_add(?, interval 1 day)");
			pstat.setString(1, user_id);
			pstat.setDate(2, start_date);
			pstat.setDate(3, end_date);
			result = pstat.executeQuery();
			if (result.next()) {
				total_count = result.getInt("total_count");
			}
			
			pstat = conn.prepareStatement(
					"SELECT id, item_instance_id, op_type, order_id, user_id, date_time, memo, sku_id "
					+ " FROM  item_instance_log"
					+ " WHERE user_id=? AND date_time>=? AND date_time<=date_add(?, interval 1 day) "
					+ " ORDER BY date_time ASC"
					+ " LIMIT ?, ?");
			pstat.setString(1, user_id);
			pstat.setDate(2, start_date);
			pstat.setDate(3, end_date);
			int offset = 0;
			if (page > 1) {
				offset = (page-1) * page_size;
			}
			pstat.setInt(4, offset);
			pstat.setInt(5, page_size);			
			result = pstat.executeQuery();
			while (result.next()) {
				ItemInstanceLog record = new ItemInstanceLog();
				record.setId(result.getString("id"));
				record.setItemInstanceId(result.getString("item_instance_id"));
				record.setOpType(result.getInt("op_type"));
				record.setUserId(result.getString("user_id"));
				record.setOrderId(result.getString("order_id"));
				record.setDateTime(result.getTimestamp("date_time"));
				record.setMemo(result.getString("Memo"));
				record.setSkuId(result.getString("sku_id"));
				array_list.add(record);
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
		
		com.common.Page<ItemInstanceLog> paged_result = new com.common.Page<ItemInstanceLog>();
		paged_result.setCurrentPage(page);
		paged_result.setPageSize(page_size);
		paged_result.setCurrentCount(array_list.size());
		paged_result.setTotalCount(total_count);
		paged_result.setResult(array_list);
		return paged_result;
	}
	
	@Override
	public void consumeItemInstance(Connection conn, String user_id, String item_instance_id) throws Exception {
		PreparedStatement pstat = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement(
					"SELECT sku_id, user_id, status FROM item_instance WHERE id=?");
			pstat.setString(1, item_instance_id);
			result = pstat.executeQuery();
			String sku_id = "";
			String uid = "";
			if (result.next()) {
				sku_id = result.getString("sku_id");
				uid = result.getString("user_id");
				if (!ItemInstance.isValidStatus(result.getInt("status"))) {
					throw new Exception("此物品已经被使用或者销毁了，不能再次使用");
				}
				if (!uid.equals(user_id)) {
					throw new Exception("此物品不属于这个用户");
				}
			}
			else {
				throw new Exception("物品不存在");
			}
			
			// 设置状态
			pstat = conn.prepareStatement("UPDATE item_instance SET status=?, modify_time=NOW() WHERE id=?");
			pstat.setInt(1, ItemInstanceStatus.USED.getCode());
			pstat.setString(2, item_instance_id);
			pstat.executeUpdate();
			
			// 记录log
			ItemInstanceLog item_log = new ItemInstanceLog();
			pstat = conn.prepareStatement("INSERT INTO item_instance_log(id, sku_id, item_instance_id, op_type, user_id, memo, date_time) VALUES(?,?,?,?,?,?,NOW())");
			pstat.setString(1, item_log.getId());
			pstat.setString(2, sku_id);
			pstat.setString(3, item_instance_id);
			pstat.setInt(4, ItemInstanceLog.OpType.USE.getCode());
			pstat.setString(5, user_id);
			pstat.setString(6, "");
			pstat.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (stat != null) {
				stat.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}
	
	@Override
	public void destroyItemInstance(Connection conn, String item_instance_id, String memo) throws Exception {
		PreparedStatement pstat = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement(
					"SELECT sku_id, user_id, status FROM item_instance WHERE id=?");
			pstat.setString(1, item_instance_id);
			result = pstat.executeQuery();
			String sku_id = "";
			String user_id = "";
			if (result.next()) {
				sku_id = result.getString("sku_id");
				user_id = result.getString("user_id");
				if (!ItemInstance.isValidStatus(result.getInt("status"))) {
					throw new Exception("此物品已经被使用或者销毁了，不能再次销毁");
				}
			}
			else {
				throw new Exception("物品不存在");
			}
			
			// 设置状态
			pstat = conn.prepareStatement("UPDATE item_instance SET status=?, modify_time=NOW() WHERE id=?");
			pstat.setInt(1, ItemInstanceStatus.DESTROYED.getCode());
			pstat.setString(2, item_instance_id);
			pstat.executeUpdate();
			
			// 记录log
			ItemInstanceLog item_log = new ItemInstanceLog();
			pstat = conn.prepareStatement("INSERT INTO item_instance_log(id, sku_id, item_instance_id, op_type, user_id, memo, date_time) VALUES(?,?,?,?,?,?,NOW())");
			pstat.setString(1, item_log.getId());
			pstat.setString(2, sku_id);
			pstat.setString(3, item_instance_id);
			pstat.setInt(4, ItemInstanceLog.OpType.DESTROY.getCode());
			pstat.setString(5, user_id);
			pstat.setString(6, memo);
			pstat.executeUpdate();
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if (stat != null) {
				stat.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}

	@Override
	public ItemClass findByCode(String code) {
		// TODO Auto-generated method stub
		return repository.findByCode(code);
	}

	@Override
	public void doRenewal(Payment payment) throws Exception {
		// TODO Auto-generated method stub
		log.warn("doRenewal---");
		try {
			User user = userService.findById(payment.getUserId());
			if(user==null){
				throw new Exception("this user do not exsit!"+payment.getUserId());
			}else {
				Order order = orderService.findById(payment.getOrderId());
				if(order==null){
					throw new Exception("do not exsit this order:"+payment.getOrderId());
				}
				Calendar calendar = Calendar.getInstance();
				Double n = Math.ceil(order.getTotalPrice()/R.User.MEMBERSHIP_ANNUAL_FEE);//取得续费年限,向上取整
				if(user.getRoot()>UserRoot.VIP.getCode()){//非会员
					user.setVipdate(calendar.getTime());
					calendar.add(Calendar.YEAR, n.intValue());
					user.setRoot(2);
					if(user.getUnum()==null||user.getUnum().equals("")){
						VipNumLog vnl = vipNumLogService.findNextVip();
						user.setUnum(vnl.getVipNO());
						vnl.setStatus(true);
						vipNumLogService.saveOrUpdate(vnl);
						userService.newUserEmail(user);//新会员邮件
					}else {
						userService.renewalEmail(user);//会员续费邮件
					}
				}else {//已是会员
					calendar.setTime(user.getVipenddate());
					calendar.add(Calendar.YEAR, n.intValue());
					userService.renewalEmail(user);//会员续费邮件
				}
				user.setVipenddate(calendar.getTime());
				user = userService.userSaveOrUpdate(user);
				//商品实例，发货
//				itemInstanceService.updateByOrderId(ItemInstanceStatus.USED.getCode(), new java.util.Date(), order.getId());
				//商品实例记录
				List<ItemInstance> instances = itemInstanceService.findByOrderIdAndStatus(order.getId(), null);
				List<ItemInstanceLog> logs = new ArrayList<ItemInstanceLog>();
				List<ItemInstance> itemInstances = new ArrayList<ItemInstance>();
				for (ItemInstance i : instances) {
					ItemInstanceLog l = new ItemInstanceLog();
					l.setDateTime(new Timestamp(System.currentTimeMillis()));
					l.setItemInstanceId(i.getId());
					l.setMemo("支付完成，发货");
					l.setOpType(OpType.DELIVER.getCode());
					l.setOrderId(order.getId());
					l.setUserId(order.getUserId());
					l.setUserNickname(order.getUserNickname());
					logs.add(l);
					l = new ItemInstanceLog();
					l.setDateTime(new Timestamp(System.currentTimeMillis()));
					l.setItemInstanceId(i.getId());
					l.setMemo("使用商品实例");
					l.setOpType(OpType.USE.getCode());
					l.setOrderId(order.getId());
					l.setUserId(order.getUserId());
					l.setUserNickname(order.getUserNickname());
					logs.add(l);
					
					i.setStatus(ItemInstanceStatus.USED.getCode());
					i.setModifyTime(new Timestamp(System.currentTimeMillis()));
					itemInstances.add(i);
				}
				itemInstanceService.saveOrUpdate(itemInstances);
				itemInstanceLogService.saveOrUpdate(logs);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public List<ItemInstance> getUserInventory(final String user_id) throws Exception {
		// TODO Auto-generated method stub
		if(user_id == null){
			throw new Exception("user id can't not be null");
		}
		Specification<ItemInstance> spec = new Specification<ItemInstance>() {
			
			@Override
			public Predicate toPredicate(Root<ItemInstance> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("userId"), user_id));
				query.multiselect(root.<String>get("id").alias("id"),root.<SKU>get("sku").alias("sku")).where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		User user = userService.findById(user_id);
		List<ItemInstance> result = itemInstanceRepository.findAll(spec);
		for (ItemInstance i : result) {
			i.setUser(user);
		}
		return result;
	}

	
}
