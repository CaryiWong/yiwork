package cn.yi.gather.v20.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.ShoutContent;

public interface IShoutContentService {
	
	public ShoutContent saveOrUpdate(ShoutContent entity);
	
	public ShoutContent findById(String id);
	
	public List<ShoutContent> findAll();
	
	public Page<ShoutContent> findPage(Integer page,Integer size);
	
}
