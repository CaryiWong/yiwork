package cn.yi.gather.v20.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.YqServiceRepository;
import cn.yi.gather.v20.entity.YqServiceInfo;
import cn.yi.gather.v20.service.IYqServiceService;

@Service("yqServiceService")
public class YqServiceService implements IYqServiceService{
	
	@Resource(name="yqServiceRepository")
	private YqServiceRepository repository;

	@Override
	public YqServiceInfo save(YqServiceInfo serviceInfo) {
		// TODO Auto-generated method stub
		return repository.save(serviceInfo);
	}

	@Override
	public YqServiceInfo findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<YqServiceInfo> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Page<YqServiceInfo> findAll(Integer size, Integer page) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size);
		return repository.findAll(pageRequest);
	}

	@Override
	public List<YqServiceInfo> findBanner() {
		// TODO Auto-generated method stub
		return repository.findByIsbanner(1);
	}

}
