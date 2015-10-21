package cn.yi.gather.v22.yg100.service;

import java.util.List;

import cn.yi.gather.v22.yg100.entity.YgItemInstance;
import cn.yi.gather.v22.yg100.entity.OrderInfo;

public interface IYgItemInstanceService {

	public YgItemInstance saveOrUpdate(YgItemInstance instance);
	
	public YgItemInstance findById(String id);
	
	public List<YgItemInstance> findAll();
	
	public List<YgItemInstance> findByCouponnumber(String vcode);
	
	public List<YgItemInstance> findByOrder(OrderInfo info);
	
	public List<YgItemInstance> findByUser(String uid);
}
