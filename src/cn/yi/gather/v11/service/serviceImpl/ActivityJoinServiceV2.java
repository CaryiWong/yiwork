package cn.yi.gather.v11.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v11.dao.ActivityJoinRepository;
import cn.yi.gather.v11.entity.Activity;
import cn.yi.gather.v11.entity.ActivityJoin;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.service.IActivityJoinServiceV2;

/**
 * 活动报名
 * 
 * @author Lee.J.Eric
 * @time 2014年6月6日下午3:10:17
 */
@Service("activityJoinServiceV2")
public class ActivityJoinServiceV2 implements IActivityJoinServiceV2 {
	
	@Resource(name = "activityJoinRepositoryV2")
	private ActivityJoinRepository repository;

	@Override
	public List<ActivityJoin> getByUserAndOwner(final User user, final Integer owner) {
		// TODO Auto-generated method stub
		Specification<ActivityJoin> spec = new Specification<ActivityJoin>() {

			@Override
			public Predicate toPredicate(Root<ActivityJoin> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = cb.equal(root.<User>get("user"), user);//报名人
				if (owner!=null) {//我发起的1,我报名的0
					Predicate p1 = cb.equal(root.<Integer>get("owner"), owner);
					query.where(cb.and(p0,p1));
				}else {//我发起的或是我报名的，0
					query.where(p0);
				}
				return query.getRestriction();
			}
		};
		return repository.findAll(spec);
	}

	@Override
	public ActivityJoin activityjoinSaveOrUpdate(ActivityJoin activityJoin) {
		// TODO Auto-generated method stub
		return repository.save(activityJoin);
	}

	@Override
	public void deleteByID(String id) {
		// TODO Auto-generated method stub
		repository.delete(id);
	}

	@Override
	public Boolean checkByUserAndActivity(User user, Activity activity) {
		// TODO Auto-generated method stub
		ActivityJoin join = repository.findByUserAndActivity(user, activity);
		if(join!=null)
			return true;
		return false;
	}

	@Override
	public ActivityJoin getByUserAndActivity(User user, Activity activity) {
		// TODO Auto-generated method stub
		return repository.findByUserAndActivity(user, activity);
	}

	@Override
	public List<ActivityJoin> getByActivity(Activity activity) {
		// TODO Auto-generated method stub
		return repository.findByActivity(activity);
	}

	@Override
	public Long countByActivity(Activity activity) {
		// TODO Auto-generated method stub
		return repository.countByActivity(activity);
	}

	@Override
	public Page<ActivityJoin> findPageByActivity(Integer page, Integer size,
			Activity activity) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "createtime"));
		return repository.findPageByActivity(pageRequest, activity);
	}
	
	@Override
	public com.common.Page<ActivityJoin> signList(Integer page, Integer size,
			final Activity activity,final String keyword) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createtime"));
		Specification<ActivityJoin> spec = new Specification<ActivityJoin>() {

			@Override
			public Predicate toPredicate(Root<ActivityJoin> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> condictions = new ArrayList<Predicate>();
				condictions.add(cb.equal(root.<Activity>get("activity"), activity));
				if(StringUtils.isNotBlank(keyword)){
					Predicate[] ors = new Predicate[3];
					ors[0] = cb.like(cb.lower(root.<User>get("user").<String>get("nickname")), "%"+keyword.toLowerCase()+"%");
					ors[1] = cb.like(cb.lower(root.<User>get("user").<String>get("email")), "%"+keyword.toLowerCase()+"%");
					ors[2] = cb.like(cb.lower(root.<User>get("user").<String>get("telnum")), "%"+keyword.toLowerCase()+"%");
					condictions.add(cb.or(ors));
				}
				Predicate[] p = new Predicate[condictions.size()]; 
				for (int i = 0; i < condictions.size(); i++) {
					p[i] = condictions.get(i);
				}
				query.where(p);
				return query.getRestriction();
			}
		};
		Page<ActivityJoin> list = repository.findAll(spec, pageRequest);
		com.common.Page<ActivityJoin> result = new com.common.Page<ActivityJoin>();
		result.setCurrentPage(page);
		result.setCurrentCount(list.getNumber());
		result.setPageSize(list.getSize());
		result.setResult(list.getContent());
		result.setTotalCount((int)list.getTotalElements());
		return result;
	}
}
