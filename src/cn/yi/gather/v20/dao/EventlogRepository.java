package cn.yi.gather.v20.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Course;
import cn.yi.gather.v20.entity.Eventlog;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.User;
@Component("eventlogRepositoryV20")
public interface EventlogRepository extends JpaRepository<Eventlog, String>,JpaSpecificationExecutor<Eventlog>{

	/**
	 * 根据用户与小活动查询
	 * @param user
	 * @param gathering
	 * @return
	 * Lee.J.Eric
	 * 2014年9月18日 上午10:20:19
	 */
	public Eventlog findByUserAndGathering(User user,Gathering gathering);
	
	/**
	 * 根据用户、小活动与动态类型查询动态
	 * @param user
	 * @param gathering
	 * @param eventtype
	 * @return
	 * Lee.J.Eric
	 * 2014年9月18日 上午10:19:49
	 */
	public Eventlog findByUserAndGatheringAndEventtype(User user,Gathering gathering,String eventtype);
	
	/**
	 * 根据用户id与小活动id删除动态
	 * @param userid
	 * @param gatheringid
	 * @param eventtype
	 * Lee.J.Eric
	 * 2014年9月18日 上午10:19:26
	 */
	@Modifying
	@Query(value = "delete from Eventlog e where e.user.id =:userid and e.gathering.id =:gatheringid and e.eventtype =:eventtype")
	public void delByUserGatheringEventtype(@Param("userid")String userid,@Param("gatheringid")String gatheringid,@Param("eventtype")String eventtype);

	/**
	 * 根据公告id删除动态
	 * @param noticeid
	 * Lee.J.Eric
	 * 2014年9月18日 上午10:19:04
	 */
	@Modifying
	@Query(value = "delete from Eventlog e where e.notice.id =:noticeid")
	public void delByNotice(@Param("noticeid")String noticeid);
	
	/**
	 * 根据小活动id删除动态
	 * @param gatheringid
	 * Lee.J.Eric
	 * 2014年9月22日 下午4:13:48
	 */
	@Modifying
	@Query(value = "delete from Eventlog e where e.gathering.id =:gatheringid")
	public void delByGathering(@Param("gatheringid")String gatheringid);
	
	/**
	 * 根据用户、课程与动态类型查询动态
	 * @param user
	 * @param course
	 * @param eventtype
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年10月16日 下午2:59:45
	 */
	public Eventlog findByUserAndCourseAndEventtype(User user,Course course,String eventtype);
	
	/**
	 * 根据用户id与课程id删除动态
	 * @param userid
	 * @param courseid
	 * @param eventtype
	 * @author Lee.J.Eric
	 * @time 2014年10月16日 下午3:01:16
	 */
	@Modifying
	@Query(value = "delete from Eventlog e where e.user.id =:userid and e.course.id =:courseid and e.eventtype =:eventtype")
	public void delByUserCourseEventtype(@Param("userid")String userid,@Param("courseid")String courseid,@Param("eventtype")String eventtype);

	/**
	 * 根据用户、活动与动态类型查询动态
	 * @param user
	 * @param activity
	 * @param eventtype
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月2日 下午5:23:02
	 */
	public Eventlog findByUserAndActivityAndEventtype(User user,Activity activity,String eventtype);
	
	/**
	 * 根据用户id与活动id删除动态
	 * @param userid
	 * @param courseid
	 * @param eventtype
	 * @author Lee.J.Eric
	 * @time 2014年10月16日 下午3:01:16
	 */
	@Modifying
	@Query(value = "delete from Eventlog e where e.user.id =:userid and e.activity.id =:activityid and e.eventtype =:eventtype")
	public void delByUserActivityEventtype(@Param("userid")String userid,@Param("activityid")String activityid,@Param("eventtype")String eventtype);

	public List<Eventlog> findByActivityAndEventtype(Activity activity,String eventtype);
}
