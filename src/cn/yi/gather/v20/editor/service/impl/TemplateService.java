package cn.yi.gather.v20.editor.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.editor.dao.TemplateRepository;
import cn.yi.gather.v20.editor.entity.Template;
import cn.yi.gather.v20.editor.service.ITemplateService;

/**
 * 模板管理
 * @author Administrator
 *
 */
@Service("templateServiceV20")
public class TemplateService implements ITemplateService{
	
	@Resource(name="templateRepositoryV20")
	private TemplateRepository repository;

	@Override
	public Template saveOrupdate(Template template) {
		// TODO Auto-generated method stub
		return repository.save(template);
	}

	@Override
	public Template findTemplate(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<Template> findTemplateList() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<Template> findTemplateList(List<String> ids) {
		// TODO Auto-generated method stub
		return repository.findByIdIn(ids);
	}

	@Override
	public List<Template> findTemplateList(final String pid) {
		// TODO Auto-generated method stub
		
		Specification<Template> spec=new Specification<Template>() {
			@Override
			public Predicate toPredicate(Root<Template> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps=new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("pid"),pid));
				ps.add(cb.notEqual(root.<String>get("pid"),root.<String>get("id")));
				
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr)).orderBy(cb.asc(root.get("templatenum")));
				return query.getRestriction();
			}
		};
		
		return repository.findAll(spec);
	}

}
