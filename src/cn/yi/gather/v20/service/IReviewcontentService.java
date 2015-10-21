package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.Reviewcontent;

public interface IReviewcontentService {

	/**
	 * 回顾内容更新/保存
	 * @param reviewcontent
	 * @return
	 * Lee.J.Eric
	 * 2014年6月13日 下午3:23:53
	 */
	public Reviewcontent reviewcontentSaveOrUpdate(Reviewcontent reviewcontent);
	
	/**
	 * 根据活动id查询活动回顾的内容
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014年6月13日 下午3:25:18
	 */
	public Reviewcontent findById(String id);
}
