package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.UserotherInfoRepository;
import cn.yi.gather.v20.entity.UserotherInfo;
import cn.yi.gather.v20.service.IUserotherinfoService;

@Service("userotherinfoServiceV20")
public class UserotherinfoService implements IUserotherinfoService{

	@Resource(name = "userotherInfoRepositoryV20")
	private UserotherInfoRepository repository;

	@Override
	public UserotherInfo saveOrUpdateUserother(UserotherInfo userotherInfo) {
		return repository.save(userotherInfo);
	}

	@Override
	public UserotherInfo findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		repository.delete(id);
	}
	
	public UserotherInfo findByUserIdAndUserotherId(String uid,String oid) {
		
		return repository.findOne("");
	}

	
}
