package cn.yi.gather.v11.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v11.entity.Activity;
import cn.yi.gather.v11.entity.ActivityJoin;
import cn.yi.gather.v11.entity.Labels;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.service.IActivityJoinServiceV2;
import cn.yi.gather.v11.service.IActivityServiceV2;
import cn.yi.gather.v11.service.IEmailServiceV2;
import cn.yi.gather.v11.service.ILabelsServiceV2;
import cn.yi.gather.v11.service.IUserServiceV2;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

@Controller("activityControllerV2")
@RequestMapping(value="v2/activity")
public class ActivityControllerV2 {

	private static Logger log = Logger.getLogger(ActivityControllerV2.class);
	
	@Resource(name="userServiceV2")
	private IUserServiceV2 userServiceV2;
	
	@Resource(name="activityServiceV2")
	private IActivityServiceV2 activityServiceV2;
	
	@Resource(name = "labelsServiceV2")
	private ILabelsServiceV2 labelsServiceV2;
	
	@Resource(name = "activityJoinServiceV2")
	private IActivityJoinServiceV2 activityJoinServiceV2;
	
	/**
	 * 创建活动
	 * @param response
	 * @param activity
	 * @param labels
	 * Lee.J.Eric
	 * 2014 2014年6月3日 下午6:14:46
	 */
	@RequestMapping(value="createactivity")
	public void createActivity(HttpServletResponse response,Activity activity,String labels,String open,String end){
		Jr jr = new Jr();
		jr.setMethod("createactivity");
		if(activity==null||labels==null||labels.equals("")){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else{
			User user = userServiceV2.findById(activity.getUser().getId());
			if(user==null||user.getRoot()>2){
				jr.setCord(1);//用户不存在或用户无权限
				jr.setMsg("用户不存在或用户无权限");
			}else{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(labels!=null&&!labels.isEmpty())
					activity.setLabel(labelsServiceV2.getLabelsByids(labels));
				if(StringUtils.isNotBlank(open)){
					try {
						activity.setOpendate1(format.parse(open));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (StringUtils.isNotBlank(end)) {
					try {
						activity.setEnddate1(format.parse(end));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				activity.setUser(user);
				activity.setJoinnum(1);
				/* *********************************0630 S*************************************  */	
				if(activity.getPricetype()==1){
					List<Integer> lIntegers=new ArrayList<Integer>();
					String[] arrs=activity.getPricevalue().split(",");
					for (String s : arrs) {
						lIntegers.add(Integer.parseInt(s));
					}
					Collections.sort(lIntegers);
					activity.setMinprice(lIntegers.get(0));
					if(lIntegers.size()<2){
						activity.setMaxprice(lIntegers.get(0));
					}else{
						activity.setMaxprice(lIntegers.get((lIntegers.size()-1)));
					}
				}else{
					activity.setMinprice(activity.getPrice());
					activity.setMaxprice(activity.getPrice());
				}
				/* *********************************0630 E*************************************  */	
				
				
				activity = activityServiceV2.activitySaveOrUpdate(activity);
				
				//activity.getJoinpeoples().add(user);//活动发起人参加活动
				if(activity==null){
					jr.setCord(1);//新建活动失败
					jr.setMsg("新建失败");
				}else{
					//*******活动发起人参加活动
					ActivityJoin activityJoin = new ActivityJoin();
					activityJoin.setActivity(activity);
					activityJoin.setEmail(user.getEmail());
					activityJoin.setName(user.getNickname());
					activityJoin.setOwner(1);//发起人标记
					activityJoin.setReason("大家好，我是"+activity.getTitle()+"的发起人");
					activityJoin.setSex(user.getSex());
					activityJoin.setTel(user.getTelnum());
					activityJoin.setVipflag(1);//会员标记
					activityJoin.setUser(user);
					activityJoinServiceV2.activityjoinSaveOrUpdate(activityJoin);
					//*******活动发起人参加活动
					
					jr.setData(activity);
					jr.setCord(0);
					jr.setMsg("创建成功");
					//审核通过，以发起人评论活动
					/*Comment comment = new Comment();
					comment.setActivity(activity.getId());
					comment.setUser(activity.getUser());
					comment.setUserimg(activity.getUserimg());
					comment.setUsernickname(activity.getUsernickname());
					String text = "大家好，我是本次活动发起人。欢迎大家参加本次活动，大家可以在这里提出你对活动的建议、看法,也欢迎大家把现场图片花絮发上来,精彩评论图片我们会选录进该活动的回顾中。";
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
	 * 获取单个活动信息
	 * @param response
	 * @param id
	 * Lee.J.Eric
	 * 2014 2014年6月4日 上午11:12:54
	 */
	@RequestMapping(value="getactivityinfo")
	public void getActivityInfo(HttpServletResponse response,String id){
		Jr jr = new Jr();
		jr.setMethod("getactivityinfo");
		if(id==null||id.isEmpty()){
			jr.setCord(-1);
		}else {
			Activity activity = activityServiceV2.findById(id);
			if(activity==null){
				jr.setCord(1);
				jr.setMsg("活动不存在");
			}else{
				if("d8cbc0902ea140f68de61398940151074".equals(activity.getId())){
					activity.setSignnum(33);
				}else if ("4187bf9d91d8431f94481398841127369".equals(activity.getId())) {
					activity.setSignnum(68);
				}else if ("c00726f07bd44428a8a71399459704548".equals(activity.getId())) {
					activity.setSignnum(182);
				}else if ("fc75e256166d461a8f8d1399446457801".equals(activity.getId())) {
					activity.setSignnum(33);
				}else if ("1b2493b297854eed8e311399463587679".equals(activity.getId())) {
					activity.setSignnum(66);
				}else if ("dfa4ceb06dc74f0faf681397652614185".equals(activity.getId())) {
					activity.setSignnum(200);
				}else if ("dd1f353c4a114fc8b70b1397659394850".equals(activity.getId())) {
					activity.setSignnum(71);
				}else if ("d2b35c0d845c4ca4a10a1397656485028".equals(activity.getId())) {
					activity.setSignnum(59);
				}else if ("7565a111b71644498cdd1399436056059".equals(activity.getId())) {
					activity.setSignnum(26);
				}else if ("2d0f2ede608841cf93d11399543250491".equals(activity.getId())) {
					activity.setSignnum(26);
				}else if ("d9e8244c41ba4a19ad181399463383478".equals(activity.getId())) {
					activity.setSignnum(63);
				}else if ("2cdafd328fa443ff9fa31397653267876".equals(activity.getId())) {
					activity.setSignnum(124);
				}else if ("7cf673c12d484b7688641398234223412".equals(activity.getId())) {
					activity.setSignnum(90);
				}else if ("c563e2c3d5214875a5971398235964465".equals(activity.getId())) {
					activity.setSignnum(92);
				}else if ("62a9e9f0a83442da801e1398232821270".equals(activity.getId())) {
					activity.setSignnum(46);
				}else if ("12a852462236443b8b171399448689480".equals(activity.getId())) {
					activity.setSignnum(105);
				}else if ("0f3f743ee7a446018f351397652394584".equals(activity.getId())) {
					activity.setSignnum(45);
				}else if ("418b873ef4184352a5d81397654724033".equals(activity.getId())) {
					activity.setSignnum(48);
				}else if ("4634ecf8ba7b43d39ec91398232679213".equals(activity.getId())) {
					activity.setSignnum(15);
				}else if ("08f0450e5ff3452288911397657230019".equals(activity.getId())) {
					activity.setSignnum(55);
				}else if ("405ad93dcc6949f5a06c1399443484558".equals(activity.getId())) {
					activity.setSignnum(62);
				}else if ("b0ae943091174f1dab921399448950806".equals(activity.getId())) {
					activity.setSignnum(20);
				}else if ("0193b06aed4f4bff85af1399443208798".equals(activity.getId())) {//翻页
					activity.setSignnum(44);
				}else if ("53163f991be24140b7df1397653720208".equals(activity.getId())) {
					activity.setSignnum(50);
				}else if ("3c6068bf315f418ea2a31397659206590".equals(activity.getId())) {
					activity.setSignnum(62);
				}else if ("763456948979466290651397657222910".equals(activity.getId())) {
					activity.setSignnum(60);
				}else if ("993bf3d6583145a495901399459316314".equals(activity.getId())) {
					activity.setSignnum(160);
				}else if ("7f86b3b76c6541bc9dfb1397655266283".equals(activity.getId())) {
					activity.setSignnum(155);
				}
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
	 * 获取我的活动列表
	 * @param response
	 * @param type
	 * @param userid
	 * @param flag 0：我发起的或是我参加的，1：我发起的，2我报名的
	 * @param page
	 * @param size
	 * @param keyword
	 * Lee.J.Eric
	 * 2014 2014年6月5日 上午11:12:03
	 */
	@RequestMapping(value="getmyactivitylist")
	public void getMyActivityList(HttpServletResponse response,Integer type,String userid,Integer flag,Integer page,Integer size,String keyword){
		Jr jr = new Jr();
		jr.setMethod("getmyactivitylist");
		if(page==null||page<0||size==null||size<0||userid==null){
			jr.setCord(-1);//非法传值
		}else{
			try {
				User user = userServiceV2.findById(userid);
				Page<Activity> list = activityServiceV2.getMyActivity(page, size, user, flag, keyword);
				jr.setPagenum(page+1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
//				Set<String> set = new HashSet<String>();
//				set.add("id");
//				set.add("title");
//				set.add("summary");
//				set.add("img");
//				set.add("createdate");
//				set.add("publishdate");
//				set.add("opendate");
//				set.add("enddate");
//				set.add("checktype");
//				set.add("address");
//				set.add("cost");
//				set.add("label.id");
//				set.add("label.zname");
//				set.add("tel");
//				set.add("user.id");
//				set.add("user.nickname");
//				set.add("user.minimg");
//				set.add("status");
//				set.add("maxnum");
//				set.add("signnum");
//				set.add("joinnum");
//				set.add("commentnum");
//				set.add("goodnum");
//				set.add("sharenum");
//				set.add("imgsnum");
//				set.add("imgs");
//				set.add("titleimg");
//				set.add("isgood");
//				set.add("isbanner");
//				set.add("huiguurl");
//				set.add("price");
//				set.add("acttype");
//				set.add("acttypetitle");
//				set.add("acttypeurl");
//				set.add("onsale");
//				set.add("pricetype");
//				set.add("pricekey1");
//				set.add("pricevalue1");
//				jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(),set));
				jr.setData(list.getContent());
				jr.setTotal(list.getTotalElements());
				jr.setCord(0);
				jr.setMsg("获取成功");
				jr.setData2(keyword);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				jr.setCord(1);
				jr.setMsg("获取失败");
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
	 * 活动报名
	 * @param response
	 * @param type
	 * @param activityjoin
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午1:53:10
	 */
	@RequestMapping(value="joinactivity")
	public void joinActivity(HttpServletResponse response,Integer type,ActivityJoin activityJoin){
		Jr jr = new Jr();
		jr.setMethod("joinactivity");
		if(activityJoin.getActivity()==null||activityJoin.getActivity().getId().isEmpty()||activityJoin.getUser()==null||activityJoin.getUser().getId().isEmpty()){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			User user = userServiceV2.findById(activityJoin.getUser().getId());
			Activity activity = activityServiceV2.findById(activityJoin.getActivity().getId());
			if(user==null||activity==null){
				jr.setCord(1);
				jr.setMsg("用户或活动不存在");
			}else {
				Boolean flag = activityJoinServiceV2.checkByUserAndActivity(user, activity);
				if(!flag){//未报名
					activityJoin.setActivity(activity);
					activityJoin.setUser(user);
					activityJoin = activityJoinServiceV2.activityjoinSaveOrUpdate(activityJoin);
					if(activityJoin==null){
						jr.setCord(1);
						jr.setMsg("报名失败");
					}else {
						activity.setJoinnum(activityJoinServiceV2.countByActivity(activity).intValue());
						activityServiceV2.activitySaveOrUpdate(activity);
						jr.setCord(0);
						jr.setMsg("报名成功");
					}
				}else {//已报名
					jr.setCord(2);
					jr.setMsg("您已报名此活动");
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
	 * 取消报名
	 * @param response
	 * @param activityid
	 * @param userid
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午6:43:18
	 */
	@RequestMapping(value="exitactivity")
	public void exitActivity(HttpServletResponse response,String activityid,String userid,Integer type){
		Jr jr = new Jr();
		jr.setMethod("exitactivity");
		if(activityid==null||activityid.isEmpty()||userid==null||userid.isEmpty()){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			try {
				User user = userServiceV2.findById(userid);
				Activity activity = activityServiceV2.findById(activityid);
				if(user==null||activity==null){
					jr.setCord(1);
					jr.setMsg("用户或活动不存在");
				}else {
					if(activity.getUser().getId().equals(user.getId())){
						jr.setCord(1);
						jr.setMsg("活动发起人不能退出报名");
					}else {
						ActivityJoin activityJoin = activityJoinServiceV2.getByUserAndActivity(user, activity);
						if(activityJoin==null){
							jr.setCord(1);
							jr.setMsg("此用户尚未报名");
						}else {
							activityJoinServiceV2.deleteByID(activityJoin.getId());
							activity.setJoinnum(activityJoinServiceV2.countByActivity(activity).intValue());
							activityServiceV2.activitySaveOrUpdate(activity);
							jr.setCord(0);
							jr.setMsg("取消报名成功");
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				jr.setCord(1);
				jr.setMsg("取消报名失败");
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
	 * 根据活动id获取报名列表
	 * @param response
	 * @param id
	 * @param type
	 * @param page
	 * @param size
	 * Lee.J.Eric
	 * 2014 2014年6月6日 下午7:03:32
	 */
	@RequestMapping(value="getjoinslist")
	public void getjoinslist(HttpServletResponse response,String id,Integer type,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("getjoinslist");
		if(id==null||id.isEmpty()||page==null||page<0||size==null||size<0){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			Activity activity = activityServiceV2.findById(id);
			if(activity==null){
				jr.setCord(1);
				jr.setMsg("此活动不存在");
			}else{
				Page<ActivityJoin> list = activityJoinServiceV2.findPageByActivity(page, size, activity);
				List<User> users = new ArrayList<User>();
				for (ActivityJoin join : list.getContent()) {
					users.add(join.getUser());
				}
				
				jr.setPagenum(page+1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setData(users);
				jr.setTotal(list.getTotalElements());
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
	 * 分享活动
	 * @param response
	 * @param id
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年6月9日 上午10:44:27
	 */
	@RequestMapping(value="shareactivity")
	public void shareActivity(HttpServletResponse response,String id,Integer type){
		Jr jr = new Jr();
		jr.setMethod("shareactivity");
		if(id==null||id.isEmpty()){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else{
			Activity activity = activityServiceV2.findById(id);
			if(activity==null){
				jr.setCord(1);
				jr.setMsg("此活动不存在");
			}else {
				activity.setSharenum(activity.getSharenum()+1);//更新分享的总数
				activity = activityServiceV2.activitySaveOrUpdate(activity);
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
	public void searchActivity(HttpServletResponse response,String keyword,Integer page,Integer size,Integer type){
		Jr jr = new Jr();
		jr.setMethod("searchactivity");
		if(page==null||page<0||size==null||size<0||keyword==null||keyword.isEmpty()){
			jr.setCord(-1);//非法传参
		}else {
			Page<Activity> list = activityServiceV2.searchActByKeyword(keyword, page, size);
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
	public void getBannerActList(HttpServletResponse response,Integer type){
		Jr jr = new Jr();
		jr.setMethod("getbanneractlist");
		try {
			List<Activity> list = activityServiceV2.getBannerActList(1);
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
	public void getActivityListByLabel(HttpServletResponse response,Integer type,Integer page,Integer size,Long label){
		Jr jr = new Jr();
		jr.setMethod("getactivitylistbylabel");
		if(page==null||page<0||size==null||size<0||label==null){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			Labels labels = labelsServiceV2.getByID(label);
			Page<Activity> list = activityServiceV2.getByLabel(labels, page, size);
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
	@RequestMapping(value="checksignforuser")
	public void checkSignForUser(HttpServletResponse response,Integer type,String userid,String activityid){
		Jr jr = new Jr();
		jr.setMethod("checksignforuser");
		if(userid==null||userid.isEmpty()||activityid==null||activityid.isEmpty()){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}else {
			User user = userServiceV2.findById(userid);
			Activity activity = activityServiceV2.findById(activityid);
			Boolean flag = activityJoinServiceV2.checkByUserAndActivity(user, activity);
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
	 * 活动列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param listtype 列表类型，-1全部，0预告，1回顾
	 * @param keyword 搜索关键字
	 * Lee.J.Eric
	 * 2014 2014年4月25日 下午4:48:16
	 */
	@RequestMapping(value="activitylist")
	public void activityList(HttpServletResponse response,Integer type,Integer page,Integer size,Integer listtype,String keyword){
		Jr jr = new Jr();
		jr.setMethod("activitylist");
		if(listtype==null||page==null||page<0||size==null||size<0){//非法传值
			jr.setCord(-1);
			jr.setMsg("参数错误");
		}else {
			Page<Activity> list = activityServiceV2.getActivityList(page, size, listtype, keyword);
			Set<String> set = new HashSet<String>();
			set.add("id");
			set.add("title");
			set.add("summary");
			set.add("img");
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
			set.add("signnum");
			set.add("joinnum");
			set.add("commentnum");
			set.add("goodnum");
			set.add("sharenum");
			set.add("imgsnum");
			set.add("imgs");
			set.add("titleimg");
			set.add("isgood");
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
}
