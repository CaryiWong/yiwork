package cn.yi.gather.v11.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v11.entity.Appversion;

public interface IAppversionServiceV2 {

	public Appversion appversionSaveOrUpdate(Appversion appversion);
	
	public Appversion findById(String id);
	
	/**
	 * 根据平台类型获取应用的最新版本
	 * @param platform
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 下午4:31:48
	 */
	public Appversion getLastedAppversionByPlatform(String platform);
	
	public Page<Appversion> getPage(Integer page,Integer size);
	
	public List<Appversion> getList(Integer page,Integer size);
	
	public com.common.Page<Appversion> versionList(com.common.Page<Appversion> page);
}
