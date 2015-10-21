package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.AnswerRepository;
import cn.yi.gather.v20.entity.Answer;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.IAnswerService;

@Service("answerServiceV20")
public class AnswerService implements IAnswerService{
	
	@Resource(name="answerRepositoryV20")
	private AnswerRepository repository;

	@Override
	public Answer save(Answer answer) {
		repository.save(answer);
		return findById(answer.getId());
	}

	@Override
	public Answer findById(String id) {
		return repository.findOne(id);
	}

	@Override
	public Page<Answer> findByObjid(Integer page,Integer size,final String objid,final String idtype) {
		PageRequest pageRequest = new PageRequest(page, size);//分页
		/* 条件过滤 */
		Specification<Answer> spec=new Specification<Answer>() {
			@Override
			public Predicate toPredicate(Root<Answer> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("objid"), objid));
				ps.add(cb.equal(root.<String>get("type"), idtype));
				Predicate[] parr = new Predicate[ps.size()]; 
				ps.toArray(parr);
				query.where(parr).groupBy(root.<User>get("user").<String>get("id"));
				return query.getRestriction();
			}
		};
		
		return repository.findAll(spec, pageRequest);
	}

}
