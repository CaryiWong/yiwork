package cn.yi.gather.v22.yg100.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v22.yg100.dao.YgItemInstanceRepository;
import cn.yi.gather.v22.yg100.entity.YgItemInstance;
import cn.yi.gather.v22.yg100.entity.OrderInfo;
import cn.yi.gather.v22.yg100.service.IYgItemInstanceService;

@Service("ygItemInstanceService")
public class YgItemInstanceService implements IYgItemInstanceService{

	@Resource(name="ygItemInstanceRepository")
	private YgItemInstanceRepository repository;
	
	@Override
	public YgItemInstance saveOrUpdate(YgItemInstance instance) {
		// TODO Auto-generated method stub
		return repository.save(instance);
	}

	@Override
	public YgItemInstance findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<YgItemInstance> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public List<YgItemInstance> findByCouponnumber(String vcode) {
		// TODO Auto-generated method stub
		return repository.findByCouponnumber(vcode);
	}

	@Override
	public List<YgItemInstance> findByOrder(OrderInfo info) {
		// TODO Auto-generated method stub
		return repository.findByOrderInfo(info);
	}

	@Override
	public List<YgItemInstance> findByUser(String uid) {
		// TODO Auto-generated method stub
		return repository.findByUserid(uid);
	}


}
