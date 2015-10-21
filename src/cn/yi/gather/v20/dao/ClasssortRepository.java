package cn.yi.gather.v20.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Classsort;

/**
 * 行业
 * @author Lee.J.Eric
 * @time 2014年5月29日上午11:31:40
 */
@Component("classsortRepositoryV20")
public interface ClasssortRepository extends JpaRepository<Classsort,Long>{
	
	/**
	 * 根据id集合查询
	 * @param idList
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月29日 上午11:32:52
	 */
	public List<Classsort> findByIdIn(List<Long> idList);
	
	/**
	 * 根据父id查询
	 * @param pid
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月29日 上午11:39:40
	 */
	public List<Classsort> findByPidOrderByIdAsc(Long pid);
	
	/**
	 * 获取所有的职业
	 * @param pid
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月29日 上午11:49:25
	 */
	public List<Classsort> findByPidNotOrderByIdAsc(Long pid);

}
