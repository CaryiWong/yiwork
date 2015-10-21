package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.AlipayNotificationLog;

@Component("alipayNotificationLogRepositoryV20")
public interface AlipayNotificationLogRepository extends JpaRepository<AlipayNotificationLog, String>,JpaSpecificationExecutor<AlipayNotificationLog>{

}
