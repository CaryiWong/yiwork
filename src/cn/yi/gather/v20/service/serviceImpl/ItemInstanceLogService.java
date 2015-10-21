package cn.yi.gather.v20.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.ItemInstanceLogRepository;
import cn.yi.gather.v20.entity.ItemInstanceLog;
import cn.yi.gather.v20.service.IItemInstanceLogService;

@Service("itemInstanceLogServiceV20")
public class ItemInstanceLogService implements IItemInstanceLogService{

	@Resource(name = "itemInstanceLogRepositoryV20")
	private ItemInstanceLogRepository repository;

	@Override
	public void saveOrUpdate(List<ItemInstanceLog> logs) {
		// TODO Auto-generated method stub
		repository.save(logs);
	}

	@Override
	public ItemInstanceLog saveOrUpdate(ItemInstanceLog entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}
}
