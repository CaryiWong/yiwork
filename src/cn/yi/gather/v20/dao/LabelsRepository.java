package cn.yi.gather.v20.dao;

import cn.yi.gather.v20.entity.Labels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 标签
 * @author Lee.J.Eric
 * @time 2014年5月29日上午11:31:47
 */
@Component("labelsRepositoryV20")
public interface LabelsRepository extends JpaRepository<Labels,Long>,JpaSpecificationExecutor<Labels>{
	
	/**
	 * 标签类别查询
	 * @param labeltype
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月29日 上午10:39:23
	 */
	public List<Labels> findByLabeltype(Integer labeltype);
	
	/**
	 * 根据id集合查询
	 * @param idList
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月29日 上午10:54:50
	 */
	public List<Labels> findByIdIn(List<Long> idList);

	public Set<Labels> findByIdIn(Set<Long> idSet);
	
	
	/**
	 * 根据标签分类与标签中文名查询
	 * @param labeltype
	 * @param zname
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月9日 下午12:18:08
	 */
	public Labels findByLabeltypeAndZnameAndPid(String labeltype,String zname,Long pid);
	
	@Modifying
	@Query("delete from Labels l where l.zname=:zname")
	public void deleteByzname(@Param("zname")String zname);
	
}
