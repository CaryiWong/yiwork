package cn.yi.gather.v20.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.CommunityInfo;

public interface ICommunityService {

	public CommunityInfo saveOrUpdate(CommunityInfo communityInfo);
	
	public CommunityInfo findById(String id);
	
	public Page<CommunityInfo> findAll(Integer size,Integer page);
	
	public List<CommunityInfo> findAll();
	
	public List<CommunityInfo> findBanner();
	
}
