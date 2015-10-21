package cn.yi.gather.v11.service;


import cn.yi.gather.v11.entity.Sysnetwork;

public interface ISysnetworkServiceV2 {
	
	public com.common.Page<Sysnetwork> getSysnetworkForPage(Integer page,Integer size,final String keyword);
	
	
	public Sysnetwork sysnetworkSaveOrUpdate(Sysnetwork sysnetwork);
	
	public Sysnetwork findById(String id);

}
