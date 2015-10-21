package cn.yi.gather.v20.service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.yi.gather.v20.dao.PushlogRepository;
import cn.yi.gather.v20.entity.Pushlog;
import cn.yi.gather.v20.service.IJPushService;

import com.common.R;

/**
 * jpush推送
 * 
 * @author Lee.J.Eric
 * @time 2014年6月11日下午4:13:17
 */
@Service("jPushServiceV20")
public class JPushService implements IJPushService {
	private static Logger log = Logger.getLogger(JPushService.class);
	
	@Resource(name = "pushlogRepositoryV20")
	private PushlogRepository pushlogRepository;


	@Override
	public Pushlog pushlogSaveOrUpdate(Pushlog pushlog) {
		// TODO Auto-generated method stub
		return pushlogRepository.save(pushlog);
	}

	@Override
	public Long countPushlogByTagTimeType(final Date time, final String tags,
			final String type) {
		// TODO Auto-generated method stub
		Specification<Pushlog> spec = new Specification<Pushlog>() {
			@Override
			public Predicate toPredicate(Root<Pushlog> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = cb.equal(root.<String> get("type"), type);
				Predicate p1 = cb.greaterThan(root.<Date> get("createtime"),
						time);
				Predicate p2 = null;
				if (tags != null) {
					String[] tag = tags.split(",");
					List<Predicate> list = new ArrayList<Predicate>();
					for (int i = 0; i < tag.length; i++) {
						list.add(cb.like(root.<String> get("tag"), tag[i]));
					}
					Predicate[] ors = new Predicate[list.size()];
					for (int i = 0; i < list.size(); i++) {
						ors[i] = list.get(i);
					}
					p2 = cb.or(ors);
				} else {
					p2 = cb.isNull(root.<String> get("tag"));
				}
				query.where(cb.and(p0, p1, p2));
				return query.getRestriction();
			}
		};
		return pushlogRepository.count(spec);
	}

	@Override
	public void push(Platform platform, Audience audience,
			Notification notification) {
		// TODO Auto-generated method stub
		JPushClient jpushClient = new JPushClient(R.JPush.MASTER_SECRET_V,R.JPush.APP_KEY_V, 3);
		PushPayload payload = PushPayload
				.newBuilder()
				.setPlatform(platform)
				.setAudience(audience)
				.setNotification(notification)
				.setOptions(// IOS测试证书：false,正式环境为true  2015-05-27
						Options.newBuilder().setApnsProduction(true).setTimeToLive(604800L).build())
				.build();
		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);
			log.info("Got result - " + result);
		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			log.error(
					"Error response from JPush server. Should review and fix it. ",
					e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
		}

	}
	
	@Override
	public void push_ios(Platform platform, Audience audience,
			Notification notification) {
		// TODO Auto-generated method stub
		log.info("start push_ios " );
		JPushClient jpushClient = new JPushClient(R.JPush.MASTER_SECRET_IOS_V,R.JPush.APP_KEY_IOS_V, 3);
		PushPayload payload = PushPayload
				.newBuilder()
				.setPlatform(platform)
				.setAudience(audience)
				.setNotification(notification)
				.setOptions(// IOS测试证书：false,正式环境为true  2015-05-27
						Options.newBuilder().setApnsProduction(true).setTimeToLive(604800L).build())
				.build();
		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);
			log.info("Got result push_ios - " + result);
		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. push_ios.", e);
		} catch (APIRequestException e) {
			log.error(
					"Error response from JPush server. Should review and fix it. push_ios.",
					e);
			log.info("HTTP Status push_ios: " + e.getStatus());
			log.info("Error Code push_ios: " + e.getErrorCode());
			log.info("Error Message push_ios: " + e.getErrorMessage());
		}

	}

	@Override
	public void push(String notification_alert, String notification_title,JSONObject notice_extras,
			final Platform platform, String[] tag, String[] tag_and) {
		if(tag.length==0){
			return ;
		}
		// 新增push记录
		String tagString = new String();
		for (int i = 0; i < tag.length; i++) {
			if (i == 0)
				tagString += tag[i];
			else
				tagString += "," + tag[i];
		}
		Pushlog pushlog = new Pushlog();
		pushlog.setTag(tagString);
		pushlog.setTitle(notification_title);
//		pushlog.setType(notification_type);
		pushlogSaveOrUpdate(pushlog);
		// 新增push记录
		final Audience audience = Audience.newBuilder()
				.addAudienceTarget(AudienceTarget.tag_and(tag_and))
				.addAudienceTarget(AudienceTarget.tag(tag)).build();
		final Notification notification = Notification
				.newBuilder()
				.addPlatformNotification(
						IosNotification.newBuilder().setAlert(notification_alert)
								.setBadge(1).setSound("default").addExtras(notice_extras).build())
				.addPlatformNotification(
						AndroidNotification.newBuilder().setAlert(notification_alert).addExtras(notice_extras).setTitle(notification_title)
								.setBuilderId(3).build()).build();
		//传说ios要单gank
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						push_ios(platform,audience,notification);
					}
				}).start();
		
		//担心第三方push时间太长，交给线程来处理,实现异步返回，避免接口超时
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				push(platform,audience,notification);
			}
		}).start();
		
		
	}

	@Override
	public void push(Platform platform, Audience audience,
			Notification notification, Message message) {
		// TODO Auto-generated method stub
		JPushClient jpushClient = new JPushClient(R.JPush.MASTER_SECRET_V,R.JPush.APP_KEY_V, 3);
		PushPayload payload = PushPayload
				.newBuilder()
				.setPlatform(platform)
				.setAudience(audience)
				.setNotification(notification)
				.setMessage(message)
				.setOptions(// IOS测试证书：false,正式环境为true
						Options.newBuilder().setApnsProduction(true).setTimeToLive(604800L).build())
				.build();
		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);
			log.info("Got result - " + result);
		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			log.error(
					"Error response from JPush server. Should review and fix it. ",
					e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
		}
	}
	


	@Override
	public void push(Platform platform, Audience audience, Message message) {
		// TODO Auto-generated method stub
		JPushClient jpushClient = new JPushClient(R.JPush.MASTER_SECRET_V,R.JPush.APP_KEY_V, 3);
		PushPayload payload = PushPayload
				.newBuilder()
				.setPlatform(platform)
				.setAudience(audience)
				.setMessage(message)
				.setOptions(// IOS测试证书：false,正式环境为true
						Options.newBuilder().setApnsProduction(true).setTimeToLive(604800L).build())
				.build();
		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);
			log.info("Got result - " + result);
		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			log.error(
					"Error response from JPush server. Should review and fix it. ",
					e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
		}
	}

	@Override
	public void push_ios(Platform platform, Audience audience, Message message) {
		// TODO Auto-generated method stub
		JPushClient jpushClient = new JPushClient(R.JPush.MASTER_SECRET_IOS_V,R.JPush.APP_KEY_IOS_V, 3);
		PushPayload payload = PushPayload
				.newBuilder()
				.setPlatform(platform)
				.setAudience(audience)
				.setMessage(message)
				.setOptions(// IOS测试证书：false,正式环境为true
						Options.newBuilder().setApnsProduction(true).setTimeToLive(604800L).build())
				.build();
		try {
			PushResult result = jpushClient.sendPush(payload);
			System.out.println(result);
			log.info("Got result - " + result);
		} catch (APIConnectionException e) {
			log.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			log.error(
					"Error response from JPush server. Should review and fix it. ",
					e);
			log.info("HTTP Status: " + e.getStatus());
			log.info("Error Code: " + e.getErrorCode());
			log.info("Error Message: " + e.getErrorMessage());
		}
	}

	@Override
	public void push(final Platform platform, String[] tag, String[] tag_and,
			String msg_content, String msg_title, String msg_content_type,
			JSONObject msg_extras) {
		if(tag.length==0){
			return ;
		}
		// TODO Auto-generated method stub
		// 新增push记录
		String tagString = new String();
		for (int i = 0; i < tag.length; i++) {
			if (i == 0)
				tagString += tag[i];
			else
				tagString += "," + tag[i];
		}
		Pushlog pushlog = new Pushlog();
		pushlog.setTag(tagString);
		pushlog.setTitle(msg_title);
//		pushlog.setType(msg_content_type);
		pushlogSaveOrUpdate(pushlog);
		// 新增push记录
		final Audience audience = Audience.newBuilder()
				.addAudienceTarget(AudienceTarget.tag_and(tag_and))
				.addAudienceTarget(AudienceTarget.tag(tag)).build();
		final Message message = Message.newBuilder().setMsgContent(msg_content)
				.setTitle(msg_title).setContentType(msg_content_type).addExtras(msg_extras).build();
		//担心第三方push时间太长，交给线程来处理,实现异步返回，避免接口超时
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				push(platform,audience,message);
			}
		}).start();
		
		//传说ios要单gank
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				push_ios(platform,audience,message);
			}
		}).start();
	}

}
