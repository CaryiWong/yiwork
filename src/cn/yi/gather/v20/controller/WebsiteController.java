package cn.yi.gather.v20.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v20.entity.Applyvip;
import cn.yi.gather.v20.entity.Joinapplication;
import cn.yi.gather.v20.entity.Order;
import cn.yi.gather.v20.entity.Spaceshow;
import cn.yi.gather.v20.entity.User;
import cn.yi.gather.v20.entity.User.UserRoot;
import cn.yi.gather.v20.entity.WorkSpaceInfo;
import cn.yi.gather.v20.service.IAlipayService;
import cn.yi.gather.v20.service.IApplyvipService;
import cn.yi.gather.v20.service.IJoinapplicationService;
import cn.yi.gather.v20.service.IOrderService;
import cn.yi.gather.v20.service.ISpaceshowService;
import cn.yi.gather.v20.service.IUserService;
import cn.yi.gather.v20.service.IWorkSpaceInfoService;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

@Controller("websiteControllerV20")
@RequestMapping(value="v20/website")
public class WebsiteController {

	@Resource(name = "applyvipServiceV20")
	private IApplyvipService applyvipService;
	
	@Resource(name = "userServiceV20")
	private IUserService userService;
	
	@Resource(name = "joinapplicationServiceV20")
	private IJoinapplicationService joinapplicationService;
	
	@Resource(name = "spaceshowServiceV20")
	private ISpaceshowService spaceshowService;
	
	@Resource(name = "orderServiceV20")
	private IOrderService orderService;
	
	@Resource(name = "alipayServiceV20")
	private IAlipayService alipayService;
	
	@Resource(name = "workSpaceInfoService")
	private IWorkSpaceInfoService workspaceService;
	/**
	 * 新增会员申请记录
	 * @param response
	 * @param applyvip
	 * @param type
	 * Lee.J.Eric
	 * 2014年6月19日 下午3:26:25
	 */
	@RequestMapping(value="addapplyvip")
	public void addApplyVIP(HttpServletResponse response,Applyvip applyvip,Integer type){
		Jr jr = new Jr();
		jr.setMethod("addapplyvip");
		if(applyvip==null){
			jr.setCord(-1);
		}else {
			applyvip = applyvipService.applyvipSaveOrUpdate(applyvip);
			if(applyvip==null){
				jr.setCord(1);
				jr.setMsg("保存失败");
			}else {
				User user = new User(applyvip);
				//注册会员默认是一起的08.26 kcm修改
				WorkSpaceInfo spaceinfo= workspaceService.find("yq88888888888888");
				user.setSpaceInfo(spaceinfo);
				
				if(!userService.checkForEmail(user.getEmail())&&!userService.checkForTelephone(user.getTelnum())){
					userService.userSaveOrUpdate(user);
					jr.setData(applyvip);
					jr.setCord(0);
				}else {
					User userdb = userService.findByTelnum(user.getTelnum());
					if(userdb==null)
						userdb = userService.getByEmail(user.getEmail());
					if(userdb!=null&&userdb.getRoot()<UserRoot.REGISTER.getCode()&&userService.checkForEmail(user.getEmail())){//已是会员
						jr.setCord(1);
						jr.setMsg("此邮箱已被会员注册");
					}else if(userdb!=null&&userdb.getRoot()<UserRoot.REGISTER.getCode()&&userService.checkForTelephone(user.getTelnum())) {
						jr.setCord(1);
						jr.setMsg("此手机号已被会员注册");
					}else if(userdb!=null&&userdb.getRoot()<UserRoot.REGISTER.getCode()&&userService.checkForIdnum(user.getIcnum())){//证件号
						jr.setCord(1);
						jr.setMsg("证件号已存在");
					}else{
						if(userdb!=null&&userdb.getRoot()==UserRoot.REGISTER.getCode())//注册权限的用户
							user.setId(userdb.getId());
						userService.userSaveOrUpdate(user);
						jr.setData(applyvip);
						jr.setCord(0);
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
	 * 成为会员
	 * @param response
	 * @param type
	 * @param applyid
	 * @author Lee.J.Eric
	 * @time 2015年1月9日 下午4:43:05
	 */
	@RequestMapping(value = "becomevip")
	public void becomeVip(HttpServletResponse response,String type,String applyid){
		Jr jr = new Jr();
		jr.setMethod("becomevip");
		if (type==null||applyid==null) {
			jr.setCord(-1);
			jr.setMsg("非法传值");
		}else {
			User user = userService.findById(applyid);//申请id即用户id
			if(user==null){
				jr.setCord(1);
				jr.setMsg("请先填写信息申请会员");
			}else {
				try {
					Order order = orderService.becomeVip(user);
					jr = alipayService.applyAlipay(type, jr, order);
//					if(type.equals("web")){
//					}else {//留给移动端，待添加
//						
//					}
				} catch (Exception e) {
					// TODO: handle exception
					jr.setCord(1);
					jr.setMsg("下单失败");
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
	 * 查看单个会员申请信息
	 * @param response
	 * @param id
	 * @param type
	 * Lee.J.Eric
	 * 2014年6月19日 下午3:26:49
	 */
	@RequestMapping(value="getapplyvip")
	public void getApplyVIP(HttpServletResponse response,String id,Integer type){
		Jr jr = new Jr();
		jr.setMethod("getapplyvip");
		if(id==null||id.isEmpty()){
			jr.setCord(-1);
		}else {
			Applyvip applyvip = applyvipService.findById(id);
			if(applyvip==null){
				jr.setCord(1);
				jr.setMsg("此申请不存在");
			}else {
				jr.setData(applyvip);
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
	 * 新增入驻申请
	 * @param response
	 * @param joinapplication
	 * @param type
	 * Lee.J.Eric
	 * 2014年6月19日 下午3:43:17
	 */
	@RequestMapping(value="addjoinapplication")
	public void addJoinApplication(HttpServletResponse response,Joinapplication joinapplication,Integer type){
		Jr jr = new Jr();
		jr.setMethod("addjoinapplication");
		if(joinapplication==null){
			jr.setCord(-1);
		}else {
			joinapplication = joinapplicationService.joinapplicationSaveOrUpdate(joinapplication);
			if(joinapplication==null){
				jr.setCord(1);
				jr.setMsg("保存失败");
			}else {
				jr.setData(joinapplication);
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
	 * 获取入驻申请信息
	 * @param response
	 * @param type
	 * @param id
	 * Lee.J.Eric
	 * 2014年6月19日 下午3:48:41
	 */
	@RequestMapping(value="getjoinapplication")
	public void getJoinApplication(HttpServletResponse response,Integer type,String id){
		Jr jr = new Jr();
		jr.setMethod("getjoinapplication");
		if(id==null||id.isEmpty()){
			jr.setCord(-1);
		}else {
			Joinapplication joinapplication = joinapplicationService.findById(id);
			if(joinapplication==null){
				jr.setCord(1);
				jr.setMsg("申请不存在");
			}else {
				jr.setData(joinapplication);
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
	 * 获取空间展示列表
	 * @param response
	 * @param type
	 * Lee.J.Eric
	 * 2014年7月8日 下午3:46:47
	 */
	@RequestMapping(value="getspaceshowlist")
	public void getSpaceshowList(HttpServletResponse response,Integer type){
		Jr jr = new Jr();
		jr.setMethod("getspaceshowlist");
		List<Spaceshow> list = spaceshowService.getAllSpaceshowList();
		Integer total = list.size();
		jr.setPagenum(1);
		jr.setPagecount(1);
		jr.setTotal(total);
		if(list!=null&&!list.isEmpty()){
			jr.setPagesum(list.size());
		}
		Set<String> set = new HashSet<String>();
		set.add("id");
		set.add("title");
		set.add("name");
		set.add("images");
		jr.setData(BeanUtil.getFieldValueMapForList(list, set));
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
	 * 根据区域id获取区域详情
	 * @param response
	 * @param type
	 * @param id
	 * Lee.J.Eric
	 * 2014年7月8日 下午3:47:06
	 */
	@RequestMapping(value="getspaceshow")
	public void getSpaceshow(HttpServletResponse response,Integer type,String id){
		Jr jr = new Jr();
		jr.setMethod("getspaceshow");
		if(id==null||id.isEmpty()){
			jr.setCord(-1);
		}else {
			Spaceshow spaceshow = spaceshowService.findById(id);
			if(spaceshow==null){
				jr.setCord(1);
			}else {
				Set<String> set = new HashSet<String>();
				set.add("id");
				set.add("title");
				set.add("name");
				set.add("images");
				jr.setCord(0);
				jr.setData(BeanUtil.getFieldValueMap(spaceshow, set));
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
}
