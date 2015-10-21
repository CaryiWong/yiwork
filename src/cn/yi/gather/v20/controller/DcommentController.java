package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v11.service.ICommentServiceV2;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.CComment;
import cn.yi.gather.v20.entity.Comment;
import cn.yi.gather.v20.entity.Course;
import cn.yi.gather.v20.entity.Dcomment;
import cn.yi.gather.v20.entity.Dcomment.DCommentTypeValue;
import cn.yi.gather.v20.entity.Demands;
import cn.yi.gather.v20.entity.GComment;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.NoticeMsg;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeActionTypeValue;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeMsgTypeValue;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.service.ICCommentService;
import cn.yi.gather.v20.service.ICommentService;
import cn.yi.gather.v20.service.IDcommentService;
import cn.yi.gather.v20.service.IDemandsService;
import cn.yi.gather.v20.service.IGCommentService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.serviceImpl.GCommentService;
import cn.yi.gather.v20.service.serviceImpl.JPushService;
import cn.yi.gather.v20.service.serviceImpl.NoticeMsgService;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

@Controller("dcommentControllerV20")
@RequestMapping(value="v20/dcomment")
public class DcommentController {

	private static Logger log = Logger.getLogger(ActivityController.class);
	
	@Resource(name = "dcommentServiceV20")
	private IDcommentService dcommentService;
	
	@Resource(name = "jPushServiceV20")
	private JPushService pushService;
	
	@Resource(name="noticeMsgServiceV20")
	private NoticeMsgService noticeMsgService;
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "demandsServiceV20")
	private IDemandsService demandsService;
	
	@Resource(name = "commentServiceV20")
	private ICommentService commentService;
	
	@Resource(name="gCommentServiceV20")
	private IGCommentService gCommentService;
	
	@Resource(name="cCommentServiceV20")
	private ICCommentService cCommentService;
	
	/**
	 * 2.0 发表需求评论
	 * @param response
	 * @param comment
	 * @param type
	 * 2014-09-09
	 */
	@RequestMapping(value="createdcomment")
	public void createDcomment(HttpServletResponse response,Dcomment comment,String type){
		Jr jr = new Jr();
		jr.setMethod("createdcomment");
		Boolean flag=true;
		if(comment==null){
			jr.setCord(3);
			jr.setMsg("非法传参");
			flag=false;
		}
		if(comment.getDemands()==null){
			jr.setCord(1);
			jr.setMsg("需求为空");
			flag=false;
		}
		if(comment.getUser()==null){
			jr.setCord(2);
			jr.setMsg("用户为空");
			flag=false;
		}
		if(comment.getReceive()==null){
			jr.setCord(2);
			jr.setMsg("用户为空");
			flag=false;
		}
		if(flag){
			Dcomment dcomment = dcommentService.dcommentSaveOrUpdate(comment);
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("texts");
			set.add("mediaurl");
			set.add("mediatype");
			set.add("createdate");
			set.add("createdate1");
			set.add("pid");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("receive.id");
			set.add("receive.nickname");
			set.add("receive.minimg");
			jr.setData(BeanUtil.getFieldValueMap(dcomment,set));
			jr.setCord(0);
		
			//回复需求，push消息给发起者     
			Demands demands=demandsService.getDemandinfo(dcomment.getDemands().getId());
			User publ_user=userService.findById(demands.getPublishuser().getId());//发起人A
			User spek_user=userService.findById(dcomment.getUser().getId());// 评论人B
			User rece_user=userService.findById(dcomment.getReceive().getId());//评论接受人C
			
			Integer tz_falg=0;// 1 发起人A收评论通知    2 评论接受人C收回复通知   3 A C都收通知
			if(spek_user.getId().equals(rece_user.getId())){ //B@B
				if(!spek_user.getId().equals(publ_user.getId())){//B≠A
					//(A≠B&&B=C)
					tz_falg=1;
				}
			}else{//B@C
				if(spek_user.getId().equals(publ_user.getId())){//B=A
					// (A=B&&B≠C)
					tz_falg=2;
				}else{//B≠A
					if(rece_user.getId().equals(publ_user.getId())){//C=A
						//(A=C&&B≠C&&A≠B)
						tz_falg=1;
					}else{//C≠A
						// (A≠B≠C)
						tz_falg=3;
					}
				}
			}
			if(tz_falg==1||tz_falg==3){
				NoticeMsg noticeMsg=new NoticeMsg();
				noticeMsg.setActiontype(NoticeActionTypeValue.ANSWER.value);
				noticeMsg.setContents(spek_user.getNickname()+"在你的需求"+demands.getTitle()+"中发了一条评论");
				noticeMsg.setIcon("loudspeaker");
				noticeMsg.setMsgtype(NoticeMsgTypeValue.DEMANDS.value);
				noticeMsg.setParam(demands.getId());
				Set<User> usertag=new HashSet<User>();
				usertag.add(publ_user);//A
				noticeMsg.setTags(usertag);
				noticeMsgService.saveOrUpdate(noticeMsg);
				JSONObject msg_extras = new JSONObject();
				msg_extras.put("type", NoticeMsgTypeValue.DEMANDS.value);
				msg_extras.put("parameter", demands.getId());
				msg_extras.put("icon", "loudspeaker");
				msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
				pushService.push(spek_user.getNickname()+"在你的需求"+demands.getTitle()+"中发了一条评论",
						demands.getTitle(),msg_extras, Platform.android_ios(), 
						new String[]{publ_user.getId()},
						new String[]{"version4","userstart0"});
			}
            if(tz_falg==2||tz_falg==3){
				NoticeMsg noticeMsg=new NoticeMsg();
				noticeMsg.setActiontype(NoticeActionTypeValue.ANSWER.value);
				noticeMsg.setContents(spek_user.getNickname()+"在需求"+demands.getTitle()+"中回复了你");
				noticeMsg.setIcon("loudspeaker");
				noticeMsg.setMsgtype(NoticeMsgTypeValue.DEMANDS.value);
				noticeMsg.setParam(demands.getId());
				Set<User> usertag=new HashSet<User>();
				usertag.add(rece_user);//C
				noticeMsg.setTags(usertag);
				noticeMsgService.saveOrUpdate(noticeMsg);
				JSONObject msg_extras = new JSONObject();
				msg_extras.put("type", NoticeMsgTypeValue.DEMANDS.value);
				msg_extras.put("parameter", demands.getId());
				msg_extras.put("icon", "loudspeaker");
				msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
				pushService.push(spek_user.getNickname()+"在需求"+demands.getTitle()+"中回复了你",
						demands.getTitle(),msg_extras, Platform.android_ios(), 
						new String[]{rece_user.getId()},
						new String[]{"version4","userstart0"});
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
	 * 评论回复
	 * @param response
	 * @param replycomment
	 * @param type
	 */
	@RequestMapping(value="createreplydcomment")
	public void createReplydcomment(HttpServletResponse response,Dcomment replycomment,String type) {
		Jr jr=new Jr();
		jr.setMethod("createreplydcomment");
		Boolean flag=true;
		if(replycomment==null){
			jr.setCord(3);
			jr.setMsg("非法传参");
			flag=false;
		}
		if(replycomment.getPid().isEmpty()){
			jr.setCord(1);
			jr.setMsg("评论主体为空");
			flag=false;
		}
		Dcomment c=dcommentService.getDcommentByID(replycomment.getPid());
		if(c==null){
			jr.setCord(1);
			jr.setMsg("评论主体为空");
			flag=false;
		}else{
			replycomment.setDemands(c.getDemands());
		}
		if(replycomment.getUser()==null){
			jr.setCord(2);
			jr.setMsg("用户为空");
			flag=false;
		}
		if(replycomment.getReceive()==null){
			jr.setCord(2);
			jr.setMsg("用户为空");
			flag=false;
		}
		if(flag){
			replycomment = dcommentService.dcommentSaveOrUpdate(replycomment);
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("texts");
			set.add("mediaurl");
			set.add("mediatype");
			set.add("createdate");
			set.add("createdate1");
			set.add("pid");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("receive.id");
			set.add("receive.nickname");
			set.add("receive.minimg");
			set.add("recommend.id");
			set.add("recommend.nickname");
			set.add("recommend.minimg");
			jr.setData(BeanUtil.getFieldValueMap(replycomment,set));
			jr.setCord(0);
			//回复需求，push消息给发起者     
			Demands demands=demandsService.getDemandinfo(replycomment.getDemands().getId());
			User publ_user=userService.findById(demands.getPublishuser().getId());//发起人A
			User spek_user=userService.findById(replycomment.getUser().getId());// 评论人B
			User rece_user=userService.findById(replycomment.getReceive().getId());//评论接受人C
			
			Integer tz_falg=0;// 1 发起人A收评论通知    2 评论接受人C收回复通知   3 A C都收通知
			if(spek_user.getId().equals(rece_user.getId())){ //B@B
				if(!spek_user.getId().equals(publ_user.getId())){//B≠A
					//(A≠B&&B=C)
					tz_falg=1;
				}
			}else{//B@C
				if(spek_user.getId().equals(publ_user.getId())){//B=A
					// (A=B&&B≠C)
					tz_falg=2;
				}else{//B≠A
					if(rece_user.getId().equals(publ_user.getId())){//C=A
						//(A=C&&B≠C&&A≠B)
						tz_falg=1;
					}else{//C≠A
						// (A≠B≠C)
						tz_falg=3;
					}
				}
			}
			if(tz_falg==1||tz_falg==3){
				NoticeMsg noticeMsg=new NoticeMsg();
				noticeMsg.setActiontype(NoticeActionTypeValue.ANSWER.value);
				noticeMsg.setContents(spek_user.getNickname()+"在你的需求"+demands.getTitle()+"中发了一条评论");
				noticeMsg.setIcon("loudspeaker");
				noticeMsg.setMsgtype(NoticeMsgTypeValue.DEMANDS.value);
				noticeMsg.setParam(demands.getId());
				Set<User> usertag=new HashSet<User>();
				usertag.add(publ_user);//A
				noticeMsg.setTags(usertag);
				noticeMsgService.saveOrUpdate(noticeMsg);
				JSONObject msg_extras = new JSONObject();
				msg_extras.put("type", NoticeMsgTypeValue.DEMANDS.value);
				msg_extras.put("parameter", demands.getId());
				msg_extras.put("icon", "loudspeaker");
				msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
				pushService.push(spek_user.getNickname()+"在你的需求"+demands.getTitle()+"中发了一条评论",
						demands.getTitle(),msg_extras, Platform.android_ios(), 
						new String[]{replycomment.getDemands().getPublishuser().getId()},
						new String[]{"version4","userstart0"});
			}
            if(tz_falg==2||tz_falg==3){
				NoticeMsg noticeMsg=new NoticeMsg();
				noticeMsg.setActiontype(NoticeActionTypeValue.ANSWER.value);
				noticeMsg.setContents(spek_user.getNickname()+"在需求"+demands.getTitle()+"中回复了你");
				noticeMsg.setIcon("loudspeaker");
				noticeMsg.setMsgtype(NoticeMsgTypeValue.DEMANDS.value);
				noticeMsg.setParam(demands.getId());
				Set<User> usertag=new HashSet<User>();
				usertag.add(rece_user);//C
				noticeMsg.setTags(usertag);
				noticeMsgService.saveOrUpdate(noticeMsg);
				JSONObject msg_extras = new JSONObject();
				msg_extras.put("type", NoticeMsgTypeValue.DEMANDS.value);
				msg_extras.put("parameter", demands.getId());
				msg_extras.put("icon", "loudspeaker");
				msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
				pushService.push(spek_user.getNickname()+"在需求"+demands.getTitle()+"中回复了你",
						demands.getTitle(),msg_extras, Platform.android_ios(), 
						new String[]{replycomment.getReceive().getId()},
						new String[]{"version4","userstart0"});
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
	 * 2.0 获取评论列表
	 * @param response
	 * @param id  需求ID  
	 * @param orderby desc  asc
	 * @param page
	 * @param size
	 * @param type
	 * 2014-09-09 
	 * 2.1  update by ys 2015-04-07(返回参数新增 评论类型  被推荐人)
	 */
	@RequestMapping(value="finddcommentlist")
	public void finddCommentList(HttpServletResponse response,String id,String uid,String orderby,Integer page,Integer size,String type) {
		Jr jr=new Jr();
		jr.setMethod("finddcommentlist");
		if(id.isEmpty()){
			jr.setCord(1);
			jr.setMsg("非法传参");
		}else{
			Page<Dcomment> list=dcommentService.dcommentFind(page, size, orderby, uid, id);
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("texts");
			set.add("mediaurl");
			set.add("mediatype");
			set.add("createdate");
			set.add("createdate1");
			set.add("pid");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("receive.id");
			set.add("receive.nickname");
			set.add("receive.minimg");
			set.add("recommend.id");
			set.add("recommend.nickname");
			set.add("recommend.minimg");
			set.add("recommend.sex");
			set.add("recommend.job");
			set.add("commenttype");
			set.add("praisesnum");
			set.add("praises.id");
			set.add("praises.nickname");
			set.add("praises.minimg");
			set.add("praises.sex");
			List<Dcomment> cms=list.getContent();
			List<Map<String,Object>> li= BeanUtil.getFieldValueMapForList(cms, set);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(li);
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除/撤销评论
	 * @param response
	 * @param commentid   评论ID
	 * @param uid         用户ID
	 * @param commenttype 评论类型(demands 需求的评论    activity 活动的评论  gathering 小活动评论  course 课程的评论)
	 * @param type
	 */
	@RequestMapping(value="delcomment")
	public void delComment(HttpServletResponse response,String commentid,String uid,String commenttype,String type) {
		Jr jr=new Jr();
		jr.setMethod("delcomment");
		if(commentid==null||uid==null||commenttype==null||commentid.length()<1||uid.length()<1||commenttype.length()<1){
			jr.setCord(1);
			jr.setMsg("非法传参");
		}else{
			if(commenttype.equals(Activity.entityName)){
				Comment comment=commentService.getCommentByID(commentid);
				if(comment==null){
					jr.setCord(1);
					jr.setMsg("评论不存在");
				}else{
					if(uid.equals(comment.getUser().getId())){
						comment.setIsdel(1);
						commentService.commentSaveOrUpdate(comment);
						jr.setCord(0);
						jr.setMsg("删除成功");
					}else{
						jr.setCord(1);
						jr.setMsg("无权限");
					}
				}
			}else if(commenttype.equals(Demands.entityName)){
				Dcomment comment=dcommentService.getDcommentByID(commentid);
				if(comment==null){
					jr.setCord(1);
					jr.setMsg("评论不存在");
				}else{
					if(uid.equals(comment.getUser().getId())){
						comment.setIsdel(1);
						dcommentService.dcommentSaveOrUpdate(comment);
						jr.setCord(0);
						jr.setMsg("删除成功");
					}else{
						jr.setCord(1);
						jr.setMsg("无权限");
					}
				}
			}else if(commenttype.equals(Gathering.entityName)){
				GComment comment=gCommentService.findGComment(commentid);
				if(comment==null){
					jr.setCord(1);
					jr.setMsg("评论不存在");
				}else{
					if(uid.equals(comment.getUser().getId())){
						comment.setIsdel(1);
						gCommentService.gCommentSaveOrUpdate(comment);
						jr.setCord(0);
						
						jr.setMsg("删除成功");
					}else{
						jr.setCord(1);
						jr.setMsg("无权限");
					}
				}
			}else if(commenttype.equals(Course.entityName)){
				CComment comment=cCommentService.findCComment(commentid);
				if(comment==null){
					jr.setCord(1);
					jr.setMsg("评论不存在");
				}else{
					if(uid.equals(comment.getUser().getId())){
						comment.setIsdel(1);
						cCommentService.cCommentSaveOrUpdate(comment);
						jr.setCord(0);
						jr.setMsg("删除成功");
					}else{
						jr.setCord(1);
						jr.setMsg("无权限");
					}
				}
			}else{
				//是否还有别的评论？
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
	 * 推荐会员给需求
	 * @param response
	 * @param type
	 * @param dcomment  推荐类型的评论主体
	 * @author ys 2015-04-07
	 */
	@RequestMapping(value="recommenduserfordemand")
	public void recommendUserForDemand(HttpServletResponse response,String type,Dcomment dcomment) {
		Jr jr = new Jr();
		jr.setMethod("recommenduserfordemand");
		if(dcomment.getDemands()!=null&&dcomment.getDemands().getId()!=null
				&&dcomment.getUser()!=null&&dcomment.getUser().getId()!=null
				&&dcomment.getRecommend()!=null&&dcomment.getRecommend().getId()!=null){
			if(DCommentTypeValue.RECOMMEND.value.equals(dcomment.getCommenttype())){
				Demands demands=demandsService.getDemandinfo(dcomment.getDemands().getId());
				User publishuser=userService.findById(demands.getPublishuser().getId());
				User user=userService.findById(dcomment.getUser().getId());
				User reuser=userService.findById(dcomment.getRecommend().getId());
				if(demands!=null&&user!=null&&reuser!=null){
					if(user.getId().equals(demands.getPublishuser().getId())||reuser.getId().equals(demands.getPublishuser().getId())){
						jr.setCord(2);
						jr.setMsg("需求发起者不能被推荐也不能推荐");
					}else{
						dcomment=dcommentService.dcommentSaveOrUpdate(dcomment);
						jr.setData(dcomment);
						jr.setCord(0);
						jr.setMsg("推荐成功");
						//需要通知 被推荐人收到通知
						NoticeMsg noticeMsg=new NoticeMsg();
						noticeMsg.setActiontype(NoticeActionTypeValue.ANSWER.value);
						noticeMsg.setContents(user.getNickname()+"在需求"+demands.getTitle()+"中推荐了你");
						noticeMsg.setIcon("loudspeaker");
						noticeMsg.setMsgtype(NoticeMsgTypeValue.DEMANDS.value);
						noticeMsg.setParam(demands.getId());
						Set<User> usertag=new HashSet<User>();
						usertag.add(reuser);
						noticeMsg.setTags(usertag);
						noticeMsgService.saveOrUpdate(noticeMsg);
						JSONObject msg_extras = new JSONObject();
						msg_extras.put("type", NoticeMsgTypeValue.DEMANDS.value);
						msg_extras.put("parameter", demands.getId());
						msg_extras.put("icon", "loudspeaker");
						msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
						pushService.push(user.getNickname()+"在需求"+demands.getTitle()+"中推荐了你",
								demands.getTitle(),msg_extras, Platform.android_ios(), 
								new String[]{reuser.getId()},
								new String[]{"version4","userstart0"});
						
						//需要通知 需求发起者也收到通知
						noticeMsg=new NoticeMsg();
						noticeMsg.setActiontype(NoticeActionTypeValue.ANSWER.value);
						noticeMsg.setContents(user.getNickname()+"在需求"+demands.getTitle()+"中向你推荐了"+reuser.getNickname());
						noticeMsg.setIcon("loudspeaker");
						noticeMsg.setMsgtype(NoticeMsgTypeValue.DEMANDS.value);
						noticeMsg.setParam(demands.getId());
						usertag=new HashSet<User>();
						usertag.add(publishuser);
						noticeMsg.setTags(usertag);
						noticeMsgService.saveOrUpdate(noticeMsg);
						msg_extras = new JSONObject();
						msg_extras.put("type", NoticeMsgTypeValue.DEMANDS.value);
						msg_extras.put("parameter", demands.getId());
						msg_extras.put("icon", "loudspeaker");
						msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
						pushService.push(user.getNickname()+"在需求"+demands.getTitle()+"中向你推荐了"+reuser.getNickname(),
								demands.getTitle(),msg_extras, Platform.android_ios(), 
								new String[]{publishuser.getId()},
								new String[]{"version4","userstart0"});
					}
				}else{
					jr.setCord(1);
					jr.setMsg("用户或需求不存在");
				}
			}else{
				jr.setCord(1);
				jr.setMsg("不是推荐");
			}
		}else{
			jr.setCord(-1);
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
	 * 需求评论点赞
	 * @param response
	 * @param type
	 * @param dcommentid  评论ID
	 * @param userid     操作用户
	 * @author ys  2015-04-07
	 */
	@RequestMapping(value="praisedcomment")
	public void praiseDcomment(HttpServletResponse response,String type,String dcommentid,String userid) {
		Jr jr = new Jr();
		jr.setMethod("praisedcomment");
		if(dcommentid!=null&&dcommentid.length()>0&&userid!=null&&userid.length()>0){
			Dcomment demands=dcommentService.getDcommentByID(dcommentid);
			User user=userService.findById(userid);
			if(demands!=null&&user!=null){
				Set<User> set=demands.getPraises();
				if(set==null)
					set=new HashSet<User>();
				if(!set.contains(user)){
					set.add(user);
					demands.setPraises(set);
					demands=dcommentService.dcommentSaveOrUpdate(demands);
					jr.setCord(0);
					jr.setMsg("点赞成功");
					jr.setData(set.size());
				}else{
					jr.setCord(1);
					jr.setMsg("重复点赞");
				}
			}else{
				jr.setCord(1);
				jr.setMsg("用户或评论不存在");
			}
		}else{
			jr.setCord(-1);
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
	 * 需求评论取消点赞
	 * @param response
	 * @param type
	 * @param dcommentid  评论ID
	 * @param userid     操作用户
	 * @author ys  2015-04-24
	 */
	@RequestMapping(value="cancelpraisedcomment")
	public void cancelPraiseDcomment(HttpServletResponse response,String type,String dcommentid,String userid) {
		Jr jr = new Jr();
		jr.setMethod("cancelpraisedcomment");
		if(dcommentid!=null&&dcommentid.length()>0&&userid!=null&&userid.length()>0){
			Dcomment demands=dcommentService.getDcommentByID(dcommentid);
			User user=userService.findById(userid);
			if(demands!=null&&user!=null){
				Set<User> set=demands.getPraises();
				if(set==null)
					set=new HashSet<User>();
				if(set.contains(user)){
					set.remove(user);
					demands.setPraises(set);
					demands=dcommentService.dcommentSaveOrUpdate(demands);
					jr.setCord(0);
					jr.setMsg("取消成功");
					jr.setData(set.size());
				}else{
					jr.setCord(0);
					jr.setMsg("取消成功");
				}
			}else{
				jr.setCord(1);
				jr.setMsg("用户或评论不存在");
			}
		}else{
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
