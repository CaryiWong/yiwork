package cn.yi.gather.v20.service.serviceImpl;

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

import cn.yi.gather.v20.dao.IndexuserinfoRepository;
import cn.yi.gather.v20.entity.Indexuserinfo;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.service.IIndexuserinfoService;
import cn.yi.gather.v20.service.ILabelsService;

/**
 * 个人展示
 * @author Lee.J.Eric
 *
 */
@Service("indexuserinfoServiceV20")
public class IndexuserinfoService implements IIndexuserinfoService{
	
	@Resource(name = "indexuserinfoRepositoryV20")
	private IndexuserinfoRepository repository;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Override
	public Indexuserinfo indexuserinfoSaveOrUpdate(Indexuserinfo indexuserinfo) {
		// TODO Auto-generated method stub
		return repository.save(indexuserinfo);
	}

	@Override
	public Indexuserinfo findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		repository.delete(id);
	}

	@Override
	public List<Indexuserinfo> getByIdList(List<String> ids) {
		// TODO Auto-generated method stub
		List<Indexuserinfo> list = new ArrayList<Indexuserinfo>();
		for (String id : ids) {
			list.add(findById(id));
		}
		return list;
	}

	@Override
	public Page<Indexuserinfo> searchByKeyword(Integer page, Integer size,
			final String keyword) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "id"));
		Specification<Indexuserinfo> spec = new Specification<Indexuserinfo>() {
			
			@Override
			public Predicate toPredicate(Root<Indexuserinfo> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				if(StringUtils.isNotBlank(keyword)){
					Predicate p0 = cb.like(cb.lower(root.<String>get("iusernickname")), "%"+keyword.toLowerCase()+"%");
					Labels labels = labelsService.findByLabeltypeAndZnameAndPid("job", keyword, 0l);
					Predicate p1 = cb.isMember(labels,root.<List<Labels>>get("iuserlables"));
					query.where(cb.or(p0,p1));
				}else {
					query.where(cb.isNotNull(root.<String>get("id")));
				}
				return query.getRestriction();
			}
		};
		return repository.findAll(spec, pageRequest);
	}
	
}
