package cn.yi.gather.v20.service;

import cn.yi.gather.v20.entity.AlipayNotificationLog;

public interface IAlipayNotificationLogService {

	public AlipayNotificationLog saveOrUpdate(AlipayNotificationLog entity);
	
	/**
	 * 是否收到了重复的支付宝通知
	 * @param alipay_notification
	 * @return
	 * @throws Exception
	 * @author Lee.J.Eric
	 * @time 2015年2月2日 上午9:56:00
	 */
	public boolean isDuplicatedAlipayNotification(AlipayNotificationLog alipay_notification) throws Exception;
}
