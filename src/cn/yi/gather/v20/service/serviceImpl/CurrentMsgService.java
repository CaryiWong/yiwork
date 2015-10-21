package cn.yi.gather.v20.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.CurrentMsgRepository;
import cn.yi.gather.v20.entity.CurrentMsg;
import cn.yi.gather.v20.service.ICurrentMsgService;

@Service("currentMsgServiceV20")
public class CurrentMsgService implements ICurrentMsgService {
	
	@Resource(name = "CurrentMsgRepositoryV20")
	private CurrentMsgRepository repository;

	@Override
	public CurrentMsg saveOrUpdate(CurrentMsg entity) {
		// TODO Auto-generated method stub
		repository.save(entity);
		return findById(entity.getId());
	}

	@Override
	public CurrentMsg findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Page<CurrentMsg> findForPage(Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page, size,new Sort(Direction.DESC, "createdate"));
		return repository.findAll(pageRequest);
	}

	@Override
	public List<CurrentMsg> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll(new Sort(Direction.DESC, "createdate"));
	}

	@Override
	public void clearNowMsg() {
		// TODO Auto-generated method stub
		repository.deleteAll();
	}
	
	
}
