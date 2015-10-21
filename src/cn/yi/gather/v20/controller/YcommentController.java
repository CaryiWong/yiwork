package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.entity.NoticeMsg;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeActionTypeValue;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeMsgTypeValue;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.Ycomment;
import cn.yi.gather.v20.entity.YiQuestion;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.INoticeMsgService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IYcommentService;
import cn.yi.gather.v20.service.IYiQeustionService;

@Controller("ycommentControllerV20")
@RequestMapping(value="v20/ycomment")
public class YcommentController {

	@Resource( name="ycommentServiceV20")
	private IYcommentService ycommentService;

	@Resource(name = "yiQeustionServiceV20")
	private IYiQeustionService yiQeustionService;

	@Resource(name = "userServiceV20")
	private IUserService userService;

	@Resource(name = "noticeMsgServiceV20")
	private INoticeMsgService noticeMsgService;

	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;

	/**
	 * 发起点评
	 * @param response
	 * @param ycomment
	 */
	@RequestMapping(value="createycomment")
	public void createycomment(HttpServletResponse response,Ycomment ycomment,String type){
		Jr jr=new Jr();
		jr.setMethod("createycomment");
		if(ycomment.getUser()==null){
			jr.setCord(1);
			jr.setMsg("非法用户");
		}
		ycomment=ycommentService.save(ycomment);
		if(ycomment!=null){
			Set<String> set=new HashSet<String>();
			set.add("id");
			set.add("text");
			set.add("createtime");
			set.add("ytype");
			set.add("coffeenum");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			jr.setCord(0);
			jr.setData(BeanUtil.getFieldValueMap(ycomment, set));
			jr.setMsg("点评成功");
		}else{
			jr.setCord(-1);
			jr.setMsg("发起点评失败");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 送咖啡
	 * @param response
	 * @param id
	 * @param coffeenum
	 * @param type
	 */
	@RequestMapping(value="updateycomment")
	public void updateycomment(HttpServletResponse response,String id,Integer coffeenum,String type){
		Jr jr=new Jr();
		jr.setMethod("updateycomment");
		if(coffeenum==null||coffeenum==0){
			jr.setCord(1);
			jr.setMsg("没有咖啡");
		}
		Ycomment ycomment=ycommentService.findById(id);
		if(ycomment!=null){
			ycomment.setCoffeenum(coffeenum);
			ycomment=ycommentService.save(ycomment);
			Set<String> set=new HashSet<String>();
			set.add("id");
			set.add("text");
			set.add("createtime");
			set.add("ytype");
			set.add("coffeenum");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			jr.setCord(0);
			jr.setData(BeanUtil.getFieldValueMap(ycomment, set));
			jr.setMsg("咖啡赠送成功");
		}else{
			jr.setCord(2);
			jr.setMsg("非法传参");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取点评列表
	 * @param response
	 * @param page
	 * @param size
	 * @param type
	 */
	@RequestMapping(value="getycommentlist")
	public void getycommentlist(HttpServletResponse response,Integer page, Integer size,String type){
		Jr jr=new Jr();
		jr.setMethod("getycommentlist");
		Page<Ycomment> list=ycommentService.getYcommentList(page, size);
		Map<String, Integer> map=new HashMap<String, Integer>();
		map.put("z", ycommentService.getCountNum("z"));
		map.put("c", ycommentService.getCountNum("c"));
		map.put("coffee", ycommentService.getCountNum("coffee"));
		jr.setData2(map);
		jr.setPagenum(page+1);
		jr.setPagecount(list.getTotalPages());
		jr.setPagesum(list.getNumberOfElements());
		Set<String> set=new HashSet<String>();
		set.add("id");
		set.add("text");
		set.add("createtime");
		set.add("ytype");
		set.add("coffeenum");
		set.add("user.id");
		set.add("user.nickname");
		set.add("user.minimg");
		jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
		jr.setTotal(list.getTotalElements());
		jr.setCord(0);
		jr.setMsg("获取成功");
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 问小Yi
	 * @param response
	 * @param type
	 * @param qeustion
	 * @author Lee.J.Eric
	 * @time 2014年12月18日 下午3:09:51
	 */
	@RequestMapping(value = "yiquestion")
	public void yiQuestion(HttpServletResponse response,String type,YiQuestion qeustion){
		Jr jr = new Jr();
		jr.setMethod("yiquestion");
		if(qeustion.getMsgtype()==null||(qeustion.getMsgtype()==1&&(qeustion.getParent()==null||"".equals(qeustion.getParent())))){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			try {
				qeustion = yiQeustionService.saveOrUpdate(qeustion);
				if(qeustion.getMsgtype()==1){//回复
					YiQuestion yq = yiQeustionService.findById(qeustion.getParent());
					yq.getAnswer().add(qeustion);
					yiQeustionService.saveOrUpdate(yq);

					String title = qeustion.getUser().getNickname()+"在问小Y中发表了一条评论";
					//消息中心
					NoticeMsg noticeMsg=new NoticeMsg();
					noticeMsg.setActiontype(NoticeActionTypeValue.ANSWER.value);
					noticeMsg.setIcon("loudspeaker");
					noticeMsg.setMsgtype(NoticeMsgTypeValue.YIQUESTION.value);
					noticeMsg.setParam(yq.getId());
					noticeMsg.setContents(title);
					Set<User> usertag=new HashSet<User>();
					usertag.add(yq.getUser());
					noticeMsgService.saveOrUpdate(noticeMsg);

					//回复问题时，给提问人push
					JSONObject msg_extras = new JSONObject();
					msg_extras.put("type", NoticeMsgTypeValue.YIQUESTION.value);
					msg_extras.put("parameter", yq.getId());
					msg_extras.put("icon", "loudspeaker");
					msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
					pushService.push(qeustion.getContents(),title,msg_extras, Platform.android_ios(),
							new String[]{yq.getUser().getId()},
							new String[]{"version4","userstart0"});
				}
				Set<String> set=new HashSet<String>();
				set.add("id");
				set.add("createTime");
				set.add("user.id");
				set.add("user.nickname");
				set.add("user.minimg");
				set.add("contents");
				set.add("answer.id");
				set.add("answer.createTime");
				set.add("answer.user.id");
				set.add("answer.user.nickname");
				set.add("answer.user.minimg");
				set.add("answer.contents");
				jr.setCord(0);
				jr.setData(BeanUtil.getFieldValueMap(qeustion, set));
				jr.setMsg("发表成功");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				jr.setCord(1);
				jr.setMsg("发表失败");
			}
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提问列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param userid
	 * @author Lee.J.Eric
	 * @time 2014年12月18日 下午3:20:09
	 */
	@RequestMapping(value = "yiquestionlist")
	public void yiQuestionList(HttpServletResponse response,String type,Integer page,Integer size,String userid){
		Jr jr = new Jr();
		jr.setMethod("yiquestionlist");
		if(page==null||size==null|page<0||size<0){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Page<YiQuestion> list = yiQeustionService.findPageByUser(userid, page, size);
			Set<String> set=new HashSet<String>();
			set.add("id");
			set.add("createTime");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("contents");
			set.add("answer.id");
			set.add("answer.createTime");
			set.add("answer.user.id");
			set.add("answer.user.nickname");
			set.add("answer.user.minimg");
			set.add("answer.contents");

			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setTotal(list.getTotalElements());
//			jr.setData(list.getContent());
			jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
			jr.setCord(0);
			jr.setMsg("获取成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关于我的消息列表-消息中心
	 * @param response
	 * @param userid
	 * @param page
	 * @param size
	 * @author Lee.J.Eric
	 * @time 2014年12月19日 下午6:45:15
	 */
	@Deprecated
	@RequestMapping(value = "mymsglist")
	public void myMsgList(HttpServletResponse response,String userid,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("mymsglist");
		if(userid==null||page==null||size==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User user = userService.findById(userid);
			Page<NoticeMsg> list = noticeMsgService.findByUser(user, page, size);
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("msgtype");
			set.add("contents");
			set.add("createdate");
			set.add("actiontype");
			set.add("param");
			set.add("icon");

			jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
			jr.setPagenum(page + 1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
			jr.setMsg("获取成功");

		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 消息中心列表
	 * @param response
	 * @param page
	 * @param size
	 * @param userid
	 * @param datetime
	 * @param flag
	 */
	@Deprecated
	@RequestMapping(value = "noticemsglist")
	public void noticeMsgList(HttpServletResponse response,Integer page,Integer size,String userid,String datetime,String flag){
		Jr jr = new Jr();
		jr.setMethod("noticemsglist");
        if (page == null || size == null || userid == null || datetime == null || flag == null|| (!flag.equalsIgnoreCase("after")&& !flag.equalsIgnoreCase("before"))) {
            jr.setCord(-1);
            jr.setMsg("非法传值");
        } else {
			try {
           	 	User user = userService.findById(userid);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = format.parse(datetime);
				Page<NoticeMsg> list = noticeMsgService.findByUser(user,page,size,date, flag);
				Set<String> set = new HashSet<String>();
				set.add("id");
				set.add("msgtype");
				set.add("contents");
				set.add("createdate");
				set.add("actiontype");
				set.add("param");
				set.add("icon");

				jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
				jr.setPagenum(page + 1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setTotal(list.getTotalElements());
				jr.setCord(0);
				jr.setMsg("获取成功");
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 所有消息
	 * @param response
	 * @param page
	 * @param size
	 * @param userid
	 * @param type
	 * 2015-04-10
	 */
	@RequestMapping(value = "allnoticemsg")
	public void allNoticemsg(HttpServletResponse response,Integer page,Integer size,String userid,String type){
		Jr jr = new Jr();
		jr.setMethod("allnoticemsg");
        if (page == null || size == null || userid == null||userid.length()<1) {
            jr.setCord(-1);
            jr.setMsg("非法传值");
        } else {
			try {
           	 	User user = userService.findById(userid);
				Page<NoticeMsg> list = noticeMsgService.allNoticeMsg(user, page, size);
				Set<String> set = new HashSet<String>();
				set.add("id");
				set.add("msgtype");
				set.add("contents");
				set.add("createdate");
				set.add("actiontype");
				set.add("param");
				set.add("icon");

				jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
				jr.setPagenum(page + 1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setTotal(list.getTotalElements());
				jr.setCord(0);
				jr.setMsg("获取成功");
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
