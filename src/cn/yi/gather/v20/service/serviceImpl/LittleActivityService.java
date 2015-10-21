package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v20.dao.ActivityRepository;
import cn.yi.gather.v20.dao.LittleActivityRepository;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.ActivityJoin;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.IActivityJoinService;
import cn.yi.gather.v20.service.IActivityService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.ILittleActivityService;

/**
 * 小活动
 * @author Lee.J.Eric
 * @time 2014年6月3日下午6:06:54
 */
@Service("littleActivityServiceV20")
public class LittleActivityService implements ILittleActivityService{
	
	@Resource(name = "littleActivityRepositoryV20")
	private LittleActivityRepository repository;
	
	@Resource(name = "activityJoinServiceV20")
	private IActivityJoinService activityJoinService;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;

 
	 
	@Override
	public com.common.Page<Gathering> getActivityForPage(Integer page,
			Integer size,final Integer cost,final Integer checktype,final Integer status,
			final String keyword,final List<Long> labelList) {
		// TODO Auto-generated method stub
		com.common.Page<Gathering> result = new com.common.Page<Gathering>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<Gathering> spec = new Specification<Gathering>() {
			
			@Override
			public Predicate toPredicate(Root<Gathering> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Date now = new Date();
				List<Predicate> conditions = new ArrayList<Predicate>();
				if(cost!=null&&cost!=-1)
					conditions.add(cb.equal(root.<Integer>get("cost"), cost));
				if(checktype!=null&&checktype!=-1)
					conditions.add(cb.equal(root.<Integer>get("checktype"), checktype));
				if(status!=null&&status!=-1){
					if(status==1){//待开启
						conditions.add(cb.greaterThan(root.<Date>get("opendate"), now));
					}else if (status==2) {//进行中
						Predicate p0 = cb.lessThan(root.<Date>get("opendate"), now);
						Predicate p1 = cb.greaterThan(root.<Date>get("enddate"), now);
						conditions.add(cb.and(p0,p1));
					}else if (status==3) {//已结束
						conditions.add(cb.lessThan(root.<Date>get("enddate"), now));
					}
				}
				if(null != labelList && labelList.size() > 0){
					List<Labels> list = labelsService.getLabelsByList(labelList);
					if(!list.isEmpty()){
						Predicate[] ors = new Predicate[list.size()];
						for (int i = 0; i < list.size(); i++) {
							ors[i] = cb.isMember(list.get(i), root.<List<Labels>>get("label"));
						}
						conditions.add(cb.or(ors));
					}
				}
				if(StringUtils.isNotBlank(keyword)){
					Predicate[] ors = new Predicate[3];
					ors[0] = cb.like(cb.lower(root.<String>get("title")), "%"+keyword.toLowerCase()+"%");
					ors[1] = cb.like(cb.lower(root.<String>get("address")), "%"+keyword.toLowerCase()+"%");
					ors[2] = cb.like(cb.lower(root.<User>get("user").<String>get("nickname")), "%"+keyword.toLowerCase()+"%");
					conditions.add(cb.or(ors));
				}
				Predicate[] predicates = new Predicate[conditions.size()];
				for (int i = 0; i < conditions.size(); i++) {
					predicates[i] = conditions.get(i);
 				}
				query.where(cb.and(predicates));
				return query.getRestriction();
			}
		};
		Page<Gathering> list = repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(list.getNumber());
		result.setPageSize(list.getSize());
		result.setResult(list.getContent());
		result.setTotalCount((int)list.getTotalElements());
		return result;
	}



	@Override
	public Gathering activitySaveOrUpdate(Gathering activity) {
		repository.save(activity);
		return findById(activity.getId());
	}



	@Override
	public Gathering findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}



	@Override
	public Page<Activity> getMyActivity(Integer page, Integer size, User user,
			Integer flag, String keyword) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Page<Activity> searchActByKeyword(String keyword, Integer page,
			Integer size) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Activity> getBannerActList(Integer banner) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Page<Activity> getByLabel(Labels label, Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Page<Activity> getActivityList(Integer page, Integer size,
			Integer listtype) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Long countBannerAct() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Activity> findListOpendateBetween(Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Activity> findListEnddateBetween(Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Page<Activity> findActivityList(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	 
	 

	 
}
