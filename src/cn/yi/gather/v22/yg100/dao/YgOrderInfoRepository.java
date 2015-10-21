package cn.yi.gather.v22.yg100.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v22.yg100.entity.OrderInfo;

@Component("ygOrderInfoRepository")
public interface YgOrderInfoRepository extends JpaRepository<OrderInfo, String>,JpaSpecificationExecutor<OrderInfo>{

	public List<OrderInfo> findByTel(String tel);
	
	public List<OrderInfo> findByUseridAndUsertype(String userid,String usertype);
	
	public List<OrderInfo> findByOrderstatus(String orderstatus);
	
}
