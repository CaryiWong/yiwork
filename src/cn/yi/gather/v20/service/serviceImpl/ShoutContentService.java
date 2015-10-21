package cn.yi.gather.v20.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.ShoutContentRepository;
import cn.yi.gather.v20.entity.ShoutContent;
import cn.yi.gather.v20.service.IShoutContentService;

@Service("shoutContentServiceV20")
public class ShoutContentService implements IShoutContentService{

	@Resource(name = "shoutContentRepositoryV20")
	private ShoutContentRepository repository;
	
	@Override
	public ShoutContent saveOrUpdate(ShoutContent entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public ShoutContent findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<ShoutContent> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Page<ShoutContent> findPage(Integer page, Integer size) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size);
		return repository.findAll(pageRequest);
	}

}
