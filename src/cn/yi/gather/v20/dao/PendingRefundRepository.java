package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.PendingRefund;

@Component("pendingRefundRepositoryV20")
public interface PendingRefundRepository extends JpaRepository<PendingRefund, String>,JpaSpecificationExecutor<PendingRefund>{

}
