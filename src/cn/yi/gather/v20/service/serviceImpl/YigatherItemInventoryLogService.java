package cn.yi.gather.v20.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.YigatherItemInventoryLogRepository;
import cn.yi.gather.v20.entity.YigatherItemInventoryLog;
import cn.yi.gather.v20.service.IYigatherItemInventoryLogService;

@Service("yigatherItemInventoryLogServiceV20")
public class YigatherItemInventoryLogService implements IYigatherItemInventoryLogService{
	
	@Resource(name = "yigatherItemInventoryLogRepositoryV20")
	private YigatherItemInventoryLogRepository repository;

	@Override
	public YigatherItemInventoryLog saveOrUpdate(YigatherItemInventoryLog entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public void saveOrUpdate(List<YigatherItemInventoryLog> entities) {
		// TODO Auto-generated method stub
		repository.save(entities);
	}
	
	

}
