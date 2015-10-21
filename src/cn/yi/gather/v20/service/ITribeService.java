package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Tribe;

public interface ITribeService {

	public Tribe tribeSaveOrUpdate(Tribe tribe);
	
	public Tribe findById(String id);
	
	public com.common.Page<Tribe> tribelist(final Integer page,final Integer size);
}
