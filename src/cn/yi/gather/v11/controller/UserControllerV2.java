package cn.yi.gather.v11.controller;

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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.yi.gather.v11.entity.Broadcast;
import cn.yi.gather.v11.entity.Feedback;
import cn.yi.gather.v11.entity.Resetpassword;
import cn.yi.gather.v11.entity.Sysnetwork;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.entity.UserFocus;
import cn.yi.gather.v11.service.IBroadcastServiceV2;
import cn.yi.gather.v11.service.IClasssortServiceV2;
import cn.yi.gather.v11.service.IEmailServiceV2;
import cn.yi.gather.v11.service.IFeedbackServiceV2;
import cn.yi.gather.v11.service.ILabelsServiceV2;
import cn.yi.gather.v11.service.IResetpasswordServiceV2;
import cn.yi.gather.v11.service.ISignLogServiceV2;
import cn.yi.gather.v11.service.ISysnetworkServiceV2;
import cn.yi.gather.v11.service.IUserFocusServiceV2;
import cn.yi.gather.v11.service.IUserServiceV2;

import com.common.Jr;
import com.common.R;
import com.tools.utils.FileUtil;
import com.tools.utils.JSONUtil;
import com.tools.utils.MD5Util;
import com.tools.utils.RandomUtil;

@Controller("userControllerV2")
@RequestMapping(value = "v2/user")
public class UserControllerV2 {
	private static Logger log = Logger.getLogger(UserControllerV2.class);
	
	@Resource(name = "userServiceV2")
	private IUserServiceV2 userServiceV2;
	
	@Resource(name = "classsortServiceV2")
	private IClasssortServiceV2 classsortServiceV2;
	
	@Resource(name = "labelsServiceV2")
	private ILabelsServiceV2 labelsServiceV2;
	
	@Resource(name = "resetpasswordServiceV2")
	private IResetpasswordServiceV2 resetpasswordServiceV2;
	
	@Resource(name = "emailServiceV2")
	private IEmailServiceV2 emailServiceV2;
	
	@Resource(name = "userFocusServiceV2")
	private IUserFocusServiceV2 userFocusServiceV2;
	
	@Resource(name = "broadcastServiceV2")
	private IBroadcastServiceV2 broadcastServiceV2;
	
	@Resource(name = "sysnetworkServiceV2")
	private ISysnetworkServiceV2 sysnetworkServiceV2;
	
	@Resource(name = "feedbackServiceV2")
	private IFeedbackServiceV2 feedbackServiceV2;
	
	@Resource(name = "signLogServiceV2")
	private ISignLogServiceV2 signLogServiceV2;
	
	/**
	 * 用户登陆
	 * @param request
	 * @param response
	 * @param type
	 * @param username
	 * @param password
	 * Lee.J.Eric
	 * 2014 2014年5月28日 下午6:12:13
	 */
	@RequestMapping(value = "login")
	public void login(HttpServletRequest request,HttpServletResponse response, Integer type,String username,String password) {
		Jr jr = new Jr();
		jr.setMethod("login");
		if (username==null||username.isEmpty()||password==null||password.isEmpty()) {
			jr.setCord(-1);
			jr.setMsg("参数错误");
		} else {
			User user = userServiceV2.findByusernameAndPWD(password, username);
			if(user!=null){
				jr.setData(user);
				jr.setCord(0);
				jr.setMsg("登陆成功");
				request.getSession().setAttribute(R.User.SESSION_USER, user);
				log.info("用户登陆成功---"+user.getNickname());
			}else {
				jr.setCord(1);
				jr.setMsg("帐号或密码错误");
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
	public void register(HttpServletResponse response,Integer type,User user,String labels,String birth){
		Jr jr = new Jr();
		jr.setMethod("register");
		if (user==null) {
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else if (user.getEmail() == null || user.getEmail().trim().isEmpty() || user.getNickname() == null || user.getNickname().trim().isEmpty()) {//去掉手机非空验证
			jr.setCord(1);
			jr.setMsg("昵称/邮箱不能为空");
		}else if(userServiceV2.checkForNickname(user.getNickname())){
			jr.setCord(2);
			jr.setMsg("此昵称已被占用");
		}else if (!user.getEmail().isEmpty()&&userServiceV2.checkForEmail(user.getEmail())) {
			jr.setCord(3);
			jr.setMsg("此邮箱已被占用");
		}/*else if (user.getTelnum() != null&&!user.getTelnum().isEmpty()&&userServiceV2.checkForTelephone(user.getTelnum())) {
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
				user.setLabel(labelsServiceV2.getLabelsByids(labels));
				//职业信息
				if(user.getIndustry()!=null)
					user.setIndustry(classsortServiceV2.getClasssortsByID(user.getIndustry().getId()));
				if(user.getJob()!=null)
					user.setJob(classsortServiceV2.getClasssortsByID(user.getJob().getId()));
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
				user = userServiceV2.userSaveOrUpdate(user);
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
	public void logout(HttpServletRequest request,HttpServletResponse response,String userid,Integer type){
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
	
	/**
	 * 获取用户信息
	 * @param response
	 * @param userid
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月30日 上午11:37:14
	 */
	@RequestMapping(value="getuser")
	public void getUser(HttpServletResponse response,String userid,Integer type){
		Jr jr = new Jr();
		jr.setMethod("getuser");
		if (userid == null || userid.equals("")) {
			jr.setCord(-1);
		}else {
			User user = userServiceV2.findById(userid);
			if(user!=null){
				jr.setData(user);
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
	 * 检测昵称是否已存在
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
		}else if (userServiceV2.checkForNickname(nickname.trim())){
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
	 * 检测邮箱是否已被使用
	 * @param request
	 * @param response
	 * @param email
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月30日 上午11:52:05
	 */
	@RequestMapping(value="checkemail")
	public void checkEmail(HttpServletResponse response,String email,Integer type){
		Jr jr = new Jr();
		jr.setMethod("checkemail");
		if(email==null||email.isEmpty()){
			jr.setCord(1);
			jr.setMsg("邮箱为空");
		}else if(userServiceV2.checkForEmail(email)){
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
	 * 检测手机是否已被使用
	 * @param request
	 * @param response
	 * @param tel
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月30日 上午11:54:58
	 */
	@RequestMapping(value="checktel")
	public void checkTel(HttpServletRequest request,HttpServletResponse response,String tel,Integer type){
		Jr jr = new Jr();
		jr.setMethod("checktel");
		if(tel==null||tel.isEmpty()){
			jr.setCord(1);
			jr.setMsg("手机号为空");
		}else if (userServiceV2.checkForTelephone(tel)) {
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
	 * 修改密码
	 * @param response
	 * @param user
	 * @param newpwd
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月30日 上午11:58:37
	 */
	@RequestMapping(value="updatepwd")
	public void updatePassword(HttpServletResponse response,User user,String oldpwd,String newpwd,Integer type){
		Jr jr = new Jr();
		jr.setMethod("updatepwd");
		if(user==null||newpwd ==null||newpwd.isEmpty()){
			jr.setCord(-1);
			jr.setMsg("参数为空");
		}else{
			User user2 = userServiceV2.findByIdAndPassword(user.getId(), oldpwd);
			if(user2==null){
				jr.setCord(1);//帐号错误或不存在此用户
				jr.setMsg("帐号或旧密码错误");
			}else {
				user2.setPassword(newpwd);
				user2 = userServiceV2.userSaveOrUpdate(user2);
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
	 * 检查会员编号是否已被使用
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
		}else if (userServiceV2.checkForUnum(unum)){
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
			Page<User> list = userServiceV2.getIndexUser(page, size,1);
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
	 * 更新用户
	 * @param response
	 * @param type
	 * @param userid
	 * @param param
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午4:22:30
	 */
	@RequestMapping(value="updateuser")
	public void updateUser(HttpServletResponse response, Integer type,String userid,@RequestParam Map<String, Object> param) {
		Jr jr = new Jr();
		jr.setMethod("updateuser");
		Set<String> keys = param.keySet();
		keys.remove("userid");
		keys.remove("type");
		if(userid==null||userid.isEmpty()||param.isEmpty()){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else { 
			User user = userServiceV2.findById(userid);
			if(user==null){
				jr.setCord(1);//找不到用户
				jr.setMsg("此用户不存在");
			}else {
				Class<?> userclass = user.getClass();
				try {
					for (String key : keys) {
						if (key.equals("id") || key.equals("root")|| key.equals("isdel")
								|| key.equals("vipenddate1")|| key.equals("vipdate1")|| key.equals("createdate1")||key.equalsIgnoreCase("realname")) {// 不作修改字段
							continue;
						}else if(key.equals("industry")){
							if(param.get(key)!=null&&!param.get(key).toString().equals(""))
								user.setIndustry(classsortServiceV2.getClasssortsByID(new Long(param.get(key).toString())));
						}else if (key.equals("job")) {
							if(param.get(key)!=null&&!param.get(key).toString().equals(""))
								user.setJob(classsortServiceV2.getClasssortsByID(new Long(param.get(key).toString())));
						}else if (key.equals("label")) {
							if(param.get(key)!=null&&!param.get(key).toString().equals(""))
								user.setLabel(labelsServiceV2.getLabelsByids(param.get(key).toString()));
						}else if(key.equals("skilledlabel")){
							if(param.get(key)!=null&&!param.get(key).toString().equals(""))
								user.setSkilledlabel(labelsServiceV2.getLabelsByids(param.get(key).toString()));
						}else if (key.equals("interestlabel")) {
							if(param.get(key)!=null&&!param.get(key).toString().equals(""))
								user.setInterestlabel(labelsServiceV2.getLabelsByids(param.get(key).toString()));
						}else if (key.equals("nickname")) {
							if(user.getNickname()!=null&&!user.getNickname().equals(param.get(key).toString())&&!param.get(key).toString().isEmpty()&&userServiceV2.checkForNickname(param.get(key).toString())){
								jr.setCord(2);
								jr.setMsg("此昵称已存在");
								throw new Exception("nickname is exist");
							}else {
								user.setNickname(param.get(key).toString());
							}
						}else if (key.equals("unum")) {
							if(user.getUnum()!=null&&!user.getUnum().equals(param.get(key).toString())&&!param.get(key).toString().isEmpty()&&userServiceV2.checkForUnum(param.get(key).toString())){
								jr.setCord(3);
								jr.setMsg("此会员号已被占用");
								throw new Exception("unum is exist");
							}else {
								user.setUnum(param.get(key).toString());
							}
						}else if (key.equals("email")) {
							if(user.getEmail()!=null&&!user.getEmail().equals(param.get(key).toString())&&!param.get(key).toString().isEmpty()&&userServiceV2.checkForEmail(param.get(key).toString())){
								jr.setCord(4);
								jr.setMsg("此邮箱已被占用");
								throw new Exception("email is exist");
							}else {
								user.setEmail(param.get(key).toString());
							}
						}else if (key.equals("telnum")) {
							if(user.getTelnum()!=null&&!user.getTelnum().equals(param.get(key).toString())&&!param.get(key).toString().isEmpty()&&userServiceV2.checkForTelephone(param.get(key).toString())){
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
					user = userServiceV2.userSaveOrUpdate(user);
					jr.setCord(0);
					jr.setData(user);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					jr.setCord(1);
					jr.setMsg("更新失败");
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
	 * 申请密码找回
	 * @param response
	 * @param email
	 * @param request
	 * @param type
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午5:04:26
	 */
	@RequestMapping(value="applyresetpwd")
	public void applyResetpwd(HttpServletResponse response,String email,HttpServletRequest request,Integer type){
		String basePath = R.Common.BASEPATH;
		String action = "v2/user/resetmypwd";
		Jr jr = new Jr();
		jr.setMsg("applyresetpwd");
		if(email==null||email.isEmpty()){
			jr.setCord(-1);//非法传参
			jr.setMsg("邮箱不能为空");
		}else {
			User user = userServiceV2.getByEmail(email);
			if(user==null){
				jr.setCord(1);
				jr.setMsg("用户未设置联系邮箱");
			}else {
				Resetpassword resetpassword = new Resetpassword();
				resetpassword.setUser(user.getId());
				resetpassword = resetpasswordServiceV2.createResetpassword(resetpassword);
				if(resetpassword==null||!emailServiceV2.applyResetPassword(email,resetpassword.getId(),basePath+action)){
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
		Resetpassword resetpassword = resetpasswordServiceV2.searchResetpassword(id);
		if(resetpassword==null||resetpassword.getFlag()!=0||resetpassword.getCreatedate().before(calendar.getTime())){
			return new ModelAndView("resetpwderror");//错误页
		}else {
			resetpassword.setFlag(1);
			resetpasswordServiceV2.updateResetpassword(resetpassword);
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
			User user =  userServiceV2.findById(userid);
			if(user==null){
				
			}else {
				user.setPassword(MD5Util.encrypt(newpwd));
				user = userServiceV2.userSaveOrUpdate(user);
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
	 * 会员列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param listtype 列表类型，-1全部，0会员展示，1空间会员
	 * @param keyword  搜索关键字
	 * Lee.J.Eric
	 * 2014 2014年5月31日 下午12:16:08
	 */
	@RequestMapping(value="viplist")
	public void vipList(HttpServletResponse response,Integer type,Integer page,Integer size,Integer listtype,String keyword){
		Jr jr = new Jr();
		jr.setMethod("viplist");
		if(listtype==null||page==null||page<0||size==null||size<0){//非法传值
			jr.setCord(-1);
			jr.setMsg("参数错误");
		}else {
			try {
				Page<User> list = userServiceV2.getVipList(page, size, -1, listtype, keyword);
				jr.setPagenum(page+1);
				jr.setPagecount(list.getTotalPages());
				jr.setPagesum(list.getNumberOfElements());
				jr.setData(list.getContent());
				jr.setTotal(list.getTotalElements());
				jr.setCord(0);
				jr.setMsg("获取成功");
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
	 * 会员加星(会员关注)
	 * @param response
	 * @param type
	 * @param myid
	 * @param vipid
	 * Lee.J.Eric
	 * 2014 2014年6月4日 下午3:54:45
	 */
	@RequestMapping(value="focusvip")
	public void focusVip(HttpServletResponse response,Integer type,String myid,String vipid){
		Jr jr = new Jr();
		jr.setMethod("focusvip");
		if(myid==null||vipid==null){
			jr.setCord(-1);
			jr.setMsg("用户id不能为空");
		}else if (myid.equals(vipid)) {
			jr.setCord(2);
			jr.setMsg("您不能关注您自己");
		}else {
			User me = userServiceV2.findById(myid);
			User who= userServiceV2.findById(vipid);
			if (me == null||who ==null) {
				jr.setCord(3);;
				jr.setMsg("会员不存在");
			}else {
				UserFocus userFocus = userFocusServiceV2.findByMeAndWho(me, who);
				if(userFocus==null){
					userFocus = new UserFocus();
					userFocus.setMe(me);
					userFocus.setWho(who);
					userFocus = userFocusServiceV2.userFocusSaveOrUpdate(userFocus);
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
	 * 取消会员加星
	 * @param response
	 * @param type
	 * @param myid
	 * @param vipid
	 * Lee.J.Eric
	 * 2014 2014年6月4日 下午4:07:18
	 */
	@RequestMapping(value="canclefocusvip")
	public void cancleFocusVip(HttpServletResponse response,Integer type,String myid,String vipid){
		Jr jr = new Jr();
		jr.setMethod("canclefocusvip");
		if(myid==null||vipid==null||myid.equals(vipid)){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User me = userServiceV2.findById(myid);
			User who= userServiceV2.findById(vipid);
			if(me==null||who==null){
				jr.setCord(1);
				jr.setMsg("无此会员信息");
			}else {
				UserFocus userFocus = userFocusServiceV2.findByMeAndWho(me, who);
				if(userFocus==null){
					jr.setCord(0);
					jr.setMsg("取消成功");
				}else {
					userFocusServiceV2.deleteById(userFocus.getId());
					jr.setCord(0);
					jr.setMsg("取消成功");
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
	 * 获取会员关注列表
	 * @param response
	 * @param type
	 * @param page
	 * @param size
	 * @param vipid
	 * @param flag 0我关注的，1我被关注的
	 * Lee.J.Eric
	 * 2014 2014年6月4日 下午5:05:12
	 */
	@RequestMapping(value="myfocusviplist")
	public void focusVipList(HttpServletResponse response,Integer type,Integer page,Integer size,String vipid,Integer flag){
		Jr jr = new Jr();
		jr.setMethod("myfocusviplist");
		if(vipid==null||page==null||page<0||size==null||size<0){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			User me = userServiceV2.findById(vipid);
			Page<UserFocus> list = userFocusServiceV2.getuserFocusList(me, -1, flag, page, size);
			List<UserFocus> userFocus = list.getContent();
			List<User> re = new ArrayList<User>();
			for (UserFocus userFocus2 : userFocus) {
				re.add(userFocus2.getWho());
			}
			jr.setPagenum(page+1);
			jr.setPagecount(list.getTotalPages());
			jr.setPagesum(list.getNumberOfElements());
			jr.setData(re);
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
	 * Lee.J.Eric
	 * 2014 2014年6月4日 下午5:38:04
	 */
	@RequestMapping(value="getmyfocuslist")
	public void getMyFocusList(HttpServletResponse response,Integer type,String vipid){
		Jr jr = new Jr();
		jr.setMethod("getmyfocuslist");
		if(vipid==null){
			jr.setCord(-1);
			jr.setMsg("非法操作");
		}else {
			User me = userServiceV2.findById(vipid);
			List<UserFocus> list = userFocusServiceV2.findByMe(me);
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
	public void getBroadcast(HttpServletResponse response,String userid,Integer page,Integer size,String time,String order,Integer type){
		Jr jr = new Jr();
		jr.setMethod("getbroadcast");
		if(userid==null||userid.equals("")||size==null||size<1){
			jr.setCord(-1);
		}else{
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				User user = userServiceV2.findById(userid);
				Page<Broadcast> list = broadcastServiceV2.findByUserAndTime(user, page, size, format.parse(time), order);
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
			Sysnetwork sysnetwork = sysnetworkServiceV2.findById(id);
			if(sysnetwork==null){
				jr.setCord(1);//找不到此广播
				jr.setMsg("此广播不存在");
			}else {
				if(userid!=null){
					User user = userServiceV2.findById(userid);
					Broadcast broadcast = broadcastServiceV2.findByUserAndSysnetwork(user, sysnetwork);
					if(broadcast!=null){
						broadcast.setIsread(1);
						broadcastServiceV2.broadcastSaveOrUpdate(broadcast);
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
	public void getMyBroadcastList(HttpServletResponse response,Integer type,Integer page,Integer size,Integer order,String userid,Integer read){
		Jr jr = new Jr();
		jr.setMethod("getmybroadcastlist");
		if(page==null||page<0||size==null||size<0||order==null||userid==null||userid.isEmpty()){
			jr.setCord(-1);//非法传参
			jr.setMsg("非法传参");
		}else {
			User user = userServiceV2.findById(userid);
			broadcastServiceV2.getMyBroadcastList(page, size, order, user, read);
			List<Sysnetwork> list = broadcastServiceV2.getMyBroadcastList(page, size, order, user, read);
			Integer total = broadcastServiceV2.countMyBroadcastList(user, read).intValue();
			Integer totalPage = total%size==0?total/size:total/size+1;
			jr.setCord(0);
			jr.setData(list);
			jr.setData2(broadcastServiceV2.countMyBroadcastList(user, 0).intValue());//未读总数
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
	public void feedback(HttpServletResponse response,Feedback feedback,Integer type){
		Jr jr = new Jr();
		jr.setMethod("feedback");
		if(feedback==null){
			jr.setCord(-1);
			jr.setMsg("参数非法");
		}else {
			User user = userServiceV2.findById(feedback.getUser().getId());
			feedback.setUser(user);
			feedback = feedbackServiceV2.feedbackSaveOrUpdate(feedback);
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
			Page<User> users = userServiceV2.findUserList(page, size);
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
							userServiceV2.userSaveOrUpdate(user);
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
	 */
	@RequestMapping(value = "usersign")
	public void userSign(HttpServletResponse response,String userid,String signtype,Integer type){
		Jr jr = new Jr();
		jr.setMethod("usersign");
		if(userid==null||userid.isEmpty()||signtype==null||signtype.isEmpty()){
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			Boolean result = signLogServiceV2.userSignLog(userid, signtype.toLowerCase());
			if(result){
				jr.setCord(0);
				jr.setMsg("签到/签出成功");
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
	 * @param datafile datafile 上传的excel文件(07版格式)
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
				fileUtil.copyFile(datafile.getInputStream(), desFile);//存放到服务器目录下
				desFile = preDeal(desFile);
				desFile = dataInput(desFile);
				response.setContentType("Application/msexcel;charset=GBK");
				response.setHeader("Content-disposition", "attachment; filename="+desFile.getName()+"");
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
					User user = userServiceV2.getByEmail(row.getCell(emailindex).getStringCellValue());
					if(user==null){
						user = new User();
						
					}
					cell = row.getCell(unumindex);
					user.setUnum(cell.getStringCellValue());
					cell = row.getCell(nameindex);
					String nick = cell.getStringCellValue();
					while (userServiceV2.checkForNickname(nick)&&!userServiceV2.checkForUnum(user.getUnum())) {
						nick += RandomUtil.getRandomeStringForLength(1);
					}
					user.setRealname(cell.getStringCellValue());
					user.setNickname(nick);
					cell = row.getCell(beginindex);
					Date bDate = cell.getDateCellValue();
					calendar.setTime(bDate);
					calendar.add(Calendar.YEAR, 1);
					user.setVipdate1(bDate);
					user.setVipenddate1(calendar.getTime());
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
					userServiceV2.userSaveOrUpdate(user);
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
	
}
