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

import cn.yi.gather.v20.dao.CCommentRepository;
import cn.yi.gather.v20.entity.CComment;
import cn.yi.gather.v20.entity.Course;
import cn.yi.gather.v20.service.ICCommentService;

@Service("cCommentServiceV20")
public class CCommentService implements ICCommentService {

	@Resource(name = "cCommentRepositoryV20")
	private CCommentRepository repository;

	@Override
	public CComment cCommentSaveOrUpdate(CComment cComment) {
		// TODO Auto-generated method stub
		return repository.save(cComment);
	}

	@Override
	public Page<CComment> findCCommentForCourse(Integer page, Integer size,
			Integer order, final String id) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size);
		if(order==1){
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, "createdate"));
		}else {
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		}
		Specification<CComment> spec = new Specification<CComment>() {
			
			@Override
			public Predicate toPredicate(Root<CComment> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				query.where(cb.equal(root.<Course>get("course").<String>get("id"), id),cb.equal(root.<Integer>get("isdel"), 0));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public CComment findCComment(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}
}
