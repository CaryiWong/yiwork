package cn.yi.gather.v20.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Broadcast;
import cn.yi.gather.v20.entity.Sysnetwork;
import cn.yi.gather.v20.entity.User;

public interface IBroadcastService {
	
	public Broadcast broadcastSaveOrUpdate(Broadcast broadcast);
	
	/**
	 * 根据用户与广播接收时间获取广播
	 * @param user
	 * @param page
	 * @param size
	 * @param time
	 * @param order
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月12日 下午12:13:21
	 */
	public Page<Broadcast> findByUserAndTime(final User user,Integer page,Integer size,final Date time,final String order);

	
	public void broadListBatchSave(List<Broadcast> list);
	
	/**
	 * 根据用户与广播本体获取
	 * @param user
	 * @param sysnetwork
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月12日 下午2:40:17
	 */
	public Broadcast findByUserAndSysnetwork(User user,Sysnetwork sysnetwork);
	
	/**
	 * 查询我的广播列表(web端)
	 * @param page
	 * @param size
	 * @param order
	 * @param user
	 * @param read
	 * @return
	 * Lee.J.Eric
	 * 2014年6月16日 下午5:49:35
	 */
	public List<Sysnetwork> getMyBroadcastList(final Integer page,final Integer size,final Integer order,final User user,final Integer read);
	
	/**
	 * 根据用户统计广播列表
	 * @param user
	 * @param read 阅读标记，0未读，1已读 -1全部
	 * @return
	 * Lee.J.Eric
	 * 2014年6月16日 下午6:11:23
	 */
	public Long countMyBroadcastList(final User user,final Integer read);
}
