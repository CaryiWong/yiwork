package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
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

import cn.yi.gather.v20.dao.BroadcastRepository;
import cn.yi.gather.v20.entity.Broadcast;
import cn.yi.gather.v20.entity.Sysnetwork;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.IBroadcastService;

import com.common.R;

/**
 * 广播接收
 * @author Lee.J.Eric
 * @time 2014年6月11日下午12:33:51
 */
@Service("broadcastServiceV20")
public class BroadcastService implements IBroadcastService{

	@Resource(name = "broadcastRepositoryV20")
	private BroadcastRepository repository;
	
	@Override
	public Broadcast broadcastSaveOrUpdate(Broadcast broadcast) {
		// TODO Auto-generated method stub
		return repository.save(broadcast);
	}

	@Override
	public Page<Broadcast> findByUserAndTime(final User user, Integer page,
			Integer size, final Date time, final String order) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate1"));
		Specification<Broadcast> spec = new Specification<Broadcast>() {
			
			@Override
			public Predicate toPredicate(Root<Broadcast> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = cb.equal(root.<User>get("user"), user);
				Predicate p1 = cb.equal(root.<Integer>get("isdel"), 0);
				Predicate p2 = null;
				if(order.equalsIgnoreCase(R.User.BEFORE)){
					p2 = cb.lessThanOrEqualTo(root.<Date>get("createdate1"), time);
				}else if (order.equalsIgnoreCase(R.User.AFTER)) {
					p2 = cb.greaterThanOrEqualTo(root.<Date>get("createdate1"), time);
				}
				if(p2!=null){
					query.where(cb.and(p0,p1,p2));
				}else {
					query.where(cb.and(p0,p1));
				}
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public void broadListBatchSave(List<Broadcast> list) {
		// TODO Auto-generated method stub
		repository.save(list);
	}

	@Override
	public Broadcast findByUserAndSysnetwork(User user, Sysnetwork sysnetwork) {
		// TODO Auto-generated method stub
		return repository.findByUserAndSysnetwork(user, sysnetwork);
	}

	@Override
	public List<Sysnetwork> getMyBroadcastList(final Integer page,final Integer size,
			final Integer order, final User user, final Integer read) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate1"));
		if(order==-1){
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate1"));
		}else {
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, "createdate1"));
		}
		
		Specification<Broadcast> spec = new Specification<Broadcast>() {
			
			@Override
			public Predicate toPredicate(Root<Broadcast> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> condictions = new ArrayList<Predicate>();
				condictions.add(cb.equal(root.<User>get("user"), user));
				condictions.add(cb.equal(root.<Integer>get("isdel"), 0));
				if(read!=-1){//-1查全部
					condictions.add(cb.equal(root.<Integer>get("isread"), read));
				}
				Predicate[] p = new Predicate[condictions.size()]; 
				for (int i = 0; i < condictions.size(); i++) {
					p[i] = condictions.get(i);
				}
				query.where(p);
				return query.getRestriction();
			}
		};
		Page<Broadcast> broadcasts = repository.findAll(spec, pageRequest);
		List<Broadcast> list = broadcasts.getContent();
		List<Sysnetwork> result = new ArrayList<Sysnetwork>();
		for (Broadcast broadcast : list) {
			result.add(broadcast.getSysnetwork());
		}
		return result;
	}

	@Override
	public Long countMyBroadcastList(final User user,final Integer read) {
		// TODO Auto-generated method stub
		Specification<Broadcast> spec = new Specification<Broadcast>() {
			
			@Override
			public Predicate toPredicate(Root<Broadcast> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> condictions = new ArrayList<Predicate>();
				condictions.add(cb.equal(root.<User>get("user"), user));
				condictions.add(cb.equal(root.<Integer>get("isdel"), 0));
				if(read!=-1){//-1查全部
					condictions.add(cb.equal(root.<Integer>get("isread"), read));
				}
				Predicate[] p = new Predicate[condictions.size()]; 
				for (int i = 0; i < condictions.size(); i++) {
					p[i] = condictions.get(i);
				}
				query.where(p);
				return query.getRestriction();
			}
		};
		return repository.count(spec);
	}
	
}
