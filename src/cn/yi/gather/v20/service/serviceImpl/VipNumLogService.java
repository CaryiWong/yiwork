package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.VipNumLogRepository;
import cn.yi.gather.v20.entity.VipNumLog;
import cn.yi.gather.v20.service.IVipNumLogService;

@Service("vipNumLogServiceV20")
public class VipNumLogService implements IVipNumLogService{

	@Resource(name = "vipNumLogRepositoryV20")
	private VipNumLogRepository repository;

	@Override
	public VipNumLog saveOrUpdate(VipNumLog entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public void saveOrUpdate(List<VipNumLog> entities) {
		// TODO Auto-generated method stub
		repository.save(entities);
	}

	@Override
	public VipNumLog findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public VipNumLog findByVipNO(String vipNO) {
		// TODO Auto-generated method stub
		return repository.findByVipNO(vipNO);
	}

	@Override
	public VipNumLog findNextVip() throws Exception{
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(0,1,new Sort(Direction.ASC, "orderNO"));
		Specification<VipNumLog> spec = new Specification<VipNumLog>() {
			@Override
			public Predicate toPredicate(Root<VipNumLog> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<Boolean>get("status"), false));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		List<VipNumLog> list = repository.findAll(spec, pageRequest).getContent();
		if(list.isEmpty()){
			throw new Exception("no available vip NO. enough");
		}
		return repository.findAll(spec, pageRequest).getContent().get(0);
	}


	@Override
	public void recycleVipNO(final List<String> vipNOs) {
		// TODO Auto-generated method stub
		Specification<VipNumLog> spec = new Specification<VipNumLog>() {
			
			@Override
			public Predicate toPredicate(Root<VipNumLog> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(root.<String>get("vipNO").in(vipNOs));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		List<VipNumLog> logs = repository.findAll(spec);
		for (VipNumLog l : logs) {
			l.setStatus(false);//未使用
		}
		repository.save(logs);
	}
	
}
