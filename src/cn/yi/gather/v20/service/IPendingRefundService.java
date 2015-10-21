package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.Payment;
import cn.yi.gather.v20.entity.PendingRefund;

public interface IPendingRefundService {
	
	public PendingRefund saveOrUpate(PendingRefund entity);
	
	public PendingRefund findById(String id);
	
	/**
	 * 待人工审核退款
	 * @param payment
	 * @author Lee.J.Eric
	 * @time 2015年1月7日 上午10:36:41
	 */
	public void prepareToRefund(Payment payment);

}
