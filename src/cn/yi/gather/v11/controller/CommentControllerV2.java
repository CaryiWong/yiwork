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

import cn.yi.gather.v11.entity.Comment;
import cn.yi.gather.v11.service.ICommentServiceV2;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

@Controller("commentControllerV2")
@RequestMapping(value="v2/comment")
public class CommentControllerV2 {

	private static Logger log = Logger.getLogger(ActivityControllerV2.class);
	
	@Resource(name = "commentServiceV2")
	private ICommentServiceV2 commentServiceV2;
	
	/**
	 * 发表评论
	 * @param response
	 * @param comment
	 * @param type
	 */
	@RequestMapping(value="createcomment")
	public void createComment(HttpServletResponse response,Comment comment,Integer type){
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
		if(falge){
			jr.setData(commentServiceV2.commentSaveOrUpdate(comment));
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
	 */
	@RequestMapping(value="createreplycomment")
	public void createReplycomment(HttpServletResponse response,Comment replycomment,Integer type) {
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
		Comment c=commentServiceV2.getCommentByID(replycomment.getPid());
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
		if(falge){
			jr.setData(commentServiceV2.commentSaveOrUpdate(replycomment));
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
	 * @param listtype 0 根据活动ID 拿评论列表  1 根据用户ID 拿评论列表
	 * @param orderby -1 时间倒序    1 正序
	 * @param page
	 * @param size
	 * @param type
	 */
	@RequestMapping(value="findcommentlist")
	public void findCommentList(HttpServletResponse response,String id,Integer listtype,Integer orderby,Integer page,Integer size,Integer type) {
		Jr jr=new Jr();
		jr.setMethod("findcommentlist");
		if(id.isEmpty()){
			jr.setCord(1);
			jr.setMsg("非法传参");
		}else{
			Page<Comment> list=commentServiceV2.commentFind(page, size, orderby, listtype, id);
			List<Comment> cms=list.getContent();
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("texts");
			set.add("imgs");
			set.add("createdate");
			//set.add("createdate1");
			set.add("pid");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			List<Map<String,Object>> li= BeanUtil.getFieldValueMapForList(cms, set);
			for (int i = 0; i < li.size(); i++) {
				List<Comment> replys=commentServiceV2.getCommentListByPid((String)li.get(i).get("id"));
				List<Map<String,Object>> l2= BeanUtil.getFieldValueMapForList(replys, set);
				li.get(i).put("replycomments", l2);
			}
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
