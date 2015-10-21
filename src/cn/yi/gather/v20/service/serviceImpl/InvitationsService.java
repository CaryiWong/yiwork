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

import cn.yi.gather.v20.dao.InvitationsRepository;
import cn.yi.gather.v20.entity.Invitations;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.IinvitationsService;

@Service("invitationsServiceV20")
public class InvitationsService implements IinvitationsService{

	@Resource(name = "invitationsRepositoryV20")
	private InvitationsRepository repository;

	@Override
	public Invitations saveOrUpdateInvitations(Invitations invitations) {
		return repository.save(invitations);
	}

	@Override
	public Invitations findInvitations(String id) {
		return repository.findOne(id);
	}

	@Override
	public Page<Invitations> invitationsList(Integer page,Integer size,
			final String uid,final String iuid,String orderby) {
		PageRequest pageRequest = new PageRequest(page, size);
		if("asc".equals(orderby)){
			pageRequest = new PageRequest(page, size,new Sort(Direction.ASC,"createtime"));
		}else if("desc".equals(orderby)){
			pageRequest = new PageRequest(page, size,new Sort(Direction.DESC,"createtime"));
		}
		Specification<Invitations> spec=new Specification<Invitations>() {
			@Override
			public Predicate toPredicate(Root<Invitations> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				Predicate p0=cb.equal(root.<User>get("user").<String>get("id"), uid);
				Predicate p1=cb.equal(root.<User>get("inviteuser").<String>get("id"), iuid);
				Predicate p2=cb.equal(root.<User>get("user").<String>get("id"), iuid);
				Predicate p3=cb.equal(root.<User>get("inviteuser").<String>get("id"), uid);
				ps.add(cb.or(cb.and(p0,p1),cb.and(p2,p3)));//我发给他的跟他发给我的 都算我们俩的
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec,pageRequest);
	}
	
	@Override
	public Page<Invitations> invitationsMyList(Integer page,Integer size,
			final String uid,String orderby) {
		PageRequest pageRequest = new PageRequest(page, size);
		if("asc".equals(orderby)){
			pageRequest = new PageRequest(page, size,new Sort(Direction.ASC,"createtime"));
		}else if("desc".equals(orderby)){
			pageRequest = new PageRequest(page, size,new Sort(Direction.DESC,"createtime"));
		}
		Specification<Invitations> spec=new Specification<Invitations>() {
			@Override
			public Predicate toPredicate(Root<Invitations> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				Predicate p0=cb.equal(root.<User>get("user").<String>get("id"), uid);
				Predicate p3=cb.equal(root.<User>get("inviteuser").<String>get("id"), uid);
				ps.add(cb.or(p0,p3));//我发出的我收到的
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec,pageRequest);
	}
}
