package cn.yi.gather.v20.controller;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.editor.comm.CreateHtml;
import cn.yi.gather.v20.editor.entity.Htmlpagecode;
import cn.yi.gather.v20.editor.service.IHtmlpagecodeService;
import cn.yi.gather.v20.entity.*;
import cn.yi.gather.v20.entity.Activity.ActivityTypeValue;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeActionTypeValue;
import cn.yi.gather.v20.entity.NoticeMsg.NoticeMsgTypeValue;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.permission.entity.AdminUser;
import cn.yi.gather.v20.permission.service.IAdminUserService;
import cn.yi.gather.v20.service.*;

import com.common.Jr;
import com.common.R;
import com.tools.utils.*;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller("activityControllerV20")
@RequestMapping(value="v20/activity")
public class ActivityController {

	private static Logger log = Logger.getLogger(ActivityController.class);
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "activityServiceV20")
	private IActivityService activityService;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name = "activityJoinServiceV20")
	private IActivityJoinService activityJoinService;
	
	@Resource(name = "gatheringServiceV20")
	private IGatheringService gatheringService;
	
	@Resource(name = "gCommentServiceV20")
	private IGCommentService gCommentService;
	
	@Resource(name = "galleryServiceV20")
	private IGalleryService galleryService;
	
	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;
	
	@Resource(name = "eventlogServiceV20")
	private IEventlogService eventlogService;
	
	@Resource(name = "courseServiceV20")
	private ICourseService courseService;
	
	@Resource(name = "cCommentServiceV20")
	private ICCommentService cCommentService;
	
	@Resource(name = "noticeMsgServiceV20")
	private INoticeMsgService noticeMsgService;
	
	@Resource(name = "questionServiceV20")
	
	private IQuestionService questionService;
	
	@Resource(name = "itemServiceV20")
	private IItemService itemService;
	
	@Resource(name = "skuServiceV20")
	private ISkuService skuService;
	
	@Resource(name = "skuInventoryServiceV20")
	private ISkuInventoryService skuInventoryService;
	
	@Resource(name = "orderServiceV20")
	private IOrderService orderService;
	
	@Resource(name = "itemInstanceServiceV20")
	private IItemInstanceService itemInstanceService;
	
	@Resource(name = "itemInstanceLogServiceV20")
	private IItemInstanceLogService itemInstanceLogService;
	
	@Resource(name ="yigatherItemInventoryLogServiceV20")
	private IYigatherItemInventoryLogService yigatherItemInventoryLogService;
	
	@Resource(name = "alipayServiceV20")
	private IAlipayService alipayService;
	
	@Resource(name="htmlpagecodeServiceV20")
	private IHtmlpagecodeService htmlpagecodeService;//页面代码
	
	@Resource(name="workSpaceInfoService")
	private IWorkSpaceInfoService spaceInfoService;
	
	@Resource (name="adminUserService")
	private IAdminUserService adminUserService;
	
	/**
	 * 创建活动 2.0
	 * @param response
	 * @param activity
	 * @param labels
	 * @param open
	 * @param end
	 * @author Lee.J.Eric
	 * @time 2014年12月2日 下午5:04:02
	 */
	@Transactional
	@RequestMapping(value="createactivity")
	public void createActivity(HttpServletResponse response,Activity activity,String labels,String open,String end){
		Jr jr = new Jr();
		jr.setMethod("createactivity");
		if(activity==null||labels==null||open==null||end==null){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else{
			AdminUser user = adminUserService.findById(activity.getUser().getId()); //@kcm 8.17修改
			if(user==null){
				jr.setCord(1);//用户不存在
				jr.setMsg("用户不存在");
			/*}else if(user.getRoot()>1){
				jr.setCord(1);//用户不存在
				jr.setMsg("用户无权限");*/
			}else{
				try {
					Boolean flag=true;
				if(activity.getActivityType().equals(ActivityTypeValue.SUB.value)){
					if(activity.getPid()==null||activity.getPid().length()<1)
					flag=false;
				}
				if(flag){
					activity.setLabel(labelsService.getLabelsByIds(labels));
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					activity.setOpendate(format.parse(open));
					activity.setEnddate(format.parse(end));
					activity.setUser(user);
					//activity.setSpaceInfo(spaceInfo);
//					activity.setJoinnum(1);
					/* *********************************0630 S*************************************  */	
					if(activity.getPricetype()==1){
						List<Integer> lIntegers=new ArrayList<Integer>();
						String[] arrs=activity.getPricevalue().split(",");
						for (String s : arrs) {
							lIntegers.add(Integer.parseInt(s));
						}
						activity.setMinprice(Collections.min(lIntegers));
						activity.setMaxprice(Collections.max(lIntegers));
					}else{
						activity.setMinprice(activity.getPrice());
						activity.setMaxprice(activity.getPrice());
					}
					activity = activityService.activitySaveOrUpdate(activity);
					
					//user.setMyactnum(user.getMyactnum()+1);//活动发起数+1
					//userService.userSaveOrUpdate(user);
					/* *********************************0630 E*************************************  */	
					
					if(activity.getMaxnum()>0&&!activity.getActivityType().equals(ActivityTypeValue.SET.value)){//收费活动，活动券库存量入库
						//查询商品大类
						ItemClass itemClass = itemService.findByCode(Activity.entityName);
						//生成具体活动的券
						SKU sku = new SKU();
						sku.setId(String.format("%d-%d", itemClass.getId(), System.currentTimeMillis()));
						sku.setItemClass(itemClass);
						sku.setStatus(0);
						sku.setName(activity.getTitle());
						sku.setCode("activity-"+System.currentTimeMillis());
						sku.setDefaultPrice(Double.valueOf(activity.getPrice()));
						sku.setMemberPrice(Double.valueOf(activity.getVprice()));
						sku.setMultPrice(activity.getPricevalue());
						skuService.saveOrUpdate(sku);
						activity.setSkuid(sku.getId());//回填，关联活动
						//活动券库存量
						SkuInventory inventory = new SkuInventory();
						inventory.setCreateTime(new Timestamp(System.currentTimeMillis()));
						inventory.setModifyTime(new Timestamp(System.currentTimeMillis()));
						inventory.setId(sku.getId());
						inventory.setIsUnlimited(activity.getMaxnum()>0?false:true);
						inventory.setAmount(activity.getMaxnum());
						skuInventoryService.saveOrUpdate(inventory);
						
					}
					//活动数据入库
					activity = activityService.activitySaveOrUpdate(activity);
					jr.setData(activity);
					jr.setCord(0);
					jr.setMsg("创建成功");
				}else{
					jr.setCord(4);//新建活动失败
					jr.setMsg("该活动为子活动,无活动集ID");
				}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					jr.setCord(1);//新建活动失败
					jr.setMsg("新建失败");
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
	 * 获取单个活动信息 2.0
	 * @param response
	 * @param id
	 * Lee.J.Eric
	 * 2014 2014年6月4日 上午11:12:54
	 */
	@RequestMapping(value="getactivity")
	public void getActivity(HttpServletResponse response,String id){
		Jr jr = new Jr();
		jr.setMethod("getactivity");
		if(id==null||id.isEmpty()){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Activity activity = activityService.findById(id);
			if(activity==null){
				jr.setCord(1);
				jr.setMsg("活动不存在");
			}else{
				jr.setCord(0);
				jr.setData(activity);
				jr.setMsg("查询成功");
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
	 * 报名活动 2.0---------待修改
	 * @param response
	 * @param type
	 * @param activityid 活动id
	 * @param userid 用户id
	 * @param tel 电话
	 * @param name 姓名
	 * @param isvip 是否会员，0:不是，1:是
	 * @param flag 报名标记，0报名，1取消报名
	 * @author Lee.J.Eric
	 * @time 2014年12月26日 上午11:18:47
	 */
	@RequestMapping(value = "signactivity")
	public void signActivity(HttpServletResponse response,String type,String activityid,String userid,String tel,String name,Integer isvip,Integer flag){
		Jr jr = new Jr();
		jr.setMethod("signactivity");
		if(type==null||userid==null||activityid==null||flag==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Activity activity = activityService.findById(activityid);
			User user = userService.findById(userid);
			if(activity==null||user==null){
				jr.setCord(1);
				jr.setMsg("活动或用户不存在");
			}else {
				Date now = new Date();
				if(now.after(activity.getOpendate())){
					jr.setCord(1);
					jr.setMsg("活动已开启!");
				}else {
					if(flag==0){//报名
						//此处待添加支付流程结果凭证，成功则走下面流程
						
						Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, activityid,Activity.entityName, "sign");
						if(eventlog==null)
							eventlog = new Eventlog();
						eventlog.setActivity(activity);
						eventlog.setActtype(Activity.entityName);
						eventlog.setEventtype("sign");
						eventlog.setUser(user);
//						eventlog.setOpentime(activity.getOpendate());
						eventlog.setUpdatetime(new Date());
						eventlogService.eventlogSaveOrUpdate(eventlog);
						
						//消息中心-消息
						Set<User> set = new HashSet<User>();
						//set.add(activity.getUser()); @kcm 8.17屏蔽
						NoticeMsg noticeMsg = new NoticeMsg();
						noticeMsg.setActiontype(NoticeActionTypeValue.SIGN.value);
						noticeMsg.setContents(user.getNickname()+"报名了你的活动"+activity.getTitle());
						noticeMsg.setMsgtype(NoticeMsgTypeValue.ACTIVITY.value);
						noticeMsg.setParam(activity.getId());
						noticeMsg.setTags(set);
						noticeMsg.setIcon("loudspeaker");
						noticeMsgService.saveOrUpdate(noticeMsg);
						
						//push通知
						JSONObject msg_extras = new JSONObject();
						msg_extras.put("type", NoticeMsgTypeValue.ACTIVITY.value);
						msg_extras.put("parameter", activity.getId());
						msg_extras.put("icon", "loudspeaker");
						msg_extras.put("action", NoticeActionTypeValue.SIGN.value);
						pushService.push(activity.getTitle() + "报名",
								activity.getTitle(),msg_extras, Platform.android_ios(),
								new String[] { activity.getUser().getId() },
								new String[] { "","userstart0" });
						
						jr.setMethod("报名成功");
					}
					if(flag==1){//取消报名
						//此处待添加支付退款流程结果凭证，成功则走下面流程
						eventlogService.deleteByUserAndEntityAndFocustypeAndEventtype(userid, activityid, Activity.entityName, "sign");
//						eventlogService.deleteByUserAndActivityAndEventtype(user, activity, "sign");
						jr.setMethod("取消成功");
					}
					jr.setCord(0);
				}
			}
		}
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
	public void activitySignlist(HttpServletResponse response,String id,String type,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("activitysignlist");
		if(id==null||id.isEmpty()||page==null||page<0||size==null||size<0){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			if(id==null||page==null||size==null){
				jr.setCord(-1);
				jr.setMsg("非法传值");
			}else {
				Page<User> list = eventlogService.findUserListByIdAndEventtypeAndActtype(id, "sign", Activity.entityName, page, size);
				jr.setPagenum(page+1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setTotal(list.getTotalElements());
				jr.setCord(0);
				jr.setMsg("获取成功");
				jr.setData(list.getContent());
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
	 * 分享活动
	 * @param response
	 * @param id
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年6月9日 上午10:44:27
	 */
	@RequestMapping(value="shareactivity")
	public void shareActivity(HttpServletResponse response,String id,String type){
		Jr jr = new Jr();
		jr.setMethod("shareactivity");
		if(id==null||id.isEmpty()){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else{
			Activity activity = activityService.findById(id);
			if(activity==null){
				jr.setCord(1);
				jr.setMsg("此活动不存在");
			}else {
				activity.setSharenum(activity.getSharenum() + 1);//更新分享的总数
				activity = activityService.activitySaveOrUpdate(activity);
				jr.setData(activity.getSharenum());
				jr.setCord(0);
				jr.setMsg("分享成功");
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
	 * 绑定活动时使用
	 * @param response
	 * @param page
	 * @param size
	 * @param type
	 * @param listtype  needform 报名表绑定/问卷绑定           needreview    绑定回顾
 	 */
	@RequestMapping(value="getactivitylist")
	public void getactivitylist(HttpServletResponse response,Integer page,Integer size,String type,String listtype){
		Jr jr = new Jr();
		jr.setMethod("getactivitylist");
		if(page==null||page<0||size==null||size<0){
			jr.setCord(-1);//非法传参
		}else {
			Page<Activity> list = activityService.findActivityList(page, size, listtype);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
			jr.setTotal(list.getTotalElements());
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
	 * 搜索活动(web端)
	 * @param response
	 * @param keyword 搜索关键字
	 * @param page 页码，从0开始
	 * @param size 页面大小
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年3月31日 上午10:52:27
	 */
	@RequestMapping(value="searchactivity")
	public void searchActivity(HttpServletResponse response,String keyword,Integer page,Integer size,String type){
		Jr jr = new Jr();
		jr.setMethod("searchactivity");
		if(page==null||page<0||size==null||size<0||keyword==null||keyword.isEmpty()){
			jr.setCord(-1);//非法传参
		}else {
			Page<Activity> list = activityService.searchActByKeyword(keyword, page, size);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
			jr.setMsg("获取成功");
			jr.setData2(keyword);
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
	 * 获取banner广告位活动
	 * @param response
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年4月8日 下午4:45:12
	 */
	@RequestMapping(value="getbanneractlist")
	public void getBannerActList(HttpServletResponse response,String type){
		Jr jr = new Jr();
		jr.setMethod("getbanneractlist");
		try {
			List<Activity> list = activityService.getBannerActList(1);
			jr.setCord(0);
			jr.setData(list);
			jr.setPagenum(1);
			jr.setPagecount(1);
			jr.setPagesum(list.size());
			jr.setTotal(list.size());
		} catch (Exception e) {
			// TODO: handle exception
			jr.setCord(1);
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
	 * 根据单个标签分页查询活动列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param label
	 * Lee.J.Eric
	 * 2014 2014年6月9日 下午1:58:29
	 */
	@RequestMapping(value="getactivitylistbylabel")
	public void getActivityListByLabel(HttpServletResponse response,String type,Integer page,Integer size,Long label){
		Jr jr = new Jr();
		jr.setMethod("getactivitylistbylabel");
		if(page==null||page<0||size==null||size<0||label==null){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			Labels labels = labelsService.findByID(label);
			Page<Activity> list = activityService.getByLabel(labels, page, size);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
			jr.setTotal(list.getTotalElements());
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
	 * 检验用户是否报名活动
	 * @param response
	 * @param type
	 * @param userid
	 * @param activityid
	 * Lee.J.Eric
	 * 2014 2014年4月14日 下午4:09:36
	 */
	@Deprecated
	@RequestMapping(value="checksignforuser")
	public void checkSignForUser(HttpServletResponse response,String type,String userid,String activityid){
		Jr jr = new Jr();
		jr.setMethod("checksignforuser");
		if(userid==null||userid.isEmpty()||activityid==null||activityid.isEmpty()){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}else {
			User user = userService.findById(userid);
			Activity activity = activityService.findById(activityid);
			Boolean flag = activityJoinService.checkByUserAndActivity(user, activity);
			if(!flag){
				jr.setCord(1);
				jr.setMsg("未报名");
			}else {
				jr.setCord(0);
				jr.setMsg("已报名");
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
	 * 已结束活动列表  2.0
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * 2014 2014-09-16
	 */
	@RequestMapping(value="activitylist",method = RequestMethod.POST)
	public void activityList(HttpServletResponse response,String type,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("activitylist");
		if(page==null||page<0||size==null||size<0||type ==null){//非法传值
			jr.setCord(-1);
			jr.setMsg("参数错误");
		}else {
			Page<Activity> list = activityService.getActivityList(page, size, 1);
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("title");
			/*set.add("summary");
			set.add("img");
			set.add("createdate");
			set.add("publishdate");
			set.add("opendate");
			set.add("enddate");
			set.add("opendate");
			set.add("enddate");
			set.add("checktype");
			set.add("address");
			set.add("cost");
			set.add("label.id");
			set.add("label.zname");
			set.add("tel");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("status");
			set.add("maxnum");
			set.add("signnum");
			set.add("joinnum");
			set.add("commentnum");
			set.add("goodnum");
			set.add("sharenum");
			set.add("imgsnum");
			set.add("titleimg");
			set.add("isbanner");
			set.add("huiguurl");
			set.add("price");
			set.add("acttype");
			set.add("acttypetitle");
			set.add("acttypeurl");
			set.add("onsale");
			set.add("pricetype");
			set.add("pricekey");
			set.add("pricevalue1");*/
			jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(),set));
			jr.setPagenum(page+1);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 2.0 发起小活动
	 * @param response
	 * @param gathering
	 * @param type
	 * Lee.J.Eric
	 * 2015年4月8日 下午3:59:39
	 */
	@RequestMapping(value = "creategathering",method = RequestMethod.POST)
	public void createGathering(HttpServletResponse response,Gathering gathering,String type){
		Jr jr = new Jr();
		jr.setMethod("creategathering");
		if(type==null||gathering == null||gathering.getUser()==null||gathering.getSpaceInfo()==null){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			User user = userService.findById(gathering.getUser().getId());
			WorkSpaceInfo spaceInfo=spaceInfoService.find(gathering.getSpaceInfo().getId());
			if(user==null||user.getRoot()>UserRoot.VIP.getCode()||spaceInfo==null){
				if(spaceInfo==null){
					jr.setCord(1);//用户不存在或用户无权限
					jr.setMsg("空间地点不存在");
				}
				jr.setCord(1);//用户不存在或用户无权限
				jr.setMsg("用户不存在或用户无权限");
			}else {
				//需要验证时间是否重叠
				//if(gatheringService.checkTime(gathering.getOpendate(), gathering.getEnddate(),gathering.getId())){
					gathering.setUser(user);//发起人
					gathering.setSpaceInfo(spaceInfo);
					gathering = gatheringService.gatheringSaveOrUpdate(gathering);
					user.setMyactnum(user.getMyactnum()+1);//用户活动发起数+1
					userService.userSaveOrUpdate(user);
					if(gathering==null){
						jr.setCord(1);//新建活动失败
						jr.setMsg("新建失败");
					}else {
						Set<String> set = new HashSet<String>();
						set.add("id");
						set.add("title");
						set.add("summary");
						set.add("createdate");
						set.add("opendate");
						set.add("enddate");
						set.add("address");
						set.add("user.id");
						set.add("user.nickname");
						set.add("user.minimg");
						set.add("style");
						jr.setData(BeanUtil.getFieldValueMap(gathering, set));
						jr.setCord(0);
						jr.setMsg("创建成功");
	
						Eventlog eventlog = new Eventlog();
						eventlog.setGathering(gathering);
						eventlog.setActtype(Gathering.entityName);
						eventlog.setEventtype("create");//是创建
						eventlog.setUser(gathering.getUser());
						eventlog.setUpdatetime(gathering.getOpendate());
						eventlog.setCity(gathering.getSpaceInfo().getSpacecity());
						eventlogService.eventlogSaveOrUpdate(eventlog);
						eventlog.setId(System.currentTimeMillis()+RandomUtil.getRandomeStringForLength(20));
						eventlog.setEventtype("sign");//报名
						eventlogService.eventlogSaveOrUpdate(eventlog);
						//-------push
						//push通知
						JSONObject msg_extras = new JSONObject();
						msg_extras.put("type", Gathering.entityName);
						msg_extras.put("parameter", gathering.getId());
						msg_extras.put("icon", "loudspeaker");
						
						//notification
						pushService.push(gathering.getTitle(),
								"新活动",msg_extras, Platform .android_ios(),
								new String[]{"yiqi"}, new String[] { "version4","userstart0" });
					}
				//}else{
					//jr.setCord(3);//活动时间重叠
					//jr.setMsg("活动时间不可用");
				//}
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
	 * 查看小活动详情 2.0
	 * @param response
	 * @param id
	 * @param type
	 * Lee.J.Eric
	 * 2014年9月10日 下午2:18:08
	 */
	@RequestMapping(value = "viewgathering",method = RequestMethod.POST)
	public void viewGathering(HttpServletResponse response,String id,String type){
		Jr jr = new Jr();
		jr.setMethod("viewgathering");
		if(id==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Gathering gathering = gatheringService.findById(id);
			if(gathering==null){
				jr.setCord(1);
				jr.setMsg("小活动不存在");
			}else {
				Set<String> set = new HashSet<String>();
				set.add("id");
				set.add("title");
				set.add("summary");
				set.add("createdate");
				set.add("opendate");
				set.add("enddate");
				set.add("address");
				set.add("user.id");
				set.add("user.nickname");
				set.add("user.minimg");
				set.add("user.job");//
				set.add("style");
				set.add("status");
				set.add("spaceInfo.id");
				set.add("spaceInfo.spacename");
				set.add("spaceInfo.spacecode");
				set.add("spaceInfo.spacelogo");
				set.add("spaceInfo.spacecity.name");
//				set.add("signuser.id");
//				set.add("signuser.nickname");
//				set.add("signuser.minimg");
//				set.add("imgs");
				jr.setData(BeanUtil.getFieldValueMap(gathering, set));
				jr.setCord(0);
				jr.setMsg("获取成功");
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
	 * 2.0 图集添加图片
	 * @param response
	 * @param id 图集主体id
	 * @param flag 0 小活动，1 活动，2 课程
	 * @param userid 上传人id
	 * @param img 
	 * @param type
	 * Lee.J.Eric
	 * 2014年9月11日 上午10:18:20
	 */
	@RequestMapping(value = "addgalleryimg",method = RequestMethod.POST)
	public void addGalleryImg(HttpServletResponse response,String id,Integer flag,String userid,MultipartFile img,String type){
		Jr jr = new Jr();
		jr.setMethod("addgalleryimg");
		if(id==null||flag==null||userid==null||img==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Gallery gallery = new Gallery();
			User user = userService.findById(userid);
			gallery.setUser(user);
			Random random = new Random();
			Integer i = random.nextInt(R.Common.IMG_TMP.length);//随机选取一个图片目录
			String pre = R.Common.IMG_TMP[i]+"_";//图片前缀
			String base = R.Common.IMG_DIR+R.Common.IMG_TMP[i];//图片目录
			File basedir = new File(base);
			if(!basedir.exists())
				basedir.mkdirs();
			String fileName = pre+"img"+RandomUtil.getRandomeStringForLength(20)+System.currentTimeMillis();
			FileUtil fileUtil = new FileUtil();
			File desFile = new File(basedir, fileName);
			try {
				fileUtil.copyFile(img.getInputStream(), desFile);
				String target1 = fileName+R.Img._640X640;
				String target2 = fileName+R.Img._320X320;
				String target3 = fileName+R.Img._160X160;
				ImageUtil imageUtil = new ImageUtil();
				String targetdir = R.Common.IMG_DIR+pre.substring(0, 1);
				imageUtil.compressPic(targetdir, targetdir, fileName, target1, 640, 640, null, false);
				imageUtil.compressPic(targetdir, targetdir, fileName, target2, 320, 320, null, false);
				imageUtil.compressPic(targetdir, targetdir, fileName, target3, 160, 160, null, false);
				gallery.setImg(fileName);
				gallery.setFlag(flag);//图片类型
				if(flag==0){//小活动
					Gathering gathering = gatheringService.findById(id);
					gallery.setGathering(gathering);
				}
				if (flag==1) {//活动
					Activity activity = activityService.findById(id);
					gallery.setActivity(activity);
				}
				if(flag==2){//课程
					Course course = courseService.findById(id);
					gallery.setCourse(course);
				}
				gallery = galleryService.gallerySaveOrUpdate(gallery);
				Set<String> set = new HashSet<String>();
				set.add("id");
				set.add("img");
				set.add("flag");
				set.add("user.id");
				set.add("user.nickname");
				set.add("user.minimg");
				set.add("user.job");
				set.add("user.sex");
				set.add("createtime");
				
				jr.setData(BeanUtil.getFieldValueMap(gallery, set));
				jr.setCord(0);
				jr.setMsg("上传成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jr.setCord(1);
				jr.setMsg("上传失败");
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
	 * 查看图集 2.0
	 * @param response
	 * @param id
	 * @param flag
	 * @param type
	 * @author Lee.J.Eric
	 * @time 2014年10月13日 下午3:11:42
	 */
	@RequestMapping(value = "viewgallery")
	public void viewGallery(HttpServletResponse response,String id,Integer flag,String type){
		Jr jr = new Jr();
		jr.setMethod("viewgallery");
		if(id==null||flag==null||type==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			List<Gallery> list = galleryService.findByIdAndFlag(id, flag);
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("img");
			set.add("flag");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("user.job");
			set.add("user.sex");
			set.add("createtime");
			
			jr.setPagenum(1);
			jr.setPagecount(1);
			jr.setPagesum(list.size());
			jr.setTotal(list.size());
			jr.setCord(0);
			jr.setMsg("获取成功");
			jr.setData(BeanUtil.getFieldValueMapForList(list, set));
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
	 * 2.0 小活动报名
	 * @param response
	 * @param gatheringid 小活动id
	 * @param userid 用户id
	 * @param flag 报名标记，0报名，1取消报名
	 * @param type
	 * Lee.J.Eric
	 * 2014年9月9日 下午2:28:17
	 */
	@RequestMapping(value = "joingathering",method = RequestMethod.POST)
	public void joinGathering(HttpServletResponse response,String gatheringid,String userid,Integer flag,String type){
		Jr jr = new Jr();
		jr.setMethod("joingathering");
		if(gatheringid==null||userid==null||flag==null){
			jr.setCord(-1);//非法传值
			jr.setMsg("非法传值");
		}else {
			Gathering gathering = gatheringService.findById(gatheringid);
			User user = userService.findById(userid);
			if(gathering==null||user==null){
				jr.setCord(1);
				jr.setMsg("小活动或用户不存在");
			}else if (gathering.getUser().equals(user)) {
				jr.setCord(1);
				jr.setMsg("您是该活动发起人");
			}else {
				if(flag == 0){//报名小活动
					Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, gatheringid, Gathering.entityName, "sign");
					if(eventlog==null)
						eventlog = new Eventlog();
					eventlog.setGathering(gathering);
					eventlog.setActtype(Gathering.entityName);
					eventlog.setEventtype("sign");//是报名
					eventlog.setUser(user);
//					eventlog.setOpentime(gathering.getOpendate());
					eventlog.setUpdatetime(gathering.getOpendate());
					eventlogService.eventlogSaveOrUpdate(eventlog);
					//活动参加数+1
					user.setJoinactnum(user.getJoinactnum()+1);//参数活动数+1
					userService.userSaveOrUpdate(user);
					jr.setMsg("报名成功");
				}
				if (flag == 1) {//取消报名小活动
					eventlogService.deleteByUserAndEntityAndFocustypeAndEventtype(userid, gatheringid, Gathering.entityName, "sign");
//					eventlogService.deleteByUserAndGatheringAndEventtype(user, gathering,"sign");
					jr.setMsg("取消成功");
				}
				
				Set<String> set = new HashSet<String>();
				set.add("id");
				set.add("title");
				set.add("summary");
				set.add("createdate");
				set.add("opendate");
				set.add("address");
				set.add("user.id");
				set.add("user.nickname");
				set.add("user.minimg");
				set.add("style");
				jr.setData(BeanUtil.getFieldValueMap(gathering, set));
				jr.setCord(0);
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
	 * 2.0 参加小活动(签到)
	 * @param response
	 * @param gatheringid
	 * @param userid
	 * @param type
	 * @author Lee.J.Eric
	 * @time 2014年12月5日 上午11:36:46
	 */
	@RequestMapping(value = "attendgathering")
	public void attendGathering(HttpServletResponse response,String gatheringid,String userid,String type){
		 Jr jr = new Jr();
		 jr.setMethod("attendgathering");
		 if(gatheringid==null||userid==null||type==null){
			 jr.setCord(-1);
			 jr.setMsg("非法传值");
		 }else {
			 Gathering gathering = gatheringService.findById(gatheringid);
				User user = userService.findById(userid);
				if(gathering==null||user==null){
					jr.setCord(1);
					jr.setMsg("小活动或用户不存在");
				}else if (gathering.getUser().equals(user)) {
					jr.setCord(1);
					jr.setMsg("您是该活动发起人");
				}else {
					Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, gatheringid, "gathering", "join");
					if(eventlog==null)
						eventlog = new Eventlog();
					eventlog.setGathering(gathering);
					eventlog.setActtype(Gathering.entityName);
					eventlog.setEventtype("join");//是签到
					eventlog.setUser(user);
//					eventlog.setOpentime(gathering.getOpendate());
					eventlog.setUpdatetime(gathering.getOpendate());
					eventlogService.eventlogSaveOrUpdate(eventlog);
					jr.setMsg("签到成功");
					
					Set<String> set = new HashSet<String>();
					set.add("id");
					set.add("title");
					set.add("summary");
					set.add("createdate");
					set.add("opendate");
					set.add("address");
					set.add("user.id");
					set.add("user.nickname");
					set.add("user.minimg");
					set.add("style");
					jr.setData(BeanUtil.getFieldValueMap(gathering, set));
					jr.setCord(0);
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
	 * 查看小活动报名列表 2.0
	 * @param response
	 * @param id
	 * @param type
	 * Lee.J.Eric
	 * 2014年9月10日 下午2:59:59
	 */
	@RequestMapping(value = "gatheringsignlist",method = RequestMethod.POST)
	public void gatheringSignList(HttpServletResponse response,String id,String type,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("gatheringsignlist");
		if(id==null||page==null||size==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Page<User> list = eventlogService.findUserListByIdAndEventtypeAndActtype(id, "sign", Gathering.entityName, page, size);
//			Page<User> list = gatheringService.findSignList(id, page, size);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
			jr.setMsg("获取成功");
			jr.setData(list.getContent());
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
	 * 查看小活动签到列表 2.0
	 * @param response
	 * @param id
	 * @param type
	 * @param page
	 * @param size
	 * @author Lee.J.Eric
	 * @time 2014年12月5日 上午11:41:24
	 */
	@RequestMapping(value = "gatheringjoinlist",method = RequestMethod.POST)
	public void gatheringJoinList(HttpServletResponse response,String id,String type,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("gatheringjoinlist");
		if(id==null||page==null||size==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Page<User> list = eventlogService.findUserListByIdAndEventtypeAndActtype(id, "join", Gathering.entityName, page, size);
//			Page<User> list = gatheringService.findSignList(id, page, size);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
			jr.setMsg("获取成功");
			jr.setData(list.getContent());
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
	 * 2.0 更新小活动
	 * @param response
	 * @param gathering
	 * @param open
	 * @param type
	 * Lee.J.Eric
	 * 2014年9月9日 下午3:30:29
	 */
	@RequestMapping(value = "updategathering",method = RequestMethod.POST)
	public void updateGathering(HttpServletResponse response,Gathering gathering,String open,String type){
		Jr jr = new Jr();
		jr.setMethod("updategathering");
		if(gathering == null||gathering.getUser()==null){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			Gathering dbGathering = gatheringService.findById(gathering.getId());
			User user = userService.findById(gathering.getUser().getId());
			if(dbGathering==null||user==null){
				jr.setCord(1);//活动或用户不存在
				jr.setMsg("活动或用户不存在");
			}else if (!dbGathering.getUser().equals(user)) {
				jr.setCord(2);
				jr.setMsg("您无权限修改");
			}else {
				//先检测时间是否可用
				if(gathering.getEnddate()!=null&&gathering.getOpendate()!=null){
					//if(gatheringService.checkTime(gathering.getOpendate(), gathering.getEnddate(),gathering.getId())){
					dbGathering.setOpendate(gathering.getOpendate());
					dbGathering.setEnddate(gathering.getEnddate());
				}
						if(StringUtils.isNotBlank(gathering.getAddress()))
							dbGathering.setAddress(gathering.getAddress());
						if(StringUtils.isNotBlank(gathering.getDeltext()))
							dbGathering.setDeltext(gathering.getDeltext());
						if(StringUtils.isNotBlank(gathering.getSummary()))
							dbGathering.setSummary(gathering.getSummary());
						if(StringUtils.isNotBlank(gathering.getTitle()))
							dbGathering.setTitle(gathering.getTitle());
						dbGathering.setStyle(gathering.getStyle());
						if(gathering.getSpaceInfo()!=null){
							WorkSpaceInfo spaceInfo=spaceInfoService.find(gathering.getSpaceInfo().getId());
							if(spaceInfo!=null){
								dbGathering.setSpaceInfo(spaceInfo);
							}
						}
						dbGathering = gatheringService.gatheringSaveOrUpdate(dbGathering);
						if(dbGathering==null){
							jr.setCord(1);//修改活动失败
							jr.setMsg("修改失败");
						}else {
							Set<String> set = new HashSet<String>();
							set.add("id");
							set.add("title");
							set.add("summary");
							set.add("createdate");
							set.add("opendate");
							set.add("enddate");
							set.add("address");
							set.add("user.id");
							set.add("user.nickname");
							set.add("user.minimg");
							set.add("style");
							jr.setData(BeanUtil.getFieldValueMap(dbGathering, set));
							jr.setCord(0);
							jr.setMsg("更新成功");
						}
					/*}else{
						//时间不可用
						jr.setCord(3);
						jr.setMsg("时间不可用");
					}
				}else{
					//非法传参
					jr.setCord(-1);//非法传参
					jr.setMsg("非法传参");
				}*/
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
	 * 2.0 删除小活动
	 * @param response
	 * @param gathering
	 * @param type
	 * Lee.J.Eric
	 * 2014年9月11日 下午2:58:06
	 */
	@RequestMapping(value = "delgathering",method = RequestMethod.POST)
	public void delGathering(HttpServletResponse response,Gathering gathering,String type){
		Jr jr = new Jr();
		jr.setMethod("delgathering");
		if(gathering ==null||gathering.getId()==null||gathering.getUser()==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Gathering db = gatheringService.findById(gathering.getId());
			if(db==null){
				jr.setCord(1);
				jr.setMsg("此活动不存在");
			}else {
				User user = userService.findById(gathering.getUser().getId());
				if(!db.getUser().equals(user)){
					jr.setCord(1);
					jr.setMsg("非发起人无权限");
				}else {
					db.setDeltext(gathering.getDeltext());
					db.setIsdel(1);
					gatheringService.deleteGathering(db);
					jr.setCord(0);
					jr.setMsg("删除成功");
					//----------给报名人发送push
					List<User> users = eventlogService.getSignListByGathering(db.getId());
					String[] uarr = new String[users.size()];
					int i = 0;
					for (User u : users) {
						uarr[i] = u.getId();
						i++;
					}
					JSONObject msg_extras = new JSONObject();
					msg_extras.put("type", Gathering.entityName);
					msg_extras.put("parameter", db.getId());
					msg_extras.put("icon", "loudspeaker");
					pushService.push(db.getDeltext(), "活动变更："+db.getTitle(),msg_extras, Platform.android_ios(), uarr, new String[]{"","userstart0"});
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
	 * 2.0 评论小活动
	 * @param response
	 * @param gComment
	 * @param type
	 * Lee.J.Eric
	 * 2014年9月9日 下午4:51:47
	 */
	@RequestMapping(value = "commentgathering",method = RequestMethod.POST)
	public void commentGathering(HttpServletResponse response,GComment gComment,String type){
		Jr jr = new Jr();
		jr.setMethod("commentgathering");
		if(gComment==null||gComment.getUser()==null||gComment.getReceiver()==null||gComment.getGathering()==null){
			jr.setCord(-1);//非法传值
			jr.setMsg("非法传值");
		}else {
			User user = userService.findById(gComment.getUser().getId());
			User receiver = userService.findById(gComment.getReceiver().getId());
			Gathering gathering = gatheringService.findById(gComment.getGathering().getId());
			if(user==null||receiver==null||gathering==null){
				jr.setCord(1);
				jr.setMsg("用户或评论主体不存在");
			}else {
				gComment = gCommentService.gCommentSaveOrUpdate(gComment);
				String pubu_contents = user.getNickname()+"在你的小活动\""+gathering.getTitle()+"\"中发了一条评论";//发起人消息
				String rece_contents = user.getNickname()+"在小活动\""+gathering.getTitle()+"\"中回复了你";//接收人消息
				
				Boolean publisher_flag = false;//发起方标志
				Boolean receiver_flag = false;//接收方标志
				
				if(gComment.getUser().getId().equals(gComment.getReceiver().getId())){//B@B
					if(!gComment.getUser().getId().equals(gathering.getUser().getId())){//B!=A
						publisher_flag = true;
					}
				}else {//B@C
					if(gComment.getUser().getId().equals(gathering.getUser().getId())){//B=A
						receiver_flag = true;
					}else {
						publisher_flag = true;
						if(!gComment.getReceiver().getId().equals(gathering.getUser().getId())){//C!=A
							receiver_flag = true;
						}
					}
				}
				
				if(publisher_flag){
					NoticeMsg noticeMsg=new NoticeMsg();
					noticeMsg.setActiontype(NoticeActionTypeValue.ANSWER.value);
					noticeMsg.setIcon("loudspeaker");
					noticeMsg.setMsgtype(NoticeMsgTypeValue.GATHERING.value);
					noticeMsg.setParam(gathering.getId());
					noticeMsg.setContents(pubu_contents);
					Set<User> user_tag=new HashSet<User>();
					user_tag.add(gathering.getUser());
					noticeMsg.setTags(user_tag);
					noticeMsgService.saveOrUpdate(noticeMsg);
					//push
					
					JSONObject msg_extras = new JSONObject();
					msg_extras.put("type", NoticeMsgTypeValue.GATHERING.value);
					msg_extras.put("parameter", gathering.getId());
					msg_extras.put("icon", "loudspeaker");
					msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
					pushService.push(pubu_contents,gathering.getTitle(),msg_extras, Platform.android_ios(), 
							new String[]{gathering.getUser().getId()},
							new String[]{"version4","userstart0"});
				}
				if(receiver_flag){
					NoticeMsg noticeMsg=new NoticeMsg();
					noticeMsg.setActiontype(NoticeActionTypeValue.ANSWER.value);
					noticeMsg.setIcon("loudspeaker");
					noticeMsg.setMsgtype(NoticeMsgTypeValue.GATHERING.value);
					noticeMsg.setParam(gathering.getId());
					noticeMsg.setContents(rece_contents);
					Set<User> user_tag=new HashSet<User>();
					user_tag.add(receiver);
					noticeMsg.setTags(user_tag);
					noticeMsgService.saveOrUpdate(noticeMsg);
					//push
					JSONObject msg_extras = new JSONObject();
					msg_extras.put("type", NoticeMsgTypeValue.GATHERING.value);
					msg_extras.put("parameter", gathering.getId());
					msg_extras.put("icon", "loudspeaker");
					msg_extras.put("action", NoticeActionTypeValue.ANSWER.value);
					pushService.push(rece_contents,gathering.getTitle(),msg_extras, Platform.android_ios(), 
							new String[]{receiver.getId()},
							new String[]{"version4","userstart0"});
				}
				
				Set<String> set = new HashSet<String>();
				set.add("id");
				set.add("user.id");
				set.add("user.nickname");
				set.add("user.minimg");
				set.add("receiver.id");
				set.add("receiver.nickname");
				set.add("receiver.minimg");
				set.add("gathering.id");
				set.add("gathering.title");
				set.add("createdate");
				set.add("texts");
				jr.setData(BeanUtil.getFieldValueMap(gComment, set));
				jr.setCord(0);
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
	 * 2.0 小活动评论列表
	 * @param response
	 * @param page
	 * @param size
	 * @param orderby
	 * @param type
	 * @param id
	 * Lee.J.Eric
	 * 2014年9月9日 下午5:46:19
	 */
	@RequestMapping(value = "gcommentlist",method = RequestMethod.POST)
	public void gCommentList(HttpServletResponse response,Integer page,Integer size,Integer orderby,String type,String id){
		Jr jr = new Jr();
		jr.setMethod("gcommentlist");
		if(page==null||size==null||id==null||orderby==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Page<GComment> list = gCommentService.findGCommentForGahering(page, size, orderby, id);
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("receiver.id");
			set.add("receiver.nickname");
			set.add("receiver.minimg");
			set.add("gathering.id");
			set.add("gathering.title");
			set.add("createdate");
			set.add("texts");
			jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
			jr.setPagenum(page+1);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 课程创建 2.0
	 * @param response
	 * @param course
	 * @param userid 操作人
	 * @param open
	 * @param end
	 * @param end
	 * @param type
	 * @author Lee.J.Eric
	 * @time 2014年10月14日 下午4:11:01
	 */
	@Transactional
	@RequestMapping(value = "createcourse")
	public void createCourse(HttpServletResponse response,Course course,String labels,String userid,String open,String end,String type){
		Jr jr = new Jr();
		jr.setMethod("createcourse");
		if(course==null||open==null||end==null||type==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User user = userService.findById(userid);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("此用户不存在");
			}else if (user.getRoot()>1) {
				jr.setCord(1);
				jr.setMsg("此用户无权限");
			}else {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					course.setLabel(labelsService.getLabelsByids(labels));
					course.setEnddate(format.parse(end));
					course.setOpendate(format.parse(open));
					course.setUser(user);
					if(course.getPricetype()==1){
						List<Integer> lIntegers=new ArrayList<Integer>();
						String[] arrs=course.getPricevalue().split(",");
						for (String s : arrs) {
							lIntegers.add(Integer.parseInt(s));
						}
						course.setMinprice(Collections.min(lIntegers));
						course.setMaxprice(Collections.max(lIntegers));
					}else{
						course.setMinprice(course.getPrice());
						course.setMaxprice(course.getPrice());
					}
					course = courseService.courseSaveOrUpdate(course);
					//事件记录
					Eventlog eventlog = new Eventlog();
					eventlog.setCourse(course);
					eventlog.setActtype(Course.entityName);
					eventlog.setEventtype("create");//是创建
					eventlog.setUser(user);
					eventlog.setUpdatetime(course.getOpendate());
					eventlogService.eventlogSaveOrUpdate(eventlog);
					course.setOnsale(0);
					course.setChecktype(1);
					if(course.getCost()==1&&course.getMaxnum()>0){//收费课程，课程券库存量入库
						//查询商品大类
						ItemClass itemClass = itemService.findByCode(Course.entityName);
						//生成具体课程的券
						SKU sku = new SKU();
						sku.setId(String.format("%d-%d", itemClass.getId(), System.currentTimeMillis()));
						sku.setItemClass(itemClass);
						sku.setStatus(0);
						sku.setName(course.getTitle());
						sku.setCode("course-"+System.currentTimeMillis());
						sku.setDefaultPrice(Double.valueOf(course.getPrice()));
						sku.setMemberPrice(Double.valueOf(course.getVprice()));
						sku.setMultPrice(course.getPricevalue());
						skuService.saveOrUpdate(sku);
						course.setSkuid(sku.getId());//回填，关联课程
						//课程券库存量
						SkuInventory inventory = new SkuInventory();
						inventory.setCreateTime(new Timestamp(System.currentTimeMillis()));
						inventory.setModifyTime(new Timestamp(System.currentTimeMillis()));
						inventory.setId(sku.getId());
						inventory.setIsUnlimited(course.getMaxnum()>0?false:true);
						inventory.setAmount(course.getMaxnum());
						skuInventoryService.saveOrUpdate(inventory);
						
					}
					//活动数据入库
					course = courseService.courseSaveOrUpdate(course);
					//push通知
					JSONObject msg_extras = new JSONObject();
					msg_extras.put("type", Course.entityName);
					msg_extras.put("parameter", course.getId());
					msg_extras.put("icon", "loudspeaker");
					
					//notification
					pushService.push(course.getTitle(),
							"用户课程",msg_extras, Platform .android_ios(),
							new String[]{"yiqi"}, new String[] { "","userstart0" });
					jr.setData(course);
					jr.setCord(0);
					jr.setMsg("获取成功");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
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
	 * 报名课程 2.0
	 * @param response
	 * @param type
	 * @param courseid 课程id
	 * @param userid 用户id
	 * @param flag 报名标记，0报名，1取消报名
	 * @author Lee.J.Eric
	 * @time 2014年10月16日 下午2:47:48
	 */
	@Deprecated
	@RequestMapping(value = "signcourse")
	public void signCourse(HttpServletResponse response,String type,String courseid,String userid,Integer flag){
		Jr jr = new Jr();
		jr.setMethod("signcourse");
		if(type==null||courseid==null||userid==null||flag==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Course course = courseService.findById(courseid);
			User user = userService.findById(userid);
			if(course==null||user==null){
				jr.setCord(1);
				jr.setMsg("课程或用户不存在");
			}else {
				Date now = new Date();
				if(now.after(course.getOpendate())){
					jr.setCord(1);
					jr.setMsg("活动已开启!");
				}else {
					if(flag==0){//报名
						
						Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, courseid, Course.entityName, "sign");
						if(eventlog==null)
							eventlog = new Eventlog();
						eventlog.setCourse(course);
						eventlog.setActtype(Course.entityName);
						eventlog.setEventtype("sign");
						eventlog.setUser(user);
//						eventlog.setOpentime(course.getOpendate());
						eventlog.setUpdatetime(course.getOpendate());
						eventlogService.eventlogSaveOrUpdate(eventlog);
						jr.setMethod("报名成功");
					}
					if(flag==1){//取消报名
						eventlogService.deleteByUserAndEntityAndFocustypeAndEventtype(userid, courseid, Course.entityName, "sign");
//						eventlogService.deleteByUserAndCourseAndEventtype(user, course, "sign");
						jr.setMethod("取消成功");
					}
					jr.setCord(0);
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
	 * 课程评论 2.0
	 * @param response
	 * @param cComment
	 * @param type
	 * @author Lee.J.Eric
	 * @time 2014年10月14日 下午5:55:25
	 */
	@RequestMapping(value = "createccomment")
	public void createCComment(HttpServletResponse response,CComment cComment,String type){
		Jr jr = new Jr();
		jr.setMethod("createccomment");
		if(cComment==null||cComment.getCourse()==null||cComment.getUser()==null||cComment.getReceiver()==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User receiver = userService.findById(cComment.getReceiver().getId());
			User user = userService.findById(cComment.getUser().getId());
			Course course = courseService.findById(cComment.getCourse().getId());
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("receiver.id");
			set.add("receiver.nickname");
			set.add("receiver.minimg");
			set.add("course.id");
			set.add("course.title");
			set.add("createdate");
			set.add("texts");
			cComment.setReceiver(receiver);
			cComment.setUser(user);
			cComment.setCourse(course);
			cComment = cCommentService.cCommentSaveOrUpdate(cComment);
			jr.setData(BeanUtil.getFieldValueMap(cComment, set));
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
	 * 课程评论列表 2.0
	 * @param response
	 * @param page
	 * @param size
	 * @param orderby 时间排序，1正序，-1倒序
	 * @param type
	 * @param id 课程id
	 * @author Lee.J.Eric
	 * @time 2014年10月20日 上午10:43:19
	 */
	@RequestMapping(value = "ccommentlist")
	public void cCommentList(HttpServletResponse response,Integer page,Integer size,Integer orderby,String type,String id){
		Jr jr = new Jr();
		jr.setMethod("ccommentlist");
		if(page==null||size==null||orderby==null||type==null||id==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Page<CComment> list = cCommentService.findCCommentForCourse(page, size, orderby, id);
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("receiver.id");
			set.add("receiver.nickname");
			set.add("receiver.minimg");
			set.add("course.id");
			set.add("course.title");
			set.add("createdate");
			set.add("texts");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 课程学员列表 2.0
	 * @param response
	 * @param type
	 * @param id 课程id
	 * @param page
	 * @param size
	 * @author Lee.J.Eric
	 * @time 2014年10月20日 上午11:08:55
	 */
	@RequestMapping(value = "coursestudentlist")
	public void courseStudentList(HttpServletResponse response,String type,String id,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("coursestudentlist");
		if(type==null||id==null||page==null||size==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Course course = courseService.findById(id);
			if(course==null){
				jr.setCord(1);
				jr.setMsg("课程不存在");
			}else if(course.getOpendate().after(new Date())){
				jr.setCord(1);
				jr.setMsg("课程未开始");
			}else {
				Page<User> list = eventlogService.findCourseStudentList(id, page, size);
				jr.setPagenum(page+1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setTotal(list.getTotalElements());
				jr.setCord(0);
				jr.setMsg("获取成功");
				jr.setData(list.getContent());
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
	 * 查看课程详情 2.0
	 * @param response
	 * @param id
	 * @param type
	 * @author Lee.J.Eric
	 * @time 2014年10月20日 下午2:17:06
	 */
	@RequestMapping(value = "viewcourse")
	public void viewCourse(HttpServletResponse response,String id,String type){
		Jr jr = new Jr();
		jr.setMethod("viewcourse");
		if(id==null||type==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Course course = courseService.findById(id);
			if(course==null){
				jr.setCord(1);
				jr.setMsg("课程不存在");
			}else {
				jr.setData(course);
				jr.setCord(0);
				jr.setMsg("获取成功");
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
	 * 检查用户是否报名小活动
	 * @param response
	 * @param type
	 * @param userid
	 * @param gatheringid
	 * @author Lee.J.Eric
	 * @time 2014年11月12日 下午2:41:29
	 */
	@RequestMapping(value = "checkgatheringforuser")
	public void checkGatheringSignUser(HttpServletResponse response,String type,String userid,String gatheringid){
		Jr jr = new Jr();
		jr.setMethod("checkgatheringforuser");
		if(type==null||userid==null||gatheringid==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else{
//			User user = userService.findById(userid);
//			Gathering gathering = gatheringService.findById(gatheringid);
			Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, gatheringid, Gathering.entityName, "sign");
			if(eventlog==null){
				jr.setData(0);
				jr.setMsg("未报名");
			}else {
				jr.setData(1);
				jr.setMsg("已报名");
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
	 * 检查用户是否报名活动
	 * @param response
	 * @param type
	 * @param userid
	 * @param activityid
	 * @author Lee.J.Eric
	 * @time 2014年12月30日 上午10:30:48
	 */
	@Deprecated
	@RequestMapping(value = "checkactivitysignuser")
	public void checkActivitySignUser(HttpServletResponse response,String type,String userid,String activityid){
		Jr jr = new Jr();
		jr.setMethod("checkcourseforuser");
		if(type==null||userid==null||activityid==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else{
			Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, activityid, Activity.entityName, "sign");
			if(eventlog==null){
				jr.setData(0);
				jr.setMsg("未报名");
			}else {
				jr.setData(1);
				jr.setMsg("已报名");
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
	 * 检查用户是否报名课程
	 * @param response
	 * @param type
	 * @param userid
	 * @param courseid
	 * @author Lee.J.Eric
	 * @time 2014年11月12日 下午2:53:22
	 */
	@Deprecated
	@RequestMapping(value = "checkcourseforuser")
	public void checkCourseSignUser(HttpServletResponse response,String type,String userid,String courseid){
		Jr jr = new Jr();
		jr.setMethod("checkcourseforuser");
		if(type==null||userid==null||courseid==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else{
//			User user = userService.findById(userid);
//			Course course = courseService.findById(courseid);
			Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, courseid, Course.entityName, "sign");
			if(eventlog==null){
				jr.setData(0);
				jr.setMsg("未报名");
			}else {
				jr.setData(1);
				jr.setMsg("已报名");
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
	 * 更新课程 2.0
	 * @param response
	 * @param course
	 * @param userid
	 * @param open
	 * @param end
	 * @param type
	 * @author Lee.J.Eric
	 * @time 2014年10月20日 下午2:39:24
	 */
	@RequestMapping(value = "updatecourse")
	public void updateCourse(HttpServletResponse response,Course course,String userid,String open,String end,String type){
		Jr jr = new Jr();
		jr.setMethod("updatecourse");
		if(course==null||course.getId()==null||userid==null||type==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User user = userService.findById(userid);
			Course dbCourse = courseService.findById(course.getId());
			if(user==null){
				jr.setCord(1);
				jr.setMsg("此用户不存在");
			}else if (user.getRoot()>1) {
				jr.setCord(1);
				jr.setMsg("此用户无权限");
			}else if(dbCourse==null){
				jr.setCord(1);
				jr.setMsg("课程不存在");
			}else {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					if(open==null){
						course.setOpendate(dbCourse.getOpendate());
					}else {
						course.setOpendate(format.parse(open));
					}
					if(end==null){
						course.setEnddate(dbCourse.getEnddate());
					}else{
						course.setEnddate(format.parse(end));
					}
					course = courseService.courseSaveOrUpdate(course);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					log.error("update course failed:"+e);
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
	 * 关注
	 * @param response
	 * @param type 客户端标识
	 * @param userid 用户id
	 * @param id 主体id
	 * @param acttype 主体类型,activity:活动，gathering：小活动,course：课程
	 * @param flag  关注标记，0关注，1取消关注
	 * @author Lee.J.Eric
	 * @time 2014年12月29日 上午10:29:56
	 */
	@RequestMapping(value = "focus")
	public void focus(HttpServletResponse response,String type,String userid,String id,String acttype,Integer flag){
		Jr jr = new Jr();
		jr.setMethod("focus");
		if(type==null||userid==null||id==null||acttype==null||flag==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User user = userService.findById(userid);
			if (flag==0) {
				Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, id,acttype.toLowerCase(),"focus");
				if(eventlog==null)
					eventlog = new Eventlog();
				if(acttype.equalsIgnoreCase(Gathering.entityName)){
					Gathering gathering = gatheringService.findById(id);
					if(user==null||gathering==null){
						jr.setCord(1);
						jr.setMsg("用户或小活动不存在");
					}else {
						eventlog.setGathering(gathering);
						eventlog.setUpdatetime(gathering.getOpendate());
					}
				}else if (acttype.equalsIgnoreCase(Activity.entityName)) {
					Activity activity = activityService.findById(id);
					if(user==null||activity==null){
						jr.setCord(1);
						jr.setMsg("用户或活动不存在");
					}else {
						eventlog.setActivity(activity);
						eventlog.setUpdatetime(activity.getOpendate());
					}
				}else if (acttype.equalsIgnoreCase(Course.entityName)) {
					Course course = courseService.findById(id);
					if(user==null||course==null){
						jr.setCord(1);
						jr.setMsg("用户或课程不存在");
					}else {
						eventlog.setCourse(course);
						eventlog.setUpdatetime(course.getOpendate());
					}
				}
				eventlog.setActtype(acttype);
				eventlog.setUser(user);
				eventlog.setEventtype("focus");//是关注
				eventlogService.eventlogSaveOrUpdate(eventlog);
				jr.setCord(0);
				jr.setMsg("关注成功");
			}else {
				eventlogService.deleteByUserAndEntityAndFocustypeAndEventtype(userid, id, acttype, "focus");
				jr.setCord(0);
				jr.setMsg("取消成功");
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
	 * 检查用户是否关注
	 * @param response
	 * @param type 客户端标识
	 * @param userid 用户id
	 * @param id 主体id 
	 * @param acttype 主体类型,activity:活动，gathering：小活动,course：课程
	 * @author Lee.J.Eric
	 * @time 2014年12月29日 下午4:15:56
	 */
	@RequestMapping(value = "checkfocus")
	public void checkFocus(HttpServletResponse response,String type,String userid,String id,String acttype){
		Jr jr = new Jr();
		jr.setMethod("checkfocus");
		if(type==null||userid==null|id==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, id, acttype, "focus");
			if(eventlog==null){
				jr.setData(0);
				jr.setMsg("未关注");
			}else {
				jr.setData(1);
				jr.setMsg("已关注");
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
	 * 报名
	 * 2.1修改
	 * @param response
	 * @param type
	 * @param id
	 * @param acttype
	 * @param userid
	 * @param tel
	 * @param name
	 * @param questions
	 * @param flag 报名标记，0报名，1取消报名
	 * @author Lee.J.Eric
	 * @time 2015年03月31日 上午11:20:36
	 */
	@RequestMapping(value = "signup")
	public void signup(HttpServletResponse response,String type,String id,String acttype,String userid,String tel,String name,String email,String questions,Integer flag){
		Jr jr = new Jr();
		jr.setMethod("signup");
		if(type==null|| id ==null||acttype==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			try {
				User user = null;
				if(userid!=null){
					user = userService.findById(userid);
				}
				if(user == null )//没传userid过来，可能是分享出去的
					user = userService.findByTelnum(tel);//先查询此手机号对应的用户
				if(user == null)
					user = userService.getByEmail(email);
				if(user == null){//查无此人，新建以此手机号的用户，权限为4注册用户
					user = new User();
					user.setTelnum(tel);
					user.setNickname(name);
					user.setEmail(email);
					user.setPassword(MD5Util.encrypt(tel));
					user = userService.userSaveOrUpdate(user);
				}
				String idtype = "";
				if(id.contains(",")){
					idtype = "multi";
				}else {
					idtype = "single";
				}

				if (acttype.equals(Activity.entityName)) {//大活动
					jr = activityService.signActivity(type,user,jr,id,idtype,questions);

				}else if (acttype.equals(Course.entityName)) {//课程

				}else if(acttype.equals(Gathering.entityName)){//小活动
					Gathering gathering = gatheringService.findById(id);
					if(gathering==null||user==null){
						jr.setCord(1);
						jr.setMsg("小活动或用户不存在");
					}else if (gathering.getUser().equals(user)) {
						jr.setCord(1);
						jr.setMsg("您是该活动发起人");
					}else {
						//报名小活动
						Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, id, "gathering", "sign");
						if(eventlog==null){//未报名
							if(flag==0){//报名
								eventlog = new Eventlog();
								eventlog.setGathering(gathering);
								eventlog.setActtype(Gathering.entityName);
								eventlog.setEventtype("sign");//是报名
								eventlog.setCity(gathering.getSpaceInfo().getSpacecity());
								eventlog.setUser(user);
								eventlog.setUpdatetime(gathering.getOpendate());
								eventlogService.eventlogSaveOrUpdate(eventlog);

								user.setJoinactnum(user.getJoinactnum() + 1);//参数活动数+1
								jr.setCord(0);
								jr.setMsg("报名成功");
							}else if(flag==1) {//取消报名
								jr.setCord(1);
								jr.setMsg("尚未报名");
							}
						}else {//已报名
							if(flag==0){//报名
								jr.setCord(1);
								jr.setMsg("已报名");
							}else if(flag==1){//取消报名
								eventlogService.deleteByUserAndEntityAndFocustypeAndEventtype(userid, gathering.getId(), Gathering.entityName, "sign");
								user.setJoinactnum(user.getJoinactnum()-1);//参数活动数-1
								jr.setCord(0);
								jr.setMsg("取消成功");
							}
						}
					}
				}
				userService.userSaveOrUpdate(user);

			} catch (Exception e) {
				// TODO: handle exception
				jr.setCord(1);
				jr.setMsg("报名失败");
				e.printStackTrace();
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
	 * 报名----待修改
	 * @param response
	 * @param type
	 * @param id
	 * @param acttype
	 * @param userid
	 * @param tel
	 * @param name
	 * @param questions
	 * @param p 多价格中报名的价格序号(从0开始)
	 * @author Lee.J.Eric
	 * @time 2014年12月31日 上午11:20:36
	 */
	@RequestMapping(value = "sign")
	public void sign(HttpServletResponse response,String type,String id,String acttype,String userid,String tel,String name,String questions,Integer p){
		Jr jr = new Jr();
		jr.setMethod("sign");
		if(type==null||id==null||acttype==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			try {
				User user = null;
				if(userid!=null){
					user = userService.findById(userid);
				}
				if(user == null )//没传userid过来，可能是分享出去的
					user = userService.findByTelnum(tel);//先查询此手机号对应的用户
				if(user == null){//查无此人，新建以此手机号的用户，权限为3(普通非会员用户)
					user = new User();
					user.setTelnum(tel);
					user.setNickname(tel);
					user.setPassword(MD5Util.encrypt(tel));
					user = userService.userSaveOrUpdate(user);
				}
				
				if (acttype.equals(Activity.entityName)) {
					Activity activity = activityService.findById(id);
					if(activity==null){
						jr.setCord(1);
						jr.setMsg("此活动不存在");
					}else if (activity.getEnddate().before(new Date())) {
						jr.setCord(1);
						jr.setMsg("此活动已结束");
					}else {
						if(activity.getMaxnum()>eventlogService.countEntityAndActtypeAndEventType(activity.getId(), Activity.entityName, "sign")){//未达报名上限人数
							Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(user.getId(), id, Activity.entityName, "sign");
							if(eventlog!=null){
								jr.setCord(1);
								jr.setMsg("您已报名过此活动");
							}else {
								if(activity.getCost()==0){//免费
									jr.setData2(0);//免费标记
									activityService.signActivity(userid, questions, id);
									jr.setMethod("报名成功");
								}else {//收费
									jr.setData2(1);//收费标记
									if (p!=null&&p==1&&user.getRoot()>UserRoot.VIP.getCode()) {//选会员价格报名&非会员
										jr.setCord(1);
										jr.setMsg("非会员无权限以会员身份报名");
									}else {
										//查询活动券库存,此处的商品id（skuid）即是库存表的id
										SkuInventory inventory = skuInventoryService.findById(activity.getSkuid());
										if(!inventory.getIsUnlimited()&&inventory.getAmount()<1){//有限&无库存
											jr.setCord(1);
											jr.setMsg("报名人数已达上限");
										}else {
											Calendar c = Calendar.getInstance();
											c.setTime(activity.getOpendate());
//											c.add(Calendar.HOUR, 2);
											Order order =orderService.createOrder(questions,"您报名的活动:"+activity.getTitle(),Activity.entityName,activity.getId(), p, user, activity.getSkuid(), 1,c.getTime(), inventory);
											jr = alipayService.applyAlipay(type, jr, order);
											
										}
									}
								}
							}
						}else {
							jr.setCord(1);
							jr.setMsg("报名人数已达上限");
						}
					}
				}else if (acttype.equals(Course.entityName)) {
					Course course = courseService.findById(id);
					if(course==null||user==null){
						jr.setCord(1);
						jr.setMsg("课程或用户不存在");
					}else if (course.getEnddate().before(new Date())) {
						jr.setCord(1);
						jr.setMsg("此课程已结束");
					}else {
						if(course.getMaxnum()>eventlogService.countEntityAndActtypeAndEventType(course.getId(), Course.entityName, "sign")){//未达报名上限人数
							Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(user.getId(), id, Course.entityName, "sign");
							if(eventlog!=null){
								jr.setCord(1);
								jr.setMsg("您已报名过此活动");
							}else {
								if(course.getCost()==0){//免费
									jr.setData2(0);//免费标记
									courseService.singCourse(userid, questions, id);
									jr.setMethod("报名成功");
								}else {//收费
									jr.setData2(1);//收费标记
									if (p!=null&&p==1&&user.getRoot()>UserRoot.VIP.getCode()) {//选会员价格报名&非会员
										jr.setCord(1);
										jr.setMsg("非会员无权限以会员身份报名");
									}else {
										//查询活动券库存,此处的商品id（skuid）即是库存表的id
										SkuInventory inventory = skuInventoryService.findById(course.getSkuid());
										if(!inventory.getIsUnlimited()&&inventory.getAmount()<1){//有限&无库存
											jr.setCord(1);
											jr.setMsg("报名人数已达上限");
										}else {
											Calendar c = Calendar.getInstance();
											c.setTime(course.getOpendate());
//											c.add(Calendar.HOUR, 2);
											Order order =orderService.createOrder(questions,"您报名的课程:"+course.getTitle(),Course.entityName,course.getId(), p, user, course.getSkuid(), 1,c.getTime(), inventory);
											jr = alipayService.applyAlipay(type, jr, order);
											
										}
									}
								}
							}
						}else {
							jr.setCord(1);
							jr.setMsg("报名人数已达上限");
						}
					}
					
				}else if(acttype.equals(Gathering.entityName)){
					Gathering gathering = gatheringService.findById(id);
					if(gathering==null||user==null){
						jr.setCord(1);
						jr.setMsg("小活动或用户不存在");
					}else if (gathering.getUser().equals(user)) {
						jr.setCord(1);
						jr.setMsg("您是该活动发起人");
					}else {
						//报名小活动
						Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, id, "gathering", "sign");
						if(eventlog==null)
							eventlog = new Eventlog();
						eventlog.setGathering(gathering);
						eventlog.setActtype(Gathering.entityName);
						eventlog.setEventtype("sign");//是报名
						eventlog.setUser(user);
						eventlog.setUpdatetime(gathering.getOpendate());
						eventlogService.eventlogSaveOrUpdate(eventlog);
						
						jr.setCord(0);
						jr.setData(0);
						jr.setMsg("报名成功");
					}
				}
				//课程、活动、小活动的报名都活动参加数+1
				user.setJoinactnum(user.getJoinactnum()+1);//参数活动数+1
				userService.userSaveOrUpdate(user);
				
			} catch (Exception e) {
				// TODO: handle exception
				jr.setCord(1);
				jr.setMsg("报名失败");
				e.printStackTrace();
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
	 * 检查用户是否报名
	 * @param response
	 * @param type 客户端标识
	 * @param userid 用户id
	 * @param id 主体id 
	 * @param acttype 主体类型,activity:活动，gathering：小活动,course：课程
	 * @author Lee.J.Eric
	 * @time 2014年12月31日 上午11:08:03
	 */
	@RequestMapping(value = "checksign")
	public void checkSign(HttpServletResponse response,String type,String userid,String id,String acttype){
		Jr jr = new Jr();
		jr.setMethod("checksign");
		if(type==null||userid==null|id==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, id, acttype, "sign");
			if(eventlog==null){
				jr.setData(0);
				jr.setMsg("未报名");
			}else {
				jr.setData(1);
				jr.setMsg("已报名");
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
	 * 检查用户是否签到
	 * @param response
	 * @param type 客户端标识
	 * @param userid 用户id
	 * @param id 主体id 
	 * @param acttype 主体类型,activity:活动，gathering：小活动,course：课程
	 * @author Lee.J.Eric
	 * @time 2014年12月30日 下午3:50:39
	 */
	@RequestMapping(value = "checkjoin")
	public void checkJoin(HttpServletResponse response,String type,String userid,String id,String acttype){
		Jr jr = new Jr();
		jr.setMethod("checkfocus");
		if(type==null||userid==null|id==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, id, acttype, "join");
			if(eventlog==null){
				jr.setData(0);
				jr.setMsg("未签到");
			}else {
				jr.setData(1);
				jr.setMsg("已签到");
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
	 * 签到(小活动，活动，课程)
	 * @param response
	 * @param type 客户端标识
	 * @param userid 用户id
	 * @param id 主体id 
	 * @param acttype 主体类型,activity:活动，gathering：小活动,course：课程
	 * @author Lee.J.Eric
	 * @time 2014年12月30日 下午3:53:41
	 */
	@RequestMapping(value = "join")
	public void join(HttpServletResponse response,String type,String userid,String id,String acttype){
		Jr jr = new Jr();
		jr.setMethod("join");
		if(type==null||userid==null||id==null||acttype==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User user = userService.findById(userid);
			Eventlog eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, id,acttype.toLowerCase(),"join");
			try {
				if(eventlog==null){//未签到
					eventlog = new Eventlog();
					if(acttype.equalsIgnoreCase(Gathering.entityName)){
						Gathering gathering = gatheringService.findById(id);
						if(user==null||gathering==null){
							jr.setCord(1);
							jr.setMsg("用户或小活动不存在");
						}else {
							eventlog.setGathering(gathering);
							eventlog.setUpdatetime(gathering.getOpendate());
						}
					}else if (acttype.equalsIgnoreCase(Activity.entityName)) {
						Activity activity = activityService.findById(id);
						if(user==null||activity==null){
							jr.setCord(1);
							jr.setMsg("用户或活动不存在");
						}else {
							eventlog.setActivity(activity);
							eventlog.setUpdatetime(activity.getOpendate());
						}
					}else if (acttype.equalsIgnoreCase(Course.entityName)) {
						Course course = courseService.findById(id);
						if(user==null||course==null){
							jr.setCord(1);
							jr.setMsg("用户或课程不存在");
						}else {
							eventlog.setCourse(course);
							eventlog.setUpdatetime(course.getOpendate());
						}
					}
					eventlog.setActtype(acttype);
					eventlog.setUser(user);
					eventlog.setEventtype("join");//是签到
					eventlogService.eventlogSaveOrUpdate(eventlog);
				}
				jr.setData(0);
				jr.setMsg("签到成功");
			} catch (Exception e) {
				// TODO: handle exception
				jr.setData(1);
				jr.setMsg("签到失败");
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
	 * 签到列表
	 * @param response
	 * @param type
	 * @param id
	 * @param acttype
	 * @param page
	 * @param size
	 * @author Lee.J.Eric
	 * @time 2014年12月30日 下午4:09:33
	 */
	@RequestMapping(value = "joinlist")
	public void joinList(HttpServletResponse response,String type,String id,String acttype,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("joinlist");
		if(type==null||id==null||acttype==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Page<User> list = eventlogService.findUserListByIdAndEventtypeAndActtype(id, "join", acttype, page, size);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
			jr.setMsg("获取成功");
			jr.setData(list.getContent());
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
	 * 检查用户是否关注、报名、签到
	 * @param response
	 * @param type
	 * @param userid
	 * @param id
	 * @param acttype
	 * @author Lee.J.Eric
	 * @time 2014年12月30日 下午4:27:29
	 */
	@RequestMapping(value = "checkfocussignjoin")
	public void checkFocusSignJoin(HttpServletResponse response,String type,String userid,String id,String acttype){
		Jr jr = new Jr();
		jr.setMethod("checkfocussignjoin");
		if(type==null||userid==null||id==null||acttype==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Map<String, Integer> map = new HashMap<String, Integer>();
			Eventlog eventlog = null;
			eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, id, acttype, "focus");
			if(eventlog==null){
				map.put("focus", 0);
			}else {
				map.put("focus", 1);
			}
			eventlog = null;
			eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, id, acttype, "sign");
			if(eventlog==null){
				map.put("sign", 0);
			}else {
				map.put("sign", 1);
			}
			eventlog = null;
			eventlog = eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, id, acttype, "join");
			if(eventlog==null){
				map.put("join", 0);
			}else {
				map.put("join", 1);
			}
			jr.setData(map);
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
	 * 根据课程id获取报名列表 2.0
	 * @param response
	 * @param id 课程id
	 * @param type
	 * @param page
	 * @param size
	 * @time 2015-1-3
	 */
	@RequestMapping(value="coursesignlist")
	public void coursesignlist(HttpServletResponse response,String id,String type,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("coursesignlist");
		if(id==null||id.isEmpty()||page==null||page<0||size==null||size<0){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			if(id==null||page==null||size==null){
				jr.setCord(-1);
				jr.setMsg("非法传值");
			}else {
				Page<User> list = eventlogService.findUserListByIdAndEventtypeAndActtype(id, "sign", Course.entityName, page, size);
				jr.setPagenum(page+1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setTotal(list.getTotalElements());
				jr.setCord(0);
				jr.setMsg("获取成功");
				jr.setData(list.getContent());
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
	 * 绑定回顾Url
	 * @param response
	 * @param url   htmlurl
	 * @param id    活动id/小活动/课程等ID
	 * @param idtype   activity  活动  || gathering  小活动 || course 课程 ||notice 公告 ||invite邀请
	 * @param userid   用户ID
	 */
	@RequestMapping(value="bdhuiguurl")
	public void bdhuiguurl(HttpServletResponse response,String url,String id,String idtype,String userid){
		Jr jr = new Jr();
		jr.setMethod("bdhuiguurl");
		if(idtype==null||id==null||url==null||userid==null){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}
		if(idtype.equals(Activity.entityName)){
			Activity activity=activityService.findById(id);
			User user=userService.findById(userid);
			if(activity!=null){
				activity.setHuiguurl(url);
				activity.setStatus(-2);//回顾
				Eventlog eventlog=eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, id, idtype, "review");
				if(eventlog==null){
					eventlog=new Eventlog();
					eventlog.setActivity(activity);
					eventlog.setActtype(idtype);
					eventlog.setEventtype("review");//回顾
					eventlog.setUpdatetime(new Date());
					eventlog.setUser(user);
					
					eventlog=eventlogService.eventlogSaveOrUpdate(eventlog);
				}
				activityService.activitySaveOrUpdate(activity);
				
				jr.setCord(0);
				jr.setMsg("绑定成功");
			}else{
				jr.setCord(-2);
				jr.setMsg("活动不存在");
			}
		}else if(idtype.equals(Course.entityName)){
			Course course=courseService.findById(id);
			User user=userService.findById(userid);
			if(course!=null){
				course.setHuiguurl(url);
				course.setStatus(-2);
				Eventlog eventlog=eventlogService.findByUserAndEntityAndActtypeAndEventtype(userid, id, idtype, "review");
				if(eventlog==null){
					eventlog=new Eventlog();
					eventlog.setCourse(course);
					eventlog.setActtype(idtype);
					eventlog.setEventtype("review");//回顾
					eventlog.setUpdatetime(new Date());
					eventlog.setUser(user);
					eventlog=eventlogService.eventlogSaveOrUpdate(eventlog);
					courseService.courseSaveOrUpdate(course);
				}
				jr.setCord(0);
				jr.setMsg("绑定成功");
			}else{
				jr.setCord(-2);
				jr.setMsg("课程不存在");
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
	 * 绑定课程时使用
	 * @param response
	 * @param page
	 * @param size
	 * @param type
	 * @param listtype  needform 报名表绑定/问卷绑定           needreview    绑定回顾
 	 */
	@RequestMapping(value="getcourselist")
	public void getcourselist(HttpServletResponse response,Integer page,Integer size,String type,String listtype){
		Jr jr = new Jr();
		jr.setMethod("getcourselist");
		if(page==null||page<0||size==null||size<0){
			jr.setCord(-1);//非法传参
		}else {
			Page<Course> list = courseService.getcourselist(page, size, listtype);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
			jr.setTotal(list.getTotalElements());
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
	 * web获取活动列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param listtype
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value = "webactivitylist")
	public void webactivitylist(HttpServletResponse response,Integer type,Integer page,Integer size,Integer listtype,String keyword){
		Jr jr = new Jr();
		jr.setMethod("activitylist");
		if(listtype==null||page==null||page<0||size==null||size<0){//非法传值
			jr.setCord(-1);
			jr.setMsg("参数错误");
		}else {
			Page<Activity> list = activityService.getActivityList(page, size, listtype);
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("title");
			set.add("summary");
			set.add("createdate");
			set.add("publishdate");
			set.add("opendate");
			set.add("enddate");
			set.add("checktype");
			set.add("address");
			set.add("cost");
			set.add("label.id");
			set.add("label.zname");
			set.add("tel");
			set.add("user.id");
			set.add("user.nickname");
			set.add("user.minimg");
			set.add("status");
			set.add("maxnum");
			set.add("cover");
			set.add("signnum");
			set.add("joinnum");
			set.add("commentnum");
			set.add("goodnum");
			set.add("sharenum");
			set.add("imgsnum");
			set.add("titleimg");
			set.add("isbanner");
			set.add("huiguurl");
			set.add("price");
			set.add("acttype");
			set.add("acttypetitle");
			set.add("acttypeurl");
			set.add("onsale");
			set.add("pricetype");
			set.add("pricekey1");
			set.add("pricevalue1");
			jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(),set));
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
			jr.setMsg("获取成功");
			jr.setData2(keyword);
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
	 * 2.1  创建报名表/问卷 页面并绑定活动
	 * @param request
	 * @param response
	 * @param htmlpagecode 页面代码
	 * @param actid  活动ID
	 * @param userid 操作人ID
	 */
	@RequestMapping(value="saveandbdpage")
	public void saveandbdpage(HttpServletRequest request,HttpServletResponse response,Htmlpagecode htmlpagecode,String actid,String userid){
		Jr jr = new Jr();
		jr.setMethod("saveandbdpage");
		if(actid==null||htmlpagecode==null){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}else{
				CreateHtml createHtml=new CreateHtml(htmlpagecode.getPagecode(),request.getServletContext().getRealPath("/"),htmlpagecode.getHtmltype());
				//防重复入库
				Htmlpagecode h=htmlpagecodeService.findByObjid(htmlpagecode.getObjid());
				if(h!=null){
					h.setPageurl(createHtml.getUrl_path());
					htmlpagecodeService.save(h);
				}else{
					htmlpagecode.setPageurl(createHtml.getUrl_path());
					htmlpagecodeService.save(htmlpagecode);
				}
				Activity activity=activityService.findById(actid);
				
				if(activity!=null){
					activity.setH5url(createHtml.getUrl_path());
					activityService.activitySaveOrUpdate(activity);
					jr.setData(createHtml.getUrl_path());
					jr.setCord(0);
					jr.setMsg("保存并绑定成功");
				}else{
					jr.setCord(-2);
					jr.setMsg("活动不存在");
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
	 * 查询活动余票
	 * @param response
	 * @param id
	 * @param type
	 */
	@RequestMapping(value = "searchrestticket")
	public void searchRestTicket(HttpServletResponse response,String id,String type){
		Jr jr = new Jr();
		jr.setMethod("searchrestticket");
		if(id==null||type==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Activity activity = activityService.findById(id);
			if(activity==null){
				jr.setCord(1);
				jr.setMsg("此活动不存在");
			}else {
				Map<String,Object> map = new HashMap<String, Object>();
				if(activity.getActivityType().equals(ActivityTypeValue.SET.value)){//集合
					List<Activity> list = activityService.findByPid(activity.getId());
					for (Activity act:list) {
						SKU sku = skuService.findById(act.getSkuid());
						SkuInventory inventory = skuInventoryService.findById(sku.getId());
						Map<String,Object> m = new HashMap<String, Object>();
						m.put("title",act.getTitle());
						m.put("rest",inventory.getAmount());
						map.put(act.getId(),m);
					}
				}else {
					SKU sku = skuService.findById(activity.getSkuid());
					SkuInventory inventory = skuInventoryService.findById(sku.getId());
					Map<String,Object> m = new HashMap<String, Object>();
					m.put("title",activity.getTitle());
					m.put("rest",inventory.getAmount());
					map.put(activity.getId(),m);
				}
				jr.setCord(0);
				jr.setData(map);
				jr.setMsg("成功");
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
	 * 修补活动封面图，添加320X320尺寸
	 */
	@RequestMapping(value = "debug_activity_cover")
	public void debugActivityCover(HttpServletResponse response){
		Jr jr = new Jr();
		jr.setMethod("debug_activity_cover");
		Page<Activity> list = activityService.findActivityList(0, 1000, null);
		List<Activity> activityList = list.getContent();
		for (Activity activity:activityList) {
			try{
				String cover = activity.getCover();
				String base = R.Common.IMG_DIR+cover.substring(0, 1);;//图片目录

				String target = cover+R.Img._320X320;
				ImageUtil imageUtil = new ImageUtil();
				imageUtil.compressPic(base, base, cover, target, 320, 320, null, true);
			}catch (Exception e){
				continue;
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
