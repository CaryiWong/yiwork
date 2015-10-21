package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Co_working_space;
import cn.yi.gather.v20.entity.Enterprise;
import cn.yi.gather.v20.entity.Tribe;

public interface ICo_working_spaceService {

	public Co_working_space SaveOrUpdate(Co_working_space obj);
	
	public Co_working_space findById(String id);
	
	public com.common.Page<Co_working_space> co_working_spacelist(final Integer page,final Integer size);
}
