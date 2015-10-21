package cn.yi.gather.v11.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.entity.UserFocus;


public interface IUserFocusServiceV2 {

	/**
	 * 根据自身id与会员id查询
	 * @param me
	 * @param who
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月3日 下午5:48:19
	 */
	public UserFocus findByMeAndWho(User me, User who);
	
	/**
	 * 用户关注保存或更新
	 * @param userFocus
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月4日 下午4:04:30
	 */
	public UserFocus userFocusSaveOrUpdate(UserFocus userFocus);
	
	/**
	 * 根据关注id删除关注
	 * @param id
	 * Lee.J.Eric
	 * 2014 2014年6月4日 下午4:09:22
	 */
	public void deleteById(String id);
	
	/**
	 * 根据会员id获取关注列表
	 * @param me 
	 * @param order 关注时间排序，1正序，-1倒序
	 * @param flag 0主动关注，1被关注
	 * @param page 
	 * @param size
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年4月23日 下午3:47:40
	 */
	public Page<UserFocus> getuserFocusList(User me,Integer order,Integer flag,Integer page,Integer size);
	
	/**
	 * 获取所有我关注的用户
	 * @param me
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月4日 下午5:41:30
	 */
	public List<UserFocus> findByMe(User me);
	
}
