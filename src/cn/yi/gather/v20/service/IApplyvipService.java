package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.Applyvip;

public interface IApplyvipService {

	public Applyvip applyvipSaveOrUpdate(Applyvip applyvip);
	
	public Applyvip findById(String id);
}
