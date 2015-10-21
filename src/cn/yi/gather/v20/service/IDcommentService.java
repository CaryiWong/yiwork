package cn.yi.gather.v20.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Dcomment;

public interface IDcommentService {

	public Dcomment dcommentSaveOrUpdate(Dcomment dcomment);
	
	public List<Dcomment> getDcommentListByPid(String pid);
	
	public Dcomment getDcommentByID(String id);
	
	public void dcommentDel(String id);
	
	/**
	 * 拿自己发表的评论/ 拿当前需求的评论/拿自己与当前需求的交集评论
	 * @param page 页码
	 * @param size 每页大小
	 * @param orderby -1 时间倒序 1 时间正序
	 * @param uid  用户ID
	 * @param id   需求ID
	 * @return
	 */
	public Page<Dcomment> dcommentFind(Integer page,Integer size,String orderby,String uid,String id);
	
	/**
	 * 拿用户参与的需求  去重复
	 * @param page
	 * @param size
	 * @param orderby
	 * @param uid
	 * @return
	 */
	public Page<Dcomment> dcommentFindByUid(Integer page,Integer size,Integer orderby,String uid);
}
