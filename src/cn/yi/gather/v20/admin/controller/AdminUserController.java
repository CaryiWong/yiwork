package cn.yi.gather.v20.admin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Broadcast;
import cn.yi.gather.v20.entity.Classsort;
import cn.yi.gather.v20.entity.Feedback;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.Notice;
import cn.yi.gather.v20.entity.NoticeMsg;
import cn.yi.gather.v20.entity.Sysnetwork;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.Userother;
import cn.yi.gather.v20.entity.VipNumLog;
import cn.yi.gather.v20.entity.WorkSpaceInfo;
import cn.yi.gather.v20.permission.entity.AdminUser;
import cn.yi.gather.v20.permission.service.IAdminUserService;
import cn.yi.gather.v20.service.IApplyvipService;
import cn.yi.gather.v20.service.IBroadcastService;
import cn.yi.gather.v20.service.IClasssortService;
import cn.yi.gather.v20.service.IFeedbackService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.INoticeMsgService;
import cn.yi.gather.v20.service.INoticeService;
import cn.yi.gather.v20.service.ISysnetworkService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IUserotherService;
import cn.yi.gather.v20.service.IVipNumLogService;
import cn.yi.gather.v20.service.IWorkSpaceInfoService;

import com.alipay.util.httpClient.HttpRequest;
import com.common.DES;
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
@Controller("adminUserControllerV20")
@RequestMapping(value = "v20/admin/user")
public class AdminUserController {
	private static Logger log = Logger.getLogger(AdminUserController.class);
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "classsortServiceV20")
	private IClasssortService classsortService;
	
	@Resource(name = "applyvipServiceV20")
	private IApplyvipService applyvipService;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name = "sysnetworkServiceV20")
	private ISysnetworkService sysnetworkService;
	
	@Resource(name =  "broadcastServiceV20")
	private IBroadcastService broadcastService;
	
	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;
	
	@Resource(name = "feedbackServiceV20")
	private IFeedbackService feedbackService;
	
	
	@Resource(name = "userotherServiceV20")
	private IUserotherService userotherService;
	
	@Resource(name = "vipNumLogServiceV20")
	private IVipNumLogService vipNumLogService;
	
	@Resource(name = "noticeServiceV20")
	private INoticeService noticeServiceV20;
	
	@Resource (name="adminUserService")
	private IAdminUserService adminUserService;
	
	@Resource (name="workSpaceInfoService")
	private IWorkSpaceInfoService workSpaceInfoService;
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
	 * 跳转到录入会员
	 * @param modelMap
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月10日 下午2:40:09
	 */
	@RequestMapping(value="addnewuser")
	public ModelAndView addNewUser(ModelMap modelMap){
		List<Long> labelList = new ArrayList<Long>();
		List<Long> focusList = new ArrayList<Long>();
		List<Long> jobid = new ArrayList<Long>();
		List<Long> jobpid = new ArrayList<Long>();
		
		List<WorkSpaceInfo> spaceList = workSpaceInfoService.findAll();
		org.springframework.data.domain.Page<Labels> industrysList= labelsService.labelList("job",0L, 0, 99);
		org.springframework.data.domain.Page<Labels> focus = labelsService.labelList("focus",null, 0, 99);
		org.springframework.data.domain.Page<Labels> joblist =labelsService.labelList("job",1411353792255L, 0, 99);
		// 兴趣
		Set<Labels> set = null;
		// 关注
		Set<Labels> focusset = null;
		// 职业
		Set<Labels> jobset = null;
		 
		//用户拓展项列表
		List<Userother> list = userotherService.findAllUserothers();
		
		org.springframework.data.domain.Page<Labels> alllist = labelsService.labelList(null,null, 0, 99);
		modelMap.put("favourite", alllist.getContent());
		modelMap.put("industrys", industrysList.getContent());
		modelMap.put("jobs", joblist.getContent());
		modelMap.put("focus", focus.getContent());
		modelMap.put("user", null);
		//用户所有列表list 关注 职业 兴趣
		modelMap.put("labelList", labelList);
		modelMap.put("focuslist", focusList);
		modelMap.put("spaceList", spaceList);
		modelMap.put("jobid", jobid);
		modelMap.put("jobpid", jobpid);
		modelMap.put("userotherlist",list);
		return new ModelAndView("admin/user/addNewUser",modelMap);
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
			//User user = userService.findByusernameAndPWD(MD5Util.encrypt(password), username);
			DES des=  new DES();
			String mdspsw= des.getEncString(password);
			System.out.println(mdspsw+"密文密码");
			AdminUser adminuser= adminUserService.findByusernameAndPWD(password, username);
			//Userinfo userinfo = userinfoService.getUserinfoByusernameAndPWD(username, MD5Util.encrypt(password));
			if(adminuser == null){
				modelMap.put("tips", "帐号或密码错误");
				return index(modelMap);
			}
			/*if(user.getRoot()>1){
				modelMap.put("tips", "非管理员不能进入后台");
				return index(modelMap);
			}*/
			request.getSession().setAttribute("tips", "");
			//request.getSession().setAttribute(R.User.SESSION_USER, user);
			request.getSession().setAttribute(R.User.SESSION_USER, adminuser);
			log.info("user login---"+adminuser.getId());
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
		request.getSession().setAttribute("tips", "");
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
		List<Classsort> industrys = classsortService.getClasssortsByPid(888888888888L);
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
				applyvip.setBirthday(format.parse(birth));
			}
			if(applyvip.getIndustry()!=null)
				applyvip.setIndustry(labelsService.findByID(applyvip.getIndustry().getId()));
			if(applyvip.getJob()!=null)
				applyvip.setJob(labelsService.findByID(applyvip.getJob().getId()));
			applyvipService.applyvipSaveOrUpdate(applyvip);
			if(charge==1){//已收费
				if(unum==null||unum.isEmpty()||begin==null||begin.isEmpty()||end==null||end.isEmpty()){
					String tips = "请填写会员号/开始/结束日期";
					modelMap.put("tips", tips);
					return new ModelAndView("admin/user/addapplyvip",modelMap);
				}else {
					User user = new User(applyvip);
					User u = userService.getByEmail(user.getEmail());
					if(u==null){//前台此帐号没注册过
						while(userService.checkForNickname(user.getNickname())){//昵称重复,随机追加一个字符
							user.setNickname(user.getNickname() + RandomUtil.getRandomeStringForLength(1));
						}
//						if(userService.checkForNickname(applyvip.getName())){
//							modelMap.put("tips", "昵称已被使用");
//							return new ModelAndView("admin/user/addapplyvip",modelMap);
//						}
						if(userService.checkForTelephone(applyvip.getTel())){
							modelMap.put("tips", "手机号已被使用");
							return new ModelAndView("admin/user/addapplyvip",modelMap);
						}
						if(userService.checkForEmail(applyvip.getEmail())){
							modelMap.put("tips", "邮箱已被使用");
							return new ModelAndView("admin/user/addapplyvip",modelMap);
						}
						if(unum!=null&&begin!=null&&end!=null){//选中缴费
							user.setUnum(unum);
							user.setVipdate(format.parse(begin));
							user.setVipenddate(format.parse(end));
							user.setRoot(2);
						}
						user = userService.userSaveOrUpdate(user);
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
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月10日 下午3:48:54
	 */
	@RequestMapping(value="userlist")
	public ModelAndView userList(HttpServletRequest request, ModelMap modelMap,Page<User> page,Integer charge,Integer sex,String userNO,String keyword,Long[] groups){
		List<Long> labelList = new ArrayList<Long>();
		if (groups != null) {
			labelList = Arrays.asList(groups);
		} else {
			labelList = null;
		}
		 
		AdminUser user =(AdminUser)request.getSession().getAttribute(R.User.SESSION_USER);
		
		//List<Labels> labels = labelsService.getLabelsByType(0);
		org.springframework.data.domain.Page<Labels> labels = labelsService.labelList("job", null, 0, 99);
		//新增业务处理 非系统管理员，只能查询该分点的数据
		if(user.getUsertype()==0){
			page = userService.getUserListForPage(page.getCurrentPage(),page.getPageSize(),charge,sex,userNO,keyword,labelList);
		}else if(user.getUsertype()==1){
			
		}else if(user.getUsertype()==2){
			page = userService.getUserListForPageAndSpace(page.getCurrentPage(),page.getPageSize(),charge,sex,userNO,keyword,labelList,user.getWorkspaceinfo().getId());
		}
	 
//		System.out.println(page.getCurrentPage());
		modelMap.put("labelList", labelList);
		//modelMap.put("labels", labels);
		modelMap.put("page", page);
		modelMap.put("charge", charge);
		modelMap.put("sex", sex);
		modelMap.put("userNO", userNO);
		modelMap.put("keyword", keyword);
		//modelMap.put("labels", labels.getContent());
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
		User user = userService.findById(userid);
		List<Long> labelList = new ArrayList<Long>();
		List<Long> focusList = new ArrayList<Long>();
		List<Long> jobList = new ArrayList<Long>();
		List<Long> jobid = new ArrayList<Long>();
		List<Long> jobpid = new ArrayList<Long>();
		
		org.springframework.data.domain.Page<Labels> industrysList= labelsService.labelList("job",0L, 0, 99);
		org.springframework.data.domain.Page<Labels> focus = labelsService.labelList("focus",null, 0, 99);
		org.springframework.data.domain.Page<Labels> joblist =labelsService.labelList("job",1411353792255L, 0, 99);
		//兴趣
		Set<Labels> set=  user.getFavourite();
		Iterator   it = set.iterator();
		while(it.hasNext()){
			Labels la =(Labels)it.next();
			labelList.add(la.getId());
		}
		//关注 
		Set<Labels> focusset=  user.getFocus();
		Iterator   focusit = focusset.iterator();
		while(focusit.hasNext()){
			Labels la =(Labels)focusit.next();
			focusList.add(la.getId());
		}
		// 职业
		Set<Labels> jobset = user.getJob();
		Iterator jobit = jobset.iterator();
		while (jobit.hasNext()) {
			Labels la = (Labels) jobit.next();
			jobList.add(la.getId());
		}
		 
		if(jobList!=null &&jobList.size()>0){
			List alllist1=labelsService.getLabelsByids(jobList.get(0).toString());
			for(int i=0;i<alllist1.size();i++){
				Labels la = (Labels) alllist1.get(i);
				jobid.add(la.getId());
				jobpid.add(la.getPid());
				//根据PID获取子级
				joblist = labelsService.labelList("job",la.getPid(), 0, 99);
			}
		}else{
			//根据PID获取子级
			joblist = labelsService.labelList("job",((Labels)industrysList.getContent().get(0)).getId(), 0, 99);
		}
		//用户拓展项列表
		List<Userother> list = userotherService.findAllUserothers();
		
		org.springframework.data.domain.Page<Labels> alllist = labelsService.labelList(null,null, 0, 99);
		modelMap.put("favourite", alllist.getContent());
		modelMap.put("industrys", industrysList.getContent());
		modelMap.put("jobs", joblist.getContent());
		modelMap.put("focus", focus.getContent());
		modelMap.put("user", user);
		//用户所有列表list 关注 职业 兴趣
		modelMap.put("labelList", labelList);
		modelMap.put("focuslist", focusList);
		modelMap.put("jobid", jobid);
		modelMap.put("jobpid", jobpid);
		modelMap.put("userotherlist",list);
		return new ModelAndView("admin/user/updateUser");
	}
	
	/**
	 * 保存录入用户信息
	 * @param modelMap
	 * @param user
	 * @param groups 领域标签id
	 * @param begin 会员开始日期
	 * @param end 会员结束日期
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月12日 下午3:34:15
	 */
	@RequestMapping(value="savenewuser")
	public ModelAndView saveNewUser(ModelMap modelMap,User user,Long[] groups,String begin,String end,Long jobid,String favouriteid){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		user.setId(System.currentTimeMillis()+ RandomUtil.getRandomeStringForLength(20));
		userService.userSaveOrUpdate(user);
		
		List<Long> labelList = new ArrayList<Long>();
		String focusid="";
		List<Long> focusList = new ArrayList<Long>();
		for(int i=0;i<groups.length;i++){
			if(focusid=="")
				focusid=groups[i].toString();
			else  
				focusid=focusid+","+groups[i];
		}
		//关注所有列表
		if (groups != null) {
			labelList = Arrays.asList(groups);
		} else {
			labelList = null;
		}
		try {
			org.springframework.data.domain.Page<Labels> alllist = labelsService.labelList(null,null, 0, 99);
			User origin = userService.findById(user.getId());
			
			if (labelList==null||labelList.size()<1||labelList.size()>300) {
				modelMap.put("tips", "关注标签数个数：1~3个");
			}else if (!origin.getUnum().equals(user.getUnum())&&userService.checkForUnum(user.getUnum())) {
				modelMap.put("tips", "会员号已存在");
			}else {
				if(user.getRoot()==2&&user.getUnum()!=null&&!user.getUnum().equals("")){
					VipNumLog vipNumLog = vipNumLogService.findByVipNO(user.getUnum());
					if(vipNumLog!=null){
						vipNumLog.setStatus(true);
						vipNumLogService.saveOrUpdate(vipNumLog);
					}
					
					origin.setUnum(user.getUnum());
					origin.setVipdate(format.parse(begin));
					origin.setVipenddate(format.parse(end));
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
				origin.setProvince(user.getProvince());
				origin.setCity(user.getCity());
				origin.setIcnum(user.getIcnum());
				origin.setIntroduction(user.getIntroduction());
				origin.setTeamintroduction(user.getTeamintroduction());
				origin.setTwonum(user.getTwonum());
				origin.setMinimg(user.getMinimg());
				origin.setMaximg(user.getMaximg());
				//后台录入的时候要设置默认密码为手机号@kcm 0831  发新用户邮件
				if(user.getPassword().equals("")){
					origin.setPassword(MD5Util.encrypt(user.getTelnum()));
					userService.newUserEmail(user);//新会员邮件
				}
				
				//保存职业
				origin.setJob(new HashSet<Labels>(labelsService.getLabelsByids(jobid.toString())));
				origin.setFocus(new HashSet<Labels>(labelsService.getLabelsByids(focusid)));
				//保存兴趣领域
				//origin.setFavourite(new HashSet<Labels>(labelsService.getLabelsByids(favouriteid)));
				
				origin.setViplevel(user.getViplevel());
				origin.setMypageurl(user.getMypageurl());
				origin.setIfindex(user.getIfindex());
				origin.setIfspace(user.getIfspace());
				origin.setIfvipshow(user.getIfvipshow());
				origin.setIcnum_type(user.getIcnum_type());
 				origin = userService.userSaveOrUpdate(origin);
				modelMap.put("tips", "新增会员成功,请手动修改是否缴费!");
			}
			org.springframework.data.domain.Page<Labels> joblist = labelsService.labelList("job",null, 0, 99);
			org.springframework.data.domain.Page<Labels> focus = labelsService.labelList("focus",null, 0, 99);
			
			//返回已关注列表 
			Set<Labels> focusset=  origin.getFocus();
			Iterator   focusit = focusset.iterator();
			while(focusit.hasNext()){
				Labels la =(Labels)focusit.next();
				focusList.add(la.getId());
			}
			//modelMap.put("favourite", alllist.getContent());  //兴趣
			
			modelMap.put("industrys", joblist.getContent());
			modelMap.put("jobs", joblist.getContent());
			modelMap.put("user", origin);
			//用户拓展项列表
			List<Userother> list = userotherService.findAllUserothers();
			//关注 已关注列表 .关注全列表
			modelMap.put("focus", focus.getContent()); 
			modelMap.put("focuslist", focusList);
			modelMap.put("userotherlist",list);
			 
			
		} catch (Exception e) {
			modelMap.put("tips", "保存失败请刷新页面或联系管理员");
			// TODO: handle exception
		}
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
	public ModelAndView saveUser(ModelMap modelMap,User user,Long[] groups,String begin,String end,Long jobid,String favouriteid){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<Long> labelList = new ArrayList<Long>();
		String focusid="";
		List<Long> focusList = new ArrayList<Long>();
		for(int i=0;i<groups.length;i++){
			if(focusid=="")
				focusid=groups[i].toString();
			else  
				focusid=focusid+","+groups[i];
		}
		//关注所有列表
		if (groups != null) {
			labelList = Arrays.asList(groups);
		} else {
			labelList = null;
		}
		try {
			org.springframework.data.domain.Page<Labels> alllist = labelsService.labelList(null,null, 0, 99);
			User origin = userService.findById(user.getId());
			
			if (labelList==null||labelList.size()<1||labelList.size()>300) {
				modelMap.put("tips", "关注标签数个数：1~3个");
			}else if (!origin.getUnum().equals(user.getUnum())&&userService.checkForUnum(user.getUnum())) {
				modelMap.put("tips", "会员号已存在");
			}else {
				if(user.getRoot()==2&&user.getUnum()!=null&&!user.getUnum().equals("")){
					VipNumLog vipNumLog = vipNumLogService.findByVipNO(user.getUnum());
					if(vipNumLog!=null){
						vipNumLog.setStatus(true);
						vipNumLogService.saveOrUpdate(vipNumLog);
					}
					//如果是新续费的，发续费邮件 @kcm 0831
					if(origin.getRoot()==4 && user.getRoot()==2){
						userService.renewalEmail(user);//会员续费邮件
					}
					origin.setUnum(user.getUnum());
					origin.setVipdate(format.parse(begin));
					origin.setVipenddate(format.parse(end));
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
				origin.setProvince(user.getProvince());
				origin.setCity(user.getCity());
				origin.setIcnum(user.getIcnum());
				origin.setIntroduction(user.getIntroduction());
				origin.setTeamintroduction(user.getTeamintroduction());
				origin.setTwonum(user.getTwonum());
				origin.setMinimg(user.getMinimg());
				origin.setMaximg(user.getMaximg());
				//保存职业
				origin.setJob(new HashSet<Labels>(labelsService.getLabelsByids(jobid.toString())));
				origin.setFocus(new HashSet<Labels>(labelsService.getLabelsByids(focusid)));
				//保存兴趣领域
				//origin.setFavourite(new HashSet<Labels>(labelsService.getLabelsByids(favouriteid)));
				
				origin.setViplevel(user.getViplevel());
				origin.setMypageurl(user.getMypageurl());
				origin.setIfindex(user.getIfindex());
				origin.setIfspace(user.getIfspace());
				origin.setIfvipshow(user.getIfvipshow());
				origin.setIcnum_type(user.getIcnum_type());
 				origin = userService.userSaveOrUpdate(origin);
				modelMap.put("tips", "修改成功");
			}
			org.springframework.data.domain.Page<Labels> joblist = labelsService.labelList("job",null, 0, 99);
			org.springframework.data.domain.Page<Labels> focus = labelsService.labelList("focus",null, 0, 99);
			
			//返回已关注列表 
			Set<Labels> focusset=  origin.getFocus();
			Iterator   focusit = focusset.iterator();
			while(focusit.hasNext()){
				Labels la =(Labels)focusit.next();
				focusList.add(la.getId());
			}
			//modelMap.put("favourite", alllist.getContent());  //兴趣
			
			modelMap.put("industrys", joblist.getContent());
			modelMap.put("jobs", joblist.getContent());
			modelMap.put("user", origin);
			//用户拓展项列表
			List<Userother> list = userotherService.findAllUserothers();
			//关注 已关注列表 .关注全列表
			modelMap.put("focus", focus.getContent()); 
			modelMap.put("focuslist", focusList);
			modelMap.put("userotherlist",list);
			 
			
		} catch (Exception e) {
			modelMap.put("tips", "修改失败请刷新页面");
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
			User user = userService.findById(id);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("无此会员信息");
			}else {
				user.setIfvipshow(isshow);
				user.setVipshowsettime(new Date());
				user = userService.userSaveOrUpdate(user);
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
			User user = userService.findById(id);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("无此会员信息");
			}else {
				user.setIfspace(isspace);
				user.setSpacesettime(new Date());
				user = userService.userSaveOrUpdate(user);
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
		page = sysnetworkService.getSysnetworkForPage(page.getCurrentPage(), page.getPageSize(), keyword);
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
		sysnetwork = sysnetworkService.sysnetworkSaveOrUpdate(sysnetwork);
		if(sysnetwork!=null){
			List<User> users = userService.getUserListByRoot(-1);
			List<Broadcast> broadcastList = new ArrayList<Broadcast>();
			for (User user : users) {
				Broadcast broadcast = new Broadcast();
				broadcast.setSysnetwork(sysnetwork);
				broadcast.setUser(user);
				broadcastList.add(broadcast);
//				try {
//					broadcastService.broadcastSaveOrUpdate(broadcast);
//				} catch (Exception e) {
//					// TODO: handle exception
//					log.error("保存广播出错------" + user.getNickname());
//				}
			}
			
			broadcastService.broadListBatchSave(broadcastList);
//			pushService.push("sys", sysnetwork.getId(), sysnetwork.getTitle(), "新广播", Platform.android_ios(), new String[]{"yiqi"}, new String[]{"version4"});
//			pushService.push("yiqi", "android,ios", sysnetwork.getTitle(), 2);
		}else {
			modelMap.put("tips", "保存广播失败");
		}
		return new ModelAndView("admin/user/broadcastlist");
	}
	
	/**
	 * 查看公告列表 
	 * @param request
	 * @param modelMap
	 * @param page
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value="noticemsglist")
	public ModelAndView noticeMsgList(HttpServletRequest request,ModelMap modelMap,Page<Notice> page,String keyword){
		page=noticeServiceV20.getNoticeForPage(page.getCurrentPage(), page.getPageSize(), keyword);
		modelMap.put("page", page);
		modelMap.put("keyword", keyword);
		return new ModelAndView("admin/system/noticemsgList"); 
	}
	
	/**
	 * 下发公告
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年6月11日 下午2:31:35
	 */
	@RequestMapping(value="createnotice")
	public ModelAndView createNotice(ModelMap modelMap){
		modelMap.put("tips", "");
		return new ModelAndView("admin/system/createNotice");
	}
	 	
	
	/**
	 * 保存公告
	 * @param request
	 * @param sysnetwork
	 * @return
	 *  
	 */
	@RequestMapping(value = "savenotice")
	public ModelAndView saveNotice(ModelMap modelMap, Notice notice,HttpServletRequest request) {
		//User user =(User)request.getSession().getAttribute(R.User.SESSION_USER);
		AdminUser user =(AdminUser)request.getSession().getAttribute(R.User.SESSION_USER);
		
		
		notice.setSpaceInfo(user.getWorkspaceinfo());
		
		notice.setUser(user); //创建者
		notice = noticeServiceV20.noticeSaveOrUpdate(notice);
		if(notice!=null){
			modelMap.put("tips", "保存公告成功");
		}else {
			modelMap.put("tips", "保存公告失败,请稍后再试");
		}
		return new ModelAndView("admin/system/noticemsgList");
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
		page = feedbackService.getFeedbackForPage(page.getCurrentPage(), page.getPageSize(), keyword);
		modelMap.put("keyword", keyword);
		modelMap.put("page", page);
		return new ModelAndView("admin/display/feedbackList");
	}
}
