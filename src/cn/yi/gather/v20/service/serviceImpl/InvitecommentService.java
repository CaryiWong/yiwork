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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.InvitecommentRepository;
import cn.yi.gather.v20.entity.Invitations;
import cn.yi.gather.v20.entity.Invitecomment;
import cn.yi.gather.v20.service.IinvitecommentService;

@Service("invitecommentServiceV20")
public class InvitecommentService implements IinvitecommentService{

	@Resource(name = "invitecommentRepositoryV20")
	private InvitecommentRepository repository;

	@Override
	public Invitecomment saveOrUpdateInvitecomment(Invitecomment invitecomment) {
		return repository.save(invitecomment);
	}

	@Override
	public Page<Invitecomment> invitecommentlist(Integer page, Integer size,
			final String id, String orderby) {
		PageRequest pageRequest = new PageRequest(page, size);
		if("asc".equals(orderby)){
			pageRequest = new PageRequest(page, size,new Sort(Direction.ASC,"createtime"));
		}else if("desc".equals(orderby)){
			pageRequest = new PageRequest(page, size,new Sort(Direction.DESC,"createtime"));
		}
		Specification<Invitecomment> spec=new Specification<Invitecomment>() {
			@Override
			public Predicate toPredicate(Root<Invitecomment> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Invitations>get("invitations").<String>get("id"), id));
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec,pageRequest);
	}

	@Override
	public List<Invitecomment> invitecommentlist(final String id) {
		
		Specification<Invitecomment> spec=new Specification<Invitecomment>() {
			@Override
			public Predicate toPredicate(Root<Invitecomment> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Invitations>get("invitations").<String>get("id"), id));
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr));
				return query.getRestriction();
			}
		};
		
		return repository.findAll(spec, new Sort(Direction.ASC,"createtime"));
	}
}
