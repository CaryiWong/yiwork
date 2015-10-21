package cn.yi.gather.v20.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.ActivityJoin;
import cn.yi.gather.v20.entity.User;


public interface IActivityJoinService {

	/**
	 * 根据用户id与活动发起人标记查询活动报名
	 * @param user
	 * @param owner 0:报名，1:发起，null:发起或是报名的
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午3:13:30
	 */
	public List<ActivityJoin> getByUserAndOwner(final User user,final Integer owner);
	
	/**
	 * 活动报名新增或保存
	 * @param activityJoin
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午5:32:56
	 */
	public ActivityJoin activityjoinSaveOrUpdate(ActivityJoin activityJoin);
	
	/**
	 * 根据id删除报名记录
	 * @param id
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午5:34:01
	 */
	public void deleteByID(String id);
	
	/**
	 * 根据用户和活动检验是否已报过名
	 * @param user
	 * @param activity
	 * @return false:未报名，true:已报名
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午5:37:54
	 */
	public Boolean checkByUserAndActivity(User user,Activity activity);
	
	/**
	 * 根据用户和活动获取活动报名
	 * @param user
	 * @param activity
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午5:38:56
	 */
	public ActivityJoin getByUserAndActivity(User user,Activity activity);
	
	/**
	 * 根据活动获取报名列表
	 * @param activityid
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年4月14日 下午3:24:35
	 */
	public List<ActivityJoin> getByActivity(Activity activity);
	
	/**
	 * 根据活动id统计报名列表
	 * @param activity
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午5:45:11
	 */
	public Long countByActivity(Activity activity);
	
	/**
	 * 根据活动获取报名列表
	 * @param page
	 * @param size
	 * @param activity
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午7:01:41
	 */
	public Page<ActivityJoin> findPageByActivity(Integer page,Integer size,Activity activity);
	
	/**
	 * 分页查询活动下的报名用户
	 * @param page
	 * @param size
	 * @param activity
	 * @param keyword
	 * @return
	 * Lee.J.Eric
	 * 2014年6月13日 下午4:33:42
	 */
	public com.common.Page<ActivityJoin> signList(Integer page,Integer size,final Activity activity,final String keyword);
}
