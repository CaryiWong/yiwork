package cn.yi.gather.v11.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v11.entity.User;

/**
 * 用户业务接口
 * @author Lee.J.Eric
 * @time 2014年5月28日下午7:03:58
 */
public interface IUserServiceV2 {
	
	/**
	 * 用户帐号与密码查询
	 * @param password
	 * @param username
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月28日 下午6:13:29
	 */
	public User findByusernameAndPWD(String password,String username);
	
	/**
	 * 用户保存或更新
	 * @param user
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月29日 下午12:44:59
	 */
	public User userSaveOrUpdate(User user);
	
	/**
	 *  检查邮箱唯一性
	 * @param email
	 * @return 存在true,不存在false
	 * Lee.J.Eric
	 * 2014 2014年5月29日 下午12:06:27
	 */
	public Boolean checkForEmail(String email);
	
	/**
	 * 检查昵称唯一性
	 * @param nickname
	 * @return 存在true,不存在false
	 * Lee.J.Eric
	 * 2014 2014年5月29日 下午12:06:43
	 */
	public Boolean checkForNickname(String nickname);
	
	/**
	 * 检查手机唯一性
	 * @param tel
	 * @return 存在true,不存在false
	 * Lee.J.Eric
	 * 2014 2014年5月29日 下午12:06:55
	 */
	public Boolean checkForTelephone(String tel);
	
	/**
	 *  检查会员号唯一性
	 * @param unum
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午2:52:34
	 */
	public Boolean checkForUnum(String unum);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月29日 下午5:43:04
	 */
	public User findById(String id);
	
	/**
	 * 分页获取首页用户
	 * @param page
	 * @param size
	 * @param isindex 0不显示，1显示
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午3:23:06
	 */
	public Page<User> getIndexUser(Integer page,Integer size,Integer isindex);
	
	/**
	 * 根据邮箱查询
	 * @param email
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午5:06:11
	 */
	public User getByEmail(String email);
	
	
	/**
	 * 分页 根据会员等级不一样查询的字段不一样
	 * @param page  当前页
	 * @param size 每页条数
	 * @param orderby  时间排序,1正序，-1倒序
	 * @param listtype 列表类型 -1全部，0会员展示，1空间会员
	 * @param keyword 搜索关键字
	 * @return
	 * 2014 2014年5月31日 下午12:19:10
	 */
	public Page<User> getVipList(Integer page,Integer size,Integer orderby,Integer listtype,String keyword);
	
	/**
	 * 根据id和密码查询-密码修改
	 * @param id
	 * @param password
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月4日 下午2:20:18
	 */
	public User findByIdAndPassword(String id,String password);
	
	/**
	 * 分页查询用户列表
	 * @param page
	 * @param size
	 * @param charge -1全部，0管理员，1收费，2未收费
	 * @param sex 0男，1女
	 * @param telmail
	 * @param labelList 标签
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月10日 下午3:55:01
	 */
	public com.common.Page<User> getUserListForPage(Integer page,Integer size,final Integer charge,final Integer sex,final String userNO,final String keyword,final List<Long> labelList,final Integer spaceshow,final Integer vipshow);	

	/**
	 * 获取的用户list
	 * @param root 用户权限，-1全部
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月11日 下午3:24:11
	 */
	public List<User> getUserListByRoot(final Integer root);
	
	/**
	 * 分页查询用户
	 * @param page
	 * @param size
	 * @return
	 * Lee.J.Eric
	 * 2014年6月27日 上午10:57:01
	 */
	public Page<User> findUserList(Integer page,Integer size);
	
	/**
	 * 根据帐号(邮箱｜电话｜昵称)查询用户
	 * @param keyword
	 * @return
	 * Lee.J.Eric
	 * 2014年8月5日 下午2:39:36
	 */
	public User findByAccount(String keyword);
	
	/**
	 * 用户全部签出
	 * 
	 * Lee.J.Eric
	 * 2014年9月17日 下午6:03:18
	 */
	public void checkoutalluser();
}
