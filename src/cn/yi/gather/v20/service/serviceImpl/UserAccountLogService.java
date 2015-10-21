package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.UserAccountLogRepository;
import cn.yi.gather.v20.entity.UserAccountLog;
import cn.yi.gather.v20.service.IUserAccountLogService;

@Service("userAccountLogServiceV20")
public class UserAccountLogService implements IUserAccountLogService{

	@Resource(name = "userAccountLogRepositoryV20")
	private UserAccountLogRepository repository;
	
	@Override
	public UserAccountLog findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public UserAccountLog saveOrUpdate(UserAccountLog entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

}
