package cn.yi.gather.v11.service.serviceImpl;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.yi.gather.v11.dao.PushlogRepository;
import cn.yi.gather.v11.entity.Pushlog;
import cn.yi.gather.v11.service.IJPushServiceV2;

import com.common.R;
import com.tools.utils.MD5Util;
import com.tools.utils.http.HttpRequester;
import com.tools.utils.http.HttpRespons;

/**
 * jpush推送
 * 
 * @author Lee.J.Eric
 * @time 2014年6月11日下午4:13:17
 */
@Service("jPushServiceV2")
public class JPushServiceV2 implements IJPushServiceV2 {
	private static Logger log = Logger.getLogger(JPushServiceV2.class);

	@Resource(name = "pushlogRepositoryV2")
	private PushlogRepository pushlogRepository;

	@Override
	public void push(String tag, String platform, String title, Integer type) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		HttpRequester requester = new HttpRequester();
		HttpRespons respons = new HttpRespons();
		int sendno = (int) (Math.random() * (0 - Integer.MAX_VALUE))
				+ Integer.MAX_VALUE;
		String verification_code = MD5Util.encrypt(
				String.valueOf(sendno) + 2 + tag + R.JPush.MASTER_SECRET_V)
				.toUpperCase();
		String content = "{\"n_content\":\""
				+ title
				+ "\", \"n_extras\":{\"ios\":{\"badge\":1,\"sound\":\"default\"},\"type\":"
				+ type + "}}";
		try {
			// 新增push记录
			Pushlog pushlog = new Pushlog();
			pushlog.setTag(tag);
			pushlog.setTitle(title);
			pushlog.setType(type);
			pushlogSaveOrUpdate(pushlog);
			// 新增push记录

			content = URLEncoder.encode(content, "utf-8");// 内容进行URL编码
			params.put(R.JPush.SEND_NO, sendno);
			params.put(R.JPush.APP_KEY, R.JPush.APP_KEY_V);
			params.put(R.JPush.RECEIVER_TYPE, 2);
			params.put(R.JPush.RECEIVER_VALUE, tag);
			params.put("verification_code", verification_code);
			params.put("msg_type", 1);
			params.put(R.JPush.PLATFORM, platform);
			params.put("msg_content", content);
			respons = requester.sendPost(R.JPush.PUSH_URL_8800, params);
			System.out.println(respons.getContent());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("push失败---" + e);
		}
	}

	@Override
	public Pushlog pushlogSaveOrUpdate(Pushlog pushlog) {
		// TODO Auto-generated method stub
		return pushlogRepository.save(pushlog);
	}

	@Override
	public Long countPushlogByTagTimeType(final Date time, final String tags,
			final Integer type) {
		// TODO Auto-generated method stub
		Specification<Pushlog> spec = new Specification<Pushlog>() {
			@Override
			public Predicate toPredicate(Root<Pushlog> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Predicate p0 = cb.equal(root.<Integer> get("type"), type);
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
	public void push(Integer type, String param, String alert, String title,
			final Platform platform, String[] tag, String[] tag_and) {
		// 新增push记录
		String tagString = new String();
		for (int i = 0;i<tag.length;i++) {
			if(!tag[i].equalsIgnoreCase("version2")){
				if(i==0)
					tagString += tag[i];
				else
					tagString += "," + tag[i];
			}
		}
		Pushlog pushlog = new Pushlog();
		pushlog.setTag(tagString);
		pushlog.setTitle(title);
		pushlog.setType(type);
		pushlogSaveOrUpdate(pushlog);
		// 新增push记录
		final Audience audience = Audience.newBuilder()
				.addAudienceTarget(AudienceTarget.tag_and(tag_and))
				.addAudienceTarget(AudienceTarget.tag(tag)).build();
		final Notification notification = Notification
				.newBuilder()
				.addPlatformNotification(
						IosNotification.newBuilder().setAlert(alert)
								.setBadge(1).setSound("default")
								.addExtra("type", type)
								.addExtra("parameter", param).build())
				.addPlatformNotification(
						AndroidNotification.newBuilder().setAlert(alert)
								.addExtra("type", type)
								.addExtra("parameter", param).setTitle(title)
								.setBuilderId(3).build()).build();
		//担心第三方push时间太长，交给线程来处理,实现异步返回，避免接口超时
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				push(platform,audience,notification);
			}
		}).start();
	}

}
