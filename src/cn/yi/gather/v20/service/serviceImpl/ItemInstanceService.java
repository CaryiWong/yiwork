package cn.yi.gather.v20.service.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v20.dao.ItemClassRepository;
import cn.yi.gather.v20.dao.ItemInstanceLogRepository;
import cn.yi.gather.v20.dao.ItemInstanceRepository;
import cn.yi.gather.v20.entity.ItemClass;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.ItemInstance.ItemInstanceStatus;
import cn.yi.gather.v20.entity.ItemInstanceLog;
import cn.yi.gather.v20.entity.ItemInstanceLog.OpType;
import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.service.IItemInstanceLogService;
import cn.yi.gather.v20.service.IItemInstanceService;

@Service("itemInstanceServiceV20")
public class ItemInstanceService implements IItemInstanceService{
	
	@Resource(name = "itemInstanceRepositoryV20")
	private ItemInstanceRepository repository;
	
	@Resource(name = "itemInstanceLogRepositoryV20")
	private ItemInstanceLogRepository logRepository;
	
	@Resource(name = "itemInstanceLogServiceV20")
	private IItemInstanceLogService itemInstanceLogService;
	

	@Override
	public void saveOrUpdate(List<ItemInstance> entities) {
		// TODO Auto-generated method stub
		repository.save(entities);
	}

	@Override
	public ItemInstance saveOrUpdate(ItemInstance entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public ItemInstance findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<ItemInstance> findByOrderIdAndStatus(final String orderId,final Integer status) {
		// TODO Auto-generated method stub
		Specification<ItemInstance> spec = new Specification<ItemInstance>() {
			
			@Override
			public Predicate toPredicate(Root<ItemInstance> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("orderId"), orderId));
				if(status != null){
					ps.add(cb.equal(root.<String>get("status"), status));
				}
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec);
	}

	@Override
	public List<ItemInstance> findByOrderId(final String orderId) {
		// TODO Auto-generated method stub
		Specification<ItemInstance> spec = new Specification<ItemInstance>() {
			
			@Override
			public Predicate toPredicate(Root<ItemInstance> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("orderId"), orderId));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec);
	}

	@Override
	public List<ItemInstance> findByUserIdAndStatus(final String userId,
			final Integer status) {
		// TODO Auto-generated method stub
		Specification<ItemInstance> spec = new Specification<ItemInstance>() {
			
			@Override
			public Predicate toPredicate(Root<ItemInstance> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("userId"), userId));
				if(status != null){
					ps.add(cb.equal(root.<String>get("status"), status));
				}
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec);
	}

	@Override
	public void updateByOrderId(Integer status,Date modifyTime,String orderId) {
		// TODO Auto-generated method stub
		repository.updateByOrderId(status,modifyTime,orderId);
	}

	@Override
	public List<ItemInstance> findCoffee(final String userId,
			final Integer status,final String skuId,final String type) {
		Specification<ItemInstance> spec = new Specification<ItemInstance>() {
			
			@Override
			public Predicate toPredicate(Root<ItemInstance> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("receiveuserId"), userId));//卷的所属者
				ps.add(cb.equal(root.<SKU>get("sku").<String>get("id"), skuId));//商品是咖啡 (自己买的跟赠送的skuid 不一样)
				if("me".equals(type)){// 我购买的
					
				}else if("receive".equals(type)){//收到的
					ps.add(cb.notEqual(root.<String>get("userId"), userId));//购买者
				}else if("share".equals(type)){//待分享
					ps.add(cb.equal(root.<String>get("userId"), userId));//购买者
				}
				
				if(status != null){
					ps.add(cb.equal(root.<Integer>get("status"), status));
				}
				
				query.where(ps.toArray(new Predicate[ps.size()])).orderBy(cb.desc(root.get("modifyTime")));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec);
	}
	
	@Override
	public Integer usecoffee(String coffeenum) {
		
		ItemInstance instance=repository.findByCouponnumberAndStatus(coffeenum, ItemInstanceStatus.UNUSED.getCode());
		if(instance!=null){
			instance.setStatus(ItemInstanceStatus.USED.getCode());
			instance.setModifyTime(new Timestamp(System.currentTimeMillis()));
			repository.save(instance);
			ItemInstanceLog instanceLog=new ItemInstanceLog();
			instanceLog.setDateTime(new Timestamp(System.currentTimeMillis()));
			instanceLog.setItemInstanceId(instance.getId());
			instanceLog.setOrderId(instance.getOrderId());
			instanceLog.setSkuId(instance.getSku().getId());
			instanceLog.setUserId(instance.getReceiveuserId());
			instanceLog.setOpType(OpType.USE.getCode());
			logRepository.save(instanceLog);
			return 0;//消劵 是否通知劵的所属者
		}else{
			return 1;
		}
		
	}

	@Transactional
	@Override
	public void instanceOverdue(final Date endDate) {
		// TODO Auto-generated method stub
		Specification<ItemInstance> spec = new Specification<ItemInstance>() {
			
			@Override
			public Predicate toPredicate(Root<ItemInstance> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Integer>get("effective"), 1));//时效性
				ps.add(cb.lessThan(root.<Date>get("endTime"), endDate));
				ps.add(cb.equal(root.<Integer>get("status"), ItemInstanceStatus.UNUSED.getCode()));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		List<ItemInstance> instances = repository.findAll(spec);
		List<ItemInstanceLog> instanceLogs = new ArrayList<ItemInstanceLog>();
		for (int i = 0; i < instances.size(); i++) {
			instances.get(i).setStatus(ItemInstanceStatus.EXPIRED.getCode());
			instances.get(i).setModifyTime(new Timestamp(System.currentTimeMillis()));
			
			//商品实例记录
			ItemInstanceLog item_log = new ItemInstanceLog();
			item_log.setItemInstanceId(instances.get(i).getId());
			item_log.setOpType(ItemInstanceLog.OpType.DESTROY.getCode());
			item_log.setOrderId(instances.get(i).getOrderId());
			item_log.setMemo("商品过期，系统自动销毁商品实例");
			item_log.setDateTime(new Timestamp(System.currentTimeMillis()));
			instanceLogs.add(item_log);
		}
		repository.save(instances);
		itemInstanceLogService.saveOrUpdate(instanceLogs);
	}

	@Override
	public com.common.Page<ItemInstance> couponlist(final String keyword,final Date start,final Date end,
		final Integer page,final Integer size) {
		
		com.common.Page<ItemInstance> result = new com.common.Page<ItemInstance>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "modifyTime"));
		Specification<ItemInstance> spec = new Specification<ItemInstance>() {	
			@Override
			public Predicate toPredicate(Root<ItemInstance> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p0 = null;
				Predicate p1 = null;
				Predicate p2 = null;
				List<Predicate> ps = new ArrayList<Predicate>();
			  if(StringUtils.isNotBlank(keyword)){
				 Predicate or0 = cb.like(cb.lower(root.<String>get("couponnumber")), "%"+keyword.toLowerCase()+"%");
				 p0=cb.or(or0);
				 ps.add(p0);
			  }
			  if(start!=null && end !=null){
				  Predicate or1= cb.greaterThan(root.<Date>get("modifyTime"), start);
				  p1=cb.and(or1);
				  ps.add(p1);
				  
				  Predicate or2= cb.lessThan(root.<Date>get("modifyTime"), end);
				  p2=cb.and(or2);
				  ps.add(p2);
			  }
			    ps.add(cb.or(cb.equal(root.<SKU>get("sku").get("coffeetype"), "normal"),cb.equal(root.<SKU>get("sku").get("coffeetype"), "giveaway")));
				ps.add(cb.equal(root.<Integer>get("status"), ItemInstanceStatus.USED.getCode()));  //已使用的
				query.where(ps.toArray(new Predicate[ps.size()])).orderBy(cb.desc(root.get("modifyTime")));
				return query.getRestriction();
			}
		};
		Page<ItemInstance> users = repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(users.getNumber());
		result.setPageSize(users.getSize());
		result.setResult(users.getContent());
		result.setTotalCount((int)users.getTotalElements());
		return result;
	}

	@Override
	public List<ItemInstance> findAllCoupon(final String userId,final Integer status,
			final String type,final List<SKU> skus) {
		Specification<ItemInstance> spec = new Specification<ItemInstance>() {
			@Override
			public Predicate toPredicate(Root<ItemInstance> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("receiveuserId"), userId));//卷的所属者
				if(skus!=null){
					List<Predicate> or_ps = new ArrayList<Predicate>();
					for (SKU sku : skus) {
						or_ps.add(cb.equal(root.<SKU>get("sku").<String>get("id"), sku.getId()));
					}
					ps.add(cb.or(or_ps.toArray(new Predicate[or_ps.size()])));
				}
				if(type!=null){
					if("me".equals(type)){// 我购买的
						
					}else if("receive".equals(type)){//收到的
						ps.add(cb.notEqual(root.<String>get("userId"), userId));//购买者
					}else if("share".equals(type)){//待分享
						ps.add(cb.equal(root.<String>get("userId"), userId));//购买者
					}
				}
				if(status != null){
					ps.add(cb.equal(root.<Integer>get("status"), status));
				}
				query.where(ps.toArray(new Predicate[ps.size()])).orderBy(cb.desc(root.get("modifyTime")));
				return query.getRestriction();
			}
			
		};
		return repository.findAll(spec);
	}
	
	
}
