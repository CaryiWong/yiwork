package cn.yi.gather.v22.yg100.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v22.yg100.dao.WxTicketRepository;
import cn.yi.gather.v22.yg100.entity.WxTicket;
import cn.yi.gather.v22.yg100.service.IWxTicketService;

@Service("wxTicketService")
public class WxTicketService implements IWxTicketService{

	@Resource(name="wxTicketRepository")
	private WxTicketRepository repository;
	
	@Override
	public WxTicket save(WxTicket wxTicket) {
		// TODO Auto-generated method stub
		return repository.save(wxTicket);
	}

	@Override
	public WxTicket find(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

}
