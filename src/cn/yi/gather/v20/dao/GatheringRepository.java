package cn.yi.gather.v20.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Gathering;
@Component("gatheringRepositoryV20")
public interface GatheringRepository extends JpaRepository<Gathering,String>{

	/**
	 * 开始时间在给定的时间段内的小活动列表
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年11月28日 下午5:27:41
	 */
	public List<Gathering> findByOpendateBetween(Date beginDate,Date endDate);
}
