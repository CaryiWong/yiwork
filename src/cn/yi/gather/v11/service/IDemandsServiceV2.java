package cn.yi.gather.v11.service;


import org.springframework.data.domain.Page;

import cn.yi.gather.v11.entity.Demands;

public interface IDemandsServiceV2 {

	public Demands createDemand(Demands demands);
	
	public Demands getDemandinfo(String id);
	
	public Demands updateDemand(Demands demands);
	
	/**
	 * 我发表的需求
	 * @param page
	 * @param size
	 * @param orderby
	 * @param uid
	 * @return
	 */
	public Page<Demands> getMyDemands(Integer page,Integer size,Integer orderby,String uid) ;
	
	
	/**
	 * 获取所有需求
	 * @param page
	 * @param size
	 * @param orderby
	 * @return
	 */
	public Page<Demands> getAllDemands(Integer page, Integer size,Integer orderby);
	
	/**
	 * 根据条件获取需求列表
	 * @param page
	 * @param size
	 * @param listtype   -1全部，0  寻人  1 视频制作  2 发起活动  3 其他 4视频以外的
	 * @param keyword    搜索关键字
	 * @param uid        用户ID 非空时查询自己发表的需求
	 * @return
	 */
	public Page<Demands> getDemandsBy(Integer page, Integer size, Integer listtype,String keyword,String uid,Integer orderby);
	
	/**
	 * Admin  获取需求列表
	 * @param page
	 * @param size
	 * @param status  需求状态 0 需要解决  1  正在解决  2 解决完成
	 * @param demandtype -1全部，0  寻人  1 视频制作  2 发起活动  3 其他 4视频以外的
	 * @param keyword
	 * @param groups 领域
	 * @param ischeck 审核
	 * @return
	 */
	public Page<Demands> getDemandsByAdmin(Integer page,Integer size,Integer status,Integer  demandtype,String keyword,String groups,Integer ischeck);
}
