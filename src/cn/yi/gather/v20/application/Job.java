package cn.yi.gather.v20.application;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Course;
import cn.yi.gather.v20.entity.CurrentMsg;
import cn.yi.gather.v20.entity.Demands;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.NoticeMsg;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.IActivityService;
import cn.yi.gather.v20.service.ICourseService;
import cn.yi.gather.v20.service.ICurrentMsgService;
import cn.yi.gather.v20.service.IDemandsService;
import cn.yi.gather.v20.service.IEventlogService;
import cn.yi.gather.v20.service.IGatheringService;
import cn.yi.gather.v20.service.IItemInstanceService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.INoticeMsgService;
import cn.yi.gather.v20.service.IOrderService;
import cn.yi.gather.v20.service.IUserService;

/**
 * 定时任务
 * 
 * @author Lee.J.Eric
 * 
 */
@Component("jobV20")
public class Job {
	private static Logger log = Logger.getLogger(Job.class);

	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "currentMsgServiceV20")
	private ICurrentMsgService currentMsgService;
	
	@Resource(name = "gatheringServiceV20")
	private IGatheringService gatheringService;
	
	@Resource(name = "activityServiceV20")
	private IActivityService activityService;
	
	@Resource(name = "courseServiceV20")
	private ICourseService courseService;
	
	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;
	
	@Resource(name = "noticeMsgServiceV20")
	private INoticeMsgService noticeMsgService;
	
	@Resource(name = "eventlogServiceV20")
	private IEventlogService eventlogService;
	
	@Resource(name="demandsServiceV20")
	private IDemandsService demandsService;
	
	@Resource(name = "orderServiceV20")
	private IOrderService orderService;
	
	@Resource(name = "itemInstanceServiceV20")
	private IItemInstanceService itemInstanceService;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	// 每天23时50分0秒
	@Scheduled(cron = "0 50 23 * * ?")
	public void daily() {
		Date now = new Date();
		userService.checkoutalluser();//所有用户签出
		currentMsgService.clearNowMsg();//清空now的信息
		userService.userOverdue(now);//会员过期
		itemInstanceService.instanceOverdue(now);//商品过期
		
	}
	
	// 每小时
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void hour(){
		labelsService.updateHot();
	}
	
	// 每天上午9点
	@Scheduled(cron = "0 0 9 * * ?")
	public void morningtime(){
//		userService.userExpire();
	}
	
	/**
	 * 检查和处理所有超时未支付的订单
	 * 
	 * @author Lee.J.Eric
	 * @time 2015年1月14日 下午4:22:08
	 */
	//每分钟
	@Scheduled(cron = "0 0/1 * * * ?")
	public void handleExpiredOrders() {
		try {
			log.warn("处理订单任务开始>........");
			// 1. 检查和处理所有超时未支付的订单
			orderService.handleExpiredOrders();
			log.warn("处理订单任务结束!");
		} catch (Exception e) {
            log.error("处理订单任务出现异常", e);
		}
	}
	
	/**
	 * 小活动即将开始
	 * 
	 * @author Lee.J.Eric
	 * @time 2014年11月28日 下午5:40:09
	 */
	// 每天7时到23时，每30分钟
	@Scheduled(cron = "10 0/30 07-23 * * ?")
	public void gatheringComing(){
		Calendar calendar = Calendar.getInstance();
		Date begin = calendar.getTime();
		calendar.add(Calendar.MINUTE, 30);
		Date end = calendar.getTime();
		List<Gathering> list = gatheringService.findListByOpendate(begin, end);
		for (Gathering gathering : list) {
			CurrentMsg msg = new CurrentMsg();
			StringBuffer sb = new StringBuffer();
			sb.append(gathering.getTitle()).append("活动开始了，在").append(gathering.getAddress());
			msg.setContents(sb.toString());
			currentMsgService.saveOrUpdate(msg);
		}
		
	}
	
	/**
	 * 活动即将开始
	 * 
	 * @author Lee.J.Eric
	 * @time 2014年12月1日 下午3:36:06
	 */
	// 每天7时到23时，每30分钟
	@Scheduled(cron = "10 0/30 07-23 * * ?")
	public void activitComing(){
		Calendar calendar = Calendar.getInstance();
		Date begin = calendar.getTime();
		calendar.add(Calendar.MINUTE, 30);
		Date end = calendar.getTime();
		List<Activity> list = activityService.findListOpendateBetween(begin, end);
		for (Activity activity : list) {
			Page<User> users = eventlogService.findUserListByIdAndEventtypeAndActtype(activity.getId(), "sign", Activity.entityName, 0, 10000);
			String[] userArr = new String[users.getContent().size()];
			for (int i=0;i< users.getTotalElements();i++) {
				userArr[i] = users.getContent().get(i).getId();
			}
			
			//now-消息
			CurrentMsg currentMsg = new CurrentMsg();
			StringBuffer sb = new StringBuffer();
			sb.append(activity.getTitle()).append("活动开始了，在").append(activity.getAddress());
			currentMsg.setContents(sb.toString());
			currentMsgService.saveOrUpdate(currentMsg);
			
			//消息中心-消息
			NoticeMsg noticeMsg = new NoticeMsg();
			noticeMsg.setActiontype("sign");
			noticeMsg.setContents("你参加的"+activity.getTitle() + "将在30分钟后开始，不要迟到哦");
			noticeMsg.setMsgtype(Activity.entityName);
			noticeMsg.setParam(activity.getId());
			noticeMsg.setTags(new HashSet<User>(users.getContent()));
			noticeMsg.setIcon("clock");
			noticeMsgService.saveOrUpdate(noticeMsg);
			
			//push通知
			JSONObject msg_extras = new JSONObject();
			msg_extras.put("type", Activity.entityName);
			msg_extras.put("parameter", activity.getId());
			msg_extras.put("icon", "clock");
			
			//notification
			pushService.push(activity.getTitle() + "即将开始！",
					activity.getTitle(),msg_extras, Platform .android_ios(),
					userArr, new String[] { "version4","userstart0" });
			//message
//			pushService.push(Platform.android_ios(), userArr, new String[] { "version4" }, activity.getTitle() + "即将开始！", activity.getTitle(), "activity", msg_extras);
			
		}
	}
	
	/**
	 * 活动结束后，发反馈通知
	 * 
	 * @author Lee.J.Eric
	 * @time 2014年12月4日 下午2:42:15
	 */
	// 每天7时到23时，每30分钟
	@Scheduled(cron = "10 0/30 07-23 * * ?")
	public void activityEnd(){
		Calendar calendar = Calendar.getInstance();
		Date begin = calendar.getTime();
		calendar.add(Calendar.MINUTE, 30);
		Date end = calendar.getTime();
		List<Activity> list = activityService.findListEnddateBetween(begin, end);
		for (Activity activity : list) {
			//判断是否有问卷，若有，则执行下面
			
			//拿到活动出席列表
			Page<User> users = eventlogService.findUserListByIdAndEventtypeAndActtype(activity.getId(), "join", Activity.entityName, 0, 10000);
			String[] userArr = new String[users.getContent().size()];
			for (int i=0;i< users.getTotalElements();i++) {
				userArr[i] = users.getContent().get(i).getId();
			}
			
			//消息中心-消息
			NoticeMsg noticeMsg = new NoticeMsg();
			noticeMsg.setActiontype("end");
			noticeMsg.setContents("你参加的活动"+activity.getTitle() + "结束了，请填写反馈问卷");
			noticeMsg.setMsgtype(Activity.entityName);
			noticeMsg.setParam(activity.getId());
			noticeMsg.setTags(new HashSet<User>(users.getContent()));
			noticeMsg.setIcon("loudspeaker");
			noticeMsgService.saveOrUpdate(noticeMsg);
			
			//push通知
			JSONObject msg_extras = new JSONObject();
			msg_extras.put("type", Activity.entityName);
			msg_extras.put("parameter", activity.getId());
			msg_extras.put("icon", "loudspeaker");
			msg_extras.put("action", "end");
			
			//notification
			pushService .push(activity.getTitle() + "已经结束！",
					activity.getTitle(), msg_extras,Platform .android_ios(),
					userArr, new String[] { "version4","userstart0" });
			
			//message
//			pushService.push(Platform.android_ios(), userArr, new String[] { "version4" }, activity.getTitle() + "已经结束！", activity.getTitle(), "activity", msg_extras);
		}
	}
	
	/**
	 * 课程即将开始
	 * 
	 * @author Lee.J.Eric
	 * @time 2014年12月1日 下午3:36:18
	 */
	// 每天7时到23时，每30分钟
	@Scheduled(cron = "10 0/30 07-23 * * ?")
	public void courseComing(){
		Calendar calendar = Calendar.getInstance();
		Date begin = calendar.getTime();
		calendar.add(Calendar.MINUTE, 30);
		Date end = calendar.getTime();
		List<Course> list = courseService.findListOpendateBetween(begin, end);
		for (Course course : list) {
			Page<User> users = eventlogService.findCourseStudentList(course.getId(), 0, 10000);
			String[] userArr = new String[users.getContent().size()];
			for (int i=0;i< users.getTotalElements();i++) {
				userArr[i] = users.getContent().get(i).getId();
			}
			//now-消息
			CurrentMsg msg = new CurrentMsg();
			StringBuffer sb = new StringBuffer();
			sb.append(course.getTitle()).append("课程开始了，在").append(course.getAddress());
			msg.setContents(sb.toString());
			currentMsgService.saveOrUpdate(msg);
			
			//消息中心-消息
			NoticeMsg noticeMsg = new NoticeMsg();
			noticeMsg.setActiontype("sign");
			noticeMsg.setContents("你参加的"+course.getTitle() + "将在30分钟后开始，不要迟到哦");
			noticeMsg.setMsgtype(Course.entityName);
			noticeMsg.setParam(course.getId());
			noticeMsg.setTags(new HashSet<User>(users.getContent()));
			noticeMsg.setIcon("clock");
			noticeMsgService.saveOrUpdate(noticeMsg);
			
			//push通知
			JSONObject msg_extras = new JSONObject();
			msg_extras.put("type", Course.entityName);
			msg_extras.put("parameter", course.getId());
			msg_extras.put("icon", "clock");
			
			//notification
			pushService .push(course.getTitle() + "即将开始！",
					course.getTitle(),msg_extras, Platform .android_ios(),
					userArr, new String[] { "version4","userstart0" });
			
			//message
//			pushService.push(Platform.android_ios(), userArr, new String[] { "version4" }, course.getTitle() + "即将开始！", course.getTitle(), "course", msg_extras);
		}
	}
	
	
	/**
	 * 需求10天询问一次
	 */
	// 每天10点
	@Scheduled(cron = "0 0 10 * * ?")
	public void demandsEnd(){
		List<Demands> list=demandsService.findjoblist();
		if(list!=null){
			for (Demands demands : list) {
				//回复需求，push消息给发起者
				NoticeMsg noticeMsg=new NoticeMsg();
				noticeMsg.setActiontype("end");
				noticeMsg.setContents("需求解决了吗");
				noticeMsg.setIcon("loudspeaker");
				noticeMsg.setMsgtype("demands");
				noticeMsg.setParam(demands.getId());
				Set<User> usertag=new HashSet<User>();
				usertag.add(demands.getPublishuser());
				noticeMsg.setTags(usertag);
				noticeMsgService.saveOrUpdate(noticeMsg);
				
				JSONObject msg_extras = new JSONObject();
				msg_extras.put("type", "demands");
				msg_extras.put("parameter", demands.getId());
				msg_extras.put("icon", "loudspeaker");
				msg_extras.put("action", "end");
				pushService.push(demands.getTitle(), "需求解决了吗",msg_extras, Platform.android_ios(), new String[]{demands.getPublishuser().getId()}, new String[]{"version4","userstart0"});
				
			}
			
		}
	}
}
