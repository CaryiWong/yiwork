package cn.yi.gather.v22.yg100.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v22.yg100.entity.YgItemInstance;
import cn.yi.gather.v22.yg100.entity.OrderInfo;

@Component("ygItemInstanceRepository")
public interface YgItemInstanceRepository extends JpaRepository<YgItemInstance, String>,JpaSpecificationExecutor<YgItemInstance>{

	public List<YgItemInstance> findByCouponnumber(String couponnumber);
	
	public List<YgItemInstance> findByOrderInfo(OrderInfo info);
	
	public List<YgItemInstance> findByUserid(String userid);
	
}
