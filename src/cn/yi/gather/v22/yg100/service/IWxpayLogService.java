package cn.yi.gather.v22.yg100.service;

import java.util.List;

import cn.yi.gather.v22.yg100.entity.WxpayLog;

public interface IWxpayLogService {

	public WxpayLog save(WxpayLog wxpayLog);
	
	public WxpayLog findById(String id);
	
	public List<WxpayLog> findAll();
	
}
