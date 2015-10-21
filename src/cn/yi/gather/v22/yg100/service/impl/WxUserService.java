package cn.yi.gather.v22.yg100.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v22.yg100.dao.WxUserRepository;
import cn.yi.gather.v22.yg100.entity.WxUser;
import cn.yi.gather.v22.yg100.service.IWxUserService;

@Service("wxUserService")
public class WxUserService implements IWxUserService{

	@Resource(name="wxUserRepository")
	private WxUserRepository repository;
	
	@Override
	public WxUser save(WxUser user) {
		// TODO Auto-generated method stub
		return repository.save(user);
	}

	@Override
	public WxUser findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public WxUser findByOpenId(String id) {
		// TODO Auto-generated method stub
		return repository.findByOpenid(id);
	}

	@Override
	public WxUser findByTel(String tel) {
		// TODO Auto-generated method stub
		return repository.findByTel(tel);
	}

}
