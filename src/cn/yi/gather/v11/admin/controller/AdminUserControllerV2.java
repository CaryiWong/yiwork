package cn.yi.gather.v11.admin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v11.entity.Applyvip;
import cn.yi.gather.v11.entity.Broadcast;
import cn.yi.gather.v11.entity.Classsort;
import cn.yi.gather.v11.entity.Feedback;
import cn.yi.gather.v11.entity.Labels;
import cn.yi.gather.v11.entity.Sysnetwork;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.service.IApplyvipServiceV2;
import cn.yi.gather.v11.service.IBroadcastServiceV2;
import cn.yi.gather.v11.service.IClasssortServiceV2;
import cn.yi.gather.v11.service.IFeedbackServiceV2;
import cn.yi.gather.v11.service.IJPushServiceV2;
import cn.yi.gather.v11.service.ILabelsServiceV2;
import cn.yi.gather.v11.service.ISysnetworkServiceV2;
import cn.yi.gather.v11.service.IUserServiceV2;

import com.common.Jr;
import com.common.Page;
import com.common.R;
import com.tools.utils.JSONUtil;
import com.tools.utils.MD5Util;
import com.tools.utils.RandomUtil;

/**
 * 后台用户控制器
 * 
 * @author Lee.J.Eric
 * @time 2014年6月10日下午2:03:01
 */
@Controller("adminUserControllerV2")
@RequestMapping(value = "v2/admin/user")
public class AdminUserControllerV2 {
	private static Logger log = Logger.getLogger(AdminUserControllerV2.class);
	
	@Resource(name = "userServiceV2")
	private IUserServiceV2 userServiceV2;
	
	@Resource(name = "classsortServiceV2")
	private IClasssortServiceV2 classsortServiceV2;
	
	@Resource(name = "applyvipServiceV2")
	private IApplyvipServiceV2 applyvipServiceV2;
	
	@Resource(name = "labelsServiceV2")
	private ILabelsServiceV2 labelsServiceV2;
	
	@Resource(name = "sysnetworkServiceV2")
	private ISysnetworkServiceV2 sysnetworkServiceV2;
	
	@Resource(name = "broadcastServiceV2")
	private IBroadcastServiceV2 broadcastServiceV2;
	
	@Resource(name = "jPushServiceV2")
	private IJPushServiceV2 pushServiceV2;
	
	@Resource(name = "feedbackServiceV2")
	private IFeedbackServiceV2 feedbackServiceV2;
	
	
	/**
	 * 跳转到后台管理登陆
	 * @param modelMap
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月10日 下午2:40:09
	 */
	@RequestMapping(value="index")
	public ModelAndView index(ModelMap modelMap){
		return new ModelAndView("adminindex",modelMap);
	}
	
	/**
	 * 管理员后台登陆
	 * @param request
	 * @param username
	 * @param password
	 * @param code
	 * @param modelMap
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月10日 下午2:40:03
	 */
	@RequestMapping(value="login",method=RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "code", required = true) String code,
			ModelMap modelMap){
		String validateCode = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (!code.equals(validateCode)) {
			modelMap.put("tips", "验证码错误");
			return index(modelMap);
		} else {
			User user = userServiceV2.findByusernameAndPWD(MD5Util.encrypt(password), username);
			//Userinfo userinfo = userinfoService.getUserinfoByusernameAndPWD(username, MD5Util.encrypt(password));
			if(user == null){
				modelMap.put("tips", "帐号或密码错误");
				return index(modelMap);
			}
			if(user.getRoot()>1){
				modelMap.put("tips", "非管理员不能进入后台");
				return index(modelMap);
			}
			request.getSession().setAttribute(R.User.SESSION_USER, user);
			log.info("用户成功登陆---"+user.getNickname());
			return new ModelAndView("main");
		}
	}
	
	/**
	 * 退出系统
	 * @param request
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月10日 下午2:39:56
	 */
	@RequestMapping(value="logout")
	public ModelAndView logout(HttpServletRequest request){
		//清除session
		//Userinfo sessionUser = (Userinfo)request.getSession().getAttribute(R.Account.SESSION_USER);
		request.getSession().removeAttribute(R.User.SESSION_USER);
		log.info("用户退出成功---");
		return new ModelAndView("redirect:index");
	}
	
	/**
	 * 跳转到新增会员
	 * @param request
	 * @param modelMap
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月10日 下午2:40:17
	 */
	@RequestMapping(value="addapplyvip")
	public ModelAndView addApplyVIP(HttpServletRequest request,ModelMap modelMap){
		List<Classsort> industrys = classsortServiceV2.getClasssortsByPid(888888888888L);
		modelMap.put("industrys", industrys);
		return new ModelAndView("admin/user/addapplyvip");
	}
	
	/**
	 * 保存会员申请
	 * @param response
	 * @param modelMap
	 * @param applyvip
	 * @param birth
	 * @param chargetype
	 * @param unum
	 * @param begin
	 * @param end
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月10日 下午2:40:36
	 */
	@RequestMapping(value="saveapplyvip")
	public ModelAndView saveApplyVIP(HttpServletResponse response,ModelMap modelMap,Applyvip applyvip,String birth,Integer charge,String unum,String begin,String end){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(birth!=null&&!birth.isEmpty()){//生日选项不为空
				applyvip.setBirthday(format.parse(birth).getTime());
			}
			if(applyvip.getIndustry()!=null)
				applyvip.setIndustry(classsortServiceV2.getClasssortsByID(applyvip.getIndustry().getId()));
			if(applyvip.getJob()!=null)
				applyvip.setJob(classsortServiceV2.getClasssortsByID(applyvip.getJob().getId()));
			applyvipServiceV2.applyvipSaveOrUpdate(applyvip);
			if(charge==1){//已收费
				if(unum==null||unum.isEmpty()||begin==null||begin.isEmpty()||end==null||end.isEmpty()){
					String tips = "请填写会员号/开始/结束日期";
					modelMap.put("tips", tips);
					return new ModelAndView("admin/user/addapplyvip",modelMap);
				}else {
					User user = new User(applyvip);
					User u = userServiceV2.getByEmail(user.getEmail());
					if(u==null){//前台此帐号没注册过
						while(userServiceV2.checkForNickname(user.getNickname())){//昵称重复,随机追加一个字符
							user.setNickname(user.getNickname() + RandomUtil.getRandomeStringForLength(1));
						}
//						if(userServiceV2.checkForNickname(applyvip.getName())){
//							modelMap.put("tips", "昵称已被使用");
//							return new ModelAndView("admin/user/addapplyvip",modelMap);
//						}
						if(userServiceV2.checkForTelephone(applyvip.getTel())){
							modelMap.put("tips", "手机号已被使用");
							return new ModelAndView("admin/user/addapplyvip",modelMap);
						}
						if(userServiceV2.checkForEmail(applyvip.getEmail())){
							modelMap.put("tips", "邮箱已被使用");
							return new ModelAndView("admin/user/addapplyvip",modelMap);
						}
						if(unum!=null&&begin!=null&&end!=null){//选中缴费
							user.setUnum(unum);
							user.setVipdate1(format.parse(begin));
							user.setVipenddate1(format.parse(end));
							user.setRoot(2);
						}
						user = userServiceV2.userSaveOrUpdate(user);
					}else {//前台已注册
						modelMap.put("tips", "已存在申请记录！");
						modelMap.put("userid", u.getId());
						return new ModelAndView("redirect:v2/admin/user/updateuser",modelMap);
					}
					modelMap.put("tips", "保存成功");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("新增会员申请失败---"+e);
			modelMap.put("tips", "保存失败");
		}
		return new ModelAndView("admin/user/addapplyvip",modelMap);
	}
	
	/**
	 * 分页查看用户列表
	 * @param modelMap
	 * @param page
	 * @param charge
	 * @param sex
	 * @param userNO
	 * @param keyword
	 * @param groups
	 * @param spaceshow -1：全部，0不在，1在
	 * @param vipshow -1：全部，0不在，1在
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月10日 下午3:48:54
	 */
	@RequestMapping(value="userlist")
	public ModelAndView userList(ModelMap modelMap,Page<User> page,Integer charge,Integer sex,String userNO,String keyword,Long[] groups,Integer spaceshow,Integer vipshow){
		List<Long> labelList = new ArrayList<Long>();
		if (groups != null) {
			labelList = Arrays.asList(groups);
		} else {
			labelList = null;
		}
		List<Labels> labels = labelsServiceV2.getLabelsByType(0);
		page = userServiceV2.getUserListForPage(page.getCurrentPage(),page.getPageSize(),charge,sex,userNO,keyword,labelList,spaceshow,vipshow);
		System.out.println(page.getCurrentPage());
		modelMap.put("labelList", labelList);
		modelMap.put("labels", labels);
		modelMap.put("page", page);
		modelMap.put("charge", charge);
		modelMap.put("sex", sex);
		modelMap.put("userNO", userNO);
		modelMap.put("keyword", keyword);
		modelMap.put("spaceshow", spaceshow);
		modelMap.put("vipshow", vipshow);
		return new ModelAndView("admin/user/userlist",modelMap);
	}
	
	/**
	 * 到用户信息更新页面
	 * @param modelMap
	 * @param userid
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月10日 下午5:28:20
	 */
	@RequestMapping(value="updateuser")
	public ModelAndView updateUser(ModelMap modelMap,String userid){
		User user = userServiceV2.findById(userid);
		List<Labels> labels = labelsServiceV2.getLabelsByType(0);
		List<Long> labelList = new ArrayList<Long>();
		List<Labels> label = user.getLabel();
		List<Classsort> industrys = classsortServiceV2.getClasssortsByPid(888888888888L);//行业
		List<Classsort> jobs = classsortServiceV2.getAllSecClassSort();//职业
		for (Labels la : label) {
			labelList.add(la.getId());
		}
		modelMap.put("industrys", industrys);
		modelMap.put("jobs", jobs);
		modelMap.put("user", user);
		modelMap.put("labelList", labelList);
		modelMap.put("labels", labels);
		return new ModelAndView("admin/user/updateUser");
	}
	
	/**
	 * 更新用户信息
	 * @param modelMap
	 * @param user
	 * @param groups 领域标签id
	 * @param begin 会员开始日期
	 * @param end 会员结束日期
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月12日 下午3:34:15
	 */
	@RequestMapping(value="saveuser")
	public ModelAndView saveUser(ModelMap modelMap,User user,Long[] groups,String begin,String end){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<Long> labelList = new ArrayList<Long>();
		if (groups != null) {
			labelList = Arrays.asList(groups);
		} else {
			labelList = null;
		}
		try {
			List<Labels> labels = labelsServiceV2.getLabelsByType(0);
			List<Labels> myLabels = labelsServiceV2.getLabelsByList(labelList);
			List<Classsort> industrys = classsortServiceV2.getClasssortsByPid(888888888888L);//行业
			List<Classsort> jobs = classsortServiceV2.getAllSecClassSort();//职业
			User origin = userServiceV2.findById(user.getId());
			if (labelList==null||labelList.size()<1||labelList.size()>3) {
				modelMap.put("tips", "标签数个数：1~3个");
			}else if (origin!=null&&origin.getUnum()!=null&&!origin.getUnum().equals(user.getUnum())&&userServiceV2.checkForUnum(user.getUnum())) {
				modelMap.put("tips", "会员号已存在");
			}else {
				if((user.getRoot()==1||user.getRoot()==2)&&user.getUnum()!=null&&!user.getUnum().equals("")){
					origin.setUnum(user.getUnum());
					origin.setVipdate1(format.parse(begin));
					origin.setVipenddate1(format.parse(end));
				}
				origin.setRoot(user.getRoot());
				origin.setRealname(user.getRealname());
				origin.setNickname(user.getNickname());
				origin.setEmail(user.getEmail());
				origin.setTelnum(user.getTelnum());
				origin.setWechat(user.getWechat());
				origin.setSex(user.getSex());
				origin.setCompany(user.getCompany());
				origin.setCompanyurl(user.getCompanyurl());
				origin.setLabel(myLabels);
				origin.setProvince(user.getProvince());
				origin.setCity(user.getCity());
				//origin.setBirthday(birthday);
				origin.setIcnum(user.getIcnum());
				origin.setIntroduction(user.getIntroduction());
				origin.setTeamintroduction(user.getTeamintroduction());
				origin.setTwonum(user.getTwonum());
				origin.setMinimg(user.getMinimg());
				origin.setMaximg(user.getMaximg());
				origin.setIndustry(classsortServiceV2.getClasssortsByID(user.getIndustry().getId()));
				origin.setJob(classsortServiceV2.getClasssortsByID(user.getJob().getId()));
				origin.setViplevel(user.getViplevel());
				origin.setMypageurl(user.getMypageurl());
				origin.setFavourite(user.getFavourite());
				origin.setIfindex(user.getIfindex());
				origin.setIfspace(user.getIfspace());
				origin.setIfvipshow(user.getIfvipshow());
 				origin = userServiceV2.userSaveOrUpdate(origin);
				modelMap.put("tips", "修改成功");
			}
			modelMap.put("industrys", industrys);
			modelMap.put("jobs", jobs);
			modelMap.put("user", origin);
			modelMap.put("labelList", labelList);
			modelMap.put("labels", labels);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ModelAndView("admin/user/updateUser");
	}
	
	/**
	 * 设置会员展示标记
	 * @param response
	 * @param isshow
	 * @param id
	 * Lee.J.Eric
	 * 2014 2014年6月10日 下午6:13:39
	 */
	@RequestMapping(value="updatevipshow")
	public void updateVipShow(HttpServletResponse response,Integer isshow,String id){
		Jr jr = new Jr();
		jr.setMethod("updatevipshow");
		if(isshow==null||id==null){
			jr.setCord(-1);
		}else {
			User user = userServiceV2.findById(id);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("无此会员信息");
			}else {
				user.setIfvipshow(isshow);
				user.setVipshowsettime(new Date());
				user = userServiceV2.userSaveOrUpdate(user);
				if(user==null){
					jr.setCord(1);
					jr.setMsg("修改失败");
				}else {
					jr.setCord(0);
					jr.setData(user);
					jr.setMsg("修改成功");
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
	 * 设置会员空间标记
	 * @param response
	 * @param isspace
	 * @param id
	 * Lee.J.Eric
	 * 2014 2014年6月10日 下午6:13:45
	 */
	@RequestMapping(value="updatevipspace")
	public void updateVipSpace(HttpServletResponse response,Integer isspace,String id){
		Jr jr = new Jr();
		jr.setMethod("updatevipspace");
		if(isspace==null||id==null){
			jr.setCord(-1);
		}else {
			User user = userServiceV2.findById(id);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("无此会员信息");
			}else {
				user.setIfspace(isspace);
				user.setSpacesettime(new Date());
				user = userServiceV2.userSaveOrUpdate(user);
				if(user==null){
					jr.setCord(1);
					jr.setMsg("修改失败");
				}else {
					jr.setCord(0);
					jr.setData(user);
					jr.setMsg("修改成功");
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
	 * 下发广播
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月11日 下午2:31:35
	 */
	@RequestMapping(value="createbroadcast")
	public ModelAndView createBroadcast(){
		return new ModelAndView("admin/user/createbroadcast");
	}
	
	/**
	 * 查看的广播列表
	 * @param request
	 * @param modelMap
	 * @param page
	 * @param keyword
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月11日 下午2:31:44
	 */
	@RequestMapping(value="broadcastlist")
	public ModelAndView broadcastList(HttpServletRequest request,ModelMap modelMap,Page<Sysnetwork> page,String keyword){
		page = sysnetworkServiceV2.getSysnetworkForPage(page.getCurrentPage(), page.getPageSize(), keyword);
		modelMap.put("page", page);
		modelMap.put("keyword", keyword);
		return new ModelAndView("admin/user/broadcastlist"); 
	}
	
	/**
	 * 保存广播
	 * @param request
	 * @param sysnetwork
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年3月26日 下午4:15:19
	 */
	@RequestMapping(value = "savebroadcast")
	public ModelAndView saveBroadcast(ModelMap modelMap, Sysnetwork sysnetwork) {
		sysnetwork = sysnetworkServiceV2.sysnetworkSaveOrUpdate(sysnetwork);
		if(sysnetwork!=null){
			List<User> users = userServiceV2.getUserListByRoot(-1);
			List<Broadcast> broadcastList = new ArrayList<Broadcast>();
			for (User user : users) {
				Broadcast broadcast = new Broadcast();
				broadcast.setSysnetwork(sysnetwork);
				broadcast.setUser(user);
				broadcastList.add(broadcast);
//				try {
//					broadcastServiceV2.broadcastSaveOrUpdate(broadcast);
//				} catch (Exception e) {
//					// TODO: handle exception
//					log.error("保存广播出错------" + user.getNickname());
//				}
			}
			
			broadcastServiceV2.broadListBatchSave(broadcastList);
			pushServiceV2.push(2, sysnetwork.getId(), sysnetwork.getTitle(), "新广播", Platform.android_ios(), new String[]{"yiqi"}, new String[]{"version2"});
//			pushServiceV2.push("yiqi", "android,ios", sysnetwork.getTitle(), 2);
		}else {
			modelMap.put("tips", "保存广播失败");
		}
		return new ModelAndView("admin/user/broadcastlist");
	}
	
	/**
	 * 分页查看用户反馈
	 * @param modelMap
	 * @param page
	 * @param keyword
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月3日 下午5:06:41
	 */
	@RequestMapping(value="feedbacklist")
	public ModelAndView feedback(ModelMap modelMap,Page<Feedback> page,String keyword){
		page = feedbackServiceV2.getFeedbackForPage(page.getCurrentPage(), page.getPageSize(), keyword);
		modelMap.put("keyword", keyword);
		modelMap.put("page", page);
		return new ModelAndView("admin/display/feedbackList");
	}
	
	/**
	 * 用户导入
	 * @return
	 * Lee.J.Eric
	 * 2014年9月22日 上午10:36:24
	 */
	@RequestMapping(value = "importuser")
	public ModelAndView importuser(){
		return new ModelAndView("admin/user/datafile");
	}
}
