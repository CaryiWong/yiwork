package cn.yi.gather.v11.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Labels;

/**
 * 标签
 * @author Lee.J.Eric
 * @time 2014年5月29日上午11:31:47
 */
@Component(value ="labelsRepositoryV2")
public interface LabelsRepository extends JpaRepository<Labels,Long>{
	
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
	
	/**
	 * 根据标签分类与标签中文名查询
	 * @param labeltype
	 * @param zname
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月9日 下午12:18:08
	 */
	public Labels findByLabeltypeAndZname(Integer labeltype,String zname);

}
