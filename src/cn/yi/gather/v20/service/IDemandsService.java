package cn.yi.gather.v20.service;


import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v20.entity.Demands;

public interface IDemandsService {

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
	public Page<Demands> getAllDemands(Integer page, Integer size,String listorderby);
	
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
	
	
	/**
	 * 2.0   我的需求列表
	 * @param page        	当前页：    首页 0
	 * @param size        	每页显示记录数
	 * @param userid        我的ID 当前用户ID
	 * @param status        需求状态：-1：全部        1：正在解决        2：解决完成     3：未上架   4：未审核  5：已取消/已关闭
	 * @param flag          1：我发起       2：我参与      
	 * @param listorderby   发布时间排序：asc 正序   desc 倒序
	 */
	@RequestMapping(value="myDemandList")
	public Page<Demands> myDemandList(Integer page,Integer size,String userid,Integer status,Integer flag,String listorderby);
	
	/**
	 * 未解决的需求 没10天询问一次
	 * @return
	 */
	public List<Demands> findjoblist();
	
	/**
	 * 搜索/按类型取需求
	 * @param page
	 * @param size
	 * @param orderby
	 * @param searchkey
	 * @param demandtype
	 * @return
	 */
	public Page<Demands> searchDemandList(Integer page, Integer size,String orderby,String searchkey,String demandtype);
	
}
