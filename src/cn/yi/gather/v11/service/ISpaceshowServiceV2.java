package cn.yi.gather.v11.service;

import java.util.List;

import cn.yi.gather.v11.entity.Spaceshow;

/**
 * 空间展示
 * @author Lee.J.Eric
 *
 */
public interface ISpaceshowServiceV2 {
	
	/**
	 * 新增/更新一个空间展示
	 * @param spaceshow
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 上午11:41:06
	 */
	public Spaceshow spaceshowSaveOrUpdate(Spaceshow spaceshow);
	
	/**
	 * * 更新区域信息
	 * @param id
	 * @param img
	 * @param flag 更新标记，0添加图片，1删除图片
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 上午11:41:00
	 */
	public Spaceshow updateSpaceshow(String id,String img,Integer flag);
	
	/**
	 * 获取所有的区域展示
	 * @return
	 * Lee.J.Eric
	 * 2014年7月8日 下午3:36:31
	 */
	public List<Spaceshow> getAllSpaceshowList();
	
	/**
	 * 根据区域id获取
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014年7月8日 下午3:47:45
	 */
	public Spaceshow findById(String id);

}
