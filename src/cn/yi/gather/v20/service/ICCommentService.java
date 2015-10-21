package cn.yi.gather.v20.service;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.CComment;

public interface ICCommentService {

	/**
	 * 新增課程評論
	 * @param cComment
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年10月14日 下午5:46:11
	 */
	public CComment cCommentSaveOrUpdate(CComment cComment);
	
	/**
	 * 根据课程获取课程评论列表
	 * @param page
	 * @param size
	 * @param order 时间排序，1正序，-1倒序
	 * @param id 课程id
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年10月20日 上午10:47:54
	 */
	public Page<CComment> findCCommentForCourse(Integer page,Integer size,Integer order,String id);
	
	public CComment findCComment(String id);
}
