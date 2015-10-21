package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.Joinapplication;

/**
 * 入驻申请
 * @author Lee.J.Eric
 *
 */
public interface IJoinapplicationService {

	public Joinapplication joinapplicationSaveOrUpdate(Joinapplication joinapplication);
	
	public Joinapplication findById(String id);
	
	/**
	 * 入驻申请列表
	 * @param page
	 * @param size
	 * @param keyword
	 * @return
	 * Lee.J.Eric
	 * 2014年6月24日 下午2:22:33
	 */
	public com.common.Page<Joinapplication> findJoinapplicationList(Integer page,Integer size,String keyword);
}
