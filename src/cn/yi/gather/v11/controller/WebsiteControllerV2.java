package cn.yi.gather.v11.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.yi.gather.v11.entity.Applyvip;
import cn.yi.gather.v11.entity.Joinapplication;
import cn.yi.gather.v11.entity.Spaceshow;
import cn.yi.gather.v11.entity.User;
import cn.yi.gather.v11.service.IApplyvipServiceV2;
import cn.yi.gather.v11.service.IJoinapplicationServiceV2;
import cn.yi.gather.v11.service.ISpaceshowServiceV2;
import cn.yi.gather.v11.service.IUserServiceV2;

import com.common.Jr;
import com.tools.utils.BeanUtil;
import com.tools.utils.JSONUtil;

@Controller("websiteControllerV2")
@RequestMapping(value="v2/website")
public class WebsiteControllerV2 {

	@Resource(name = "applyvipServiceV2")
	private IApplyvipServiceV2 applyvipServiceV2;
	
	@Resource(name = "userServiceV2")
	private IUserServiceV2 userServiceV2;
	
	@Resource(name = "joinapplicationServiceV2")
	private IJoinapplicationServiceV2 joinapplicationServiceV2;
	
	@Resource(name = "spaceshowServiceV2")
	private ISpaceshowServiceV2 spaceshowServiceV2;
	
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
			applyvip = applyvipServiceV2.applyvipSaveOrUpdate(applyvip);
			if(applyvip==null){
				jr.setCord(1);
				jr.setMsg("保存失败");
			}else {
				User user = new User(applyvip);
				if(!userServiceV2.checkForEmail(user.getEmail())&&!userServiceV2.checkForTelephone(user.getTelnum())){
					userServiceV2.userSaveOrUpdate(user);
				}
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
			Applyvip applyvip = applyvipServiceV2.findById(id);
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
			joinapplication = joinapplicationServiceV2.joinapplicationSaveOrUpdate(joinapplication);
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
			Joinapplication joinapplication = joinapplicationServiceV2.findById(id);
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
		List<Spaceshow> list = spaceshowServiceV2.getAllSpaceshowList();
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
			Spaceshow spaceshow = spaceshowServiceV2.findById(id);
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
