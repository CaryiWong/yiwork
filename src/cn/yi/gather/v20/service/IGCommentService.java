package cn.yi.gather.v20.service;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.GComment;

public interface IGCommentService {

	/**
	 * 新增小活动评论
	 * @param gComment
	 * @return
	 * Lee.J.Eric
	 * 2014年9月9日 下午4:46:42
	 */
	public GComment gCommentSaveOrUpdate(GComment gComment);
	
	/**
	 * 根据小活动id查评论列表
	 * @param page
	 * @param size
	 * @param order 时间排序  1正序，-1倒序
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014年9月9日 下午5:26:45
	 */
	public Page<GComment> findGCommentForGahering(final Integer page,final Integer size,Integer order,final String id);
	
	
	public GComment findGComment(String id);
	
}
