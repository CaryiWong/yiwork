package cn.yi.gather.v11.service.serviceImpl;

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

import cn.yi.gather.v11.dao.DcommentRepository;
import cn.yi.gather.v11.dao.DemandsRepository;
import cn.yi.gather.v11.entity.Dcomment;
import cn.yi.gather.v11.entity.Demands;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.service.IDcommentServiceV2;

@Service("dcommentServiceV2")
public class DcommentServiceV2 implements IDcommentServiceV2{

	@Resource(name = "dcommentRepositoryV2")
	private DcommentRepository dcommentRepository;
	
	@Resource(name = "demandsRepositoryV2")
	private DemandsRepository demandsRepository;
	
	@Override
	public Dcomment dcommentSaveOrUpdate(Dcomment dcomment) {
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
	public Page<Dcomment> dcommentFind(Integer page, Integer size,
			Integer orderby,final String uid,final String id) {
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
				Predicate p0=cb.isNotNull(root.<String>get("id"));
				Predicate p1=cb.equal(root.<Demands>get("demands").<String>get("id"), id);
				/*Predicate p1=cb.isNotNull(root.<String>get("id"));
				Predicate p2=cb.isNotNull(root.<String>get("id"));
				Predicate p3=cb.isNotNull(root.<String>get("id"));
				if(id!=null&&id.length()>0){ //拿需求的评论  需要结合用户ID判断权限
					Demands demands=demandsRepository.findOne(id);
					p1=cb.equal(root.<Demands>get("demands").<String>get("id"), id);//需求发起者可以查看所有
					if(uid!=null&&uid.length()>0){
						if(!uid.equals(demands.getPublishuser().getId())){ 
							Predicate p=cb.equal(root.<User>get("user").<String>get("id"), uid);//拿用户与需求发起者之间的对话
							Predicate pp=cb.equal(root.<User>get("receive").<String>get("id"), uid);//拿用户与需求发起者之间的对话
							p2=cb.or(p,pp);
						}
					}
				}else{
					if(uid!=null&&uid.length()>0){
						p3=cb.equal(root.<User>get("user").<String>get("id"), uid);//拿自己发表的评论
					}
				}*/
				query.where(p0,p1);
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
				query.groupBy(root.<Demands>get("demands"));
				query.where(p0);
				return query.getRestriction();
			}
		};
		return dcommentRepository.findAll(spec, pageRequest);
	}

}
