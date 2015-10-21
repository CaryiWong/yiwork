package cn.yi.gather.v20.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.UserFocus;


public interface IUserFocusService {

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
	 * 根据自身id与会员id查询
	 * @param me
	 * @param who
	 * @param relation 0:收藏，1:交情
	 * @return
	 * Lee.J.Eric
	 * 2014年9月26日 下午5:46:19
	 */
	public UserFocus findByMeAndWhoAndRelation(User me, User who,Integer relation);
	
	/**
	 * 用户关注保存或更新
	 * @param userFocus
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月4日 下午4:04:30
	 */
	public UserFocus saveOrUpdate(UserFocus userFocus);
	
	/**
	 * 根据关注id删除关注
	 * @param id
	 * Lee.J.Eric
	 * 2014 2014年6月4日 下午4:09:22
	 */
	public void deleteById(String id);
	
	
	/**
	 * 根据会员id获取收藏，交情列表
	 * @param me
	 * @param order 1正序，-1倒序
	 * @param relation 0 收藏，1交情
	 * @param page
	 * @param size
	 * @return
	 * Lee.J.Eric
	 * 2014年9月26日 下午5:50:54
	 */
	public Page<User> getuserFocusList(String me,Integer order,Integer relation,Integer page,Integer size);
	
	/**
	 * 获取所有我关注的用户
	 * @param me
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月4日 下午5:41:30
	 */
	public List<UserFocus> findByMeAndRelation(User me,Integer relation);
	
}
