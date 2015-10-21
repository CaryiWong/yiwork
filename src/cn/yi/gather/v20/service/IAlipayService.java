package cn.yi.gather.v20.service;

import com.common.Jr;

import cn.yi.gather.v20.entity.AlipayNotificationLog;
import cn.yi.gather.v20.entity.Order;

public interface IAlipayService {

	/**
	 * alipay异步通知，根据订单类型处理相关业务
	 * @param notification
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月5日 下午4:47:32
	 */
	public void dealAlipayNotification(AlipayNotificationLog notification) throws Exception;
	
	/**
	 * 请求alipay
	 * @param type web|ios|android|mobile
	 * @param jr
	 * @param order
	 * @return
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年1月9日 下午2:33:52
	 */
	public Jr applyAlipay(String type, Jr jr, Order order) throws Exception;
}
