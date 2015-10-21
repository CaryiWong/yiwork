package cn.yi.gather.v22.yg100.service;

import cn.yi.gather.v22.yg100.entity.WxTicket;

public interface IWxTicketService {

	public WxTicket save(WxTicket wxTicket);
	
	public WxTicket find(String id);
}
