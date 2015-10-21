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

import cn.yi.gather.v11.dao.AppversionRepository;
import cn.yi.gather.v11.entity.Appversion;
import cn.yi.gather.v11.service.IAppversionServiceV2;

@Service("appversionServiceV2")
public class AppversionServiceV2 implements IAppversionServiceV2{
	@Resource(name = "appversionRepositoryV2")
	private AppversionRepository repository;

	@Override
	public Appversion appversionSaveOrUpdate(Appversion appversion) {
		// TODO Auto-generated method stub
		return repository.save(appversion);
	}

	@Override
	public Appversion findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Appversion getLastedAppversionByPlatform(final String platform) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(0,1,new Sort(Direction.DESC, "createdate"));
		Specification<Appversion> spec = new Specification<Appversion>() {
			
			@Override
			public Predicate toPredicate(Root<Appversion> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p = cb.equal(root.<String>get("platform"), platform);
				query.where(p);
				return query.getRestriction();
			}
		};
		Page<Appversion> page = repository.findAll(spec, pageRequest);
		List<Appversion> list = page.getContent();
		if(list.isEmpty())
			return null;
		return list.get(0);
	}

	
	@Override
	public Page<Appversion> getPage(Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		return repository.findAll(pageRequest);
	}

	@Override
	public List<Appversion> getList(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return getPage(page,size).getContent();
	}

	@Override
	public com.common.Page<Appversion> versionList(
			com.common.Page<Appversion> page) {
		// TODO Auto-generated method stub
		Page<Appversion> result =  getPage(page.getCurrentPage(),page.getPageSize());
		page.setTotalCount(result.getNumberOfElements());
		page.setResult(result.getContent());
		return page;
	}

}
