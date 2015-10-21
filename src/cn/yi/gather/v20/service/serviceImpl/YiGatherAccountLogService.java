package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.YiGatherAccountLogRepository;
import cn.yi.gather.v20.entity.YiGatherAccountLog;
import cn.yi.gather.v20.service.IYiGatherAccountLogService;

@Service("yiGatherAccountLogServiceV20")
public class YiGatherAccountLogService implements IYiGatherAccountLogService{

	@Resource(name = "yiGatherAccountLogRepositoryV20")
	private YiGatherAccountLogRepository repository;
	
	@Override
	public YiGatherAccountLog saveOrUpdate(YiGatherAccountLog entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public YiGatherAccountLog findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
