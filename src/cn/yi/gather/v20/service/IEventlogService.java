package cn.yi.gather.v20.service;

import java.sql.Connection;
import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Course;
import cn.yi.gather.v20.entity.Eventlog;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.User;

/**
 * 事件动态记录
 * @author Lee.J.Eric
 *
 */
public interface IEventlogService {

	/**
	 * 事件动态记录新增或更新
	 * @param eventlog
	 * @return
	 * Lee.J.Eric
	 * 2014年9月12日 下午2:56:48
	 */
	public Eventlog eventlogSaveOrUpdate(Eventlog eventlog);
	
	/**
	 * 根据id获取事件
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014年9月12日 下午3:02:05
	 */
	public Eventlog findById(String id);
	
	/**
	 * 根据主键删除
	 * @param id
	 * Lee.J.Eric
	 * 2014年9月15日 下午4:15:26
	 */
	public void deleteById(String id);
	
	/**
	 * 社区动态
	 * @param page
	 * @param size
	 * @param eventtype 动态类型，future:即将，activity:活动,course:课程，review：回顾
	 * @return
	 * Lee.J.Eric
	 * 2014年9月12日 下午5:31:57
	 */
	public Page<Eventlog> findTrendsPage(Integer page,Integer size,String eventtype);
	
	
	/**
	 * 获取动态列表
	 * @param page
	 * @param size
	 * @param eventtype
	 * @param userid
	 * @param cityid  地点切换 过滤数据
	 * @return
	 */
	public Page<Eventlog> findTrendsPage(Integer page,Integer size,String eventtype,String userid,String cityid);
	
	/**
	 * 根据用户与小活动查询
	 * @param user
	 * @param gathering
	 * @param eventtype
	 * @return
	 * Lee.J.Eric
	 * 2014年9月15日 下午4:32:04
	 */
	@Deprecated
	public Eventlog findByUserAndGatheringAndEventtype(User user,Gathering gathering,String eventtype);
	
	/**
	 * 根据用户与小活动删除
	 * @param user
	 * @param gathering
	 * @param eventtype
	 * Lee.J.Eric
	 * 2014年9月15日 下午4:34:33
	 */
	@Deprecated
	public void deleteByUserAndGatheringAndEventtype(User user,Gathering gathering,String eventtype);
	
	/**
	 * 根据小活动查询报名列表
	 * @param gathering
	 * @return
	 * Lee.J.Eric
	 * 2014年9月15日 下午3:37:42
	 */
	public List<User> getSignListByGathering(String gatheringid);
	
	/**
	 * 查询小活动报名列表
	 * @param gatheringid
	 * @param page
	 * @param size
	 * @return
	 * Lee.J.Eric
	 * 2014年9月15日 下午5:24:12
	 */
	public Page<User> getUserListByGatheringAndEventtype(String gatheringid,String eventtype,Integer page,Integer size);
	
	
	/**
	 * 查询我的社区动态
	 * @param page
	 * @param size
	 * @param userid 用户id
	 * @param actiontype join:参加，focus:关注,create:发起
	 * @return
	 * Lee.J.Eric
	 * 2014年9月16日 上午11:45:08
	 */
	public Page<Eventlog> findMyTrendsPage(Integer page,Integer size,String userid,String actiontype);
	
	/**
	 * 根据公告删除动态
	 * @param noticeid
	 * Lee.J.Eric
	 * 2014年9月17日 下午5:27:35
	 */
	public void deleteByNotice(String noticeid);
	
	/**
	 * 根据小活动删除动态
	 * @param gatheringid
	 * Lee.J.Eric
	 * 2014年9月22日 下午4:14:57
	 */
	public void deleteByGathering(String gatheringid);
	
	/**
	 * 根据用户与课程查询
	 * @param user
	 * @param gathering
	 * @param eventtype
	 * @return
	 * Lee.J.Eric
	 * 2014年9月15日 下午4:32:04
	 */
	@Deprecated
	public Eventlog findByUserAndCourseAndEventtype(User user,Course course,String eventtype);
	
	/**
	 * 根据用户与课程删除
	 * @param user
	 * @param gathering
	 * @param eventtype
	 * Lee.J.Eric
	 * 2014年9月15日 下午4:34:33
	 */
	@Deprecated
	public void deleteByUserAndCourseAndEventtype(User user,Course course,String eventtype);
	
	/**
	 * 课程学员列表
	 * @param courseid
	 * @param page
	 * @param size
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年10月20日 上午11:11:30
	 */
	public Page<User> findCourseStudentList(String courseid,Integer page,Integer size);
	
	/**
	 * 根据用户与活动查询
	 * @param user
	 * @param activity
	 * @param eventtype
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月2日 下午5:21:32
	 */
	@Deprecated
	public Eventlog findByUserAndActivityAndEventtype(User user,Activity activity,String eventtype);
	
	/**
	 * 根据用户与活动删除
	 * @param user
	 * @param activity
	 * @param eventtype
	 * @author Lee.J.Eric
	 * @time 2014年12月2日 下午5:27:21
	 */
	@Deprecated
	public void deleteByUserAndActivityAndEventtype(User user,Activity activity,String eventtype);
	
	/**
	 * 查询用户列表
	 * @param id 主体id
	 * @param eventtype  类型 create:创建  join:参加  sign:报名  focus:关注 review:回顾||invite邀请
	 * @param acttype 事件类型  activity  活动  || gathering  小活动 || course 课程 ||notice 公告 ||invite邀请
	 * @param page
	 * @param size
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月30日 下午3:36:27
	 */
	public Page<User> findUserListByIdAndEventtypeAndActtype(String id,String eventtype,String acttype,Integer page,Integer size);
	
	/**
	 * 查询活动报名列表
	 * @param activityid
	 * @param page
	 * @param size
	 * @return
	 * Lee.J.Eric
	 * 2014年9月15日 下午5:24:12
	 */
	@Deprecated
	public Page<User> getSignListByActivity(String activityid,Integer page,Integer size);
	
	/**
	 * 查询活动出席列表
	 * @param activityid
	 * @param page
	 * @param size
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月4日 下午2:51:52
	 */
	@Deprecated
	public Page<User> getJoinListByActivity(String activityid,Integer page,Integer size);
	
	/**
	 * 根据用户，主体，事件类型，事件行为标识查询
	 * @param userid
	 * @param entityid
	 * @param focustype
	 * @param eventtype
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月29日 上午10:52:22
	 */
	public Eventlog findByUserAndEntityAndActtypeAndEventtype(String userid,String entityid,String acttype,String eventtype);
	
	/**
	 * 根据用户，主体，事件类型，事件行为标识查询
	 * @param userid
	 * @param entityid
	 * @param focustype
	 * @param eventtype
	 * @author Lee.J.Eric
	 * @time 2014年12月29日 上午11:03:50
	 */
	public void deleteByUserAndEntityAndFocustypeAndEventtype(String userid,String entityid,String acttype,String eventtype);

	/**
	 * 根据主体，事件类型，事件行为统计
	 * @param entityid
	 * @param acttype
	 * @param eventtype
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月31日 下午3:38:49
	 */
	public Long countEntityAndActtypeAndEventType(String entityid,String acttype,String eventtype);

	/**
	 * 根据活动id,会员号，性别，关键字获取活动的报名列表
	 * @param conn 
	 * @param actid
	 * @param keyword
	 * @param sex
	 * @param userno
	 * @param page
	 * @param page_size
	 * @return
	 * @throws Exception
	 */
	public com.common.Page<User> getSignListByIdForPage(Connection conn,String actid,Integer page, Integer page_size) throws Exception ;


	public Page<Eventlog> searchTrends(Integer page,Integer size,String user_id,String keyword,String btn_name);

	/**
	 * 活动报名列表
	 * @param actid
	 * @return
	 */
	public List<Eventlog> findByActAndSign(String actid);
	
}
