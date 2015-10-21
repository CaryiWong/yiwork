package cn.yi.gather.v20.editor.service.impl;

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

import cn.yi.gather.v20.editor.dao.ObjectOptionsRepository;
import cn.yi.gather.v20.editor.entity.ObjectOptions;
import cn.yi.gather.v20.editor.entity.Template;
import cn.yi.gather.v20.editor.service.IObjectOptionsService;

/**
 * 模板所填所有属性
 * @author Administrator
 *
 */
@Service("objectOptionsServiceV20")
public class ObjectOptionsService implements IObjectOptionsService{

	@Resource(name="objectOptionsRepositoryV20")
	private ObjectOptionsRepository repository;
	
	@Override
	public ObjectOptions saveOrupdate(ObjectOptions objectOptions) {
		// TODO Auto-generated method stub
		return repository.save(objectOptions);
	}

	@Override
	public ObjectOptions findObjectOptions(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<ObjectOptions> findAllObjectOptions() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Page<ObjectOptions> findObjectOptionsByTemplate(final String template_id,Integer page,Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size);
		
		Specification<ObjectOptions> spec=new Specification<ObjectOptions>() {
			@Override
			public Predicate toPredicate(Root<ObjectOptions> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Template>get("template").<String>get("id") ,template_id));
				
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr)).groupBy(root.<String>get("objid"));
				return query.getRestriction();
			}
		};
		
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public Page<ObjectOptions> findObjectOptionsByObj(final String objid,final String objtype,Integer page,Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size);
		
		Specification<ObjectOptions> spec=new Specification<ObjectOptions>() {
			@Override
			public Predicate toPredicate(Root<ObjectOptions> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("objid") ,objid));
				ps.add(cb.equal(root.<String>get("objtype") ,objtype));
				
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr)).groupBy(root.<Template>get("template").<String>get("id"));
				return query.getRestriction();
			}
		};
		
		return repository.findAll(spec, pageRequest);
	}

	@Override
	public List<ObjectOptions> findObjectOptionsByObj(final String objid) {
		Specification<ObjectOptions> spec=new Specification<ObjectOptions>() {
			@Override
			public Predicate toPredicate(Root<ObjectOptions> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("objid") ,objid));
				
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr)).groupBy(root.<Template>get("template").<String>get("id"));
				return query.getRestriction();
			}
		};
		
		return repository.findAll(spec);
	}

	@Override
	public List<ObjectOptions> updateObj(String objid, String objtype,
			String lsid) {
		repository.updateObj(objid, objtype, lsid);
		return this.findObjectOptionsByObj(objid);
	}

	@Override
	public Page<ObjectOptions> objectlistbyparam(final String objid,final  String objtype,
			final String tmpid, Integer page, Integer size) {
		PageRequest pageRequest;
		if(page<0){
			pageRequest = new PageRequest(page, size);
		}else{
			pageRequest = new PageRequest(0, 1000);
		}
		
		Specification<ObjectOptions> spec=new Specification<ObjectOptions>() {
			@Override
			public Predicate toPredicate(Root<ObjectOptions> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				if(objid!=null&&objid.length()>0){
					ps.add(cb.equal(root.<String>get("objid") ,objid));
				}
				if(objtype!=null&&objtype.length()>0){
					ps.add(cb.equal(root.<String>get("objtype") ,objtype));
				}
				if(tmpid!=null&&tmpid.length()>0){
					ps.add(cb.equal(root.<Template>get("template").<String>get("id") ,tmpid));
				}
				
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr)).groupBy(root.<Template>get("template").<String>get("id"));
				return query.getRestriction();
			}
		};
		
		return repository.findAll(spec,pageRequest);
	}

}
