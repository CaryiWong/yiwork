package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;





import cn.yi.gather.v20.dao.ApplyvipRepository;
import cn.yi.gather.v20.dao.TribeRepository;
import cn.yi.gather.v20.dao.Tribe_partnerRepository;
import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.entity.Tribe_partner;
import cn.yi.gather.v20.entity.ItemInstance.ItemInstanceStatus;
import cn.yi.gather.v20.service.IApplyvipService;
import cn.yi.gather.v20.service.ITribeService;
import cn.yi.gather.v20.service.ITribe_partnerService;

import org.springframework.stereotype.Service;

 

import cn.yi.gather.v20.dao.ApplyvipRepository;
import cn.yi.gather.v20.dao.TribeRepository;
import cn.yi.gather.v20.dao.VisitRepository;
import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.entity.Visit;
import cn.yi.gather.v20.service.IApplyvipService;
import cn.yi.gather.v20.service.ITribeService;
import cn.yi.gather.v20.service.IVisitService;

/**
 * 5.	预约参观
 * @author kcm
 * @time 2015年5月13日下午3:06:21
 */
@Service("visitServiceV20")
public class VisitService implements IVisitService{
	@Resource(name = "visitRepositoryV20")
	private VisitRepository repository;

	@Override
	public Visit SaveOrUpdate(Visit applyvip) {
		// TODO Auto-generated method stub
		return repository.save(applyvip);
	}

	@Override
	public Visit findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public com.common.Page<Visit> visitlist(Integer page, Integer size) {
		com.common.Page<Visit> result = new com.common.Page<Visit>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<Visit> spec = new Specification<Visit>() {	
			@Override
			public Predicate toPredicate(Root<Visit> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				//Predicate p0 = null;
				List<Predicate> ps = new ArrayList<Predicate>();
				query.where(ps.toArray(new Predicate[ps.size()])).orderBy(cb.desc(root.get("createdate")));
				return query.getRestriction();
			}
		};
		Page<Visit> users = repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(users.getNumber());
		result.setPageSize(users.getSize());
		result.setResult(users.getContent());
		result.setTotalCount((int)users.getTotalElements());
		return result;
	}

}
