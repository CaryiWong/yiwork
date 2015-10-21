package cn.yi.gather.v20.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v20.entity.YiQuestion;

public interface IYiQeustionService {

	
	public YiQuestion findById(String id);
	
	/**
	 * 新增&更新
	 * @param entity
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月18日 下午2:55:25
	 */
	public YiQuestion saveOrUpdate(YiQuestion entity);
	
	/**
	 * 删除
	 * @param entity
	 * @author Lee.J.Eric
	 * @time 2014年12月18日 下午2:55:56
	 */
	public void delete(YiQuestion entity);
	
	/**
	 * 查询问小Yi问题列表
	 * @param userid
	 * @param page
	 * @param size
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月18日 下午3:29:38
	 */
	public Page<YiQuestion> findPageByUser(String userid,Integer page,Integer size);
	
	/**
	 * 根据父级id查询所有的回复
	 * @param parentid
	 * @return
	 * @author Lee.J.Eric
	 * @time 2014年12月18日 下午4:49:05
	 */
	public List<YiQuestion> findByParentId(String parentid);
}
