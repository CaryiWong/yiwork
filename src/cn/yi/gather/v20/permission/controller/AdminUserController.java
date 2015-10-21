package cn.yi.gather.v20.permission.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.jpush.api.push.model.Platform;
import cn.yi.gather.v20.entity.Activity;
import cn.yi.gather.v20.entity.ActivityJoin;
import cn.yi.gather.v20.entity.Gathering;
import cn.yi.gather.v20.entity.Labels;
import cn.yi.gather.v20.entity.Reviewcontent;
import cn.yi.gather.v20.entity.Tribe;
import cn.yi.gather.v20.entity.WorkSpaceInfo;
import cn.yi.gather.v20.permission.entity.AdminRole;
import cn.yi.gather.v20.permission.entity.AdminUser;
import cn.yi.gather.v20.permission.service.IAdminPerService;
import cn.yi.gather.v20.permission.service.IAdminRoleService;
import cn.yi.gather.v20.permission.service.IAdminUserService;
import cn.yi.gather.v20.service.IActivityJoinService;
import cn.yi.gather.v20.service.IActivityService;
import cn.yi.gather.v20.service.IJPushService;
import cn.yi.gather.v20.service.ILabelsService;
import cn.yi.gather.v20.service.ILittleActivityService;
import cn.yi.gather.v20.service.IReviewcontentService;
import cn.yi.gather.v20.service.ITribeService;
import cn.yi.gather.v20.service.IWorkSpaceInfoService;

import com.alipay.sign.MD5;
import com.common.CreateHtml;
import com.common.DES;
import com.common.Jr;
import com.common.Page;
import com.common.R;
import com.tools.utils.JSONUtil;
import com.tools.utils.MD5Util;

/**
 * 后台-小活动控制器
 * @author Lee.J.Eric
 * @time 2014年6月13日下午12:05:01
 */
@Controller("AdminUserControllerV20")
@RequestMapping(value = "v20/admin/permission")
public class AdminUserController {
	@Resource (name="adminRoleService")
	private IAdminRoleService adminRoleService;
	
	@Resource (name="adminUserService")
	private IAdminUserService adminUserService;
	
	@Resource (name="adminPerService")
	private IAdminPerService adminPerService;
	
	@Resource (name="workSpaceInfoService")
	private IWorkSpaceInfoService workSpaceService;
	
	
	@RequestMapping(value = "adminuserlist")
	public ModelAndView adminUserList(){
		
		return new ModelAndView("admin/permission/getUserList1");
	}
	
	/**
	 * 跳到添加管理员页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "adduser")
	public ModelAndView addUser(ModelMap modelMap){
		List<AdminRole> roleList = adminRoleService.selectRoleAll();
		List<WorkSpaceInfo> spaceList=workSpaceService.findAll();
		modelMap.put("roleList", roleList);
		modelMap.put("spaceList", spaceList);
		return new ModelAndView("admin/permission/addUser");
	}
	
	/**
	 * 新增管理员
	 * @param response
	 * @param role
	 * @param request
	 */
	@RequestMapping(value="saveuser")
	@ResponseBody
	public void saveUser(HttpServletResponse response,AdminUser user,HttpServletRequest request){
		Jr jr = new Jr();
		jr.setMethod("saveuser");
		 
		if(user.getUsername()==null ||user.getConnectphone()==null ||user.getDescription()==null ||user.getWorkspaceinfo()==null){
			jr.setCord(-1);
			jr.setMethod("参数错误，缺少参数");
		}else{
			String pwd=user.getUserpassword();
			DES des = new DES();
			//user.setUserpassword(des.getEncString(pwd));
			
			
			AdminUser t= adminUserService.SaveOrUpdate(user);
			jr.setCord(0);
			jr.setMsg("保存成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据角色ID查询用户列表
	 * @param response
	 * @param role
	 * @param request
	 */
	@RequestMapping(value="searchuserbyroleid")
	@ResponseBody
	public void searchUserByRoleId(HttpServletResponse response,String roleid,HttpServletRequest request){
		Jr jr = new Jr();
		jr.setMethod("searchuserbyroleid");
		if(roleid==null ||roleid.equals("")){
			jr.setCord(-1);
			jr.setMethod("参数错误，缺少参数");
		}else{
			List<AdminUser> userlist= adminPerService.searchUserByRoleId(roleid);
			if(userlist.size()<=0){
				//调用删除方法 删除角色
				jr.setCord(0);
				jr.setMsg("删除角色成功");
			}else{
				//还有用户使用该角色 不能删除
				jr.setCord(-2);
				jr.setMsg("还有用户使用该角色 不能删除!");
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
	 *  到新增角色界面
	 * @return
	 */
	@RequestMapping(value = "addrole")
	public ModelAndView addRole(){
		return new ModelAndView("admin/permission/addRole");
	}
	
	/**  新建页面保存
	 * 保存角色
	 * @param response
	 * @param role
	 * @param request
	 */
	@RequestMapping(value="saverole")
	@ResponseBody
	public void saveRole(HttpServletResponse response,AdminRole role,HttpServletRequest request){
		Jr jr = new Jr();
		jr.setMethod("saverole");
		if(role.getRolename()==null ||role.getDescription()==null){
			jr.setCord(-1);
			jr.setMethod("参数错误，缺少参数");
		}else{
			role.setCreatetime(new Date());
			AdminRole t= adminRoleService.SaveOrUpdate(role);
			jr.setCord(0);
			jr.setMsg("角色保存成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**  
	 * 更新角色
	 * @param response
	 * @param role
	 * @param request
	 */
	@RequestMapping(value="updaterole")
	@ResponseBody
	public void updateRole(HttpServletResponse response,AdminRole role,String permitList,HttpServletRequest request){
		try {
			role.setRolename(URLDecoder.decode(role.getRolename(), "utf-8"));
			role.setDescription(URLDecoder.decode(role.getDescription(),
					"utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Jr jr = new Jr();
		jr.setMethod("updateRole");
		if(role.getRolename()==null ||role.getDescription()==null ||permitList.equals("")){
			jr.setCord(-1);
			jr.setMethod("参数错误，缺少参数");
		}else{
			adminRoleService.updateRoleByRoleid(role, permitList);
			adminRoleService.SaveOrUpdate(role);
			jr.setCord(0);
			jr.setMsg("角色更新成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**  
	 * 更新角色
	 * @param response
	 * @param role
	 * @param request
	 */
	@RequestMapping(value="deleterole")
	@ResponseBody
	public void deleteRole(HttpServletResponse response,String roleid,HttpServletRequest request){
		Jr jr = new Jr();
		jr.setMethod("updateRole");
		if(roleid==null ||roleid.equals("")){
			jr.setCord(-1);
			jr.setMethod("参数错误，缺少参数");
		}else{
			AdminRole adminrole=adminRoleService.findById(roleid);
			adminrole.setIsdel(1);
			adminRoleService.SaveOrUpdate(adminrole);
			jr.setCord(0);
			jr.setMsg("角色删除成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping(value = "addPermission")
	public ModelAndView addPermission(){
		
		return new ModelAndView("admin/permission/getUserList1");
	}
	
	
	
	/**
	 * 到角色管理
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "permanage")
	public ModelAndView perManage(ModelMap modelMap){
		List<AdminRole> roleList = adminRoleService.selectRoleAll();
		modelMap.put("roleList", roleList);
		return new ModelAndView("admin/permission/addPermit");
	}
	
	
	/**
	 * 到系统管理员管理列表
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "userlist")
	public ModelAndView userList(ModelMap modelMap){
		List<AdminUser> userList = adminUserService.selectAllUser();
		modelMap.put("userList", userList);
		return new ModelAndView("admin/permission/getUserList");
	}
	
	/**
	 * 到系统管理员查看详细
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "selectuser")
	public ModelAndView selectUser(ModelMap modelMap,String userId){
		modelMap.put("userId", userId);
		return new ModelAndView("admin/permission/modifyUser");
	}
	
	/**
	 * 更新保存管理员信息
	 * @param response
	 * @param role
	 * @param permitList
	 * @param request
	 */
	@RequestMapping(value="updateuser")
	@ResponseBody
	public void updateUser(HttpServletResponse response,AdminUser user,HttpServletRequest request){
		Jr jr = new Jr();
		jr.setMethod("updateUser");
		if(user.getId()==null ||user.getDescription()==null){
			jr.setCord(-1);
			jr.setMethod("参数错误，缺少参数");
		}else{
			AdminUser oldUser = adminUserService.findById(user.getId());
			oldUser.setAdminrole(user.getAdminrole());
			oldUser.setDescription(user.getDescription());
			//oldUser.setUserpassword(user.getUserpassword());
			oldUser.setUsername(user.getUsername());
			
			String pwd=user.getUserpassword();
			DES des = new DES();
			//oldUser.setUserpassword(des.getEncString(pwd));
			
			
			adminUserService.SaveOrUpdate(oldUser);
			jr.setCord(0);
			jr.setMsg("管理员更新成功");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 删除管理员，设置为不可用状态
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "deleteuser")
	public void deleteUser(HttpServletResponse response,ModelMap modelMap,String userId){
		Jr jr =new Jr();
		jr.setMethod("deleteuser");
		
		AdminUser user= adminUserService.findById(userId);
		if(user!=null){
			user.setIsdel(1); //设置删除标记
			adminUserService.SaveOrUpdate(user);
			jr.setCord(0);
			jr.setMsg("删除管理员'"+user.getUsername()+"'成功");
		}else{
			jr.setCord(1);
			jr.setMsg("管理员不存在");
		}
		try {
			JSONUtil jsonUtil = new JSONUtil();
			jsonUtil.printJSONtoClient(jr, response,"yyyy-MM-dd HH:mm:ss");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
