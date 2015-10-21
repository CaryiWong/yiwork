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

import cn.yi.gather.v20.dao.AppversionRepository;
import cn.yi.gather.v20.entity.Appversion;
import cn.yi.gather.v20.service.IAppversionService;

@Service("appversionServiceV20")
public class AppversionService implements IAppversionService{
	@Resource(name = "appversionRepositoryV20")
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
	public Appversion getLastedAppversionByPlatform(final String platform,final Integer ver,final String version) {
		// TODO Auto-generated method stub
		Specification<Appversion> spec = new Specification<Appversion>() {
			
			@Override
			public Predicate toPredicate(Root<Appversion> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("platform"), platform));
				if(platform.equals("ios")){
					ps.add(cb.greaterThanOrEqualTo(root.<String>get("version"), version));
				}
				if(platform.equals("android")){
					ps.add(cb.ge(root.<Integer>get("ver"), ver));
				}
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		
		List<Appversion> list = repository.findAll(spec, new Sort(Direction.DESC, "createdate"));
		if(list.isEmpty()){
			return null;
		}else {
			String force_flag = "n";
			for (Appversion appversion : list) {
				if(appversion.getForce().equals("y")){
					force_flag = "y";
					break;
				}
			}
			Appversion appver = list.get(0);
			appver.setForce(force_flag);
			return appver;
		}
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
