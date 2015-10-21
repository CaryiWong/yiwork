package cn.yi.gather.v11.service;

import java.util.List;

import cn.yi.gather.v11.entity.Classsort;

public interface IClasssortServiceV2 {

	/**
	 * 获取所有行业分类
	 * @return
	 */
	public List<Classsort> getAllClasssort();
	
	/**
	 * 通过 行业ID   格式 id,id,id   获取对应的 行业对象集合 最后不带 逗号
	 * @param ids
	 * @return
	 */
	public List<Classsort> getClasssortByids(String ids);
	
	/**
	 * 能过行业list获取对应的行业对象集合
	 * @param ids
	 * @return
	 * Lee.J.Eric
	 * 2014-3-15 下午2:30:07
	 */
	public List<Classsort> getClasssortsByList(List<Long> ids);
	
	/**
	 * 根据父级id查询子级列表
	 * @param pid 父级id,0为顶级
	 * @return
	 * Lee.J.Eric
	 * 2014-3-20 下午4:22:47
	 */
	public List<Classsort> getClasssortsByPid(Long pid);
	
	/**
	 * 根据id获取单个行业
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014-3-21 上午10:59:21
	 */
	public Classsort getClasssortsByID(Long id);
	
	/**
	 * 获取所有的职业
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年4月25日 下午4:08:02
	 */
	public List<Classsort> getAllSecClassSort();
}
