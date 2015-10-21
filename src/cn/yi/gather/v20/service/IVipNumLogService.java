package cn.yi.gather.v20.service;

import java.util.List;

import cn.yi.gather.v20.entity.VipNumLog;

public interface IVipNumLogService {

	public VipNumLog saveOrUpdate(VipNumLog entity);
	
	public void saveOrUpdate(List<VipNumLog> entities);
	
	public VipNumLog findById(String id);
	
	public VipNumLog findByVipNO(String vipNO);
	
	/**
	 * 查询下一个可分配的会员号
	 * @return
	 * @author Lee.J.Eric
	 * @time 2015年1月8日 下午4:34:49
	 */
	public VipNumLog findNextVip() throws Exception;
	
	/**
	 * 回收会员号
	 * @param vipNOs
	 * @author Lee.J.Eric
	 * @time 2015年1月10日 下午3:58:37
	 */
	public void recycleVipNO(List<String> vipNOs);
}
