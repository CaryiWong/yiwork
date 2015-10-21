package cn.yi.gather.v22.yg100.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.yi.gather.v22.yg100.entity.OrderInfo;

public interface IYgOrderService {

	public OrderInfo saveOrUpdate(OrderInfo info);
	
	public OrderInfo findById(String id);
	
	public List<OrderInfo> findAll();
	
	public List<OrderInfo> findByTel(String tel);
	
	public List<OrderInfo> findByUser(String uid,String utype);
	
	public List<OrderInfo> findByStatus(String status);
	
	public Page<OrderInfo> findList(String userid,String usertype,String tel,String openid,Integer page,Integer size,String orderstart);
	
}
