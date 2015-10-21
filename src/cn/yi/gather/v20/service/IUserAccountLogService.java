package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.UserAccountLog;

public interface IUserAccountLogService {

	public UserAccountLog findById(String id);
	
	public UserAccountLog saveOrUpdate(UserAccountLog entity);
}
