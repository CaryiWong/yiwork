package cn.yi.gather.v20.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.ItemInstance;

@Component("itemInstanceRepositoryV20")
public interface ItemInstanceRepository extends JpaRepository<ItemInstance, String>,JpaSpecificationExecutor<ItemInstance> {

	@Modifying
	@Query(value = "update ItemInstance set status =:status,modifyTime=:modifyTime where orderId =:orderId")
	public void updateByOrderId(@Param("status")Integer status,@Param("modifyTime")Date modifyTime,@Param("orderId")String orderId);
	
	public ItemInstance findByCouponnumberAndStatus(String couponnumber,Integer status);
	
	
}
