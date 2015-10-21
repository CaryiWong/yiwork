package cn.yi.gather.v20.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.CommunityRepository;
import cn.yi.gather.v20.entity.CommunityInfo;
import cn.yi.gather.v20.service.ICommunityService;

@Service("communityService")
public class CommunityService implements ICommunityService{

	@Resource(name="communityRepository")
	private CommunityRepository repository;

	@Override
	public CommunityInfo saveOrUpdate(CommunityInfo communityInfo) {
		// TODO Auto-generated method stub
		return repository.save(communityInfo);
	}

	@Override
	public CommunityInfo findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public Page<CommunityInfo> findAll(Integer size, Integer page) {
		// TODO Auto-generated method stub
		PageRequest pageRequest = new PageRequest(page,size);
		return repository.findAll(pageRequest);
	}

	@Override
	public List<CommunityInfo> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<CommunityInfo> findBanner() {
		// TODO Auto-generated method stub
		return repository.findByIsbanner(1);
	}
	
}
