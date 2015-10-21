package cn.yi.gather.v11.service.serviceImpl;

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

import cn.yi.gather.v11.dao.IndexuserinfoRepository;
import cn.yi.gather.v11.entity.Indexuserinfo;
import cn.yi.gather.v11.entity.Labels;
import cn.yi.gather.v11.service.IIndexuserinfoServiceV2;
import cn.yi.gather.v11.service.ILabelsServiceV2;

/**
 * 个人展示
 * @author Lee.J.Eric
 *
 */
@Service("indexuserinfoServiceV2")
public class IndexuserinfoServiceV2 implements IIndexuserinfoServiceV2{
	@Resource(name = "indexuserinfoRepositoryV2")
	private IndexuserinfoRepository repository;
	
	@Resource(name = "labelsServiceV2")
	private ILabelsServiceV2 labelsServiceV2;
	
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
					Labels labels = labelsServiceV2.getLabelsByLabeltypeAndZname(0, keyword);
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
