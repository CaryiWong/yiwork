package cn.yi.gather.v11.service;

import cn.yi.gather.v11.entity.Applyvip;

public interface IApplyvipServiceV2 {

	public Applyvip applyvipSaveOrUpdate(Applyvip applyvip);
	
	public Applyvip findById(String id);
}
