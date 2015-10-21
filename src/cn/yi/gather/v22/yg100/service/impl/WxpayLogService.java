package cn.yi.gather.v22.yg100.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v22.yg100.dao.WxpayLogRepository;
import cn.yi.gather.v22.yg100.entity.WxpayLog;
import cn.yi.gather.v22.yg100.service.IWxpayLogService;

@Service("wxpayLogService")
public class WxpayLogService implements IWxpayLogService{

	@Resource(name="wxpayLogRepository")
	private WxpayLogRepository repository;
	
	@Override
	public WxpayLog save(WxpayLog wxpayLog) {
		// TODO Auto-generated method stub
		return repository.save(wxpayLog);
	}

	@Override
	public WxpayLog findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<WxpayLog> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
