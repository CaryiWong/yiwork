package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;

import java.util.Date;
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

/**
 * 4.	社区伙伴
 * @author kcm
 * @time 2015年5月13日下午3:06:21
 */
@Service("tribe_partnerServiceServiceV20")
public class Tribe_partnerService implements ITribe_partnerService{
	@Resource(name = "tribe_partnerRepositoryV20")
	private Tribe_partnerRepository repository;

	@Override
	public Tribe_partner SaveOrUpdate(Tribe_partner applyvip) {
		// TODO Auto-generated method stub
		return repository.save(applyvip);
	}

	@Override
	public Tribe_partner findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public com.common.Page<Tribe_partner> tribe_partnerlist(Integer page, Integer size) {
		com.common.Page<Tribe_partner> result = new com.common.Page<Tribe_partner>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<Tribe_partner> spec = new Specification<Tribe_partner>() {	
			@Override
			public Predicate toPredicate(Root<Tribe_partner> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				//Predicate p0 = null;
				List<Predicate> ps = new ArrayList<Predicate>();
				query.where(ps.toArray(new Predicate[ps.size()])).orderBy(cb.desc(root.get("createdate")));
				return query.getRestriction();
			}
		};
		Page<Tribe_partner> users = repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(users.getNumber());
		result.setPageSize(users.getSize());
		result.setResult(users.getContent());
		result.setTotalCount((int)users.getTotalElements());
		return result;
	}

}
