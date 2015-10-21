package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

 

import cn.yi.gather.v20.dao.ApplyvipRepository;
import cn.yi.gather.v20.dao.EnterpriseRepository;
import cn.yi.gather.v20.dao.TribeRepository;
import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Enterprise;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.service.IApplyvipService;
import cn.yi.gather.v20.service.IEnterpriseService;
import cn.yi.gather.v20.service.ITribeService;
 



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
/**
 * 2.	品牌商／企业
 * @author kcm
 * @time 2015年5月13日下午3:06:21
 */
@Service("enterpriseServiceV20")
public class EnterpriseService implements IEnterpriseService{
	@Resource(name = "enterpriseRepositoryV20")
	private EnterpriseRepository repository;

	@Override
	public Enterprise SaveOrUpdate(Enterprise applyvip) {
		// TODO Auto-generated method stub
		return repository.save(applyvip);
	}

	@Override
	public Enterprise findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public com.common.Page<Enterprise> enterpriselist(Integer page, Integer size) {
		com.common.Page<Enterprise> result = new com.common.Page<Enterprise>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<Enterprise> spec = new Specification<Enterprise>() {	
			@Override
			public Predicate toPredicate(Root<Enterprise> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				//Predicate p0 = null;
				List<Predicate> ps = new ArrayList<Predicate>();
				query.where(ps.toArray(new Predicate[ps.size()])).orderBy(cb.desc(root.get("createdate")));
				return query.getRestriction();
			}
		};
		Page<Enterprise> users = repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(users.getNumber());
		result.setPageSize(users.getSize());
		result.setResult(users.getContent());
		result.setTotalCount((int)users.getTotalElements());
		return result;
	}

}
