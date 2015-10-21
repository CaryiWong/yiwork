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



import cn.yi.gather.v20.dao.ApplyvipRepository;
import cn.yi.gather.v20.dao.Co_working_spaceRepository;
import cn.yi.gather.v20.dao.TribeRepository;
import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Co_working_space;
import cn.yi.gather.v20.entity.Tribe_partner;
import cn.yi.gather.v20.service.IApplyvipService;
import cn.yi.gather.v20.service.ICo_working_spaceService;
import cn.yi.gather.v20.service.ITribeService;

/**
 * 3.	众创空间
 * @author kcm
 * @time 2015年5月13日下午3:06:21
 */
@Service("co_working_spaceServiceV20")
public class Co_working_spaceService implements ICo_working_spaceService{
	@Resource(name = "coworkingspaceRepositoryV20")
	private Co_working_spaceRepository repository;

	@Override
	public Co_working_space SaveOrUpdate(Co_working_space obj) {
		// TODO Auto-generated method stub
		return repository.save(obj);
	}

	@Override
	public Co_working_space findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public com.common.Page<Co_working_space> co_working_spacelist(Integer page,
			Integer size) {
		com.common.Page<Co_working_space> result = new com.common.Page<Co_working_space>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate"));
		Specification<Co_working_space> spec = new Specification<Co_working_space>() {	
			@Override
			public Predicate toPredicate(Root<Co_working_space> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps = new ArrayList<Predicate>();
				query.where(ps.toArray(new Predicate[ps.size()])).orderBy(cb.desc(root.get("createdate")));
				return query.getRestriction();
			}
		};
		Page<Co_working_space> users = repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(users.getNumber());
		result.setPageSize(users.getSize());
		result.setResult(users.getContent());
		result.setTotalCount((int)users.getTotalElements());
		return result;
	}

	 

}
