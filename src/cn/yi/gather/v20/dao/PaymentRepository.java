package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Payment;

@Component("paymentRepositoryV20")
public interface PaymentRepository extends JpaRepository<Payment, String>,JpaSpecificationExecutor<Payment>{

}
