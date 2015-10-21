package cn.yi.gather.v20.service;

import java.util.List;

import cn.yi.gather.v20.entity.YigatherItemInventoryLog;

public interface IYigatherItemInventoryLogService {
	
	public YigatherItemInventoryLog saveOrUpdate(YigatherItemInventoryLog entity);
	
	public void saveOrUpdate(List<YigatherItemInventoryLog> entities);

}
