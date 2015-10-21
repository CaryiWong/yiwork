package cn.yi.gather.v11.service;

import java.util.Date;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import cn.yi.gather.v11.entity.Pushlog;

/**
 * jpush推送接口
 * @author Lee.J.Eric
 * @time 2014年6月11日下午4:13:13
 */
public interface IJPushServiceV2 {
	
	/**
	 * 主动push消息--jpush V3.1.2
	 * @param platform
	 * @param audience
	 * @param notification
	 * Lee.J.Eric
	 * 2014年8月7日 上午11:04:22
	 */
	public void push(Platform platform,Audience audience,Notification notification);
	
	/**
	 * 主动push消息
	 * @param tag 标签
	 * @param platform 平台类型,android,ios(逗号分隔)
	 * @param title
	 * @param type 0活动，1资源，2系统
	 * Lee.J.Eric
	 * 2014 2014年6月11日 下午4:13:45
	 */
	public void push(String tag,String platform,String title,Integer type);
	
	/**
	 * 新增push记录
	 * @param pushlog
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月11日 下午4:14:56
	 */
	public Pushlog pushlogSaveOrUpdate(Pushlog pushlog);
	
	/**
	 * * 根据push时间，tag,type统计push的个数
	 * @param time
	 * @param tags
	 * @param type 0活动，1资源，2系统
	 * @param time
	 * @param tags
	 * @param type
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月11日 下午4:16:24
	 */
	public Long countPushlogByTagTimeType(Date time,String tags,Integer type);

	/**
	 * 
	 * @param type 0活动，1资源，2系统
	 * @param parameter 根据type指定的参数,若为系统，则为空串
	 * @param alert 内容
	 * @param title 标题
	 * @param platform 平台类型
	 * @param tag 标签(并集)
	 * @param tag_and 标签(交集)
	 * Lee.J.Eric
	 * 2014年8月7日 下午4:54:41
	 */
	public void push(Integer type, String parameter, String alert, String title,
			final Platform platform, String[] tag, String[] tag_and);
}
