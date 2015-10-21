package cn.yi.gather.v20.admin.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.ActivityJoin;
import cn.yi.gather.v20.entity.AlipayNotificationLog;
import cn.yi.gather.v20.entity.Course;
import cn.yi.gather.v20.entity.Eventlog;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.Question;
import cn.yi.gather.v20.entity.Reviewcontent;
import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeActionTypeValue;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeMsgTypeValue;
import cn.yi.gather.v20.service.IActivityJoinService;
import cn.yi.gather.v20.service.IActivityService;
import cn.yi.gather.v20.service.ICourseService;
import cn.yi.gather.v20.service.IEventlogService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.IReviewcontentService;
import cn.yi.gather.v20.service.ISkuService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.serviceImpl.skuService;

import com.common.CreateHtml;
import com.common.Jr;
import com.common.Page;
import com.common.R;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tools.utils.ExportExcel;
import com.tools.utils.JSONUtil;

/**
 * 后台-活动控制器
 * @author Lee.J.Eric
 * @time 2014年6月13日下午12:05:01
 */
@Controller("adminActivityControllerV20")
@RequestMapping(value = "v20/admin/activity")
public class AdminActivityController {
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name = "activityServiceV20")
	private IActivityService activityService;
	
	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;
	
	@Resource(name = "reviewcontentServiceV20")
	private IReviewcontentService reviewcontentService;
	
	@Resource(name = "activityJoinServiceV20")
	private IActivityJoinService activityJoinService;
	
	@Resource(name = "eventlogServiceV20")
	private IEventlogService eventlogService;
	
	@Resource(name="courseServiceV20")
	private ICourseService courseService;
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource;
	
	@Resource(name="userServiceV20")
	private IUserService userService;
	
	@Resource(name="skuServiceV20")
	private ISkuService skuService;
	
	@RequestMapping(value = "activitylist")
	public ModelAndView activitylist(ModelMap modelMap, Page<Activity> page, Integer cost,
			Integer checktype, Integer status, String keyword, Long[] groups){
		List<Long> labelList = new ArrayList<Long>();
		if (null != groups) {
			labelList = Arrays.asList(groups);
		} else {
			labelList = null;
		}
		//List<Labels> labels = labelsService.getLabelsByType(0);
		page = activityService.getActivityForPage(page.getCurrentPage(), page.getPageSize(), cost, checktype, status, keyword, labelList);
		modelMap.put("labelList", labelList);
		//modelMap.put("labels", labels);
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
		Activity activity = activityService.findById(activityid);
		//List<Labels> labels = labelsService.getLabelsByType(0);
	/*	List<Long> labelList = new ArrayList<Long>();
		Set<Labels> myLabel = activity.getLabel();
		for (Labels labels2 : myLabel) {
			labelList.add(labels2.getId());
		}
		modelMap.put("labelList", labelList);*/
		modelMap.put("activity", activity);
		modelMap.put("labels", null);
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
		//List<Labels> labels = labelsService.getLabelsByType(0);
		//List<Labels> mylabel = labelsService.getLabelsByList(labelList);
		Activity original = activityService.findById(activity.getId());
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
				original.setVprice(lIntegers.get((lIntegers.size()-2)));  
			}
		}else{
			if(activity.getPricevalue()=="" ||activity.getPricevalue().equals("")){
				original.setVprice(0);
			}else{
				original.setVprice(Integer.parseInt(activity.getPricevalue()));  
			}
			original.setMinprice(activity.getPrice());
			original.setMaxprice(activity.getPrice());
		}
		/* *********************************0630 E*************************************  */	
		
		try {//兼容之前的日期格式
			if(open!=null&&!open.isEmpty()){
				original.setOpendate(format.parse(open));
//				original.setOpendate(original.getOpendate1().getTime());
			}
			if(end!=null&&!end.isEmpty()){
				original.setEnddate(format.parse(end));
//				original.setEnddate(original.getEnddate1().getTime());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
			//original.setLabel(mylabel);
			original = activityService.activitySaveOrUpdate(original);
			//保存SKU信息 价格等
			SKU sku =  skuService.getSku(original.getSkuid());
			List<Integer> pricevalue=new ArrayList<Integer>();
			for(Object j:original.getPricevalue1()){
				pricevalue.add(Integer.parseInt(j.toString()));
			}
			 
			if(pricevalue.size()==1 && original.getPricetype()==0){	 //统一价
				sku.setDefaultPrice(Double.parseDouble(original.getPricevalue()));
				sku.setMemberPrice(Double.parseDouble(original.getPricevalue()));
			}if(original.getPricetype()==1){  //多价格
				Collections.sort(pricevalue);  //排序
				sku.setDefaultPrice(Double.parseDouble(pricevalue.get(pricevalue.size()-1).toString()));  //非会员在后
				sku.setMemberPrice(Double.parseDouble(pricevalue.get(pricevalue.size()-2).toString()));   //会员在前
				sku.setMultPrice(pricevalue.get(pricevalue.size()-1)+","+pricevalue.get(pricevalue.size()-2));      //混合价  100,99  这样子
			}
			
 			skuService.saveOrUpdate(sku);
			
			modelMap.put("tips", "修改成功");
	 
		modelMap.put("activity", original);
		modelMap.put("labelList", labelList);
		//modelMap.put("labels", labels);
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
			Activity activity = activityService.findById(id);
			if (activity==null) {
				jr.setCord(1);
				jr.setMsg("活动不存在");
			}else {
				activity.setChecktype(checktype);
				activity.setPublishdate(new Date());
				activity = activityService.activitySaveOrUpdate(activity);
				
				if(checktype==1){//活动审核通过，向客户端push消息提示
					//事件记录
					Eventlog eventlog = new Eventlog();
					eventlog.setActivity(activity);
					eventlog.setActtype(Activity.entityName);
					eventlog.setEventtype("create");//是创建
					//eventlog.setUser(activity.getUser());@kcm 8.17屏蔽
					eventlog.setUpdatetime(activity.getOpendate());
					eventlog.setCity(activity.getSpaceInfo().getSpacecity());
					eventlogService.eventlogSaveOrUpdate(eventlog);
					//push通知
					JSONObject msg_extras = new JSONObject();
					msg_extras.put("type", NoticeMsgTypeValue.ACTIVITY.value);
					msg_extras.put("parameter", activity.getId());
					msg_extras.put("icon", "loudspeaker");
					pushService.push("新活动:" + activity.getTitle(), "新活动", msg_extras, Platform.android_ios(), new String[]{"yiqi"}, new String[] { "version4"});
					//8.17屏蔽push
				}
				jr.setCord(0);
				jr.setData(activity);
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
	 * 手动推送
	 * @param response
	 * @param id
	 */
	@RequestMapping(value="pushactivity")
	public void pushActivity(HttpServletResponse response,String id){
		Map<String, String> result = new HashMap<String, String>();
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, String> msg = new HashMap<String, String>();
		Activity activity = activityService.findById(id);
		if(activity==null){
			result.put("result", "f");
			msg.put("msg", "操作失败");
		}else {
			if(activity.getChecktype()==1){//手动推送（前提 已经审核过的活动）
				//push通知
				//push通知
				JSONObject msg_extras = new JSONObject();
				msg_extras.put("type", NoticeMsgTypeValue.ACTIVITY.value);
				msg_extras.put("parameter", activity.getId());
				msg_extras.put("icon", "loudspeaker");
				//User user=userService.findById("6c2a69aaf9744498a1a11399608219942");
				pushService.push("新活动:" + activity.getTitle(), "新活动", msg_extras, Platform.android_ios(), new String[]{"yiqi"}, new String[] { "version4"});
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
		Activity activity = activityService.findById(actid);
		if(activity==null){
			result.put("result", "f");
			msg.put("msg", "设置失败");
		}else {
			if(activity.getChecktype()==1){//活动已通过审核
				Long count = activityService.countBannerAct();
				if(banner==1&&count.intValue()>=R.Act.SLIDE_COUNT){
					result.put("result", "f");
					msg.put("msg", "banner上限");
				}else {
					activity.setIsbanner(banner);
					activity = activityService.activitySaveOrUpdate(activity);
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
		Activity activity = activityService.findById(id);
		activity.setHuiguurl(createHtml.getUrl_path());
		modelMap.put("target", createHtml.getUrl_path());
		activityService.activitySaveOrUpdate(activity);
		Reviewcontent reviewcontent = new Reviewcontent(id, texts);
		reviewcontentService.reviewcontentSaveOrUpdate(reviewcontent);
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
		Reviewcontent reviewcontent = reviewcontentService.findById(activityid);
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
		Activity activity = activityService.findById(id);
		if(activity==null||onsale==null){
			result.put("result", "f");
			msg.put("msg", "操作失败");
		}else {
			activity.setOnsale(onsale);//上下架状态
			activity = activityService.activitySaveOrUpdate(activity);
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
			Activity activity = activityService.findById(activityid);
			page = activityJoinService.signList(page.getCurrentPage(), page.getPageSize(), activity, keyword);
			modelMap.put("keyword", keyword);
			modelMap.put("activityid", activityid);
			modelMap.put("page", page);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("admin/activity/signlist");
	}
	
	
	/**
	 * 根据活动id获取报名列表 2.0
	 * @param response
	 * @param id 活动id
	 * @param type
	 * @param page
	 * @param size
	 * @author Lee.J.Eric
	 * @time 2014年12月2日 下午5:33:55
	 */
	@RequestMapping(value="activitysignlist")
	public ModelAndView activitySignlist(HttpServletResponse response,ModelMap modelMap,String activityid,String type,Page<User> page){
		 
		try {
			Connection conn = dataSource.getConnection();
			Page<User> paged_result = eventlogService.getSignListByIdForPage(conn, activityid, page.getCurrentPage(), page.getPageSize());
			modelMap.put("activityid", activityid);
			modelMap.put("page", paged_result);
			 
			conn.close();
		}
		catch (Exception e) {
			 
		}
		
		return new ModelAndView("admin/activity/signlist");
	}
	
	
	/**
	 * 获取课程列表
	 * @param response
	 * @param type  web
	 * @param page   
	 * @param size
	 * @param modelMap
	 * @param keyword  关键字(搜索时使用)
	 * @return
	 */
	@RequestMapping(value="getcourselist")
	public ModelAndView getCourseList(HttpServletResponse response,Integer type,Integer page,Integer size,ModelMap modelMap,String keyword){
		try {
			org.springframework.data.domain.Page<Course> list =courseService.courselist(page, size, keyword);
 			modelMap.put("keyword", keyword);
		 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("admin/course/courselist");
	}
	
	/**
	 * 导出报名列表 
	 * @param response
	 * @param actid
	 * @throws Exception
	 */
	@RequestMapping(value="exportExcel")
	public void exportExeclSignedList(HttpServletResponse response,String actid) throws Exception{
		/*Connection conn = dataSource.getConnection();
		Page<User> paged_result = eventlogService.getSignListByIdForPage(conn, actid,0, 1000);
		List list = paged_result.getResult();
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment; filename=signedList.xls");
		OutputStream out = new BufferedOutputStream(response.getOutputStream());
		ExportExcel export = new ExportExcel();
		export.ExportExcel(out,list);
		out.flush();
		out.close();*/
		List list=eventlogService.findByActAndSign(actid);
		String[][] h_arr={{"姓名"},{"妮称"},{"电话"},{"邮箱"},{"性别"},
				{"是否会员"},{"会员号"},{"报名时间"},{"报名问卷",""}};
		String[][] b_arr={{"user.realname"},{"user.nickname"},{"user.telnum"},{"user.email"},{"user.sex"},
				{"user.root"},{"user.unum"},{"createtime"},{"qlist","questiontext","answertext"}};
		Class[] c_arr={Question.class};
		if(list!=null){
			ExportExcel export = new ExportExcel();
			HSSFWorkbook wbook=export.createExcelWorkbook("报名表", h_arr, b_arr,c_arr, 2, list, "yyyy-MM-dd HH:mm:ss", Eventlog.class);
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment; filename=signedList.xls");
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			wbook.write(out);
			out.flush();
			out.close();
		}
		
	}
	 
}
