package cn.yi.gather.v20.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.entity.Broadcast;
import cn.yi.gather.v20.entity.CurrentMsg;
import cn.yi.gather.v20.entity.Feedback;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.Resetpassword;
import cn.yi.gather.v20.entity.ShoutContent;
import cn.yi.gather.v20.entity.Sysnetwork;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.entity.UserFocus;
import cn.yi.gather.v20.entity.Userother;
import cn.yi.gather.v20.entity.UserotherInfo;
import cn.yi.gather.v20.service.IBroadcastService;
import cn.yi.gather.v20.service.IClasssortService;
import cn.yi.gather.v20.service.ICurrentMsgService;
import cn.yi.gather.v20.service.IEmailService;
import cn.yi.gather.v20.service.IFeedbackService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.IResetpasswordService;
import cn.yi.gather.v20.service.IShoutContentService;
import cn.yi.gather.v20.service.ISignLogService;
import cn.yi.gather.v20.service.ISysnetworkService;
import cn.yi.gather.v20.service.IUserFocusService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IUserotherService;
import cn.yi.gather.v20.service.IUserotherinfoService;

import com.common.Jr;
import com.common.R;
import com.tools.utils.BeanUtil;
import com.tools.utils.FileUtil;
import com.tools.utils.ImageUtil;
import com.tools.utils.JSONUtil;
import com.tools.utils.MD5Util;
import com.tools.utils.RandomUtil;

@Controller("userControllerV20")
@RequestMapping(value = "v20/user")
public class UserController {
	private static Logger log = Logger.getLogger(UserController.class);
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "classsortServiceV20")
	private IClasssortService classsortService;
	
	@Resource(name = "labelsServiceV20")
	private ILabelsService labelsService;
	
	@Resource(name = "resetpasswordServiceV20")
	private IResetpasswordService resetpasswordService;
	
	@Resource(name = "emailServiceV20")
	private IEmailService emailService;
	
	@Resource(name = "userFocusServiceV20")
	private IUserFocusService userFocusService;
	
	@Resource(name = "broadcastServiceV20")
	private IBroadcastService broadcastService;
	
	@Resource(name = "sysnetworkServiceV20")
	private ISysnetworkService sysnetworkService;
	
	@Resource(name = "feedbackServiceV20")
	private IFeedbackService feedbackService;
	
	@Resource(name = "signLogServiceV20")
	private ISignLogService signLogService;
	
	@Resource(name = "userotherServiceV20")
	private IUserotherService userotherService;
	
	@Resource(name = "userotherinfoServiceV20")
	private IUserotherinfoService userotherinfoService;
	
	@Resource(name = "currentMsgServiceV20")
	private ICurrentMsgService currentMsgService;
	
	@Resource(name = "shoutContentServiceV20")
	private IShoutContentService shoutContentService;
	
	@Resource(name = "jPushServiceV20")
	private IJPushService pushService;
	
	/**
	 * 用户登陆 2.0
	 * @param request
	 * @param response
	 * @param type
	 * @param username
	 * @param password
	 * @author Lee.J.Eric
	 * @time 2014年10月20日 下午4:23:50
	 */
	@RequestMapping(value = "login")
	public void login(HttpServletRequest request,HttpServletResponse response, String type,String username,String password) {
		Jr jr = new Jr();
		jr.setMethod("login");
		if (username==null||username.isEmpty()||password==null||password.isEmpty()) {
			jr.setCord(-1);
			jr.setMsg("参数错误");
		} else {
			User user = userService.findByusernameAndPWD(password, username);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("帐号或密码错误");
			}else {
				if(user.getRoot()==UserRoot.OVREDUE.getCode()){
					jr.setMsg("过期会员登陆");
				}else if(user.getRoot()==UserRoot.REGISTER.getCode()) {
					jr.setMsg("非会员登陆");
				}else {
					jr.setMsg("登陆成功");
				}
				jr.setData(user);
				jr.setCord(0);
				request.getSession().setAttribute(R.User.SESSION_USER, user);
				log.info("user login---"+user.getId());
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
	 * 用户注册
	 * @param response
	 * @param type
	 * @param user
	 * @param labels
	 * Lee.J.Eric
	 * 2014 2014年5月29日 下午1:13:41
	 */
	@RequestMapping(value="register")
	public void register(HttpServletResponse response,String type,User user,String labels,String birth){
		Jr jr = new Jr();
		jr.setMethod("register");
		if (user==null) {
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else if (user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getNickname() == null || user.getNickname().trim().isEmpty()) {//去掉手机非空验证
			jr.setCord(1);
			jr.setMsg("昵称/邮箱不能为空");
		}else if(userService.checkForNickname(user.getNickname())){
			jr.setCord(2);
			jr.setMsg("此昵称已被占用");
		}else if (!user.getEmail().isEmpty()&&userService.checkForEmail(user.getEmail())) {
			jr.setCord(3);
			jr.setMsg("此邮箱已被占用");
		}/*else if (user.getTelnum() != null&&!user.getTelnum().isEmpty()&&userService.checkForTelephone(user.getTelnum())) {
			jr.setCord(4);
			jr.setMsg("此电话号码已被使用");
		}*/else if (StringUtils.isBlank(labels)) {
			jr.setCord(5);
			jr.setMsg("兴趣不能为空");
		}else {
			String[] labelarr = labels.split(",");
			if(labelarr.length>3||labelarr.length==0){
				jr.setCord(6);
				jr.setMsg("标签为1到3个");
			}else {
				//user.setLabel(labelsService.getLabelsByids(labels));
				//职业信息
				//if(user.getIndustry()!=null)
				//	user.setIndustry(classsortService.getClasssortsByID(user.getIndustry().getId()));
				//if(user.getJob()!=null)
				//	user.setJob(classsortService.getClasssortsByID(user.getJob().getId()));
				//常用邮箱和常用电话
				if(birth!=null&&!birth.isEmpty()){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						user.setBirthday(format.parse(birth));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				user = userService.userSaveOrUpdate(user);
				jr.setData(user);
				jr.setCord(0);
				jr.setMsg("注册成功");
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
	 * 用户退出
	 * @param request
	 * @param response
	 * @param userid
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月29日 下午1:14:32
	 */
	@RequestMapping(value="logout")
	public void logout(HttpServletRequest request,HttpServletResponse response,String userid,String type){
		Jr jr = new Jr();
		jr.setMethod("logout");
		request.getSession().removeAttribute(R.User.SESSION_USER);
		jr.setCord(0);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
			log.info("用户退出成功---"+userid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** 2.0
	 * 获取用户信息/通过用户ID获取用户详情
	 * @param response
	 * @param userid
	 * @param type
	 * 2014-09-15
	 */
	@RequestMapping(value="getuser")
	public void getUser(HttpServletResponse response,String userid,String type){
		Jr jr = new Jr();
		jr.setMethod("getuser");
		if (userid == null || userid.equals("")) {
			jr.setCord(-1);
		}else {
			User user = userService.findById(userid);
			if(user!=null){
				Set<String> set=new HashSet<String>();
				set.add("id");
				set.add("nickname");
				set.add("minimg");
				set.add("maximg");
				set.add("unum");
				set.add("introduction");
				set.add("sex");
				set.add("email");
				set.add("telnum");
				set.add("wechat");
				set.add("age");
				set.add("birthday");
				set.add("constellation");
				set.add("province");
				set.add("city");
				set.add("mypageurl");
				set.add("favourite.id");
				set.add("favourite.zname");
				set.add("favourite.pid");
				set.add("job.id");
				set.add("job.zname");
				set.add("job.pid");
				set.add("focus.id");
				set.add("focus.zname");
				set.add("focus.pid");
				set.add("createdate");
				set.add("myactnum");
				set.add("joinactnum");
				set.add("ifindex");
				set.add("ifspace");
				set.add("ifvipshow");
				set.add("root");
				//set.add("investmentdemand");
				set.add("vipdate");
				set.add("vipenddate");
				set.add("viplevel");
				set.add("virtualname");
				set.add("demandsnum");
				set.add("teamintroduction");
				//set.add("dream");
				//set.add("advertisement");
				set.add("mediaurl");
				//set.add("longdemand");
				//set.add("businessplan");
				set.add("twonum");
				set.add("pakageaddress");
				set.add("regaddressnum");
				set.add("regtgnum");
				set.add("signnum");
				set.add("userstart");
				set.add("userothers.texts");
				set.add("userothers.userother.ztitle");
				set.add("userothers.userother.id");
				set.add("userothers.userother.infotype");
				set.add("userothers.id");
				jr.setData(BeanUtil.getFieldValueMap(user, set));
				jr.setCord(0);
				jr.setMsg("获取成功");
			}else {
				jr.setCord(1);
				jr.setMsg("此用户不存在");
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
	 * 检测昵称是否已存在 2.0
	 * @param request
	 * @param response
	 * @param nickname
	 * Lee.J.Eric
	 * 2014 2014年5月30日 上午11:45:15
	 */
	@RequestMapping(value="checknickname")
	public void checkNickname(HttpServletResponse response,String nickname){
		Jr jr = new Jr();
		jr.setMethod("checknickname");
		if(nickname==null||nickname.isEmpty()){
			jr.setCord(1);
			jr.setMsg("昵称为空");
		}else if (userService.checkForNickname(nickname.trim())){
			jr.setCord(-1);
			jr.setMsg("此昵称已存在");
		}else{
			jr.setCord(0);
			jr.setMsg("此昵称可以使用");
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
	 * 检测邮箱是否已被使用 2.0
	 * @param request
	 * @param response
	 * @param email
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月30日 上午11:52:05
	 */
	@RequestMapping(value="checkemail")
	public void checkEmail(HttpServletResponse response,String email,String type){
		Jr jr = new Jr();
		jr.setMethod("checkemail");
		if(email==null||email.isEmpty()){
			jr.setCord(1);
			jr.setMsg("邮箱为空");
		}else if(userService.checkForEmail(email)){
			jr.setCord(-1);
			jr.setMsg("此邮箱已被占用");
		}else {
			jr.setCord(0);
			jr.setMsg("此邮箱可以使用");
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
	 * 检测手机是否已被使用 2.0
	 * @param request
	 * @param response
	 * @param tel
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月30日 上午11:54:58
	 */
	@RequestMapping(value="checktel")
	public void checkTel(HttpServletRequest request,HttpServletResponse response,String tel,String type){
		Jr jr = new Jr();
		jr.setMethod("checktel");
		if(tel==null||tel.isEmpty()){
			jr.setCord(1);
			jr.setMsg("手机号为空");
		}else if (userService.checkForTelephone(tel)) {
			jr.setCord(-1);
			jr.setMsg("此手机号已被占用");
		}else {
			jr.setCord(0);
			jr.setMsg("此手机号可以使用");
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
	 * 检查身份证号唯一性
	 * @param request
	 * @param response
	 * @param idnum
	 * @param type
	 * @author Lee.J.Eric
	 * @time 2015年1月9日 下午6:08:40
	 */
	@RequestMapping(value="checkidnum")
	public void checkIdnum(HttpServletRequest request,HttpServletResponse response,String idnum,String type){
		Jr jr = new Jr();
		jr.setMethod("checktel");
		if(idnum==null||idnum.isEmpty()){
			jr.setCord(1);
			jr.setMsg("身份证号为空");
		}else if (userService.checkForIdnum(idnum)) {
			jr.setCord(-1);
			jr.setMsg("此身份证号已被占用");
		}else {
			jr.setCord(0);
			jr.setMsg("此身份证号可以使用");
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
	 * 修改密码 2.0
	 * @param response
	 * @param user
	 * @param newpwd
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月30日 上午11:58:37
	 */
	@RequestMapping(value="updatepwd")
	public void updatePassword(HttpServletResponse response,User user,String oldpwd,String newpwd,String type){
		Jr jr = new Jr();
		jr.setMethod("updatepwd");
		if(user==null||newpwd ==null||newpwd.isEmpty()){
			jr.setCord(-1);
			jr.setMsg("参数为空");
		}else{
			User user2 = userService.findByIdAndPassword(user.getId(), oldpwd);
			if(user2==null){
				jr.setCord(1);//帐号错误或不存在此用户
				jr.setMsg("帐号或旧密码错误");
			}else {
				user2.setPassword(newpwd);
				user2 = userService.userSaveOrUpdate(user2);
				if(user2==null){
					jr.setCord(3);//更新用户出错
					jr.setMsg("密码修改，请重新尝试");
				}else {
					jr.setCord(0);//更新成功
					jr.setData(user2);
					jr.setMsg("密码修改成功");
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
	 * 检查会员编号是否已被使用 2.0
	 * @param request
	 * @param response
	 * @param unum
	 * Lee.J.Eric
	 * 2014-3-15 下午1:58:55
	 */
	@RequestMapping(value="checkunum")
	public void checkUnum(HttpServletRequest request,HttpServletResponse response,String unum){
		Jr jr = new Jr();
		jr.setMethod("checkunum");
		if(unum==null||unum.isEmpty()){
			jr.setCord(1);
			jr.setMsg("会员号为空");
		}else if (userService.checkForUnum(unum)){
			jr.setCord(-1);
			jr.setMsg("此会员号已被占用");
		}else{
			jr.setCord(0);
			jr.setMsg("此会员号可以使用");
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
	 * 获取首页会员列表信息
	 * @param response
	 * @param page
	 * @param size
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午3:20:42
	 */
	@RequestMapping(value="getindexuserlist")
	public void getIndexUserList(HttpServletResponse response,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("getindexuserlist");
		if(page==null||page<0||size==null||size<0){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}else {
			Page<User> list = userService.getIndexUser(page, size,1);
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(list.getContent());
			jr.setTotal(list.getTotalElements());
			jr.setCord(0);
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
	 * 更新用户 2.0
	 * @param response
	 * @param type
	 * @param id
	 * @param param
	 * @author Lee.J.Eric
	 * @time 2014年11月5日 下午5:53:51
	 */
	@RequestMapping(value="updateuser")
	public void updateUser(HttpServletResponse response, String type,String id,@RequestParam Map<String, Object> param) {
		Jr jr = new Jr();
		jr.setMethod("updateuser");
		Set<String> keys = param.keySet();
		keys.remove("id");
		keys.remove("type");
		if(id==null||id.isEmpty()||param.isEmpty()||type==null||type.isEmpty()){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else { 
			User user = userService.findById(id);
			if(user==null){
				jr.setCord(1);//找不到用户
				jr.setMsg("此用户不存在");
			}else {
				Class<?> userclass = user.getClass();
				try {
					for (String key : keys) {
						if (key.equals("id") || key.equals("root")|| key.equals("isdel")
								|| key.equals("vipenddate")|| key.equals("vipdate")|| key.equals("createdate")||key.equalsIgnoreCase("realname")||key.equalsIgnoreCase("password")) {// 不作修改字段
							continue;
						}else if (key.equals("job")) {//职业
							user.setJob(new HashSet<Labels>(labelsService.getLabelsByids(param.get(key).toString())));
						}else if (key.equals("favourite")) {//爱好
							user.setFavourite(new HashSet<Labels>(labelsService.getLabelsByids(param.get(key).toString())));
						}else if (key.equals("focus")) {//关注的领域
							user.setFocus(new HashSet<Labels>(labelsService.getLabelsByids(param.get(key).toString())));
						}else if (key.equals("nickname")) {
							if(user.getNickname()!=null&&!user.getNickname().equals(param.get(key).toString())&&!param.get(key).toString().isEmpty()&&userService.checkForNickname(param.get(key).toString())){
								jr.setCord(2);
								jr.setMsg("此昵称已存在");
								throw new Exception("nickname is exist");
							}else {
								user.setNickname(param.get(key).toString());
							}
						}else if (key.equals("unum")) {
							if(user.getUnum()!=null&&!user.getUnum().equals(param.get(key).toString())&&!param.get(key).toString().isEmpty()&&userService.checkForUnum(param.get(key).toString())){
								jr.setCord(3);
								jr.setMsg("此会员号已被占用");
								throw new Exception("unum is exist");
							}else {
								user.setUnum(param.get(key).toString());
							}
						}else if (key.equals("email")) {
							if(user.getEmail()!=null&&!user.getEmail().equals(param.get(key).toString())&&!param.get(key).toString().isEmpty()&&userService.checkForEmail(param.get(key).toString())){
								jr.setCord(4);
								jr.setMsg("此邮箱已被占用");
								throw new Exception("email is exist");
							}else {
								user.setEmail(param.get(key).toString());
							}
						}else if (key.equals("telnum")) {
							if(user.getTelnum()!=null&&!user.getTelnum().equals(param.get(key).toString())&&!param.get(key).toString().isEmpty()&&userService.checkForTelephone(param.get(key).toString())){
								jr.setCord(5);
								jr.setMsg("此手机号已被占用");
								throw new Exception("telnum is exist");
							}else {
								user.setTelnum(param.get(key).toString());
							}
						}else {
							Field field = userclass.getDeclaredField(key);
							String fType = field.getGenericType().toString();
							field.setAccessible(true);//设置些属性是可以访问的
							if(fType.endsWith("String")){
								field.set(user, param.get(key));
							}else if(fType.endsWith("Long")) {
								field.set(user,new Long(param.get(key).toString())) ;
							}else if(fType.endsWith("Integer")) {
								field.set(user, new Integer(param.get(key).toString()));
							}else if(fType.endsWith("Date")) {
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								field.set(user, format.parse(param.get(key).toString()));
							}
						}
					}
					user = userService.userSaveOrUpdate(user);
					jr.setCord(0);
					jr.setData(user);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					jr.setCord(1);
//					jr.setMsg("更新失败");
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
	 * 用户自定义项列表 2.0
	 * 
	 * @param response
	 * @param type
	 * @param userid
	 * @author Lee.J.Eric
	 * @time 2014年11月6日 下午2:43:56
	 */
	@RequestMapping(value = "userotherlist")
	public void userotherList(HttpServletResponse response, String type, String userid) {
		Jr jr = new Jr();
		jr.setMethod("userotherlist");
		if (type == null) {
			jr.setCord(-1);
			jr.setMsg("非法传值");
		} else {
			List<Userother> list = userotherService.findAllUserothers();
			jr.setData(list);
			jr.setCord(0);
			jr.setMsg("获取成功");
			jr.setTotal(list.size());
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response, "yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 更新用户自定义项
	 * @param response
	 * @param type
	 * @param userid
	 * @param infos 
	 * @author Lee.J.Eric
	 * @time 2014年11月6日 下午5:21:04
	 */
	@RequestMapping(value = "updateuserothers")
	public void updateUserothers(HttpServletResponse response,String type,String userid,UserotherInfo infos){
		Jr jr = new Jr();
		jr.setMethod("updateuserothers");
		if(type==null||userid==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User user = userService.findById(userid);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("用户不存在");
			}else {
				Set<UserotherInfo> set = user.getUserothers();
				//先判断该KEY 是否存在  存在  则直接替换value
				for (UserotherInfo info:set) {
					if(info.getId().equals(infos.getId())){
						if(infos.getTexts().equals("")){
							user.getUserothers().remove(info);
							user = userService.userSaveOrUpdate(user);
							userotherinfoService.deleteById(info.getId());
						}else if(!infos.getTexts().equals("")){
							info.setTexts(infos.getTexts());
							infos=userotherinfoService.saveOrUpdateUserother(info);
						}
						break;
					}
				}
				//1 没有存在 infos 还是用户传的infos   2 之前有存在 已经在循环里面直接替换了
				if(!infos.getTexts().equals("")){
					infos = userotherinfoService.saveOrUpdateUserother(infos);
					if(infos!=null){
						user.getUserothers().add(infos);
						user = userService.userSaveOrUpdate(user);
					}
				}
				
				jr.setCord(0);
				jr.setMsg("操作成功");
				jr.setData(user);
			}
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response, "yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 申请密码找回
	 * @param response
	 * @param email
	 * @param request
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午5:04:26
	 */
	@RequestMapping(value="applyresetpwd")
	public void applyResetpwd(HttpServletResponse response,String email,HttpServletRequest request,String type){
		String basePath = R.Common.BASEPATH;
		String action = "v20/user/resetmypwd";
		Jr jr = new Jr();
		jr.setMsg("applyresetpwd");
		if(email==null||email.isEmpty()){
			jr.setCord(-1);//非法传参
			jr.setMsg("邮箱不能为空");
		}else {
			User user = userService.getByEmail(email);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("用户未设置联系邮箱");
			}else {
				Resetpassword resetpassword = new Resetpassword();
				resetpassword.setUser(user.getId());
				resetpassword = resetpasswordService.createResetpassword(resetpassword);
				if(resetpassword==null||!emailService.applyResetPassword(email,resetpassword.getId(),basePath+action)){
					jr.setCord(2);
					jr.setMsg("找回失败，请重新找回");
				}else {
					jr.setCord(0);
					jr.setMsg("申请成功，请登陆联系邮箱重置密码");
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
	 * 邮件链接重置密码
	 * @param id
	 * @param modelMap
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年4月17日 下午5:19:42
	 */
	@RequestMapping(value="resetmypwd")
	public ModelAndView resetMyPWD(String id,ModelMap modelMap){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, -1);
		Resetpassword resetpassword = resetpasswordService.searchResetpassword(id);
		if(resetpassword==null||resetpassword.getFlag()!=0||resetpassword.getCreatedate().before(calendar.getTime())){
			return new ModelAndView("resetpwderror");//错误页
		}else {
			resetpassword.setFlag(1);
			resetpasswordService.updateResetpassword(resetpassword);
			modelMap.put("userid", resetpassword.getUser());
			return new ModelAndView("resetpwd");
		}
	}
	
	/**
	 * 找回密码-重置密码
	 * @param userid
	 * @param newpwd
	 * @param modelMap
	 * Lee.J.Eric
	 * 2014 2014年4月18日 下午3:26:47
	 */
	@RequestMapping(value="setmypwd")
	public void setMyPWD(String userid,String newpwd,ModelMap modelMap,HttpServletResponse response,HttpServletRequest request){
		if(userid==null||userid.isEmpty()||newpwd==null||newpwd.isEmpty()){
			modelMap.put("tips", "新密码不能为空");
		}else {
			User user =  userService.findById(userid);
			if(user==null){
				
			}else {
				user.setPassword(MD5Util.encrypt(newpwd));
				user = userService.userSaveOrUpdate(user);
			}
		}
		try {
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			response.sendRedirect(basePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 重置用户新密码
	 * @param response
	 * @param type
	 * @param userid
	 * @param newpwd
	 * @author Lee.J.Eric
	 * @time 2014年12月16日 下午4:43:18
	 */
	@RequestMapping(value = "resetpwd")
	public void resetPWD(HttpServletResponse response,String type,String userid,String newpwd){
		Jr jr = new Jr();
		jr.setMethod("resetpwd");
		if(type==null||userid==null||newpwd==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			try {
				User user = userService.findById(userid);
				user.setPassword(newpwd);
				userService.userSaveOrUpdate(user);
				jr.setCord(0);
				jr.setMsg("重置成功");
			} catch (Exception e) {
				// TODO: handle exception
				jr.setCord(1);
				jr.setMsg("重置失败");
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
	 * 2.0  用户/会员列表
	 * @param response
	 * @param type        访问端：    android  安卓   ios IOS   web 网页端
	 * @param page        当前页：    首页 0
	 * @param size        每页显示记录数
	 * @param listtype    列表类型 : all  所有用户     vip  会员列表   
	 * @param listwhere   列表条件： label 按标签     act  按活动    search 按关键字  nowhere 无条件
	 * @param keyword     标签ID  活动ID  关键字
	 * @param listorderby 注册时间排序：asc 正序   desc 倒序
	 * 2014 2014-09-02
	 */
	@RequestMapping(value="userlist")
	public void userlist(HttpServletResponse response,String type,Integer page,Integer size,String listtype,String listwhere,String keyword,String listorderby){
		Jr jr = new Jr();
		jr.setMethod("userlist");
		if("nowhere".equals(listwhere)){
			keyword="";
		}
		if(listtype==null||page==null||page<0||size==null||size<0||listwhere==null||listorderby==null||keyword==null){//非法传值
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}else {
			try {
				Page<User> list = userService.findUserList(page, size,listtype,listwhere, keyword,listorderby);
				if(list!=null){
					Set<String> set=new HashSet<String>();
					set.add("id");
					set.add("nickname");//妮称
					set.add("minimg");//头像
					set.add("userstart");//   用户状态 0 求勾搭 1 暂时不勾搭
					set.add("ifspace");
					set.add("sex");//性别 0 男
					set.add("job.zname");//职业
					set.add("root");//权限 2 是会员 3 非会员
					jr.setPagenum(page+1);
					jr.setPagecount(list.getTotalPages());
					jr.setPagesum(list.getNumberOfElements());
					jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
					jr.setTotal(list.getTotalElements());
				}
				jr.setCord(0);
				jr.setMsg("获取成功");
			} catch (Exception e) {
				e.printStackTrace();
				jr.setCord(1);
				jr.setMsg("获取失败");
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
	 * 2.0  谁在空间 在空间的用户列表
	 * @param response
	 * @param type        访问端：    android  安卓   ios IOS   web 网页端
	 * @param page        当前页：    首页 0
	 * @param size        每页显示记录数
	 * @param listtype    列表类型 : all  所有用户(预留可能需要获取含有在空间的游客)     vip  会员列表   
	 * @param listorderby 签到时间排序：asc 正序   desc 倒序
	 * 2014 2014-09-04
	 */
	@RequestMapping(value="spaceuserlist")
	public void spaceuserlist(HttpServletResponse response,String type,Integer page,Integer size,String listtype,String listorderby){
		Jr jr = new Jr();
		jr.setMethod("spaceuserlist");
		if(listtype==null||page==null||page<0||size==null||size<0||listorderby==null){//非法传值
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}else {
			try {
				Page<User> list = userService.spaceuserlist(page, size,listtype,listorderby);
				if(list!=null){
					Set<String> set=new HashSet<String>();
					set.add("id");
					set.add("nickname");
					set.add("minimg");
					set.add("job.id");
					set.add("job.zname");
					set.add("userstart");//   0不在空间    ||   1求勾搭   ||   2忙碌
					set.add("sex");
					jr.setPagenum(page+1);
					jr.setPagecount(list.getTotalPages());
					jr.setPagesum(list.getNumberOfElements());
					jr.setData(BeanUtil.getFieldValueMapForList(list.getContent(), set));
					jr.setTotal(list.getTotalElements());
				}
				jr.setCord(0);
				jr.setMsg("获取成功");
			} catch (Exception e) {
				e.printStackTrace();
				jr.setCord(1);
				jr.setMsg("获取失败");
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
	 * 2.0会员收藏---名片夹
	 * @param response
	 * @param type
	 * @param myid
	 * @param vipid
	 * Lee.J.Eric
	 * 2014年9月26日 下午5:01:50
	 */
	@RequestMapping(value="focusvip",method = RequestMethod.POST)
	public void focusVip(HttpServletResponse response,String type,String myid,String vipid){
		Jr jr = new Jr();
		jr.setMethod("focusvip");
		if(myid==null||vipid==null){
			jr.setCord(-1);
			jr.setMsg("用户id不能为空");
		}else if (myid.equals(vipid)) {
			jr.setCord(2);
			jr.setMsg("您不能关注您自己");
		}else {
			User me = userService.findById(myid);
			User who= userService.findById(vipid);
			if (me == null||who ==null) {
				jr.setCord(3);;
				jr.setMsg("会员不存在");
			}else {
				UserFocus userFocus = userFocusService.findByMeAndWhoAndRelation(me, who, 0);
				if(userFocus==null){
					userFocus = new UserFocus();
					userFocus.setMe(me);
					userFocus.setWho(who);
					userFocus = userFocusService.saveOrUpdate(userFocus);
					if(userFocus==null){
						jr.setCord(1);
						jr.setMsg("关注失败");
					}else {
						jr.setCord(0);
						jr.setMsg("关注成功");
					}
				}else {
					jr.setCord(0);
					jr.setMsg("关注成功");
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
	 * 2.0 取消会员收藏-名片夹
	 * @param response
	 * @param type
	 * @param myid
	 * @param vipid
	 * Lee.J.Eric
	 * 2014年9月26日 下午5:00:58
	 */
	@RequestMapping(value="canclefocusvip",method = RequestMethod.POST)
	public void cancleFocusVip(HttpServletResponse response,String type,String myid,String vipid){
		Jr jr = new Jr();
		jr.setMethod("canclefocusvip");
		if(myid==null||vipid==null||myid.equals(vipid)){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User me = userService.findById(myid);
			User who= userService.findById(vipid);
			if(me==null||who==null){
				jr.setCord(1);
				jr.setMsg("无此会员信息");
			}else {
				UserFocus userFocus = userFocusService.findByMeAndWhoAndRelation(me, who, 0);
				if(userFocus!=null)
					userFocusService.deleteById(userFocus.getId());
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
	 * 2.0 获取会员收藏列表---名片夹
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param vipid
	 * Lee.J.Eric
	 * 2014年9月26日 下午5:03:23
	 */
	@RequestMapping(value="myfocusviplist",method = RequestMethod.POST)
	public void focusVipList(HttpServletResponse response,String type,Integer page,Integer size,String vipid){
		Jr jr = new Jr();
		jr.setMethod("myfocusviplist");
		if(vipid==null||page==null||page<0||size==null||size<0){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			Page<User> list = userFocusService.getuserFocusList(vipid, -1,0, page, size);
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
			jr.setCord(1);
			jr.setMsg("获取失败");
		}
	}
	
	/**
	 * 2.0 获取会员交情列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param vipid
	 * @param flag
	 * Lee.J.Eric
	 * 2014年9月28日 上午10:18:12
	 */
	@RequestMapping(value="myuserrelationlist",method = RequestMethod.POST)
	public void myUserRelationList(HttpServletResponse response,String type,Integer page,Integer size,String vipid){
		Jr jr = new Jr();
		jr.setMethod("myuserrelationlist");
		if(vipid==null||page==null||page<0||size==null||size<0){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			Page<User> list = userFocusService.getuserFocusList(vipid, -1,1, page, size);
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
			jr.setCord(1);
			jr.setMsg("获取失败");
		}
	}
	
	/**
	 * 我关注的会员id列表
	 * @param response
	 * @param type
	 * @param vipid
	 * @author Lee.J.Eric
	 * @time 2014年11月11日 上午11:23:22
	 */
	@RequestMapping(value="getmyfocuslist")
	public void getMyFocusList(HttpServletResponse response,String type,String vipid){
		Jr jr = new Jr();
		jr.setMethod("getmyfocuslist");
		if(vipid==null){
			jr.setCord(-1);
			jr.setMsg("非法操作");
		}else {
			User me = userService.findById(vipid);
			//只查名片夹
			List<UserFocus> list = userFocusService.findByMeAndRelation(me,0);
			List<String> re = new ArrayList<String>();
			for (UserFocus userFocus : list) {
				re.add(userFocus.getWho().getId());
			}
			jr.setCord(0);
			jr.setData(re);
			jr.setTotal(re.size());
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
	 * 获取未读广播列表
	 * @param response
	 * @param userid
	 * @param page
	 * @param size
	 * @param time
	 * @param order
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年6月12日 下午12:35:30
	 */
	@RequestMapping(value="getbroadcast")
	public void getBroadcast(HttpServletResponse response,String userid,Integer page,Integer size,String time,String order,String type){
		Jr jr = new Jr();
		jr.setMethod("getbroadcast");
		if(userid==null||userid.equals("")||size==null||size<1){
			jr.setCord(-1);
		}else{
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				User user = userService.findById(userid);
				Page<Broadcast> list = broadcastService.findByUserAndTime(user, page, size, format.parse(time), order);
				List<Broadcast> broadcasts = list.getContent();
				List<Sysnetwork> re = new ArrayList<Sysnetwork>();
				for (Broadcast broadcast : broadcasts) {
					re.add(broadcast.getSysnetwork());
				}
				jr.setPagenum(page+1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setData(re);
				jr.setTotal(list.getTotalElements());
				jr.setCord(0);
				jr.setMsg("获取成功");
			} catch (Exception e) {
				// TODO: handle exception
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
	 * 阅读广播
	 * @param response
	 * @param id
	 * @param userid
	 * Lee.J.Eric
	 * 2014 2014年6月12日 下午2:37:22
	 */
	@RequestMapping(value="readbroadcast")
	public void readBroadcast(HttpServletResponse response,String id,String userid){
		Jr jr = new Jr();
		jr.setMethod("readbroadcast");
		if(id==null){
			jr.setCord(-1);
			jr.setMsg("非法传参");
		}else {
			Sysnetwork sysnetwork = sysnetworkService.findById(id);
			if(sysnetwork==null){
				jr.setCord(1);//找不到此广播
				jr.setMsg("此广播不存在");
			}else {
				if(userid!=null){
					User user = userService.findById(userid);
					Broadcast broadcast = broadcastService.findByUserAndSysnetwork(user, sysnetwork);
					if(broadcast!=null){
						broadcast.setIsread(1);
						broadcastService.broadcastSaveOrUpdate(broadcast);
					}
				}
				jr.setData(sysnetwork);
				jr.setCord(0);
				jr.setMsg("阅读广播成功");
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
	 * 获取我的广播列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param order
	 * @param userid
	 * @param read
	 */
	@RequestMapping(value="getmybroadcastlist")
	public void getMyBroadcastList(HttpServletResponse response,String type,Integer page,Integer size,Integer order,String userid,Integer read){
		Jr jr = new Jr();
		jr.setMethod("getmybroadcastlist");
		if(page==null||page<0||size==null||size<0||order==null||userid==null||userid.isEmpty()){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			User user = userService.findById(userid);
			broadcastService.getMyBroadcastList(page, size, order, user, read);
			List<Sysnetwork> list = broadcastService.getMyBroadcastList(page, size, order, user, read);
			Integer total = broadcastService.countMyBroadcastList(user, read).intValue();
			Integer totalPage = total%size==0?total/size:total/size+1;
			jr.setCord(0);
			jr.setData(list);
			jr.setData2(broadcastService.countMyBroadcastList(user, 0).intValue());//未读总数
			jr.setTotal(total);
			jr.setPagenum(page+1);
			jr.setPagecount(totalPage);
			if(list!=null&&!list.isEmpty()){
				jr.setPagesum(list.size());
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
	 * 意见反馈
	 * @param response
	 * @param feedback
	 * @param type
	 * Lee.J.Eric
	 * 2014年6月19日 下午4:04:13
	 */
	@RequestMapping(value="feedback")
	public void feedback(HttpServletResponse response,Feedback feedback,String type){
		Jr jr = new Jr();
		jr.setMethod("feedback");
		if(feedback==null){
			jr.setCord(-1);
			jr.setMsg("参数非法");
		}else {
			User user = userService.findById(feedback.getUser().getId());
			feedback.setUser(user);
			feedback = feedbackService.feedbackSaveOrUpdate(feedback);
			if(feedback==null){
				jr.setCord(1);
				jr.setMsg("反馈失败");
			}else {
				jr.setCord(0);
				jr.setMsg("反馈成功");
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
	 * 从身份证计算出生日期并更新取出生日期(用于数据更新)
	 * @param response
	 * Lee.J.Eric
	 * 2014年6月27日 上午10:48:24
	 */
	@RequestMapping(value = "refreshbirthdayfromidnum")
	public void refreshBirthdayFromIDNum(HttpServletResponse response,Integer page,Integer size){
		Jr jr = new Jr();
		jr.setMethod("refreshbirthdayfromidnum");
		if(page==null||page<0||size==null||size<0){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Page<User> users = userService.findUserList(page, size);
			List<User> list = users.getContent();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			for (User user : list) {
				String idnum = user.getIcnum();//身份证号
				if(idnum!=null&&(idnum.length()==18||idnum.length()==15)){
					try {
						String birth = idnum.substring(6, 14);
						Date birthday = format.parse(birth);
						if(birthday.before(new Date())){
							user.setBirthday(birthday);
							userService.userSaveOrUpdate(user);
						}
					} catch (Exception e) {
						// TODO: handle exception
						continue;
					}
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
	 * 用户签到/签出
	 * @param response
	 * @param userid 用户id
	 * @param signtype 签到in/签出out
	 * Lee.J.Eric
	 * 2014年6月30日 下午3:38:13
	 * update 2015-8-10 
	 */
	@RequestMapping(value = "usersign")
	public void userSign(HttpServletResponse response,String userid,String signtype,String type,String gps){
		Jr jr = new Jr();
		jr.setMethod("usersign");
		if(userid==null||userid.isEmpty()||signtype==null||signtype.isEmpty()){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Boolean result = signLogService.userSignLog(userid, signtype.toLowerCase(),gps);
			if(result){
				jr.setCord(0);
				jr.setMsg("签到/签出成功");
				signLogService.signNumByMoth(userid);
			}else {
				jr.setCord(1);
				jr.setMsg("签到/签出失败");
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
	 * excel导入用户
	 * @param response
	 * @param datafile datafile 上传的excel文件(07版格式.xlsx)
	 * Lee.J.Eric
	 * 2014年9月19日 下午4:22:46
	 */
	@RequestMapping(value = "importuser")
	public void importUser(HttpServletResponse response,MultipartFile datafile){
		Jr jr = new Jr();
		jr.setMethod("userimport");
		if(datafile==null){
			jr.setCord(-1);
			jr.setMsg("文件为空");
		}else {
			String base = R.Common.IMG_DIR + "excel/";
			File basedir = new File(base);
			if(!basedir.exists())
				basedir.mkdirs();
			String fileName = System.currentTimeMillis()+ ".xlsx";
			FileUtil fileUtil = new FileUtil();
			File desFile = new File(basedir, fileName);
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				fileUtil.copyFile(datafile.getInputStream(), desFile);//存放到服务器目录下
				desFile = preDeal(desFile);
				desFile = dataInput(desFile);
				response.setContentType("Application/msexcel;charset=GBK");
				response.setHeader("Content-disposition", "attachment; filename="+"会员数据导入反馈表_"+format.format(new Date())+"");
				OutputStream out = new BufferedOutputStream(response.getOutputStream());
				InputStream is = new BufferedInputStream(new FileInputStream(desFile));
				byte[] buffer = new byte[is.available()];
				is.read(buffer);
				is.close();
				out.write(buffer);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据excel表生成相应的用户
	 * @param file
	 * @return
	 * Lee.J.Eric
	 * 2014年7月30日 下午3:46:07
	 */
	private File dataInput(File file){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
			int lastrow = sheet.getLastRowNum();//总行数
			
			XSSFRow headRow = sheet.getRow(0);// 取得表头行,第一行，若有改动请作相应修改
			int headlength = headRow.getPhysicalNumberOfCells();
			
			int unumindex = 0;
			int nameindex = 0;
			int beginindex = 0;
			int idindex = 0;
			int birthindex = 0;
			int telindex = 0;
			int emailindex = 0;
			Cell hCell = headRow.createCell(headlength);
			hCell.setCellValue("备注");
			
			//根据表头获取数据列索引
			for (int i = 0; i < headlength; i++) {
				Cell cell = headRow.getCell(i);
				if (cell != null) {
					String value = cell.getStringCellValue();
					if ("会员卡号".equals(value)) {
						unumindex = i;
						continue;
					} else if ("姓名".equals(value)) {
						nameindex = i;
						continue;
					}  else if ("签约日期".equals(value)) {
						beginindex = i;
						continue;
					}else if ("身份证号".equals(value)) {
						idindex = i;
						continue;
					}else if ("生日".equals(value)) {
						birthindex = i;
						continue;
					}else if ("电话".equals(value)) {
						telindex = i;
						continue;
					}else if ("电邮".equals(value)) {
						emailindex = i;
						continue;
					}
				} 
			}
			
			Calendar calendar = Calendar.getInstance();
			XSSFRow row = null;
			Cell cell = null;
			for (int i = 1; i <= lastrow; i++) {
				try {
					row = sheet.getRow(i);
					User user = userService.findByTelnum(row.getCell(telindex).getStringCellValue());//手机号唯一确定用户
					if(user==null)
						user = new User();
					cell = row.getCell(unumindex);
					user.setUnum(cell.getStringCellValue());
					cell = row.getCell(nameindex);
					String nick = cell.getStringCellValue();
					while (userService.checkForNickname(nick)&&!userService.checkForUnum(user.getUnum())) {
						nick += RandomUtil.getRandomeStringForLength(1);
					}
					user.setRealname(cell.getStringCellValue());
					user.setNickname(nick);
					cell = row.getCell(beginindex);
					Date bDate = cell.getDateCellValue();
					calendar.setTime(bDate);
					calendar.add(Calendar.YEAR, 1);
					user.setVipdate(bDate);
					user.setVipenddate(calendar.getTime());
					cell = row.getCell(idindex);
					if(cell!=null){
						user.setIcnum(cell.getStringCellValue());
						cell = row.getCell(birthindex);
						user.setBirthday(format.parse(cell.getStringCellValue()));
						String sex = cell.getStringCellValue().substring(cell.getStringCellValue().length()-2, cell.getStringCellValue().length()-1);//身份证倒数第二位是性别识别码，男单女双
						Integer s = Integer.parseInt(sex);
						if(s%2==0){//女
							user.setSex(1);
						}else {//男
							user.setSex(0);
						}
					}
					cell = row.getCell(telindex);
					user.setTelnum(cell.getStringCellValue());
					user.setPassword(MD5Util.encrypt(cell.getStringCellValue()));
					cell = row.getCell(emailindex);
					user.setEmail(cell.getStringCellValue());
					user.setRoot(2);//会员权限
					userService.userSaveOrUpdate(user);
					cell = row.createCell(headlength);
					cell.setCellValue("导入成功");
				} catch (Exception e) {
					// TODO: handle exception
					log.error("importuser failed---"+e);
					e.printStackTrace();
					row = sheet.getRow(i);
					cell = row.createCell(headlength);
					cell.setCellValue("此用户导入失败");
					continue;
				}
			}
			FileOutputStream os = new FileOutputStream(file);
			xssfWorkbook.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * excel预处理
	 * @param targetFile
	 * @return
	 * Lee.J.Eric
	 * 2014年9月19日 下午5:13:55
	 */
	private File preDeal(File targetFile) {
		try {
			FileInputStream fis = new FileInputStream(targetFile);
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
			int lastrow = sheet.getLastRowNum();

			XSSFRow headRow = sheet.getRow(0);// 取得表头行,第一行，若有改动请作相应修改
			int birthindex = 0;
			int idindex = 0;
			int headlength = headRow.getPhysicalNumberOfCells();
			for (int i = 0; i < headlength; i++) {
				Cell cell = headRow.getCell(i);
				if (cell != null) {
					String value = cell.getStringCellValue();
					if ("生日".equals(value)) {
						birthindex = i;
						continue;
					} else if ("身份证号".equals(value)) {
						idindex = i;
						continue;
					}
				} 
			}

			for (int i = 1; i <= lastrow; i++) {//从身份证号获取生日
				XSSFRow row = sheet.getRow(i);
				Cell cell = row.getCell(idindex);// 获取身份证号单元格
				if (cell != null) {
					if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						String birthString = cell.getStringCellValue().substring(6, 14);
						String birth = birthString.substring(0, 4) + "/"+birthString.substring(4,6)+"/"+birthString.substring(6,8);
						Cell c = row.createCell(birthindex);
						c.setCellType(XSSFCell.CELL_TYPE_STRING);
						c.setCellValue(birth);
					}
				}
			}
			FileOutputStream os = new FileOutputStream(targetFile);
			xssfWorkbook.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return targetFile;
	}
	
	/**
	 * 更新用户头像 2.0
	 * @param response
	 * @param img
	 * @param userid
	 * @param type
	 * @author Lee.J.Eric
	 * @time 2014年11月7日 上午10:31:52
	 */
	@RequestMapping(value = "updateusericon")
	public void updateUserIcon(HttpServletResponse response,MultipartFile img,String userid,String type){
		Jr jr = new Jr();
		jr.setMethod("updateusericon");
		if(img==null||userid==null||type==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User user =userService.findById(userid);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("此用户不存在");
			}else {
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
					imageUtil.compressPic(targetdir, targetdir, fileName, target1, 640, 640, null, true);
					imageUtil.compressPic(targetdir, targetdir, fileName, target2, 300, 420, null, true);
					imageUtil.compressPic(targetdir, targetdir, fileName, target3, 160, 160, null, true);
					jr.setData(fileName);
					jr.setCord(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					jr.setCord(1);
				}
				user.setMinimg(fileName);
				userService.userSaveOrUpdate(user);
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
	 * shout一声
	 * @param response
	 * @param userid 喊话人
	 * @param msg
	 * @param type
	 * @author Lee.J.Eric
	 * @time 2014年11月28日 下午4:08:28
	 */
	@RequestMapping(value = "shout")
	public void shout(HttpServletResponse response,String userid,CurrentMsg msg,String type){
		Jr jr = new Jr();
		jr.setMethod("shout");
		if(userid==null||msg.getContents().equals("")){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User user = userService.findById(userid);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("用户不存在");
			}else {
				try {
					String contents = msg.getContents();
					StringBuffer sb = new StringBuffer();
					sb.append(user.getNickname()).append(" ");
					sb.append("喊了一声");
					sb.append("\"").append(msg.getContents()).append("\"");
					msg.setContents(sb.toString());
					currentMsgService.saveOrUpdate(msg);
					
					//回复问题时，给提问人push
					JSONObject msg_extras = new JSONObject();
					msg_extras.put("type", "now");
					msg_extras.put("parameter", msg.getId());
					msg_extras.put("icon", "loudspeaker");
					msg_extras.put("action", "answer");
					pushService.push(Platform.android_ios(), new String[]{"yiqi"}, new String[]{"version4","userstart0","ifspace1"}, contents,user.getNickname()+"喊了一声", "text", msg_extras);
					
					jr.setCord(0);
					jr.setMsg("喊话成功");
				} catch (Exception e) {
					// TODO: handle exception
					jr.setCord(1);
					jr.setMsg("喊话失败");
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
	 * now消息列表
	 * @param response
	 * @param type
	 * @author Lee.J.Eric
	 * @time 2014年11月28日 下午4:41:25
	 */
	@RequestMapping(value = "nowmsglist")
	public void nowMsgList(HttpServletResponse response,String type){
		Jr jr = new Jr();
		jr.setMethod("nowmsglist");
		List<CurrentMsg> list = currentMsgService.findAll();
		jr.setData(list);
		jr.setMsg("获取成功");
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置用户忙碌状态
	 * @param response
	 * @param type
	 * @param userid 用户id
	 * @param status 忙碌状态 0欢迎交流，1忙碌勿扰
	 * @author Lee.J.Eric
	 * @time 2014年12月4日 下午3:04:18
	 */
	@RequestMapping(value = "userstatus")
	public void userStatus(HttpServletResponse response,String type,String userid,Integer status){
		Jr jr = new Jr();
		jr.setMethod("userstatus");
		if(type==null||userid==null||status==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User user = userService.findById(userid);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("用户不存在");
			}else {
				try {
					user.setUserstart(status);
					userService.userSaveOrUpdate(user);
					jr.setCord(0);
					jr.setMsg("设置成功");
				} catch (Exception e) {
					// TODO: handle exception
					jr.setCord(1);
					jr.setMsg("设置失败");
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
	 * 统计在线用户总数 2.0
	 * @param response
	 * @param type
	 * @author Lee.J.Eric
	 * @time 2014年12月8日 下午5:53:29
	 */
	@RequestMapping(value = "countonlineuser")
	public void countOnlineUser(HttpServletResponse response,String type){
		Jr jr = new Jr();
		jr.setMethod("countonlineuser");
		if(type==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			long count = userService.countOnlineUser();
			jr.setData(count);
			jr.setMsg("操作成功");
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
	 * 根据会员电话查询
	 * @param response
	 * @param type
	 * @param phone
	 * @author Lee.J.Eric
	 * @time 2015年1月8日 下午12:11:43
	 */
	@RequestMapping(value = "checkvipphone")
	public void checkVipPhone(HttpServletResponse response,String type,String phone){
		Jr jr = new Jr();
		jr.setMethod("checkvipphone");
		if(type==null||phone==null){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User user = userService.findByTelnum(phone);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("此用户不存在");
			}else if(user.getRoot().equals(UserRoot.REGISTER.getCode())) {
				jr.setCord(2);
				jr.setMsg("此用户不是会员");
			}else {
				Set<String> set = new HashSet<String>();
				set.add("id");
				jr.setCord(0);
				jr.setData(BeanUtil.getFieldValueMap(user, set));
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
	 * shout一声内容
	 * @param response
	 * @author Lee.J.Eric
	 * @time 2015年1月12日 下午12:07:16
	 */
	@RequestMapping(value = "shoutcontent")
	public void shoutContent(HttpServletResponse response){
		Jr jr = new Jr();
		jr.setMethod("shoutcontent");
		Page<ShoutContent> list = shoutContentService.findPage(0, 10);
		jr.setPagenum(1);
		jr.setPagecount(list.getTotalPages());
		jr.setPagesum(list.getNumberOfElements());
		jr.setData(list.getContent());
		jr.setTotal(list.getTotalElements());
		jr.setCord(0);
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 分享咖啡摇一摇 随机一个用户
	 * @param response
	 */
	@RequestMapping(value = "randomuser")
	public void randomuser(HttpServletResponse response,String userid){
		Jr jr = new Jr();
		jr.setMethod("randomuser");
		User u=userService.randomuser(userid);
		if(u!=null){
			jr.setData(u);
			jr.setCord(0);
		}else{
			jr.setCord(1);
			jr.setMsg("在摇一次");
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
