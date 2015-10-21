package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.UserotherInfo;

public interface IUserotherinfoService {

	public UserotherInfo saveOrUpdateUserother(UserotherInfo userotherInfo);
	
	public UserotherInfo findById(String id);
	
	public void deleteById(String id);
	
}
