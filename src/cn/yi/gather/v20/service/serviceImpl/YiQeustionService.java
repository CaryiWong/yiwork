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

import cn.yi.gather.v20.dao.YiQeustionRepository;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.YiQuestion;
import cn.yi.gather.v20.service.IYiQeustionService;

@Service("yiQeustionServiceV20")
public class YiQeustionService implements IYiQeustionService{
	
	@Resource(name = "yiQeustionRepositoryV20")
	private YiQeustionRepository repository;
	

	@Override
	public YiQuestion saveOrUpdate(YiQuestion entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public void delete(YiQuestion entity) {
		// TODO Auto-generated method stub
		repository.delete(entity);
	}

	@Override
	public Page<YiQuestion> findPageByUser(final String userid,Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createTime"));
		Specification<YiQuestion> spec = new Specification<YiQuestion>() {
			
			@Override
			public Predicate toPredicate(Root<YiQuestion> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Integer>get("msgtype"), 0));
				ps.add(cb.equal(root.<String>get("parent"), ""));
				if(userid!=null){
					ps.add(cb.equal(root.<User>get("user").<String>get("id"), userid));
				}
				Predicate[] prr=new Predicate[ps.size()];
				ps.toArray(prr);
				query.where(cb.and(prr));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public List<YiQuestion> findByParentId(final String parentid) {
		// TODO Auto-generated method stub
		Specification<YiQuestion> spec = new Specification<YiQuestion>() {

			@Override
			public Predicate toPredicate(Root<YiQuestion> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				query.where(cb.equal(root.<String>get("parent"), parentid)).orderBy(cb.asc(root.<Date>get("createTime")));
				return query.distinct(true).getRestriction();
			}
			
		};
		return repository.findAll(spec);
	}

	@Override
	public YiQuestion findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
