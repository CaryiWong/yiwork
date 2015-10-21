package cn.yi.gather.v20.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v20.dao.DcommentRepository;
import cn.yi.gather.v20.dao.DemandsRepository;
import cn.yi.gather.v20.entity.Dcomment;
import cn.yi.gather.v20.entity.Dcomment.DCommentTypeValue;
import cn.yi.gather.v20.entity.Demands;
import cn.yi.gather.v20.entity.Order;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.IDcommentService;

@Service("dcommentServiceV20")
public class DcommentService implements IDcommentService{

	@Resource(name = "dcommentRepositoryV20")
	private DcommentRepository dcommentRepository;
	
	@Resource(name = "demandsRepositoryV20")
	private DemandsRepository demandsRepository;
	
	@Transactional
	@Override
	public Dcomment dcommentSaveOrUpdate(Dcomment dcomment) {
		Demands d=demandsRepository.findOne(dcomment.getDemands().getId());
		if(d!=null){
			Dcomment dc=dcommentRepository.findOne(dcomment.getId());
			if(dc!=null){
				if(dcomment.getIsdel()==1){
					d.setCommentnum(d.getCommentnum()-1);
				}
			}else{
				d.setCommentnum(d.getCommentnum()+1);
			}
		demandsRepository.save(d);
		}else{
			return null;
		}
		return dcommentRepository.save(dcomment);
	}

	@Override
	public List<Dcomment> getDcommentListByPid(String pid) {
		return dcommentRepository.findByPid(pid);
	}

	@Override
	public Dcomment getDcommentByID(String id) {
		return dcommentRepository.findOne(id);
	}

	@Override
	public void dcommentDel(String id) {
		dcommentRepository.delete(id);
	}

	@Override
	public Page<Dcomment> dcommentFind(Integer page, Integer size,final 
			String orderby,final String uid,final String id) {
		PageRequest pageRequest = new PageRequest(page, size);
		
		Specification<Dcomment> spec = new Specification<Dcomment>() {
			@Override
			public Predicate toPredicate(Root<Dcomment> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Predicate p1=cb.equal(root.<Demands>get("demands").<String>get("id"), id);
				Predicate p2=cb.equal(root.<Integer>get("isdel"), 0);
				Expression<?> e1 =cb.selectCase().when(cb.equal(root.<String>get("commenttype"),DCommentTypeValue.RECOMMEND.value),1)
				.otherwise(0);
				if("asc".equals(orderby)){
					query.where(p1,p2).orderBy(cb.desc(e1),cb.asc(root.get("createdate1")));
				}else {
					query.where(p1,p2).orderBy(cb.desc(e1),cb.desc(root.get("createdate1")));
				}
				return query.getRestriction();
			}
		};
		return dcommentRepository.findAll(spec, pageRequest);
	}

	@Override
	public Page<Dcomment> dcommentFindByUid(Integer page, Integer size,
			Integer orderby,final String uid) {
		PageRequest pageRequest = new PageRequest(page, size);
		if(orderby==1){
			pageRequest = new PageRequest(page,size,new Sort(Direction.ASC, "createdate1"));
		}else {
			pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate1"));
		}
		Specification<Dcomment> spec = new Specification<Dcomment>() {
			@Override
			public Predicate toPredicate(Root<Dcomment> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p0=cb.equal(root.<User>get("user").<String>get("id"), uid);
				Predicate p1=cb.equal(root.<Integer>get("isdel"), 0);
				query.groupBy(root.<Demands>get("demands"));
				query.where(p0,p1);
				return query.getRestriction();
			}
		};
		return dcommentRepository.findAll(spec, pageRequest);
	}

}
