package cn.yi.gather.v22.yg100.service;

import cn.yi.gather.v22.yg100.entity.WxUser;

public interface IWxUserService {

	public WxUser save(WxUser user);
	
	public WxUser findById(String id);
	
	public WxUser findByOpenId(String id);
	
	public WxUser findByTel(String tel);
}
