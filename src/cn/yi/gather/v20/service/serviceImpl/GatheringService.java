package cn.yi.gather.v20.service.serviceImpl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.xpath.internal.operations.Bool;

import cn.yi.gather.v20.dao.GatheringRepository;
import cn.yi.gather.v20.dao.UserRepository;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.IEventlogService;
import cn.yi.gather.v20.service.IGatheringService;

@Service("gatheringServiceV20")
public class GatheringService implements IGatheringService{
	
	@Resource(name = "gatheringRepositoryV20")
	private GatheringRepository repository;
	
	@Resource(name = "userRepositoryV20")
	private UserRepository userRepository;
	
	@Resource(name = "eventlogServiceV20")
	private IEventlogService eventlogService;

	@Override
	public Gathering gatheringSaveOrUpdate(Gathering gathering) {
		// TODO Auto-generated method stub
		repository.save(gathering);
		return findById(gathering.getId());
	}

	@Override
	public Gathering findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Page<User> findSignList(final String id, Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size);
		Specification<User> spec = new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> ps = new ArrayList<Predicate>();
				Root<Gathering> rootgat=query.from(Gathering.class);
				ps.add(cb.equal(rootgat.<String>get("id"), id));
				Join<Gathering,User> userJoin =rootgat.join("signuser");
				ps.add(cb.equal(userJoin.<String>get("id"), root.<String>get("id")));
				Predicate[] parr = new Predicate[ps.size()]; 
				ps.toArray(parr);
				query.where(parr);
				return query.getRestriction();
			}
		};
		return userRepository.findAll(spec, pageRequest);
	}

	@Transactional
	@Override
	public void deleteGathering(Gathering gathering) {
		// TODO Auto-generated method stub
		repository.save(gathering);
		eventlogService.deleteByGathering(gathering.getId());
	}

	@Override
	public List<Gathering> findListByOpendate(Date beginDate, Date endDate) {
		// TODO Auto-generated method stub
		return repository.findByOpendateBetween(beginDate, endDate);
	}
	
	@Override
	public boolean checkTime(Date openDate, Date endDate,String id) {
		Boolean falg=true;
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(openDate);
			c.add(Calendar.HOUR, -2);
			List<Gathering> list=repository.findByOpendateBetween(c.getTime(), endDate);//含边界值
			if(list!=null&&list.size()>0){
				for (Gathering gathering : list) {
					if(falg&&id.equals(gathering.getId())){
					}else{
						if(falg&&gathering.getOpendate()==endDate){
						}else if(falg&&gathering.getEnddate().before(openDate)){
						}else if(falg&&gathering.getEnddate()==openDate){
						}else{
							return false;
						}
					}
				}
			}
		} catch (Exception e) {
			falg=false;
			e.printStackTrace();
		}
		return falg;
	}

}
