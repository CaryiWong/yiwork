package cn.yi.gather.v20.service;

import java.util.Date;
import java.util.List;

import com.common.Jr;
import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.User;


public interface IActivityService {

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
	 * @param listtype 列表类型，-1全部，0 未来的，1过去的
	 * @return
	 * 2014 2014-09-16
	 */
	public Page<Activity> getActivityList(Integer page,Integer size,Integer listtype);
	
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
	
	/**
	 * 根据开始时间查询活动列表
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月1日 下午3:40:22
	 */
	public List<Activity> findListOpendateBetween(Date beginDate,Date endDate);
	
	/**
	 * 根据结束时间查询活动列表
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月4日 下午2:46:09
	 */
	public List<Activity> findListEnddateBetween(Date beginDate,Date endDate);
	
	/**
	 * 
	 * @param page
	 * @param size
	 * @param listtype   needform 报名表绑定/问卷绑定           needreview    绑定回顾
	 * @return
	 */
	public Page<Activity> findActivityList(Integer page, Integer size,String listtype);
	
	/**
	 * 报名活动后续处理
	 * @param userid
	 * @param questions   //订单里面的jsontext
	 * @param activityid
	 */
	public void signActivity(String userid, String questions, String activityid) throws Exception;
	
	/**
	 * 根据skuid查询
	 * @param skuid
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月7日 下午4:46:48
	 */
	public Activity findBySkuId(String skuid);

	/**
	 * 根据活动id查询活动集(用,分隔)
	 * @param ids
	 * @author Lee.J.Eric
	 * @time 2015年3月31日 下午2:34:48
	 * @return
	 */
	public List<Activity> findByIds(String ids);

	public Jr signActivity(String type,User user,Jr jr,String ids,String idtype,String questions)throws Exception;

	public void signActivity(User user, String questions, Activity activity) throws Exception;

	public List<Activity> findByPid(String pid);
}
