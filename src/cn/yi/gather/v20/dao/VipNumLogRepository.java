package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.VipNumLog;

@Component("vipNumLogRepositoryV20")
public interface VipNumLogRepository extends JpaRepository<VipNumLog, String>,
		JpaSpecificationExecutor<VipNumLog> {

	public VipNumLog findByVipNO(String vipNO);
}
