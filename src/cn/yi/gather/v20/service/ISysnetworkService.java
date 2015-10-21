package cn.yi.gather.v20.service;


import cn.yi.gather.v20.entity.Sysnetwork;

public interface ISysnetworkService {
	
	public com.common.Page<Sysnetwork> getSysnetworkForPage(Integer page,Integer size,final String keyword);
	
	
	public Sysnetwork sysnetworkSaveOrUpdate(Sysnetwork sysnetwork);
	
	public Sysnetwork findById(String id);

}
