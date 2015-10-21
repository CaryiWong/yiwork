package cn.yi.gather.v20.service;

import java.util.List;

import cn.yi.gather.v20.entity.Indexteaminfo;


/**
 * 团队展示
 * @author Lee.J.Eric
 *
 */
public interface IIndexteaminfoService {
	
	
	public Indexteaminfo indexteaminfoSaveOrUpdate(Indexteaminfo indexteaminfo);
	
	public Indexteaminfo findById(String id);
	
	public List<Indexteaminfo> getByIDList(List<String> ids);
	
	public void deleteById(String id);

	/**
	 * 根据id集合获取首页团队展示列表
	 * @param ids
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 下午2:14:06
	 */
	public List<Indexteaminfo> findByIdList(List<String> ids);
}
