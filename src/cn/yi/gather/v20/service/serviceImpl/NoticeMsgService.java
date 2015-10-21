package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

import cn.yi.gather.v20.dao.NoticeMsgRepository;
import cn.yi.gather.v20.entity.NoticeMsg;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeActionTypeValue;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeMsgTypeValue;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.INoticeMsgService;

@Service("noticeMsgServiceV20")
public class NoticeMsgService implements INoticeMsgService{

	@Resource(name = "noticeMsgRepositoryV20")
	private NoticeMsgRepository repository;
	
	@Override
	public NoticeMsg saveOrUpdate(NoticeMsg entity) {
		// TODO Auto-generated method stub
		repository.save(entity);
		return findById(entity.getId());
	}

	@Override
	public NoticeMsg findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Page<NoticeMsg> findByUser(final User user, Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<NoticeMsg> spec = new Specification<NoticeMsg>() {

			@Override
			public Predicate toPredicate(Root<NoticeMsg> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				query.where(cb.isMember(user, root.<Set<User>>get("tags")));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public Page<NoticeMsg> findByUser(final User user, Integer page, Integer size, final Date date,final String axis) {
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<NoticeMsg> spec = new Specification<NoticeMsg>() {
			@Override
			public Predicate toPredicate(Root<NoticeMsg> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.isMember(user, root.<Set<User>>get("tags")));
				if (axis.equalsIgnoreCase("after")){
					ps.add(cb.greaterThan(root.<Date>get("createdate"), date));
				}else if(axis.equalsIgnoreCase("before")){
					ps.add(cb.lessThan(root.<Date>get("createdate"), date));
				}
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public Page<NoticeMsg> allNoticeMsg(final User user, Integer page, Integer size) {
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<NoticeMsg> spec = new Specification<NoticeMsg>() {
			@Override
			public Predicate toPredicate(Root<NoticeMsg> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.isMember(user, root.<Set<User>>get("tags")));
				ps.add(cb.notEqual(root.<Integer>get("isread"), 1));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public void updateReadForInvit(final User user,final String invit_id,final String not_id) {
		List<NoticeMsg> list=new ArrayList<NoticeMsg>();
		Specification<NoticeMsg> spec = new Specification<NoticeMsg>() {
			@Override
			public Predicate toPredicate(Root<NoticeMsg> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.isMember(user, root.<Set<User>>get("tags")));
				ps.add(cb.equal(root.<String>get("param"), invit_id));
				ps.add(cb.notEqual(root.<String>get("id"), not_id));
				ps.add(cb.notEqual(root.<Integer>get("isread"), 1));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		list=repository.findAll(spec);
		if(list!=null&&list.size()>0){
			for (NoticeMsg noticeMsg : list) {
				noticeMsg.setIsread(1);
				repository.save(noticeMsg);
			}
		}
	}
}
