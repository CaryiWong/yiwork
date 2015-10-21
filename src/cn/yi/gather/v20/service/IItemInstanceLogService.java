package cn.yi.gather.v20.service;

import java.util.List;

import cn.yi.gather.v20.entity.ItemInstanceLog;


public interface IItemInstanceLogService {

	public ItemInstanceLog saveOrUpdate(ItemInstanceLog entity);
	
	public void saveOrUpdate(List<ItemInstanceLog> logs);
}
