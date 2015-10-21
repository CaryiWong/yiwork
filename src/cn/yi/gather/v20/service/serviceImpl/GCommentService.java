package cn.yi.gather.v20.service.serviceImpl;

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

import cn.yi.gather.v20.dao.GCommentRepository;
import cn.yi.gather.v20.entity.GComment;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.service.IGCommentService;

@Service("gCommentServiceV20")
public class GCommentService implements IGCommentService{
	
	@Resource(name = "gCommentRepositoryV20")
	private GCommentRepository repository;
	
	@Override
	public GComment gCommentSaveOrUpdate(GComment gComment) {
		// TODO Auto-generated method stub
		return repository.save(gComment);
	}

	@Override
	public Page<GComment> findGCommentForGahering(final Integer page, final Integer size,
			Integer order,final String id) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size);
		if(order==1){
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, "createdate"));
		}else {
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		}
		Specification<GComment> spec = new Specification<GComment>() {
			
			@Override
			public Predicate toPredicate(Root<GComment> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
//				List<Predicate> ps = new ArrayList<Predicate>();
//				ps.add(cb.equal(root.<Gathering>get("gathering").<String>get("id"), id));
				query.where(cb.equal(root.<Gathering>get("gathering").<String>get("id"), id),
						cb.equal(root.<Integer>get("isdel"), 0));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public GComment findGComment(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
