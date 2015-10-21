package cn.yi.gather.v20.service.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.AlipayRefundNotificationLogRepository;
import cn.yi.gather.v20.entity.AlipayRefundNotificationLog;
import cn.yi.gather.v20.service.IAlipayRefundNotificationLogService;

@Service("alipayRefundNotificationLogServiceV20")
public class AlipayRefundNotificationLogService implements IAlipayRefundNotificationLogService{
	
	@Resource(name = "alipayRefundNotificationLogRepositoryV20")
	private AlipayRefundNotificationLogRepository repository;

	@Override
	public AlipayRefundNotificationLog saveOrUpdate(
			AlipayRefundNotificationLog entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

}
