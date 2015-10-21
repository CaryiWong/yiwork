package cn.yi.gather.v20.service;

import java.util.Date;


import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.ItemInstance;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.entity.Tribe_partner;

public interface ITribe_partnerService {

	public Tribe_partner SaveOrUpdate(Tribe_partner obj);
	
	public Tribe_partner findById(String id);
	
	public com.common.Page<Tribe_partner> tribe_partnerlist(final Integer page,final Integer size);
}
