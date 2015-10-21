package cn.yi.gather.v11.service.serviceImpl;

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

import cn.yi.gather.v11.dao.SysnetworkRepository;
import cn.yi.gather.v11.entity.Sysnetwork;
import cn.yi.gather.v11.service.ISysnetworkServiceV2;

/**
 * 广播内容
 * @author Lee.J.Eric
 * @time 2014年6月11日下午12:31:48
 */
@Service("sysnetworkServiceV2")
public class SysnetworkServiceV2 implements ISysnetworkServiceV2{

	@Resource(name = "sysnetworkRepositoryV2")
	private SysnetworkRepository repository;

	@Override
	public com.common.Page<Sysnetwork> getSysnetworkForPage(Integer page, Integer size,
			final String keyword) {
		// TODO Auto-generated method stub
		com.common.Page<Sysnetwork> result = new com.common.Page<Sysnetwork>();
		PageRequest pageRequest = new PageRequest(page,size,new Sort(Direction.DESC, "createdate1"));
		Specification<Sysnetwork> spec = new Specification<Sysnetwork>() {

			@Override
			public Predicate toPredicate(Root<Sysnetwork> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = null;
				if(StringUtils.isNotBlank(keyword)){
					p0 = cb.like(cb.lower(root.<String>get("title")), "%"+keyword.toLowerCase()+"%");
				}else {
					p0 = cb.isNotNull(root.<String>get("id"));
				}
				query.where(p0);
				return query.getRestriction();
			}
		};
		Page<Sysnetwork> list = repository.findAll(spec, pageRequest);
		result.setCurrentPage(page);
		result.setCurrentCount(list.getNumber());
		result.setPageSize(list.getSize());
		result.setResult(list.getContent());
		result.setTotalCount(list.getNumberOfElements());
		result.setTotalPage(list.getTotalPages());
		return result;
	}

	@Override
	public Sysnetwork sysnetworkSaveOrUpdate(Sysnetwork sysnetwork) {
		// TODO Auto-generated method stub
		return repository.save(sysnetwork);
	}

	@Override
	public Sysnetwork findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
