package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.lf5.PassingLogRecordFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.UserFocusRepository;
import cn.yi.gather.v20.dao.UserRepository;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.UserFocus;
import cn.yi.gather.v20.service.IUserFocusService;

/**
 * 用户关注
 * @author Lee.J.Eric
 * @time 2014年6月3日下午5:49:30
 */
@Service("userFocusServiceV20")
public class UserFocusService implements IUserFocusService{
	
	@Resource(name = "userFocusRepositoryV20")
	private UserFocusRepository repository;
	
	@Resource(name = "userRepositoryV20")
	private UserRepository userRepository;

	@Override
	public UserFocus findByMeAndWho(User me, User who) {
		// TODO Auto-generated method stub
		return repository.findByMeAndWho(me, who);
	}

	@Override
	public UserFocus findByMeAndWhoAndRelation(User me, User who,
			Integer relation) {
		// TODO Auto-generated method stub
		return repository.findByMeAndWhoAndRelation(me, who, relation);
	}

	@Override
	public UserFocus saveOrUpdate(UserFocus userFocus) {
		// TODO Auto-generated method stub
		return repository.save(userFocus);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		repository.delete(id);
	}


	@Override
	public Page<User> getuserFocusList(final String me, final Integer order,
			final Integer relation, Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size);
		Specification<User> spec = new Specification<User>() {
			
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				
				Root<UserFocus> focus_root = query.from(UserFocus.class);
//				ps.add(cb.equal(focus_root.<User>get("me").<String>get("id"), me));
				//目前关系独立，0收藏，1交情
				if(relation==0){
					ps.add(cb.equal(focus_root.<Integer>get("relation"), relation));
					ps.add(cb.equal(focus_root.<User>get("me").<String>get("id"), me));
					Join<UserFocus, User> uJoin = focus_root.join(focus_root.getModel().getSingularAttribute("who", User.class),JoinType.LEFT);
					ps.add(cb.equal(uJoin.<String>get("id"), root.<String>get("id")));
				}else {
					ps.add(cb.notEqual(root.<String>get("id"), me));
					ps.add(cb.equal(focus_root.<Integer>get("relation"), relation));
					ps.add(cb.or(cb.equal(focus_root.<User>get("me").<String>get("id"), me),cb.equal(focus_root.<User>get("who").<String>get("id"), me)));
					
					Join<UserFocus, User> uJoin = focus_root.join(focus_root.getModel().getSingularAttribute("who", User.class),JoinType.LEFT);
					
					Join<UserFocus, User> uJoin1 = focus_root.join(focus_root.getModel().getSingularAttribute("me", User.class),JoinType.LEFT);
					
					ps.add(cb.or(cb.equal(uJoin.<String>get("id"), root.<String>get("id")),cb.equal(uJoin1.<String>get("id"), root.<String>get("id"))));
				}
//				if(relation!=null)
//					ps.add(cb.equal(focus_root.<Integer>get("relation"), relation));
//				else 
//					ps.add(cb.isNotNull(focus_root.<Integer>get("relation")));
				
				//联表操作放后面，sql效率
//				Join<UserFocus, User> uJoin = focus_root.join(focus_root.getModel().getSingularAttribute("who", User.class),JoinType.LEFT);
//				ps.add(cb.equal(uJoin.<String>get("id"), root.<String>get("id")));
				
				Predicate[] parr = new Predicate[ps.size()]; 
				ps.toArray(parr);
				if(order == 1)
					query.where(parr).orderBy(cb.asc(focus_root.<Date>get("updatedate")));
				else
					query.where(parr).orderBy(cb.desc(focus_root.<Date>get("updatedate")));
				return query.distinct(true).getRestriction();
			}
		};
		return userRepository.findAll(spec,pageRequest);
	}

	@Override
	public List<UserFocus> findByMeAndRelation(User me,Integer relation) {
		// TODO Auto-generated method stub
		return repository.findByMeAndRelation(me, relation);
	}
	
}
