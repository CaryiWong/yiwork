package cn.yi.gather.v20.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.User;

public interface IGatheringService {
	
	/**
	 * 小活动保存/更新
	 * @param gathering
	 * @return
	 * Lee.J.Eric
	 * 2014年9月9日 上午10:47:56
	 */
	public Gathering gatheringSaveOrUpdate(Gathering gathering);

	/**
	 * 根据id查询小活动
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014年9月9日 下午2:33:45
	 */
	public Gathering findById(String id);
	
	public Page<User> findSignList(String id,Integer page,Integer size);
	
	/**
	 * 删除小活动
	 * @param gathering
	 * Lee.J.Eric
	 * 2014年9月22日 下午4:10:29
	 */
	public void deleteGathering(Gathering gathering);
	
	/**
	 * 根据开始时间查询
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年11月28日 下午5:23:24
	 */
	public List<Gathering> findListByOpendate(Date beginDate,Date endDate);
	
	/**
	 * 检测小活动创建时间是否被占用
	 * @param openDate
	 * @param endDate
	 * @return
	 */
	public boolean checkTime(Date openDate, Date endDate,String id);
}
