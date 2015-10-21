package cn.yi.gather.v20.service;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.NoticeMsg;
import cn.yi.gather.v20.entity.User;

import java.util.Date;

public interface INoticeMsgService {
	
	public NoticeMsg saveOrUpdate(NoticeMsg entity);
	
	public NoticeMsg findById(String id);

	/**
	 * 根据用户查询消息记录
	 * @param user
	 * @param page
	 * @param size
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月19日 下午6:39:39
	 */
	public Page<NoticeMsg> findByUser(User user,Integer page,Integer size);

	/**
	 * 根据用户查询消息记录
	 * @param user
	 * @param page
	 * @param size
	 * @param date
	 * @param axis
	 * @return
	 */
	public Page<NoticeMsg> findByUser(User user,Integer page,Integer size,Date date,String axis);
	
	
	public Page<NoticeMsg> allNoticeMsg(User user,Integer page,Integer size);
	
	/**
	 * 把当前用户 在 这个邀请下面的 所有对话 除却当前not_id的消息置为 已读 方便归拢查询
	 * @param user
	 * @param invit_id
	 * @param not_id
	 */
	public void updateReadForInvit(User user,String invit_id,String not_id);
}
