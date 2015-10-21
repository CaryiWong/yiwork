package cn.yi.gather.v11.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v11.entity.Indexuserinfo;

/**
 * 个人展示
 * @author Lee.J.Eric
 *
 */
public interface IIndexuserinfoServiceV2 {

	/**
	 * 新增/更新首页个人展示
	 * @param indexuserinfo
	 * @return
	 * Lee.J.Eric
	 * 2014年6月18日 下午4:35:57
	 */
	public Indexuserinfo indexuserinfoSaveOrUpdate(Indexuserinfo indexuserinfo);
	
	/**
	 * 根据id获取首页人个展示
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014年6月18日 下午4:36:18
	 */
	public Indexuserinfo findById(String id);
	
	/**
	 * 根据id删除首页个人展示
	 * @param id
	 * Lee.J.Eric
	 * 2014年6月18日 下午4:46:53
	 */
	public void deleteById(String id);
	
	/**
	 * 根据id集合获取首页个人展示列表
	 * @param ids
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 下午2:08:41
	 */
	public List<Indexuserinfo> getByIdList(List<String> ids);
	
	/**
	 * 根据关键字搜索个人展示
	 * @param page
	 * @param size
	 * @param keyword
	 * @return
	 * Lee.J.Eric
	 * 2014年6月19日 下午2:30:06
	 */
	public Page<Indexuserinfo> searchByKeyword(Integer page,Integer size,final String keyword);
}
