package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.AlipayNotificationLogRepository;
import cn.yi.gather.v20.entity.AlipayNotificationLog;
import cn.yi.gather.v20.service.IAlipayNotificationLogService;

@Service("alipayNotificationLogServiceV20")
public class AlipayNotificationLogService implements IAlipayNotificationLogService{
	
	@Resource(name = "alipayNotificationLogRepositoryV20")
	private AlipayNotificationLogRepository repository;

	@Override
	public AlipayNotificationLog saveOrUpdate(
			AlipayNotificationLog entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public boolean isDuplicatedAlipayNotification(
			final AlipayNotificationLog alipay_notification) throws Exception {
		// TODO Auto-generated method stub
		Specification<AlipayNotificationLog> spec = new Specification<AlipayNotificationLog>() {
			
			@Override
			public Predicate toPredicate(Root<AlipayNotificationLog> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<String>get("alipayTradeNo"), alipay_notification.getAlipayTradeNo()));
				ps.add(cb.notEqual(root.<String>get("notifyId"), alipay_notification.getNotifyId()));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};
		List<AlipayNotificationLog> list = repository.findAll(spec);
		return list.isEmpty()?false:true;
	}

}
