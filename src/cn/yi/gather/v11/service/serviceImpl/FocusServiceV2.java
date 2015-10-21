package cn.yi.gather.v11.service.serviceImpl;

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

import cn.yi.gather.v11.dao.FocusRepository;
import cn.yi.gather.v11.entity.Demands;
import cn.yi.gather.v11.entity.Focus;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.service.IFocusServiceV2;

@Service("focusServiceV2")
public class FocusServiceV2 implements IFocusServiceV2{

	@Resource(name = "focusRepositoryV2")
	private FocusRepository focusRepository;
	
	@Override
	public Focus saveFocus(Focus focus) {
		return focusRepository.save(focus);
	}

	@Override
	public Page<Focus> findFocusBy(Integer page, Integer size,final String uid,
			final String did, Integer orderby) {
		PageRequest pageRequest = new PageRequest(page, size);
		if(orderby==1){
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, "createdate1"));
		}else {
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate1"));
		}
		Specification<Focus> specification=new Specification<Focus>() {
			@Override
			public Predicate toPredicate(Root<Focus> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p0=null;
				if(uid!=null&&uid.length()>0){
					p0=cb.equal(root.<User>get("user").<String>get("id"),uid);
				}
				if(did!=null&&did.length()>0){
					p0=cb.equal(root.<Demands>get("demands").<String>get("id"),did);
				}
				query.where(p0);
				return query.getRestriction();
			}
		};
		return focusRepository.findAll(specification, pageRequest);
	}

	@Override
	public void delFocus(User user, Demands demands) {
		focusRepository.delete(focusRepository.findFocusByUserAndDemands(user, demands));
	}

}
