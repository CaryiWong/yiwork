package cn.yi.gather.v11.service;

import cn.yi.gather.v11.entity.Indexorderbyinfo;

/**
 * 个人/团队排序
 * @author Lee.J.Eric
 *
 */
public interface IIndexorderbyinfoServiceV2 {
	
	/**
	 * 新增/更新排序
	 * @param indexorderbyinfo
	 * @return
	 * Lee.J.Eric
	 * 2014年6月18日 下午4:59:25
	 */
	public Indexorderbyinfo indexorderbyinfoSaveOrUpdate(Indexorderbyinfo indexorderbyinfo);
	
	/**
	 * 根据id获取
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014年6月18日 下午4:59:13
	 */
	public Indexorderbyinfo findById(String id);
	
	/**
	 * 根据排序类型获取排序列表
	 * @param type 0:hugo,1:team
	 * @return
	 * Lee.J.Eric
	 * 2014年6月18日 下午4:58:23
	 */
	public Indexorderbyinfo findByType(Integer type);
}
