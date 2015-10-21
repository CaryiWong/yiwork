package cn.yi.gather.v22.yg100.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v22.yg100.dao.YgOrderInfoRepository;
import cn.yi.gather.v22.yg100.entity.OrderInfo;
import cn.yi.gather.v22.yg100.service.IYgOrderService;

@Service("ygOrderService")
public class YgOrderService implements IYgOrderService{
	
	@Resource(name="ygOrderInfoRepository")
	private YgOrderInfoRepository repository;

	@Override
	public OrderInfo saveOrUpdate(OrderInfo info) {
		// TODO Auto-generated method stub
		return repository.save(info);
	}

	@Override
	public OrderInfo findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<OrderInfo> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<OrderInfo> findByTel(String tel) {
		// TODO Auto-generated method stub
		return repository.findByTel(tel);
	}

	@Override
	public List<OrderInfo> findByUser(String uid, String utype) {
		// TODO Auto-generated method stub
		return repository.findByUseridAndUsertype(uid, utype);
	}

	@Override
	public List<OrderInfo> findByStatus(String status) {
		// TODO Auto-generated method stub
		return repository.findByOrderstatus(status);
	}

	@Override
	public Page<OrderInfo> findList(final String userid,final String usertype,final String tel,
			final String openid,final Integer page,final Integer size,final String orderstart) {
		PageRequest pageRequest = new PageRequest(page, size, new Sort(Direction.DESC, "createtime"));
		Specification<OrderInfo> spec=new Specification<OrderInfo>() {
			@Override
			public Predicate toPredicate(Root<OrderInfo> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> ps = new ArrayList<Predicate>();
				if(userid!=null){
					ps.add(cb.equal(root.<String>get("userid"), userid));
				}
				if(usertype!=null){
					ps.add(cb.equal(root.<String>get("usertype"), usertype));
				}
				if(tel!=null){
					ps.add(cb.equal(root.<String>get("tel"), tel));
				}
				if(openid!=null){
					ps.add(cb.equal(root.<String>get("openid"), openid));
				}
				if(orderstart!=null){
					ps.add(cb.equal(root.<String>get("orderstatus"), orderstart));
				}
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

}
