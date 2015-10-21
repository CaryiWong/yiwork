package cn.yi.gather.v20.service;

import java.util.Date;

import net.sf.json.JSONObject;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import cn.yi.gather.v20.entity.Pushlog;

/**
 * jpush推送接口
 * @author Lee.J.Eric
 * @time 2014年6月11日下午4:13:13
 */
public interface IJPushService {
	
	/**
	 * 主动push消息--jpush V3.1.2
	 * @param platform 平台
	 * @param audience 目标听众
	 * @param notification 通知
	 * Lee.J.Eric
	 * 2014年8月7日 上午11:04:22
	 */
	public void push(Platform platform,Audience audience,Notification notification);
	
	/**
	 * 
	 * @param platform
	 * @param audience
	 * @param notification
	 * @author Lee.J.Eric
	 * @time 2015年3月11日 下午5:24:19
	 */
	public void push_ios(Platform platform,Audience audience,Notification notification);
	
	/**
	 * 主动push消息--jpush V3.1.2
	 * @param platform 平台
	 * @param audience 目标听众
	 * @param notification 通知
	 * @param message 应用内消息
	 * @author Lee.J.Eric
	 * @time 2014年12月2日 上午11:19:46
	 */
	public void push(Platform platform,Audience audience,Notification notification,Message message);
	
	/**
	 * 主动push消息--jpush V3.1.2
	 * @param platform 平台
	 * @param audience 目标听众
	 * @param message 应用内消息
	 * @author Lee.J.Eric
	 * @time 2014年12月2日 下午3:00:04
	 */
	public void push(Platform platform,Audience audience,Message message);
	
	/**
	 * 
	 * @param platform
	 * @param audience
	 * @param message
	 * @author Lee.J.Eric
	 * @time 2015年3月11日 下午4:22:50
	 */
	public void push_ios(Platform platform,Audience audience,Message message);
	
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
	public Long countPushlogByTagTimeType(Date time,String tags,String type);

	/**
	 * 
	 * @param notification_type activity:活动，demands:资源，sys:系统
	 * @param notification_parameter 根据type指定的参数,若为系统，则为空串
	 * @param notification_alert 内容
	 * @param notification_title 标题
	 * @param platform 平台类型
	 * @param tag 标签(并集)
	 * @param tag_and 标签(交集)
	 * Lee.J.Eric
	 * 2014年8月7日 下午4:54:41
	 */
	public void push(String notification_alert, String notification_title,JSONObject notice_extras,
			Platform platform, String[] tag, String[] tag_and);
	
//	/**
//	 * 
//	 * @param notification_type activity:活动，demands:资源，sys:系统
//	 * @param notification_parameter 根据type指定的参数,若为系统，则为空串
//	 * @param notification_alert 内容
//	 * @param notification_title 标题
//	 * @param platform 平台类型
//	 * @param tag 标签(并集)
//	 * @param tag_and 标签(交集)
//	 * @param msg_content 消息内容
//	 * @param msg_title 消息标题
//	 * @param msg_content_type 消息内容类型
//	 * @param msg_extras 消息可选参数
//	 * @author Lee.J.Eric
//	 * @time 2014年12月2日 上午11:22:50
//	 */
//	public void push(String notification_alert, String notification_title,JSONObject notice_extras,
//			Platform platform, String[] tag, String[] tag_and,String msg_content,String msg_title,String msg_content_type,JSONObject msg_extras);

	/**
	 * 
	 * @param platform 平台类型
	 * @param tag 标签(并集)
	 * @param tag_and 标签(交集)
	 * @param msg_content 消息内容
	 * @param msg_title 消息标题
	 * @param msg_content_type 消息内容类型
	 * @param msg_extras 消息可选参数
	 * @author Lee.J.Eric
	 * @time 2014年12月2日 上午11:22:50
	 */
	public void push(Platform platform, String[] tag, String[] tag_and,String msg_content,String msg_title,String msg_content_type,JSONObject msg_extras);
}
