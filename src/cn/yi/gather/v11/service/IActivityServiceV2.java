package cn.yi.gather.v11.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v11.entity.Activity;
import cn.yi.gather.v11.entity.Labels;
import cn.yi.gather.v11.entity.User;


public interface IActivityServiceV2 {

	/**
	 * 活动创建或更新
	 * @param activity
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月4日 上午10:19:31
	 */
	public Activity activitySaveOrUpdate(Activity activity);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月4日 上午11:17:19
	 */
	public Activity findById(String id);
	
	/**
	 * 获取我的活动列表
	 * @param page
	 * @param size
	 * @param user
	 * @param flag 0：我参加的或是我发起的，1：我发起的活动，2我参加的活动
	 * Lee.J.Eric
	 * 2014-3-10 下午2:35:19
	 */
	public Page<Activity> getMyActivity(Integer page,Integer size,final User user,final Integer flag,final String keyword);
	
	/**
	 * 根据关键字搜索活动
	 * @param keyword
	 * @param page
	 * @param size
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月9日 上午11:36:05
	 */
	public Page<Activity> searchActByKeyword(final String keyword, Integer page,Integer size);
	
	/**
	 * 根据banner标记活动列表
	 * @param banner
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月9日 下午12:54:41
	 */
	public List<Activity> getBannerActList(Integer banner);
	
	/**
	 * 根据标签分页查询
	 * @param label
	 * @param page
	 * @param size
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月9日 下午2:02:35
	 */
	public Page<Activity> getByLabel(Labels label,Integer page,Integer size);
	
	/**
	 * 获取活动列表
	 * @param page
	 * @param size
	 * @param listtype 列表类型，-1全部，0预告，1回顾
	 * @param keyword 搜索关键字
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月9日 下午2:36:33
	 */
	public Page<Activity> getActivityList(Integer page,Integer size,final Integer listtype,final String keyword);
	
	/**
	 * 后台查询活动列表
	 * @param page
	 * @param size
	 * @param cost
	 * @param checktype
	 * @param status
	 * @param keyword
	 * @param labelList
	 * @return
	 * Lee.J.Eric
	 * 2014年6月13日 下午3:07:48
	 */
	public com.common.Page<Activity> getActivityForPage(Integer page,Integer size,final Integer cost,final Integer checktype,final Integer status,final String keyword,final List<Long> labelList);

	/**
	 * 统计banner活动总数
	 * @return
	 * Lee.J.Eric
	 * 2014年6月13日 下午3:07:34
	 */
	public Long countBannerAct();
}
