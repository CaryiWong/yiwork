package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.entity.Visit;

public interface IVisitService {

	public Visit SaveOrUpdate(Visit tribe);
	
	public Visit findById(String id);
	
	public com.common.Page<Visit> visitlist(final Integer page,final Integer size);
}
