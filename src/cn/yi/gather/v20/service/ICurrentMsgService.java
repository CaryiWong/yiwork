package cn.yi.gather.v20.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.CurrentMsg;

public interface ICurrentMsgService {

	public CurrentMsg saveOrUpdate(CurrentMsg entity);
	
	public CurrentMsg findById(String id);
	
	public Page<CurrentMsg> findForPage(Integer page,Integer size);
	
	public List<CurrentMsg> findAll();
	
	public void clearNowMsg();
}
