package cn.yi.gather.v11.service;


import cn.yi.gather.v11.entity.Feedback;

public interface IFeedbackServiceV2 {

	/**
	 * 新增反馈
	 * @param feedback
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年4月17日 上午11:55:53
	 */
	public Feedback feedbackSaveOrUpdate(Feedback feedback);
	
	/**
	 * 分页查看用户反馈
	 * @param page
	 * @param keyword
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月3日 下午5:01:48
	 */
	public com.common.Page<Feedback> getFeedbackForPage(Integer page,Integer size,String keyword);
}
