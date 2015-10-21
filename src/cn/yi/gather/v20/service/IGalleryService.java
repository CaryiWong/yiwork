package cn.yi.gather.v20.service;

import java.util.List;

import cn.yi.gather.v20.entity.Gallery;

public interface IGalleryService {

	/**
	 * 图集保存/更新
	 * @param gallery
	 * @return
	 * Lee.J.Eric
	 * 2014年9月11日 上午10:55:29
	 */
	public Gallery gallerySaveOrUpdate(Gallery gallery);
	
	/**
	 * 查询图集
	 * @param id 图集所属主体
	 * @param flag 主体类型 ，0 小活动，1 活动，2 课程
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年10月13日 下午3:20:15
	 */
	public List<Gallery> findByIdAndFlag(String id,Integer flag);
}
