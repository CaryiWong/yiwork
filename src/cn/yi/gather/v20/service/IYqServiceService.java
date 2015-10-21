package cn.yi.gather.v20.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.YqServiceInfo;

public interface IYqServiceService {

	public YqServiceInfo save(YqServiceInfo serviceInfo);
	
	public YqServiceInfo findById(String id);
	
	public List<YqServiceInfo> findAll();
	
	public Page<YqServiceInfo> findAll(Integer page, Integer size);
	
	public List<YqServiceInfo> findBanner();
	
}
