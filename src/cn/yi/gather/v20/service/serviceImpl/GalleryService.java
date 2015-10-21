package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.GalleryRepository;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Course;
import cn.yi.gather.v20.entity.Gallery;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.service.IGalleryService;

@Service("galleryServiceV20")
public class GalleryService implements IGalleryService{
	
	@Resource(name = "galleryRepositoryV20")
	private GalleryRepository repository;

	@Override
	public Gallery gallerySaveOrUpdate(Gallery gallery) {
		// TODO Auto-generated method stub
		return repository.save(gallery);
	}

	@Override
	public List<Gallery> findByIdAndFlag(final String id,final Integer flag) {
		// TODO Auto-generated method stub
		Specification<Gallery> spec = new Specification<Gallery>() {
			
			@Override
			public Predicate toPredicate(Root<Gallery> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps=new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Integer>get("isdel"), 0));//未删除
				ps.add(cb.equal(root.<Integer>get("flag"), flag));
				if (flag==0) {//小活动
					ps.add(cb.equal(root.<Gathering>get("gathering").<String>get("id"), id));
				}else if (flag==1) {//活动
					ps.add(cb.equal(root.<Activity>get("activity").<String>get("id"), id));
				}else if(flag==2){//课程
					ps.add(cb.equal(root.<Course>get("course").<String>get("id"), id));
				}
				Predicate[] parr=new Predicate[ps.size()];
				ps.toArray(parr);
				query.where(cb.and(parr)).orderBy(cb.desc(root.<Date>get("createtime")));
				return query.distinct(true).getRestriction();
			}
		};
		return repository.findAll(spec);
	}

}
