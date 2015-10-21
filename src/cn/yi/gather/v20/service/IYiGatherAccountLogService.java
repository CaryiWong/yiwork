package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.YiGatherAccountLog;

public interface IYiGatherAccountLogService {

	public YiGatherAccountLog saveOrUpdate(YiGatherAccountLog entity);
	
	public YiGatherAccountLog findById(String id);
}
