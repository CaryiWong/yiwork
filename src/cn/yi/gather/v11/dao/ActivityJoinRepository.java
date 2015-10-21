package cn.yi.gather.v11.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Activity;
import cn.yi.gather.v11.entity.ActivityJoin;
import cn.yi.gather.v11.entity.User;

@Component("activityJoinRepositoryV2")
public interface ActivityJoinRepository extends JpaRepository<ActivityJoin, String>,JpaSpecificationExecutor<ActivityJoin> {

	/**
	 * 根据报名用户与活动查询
	 * @param user
	 * @param activity
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午6:04:54
	 */
	public ActivityJoin findByUserAndActivity(User user, Activity activity);
	
	/**
	 * 根据活动查询
	 * @param activity
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午6:05:53
	 */
	public List<ActivityJoin> findByActivity(Activity activity);
	
	/**
	 * 根据活动统计报名总数
	 * @param activity
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午6:34:46
	 */
	public Long countByActivity(Activity activity);
	
	/**
	 * 根据活动查询
	 * @param pageable
	 * @param activity
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午6:11:47
	 */
	public Page<ActivityJoin> findPageByActivity(Pageable pageable,Activity activity);
	
}
