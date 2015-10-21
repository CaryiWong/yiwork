package cn.yi.gather.v11.admin.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v11.entity.Activity;
import cn.yi.gather.v11.entity.ActivityJoin;
import cn.yi.gather.v11.entity.Labels;
import cn.yi.gather.v11.entity.Reviewcontent;
import cn.yi.gather.v11.service.IActivityJoinServiceV2;
import cn.yi.gather.v11.service.IActivityServiceV2;
import cn.yi.gather.v11.service.IJPushServiceV2;
import cn.yi.gather.v11.service.ILabelsServiceV2;
import cn.yi.gather.v11.service.IReviewcontentServiceV2;

import com.common.CreateHtml;
import com.common.Jr;
import com.common.Page;
import com.common.R;
import com.tools.utils.JSONUtil;

/**
 * 后台-活动控制器
 * @author Lee.J.Eric
 * @time 2014年6月13日下午12:05:01
 */
@Controller("adminActivityControllerV2")
@RequestMapping(value = "v2/admin/activity")
public class AdminActivityControllerV2 {
	
	@Resource(name ="labelsServiceV2")
	private ILabelsServiceV2 labelsServiceV2;
	
	@Resource(name = "activityServiceV2")
	private IActivityServiceV2 activityServiceV2;
	
	@Resource(name ="jPushServiceV2")
	private IJPushServiceV2 pushServiceV2;
	
	@Resource(name = "reviewcontentServiceV2")
	private IReviewcontentServiceV2 reviewcontentServiceV2;
	
	@Resource(name = "activityJoinServiceV2")
	private IActivityJoinServiceV2 activityJoinServiceV2;
	
	@RequestMapping(value = "activitylist")
	public ModelAndView activitylist(ModelMap modelMap, Page<Activity> page, Integer cost,
			Integer checktype, Integer status, String keyword, Long[] groups){
		List<Long> labelList = new ArrayList<Long>();
		if (null != groups) {
			labelList = Arrays.asList(groups);
		} else {
			labelList = null;
		}
		List<Labels> labels = labelsServiceV2.getLabelsByType(0);
		page = activityServiceV2.getActivityForPage(page.getCurrentPage(), page.getPageSize(), cost, checktype, status, keyword, labelList);
		modelMap.put("labelList", labelList);
		modelMap.put("labels", labels);
		modelMap.put("page", page);
		modelMap.put("cost", cost);
		modelMap.put("checktype", checktype);
		modelMap.put("status", status);
		modelMap.put("keyword", keyword);
		return new ModelAndView("admin/activity/activitylist");
	}
	
	/**
	 * 跳转到更新活动页面
	 * @param modelMap
	 * @param activityid
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月13日 下午2:44:25
	 */
	@RequestMapping(value = "getupdateactivity")
	public ModelAndView getUpdateActivity(ModelMap modelMap, String activityid){
		Activity activity = activityServiceV2.findById(activityid);
		List<Labels> labels = labelsServiceV2.getLabelsByType(0);
		List<Long> labelList = new ArrayList<Long>();
		List<Labels> myLabel = activity.getLabel();
		for (Labels labels2 : myLabel) {
			labelList.add(labels2.getId());
		}
		modelMap.put("labelList", labelList);
		modelMap.put("activity", activity);
		modelMap.put("labels", labels);
		return new ModelAndView("admin/activity/updateactivity");
	}
	
	/**
	 * 更新活动情况
	 * @param modelMap
	 * @param activity
	 * @param groups
	 * @param open
	 * @param end
	 * @return
	 * Lee.J.Eric
	 * 2014年6月13日 下午2:55:03
	 */
	@RequestMapping(value = "updateactivity")
	public ModelAndView updateActivity(ModelMap modelMap, Activity activity, Long[] groups,String open,String end){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Long> labelList = new ArrayList<Long>();
		if(null != groups){
			labelList = Arrays.asList(groups);
		}else{
			labelList = null;
		}
		List<Labels> labels = labelsServiceV2.getLabelsByType(0);
		List<Labels> mylabel = labelsServiceV2.getLabelsByList(labelList);
		Activity original = activityServiceV2.findById(activity.getId());
		original.setTitle(activity.getTitle());
		original.setSummary(activity.getSummary());
		original.setAddress(activity.getAddress());
		original.setCost(activity.getCost());
		original.setTel(activity.getTel());
		original.setMaxnum(activity.getMaxnum());
		original.setPrice(activity.getPrice());
		//个性化
		original.setActtype(activity.getActtype());
		original.setActtypetitle(activity.getActtypetitle());
		original.setActtypeurl(activity.getActtypeurl());
		/* *********************************0630 S*************************************  */	
		original.setPricetype(activity.getPricetype());
		original.setPricekey(activity.getPricekey());
		original.setPricevalue(activity.getPricevalue());
		
		if(activity.getPricetype()==1){
			List<Integer> lIntegers=new ArrayList<Integer>();
			String[] arrs=activity.getPricevalue().split(",");
			for (String s : arrs) {
				lIntegers.add(Integer.parseInt(s));
			}
			Collections.sort(lIntegers);
			original.setMinprice(lIntegers.get(0));
			if(lIntegers.size()<2){
				original.setMaxprice(lIntegers.get(0));
			}else{
				original.setMaxprice(lIntegers.get((lIntegers.size()-1)));
			}
		}else{
			original.setMinprice(activity.getPrice());
			original.setMaxprice(activity.getPrice());
		}
		/* *********************************0630 E*************************************  */	
		
		try {//兼容之前的日期格式
			if(open!=null&&!open.isEmpty()){
				original.setOpendate1(format.parse(open));
				original.setOpendate(original.getOpendate1().getTime());
			}
			if(end!=null&&!end.isEmpty()){
				original.setEnddate1(format.parse(end));
				original.setEnddate(original.getEnddate1().getTime());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (labelList==null||labelList.size()<1||labelList.size()>3) {
			modelMap.put("tips", "标签数个数：1~3个");
		}else {
			original.setLabel(mylabel);
			original = activityServiceV2.activitySaveOrUpdate(original);
			modelMap.put("tips", "修改成功");
		}
		modelMap.put("activity", original);
		modelMap.put("labelList", labelList);
		modelMap.put("labels", labels);
		return new ModelAndView("admin/activity/updateactivity");
	}
	
	/**
	 * 审核活动
	 * @param response
	 * @param id
	 * @param checktype
	 * Lee.J.Eric
	 * 2014年6月13日 下午3:01:33
	 */
	@RequestMapping(value="verifyactivity")
	public void verifyActivity(HttpServletResponse response,String id,Integer checktype){
		Jr jr = new Jr();
		jr.setMethod("verifyactivity");
		if(id==null||id.isEmpty()||checktype==null){
			jr.setCord(-1);//非法传值
			jr.setMsg("非法传值");
		}else {
			Activity activity = activityServiceV2.findById(id);
			if (activity==null) {
				jr.setCord(1);
				jr.setMsg("活动不存在");
			}else {
				activity.setChecktype(checktype);
				Date now = new Date();
				if(checktype==1){
					if(activity.getOpendate1().after(now)){//开启时间未到
						activity.setStatus(1);//待开启
					}else if(activity.getOpendate1().before(now)&&activity.getEnddate1().after(now)){//
						activity.setStatus(2);//进行中
					}else {
						activity.setStatus(3);//已结束
					}
					activity.setPublishdate1(now);
				}
				activity = activityServiceV2.activitySaveOrUpdate(activity);
				if(checktype==1){//活动审核通过，向客户端push消息提示
//					String title = "新活动:" + activity.getTitle();
//					pushServiceV2.push("yiqi", "android,ios", title, 0);
					pushServiceV2.push(0, activity.getId(), activity.getTitle(), "新活动", Platform.android_ios(), new String[]{"yiqi"}, new String[]{"version2"});
				}
				if (activity==null) {
					jr.setCord(1);
				}else {
					jr.setCord(0);
					jr.setData(activity);
					/*//审核通过，以发起人评论活动
					Comment comment = new Comment();
					comment.setActivity(activity.getId());
					comment.setUser(activity.getUser());
					//comment.setUserimg(activity.getUserimg());
					//comment.setUsernickname(activity.getUsernickname());
					String text = "大家好，我是本次活动发起人。欢迎大家参加本次活动，大家可以在这里提出你对活动的建议、看法；也欢迎大家把现场图片花絮发上来。精彩评论图片我们会选录进该活动的回顾中。";
					comment.setTexts(text);
					commentService.createComment(comment);*/
				}
			}
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
	 * banner广告位设置
	 * @param response
	 * @param actid
	 * @param banner
	 * @param type
	 * Lee.J.Eric
	 * 2014年6月13日 下午3:01:47
	 */
	@RequestMapping(value="setbanner")
	public void setBanner(HttpServletResponse response,String actid,Integer banner,Integer type){
		Map<String, String> result = new HashMap<String, String>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, String> msg = new HashMap<String, String>();
		Activity activity = activityServiceV2.findById(actid);
		if(activity==null){
			result.put("result", "f");
			msg.put("msg", "设置失败");
		}else {
			if(activity.getChecktype()==1){//活动已通过审核
				Long count = activityServiceV2.countBannerAct();
				if(banner==1&&count.intValue()>=R.Act.SLIDE_COUNT){
					result.put("result", "f");
					msg.put("msg", "banner上限");
				}else {
					activity.setIsbanner(banner);
					activity = activityServiceV2.activitySaveOrUpdate(activity);
					if(activity==null){
						result.put("result", "f");
						msg.put("msg", "设置失败");
					}else {
						result.put("result", "s");
						msg.put("msg", "设置成功");
						data.put("data", activity);
					}
				}
			}else {//活动未通过审核
				result.put("result", "f");
				msg.put("msg", "活动未审核");
			}
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(result, data, msg, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 新增回顾
	 * @param modelMap
	 * @param activityid
	 * @return
	 * Lee.J.Eric
	 * 2014年6月13日 下午3:09:29
	 */
	@RequestMapping(value="createhtml")
	public ModelAndView createhtml(ModelMap modelMap,String activityid){
		modelMap.put("activityid", activityid);
		return new ModelAndView("admin/activity/createhtml",modelMap);
	}
	
	/**
	 * 保存活动回顾
	 * @param modelMap
	 * @param texts
	 * @param id
	 * @param request
	 * @param response
	 * Lee.J.Eric
	 * 2014年6月13日 下午3:09:49
	 */
	@RequestMapping(value="createhtmlpage")
	public void createhtmlpage(ModelMap modelMap,String texts,String id, HttpServletRequest request,HttpServletResponse response){
		CreateHtml createHtml=new CreateHtml(texts,request.getServletContext().getRealPath("/"));
		//modelMap.put("htmlurl", createHtml.getUrl_path());
		Activity activity = activityServiceV2.findById(id);
		activity.setHuiguurl(createHtml.getUrl_path());
		modelMap.put("target", createHtml.getUrl_path());
		activityServiceV2.activitySaveOrUpdate(activity);
		Reviewcontent reviewcontent = new Reviewcontent(id, texts);
		reviewcontentServiceV2.reviewcontentSaveOrUpdate(reviewcontent);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(modelMap, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转更新回顾
	 * @param modelMap
	 * @param activityid
	 * @return
	 * Lee.J.Eric
	 * 2014年6月13日 下午3:29:02
	 */
	@RequestMapping(value="updatehtml")
	public ModelAndView updateHtml(ModelMap modelMap,String activityid){
		Reviewcontent reviewcontent = reviewcontentServiceV2.findById(activityid);
		modelMap.put("reviewcontent", reviewcontent);
		return new ModelAndView("admin/activity/updatehtml",modelMap);
	}
	
	/**
	 * 修改活动上/下架状态
	 * @param response
	 * @param id 活动id
	 * @param onsale  0上架/1下架
	 * @param response
	 * @param id
	 * @param onsale
	 * Lee.J.Eric
	 * 2014年6月13日 下午3:31:39
	 */
	@RequestMapping(value="setonsale")
	public void setOnsale(HttpServletResponse response,String id,Integer onsale){
		Map<String, String> result = new HashMap<String, String>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, String> msg = new HashMap<String, String>();
		Activity activity = activityServiceV2.findById(id);
		if(activity==null||onsale==null){
			result.put("result", "f");
			msg.put("msg", "操作失败");
		}else {
			activity.setOnsale(onsale);//上下架状态
			activity = activityServiceV2.activitySaveOrUpdate(activity);
			if(activity==null){
				result.put("result", "f");
				msg.put("msg", "操作失败");
			}else {
				result.put("result", "s");
				msg.put("msg", "操作成功");
				data.put("data", activity);
			}
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(result, data, msg, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取活动的报名列表
	 * @param response
	 * @param type
	 * @param activityid
	 * @param page
	 * @param modelMap
	 * @param keyword
	 * @return
	 * Lee.J.Eric
	 * 2014年6月13日 下午4:53:13
	 */
	@RequestMapping(value="getactsignlist")
	public ModelAndView getActSignList(HttpServletResponse response,Integer type,String activityid,Page<ActivityJoin> page,ModelMap modelMap,String keyword){
		try {
			Activity activity = activityServiceV2.findById(activityid);
			page = activityJoinServiceV2.signList(page.getCurrentPage(), page.getPageSize(), activity, keyword);
			modelMap.put("keyword", keyword);
			modelMap.put("activityid", activityid);
			modelMap.put("page", page);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("admin/activity/signlist");
	}
}
