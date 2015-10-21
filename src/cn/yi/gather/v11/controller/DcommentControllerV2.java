package cn.yi.gather.v11.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v11.entity.Dcomment;
import cn.yi.gather.v11.service.IDcommentServiceV2;
import cn.yi.gather.v11.service.serviceImpl.JPushServiceV2;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

@Controller("dcommentControllerV2")
@RequestMapping(value="v2/dcomment")
public class DcommentControllerV2 {

	private static Logger log = Logger.getLogger(ActivityControllerV2.class);
	
	@Resource(name = "dcommentServiceV2")
	private IDcommentServiceV2 dcommentServiceV2;
	
	@Resource(name = "jPushServiceV2")
	private JPushServiceV2 pushServiceV2;
	
	/**
	 * 发表评论
	 * @param response
	 * @param comment
	 * @param type
	 */
	@RequestMapping(value="createdcomment")
	public void createDcomment(HttpServletResponse response,Dcomment comment,Integer type){
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
			Dcomment dcomment = dcommentServiceV2.dcommentSaveOrUpdate(comment);
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
			pushServiceV2.push(1, dcomment.getDemands().getId(),dcomment.getDemands().getTitle(), "新的评论", Platform.android_ios(), new String[]{dcomment.getDemands().getPublishuser().getId()}, new String[]{"version2"});
			//pushServiceV2.push(dcomment.getDemands().getPublishuser().getId(), "android,ios", "您的需要有新的评论", 2);
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
	public void createReplydcomment(HttpServletResponse response,Dcomment replycomment,Integer type) {
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
		Dcomment c=dcommentServiceV2.getDcommentByID(replycomment.getPid());
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
			replycomment = dcommentServiceV2.dcommentSaveOrUpdate(replycomment);
			jr.setData(BeanUtil.getFieldValueMap(replycomment,set));
			jr.setCord(0);
			//回复需求，push消息给发起者
			pushServiceV2.push(1, replycomment.getDemands().getId(), replycomment.getDemands().getTitle(), "新的回复", Platform.android_ios(), new String[]{replycomment.getDemands().getPublishuser().getId()}, new String[]{"version2"});
//			pushServiceV2.push(replycomment.getDemands().getPublishuser().getId(), "android,ios", "您的需要有新的回复", 2);
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
	 * @param id  需求ID  
	 * @param uid 用户 ID
	 * @param orderby -1 时间倒序    1 正序
	 * @param page
	 * @param size
	 * @param type
	 */
	@RequestMapping(value="finddcommentlist")
	public void finddCommentList(HttpServletResponse response,String id,String uid,Integer orderby,Integer page,Integer size,Integer type) {
		Jr jr=new Jr();
		jr.setMethod("finddcommentlist");
		if(id.isEmpty()){
			jr.setCord(1);
			jr.setMsg("非法传参");
		}else{

			Page<Dcomment> list=dcommentServiceV2.dcommentFind(page, size, orderby, uid, id);
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
			//set.add("user.industry.zname");
			List<Dcomment> cms=list.getContent();
			List<Map<String,Object>> li= BeanUtil.getFieldValueMapForList(cms, set);
			/*for (int i = 0; i < cms.size(); i++) {
				List<Dcomment> replys=dcommentServiceV2.getDcommentListByPid(cms.get(i).getId());
				List<Map<String,Object>> l2= BeanRefUtil.getFieldValueMapForList(replys, set);
				li.get(i).put("replycomments", l2);
			}*/
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
