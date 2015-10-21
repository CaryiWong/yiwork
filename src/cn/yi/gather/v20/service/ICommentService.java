package cn.yi.gather.v20.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Comment;

public interface ICommentService {

	public Comment commentSaveOrUpdate(Comment comment);
	
	public List<Comment> getCommentListByPid(String pid);
	
	public Comment getCommentByID(String id);
	
	public void commentDel(String id);
	
	/**
	 * 
	 * @param page 页码
	 * @param size 每页大小
	 * @param orderby -1 时间倒序 1 时间正序
	 * @param listtype 0 根据活动拿评论  1 根据用户拿评论
	 * @param id   
	 * @return
	 */
	public Page<Comment> commentFind(Integer page,Integer size,String orderby,String listtype,String id);
}
