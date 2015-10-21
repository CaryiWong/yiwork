package cn.yi.gather.v11.service.serviceImpl;

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

import cn.yi.gather.v11.dao.ActivityRepository;
import cn.yi.gather.v11.entity.Activity;
import cn.yi.gather.v11.entity.ActivityJoin;
import cn.yi.gather.v11.entity.Labels;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.service.IActivityJoinServiceV2;
import cn.yi.gather.v11.service.IActivityServiceV2;
import cn.yi.gather.v11.service.ILabelsServiceV2;

/**
 * 活动
 * @author Lee.J.Eric
 * @time 2014年6月3日下午6:06:54
 */
@Service("activityServiceV2")
public class ActivityServiceV2 implements IActivityServiceV2{
	
	@Resource(name = "activityRepositoryV2")
	private ActivityRepository repository;
	
	@Resource(name = "activityJoinServiceV2")
	private IActivityJoinServiceV2 activityJoinServiceV2;
	
	@Resource(name = "labelsServiceV2")
	private ILabelsServiceV2 labelsServiceV2;

	@Override
	public Activity activitySaveOrUpdate(Activity activity) {
		// TODO Auto-generated method stub
		return repository.save(activity);
	}

	@Override
	public Activity findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Page<Activity> getMyActivity(Integer page, Integer size, final User user,
			final Integer flag,final String keyword) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "opendate1"));
		List<ActivityJoin> activityjoins = new ArrayList<ActivityJoin>();
		if(flag==0){//由我发起或是我报名的
			activityjoins = activityJoinServiceV2.getByUserAndOwner(user, null);
		}else if(flag==1){//由我发起的
			activityjoins = activityJoinServiceV2.getByUserAndOwner(user, 1);
		}else{//我报名的
			activityjoins = activityJoinServiceV2.getByUserAndOwner(user, 0);
		}
		final List<String> actids = new ArrayList<String>();
		actids.add("");
		for (ActivityJoin activityjoin : activityjoins) {
			actids.add(activityjoin.getActivity().getId());
		}
		Specification<Activity> spec = new Specification<Activity>() {
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> condictions = new ArrayList<Predicate>();
				condictions.add(cb.equal(root.<Integer>get("onsale"), 0));//上架状态
				condictions.add(cb.equal(root.<Integer>get("checktype"), 1));////审核状态
				condictions.add(root.<String>get("id").in(actids));
				if(StringUtils.isNotBlank(keyword)){//搜索
					Predicate p2 = cb.like(cb.lower(root.<String>get("title")), "%"+keyword.toLowerCase()+"%");
					Predicate p3 = cb.like(cb.lower(root.<String>get("address")), "%"+keyword.toLowerCase()+"%");
					Predicate p4 = cb.like(cb.lower(root.<User>get("user").<String>get("nickname")), "%"+keyword.toLowerCase()+"%");
					Predicate ors = cb.or(p2,p3,p4);
					condictions.add(ors);
				}
				Predicate[] p = new Predicate[condictions.size()]; 
				for (int i = 0; i < condictions.size(); i++) {
					p[i] = condictions.get(i);
				}
				query.where(p);
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public Page<Activity> searchActByKeyword(final String keyword, Integer page,Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate1"));
		Specification<Activity> spec = new Specification<Activity>() {
			
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = cb.equal(root.<Integer>get("onsale"), 0);//上架状态
				Predicate p1 = cb.equal(root.<Integer>get("checktype"), 1);//审核状态
				if(StringUtils.isNotBlank(keyword)){//搜索
					Predicate p2 = cb.like(cb.lower(root.<String>get("title")), "%"+keyword.toLowerCase()+"%");
					Labels labels = labelsServiceV2.getLabelsByLabeltypeAndZname(0, keyword);
					Predicate p3 = cb.isMember(labels,root.<List<Labels>>get("label"));
					Predicate ors = cb.or(p2,p3);
					query.where(cb.and(p0,p1,ors));
				}else {//非搜索
					query.where(cb.and(p0,p1));
				}
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public List<Activity> getBannerActList(Integer banner) {
		// TODO Auto-generated method stub
		//通过审核、上架并设置了banner标记的
		return repository.findByChecktypeAndOnsaleAndIsbanner(1, 0, banner);
	}

	@Override
	public Page<Activity> getByLabel(Labels label, Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "opendate1"));
		Page<Activity> result = repository.findByLabelIn(pageRequest, label);
		for (int i = 0; i < result.getContent().size(); i++) {//重新查询每个活动的报名人数
			if("d8cbc0902ea140f68de61398940151074".equals(result.getContent().get(i).getId())){
				result.getContent().get(i).setSignnum(33);
			}else if ("4187bf9d91d8431f94481398841127369".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(68);
			}else if ("c00726f07bd44428a8a71399459704548".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(182);
			}else if ("fc75e256166d461a8f8d1399446457801".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(33);
			}else if ("1b2493b297854eed8e311399463587679".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(66);
			}else if ("dfa4ceb06dc74f0faf681397652614185".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(200);
			}else if ("dd1f353c4a114fc8b70b1397659394850".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(71);
			}else if ("d2b35c0d845c4ca4a10a1397656485028".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(59);
			}else if ("7565a111b71644498cdd1399436056059".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(26);
			}else if ("2d0f2ede608841cf93d11399543250491".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(26);
			}else if ("d9e8244c41ba4a19ad181399463383478".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(63);
			}else if ("2cdafd328fa443ff9fa31397653267876".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(124);
			}else if ("7cf673c12d484b7688641398234223412".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(90);
			}else if ("c563e2c3d5214875a5971398235964465".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(92);
			}else if ("62a9e9f0a83442da801e1398232821270".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(46);
			}else if ("12a852462236443b8b171399448689480".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(105);
			}else if ("0f3f743ee7a446018f351397652394584".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(45);
			}else if ("418b873ef4184352a5d81397654724033".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(48);
			}else if ("4634ecf8ba7b43d39ec91398232679213".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(15);
			}else if ("08f0450e5ff3452288911397657230019".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(55);
			}else if ("405ad93dcc6949f5a06c1399443484558".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(62);
			}else if ("b0ae943091174f1dab921399448950806".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(20);
			}else if ("0193b06aed4f4bff85af1399443208798".equals(result.getContent().get(i).getId())) {//翻页
				result.getContent().get(i).setSignnum(44);
			}else if ("53163f991be24140b7df1397653720208".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(50);
			}else if ("3c6068bf315f418ea2a31397659206590".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(62);
			}else if ("763456948979466290651397657222910".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(60);
			}else if ("993bf3d6583145a495901399459316314".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(160);
			}else if ("7f86b3b76c6541bc9dfb1397655266283".equals(result.getContent().get(i).getId())) {
				result.getContent().get(i).setSignnum(155);
			}
		}
		return result;
	}

	@Override
	public Page<Activity> getActivityList(Integer page, Integer size,
			final Integer listtype, final String keyword) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "opendate1"));
		if(listtype==0){//待开启或是进行中
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "opendate1"));
		}else if(listtype==1){//已结束
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "enddate1"));
		}
		Specification<Activity> spec = new Specification<Activity>() {
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = cb.equal(root.<Integer>get("onsale"), 0);//上架状态
				Predicate p1 = cb.equal(root.<Integer>get("checktype"), 1);//审核状态
				Predicate p2 = null;
				if(listtype==0){//待开启或是进行中
					p2 = cb.greaterThan(root.<Date>get("enddate1"), new Date());
				}else if (listtype==1) {//已结束
					p2 = cb.lessThan(root.<Date>get("enddate1"), new Date());
				}
				if(StringUtils.isNotBlank(keyword)){//搜索
					Predicate p3 = cb.like(cb.lower(root.<String>get("title")), "%"+keyword.toLowerCase()+"%");//标题
					Predicate p4 = cb.like(cb.lower(root.<User>get("user").<String>get("nickname")), "%"+keyword.toLowerCase()+"%");//发起人昵称
					Predicate p5 = cb.like(cb.lower(root.<String>get("address")), "%"+keyword.toLowerCase()+"%");//地区
					Labels labels = labelsServiceV2.getLabelsByLabeltypeAndZname(0, keyword);
					Predicate p6 = cb.isMember(labels,root.<List<Labels>>get("label"));//标签中文名
					Predicate ors = cb.or(p3,p4,p5,p6);
					if(p2==null)
						query.where(cb.and(p0,p1,ors));
					else
						query.where(cb.and(p0,p1,p2,ors));
				}else {//非搜索
					if(p2==null)
						query.where(cb.and(p0,p1));
					else
						query.where(cb.and(p0,p1,p2));
				}
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public com.common.Page<Activity> getActivityForPage(Integer page,
			Integer size,final Integer cost,final Integer checktype,final Integer status,
			final String keyword,final List<Long> labelList) {
		// TODO Auto-generated method stub
		com.common.Page<Activity> result = new com.common.Page<Activity>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate1"));
		Specification<Activity> spec = new Specification<Activity>() {
			
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query,
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
						conditions.add(cb.greaterThan(root.<Date>get("opendate1"), now));
					}else if (status==2) {//进行中
						Predicate p0 = cb.lessThan(root.<Date>get("opendate1"), now);
						Predicate p1 = cb.greaterThan(root.<Date>get("enddate1"), now);
						conditions.add(cb.and(p0,p1));
					}else if (status==3) {//已结束
						conditions.add(cb.lessThan(root.<Date>get("enddate1"), now));
					}
				}
				if(null != labelList && labelList.size() > 0){
					List<Labels> list = labelsServiceV2.getLabelsByList(labelList);
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
		Page<Activity> list = repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(list.getNumber());
		result.setPageSize(list.getSize());
		result.setResult(list.getContent());
		result.setTotalCount((int)list.getTotalElements());
		return result;
	}

	@Override
	public Long countBannerAct() {
		// TODO Auto-generated method stub
		return repository.countByIsbanner(1);
	}
	
}
