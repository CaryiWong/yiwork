package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v20.entity.Eventlog;
import cn.yi.gather.v20.service.IEventlogService;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

/**
 * 事件动态
 * @author Lee.J.Eric
 *
 */
@Controller("eventlogControllerV20")
@RequestMapping(value = "v20/eventlog")
public class EventlogController {
	
	@Resource(name = "eventlogServiceV20")
	private IEventlogService eventlogService;

	/**
	 * 社区动态
	 * @param response
	 * @param page
	 * @param size
	 * @param type
	 * @param eventtype 动态类型，future:即将，activity:活动,course:课程，review：回顾
	 * Lee.J.Eric
	 * 2014年9月12日 下午5:28:58
	 */
	@RequestMapping(value = "trends")
	public void trends(HttpServletResponse response,Integer page,Integer size,String type,String eventtype,String userid,String cityid){
		Jr jr = new Jr();
		jr.setMethod("trends");
		if(page==null||size==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Page<Eventlog> list = eventlogService.findTrendsPage(page, size,eventtype,userid,cityid);
			Set<String> set = new HashSet<String>();
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("user.job");
			set.add("user.spaceInfo.id");
			set.add("user.spaceInfo.spacename");
			set.add("user.spaceInfo.spacecode");
			set.add("user.spaceInfo.spacecity.name");
			
			set.add("activity.id");
			set.add("activity.title");
			set.add("activity.cover");
			set.add("activity.address");
			set.add("activity.opendate");
			set.add("activity.enddate");
			set.add("activity.status");
			set.add("activity.h5url");
			set.add("activity.imgtexturl");
			set.add("activity.cost");
			set.add("activity.pricetype");
			set.add("activity.price");
			set.add("activity.vprice");
			set.add("activity.pricekey1");
			set.add("activity.pricevalue1");
			set.add("activity.spaceInfo.id");
			set.add("activity.spaceInfo.spacename");
			set.add("activity.spaceInfo.spacecode");
			set.add("activity.spaceInfo.spacelogo");
			set.add("activity.spaceInfo.spacecity.name");
			
			set.add("gathering.id");
			set.add("gathering.title");
			set.add("gathering.opendate");
			set.add("gathering.enddate");
			set.add("gathering.address");
			set.add("gathering.summary");
			set.add("gathering.status");
			set.add("gathering.user.id");
			set.add("gathering.user.nickname");
			set.add("gathering.user.minimg");
			set.add("gathering.user.sex");
			set.add("gathering.user.job");
			set.add("gathering.spaceInfo.id");
			set.add("gathering.spaceInfo.spacename");
			set.add("gathering.spaceInfo.spacecode");
			set.add("gathering.spaceInfo.spacelogo");
			set.add("gathering.spaceInfo.spacecity.name");
			
			set.add("notice.id");
			set.add("notice.title");
			set.add("notice.content");
			set.add("notice.createtime");
			set.add("notice.user.id");
			set.add("notice.user.nickname");
			set.add("notice.user.minimg");
			set.add("notice.spaceInfo.id");
			set.add("notice.spaceInfo.spacename");
			set.add("notice.spaceInfo.spacecode");
			set.add("notice.spaceInfo.spacelogo");
			set.add("notice.spaceInfo.spacecity.name");
			
			set.add("course.id");
			set.add("course.title");
			set.add("course.opendate");
			set.add("course.address");
			set.add("course.cover");
			set.add("course.status");
			set.add("course.h5url");
			set.add("course.imgtexturl");
			set.add("course.cost");
			set.add("course.pricetype");
			set.add("course.price");
			set.add("course.vprice");
			set.add("course.pricekey1");
			set.add("course.pricevalue1");
			set.add("course.spaceInfo.id");
			set.add("course.spaceInfo.spacename");
			set.add("course.spaceInfo.spacecode");
			set.add("course.spaceInfo.spacelogo");
			set.add("course.spaceInfo.spacecity.name");
			
			set.add("article.id");
			set.add("article.title");
			set.add("article.linkurl");
			set.add("article.coverimg");
			set.add("article.createdate");
			set.add("article.user.id");
			set.add("article.user.nickname");
			set.add("article.user.minimg");
			set.add("article.spaceInfo.id");
			set.add("article.spaceInfo.spacename");
			set.add("article.spaceInfo.spacecode");
			set.add("article.spaceInfo.spacelogo");
			set.add("article.spaceInfo.spacecity.name");
			
			set.add("updatetime");
			set.add("acttype");
			set.add("eventtype");
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setTotal(list.getTotalElements());
			jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
			jr.setCord(0);
			jr.setMsg("获取成功");
		}
	
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 我的社区动态
	 * @param response
	 * @param page
	 * @param size
	 * @param type
	 * @param actiontype join:参加，focus:关注,create:发起
	 * @param userid 用户id
	 * Lee.J.Eric
	 * 2014年9月16日 上午11:20:52
	 */
	@RequestMapping(value = "mytrends")
	public void myTrends(HttpServletResponse response,Integer page,Integer size,String type,String actiontype,String userid){
		Jr jr = new Jr();
		jr.setMethod("mytrends");
		if(page==null||size==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Page<Eventlog> list = eventlogService.findMyTrendsPage(page, size, userid, actiontype);
			Set<String> set = new HashSet<String>();
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("user.job");
			set.add("user.spaceInfo.id");
			set.add("user.spaceInfo.spacename");
			set.add("user.spaceInfo.spacecode");
			set.add("user.spaceInfo.spacecity");
			
			set.add("activity.id");
			set.add("activity.title");
			set.add("activity.cover");
			set.add("activity.address");
			set.add("activity.opendate");
			set.add("activity.enddate");
			set.add("activity.status");
			set.add("activity.h5url");
			set.add("activity.imgtexturl");
			set.add("activity.cost");
			set.add("activity.pricetype");
			set.add("activity.price");
			set.add("activity.vprice");
			set.add("activity.pricekey1");
			set.add("activity.pricevalue1");
			set.add("activity.spaceInfo.id");
			set.add("activity.spaceInfo.spacename");
			set.add("activity.spaceInfo.spacecode");
			set.add("activity.spaceInfo.spacelogo");
			set.add("activity.spaceInfo.spacecity");
			
			set.add("gathering.id");
			set.add("gathering.title");
			set.add("gathering.opendate");
			set.add("gathering.enddate");
			set.add("gathering.address");
			set.add("gathering.summary");
			set.add("gathering.status");
			set.add("gathering.user.id");
			set.add("gathering.user.nickname");
			set.add("gathering.user.minimg");
			set.add("gathering.user.sex");
			set.add("gathering.user.job");
			set.add("gathering.spaceInfo.id");
			set.add("gathering.spaceInfo.spacename");
			set.add("gathering.spaceInfo.spacecode");
			set.add("gathering.spaceInfo.spacelogo");
			set.add("gathering.spaceInfo.spacecity");
			
			set.add("notice.id");
			set.add("notice.title");
			set.add("notice.content");
			set.add("notice.createtime");
			set.add("notice.user.id");
			set.add("notice.user.nickname");
			set.add("notice.user.minimg");
			set.add("notice.spaceInfo.id");
			set.add("notice.spaceInfo.spacename");
			set.add("notice.spaceInfo.spacecode");
			set.add("notice.spaceInfo.spacelogo");
			set.add("notice.spaceInfo.spacecity");
			
			set.add("course.id");
			set.add("course.title");
			set.add("course.opendate");
			set.add("course.address");
			set.add("course.cover");
			set.add("course.status");
			set.add("course.h5url");
			set.add("course.imgtexturl");
			set.add("course.cost");
			set.add("course.pricetype");
			set.add("course.price");
			set.add("course.vprice");
			set.add("course.pricekey1");
			set.add("course.pricevalue1");
			set.add("course.spaceInfo.id");
			set.add("course.spaceInfo.spacename");
			set.add("course.spaceInfo.spacecode");
			set.add("course.spaceInfo.spacelogo");
			set.add("course.spaceInfo.spacecity");
			
			set.add("article.id");
			set.add("article.title");
			set.add("article.linkurl");
			set.add("article.coverimg");
			set.add("article.createdate");
			set.add("article.user.id");
			set.add("article.user.nickname");
			set.add("article.user.minimg");
			set.add("article.spaceInfo.id");
			set.add("article.spaceInfo.spacename");
			set.add("article.spaceInfo.spacecode");
			set.add("article.spaceInfo.spacelogo");
			set.add("article.spaceInfo.spacecity");
			
			set.add("invitation.id");
			set.add("invitation.title");
			set.add("invitation.opentime");
			set.add("invitation.address");
			set.add("invitation.status");
			set.add("invitation.user.id");
			set.add("invitation.user.nickname");
			set.add("invitation.user.minimg");
			set.add("invitation.user.job");
			set.add("invitation.user.sex");
			set.add("invitation.inviteuser.id");
			set.add("invitation.inviteuser.nickname");
			set.add("invitation.inviteuser.minimg");
			set.add("invitation.inviteuser.job");
			set.add("invitation.inviteuser.sex");
			
			set.add("updatetime");
			set.add("acttype");
			set.add("eventtype");
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setTotal(list.getTotalElements());
			jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
			jr.setCord(0);
			jr.setMsg("获取成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	 * 社区动态-搜索
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param keyword
	 * @param userid
	 * @param btn  按钮搜索，即将:soon,活动:activity,社区新闻，动态:news
	 */
	@RequestMapping(value = "searchtrends")
	public void searchTrends(HttpServletResponse response,String type,Integer page,Integer size,String keyword,String userid,String btn){
		Jr jr = new Jr();
		jr.setMethod("searchtrends");
		if(type==null||page==null||size==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Page<Eventlog> list = eventlogService.searchTrends(page,size,userid,keyword,btn);
			Set<String> set = new HashSet<String>();
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("user.job");
			set.add("user.spaceInfo.id");
			set.add("user.spaceInfo.spacename");
			set.add("user.spaceInfo.spacecode");
			set.add("user.spaceInfo.spacecity.name");
			
			set.add("activity.id");
			set.add("activity.title");
			set.add("activity.cover");
			set.add("activity.address");
			set.add("activity.opendate");
			set.add("activity.enddate");
			set.add("activity.status");
			set.add("activity.h5url");
			set.add("activity.imgtexturl");
			set.add("activity.cost");
			set.add("activity.pricetype");
			set.add("activity.price");
			set.add("activity.vprice");
			set.add("activity.pricekey1");
			set.add("activity.pricevalue1");
			set.add("activity.spaceInfo.id");
			set.add("activity.spaceInfo.spacename");
			set.add("activity.spaceInfo.spacecode");
			set.add("activity.spaceInfo.spacelogo");
			set.add("activity.spaceInfo.spacecity.name");
			
			set.add("gathering.id");
			set.add("gathering.title");
			set.add("gathering.opendate");
			set.add("gathering.enddate");
			set.add("gathering.address");
			set.add("gathering.summary");
			set.add("gathering.status");
			set.add("gathering.user.id");
			set.add("gathering.user.nickname");
			set.add("gathering.user.minimg");
			set.add("gathering.user.sex");
			set.add("gathering.user.job");
			set.add("gathering.spaceInfo.id");
			set.add("gathering.spaceInfo.spacename");
			set.add("gathering.spaceInfo.spacecode");
			set.add("gathering.spaceInfo.spacelogo");
			set.add("gathering.spaceInfo.spacecity.name");
			
			set.add("notice.id");
			set.add("notice.title");
			set.add("notice.content");
			set.add("notice.createtime");
			set.add("notice.user.id");
			set.add("notice.user.nickname");
			set.add("notice.user.minimg");
			set.add("notice.spaceInfo.id");
			set.add("notice.spaceInfo.spacename");
			set.add("notice.spaceInfo.spacecode");
			set.add("notice.spaceInfo.spacelogo");
			set.add("notice.spaceInfo.spacecity.name");
			
			set.add("course.id");
			set.add("course.title");
			set.add("course.opendate");
			set.add("course.address");
			set.add("course.cover");
			set.add("course.status");
			set.add("course.h5url");
			set.add("course.imgtexturl");
			set.add("course.cost");
			set.add("course.pricetype");
			set.add("course.price");
			set.add("course.vprice");
			set.add("course.pricekey1");
			set.add("course.pricevalue1");
			set.add("course.spaceInfo.id");
			set.add("course.spaceInfo.spacename");
			set.add("course.spaceInfo.spacecode");
			set.add("course.spaceInfo.spacelogo");
			set.add("course.spaceInfo.spacecity.name");
			
			set.add("article.id");
			set.add("article.title");
			set.add("article.linkurl");
			set.add("article.coverimg");
			set.add("article.createdate");
			set.add("article.user.id");
			set.add("article.user.nickname");
			set.add("article.user.minimg");
			set.add("article.spaceInfo.id");
			set.add("article.spaceInfo.spacename");
			set.add("article.spaceInfo.spacecode");
			set.add("article.spaceInfo.spacelogo");
			set.add("article.spaceInfo.spacecity.name");

			set.add("updatetime");
			set.add("acttype");
			set.add("eventtype");
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setTotal(list.getTotalElements());
			jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
			jr.setCord(0);
			jr.setMsg("获取成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
