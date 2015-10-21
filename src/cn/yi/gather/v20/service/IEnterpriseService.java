package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Enterprise;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.entity.Visit;

public interface IEnterpriseService {

	public Enterprise SaveOrUpdate(Enterprise obj);
	
	public Enterprise findById(String id);
	
	public com.common.Page<Enterprise> enterpriselist(final Integer page,final Integer size);
}
