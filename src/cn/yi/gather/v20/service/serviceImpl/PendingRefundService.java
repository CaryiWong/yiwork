package cn.yi.gather.v20.service.serviceImpl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.PendingRefundRepository;
import cn.yi.gather.v20.entity.Payment;
import cn.yi.gather.v20.entity.PendingRefund;
import cn.yi.gather.v20.entity.PendingRefund.RefundStatus;
import cn.yi.gather.v20.service.IPendingRefundService;

@Service("pendingRefundServiceV20")
public class PendingRefundService implements IPendingRefundService {
	
	private static Logger log = Logger.getLogger(PendingRefundService.class);
	
	@Resource(name = "pendingRefundRepositoryV20")
	private PendingRefundRepository repository;

	@Override
	public PendingRefund saveOrUpate(PendingRefund entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	public PendingRefund findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public void prepareToRefund(Payment payment) {
		// TODO Auto-generated method stub
		PendingRefund pr = new PendingRefund();
		pr.setAlipayTradeNo(payment.getAlipayTradeNo());
		pr.setBuyerId(payment.getBuyerId());
		pr.setBuyerEmail(payment.getBuyerEmail());
		pr.setBankSeqNo(payment.getBankSeqNo());
		pr.setOrderId(payment.getOrderId());
		pr.setUserId(payment.getUserId());
		pr.setMoney(payment.getMoney());
		pr.setStatus(RefundStatus.PENDING.getCode());
		pr.setMemo("订单不存在");
		pr.setCreateTime(new Timestamp(System.currentTimeMillis()));
		pr.setModifyTime(new Timestamp(System.currentTimeMillis()));
		repository.save(pr);
	}

}
