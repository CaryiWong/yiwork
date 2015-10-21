package cn.yi.gather.v20.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.Labels;

public interface ILabelsService {

	/**
	 * 根据id获取单个label
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年4月22日 下午5:07:02
	 */
	public Labels findByID(Long id);
	
	/**
	 * 获取所有标签
	 * @return
	 * Lee.J.Eric
	 * 2014-2-26 上午10:47:47
	 */
	public List<Labels> getAllLabels();
	/**
	 * 通过 id,id,id 字符串获取对应的标签集合
	 * @param ids  格式 id,id,id   最后不需要带,
	 * @return List
	 */
	public List<Labels> getLabelsByids(String ids);

	public Set<Labels> getLabelsByIds(String ids);
	
	/**
	 * 通过List<Long>获取对应的标签集合
	 * @param ids
	 * @return
	 * Lee.J.Eric
	 * 2014-3-14 下午4:42:45
	 */
	public List<Labels> getLabelsByList(List<Long> ids);
	
	/**
	 * 分类获取标签
	 * @param type 0领域，1形式，2面向人群,3视频制作 4寻人
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年4月22日 下午6:32:38
	 */
	public List<Labels> getLabelsByType(Integer type);
	
	/**
	 * 根据标签分类与标签中文名查询
	 * @param labeltype
	 * @param zname
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月9日 下午12:16:04
	 */
	public Labels findByLabeltypeAndZnameAndPid(String labeltype, String zname,Long pid);
	
	/**
	 * 获取所有标签
	 * @param page 预留分页 当前页
	 * @param size 每页大小
	 * @param labeltype
	 * @return
	 */
	public Page<Labels> getLabels(Integer page, Integer size,Integer labeltype);
	
	public void deleteLabels(String zname);
	
	public Labels saveOrUpdate(Labels entity);
	
	
	public Page<Labels> labelList(String labeltype,Long pid,Integer page,Integer size);
	
	public void updateHot();
	public void deleteLabel(Long id);
}
