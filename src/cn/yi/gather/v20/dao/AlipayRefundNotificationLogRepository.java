package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.AlipayRefundNotificationLog;

@Component("alipayRefundNotificationLogRepositoryV20")
public interface AlipayRefundNotificationLogRepository extends
		JpaRepository<AlipayRefundNotificationLog, String> {

}
