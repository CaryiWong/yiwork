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

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.Comment;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.NoticeMsg;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeMsgTypeValue;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeActionTypeValue;
import cn.yi.gather.v20.permission.entity.AdminUser;
import cn.yi.gather.v20.service.IActivityService;
import cn.yi.gather.v20.service.ICommentService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.INoticeMsgService;
import cn.yi.gather.v20.service.IUserService;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

@Controller("commentControllerV20")
@RequestMapping(value="v20/comment")
public class CommentController {

	private static Logger log = Logger.getLogger(ActivityController.class);
	
	@Resource(name = "commentServiceV20")
	private ICommentService commentService;
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "activityServiceV20")
	private IActivityService activityService;
	
	@Resource(name = "noticeMsgServiceV20")
	private INoticeMsgService noticeMsgService;
	
	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;
	
	/**
	 * 发表评论
	 * @param response
	 * @param comment
	 * @param type
	 * 2014.12.10  YS
	 */
	@RequestMapping(value="createcomment")
	public void createComment(HttpServletResponse response,Comment comment,String type){
		Jr jr = new Jr();
		jr.setMethod("createcomment");
		Boolean falge=true;
		if(comment==null){
			jr.setCord(3);
			jr.setMsg("非法传参");
			falge=false;
		}
		if(comment.getActivity()==null){
			jr.setCord(1);
			jr.setMsg("活动为空");
			falge=false;
		}
		if(comment.getUser()==null){
			jr.setCord(2);
			jr.setMsg("用户为空");
			falge=false;
		}
		if(comment.getReceive()==null){
			jr.setCord(2);
			jr.setMsg("接受者为空");
			falge=false;
		}
		if(falge){
			comment=commentService.commentSaveOrUpdate(comment);
			User user = userService.findById(comment.getUser().getId());
			User receiver = userService.findById(comment.getReceive().getId());
			Activity activity = activityService.findById(comment.getActivity().getId());
			
			String pubu_contents = user.getNickname()+"在你的活动\""+activity.getTitle()+"\"中发了一条评论";//发起人消息
			String rece_contents = user.getNickname()+"在活动\""+activity.getTitle()+"\"中回复了你";//接收人消息
			
			Boolean publishuser_flag = false;//发起方标志
			Boolean receiver_flag = false;//接收方标志
			
			if(comment.getUser().getId().equals(comment.getReceive().getId())){//B@B
				if(!comment.getUser().equals(activity.getUser().getId())){//B!=A
					publishuser_flag = true;
				}
			}else {//B@C
				if(comment.getUser().getId().equals(activity.getUser().getId())){//B=A
					receiver_flag = true;
				}else {
					publishuser_flag = true;
					if(!comment.getReceive().getId().equals(activity.getUser().getId())){//C!=A
						receiver_flag = true;
					}
				}
			}
			
			if(publishuser_flag){
				NoticeMsg noticeMsg=new NoticeMsg();
				noticeMsg.setActiontype(NoticeActionTypeValue.ANSWER.value);
				noticeMsg.setIcon("loudspeaker");
				noticeMsg.setMsgtype(NoticeMsgTypeValue.ACTIVITY.value);
				noticeMsg.setParam(activity.getId());
				noticeMsg.setContents(pubu_contents);
				Set<AdminUser> usertag=new HashSet<AdminUser>(); //有修改@kcm
				usertag.add(activity.getUser());
				noticeMsgService.saveOrUpdate(noticeMsg);
				//push
				
				JSONObject msg_extras = new JSONObject();
				msg_extras.put("type", NoticeMsgTypeValue.ACTIVITY.value);
				msg_extras.put("parameter", activity.getId());
				msg_extras.put("icon", "loudspeaker");
				msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
				pushService.push(pubu_contents,activity.getTitle(),msg_extras, Platform.android_ios(), 
						new String[]{activity.getUser().getId()},
						new String[]{"version4","userstart0"});
			}
			if(receiver_flag){
				NoticeMsg noticeMsg=new NoticeMsg();
				noticeMsg.setActiontype(NoticeActionTypeValue.ANSWER.value);
				noticeMsg.setIcon("loudspeaker");
				noticeMsg.setMsgtype(NoticeMsgTypeValue.ACTIVITY.value);
				noticeMsg.setParam(activity.getId());
				noticeMsg.setContents(rece_contents);
				Set<User> usertag=new HashSet<User>();
				usertag.add(receiver);
				noticeMsgService.saveOrUpdate(noticeMsg);
				//push
				JSONObject msg_extras = new JSONObject();
				msg_extras.put("type", NoticeMsgTypeValue.ACTIVITY.value);
				msg_extras.put("parameter", activity.getId());
				msg_extras.put("icon", "loudspeaker");
				msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
				pushService.push(rece_contents,activity.getTitle(),msg_extras, Platform.android_ios(), 
						new String[]{receiver.getId()},
						new String[]{"version4","userstart0"});
			}
			
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("texts");
			set.add("createdate");
			set.add("pid");
			set.add("activity.id");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("receive.id");
			set.add("receive.nickname");
			set.add("receive.minimg");
			jr.setData(BeanUtil.getFieldValueMap(comment,set));
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
	 * 评论回复
	 * @param response
	 * @param replycomment
	 * @param type
	 * 回复直接调用发表评论  2014.12.10
	 */
	@Deprecated
	@RequestMapping(value="createreplycomment")
	public void createReplycomment(HttpServletResponse response,Comment replycomment,String type) {
		Jr jr=new Jr();
		jr.setMethod("createreplycomment");
		Boolean falge=true;
		if(replycomment==null){
			jr.setCord(3);
			jr.setMsg("非法传参");
			falge=false;
		}
		if(replycomment.getPid().isEmpty()){
			jr.setCord(1);
			jr.setMsg("评论主体为空");
			falge=false;
		}
		Comment c=commentService.getCommentByID(replycomment.getPid());
		if(c==null){
			jr.setCord(1);
			jr.setMsg("评论主体为空");
			falge=false;
		}else{
			replycomment.setActivity(c.getActivity());
		}
		if(replycomment.getUser()==null){
			jr.setCord(2);
			jr.setMsg("用户为空");
			falge=false;
		}
		if(replycomment.getReceive()==null){
			jr.setCord(2);
			jr.setMsg("接收者为空");
			falge=false;
		}
		if(falge){
			jr.setData(commentService.commentSaveOrUpdate(replycomment));
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
	 * 获取评论列表
	 * @param response
	 * @param id  活动ID  用户ID
	 * @param listtype act 根据活动ID 拿评论列表  use 根据用户ID 拿评论列表
	 * @param orderby desc 时间倒序    asc 正序
	 * @param page
	 * @param size
	 * @param type
	 */
	@RequestMapping(value="findcommentlist")
	public void findCommentList(HttpServletResponse response,String id,String listtype,String orderby,Integer page,Integer size,String type) {
		Jr jr=new Jr();
		jr.setMethod("findcommentlist");
		if(id.isEmpty()){
			jr.setCord(1);
			jr.setMsg("非法传参");
		}else{
			Page<Comment> list=commentService.commentFind(page, size, orderby, listtype, id);
			List<Comment> cms=list.getContent();
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("texts");
			set.add("createdate");
			set.add("pid");
			set.add("activity.id");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("receive.id");
			set.add("receive.nickname");
			set.add("receive.minimg");
			List<Map<String,Object>> li= BeanUtil.getFieldValueMapForList(cms, set);
//			for (int i = 0; i < li.size(); i++) {
//				List<Comment> replys=commentService.getCommentListByPid((String)li.get(i).get("id"));
//				List<Map<String,Object>> l2= BeanUtil.getFieldValueMapForList(replys, set);
//				li.get(i).put("replycomments", l2);
//			}
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
}
