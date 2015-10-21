package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.entity.Demands;
import cn.yi.gather.v20.entity.Eventlog;
import cn.yi.gather.v20.entity.Invitations;
import cn.yi.gather.v20.entity.Invitecomment;
import cn.yi.gather.v20.entity.NoticeMsg;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeActionTypeValue;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeMsgTypeValue;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.UserFocus;
import cn.yi.gather.v20.service.IEventlogService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.INoticeMsgService;
import cn.yi.gather.v20.service.IUserFocusService;
import cn.yi.gather.v20.service.IinvitationsService;
import cn.yi.gather.v20.service.IinvitecommentService;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

@Controller("invitationsControllerV20")
@RequestMapping(value="v20/invitations")
public class InvitationsController {

	@Resource(name = "invitationsServiceV20")
	private IinvitationsService invitationsService;
	
	@Resource(name = "invitecommentServiceV20")
	private IinvitecommentService invitecommentService;
	
	@Resource(name="userFocusServiceV20")
	private IUserFocusService userFocusService;
	
	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;
	
	@Resource(name = "noticeMsgServiceV20")
	private INoticeMsgService noticeMsgService;
	
	@Resource(name = "eventlogServiceV20")
	private IEventlogService eventlogService;
	
	/**
	 * 2.0 发送邀请函
	 * @param response
	 * @param invitations
	 * @param type
	 * 2014-09-22
	 */
	@RequestMapping(value = "createinvitations")
	public void createinvitations(HttpServletResponse response,Invitations invitations,String type,String opendate) {
		Jr jr = new Jr();
		jr.setMethod("createinvitations");
		try {
			if(invitations.getUser()==null||invitations.getInviteuser()==null||invitations.getTitle()==null||invitations.getTitle().length()<1){
				jr.setCord(-1);
				jr.setMsg("非法传参");
			}else if(invitations.getUser().getId().equals(invitations.getInviteuser().getId())){
				jr.setCord(-2);
				jr.setMsg("二货自己邀请自己");
			}else{
				
				if(opendate!=null){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date open = format.parse(opendate);
					invitations.setOpentime(open);
					invitations.setIfhavetime("Y");
				}else{
					invitations.setIfhavetime("N");
				}
				invitations=invitationsService.saveOrUpdateInvitations(invitations);
				if(invitations==null){
					jr.setCord(-1);
					jr.setMsg("创建失败");
				}else{
					// 需要给接收人发送通知
					
					//push通知
					JSONObject msg_extras = new JSONObject();
					msg_extras.put("type", NoticeMsgTypeValue.INVITE.value);
					msg_extras.put("parameter", invitations.getId());
					msg_extras.put("icon", "letter");
					msg_extras.put("action", NoticeActionTypeValue.INVITE.value);
					pushService .push(invitations.getUser().getNickname() + "给你一封邀请函",
									invitations.getTitle(),msg_extras, Platform .android_ios(),
									new String[] { invitations.getInviteuser() .getId() },
									new String[] { "version4","userstart0" });
					
					//消息入库
					Set<User> userSet = new HashSet<User>();
					userSet.add(invitations.getInviteuser());
					NoticeMsg msg = new NoticeMsg();
					msg.setActiontype(NoticeActionTypeValue.INVITE.value);
					msg.setContents(invitations .getUser().getNickname() + "给你一封邀请函");
					msg.setMsgtype(NoticeMsgTypeValue.INVITE.value);
					msg.setParam(invitations.getId());
					msg.setTags(userSet);
					msg.setIcon("letter");
					msg = noticeMsgService.saveOrUpdate(msg);
					
					//往交情表添加一条记录
					UserFocus focus=new UserFocus();
					focus.setMe(invitations.getUser());
					focus.setWho(invitations.getInviteuser());
					focus.setRelation(1);
					userFocusService.saveOrUpdate(focus);
					
					Set<String> set=new HashSet<String>();
					set.add("id");
					set.add("title");
					set.add("summary");
					set.add("thingstexts");
					set.add("opentime");
					set.add("ifhavetime");
					set.add("address");
					set.add("coffee");
					set.add("cnum");
					set.add("answer");
					set.add("user.id");
					set.add("user.nickname");
					set.add("user.minimg");
					set.add("user.job");
					set.add("user.sex");
					set.add("inviteuser.id");
					set.add("inviteuser.nickname");
					set.add("inviteuser.minimg");
					set.add("inviteuser.job");
					set.add("inviteuser.sex");
					jr.setData(BeanUtil.getFieldValueMap(invitations, set));
					jr.setCord(0);
					jr.setMsg("创建成功");	
				}
			}
		} catch (Exception e) {
			jr.setCord(1);
			jr.setMsg("异常");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2.0 获取邀请函详情
	 * @param response
	 * @param id 邀请函ID
	 * @param type
	 * 2014-09-22
	 */
	@RequestMapping(value = "findinvitations")
	public void findinvitations(HttpServletResponse response,String id,String type) {
		Jr jr = new Jr();
		jr.setMethod("findinvitations");
		try {
			Set<String> set=new HashSet<String>();
			set.add("id");
			set.add("title");
			set.add("summary");
			set.add("thingstexts");
			set.add("opentime");
			set.add("ifhavetime");
			set.add("address");
			set.add("coffee");
			set.add("cnum");
			set.add("answer");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("user.job");
			set.add("user.sex");
			set.add("inviteuser.id");
			set.add("inviteuser.nickname");
			set.add("inviteuser.minimg");
			set.add("inviteuser.job");
			set.add("inviteuser.sex");
			jr.setData(BeanUtil.getFieldValueMap(invitationsService.findInvitations(id),set));
			
			Set<String> set2=new HashSet<String>();
			set2.add("id");
			set2.add("createtime");
			set2.add("texts");
			set2.add("invitations.id");
			set2.add("user.id");
			set2.add("user.nickname");
			set2.add("user.minimg");
			set2.add("user.job");
			set2.add("user.sex");
			set2.add("user.userstart");
			set2.add("acceptuser.id");
			set2.add("acceptuser.nickname");
			set2.add("acceptuser.minimg");
			set2.add("acceptuser.job");
			set2.add("acceptuser.userstart");
			set2.add("acceptuser.sex");
			jr.setData2(BeanUtil.getFieldValueMapForList(invitecommentService.invitecommentlist(id), set2));
			jr.setCord(0);
			jr.setMsg("获取成功");	
		} catch (Exception e) {
			jr.setCord(1);
			jr.setMsg("异常");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2.0 查看2人的邀请函列表
	 * @param response
	 * @param page
	 * @param size
	 * @param uid    登录者ID
	 * @param iuid   对方ID
	 * @param orderby desc/asc 安装创建时间排序
	 * @param type
	 * 2014-09-22
	 */
	@RequestMapping(value = "invitationslist")
	public void invitationslist(HttpServletResponse response,Integer page,Integer size,String uid,String iuid,String orderby,String type) {
		Jr jr = new Jr();
		jr.setMethod("invitationslist");
		try {
			Page<Invitations> list=invitationsService.invitationsList(page, size, uid, iuid, orderby);
			Set<String> set=new HashSet<String>();
			set.add("id");
			set.add("title");
			set.add("summary");
			set.add("thingstexts");
			set.add("opentime");
			set.add("ifhavetime");
			set.add("address");
			set.add("coffee");
			set.add("cnum");
			set.add("answer");
			set.add("itype");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("user.job");
			set.add("user.sex");
			set.add("user.userstart");
			set.add("inviteuser.id");
			set.add("inviteuser.nickname");
			set.add("inviteuser.minimg");
			set.add("inviteuser.job");
			set.add("inviteuser.userstart");
			set.add("inviteuser.sex");
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setTotal(list.getTotalElements());
			jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
			jr.setCord(0);
			jr.setMsg("获取成功");	
		} catch (Exception e) {
			jr.setCord(1);
			jr.setMsg("异常");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2.0 查看我的邀请涵
	 * @param response
	 * @param page
	 * @param size
	 * @param uid    登录者ID
	 * @param iuid   对方ID
	 * @param orderby desc/asc 安装创建时间排序
	 * @param type
	 * 2014-09-22
	 */
	@RequestMapping(value = "invitationsmylist")
	public void invitationsmylist(HttpServletResponse response,Integer page,Integer size,String uid,String orderby,String type) {
		Jr jr = new Jr();
		jr.setMethod("invitationsmylist");
		try {
			Page<Invitations> list=invitationsService.invitationsMyList(page, size, uid, orderby);
			Set<String> set=new HashSet<String>();
			set.add("id");
			set.add("title");
			set.add("summary");
			set.add("thingstexts");
			set.add("opentime");
			set.add("ifhavetime");
			set.add("address");
			set.add("coffee");
			set.add("cnum");
			set.add("answer");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("user.job");
			set.add("user.userstart");
			set.add("user.sex");
			set.add("inviteuser.id");
			set.add("inviteuser.nickname");
			set.add("inviteuser.minimg");
			set.add("inviteuser.job");
			set.add("inviteuser.userstart");
			set.add("inviteuser.sex");
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setTotal(list.getTotalElements());
			jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
			jr.setCord(0);
			jr.setMsg("获取成功");	
		} catch (Exception e) {
			jr.setCord(1);
			jr.setMsg("异常");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}  
	
	/**
	 * 2.0 先聊一聊
	 * @param response
	 * @param invitecomment
	 * @param type
	 */
	@RequestMapping(value = "firstspeak")
	public void firstSpeak(HttpServletResponse response,String in_id,String u_id, String type){
		Jr jr = new Jr();
		jr.setMethod("firstspeak");
		Boolean falg=true;
		if(in_id==null||in_id.length()<1||u_id==null||u_id.length()<1){
			jr.setCord(1);
			jr.setMsg("非法传参");
			falg=false;
		}
		if(falg){
			try {
				Invitations invitations=invitationsService.findInvitations(in_id);
				if(invitations!=null){
					if("N".equals(invitations.getAnswer())){
						if(u_id.equals(invitations.getInviteuser().getId())){
							invitations.setAnswer("S");
							invitationsService.saveOrUpdateInvitations(invitations);
							jr.setCord(0);
							jr.setMsg("开启成功");
						}else{
							jr.setCord(3);
							jr.setMsg("无操作权限");
						}
					}else{
						jr.setCord(4);
						jr.setMsg("邀请函已被接受");
					}
				}else{
					jr.setCord(2);
					jr.setMsg("邀请函不存在");
				}
			} catch (Exception e) {
				e.printStackTrace();
				jr.setCord(-1);
				jr.setMsg("服务器异常");
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
	 * 2.0 开启邀请函之间的对话
	 * @param response
	 * @param invitecomment
	 * @param type
	 * 2014-09-22
	 */
	@RequestMapping(value = "createinvitecomment")
	public void createinvitecomment(HttpServletResponse response,Invitecomment invitecomment,String type) {
		Jr jr = new Jr();
		jr.setMethod("createinvitecomment");
		try {
			if(invitecomment.getAcceptuser()==null||invitecomment.getUser()==null||invitecomment.getInvitations()==null){
				jr.setCord(-1);
				jr.setMsg("非法传参");
			}else{
				invitecomment=invitecommentService.saveOrUpdateInvitecomment(invitecomment);
				if(invitecomment != null){
					//对话数+1
					Invitations invitations=invitationsService.findInvitations(invitecomment.getInvitations().getId());
					invitations.setCnum(invitations.getCnum()+1);
					invitationsService.saveOrUpdateInvitations(invitations);
					
					//需要发送通知
					//push通知
					JSONObject msg_extras = new JSONObject();
					msg_extras.put("type", NoticeMsgTypeValue.INVITE.value);
					msg_extras.put("parameter", invitations.getId());
					msg_extras.put("icon", "letter");
					msg_extras.put("action", NoticeActionTypeValue.SPEAK.value);
					pushService .push(invitecomment.getUser().getNickname() + "说",
							invitecomment.getTexts(),msg_extras, Platform .android_ios(),
									new String[] { invitecomment.getAcceptuser().getId()},
									new String[] { "version4"});
					
					NoticeMsg noticeMsg=new NoticeMsg();
					noticeMsg.setActiontype(NoticeActionTypeValue.SPEAK.value);
					noticeMsg.setContents(invitecomment.getUser().getNickname()+"说"+invitecomment.getTexts());
					noticeMsg.setIcon("letter");
					noticeMsg.setMsgtype(NoticeMsgTypeValue.INVITE.value);
					noticeMsg.setParam(invitations.getId());
					Set<User> usertag=new HashSet<User>();
					usertag.add(invitecomment.getAcceptuser());//A
					noticeMsg.setTags(usertag);
					noticeMsgService.saveOrUpdate(noticeMsg);
					noticeMsgService.updateReadForInvit(invitecomment.getAcceptuser(), invitations.getId(), noticeMsg.getId());
					
					Set<String> set=new HashSet<String>();
					set.add("id");
					set.add("createtime");
					set.add("texts");
					set.add("invitations.id");
					set.add("user.id");
					set.add("user.nickname");
					set.add("user.minimg");
					set.add("user.job");
					set.add("user.sex");
					set.add("user.userstart");
					set.add("acceptuser.id");
					set.add("acceptuser.nickname");
					set.add("acceptuser.minimg");
					set.add("acceptuser.job");
					set.add("acceptuser.userstart");
					set.add("acceptuser.sex");
					jr.setData(BeanUtil.getFieldValueMap(invitecomment, set));
					//?? 是否需要返回新的列表
					Page<Invitecomment> list=invitecommentService.invitecommentlist(0, 100, invitecomment.getInvitations().getId(), "desc");
					jr.setData2(BeanUtil.getFieldValueMapForList(list.getContent(), set));
					jr.setCord(0);
					jr.setMsg("成功");
				}else{
					jr.setCord(1);
					jr.setMsg("失败");
				}
			}
			
		} catch (Exception e) {
			jr.setCord(1);
			jr.setMsg("异常");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 2.0 获取一个邀请函 之间2个人的对话列表
	 * @param response
	 * @param page
	 * @param size
	 * @param id 邀请函ID
	 * @param orderby desc/asc 排序
	 * @param type
	 */
	@RequestMapping(value = "invitecommentlist")
	public void invitecommentlist(HttpServletResponse response,Integer page,Integer size, String id,String orderby, String type){
		Jr jr = new Jr();
		jr.setMethod("invitecommentlist");
		try {
			if(id!=null){
				Page<Invitecomment> list=invitecommentService.invitecommentlist(page, size, id, orderby);
				Set<String> set=new HashSet<String>();
				set.add("id");
				set.add("createtime");
				set.add("texts");
				set.add("invitations.id");
				set.add("user.id");
				set.add("user.nickname");
				set.add("user.minimg");
				set.add("user.job");
				set.add("user.sex");
				set.add("user.userstart");
				set.add("acceptuser.id");
				set.add("acceptuser.nickname");
				set.add("acceptuser.minimg");
				set.add("acceptuser.job");
				set.add("acceptuser.userstart");
				set.add("acceptuser.sex");
				jr.setPagenum(page+1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setTotal(list.getTotalElements());
				jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
				jr.setCord(0);
				jr.setMsg("获取成功");	
			}else{
				jr.setCord(-1);
				jr.setMsg("非法传参");
			}
		} catch (Exception e) {
			jr.setCord(1);
			jr.setMsg("异常");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 2.0 完成邀请函  决定见面
	 * @param response
	 * @param type
	 * @param id 邀请函ID
	 * @param address 有重新设定地点就传值 不然就不传
	 * @param datetime有重新约定时间就传值 不然就不传
	 * @param coffee   Y 有请咖啡 
	 */
	@RequestMapping(value = "okinvitations")
	public void okinvitations(HttpServletResponse response, String type,String id,String address,String datetime,String coffee){
		Jr jr = new Jr();
		jr.setMethod("okinvitations");
		try {
			if(id!=null){
				Invitations invitations=invitationsService.findInvitations(id);
				if((invitations!=null&&invitations.getAnswer().equalsIgnoreCase("N"))||(invitations!=null&&invitations.getAnswer().equalsIgnoreCase("S"))){
					if(address!=null&&address.length()>0){
						invitations.setAddress(address);//是否重新修改地点
					}
					if(datetime!=null){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date open = format.parse(datetime);
						invitations.setOpentime(open);
						invitations.setIfhavetime("Y");
					}
					invitations.setAnswer("Y");//结束邀请
					if(coffee!=null&&coffee.equals("Y")){
						invitations.setCoffee("Y");
					}
					invitations=invitationsService.saveOrUpdateInvitations(invitations);
					//往交情表添加一条记录
//					UserFocus focus=new UserFocus();
//					focus.setMe(invitations.getUser());
//					focus.setWho(invitations.getInviteuser());
//					focus.setRelation(1);
//					userFocusService.saveOrUpdate(focus);
					
					//动态记录表
					Eventlog eventlog = new Eventlog();
					eventlog.setInvitation(invitations);
					eventlog.setActtype("invite");
					eventlog.setEventtype("invite");
					eventlog.setUser(invitations.getUser());//发送者
//					eventlog.setOpentime(invitations.getOpentime());
					eventlog.setUpdatetime(invitations.getOpentime());
					eventlogService.eventlogSaveOrUpdate(eventlog);
					
					eventlog = new Eventlog();
					eventlog.setInvitation(invitations);
					eventlog.setActtype("invite");
					eventlog.setEventtype("invite");
//					eventlog.setOpentime(invitations.getOpentime());
					eventlog.setUpdatetime(invitations.getOpentime());
					eventlog.setUser(invitations.getInviteuser());//接收者
					eventlogService.eventlogSaveOrUpdate(eventlog);
					
					//push通知
					JSONObject msg_extras = new JSONObject();
					msg_extras.put("type", "invite");
					msg_extras.put("parameter", invitations.getId());
					msg_extras.put("icon", "letter");
					msg_extras.put("action", "end");
					pushService .push(invitations.getTitle() + "邀约已成功，请及时参加哦",
									invitations.getTitle(),msg_extras, Platform .android_ios(),
									new String[] { invitations.getInviteuser() .getId(),invitations.getUser().getId() },
									new String[] { "version4","userstart0" });
					
					Set<String> set=new HashSet<String>();
					set.add("id");
					set.add("title");
					set.add("summary");
					set.add("thingstexts");
					set.add("opentime");
					set.add("ifhavetime");
					set.add("address");
					set.add("coffee");
					set.add("cnum");
					set.add("answer");
					set.add("user.id");
					set.add("user.nickname");
					set.add("user.minimg");
					set.add("user.job");
					set.add("user.sex");
					set.add("inviteuser.id");
					set.add("inviteuser.nickname");
					set.add("inviteuser.minimg");
					set.add("inviteuser.job");
					set.add("inviteuser.sex");
					jr.setData(BeanUtil.getFieldValueMap(invitations, set));
					jr.setCord(0);
					jr.setMsg("获取成功");	
				}else{
					jr.setCord(1);
					jr.setMsg("邀请函不存在");	
				}
			}else{
				jr.setCord(-1);
				jr.setMsg("非法传参");
			}
		} catch (Exception e) {
			jr.setCord(1);
			jr.setMsg("异常");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
