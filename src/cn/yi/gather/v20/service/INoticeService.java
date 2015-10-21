package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.Notice;

/**
 * 社区公告
 * 
 * @author Lee.J.Eric
 * 
 */
public interface INoticeService {

	/**
	 * 公告新增(新增动态记录)
	 * @param notice
	 * @return
	 * Lee.J.Eric
	 * 2014年9月17日 下午3:57:59
	 */
	public Notice noticeSaveOrUpdate(Notice notice);
	
	/**
	 * 公告删除(删除动态记录)
	 * @param id
	 * Lee.J.Eric
	 * 2014年9月17日 下午4:16:42
	 */
	public void deleteNotice(String id);
	
	/**
	 * 根据id查询公告
	 * @param id
	 * @return
	 * Lee.J.Eric
	 * 2014年9月17日 下午5:24:09
	 */
	public Notice findByid(String id);
	
	public com.common.Page<Notice> getNoticeForPage(Integer page, Integer size,final String keyword);
}
